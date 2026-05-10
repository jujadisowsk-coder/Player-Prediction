@echo off
echo Building Player Prediction Mod for Minecraft 1.21.1...
call gradlew.bat build
echo Build complete! JAR file at: build\libs\player-prediction-1.0.0.jar
pause
