package com.vydra.death.screen.modules;

public interface IModuleAction {
    String getName();
    void invoke();
    default String[] args() {
        return null;
    }

    default boolean isArgsEmpty() {
        return args() == null || args().length == 0;
    }
}
