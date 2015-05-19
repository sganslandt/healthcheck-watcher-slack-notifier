package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServiceAddedEvent {
    private final String serviceName;

    @JsonCreator
    public ServiceAddedEvent(@JsonProperty("serviceName") final String serviceName) {
        this.serviceName = serviceName;
    }
}
