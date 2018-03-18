package br.com.mhcaps.pipeline;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.stream.Stream;

public class ConsolePrinterActor extends AbstractActor {
    static public Props props() {
        return Props.create(ConsolePrinterActor.class, () -> new ConsolePrinterActor());
    }

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public void preStart() {
        log.info("Starting actor {}", this.getClass().getSimpleName());
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println(s);
                    context().stop(getSelf());
                })
                .match(String[].class, s -> {
                    Stream.of(s).forEach(l -> System.out.println(l));
                    context().stop(getSelf());
                })
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

}
