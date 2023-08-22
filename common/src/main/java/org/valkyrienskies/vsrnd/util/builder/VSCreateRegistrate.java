package org.valkyrienskies.vsrnd.util.builder;

import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.valkyrienskies.vsrnd.platform.Dist;
import org.valkyrienskies.vsrnd.platform.SharedValues;
import org.valkyrienskies.vsrnd.platform.VSCItem;

import java.util.function.Supplier;

public class VSCreateRegistrate {

	public static <T extends VSCItem, P> NonNullUnaryOperator<ItemBuilder<T, P>> customRenderedItem(
			Supplier<Supplier<CustomRenderedItemModelRenderer>> supplier) {
		return b -> {
			Dist.onClient(() -> customRenderedItem(b, supplier));
			return b;
		};
	}

	@Environment(EnvType.CLIENT)
	private static <T extends VSCItem, P> void customRenderedItem(ItemBuilder<T, P> b,
																  Supplier<Supplier<CustomRenderedItemModelRenderer>> supplier) {
		b.onRegister(new CustomRendererRegistrationHelper(supplier));
	}

	@Environment(EnvType.CLIENT)
	private static void registerCustomRenderedItem(VSCItem entry, CustomRenderedItemModelRenderer renderer) {
		CreateClient.MODEL_SWAPPER.getCustomItemModels()
								  .register(RegisteredObjects.getKeyOrThrow(entry), CustomRenderedItemModel::new);
	}

	@Environment(EnvType.CLIENT)
	private record CustomRendererRegistrationHelper(
			Supplier<Supplier<CustomRenderedItemModelRenderer>> supplier) implements NonNullConsumer<VSCItem> {
		@Override
		public void accept(VSCItem entry) {
			CustomRenderedItemModelRenderer renderer = supplier.get().get();
			SharedValues.customRenderedRegisterer().accept(entry, renderer);
			registerCustomRenderedItem(entry, renderer);
		}
	}
}
