package org.sganslandt.watcher.notifier.slack.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import lombok.Data;

@Data
public final class Health {
    private final String name;
    private final boolean healthy;
    private final Optional<String> message;

    @JsonCreator
    public Health(
            @JsonProperty("name") String name,
            @JsonProperty("healthy") boolean healthy,
            @JsonProperty("message") Optional<String> message) {
        this.name = name;
        this.healthy = healthy;
        this.message = message;
    }

}
