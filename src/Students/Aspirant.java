package Students;

public class Aspirant extends Student {
    private String scienceProject;
    public Aspirant (String firstName, String lastName, int group, double averageMark, String scienceProject) {
        super (firstName, lastName, group, averageMark);
        this.scienceProject = scienceProject;

    }

    @Override
    public int getScholarship() {
        int sum = 2500;
        int sum1 = 2200;
        if (getAverageMark() == 5) {
            return sum;
        } return sum1;


    }
}
