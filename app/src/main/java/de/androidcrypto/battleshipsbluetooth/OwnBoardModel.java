package de.androidcrypto.battleshipsbluetooth;

import java.io.Serializable;

public class OwnBoardModel implements Serializable {

    private static final long serialVersionUID = -5623483631843607252L;

    private int[] color;

    public OwnBoardModel(int[] color) {
        this.color = color;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
