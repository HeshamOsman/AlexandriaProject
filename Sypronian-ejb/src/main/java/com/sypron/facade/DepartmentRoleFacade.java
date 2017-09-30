/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.facade;

import com.sypron.entity.Department;
import com.sypron.entity.DepartmentRole;
import com.sypron.entity.Role;
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
public class DepartmentRoleFacade extends AbstractFacade<DepartmentRole> {

    @PersistenceContext(unitName = "Syporian-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartmentRoleFacade() {
        super(DepartmentRole.class);
    }
    
      public List<Department> findAllDepartmentsWithRoleEmployer(){
    
        try{
            TypedQuery<Department> query =em.createQuery("SELECT dr.department From DepartmentRole dr WHERE dr.role.name = 'employer'",
                     Department.class);
            return  query.getResultList();
               
        }catch(NoResultException exception){
            return null;
        }
 
    }
     public List<Role> findAllRolesForDepartment(String departmentName){
    
        try{
            TypedQuery<Role> query =em.createQuery("SELECT dr.role From DepartmentRole dr WHERE dr.department.name = :dName ",
                     Role.class).setParameter("dName", departmentName);
            return  query.getResultList();
               
        }catch(NoResultException exception){
            return null;
        }
 
    }  
            
            
      public DepartmentRole findDepartmentRole(String departmentName,String roleName){
        try{
                return (DepartmentRole) em.createQuery("SELECT dr From DepartmentRole dr WHERE dr.department.name = :dName AND dr.role.name = :rName")
                .setParameter("dName", departmentName).setParameter("rName", roleName).getSingleResult();
        }catch(NoResultException exception){
            return null;
        }
 
    }  
    
}
