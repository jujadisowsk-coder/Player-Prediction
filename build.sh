#!/bin/bash
echo "Building Player Prediction Mod for Minecraft 1.21.1..."
chmod +x gradlew
./gradlew build
echo "Build complete! JAR file at: build/libs/player-prediction-1.0.0.jar"
