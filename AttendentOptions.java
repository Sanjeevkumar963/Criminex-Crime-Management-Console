package com.java.criminalDatabase;
import java.sql.*;
import java.util.*;
public class AttendentOptions 
{
	AdminOptions a=new AdminOptions();
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
		};
	}
	public void displayOptions(Scanner sc) 
	{
		while (true) 
		{
			System.out.println("Attendent Options:");
			System.out.println("1. View Details of Prisoner");
			System.out.println("2. Reappeal");
			System.out.println("3. Exit");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();
			sc.nextLine(); 

			switch (choice) 
			{
			case 1:
		        System.out.println("Enter Suspect ID:");
		        String id=sc.next();
		        a.get_connection();
		        a.specificSuspect(id);
		        a.displayCaseDetailsById(id);
		        a.displayPrisonersByID(id);
				break;
			case 2:
				System.out.println("Do You Wanna Reappeal ?");
		        System.out.println("1. Yes\n2. No");
		        int n=sc.nextInt();
		        sc.nextLine();
		        if(n==1)
		        {
			        a.get_connection();
		        	System.out.println("Enter Case No:");
					String id1=sc.nextLine();
					System.out.println("Enter Reappeal:");
					String s=sc.nextLine();
					a.updateJudgmentByCaseNumber(id1,s);
					System.out.println("Enter Case No:");
					String id11=sc.nextLine();
					System.out.println("Enter Case Date to Be Updated:");
					String s1=sc.nextLine();
					a.updateCaseDateByCaseNumber(id11,s1);
		        }
		        else
		        	return;
			default:
//				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}
}