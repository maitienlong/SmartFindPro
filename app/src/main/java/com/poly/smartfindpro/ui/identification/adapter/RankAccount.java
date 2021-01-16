package com.poly.smartfindpro.ui.identification.adapter;

public class RankAccount {
    private int imge;
    private String funtion;

    public RankAccount() {
    }

    public RankAccount(int imge, String funtion) {
        this.imge = imge;
        this.funtion = funtion;
    }

    public void setImge(int imge) {
        this.imge = imge;
    }

    public void setFuntion(String funtion) {
        this.funtion = funtion;
    }

    public int getImge() {
        return imge;
    }

    public String getFuntion() {
        return funtion;
    }
}
