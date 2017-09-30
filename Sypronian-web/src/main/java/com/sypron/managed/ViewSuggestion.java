/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.Action;
import com.sypron.entity.Suggestion;
import com.sypron.entity.User;
import com.sypron.facade.ActionFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.facade.SuggestionFacade;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
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
public class ViewSuggestion {
    @Inject
    private SuggestionFacade suggestionFacade;
    @Inject
    private ActionFacade actionFacade;

    @Inject
    private StatusFacade statusFacade;
    private UserDTO currentUserDTO;
    private Suggestion currentSuggestion;
    private Integer suggestionIdParam;
    private List<Action> suggestionActions;
    private Action newSuggestionAction;
    private boolean renderAddAction;
    /**
     * Creates a new instance of ViewSuggestion
     */
    public ViewSuggestion() {
    }
    
     @PostConstruct
    public void onInit() {
        currentUserDTO = SessionUtils.getLoggedUser();
    }

    public Suggestion getCurrentSuggestion() {
        if (currentSuggestion == null) {
            if (suggestionIdParam == null) {
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
                currentSuggestion = suggestionFacade.find(suggestionIdParam);
                if (currentSuggestion == null) {
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
                
                if(currentUserDTO.getPermissionsMap().get("suggestion", "list", "company") != null){
                    renderAddAction = true;
                    return currentSuggestion;
                }
                else if (currentUserDTO.getPermissionsMap().get("suggestion", "list", "department") != null) {
                    renderAddAction = true;
                    return currentSuggestion;
                }
            }

        }
        return currentSuggestion;
    }

    public void setCurrentSuggestion(Suggestion currentSuggestion) {
        this.currentSuggestion = currentSuggestion;
    }

    public Integer getSuggestionIdParam() {
        return suggestionIdParam;
    }

    public void setSuggestionIdParam(Integer suggestionIdParam) {
        this.suggestionIdParam = suggestionIdParam;
    }

  

    public List<Action> getSuggestionActions() {
        if (suggestionActions == null) {
            suggestionActions = actionFacade.getSuggestionActions(suggestionIdParam);
       
        }
        return suggestionActions;
    }

    public void setSuggestionActions(List<Action> suggestionActions) {
        this.suggestionActions = suggestionActions;
    }

    public Action getNewSuggestionAction() {
        return newSuggestionAction;
    }

    public void setNewSuggestionAction(Action newSuggestionAction) {
        this.newSuggestionAction = newSuggestionAction;
    }

    public boolean isRenderAddAction() {
        return renderAddAction;
    }

    public void setRenderAddAction(boolean renderAddAction) {
        this.renderAddAction = renderAddAction;
    }

    public void addAction() {
        newSuggestionAction.setActionDate(new Date());
        newSuggestionAction.setSuggestion(currentSuggestion);
        newSuggestionAction.setCreateDate(new Date());
        newSuggestionAction.setUser(new User(currentUserDTO.getId()));
        actionFacade.create(newSuggestionAction);
        if (suggestionActions == null) {
            suggestionActions = new ArrayList<>();
        }
        suggestionActions.add(newSuggestionAction);
        newSuggestionAction = new Action();
    }
    
    
}
