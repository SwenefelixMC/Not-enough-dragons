package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

import net.sweningteam.not_enough_dragons.common.entity.dragons.bolder_class.gronckle.Gronckle;
import net.sweningteam.not_enough_dragons.common.entity.dragons.strike_class.night_furry.NightFury;
import net.sweningteam.not_enough_dragons.common.entity.egg.GronckleEgg;
import net.sweningteam.not_enough_dragons.common.entity.egg.NightFuryEgg;
import net.sweningteam.not_enough_dragons.common.entity.projectile.PlasmaBlast;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final Supplier<EntityType<NightFury>> NIGHT_FURY = registerEntity("night_fury", NightFury::new , MobCategory.CREATURE, 1.5F, 1.6F,0.7F);
    public static final Supplier<EntityType<PlasmaBlast>> PLASMA_BLAST = registerEntity("plasma_blast",PlasmaBlast::new,MobCategory.MISC,0.25F,0.25F,0);

    public static final Supplier<EntityType<NightFuryEgg>> NIGHT_FURY_EGG = registerEntity("night_fury_egg", NightFuryEgg::new, MobCategory.CREATURE, 0.5F,0.55F,0.1F);

    public static final Supplier<EntityType<Gronckle>> GRONCKLE = registerEntity("gronckle", Gronckle::new , MobCategory.CREATURE, 2, 2,1.6F);
    public static final Supplier<EntityType<GronckleEgg>> GRONCKLE_EGG = registerEntity("gronckle_egg", GronckleEgg::new , MobCategory.CREATURE, 0.5F,0.55F,1.6F);

    private static <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory category, float with, float hight, float eyeHeight ){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.ENTITY_TYPE, id, ()-> EntityType.Builder.of(factory, category).sized(with, hight).eyeHeight(eyeHeight).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,id))));
    }

    public static void registerEntityAtributes(BiConsumer<EntityType<?extends LivingEntity>, AttributeSupplier> consumer){
        consumer.accept(NIGHT_FURY.get(), NightFury.createAttributes().build());
        consumer.accept(NIGHT_FURY_EGG.get(), NightFuryEgg.createAtributes().build());
        consumer.accept(GRONCKLE.get(), Gronckle.createAtributes().build());
        consumer.accept(GRONCKLE_EGG.get(), GronckleEgg.createAtributes().build());
    }


    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering EntityTypes for {}", NotEnoughDragons.MOD_NAME);
    }
}
