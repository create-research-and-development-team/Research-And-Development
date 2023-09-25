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
    private ArrayList<ArrayList<Vector3d>> forces;


    public RNDShipControl() {
        forces = new ArrayList<ArrayList<Vector3d>>();

    }
    @Override
    public void applyForces(@NotNull PhysShip physShip) {
        PhysShipImpl Impl = (PhysShipImpl) physShip;
        if (forces.isEmpty()) return;
        System.out.println(forces);
        for (ArrayList<Vector3d> force : forces) {


            Vector3d Pos = force.get(0).sub(Impl.getTransform().getPositionInShip());
            Vector3d Dir = Impl.getTransform().transformDirectionNoScalingFromWorldToShip(force.get(1), Pos).mul(10);


            Impl.applyRotDependentForceToPos(Dir,Pos);

        }
        forces.clear();
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
        ArrayList<Vector3d> list = new ArrayList<Vector3d>();
        list.add(0,new Vector3d(Pos.x,Pos.y,Pos.z) );
        list.add(1,new Vector3d(Dir.x,Dir.y,Dir.z));
        forces.add(list);
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
