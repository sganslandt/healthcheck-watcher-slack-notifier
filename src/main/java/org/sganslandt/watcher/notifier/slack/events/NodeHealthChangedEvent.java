package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NodeHealthChangedEvent {
    private final String serviceName;
    private final String nodeUrl;
    private final String state;
    private final List<Health> healths;

    @JsonCreator
    public NodeHealthChangedEvent(
            @JsonProperty("serviceName") final String serviceName,
            @JsonProperty("nodeUrl") final String nodeUrl,
            @JsonProperty("state") final String state,
            @JsonProperty("healths") final List<Health> healths) {
        this.serviceName = serviceName;
        this.nodeUrl = nodeUrl;
        this.state = state;
        this.healths = healths;
    }
}
