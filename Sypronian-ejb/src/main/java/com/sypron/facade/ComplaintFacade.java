/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.Complaint;
import com.sypron.entity.Permission;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author hisham
 */
@Stateless
public class ComplaintFacade extends AbstractFacade<Complaint> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplaintFacade() {
        super(Complaint.class);
    }
    
    public List<Complaint> getComplaintsByScope(String scope,Integer currentUserId,Integer departmentId){
        TypedQuery<Complaint> query = null;
        switch(scope){
            case "user":
                query = em.createQuery("SELECT c From Complaint c WHERE c.user.id = :userId ",
                     Complaint.class).setParameter("userId", currentUserId);
                break;
            case "department":

                query = em.createQuery("SELECT c From Complaint c WHERE c.user.departmentRole.department.id = :dId ",
                     Complaint.class).setParameter("dId", departmentId);
                break;    
            case "company":
                query = em.createQuery("SELECT c From Complaint c ",
                     Complaint.class);
                break;        
        }
        if(query == null)
            return null;
        else  
            return query.getResultList();
    }
    
}
