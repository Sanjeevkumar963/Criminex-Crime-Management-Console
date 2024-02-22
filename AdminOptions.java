package com.java.criminalDatabase;
import java.sql.*;
import java.util.Date;
import java.util.*;
public class AdminOptions
{
	private Connection con=null;
	public void get_connection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Criminal_Database","root","root");
			// 3306 - port number 
			// three parameters
			// url(db name),username and password
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void displayOptions(Scanner sc) 
	{
		while (true) 
		{
			System.out.println("Admin Options:");//
			System.out.println("1. Add Suspects");//
			System.out.println("2. Add Case Details");
			System.out.println("3. File a court Session");
			System.out.println("4. Modify Suspect (Or) Case Details");
			System.out.println("5. Select Suspect BY ID");//
			System.out.println("6. View All Suspect List");//
			System.out.println("7. View All Suspects List By Gender");//
			System.out.println("8. View All Suspects List By Status");//
			System.out.println("9. Regular Updation");
			System.out.println("10. Display all Prisoners");
			System.out.println("11. Exit");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();
			sc.nextLine(); 

			switch (choice) 
			{
			case 1:
				System.out.print("Enter Suspect name: ");
				String name = sc.nextLine();
				System.out.print("Enter Age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter Gender: ");
				String gender = sc.nextLine();
				System.out.print("Enter Suspect ID: ");
				String s_id = sc.nextLine();
				addCriminal(name, age, gender, s_id);
				break;
			case 2:
				System.out.print("Enter Suspect ID: ");
				String s_id1 = sc.nextLine();
				System.out.print("Enter FIR_NO ID: ");
				String fir_no = sc.nextLine();
				System.out.print("Enter Crime: ");
				String crime = sc.nextLine();
				System.out.print("Enter Guilty Status: ");
				String status = sc.nextLine();
				addCaseDetails(s_id1,fir_no,crime,status);
				break;
			case 3:
				System.out.print("Enter Case no: ");
				String case_no = sc.nextLine();
				System.out.print("Enter Suspect ID: ");
				String s_id2 = sc.nextLine();
				System.out.print("Enter Advocate: ");
				String advocate = sc.nextLine();
				System.out.print("Enter Case Date: ");
				String date = sc.nextLine();
				System.out.print("Enter Judgement: ");
				String judgement = sc.nextLine();
				fileCase(case_no,s_id2,advocate,date,judgement);
				break;
			case 4:
				System.out.print("1. Age\n2.Case_date\n3.update Judgement ");
				int num = sc.nextInt();
				sc.nextLine();
				if(num==1)
				{
					System.out.println("Enter Criminal ID:");
					String id=sc.nextLine();
					System.out.println("Enter Age To Be Updated:");
					int a=sc.nextInt();
					modifyAgeBySuspectID(id,a);
				}
				else if(num==2)
				{
					System.out.println("Enter Case No:");
					String id=sc.nextLine();
					System.out.println("Enter Case Date to Be Updated:");
					String s=sc.nextLine();
					updateCaseDateByCaseNumber(id,s);
				}
				else if(num==3)
				{
					System.out.println("Enter Case No:");
					String id=sc.nextLine();
					System.out.println("Enter Case Judgement to Be Updated:");
					String s=sc.nextLine();
					updateJudgmentByCaseNumber(id,s);
				}
				break;
			case 5:
				System.out.print("Enter Suspect ID: ");
				String searchID=sc.nextLine();
				specificSuspect(searchID);
				break;
			case 6:
				displayAllSuspects();
				break;
			case 7:
				System.out.print("Enter Gender: ");
				String g = sc.nextLine();
				displayAllSuspectsByGender(g);
				break;
			case 8:
				System.out.print("Enter status: ");
				String h = sc.nextLine();
				displayAllSuspectsByStatus(h);
				break;
			case 9:
				handleJudgement();
				retrieveAndProcessJudgements();
				System.out.println("Regular Updation Done");
				break;
			case 10:
				displayAllPrisoners();
				break;
			case 11:
				System.out.println("Exiting Criminal Database...");
				return;               	
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	
	// Adding Criminals
	public void addCriminal(String name, int age,String gender,String s_id) 
	{
		try 
		{

			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO suspects (name, age, gender, s_id) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, name);
			preparedStatement.setLong(2, age);
			preparedStatement.setString(3, gender);
			preparedStatement.setString(4, s_id);
			preparedStatement.executeUpdate();
			System.out.println("Suspect added successfully!");
			
		} 
		catch (SQLException e) 
		{
			System.out.println("Error inserting Suspect data: " + e.getMessage());
		}
	}
	
	// adding case details
	public void addCaseDetails(String s_id, String fir_no,String crime,String guilt_status) 
	{
		try 
		{
//			Date currentDate = new Date();
//			Timestamp timestamp = new Timestamp(currentDate.getTime());
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO caseDetails (suspect_id,  fir_no, crime, guilty_status) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, s_id);
			preparedStatement.setString(2, fir_no);
			preparedStatement.setString(3, crime);
			preparedStatement.setString(4, guilt_status);
			preparedStatement.executeUpdate();
			System.out.println("Suspect added successfully!");
		} 
		catch (SQLException e) 
		{
			System.out.println("Error inserting Suspect data: " + e.getMessage());
		}
	}
	
	public void displayCaseDetailsById(String id) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT case_no,advocate,case_date,judgement FROM cases WHERE s_id= ?");
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String c = resultSet.getString("case_no");
				String a = resultSet.getString("advocate");
				Date d = resultSet.getDate("case_date");
				String j = resultSet.getString("judgement");

				// Display the details of the criminal
				System.out.println("Case No: " + c);
				System.out.println("Advocate: " + a);
				System.out.println("Case Date: " + d);
				System.out.println("Judgment: " + j);
				System.out.println("-----------------------------------");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching suspect details: " + e.getMessage());
		}
	}

	// view all Suspect list
	public void displayAllSuspects() 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM suspects");
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String SID = resultSet.getString("s_id");

				// Display the details of the criminal
				System.out.println("Name: " + name);
				System.out.println("Age: " + age);
				System.out.println("Gender: " + gender);
				System.out.println("Suspect ID: " + SID);
				System.out.println("-----------------------------------");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching suspect details: " + e.getMessage());
		}
	}

	// view all criminal list by gender
	public void displayAllSuspectsByGender(String g) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM suspects WHERE gender = ?");
			preparedStatement.setString(1, g);	        
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String SID = resultSet.getString("s_id");

				// Display the details of the criminal
				System.out.println("Name: " + name);
				System.out.println("Age: " + age);
				System.out.println("Gender: " + gender);
				System.out.println("Suspect ID: " + SID);
				System.out.println("-----------------------------------");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching Suspect details: " + e.getMessage());
		}
	}
	
	// view all criminal list by status
	public void displayAllSuspectsByStatus(String status) 
	{
	    try {
	        PreparedStatement preparedStatement = con.prepareStatement("SELECT s.*, cd.fir_no, cd.crime, cd.guilty_status, cd.date_of_arrest " +
	                                                                     "FROM suspects s " +
	                                                                     "JOIN caseDetails cd ON s.s_id = cd.suspect_id " +
	                                                                     "WHERE cd.guilty_status = ?");
	        preparedStatement.setString(1, status);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            String name = resultSet.getString("name");
	            int age = resultSet.getInt("age");
	            String gender = resultSet.getString("gender");
	            String suspectID = resultSet.getString("s_id");
	            String firNo = resultSet.getString("fir_no");
	            String crime = resultSet.getString("crime");
	            String guiltyStatus = resultSet.getString("guilty_status");
	            Timestamp dateOfArrest = resultSet.getTimestamp("date_of_arrest");

	            // Display the details of the suspect
	            System.out.println("Name: " + name);
	            System.out.println("Age: " + age);
	            System.out.println("Gender: " + gender);
	            System.out.println("Suspect ID: " + suspectID);
	            System.out.println("FIR Number: " + firNo);
	            System.out.println("Crime: " + crime);
	            System.out.println("Guilty Status: " + guiltyStatus);
	            System.out.println("Date of Arrest: " + dateOfArrest);
	            System.out.println("-----------------------------------");
	        }
	    } 
	    catch (SQLException e) 
	    {
	        System.out.println("Error fetching suspect details: " + e.getMessage());
	    }
	}


	// specific criminal
	public void specificSuspect(String s_id) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM suspects WHERE s_id = ?");
			preparedStatement.setString(1, s_id);	        
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String SID = resultSet.getString("s_id");

				// Display the details of the criminal
				System.out.println("Name: " + name);
				System.out.println("Age: " + age);
				System.out.println("Gender: " + gender);
				System.out.println("Suspect ID: " + SID);
				System.out.println("-----------------------------------");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching Suspect details: " + e.getMessage());
		}
	}

	// filing a case
	public void fileCase(String case_no, String s_id, String advocate, String date,String judgement)
	{
		try 
		{
			String fullDate = date + " 00:00:00";
			Timestamp caseDate = Timestamp.valueOf(fullDate);
			Timestamp filingDate = new Timestamp(System.currentTimeMillis());
			Date currentDate = new Date();
			Timestamp timestamp = new Timestamp(currentDate.getTime());
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO cases(case_no, s_id, advocate, filing_date, case_date ,judgement) VALUES (?, ?, ?, ?, CAST(? AS DATE), ?)");
			preparedStatement.setString(1, case_no);
			preparedStatement.setString(2, s_id);
			preparedStatement.setString(3, advocate);
			preparedStatement.setTimestamp(4, timestamp);
			preparedStatement.setTimestamp(5, caseDate);
			preparedStatement.setString(6, judgement);

			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows > 0) {
				System.out.println("Case filed successfully!");
			} else {
				System.out.println("Failed to file the case.");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error filing the case: " + e.getMessage());
		}
	}


	// Method to get criminal by ID
	private Suspect getCriminalByID(String criminalID) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Suspects WHERE s_id = ?");
			preparedStatement.setString(1, criminalID);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) 
			{
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String fir_no = resultSet.getString("s_id");
				return new Suspect(name, age, gender, criminalID);
			} 
			else 
			{
				return null; 
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching Suspect details: " + e.getMessage());
			return null;
		}
	}


	//modify age by criminal id
	public void modifyAgeBySuspectID(String SID, int newAge)
	{
		Suspect criminal = getCriminalByID(SID);

		if (criminal != null)
		{
			modifyAge(criminal,newAge, con);
		} 
		else 
		{
			System.out.println("Suspect with ID " + SID + " not found.");
		}
	}


	//modify age
	private void modifyAge(Suspect criminal, int newAge, Connection con2) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE suspects SET age = ? WHERE s_id = ?");
			preparedStatement.setInt(1, newAge);
			preparedStatement.setString(2, criminal.getS_id());
			int updatedRows = preparedStatement.executeUpdate();
			if (updatedRows > 0) 
			{
				System.out.println("Age modified successfully!");
				criminal.setAge(newAge); 
			} 
			else 
			{
				System.out.println("Suspect not found.");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error modifying criminal age: " + e.getMessage());
		}	
	}
	
	//update cade_date By Case Number
	public void updateCaseDateByCaseNumber(String caseNumber, String newCaseDate) 
	{
	    try {
	    	String fullDate = newCaseDate + " 00:00:00";
			Timestamp caseDate = Timestamp.valueOf(fullDate);
			Timestamp filingDate = new Timestamp(System.currentTimeMillis());
	        PreparedStatement preparedStatement = con.prepareStatement("UPDATE cases SET case_date = ? WHERE case_no = ?");
	        preparedStatement.setTimestamp(1, caseDate);
	        preparedStatement.setString(2, caseNumber);
	        int updatedRows = preparedStatement.executeUpdate();
	        if (updatedRows > 0) 
	        {
	            System.out.println("Case date updated successfully!");
	        } 
	        else 
	        {
	            System.out.println("Failed to update case date. Case number not found.");
	        }
	    } 
	    catch (SQLException e) 
	    {
	        System.out.println("Error updating case date: " + e.getMessage());
	    }
	}
	
	// Method to update judgment by case number
	public void updateJudgmentByCaseNumber(String caseNumber, String newJudgment) 
	{
	    try 
	    {
	        PreparedStatement preparedStatement = con.prepareStatement("UPDATE cases SET judgement = ? WHERE case_no = ?");
	        preparedStatement.setString(1, newJudgment);
	        preparedStatement.setString(2, caseNumber);
	        int updatedRows = preparedStatement.executeUpdate();
	        if (updatedRows > 0) 
	        {
	            System.out.println("Judgment updated successfully!");
	        } 
	        else 
	        {
	            System.out.println("Failed to update judgment. Case number not found.");
	        }
	    } 
	    catch (SQLException e) 
	    {
	        System.out.println("Error updating judgment: " + e.getMessage());
	    }
	}
	
	// retrieve judgments
	public void retrieveAndProcessJudgements() 
	{
	    try 
	    {
	        Statement statement = con.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT case_no, judgement, case_date, s_id FROM cases WHERE judgement = 'Next Session'");

	        while (resultSet.next()) 
	        {
	            String caseNumber = resultSet.getString("case_no");
	            String judgment = resultSet.getString("judgement");
	            Timestamp caseDate = resultSet.getTimestamp("case_date");
	            String criminalID = resultSet.getString("s_id");

	            if (judgment.equals("Next Session")) 
	            {
	                if (checkRemandExists(criminalID)) 
	                {
	                    updateRemandTill(criminalID, caseDate);
	                } 
	                else 
	                {
	                    insertIntoRemand(criminalID, caseDate);
	                }
	            }
	        }
	    } 
	    catch (SQLException e) 
	    {
	        System.out.println("Error retrieving and processing judgements: " + e.getMessage());
	    }
	}

	// check if the criminal is already is in the remand list
	private boolean checkRemandExists(String criminalID) 
	{
	    try 
	    {
	        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM remand WHERE s_id = ?");
	        preparedStatement.setString(1, criminalID);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        return resultSet.next();
	    } 
	    catch (SQLException e)
	    {
	        System.out.println("Error checking remand existence: " + e.getMessage());
	        return false;
	    }
	}

	//updating only remand till
	private void updateRemandTill(String SID, Timestamp caseDate) 
	{
	    try 
	    {
	        PreparedStatement preparedStatement = con.prepareStatement("UPDATE remand SET remand_till = CAST(? AS DATE) WHERE s_id = ?");
	        preparedStatement.setTimestamp(1, caseDate);
	        preparedStatement.setString(2, SID);
	        int updatedRows = preparedStatement.executeUpdate();
	        if (updatedRows > 0) 
	        {
	            System.out.println("Remand till date updated successfully for Suspect ID: " + SID);
	        } 
	        else 
	        {
	            System.out.println("Failed to update remand till date for Suspect ID: " + SID);
	        }
	    } 
	    catch (SQLException e)
	    {
	        System.out.println("Error updating remand till date: " + e.getMessage());
	    }
	}

	// inserting into remand table
	private void insertIntoRemand(String SID, Timestamp caseDate)
	{
	    try 
	    {
	        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO remand (s_id, remand_till) VALUES (?, CAST(? AS DATE))");
	        preparedStatement.setString(1, SID);
	        preparedStatement.setTimestamp(2, caseDate);
	        int insertedRows = preparedStatement.executeUpdate();
	        if (insertedRows > 0) 
	        {
	            System.out.println("Suspect ID and remand date inserted successfully.");
	        } 
	        else 
	        {
	            System.out.println("Failed to insert Suspect1 ID and remand date.");
	        }
	    } 
	    catch (SQLException e) 
	    {
	        System.out.println("Error inserting into remand table: " + e.getMessage());
	    }
	}
	
	// judgment handler
	public void handleJudgement() {
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM cases");

			while (resultSet.next()) 
			{
				String judgment = resultSet.getString("judgement");
				Timestamp caseDate = resultSet.getTimestamp("case_date");
				String SID = resultSet.getString("s_id");
				
				PreparedStatement p= con.prepareStatement("SELECT guilty_status from caseDetails Where s_id= ?");
				p.setString(1,SID);
				ResultSet r=p.executeQuery();
				String guilt = null;
				if(r.next()) 
				{
				    guilt=r.getString("guilty_status");
				}

				if(judgment.equals("Proven Guilty") && !guilt.equals("Proven Guilty")) 
				{
					PreparedStatement updateCaseDetailsStatement = con.prepareStatement("UPDATE caseDetails SET guilty_status = 'Proven Guilty' WHERE s_id = ?");
					updateCaseDetailsStatement.setString(1, SID);
					updateCaseDetailsStatement.executeUpdate();

					PreparedStatement selectCaseDetailsStatement = con.prepareStatement("SELECT fir_no, crime FROM caseDetails WHERE s_id = ?");
					selectCaseDetailsStatement.setString(1, SID);
					ResultSet caseDetailsResultSet = selectCaseDetailsStatement.executeQuery();
					if (caseDetailsResultSet.next()) 
					{
						String firNo = caseDetailsResultSet.getString("fir_no");
						String crime = caseDetailsResultSet.getString("crime");

						PreparedStatement checkPrisonerStatement = con.prepareStatement("SELECT * FROM prisoners WHERE s_id = ?");
						checkPrisonerStatement.setString(1, SID);
						ResultSet prisonerResultSet = checkPrisonerStatement.executeQuery();

						Date dateOfRelease = calculateReleaseDate(crime, caseDate);
						if (prisonerResultSet.next()) 
						{
							updatePrisonerReleaseDate(SID, dateOfRelease, caseDate);
						} 
						else 
						{
							insertIntoPrisoners(SID, firNo, caseDate, dateOfRelease);
						}
						
						PreparedStatement release = con.prepareStatement("DELETE FROM remand WHERE s_id = ?");
						release.setString(1, SID);
						release.executeUpdate();
						
						System.out.println("Prisoner Details Updated Successfully");
					}
				}
				else if(judgment.equals("Guiltless"))
				{
					PreparedStatement updateStatus = con.prepareStatement("UPDATE caseDetails SET guilty_status = 'Proven Guiltless' WHERE s_id = ?");
					updateStatus.setString(1, SID);
					updateStatus.executeUpdate();
					
					PreparedStatement release = con.prepareStatement("DELETE FROM remand WHERE s_id = ?");
					release.setString(1, SID);
					release.executeUpdate();
					
					// if he/she is already in prison
					PreparedStatement releasep = con.prepareStatement("DELETE FROM prisoners WHERE s_id = ?");
					releasep.setString(1, SID);
					releasep.executeUpdate();
				}
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error retrieving and processing judgements: " + e.getMessage());
		}
	}
	
	private Date calculateReleaseDate(String crime, Timestamp caseDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(caseDate.getTime());

		switch (crime) 
		{
			case "Murderer":
				calendar.add(Calendar.YEAR, 14);
				break;
			case "Rapist":
				calendar.add(Calendar.YEAR, 2);
				break;
			case "Fraud":
				calendar.add(Calendar.YEAR, 7);
				break;
			default:
				// more to be added
				break;
		}
		return new Date(calendar.getTimeInMillis());
	}

	private void updatePrisonerReleaseDate(String SID, Date dateOfRelease, Timestamp caseDate) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE prisoners SET date_of_release = ?, date_of_imprisonment = ? WHERE s_id = ?");
			preparedStatement.setDate(1, new java.sql.Date(dateOfRelease.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(caseDate.getTime()));
			preparedStatement.setString(3, SID);
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("Error updating prisoner release date: " + e.getMessage());
		}
	}

	private void insertIntoPrisoners(String SID, String firNo,Date dateOfImprisonment, Date dateOfRelease) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO prisoners (s_id,fir_no, date_of_imprisonment, date_of_release) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, SID);
			preparedStatement.setString(2, firNo);
			preparedStatement.setDate(3, new java.sql.Date(dateOfImprisonment.getTime()));
			preparedStatement.setDate(4, new java.sql.Date(dateOfRelease.getTime()));
			preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			System.out.println("Error inserting into prisoners table: " + e.getMessage());
		}
	}
	
	public void displayAllPrisoners() 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM prisoners");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				String pid = resultSet.getString("p_id");
				String sid = resultSet.getString("s_id");
				String firno=resultSet.getString("fir_no");
				Date i = resultSet.getDate("date_of_imprisonment");
				String r = resultSet.getString("date_of_release");

				// Display the details of the criminal
				System.out.println("Prisoner ID: " + pid);
				System.out.println("Suspect ID: " + sid);
				System.out.println("FIR No: " + firno);
				System.out.println("Date Of Improsinment: " + i);
				System.out.println("Date Of Release: " + r);
				System.out.println("-----------------------------------");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching suspect details: " + e.getMessage());
		}
	}
	
	public void displayPrisonersByID(String SID) 
	{
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement("SELECT p_id,date_of_imprisonment,date_of_release FROM prisoners Where s_id=?");
			preparedStatement.setString(1, SID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) 
			{
				String pid = resultSet.getString("p_id");
				Date i = resultSet.getDate("date_of_imprisonment");
				String r = resultSet.getString("date_of_release");

				// Display the details of the criminal
				System.out.println("Prisoner ID: " + pid);
				System.out.println("Date Of Improsinment: " + i);
				System.out.println("Date Of Release: " + r);
				System.out.println("-----------------------------------");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error fetching suspect details: " + e.getMessage());
		}
	}
}