package com.github.purnet.gamedataaccesslayer;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class RequestListener implements ServletRequestListener {

	public void requestInitialized(ServletRequestEvent event) {
		String requestUuid = ThreadId.get();
		System.out.println("now initializing request for uuid " + requestUuid);
		SessionManager sm = SessionManager.getInstance();
		sm.createSession(requestUuid);
	}
	
	public void requestDestroyed(ServletRequestEvent event) {
		String requestUuid = ThreadId.get();
		System.out.println("request " + requestUuid + "finished");
		SessionManager sm = SessionManager.getInstance();
		sm.closeSession(requestUuid);
	}

}