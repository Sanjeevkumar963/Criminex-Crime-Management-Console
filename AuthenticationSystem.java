package com.java.criminalDatabase;
import java.sql.*;
class AuthenticationSystem 
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
	
	
	// Authenticate Attendent
    public String authenticateAttendent(String email, String password) 
    {
        try 
        {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM attendent WHERE email = ? AND password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) 
            {
                return "attendent";
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Error authenticating attendent: " + e.getMessage());
        }
        return "attendent not found";
    }
    
    // Authenticate Admin
    public String authenticateAdmin(String email, String password) 
    {
        try 
        {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM admin WHERE email = ? AND password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) 
            {
                return "admin";
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Error authenticating admin: " + e.getMessage());
        }
        return "user not found";
    }
    
    // Add Attendent
    public void addAttendent(String email, String password,String aadhar,String phone) 
    {
        try 
        {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO attendent (email, password,aadhar_no,Phone_number) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, aadhar);
            preparedStatement.setString(4, phone);            
            preparedStatement.executeUpdate();
            System.out.println("Attendent added successfully!");
            System.out.println("Attendent data inserted into the database!");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error inserting Attendent data: " + e.getMessage());
        }
    }
    
    // Add Admin
    public void addAdmin(String email, String password,String aadhar,String phone) 
    {
        try 
        {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO admin (email, password,aadhar_no,Phone_number) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, aadhar);
            preparedStatement.setString(4, phone); 
            preparedStatement.executeUpdate();
            System.out.println("Admin added successfully!");
            System.out.println("Admin data inserted into the database!");
        } 
        catch (SQLException e) 
        {
            System.out.println("Error inserting admin data: " + e.getMessage());
        }
    }
}