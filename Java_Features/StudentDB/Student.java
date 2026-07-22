import java.io.Serializable;

public class Student implements Serializable{
    //Attributes
    private String name;
    private String surname;
    private int age;
    private int grade;

    //Constructor
    public Student(String name, String surname, int age, int grade) throws InvalidAgeException, InvalidGradeException {
        if(age < 5 || age > 100){
            throw new InvalidAgeException("Invalid age");
        }
        if(grade< 0 || grade> 10){
            throw new InvalidGradeException("Invalid grade");
        }

        this.name= name;
        this.surname= surname;
        this.age= age;
        this.grade= grade;
    }

    //Getters
    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public int getGrade(){
        return grade;
    }

    public int getAge(){
        return age;
    }

    //Text Area output with full student details
    public String toString(){
        return name + " " + surname + " Grade: " + grade + " Age: " + age;
    }
}
