package ru.cft.focusstart.suhodolov;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Класс, который представляет собой фрейм, который показывает таблицу рекордов нужной сложности
 */
public class LeaderBoard extends JFrame {

    /**
     * Конструктор, который создает фрейм
     *
     * @param difficultyType сложность, таблицу рекордов которой нужно показать
     * @param results        список результатов побед на переданной сложности
     */
    public LeaderBoard(final DifficultyType difficultyType, final List<Integer> results) {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle(difficultyType.name() + " LeaderBoard");
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        JPanel jPanel = new JPanel();

        if (results.size() == 0) {
            JLabel jLabel = new JLabel("No winners yet!");
            jPanel.add(jLabel);
        } else {
            for (int i = 1; i < results.size() + 1; i++) {
                JLabel jLabel = new JLabel(i + " place: " + results.get(i - 1) + " sec.");
                jPanel.add(jLabel);
            }
            jPanel.setLayout(new GridLayout(results.size(), 1));
        }
        add(jPanel, BorderLayout.NORTH);
        pack();
    }
}
