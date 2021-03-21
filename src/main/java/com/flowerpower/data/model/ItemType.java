package com.flowerpower.data.model;

public enum ItemType {
    FLOWER,
    FLOWERPACK;

    public static String toString(ItemType itemType) {
        switch (itemType) {
            case FLOWER:
                return "Flower";
            case FLOWERPACK:
                return "Flower pack";
        }

        throw new IllegalArgumentException("Invalid key for ItemType: " + itemType);
    }
}
