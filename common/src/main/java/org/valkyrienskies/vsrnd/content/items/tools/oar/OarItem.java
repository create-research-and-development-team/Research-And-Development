package org.valkyrienskies.vsrnd.content.items.tools.oar;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.vsrnd.ships.RNDShipControl;
import rbasamoyai.createbigcannons.fabric.mixin.ArmTileEntityMixin;

import java.util.logging.Level;

public class OarItem extends Item {
    public OarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) return InteractionResult.PASS;

        Ship ship = (Ship) VSGameUtilsKt.getShipObjectEntityMountedTo(context.getLevel(), context.getPlayer());
        System.out.println(ship==null);
        if (ship==null) return InteractionResult.PASS;
        RNDShipControl control = RNDShipControl.getOrCreate( (ServerShip) ship);
        control.addForce(context.getPlayer().position(),context.getPlayer().getForward().multiply(10000,10000,10000));
        return InteractionResult.SUCCESS;
    }
}
