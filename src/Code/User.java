package Code;

public class User {

	String name;
    String ID;
	String program;
	String role;

	    public User(String name, String ID, String program, String role) {
	        this.name = name;
	        this.ID = ID;
	        this.program = program;
	        this.role = role;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getProgram() {
			return program;
		}

		public void setProgram(String program) {
			this.program = program;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

	  	


}
