# Player Prediction Mod

A Minecraft 1.21.1 Fabric mod that predicts where players will move and displays their movement history.

## Features

✅ **Player Movement Prediction** - Calculates velocity from history  
✅ **Backtrack Visualization** - Green trails showing where players have been  
✅ **Prediction Display** - Red boxes showing predicted next positions  
✅ **Velocity Vectors** - Yellow lines showing movement direction  
✅ **Auto Memory Cleanup** - Efficient data management  
✅ **High Performance** - Optimized for smooth gameplay  

## Installation

1. Ensure you have Fabric Loader 0.15+ installed
2. Download the latest JAR from releases
3. Place in `.minecraft/mods/` folder
4. Launch Minecraft with Fabric

## Building from Source

### Requirements
- Java 21+
- Git

### Build

```bash
git clone https://github.com/jujadisowsk-coder/Player-Prediction.git
cd Player-Prediction
bash build.sh  # or build.bat on Windows
```

JAR will be at: `build/libs/player-prediction-1.0.0.jar`

## Configuration

Edit `PlayerTracker.java` to adjust:
- `MAX_HISTORY` - Maximum trail points (default: 200)
- `HISTORY_RETENTION_TICKS` - How long to keep data (default: 600 ticks = 30 seconds)
- `VELOCITY_SMOOTHING` - Prediction smoothness (default: 0.7)

## How It Works

1. **Tracking** - Stores player positions and velocity every tick
2. **Prediction** - Calculates next position using smoothed velocity
3. **Rendering** - Displays trails, predictions, and velocity vectors
4. **Cleanup** - Automatically removes old data to prevent memory leaks

## License

MIT License
