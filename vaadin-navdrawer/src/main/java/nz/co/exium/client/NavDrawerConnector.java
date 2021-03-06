package nz.co.exium.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.client.ui.SimpleManagedLayout;
import com.vaadin.shared.ui.Connect;
import nz.co.exium.NavDrawer;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(NavDrawer.class)
public class NavDrawerConnector extends AbstractSingleComponentContainerConnector implements
SimpleManagedLayout, NavDrawerListener {

	NavDrawerServerRpc rpc = RpcProxy.create(NavDrawerServerRpc.class, this);

	public NavDrawerConnector() {
		super();

		getWidget().setListener(this);

		registerRpc(NavDrawerClientRpc.class, new NavDrawerClientRpc() {

			@Override
			public void setExpand(boolean expand, boolean animated) {
				getWidget().setExpand(expand, animated);
			}

		});
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(NavDrawerWidget.class);
	}

	@Override
	public NavDrawerWidget getWidget() {
		return (NavDrawerWidget) super.getWidget();
	}

	@Override
	public NavDrawerState getState() {
		return (NavDrawerState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		getWidget().configure(getState().pixel);
		if (stateChangeEvent.hasPropertyChanged("animationDuration")) {
			getWidget().setAnimationDuration(getState().animationDuration);
		}
		if (stateChangeEvent.hasPropertyChanged("pixel")) {
			getWidget().setFixedContentSize(getState().pixel);
		}
	}
	
    @Override
    public void onConnectorHierarchyChange(final ConnectorHierarchyChangeEvent event) {
        getWidget().setWidget(getContentWidget());
    }
	
	@Override
    public void updateCaption(ComponentConnector connector) {}
	
    @Override
    public void layout() {
		getWidget().configure(getState().pixel);
        getWidget().initialize(getState().expand);
    }

	@Override
	public void onToggle(boolean expand) {
		NavDrawerConnector.this.rpc.clicked(expand);
	}
}
