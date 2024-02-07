package net.bilibili.xc94188.Items;

import net.bilibili.xc94188.PaoMo;
import net.bilibili.xc94188.Foods.ModFoods;
import net.bilibili.xc94188.Tips.ModItemTipsNoCtrl;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item STICK = registerItem("stick", new ModItemTipsNoCtrl("stick", new FabricItemSettings()));
    public static final Item AIR = registerItem("air", new ModItemTipsNoCtrl("air", new FabricItemSettings()));

    public static final Item ZHUGE = registerItem("zhuge",new ZhuGeItem(new FabricItemSettings().maxCount(1).maxDamage(465)));
    public static final Item YOU_WERE_DECEIVED = registerItem("you_were_deceived",
            new Item(new FabricItemSettings().rarity(Rarity.RARE)
                    .food(ModFoods.YOU_WERE_DECEIVED)));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(PaoMo.MOD_ID, name), item);
    }

    public static void registerModItems() {
        PaoMo.LOGGER.info("Registering Mod Items for " + PaoMo.MOD_ID);
    }

}
