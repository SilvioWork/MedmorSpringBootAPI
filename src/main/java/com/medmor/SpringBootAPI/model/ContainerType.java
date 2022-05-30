package com.medmor.SpringBootAPI.model;


public enum ContainerType {
    CARDBOARD, PLASTIC, GLASS, NYLON, UNASIGNED;

    private String type;

    ContainerType(String type) {
        this.type = type;
    }

    ContainerType() {
    }

    public String getType() {
        return type;
    }
}
