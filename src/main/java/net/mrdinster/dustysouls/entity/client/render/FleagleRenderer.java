package net.mrdinster.dustysouls.entity.client.render;

import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.mrdinster.dustysouls.entity.client.model.FleagleModel;
import net.mrdinster.dustysouls.entity.custom.FleagleEntity;

public class FleagleRenderer extends GeoEntityRenderer<FleagleEntity, EntityRenderState> {
    public FleagleRenderer(EntityRendererProvider.Context context) {
        super(context, new FleagleModel());
        this.shadowRadius = 0.9F;

    }


    @Override
    public void extractRenderState(FleagleEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.extractRenderState(entity, entityRenderState, partialTick);
        if (entity.isBaby()) {
            this.shadowRadius = 0.4F;
        } else {
            this.shadowRadius = 0.9F;
        }
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<EntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        DefaultAnimations.hardcodedHeadRotation(renderPassInfo,snapshots,"neck");

    }
}