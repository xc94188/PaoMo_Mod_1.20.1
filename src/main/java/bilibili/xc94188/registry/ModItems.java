package bilibili.xc94188.registry;

import bilibili.xc94188.PaoMo;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item STICK = registerItem("stick", new ModItemTipsNoCtrl("stick", new FabricItemSettings()));

    public static final Item AIR = registerItem("air", new ModItemTipsNoCtrl("air", new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(PaoMo.MOD_ID, name), item);
    }

    public static void registerModItems() {
        PaoMo.LOGGER.info("Registering Mod Items for " + PaoMo.MOD_ID);
    }
}
