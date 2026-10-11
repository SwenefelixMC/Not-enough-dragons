package net.sweningteam.not_enough_dragons.neoforge.platform;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.sweningteam.not_enough_dragons.platform.NetworkHelper;
import net.sweningteam.not_enough_dragons.platform.PlatformHelper;
import net.sweningteam.not_enough_dragons.platform.RegisterHelper;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements PlatformHelper {
    NeoForgeRegistryHelper registryHelper;

    NeoForgeNetworkHelper networkHelper;
    @Override
    public RegisterHelper getRegisterHelper() {
        if(registryHelper == null) {
            registryHelper = new NeoForgeRegistryHelper();
        }
        return registryHelper;
    }

    @Override
    public NetworkHelper getNetworkHelper() {
        if(networkHelper == null) {
            networkHelper = new NeoForgeNetworkHelper();
        }
        return networkHelper;
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }
}
