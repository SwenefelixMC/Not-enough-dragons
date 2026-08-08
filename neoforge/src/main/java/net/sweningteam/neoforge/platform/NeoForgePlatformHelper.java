package net.sweningteam.neoforge.platform;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.sweningteam.platform.NetworkHelper;
import net.sweningteam.platform.PlatformHelper;
import net.sweningteam.platform.RegisterHelper;

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
