package cp3.ass03;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class LifeThreads implements Callable<ArrayList<Point>> {
    boolean[][] gameBoard;
    ArrayList<Point> survivingCells;
    int[] birth;
    int[] survives;
    int i;
    int end;

    public LifeThreads(boolean[][] gameBoard, int[] birth, int[] survives, int i, int end)
    {
        this.gameBoard = gameBoard;
        this.survivingCells = new ArrayList<Point>(0);
        this.birth = birth;
        this.survives = survives;
        this.i = i;
        if (this.i ==0)
            this.i++;
        this.end = end;
    }

    public ArrayList<Point> call() {
        //System.out.println("thread " + i);
        for (i = i; i < end; i++) {
            for (int j = 1; j < gameBoard[0].length - 1; j++) {
                // count up the number of surrounding cells that are alive
                int surrounding = 0;
                if (gameBoard[i - 1][j - 1]) {
                    surrounding++;
                }
                if (gameBoard[i - 1][j]) {
                    surrounding++;
                }
                if (gameBoard[i - 1][j + 1]) {
                    surrounding++;
                }
                if (gameBoard[i][j - 1]) {
                    surrounding++;
                }
                if (gameBoard[i][j + 1]) {
                    surrounding++;
                }
                if (gameBoard[i + 1][j - 1]) {
                    surrounding++;
                }
                if (gameBoard[i + 1][j]) {
                    surrounding++;
                }
                if (gameBoard[i + 1][j + 1]) {
                    surrounding++;
                }

                // Check for survival
                if (gameBoard[i][j]) {
                    // Cell is alive, Can the cell live? (Conway, 2-3)
                    boolean survive = true;
                    for (int si = 0; si < this.survives.length; si++) {
                        if (this.survives[si] == surrounding) {
                            // survivial!!
                            survivingCells.add(new Point(i - 1, j - 1));
                            break;
                        }
                    }

                } else // Cell is dead, will the cell be given birth? (Conway, 3)
                {
                    for (int bi = 0; bi < this.birth.length; bi++) {
                        if (this.birth[bi] == surrounding) {
                            // survivial!!
                            survivingCells.add(new Point(i - 1, j - 1));
                            break;
                        }
                    }
                }
            }
        }
        return survivingCells;
    }
}