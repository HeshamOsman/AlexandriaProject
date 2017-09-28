/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.util.SessionUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author hisham
 */
@ManagedBean()
@ViewScoped
public class MenuBackingBean implements Serializable {
//

    UserDTO currentUser;
    boolean renderComplaint;
    boolean renderNewComplaint;
    boolean renderListDepartmentComplaint;
    boolean renderListCompanyComplaint;
    boolean renderListMyComplaint;
    boolean renderSuggestion;
    boolean renderNewSuggestion;
    boolean renderListDepartmentSuggestion;
    boolean renderListCompanySuggestion;
    boolean renderListMySuggestion;

    boolean renderUser;
    boolean renderNewUser;
    boolean renderListUsers;
//    Map<String, > menuRendering;
//    MultiKeyMap<String, Boolean> menuRendering;

    /**
     * Creates a new instance of MenuBackingBean
     */
    public MenuBackingBean() {
    }

    @PostConstruct
    public void onInit() {
//        menuRendering = new MultiKeyMap<>();
//        menuRendering.put("jgh","ds","",Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
//        menuRendering.put(key, Boolean.FALSE);
        currentUser = SessionUtils.getLoggedUser();
    }

    public boolean isRenderComplaint() {
        if(isRenderNewComplaint()||isRenderListDepartmentComplaint()
                ||isRenderListCompanyComplaint()||isRenderListMyComplaint()){
//            System.out.println(">>>>>>>1True");
            return renderComplaint = true;
        }
        return renderComplaint = false;
    }

    public void setRenderComplaint(boolean renderComplaint) {
        this.renderComplaint = renderComplaint;
    }

    public boolean isRenderNewComplaint() {
        if (currentUser == null) {
            return renderNewComplaint = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("complaint", "add","non")) {
//                System.out.println(">>>>>>>2True");
                return renderNewComplaint = true;
            } else {
                return renderNewComplaint = false;
            }
        }
    }

    public void setRenderNewComplaint(boolean renderNewComplaint) {
        this.renderNewComplaint = renderNewComplaint;
    }

    public boolean isRenderListDepartmentComplaint() {
        if (currentUser == null) {
            return renderListDepartmentComplaint = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("complaint", "list", "department")) {
//                System.out.println(">>>>>>>3True");
                return renderListDepartmentComplaint = true;
            } else {
                return renderListDepartmentComplaint = false;
            }
        }
    }

    public void setRenderListDepartmentComplaint(boolean renderListDepartmentComplaint) {
        this.renderListDepartmentComplaint = renderListDepartmentComplaint;
    }

    public boolean isRenderListCompanyComplaint() {
        if (currentUser == null) {
            return renderListCompanyComplaint = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("complaint", "list", "company")) {
//                System.out.println(">>>>>>>3True");
                return renderListCompanyComplaint = true;
            } else {
                return renderListCompanyComplaint = false;
            }
        }
    }

    public void setRenderListCompanyComplaint(boolean renderListCompanyComplaint) {
        this.renderListCompanyComplaint = renderListCompanyComplaint;
    }

    public boolean isRenderListMyComplaint() {
        if (currentUser == null) {
            return renderListMyComplaint = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("complaint", "list", "user")) {
//                System.out.println(">>>>>>>4True");
                return renderListMyComplaint = true;
            } else {
                return renderListMyComplaint = false;
            }
        }
    }

    public void setRenderListMyComplaint(boolean renderListMyComplaint) {
        this.renderListMyComplaint = renderListMyComplaint;
    }

    public boolean isRenderSuggestion() {
        
         if(isRenderNewSuggestion()||isRenderListDepartmentSuggestion()
                ||isRenderListCompanySuggestion()||isRenderListMySuggestion()){
//             System.out.println(">>>>>>>5True");
            return renderSuggestion = true;
        }
        return renderSuggestion = false;
        
     
    }

    public void setRenderSuggestion(boolean renderSuggestion) {
        this.renderSuggestion = renderSuggestion;
    }

    public boolean isRenderNewSuggestion() {
        if (currentUser == null) {
            return renderNewSuggestion = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("suggestion", "add","non")) {
//                System.out.println(">>>>>>>6True");
                return renderNewSuggestion = true;
            } else {
                return renderNewSuggestion = false;
            }
        }
    }

    public void setRenderNewSuggestion(boolean renderNewSuggestion) {
        this.renderNewSuggestion = renderNewSuggestion;
    }

    public boolean isRenderListDepartmentSuggestion() {
        if (currentUser == null) {
            return renderListDepartmentSuggestion = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("suggestion", "list", "department")) {
                System.out.println(">>>>>>>7True");
                return renderListDepartmentSuggestion = true;
            } else {
                return renderListDepartmentSuggestion = false;
            }
        }
    }

    public void setRenderListDepartmentSuggestion(boolean renderListDepartmentSuggestion) {
        this.renderListDepartmentSuggestion = renderListDepartmentSuggestion;
    }

    public boolean isRenderListCompanySuggestion() {
        if (currentUser == null) {
            return renderListCompanySuggestion = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("suggestion", "list", "company")) {
//                System.out.println(">>>>>>>8True");
                return renderListCompanySuggestion = true;
            } else {
                return renderListCompanySuggestion = false;
            }
        }
    }

    public void setRenderListCompanySuggestion(boolean renderListCompanySuggestion) {
        this.renderListCompanySuggestion = renderListCompanySuggestion;
    }

    public boolean isRenderListMySuggestion() {
        if (currentUser == null) {
            return renderListMySuggestion = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("suggestion", "list", "user")) {
//                System.out.println(">>>>>>>9True");
                return renderListMySuggestion = true;
            } else {
                return renderListMySuggestion = false;
            }
        }
    }

    public void setRenderListMySuggestion(boolean renderListMySuggestion) {
        this.renderListMySuggestion = renderListMySuggestion;
    }

    public boolean isRenderUser() {
        
         if(isRenderNewUser()||isRenderListUsers()){
//             System.out.println(">>>>>>>10True");
            return renderUser = true;
        }
        return renderUser = false;
       
    }

    public void setRenderUser(boolean renderUser) {
        this.renderUser = renderUser;
    }

    public boolean isRenderNewUser() {
        if (currentUser == null) {
            return renderNewUser = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("user", "add", "non")) {
//                System.out.println(">>>>>>>11True");
                return renderNewUser = true;
            } else {
                return renderNewUser = false;
            }
        }
    }

    public void setRenderNewUser(boolean renderNewUser) {
        this.renderNewUser = renderNewUser;
    }

    public boolean isRenderListUsers() {
        if (currentUser == null) {
            return renderListUsers = false;
        } else {
            if (currentUser.getPermissionsMap().containsKey("user", "list", "non")) {
//                System.out.println(">>>>>>>12True");
                return renderListUsers = true;
            } else {
                return renderListUsers = false;
            }
        }
    }

    public void setRenderListUsers(boolean renderListUsers) {
        this.renderListUsers = renderListUsers;
    }

}
