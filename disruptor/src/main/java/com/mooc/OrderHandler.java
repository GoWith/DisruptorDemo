package com.mooc;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * @author maoyi
 * @version V1.0
 * date 2020/7/10 3:01 PM
 * Description: TODO
 */
public class OrderHandler implements EventHandler<Order> ,WorkHandler<Order>{

    public void onEvent(Order order, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(sequence);
        this.onEvent(order);
    }

    public void onEvent(Order order) throws Exception {
        order.setId(UUID.randomUUID().toString());
        System.out.println("handle over.");
    }
}
