# Native Bridge Implementation Guide

This document explains how to complete the native implementations for ads and in-app purchases.

## Overview

The Go code provides platform-agnostic interfaces in:
- `admanager.go` - Controls when ads should show and premium status
- `platform_android.go` - Android-specific bridge (needs JNI implementation)
- `platform_ios.go` - iOS-specific bridge (needs Swift/ObjC implementation)
- `platform_stub.go` - Desktop testing (no real ads)

## Android Implementation

### Required Dependencies

In your `build.gradle` (app level):

```gradle
dependencies {
    implementation 'com.google.android.gms:play-services-ads:22.0.0'
    implementation 'com.android.billingclient:billing:6.0.0'
}
```

### AndroidManifest.xml

Add to `<application>`:

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-YOUR-APP-ID" />
```

### Java/Kotlin Bridge Class

Create `AdMobBridge.java`:

```java
package com.nothicalnonsence.mealnomeal;

import android.app.Activity;
import com.google.android.gms.ads.*;

public class AdMobBridge {
    private Activity activity;
    private AdView bannerView;
    private InterstitialAd interstitialAd;
    
    public AdMobBridge(Activity activity) {
        this.activity = activity;
        MobileAds.initialize(activity);
    }
    
    public void showBanner(String adUnitId) {
        activity.runOnUiThread(() -> {
            bannerView = new AdView(activity);
            bannerView.setAdUnitId(adUnitId);
            bannerView.setAdSize(AdSize.BANNER);
            // Add to layout at bottom
        });
    }
    
    public void loadInterstitial(String adUnitId) {
        InterstitialAd.load(activity, adUnitId, new AdRequest.Builder().build(),
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(InterstitialAd ad) {
                    interstitialAd = ad;
                }
            });
    }
    
    public void showInterstitial() {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
        }
    }
}
```

### Billing Implementation

Use `BillingClient` for in-app purchases. Implement `PurchasesUpdatedListener` to handle:
- Purchase success → Call `platform_android.go` callback
- Purchase failure → Show error

### JNI Integration

The Go functions in `platform_android.go` need to call Java methods via JNI. Example pattern:

```go
// In platform_android.go
func (pb *PlatformBridgeAndroid) ShowBannerAd(adUnitID string) {
    // JNI call to AdMobBridge.showBanner()
}
```

## iOS Implementation

### Required Dependencies

Add to your Podfile:

```ruby
pod 'Google-Mobile-Ads-SDK'
```

Or use Swift Package Manager for Google Mobile Ads.

### Info.plist

Add:

```xml
<key>GADApplicationIdentifier</key>
<string>ca-app-pub-YOUR-APP-ID</string>
```

### Swift Bridge Class

Create `AdMobBridge.swift`:

```swift
import GoogleMobileAds

class AdMobBridge: NSObject {
    private var bannerView: GADBannerView?
    private var interstitial: GADInterstitialAd?
    
    func initialize() {
        GADMobileAds.sharedInstance().start(completionHandler: nil)
    }
    
    func showBanner(adUnitID: String, viewController: UIViewController) {
        let banner = GADBannerView(adSize: GADAdSizeBanner)
        banner.adUnitID = adUnitID
        banner.rootViewController = viewController
        banner.load(GADRequest())
        // Add to view hierarchy at bottom
        self.bannerView = banner
    }
    
    func loadInterstitial(adUnitID: String) {
        GADInterstitialAd.load(withAdUnitID: adUnitID, request: GADRequest()) { ad, error in
            if let ad = ad {
                self.interstitial = ad
            }
        }
    }
    
    func showInterstitial(from viewController: UIViewController) {
        if let ad = interstitial {
            ad.present(fromRootViewController: viewController)
        }
    }
}
```

### StoreKit Implementation

Use `SKPaymentQueue` for in-app purchases:

```swift
func purchaseRemoveAds() {
    let productID = "remove_ads"
    // Request product, then add payment to queue
}
```

### C-Go Bridge

Create `bridge.h` and `bridge.m` to expose Swift/ObjC functions to Go:

```c
// bridge.h
void ShowBanner(const char* adUnitID);
void ShowInterstitial();
void InitiatePurchase(const char* productID);
```

Then call from `platform_ios.go` via CGO.

## Testing

### Android
1. Use test ad unit IDs during development
2. Test purchases with Google Play's test card (always approves)
3. Enable debug logging: `MobileAds.setRequestConfiguration()`

### iOS
1. Use test ad unit IDs
2. Test purchases in Sandbox environment
3. Use StoreKit Testing in Xcode

## Common Issues

1. **Ads not showing**: Check AdMob app approval status (can take 24-48 hours)
2. **Purchase not working**: Verify product ID matches exactly between code and console
3. **Premium not persisting**: Ensure purchase validation and local storage

## Next Steps

1. Set up AdMob account and get real app ID
2. Implement native bridge classes (Java/Swift)
3. Test with test ad units
4. Switch to real ad units for release
5. Test IAP in sandbox/test environment
6. Submit to stores

For detailed questions: refer to Google AdMob documentation and Google Play Billing / Apple StoreKit documentation.
