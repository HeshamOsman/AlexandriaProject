/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.DepartmentRole;
import com.sypron.entity.User;
import com.sypron.facade.ComplaintFacade;
import com.sypron.facade.DepartmentRoleFacade;
import com.sypron.facade.SuggestionFacade;
import com.sypron.facade.UserFacade;
import com.sypron.util.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author hisham
 */
//@ManagedBean()
//@ViewScoped
@Named
@javax.faces.view.ViewScoped
public class HomeBackingBean implements Serializable {

    /**
     * Creates a new instance of LogingBackingBean
     */
    @Inject
    UserFacade userFacade;
    
    @Inject
    ComplaintFacade complaintFacade;
    
    @Inject
    SuggestionFacade suggestionFacade;
    
    @Inject
    DepartmentRoleFacade departmentRoleFacade;
    
    private BarChartModel complaintSuggestionModel;
    
//    private BarChartModel suggestionModel;
    
    private UserDTO currentUserDTO;
    
    private DepartmentRole currentUserDepartmentRole;
    
//    private String ss;
    
    public HomeBackingBean() {
    }

    @PostConstruct
    public void init() {
         currentUserDTO = SessionUtils.getLoggedUser();
         currentUserDepartmentRole = departmentRoleFacade.find(currentUserDTO.getUserDepartmentRoleId());
         createBarModels();
    }

    public BarChartModel getComplaintSuggestionModel() {
        return complaintSuggestionModel;
    }

    public void setComplaintSuggestionModel(BarChartModel complaintSuggestionModel) {
        this.complaintSuggestionModel = complaintSuggestionModel;
    }

 
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries complains = new ChartSeries();
        ChartSeries suggestions = new ChartSeries();
        complains.setLabel("Complains");
        for(Object[] obj:complaintFacade.getComplaintsStatsByRole(currentUserDTO.getId(), currentUserDepartmentRole)){
            String s = (Integer)obj[1]+":"+(Integer)obj[2];
            complains.set(s, (Long)obj[0]);
            suggestions.set(s, 0);
        }
     
        suggestions.setLabel("Suggestions");
        for(Object[] obj:suggestionFacade.getSuggestionsStatsByRole(currentUserDTO.getId(), currentUserDepartmentRole)){
            String s = (Integer)obj[1]+":"+(Integer)obj[2];
            suggestions.set(s, (Long)obj[0]);
            if(complains.getData().get(s)==null){
                 complains.set(s, 0);
            }
        }
        if(complains.getData().isEmpty()){
               complains.set("No Data", 0);
        }
        if(suggestions.getData().isEmpty()){
               suggestions.set("No Data", 0);
        }
//        boys.set("2005", 100);
//        boys.set("2006", 44);
//        boys.set("2007", 150);
//        boys.set("2008", 25);
// 
//        ChartSeries girls = new ChartSeries();
//        girls.setLabel("Girls");
//        girls.set("2004", 52);
//        girls.set("2005", 60);
//        girls.set("2006", 110);
//        girls.set("2007", 135);
//        girls.set("2008", 120);
 
        model.addSeries(complains);
        model.addSeries(suggestions);
//        model.addSeries(girls);
         
        return model;
    }
     
    private void createBarModels() {
        createBarModel();
    }
     
    private void createBarModel() {
        complaintSuggestionModel = initBarModel();
         
        complaintSuggestionModel.setTitle("Complain Suggetions");
        complaintSuggestionModel.setLegendPosition("ne");
         
        Axis xAxis = complaintSuggestionModel.getAxis(AxisType.X);
        if(currentUserDepartmentRole.getRole().getName().equals("employer")){
          xAxis.setLabel("Submitted Complaints Suggestions");  
        }else{
            xAxis.setLabel("Recevied Complaints Suggestions");
        }
        
         
        Axis yAxis = complaintSuggestionModel.getAxis(AxisType.Y);
        yAxis.setLabel("Number");
        yAxis.setTickInterval("1");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
     
  
    
    
}
