#!/bin/bash

# Последен опит за диагностика.

# 0. Проверка за NDK
if [ -z "$ANDROID_NDK_HOME" ]; then
    echo "ГРЕШКА: ANDROID_NDK_HOME не е зададена."
    exit 1
fi

FYNE_CMD="./bin/fyne-api36"

# Проверка на инструмента
if [ ! -f "$FYNE_CMD" ]; then
    echo "ГРЕШКА: Модифицираният fyne не е намерен в $FYNE_CMD"
    echo "Моля, първо изпълнете: ./tools/build_fyne_api36.sh"
    exit 1
fi

echo "--- 1. Генериране на пакета с API 36 (финален опит за диагностика) ---"
# Изтриваме стария лог
rm -f fyne_build.log

# Изпълняваме командата и записваме всичко в лог
"$FYNE_CMD" package -os android --app-id com.nothicalnonsence.mealnomeal -name "Meal_No_Meal_Release" --release > fyne_build.log 2>&1

# Проверяваме дали е създаден .aab файл
AAB_FILE=$(ls -t *.aab 2>/dev/null | head -n 1)

if [ -n "$AAB_FILE" ]; then
    echo "--- УСПЕХ! Генериран е файл: $AAB_FILE ---"
    # ... (останалата част от скрипта за подписване)
else
    echo "ГРЕШКА: Файлът не беше генериран."
    echo "--- Съдържание на fyne_build.log: ---"
    cat fyne_build.log
    echo "--- Край на fyne_build.log ---"

    echo ""
    echo "--- Проверка на системните логове за 'killed process' (OOM Killer) ---"
    # Проверяваме дали операционната система е убила процеса поради липса на памет
    dmesg | grep -i -E "killed process|out of memory" | tail -n 10
    echo "--- Край на системните логове ---"
fi