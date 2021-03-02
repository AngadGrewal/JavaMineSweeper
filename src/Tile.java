public class Tile {
    private int value;
    private boolean revealed;
    private boolean flagged;

    public Tile(int value, boolean revealed, boolean flagged) {
        this.value = value;
        this.revealed = revealed;
        this.flagged = flagged;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isBomb() {
        return value == -1;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        if (revealed) {
            if (value == -1) {
                return "ó";
            }
            else if (value == 0) {
                return ".";
            }
            return "" + value;
        } else if (flagged) {
            return "F";
        } else {
            return "#";
        }
    }
}
