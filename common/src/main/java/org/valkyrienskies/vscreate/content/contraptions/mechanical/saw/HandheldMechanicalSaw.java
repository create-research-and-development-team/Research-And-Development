package org.valkyrienskies.vscreate.content.contraptions.mechanical.saw;

import com.simibubi.create.content.curiosities.armor.BackTankUtil;
import com.simibubi.create.foundation.utility.AbstractBlockBreakQueue;
import com.simibubi.create.foundation.utility.TreeCutter;
import com.simibubi.create.foundation.utility.VecHelper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.NonnullDefault;
import org.valkyrienskies.vscreate.util.SimpleBackTankHelper;

import java.util.Optional;
import java.util.function.Consumer;

@NonnullDefault
public class HandheldMechanicalSaw extends AxeItem {
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

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return getItemDamage(stack, amount, entity, onBroken);
    }

    @ExpectPlatform
    public static <T extends LivingEntity> int getItemDamage(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return amount;
    }
}
