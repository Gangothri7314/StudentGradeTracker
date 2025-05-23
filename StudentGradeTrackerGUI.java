import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Student {
    String name;
    ArrayList<Integer> grades = new ArrayList<>();

    Student(String name) {
        this.name = name;
    }

    double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) sum += grade;
        return (double) sum / grades.size();
    }

    int getHighest() {
        if (grades.isEmpty()) return -1;
        int max = grades.get(0);
        for (int grade : grades) if (grade > max) max = grade;
        return max;
    }

    int getLowest() {
        if (grades.isEmpty()) return -1;
        int min = grades.get(0);
        for (int grade : grades) if (grade < min) min = grade;
        return min;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nGrades: %s\nAvg: %.2f, Max: %d, Min: %d\n",
                name, grades, getAverage(), getHighest(), getLowest());
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextArea displayArea;
    private JTextField nameField, gradesField;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Top panel: input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grades (comma separated):"));
        gradesField = new JTextField();
        inputPanel.add(gradesField);

        JButton addButton = new JButton("Add Student");
        inputPanel.add(addButton);

        JButton showButton = new JButton("Show Summary");
        inputPanel.add(showButton);

        // Center panel: display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        addButton.addActionListener(e -> addStudent());
        showButton.addActionListener(e -> showSummary());
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradesText = gradesField.getText().trim();

        if (name.isEmpty() || gradesText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter name and grades.");
            return;
        }

        Student student = new Student(name);
        try {
            String[] gradeStrings = gradesText.split(",");
            for (String g : gradeStrings) {
                int grade = Integer.parseInt(g.trim());
                if (grade < 0 || grade > 100) throw new NumberFormatException();
                student.grades.add(grade);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade input. Use numbers 0-100 separated by commas.");
            return;
        }

        students.add(student);
        nameField.setText("");
        gradesField.setText("");
        JOptionPane.showMessageDialog(this, "Student added successfully!");
    }

    private void showSummary() {
        if (students.isEmpty()) {
            displayArea.setText("No students added.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Student s : students) {
            sb.append(s.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}
