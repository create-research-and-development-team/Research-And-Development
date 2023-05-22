package org.valkyrienskies.vscreate.content.contraptions.mechanical.drill;

import com.simibubi.create.content.curiosities.armor.BackTankUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.lwjgl.system.NonnullDefault;
import org.valkyrienskies.vscreate.util.BacktankDamageHandler;
import org.valkyrienskies.vscreate.util.BoundingBoxHelper;

@NonnullDefault
public class HandheldMechanicalDrill extends PickaxeItem {
    // Constants
    private static final int MAX_DAMAGE = 2048;
    private static final int MAX_BACKTANK_USES = 1000;

    // Mining Constants
    private static final int MINING_ANGLE_LIMIT = 55;

    private static final int MINE_DIAMETER = 3;
    private static final int MINE_DEPTH = 1;

    private static final int CROUCH_MINE_DIAMETER = 1;
    private static final int CROUCH_MINE_DEPTH = 3;

    public HandheldMechanicalDrill(Tier tier, int attackBonus, float attackSpeedBonus, Properties properties) {
        super(tier, attackBonus, attackSpeedBonus, ((FabricItemSettings)properties)
                .maxDamage(MAX_DAMAGE)
                .customDamage(new BacktankDamageHandler(MAX_BACKTANK_USES))
        );
    }

    @Override
    public boolean mineBlock(ItemStack tool, Level level, BlockState block, BlockPos pos, LivingEntity player) {
        boolean success = super.mineBlock(tool, level, block, pos, player);

        if(success && isCorrectToolForDrops(block) && !level.isClientSide){
            if(player.isCrouching()) {
                mineTunnel(tool, level, pos, player, CROUCH_MINE_DIAMETER, CROUCH_MINE_DEPTH);
            } else {
                mineTunnel(tool, level, pos, player, MINE_DIAMETER, MINE_DEPTH);
            }
        }

        return success;
    }

    private void mineTunnel(ItemStack tool, Level level, BlockPos pos, LivingEntity player, int diameter, int depth) {
        Direction direction = determineDirection(player);

        int boundX = (int)Math.ceil(-diameter / 2f);
        int boundY = (int)Math.ceil(-diameter / 2f);
        int boundZ = 0;

        int boundWidth = diameter;
        int boundHeight = diameter;
        int boundDepth = depth;

        BoundingBox boundingBox = BoundingBoxHelper.orientBox(pos.getX(), pos.getY(), pos.getZ(), boundX, boundY, boundZ, boundWidth, boundHeight, boundDepth, direction);

        BlockPos.betweenClosedStream(boundingBox).forEach(testPos -> tryMine(tool, level, testPos, player));
    }

    private void tryMine(ItemStack tool, Level level, BlockPos pos, LivingEntity livingEntity) {
        BlockState minedBlockState = level.getBlockState(pos);
        if(!tool.isCorrectToolForDrops(minedBlockState) || minedBlockState.getTags().anyMatch(BlockTags.NEEDS_DIAMOND_TOOL::equals)) {
            return;
        }

        if(level.removeBlock(pos, false))
            minedBlockState.getBlock().destroy(level, pos, minedBlockState);
        else return;

        if(!(livingEntity instanceof Player player)) return;

        if(player.hasCorrectToolForDrops(minedBlockState)) {
            player.awardStat(Stats.BLOCK_MINED.get(minedBlockState.getBlock()));
            Block.dropResources(minedBlockState, level, pos, null, livingEntity, tool);
        }
    }

    // TODO: Move to a static helper class
    private Direction determineDirection(LivingEntity player) {
        float xRot = player.getXRot();
        if (xRot <= -MINING_ANGLE_LIMIT) {
            return Direction.UP;
        } else if (xRot >= MINING_ANGLE_LIMIT) {
            return Direction.DOWN;
        } else {
            return player.getDirection();
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BackTankUtil.isBarVisible(stack, MAX_BACKTANK_USES);
    }
    @Override
    public int getBarWidth(ItemStack stack) {
        return BackTankUtil.getBarWidth(stack, MAX_BACKTANK_USES);
    }
    @Override
    public int getBarColor(ItemStack stack) {
        return BackTankUtil.getBarColor(stack, MAX_BACKTANK_USES);
    }
}
