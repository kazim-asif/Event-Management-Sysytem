package Code;

import java.util.Scanner;

public class Main {
    static ManagementModule database;
    private static Student currentStudent;

    public static void main(String[] args) {
        database = new ManagementModule();
        Database.fetchStudentsAndGuests();
        Scanner scanner = new Scanner(System.in);

        boolean quit = false;
        while (!quit) {
            System.out.println("1. Sign in");
            System.out.println("2. Create new event");
            System.out.println("3. Sign out");
            System.out.println("4. List all participants");
            System.out.println("5. List student participants");
            System.out.println("6. List participants by program");
            System.out.println("7. Search for a student");
            System.out.println("8. Print ceremony program");
            System.out.println("9. Delete event");
            System.out.println("0. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    signIn(scanner);
                    break;
                case 2:
                    createNewEvent(scanner);
                    break;
                case 3:
                    signOut();
                    break;
                case 4:
                    database.getStudentParticipants();
                    break;
                case 5:
                    database.getAllParticipants();
                    break;
                case 6:
                    listParticipantsByProgram(scanner);
                    break;
                case 7:
                    searchStudent(scanner);
                    break;
                case 8:
                    database.printCeremonyProgram();
                    break;
                case 9:
                    database.deleteRegistration(currentStudent);
                    break;
                case 0:
                    quit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
            
            Database.storeStudentsAndGuests(database.getStudentDatabase());
        }

        scanner.close();
    }

    private static void signIn(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter your program: ");
        String program = scanner.nextLine();

        currentStudent = new Student(name, ID, program);
        
        System.out.println("Sign in successful.");
    }

    private static void createNewEvent(Scanner scanner) {
        if (currentStudent == null) {
            System.out.println("Please sign in first.");
            return;
        }
        
        if (currentStudent.isRegistered()) {
            System.out.println("You have already registered for the event.");
            System.out.print("Do you want to edit your existing event and add remaining guests? (Y/N): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                editEvent(scanner);
                return;
            }
        }

        ProgramResponsible programResponsible = new ProgramResponsible("John Doe", null, null);
        database.setProgramResponsible(currentStudent, programResponsible);
        database.registerStudent(currentStudent);

        System.out.print("Enter the number of guests you want to invite (up to 4): ");
        int numGuests = scanner.nextInt();
        scanner.nextLine();

        currentStudent.registerEvent();
        currentStudent.adjustEvent(numGuests);

        for (int i = 0; i < numGuests; i++) {
            System.out.print("Enter the name of guest " + (i + 1) + ": ");
            String guestName = scanner.nextLine();
            Guest guest = new Guest(guestName,currentStudent);
            database.addGuest(currentStudent, guest);
        }
    }


    private static void editEvent(Scanner scanner) {
        if (currentStudent == null) {
            System.out.println("Please sign in first.");
            return;
        }

        if (!currentStudent.isRegistered()) {
            System.out.println("You have not registered for the event yet.");
            return;
        }

        System.out.println("You have already registered for the event.");
        System.out.print("Enter the number of remaining guests you want to add (up to " + (4 - currentStudent.getGuests().size()) + "): ");
        int numRemainingGuests = scanner.nextInt();
        scanner.nextLine();

        int remainingSlots = 4 - currentStudent.getGuests().size();
        if (numRemainingGuests > remainingSlots) {
            System.out.println("You can only add up to " + remainingSlots + " remaining guests.");
            return;
        }

        currentStudent.adjustEvent(numRemainingGuests);

        for (int i = currentStudent.getGuests().size() - numRemainingGuests; i < currentStudent.getGuests().size(); i++) {
            System.out.print("Enter the name of guest " + (i + 1) + ": ");
            String guestName = scanner.nextLine();
            Guest guest = new Guest(guestName,currentStudent);
            database.addGuest(currentStudent, guest);
        }

        System.out.println("Event adjusted successfully.");
    }

    
    private static void signOut() {
        currentStudent = null;
        System.out.println("Signed out successfully.");
    }

    private static void listParticipantsByProgram(Scanner scanner) {
        System.out.print("Enter the program name: ");
        String program = scanner.nextLine();

        database.getParticipantsByProgram(program);
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter the name of the student: ");
        String name = scanner.nextLine();

        database.searchStudent(name);
    }
}

