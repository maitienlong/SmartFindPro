package com.poly.smartfindpro.ui.searchProduct.filterProduct;

public class Priority {
    private int priority;

    private boolean isAvail = false;

    private int sbd;

    public Priority(int priority, boolean isAvail, int sbd) {
        this.priority = priority;
        this.isAvail = isAvail;
        this.sbd = sbd;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isAvail() {
        return isAvail;
    }

    public void setAvail(boolean avail) {
        isAvail = avail;
    }

    public int getSbd() {
        return sbd;
    }

    public void setSbd(int sbd) {
        this.sbd = sbd;
    }
}
