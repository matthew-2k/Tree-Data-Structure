import java.util.Objects;

class Student
{

    String name;
    String student_number;
    String field_of_study;
    String GPA;

    public Student(String name) {
        this.name = name;
    }
    //constructor of student
    public Student(String name, String student_number, String field_of_study, String GPA) {
        this.name = name;
        this.student_number = student_number;
        this.field_of_study = field_of_study;
        this.GPA = GPA;
    }
    //getters
    public String getName() {
        return name;
    }
    public String getStudent_number() {
        return student_number;
    }

    public String getField_of_study() {
        return field_of_study;
    }

    public String getGPA() {
        return GPA;
    }

    @Override
    public boolean equals(Object o) { //over riding squals method
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(student_number, student.student_number) &&
                Objects.equals(field_of_study, student.field_of_study) &&
                Objects.equals(GPA, student.GPA);
    }

    @Override
    public int hashCode() {  //over riding hashcode method
        int hash = 3;
        hash = Integer.parseInt(67 * hash + this.student_number);
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = Integer.parseInt(67 * hash + this.field_of_study);
        return hash;
       // return Objects.hash(name, student_number, field_of_study);
    }


}


