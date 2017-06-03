package org.dave.ocsensors.misc;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dave.ocsensors.OCSensors;

import java.io.File;

public class ConfigurationHandler {
    public static Configuration configuration;

    public static File configDir;
    public static File nbtDataDir;

    private static final String CATEGORY_SENSOR = "Sensor";

    public static void init(File configFile) {
        if(configuration != null) {
            return;
        }

        configDir = new File(configFile.getParentFile(), OCSensors.MODID);
        if(!configDir.exists()) {
            configDir.mkdirs();
        }

        nbtDataDir = new File(configDir, "nbt");
        if(!nbtDataDir.exists()) {
            nbtDataDir.mkdirs();
        }

        configuration = new Configuration(new File(configDir, "settings.cfg"), null);
        loadConfiguration();
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent event) {
        if(!event.getModID().equalsIgnoreCase(OCSensors.MODID)) {
            return;
        }

        loadConfiguration();
    }

    private static void loadConfiguration() {
        SensorSettings.maxRange = configuration.getFloat(
                "maxRange",
                CATEGORY_SENSOR,
                16.0f, 0.0f, 2048.0f,
                "Maximum range a sensor can scan"
        );

        SensorSettings.maxSearchRange = configuration.getFloat(
                "maxSearchRange",
                CATEGORY_SENSOR,
                8.0f, 0.0f, 2048.0f,
                "Maximum range a sensor can search for blocks. Setting this to a high value is a bad idea and might cause lag."
        );

        SensorSettings.disableSearch = configuration.getBoolean(
                "disableSearch",
                CATEGORY_SENSOR,
                false,
                "Disable the search method altogether"
        );

        SensorSettings.disableScanPause = configuration.getBoolean(
                "disableScanPause",
                CATEGORY_SENSOR,
                false,
                "Disable all artificial pauses when scanning. You should rather adjust the pause times!"
        );

        SensorSettings.disableSearchPause = configuration.getBoolean(
                "disableSearchPause",
                CATEGORY_SENSOR,
                false,
                "Disable all artificial pauses when searching. You should rather adjust the pause time!"
        );

        SensorSettings.pauseForAirBlock = configuration.getFloat(
                "pauseForAir",
                CATEGORY_SENSOR,
                0.02f, 0.0f, 4.0f,
                "How long it takes to scan an air block in seconds"
        );

        SensorSettings.pauseForBlock = configuration.getFloat(
                "pauseForBlock",
                CATEGORY_SENSOR,
                0.05f, 0.0f, 4.0f,
                "How long it takes to scan a normal block in seconds"
        );

        SensorSettings.pauseForTileEntity = configuration.getFloat(
                "pauseForTileEntity",
                CATEGORY_SENSOR,
                0.1f, 0.0f, 4.0f,
                "How long it takes to scan a tile entity in seconds (additive with pauseForBlock)"
        );

        SensorSettings.pauseForSearchPerBlock = configuration.getFloat(
                "pauseForSearchPerBlock",
                CATEGORY_SENSOR,
                0.0001f, 0.0f, 1.0f,
                "Each block being scanned increases the search time by this amount"
        );

        if(configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static class SensorSettings {
        public static float maxRange;
        public static float maxSearchRange;
        public static float pauseForAirBlock;
        public static float pauseForBlock;
        public static float pauseForTileEntity;
        public static float pauseForSearchPerBlock;

        public static boolean disableSearch;
        public static boolean disableScanPause;
        public static boolean disableSearchPause;
    }
}
