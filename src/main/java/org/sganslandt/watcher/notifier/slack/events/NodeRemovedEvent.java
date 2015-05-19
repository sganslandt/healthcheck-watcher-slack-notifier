package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NodeRemovedEvent {
    private final String serviceName;
    private final String url;

    @JsonCreator
    public NodeRemovedEvent(
            @JsonProperty("serviceName") final String serviceName,
            @JsonProperty("url") final String url) {
        this.serviceName = serviceName;
        this.url = url;
    }
}
