package ru.cft.focusstart.suhodolov.ui;

import ru.cft.focusstart.suhodolov.model.Board;
import ru.cft.focusstart.suhodolov.model.Cell;
import ru.cft.focusstart.suhodolov.model.Game;
import ru.cft.focusstart.suhodolov.observers.CellObserver;
import ru.cft.focusstart.suhodolov.observers.GameStateObserver;
import ru.cft.focusstart.suhodolov.observers.TimerObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Класс, который представляет собой основной фрейм приложения, наследуется от класса JFrame
 */
public class Gui extends JFrame implements CellObserver, TimerObserver, GameStateObserver {

    private final Icon playSmile = new ImageIcon(Game.class.getResource("/icons/play_smile.png"));
    private final Icon winSmile = new ImageIcon(Game.class.getResource("/icons/win_smile.png"));
    private final Icon loseSmile = new ImageIcon(Game.class.getResource("/icons/lose_smile.png"));
    private final Icon closedIcon = new ImageIcon(Game.class.getResource("/icons/closed.png"));
    private final Icon minedIcon = new ImageIcon(Game.class.getResource("/icons/mined.png"));
    private final Icon zeroIcon = new ImageIcon(Game.class.getResource("/icons/zero.png"));
    private final Icon mineIcon = new ImageIcon(Game.class.getResource("/icons/mine.png"));
    private final Icon noMineIcon = new ImageIcon(Game.class.getResource("/icons/no_mine.png"));
    private final Icon flagIcon = new ImageIcon(Game.class.getResource("/icons/flag.png"));


    private static final String BOMB_COUNTING_TEXT = "Bomb counting: ";
    private static final String TIME_PASSED_TEXT = "Time passed: ";

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
        exitItem.addActionListener(e -> System.exit(0));

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
     * Метод, который по входным координатам создает кнопку, которая будет связана с ячейкой игрового поля
     *
     * @param x координата
     * @param y координата
     */
    private void setBoardButton(final int x, final int y) {
        buttons[x][y] = new JButton();
        buttons[x][y].setIcon(closedIcon);
        buttons[x][y].setPreferredSize(buttonPreferredSize);
    }

    /**
     * Геттер для buttons
     *
     * @return двумерный массив buttons
     */
    public JButton[][] getButtons() {
        return buttons;
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
     * Имплементированный метод из CellObserver
     * меняет иконки для кнопок привязанных к измененным ячейкам
     *
     * @param changedCells список измененных ячеек
     */
    @Override
    public void onCellsChanged(final List<Cell> changedCells) {
        for (Cell cell : changedCells) {
            if (cell.isChecked() && cell.isMined()) {
                buttons[cell.getX()][cell.getY()].setIcon(minedIcon);
            } else if (cell.getSurroundingMines() > 0) {
                buttons[cell.getX()][cell.getY()].setIcon(new ImageIcon(Game.class.getResource(
                        "/icons/num" + cell.getSurroundingMines() + ".png")));
            } else {
                buttons[cell.getX()][cell.getY()].setIcon(zeroIcon);
            }
        }
    }

    /**
     * Имплементированный метод из CellObserver
     * меняет иконку для кнопки привязанной к ячейке, на которой поставили или убрали флаг
     * меняет количество ненайденных мин на minesLabel
     *
     * @param cell            ячейка, с которой манипулирует пользователь
     * @param notFoundedMines количество ненайденных мин
     */
    @Override
    public void onFlagChanged(final Cell cell, final int notFoundedMines) {
        if (cell.isFlagged()) {
            buttons[cell.getX()][cell.getY()].setIcon(flagIcon);
            minesLabel.setText(Gui.BOMB_COUNTING_TEXT + notFoundedMines);

        } else {
            buttons[cell.getX()][cell.getY()].setIcon(closedIcon);
            minesLabel.setText(Gui.BOMB_COUNTING_TEXT + notFoundedMines);
        }
    }

    /**
     * Имплементированный метод из GameStateObserver
     * меняет панель фрейма для новой игры по переданным значениям
     *
     * @param rows          количество строк в новой игре
     * @param cols          количество столбцов в новой игре
     * @param numberOfMines количество мин в новой игре
     */
    @Override
    public void onNewGame(final int rows, final int cols, final int numberOfMines) {
        this.rows = rows;
        this.cols = cols;

        smileLabel.setIcon(playSmile);

        boardPanel.removeAll();

        boardPanel.setLayout(new GridLayout(cols, rows));
        minesLabel.setText(BOMB_COUNTING_TEXT + numberOfMines);
        timePassedLabel.setText(TIME_PASSED_TEXT + 0);

        buttons = new JButton[cols][rows];

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                setBoardButton(x, y);
                boardPanel.add(buttons[x][y]);
            }
        }

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Имплементированный метод из GameStateObserver
     * меняет иконку smileLabel на иконку победителя
     */
    @Override
    public void onWin() {
        smileLabel.setIcon(winSmile);
    }

    /**
     * Имплементированный метод из GameStateObserver
     * меняет иконку smileLabel на иконку проигравшего
     * показывает кнопки, под которыми лежали мины
     *
     * @param board поле игры
     */
    @Override
    public void onLose(final Board board) {
        smileLabel.setIcon(loseSmile);

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = board.getCell(x, y);
                if (cell.isFlagged() && !cell.isMined()) {
                    buttons[x][y].setIcon(noMineIcon);
                }
                if (cell.isMined() && !cell.isChecked()) {
                    buttons[x][y].setIcon(mineIcon);
                }
            }
        }
    }

    /**
     * Имплементированный метод из TimerObserver
     * меняет текст на timePassedLabel
     *
     * @param timePassed число, которое показывает сколько времени с начала игры прошло
     */
    @Override
    public void onTimerChange(final int timePassed) {
        timePassedLabel.setText(TIME_PASSED_TEXT + timePassed);
    }
}