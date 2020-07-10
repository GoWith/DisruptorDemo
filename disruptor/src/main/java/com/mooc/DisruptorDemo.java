package com.mooc;

import com.lmax.disruptor.*;
import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.concurrent.*;

/**
 * @author maoyi
 * @version V1.0
 * date 2020/7/10 2:56 PM
 * Description: TODO
 * https://www.cnblogs.com/kebibuluan/p/7655876.html
 * https://blog.csdn.net/a78270528/article/details/79925404
 */
public class DisruptorDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS= 4;

        final RingBuffer<Order> orderProducer = RingBuffer.createSingleProducer(new EventFactory<Order>() {
            public Order newInstance() {
                return new Order();
            }
        }, BUFFER_SIZE, new YieldingWaitStrategy());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBERS);

        SequenceBarrier sequenceBarrier = orderProducer.newBarrier();

        BatchEventProcessor<Order> orderBatchEventProcessor = new BatchEventProcessor<Order>(orderProducer, sequenceBarrier, new OrderHandler());

        orderProducer.addGatingSequences(orderBatchEventProcessor.getSequence());

        executorService.submit(orderBatchEventProcessor);

        Future<Void> future = executorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                long seq;
                for (int i = 0; i < 1000; i++) {
                    seq = orderProducer.next();
                    orderProducer.get(seq).setAmount(Math.random() * 9999);
                    orderProducer.publish(seq);
                }
                return null;
            }
        });

        future.get();
        Thread.sleep(1000);
        orderBatchEventProcessor.halt();
        executorService.shutdown();
    }
}
