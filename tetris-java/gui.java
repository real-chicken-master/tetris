import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
/**
 * Write a description of class gui here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class gui extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    Canvas screen;
    BufferedImage offScreenImage;
    int windowHeight = 800;
    int windowLength = 800;
    int imageHeight = 40;
    int imageWidth = 40;
    ImageIcon BlackSquare =  new ImageIcon(new ImageIcon("images/BlackSquare.PNG").getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
    ImageIcon BlueSquare =  new ImageIcon(new ImageIcon("images/BlueSquare.PNG").getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));   
    /**
     * Constructor for objects of class gui
     */
    public gui()
    {
        setTitle("tetris");

        this.getContentPane().setPreferredSize(new Dimension(windowLength,windowHeight));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.toFront();

        this.setVisible(true);

        menuBar=new JMenuBar();
        this.setJMenuBar(menuBar);

        menu = new JMenu(" Menu ");
        menuBar.add(menu);

        menuItem=new JMenuItem("restart");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem=new JMenuItem("update");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(windowLength,windowHeight));
        screen = new Canvas();
        panel.add(screen);
        this.pack();
    }

    public void actionPerformed(ActionEvent e){
        String cmd=e.getActionCommand();
        repaint();
    }

}    