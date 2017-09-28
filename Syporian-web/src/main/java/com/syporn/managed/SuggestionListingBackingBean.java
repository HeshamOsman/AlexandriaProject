/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.Complaint;
import com.sypron.entity.Suggestion;
import com.sypron.facade.ComplaintFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.facade.SuggestionFacade;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
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
@ManagedBean
@ViewScoped
public class SuggestionListingBackingBean {
    
    
    
    @Inject
    private SuggestionFacade suggestionFacade;
    @Inject
    private StatusFacade statusFacade;
//    @ManagedProperty("#{param.scope}")
    private String scope;
    private List<Suggestion> suggestions;
    private Suggestion selectedSuggestion;
    private UserDTO currentUserDTO;
     /**
     * Creates a new instance of SuggestionListingBackingBean
     */
    public SuggestionListingBackingBean() {
    }
    @PostConstruct
    public void onInit(){

        currentUserDTO = SessionUtils.getLoggedUser();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<Suggestion> getSuggestions() {
        if(suggestions == null){
            if(currentUserDTO.getPermissionsMap()
                    .get("suggestion", "list",scope)==null){
                FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/notAuthorized.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
            }else{
                suggestions = suggestionFacade.getSuggestionsByScope(scope,
                        currentUserDTO.getId(), currentUserDTO.getUserDepartmentId());
            }
        }
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public Suggestion getSelectedSuggestion() {
        return selectedSuggestion;
    }

    public void setSelectedSuggestion(Suggestion selectedSuggestion) {
        this.selectedSuggestion = selectedSuggestion;
    }
    
    public void onRowSelected(SelectEvent event) {
        Suggestion suggestionFromEvent = (Suggestion) event.getObject();
        if((currentUserDTO.getPermissionsMap()
                .get("suggestion", "list", "department")!=null || currentUserDTO.getPermissionsMap()
                .get("suggestion", "list", "company")!=null)&&suggestionFromEvent.getStatus().getName().equals("new")){
            suggestionFromEvent.setStatus(statusFacade.getStatusByName("viewed"));
            suggestionFacade.edit(suggestionFromEvent);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/viewSuggestion.xhtml?suggestionId="+suggestionFromEvent.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    
    
    
}
