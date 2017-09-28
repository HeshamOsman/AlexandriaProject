/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.User;
import com.sypron.facade.RolePermissionFacade;
import com.sypron.facade.UserFacade;
import com.sypron.util.PasswordEncoder;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hisham
 */
@ManagedBean
@SessionScoped
public class LoginBackingBean implements Serializable{

    private static final long serialVersionUID = 1094801825228386363L;

    @Inject
    UserFacade userFacade;
    @Inject
    RolePermissionFacade rolePermissionFacade;
    private String password;
    private String generatedPassword;
    private String msg;
    private String email;
    private  User currentUser;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 

    //validate login
    public String validateUsernamePassword() {
        boolean valid = validateCredentials();
        if (valid) {
//            HttpSession session = SessionUtils.getSession();
//            session.setAttribute("userInfo", new UserDTO
//        (currentUser.getId(), currentUser.getEmail(), currentUser.getFirstName(), currentUser.getLastName()));
            SessionUtils.setLoggedUser(new UserDTO
        (currentUser.getId(), currentUser.getEmail(), currentUser.getFirstName(),
                currentUser.getLastName(),currentUser.getDepartmentRole().getDepartment().getId(),
                currentUser.getDepartmentRole().getRole().getId(),currentUser.getDepartmentRole().getId()
                ,currentUser.getActive()
                ,rolePermissionFacade.findAllPermissionForRole(currentUser.getDepartmentRole().getRole())));
            
//            UserDTO d= SessionUtils.getLoggedUser();
//            System.out.println(">><<<>>><<<>>"+d.getEmail());
             FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest)context.getExternalContext().getRequest();
    String contextPath = origRequest.getContextPath();
try {
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(contextPath  + "/homePage.xhtml");
    } catch (IOException e) {
        e.printStackTrace();
    }
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return null;
        }
    }
    
    private boolean validateCredentials (){
        
        generatedPassword = PasswordEncoder.encode(password);
        currentUser  = userFacade.findUserByEmailAndPassword(email, generatedPassword);
        
        return (currentUser != null);
            
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
          FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest)context.getExternalContext().getRequest();
    String contextPath = origRequest.getContextPath();
try {
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(contextPath  + "/loging.xhtml");
    } catch (IOException e) {
        e.printStackTrace();
    }
        
        return null;
    }
}
