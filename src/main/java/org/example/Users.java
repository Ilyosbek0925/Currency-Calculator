package org.example;

public class Users {
	String name;
	String email;
	long id;
	States state;

	public Users(String name, String email, char id, States state) {
		this.name = name;
		this.email = email;
		this.id = id;
		this.state = state;
	}
	public Users(Long id, States state) {
		this.id = id;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public long getId() {
		return id;
	}

	public States getState() {
		return state;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setState(States state) {
		this.state = state;
	}
}
