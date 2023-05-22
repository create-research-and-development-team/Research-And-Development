package org.valkyrienskies.vscreate.content.contraptions.propeller;

import org.joml.Vector3dc;
import org.joml.Vector3ic;

import java.util.List;

public record PropellerCreatePhysData(Vector3dc bearingPos, Vector3dc bearingAxis, double bearingAngle,
                                      double bearingSpeed, List<Vector3ic> propellorPositions, boolean inverted) {

}
