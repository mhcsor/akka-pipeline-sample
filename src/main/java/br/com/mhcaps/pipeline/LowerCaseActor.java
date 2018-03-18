package br.com.mhcaps.pipeline;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.stream.Stream;

public class LowerCaseActor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ActorRef next;

    public LowerCaseActor(ActorRef next) {
        this.next = next;
    }

    @Override
    public void preStart() {
        log.info("Starting actor {}", this.getClass().getSimpleName());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> next.tell(s.toLowerCase(), getSelf()))
                .match(String[].class, s -> next.tell(Stream.of(s).map(String::toLowerCase).toArray(String[]::new), getSelf()))
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

}
