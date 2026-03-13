package CRS;
import java.io.*;

public class User implements Serializable {
	
	// A unique ID for serialization
	private static final long serialVersionUID = 1L;
	
	// Fields (information stored about every user). 
	// These are protected so Admin and Student classes can access them directly.
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
		
	// Constructor: used when creating a new User object.
	// It sets the username, password, first name, and last name.
	public User(String un, String pw, String fn, String ln) {
			this.username = un;
			this.password = pw;
			this.firstName = fn;
			this.lastName = ln;
		}
	
	// Check if login information matches this User's data.
	public boolean check_login(String username, String password) {
		if(this.username.equals(username) && this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	// Getters: allow safe access to private/protected fields.
	public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public String getLastName() {
    	return lastName;
    }
}

