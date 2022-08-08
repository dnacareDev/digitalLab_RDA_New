package com.digitalLab.Enum;

public enum PlateStatusType {
    step(0), batch(1), all(2);

    private final int value;

    PlateStatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
