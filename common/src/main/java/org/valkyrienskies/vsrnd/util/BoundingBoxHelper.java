package org.valkyrienskies.vsrnd.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

/**
 * Helper functions for dealing with bounding boxes
 */
public class BoundingBoxHelper {
	/**
	 * Orient a bounding box from facing south to facing any other direction.<br>
	 * Created because the vanilla implementation doesn't support {@code UP} and {@code DOWN}.
	 *
	 * @param origin  coordinate around which rotation happens
	 * @param xOffset x offset applied before rotation
	 * @param yOffset y offset applied before rotation
	 * @param zOffset z offset applied before rotation
	 * @param width   width of the bounding box
	 * @param height  height of the bounding box
	 * @param depth   depth of the bounding box
	 * @param facing  direction to rotate the bounding box to face
	 *
	 * @return rotated bounding box
	 */
	public static BoundingBox orientBox(BlockPos origin, int xOffset, int yOffset, int zOffset, int width, int height, int depth, Direction facing) {
		return orientBox(origin.getX(),
						 origin.getY(),
						 origin.getZ(),
						 xOffset,
						 yOffset,
						 zOffset,
						 width,
						 height,
						 depth,
						 facing);
	}

	/**
	 * Orient a bounding box from facing south to facing any other direction.<br>
	 * Created because the vanilla implementation doesn't support {@code UP} and {@code DOWN}.
	 *
	 * @param originX x coordinate around which rotation happens
	 * @param originY y coordinate around which rotation happens
	 * @param originZ z coordinate around which rotation happens
	 * @param xOffset x offset applied before rotation
	 * @param yOffset y offset applied before rotation
	 * @param zOffset z offset applied before rotation
	 * @param width   width of the bounding box
	 * @param height  height of the bounding box
	 * @param depth   depth of the bounding box
	 * @param facing  direction to rotate the bounding box to face
	 *
	 * @return rotated bounding box
	 */
	public static BoundingBox orientBox(int originX, int originY, int originZ, int xOffset, int yOffset, int zOffset, int width, int height, int depth, Direction facing) {
		switch (facing) {
			case SOUTH:
			default:
				return new BoundingBox(originX + xOffset,
									   originY + yOffset,
									   originZ + zOffset,
									   originX + width - 1 + xOffset,
									   originY + height - 1 + yOffset,
									   originZ + depth - 1 + zOffset);
			case NORTH:
				return new BoundingBox(originX + xOffset,
									   originY + yOffset,
									   originZ - depth + 1 + zOffset,
									   originX + width - 1 + xOffset,
									   originY + height - 1 + yOffset,
									   originZ + zOffset);
			case WEST:
				return new BoundingBox(originX - depth + 1 + zOffset,
									   originY + yOffset,
									   originZ + xOffset,
									   originX + zOffset,
									   originY + height - 1 + yOffset,
									   originZ + width - 1 + xOffset);
			case EAST:
				return new BoundingBox(originX + zOffset,
									   originY + yOffset,
									   originZ + xOffset,
									   originX + depth - 1 + zOffset,
									   originY + height - 1 + yOffset,
									   originZ + width - 1 + xOffset);
			case UP:
				return new BoundingBox(originX + xOffset,
									   originY + zOffset,
									   originZ + height - 1 + yOffset,
									   originX + width - 1 + xOffset,
									   originY + depth - 1 + zOffset,
									   originZ + yOffset);
			case DOWN:
				return new BoundingBox(originX + xOffset,
									   originY + depth - 1 + zOffset,
									   originZ + yOffset,
									   originX + width - 1 + xOffset,
									   originY + zOffset,
									   originZ + height - 1 + yOffset);
		}
	}
}
