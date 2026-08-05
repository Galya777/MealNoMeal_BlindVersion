# Release (Android + iOS)

## Privacy Policy URL (GitHub Pages)

This repo contains a ready-to-publish GitHub Pages site under `docs/`.

1. In GitHub go to:
   Settings -> Pages
2. Set:
   - Source: Deploy from a branch
   - Branch: `main`
   - Folder: `/docs`
3. Save and wait for GitHub to publish.

Your Privacy Policy URL will be:

`https://galya777.github.io/MealNoMeal/privacy.html`

## Prerequisites

- Go 1.22+
- Fyne tools installed:

```bash
go install fyne.io/fyne/v2/cmd/fyne@latest
```

## App identity

- Name: Meal or No Meal
- ID: com.nothicalnonsence.mealnomeal

Do not change the ID after first release.

## Android (Google Play)

### 1) Install Android tooling

You need:
- Android SDK
- Java (JDK)

### 2) Build a release artifact

Google Play prefers Android App Bundle:

```bash
fyne package -os android -appID com.nothicalnonsence.mealnomeal -name "Meal or No Meal"
```

If you need an APK for testing:

```bash
fyne package -os android -appID com.nothicalnonsence.mealnomeal -name "Meal or No Meal" -release
```

### 3) Signing (required)

Before uploading to Google Play, your release must be signed with your keystore.
Create a keystore and keep it safe. Configure signing according to Fyne packaging requirements.

Also see store text templates in `store/google_play_listing.txt`.

## iOS (App Store)

### Important

iOS signing and App Store upload require macOS + Xcode.

### Steps

- Join Apple Developer Program
- Create App ID: com.nothicalnonsence.mealnomeal
- Create provisioning profiles and certificates
- Build/archive using Fyne packaging and Xcode
- Upload to App Store Connect (Transporter)

Also see store text templates in `store/app_store_listing.txt`.

## Store listing checklist

- App name: Meal or No Meal
- Short description + full description
- Screenshots (phone + tablet if you support them)
- Support email and/or support URL
- Privacy Policy URL (hosted)
- Age rating questionnaire completed
- Content declarations completed

## Monetization Setup (Ads + IAP)

This app includes:
- **Banner ads** (always visible at bottom)
- **Interstitial ads** (shown at end of game)
- **Remove Ads IAP** ($1.99 one-time purchase)

### AdMob Setup (required for ads to work)

1. Create **AdMob account** at https://admob.google.com
2. Add your apps:
   - Android: `com.nothicalnonsence.mealnomeal`
   - iOS: `com.nothicalnonsence.mealnomeal`
3. Create **Ad Units**:
   - Banner ad unit
   - Interstitial ad unit
4. Copy **App ID** and **Ad Unit IDs** to your build configuration

### Android - Google Play Billing

1. In Play Console: **Monetize -> Products -> In-app products**
2. Create product:
   - Product ID: `remove_ads`
   - Name: "Remove Ads"
   - Price: $1.99 (or your local currency equivalent)
3. Enable **Google Play Billing Library** in your Android build

### iOS - App Store Connect

1. In App Store Connect: **Features -> In-App Purchases**
2. Create product:
   - Reference Name: "Remove Ads"
   - Product ID: `remove_ads`
   - Type: Non-Consumable
   - Price: $1.99
3. Submit for review with your app

### Complete the Native Bridge (Important!)

The Go code includes platform bridge interfaces, but **you must complete the native implementations**:

- **Android:** Implement JNI calls in `platform_android.go` to connect to AdMob SDK and Billing Library
- **iOS:** Implement calls in `platform_ios.go` to connect to Google Mobile Ads SDK and StoreKit

### Test Ad Units (for development)

Use these test IDs to avoid policy violations during development:

**Android:**
- Banner: `ca-app-pub-3940256099942544/6300978111`
- Interstitial: `ca-app-pub-3940256099942544/1033173712`

**iOS:**
- Banner: `ca-app-pub-3940256099942544/2934735716`
- Interstitial: `ca-app-pub-3940256099942544/4411468910`

## Terms of Service

Create and host a Terms of Service page (similar to Privacy Policy) and add the URL to your store listings. Include:
- App usage terms
- In-app purchase terms
- Refund policy
- Limitation of liability

## Data Safety Form (Google Play)

When filling Data Safety:
- **Collected:** Device/Ad ID (for advertising)
- **Shared:** Yes, with Google (AdMob)
- **Purpose:** Advertising
- **Optional:** In-app purchase data (processed by Play Store)

## Full Monetization Checklist

- [ ] AdMob account created
- [ ] Ad Units created (Banner + Interstitial)
- [ ] Test ads working in development
- [ ] Real ad units configured for release
- [ ] Google Play: In-app product "remove_ads" created ($1.99)
- [ ] App Store: In-app purchase "remove_ads" created ($1.99)
- [ ] Native bridge implementations completed (Android/iOS)
- [ ] Purchase testing completed (test cards/sandbox)
- [ ] Privacy Policy updated with AdMob disclosures
- [ ] Terms of Service created and hosted
- [ ] Data Safety form submitted (Google Play)
