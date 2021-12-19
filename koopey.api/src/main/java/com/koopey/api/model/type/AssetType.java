package com.koopey.api.model.type;

public enum AssetType {
    
    ARTICLE("article"), PRODUCT("product"), SERVICE("service");

    public final String type;

    private AssetType(String type) {
        this.type = type;
    }    

    @Override
    public String toString() {
        return type;
    }
}