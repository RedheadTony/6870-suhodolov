package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    public static final String BOMB_COUNTING_TEXT = "Bomb counting: ";
    public static final String TIME_PASSED_TEXT = "Time passed: ";

    private final JLabel minesLabel;

    private final JLabel timePassedLabel;

    private final Dimension buttonPreferredSize = new Dimension(50, 50);

    private final JLabel smileLabel;
    private final JPanel boardPanel;

    private int cols = 9;
    private int rows = 9;

    private JButton[][] buttons = new JButton[cols][rows];

    public Gui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("CFT Minesweeper");
        setIconImage(new ImageIcon(Gui.class.getResource("/icons/icon.png")).getImage());
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);

        JMenuItem beginnerLevelItem = new JMenuItem("Beginner");
        JMenuItem intermediateLevelItem = new JMenuItem("Intermediate");
        JMenuItem expertLevelItem = new JMenuItem("Expert");

        JMenu difficultyMenu = new JMenu("Game difficulty");
        difficultyMenu.add(beginnerLevelItem);
        difficultyMenu.add(intermediateLevelItem);
        difficultyMenu.add(expertLevelItem);

        JMenuItem beginnerLeaderBoardItem = new JMenuItem("Beginner");
        JMenuItem intermediateLeaderBoardItem = new JMenuItem("Intermediate");
        JMenuItem expertLeaderBoardItem = new JMenuItem("Expert");

        JMenu leaderBoardMenu = new JMenu("LeaderBoard");
        leaderBoardMenu.add(beginnerLeaderBoardItem);
        leaderBoardMenu.add(intermediateLeaderBoardItem);
        leaderBoardMenu.add(expertLeaderBoardItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(fileMenu);
        jMenuBar.add(difficultyMenu);
        jMenuBar.add(leaderBoardMenu);
        setJMenuBar(jMenuBar);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout());

        int numberOfMines = 10;

        minesLabel = new JLabel(BOMB_COUNTING_TEXT + numberOfMines);

        timePassedLabel = new JLabel(TIME_PASSED_TEXT + 0);

        smileLabel = new JLabel();
        smileLabel.setPreferredSize(buttonPreferredSize);

        upperPanel.add(minesLabel);
        upperPanel.add(smileLabel);
        upperPanel.add(timePassedLabel);

        add(upperPanel, BorderLayout.NORTH);

        boardPanel = new JPanel();

        boardPanel.setLayout(new GridLayout(cols, rows));
        add(boardPanel, BorderLayout.CENTER);
    }

    public void changeDifficulty(final int rows, final int cols, final int numberOfMines) {
        buttons = new JButton[cols][rows];
        boardPanel.setLayout(new GridLayout(cols, rows));
        minesLabel.setText(BOMB_COUNTING_TEXT + numberOfMines);
        timePassedLabel.setText(TIME_PASSED_TEXT + 0);
    }

    public void setBoardButton(final int x, final int y) {
        buttons[x][y] = new JButton();
        buttons[x][y].setIcon(new ImageIcon(Gui.class.getResource("/icons/closed.png")));
        buttons[x][y].setPreferredSize(buttonPreferredSize);
    }

    public JLabel getSmileLabel() {
        return smileLabel;
    }

    public JLabel getMinesLabel() {
        return minesLabel;
    }

    public JLabel getTimePassedLabel() {
        return timePassedLabel;
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JButton[][] getButtons() {
        return buttons;
    }
}