/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hisham
 */
@Entity
@Table(name = "tbl_suggestion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suggestion.findAll", query = "SELECT s FROM Suggestion s")
    , @NamedQuery(name = "Suggestion.findById", query = "SELECT s FROM Suggestion s WHERE s.id = :id")
    , @NamedQuery(name = "Suggestion.findBySubject", query = "SELECT s FROM Suggestion s WHERE s.subject = :subject")
    , @NamedQuery(name = "Suggestion.findByStatus", query = "SELECT s FROM Suggestion s WHERE s.status = :status")
    , @NamedQuery(name = "Suggestion.findByAnonymous", query = "SELECT s FROM Suggestion s WHERE s.anonymous = :anonymous")
    , @NamedQuery(name = "Suggestion.findBySuggestionIdentifier", query = "SELECT s FROM Suggestion s WHERE s.suggestionIdentifier = :suggestionIdentifier")
    , @NamedQuery(name = "Suggestion.findByCreateDate", query = "SELECT s FROM Suggestion s WHERE s.createDate = :createDate")})
public class Suggestion implements Serializable {

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
    @Column(name = "suggestion_definition")
    private String suggestionDefinition;
    @Lob
    @Size(max = 65535)
    @Column(name = "suggestion_impact")
    private String suggestionImpact;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anonymous")
    private boolean anonymous;
   
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "suggestion_identifier")
    private String suggestionIdentifier;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    public Suggestion() {
    }

    public Suggestion(Integer id) {
        this.id = id;
    }

    public Suggestion(Integer id, String subject, String suggestionDefinition, Status status,
            boolean anonymous, String suggestionIdentifier, Date createDate,User user,String suggestionImpact) {
        this.id = id;
        this.subject = subject;
        this.suggestionDefinition = suggestionDefinition;
        this.status = status;
        this.anonymous = anonymous;
        this.suggestionIdentifier = suggestionIdentifier;
        this.createDate = createDate;
        this.suggestionImpact = suggestionImpact;
        this.user = user;
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

    public String getSuggestionDefinition() {
        return suggestionDefinition;
    }

    public void setSuggestionDefinition(String suggestionDefinition) {
        this.suggestionDefinition = suggestionDefinition;
    }

    public String getSuggestionImpact() {
        return suggestionImpact;
    }

    public void setSuggestionImpact(String suggestionImpact) {
        this.suggestionImpact = suggestionImpact;
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

   

    public boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getSuggestionIdentifier() {
        return suggestionIdentifier;
    }

    public void setSuggestionIdentifier(String suggestionIdentifier) {
        this.suggestionIdentifier = suggestionIdentifier;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if (!(object instanceof Suggestion)) {
            return false;
        }
        Suggestion other = (Suggestion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sypron.entity.Suggestion[ id=" + id + " ]";
    }
    
}
