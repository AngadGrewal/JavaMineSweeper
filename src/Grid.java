public class Grid {
    private final int size;
    private final Tile[][] gridArray;
    private int mineCount;
    private int flags;

    public int getSize() {
        return size;
    }

    public Grid(int size) {
        this.size = size;
        this.gridArray = new Tile[size][size];
        this.mineCount = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.random() > 0.8) {
                    gridArray[i][j] = new Tile(-1, false, false);
                    mineCount ++;
                } else {
                    gridArray[i][j] = new Tile(0, false, false);
                }
            }
        }

        this.flags = mineCount;
        System.out.println(flags);

        /* Finding the neighbour bombs of any non-bomb cell */
        for (int x = 0; x < gridArray.length; x++) {
            for (int y = 0; y < gridArray[0].length; y++) {
                // if a bomb we don't need to count
                if (!gridArray[x][y].isBomb()) {
                    int count = 0;

                    // find the left side of the boundary box
                    int xMin = Math.max(x - 1, 0);
                    // find the right side of the boundary box
                    int xMax = Math.min(x + 1, gridArray.length - 1);
                    // find the y min side of the boundary box
                    int yMin = Math.max(y - 1, 0);
                    // find the y max side of the boundary box
                    int yMax = Math.min(y + 1, gridArray[0].length - 1);

                    // now loop using the boundaries calculated above
                    for (int x2 = xMin; x2 <= xMax; x2++) {
                        for (int y2 = yMin; y2 <= yMax; y2++) {
                            count += (gridArray[x2][y2].isBomb() ? 1 : 0);
                        }
                    }
                    gridArray[x][y].setValue(count);
                }
            }
        }
    }

    public void printGrid() {
        System.out.println("");
        System.out.print("     ");
        for (int x = 0; x < gridArray.length; x++) {
            System.out.print(x + "   ");
        }
        System.out.print("\n\n");
        for (int i = 0; i < gridArray.length; i++) {
            System.out.print(i + "   ");

            for (int j = 0; j < gridArray.length; j++) {
                System.out.print(" " + gridArray[i][j] + "  ");
            }
            System.out.print("\n");
        }
        System.out.println("\nNumber of Flags Remaining: " + flags);
    }

    public void revealBombs() {
        for (int x = 0; x < gridArray.length; x++) {
            for (int y = 0; y < gridArray[0].length; y++) {
                if (gridArray[x][y].isBomb()) {
                    gridArray[x][y].setRevealed(true);
                }
            }
        }
    }

    public void emptyReveal(int x, int y) {
        int xMin = Math.max(x - 1, 0);
        int xMax = Math.min(x + 1, gridArray.length - 1);
        int yMin = Math.max(y - 1, 0);
        int yMax = Math.min(y + 1, gridArray[0].length - 1);

        for (int x2 = xMin; x2 <= xMax; x2++) {
            for (int y2 = yMin; y2 <= yMax; y2++) {
                // recursively we need to see whether the revealed are also empty
                if (gridArray[x2][y2].isEmpty() && !gridArray[x2][y2].isRevealed()) {
                    if (x2 != x || y2 != y) {
                        gridArray[x2][y2].setRevealed(true);
                        emptyReveal(x2, y2);
                    }
                }
                gridArray[x2][y2].setRevealed(true);
            }
        }
    }

    public boolean reveal(int x, int y) {
        if (gridArray[x][y].isRevealed()) {
            System.out.println("Already revealed");
        } else {
            if (gridArray[x][y].isBomb()) {
                revealBombs();
                return true;
            }
            if (gridArray[x][y].isEmpty()) {
                emptyReveal(x, y);
            } else {
                gridArray[x][y].setRevealed(true);
            }
        }
        printGrid();
        return false;
    }

    public boolean flag(int x, int y) {
        if (gridArray[x][y].isRevealed()) {
            System.out.println("Cannot Flag");
        } else {
            if (gridArray[x][y].isFlagged()) {
                gridArray[x][y].setFlagged(false);
                flags++;
            } else {
                gridArray[x][y].setFlagged(true);
                flags--;
            }
            printGrid();
        }
        if (flags == 0) {
            if (winCondition()) {
                System.out.println("\nYou Win!");
                return true;
            } else {
                printGrid();
            }
        }
        return false;
    }

    public boolean winCondition() {
        int flaggedCount = 0;
        for (int x = 0; x < gridArray.length; x++) {
            for (int y = 0; y < gridArray[0].length; y++) {
                if (gridArray[x][y].isBomb()) {
                    if (gridArray[x][y].isFlagged()) {
                        flaggedCount ++;
                    }
                }
            }
        }
        return flaggedCount == mineCount;
    }

    public void gridReveal() {
        for (int x = 0; x < gridArray.length; x++) {
            for (int y = 0; y < gridArray[0].length; y++) {
                if (!gridArray[x][y].isBomb()) {
                    gridArray[x][y].setRevealed(true);
                }
            }
        }
    }
}
