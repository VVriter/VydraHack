package com.vydra.death.screen.modules.impl.miscalaneous;

import com.vydra.death.screen.modules.Category;
import com.vydra.death.screen.modules.Module;
import com.vydra.death.screen.modules.settings.types.BooleanSetting;
import lombok.Getter;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.EnumHand;

public class NoEntityTrace extends Module {

    @Getter public static NoEntityTrace instance;

    public NoEntityTrace() {
        super("NoEntityTrace", Category.MISCELLANEOUS);
        instance = this;
    }

    public BooleanSetting pickAxe = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Pickaxe")
            .withDefaultValue(true)
            .build();

    public BooleanSetting gapple = new BooleanSetting.Builder()
            .withModule(this)
            .withName("Gapple")
            .withDefaultValue(true)
            .build();

    public boolean can() {
        return  (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem( ) instanceof ItemPickaxe && pickAxe.isValue()) || (mc.player.getHeldItem( EnumHand.MAIN_HAND ).getItem( ) instanceof ItemAppleGold && gapple.isValue() );
    }
}
