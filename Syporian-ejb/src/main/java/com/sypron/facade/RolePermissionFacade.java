/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.Department;
import com.sypron.entity.Permission;
import com.sypron.entity.Role;
import com.sypron.entity.RolePermission;
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
public class RolePermissionFacade extends AbstractFacade<RolePermission> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolePermissionFacade() {
        super(RolePermission.class);
    }
    
    public List<Permission> findAllPermissionForRole(Role role){
    
        try{
            TypedQuery<Permission> query =em.createQuery("SELECT rp.permission From RolePermission rp WHERE rp.role = :role",
                     Permission.class).setParameter("role", role);
            return  query.getResultList();
               
        }catch(NoResultException exception){
            return null;
        }
 
    }
    
}
