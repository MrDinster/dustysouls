package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.DodoEntity;
import org.jspecify.annotations.Nullable;

public class DodoModel extends GeoModel<DodoEntity> {

    private static final DataTicket<Boolean> BABY = DataTickets.create("dodo_chick", Boolean.class);

    private static final Identifier[] MODELS = new Identifier[]{
            DustySouls.id("dodo"),
            DustySouls.id("dodo_chick")};
    private static final Identifier[] TEXTURES = new Identifier[]{
            DustySouls.id("textures/entity/dodo.png"),
            DustySouls.id("textures/entity/dodo_chick.png")};
    private static final Identifier[] ANIMATIONS = new Identifier[]{
            DustySouls.id("dodo"),
            DustySouls.id("dodo_chick")};

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODELS[renderState.getOrDefaultGeckolibData(BABY, false) ? 1 : 0];
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURES[renderState.getOrDefaultGeckolibData(BABY, false) ? 1 : 0];
    }


    @Override
    public Identifier getAnimationResource(DodoEntity animatable) {
        return ANIMATIONS[animatable.isBaby() ? 1 : 0];
    }

    @Override
    public void addAdditionalStateData(DodoEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(BABY, animatable.isBaby());
    }
}
