package org.valkyrienskies.rnd.content.contraptions.propellor.stream;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.particle.AirFlowParticleData;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.utility.VecHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.valkyrienskies.rnd.platform.PlatformUtils;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PropStream {

    static boolean isClientPlayerInPropStream;
    public final IPropStreamSource source;
    public AABB bounds = new AABB(0, 0, 0, 0, 0, 0);
    public List<PropStreamSegment> segments = new ArrayList<>();
    public Direction direction;
    public boolean pushing;
    public float maxDistance;
    protected List<Entity> caughtEntities = new ArrayList<>();
    protected List<LoadedServerShip> caughtShips = new ArrayList<>();

    public PropStream(IPropStreamSource source) {
        this.source = source;
    }

    public static float getFlowLimit(Level world, BlockPos start, float max, Direction facing) {
        Vec3 directionVec = Vec3.atLowerCornerOf(facing.getNormal());
        Vec3 planeVec = VecHelper.axisAlingedPlaneOf(directionVec);

        // 4 Rays test for holes in the shapes blocking the flow
        float offsetDistance = .25f;
        Vec3[] offsets = new Vec3[]{planeVec.multiply(offsetDistance, offsetDistance, offsetDistance),
                planeVec.multiply(-offsetDistance, -offsetDistance, offsetDistance),
                planeVec.multiply(offsetDistance, -offsetDistance, -offsetDistance),
                planeVec.multiply(-offsetDistance, offsetDistance, -offsetDistance),};

        float limitedDistance = 0;

        // Determine the distance of the air flow
        Outer:
        for (int i = 1; i <= max; i++) {
            BlockPos streamPos = start.relative(facing, i);
            if (!world.isLoaded(streamPos))
                break;
            BlockState state = world.getBlockState(streamPos);
            if (shouldAlwaysPass(state))
                continue;
            VoxelShape voxelshape = state.getCollisionShape(world, streamPos, CollisionContext.empty());
            if (voxelshape.isEmpty())
                continue;
            if (voxelshape == Shapes.block()) {
                max = i - 1;
                break;
            }

            for (Vec3 offset : offsets) {
                Vec3 rayStart = VecHelper.getCenterOf(streamPos)
                        .subtract(directionVec.scale(.5f + 1 / 32f))
                        .add(offset);
                Vec3 rayEnd = rayStart.add(directionVec.scale(1 + 1 / 32f));
                BlockHitResult blockraytraceresult =
                        world.clipWithInteractionOverride(rayStart, rayEnd, streamPos, voxelshape, state);
                if (blockraytraceresult == null)
                    continue Outer;

                double distance = i - 1 + blockraytraceresult.getLocation()
                        .distanceTo(rayStart);
                if (limitedDistance < distance)
                    limitedDistance = (float) distance;
            }

            max = limitedDistance;
            break;
        }
        return max;
    }

    private static boolean shouldAlwaysPass(BlockState state) {
        return AllTags.AllBlockTags.FAN_TRANSPARENT.matches(state);
    }

    @Environment(EnvType.CLIENT)
    private static void enableClientPlayerSound(Entity e, float maxVolume) {
        if (e != Minecraft.getInstance()
                .getCameraEntity())
            return;

        isClientPlayerInPropStream = true;

        float pitch = (float) Mth.clamp(e.getDeltaMovement()
                .length() * .5f, .5f, 2f);
    }

    @Environment(EnvType.CLIENT)
    public static void tickClientPlayerSounds() {
        isClientPlayerInPropStream = false;
    }

    public static boolean isPlayerCreativeFlying(Entity entity) {
        if (entity instanceof Player player) {
            return player.isCreative() && player.getAbilities().flying;
        }
        return false;
    }

    public void tick() {
        if (direction == null)
            rebuild();
        if (direction != null) {
            Level world = source.getStreamWorld();
            Direction facing = direction;
            if (world != null && world.isClientSide) {
                float offset = pushing ? 0.5f : maxDistance + .5f;
                Vec3 pos = VecHelper.getCenterOf(source.getStreamPos())
                        .add(Vec3.atLowerCornerOf(facing.getNormal())
                                .scale(offset));
                if (world.random.nextFloat() < AllConfigs.CLIENT.fanParticleDensity.get())
                    world.addParticle(new AirFlowParticleData(source.getStreamPos()), pos.x, pos.y, pos.z, 0, 0, 0);
            }

            tickAffectedEntities(world, facing);
            tickAffectedShips(world, facing);
        }
    }

    protected void tickAffectedEntities(Level world, Direction facing) {
        for (Iterator<Entity> iterator = caughtEntities.iterator(); iterator.hasNext(); ) {
            Entity entity = iterator.next();
            if (!entity.isAlive() || !entity.getBoundingBox()
                    .intersects(bounds) || isPlayerCreativeFlying(entity)) {
                iterator.remove();
                continue;
            }

            Vec3 center = VecHelper.getCenterOf(source.getStreamPos());
            Vec3i flow = (pushing ? facing : facing.getOpposite()).getNormal();

            float sneakModifier = entity.isShiftKeyDown() ? 4096f : 512f;
            float speed = Math.abs(source.getRotspeed());
            double entityDistance = entity.position()
                    .distanceTo(center);
            float acceleration = (float) (speed / sneakModifier / (entityDistance / maxDistance) * source.getSailCount());
            Vec3 previousMotion = entity.getDeltaMovement();
            float maxAcceleration = 25;

            double xIn = Mth.clamp(flow.getX() * acceleration - previousMotion.x, -maxAcceleration, maxAcceleration);
            double yIn = Mth.clamp(flow.getY() * acceleration - previousMotion.y, -maxAcceleration, maxAcceleration);
            double zIn = Mth.clamp(flow.getZ() * acceleration - previousMotion.z, -maxAcceleration, maxAcceleration);

            entity.setDeltaMovement(previousMotion.add(new Vec3(xIn, yIn, zIn).scale(1 / 8f)));
            entity.fallDistance = 0;

            if (entity.level.isClientSide)
                enableClientPlayerSound(entity, Mth.clamp(speed / 128f * .4f, 0.01f, .4f));
            else if (entity instanceof ServerPlayer)
                PlatformUtils.setAboveGroundTicks((ServerPlayer) entity, 1);

            entityDistance -= .5f;

        }

    }

    protected void tickAffectedShips(Level world, Direction facing) {

    }

    public void rebuild() {
        if (source.getRotspeed() == 0) {
            maxDistance = 0;
            segments.clear();
            bounds = new AABB(0, 0, 0, 0, 0, 0);
            return;
        }

        direction = source.getStreamOriginSide();
        pushing = source.getStreamDirection() == direction;
        maxDistance = source.getMaxDistance();

        Level world = source.getStreamWorld();
        BlockPos start = source.getStreamPos();
        float max = this.maxDistance;
        Direction facing = direction;
        Vec3 scale2 = VectorConversionsMCKt.toMinecraft(source.getStreamScale());
        Vec3 directionVec = Vec3.atLowerCornerOf(facing.getNormal());
        maxDistance = getFlowLimit(world, start, max, facing);

        // Determine segments with transported fluids/gases
        PropStreamSegment streamSegment = new PropStreamSegment();
        segments.clear();
        streamSegment.startOffset = 0;

        int limit = (int) (maxDistance + .5f);
        int searchStart = pushing ? 0 : limit;
        int searchEnd = pushing ? limit : 0;
        int searchStep = pushing ? 1 : -1;

        streamSegment.endOffset = searchEnd + searchStep;
        segments.add(streamSegment);

        // Build Bounding Box
        if (maxDistance < 0.25f)
            bounds = new AABB(0, 0, 0, 0, 0, 0);
        else {
            float factor = maxDistance - 1;
            Vec3 scale = directionVec.scale(factor);
            if (factor > 0)
                bounds = new AABB(start.relative(direction)).inflate(scale2.x, scale2.y, scale2.z).expandTowards(scale);
            else {
                bounds = new AABB(start.relative(direction)).contract(scale.x, scale.y, scale.z)
                        .move(scale);
            }
        }

    }

    public void findEntities() {
        caughtEntities.clear();
        caughtEntities = source.getStreamWorld()
                .getEntities(null, bounds);
    }

    public void findShips() {
//        if (!source.getStreamWorld().isClientSide)
//            return;
//        ServerShipWorld world = VSGameUtilsKt.getShipObjectWorld(Objects.requireNonNull((ServerLevel) source.getStreamWorld()));
//        caughtShips.clear();
//        caughtShips = (List<LoadedServerShip>) world.getLoadedShips().getIntersecting(VectorConversionsMCKt.toJOML(bounds));
    }

    public static class PropStreamSegment {
        int startOffset;
        int endOffset;
    }

}
