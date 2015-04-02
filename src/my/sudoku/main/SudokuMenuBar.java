package my.sudoku.main;

import javax.swing.*;
import java.awt.*;

public class SudokuMenuBar
{
    public void addMenu(SudokuMainFrame frame)
    {
        Font font = new Font("Verdana", Font.PLAIN, 11);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //========== list of items in menubar
        JMenu changeLevel = new JMenu("Level");
        JMenu menu = new JMenu("Options");

        JMenuItem lvl1 = new JMenuItem("Easy");
        JMenuItem lvl2 = new JMenuItem("Medium");
        JMenuItem lvl3 = new JMenuItem("Difficult");
        JMenuItem lvl4 = new JMenuItem("Extremely");

        JMenuItem symmetr = new JMenuItem("Symmetrical");
        JMenuItem asymmetr = new JMenuItem("Asymmetrical");
        JMenuItem print = new JMenuItem("Print");
        JMenu generate = new JMenu("Generate");
        JMenuItem exit = new JMenuItem("Exit");

        //============= adding components
        changeLevel.add(lvl1);
        changeLevel.add(lvl2);
        changeLevel.add(lvl3);
        changeLevel.add(lvl4);
        menu.add(generate);
        menu.add(changeLevel);
        menu.add(print);
        menu.addSeparator();
        menu.add(exit);
        menuBar.add(menu);
        generate.add(symmetr);
        generate.add(asymmetr);

        //============= set fonts
        menu.setFont(font);

        generate.setFont(new Font("Verdana", Font.BOLD, 11));
        asymmetr.setFont(font);
        symmetr.setFont(font);
        print.setFont(font);
        exit.setFont(font);
        changeLevel.setFont(font);
        lvl1.setFont(font);
        lvl2.setFont(font);
        lvl3.setFont(font);
        lvl4.setFont(font);

        //============ adding action listeners
        lvl1.addActionListener(frame);
        lvl2.addActionListener(frame);
        lvl3.addActionListener(frame);
        lvl4.addActionListener(frame);
        symmetr.addActionListener(frame);
        asymmetr.addActionListener(frame);
        exit.addActionListener(frame);
        print.addActionListener(frame);
    }
}
