package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.ScratchEntity;

public class ScratchModel extends GeoModel<ScratchEntity> {


    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return DustySouls.id("scratch");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return DustySouls.id("textures/entity/scratch.png");

    }

    @Override
    public Identifier getAnimationResource(ScratchEntity animatable) {
        return DustySouls.id("scratch");
    }

}
