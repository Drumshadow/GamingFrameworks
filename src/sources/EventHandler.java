package sources;

import java.util.ArrayList;

public class EventHandler {
    private ArrayList<Event> events;

    public EventHandler() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public Event getEvent(int i) {
        return events.get(i);
    }

    public int size() {
        return events.size();
    }

    public void removeAll() {
        events.clear();
    }
}
