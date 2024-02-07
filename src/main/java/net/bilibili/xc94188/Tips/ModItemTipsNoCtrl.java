package net.bilibili.xc94188.Tips;


import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItemTipsNoCtrl extends Item {
    String name;

    public ModItemTipsNoCtrl(String name, Settings settings) {
        super(settings);
        this.name = name;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.paomo." + name + ".tip").formatted(Formatting.GREEN));
        super.appendTooltip(stack, world, tooltip, context);
    }
}