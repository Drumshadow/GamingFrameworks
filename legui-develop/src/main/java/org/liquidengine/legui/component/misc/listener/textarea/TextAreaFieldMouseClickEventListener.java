package org.liquidengine.legui.component.misc.listener.textarea;

import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseClickEvent.MouseClickAction;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;

/**
 * Mouse click event listener for text area. Used to update caret position.
 */
public class TextAreaFieldMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle {@link MouseClickEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
        if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
            int mouseCaretPosition = textAreaField.getMouseCaretPosition();
            textAreaField.setCaretPosition(mouseCaretPosition);
            textAreaField.setEndSelectionIndex(mouseCaretPosition);
            if (!event.isModShift()) {
                textAreaField.setStartSelectionIndex(mouseCaretPosition);
            }
        }
        if (event.getAction() == MouseClickAction.RELEASE) {
            EventProcessor.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}
