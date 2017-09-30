/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.Action;
import com.sypron.entity.Complaint;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author hisham
 */
@Stateless
public class ActionFacade extends AbstractFacade<Action> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActionFacade() {
        super(Action.class);
    }
    
    public List<Action> getComplaintActions(Integer complaintId){
  
        return em.createQuery("SELECT a From Action a WHERE a.complaint.id = :cId ",
                     Action.class).setParameter("cId", complaintId).getResultList();
    }
    
    public List<Action> getSuggestionActions(Integer suggestionId){
  
        return em.createQuery("SELECT a From Action a WHERE a.suggestion.id = :sId ",
                     Action.class).setParameter("sId", suggestionId).getResultList();
    }
    
}
