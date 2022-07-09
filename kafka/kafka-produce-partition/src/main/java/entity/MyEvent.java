package entity;

import java.io.Serializable;

public class MyEvent implements Serializable {
    private String eventId;
    private String name;
    private String eventType;
    private long eventTime;

    public MyEvent() {
    }

    public MyEvent(String eventId, String name, String eventType, long eventTime) {
        this.eventId = eventId;
        this.name = name;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "eventId='" + eventId + '\'' +
                ", name='" + name + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                '}';
    }
}