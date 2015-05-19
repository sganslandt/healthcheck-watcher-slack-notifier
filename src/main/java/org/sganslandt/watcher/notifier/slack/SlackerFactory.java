package org.sganslandt.watcher.notifier.slack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.sganslandt.watcher.notifier.slack.core.Slacker;

@Data
public class SlackerFactory {
    private final String channel;
    private final int nodeStatusThrottleDurationSeconds;
    private final String screenName;
    private final String iconUrl;
    private final String webhookPath;

    @JsonCreator
    public SlackerFactory(@JsonProperty("channel") final String channel,
                          @JsonProperty("nodeStatusThrottleDurationSeconds") final int nodeStatusThrottleDurationSeconds,
                          @JsonProperty("screenName") final String screenName,
                          @JsonProperty("iconUrl") final String iconUrl,
                          @JsonProperty("webhookPath") final String webhookPath) {
        this.channel = channel;
        this.nodeStatusThrottleDurationSeconds = nodeStatusThrottleDurationSeconds;
        this.screenName = screenName;
        this.iconUrl = iconUrl;
        this.webhookPath = webhookPath;
    }

    public Slacker build() {
        return new Slacker(channel, screenName, iconUrl, nodeStatusThrottleDurationSeconds, webhookPath);
    }
}
