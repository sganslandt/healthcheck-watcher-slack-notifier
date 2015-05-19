package org.sganslandt.watcher.notifier.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Configuration extends io.dropwizard.Configuration {

    @Valid
    @NotNull
    @Getter
    @JsonProperty
    private SlackerFactory slacker;

}