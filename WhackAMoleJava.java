import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WhackAMoleJava {
    private static final int GRID_SIZE = 3;
    private static final int GAME_DURATION = 30; 
    private JButton[][] holes;
    private Random random;
    private int score;
    private JLabel scoreLabel, timerLabel;
    private Timer gameTimer, moleTimer;
    private int timeLeft;

    public WhackAMoleJava() {
        JFrame frame = new JFrame("Whack A Mole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        holes = new JButton[GRID_SIZE][GRID_SIZE];
        random = new Random();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setBackground(new Color(89, 48, 70));
                button.setEnabled(false);
                button.addActionListener(new MoleClickListener());
                holes[i][j] = button;
                gridPanel.add(button);
            }
        }

        score = 0;
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time: " + GAME_DURATION + "s", SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(scoreLabel);
        topPanel.add(timerLabel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);

        timeLeft = GAME_DURATION;
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + "s");
            if (timeLeft <= 0) {
                gameTimer.stop();
                moleTimer.stop();
                JOptionPane.showMessageDialog(frame, "Game Over! Final Score: " + score);
            }
        });

        moleTimer = new Timer(1000, e -> showMole());

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.addActionListener(e -> {
            score = 0;
            scoreLabel.setText("Score: " + score);
            timeLeft = GAME_DURATION;
            timerLabel.setText("Time: " + timeLeft + "s");
            gameTimer.start();
            moleTimer.start();
        });
        frame.add(startButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void showMole() {
        for (JButton[] row : holes) {
            for (JButton button : row) {
                button.setText(" ");
                button.setEnabled(false);
            }
        }

        int x = random.nextInt(GRID_SIZE);
        int y = random.nextInt(GRID_SIZE);
        holes[x][y].setText("D");
        holes[x][y].setEnabled(true);
    }

    private class MoleClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals("D")) {
                score++;
                scoreLabel.setText("Score: " + score);
                clickedButton.setText(" ");
                clickedButton.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WhackAMoleJava::new);
    }
}
