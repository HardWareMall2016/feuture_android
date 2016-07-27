package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class StaffListContent implements Serializable {
    private String name ;
    private int personId ;
    private String personLogo ;
    private int salary ;
    private String jobname ;
    private int jobid  ;

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }


    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }


    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPersonLogo() {
        return personLogo;
    }

    public void setPersonLogo(String personLogo) {
        this.personLogo = personLogo;
    }


    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
