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

## Monetization checklist

If you plan to receive money:

- Paid app OR In-App Purchases OR Subscriptions OR Ads
- If using IAP/subscriptions: implement purchase flow + restore purchases
- Add Terms of Service
- Update privacy policy disclosures if you add analytics/ads/IAP
