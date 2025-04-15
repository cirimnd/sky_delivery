package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，定时处理订单状态
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderTask {

    private final OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * *")
    public void processTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        int status=Orders.PENDING_PAYMENT;
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        List<Orders> list= orderMapper.getByStatusAndOrderTimeLT(status,time);
        if(list!=null&&list.size()>0){
            for(Orders order:list){
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 处理一直处于派送中
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void processDeliveryOrder(){
        log.info("定时处理处于派送中的订单:{}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if(list!=null&&list.size()>0){
            for(Orders order:list){
                order.setStatus(Orders.COMPLETED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

}
