#!/bin/bash
# 2D Classic Snake Game Runner for Linux/macOS

echo "============================================================"
echo "      Compiling 2D Snake Java Swing Game...                 "
echo "============================================================"

mkdir -p bin
javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo "[ERROR] Compilation failed! Please ensure a Java JDK is installed."
    exit 1
fi

echo "[SUCCESS] Build completed! Launching Snake Game..."
java -cp bin Main
