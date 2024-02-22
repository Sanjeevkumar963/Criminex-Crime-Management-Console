package com.java.criminalDatabase;
public class Suspect 
{
    private String name;
    private int age;
    private String gender;
    private String s_id;

    public Suspect(String name, int age, String gender, String s_id) 
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.s_id = s_id;
    }

    public Suspect() 
    {
		// a default constructor to create refrence object in other classes
	}

	public String getName() 
	{
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getAge() 
    {
        return age;
    }

    public void setAge(int age) 
    {
        this.age = age;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getS_id() 
    {
        return s_id;
    }

    public void setC_id(String s_id) 
    {
        this.s_id = s_id;
    }
}
