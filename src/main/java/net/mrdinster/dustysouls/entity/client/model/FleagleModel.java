package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.FleagleEntity;
import org.jspecify.annotations.Nullable;

public class FleagleModel extends GeoModel<FleagleEntity> {

    private static final DataTicket<Boolean> BABY = DataTickets.create("lilchick", Boolean.class);

    private static final Identifier[] MODELS = new Identifier[]{
            DustySouls.id("fleagle"),
            DustySouls.id("lilchick")};
    private static final Identifier[] TEXTURES = new Identifier[]{
            DustySouls.id("textures/entity/fleagle.png"),
            DustySouls.id("textures/entity/lilchick.png")};
    private static final Identifier[] ANIMATIONS = new Identifier[]{
            DustySouls.id("fleagle"),
            DustySouls.id("lilchick")};

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODELS[renderState.getOrDefaultGeckolibData(BABY, false) ? 1 : 0];
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURES[renderState.getOrDefaultGeckolibData(BABY, false) ? 1 : 0];
    }


    @Override
    public Identifier getAnimationResource(FleagleEntity animatable) {
        return ANIMATIONS[animatable.isBaby() ? 1 : 0];
    }

    @Override
    public void addAdditionalStateData(FleagleEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(BABY, animatable.isBaby());
    }
}
