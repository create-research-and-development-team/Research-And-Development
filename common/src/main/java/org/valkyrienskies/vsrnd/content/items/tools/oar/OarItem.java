package org.valkyrienskies.vsrnd.content.items.tools.oar;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) return InteractionResult.PASS;

        Ship ship = (Ship) VSGameUtilsKt.getShipObjectEntityMountedTo(context.getLevel(), context.getPlayer()) ;
        if (ship == null) {
            ship = GetShipAtFeet(context.getLevel(), context.getPlayer());
        }
        System.out.println(ship==null);
        if (ship==null) return InteractionResult.PASS;
        RNDShipControl control = RNDShipControl.getOrCreate( (ServerShip) ship);
        control.addForce(context.getPlayer().position(),context.getPlayer().getForward().multiply(100000,0,100000));
        return InteractionResult.SUCCESS;
    }


    private Ship GetShipAtFeet(Level level, Player player) {
        Vec3 feet = player.getPosition(0).subtract(new Vec3(0,1.5,0));
        Vec3 offset = new Vec3(0.5,0.5,0.5);
        Iterable<Ship> ships = VSGameUtilsKt.getShipsIntersecting(level,new AABB(feet.subtract(offset),feet.add(offset)));
        System.out.println(ships);
        if (ships.iterator().hasNext()) {
            return (Ship) ships.iterator().next();
        }
        return null;
    }
}
