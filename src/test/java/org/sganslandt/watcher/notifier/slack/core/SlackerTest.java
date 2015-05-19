package org.sganslandt.watcher.notifier.slack.core;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sganslandt.watcher.notifier.slack.events.*;

import java.util.LinkedList;
import java.util.List;

@Ignore
public class SlackerTest {

    private Slacker slacker;
    private String testNodeUrl;
    private String testService;

    @Before
    public void setup() {
        slacker = new Slacker("#slacker-test", "watcher", "https://avatars3.githubusercontent.com/u/1101238", 10, "");

        testNodeUrl = "http://test.example.com";
        testService = "test-service";
    }

    @Test
    public void testSystemStateChanged() {
        slacker.handle(new SystemStateChangedEvent("test", "Unhealthy"));
        slacker.handle(new SystemStateChangedEvent("test", "Healthy"));
        slacker.handle(new SystemStateChangedEvent("test", "Foo"));
    }

    @Test
    public void testNodeAdded() {
        slacker.handle(new NodeAddedEvent(testService, testNodeUrl));
    }

    @Test
    public void testNodeHealthChanged() {
        final List<Health> healths = new LinkedList<>();
        healths.add(new Health("database", true, Optional.<String>absent()));
        slacker.handle(new NodeHealthChangedEvent(testService, testNodeUrl, "Healthy", healths));

        healths.add(new Health("broker", false, Optional.of("StackOverFlowException")));
        slacker.handle(new NodeHealthChangedEvent(testService, testNodeUrl, "Unhealthy", healths));
    }

    @Test
    public void testNodeRemoved() {
        slacker.handle(new NodeRemovedEvent(testService, testNodeUrl));
    }

    @Test
    public void testServiceAdded() {
        slacker.handle(new ServiceAddedEvent(testService));
    }

    @Test
    public void testServiceRemoved() {
        slacker.handle(new ServiceRemovedEvent(testService));
    }

    @Test
    public void testServiceStateChanged() {
        slacker.handle(new ServiceStateChangedEvent(testService, "Healthy"));
        slacker.handle(new ServiceStateChangedEvent(testService, "Unhealthy"));
        slacker.handle(new ServiceStateChangedEvent(testService, "Unknown"));
    }

}
