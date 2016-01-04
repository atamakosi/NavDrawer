package nz.co.exium;

import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import nz.co.exium.client.NavDrawerClientRpc;
import nz.co.exium.client.NavDrawerServerRpc;
import nz.co.exium.client.NavDrawerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// This is the server-side UI component that provides public API 
// for NavDrawer
public class NavDrawer extends AbstractSingleComponentContainer {

//    private final List<NavDrawerListener> listenerList = new ArrayList<>();
    private List<Component> children = new ArrayList<>();
    
	private NavDrawerServerRpc rpc = new NavDrawerServerRpc() {

		public void clicked(boolean enabled) {
//			getState().expand = enabled;
//            toggle();
		}
	};

	public NavDrawer() {
		registerRpc(rpc);
	}

	@Override
	public NavDrawerState getState() {
		return (NavDrawerState) super.getState();
	}
	
    public void collapse() {
        getRpcProxy(NavDrawerClientRpc.class).setExpand(false, true);
    }

    public void expand() {
        getRpcProxy(NavDrawerClientRpc.class).setExpand(true, true);
    }
    
    public boolean isExpanded() {
        return getState().expand;
    }
    
    public int getAnimationDuration() {
        return getState().animationDuration;
    }

    public void setAnimationDuration(final int animationDuration) {
        getState().animationDuration = animationDuration;
    }

    public void setExpanded(final boolean value, final boolean animated) {
        getRpcProxy(NavDrawerClientRpc.class).setExpand(value, animated);
    }

    @Override
    public int getComponentCount() {
        return children.size();
    }

    @Override
    public Iterator<Component> iterator() {
        return children.iterator();
    }
    
    public void toggle() {
		getRpcProxy(NavDrawerClientRpc.class).setExpand(!getState().expand, true);
    }
}
