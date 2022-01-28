package com.android.internshipportal;

public class userHelper {

    String name, enrollment, department, mobile, email, password, confirmPassword;

    public userHelper() {
    }

    public userHelper(String name, String enrollment, String department, String mobile, String email, String password, String confirmPassword) {
        this.name = name;
        this.enrollment = enrollment;
        this.department = department;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

}
