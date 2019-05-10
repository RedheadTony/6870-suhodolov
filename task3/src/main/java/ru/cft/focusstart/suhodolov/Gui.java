package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    private final Dimension buttonPreferredSize = new Dimension(50, 50);

    private JButton smileButton;
    private JPanel boardPanel;

    private JButton[][] buttons;

    public Gui() {
        buttons = new JButton[9][9];
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("CFT Minesweeper");
        setIconImage(new ImageIcon(Gui.class.getResource("/icons/icon.png")).getImage());
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        JMenu jMenu = new JMenu("File");
        jMenu.add(newGameItem);
        jMenu.add(exitItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        setJMenuBar(jMenuBar);

        JPanel smilePanel = new JPanel();
        smilePanel.setLayout(new GroupLayout(smilePanel));

        smileButton = new JButton();
        smileButton.setPreferredSize(buttonPreferredSize);

        smilePanel.add(smileButton);

        add(smileButton, BorderLayout.NORTH);

        boardPanel = new JPanel();

        boardPanel.setLayout(new GridLayout(9, 9));
        add(boardPanel, BorderLayout.CENTER);
    }

    public void setButton(final int x, final int y) {
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