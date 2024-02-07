package net.bilibili.xc94188;

import net.bilibili.xc94188.Commands.ModCommands;
import net.bilibili.xc94188.blocks.ModBlocks;
import net.bilibili.xc94188.Enchantments.ModEnchantments;
import net.bilibili.xc94188.Items.ModItemGroup;
import net.bilibili.xc94188.Items.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaoMo implements ModInitializer {
    public static final String MOD_ID = "paomo";
    public static final Logger LOGGER = LoggerFactory.getLogger("paomo");

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModCommands.registerCommands();
        ModEnchantments.registerModEnchantments();
        ModItemGroup.registerModItemGroup();

    }
}
