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
		main.addNewData();
		main.printSchools();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	
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
		newSchool.setName("Xaviers");
		
				
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
