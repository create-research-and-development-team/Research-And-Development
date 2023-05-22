package org.valkyrienskies.vscreate.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class BoundingBoxHelper {
    public static BoundingBox orientBox(int structureMinX, int structureMinY, int structureMinZ, int xMin, int yMin, int zMin, int width, int height, int depth, Direction facing) {
        switch (facing) {
            case SOUTH:
            default:
                return new BoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + width - 1 + xMin, structureMinY + height - 1 + yMin, structureMinZ + depth - 1 + zMin);
            case NORTH:
                return new BoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ - depth + 1 + zMin, structureMinX + width - 1 + xMin, structureMinY + height - 1 + yMin, structureMinZ + zMin);
            case WEST:
                return new BoundingBox(structureMinX - depth + 1 + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMin, structureMinY + height - 1 + yMin, structureMinZ + width - 1 + xMin);
            case EAST:
                return new BoundingBox(structureMinX + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + depth - 1 + zMin, structureMinY + height - 1 + yMin, structureMinZ + width - 1 + xMin);
            case UP:
                return new BoundingBox(structureMinX + xMin, structureMinY + zMin, structureMinZ + height - 1 + yMin, structureMinX + width - 1 + xMin, structureMinY + depth - 1 + zMin, structureMinZ + yMin);
            case DOWN:
                return new BoundingBox(structureMinX + xMin, structureMinY + depth - 1 + zMin, structureMinZ + yMin, structureMinX + width - 1 + xMin, structureMinY + zMin, structureMinZ + height - 1 + yMin);
        }
    }
}
