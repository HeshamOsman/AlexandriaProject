/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.util;

import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author hisham
 */
public class PageAuthorization {
    
    public final static  MultiKeyMap<String,Boolean> PERMISSIONSMAP;
    static{
        PERMISSIONSMAP = new MultiKeyMap<>();
        
        PERMISSIONSMAP.put("addUser.xhtml","user", "add",Boolean.TRUE);
        PERMISSIONSMAP.put("complaintForm.xhtml","complaint", "add",Boolean.TRUE);
        PERMISSIONSMAP.put("complaintsListing.xhtml","complaint", "list",Boolean.TRUE);
        PERMISSIONSMAP.put("suggestionForm.xhtml","suggestion", "add",Boolean.TRUE);
        PERMISSIONSMAP.put("suggestionsListing.xhtml","suggestion", "list",Boolean.TRUE);
        PERMISSIONSMAP.put("userListing.xhtml","user", "list",Boolean.TRUE);
        PERMISSIONSMAP.put("viewComplaint.xhtml","complaint", "list",Boolean.TRUE);
        PERMISSIONSMAP.put("viewSuggestion.xhtml","suggestion", "list",Boolean.TRUE);
        PERMISSIONSMAP.put("viewUser.xhtml","user", "list",Boolean.TRUE);
        
    }
}
