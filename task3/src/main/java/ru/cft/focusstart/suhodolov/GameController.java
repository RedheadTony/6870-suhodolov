package ru.cft.focusstart.suhodolov;

import ru.cft.focusstart.suhodolov.model.DifficultyType;
import ru.cft.focusstart.suhodolov.model.Game;
import ru.cft.focusstart.suhodolov.ui.Gui;
import ru.cft.focusstart.suhodolov.ui.LeaderBoard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Класс контроллер
 */
public class GameController {

    private final Game game;

    private DifficultyType difficulty = DifficultyType.BEGINNER;

    /**
     * Конструктор, который начинает меню и добавляет листенеры для объектов,
     * которые будут реагировать на действия пользователя
     */
    public GameController() {
        Gui gui = new Gui();
        LeaderBoard leaderBoard = new LeaderBoard();

        this.game = new Game(gui, gui, gui, leaderBoard);

        setMenuListeners(gui, leaderBoard);
        setButtonsListener(gui);

        gui.getSmileLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.changeDifficulty(difficulty);
                setButtonsListener(gui);
            }
        });
    }

    /**
     * Метод, который достает из Gui элементы меню и создает для них листенеры
     *
     * @param gui         объект Gui, из которого мы достаем элементы меню
     * @param leaderBoard объект таблицы рекордов, который будет открываться через некоторые пункты меню
     */
    private void setMenuListeners(final Gui gui, final LeaderBoard leaderBoard) {
        JMenuBar jMenuBar = gui.getJMenuBar();
        JMenu fileMenu = jMenuBar.getMenu(0);

        JMenuItem newGameItem = fileMenu.getItem(0);
        newGameItem.addActionListener(e -> {
            game.changeDifficulty(difficulty);
            setButtonsListener(gui);
        });

        JMenu difficultyMenu = jMenuBar.getMenu(1);

        JMenuItem beginnerLevelItem = difficultyMenu.getItem(0);
        beginnerLevelItem.addActionListener(e -> {
            difficulty = DifficultyType.BEGINNER;
            game.changeDifficulty(difficulty);
            setButtonsListener(gui);
        });

        JMenuItem intermediateLevelItem = difficultyMenu.getItem(1);
        intermediateLevelItem.addActionListener(e -> {
            difficulty = DifficultyType.INTERMEDIATE;
            game.changeDifficulty(difficulty);
            setButtonsListener(gui);
        });

        JMenuItem expertLevelItem = difficultyMenu.getItem(2);
        expertLevelItem.addActionListener(e -> {
            difficulty = DifficultyType.EXPERT;
            game.changeDifficulty(difficulty);
            setButtonsListener(gui);
        });

        JMenu leaderBoardMenu = jMenuBar.getMenu(2);

        JMenuItem beginnerLeaderBoardItem = leaderBoardMenu.getItem(0);
        beginnerLeaderBoardItem.addActionListener(e -> leaderBoard.openLeaderBoard(DifficultyType.BEGINNER));

        JMenuItem intermediateLeaderBoardItem = leaderBoardMenu.getItem(1);
        intermediateLeaderBoardItem.addActionListener(e -> leaderBoard.openLeaderBoard(DifficultyType.INTERMEDIATE));

        JMenuItem expertLeaderBoardItem = leaderBoardMenu.getItem(2);
        expertLeaderBoardItem.addActionListener(e -> leaderBoard.openLeaderBoard(DifficultyType.EXPERT));
    }

    /**
     * Метод, который добавляет MouseListeners для кнопок, которые достает из Gui
     *
     * @param gui Gui, из которой достаем кнопки
     */
    private void setButtonsListener(final Gui gui) {
        JButton[][] buttons = gui.getButtons();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                final int x = i;
                final int y = j;
                buttons[x][y].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            game.changeCell(x, y);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            game.changeFlag(x, y);
                        }
                    }
                });
            }
        }
    }
}
