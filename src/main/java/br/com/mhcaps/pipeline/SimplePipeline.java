package br.com.mhcaps.pipeline;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SimplePipeline {

    private static final String MSG = "In that darkness the White Walkers came for the first time. They swept through cities and kingdoms, riding their dead horses, hunting with their packs of pale spiders big as hounds.";

    public static void main(String[] args) throws Exception {
        final ActorSystem actorSystem = ActorSystem.create("system");
        final ActorRef consolePrinterActor = actorSystem.actorOf(ConsolePrinterActor.props());
        final ActorRef lowerCaseActor = actorSystem.actorOf(Props.create(LowerCaseActor.class, consolePrinterActor));
        final ActorRef tokenizerActor = actorSystem.actorOf(Props.create(TokenizerActor.class, lowerCaseActor));
        final ActorRef punctuationRemoverActor = actorSystem.actorOf(Props.create(PunctuationRemoverActor.class, tokenizerActor));

        punctuationRemoverActor.tell(MSG, ActorRef.noSender());

        actorSystem.terminate();


    }

}
