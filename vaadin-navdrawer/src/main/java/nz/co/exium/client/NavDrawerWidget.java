package nz.co.exium.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.SimplePanel;

public class NavDrawerWidget extends SimplePanel {

    private final DivElement wrapperNode, contentNode;

    private boolean expand = false;
    private boolean initialized = false;

    private DrawerAnimation drawerAnimation = new DrawerAnimation();
	private NavDrawerListener listener;

    private Integer componentSize = null;
    private int animationDuration;
    
	public NavDrawerWidget() {
		super();
        this.wrapperNode = Document.get().createDivElement();
        this.wrapperNode.setClassName(NavDrawerWidget.class + "-wrapper");
        getElement().appendChild(this.wrapperNode);

        this.contentNode = Document.get().createDivElement();
        this.contentNode.setClassName(NavDrawerWidget.class + "-content");
        this.wrapperNode.appendChild(this.contentNode);
	}

   public void initialize(final boolean expand) {
		this.expand = expand;
		animateTo(expand, 0, false);
	}

	public void configure(final int pixel) {
		if (!this.initialized) {
			if (pixel > 0) {
				this.componentSize = pixel;
			}
			this.initialized = true;
		}
	}

	//mark for removal?
	@Override
	public void onBrowserEvent(final Event event) {
		if (event != null && (event.getTypeInt() == Event.ONCLICK)) {
			animateTo(!this.expand, this.animationDuration, true);
		}
		super.onBrowserEvent(event);
	}

	public void setExpand(final boolean expand, final boolean animated) {
		animateTo(expand, animated ? this.animationDuration: 0, true);
	}

	public void animateTo(final boolean expand, final int duration, final boolean fireToggleEvent) {
		if (this.drawerAnimation.isRunning()) {
			return;
		}

		this.drawerAnimation.setAnimateToExpand(expand, fireToggleEvent);
		this.drawerAnimation.run(duration);
	}

	public void setAnimationDuration(final int animationDuration) {
		this.animationDuration = animationDuration;
	}

	public void setStyles(final String styles) {
		this.wrapperNode.setClassName(wrapperNode.getClassName() + styles);
		this.contentNode.setClassName(NavDrawerWidget.class + "-content" + styles);
	}

	public void setListener(NavDrawerListener listener) {
		this.listener = listener;
	}

	public NavDrawerListener getListener() {
		return this.listener;
	}

	public void setFixedContentSize(final int pixel) {
		this.componentSize = pixel;
	}


	public class DrawerAnimation extends Animation {

		private boolean expand = false;
		private boolean fireEvent = true;

		public void setAnimateToExpand(final boolean expand, final boolean fireEvent) {
			this.expand = expand;
			this.fireEvent = fireEvent;
		}

		private void changeSize(final double size) {
			NavDrawerWidget.this.contentNode
					.getStyle()
					.setWidth(NavDrawerWidget.this.componentSize, Style.Unit.PX);
			if (NavDrawerWidget.this.expand) {
				NavDrawerWidget.this.contentNode
						.getStyle()
						.setLeft(-1 * (NavDrawerWidget.this.componentSize - size), Style.Unit.PX);
			} else {
				NavDrawerWidget.this.contentNode
						.getStyle()
						.setLeft(-1 * size, Style.Unit.PX);
			}
		}

		@Override
		protected void onStart() {
			NavDrawerWidget.this.contentNode.getStyle()
					.setDisplay(Style.Display.BLOCK);
			if (NavDrawerWidget.this.componentSize == null || NavDrawerWidget.this.componentSize <= 0) {
				NavDrawerWidget.this.contentNode.getStyle().clearWidth();
				if (NavDrawerWidget.this.contentNode.getFirstChildElement() != null) {
					NavDrawerWidget.this.componentSize = NavDrawerWidget.this.contentNode.getFirstChildElement()
							.getOffsetWidth();
				}
			}
		}

		@Override
		protected void onUpdate(double progress) {
			changeSize(extractProportionalLength(progress));
		}

		@Override
		protected void onComplete() {
			NavDrawerWidget.this.expand = this.expand;

			if (!NavDrawerWidget.this.expand) {
				NavDrawerWidget.this.contentNode.getStyle()
						.setDisplay(Style.Display.NONE);
				changeSize(0);
			} else {
				changeSize(NavDrawerWidget.this.componentSize);
			}

			if (NavDrawerWidget.this.listener != null && this.fireEvent) {
				NavDrawerWidget.this.listener.onToggle(NavDrawerWidget.this.expand);
			}
		}

		private int extractProportionalLength(final double progress) {
			if (this.expand) {
				return (int) (NavDrawerWidget.this.componentSize * progress);
			} else {
				return (int) (NavDrawerWidget.this.componentSize * (1.0 - progress));
			}
		}
	}
}