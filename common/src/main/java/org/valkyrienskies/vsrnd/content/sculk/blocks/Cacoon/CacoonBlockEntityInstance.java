package org.valkyrienskies.vsrnd.content.sculk.blocks.Cacoon;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllPartialModels;

import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;


import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class CacoonBlockEntityInstance  extends SingleRotatingInstance<CacoonBlockEntity> {
    public CacoonBlockEntityInstance(MaterialManager materialManager, CacoonBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }
    protected RotatingData additionalShaft;


    @Override
    public void init() {
        super.init();

        return;



        //float speed = blockEntity.getSpeed();
        //Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        //BlockPos pos = blockEntity.getBlockPos();
        //float offset = BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos);
        //Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        //Instancer<RotatingData> half = getRotatingMaterial().getModel(AllPartialModels.COGWHEEL_SHAFT, blockState,
        //        facing, () -> this.rotateToAxis(axis));

        //additionalShaft = setup(half.createInstance(), speed);
        //additionalShaft.setRotationOffset(offset);
    }
    private PoseStack rotateToAxis(Direction.Axis axis) {
        Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        PoseStack poseStack = new PoseStack();
        //TransformStack.cast(poseStack)
        //        .centre()
        //        .rotateToFace(facing)
        //        .multiply(Vector3f.XN.rotationDegrees(-90))
        //        .unCentre();
        return poseStack;
    }

    @Override
    protected Instancer<RotatingData> getModel() {

        return super.getModel();

        //Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        //Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        //return getRotatingMaterial().getModel(AllPartialModels.SHAFTLESS_LARGE_COGWHEEL, blockState, facing,
        //        () -> this.rotateToAxis(axis));
    }


    @Override
    public void update() {
        //super.update();
        if (additionalShaft != null) {
            updateRotation(additionalShaft);
            additionalShaft.setRotationOffset(CacoonBlockEntityRenderer.getShaftAngleOffset(axis, pos));
        }
    }

    @Override
    public void updateLight() {
        super.updateLight();
        if (additionalShaft != null)
            relight(pos, additionalShaft);
    }

    @Override
    public void remove() {
        super.remove();
        if (additionalShaft != null)
            additionalShaft.delete();
    }
}
