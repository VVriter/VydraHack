package com.vydra.death.screen.commands;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class Option {
    @Getter
    private String name;

    @Getter
    private OptionType optionType;

    @Getter
    private boolean required;

    @Getter
    private List<String> compleatableStringList;

    @Getter
    private String compleatableWord;

    public Option(String name, OptionType optionType) {
        this.name = name;
        this.optionType = optionType;
        this.required = true;
    }

    public Option(String name, OptionType optionType, boolean required) {
        this.name = name;
        this.optionType = optionType;
        this.required = required;
    }

    public final void setCompleatableStringList(List<String> list) {
        this.compleatableStringList = new ArrayList<>();
        this.compleatableStringList.addAll(list);
    }

    public final void setCompleatableWord(String word) {
        this.compleatableWord = word;
    }

    public final List<Object> getCompleatedValues() {
        ArrayList<Object> objects = new ArrayList<>();
        switch (optionType) {
            case BOOLEAN: {
                objects.add(true);
                objects.add(false);
                break;
            }

            case LIST: {
                objects.addAll(getCompleatableStringList());
                break;
            }

            case STRING: {
                objects.add(getCompleatableWord());
                objects.addAll(getCompleatableStringList());
                break;
            }

            case MENTION: {

            }
        }

        return objects;
    }
}
