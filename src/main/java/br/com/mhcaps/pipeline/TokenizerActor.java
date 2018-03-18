package br.com.mhcaps.pipeline;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TokenizerActor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ActorRef next;

    public TokenizerActor(ActorRef next) {
        this.next = next;
    }

    @Override
    public void preStart() {
        log.info("Starting actor {}", this.getClass().getSimpleName());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> next.tell(s.split("\\s+"), getSelf()))
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

}
