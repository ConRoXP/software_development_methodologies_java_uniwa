import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class StudentApp extends JFrame{
    private JTextField nameField, surnameField, gradeField, ageField;
    private JButton addButton, searchButton, saveButton, loadButton;
    private JTextArea outputArea;

    private ArrayList<Student> students= new ArrayList<>();

    //GUI creation
    public StudentApp(){
        //Window parameters
        setTitle("Student Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        //Labels
        add(new JLabel("Name:"));
        nameField= new JTextField(15);
        add(nameField);
        
        add(new JLabel("Surname:"));
        surnameField= new JTextField(15);
        add(surnameField);

        add(new JLabel("Grade:"));
        gradeField= new JTextField(5);
        add(gradeField);

        add(new JLabel("Age:"));
        ageField= new JTextField(5);
        add(ageField);

        //Buttons
        addButton= new JButton("Add");
        searchButton= new JButton("Search");
        saveButton= new JButton("Save");
        loadButton= new JButton("Load");

        add(addButton);
        add(searchButton);
        add(saveButton);
        add(loadButton);

        //Text Area
        outputArea= new JTextArea(10, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        //Action Listeners
        addButton.addActionListener(e-> addStudent());
        searchButton.addActionListener(e-> searchStudent());
        saveButton.addActionListener(e-> saveStudents());
        loadButton.addActionListener(e-> loadStudents());

        setVisible(true);
    }

    //Adding students to Array List
    private void addStudent(){
        String name= nameField.getText();
        String surname= surnameField.getText();
        String gradeText= gradeField.getText();
        String ageText= ageField.getText();

        //Grade and age validation
        try{
            int age= Integer.parseInt(ageText);
            int grade= Integer.parseInt(gradeText);
            Student student= new Student(name, surname, age, grade);
            students.add(student);

            outputArea.append(student + " added.\n");
        }
        catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(this, "Invalid data type in age section");
        }
        catch(InvalidAgeException iae){
            JOptionPane.showMessageDialog(this, "Invalid age");
        }
        catch(InvalidGradeException ige){
            JOptionPane.showMessageDialog(this, "Invalid grade");
        }
    }

    //Student search
    private void searchStudent(){
        String name= nameField.getText();
        String surname= surnameField.getText();
        boolean found= false;

        for(Student s: students){
            if(s.getName().equalsIgnoreCase(name) && s.getSurname().equalsIgnoreCase(surname)){
                outputArea.append(s + " found\n");
                found= true;
                break;
            }
        }
        if(found== false){
            outputArea.append("Student not found\n");
        }
    }

    //Saving to file
    private void saveStudents() {
        try(ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream("students.dat"))){
            oos.writeObject(students);
            outputArea.append("Students saved sucessfully\n");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error saving students: " + e.getMessage());
        }
    }

    //Loading from file
    @SuppressWarnings("unchecked")
    private void loadStudents(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))){
            students= (ArrayList<Student>) ois.readObject();
            outputArea.append("Students loaded successfully\n");
            for(Student s : students){
                outputArea.append(s.toString() + "\n");
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        new StudentApp();
    }
}

