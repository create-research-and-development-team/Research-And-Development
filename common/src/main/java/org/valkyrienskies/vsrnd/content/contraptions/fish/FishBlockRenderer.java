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

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());

        // Render Hands

        SuperByteBuffer Head = CachedBufferer.partial(VSCreatePartials.FISH_HEAD, blockState);

        // Doors

        float angle = be.Head.getValue(partialTicks);
        rotateHead(Head, angle, new Vec3(0,0,0),direction).light(light)
                .renderInto(ms, vb);


        // Figure


    }

    private SuperByteBuffer rotateHead(SuperByteBuffer buffer, float angle, Vec3 pivot, Direction facing) {
        //buffer.translate(pivot.x(), pivot.y(), pivot.z());
        //buffer.rotateCentered(Direction.UP, AngleHelper.rad(angle));
        buffer.rotateCentered(Direction.UP, AngleHelper.rad(AngleHelper.horizontalAngle(facing.getCounterClockWise()))+AngleHelper.rad(angle+90));
        return buffer;
    }
}
