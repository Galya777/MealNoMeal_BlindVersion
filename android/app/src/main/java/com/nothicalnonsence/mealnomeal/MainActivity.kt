package com.nothicalnonsence.mealnomeal

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.LoadAdError

class MainActivity : AppCompatActivity() {

    private var bannerView: AdView? = null
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            MobileAds.initialize(this) {}
        } catch (e: Exception) {
            // Log or handle initialization error to prevent crash
        }

        val root = FrameLayout(this)

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val title = androidx.appcompat.widget.AppCompatTextView(this).apply {
            text = "Meal or No Meal (Android)"
            textSize = 22f
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 16)
        }

        val showInterstitialButton = Button(this).apply {
            text = "Show interstitial (test)"
            setOnClickListener {
                interstitialAd?.show(this@MainActivity)
            }
        }

        content.addView(title)
        content.addView(showInterstitialButton)

        root.addView(content)

        setContentView(root)

        // Banner will be managed from Go via platform bridge in the future
        // For now, we auto-load it on start
        showBanner(root)
        loadInterstitial()
    }

    // Public methods to be called from Go (via JNI/Fyne bridge)
    fun showBannerAd() {
        runOnUiThread {
            bannerView?.visibility = android.view.View.VISIBLE
            bannerView?.loadAd(AdRequest.Builder().build())
        }
    }

    fun hideBannerAd() {
        runOnUiThread {
            bannerView?.visibility = android.view.View.GONE
        }
    }

    fun showInterstitialAd() {
        runOnUiThread {
            if (interstitialAd != null) {
                interstitialAd?.show(this)
            } else {
                loadInterstitial() // Reload if not ready
            }
        }
    }

    private fun showBanner(root: FrameLayout) {
        try {
            val adView = AdView(this)
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = "ca-app-pub-3940256099942544/6300978111" // Test banner

            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL

            root.addView(adView, params)
            adView.loadAd(AdRequest.Builder().build())
            bannerView = adView
        } catch (e: Exception) {
            // AdMob might fail on some devices or if Play Services are missing
        }
    }

    private fun loadInterstitial() {
        try {
            val request = AdRequest.Builder().build()
            InterstitialAd.load(
                this,
                "ca-app-pub-3940256099942544/1033173712", // Test interstitial
                request,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        interstitialAd = null
                    }
                }
            )
        } catch (e: Exception) {
            interstitialAd = null
        }
    }

    override fun onDestroy() {
        bannerView?.destroy()
        bannerView = null
        interstitialAd = null
        super.onDestroy()
    }
}
