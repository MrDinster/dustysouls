package net.mrdinster.dustysouls.entity.client.model;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.custom.RemagerEntity;
import org.jetbrains.annotations.Nullable;

public class RemagerModel extends GeoModel<RemagerEntity> {


    private static final DataTicket<Boolean> OVERWINTERING = DataTickets.create("remager_overwintering", Boolean.class);

    private static final Identifier[] TEXTURES = new Identifier[]{
            DustySouls.id("textures/entity/remager.png"),          // Índice 0: Despierto / Normal
            DustySouls.id("textures/entity/remager_overwintering.png")  // Índice 1: Dormido / Invernando
    };


    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return DustySouls.id("remager");
    }


    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        boolean isOverwintering = renderState.getOrDefaultGeckolibData(OVERWINTERING, false);
        return TEXTURES[(isOverwintering) ? 1 : 0];
    }

    @Override
    public Identifier getAnimationResource(RemagerEntity animatable) {

        return DustySouls.id("remager");
    }

    @Override
    public void addAdditionalStateData(RemagerEntity animatable, @Nullable Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);

        // Guardamos los booleanos en sus respectivos casilleros antes de renderizar el frame
        renderState.addGeckolibData(OVERWINTERING, animatable.isOverwintering());
    }

}
