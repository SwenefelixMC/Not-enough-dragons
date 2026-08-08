package net.sweningteam.platform;

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
