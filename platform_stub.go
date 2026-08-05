//go:build !android && !ios
// +build !android,!ios

package main

// PlatformBridgeStub for desktop testing (no actual ads)
type PlatformBridgeStub struct {
	adManager *AdManager
}

func NewPlatformBridgeStub(am *AdManager) *PlatformBridgeStub {
	return &PlatformBridgeStub{adManager: am}
}

func (pb *PlatformBridgeStub) InitializeAdMob(appID string) {}
func (pb *PlatformBridgeStub) ShowBannerAd(adUnitID string) {
	if pb.adManager.ShouldShowAds() {
		// Log only on desktop
		pb.adManager.ShowBanner()
	}
}
func (pb *PlatformBridgeStub) HideBannerAd() {
	pb.adManager.HideBanner()
}
func (pb *PlatformBridgeStub) ShowInterstitialAd(adUnitID string) {
	if pb.adManager.ShouldShowAds() {
		pb.adManager.ShowInterstitial()
	}
}
func (pb *PlatformBridgeStub) LoadInterstitialAd(adUnitID string) {}
func (pb *PlatformBridgeStub) InitiatePurchase(sku string) {
	// Simulate purchase success for desktop testing
	pb.adManager.SetPremium(true)
}
func (pb *PlatformBridgeStub) RestorePurchases() {
	// Simulate restore
}
func (pb *PlatformBridgeStub) OnPurchaseCompleted(sku string, success bool) {
	if success && sku == "remove_ads" {
		pb.adManager.SetPremium(true)
	}
}
