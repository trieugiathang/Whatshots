package com.viettel.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.viettel.common.ActionEvent;
import com.viettel.viettellib.network.http.HTTPRequest;
import com.viettel.view.BaseActivity;

public class UserController extends AbstractController {

	static UserController instance;

	protected UserController() {
		
	}

	public static UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}
		return instance;
	}

	@Override
	public void handleSwitchActivity(ActionEvent e){
		Activity base = (Activity) e.sender;
		Intent intent;
		Bundle extras;
		switch(e.action){
		}
	}
	
	
	public void handleViewEvent(ActionEvent e) {
		HTTPRequest request = null;
		boolean isProcessRequest = true;
		BaseActivity baseActivity = (BaseActivity)e.sender;
		if (baseActivity != null && e.isBlockRequest){
			if (baseActivity.checkExistRequestProcessing(e.action)){
				isProcessRequest = false; 
			}
		} 
		if (isProcessRequest){
			switch (e.action) {
			default:
				break;
			}
			if (request != null && baseActivity != null) {
				baseActivity.addProcessingRequest(request, e.isBlockRequest);
				e.request = request;
			}
		}
	}
}
