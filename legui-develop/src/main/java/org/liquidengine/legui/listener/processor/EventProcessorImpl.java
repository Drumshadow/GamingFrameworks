package org.liquidengine.legui.listener.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.listener.EventListener;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Default implementation of event processor.
 * <p>
 * Created by ShchAlexander on 1/25/2017.
 */
public class EventProcessorImpl extends EventProcessor {

    private static final Logger LOGGER = LogManager.getLogger();
    private Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();


    /**
     * Should be called to process events.
     */
    @Override
    public void processEvents() {
        for (Event event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            Component targetComponent = event.getTargetComponent();
            if (targetComponent == null) {
                return;
            }
            List<? extends EventListener> listeners = targetComponent.getListenerMap().getListeners(event.getClass());
            for (EventListener listener : listeners) {
                listener.process(event);
            }
        }
    }

    /**
     * Used to push event to event processor.
     *
     * @param event event to push to event processor.
     */
    @Override
    public void pushEvent(Event event) {
        if (event.getContext() != null && event.getContext().isDebugEnabled()) {
            LOGGER.debug(event);
        }
        eventQueue.add(event);
    }
}
