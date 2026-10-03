@echo off
title 2D Classic Snake Game
echo ============================================================
echo      Compiling 2D Snake Java Swing Game...
echo ============================================================
if not exist "bin" mkdir bin
javac -d bin src\*.java
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed! Ensure Java JDK is installed and in your PATH.
    pause
    exit /b
)

echo.
echo [SUCCESS] Build completed! Launching Snake Game...
start javaw -cp bin Main
exit
