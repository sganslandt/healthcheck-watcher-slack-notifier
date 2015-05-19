package org.sganslandt.watcher.notifier.slack;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.sganslandt.watcher.notifier.slack.core.Slacker;
import org.sganslandt.watcher.notifier.slack.resources.EventHandlerResource;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Application extends io.dropwizard.Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public String getName() {
        return "healthcheck-watcher-slack-notifier";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        AsyncEventBus eventBus = new AsyncEventBus("eventBus", new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>()));
        Slacker slacker = configuration.getSlacker().build();
        eventBus.register(slacker);

        // The Jersey Resource
        final EventHandlerResource healthResource = new EventHandlerResource(eventBus);

        // Exprose the Jersey Resource
        environment.jersey().register(healthResource);
    }

}