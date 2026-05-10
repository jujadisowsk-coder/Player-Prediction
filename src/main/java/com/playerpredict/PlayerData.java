package com.playerpredict;

import net.minecraft.util.math.Vec3d;
import java.util.*;

public class PlayerData {
    private final List<Vec3d> positions = new ArrayList<>();
    private Vec3d velocity = Vec3d.ZERO;
    private Vec3d smoothedVelocity = Vec3d.ZERO;
    private int age = 0;
    private static final double VELOCITY_SMOOTHING = 0.7;

    public void addPosition(Vec3d pos) {
        positions.add(pos);
    }

    public void updateVelocity(Vec3d newVelocity) {
        this.velocity = newVelocity;
        this.smoothedVelocity = this.smoothedVelocity.multiply(VELOCITY_SMOOTHING)
                .add(newVelocity.multiply(1 - VELOCITY_SMOOTHING));
    }

    public Vec3d getLastPosition() {
        return positions.isEmpty() ? Vec3d.ZERO : positions.get(positions.size() - 1);
    }

    public Vec3d getSmoothedVelocity() {
        return smoothedVelocity;
    }

    public List<Vec3d> getPositions() {
        return positions;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        age++;
    }
}
