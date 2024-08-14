package Code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ManagementModule {
    private ArrayList<Student> studentDatabase;
    private HashMap<User, ArrayList<Guest>> eventDatabase;

    public List<Student> getStudentDatabase() {
		return studentDatabase;
	}

	public void setStudentDatabase(ArrayList<Student> studentDatabase) {
		this.studentDatabase = studentDatabase;
	}

	public HashMap<User, ArrayList<Guest>> getEventDatabase() {
		return eventDatabase;
	}

	public void setEventDatabase(HashMap<User, ArrayList<Guest>> eventDatabase) {
		this.eventDatabase = eventDatabase;
	}

	public ManagementModule() {
        studentDatabase = new ArrayList<>();
        eventDatabase = new HashMap<>();
    }

    public void registerStudent(Student student) {
        if (studentDatabase.contains(student)) {
            System.out.println("Student already exists in the database.");
        } else {
            studentDatabase.add(student);
            eventDatabase.put(student, new ArrayList<>());
            System.out.println("Student registration successful.");
        }
    }

    public boolean checkRegistrationStatus(Student student) {
        return student.isRegistered();
    }

    public void adjustRegistration(Student student, int additionalGuests) {
        if (student.isRegistered()) {
            ArrayList<Guest> guests = eventDatabase.get(student);
            int currentGuests = guests.size();
            if (currentGuests + additionalGuests <= 4) {
                for (int i = 0; i < additionalGuests; i++) {
                    guests.add(new Guest("Guest", student)); // Assuming you want to store the student's name
                }
                System.out.println("Event adjustment successful.");
            } else {
                System.out.println("You can only invite up to 4 guests.");
            }
        } else {
            System.out.println("You have not registered for the event yet.");
        }
    }


    public void deleteRegistration(Student student) {
        if (student.isRegistered()) {
            eventDatabase.remove(student);
            student.deleteEvent();
            System.out.println("Event deletion successful.");
        } else {
            System.out.println("You have not registered for the event yet.");
        }
    }

    public void getAllParticipants() {
        ArrayList<Student> students = studentDatabase;
       
        students.forEach(student -> System.out.println(student.getName() + " - Student"));
        
    }


    public void getStudentParticipants() {
        studentDatabase.stream()
                .filter(Student::isRegistered)
                .forEach(student -> {
                    System.out.println("Student: " + student.getName());
                    ArrayList<Guest> guests = eventDatabase.get(student);
                    if (guests.isEmpty()) {
                        System.out.println("No guests");
                    } else {
                        System.out.println("Guests:");
                        guests.forEach(guest -> System.out.println("- " + guest.getName()));
                    }
                });
    }


    public void getParticipantsByProgram(String program) {
        studentDatabase.stream()
                .filter(student -> student.isRegistered() && student.getProgram().equals(program))
                .forEach(student -> System.out.println(student.getName()));
    }

    public boolean searchStudent(String name) {
        Optional<Student> student = studentDatabase.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();

        if (student.isPresent()) {
            System.out.println("Student " + name + " has registered for the event.");
            return true;
        } else {
            System.out.println("Student " + name + " has not registered for the event.");
            return false;
        }
    }

    public void setProgramResponsible(Student student, ProgramResponsible programResponsible) {
        if (student.isRegistered()) {
            student.setProgramResponsible(programResponsible);
            System.out.println("Program Responsible set for the student.");
        } else {
            System.out.println("You have not registered for the event yet.");
        }
    }
    
    public void printCeremonyProgram() {
        System.out.println("Introduction: 30 minutes.");
        for (Student student : studentDatabase) {
            if (student.isRegistered()) {
                System.out.println("- Total duration: " + calculateProgramDuration(student) + " minutes");
            }
        }
        System.out.println("Closing remarks: 15 minutes.");
    }

    private int calculateProgramDuration(Student student) {
        int totalStudents = studentDatabase.size();
        int speechDuration = 2;
        int additionalMinutes = (totalStudents / 5);
        return speechDuration + additionalMinutes;
    }

    private ArrayList<Student> getProgramStudents(String program) {
        return (ArrayList<Student>) studentDatabase.stream()
                .filter(student -> student.getProgram().equals(program))
                .toList();
    }

    private ArrayList<Guest> getAllGuests() {
        ArrayList<Guest> guests = new ArrayList<>();
        eventDatabase.values().forEach(guestList -> {
            for (Guest guest : guestList) {
                if (!guests.contains(guest)) {
                    guests.add(guest);
                }
            }
        });
        return guests;
    }
    
    public void addGuest(Student student, Guest guest) {
        if (student != null && guest != null) {
            if (eventDatabase.containsKey(student)) {
                ArrayList<Guest> guestList = eventDatabase.get(student);
                if (guestList.size() < 4) {
                    guestList.add(guest);
                    System.out.println("Guest added successfully.");
                } else {
                    System.out.println("You can only invite up to 4 guests.");
                }
            } else {
                System.out.println("Student has not registered for the event yet.");
            }
        } else {
            System.out.println("Invalid student or guest provided.");
        }
    }


}

