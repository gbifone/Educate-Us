package com.example.timothy.educate_us;

/**
 * Created by Timothy on 2/12/2017.
 */

public class Course {
    private String courseName;
    private String teacherName;
    private String URL;
    private String reading;
    private String assignment;


    public Course()
    {

    }

    public Course(String name, String teacher, String inputURL, String inputReading, String inputAssignment)
    {
        courseName = name;
        teacherName = teacher;
        URL = inputURL;
        reading = inputReading;
        assignment = inputAssignment;
    }

    public String getCourseName()
    {
        return courseName;
    }
    public void setCourseName(String name)
    {
        courseName = name;
    }
    public String getTeacherName()
    {
        return teacherName;
    }
    public void setTeacherName(String name)
    {
        teacherName = name;
    }
    public String getURL()
    {
        return URL;
    }
    public void setURL(String name)
    {
        URL = name;
    }
    public String getReading()
    {
        return reading;
    }
    public void setReading(String name)
    {
        reading = name;
    }
    public String getAssignment()
    {
        return assignment;
    }
    public void setAssignment(String name)
    {
        assignment = name;
    }


}
