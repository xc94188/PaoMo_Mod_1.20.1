package net.bilibili.xc94188.registry.Foods;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoods {
    public static final FoodComponent YOU_WERE_DECEIVED = (new FoodComponent.Builder()).hunger(4).saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 0), 1.0F)
            .alwaysEdible().build();
}
