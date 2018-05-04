import java.util.HashSet;
import java.util.Set;

public class Teacher {

	private long id;
	private String name;
	private String surname;
	private String subject;
	private Set<SchoolClass> classes;
	
	public Teacher() {
		this.classes = new HashSet<SchoolClass>();
	}
	
	
	public Teacher(String name, String surname, String subject) {
		this.name = name;
		this.surname = surname;
		this.subject = subject;
	
	}

	public Teacher(String name, String surname, String subject, Set<SchoolClass> classes) {
		this.name = name;
		this.surname = surname;
		this.subject = subject;
		this.classes = classes;
	}

	public Set<SchoolClass> getClasses() {
		return classes;
	}

	public void setClasses(Set<SchoolClass> classes) {
		this.classes = classes;
	}
	
	public void addClasses(SchoolClass schoolClass) {
		this.classes.add(schoolClass);
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
