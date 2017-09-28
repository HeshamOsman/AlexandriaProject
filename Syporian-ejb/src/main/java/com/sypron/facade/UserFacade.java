/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author hisham
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User findUserByEmailAndPassword(String email,String hashedPassword){
        try{
                return (User)em.createQuery("SELECT u From User u WHERE u.email = :email AND u.password = :hashedPassword")
                .setParameter("email", email).setParameter("hashedPassword", hashedPassword).getSingleResult();
        }catch(NoResultException exception){
            return null;
        }
 
    }
    
     public Long isEmailExist(String email){
        try{
                return (Long)em.createQuery("SELECT COUNT(u)  From User u WHERE u.email = :email ")
                .setParameter("email", email).getSingleResult();
        }catch(NoResultException exception){
            return null;
        }
 
    }
    
}
