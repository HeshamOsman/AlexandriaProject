/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.entity.Department;
import com.sypron.entity.DepartmentRole;
import com.sypron.entity.User;
import com.sypron.facade.DepartmentRoleFacade;
import com.sypron.facade.UserFacade;
import com.sypron.util.PasswordEncoder;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author hisham
 */
@Named
@RequestScoped
public class RegistrationBackingBean implements Serializable {

    /**
     * Creates a new instance of RegistrationBackingBean
     */
    private User user;
    private String password;
    private String retypedPassword;
    private String selectedDepartmentName;
    private List<Department> departments;
    
    @Inject
    UserFacade userFacade;
    @Inject
    DepartmentRoleFacade departmentRoleFacade;
    
    public RegistrationBackingBean() {
       
           user= new User();
 
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypedPassword() {
        return retypedPassword;
    }

    public void setRetypedPassword(String retypedPassword) {
        this.retypedPassword = retypedPassword;
    }

    public String getSelectedDepartmentName() {
        return selectedDepartmentName;
    }

    public void setSelectedDepartmentName(String selectedDepartmentName) {
        this.selectedDepartmentName = selectedDepartmentName;
    }

    public List<Department> getDepartments() {
        if(departments == null){
                   departments = departmentRoleFacade.findAllDepartmentsWithRoleEmployer();
        }
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    
    
    public String addUser(){
        if(password == null || password.isEmpty()){
             FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Passowrd Requierd",null));
            return null;
        }
        
        if(!password.equals(retypedPassword)){
             FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            " Passowrd Mismatched",
                            null));
            return null;
        }
        
        if(selectedDepartmentName == null||selectedDepartmentName.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Department Requierd",
                            null));
            return null;
        }
        
        if(userFacade.isEmailExist(user.getEmail())>0){
 
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Email Exist",
                            null));
            return null;
        }
      
        user.setDepartmentRole(departmentRoleFacade.findDepartmentRole(selectedDepartmentName,"employer"));
        user.setPassword(PasswordEncoder.encode(password));
        
        userFacade.create(user);
        return "loging";
    }
    
    
    
    
}
