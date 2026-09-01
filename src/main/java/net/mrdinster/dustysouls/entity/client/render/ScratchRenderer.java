package net.mrdinster.dustysouls.entity.client.render;

import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.mrdinster.dustysouls.entity.client.model.ScratchModel;
import net.mrdinster.dustysouls.entity.custom.ScratchEntity;
import org.jspecify.annotations.Nullable;

public class ScratchRenderer extends GeoEntityRenderer<ScratchEntity, EntityRenderState> {
    public ScratchRenderer(EntityRendererProvider.Context context) {
        super(context, new ScratchModel());
        this.shadowRadius = 0.6F;

    }


    @Override
    public void adjustModelBonesForRender(RenderPassInfo<EntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        DefaultAnimations.hardcodedHeadRotation(renderPassInfo,snapshots,"neck");
    }

    @Override
    public EntityRenderState createRenderState(ScratchEntity animatable, @Nullable Void relatedObject) {
        return super.createRenderState(animatable, relatedObject);
    }


    @Override
    public void extractRenderState(ScratchEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.extractRenderState(entity, entityRenderState, partialTick);
    }
}