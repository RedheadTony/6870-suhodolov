package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Gui extends JFrame {

    private final Icon closedIcon = new ImageIcon(Gui.class.getResource("/icons/closed.png"));
    private final Icon minedIcon = new ImageIcon(Gui.class.getResource("/icons/mined.png"));
    private final Icon zeroIcon = new ImageIcon(Gui.class.getResource("/icons/zero.png"));
    private final Icon mineIcon = new ImageIcon(Gui.class.getResource("/icons/mine.png"));
    private final Icon noMineIcon = new ImageIcon(Gui.class.getResource("/icons/no_mine.png"));
    private final Icon flagIcon = new ImageIcon(Gui.class.getResource("/icons/flag.png"));

    private final Dimension buttonPreferredSize = new Dimension(50, 50);

    private JPanel smilePanel;
    private JButton smileButton;
    private JPanel boardPanel;

    private JButton[][] buttons;
    private Board board;

    private boolean playing = false;

    public Gui() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("CFT Minesweeper");
        setIconImage(new ImageIcon(Gui.class.getResource("/icons/icon.png")).getImage());
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> newGame());

        JMenu jMenu = new JMenu("File");
        jMenu.add(newGameItem);
        jMenu.add(exitItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        setJMenuBar(jMenuBar);

        smilePanel = new JPanel();
        smilePanel.setLayout(new GroupLayout(smilePanel));

        smileButton = new JButton();
        smileButton.setPreferredSize(buttonPreferredSize);
        smileButton.addActionListener(e -> newGame());
        smilePanel.add(smileButton);

        add(smileButton, BorderLayout.NORTH);

        boardPanel = new JPanel();

        boardPanel.setLayout(new GridLayout(9, 9));
        newGame();
        add(boardPanel, BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null);
    }

    private void newGame() {
        smileButton.setIcon(new ImageIcon(Gui.class.getResource("/icons/play_smile.png")));

        boardPanel.removeAll();
        boardPanel.invalidate();
        board = new Board(9, 9, 10);
        buttons = new JButton[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                setButton(x, y);
                boardPanel.add(buttons[x][y]);
            }
        }
        boardPanel.validate();
        playing = true;
    }

    private void setButton(int x, int y) {
        buttons[x][y] = new JButton();
        buttons[x][y].setIcon(closedIcon);

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

        buttons[x][y].setPreferredSize(buttonPreferredSize);
    }

    private void openCell(int x, int y) {
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

    private void openCellsAroundZero(Cell openedCell) {
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
        smileButton.setIcon(new ImageIcon(Gui.class.getResource("/icons/win_smile.png")));
    }

    private void losingGame() {
        playing = false;
        smileButton.setIcon(new ImageIcon(Gui.class.getResource("/icons/lose_smile.png")));
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
