package nz.co.exium.client;

import com.google.gwt.user.client.ui.Label;

// Extend any GWT Widget
public class NavDrawerWidget extends Label {

	public NavDrawerWidget() {

		// CSS class-name should not be v- prefixed
		setStyleName("vaadin-navdrawer");

		// State is set to widget in NavDrawerConnector		
	}

}