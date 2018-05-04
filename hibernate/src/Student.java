
public class Student {
		
	private long id;
	private String name;
	private String surname;
	private String pesel;
		
	
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student (String name, String surname, String pesel ) {
		this.name = name;
		this.pesel =pesel;
		this.surname =surname;
		
	}

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public String getPesel() {
		return pesel;
	}



	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	
	public String toString() {
		return "Student: " + getName() + " " + getSurname() + " " + getPesel();
}
	
}
