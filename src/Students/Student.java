package Students;

public class Student {

    private String firstName;
    private String lastname;
    private int group;
    private double averageMark;

    public Student (String firstName, String lastname, int group, double averageMark) {
        setFirstName(firstName);
        setLastname(lastname);
        setGroup(group);
        setAverageMark(averageMark);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public int getGroup() {
        return group;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public int getScholarship() {
        int sum = 2000;
        int sum1 = 1900;
        if (getAverageMark() == 5) {
            return sum;
        } return sum1;


    }
    public void printInfo() {
        System.out.println(getScholarship());
    }
}
