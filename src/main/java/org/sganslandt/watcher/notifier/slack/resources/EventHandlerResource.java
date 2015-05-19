package org.sganslandt.watcher.notifier.slack.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.eventbus.EventBus;
import org.sganslandt.watcher.notifier.slack.events.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EventHandlerResource {

    private final EventBus eventBus;

    public EventHandlerResource(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @POST
    @Timed
    @Path("/systemStateChanged")
    public void handleSystemStateChanged(SystemStateChangedEvent event) {
        eventBus.post(event);
    }

    @POST
    @Timed
    @Path("/serviceAdded")
    public void handleServiceAdded(ServiceAddedEvent event) {
        eventBus.post(event);
    }

    @POST
    @Timed
    @Path("/serviceRemoved")
    public void handleServiceRemove(ServiceRemovedEvent event) {
        eventBus.post(event);
    }

    @POST
    @Timed
    @Path("/serviceStateChanged")
    public void handleServiceStateChanged(ServiceStateChangedEvent event) {
        eventBus.post(event);
    }

    @POST
    @Timed
    @Path("/nodeAdded")
    public void handleNodeAdded(NodeAddedEvent event) {
        eventBus.post(event);
    }

    @POST
    @Timed
    @Path("/nodeHealthChanged")
    public void handleNodeHealthChanged(NodeHealthChangedEvent event) {
        eventBus.post(event);
    }

    @POST
    @Timed
    @Path("/nodeRemoved")
    public void handleNodeRemoved(NodeRemovedEvent event) {
        eventBus.post(event);
    }

}
