package org.valkyrienskies.vsrnd.content.items.tools.drill;

import com.google.common.collect.Multimap;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.item.CustomArmPoseItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import org.valkyrienskies.vsrnd.platform.PlatformUtils;
import org.valkyrienskies.vsrnd.util.BoundingBoxHelper;
import org.valkyrienskies.vsrnd.util.SimpleBackTankHelper;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@NonnullDefault
public class HandheldMechanicalDrill extends PickaxeItem implements CustomArmPoseItem {
    // Constants
    protected static final int MAX_DAMAGE = 2048;
    protected static final int MAX_BACKTANK_USES = 1000;

    // Mining Constants
    private static final int MINING_ANGLE_LIMIT = 55;

    private static final int MINE_DIAMETER = 3;
    private static final int MINE_DEPTH = 1;

    private static final int CROUCH_MINE_DIAMETER = 1;
    private static final int CROUCH_MINE_DEPTH = 3;

    public HandheldMechanicalDrill(Tier tier, int attackBonus, float attackSpeedBonus, Properties properties) {
        super(tier, attackBonus, attackSpeedBonus, SimpleBackTankHelper.getProperties(properties, MAX_DAMAGE, MAX_BACKTANK_USES));
    }

    @Override
    public boolean mineBlock(ItemStack tool, Level level, BlockState block, BlockPos pos, LivingEntity player) {
        boolean success = super.mineBlock(tool, level, block, pos, player);

        if(success && isCorrectToolForDrops(block) && !level.isClientSide){
            if(player.isCrouching()) {
                mineTunnel(level, pos, player, CROUCH_MINE_DIAMETER, CROUCH_MINE_DEPTH);
            } else {
                mineTunnel(level, pos, player, MINE_DIAMETER, MINE_DEPTH);
            }
        }

        return success;
    }

    /**
     * Mines a tunnel in the {@code direction} the player is facing at the given block.<br>
     * The tunnel is a rectangular prism of width and height {@code diameter}, and depth {@code depth}.
     *
     * @param level     level to break the tunnel in
     * @param origin       position to start the tunnel at
     * @param player    player to simulate breaking the tunnel with
     * @param diameter  diameter of the tunnel
     * @param depth     depth of the tunnel
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private void mineTunnel(Level level, BlockPos origin, LivingEntity player, int diameter, int depth) {
        Direction direction = determineDirection(player);

        int offsetX = (int)Math.ceil(-diameter / 2f);
        int offsetY = (int)Math.ceil(-diameter / 2f);
        int offsetZ = 0;

        int boundWidth = diameter;
        int boundHeight = diameter;
        int boundDepth = depth;

        BoundingBox boundingBox = BoundingBoxHelper.orientBox(origin, offsetX, offsetY, offsetZ, boundWidth, boundHeight, boundDepth, direction);

        BlockPos.betweenClosedStream(boundingBox).forEach(testPos -> tryMine(level, testPos, player));
    }

    /**
     * Attempts to simulate mining a block. If a tool is specified, it must be breakable by that tool, and must not require a diamond tool
     *
     * @param level         level to break in
     * @param pos           position to break at
     * @param livingEntity  entity to simulate the break as (can be {@code null})
     */
    private void tryMine(Level level, BlockPos pos, @Nullable LivingEntity livingEntity) {
        BlockState minedBlockState = level.getBlockState(pos);

        if(livingEntity != null) {
            ItemStack tool = livingEntity.getMainHandItem();

            if (!tool.isCorrectToolForDrops(minedBlockState) || minedBlockState.getTags().anyMatch(BlockTags.NEEDS_DIAMOND_TOOL::equals)) {
                return;
            }

            if (livingEntity instanceof Player player && player.hasCorrectToolForDrops(minedBlockState)) {
                player.awardStat(Stats.BLOCK_MINED.get(minedBlockState.getBlock()));
            }
        }

        if (level.removeBlock(pos, false))
            minedBlockState.getBlock().destroy(level, pos, minedBlockState);
        else {
            return;
        }

        if(livingEntity == null) return;
        Block.dropResources(minedBlockState, level, pos, null, livingEntity, livingEntity.getMainHandItem());
    }

    /**
     * Returns the player direction, with added logic for Up and Down based on {@code MINING_ANGLE_LIMIT}
     *
     * @param player    player direction to check
     * @return          player direction
     */
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

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> attributes = super.getDefaultAttributeModifiers(slot);
        Attribute distanceAttribute = PlatformUtils.getNewReachModifier();
        attributes.put(distanceAttribute, new AttributeModifier("Reach", -3, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BacktankUtil.isBarVisible(stack, MAX_BACKTANK_USES);
    }
    @Override
    public int getBarWidth(ItemStack stack) {
        return BacktankUtil.getBarWidth(stack, MAX_BACKTANK_USES);
    }
    @Override
    public int getBarColor(ItemStack stack) {
        return BacktankUtil.getBarColor(stack, MAX_BACKTANK_USES);
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if(BacktankUtil.canAbsorbDamage(entity, MAX_BACKTANK_USES)) {
            return 0;
        }
        return amount;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    @Nullable
    public HumanoidModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging) {
            return HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
        return null;
    }
}
