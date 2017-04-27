package tests.dto;

public class User {

	private String username;
	private String password;
	private boolean isValid;

	public User(String isValid, String username, String password) {
		this.isValid = Boolean.parseBoolean(isValid);
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
