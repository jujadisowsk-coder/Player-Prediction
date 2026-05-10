package com.playerpredict;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerPredictionMod implements ModInitializer {
    public static final String MOD_ID = "player-prediction";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[Player Prediction] Initializing mod...");
        
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            PlayerTracker.updateAllPlayers(server);
        });
        
        ServerWorldEvents.UNLOAD.register((server, world) -> {
            PlayerTracker.clearWorld(world);
        });
        
        LOGGER.info("[Player Prediction] Mod initialized successfully!");
    }
}
