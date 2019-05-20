package ru.cft.focusstart.suhodolov.ui;

import ru.cft.focusstart.suhodolov.model.DifficultyType;
import ru.cft.focusstart.suhodolov.observers.LeaderBoardObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, который представляет собой фрейм, который показывает таблицу рекордов нужной сложности
 */
public class LeaderBoard extends JFrame implements LeaderBoardObserver {

    private JPanel leaderBoardPanel = new JPanel();

    private final Map<DifficultyType, List<Integer>> statistics = new HashMap<>();

    /**
     * Конструктор, в котором создаются списки нужных сложностей в мапе статистики
     */
    public LeaderBoard() {
        statistics.put(DifficultyType.BEGINNER, new ArrayList<>());
        statistics.put(DifficultyType.INTERMEDIATE, new ArrayList<>());
        statistics.put(DifficultyType.EXPERT, new ArrayList<>());
    }

    /**
     * Метод, который показывает этот фрейм пользователю
     * выводит результаты, если они есть или сообщение о том, что результатов нет
     *
     * @param difficultyType тип сложности, результаты которого хочет увидеть пользователь
     */
    public void openLeaderBoard(DifficultyType difficultyType) {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle(difficultyType.name() + " LeaderBoard");
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        List<Integer> results = statistics.get(difficultyType);
        leaderBoardPanel.removeAll();
        if (results.size() == 0) {
            JLabel jLabel = new JLabel("No winners yet!");
            leaderBoardPanel.add(jLabel);
        } else {
            for (int i = 1; i < results.size() + 1; i++) {
                JLabel jLabel = new JLabel(i + " place: " + results.get(i - 1) + " sec.");
                leaderBoardPanel.add(jLabel);
            }
            leaderBoardPanel.setLayout(new GridLayout(results.size(), 1));
        }
        add(leaderBoardPanel, BorderLayout.NORTH);
        pack();
    }

    /**
     * Имплементированный метод из LeaderBoardObserver
     * добавляет в статистику нужной сложности новый результат и сортирует список этой сложности
     *
     * @param difficulty сложность, на которой пользователь получил этот результат
     * @param timePassed результат, то время за которое пользователь прошел игру
     */
    @Override
    public void onLeadersListChange(DifficultyType difficulty, int timePassed) {
        statistics.get(difficulty).add(timePassed);
        statistics.get(difficulty).sort(Integer::compareTo);
    }
}
