package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.*;
import com.rena.lost.common.entities.misc.CustomEggEntity;
import com.rena.lost.common.entities.misc.MudBallEntity;
import com.rena.lost.common.entities.misc.SpearEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, LostInTime.MOD_ID);

    public static final RegistryObject<EntityType<Sahonachelys>> SAHONACHELYS = ENTITY_TYPES.register("sahonachelys",
            ()-> EntityType.Builder.<Sahonachelys>create(Sahonachelys::new, EntityClassification.CREATURE).size(1.2F, 0.4F).trackingRange(10).build("sahonachelys"));
    public static final RegistryObject<EntityType<Dakosaurus>> DAKOSAURUS = ENTITY_TYPES.register("dakosaurus",
            ()-> EntityType.Builder.<Dakosaurus>create(Dakosaurus::new, EntityClassification.WATER_CREATURE).size(1.2F, 0.8F).trackingRange(10).build("dakosaurus"));
    public static final RegistryObject<EntityType<Diplomoceras>> DIPLOMOCERAS = ENTITY_TYPES.register("diplomoceras",
            ()-> EntityType.Builder.<Diplomoceras>create(Diplomoceras::new, EntityClassification.WATER_AMBIENT).size(0.5f, 0.5f).build("diplomoceras"));
    public static final RegistryObject<EntityType<Hypsocormus>> HYPSOCORMUS = ENTITY_TYPES.register("hypsocormus",
            ()-> EntityType.Builder.<Hypsocormus>create(Hypsocormus::new, EntityClassification.WATER_AMBIENT).size(0.5f, 0.5f).build("hypsocormus"));
    public static final RegistryObject<EntityType<Tepexichthys>> TEPEXICHTHYS = ENTITY_TYPES.register("tepexichthys",
            ()-> EntityType.Builder.<Tepexichthys>create(Tepexichthys::new, EntityClassification.WATER_AMBIENT).size(0.5f, 0.5f).build("tepexichthys"));
    public static final RegistryObject<EntityType<Pelecanimimus>> PELECANIMIMUS = ENTITY_TYPES.register("pelecanimimus",
            ()-> EntityType.Builder.<Pelecanimimus>create(Pelecanimimus::new, EntityClassification.CREATURE).size(0.7F, 1.3F).trackingRange(10).build("pelecanimimus"));
    public static final RegistryObject<EntityType<Mirarce>> MIRARCE = ENTITY_TYPES.register("mirarce",
            ()-> EntityType.Builder.<Mirarce>create(Mirarce::new, EntityClassification.CREATURE).size(0.7F, 1.0F).trackingRange(10).build("mirarce"));

    //Projectiles
    public static final RegistryObject<EntityType<CustomEggEntity>> CUSTOM_EGG = ENTITY_TYPES.register("custom_egg",
            ()-> EntityType.Builder.<CustomEggEntity>create(CustomEggEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4).build("custom_egg"));
    public static final RegistryObject<EntityType<MudBallEntity>> MUD_BALL = ENTITY_TYPES.register("mud_ball",
            ()-> EntityType.Builder.<MudBallEntity>create(MudBallEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).trackingRange(4).updateInterval(10).build("mud_ball"));
    public static final RegistryObject<EntityType<SpearEntity>> SPEAR = ENTITY_TYPES.register("spear",
            () -> EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(LostInTime.modLoc("spear").toString()));
}
