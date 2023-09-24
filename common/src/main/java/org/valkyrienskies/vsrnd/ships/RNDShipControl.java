package org.valkyrienskies.vsrnd.ships;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.api.ServerShipUser;
import org.valkyrienskies.core.impl.api.ShipForcesInducer;
import org.valkyrienskies.core.impl.api.Ticked;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)

public class RNDShipControl implements ShipForcesInducer, ServerShipUser {

    @JsonIgnore
    ServerShip ship;
    @JsonIgnore
    private ArrayList<ArrayList<Vec3>> forces;
    @JsonIgnore
    private ArrayList<ArrayList<Vec3>> newforces;

    public RNDShipControl() {
        forces = new ArrayList<ArrayList<Vec3>>();
        newforces =  new ArrayList<ArrayList<Vec3>>();
    }
    @Override
    public void applyForces(@NotNull PhysShip physShip) {
        PhysShipImpl Impl = (PhysShipImpl) physShip;
        if (newforces.isEmpty() && forces.isEmpty()) return;
        System.out.println(forces);
        for (ArrayList<Vec3> force : forces) {
            Vector3d Pos = new Vector3d(force.get(0).x,force.get(0).y,force.get(0).z);
            Vector3d Dir = new Vector3d(force.get(1).x,force.get(1).y,force.get(1).z);


            Impl.applyInvariantForceToPos(Dir,Pos);
            forces.remove(force);
        }
        forces = newforces;
        newforces.clear();
    }



    static public RNDShipControl getOrCreate(ServerShip ship) {
        RNDShipControl control = ship.getAttachment(RNDShipControl.class);
        if (control != null) return control;
        control = new RNDShipControl();
        control.setShip(ship);
        ship.saveAttachment(RNDShipControl.class, control);
        return control;
    }

    public void addForce(Vec3 Pos, Vec3 Dir) {
        ArrayList<Vec3> list = new ArrayList<Vec3>();
        list.add(0,Pos);
        list.add(1,Dir);
        newforces.add(list);
    }
    @Nullable
    @Override
    public ServerShip getShip() {
        return this.ship;
    }

    @Override
    public void setShip(@Nullable ServerShip serverShip) {
        this.ship = serverShip;
    }
}
