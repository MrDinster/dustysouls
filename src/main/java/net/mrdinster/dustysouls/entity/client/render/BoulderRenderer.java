package net.mrdinster.dustysouls.entity.client.render;

import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.mrdinster.dustysouls.entity.client.model.BoulderModel;
import net.mrdinster.dustysouls.entity.custom.BoulderEntity;
import org.jspecify.annotations.Nullable;

public class BoulderRenderer extends GeoEntityRenderer<BoulderEntity, EntityRenderState> {
    public BoulderRenderer(EntityRendererProvider.Context context) {
        super(context, new BoulderModel());
        this.shadowRadius = 0.45F;

    }


    @Override
    public void adjustModelBonesForRender(RenderPassInfo<EntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        DefaultAnimations.hardcodedHeadRotation(renderPassInfo,snapshots,"head");
    }

    @Override
    public EntityRenderState createRenderState(BoulderEntity animatable, @Nullable Void relatedObject) {
        return super.createRenderState(animatable, relatedObject);
    }


    @Override
    public void extractRenderState(BoulderEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.extractRenderState(entity, entityRenderState, partialTick);
    }
}