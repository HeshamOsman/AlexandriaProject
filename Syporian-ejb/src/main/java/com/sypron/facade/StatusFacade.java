/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.Complaint;
import com.sypron.entity.Status;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author hisham
 */
@Stateless
public class StatusFacade extends AbstractFacade<Status> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusFacade() {
        super(Status.class);
    }
    
    public Status getStatusByName(String statusName){
             TypedQuery<Status> query = em.createQuery("SELECT s From Status s WHERE s.name = :sName ",
                     Status.class).setParameter("sName", statusName);
             return query.getSingleResult();
    }
    
}
