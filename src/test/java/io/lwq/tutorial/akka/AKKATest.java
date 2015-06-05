package io.lwq.tutorial.akka;

import akka.Main;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.junit.Test;

/**
 * Created by 8002273 on 6/5/2015.
 */
public class AKKATest {

    @Test
    public void test(){

        Main.main(new String[] {HelloWorld.class.getName()});
    }

    public static class HelloWorld extends UntypedActor {

        @Override
        public void preStart() {
            // create the greeter actor
            final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
            // tell it to perform the greeting
            greeter.tell(Greeter.Msg.GREET, getSelf());
        }

        @Override
        public void onReceive(Object msg) {
            if (msg == Greeter.Msg.DONE) {
                // when the greeter is done, stop this actor and with it the application
                getContext().stop(getSelf());
            } else
                unhandled(msg);
        }
    }

    public static class Greeter extends UntypedActor {

        public static enum Msg {
            GREET, DONE;
        }

        @Override
        public void onReceive(Object msg) {
            if (msg == Msg.GREET) {
                System.out.println("Hello World!");
                getSender().tell(Msg.DONE, getSelf());
            } else
                unhandled(msg);
        }

    }

}
