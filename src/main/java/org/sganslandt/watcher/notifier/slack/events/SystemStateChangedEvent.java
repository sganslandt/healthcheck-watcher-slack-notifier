package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SystemStateChangedEvent {
    private final String systemName;
    private final String state;

    @JsonCreator
    public SystemStateChangedEvent(
            @JsonProperty("systemName") final String systemName,
            @JsonProperty("state") final String state) {
        this.systemName = systemName;
        this.state = state;
    }
}
