#!/usr/bin/env bash
# Билд на патчнат fyne CLI, който генерира AndroidManifest с targetSdkVersion=36.
#
# Защо: fyne.io/tools (CLI-ят зад "fyne package"/"fyne release") твърдо кодира
# targetSdkVersion=35 в cmd/fyne/internal/mobile/build.go. Google Play изисква
# targetSdk >= 36 (Android 16) за нови качвания. Няма флаг за това, затова
# компилираме CLI-я с една променена стойност.
#
# Употреба:  ./tools/build_fyne_api36.sh
# Резултат:  ./bin/fyne-api36
set -euo pipefail

TOOLS_VER="${TOOLS_VER:-v1.7.2}"
TARGET_API="${TARGET_API:-36}"

REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT_BIN="$REPO_DIR/bin/fyne-api36"
WORK_DIR="${WORK_DIR:-$(mktemp -d -t fyne-api36-XXXXXX)}"

echo "--- 1. Изтегляне на fyne.io/tools@$TOOLS_VER ---"
go mod download "fyne.io/tools@$TOOLS_VER"
SRC="$(go env GOMODCACHE)/fyne.io/tools@$TOOLS_VER"
[ -d "$SRC" ] || { echo "ГРЕШКА: не намирам $SRC"; exit 1; }

echo "--- 2. Копиране в $WORK_DIR (module cache е read-only) ---"
rm -rf "$WORK_DIR/tools"
cp -r "$SRC" "$WORK_DIR/tools"
chmod -R u+w "$WORK_DIR/tools"

echo "--- 3. Патч: target := 35 -> $TARGET_API ---"
BUILD_GO="$WORK_DIR/tools/cmd/fyne/internal/mobile/build.go"
grep -q "target := 35" "$BUILD_GO" || {
    echo "ГРЕШКА: 'target := 35' не е намерен в $BUILD_GO."
    echo "Вероятно fyne.io/tools $TOOLS_VER е сменил кода — провери ръчно."
    exit 1
}
sed -i "s/target := 35/target := $TARGET_API/" "$BUILD_GO"
grep -n "target := " "$BUILD_GO"

echo "--- 4. Компилация ---"
mkdir -p "$REPO_DIR/bin"
(cd "$WORK_DIR/tools" && go build -o "$OUT_BIN" ./cmd/fyne)

echo "--- ГОТОВО ---"
"$OUT_BIN" version || true
echo "Бинарен файл: $OUT_BIN"
