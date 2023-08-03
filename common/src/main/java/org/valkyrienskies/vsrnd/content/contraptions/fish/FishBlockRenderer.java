package org.valkyrienskies.vsrnd.content.contraptions.fish;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.clock.CuckooClockBlock;
import com.simibubi.create.content.kinetics.clock.CuckooClockBlockEntity;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;
import org.valkyrienskies.vsrnd.VSCreatePartials;

public class FishBlockRenderer extends KineticBlockEntityRenderer<FishBlockEntity>  {
    public FishBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);

    }



    @Override
    protected void renderSafe(FishBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (!(be instanceof FishBlockEntity))
            return;

        BlockState blockState = be.getBlockState();
        Direction direction = blockState.getValue(FishBlock.FACING);

        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());

        // Render Hands

        SuperByteBuffer Head = CachedBufferer.partial(VSCreatePartials.FISH_HEAD, blockState);
        SuperByteBuffer Tail = CachedBufferer.partial(VSCreatePartials.FISH_TAIL, blockState);
        SuperByteBuffer Flipper = CachedBufferer.partial(VSCreatePartials.FISH_FLIPPER, blockState);
        SuperByteBuffer Jaw = CachedBufferer.partial(VSCreatePartials.FISH_JAW, blockState);
        // Doors

        float Headangle =  be.Head.getValue(partialTicks);
        float Tailangle =  be.Tail.getValue(partialTicks);
        float Flipperangle =  be.Flipper.getValue(partialTicks);
        float Jawangle =  be.Jaw.getValue(partialTicks);


        Vec3 Headpivot = new Vec3(3/16f,0,12.5/16f);
        SuperByteBuffer rotatedHead =  rotateDir(Head, Headangle,Headpivot ,direction);
        SuperByteBuffer UpHead = rotateUp(rotatedHead, Headangle,Headpivot ,direction);
        unrotateDir(UpHead,Headpivot).light(light)
                .renderInto(ms, vb);



        Vec3 Tailpivot = new Vec3(12/16f,4/16f,12/16f);
        SuperByteBuffer rotatedTail =  rotateDir(Tail, Tailangle,Tailpivot ,direction);
        SuperByteBuffer UpTail = rotateUp(rotatedTail, Tailangle,Tailpivot ,direction);
        unrotateDir(UpTail,Tailpivot).light(light)
                .renderInto(ms, vb);

        Vec3 Flipperpivot = new Vec3(4/16f,2/16f,11.5/16f);
        SuperByteBuffer rotatedFlipper =  rotateDir(Flipper, Flipperangle,Flipperpivot ,direction);
        SuperByteBuffer EastFlipper = rotateEast(rotatedFlipper, Flipperangle,Flipperpivot ,direction);
        unrotateDir(EastFlipper,Flipperpivot).light(light)
                .renderInto(ms, vb);

        Vec3 JawPivot = new Vec3(3/16f,4/16f,12/16f);
        SuperByteBuffer rotatedJaw =  rotateDir(Jaw, Headangle,JawPivot ,direction);
        SuperByteBuffer UpJaw = rotateUp(rotatedJaw, Headangle,Headpivot ,direction);
        SuperByteBuffer NorthJaw = rotateNorth(UpJaw, Jawangle,JawPivot ,direction);
        unrotateDir(NorthJaw,JawPivot).light(light)
                .renderInto(ms, vb);
        // Figure


    }

    private SuperByteBuffer rotateDir(SuperByteBuffer buffer, float angle, Vec3 pivot, Direction facing) {

        //buffer.rotateCentered(Direction.UP, AngleHelper.rad(angle));
        buffer.rotateCentered(Direction.UP, AngleHelper.rad(AngleHelper.horizontalAngle(facing.getCounterClockWise())+90));
        buffer.translate(pivot);

        return buffer;
    }

    private SuperByteBuffer unrotateDir(SuperByteBuffer buffer, Vec3 pivot) {

        buffer.translate(pivot.multiply(-1,-1,-1));
        return buffer;
    }

    private SuperByteBuffer rotateUp(SuperByteBuffer buffer, float angle, Vec3 pivot, Direction facing) {

        //buffer.rotateCentered(Direction.UP, AngleHelper.rad(angle));

        buffer.rotate(Direction.UP, AngleHelper.rad(angle));

        return buffer;
    }



    private SuperByteBuffer rotateEast(SuperByteBuffer buffer, float angle, Vec3 pivot, Direction facing) {

        //buffer.rotateCentered(Direction.UP, AngleHelper.rad(angle));

        buffer.rotate(Direction.EAST, AngleHelper.rad(angle));

        return buffer;
    }


    private SuperByteBuffer rotateNorth(SuperByteBuffer buffer, float angle, Vec3 pivot, Direction facing) {

        //buffer.rotateCentered(Direction.UP, AngleHelper.rad(angle));

        buffer.rotate(Direction.NORTH, AngleHelper.rad(angle));

        return buffer;
    }
}
