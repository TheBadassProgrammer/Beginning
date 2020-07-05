package com.beginning.GUI;

import com.beginning.britannica.BritannicaMainThread;
import com.beginning.wikipedia.WikiMainThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.beginning.britannica.BritannicaMainThread.queryAns;
import static com.beginning.wikipedia.WikiMainThread.wikiQueryAns;

class ImageClass{
    public static JFrame imageFrame;
    public static JPanel imagePanel;

    public static void thirdFrame(){
        imageFrame = new JFrame("Beginning");
        imageFrame.setSize(500, 300);

        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imagePanel = new JPanel();
        imageFrame.add(imagePanel);

        placeComponents(imagePanel);
        imageFrame.setVisible(true);
    }
    private static void placeComponents(JPanel panel){
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\MINAKSHI\\IdeaProjects\\Beginning\\src\\com\\beginning\\giphy.gif");
        JLabel image = new JLabel(imageIcon);
        imageFrame.add(image);
    }
}

public class MainGUI implements ActionListener {
    public static JFrame mainFrame;
    public static JPanel mainPanel;
    public static JLabel dateLabel;
    public static JLabel timeLabel;
    public static JLabel dayLabel;
    public static JLabel queryLabel;
    public static JTextField queryText;
    public static JButton BritannicaSearchButton;
    public static JButton WikiSearchButton;
    public static JButton clearButton;
    public static JButton wikiClearButton;

    public static void secondFrame(){

        mainFrame = new JFrame("Beginning");
        mainFrame.setSize(850, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - mainFrame.getWidth();
        int y = 0;
        mainFrame.setLocation(x, y);
        mainFrame.setVisible(true);

        mainPanel = new JPanel();
        mainFrame.add(mainPanel);
        mainPanel.setBackground(Color.BLACK);

        placeComponents(mainPanel);
        mainFrame.setVisible(true);

    }

    private static void placeComponents(JPanel panel) {

        mainPanel.setLayout(null);


        dateLabel = new JLabel("Date: " + String.valueOf(LocalDate.now()));
        dayLabel = new JLabel("Day: " + String.valueOf(LocalDate.now().getDayOfWeek()));
        timeLabel = new JLabel("Time: " + String.valueOf(LocalTime.now()));

        dateLabel.setBounds(10, 20, 200, 25);
        dayLabel.setBounds(10, 50, 200, 25);
        timeLabel.setBounds(10, 80, 200, 25);

        mainPanel.add(dateLabel);
        mainPanel.add(dayLabel);
        mainPanel.add(timeLabel);

        queryLabel = new JLabel("Enter your query:");
        queryLabel.setBounds(10, 135, 220, 25);
        queryLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        mainPanel.add(queryLabel);

        queryText = new JTextField(100);
        queryText.setBounds(150, 120, 250, 55);
        queryText.setBackground(Color.darkGray);
        queryText.setForeground(Color.BLACK);
        queryText.setFont(new Font("Serif", Font.BOLD, 18));
        mainPanel.add(queryText);


        BritannicaSearchButton = new JButton("Britannica Search");
        BritannicaSearchButton.setBounds(420, 120, 200, 25);
        BritannicaSearchButton.setBackground(Color.gray);
        BritannicaSearchButton.addActionListener(new MainGUI());
        mainPanel.add(BritannicaSearchButton);

        WikiSearchButton = new JButton("Wikipedia Search");
        WikiSearchButton.setBounds(620, 120, 200, 25);
        WikiSearchButton.setBackground(Color.gray);
        WikiSearchButton.addActionListener(new MainGUI());
        mainPanel.add(WikiSearchButton);

        clearButton = new JButton("Clear Britannica");
        clearButton.setBounds(420, 150, 200, 25);
        clearButton.setBackground(Color.gray);
        clearButton.addActionListener(new MainGUI());
        mainPanel.add(clearButton);

        wikiClearButton = new JButton("Clear Wikipedia");
        wikiClearButton.setBounds(620, 150, 200, 25);
        wikiClearButton.setBackground(Color.gray);
        wikiClearButton.addActionListener(new MainGUI());
        mainPanel.add(wikiClearButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BritannicaSearchButton) {
            BritannicaMainThread t2 = new BritannicaMainThread();
            t2.start();

        } else if (e.getSource() == WikiSearchButton) {
            WikiMainThread t7 = new WikiMainThread();
            t7.start();

        } else if (e.getSource() == clearButton) {
            mainPanel.remove(queryAns);
            mainFrame.pack();
            mainFrame.setSize(850, 300);
            mainFrame.setVisible(true);

        } else if (e.getSource() == wikiClearButton) {
            mainPanel.remove(wikiQueryAns);
            mainFrame.pack();
            mainFrame.setSize(850, 300);
            mainFrame.setVisible(true);
        }
    }


    static class LoginGUI extends Component implements ActionListener, FocusListener {
        private static JFrame loginFrame;
        private static JPanel loginPanel;
        private static JLabel userLabel;
        private static JTextField userText;
        private static JLabel passwordLabel;
        private static JPasswordField passwordText;
        private static JButton loginButton;
        private static JButton exitButton;
        private static String userID;
        private static String password;

        public void loginFrame() {

            loginFrame = new JFrame("Login");
            loginFrame.setSize(295, 155);
            loginFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            /* Creating panel. This is same as a div tag in HTML
             * We can create several panels and add them to specific
             * positions in a JFrame. Inside panels we can add text
             * fields, buttons and other components.
             */
            loginPanel = new JPanel();
            loginFrame.add(loginPanel);
            /* calling user defined method for adding components
             * to the panel.
             */

            placeComponents(loginPanel);
            loginFrame.setVisible(true);
        }

        private static void placeComponents(JPanel panel) {

            panel.setLayout(null);

            userLabel = new JLabel("User");
            userLabel.setBounds(10, 20, 80, 25);
            panel.add(userLabel);

            userText = new JTextField(20);
            userText.setBounds(100, 20, 165, 25);
            userText.addFocusListener(new LoginGUI());
            panel.add(userText);

            passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(10, 50, 80, 25);
            panel.add(passwordLabel);

            passwordText = new JPasswordField(20);
            passwordText.setBounds(100, 50, 165, 25);
            panel.add(passwordText);

            loginButton = new JButton("Login");
            loginButton.setBounds(100, 80, 80, 25);
            loginButton.addActionListener(new LoginGUI());
            panel.add(loginButton);

            exitButton = new JButton("Exit");
            exitButton.setBounds(185, 80, 80, 25);
            exitButton.addActionListener(new LoginGUI());
            panel.add(exitButton);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton){
                userID = userText.getText();
                password = passwordText.getText();

                if (userID.toLowerCase().equals("nitin") && password.equals("nitin001")) {
                    loginFrame.dispose();
                    MainGUI.secondFrame();
                    ImageClass.thirdFrame();
                    CurrentDateTimeThread t3 = new CurrentDateTimeThread();
                    t3.start();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Incorrect login or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            else if (e.getSource() == exitButton){
                loginFrame.dispose();
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            LoginThread t5 = new LoginThread();
            t5.start();
        }

        @Override
        public void focusLost(FocusEvent e) {

        }



        }
    public static void main(String[] args) {
        new LoginGUI().loginFrame();
    }
}
