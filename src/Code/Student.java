package Code;

import java.util.ArrayList;

public class Student extends User {
    private boolean registrationStatus;
    private ArrayList<Guest> guests;
    ProgramResponsible programResponsible;

    public Student(String name, String ID, String program) {
        super(name, ID, program, "student");
        this.registrationStatus = false;
        this.guests = new ArrayList<>();
    }

    public boolean isRegistered() {
        return registrationStatus;
    }

    public void registerEvent() {
        if (registrationStatus) {
            System.out.println("You have already registered for the event.");
        } else {
            registrationStatus = true;
            System.out.println("Event registration successful.");
        }
    }

    public void adjustEvent(int additionalGuests) {
        if (registrationStatus) {
            int currentGuests = guests.size();
            if (currentGuests + additionalGuests <= 4) {
                for (int i = 0; i < additionalGuests; i++) {
                	guests.add(new Guest(this.getName(), this));
                }
                System.out.println("Event adjustment successful.");
            } else {
                System.out.println("You can only invite up to 4 guests.");
            }
        } else {
            System.out.println("You have not registered for the event yet.");
        }
    }

    public void deleteEvent() {
        if (registrationStatus) {
            registrationStatus = false;
            guests.clear();
            System.out.println("Event deletion successful.");
        } else {
            System.out.println("You have not registered for the event yet.");
        }
    }
    
    public ProgramResponsible getProgramResponsible() {
        return programResponsible;
    }

    public void setProgramResponsible(ProgramResponsible programResponsible) {
        this.programResponsible = programResponsible;
    }
    
    public ArrayList<Guest> getGuests() {
        return guests;
    }
}

