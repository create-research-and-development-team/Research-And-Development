package org.valkyrienskies.vsrnd.util.render;

import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import net.minecraft.client.resources.model.BakedModel;

public abstract class VSCreateCustomRenderedItemModel extends CustomRenderedItemModel {
    public VSCreateCustomRenderedItemModel(BakedModel template, String basePath) {
        super(template);
    }

}
