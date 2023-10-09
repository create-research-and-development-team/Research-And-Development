package org.valkyrienskies.vsrnd.content.items.tools.oar;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.vsrnd.ships.RNDShipControl;
import rbasamoyai.createbigcannons.fabric.mixin.ArmTileEntityMixin;

public class OarItem extends Item {
    public OarItem(Properties properties) {
        super(properties);
    }

//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        if (context.getLevel().isClientSide()) return InteractionResult.PASS;
//
//        Ship ship = (Ship) VSGameUtilsKt.getShipObjectEntityMountedTo(context.getLevel(), context.getPlayer()) ;
//        if (ship == null) {
//            ship = GetShipAtFeet(context.getLevel(), context.getPlayer());
//        }
//
//        if (ship==null) return InteractionResult.PASS;
//        RNDShipControl control = RNDShipControl.getOrCreate( (ServerShip) ship);
//        control.addForce(context.getPlayer().position(),context.getPlayer().getForward().multiply(100000,100000,100000));
//        return InteractionResult.SUCCESS;
//    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (level.isClientSide()) return InteractionResultHolder.pass(stack);

        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY);
        BlockState state = level.getBlockState(blockHitResult.getBlockPos());
        if (state.getMaterial().isLiquid()) {
            Ship ship = (Ship) VSGameUtilsKt.getShipObjectEntityMountedTo(level, player) ;
            if (ship == null) {
                ship = GetShipAtFeet(level, player);
            }

            if (ship==null) return  InteractionResultHolder.pass(stack);
            RNDShipControl control = RNDShipControl.getOrCreate( (ServerShip) ship);
            control.addForce(new Vec3( blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ()),player.getForward().multiply(0.001,0,0.001));
            return InteractionResultHolder.success(stack);
        };
        return super.use(level,player,usedHand);
    }

    private Ship GetShipAtFeet(Level level, Player player) {
        Vec3 feet = player.getPosition(0).subtract(new Vec3(0,1,0));
        Vec3 offset = new Vec3(0.1,0.1,0.1);
        Iterable<Ship> ships = VSGameUtilsKt.getShipsIntersecting(level,new AABB(feet.subtract(offset),feet.add(offset)));

        if (ships.iterator().hasNext()) {
            return (Ship) ships.iterator().next();
        }
        return null;
    }
}
