package main

import (
	"fyne.io/fyne/v2"
	_ "fyne.io/fyne/v2"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/dialog"
	"fyne.io/fyne/v2/widget"
)

// AdManager controls ads and premium status
type AdManager struct {
	isPremium   bool
	adsEnabled  bool
	bannerShown bool
}

func NewAdManager() *AdManager {
	return &AdManager{
		isPremium:   false,
		adsEnabled:  true,
		bannerShown: false,
	}
}

// Check if ads should be shown
func (am *AdManager) ShouldShowAds() bool {
	return !am.isPremium && am.adsEnabled
}

// Mark as premium (Remove Ads purchased)
func (am *AdManager) SetPremium(status bool) {
	am.isPremium = status
	if status {
		am.bannerShown = false
	}
}

// Show banner ad (called by platform bridge)
func (am *AdManager) ShowBanner() {
	if am.ShouldShowAds() {
		am.bannerShown = true
		// Platform-specific implementation will handle actual banner
	}
}

// Hide banner ad
func (am *AdManager) HideBanner() {
	am.bannerShown = false
}

// Show interstitial ad (between games)
func (am *AdManager) ShowInterstitial() {
	if am.ShouldShowAds() {
		// Platform-specific implementation
	}
}

// Premium UI
func (am *AdManager) ShowPremiumDialog(parent fyne.Window) {
	if am.isPremium {
		dialog.ShowInformation("Premium", "You already have Premium (No Ads)!", parent)
		return
	}

	content := widget.NewLabel("Remove all ads permanently for $1.99\n\nSupport the developer and enjoy ad-free gaming!")

	purchaseBtn := widget.NewButton("Purchase $1.99", func() {
		am.InitiatePurchase(parent)
	})

	cancelBtn := widget.NewButton("Cancel", nil)

	d := dialog.NewCustom("Remove Ads", "", container.NewVBox(content, purchaseBtn, cancelBtn), parent)
	cancelBtn.OnTapped = func() { d.Hide() }
	d.Show()
}

// Initiate purchase flow
func (am *AdManager) InitiatePurchase(parent fyne.Window) {
	// This will be implemented by platform bridge
	// For now, show coming soon
	dialog.ShowInformation("Purchase", "Connecting to store...", parent)
}

// Restore purchases
func (am *AdManager) RestorePurchases(parent fyne.Window) {
	// Check if user already bought on another device
	dialog.ShowInformation("Restore", "Checking for previous purchases...", parent)
}
