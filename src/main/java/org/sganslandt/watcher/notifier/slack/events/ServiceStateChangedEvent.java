package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ServiceStateChangedEvent {
    private final String serviceName;
    private final String state;

    @JsonCreator
    public ServiceStateChangedEvent(
            @JsonProperty("serviceName") final String serviceName,
            @JsonProperty("state") final String state) {
        this.serviceName = serviceName;
        this.state = state;
    }
}
