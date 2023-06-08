package com.vydra.death.screen.modules;

import lombok.Getter;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public enum Category {
    COMBAT(new ItemStack(Items.END_CRYSTAL)),
    MOVEMENT(new ItemStack(Items.ELYTRA)),
    RENDER(new ItemStack(Items.DIAMOND)),
    EXPLOITS(new ItemStack(Items.ENDER_PEARL)),
    MISCELLANEOUS(new ItemStack(Items.APPLE)),
    CLIENT(new ItemStack(Items.EXPERIENCE_BOTTLE));


    @Getter
    private ItemStack stack;
    Category(ItemStack stack) {
        this.stack = stack;
    }
}

