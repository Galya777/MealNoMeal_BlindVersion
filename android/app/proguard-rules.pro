# AdMob rules
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep public class com.google.ads.** {
   public *;
}

# Keep our MainActivity and its public methods for potential JNI/Reflection
-keep class com.nothicalnonsence.mealnomeal.MainActivity {
    public void showBannerAd();
    public void hideBannerAd();
    public void showInterstitialAd();
}

# General Android rules to prevent over-optimization of resources
-keep class androidx.appcompat.widget.** { *; }
-keep class com.google.android.material.** { *; }
