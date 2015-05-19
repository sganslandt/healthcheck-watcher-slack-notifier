package org.sganslandt.watcher.notifier.slack.core;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
class SlackMessage {
    private final String channel;
    private final String username;
    private final String text;
    private final String icon_url;
    private final List<Map<String, Object>> attachments;

    public SlackMessage(String username, String iconUrl, String channel, String message) {
        this.channel = channel;
        this.text = message;
        this.username = username;
        this.icon_url = iconUrl;
        this.attachments = new LinkedList<>();
    }

    public SlackMessage(String username, String iconUrl, String channel, String message, Color color) {
        this.channel = channel;
        this.text = "";
        this.username = username;
        this.icon_url = iconUrl;
        this.attachments = new LinkedList<>();
        final Map<String, Object> attachment = new HashMap<>();
        attachment.put("fallback", "Summary");
        attachment.put("color", color.value);
        attachment.put("text", message);
        attachments.add(attachment);
    }

    public SlackMessage withAttachment(String text, Color color) {
        HashMap<String, Object> attachment = new HashMap<>();
        attachment.put("color", color.value);
        attachment.put("text", text);
        attachments.add(attachment);
        return this;
    }

    static class Color {
        static final Color warning = of("warning");
        static final Color good = of("good");
        static final Color danger = of("danger");
        static final Color teal = of("#35B3AB");

        private final String value;

        public Color(final String value) {
            this.value = value;
        }

        static Color of(String value) {
            return new Color(value);
        }

    }

}

