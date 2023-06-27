package com.vydra.death.screen.modules.action;

import com.vydra.death.screen.modules.IModuleAction;
import com.vydra.death.screen.modules.Module;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisableAction implements IModuleAction {

    private Module module;

    @Override
    public String getName() {
        return "disable";
    }

    @Override
    public void invoke() {
        module.onDisable();
    }
}
