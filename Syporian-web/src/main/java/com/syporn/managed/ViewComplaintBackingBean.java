/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.Action;
import com.sypron.entity.Complaint;
import com.sypron.entity.User;
import com.sypron.facade.ActionFacade;
import com.sypron.facade.ComplaintFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
public class ViewComplaintBackingBean {

    @Inject
    private ComplaintFacade complaintFacade;

    @Inject
    private ActionFacade actionFacade;

    @Inject
    private StatusFacade statusFacade;
    private UserDTO currentUserDTO;
    private Complaint currentComplaint;
    private Integer complaintIdParam;
    private List<Action> complaintActions;
    private Action newComplaintAction;
    private boolean renderAddAction;

    /**
     * Creates a new instance of viewComplaint
     */
    public ViewComplaintBackingBean() {

        newComplaintAction = new Action();
//        complaintActions = new ArrayList<>();
    }

    @PostConstruct
    public void onInit() {
        currentUserDTO = SessionUtils.getLoggedUser();
    }

    public Complaint getCurrentComplaint() {
        if (currentComplaint == null) {
            if (complaintIdParam == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
                String contextPath = origRequest.getContextPath();
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(contextPath + "/notAuthorized.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                currentComplaint = complaintFacade.find(complaintIdParam);
                if (currentComplaint == null) {
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
                
                if(currentUserDTO.getPermissionsMap().get("complaint", "list", "company") != null){
                    renderAddAction = true;
                    return currentComplaint;
                }
                else if (currentUserDTO.getPermissionsMap().get("complaint", "list", "department") != null) {
                    renderAddAction = true;
                    return currentComplaint;
                }  else {
                    return null;
                }
            }

        }
        return currentComplaint;
    }

    public void setCurrentComplaint(Complaint currentComplaint) {
        this.currentComplaint = currentComplaint;
    }

    public Integer getComplaintIdParam() {
        return complaintIdParam;
    }

    public void setComplaintIdParam(Integer complaintIdParam) {
        this.complaintIdParam = complaintIdParam;
    }

    public List<Action> getComplaintActions() {
        if (complaintActions == null) {
            complaintActions = actionFacade.getComplaintActions(complaintIdParam);
            System.err.println(">>>>>>>>>>>>List" + complaintActions.size());
        }
        return complaintActions;
    }

    public void setComplaintActions(List<Action> complaintActions) {
        this.complaintActions = complaintActions;
    }

    public Action getNewComplaintAction() {
        return newComplaintAction;
    }

    public void setNewComplaintAction(Action newComplaintAction) {
        this.newComplaintAction = newComplaintAction;
    }

    public boolean isRenderAddAction() {
        return renderAddAction;
    }

    public void setRenderAddAction(boolean renderAddAction) {
        this.renderAddAction = renderAddAction;
    }

    public void addAction() {
        newComplaintAction.setActionDate(new Date());
        newComplaintAction.setComplaint(currentComplaint);
        newComplaintAction.setCreateDate(new Date());
        newComplaintAction.setUser(new User(currentUserDTO.getId()));
        actionFacade.create(newComplaintAction);
        if (complaintActions == null) {
            complaintActions = new ArrayList<>();
        }
        complaintActions.add(newComplaintAction);
        newComplaintAction = new Action();
    }

}
