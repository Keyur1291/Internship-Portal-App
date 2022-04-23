package com.android.internshipportal;

public class recycle_getter_setter {

    String id, name, enrollment, department, mobile, email, Cname, subject, Caddress, Cmobile, Cemail;

    public recycle_getter_setter() {
    }

    public recycle_getter_setter(String id, String name, String enrollment, String department, String mobile, String email, String Cname, String subject, String Caddress, String Cmobile, String Cemail) {
        this.id = id;
        this.name = name;
        this.enrollment = enrollment;
        this.department = department;
        this.mobile = mobile;
        this.email = email;
        this.Cname = Cname;
        this.subject = subject;
        this.Caddress = Caddress;
        this.Cmobile = Cmobile;
        this.Cemail = Cemail;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCaddress() {
        return Caddress;
    }

    public void setCaddress(String caddress) {
        Caddress = caddress;
    }

    public String getCmobile() {
        return Cmobile;
    }

    public void setCmobile(String cmobile) {
        Cmobile = cmobile;
    }

    public String getCemail() {
        return Cemail;
    }

    public void setCemail(String cemail) {
        Cemail = cemail;
    }
}
