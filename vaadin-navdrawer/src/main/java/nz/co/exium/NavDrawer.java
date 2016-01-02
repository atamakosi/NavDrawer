package nz.co.exium;

import nz.co.exium.client.NavDrawerClientRpc;
import nz.co.exium.client.NavDrawerServerRpc;
import nz.co.exium.client.NavDrawerState;

import com.vaadin.shared.MouseEventDetails;

// This is the server-side UI component that provides public API 
// for NavDrawer
public class NavDrawer extends com.vaadin.ui.AbstractComponent {

	private int clickCount = 0;

	// To process events from the client, we implement ServerRpc
	private NavDrawerServerRpc rpc = new NavDrawerServerRpc() {

		// Event received from client - user clicked our widget
		public void clicked(MouseEventDetails mouseDetails) {
			
			// Send nag message every 5:th click with ClientRpc
			if (++clickCount % 5 == 0) {
				getRpcProxy(NavDrawerClientRpc.class)
						.alert("Ok, that's enough!");
			}
			
			// Update shared state. This state update is automatically 
			// sent to the client. 
			getState().text = "You have clicked " + clickCount + " times";
		}
	};

	public NavDrawer() {

		// To receive events from the client, we register ServerRpc
		registerRpc(rpc);
	}

	// We must override getState() to cast the state to NavDrawerState
	@Override
	public NavDrawerState getState() {
		return (NavDrawerState) super.getState();
	}
}
