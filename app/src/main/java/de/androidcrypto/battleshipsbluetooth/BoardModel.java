package de.androidcrypto.battleshipsbluetooth;

import java.io.Serializable;

public class BoardModel implements Serializable {

    private static final long serialVersionUID = 193857919736069505L;

    private int[] color;

    public BoardModel(int[] color) {
        this.color = color;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
