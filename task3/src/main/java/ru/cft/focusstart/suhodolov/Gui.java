package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private final Dimension buttonPreferredSize = new Dimension(50, 50);

    private JButton smileButton;
    private JPanel boardPanel;

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

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(fileMenu);
        jMenuBar.add(difficultyMenu);
        setJMenuBar(jMenuBar);

        JPanel smilePanel = new JPanel();
        smilePanel.setLayout(new GroupLayout(smilePanel));

        smileButton = new JButton();
        smileButton.setPreferredSize(buttonPreferredSize);

        smilePanel.add(smileButton);

        add(smileButton, BorderLayout.NORTH);

        boardPanel = new JPanel();

        boardPanel.setLayout(new GridLayout(cols, rows));
        add(boardPanel, BorderLayout.CENTER);
    }

    public void changeDifficulty(final int rows, final int cols) {
        buttons = new JButton[cols][rows];
        boardPanel.setLayout(new GridLayout(cols, rows));
    }

    public void setBoardButton(final int x, final int y) {
        buttons[x][y] = new JButton();
        buttons[x][y].setIcon(new ImageIcon(Gui.class.getResource("/icons/closed.png")));
        buttons[x][y].setPreferredSize(buttonPreferredSize);
    }

    public JButton getSmileButton() {
        return smileButton;
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JButton[][] getButtons() {
        return buttons;
    }
}