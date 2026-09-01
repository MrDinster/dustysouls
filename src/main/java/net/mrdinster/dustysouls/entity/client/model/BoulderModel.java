package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.BoulderEntity;

public class BoulderModel extends GeoModel<BoulderEntity> {


    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return DustySouls.id("boulder");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return DustySouls.id("textures/entity/boulder.png");

    }

    @Override
    public Identifier getAnimationResource(BoulderEntity animatable) {
        return DustySouls.id("boulder");
    }

}
