package org.sganslandt.watcher.notifier.slack.core;

import com.google.common.eventbus.Subscribe;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.sganslandt.watcher.notifier.slack.events.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class Slacker {

    private final String channel;
    private final int nodeStatusThrottleDurationSeconds;
    private final WebTarget slackMeResource;
    private String screenName;
    private String iconUrl;

    public Slacker(final String channel, final String screenName, final String iconUrl, final int nodeStatusThrottleDurationSeconds, final String webhookPath) {
        this.channel = channel;
        this.screenName = screenName;
        this.iconUrl = iconUrl;
        this.nodeStatusThrottleDurationSeconds = nodeStatusThrottleDurationSeconds;

        final Client client = ClientBuilder.newClient();

        UriBuilder uriBuilder = new JerseyUriBuilder();
        uriBuilder
                .scheme("https")
                .host("hooks.slack.com")
                .port(443)
                .path(webhookPath);
        slackMeResource = client.target(uriBuilder.build());
    }

    @Subscribe
    public void handle(final SystemStateChangedEvent event) {
        String message;
        switch (event.getState()) {
            case "Healthy":
                message = event.getSystemName() + " is healthy again!";
                break;
            case "Unhealthy":
                message = "Something is up with " + event.getSystemName() + ".";
                break;
            default:
                message = "Something happened with " + event.getSystemName() + ". Can't understand state " + event.getState() + ".";
        }

        final boolean isHealthy = event.getState().equals("Healthy");

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, isHealthy ? SlackMessage.Color.good : SlackMessage.Color.danger)));
    }

    @Subscribe
    public void handle(final NodeAddedEvent event) {
        String message = "A new node was added to " + event.getServiceName() + " @ " + event.getNodeUrl();

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, SlackMessage.Color.teal)));
    }

    @Subscribe
    public void handle(final NodeHealthChangedEvent event) {
        String message = event.getServiceName() + " node @ " + event.getNodeUrl() + " is " + event.getState().toLowerCase() + ".";
        for (Health h : event.getHealths()) {
            message += "\n" + h.getName() + ": " + (h.isHealthy() ? "Healthy" : h.getMessage().get());
        }

        final boolean isHealthy = event.getState().equals("Healthy");

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, isHealthy ? SlackMessage.Color.good : SlackMessage.Color.danger)));
    }

    @Subscribe
    public void handle(NodeRemovedEvent event) {
        String message = "A node was removed from " + event.getServiceName() + " @ " + event.getUrl();

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, SlackMessage.Color.teal)));
    }

    @Subscribe
    public void handle(final ServiceAddedEvent event) {
        String message = "A new service has been born! All welcome " + event.getServiceName() + ".";

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, SlackMessage.Color.teal)));

    }

    @Subscribe
    public void handle(final ServiceRemovedEvent event) {
        String message = event.getServiceName() + " has been removed from the system.";

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, SlackMessage.Color.teal)));
    }

    @Subscribe
    public void handle(final ServiceStateChangedEvent event) {
        String message;
        switch (event.getState()) {
            case "Healthy":
                message = event.getServiceName() + " is healthy again!";
                break;
            case "Unhealthy":
                message = "Something is up with " + event.getServiceName() + ".";
                break;
            default:
                message = "Something happened with " + event.getServiceName() + ". Can't understand state " + event.getState() + ".";
        }

        final boolean isHealthy = event.getState().equals("Healthy");

        slackMeResource
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(toSlackMessage(message, isHealthy ? SlackMessage.Color.good : SlackMessage.Color.danger)));
    }

    public SlackMessage toSlackMessage(String message) {
        return new SlackMessage(screenName, iconUrl, channel, message);
    }

    public SlackMessage toSlackMessage(String message, SlackMessage.Color color) {
        return new SlackMessage(screenName, iconUrl, channel, message, color);
    }
}
