package com.beactive.core;

public enum ItemType {
    LECTURE,
    SEMINAR,
    LAB,
    TRAIN,
    INVITED_LECTURE,
    CONCERT;

    public static ItemType parseFromInt(int code) {
        switch (code) {
            case 101: {
                return LECTURE;
            }
            case 102: {
                return SEMINAR;
            }
            case 103: {
                return LAB;
            }
            case 201: {
                return TRAIN;
            }
            case 301: {
                return INVITED_LECTURE;
            }
            case 302: {
                return CONCERT;
            }
            default: {
                throw new IllegalArgumentException("Unsupported type code: " + code);
            }
        }
    }
}
