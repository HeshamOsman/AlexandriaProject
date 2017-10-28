/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.ComplintDTO;
import com.sypron.dto.UserDTO;
import com.sypron.entity.Action;
import com.sypron.entity.Status;
import com.sypron.entity.Suggestion;
import com.sypron.entity.User;
import com.sypron.facade.ActionFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.facade.SuggestionFacade;
import com.sypron.util.SessionUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.primefaces.context.RequestContext;

/**
 *
 * @author hisham
 */
//@ManagedBean
//@ViewScoped
@Named
@javax.faces.view.ViewScoped
public class ViewSuggestion implements Serializable {
    @Inject
    private SuggestionFacade suggestionFacade;
    @Inject
    private ActionFacade actionFacade;

    @Inject
    private StatusFacade statusFacade;
  
    private List<Status> statusList;
    private UserDTO currentUserDTO;
    private Suggestion currentSuggestion;
    private Integer suggestionIdParam;
    private List<Action> suggestionActions;
    private Action newSuggestionAction;
    private boolean renderAddAction;
    private boolean statusChangeEnabled;
     private String statusString;
    /**
     * Creates a new instance of ViewSuggestion
     */
    public ViewSuggestion() {
        newSuggestionAction = new Action();
    }
    
     @PostConstruct
    public void onInit() {
        currentUserDTO = SessionUtils.getLoggedUser();
          if (statusList == null) {
            statusList = statusFacade.findAll();
            if (currentUserDTO.getPermissionsMap().get("suggestion", "list", "company") != null
                    || currentUserDTO.getPermissionsMap().get("suggestion", "list", "department") != null) {
                statusList.remove(statusFacade.getStatusByName("new"));
            }

        }
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
                    if(currentSuggestion.getStatus().getName().equals("in_progress")){
                    renderAddAction = true;    
                    }
                    statusChangeEnabled = true;
                    return currentSuggestion;
                }
                else if (currentUserDTO.getPermissionsMap().get("suggestion", "list", "department") != null
                        &&currentUserDTO.getUserDepartmentId().equals(currentSuggestion.getUser().getDepartmentRole().getDepartment().getId())) {
                    if(currentSuggestion.getStatus().getName().equals("in_progress")){
                    renderAddAction = true;    
                    }
                    statusChangeEnabled = true;
                    return currentSuggestion;
                }  else if(currentUserDTO.getId().equals(currentSuggestion.getUser().getId())) {
                    return currentSuggestion;
                }else{
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

        }
        return currentSuggestion;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public String getStatusString() {
           if(statusString == null){
                statusString = getCurrentSuggestion().getStatus().getName();
        }
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public boolean isStatusChangeEnabled() {
        return statusChangeEnabled;
    }

    public void setStatusChangeEnabled(boolean statusChangeEnabled) {
        this.statusChangeEnabled = statusChangeEnabled;
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').hide();");
    }
    
    
      public void onChangeStatus(){
        currentSuggestion.setStatus(statusFacade.getStatusByName(statusString));
        suggestionFacade.edit(currentSuggestion);
        if(statusString.equals("in_progress"))
            renderAddAction = true;
        else
            renderAddAction = false;
        
    }
      
      
      public void pDF(){

  try {
ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
 String reportPath;
            reportPath = servletContext.getRealPath("/resources/reports/complaintReport.jasper");
       FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse httpServletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
         
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
 httpServletResponse.setContentType("application/pdf");

     httpServletResponse.setHeader("Content-disposition", "inline; filename=nn.pdf");

        JRPdfExporter exporter = new JRPdfExporter();
       
HashMap hashMap = new HashMap();
hashMap.put("pDir", servletContext.getRealPath("/resources/images/"));
hashMap.put("pLogo", "sypron_logo.png");

        List<ComplintDTO> l = new ArrayList<>();
      l.add(new ComplintDTO(currentSuggestion.getSubject(), currentSuggestion.getSuggestionIdentifier(),
      currentSuggestion.getUser().getFirstName()+" "+currentSuggestion.getUser().getLastName(),
      currentSuggestion.getUser().getDepartmentRole().getDepartment().getName(),currentSuggestion.getSuggestionImpact(),currentSuggestion.getSuggestionDefinition(),
      currentSuggestion.getCreateDate()));
//            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,hashMap, new JRBeanCollectionDataSource(l));
             exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
            exporter.exportReport();
            servletOutputStream.flush();
    servletOutputStream.close();
           facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in generate Report..."+e);
        } finally {
        }



    }
    
}
