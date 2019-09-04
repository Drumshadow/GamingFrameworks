package org.liquidengine.legui.component.misc.listener.widget;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;

import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

/**
 * @author ShchAlexander.
 */
public class WidgetMinimizeButMouseClickEventListener implements MouseClickEventListener {

    private Widget widget;

    public WidgetMinimizeButMouseClickEventListener(Widget widget) {
        this.widget = widget;
    }

    public void process(MouseClickEvent event) {
        if (CLICK == event.getAction()) {
            boolean newValue = !widget.isMinimized();
            widget.getMinimizeButton().getStyle().getBackground().setIcon(newValue ? widget.getMaximizeIcon() : widget.getMinimizeIcon());
            widget.setMinimized(newValue);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}
