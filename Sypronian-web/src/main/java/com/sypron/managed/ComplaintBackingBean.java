/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.Complaint;
import com.sypron.entity.User;
import com.sypron.facade.ComplaintFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.util.PasswordEncoder;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
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
public class ComplaintBackingBean {

    //mention past complaint
    @Inject
    private ComplaintFacade complaintFacade;

    @Inject
    private StatusFacade statusFacade;
    private UserDTO currentUserDTO;
    private Complaint newComplaint;

    /**
     * Creates a new instance of ComplaintBackingBean
     */
    public ComplaintBackingBean() {
        currentUserDTO = SessionUtils.getLoggedUser();
        newComplaint = new Complaint();
    }

    public Complaint getNewComplaint() {
        return newComplaint;
    }

    public void setNewComplaint(Complaint newComplaint) {
        this.newComplaint = newComplaint;
    }

    public String addComplaint() {
        newComplaint.setStatus(statusFacade.getStatusByName("new"));
        newComplaint.setComplaintIdentifier("SYP" + System.nanoTime());
        newComplaint.setCreateDate(new Date());
        newComplaint.setUser(new User(currentUserDTO.getId()));
        complaintFacade.create(newComplaint);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/complaintsListing.xhtml?scope=user");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
