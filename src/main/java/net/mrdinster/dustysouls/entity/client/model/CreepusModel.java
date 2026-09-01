package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.CreepusEntity;

public class CreepusModel extends GeoModel<CreepusEntity> {


    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return DustySouls.id("creepus");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return DustySouls.id("textures/entity/creepus.png");

    }

    @Override
    public Identifier getAnimationResource(CreepusEntity animatable) {
        return DustySouls.id("creepus");
    }

}
