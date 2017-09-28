/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.dto;

import com.sypron.entity.Permission;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author hisham
 */
public class UserDTO implements Serializable{
     private static final long serialVersionUID = 1L;
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer userDepartmentId;
    private Integer userRoleId;
    private Integer userDepartmentRoleId;
    private List<Permission> permissions;
    private Boolean active;
    private MultiKeyMap<String,Boolean> permissionsMap;
    public UserDTO(Integer id, String email, String firstName,String lastName,
           Integer userDepartmentId , Integer userRoleId ,Integer userDepartmentRoleId
            ,Boolean active,List<Permission> permissions) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissions = permissions;
        this.userDepartmentId =userDepartmentId;
        this.userRoleId = userRoleId;
        this.userDepartmentRoleId= userDepartmentRoleId;
        this.active= active;
        permissionsMap = new MultiKeyMap<>();
        
        for(Permission permission:permissions){
            permissionsMap.put(permission.getName(),permission.getMethod(),permission.getScope(), Boolean.TRUE);
            System.out.println(">>>>>"+permission.getName()+">"+permission.getMethod()+">"+permission.getScope()+">>>>>>>"+
                    permissionsMap.get(permission.getName(),permission.getMethod(),permission.getScope()));
        }
        
    }

    public MultiKeyMap<String, Boolean> getPermissionsMap() {
        return permissionsMap;
    }

    public void setPermissionsMap(MultiKeyMap<String, Boolean> permissionsMap) {
        this.permissionsMap = permissionsMap;
    }

    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Integer getUserDepartmentId() {
        return userDepartmentId;
    }

    public void setUserDepartmentId(Integer userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserDepartmentRoleId() {
        return userDepartmentRoleId;
    }

    public void setUserDepartmentRoleId(Integer userDepartmentRoleId) {
        this.userDepartmentRoleId = userDepartmentRoleId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    
    
    
}
