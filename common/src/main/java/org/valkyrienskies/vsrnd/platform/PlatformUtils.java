package org.valkyrienskies.vsrnd.platform;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import org.valkyrienskies.vsrnd.util.fluid.VSCFluidTankBehaviour;

public class PlatformUtils {

	@ExpectPlatform
	public static double getReachDistance(Player player) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static Packet<?> createExtraDataSpawnPacket(Entity entity) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void setAboveGroundTicks(ServerPlayer player, int ticks) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static VSCFluidTankBehaviour cwFluidTank(BehaviourType<VSCFluidTankBehaviour> type, SmartBlockEntity te, int tanks, long tankCapacity, boolean enforceVariety) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static boolean isModLoaded(String modId) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static Attribute getReachModifier() {
		throw new AssertionError();
	}
}
