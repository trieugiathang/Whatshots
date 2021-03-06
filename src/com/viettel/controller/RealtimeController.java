/**
 * Copyright 2011 Viettel Telecome. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.controller;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.viettel.common.ActionEvent;
import com.viettel.common.KunKunInfo;
import com.viettel.common.ModelEvent;
import com.viettel.view.BaseActivity;

/**
 *  Xy ly MVC cho model realtime (interface)
 *  @author: BangHN
 *  @version: 1.0
 *  @since: Dec 16, 2011
 */
public class RealtimeController extends AbstractController {

	static RealtimeController instance;
	
	
	/**
	 * goi event xmpp cho ui thread
	 * @author: BangHN
	 * @version: 1.0
	 * @since: 1.0
	 */
	class XMPPNotifier extends AsyncTask<Void, Void, Void> {
		int action;
		Serializable data;

		protected XMPPNotifier(int action, Serializable data) {
			this.action = action;
			this.data = data;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Context appContext = KunKunInfo.getInstance().getAppContext();
			if (appContext != null) {
				Intent intent = new Intent(BaseActivity.VT_ACTION);
				Bundle bundle = new Bundle();
				bundle.putInt("kunkun.action", this.action);
				bundle.putInt("kunkun.hashCode", hashCode());
				if (data != null) {
					bundle.putSerializable("data", data);
				}
				intent.putExtras(bundle);
				appContext.sendBroadcast(intent);
			}
		}
	}

	protected RealtimeController() {
	}

	public static RealtimeController getInstance() {
		if (instance == null) {
			instance = new RealtimeController();
		}
		return instance;
	}
	
	/**
	 * goi cac xmpp event len tang view, chi goi tren xmpp thread
	 * @author: BangHN
	 * @param action
	 * @param data
	 * @return: void
	 * @throws:
	 */
	private void broadcastXMPPEvent(int action, Serializable data) {
		Void param = null;
		new XMPPNotifier(action, data).execute(param);
	}
	
	
	
	
	@Override
	public void handleViewEvent(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleModelEvent(ModelEvent modelEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSwitchActivity(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * nhan cac su kien XMPP, tach ra khoi luong xmpp de khoi nhap nhang ve pham
	 * vi thread, ham nay chay tren thread cua xmpp
	 * @author: BangHN
	 * @param modleEvent
	 * @return: void
	 * @throws:
	 */
	public void handleXMPPEvent(final ModelEvent modelEvent) {
		ActionEvent actionEvent = modelEvent.getActionEvent();
		broadcastXMPPEvent(actionEvent.action,
				(Serializable) modelEvent.getModelData());
	}

}
