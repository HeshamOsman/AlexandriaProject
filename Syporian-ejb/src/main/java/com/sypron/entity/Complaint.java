/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hisham
 */
@Entity
@Table(name = "tbl_complaint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Complaint.findAll", query = "SELECT c FROM Complaint c")
    , @NamedQuery(name = "Complaint.findById", query = "SELECT c FROM Complaint c WHERE c.id = :id")
    , @NamedQuery(name = "Complaint.findBySubject", query = "SELECT c FROM Complaint c WHERE c.subject = :subject")
    , @NamedQuery(name = "Complaint.findByAnonymous", query = "SELECT c FROM Complaint c WHERE c.anonymous = :anonymous")
    , @NamedQuery(name = "Complaint.findByComplaintIdentifier", query = "SELECT c FROM Complaint c WHERE c.complaintIdentifier = :complaintIdentifier")})
public class Complaint implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "subject")
    private String subject;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "complaint_definition")
    private String complaintDefinition;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anonymous")
    private boolean anonymous;
    @Lob
    @Size(max = 65535)
    @Column(name = "requested_resolution")
    private String requestedResolution;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "complaint_identifier")
    private String complaintIdentifier;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(mappedBy = "complaint")
    private List<Action> actionList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public Complaint() {
    }

    public Complaint(Integer id) {
        this.id = id;
    }

    public Complaint(Integer id, String subject, String complaintDefinition, boolean anonymous, String complaintIdentifier) {
        this.id = id;
        this.subject = subject;
        this.complaintDefinition = complaintDefinition;
        this.anonymous = anonymous;
        this.complaintIdentifier = complaintIdentifier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComplaintDefinition() {
        return complaintDefinition;
    }

    public void setComplaintDefinition(String complaintDefinition) {
        this.complaintDefinition = complaintDefinition;
    }

    public boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getRequestedResolution() {
        return requestedResolution;
    }

    public void setRequestedResolution(String requestedResolution) {
        this.requestedResolution = requestedResolution;
    }

    public String getComplaintIdentifier() {
        return complaintIdentifier;
    }

    public void setComplaintIdentifier(String complaintIdentifier) {
        this.complaintIdentifier = complaintIdentifier;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

    @XmlTransient
    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complaint)) {
            return false;
        }
        Complaint other = (Complaint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sypron.entity.Complaint[ id=" + id + " ]";
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
