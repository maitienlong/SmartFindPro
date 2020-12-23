package com.poly.smartfindpro.ui.identification.veriface.internal;

import java.io.Serializable;

public class CardType implements Serializable {
    private String cardId;
    private String cardName;
    private TVCardOrientation orientation;
    private boolean requireBackside;

    public CardType(String cardId, String cardName, boolean requireBackside, TVCardOrientation orientation) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.requireBackside = requireBackside;
        this.orientation = orientation;
    }

    public String getCardId() {
        return this.cardId;
    }

    public String getCardName() {
        return this.cardName;
    }

    public TVCardOrientation getOrientation() {
        return this.orientation;
    }

    public boolean isRequireBackside() {
        return this.requireBackside;
    }

    public static enum TVCardOrientation {
        VERTICAL,
        HORIZONTAL;

        private TVCardOrientation() {
        }
    }
}
