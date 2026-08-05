# Google Mobile Ads SDK
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.ads.mediation.** { *; }
-keep public class com.google.android.gms.ads.AdActivity { *; }
-keep public class com.google.android.gms.ads.AdView { *; }
-keep public class com.google.android.gms.ads.AdSize { *; }
-keep public class com.google.android.gms.ads.initialization.InitializationStatus { *; }
-keep public class com.google.android.gms.ads.initialization.AdapterStatus { *; }
-keep public class com.google.android.gms.ads.rewarded.RewardItem { *; }
-keep public class com.google.android.gms.ads.rewarded.ServerSideVerificationOptions { *; }
-keep public class com.google.android.gms.ads.interstitial.InterstitialAd { *; }
-keep public class com.google.android.gms.ads.formats.** { *; }
-keep class com.google.android.gms.ads.internal.build.BuildVersion { *; }

# Keep our MainActivity and its public methods for potential JNI/Reflection
-keep class com.nothicalnonsence.mealnomeal.MainActivity {
    public void showBannerAd();
    public void hideBannerAd();
    public void showInterstitialAd();
}

# General Android rules to prevent over-optimization of resources
-keep class androidx.appcompat.widget.** { *; }
-keep class com.google.android.material.** { *; }