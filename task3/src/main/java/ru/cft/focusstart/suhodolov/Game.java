package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Game {

    private final Icon playSmile = new ImageIcon(Game.class.getResource("/icons/play_smile.png"));
    private final Icon winSmile = new ImageIcon(Game.class.getResource("/icons/win_smile.png"));
    private final Icon loseSmile = new ImageIcon(Game.class.getResource("/icons/lose_smile.png"));
    private final Icon closedIcon = new ImageIcon(Game.class.getResource("/icons/closed.png"));
    private final Icon minedIcon = new ImageIcon(Game.class.getResource("/icons/mined.png"));
    private final Icon zeroIcon = new ImageIcon(Game.class.getResource("/icons/zero.png"));
    private final Icon mineIcon = new ImageIcon(Game.class.getResource("/icons/mine.png"));
    private final Icon noMineIcon = new ImageIcon(Game.class.getResource("/icons/no_mine.png"));
    private final Icon flagIcon = new ImageIcon(Game.class.getResource("/icons/flag.png"));

    private Gui gui;
    private Board board;
    private JButton[][] buttons;

    private DifficultyType difficulty = DifficultyType.BEGINNER;

    private Timer timer;
    private int timePassed;

    private Map<DifficultyType, List<Integer>> statistics = new HashMap<>();

    private boolean playing = false;
    private int rows = 9;
    private int cols = 9;
    private int numberOfMines = 10;
    private int notFoundedMines = numberOfMines;

    public Game() {
        statistics.put(DifficultyType.BEGINNER, new ArrayList<>());
        statistics.put(DifficultyType.INTERMEDIATE, new ArrayList<>());
        statistics.put(DifficultyType.EXPERT, new ArrayList<>());

        this.gui = new Gui();

        setMenuListeners();

        gui.getSmileLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newGame(rows, cols, numberOfMines);
            }
        });

        timer = new Timer(1000, e -> {
            timePassed++;
            gui.getTimePassedLabel().setText(Gui.TIME_PASSED_TEXT + timePassed);
        });

        newGame(rows, cols, numberOfMines);
    }

    private void newGame(final int rows, final int cols, final int numberOfMines) {
        gui.getSmileLabel().setIcon(playSmile);
        gui.getBoardPanel().removeAll();
        gui.changeDifficulty(rows, cols, numberOfMines);

        notFoundedMines = numberOfMines;
        timePassed = 0;

        board = new Board(rows, cols, numberOfMines);
        buttons = gui.getButtons();
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                gui.setBoardButton(x, y);
                setButtonsListener(x, y);
                gui.getBoardPanel().add(buttons[x][y]);
            }
        }
        playing = true;

        gui.pack();

        gui.setLocationRelativeTo(null);
    }

    private void setMenuListeners() {
        JMenuBar jMenuBar = gui.getJMenuBar();
        JMenu fileMenu = jMenuBar.getMenu(0);

        JMenuItem newGameItem = fileMenu.getItem(0);
        newGameItem.addActionListener(e -> newGame(rows, cols, numberOfMines));

        JMenuItem exitItem = fileMenu.getItem(1);
        exitItem.addActionListener(e -> System.exit(0));

        JMenu difficultyMenu = jMenuBar.getMenu(1);

        JMenuItem beginnerLevelItem = difficultyMenu.getItem(0);
        beginnerLevelItem.addActionListener(e -> {
            difficulty = DifficultyType.BEGINNER;
            newGame(this.rows = 9, this.cols = 9, this.numberOfMines = 10);
        });

        JMenuItem intermediateLevelItem = difficultyMenu.getItem(1);
        intermediateLevelItem.addActionListener(e -> {
            difficulty = DifficultyType.INTERMEDIATE;
            newGame(this.rows = 16, this.cols = 16, this.numberOfMines = 40);
        });

        JMenuItem expertLevelItem = difficultyMenu.getItem(2);
        expertLevelItem.addActionListener(e -> {
            difficulty = DifficultyType.EXPERT;
            newGame(this.rows = 16, this.cols = 30, this.numberOfMines = 99);
        });

        JMenu leaderBoardMenu = jMenuBar.getMenu(2);

        JMenuItem beginnerLeaderBoardItem = leaderBoardMenu.getItem(0);
        beginnerLeaderBoardItem.addActionListener(e ->
                new LeaderBoard(DifficultyType.BEGINNER, statistics.get(DifficultyType.BEGINNER)));

        JMenuItem intermediateLeaderBoardItem = leaderBoardMenu.getItem(1);
        intermediateLeaderBoardItem.addActionListener(e ->
                new LeaderBoard(DifficultyType.INTERMEDIATE, statistics.get(DifficultyType.INTERMEDIATE)));

        JMenuItem expertLeaderBoardItem = leaderBoardMenu.getItem(2);
        expertLeaderBoardItem.addActionListener(e ->
                new LeaderBoard(DifficultyType.EXPERT, statistics.get(DifficultyType.EXPERT)));
    }

    private void setButtonsListener(final int x, final int y) {
        buttons[x][y].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!playing) {
                    return;
                }
                if (!timer.isRunning()) {
                    timer.start();
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (board.getCell(x, y).isChecked()) {
                        openCellAroundFlags(x, y);
                    } else {
                        openCell(x, y);
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    Cell cell = board.getCell(x, y);

                    if (cell.isChecked()) {
                        return;
                    }
                    if (!cell.isFlagged()) {
                        buttons[x][y].setIcon(flagIcon);
                        cell.setFlagged(true);
                        notFoundedMines--;
                        gui.getMinesLabel().setText(Gui.BOMB_COUNTING_TEXT + notFoundedMines);
                    } else {
                        buttons[x][y].setIcon(closedIcon);
                        cell.setFlagged(false);
                        notFoundedMines++;
                        gui.getMinesLabel().setText(Gui.BOMB_COUNTING_TEXT + notFoundedMines);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void openCellAroundFlags(final int x, final int y) {
        Cell cell = board.getCell(x, y);
        int surroundingFlags = 0;
        for (int i = Board.makeValidCoordinate(cell.getX() - 1, cols);
             i <= Board.makeValidCoordinate(cell.getX() + 1, cols); i++) {
            for (int j = Board.makeValidCoordinate(cell.getY() - 1, rows);
                 j <= Board.makeValidCoordinate(cell.getY() + 1, rows); j++) {
                if (board.getCell(i, j).isFlagged()) {
                    surroundingFlags++;
                }
            }
        }
        if (cell.getSurroundingMines() == surroundingFlags) {
            openCellsAroundZero(cell);
        }
    }

    private void openCell(final int x, final int y) {
        Cell cell = board.getCell(x, y);

        if (cell.isChecked() || cell.isFlagged()) {
            return;
        } else {
            cell.setChecked(true);
        }

        if (cell.isMined()) {
            buttons[x][y].setIcon(minedIcon);
            losingGame();
        } else if (cell.getSurroundingMines() > 0) {
            buttons[x][y].setIcon(new ImageIcon(Game.class.getResource(
                    "/icons/num" + cell.getSurroundingMines() + ".png")));
        } else {
            buttons[x][y].setIcon(zeroIcon);
            openCellsAroundZero(cell);
        }

        if (checkWinGame() == numberOfMines) {
            winningGame();
        }
    }

    private void openCellsAroundZero(final Cell openedCell) {
        for (int x = Board.makeValidCoordinate(openedCell.getX() - 1, cols);
             x <= Board.makeValidCoordinate(openedCell.getX() + 1, cols); x++) {
            for (int y = Board.makeValidCoordinate(openedCell.getY() - 1, rows);
                 y <= Board.makeValidCoordinate(openedCell.getY() + 1, rows); y++) {
                openCell(x, y);
            }
        }
    }

    private int checkWinGame() {
        int uncheckedCells = 0;
        for (Cell[] cells : board.getCells())
            for (Cell cell : cells) {
                if (!cell.isChecked()) {
                    uncheckedCells++;
                }
            }
        return uncheckedCells;
    }

    private void winningGame() {
        timer.stop();
        playing = false;
        gui.getSmileLabel().setIcon(winSmile);

        statistics.get(difficulty).add(timePassed);
        statistics.get(difficulty).sort(Integer::compareTo);
    }

    private void losingGame() {
        timer.stop();
        playing = false;
        gui.getSmileLabel().setIcon(loseSmile);
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
}