package net.sweningteam.not_enough_dragons.client.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.platform.PlatformHelper;
import net.sweningteam.not_enough_dragons.platform.Services;
import org.lwjgl.sdl.SDL;

import java.util.function.Supplier;

public class ModKeyMappings {
    public static final KeyMapping.Category DRAGON_CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "dragon_controls"));
    public static final KeyMapping DRAGON_CHARGE_MAIN_ATTACK_KEY = register("key.not_enough_dragons.ability.dragon_main_attack", InputConstants.Type.KEYBOARD, InputConstants.KEY_R,DRAGON_CATEGORY);

    public static KeyMapping register(String name, InputConstants.Type type, int key, KeyMapping.Category category){
        return Services.PLATFORM.getRegisterHelper().registerKeyMapping(name,type,key,category);
    }

    public static void init() {
        NotEnoughDragons.LOGGER.info(" [Client] Registering KeyMappings for {}!", NotEnoughDragons.MOD_NAME);
    }
}
