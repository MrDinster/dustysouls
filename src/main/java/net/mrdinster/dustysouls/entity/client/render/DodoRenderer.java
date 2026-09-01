package net.mrdinster.dustysouls.entity.client.render;

import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.mrdinster.dustysouls.entity.client.model.DodoModel;
import net.mrdinster.dustysouls.entity.custom.DodoEntity;

public class DodoRenderer extends GeoEntityRenderer<DodoEntity, EntityRenderState> {
    public DodoRenderer(EntityRendererProvider.Context context) {
        super(context, new DodoModel());
        this.shadowRadius = 0.6F;
    }

    @Override
    public void extractRenderState(DodoEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.extractRenderState(entity, entityRenderState, partialTick);
        if (entity.isBaby()) {
            this.shadowRadius = 0.3F; // Sombra pequeña para el dodo bebé
        } else {
            this.shadowRadius = 0.6F; // Sombra normal para el dodo adulto
        }
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<EntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        DefaultAnimations.hardcodedHeadRotation(renderPassInfo,snapshots,"neck");
    }
}