package ru.cft.focusstart.suhodolov;

public class Cell {


    private int x;
    private int y;

    private boolean checked = false;
    private boolean mined = false;
    private boolean flagged = false;

    private String state = "";

    private int surroundingMines = 0;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined(boolean mined) {
        this.mined = mined;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }
}
