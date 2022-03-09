package com.android.internshipportal;

public class user_list {

    String name, enrollment, department;

    public user_list(){}

    public user_list(String name, String enrollment, String department) {
        this.name = name;
        this.enrollment = enrollment;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
