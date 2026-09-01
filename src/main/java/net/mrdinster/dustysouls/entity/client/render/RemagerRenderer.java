package net.mrdinster.dustysouls.entity.client.render;

import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.mrdinster.dustysouls.entity.client.model.RemagerModel;
import net.mrdinster.dustysouls.entity.custom.RemagerEntity;
import org.jspecify.annotations.Nullable;

public class RemagerRenderer extends GeoEntityRenderer<RemagerEntity, EntityRenderState> {
    public RemagerRenderer(EntityRendererProvider.Context context) {
        super(context, new RemagerModel());
        this.shadowRadius = 0.4F;

    }



    @Override
    public void adjustModelBonesForRender(RenderPassInfo<EntityRenderState> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);

        DefaultAnimations.hardcodedHeadRotation(renderPassInfo,snapshots,"headp");
    }

    @Override
    public EntityRenderState createRenderState(RemagerEntity animatable, @Nullable Void relatedObject) {
        return super.createRenderState(animatable, relatedObject);
    }


    @Override
    public void extractRenderState(RemagerEntity entity, EntityRenderState entityRenderState, float partialTick) {
        super.extractRenderState(entity, entityRenderState, partialTick);
    }
}