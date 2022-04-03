package com.elishi.android.Class;

public class SpinnerItem {
    private String ITEM_ID;
    private String ITEM_NAME;

    public SpinnerItem(String ITEM_ID, String ITEM_NAME) {
        this.ITEM_ID = ITEM_ID;
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }
}
