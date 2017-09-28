/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.Complaint;
import com.sypron.entity.Suggestion;
import com.sypron.entity.User;
import com.sypron.facade.ComplaintFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.facade.SuggestionFacade;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.util.Date;
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
public class SuggestionBackingBean {

    @Inject
    private SuggestionFacade suggestionFacade;

    @Inject
    private StatusFacade statusFacade;
    private UserDTO currentUserDTO;
    private Suggestion newSuggestion;

    /**
     * Creates a new instance of ComplaintBackingBean
     */
    public SuggestionBackingBean() {
        currentUserDTO = SessionUtils.getLoggedUser();
        newSuggestion = new Suggestion();
    }

    public Suggestion getNewSuggestion() {
        return newSuggestion;
    }

    public void setNewSuggestion(Suggestion newSuggestion) {
        this.newSuggestion = newSuggestion;
    }
    

    

    public String addSuggestion() {
        newSuggestion.setStatus(statusFacade.getStatusByName("new"));
        newSuggestion.setSuggestionDefinition("SYP" + System.nanoTime());
        newSuggestion.setCreateDate(new Date());
        newSuggestion.setUser(new User(currentUserDTO.getId()));
        suggestionFacade.create(newSuggestion);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/suggestionsListing.xhtml?scope=user");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
