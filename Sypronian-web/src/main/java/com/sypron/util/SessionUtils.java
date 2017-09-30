/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.util;
import com.sypron.dto.UserDTO;
import com.sypron.entity.User;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 *
 * @author hisham
 */
public class SessionUtils {
    public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}
  public static void setLoggedUser(UserDTO userDTO) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userInfo", userDTO);
    }
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
        
    public static UserDTO getLoggedUser() {
        return (UserDTO) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("userInfo");
    }

//	public static String getUserName() {
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
//				.getExternalContext().getSession(false);
//		return session.getAttribute("username").toString();
//	}

//	public static String getUserId() {
//		HttpSession session = getSession();
//		if (session != null)
//			return (String) session.getAttribute("userid");
//		else
//			return null;
//	}
}
