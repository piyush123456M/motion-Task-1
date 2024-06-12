import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizApplication {
    private JFrame frame;
    private JPanel panel;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton submitButton;
    private int score;
    private int currentQuestion;
    private List<Question> questions;

    public QuizApplication() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", new String[] {"Paris", "London", "Berlin", "Rome"}, 0));
        questions.add(new Question("What is the largest planet in our solar system?", new String[] {"Earth", "Saturn", "Jupiter", "Uranus"}, 2));
        questions.add(new Question("What is the smallest country in the world?", new String[] {"Vatican City", "Monaco", "Nauru", "Tuvalu"}, 0));

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));

        options = new JRadioButton[4];
        group = new ButtonGroup();
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionPanel.add(options[i]);
        }
        panel.add(optionPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userAnswer = getSelectedOption();
                if (userAnswer == questions.get(currentQuestion).getCorrectAnswer()) {
                    score++;
                }
                currentQuestion++;
                if (currentQuestion < questions.size()) {
                    displayQuestion();
                } else {
                    displayScore();
                }
            }
        });

        panel.add(submitButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);

        currentQuestion = 0;
        score = 0;
        displayQuestion();

        frame.setVisible(true);
    }

    private int getSelectedOption() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private void displayQuestion() {
        Question q = questions.get(currentQuestion);
        questionLabel.setText(q.getQuestion());
        for (int i = 0; i < 4; i++) {
            options[i].setText(q.getOptions()[i]);
            options[i].setSelected(false);
        }
        panel.add(questionLabel, BorderLayout.NORTH);
        panel.revalidate();
        panel.repaint();
    }

    private void displayScore() {
        questionLabel.setText("Your final score is " + score + "/" + questions.size());
        panel.remove(submitButton);
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}