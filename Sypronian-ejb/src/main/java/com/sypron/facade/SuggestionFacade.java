/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.Suggestion;
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
public class SuggestionFacade extends AbstractFacade<Suggestion> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SuggestionFacade() {
        super(Suggestion.class);
    }
    
      public List<Suggestion> getSuggestionsByScope(String scope,Integer currentUserId,Integer departmentId){
        TypedQuery<Suggestion> query = null;
        switch(scope){
            case "user":
                query = em.createQuery("SELECT s From Suggestion s WHERE s.user.id = :userId ",
                     Suggestion.class).setParameter("userId", currentUserId);
                break;
            case "department":

                query = em.createQuery("SELECT s From Suggestion s WHERE s.user.departmentRole.department.id = :dId ",
                     Suggestion.class).setParameter("dId", departmentId);
                break;    
            case "company":
                query = em.createQuery("SELECT s From Suggestion s ",
                     Suggestion.class);
                break;        
        }
        if(query == null)
            return null;
        else  
            return query.getResultList();
    }
    
}
