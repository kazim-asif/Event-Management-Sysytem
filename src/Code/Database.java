package Code;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/universityDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    		 
  
    public static void fetchStudentsAndGuests() {
        
    	System.out.println("Connecting with database ..");
    	
    	
    	List<Student> students = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
        	Statement statement = connection.createStatement()) {
        	System.out.println("Connection established ..");
        	System.out.println("Reading data ..");
            String studentsQuery = "SELECT id, name, programId FROM Students";
            ResultSet studentsResultSet = statement.executeQuery(studentsQuery);

            // Fetch students
            while (studentsResultSet.next()) {
                int studentId = studentsResultSet.getInt("id");
                String studentName = studentsResultSet.getString("name");
                String prog = studentsResultSet.getString("programId");
                Student student = new Student(studentName,""+studentId, prog);
                Main.database.registerStudent(student);
                students.add(student);
            }

            String guestsQuery = "SELECT id, name, invitedByStudentId FROM Guests";
            ResultSet guestsResultSet = statement.executeQuery(guestsQuery);

            // Fetch guests
            while (guestsResultSet.next()) {
                int guestId = guestsResultSet.getInt("id");
                String guestName = guestsResultSet.getString("name");
                int invitedByStudentId = guestsResultSet.getInt("invitedByStudentId");

                for(int i=0;i<students.size();i++) {
                	if(students.get(i).ID.equals(""+invitedByStudentId)) {
                		 Guest guest = new Guest(guestName,students.get(i));
                		 Main.database.addGuest(students.get(i), guest);
                        
                	}
                }
                
               

               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

       
    }


    public static void storeStudentsAndGuests(List<Student> students) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Clear existing data from the tables
            statement.executeUpdate("DELETE FROM Students");
            statement.executeUpdate("DELETE FROM Guests");

            // Insert students and their guests
            for (Student student : students) {
                String studentQuery = String.format("INSERT INTO Students (id, name, programId) VALUES (%d, '%s', '%s')",
                       Integer.parseInt(student.ID), student.getName(),student.program);
                statement.executeUpdate(studentQuery);
                int x=0;
                for (Guest guest : student.getGuests()) {
                	x++;
                    String guestQuery = String.format("INSERT INTO Guests (id, name, invitedByStudentId) " +
                            "VALUES (%d, '%s', %d)", (x+ Integer.parseInt(student.ID)+101) , guest.getName(), Integer.parseInt(student.ID));
                    statement.executeUpdate(guestQuery);
                }
            }

            //System.out.println("Data stored in the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Student findStudentById(List<Student> students, int id) {
        for (Student student : students) {
            if ( Integer.parseInt(student.ID) == id) {
                return student;
            }
        }
        return null;
    }
}

