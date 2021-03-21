package com.flowerpower.data.model;

public enum OrderStatus {
    IN_PROGRESS,
    DELIVERED;

    public static String toString(OrderStatus orderStatus) {
        switch (orderStatus) {
            case IN_PROGRESS:
                return "In Progress";
            case DELIVERED:
                return "Delivered";
        }

        throw new IllegalArgumentException("Invalid key for OrderStatus: " + orderStatus);
    }

}
