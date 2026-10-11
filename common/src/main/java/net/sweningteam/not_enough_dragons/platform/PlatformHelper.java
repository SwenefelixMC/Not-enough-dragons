package net.sweningteam.not_enough_dragons.platform;

import java.nio.file.Path;

public interface PlatformHelper {
    RegisterHelper getRegisterHelper();

    NetworkHelper getNetworkHelper();

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }
}
