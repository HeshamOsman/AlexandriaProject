/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.ComplintDTO;
import com.sypron.dto.UserDTO;
import com.sypron.entity.Action;
import com.sypron.entity.Complaint;
import com.sypron.entity.Status;
import com.sypron.entity.User;
import com.sypron.facade.ActionFacade;
import com.sypron.facade.ComplaintFacade;
import com.sypron.facade.StatusFacade;
import com.sypron.util.SessionUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import org.primefaces.context.RequestContext;

/**
 *
 * @author hisham
 */
//@ManagedBean
//@ViewScoped
@Named
@javax.faces.view.ViewScoped
public class ViewComplaintBackingBean implements Serializable {

    @Inject
    private ComplaintFacade complaintFacade;

    @Inject
    private ActionFacade actionFacade;

    @Inject
    private StatusFacade statusFacade;
    private List<Status> statusList;

    private UserDTO currentUserDTO;
    private Complaint currentComplaint;
    private Integer complaintIdParam;
    private List<Action> complaintActions;
    private Action newComplaintAction;
    private boolean renderAddAction;
    private boolean statusChangeEnabled;
    private String statusString;

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
        
        if (statusList == null) {
            statusList = statusFacade.findAll();
            if (currentUserDTO.getPermissionsMap().get("complaint", "list", "company") != null
                    || currentUserDTO.getPermissionsMap().get("complaint", "list", "department") != null) {
                statusList.remove(statusFacade.getStatusByName("new"));
            }

        }
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
                    if(currentComplaint.getStatus().getName().equals("in_progress")){
                    renderAddAction = true;    
                    }
                    statusChangeEnabled = true;
                    return currentComplaint;
                }
                else if (currentUserDTO.getPermissionsMap().get("complaint", "list", "department") != null
                        &&currentUserDTO.getUserDepartmentId().equals(currentComplaint.getUser().getDepartmentRole().getDepartment().getId())) {
                    if(currentComplaint.getStatus().getName().equals("in_progress")){
                    renderAddAction = true;    
                    }
                    statusChangeEnabled = true;
                    return currentComplaint;
                }  else if(currentUserDTO.getId().equals(currentComplaint.getUser().getId())) {
                    return currentComplaint;
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

    public boolean isStatusChangeEnabled() {
        return statusChangeEnabled;
    }

    public void setStatusChangeEnabled(boolean statusChangeEnabled) {
        this.statusChangeEnabled = statusChangeEnabled;
    }
    

    public List<Status> getStatusList() {
      
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public String getStatusString() {
        if(statusString == null){
                statusString = getCurrentComplaint().getStatus().getName();
        }
    

        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }
    
    public void onChangeStatus(){
        currentComplaint.setStatus(statusFacade.getStatusByName(statusString));
        complaintFacade.edit(currentComplaint);
        if(statusString.equals("in_progress"))
            renderAddAction = true;
        else
            renderAddAction = false;
        
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').hide();");
    }
    
    
    
//     public void generateReport(String exporterType) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        CommonBean commonBean = (CommonBean) context.getApplication().evaluateExpressionGet(context, "#{commonBean}", CommonBean.class);
//        // commonBean.generateReport();
//        listOfBranch = commonBean.selectedBranch;
//        listOfDepartment = commonBean.selectedDepartment;
//        listOfEmp = commonBean.selectedEmp;
//        listOfJob = commonBean.selectedJob;
//        listOfClassJob = commonBean.selectedJobClass;
//        listOfNationality = commonBean.selectedNationality;
//        listOfCostCenter = commonBean.selectedCostCenter;
//        toCurrencyCode = commonBean.getCurrencyCode();
//        drawReport(exporterType);
//    }
//
//    public void drawReport(String exporterType) {
//        BankFormReportDesign bankFormReportDesign = new BankFormReportDesign();
//        if (selectedBankFormObject != null) {
//            bankId = Integer.parseInt(selectedBankFormObject[4].toString());
//            formId = Integer.parseInt(selectedBankFormObject[3].toString());
//            bankFormReportDesign.setBankId(bankId);
//            bankFormReportDesign.setFormId(formId);
//            bankFormReportDesign.setSqlBankReport(prepareReportSql(bankId, formId));
//
//        }
//        try {
//            String group = "";
//            switch (groupBy) {
//                case "1":
//                    group = "Department";
//                    break;
//                case "2":
//                    group = "Nationality";
//                    break;
//                case "3":
//                    group = "CostCenter";
//                    break;
//                case "4":
//                    group = "Job";
//                    break;
//                case "5":
//                    group = "JobClassification";
//                    break;
//            }
//            String reportExportName = "Bank_Form_";
//            bankFormReportDesign.setGroupBy(groupBy);
//            bankFormReportDesign.setReportTitle(reportTitle);
//            bankFormReportDesign.setChecked(checked);
//            bankFormReportDesign.setDataType(dataType);
//            bankFormReportDesign.setEmployeeType(empFlag);
//            bankFormReportDesign.setBranches(convertListToString(listOfBranch));
//            bankFormReportDesign.setDepartments(convertListToString(listOfDepartment));
//            bankFormReportDesign.setEmployees(convertListToString(listOfEmp));
//            bankFormReportDesign.setJobClass(convertListToString(listOfClassJob));
//            bankFormReportDesign.setJobs(convertListToString(listOfJob));
//            bankFormReportDesign.setNationalities(convertListToString(listOfNationality));
//            bankFormReportDesign.setCostCenters(convertListToString(listOfCostCenter));
////            bankFormReportDesign.setStartDate(dateFormat.format(fromDate));
//            //    bankFormReportDesign.setEndDate(dateFormat.format(toDate));
//
//            if (toCurrencyCode != null) {
//                bankFormReportDesign.setToCurrencyCode(splitString(toCurrencyCode, "/", 0));
//                bankFormReportDesign.setToCurrencyName(splitString(toCurrencyCode, "/", 1));
//            }
//
//            // set Logo Image
//
//            dmsStpBranch = dmsStpBranchFacade.getBranch(ManagedBase.getLoggedUser().getBranchID());
//            if (dmsStpBranch.getLogoPath() != null) {
//                logoNameArr = dmsStpBranch.getLogoPath().split(",");
//                logoName = logoNameArr[0];
//            }
//
//            bankFormReportDesign.setLogoName(logoName);
//
//
//            con = myDs.getConnection();
//
//            if (exporterType.equals("CSV")) {
//                bankFormReportDesign.generateReportCsvWithConnection(con, reportExportName);
//            } else {
//                if (exporterType.equals("PDF")) {
//                    bankFormReportDesign.generateReportWithConnection(con, reportExportName);
//                } else {
//                    if (exporterType.equals("TXT")) {
//                        bankFormReportDesign.generateReportTxtWithConnection(con, reportExportName);
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(CommonBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    
//    
//    
//     @Override
//    public HashMap prepareQueryParams() {
//        HashMap hashMap = new HashMap();
//        dmsStpBranch = dmsStpBranchFacade.getBranch(ManagedBase.getLoggedUser().getBranchID());
//        if (dmsStpBranch.getLogoPath() != null) {
//            logoNameArr = dmsStpBranch.getLogoPath().split(",");
//            logoName = logoNameArr[0];
//        }
//
//        hashMap.put("pBranchID", ManagedBase.getLoggedUser().getBranchID());
//        hashMap.put("pLogoName", logoName);
//
//        hashMap.put("pEmployees", selectedEmployees);
//        hashMap.put("pDepartments", selectedDepartments);
//        hashMap.put("pOrders", selectedOrders);
//        hashMap.put("pElements", selectedElements);
//
//        //not taken elements
//        hashMap.put("npElements", notSelectedElements);
//
//        hashMap.put("pFromYear", fromYear_No);
//        hashMap.put("pToYear", toYear_No);
//
//        hashMap.put("pFromMonth", fromMonth_No);
//        hashMap.put("pToMonth", toMonth_No);
//
//        hashMap.put("pReportsDir", servletContext.getRealPath("/resources/images/"));
//
//        switch (reportStatus) {
//            case "A":
//                hashMap.put("Status", "A");
//                hashMap.put("endWorkFlag", "I");
//                break;
//            case "I":
//                hashMap.put("Status", "I");
//                hashMap.put("endWorkFlag", "I");
//                break;
//            case "E":
//                hashMap.put("Status", "I");
//                hashMap.put("endWorkFlag", "E");
//                break;
//            case "%":
//                hashMap.put("Status", "%");
//                hashMap.put("endWorkFlag", "%");
//                break;
//        }
//
//        return hashMap;
//    }
//
//    @Override
//    public String prepareReportPath() {
//        String reportPath;
//        if (groupByDepartment) {
//            reportPath = servletContext.getRealPath("/PAY/Report/Report Designer/PayrollElementReport.jasper");
//        } else {
//            reportPath = servletContext.getRealPath("/PAY/Report/Report Designer/PayrollElementReport_byElement.jasper");
//        }
//
//        return reportPath;
//    }
//
//    public void PDF(ActionEvent actionEvent) throws JRException, IOException {
//        JasperPrint jasperPrint = jasperReportsInitializer.prepareJasperReport(prepareQueryParams(), prepareReportPath());
//
//        if (jasperPrint != null && jasperPrint.getPages() != null && jasperPrint.getPages().isEmpty()) {
//            System.err.println("No Data");
//            return;
//        }
//
//        ServletOutputStream servletOutputStream = ((HttpServletResponse) FacesContext
//                .getCurrentInstance()
//                .getExternalContext()
//                .getResponse()).getOutputStream();
//
//        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//        JasperViewer.viewReport(jasperPrint, true);
//        
//        FacesContext
//                .getCurrentInstance()
//                .responseComplete();
//    }
    
    public void pDF(){
//        try {
//            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            JasperPrint jasperPrint;
//            HashMap hashMap = new HashMap();
//            hashMap.put("subject", currentComplaint.getSubject());
//            hashMap.put("complaintIdentifier", currentComplaint.getComplaintIdentifier());
//            String reportPath;
//            reportPath = servletContext.getRealPath("/resources/reports/complaintReport.jasper");
//
//            System.err.println("reportPath : " + reportPath);
//           
//            jasperPrint = JasperFillManager.fillReport(reportPath, hashMap);
//            
//            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            //httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + new Date() + ".pdf");
//            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//            JasperViewer.viewReport(jasperPrint, true);
//        } catch (Exception ex) {
//            Logger.getLogger(ViewComplaintBackingBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
  try {
ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
 String reportPath;
            reportPath = servletContext.getRealPath("/resources/reports/complaintReport.jasper");
       FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse httpServletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            //httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + new Date() + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
 httpServletResponse.setContentType("application/pdf");

     httpServletResponse.setHeader("Content-disposition", "inline; filename=nn.pdf");

        JRPdfExporter exporter = new JRPdfExporter();
       
HashMap hashMap = new HashMap();
hashMap.put("pDir", servletContext.getRealPath("/resources/images/"));
hashMap.put("pLogo", "sypron_logo.png");

        List<ComplintDTO> l = new ArrayList<>();
      l.add(new ComplintDTO(currentComplaint.getSubject(), currentComplaint.getComplaintIdentifier(),
      currentComplaint.getUser().getFirstName()+" "+currentComplaint.getUser().getLastName(),
      currentComplaint.getUser().getDepartmentRole().getDepartment().getName(),currentComplaint.getRequestedResolution(),currentComplaint.getComplaintDefinition(),
      currentComplaint.getCreateDate()));
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
//JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//            JasperViewer.viewReport(jasperPrint, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in generate Report..."+e);
        } finally {
        }
//         byte[] bytes =  outputStream.toByteArray();

//   FacesContext facesContext = FacesContext.getCurrentInstance(); //Get the context ONCE
//     HttpServletResponse response = (HttpServletResponse)       facesContext.getExternalContext().getResponse();
////        InputStream reportStream =   facesContext.getExternalContext().getResourceAsStream("/resources/reports/complaintReport.jasper");
//try {
//    ServletOutputStream servletOutputStream = response.getOutputStream();
//   
//
////        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, null);
//    
//    servletOutputStream.flush();
//    servletOutputStream.close();
//}  catch (Exception ex) {
//       //
//   }




    }
 

        
   

}
