package net.bilibili.xc94188.Items;

import net.bilibili.xc94188.Enchantments.ModEnchantments;
import net.bilibili.xc94188.PaoMo;
import net.bilibili.xc94188.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup PAOMO_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(PaoMo.MOD_ID, "paomo"),
            FabricItemGroup.builder().displayName(Text.translatable("活"))
                    .icon(() -> new ItemStack(ModItems.STICK)).entries((displayContext, entries) -> {
                        entries.add(ModItems.STICK);
                        entries.add(ModItems.AIR);
                        entries.add(ModItems.YOU_WERE_DECEIVED);
                        entries.add(ModBlocks.STICK_BLOCK);
                        entries.add(ModItems.ZHUGE);
                    }).build());

    public static void registerModItemGroup() {
        PaoMo.LOGGER.debug("Registering mod item group for" + PaoMo.MOD_ID);
    }
}
