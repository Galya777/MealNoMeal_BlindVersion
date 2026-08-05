# 🍽️ Meal or No Meal 🍽️

A **Deal or No Deal** style game built in **Go** with the [Fyne](https://fyne.io) GUI toolkit.  
Themed around food trays instead of briefcases, the game combines luck, strategy, and a bit of fun with random **bonuses**.

---

## 🎮 Gameplay

- 26 trays (`NUM_TRAYS = 26`) each contain a hidden **cash value** or **food item**.  
- At the start, the player selects **their tray** to keep until the end.  
- The player then opens trays one by one. Opened values are **crossed off** the sidebar.  
- Every 3 trays, the **Chef** (banker) makes an offer:
  - Either a **cash deal** based on remaining trays.
  - Or a **swap offer** to exchange your tray with another unopened tray.
- Bonuses may appear once per game:
  - **Multiplier** (×2, ×3, ÷2, etc.)
  - **Additive** (+1000, -500, etc.)
- When only **1 unopened tray remains** (besides the player’s), the Chef makes **one final offer** before the last reveal.
- Finally, the **player’s tray** is opened and the prize revealed!

---

## ✨ Features

- **Randomized cash values and food items** each game.  
- **Food-themed tray replacements** for added variety.  
- **Bonus Manager**:
  - Only 1 multiplier and 1 additive per game.
  - Shown before banker’s offer.
  - Results displayed in alert dialogs.  
- **Tray management**:
  - Player’s tray becomes inactive immediately.
  - Opened trays and sidebar values marked with ✓.  
- **Final reveal logic** with last Chef offer.  
- **Replay option** at end of game.  

---

## 📦 Requirements

- [Go 1.18+](https://golang.org/dl/)  
- [Fyne Toolkit](https://developer.fyne.io/started/)  
- Local `images/` folder containing:
  - `1.jpg` … `26.jpg` → Money tray images
  - `51.jpg` … `68.jpg` → Food item images
  - Chef images for offers  

---

## 🚀 Run the Game

```bash
# Install fyne
go get fyne.io/fyne/v2

# Run
go run main.go


#You can play the game in the appstoreL

- become my tester:
https://play.google.com/store/apps/details?id=com.nothicalnonsence.mealnomeal
- official game:
TBA
