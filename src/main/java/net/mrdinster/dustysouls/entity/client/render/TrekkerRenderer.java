package net.mrdinster.dustysouls.entity.client.render;

import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.mrdinster.dustysouls.entity.client.model.TrekkerModel;
import net.mrdinster.dustysouls.entity.custom.TrekkerEntity;

public class TrekkerRenderer extends GeoEntityRenderer<TrekkerEntity, EntityRenderState> {
    public TrekkerRenderer(EntityRendererProvider.Context context) {
        super(context, new TrekkerModel());
        this.shadowRadius = 0.6F;
    }

    @Override
    public void extractRenderState(TrekkerEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.extractRenderState(entity, entityRenderState, partialTick);
        if (entity.isBaby()) {
            this.shadowRadius = 0.3F;
        } else {
            this.shadowRadius = 0.6F;
        }
    }


    @Override
    public void adjustModelBonesForRender(RenderPassInfo<EntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        DefaultAnimations.hardcodedHeadRotation(renderPassInfo,snapshots,"head");
    }
}
