package nz.co.exium.client;

import com.vaadin.shared.communication.ServerRpc;

// ServerRpc is used to pass events from client to server
public interface NavDrawerServerRpc extends ServerRpc {

	void clicked(boolean enabled);

}
