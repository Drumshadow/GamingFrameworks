package sources;

import java.util.ArrayList;

public class EventHandler {
    private ArrayList<Event> events;

    EventHandler() {
        events = new ArrayList<>();
    }

    void addEvent(Event event) {
        events.add(event);
    }

    Event getEvent(int i) {
        return events.get(i);
    }

    public int size() {
        return events.size();
    }

    void removeAll() {
        events.clear();
    }
}
