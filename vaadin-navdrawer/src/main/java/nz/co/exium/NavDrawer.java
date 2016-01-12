package nz.co.exium;

import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import nz.co.exium.client.NavDrawerClientRpc;
import nz.co.exium.client.NavDrawerListener;
import nz.co.exium.client.NavDrawerServerRpc;
import nz.co.exium.client.NavDrawerState;

import java.util.ArrayList;
import java.util.List;

// This is the server-side UI component that provides public API 
// for NavDrawer
public class NavDrawer extends AbstractSingleComponentContainer {

    List<NavDrawerListener> listeners = new ArrayList<>();

	private final NavDrawerServerRpc rpc = new NavDrawerServerRpc() {

		public void clicked(boolean enabled) {
            getState().expand = enabled;
            for (NavDrawerListener listener : listeners) {
                listener.onToggle(enabled);
            }
		}
	};

	public NavDrawer() {
        setHeight(100, Unit.PERCENTAGE);
        setWidth(256, Unit.PIXELS);
        setImmediate(true);
		registerRpc(this.rpc);
	}

    public NavDrawer(Component content) {
        setContent(content);
        setHeight(100, Unit.PERCENTAGE);
        setWidth(256, Unit.PIXELS);
        setImmediate(true);
        registerRpc(this.rpc);
    }

    public void addListener(NavDrawerListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(NavDrawerListener listener) {
        return listeners.remove(listener);
    }

	@Override
	public NavDrawerState getState() {
		return (NavDrawerState) super.getState();
	}

    public void setFixedContentSize(final int pixel) {
        getState().pixel = pixel;
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
    
    public void toggle() {
		getRpcProxy(NavDrawerClientRpc.class).setExpand((getState().expand = !getState().expand ), true);
    }
}
