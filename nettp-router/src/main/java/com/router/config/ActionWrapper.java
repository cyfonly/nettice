package com.router.config;

import java.lang.reflect.Method;

import com.router.BaseAction;

/**
 * 包装Action
 * @author yunfeng.cheng
 * @create 2016-08-03
 */
public class ActionWrapper {
	
	public BaseAction actionObject;
	public Method method;
	public String actionPath;
    public Method callBackMethod;
    
	public ActionWrapper(BaseAction actionObject, Method method, Method callBackMethod,String actionPath) {
		this.actionObject = actionObject;
		this.method = method;
		this.actionPath = actionPath;
        this.callBackMethod = callBackMethod;
	}

    public ActionWrapper(BaseAction actionObject, Method method, String actionPath) {
        this.actionObject = actionObject;
        this.method = method;
        this.actionPath = actionPath;
    }

}
