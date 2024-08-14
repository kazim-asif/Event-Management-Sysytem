package Code;

public class Guest {
    private String name;
    private Student invitedBy;

    public Guest(String name, Student invitedBy) {
        this.name = name;
        this.invitedBy = invitedBy;
    }

    public String getName() {
        return name;
    }

    public Student getInvitedBy() {
        return invitedBy;
    }
}
