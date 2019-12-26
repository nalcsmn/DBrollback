package rollback;

import java.sql.*;

import java.util.Scanner;

public class savepoint {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		boolean check = true;
		ResultSet rs = null;
		Savepoint sp = null;

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/savep?autoReconnect=true&useSSL=false", "root",
				"Qwerty120995!");

		System.out.println("	");
		System.out.println("INSERT THE FOLLOWING DETAILS!!");

		conn.setAutoCommit(false);
		sp = conn.setSavepoint("BEFORESHIT");

		try {

			System.out.println("Enter Lastname");
			String lastname = scan.next();
			System.out.println("Enter Firstname");
			String firstname = scan.next();
			System.out.println("Enter Middlename");
			String middlename = scan.next();
			System.out.println("Enter Age");
			int age = scan.nextInt();

			pst = conn.prepareStatement("INSERT INTO savesample (last_name, middle_name, first_name, age) VALUES (?,?,?,?)");
			pst.setString(1, lastname);
			pst.setString(2, middlename);
			pst.setString(3, firstname);
			pst.setInt(4, age);
			pst.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			conn.rollback(sp);
		}

		System.out.println("Do you want to add more details Y/N");
		String answer = scan.next();

		if (answer.equalsIgnoreCase("Y")) {
			check = true;

		} else {

			System.out.println("What do you want to do (Update, Delete or Select)");
			String option = scan.next();

			

				if (option.equalsIgnoreCase("Update")) {
					System.out.println("What do you want to update (lastname, firstname, middlename, age");
					String option2 = scan.next();
					System.out.println("Enter the id number");
					int idnum = scan.nextInt();
					System.out.println("Please input new value");
					String value = scan.next();
					
					conn.setAutoCommit(false);
					sp = conn.setSavepoint("After 1st Insert");
					
					
					try {

					if (option2.equalsIgnoreCase("lastname")) {
						pst = conn.prepareStatement("UPDATE savesample SET last_name = ? WHERE id = ?");
						pst.setString(1, value);
						pst.setInt(2, idnum);
						pst.executeUpdate();
						conn.commit();
					}

					else if (option2.equalsIgnoreCase("firstname")) {
						pst = conn.prepareStatement("UPDATE savesample SET first_name = ? WHERE id = ?");
						pst.setString(1, value);
						pst.setInt(2, idnum);
						pst.executeUpdate();
						conn.commit();

					}

					else if (option2.equalsIgnoreCase("middlename")) {
						pst = conn.prepareStatement("UPDATE savesample SET middle_name = ? WHERE id = ?");
						pst.setString(1, value);
						pst.setInt(2, idnum);
						pst.executeUpdate();
						conn.commit();
					}

					else if (option2.equalsIgnoreCase("age")) {
						pst = conn.prepareStatement("UPDATE savesample SET age = ? WHERE id = ?");
						pst.setString(1, value);
						pst.setInt(2, idnum);
						pst.executeUpdate();
						conn.commit();
					
				}
			} catch (Exception e) {
				conn.rollback(sp);
			}
			conn.setAutoCommit(false);
			sp =conn.setSavepoint("After 2nd Insert");
			try {

				if (option.equalsIgnoreCase("delete")) {
					System.out.println("Enter the id number you want to delete");
					int del = scan.nextInt();
					pst = conn.prepareStatement("DELETE FROM user_details WHERE id = ?");
					pst.setInt(1, del);
					pst.execute();
					conn.commit();
				}
			} catch (Exception b) {
				conn.rollback(sp);
			}
			conn.setAutoCommit(false);
			conn.setSavepoint("After 3rd Insert");

			try {

				if (option.equalsIgnoreCase("select")) {
					System.out.println("Please input the id number you want to select");
					int sel = scan.nextInt();
					pst = conn.prepareStatement("SELECT * FROM user_details WHERE ID = ?");
					pst.setInt(1, sel);
					rs = pst.executeQuery();
					conn.commit();
				}

				while (rs.next()) {
					System.out.println(rs.getString("last_name") + " " + rs.getString("first_name") + " "
							+ rs.getString("middle_name") + " " + rs.getString("age"));
				}
				
			} catch (Exception q) {
				conn.rollback(sp);
			}

		}
		
		}conn.close();
		main(args);

	}

}
