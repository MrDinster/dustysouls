package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.TrekkerEntity;
import org.jspecify.annotations.Nullable;

public class TrekkerModel extends GeoModel<TrekkerEntity> {
    private static final DataTicket<Boolean> BABY = DataTickets.create("trekker_baby", Boolean.class);

    private static final Identifier[] MODELS = new Identifier[]{
            DustySouls.id("trekker"),
            DustySouls.id("trekker_baby")};
    private static final Identifier[] TEXTURES = new Identifier[]{
            DustySouls.id("textures/entity/trekker.png"),
            DustySouls.id("textures/entity/trekker_baby.png")};
    private static final Identifier[] ANIMATIONS = new Identifier[]{
            DustySouls.id("trekker"),
            DustySouls.id("trekker_baby")};


    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODELS[renderState.getOrDefaultGeckolibData(BABY, false) ? 1 : 0];
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURES[renderState.getOrDefaultGeckolibData(BABY, false) ? 1 : 0];
    }


    @Override
    public Identifier getAnimationResource(TrekkerEntity animatable) {
        return ANIMATIONS[animatable.isBaby() ? 1 : 0];
    }

    @Override
    public void addAdditionalStateData(TrekkerEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(BABY, animatable.isBaby());
    }
}
