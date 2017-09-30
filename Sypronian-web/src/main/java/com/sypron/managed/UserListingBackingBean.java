/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.entity.User;
import com.sypron.facade.UserFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author hisham
 */
@ManagedBean()
@ViewScoped
public class UserListingBackingBean implements Serializable{

    /**
     * Creates a new instance of UserListingBackingBean
     */
    @Inject
    UserFacade userFacade;
    private List<User> users;
    private User selectedUser;
    public UserListingBackingBean() {
    }

    public List<User> getUsers() {
        if(users == null){
            users = userFacade.findAll();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public void onRowSelected(SelectEvent event){
        User userFromEvent = (User) event.getObject();
         FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/viewUser.xhtml?userId="+userFromEvent.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
