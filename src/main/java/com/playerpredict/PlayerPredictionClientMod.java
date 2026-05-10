package com.playerpredict;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class PlayerPredictionClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PlayerPredictionMod.LOGGER.info("[Player Prediction] Client initialized!");
        
        WorldRenderEvents.LAST.register(context -> {
            PlayerPredictionRenderer.renderPlayerPredictions(context);
        });
    }
}
