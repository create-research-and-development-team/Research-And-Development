package org.valkyrienskies.vsrnd.content.items.tools.saw;

import com.google.common.collect.Multimap;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.item.CustomArmPoseItem;
import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import com.simibubi.create.foundation.utility.TreeCutter;
import com.simibubi.create.foundation.utility.VecHelper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.NonnullDefault;
import org.valkyrienskies.vsrnd.platform.PlatformUtils;
import org.valkyrienskies.vsrnd.util.SimpleBackTankHelper;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

@NonnullDefault
public class HandheldMechanicalSaw extends AxeItem implements CustomArmPoseItem {
    public static final int MAX_DAMAGE = 2048;
    public static final int MAX_BACKTANK_USES = 1000;

    public HandheldMechanicalSaw(Tier tier, float attackBonus, float attackSpeedBonus, Properties properties) {
        super(tier, attackBonus, attackSpeedBonus, SimpleBackTankHelper.getProperties(properties, MAX_DAMAGE, MAX_BACKTANK_USES));
    }

    @Override
    public boolean mineBlock(ItemStack tool, Level level, BlockState block, BlockPos pos, LivingEntity livingEntity) {
        boolean ret = super.mineBlock(tool, level, block, pos, livingEntity);

        if(livingEntity.isCrouching()) return ret;

        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

        Optional<AbstractBlockBreakQueue> dynamicTree = TreeCutter.findDynamicTree(block.getBlock(), pos);
        if (dynamicTree.isPresent()) {
            dynamicTree.get()
                    .destroyBlocks(level, livingEntity, (blockPos, stack) -> dropItemFromCutTree(level, blockPos, stack));
            return true;
        }

        Player player = (livingEntity instanceof Player) ? (Player) livingEntity : null;
        TreeCutter.findTree(level, pos).destroyBlocks(level, tool, player, (blockPos, drop) -> dropItemFromCutTree(level, blockPos, drop));
        return ret;
    }

    public void dropItemFromCutTree(Level level, BlockPos blockPos, ItemStack drop) {
        Vec3 dropPos = VecHelper.getCenterOf(blockPos);
        ItemEntity entity = new ItemEntity(level, dropPos.x, dropPos.y, dropPos.z, drop);
        level.addFreshEntity(entity);
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
