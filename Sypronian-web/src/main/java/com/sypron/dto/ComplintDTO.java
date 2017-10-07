/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.dto;

import java.util.Date;

/**
 *
 * @author hisham
 */
public class ComplintDTO {
    private String subject;
    private String complaintIdentifier;
    private String ownerName;
    private String departmentName;
    private String requestedResolution;
    private String complaintDefinition;
    private Date date;

    public ComplintDTO(String subject, String complaintIdentifier, String ownerName, String departmentName, String requestedResolution, String complaintDefinition, Date date) {
        this.subject = subject;
        this.complaintIdentifier = complaintIdentifier;
        this.ownerName = ownerName;
        this.departmentName = departmentName;
        this.requestedResolution = requestedResolution;
        this.complaintDefinition = complaintDefinition;
        this.date = date;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRequestedResolution() {
        return requestedResolution;
    }

    public void setRequestedResolution(String requestedResolution) {
        this.requestedResolution = requestedResolution;
    }

    public String getComplaintDefinition() {
        return complaintDefinition;
    }

    public void setComplaintDefinition(String complaintDefinition) {
        this.complaintDefinition = complaintDefinition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

    
    
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComplaintIdentifier() {
        return complaintIdentifier;
    }

    public void setComplaintIdentifier(String complaintIdentifier) {
        this.complaintIdentifier = complaintIdentifier;
    }
    
    
}
