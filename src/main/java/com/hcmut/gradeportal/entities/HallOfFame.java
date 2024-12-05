package com.hcmut.gradeportal.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class HallOfFame {
    
    private String familyName;
    private String givenName;
    private String email;
    //private String courseCode;
    //private String semesterCode;
    private String className;
    private List<Double> BT;
    private List<Double> TN;
    private List<Double> BTL;
    private List<Double> GK;
    private List<Double> CK;
    private Double finalMark;
    public HallOfFame(Student student, List<Double> bt,List<Double> tn,List<Double> btl,List<Double> gk,List<Double> ck,Double finalmark){
        this.familyName = student.getFamilyName();
        this.givenName = student.getGivenName();
        this.email = student.getEmail();
        //this.courseCode = courseCode;
        //this.semesterCode = semesterCode;
        this.BT = bt;
        this.TN = tn;
        this.BTL = btl;
        this.GK = gk;
        this.CK = ck;
        
        this.finalMark=finalmark;
    }
    
}
