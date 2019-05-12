package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.*;

/**
 * Класс, который представляет собой основной фрейм приложения, наследуется от класса JFrame
 */
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

    /**
     * Конструктор, который определяет параметры фрейма и создает все его компоненты
     */
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

    /**
     * Метод, который обновляет кнопки и лейблы в зависисимости от выбранной сложности
     *
     * @param rows          количество строк на панели
     * @param cols          количество колонок на панели
     * @param numberOfMines количество мин, которое будет отображаться в minesLabel
     */
    public void changeDifficulty(final int rows, final int cols, final int numberOfMines) {
        buttons = new JButton[cols][rows];
        boardPanel.setLayout(new GridLayout(cols, rows));
        minesLabel.setText(BOMB_COUNTING_TEXT + numberOfMines);
        timePassedLabel.setText(TIME_PASSED_TEXT + 0);
    }

    /**
     * Метод, который по входным координатам создает кнопку, которая будет связана с ячейкой игрового поля
     *
     * @param x координата
     * @param y координата
     */
    public void setBoardButton(final int x, final int y) {
        buttons[x][y] = new JButton();
        buttons[x][y].setIcon(new ImageIcon(Gui.class.getResource("/icons/closed.png")));
        buttons[x][y].setPreferredSize(buttonPreferredSize);
    }

    /**
     * Геттер для smileLabel
     *
     * @return кликабельный лейбл, на котором установлена иконка смайлика, которая показывает состояние игры
     */
    public JLabel getSmileLabel() {
        return smileLabel;
    }

    /**
     * Геттер для minesLabel
     *
     * @return лейбл, на котором показывается количество ненайденных мин
     */
    public JLabel getMinesLabel() {
        return minesLabel;
    }

    /**
     * Геттер для timePassedLabel
     *
     * @return лейбл, на котором показывается время пройденной с начала игры
     */
    public JLabel getTimePassedLabel() {
        return timePassedLabel;
    }

    /**
     * Геттер для boardPanel
     *
     * @return панель с игровым полем
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Геттер для buttons
     *
     * @return двумерный массив buttons
     */
    public JButton[][] getButtons() {
        return buttons;
    }
}