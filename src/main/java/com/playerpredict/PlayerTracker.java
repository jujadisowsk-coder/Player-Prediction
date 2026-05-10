package com.playerpredict;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import java.util.*;

public class PlayerTracker {
    private static final Map<String, PlayerData> playerDataMap = new HashMap<>();
    private static final int MAX_HISTORY = 200;
    private static final int HISTORY_RETENTION_TICKS = 600; // 30 seconds at 20 TPS

    public static void updateAllPlayers(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            for (PlayerEntity player : world.getPlayers()) {
                updatePlayer(player);
            }
        }
        cleanupOldData();
    }

    private static void updatePlayer(PlayerEntity player) {
        String playerId = player.getUuidAsString();
        PlayerData data = playerDataMap.computeIfAbsent(playerId, k -> new PlayerData());
        
        Vec3d currentPos = player.getPos();
        data.addPosition(currentPos);
        data.updateVelocity(player.getVelocity());
    }

    public static PlayerData getPlayerData(String playerId) {
        return playerDataMap.get(playerId);
    }

    public static Vec3d predictNextPosition(String playerId) {
        PlayerData data = playerDataMap.get(playerId);
        if (data == null || data.getPositions().isEmpty()) {
            return null;
        }
        
        Vec3d lastPos = data.getLastPosition();
        Vec3d velocity = data.getSmoothedVelocity();
        
        return lastPos.add(velocity);
    }

    public static List<Vec3d> getPlayerTrail(String playerId) {
        PlayerData data = playerDataMap.get(playerId);
        return data != null ? new ArrayList<>(data.getPositions()) : Collections.emptyList();
    }

    private static void cleanupOldData() {
        playerDataMap.values().forEach(data -> {
            while (data.getPositions().size() > MAX_HISTORY) {
                data.getPositions().remove(0);
            }
            if (data.getAge() > HISTORY_RETENTION_TICKS && data.getPositions().isEmpty()) {
                data.incrementAge();
            }
        });
        
        playerDataMap.entrySet().removeIf(entry -> entry.getValue().getAge() > HISTORY_RETENTION_TICKS);
    }

    public static void clearWorld(ServerWorld world) {
        playerDataMap.clear();
    }

    public static Collection<String> getAllTrackedPlayers() {
        return playerDataMap.keySet();
    }
}
