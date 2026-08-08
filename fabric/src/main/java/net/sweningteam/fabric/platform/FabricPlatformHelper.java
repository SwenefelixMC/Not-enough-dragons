package net.sweningteam.fabric.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.sweningteam.platform.NetworkHelper;
import net.sweningteam.platform.PlatformHelper;
import net.sweningteam.platform.RegisterHelper;

public class FabricPlatformHelper implements PlatformHelper {
    private FabricRegistryHelper registryHelper;
    private FabricNetworkHelper networkHelper;
    @Override
    public RegisterHelper getRegisterHelper() {
        if(registryHelper == null) {
            registryHelper = new FabricRegistryHelper();
        }
        return registryHelper;
    }

    @Override
    public NetworkHelper getNetworkHelper() {
        if(networkHelper == null){
            networkHelper = new FabricNetworkHelper();
        }
        return networkHelper;
    }

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
