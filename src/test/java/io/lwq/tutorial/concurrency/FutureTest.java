package io.lwq.tutorial.concurrency;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Basic Future Test
 */
public class FutureTest {

    @Test
    public void get() throws ExecutionException, InterruptedException {

        // Executor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit a Callable task and return a Future
        Future<Integer> future = executor.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(100);
            return 123;
        });

        // Future will be resolved asynchronsly
        System.out.println("future done? " + future.isDone());

        // Future.get block thread
        Integer result = future.get();
        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
    }

    @Test(expected = TimeoutException.class)
    public void getWithTimeout() throws InterruptedException, ExecutionException, TimeoutException {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(200);
            return 123;
        });

        future.get(100, TimeUnit.MILLISECONDS);
    }

    @Test
    public void invokeAll() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        Callable<String> task1 = () -> {
            TimeUnit.MILLISECONDS.sleep(200);
            return "task1";
        };
        Callable<String> task2 = () -> {
            TimeUnit.MILLISECONDS.sleep(100);
            return "task2";
        };
        List<Callable<String>> tasks = Arrays.asList(task1, task2);

        // invokeAll will wait for all tasks completed
        // we have control in each future's state
        executor.invokeAll(tasks)
            .stream()
            .map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            })
            .forEach(System.out::println);
    }

    @Test
    public void invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newWorkStealingPool();

        Callable<String> task1 = () -> {
            TimeUnit.MILLISECONDS.sleep(200);
            return "task1";
        };
        Callable<String> task2 = () -> {
            TimeUnit.MILLISECONDS.sleep(100);
            return "task2";
        };
        List<Callable<String>> tasks = Arrays.asList(task1, task2);

        // invokeAll will wait for all tasks completed
        // we have control in each future's state
        String result = executor.invokeAny(tasks);
        Assert.assertEquals("task2", result);
    }

}
