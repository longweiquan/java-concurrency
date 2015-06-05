package io.lwq.tutorial.rx;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by 8002273 on 6/5/2015.
 */
public class OperatorsTest {

    @Test
    public void subscribe(){
        hello("Ben", "George");
    }

    private static void hello(String... names){

        Action1<String> onNext = (name) -> System.out.println(name);
        Action1<Throwable> onError = (throwable) -> System.out.println(throwable.getMessage());
        Action0 onComplete = () -> System.out.println("complete !");

        Func1<String, String> transformer = (s) -> "next: " + s;

        // Creating Observable and subscribe actions
        Observable.from(names)                          // source (observable)
            .map(transformer)                           // transform source
            .subscribe(onNext, onError, onComplete);    // target (observer/subscriber)
    }

}
