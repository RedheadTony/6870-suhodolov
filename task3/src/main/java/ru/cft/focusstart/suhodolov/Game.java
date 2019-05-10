package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game {

    private final Icon closedIcon = new ImageIcon(Gui.class.getResource("/icons/closed.png"));
    private final Icon minedIcon = new ImageIcon(Gui.class.getResource("/icons/mined.png"));
    private final Icon zeroIcon = new ImageIcon(Gui.class.getResource("/icons/zero.png"));
    private final Icon mineIcon = new ImageIcon(Gui.class.getResource("/icons/mine.png"));
    private final Icon noMineIcon = new ImageIcon(Gui.class.getResource("/icons/no_mine.png"));
    private final Icon flagIcon = new ImageIcon(Gui.class.getResource("/icons/flag.png"));

    private Gui gui;
    private Board board;
    private JButton[][] buttons;

    private boolean playing = false;

    public Game() {
        this.gui = new Gui();

        setMenuListeners();
        gui.getSmileButton().addActionListener(e -> newGame());

        newGame();
    }

    private void newGame() {
        gui.getSmileButton().setIcon(new ImageIcon(Gui.class.getResource("/icons/play_smile.png")));
        gui.getBoardPanel().removeAll();
        gui.getBoardPanel().invalidate();
        board = new Board(9, 9, 10);
        buttons = gui.getButtons();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                gui.setButton(x, y);
                setButtonsListener(x, y);
                gui.getBoardPanel().add(buttons[x][y]);
            }
        }
        gui.getBoardPanel().validate();
        playing = true;

        gui.pack();

        gui.setLocationRelativeTo(null);
    }

    private void setMenuListeners() {
        JMenuBar jMenuBar = gui.getJMenuBar();
        JMenu jMenu = jMenuBar.getMenu(0);

        JMenuItem newGameItem = jMenu.getItem(0);
        newGameItem.addActionListener(e -> newGame());

        JMenuItem exitItem = jMenu.getItem(1);
        exitItem.addActionListener(e -> System.exit(0));
    }

    private void setButtonsListener(final int x, final int y) {
        buttons[x][y].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!playing) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    openCell(x, y);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    Cell cell = board.getCell(x, y);

                    if (cell.isChecked()) {
                        return;
                    }
                    if (!cell.isFlagged()) {
                        buttons[x][y].setIcon(flagIcon);
                        cell.setFlagged(true);
                    } else {
                        buttons[x][y].setIcon(closedIcon);
                        cell.setFlagged(false);
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
            buttons[x][y].setIcon(new ImageIcon(Gui.class.getResource(
                    "/icons/num" + cell.getSurroundingMines() + ".png")));
        } else {
            buttons[x][y].setIcon(zeroIcon);
            openCellsAroundZero(cell);
        }
        if (checkWinGame() == 10) {
            winningGame();
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

    private void openCellsAroundZero(final Cell openedCell) {
        for (int x = board.makeValidCoordinate(openedCell.getX() - 1, 9);
             x <= board.makeValidCoordinate(openedCell.getX() + 1, 9); x++) {
            for (int y = board.makeValidCoordinate(openedCell.getY() - 1, 9);
                 y <= board.makeValidCoordinate(openedCell.getY() + 1, 9); y++) {
                openCell(x, y);
            }
        }
    }

    private void winningGame() {
        playing = false;
        gui.getSmileButton().setIcon(new ImageIcon(Gui.class.getResource("/icons/win_smile.png")));
    }

    private void losingGame() {
        playing = false;
        gui.getSmileButton().setIcon(new ImageIcon(Gui.class.getResource("/icons/lose_smile.png")));
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
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
