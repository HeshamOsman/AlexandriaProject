/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.Department;
import com.sypron.entity.Role;
import com.sypron.entity.User;
import com.sypron.facade.DepartmentFacade;
import com.sypron.facade.DepartmentRoleFacade;
import com.sypron.facade.UserFacade;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hisham
 */
@ManagedBean
@ViewScoped
public class ViewUserBackingBean {
    @Inject
    private UserFacade userFacade;
    private Integer userIdParam;
    private User currentUser;
    private String userDepartmentName;
    private String userRoleName;
    private boolean ownerEdit;
    private boolean adminEdit;
    
    private List<Department> departments;
    private List<Role> departmentRoles;

    private UserDTO currentLogeddUser;
    @Inject
    DepartmentRoleFacade departmentRoleFacade;
    @Inject
    DepartmentFacade departmentFacade;
    /**
     * Creates a new instance of ViewUser
     */
    public ViewUserBackingBean() {
    }
    @PostConstruct
    public void onInit(){
        currentLogeddUser = SessionUtils.getLoggedUser();
    }

    public Integer getUserIdParam() {
        return userIdParam;
    }

    public void setUserIdParam(Integer userIdParam) {
        this.userIdParam = userIdParam;
    }

    public User getCurrentUser() {
        if(currentUser == null){
           if(userIdParam == null){
               FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/notAuthorized.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
           } 
           currentUser= userFacade.find(userIdParam);
           if(currentUser == null){
                      FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/notAuthorized.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
           }
        }
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
      public List<Department> getDepartments() {
        if(departments == null){
            departments = departmentFacade.findAll();
        }
        
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Role> getDepartmentRoles() {
        if(departmentRoles==null){
            departmentRoles = departmentRoleFacade.findAllRolesForDepartment(getUserDepartmentName());
        }
        return departmentRoles;
    }

    public void setDepartmentRoles(List<Role> departmentRoles) {
        this.departmentRoles = departmentRoles;
    }

    public String getUserRoleName() {
        if(userRoleName==null){
            userRoleName = getCurrentUser().getDepartmentRole().getRole().getName();
        }
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getUserDepartmentName() {
        
        if(userDepartmentName==null){
            userDepartmentName = getCurrentUser().getDepartmentRole().getDepartment().getName();
        }
        return userDepartmentName;
    }

    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }

    public boolean isOwnerEdit() {
        if(currentLogeddUser.getId().equals(userIdParam)){
            ownerEdit = true;
        }
        return ownerEdit;
    }

    public void setOwnerEdit(boolean ownerEdit) {
        this.ownerEdit = ownerEdit;
    }

    public boolean isAdminEdit() {
        if(!currentLogeddUser.getId().equals(userIdParam)){
            adminEdit = true;
        }
        return adminEdit;
    }

    public void setAdminEdit(boolean adminEdit) {
        this.adminEdit = adminEdit;
    }
    
    

    public void onChangeDepartment() {
        departmentRoles = departmentRoleFacade.findAllRolesForDepartment(userDepartmentName);
    }
    
    
    
}
