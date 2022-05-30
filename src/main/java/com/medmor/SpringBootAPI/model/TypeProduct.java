package com.medmor.SpringBootAPI.model;

public enum TypeProduct {
    ELECTRONIC, TOOLS, EQUIPMENT, LOCKSMITHERY, PLUMBING, UNASIGNED;

    private String type;

    TypeProduct(String type) {
        this.type = type;
    }

    TypeProduct() {
    }

    public String getType() {
        return type;
    }

    public static boolean contains(String s)
    {
        for(TypeProduct typeProduct:values())
            if (typeProduct.name().equals(s))
                return true;
        return false;
    }
}
