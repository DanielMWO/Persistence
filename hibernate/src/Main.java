import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.objectweb.asm.ClassAdapter;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		//main.executeQueries();
		main.countElements();
		//main.updateSchool();
		main.addNewTeacherData();
		//main.addNewData();
		//main.printSchools();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	
	//Zadanie4 (1-2)
	private void executeQueries() {
        String hql = "FROM School s WHERE s.name = 'UE'";
        Query query = session.createQuery(hql);
        List results = query.list();
        System.out.println(results);
        
        
        Transaction transaction = session.beginTransaction();
        for (Object s:results) {
        	session.delete(s);
        	}
        transaction.commit();
        System.out.println("Deleted");
	}
	//Zadaie4 (3-5)
	private void countElements() {
		String countSchools =  "SELECT COUNT(s) FROM School s";
		String countStudnets =  "SELECT COUNT(s) FROM Student s";
		String countSchools2 =  "SELECT s.name FROM School s WHERE s.classes.size >= 2";
		String findSchools =  "SELECT s FROM School s INNER JOIN s.classes classes WHERE classes.profile = 'mat-fiz' AND classes.currentYear >= 2";
		
		Query query = session.createQuery(countSchools);
		System.out.println("Liczba Szkó³: " +query.list().toString());
		query = session.createQuery(countStudnets);
		System.out.println("Liczba studentów: " +query.list().toString());
		query = session.createQuery(countSchools2);
		System.out.println("Szko³y o co najmniej dwóch klasach : " +query.list().toString());
		query = session.createQuery(findSchools);
		System.out.println("Szko³a z mat-fiz i roku 2+ : " +query.list().toString());
	}
	
	//Zadaine4(5)
	private void updateSchool() {
	
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 2);
		School school = (School) query.uniqueResult();
		System.out.println("\n---------\n" + school);
		Transaction transaction = session.beginTransaction();
		school.setAddress("ul. Zmieniona 22");
		transaction.commit();
		query.setLong("id", 2);
		School school2 = (School) query.uniqueResult();
		System.out.println("\n---------\n Nowy Adress" + school2);
		
	}
	
	//Zadanie 5 
	
	private void addNewTeacherData() {
		//preparing Teachers
		
		Teacher teacher1 = new Teacher("Morgan", "Freeman","Pscyhic reading");
		Teacher teacher2 = new Teacher("Niko", "Belic","Regeneration");
		Teacher teacher3 = new Teacher("Vlad", "Impailer","Meditatiom");
		Teacher teacher4 = new Teacher("Conan", "Babarian","One-arm combat");
		
		//fetching classes
	
		Query query = session.createQuery("FROM SchoolClass WHERE id=5");
		SchoolClass sClass1 = (SchoolClass) query.uniqueResult();
		query = session.createQuery("FROM SchoolClass WHERE id=6");
		SchoolClass sClass2 = (SchoolClass) query.uniqueResult();
		System.out.println(sClass1);
		System.out.println(sClass2);
		
		Transaction transaction = session.beginTransaction();
		
		teacher1.addClasses(sClass1);
		teacher1.addClasses(sClass2);
		teacher2.addClasses(sClass1);
		teacher3.addClasses(sClass2);
		teacher4.addClasses(sClass2);
		
		session.save(teacher1);
		session.save(teacher2);
		session.save(teacher3);
		session.save(teacher4);
		
		sClass1.addTeachers(teacher1);
		sClass1.addTeachers(teacher2);
		
		sClass2.addTeachers(teacher1);
		sClass2.addTeachers(teacher3);
		sClass2.addTeachers(teacher4);
				
		
		transaction.commit();
	}
		
	
	
	
	//Zadanie 3
	private void addNewData() {
		
		//Preaparing The DATA
		Set<Student> tempStudentSet1 = new HashSet<Student>();
		Set<Student> tempStudentSet2 = new HashSet<Student>();
		Set<SchoolClass> tempClasses = new HashSet<SchoolClass>();
		
		Student studnent1 = new Student("Dave", "Jones", "87032298322");
		Student studnent2 = new Student("Freddy", "Kryger", "87032298322");
		Student studnent3 = new Student("Marlin", "Manson", "87032298322");
		Student studnent4 = new Student("Bat", "Man", "87032298322");
		Student studnent5 = new Student("Rock", "Man", "87032298322");
		Student studnent6 = new Student("Criss", "Rock", "87032298322");
		Student studnent7 = new Student("David", "Bones", "87032298322");
		
		tempStudentSet1.add(studnent1); 		
		tempStudentSet1.add(studnent2); 		
		tempStudentSet1.add(studnent3); 		
		tempStudentSet1.add(studnent4); 		
		tempStudentSet2.add(studnent5); 		
		tempStudentSet2.add(studnent6); 		
		tempStudentSet2.add(studnent7); 		
		
		
		SchoolClass classA = new SchoolClass();
		SchoolClass classB = new SchoolClass();
		
		classA.setStudents(tempStudentSet1);
		classA.setCurrentYear(1);
		classA.setStartYear(2018);
		classA.setProfile("SpecialTreatment");
		
		classB.setStudents(tempStudentSet2);
		classB.setCurrentYear(2);
		classB.setStartYear(2017);
		classB.setProfile("RockAndRoll");
		
		
		tempClasses.add(classB);
		tempClasses.add(classA);
		
		School newSchool = new School();
		
		newSchool.setClasses(tempClasses);
		newSchool.setAddress("Not to be disclosed");
		newSchool.setName("UE");
		
				
		//Addidng DATA
		
		Transaction transaction = session.beginTransaction();
		session.save(newSchool);
		transaction.commit();
		
	}	
	
	
	
	
	
	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		
		System.out.println("### Schools and classes");
		for (School s : schools) {
			System.out.println(s);
			s.listClasses();
			
		}
	}

/*	private void jdbcTest() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}*/
	
	// end jdbcTest

}
