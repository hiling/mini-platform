package com.github.hiling.oauth.client.web;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/14/2018.
 */
public class UserInfo {
    String userId;
    String clientId;
    List<String> scopeList;

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getClientId(){
        return clientId;
    }

    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public List<String> getScopeList(){
        return scopeList;
    }

    public void setScopeList(List<String> scopeList){
        this.scopeList = scopeList;
    }

}
