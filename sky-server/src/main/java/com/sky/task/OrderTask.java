package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ?") // 每分钟执行一次
    //@Scheduled(cron = "1/5 * * * * ?") // 每5秒钟执行一次（测试用）
    public void processTimeoutOrders() {
        // 这里是处理超时订单的逻辑
        log.info("定时处理超时订单:{}", LocalDateTime.now());
        LocalDateTime timeoutTime = LocalDateTime.now().minusMinutes(15); // 超时时间为15分钟
        List<Orders> orders = orderMapper.getTimeoutOrdersByStatus(Orders.PENDING_PAYMENT, timeoutTime);

        if(orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED); // 设置订单状态为已取消
                order.setCancelTime(LocalDateTime.now()); // 设置取消时间
                order.setCancelReason("订单超时未支付，系统自动取消"); // 设置取消原因
                orderMapper.update(order); // 更新订单状态
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * *") // 每天凌晨一点执行一次
    //@Scheduled(cron = "0/5 * * * * ?") // 每5秒钟执行一次（测试用）
    public void processDeliveryOrders() {
        // 处理超时订单
        log.info("定时处理超时派送订单:{}", LocalDateTime.now());
        LocalDateTime timeoutTime = LocalDateTime.now().minusMinutes(60);
        List<Orders> orders = orderMapper.getTimeoutOrdersByStatus(Orders.DELIVERY_IN_PROGRESS,timeoutTime);

        if(orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED); // 设置订单状态为已完成
                order.setDeliveryTime(LocalDateTime.now()); // 设置完成时间
                orderMapper.update(order); // 更新订单状态
            }
        }
    }
}
