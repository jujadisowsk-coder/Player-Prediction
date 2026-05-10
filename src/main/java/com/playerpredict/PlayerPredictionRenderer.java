package com.playerpredict;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.Vec3d;
import java.util.List;

public class PlayerPredictionRenderer {
    private static final float GREEN = 0.0f;   // Backtrack trail
    private static final float RED = 1.0f;     // Prediction
    private static final float YELLOW = 1.0f; // Velocity
    private static final float BLUE = 0.0f;
    private static final float ALPHA = 0.8f;

    public static void renderPlayerPredictions(WorldRenderContext context) {
        VertexConsumer vertexConsumer = context.consumers().getBuffer(RenderLayer.getLines());
        Vec3d cameraPos = context.camera().getPos();

        // Render trails and predictions for all players
        for (String playerId : PlayerTracker.getAllTrackedPlayers()) {
            List<Vec3d> trail = PlayerTracker.getPlayerTrail(playerId);
            
            // Render backtrack trail (green)
            renderTrail(vertexConsumer, trail, cameraPos, GREEN, 1.0f, BLUE, ALPHA);
            
            // Render prediction (red box)
            Vec3d predictedPos = PlayerTracker.predictNextPosition(playerId);
            if (predictedPos != null) {
                renderPredictionBox(vertexConsumer, predictedPos, cameraPos, RED, BLUE, BLUE, ALPHA);
                
                // Render velocity vector (yellow)
                if (!trail.isEmpty()) {
                    Vec3d lastPos = trail.get(trail.size() - 1);
                    renderLine(vertexConsumer, lastPos, predictedPos, cameraPos, YELLOW, YELLOW, BLUE, ALPHA);
                }
            }
        }
    }

    private static void renderTrail(VertexConsumer consumer, List<Vec3d> positions, Vec3d cameraPos, 
                                    float r, float g, float b, float a) {
        if (positions.size() < 2) return;
        
        for (int i = 0; i < positions.size() - 1; i++) {
            Vec3d pos1 = positions.get(i).subtract(cameraPos);
            Vec3d pos2 = positions.get(i + 1).subtract(cameraPos);
            renderLine(consumer, pos1, pos2, Vec3d.ZERO, r, g, b, a);
        }
    }

    private static void renderPredictionBox(VertexConsumer consumer, Vec3d center, Vec3d cameraPos,
                                           float r, float g, float b, float a) {
        Vec3d pos = center.subtract(cameraPos);
        float size = 0.5f;
        
        float x = (float) pos.x;
        float y = (float) pos.y;
        float z = (float) pos.z;
        
        // Draw box edges
        int color = toColor(r, g, b, a);
        consumer.vertex(x - size, y - size, z - size).color(color).next();
        consumer.vertex(x + size, y - size, z - size).color(color).next();
        consumer.vertex(x + size, y - size, z - size).color(color).next();
        consumer.vertex(x + size, y + size, z - size).color(color).next();
        consumer.vertex(x + size, y + size, z - size).color(color).next();
        consumer.vertex(x - size, y + size, z - size).color(color).next();
        consumer.vertex(x - size, y + size, z - size).color(color).next();
        consumer.vertex(x - size, y - size, z - size).color(color).next();
    }

    private static void renderLine(VertexConsumer consumer, Vec3d start, Vec3d end, Vec3d cameraPos,
                                   float r, float g, float b, float a) {
        Vec3d s = start.subtract(cameraPos);
        Vec3d e = end.subtract(cameraPos);
        int color = toColor(r, g, b, a);
        
        consumer.vertex(s.x, s.y, s.z).color(color).next();
        consumer.vertex(e.x, e.y, e.z).color(color).next();
    }

    private static int toColor(float r, float g, float b, float a) {
        return ((int)(a * 255) << 24) | ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
    }
}
