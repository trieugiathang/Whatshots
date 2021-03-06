package com.viettel.controller;

import java.util.Vector;

import android.os.Bundle;

import com.viettel.common.ActionEvent;
import com.viettel.common.ActionEventConstant;
import com.viettel.common.ErrorConstants;
import com.viettel.common.KunKunInfo;
import com.viettel.common.ModelEvent;
import com.viettel.constants.IntentConstants;
import com.viettel.utils.LogMsg;
import com.viettel.utils.ServerLogger;
import com.viettel.viettellib.network.http.HTTPRequest;
import com.viettel.viettellib.network.http.NetworkUtil;
import com.viettel.view.BaseActivity;

public abstract class AbstractController {
	abstract public void handleViewEvent(ActionEvent e);
    abstract public void handleSwitchActivity(ActionEvent e);
    
    /**
    *  Xu ly du lieu tu model tra ve --> TH loi
    *  @author: TruongHN
    *  @param modelEvent
    *  @return: void
    *  @throws:
     */
    public void handleErrorModelEvent(final ModelEvent modelEvent) {
    	final ActionEvent e = modelEvent.getActionEvent();
    	LogMsg log = new LogMsg();
    	if (modelEvent.getModelCode() == ErrorConstants.ERROR_SESSION_RESET){
    		log.setName("NOT_LOGIN");
    	}else{
    		log.setName("action:" + modelEvent.getActionEvent().action + " versionName : " + KunKunInfo.getInstance().getProfile().getVersionName() +
					" userId:" + KunKunInfo.getInstance().getProfile().getUserData().id);
    	}
    	log.appendDes("para: ");
    	
		if (e.action == ActionEventConstant.LOGIN){
			Bundle bundle = (Bundle) e.viewData;
			if (bundle != null){
				Vector<String> userInfo = new Vector<String>();
				userInfo.add("loginName");
				userInfo.add(bundle.getString(IntentConstants.INTENT_LOGIN_PHONE));
				userInfo.add("phoneModel");
				userInfo.add(bundle
						.getString(IntentConstants.INTENT_LOGIN_PHONE_MODEL));
				userInfo.add("loginStatus");
				userInfo.add(bundle.getString(IntentConstants.INTENT_LOGIN_STATUS));
				StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(NetworkUtil.getJSONString("authenticate.login",
						userInfo));
				log.appendDes(strBuffer.toString());
			}
			
		}else{
			log.appendDes(modelEvent.getParams());
		}
		log.appendDes("result: ");
		log.appendDes(modelEvent.getDataText());
		log.appendDes("error code client: ");
		log.appendDes(String.valueOf(modelEvent.getModelCode()));
		// check: chi ghi log khi loi server
		if (e.action != ActionEventConstant.LOGIN_XMPP && e.action != ActionEventConstant.RE_LOGIN_XMPP){
			if (modelEvent.getModelCode() == ErrorConstants.ERROR_COMMON ||
			   (modelEvent.getModelCode() != ErrorConstants.ERROR_COMMON  && e.action != ActionEventConstant.LOGIN )){
				ServerLogger.sendLog(log);
			}
		}
		
		// end 
		handleCommonError(modelEvent);
		BaseActivity ac = (BaseActivity) e.sender;
		HTTPRequest request = e.request;
		if (ac.isFinished) {
			return;
		}
		ac.runOnUiThread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				BaseActivity view = (BaseActivity) e.sender;
				view.handleErrorModelViewEvent(modelEvent);
			}
		
		});
		if (request != null) {
			ac.removeProcessingRequest(request, e.isBlockRequest);
			e.request = null;
		}
	}
    
    /**
     * 
    *  Xu ly cac loi chung
    *  @author: AnhND
    *  @param modelEvent
    *  @return: void
    *  @throws:
     */
    public void handleCommonError(ModelEvent modelEvent){    	
    	ActionEvent actionEvent = modelEvent.getActionEvent();
    	switch(modelEvent.getModelCode()){
    	case ErrorConstants.ERROR_SESSION_RESET:
    		actionEvent.controller = this;
    		break;
    	}
    }
    
    /**
    *  Xu ly du lieu tu model tra ve --> TH thanh cong
    *  @author: TruongHN
    *  @param modelEvent
    *  @return: void
    *  @throws:
     */
    public void handleModelEvent(final ModelEvent modelEvent) {
		final ActionEvent e = modelEvent.getActionEvent();
		BaseActivity ac = (BaseActivity) e.sender;
		HTTPRequest request = e.request;
		
		if (ac != null && !ac.isFinished && 
				(request == null || (request != null && request.isAlive()))) {
			ac.runOnUiThread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					BaseActivity base = (BaseActivity) e.sender;
					if (base != null) {
						base.handleModelViewEvent(modelEvent);
					}
				}
			});
			if (request != null) {
				ac.removeProcessingRequest(request, e.isBlockRequest);
				e.request = null;
			}
		}
	}
}
