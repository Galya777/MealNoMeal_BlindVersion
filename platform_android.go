//go:build android
// +build android

package main

import (
	"log"
)

// PlatformBridgeAndroid handles Android-specific ad and IAP functionality
type PlatformBridgeAndroid struct {
	adManager *AdManager
}

func NewPlatformBridgeAndroid(am *AdManager) *PlatformBridgeAndroid {
	return &PlatformBridgeAndroid{adManager: am}
}

// InitializeAdMob sets up AdMob SDK
// This will be called from Java/Kotlin via gomobile
func (pb *PlatformBridgeAndroid) InitializeAdMob(appID string) {
	log.Println("Initializing AdMob with AppID:", appID)
	// JNI call to initialize MobileAds.initialize()
}

// ShowBannerAd shows banner at bottom
func (pb *PlatformBridgeAndroid) ShowBannerAd(adUnitID string) {
	if pb.adManager.ShouldShowAds() {
		log.Println("Showing banner ad:", adUnitID)
		// JNI call to create and show banner
	}
}

// HideBannerAd hides banner
func (pb *PlatformBridgeAndroid) HideBannerAd() {
	log.Println("Hiding banner ad")
	// JNI call to hide banner
}

// ShowInterstitialAd shows full-screen ad
func (pb *PlatformBridgeAndroid) ShowInterstitialAd(adUnitID string) {
	if pb.adManager.ShouldShowAds() {
		log.Println("Showing interstitial:", adUnitID)
		// JNI call to load and show interstitial
	}
}

// LoadInterstitialAd preloads ad for faster display
func (pb *PlatformBridgeAndroid) LoadInterstitialAd(adUnitID string) {
	log.Println("Loading interstitial:", adUnitID)
	// JNI call to preload
}

// InitiatePurchase starts billing flow for Remove Ads
func (pb *PlatformBridgeAndroid) InitiatePurchase(sku string) {
	log.Println("Starting purchase for:", sku)
	// JNI call to BillingClient.launchBillingFlow()
}

// RestorePurchases queries owned items
func (pb *PlatformBridgeAndroid) RestorePurchases() {
	log.Println("Restoring purchases")
	// JNI call to BillingClient.queryPurchases()
}

// OnPurchaseCompleted callback from Java
func (pb *PlatformBridgeAndroid) OnPurchaseCompleted(sku string, success bool) {
	if success && sku == "remove_ads" {
		pb.adManager.SetPremium(true)
		log.Println("Premium activated!")
	}
}
