package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NodeAddedEvent {
    private final String serviceName;
    private final String nodeUrl;

    @JsonCreator
    public NodeAddedEvent(@JsonProperty("serviceName") final String serviceName, @JsonProperty("nodeUrl") final String nodeUrl) {
        this.serviceName = serviceName;
        this.nodeUrl = nodeUrl;
    }
}
