package Code;

public class ProgramResponsible extends User {
    public ProgramResponsible(String name, String ID, String program) {
        super(name, ID, program, "program responsible");
    }

    public int responsibleSpeechDuration(int totalStudents) {
        return 1 + (totalStudents / 5);
    }
}

