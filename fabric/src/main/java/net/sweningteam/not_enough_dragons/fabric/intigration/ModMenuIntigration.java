package net.sweningteam.not_enough_dragons.fabric.intigration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.sweningteam.not_enough_dragons.common.config.ModMainConfig;

public class ModMenuIntigration implements ModMenuApi {
    boolean myBooleanOption = true;
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ParantScreen->new ModMainConfig().CONFIG.generateScreen(ParantScreen);
    }
}
