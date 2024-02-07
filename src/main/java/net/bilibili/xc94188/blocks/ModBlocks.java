package net.bilibili.xc94188.blocks;

import net.bilibili.xc94188.PaoMo;
import net.bilibili.xc94188.Items.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block STICK_BLOCK = registerBlock("stick_block", new Block(AbstractBlock.Settings.create()
            .mapColor(MapColor.IRON_GRAY)
            .requiresTool()
            .strength(3.0F, 6.0F)
            .sounds(BlockSoundGroup.METAL)));

    public static Block registerBlock(String name, Block block) {
        ModItems.registerItem(name, new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, new Identifier(PaoMo.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        PaoMo.LOGGER.info("Registering Mod Blocks for " + PaoMo.MOD_ID);
    }
}
