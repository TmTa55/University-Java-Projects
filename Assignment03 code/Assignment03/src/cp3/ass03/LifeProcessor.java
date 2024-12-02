package cp3.ass03;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author lewi0146
 */
public class LifeProcessor {


    public enum ComputeMode { JAVA_SINGLE, JAVA_MULTI };

    private Dimension gameBoardSize = null;
    private ArrayList<Point> point = new ArrayList<Point>(0);

    private boolean keepLiving;
    int[] birth;
    int[] survives;
    int blockSize;

    private ArrayList<LifeListener> listeners;

    /**
     * "B3/S23"
     *
     * @param birth
     * @param survives
     */
    public LifeProcessor(int[] birth, int[] survives, ArrayList<Point> point, Dimension gameBoardSize, int blockSize) {
        this.birth = birth;
        this.survives = survives;
        this.point = point;
        this.gameBoardSize = gameBoardSize;
        this.blockSize = blockSize;

        this.listeners = new ArrayList<>();
    }

    public void stopLife() {
        this.keepLiving = false;
    }

    public void processLife(int generations, ComputeMode m) {

        switch (m) {
            case JAVA_SINGLE:
                compute_java_single(generations);
                break;
            case JAVA_MULTI:
                compute_java_multi(generations);
                break;
        }
    }

    private void compute_java_single(int generations)
    {

        keepLiving = true;
        int ilive = 0;
        int movesPerSecond = 0;
        if (generations < 0) {
            movesPerSecond = -generations;
            ilive = generations-1; // ignore the ilive (go until keepLiving is false)
        }

        while (keepLiving && ilive < generations) {

            // create a new gameBoard for asessing life
            boolean[][] gameBoard = new boolean[((gameBoardSize.width) / blockSize)][((gameBoardSize.height) / blockSize)];

            // Initialise the game board with the surviving life forms (Point objects)
            for (int i = 0; i < point.size(); i++) {
                Point current = point.get(i);
                gameBoard[current.x + 1][current.y + 1] = true;
            }
            ArrayList<Point> survivingCells = new ArrayList<Point>(0);

            // Iterate through the gameBoard array, following the game of life rules
            for (int i = 1; i < gameBoard.length - 1; i++) {
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

            // update the points
            point.clear();
            point.addAll(survivingCells);

            // notify listeners
            for (LifeListener l : listeners) {
                l.lifeUpdated();
            }

            if (generations > 0) {
                ilive++;
            } else {
                try {
                    Thread.sleep(1000 / movesPerSecond);
                } catch (InterruptedException ex) {
                    break;
                }
            }

        }
    }

    private void compute_java_multi(int generations) {
        keepLiving = true;
        int ilive = 0;
        int movesPerSecond = 0;
        int threadSize = 4;
        ExecutorService pool = Executors.newFixedThreadPool(threadSize);
        if (generations < 0) {
            movesPerSecond = -generations;
            ilive = generations - 1; // ignore the ilive (go until keepLiving is false)
        }

        while (keepLiving && ilive < generations) {
            // create a new gameBoard for asessing life

            boolean[][] gameBoard = new boolean[((gameBoardSize.width) / blockSize)][((gameBoardSize.height) / blockSize)];
            //ArrayList<Thread> threads = new ArrayList<>(threadSize);

            // Initialise the game board with the surviving life forms (Point objects)
            for (int i = 0; i < point.size(); i++) {
                Point current = point.get(i);
                gameBoard[current.x + 1][current.y + 1] = true;
            }

            // Iterate through the gameBoard array, following the game of life rules
            ArrayList<Point> survivingCells = new ArrayList<>();
            for (int i = 0; i < threadSize; i++) {
                //could multithread through this part. do each i at the same time
                int columnStart = (((i*gameBoardSize.width)/blockSize)/threadSize);
                int columnEnd = ((((i+1)*gameBoardSize.width)/blockSize)/threadSize)-1;
                LifeThreads lengthRun = new LifeThreads(gameBoard, birth, survives, columnStart,columnEnd);
                Future<ArrayList<Point>> tmpPoints = pool.submit(lengthRun);
                try {
                    survivingCells.addAll(tmpPoints.get());
                    //System.out.println("thread ended " +i);
                    //System.out.println("start: "+ columnStart);
                    //System.out.println("end: "+ columnEnd);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                /*Thread t = new Thread(() ->{
                        checkCells(gameBoard,columnStart,columnEnd);
                });
                t.start();
                threads.add(t);
            }
            for (Thread t :
                    threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }

        // update the points
        point.clear();
        point.addAll(survivingCells);

        // notify listeners
        for (LifeListener l : listeners) {
            l.lifeUpdated();
        }

        if (generations > 0) {
            ilive++;
        } else {
            try {
                Thread.sleep(1000 / movesPerSecond);
            } catch (InterruptedException ex) {
                break;
            }
        }

    }
        pool.shutdown();
    }

    public void checkCells(boolean[][] gameBoard, int i, int end) {
        if (i == 0)
            i++;
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
                            point.add(new Point(i - 1, j - 1));
                            break;
                        }
                    }

                } else // Cell is dead, will the cell be given birth? (Conway, 3)
                {
                    for (int bi = 0; bi < this.birth.length; bi++) {
                        if (this.birth[bi] == surrounding) {
                            // survivial!!
                            point.add(new Point(i - 1, j - 1));
                            break;
                        }
                    }
                }
            }
        }
    }
    public void addLifeListener(LifeListener l) {
        this.listeners.add(l);
    }

}
