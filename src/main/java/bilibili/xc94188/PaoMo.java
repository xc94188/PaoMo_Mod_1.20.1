package bilibili.xc94188;

import bilibili.xc94188.registry.ModBlocks;
import bilibili.xc94188.registry.ModCommands;
import bilibili.xc94188.registry.ModItemGroup;
import bilibili.xc94188.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaoMo implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "paomo";
    public static final Logger LOGGER = LoggerFactory.getLogger("paomo");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModCommands.registerCommands();
        ModItemGroup.registerModItemGroup();
    }
}