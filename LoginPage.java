package com.java.criminalDatabase;
import java.util.Scanner;
public class LoginPage 
{
    public static void main(String[] args) 
    {
        AuthenticationSystem authSystem=new AuthenticationSystem();
        authSystem.get_connection();
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to the Criminal Database");
        while(true) 
        {
            System.out.println("1. Sign In\n2. Sign Up\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            switch (choice) 
            {
                case 1:
                    signIn(authSystem, sc);
                    break;
                case 2:
                    signUp(authSystem, sc);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Sign 2-Ways
    private static void signIn(AuthenticationSystem authSystem,Scanner sc) 
    {
        System.out.print("Enter Email: ");
        String email=sc.next();
        System.out.print("Enter Password: ");
        String password=sc.next();
        System.out.print("Enter your role: ");
        String role=sc.next();
        if(role.equals("attendent")) 
        {
        	String res=authSystem.authenticateAttendent(email,password);
        	if(res.equals("attendent"))
        	{
        		System.out.println("Welcome Attendent");
        		AttendentOptions a=new AttendentOptions();
        		a.get_connection();
        		a.displayOptions(sc);
        	}
        	else
        	{
        		System.out.println(res);
        	}
        } 
        else if (role.equals("admin")) 
        {
            String res = authSystem.authenticateAdmin(email, password);
            if (res.equals("admin")) 
            {
                System.out.println("Welcome Admin");
                AdminOptions a=new AdminOptions();
                a.get_connection();
                a.displayOptions(sc);
            } 
            else 
            {
                System.out.println(res);
            }
        }
        else 
        {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
    
    // Sign-up 2-Ways
    private static void signUp(AuthenticationSystem authSystem,Scanner sc) 
    {
        System.out.println("Choose Role: ");
        System.out.println("1. Attendent\n2. Admin");
        int roleChoice=sc.nextInt();
        System.out.print("Enter Email: ");
        String email=sc.next();
        System.out.print("Enter Password: ");
        String password=sc.next();
        System.out.print("Enter aadhar number : ");
        String aadhar=sc.next();
        System.out.print("Enter Phone Number: ");
        String ph=sc.next();
        if (roleChoice==1) 
        {
            authSystem.addAttendent(email,password,aadhar,ph);
        } 
        else if (roleChoice==2)
        {
            authSystem.addAdmin(email,password,aadhar,ph);
        } 
        else 
        {
            System.out.println("Invalid role choice.");
        }
    }
}