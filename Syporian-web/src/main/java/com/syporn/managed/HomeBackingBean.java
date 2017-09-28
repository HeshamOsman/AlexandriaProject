/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.managed;

import com.sypron.dto.UserDTO;
import com.sypron.entity.User;
import com.sypron.facade.UserFacade;
import com.sypron.util.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author hisham
 */
@ManagedBean()
@ViewScoped
public class HomeBackingBean implements Serializable {

    /**
     * Creates a new instance of LogingBackingBean
     */
//    @Inject
//    UserFacade userFacade;
//    private String ss;
    
    public HomeBackingBean() {
    }
//    
//    public List<UserDTO> getAll(){
//        List<UserDTO> us = new ArrayList<>();
//        System.out.println("Hes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."+SessionUtils.getLoggedUser().getEmail());
//        us.add(SessionUtils.getLoggedUser());
//        return us;
//    }
//
//    public String getSs() {
//        return ss;
//    }
//
//    public void setSs(String ss) {
//        this.ss = ss;
//    }
    
    
}
