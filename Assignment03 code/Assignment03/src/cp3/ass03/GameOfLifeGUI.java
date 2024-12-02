package cp3.ass03;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOfLifeGUI implements LifeListener, GameBoardListener
{
    private JTextField jTextFieldBlockSize;
    private JCheckBox jCheckBoxGridOn;
    private JComboBox jComboBoxFillPercentage;
    private JButton jButtonRandomFill;
    private JTextField jTextFieldFile;
    private JButton jButtonLoad;
    private JButton jButtonSave;
    private JTextField jTextFieldBirths;
    private JTextField jTextFieldSurvives;
    private JTextField jTextFieldGenerations;
    private JComboBox jComboBoxThreadMode;
    private JButton jButtonPlay;
    private JTextField jTextFieldElapsedTime;
    private JPanel jPanelGameOfLife;
    private JPanel jPanelGameBoard;
    private JButton jPanelStop;

    private GameBoard gb;
    JFrame app;

    LifeProcessor lp;
    TitledBorder gbBorder;

    public GameOfLifeGUI()  {


        this.gb = new GameBoard();
        jPanelGameBoard.add(gb, BorderLayout.CENTER);

        gbBorder = BorderFactory.createTitledBorder("Game of Life Board: <width, height>");
        jPanelGameBoard.setBorder(gbBorder);

        this.gb.addGameBoardListener(this);

        app = new JFrame();
        app.getContentPane().add(this.jPanelGameOfLife);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.pack();
        app.setSize(app.getWidth(),500);

        addListeners();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                app.setVisible(true);
            }
        });

    }

    protected void saveToFile()
    {
        String filename = this.jTextFieldFile.getText();

        try {
            gb.saveGameBoard(filename);
        } catch (IOException ex) {

            JOptionPane.showMessageDialog(app, "Error saving to file " + filename + ": " + ex.getMessage());

        }
    }
    protected void loadFromFile() {
        String filename = this.jTextFieldFile.getText();

        try {
            gb.loadGameBoard(filename);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(app, "Error reading from file " + filename + ": " + ex.getMessage());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(app, "Error parsing from file " + filename + ": " + ex.getMessage());
        }

    }

    protected void play()
    {
        int blockSize = Integer.parseInt(this.jTextFieldBlockSize.getText());
        String[] birthS = this.jTextFieldBirths.getText().split(",");
        int[] birth = new int[birthS.length];
        for (int i = 0; i < birthS.length; i++) {
            birth[i] = Integer.parseInt(birthS[i].trim());
        }

        String[] survivesS = this.jTextFieldSurvives.getText().split(",");
        int[] survives = new int[survivesS.length];
        for (int i = 0; i < survivesS.length; i++) {
            survives[i] = Integer.parseInt(survivesS[i].trim());
        }

        lp = new LifeProcessor(birth, survives, this.gb.getPoints(), this.gb.getSize(), blockSize);
        lp.addLifeListener(this);

        int generations = Integer.parseInt(this.jTextFieldGenerations.getText());
        LifeProcessor.ComputeMode computeMode = LifeProcessor.ComputeMode.class.getEnumConstants()[this.jComboBoxThreadMode.getSelectedIndex()];

        long startTime = System.currentTimeMillis();
        lp.processLife(generations, computeMode);
        long endTime = System.currentTimeMillis();
        this.jTextFieldElapsedTime.setText(getElapsedTimeString(endTime - startTime));

    }

    public String getElapsedTimeString(long elapsedTime) {
        String format = String.format("%%0%dd", 2);
        String millisecs = String.format(format, elapsedTime % 1000);
        elapsedTime /= 1000;
        String seconds = String.format(format, elapsedTime % 60);
        String minutes = String.format(format, (elapsedTime % 3600) / 60);
        String hours = String.format(format, elapsedTime / 3600);
        String time = hours + ":" + minutes + ":" + seconds + ":" + millisecs;
        return time;
    }

    @Override
    public void lifeUpdated() {
        gb.repaint();
    }

    @Override
    public void gameBoardDimensionUpdated(Dimension gameBoardSize) {
        this.gbBorder.setTitle("Game of Life Board: " + gameBoardSize.getWidth() + ", " + gameBoardSize.getHeight());
        this.jPanelGameBoard.repaint();
    }

    protected void addListeners()
    {
        jButtonLoad.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        jTextFieldBlockSize.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int blockSize = Integer.parseInt(jTextFieldBlockSize.getText());

                gb.updateBlockSize(blockSize);
            }
        });
        jCheckBoxGridOn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                gb.updateDrawGrid(jCheckBoxGridOn.isSelected());
            }
        });
        jButtonRandomFill.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int percentage = Integer.parseInt((String) jComboBoxFillPercentage.getSelectedItem());

                gb.randomlyFillBoard(percentage);
            }
        });
        jButtonLoad.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });
        jButtonSave.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        jTextFieldFile.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });
        jButtonPlay.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });
    }
}
