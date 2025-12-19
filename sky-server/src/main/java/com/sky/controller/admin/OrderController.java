package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 管理员端条件查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/conditionSearch")
    public Result conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("管理员端条件查询订单:{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.getOrdersByCondition(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 订单统计
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/statistics")
    public Result statistics() {
        log.info("订单统计");
        OrderStatisticsVO orderStatisticsVO = orderService.orderStatistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 根据id查询订单详情
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    public Result getOrderDetailById(@PathVariable Long id) {
        log.info("根据id查询订单详情：{}", id);
        return Result.success(orderService.getOrderDetailById(id));
    }

    /**
     * 确认订单
     * @param ordersConfirmDTO
     * @return
     */
    @PutMapping("/confirm")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("接单：{}", ordersConfirmDTO);
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * 拒单
     * @param ordersRejectionDTO
     * @return
     */
    @PutMapping("/rejection")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("拒单：{}", ordersRejectionDTO);
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 取消订单
     * @param ordersCancelDTO
     * @return
     */
    @PutMapping("/cancel")
    public Result cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        log.info("取消订单：{}", ordersCancelDTO);
        orderService.cancelOrder(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 订单派送
     * @param id
     * @return
     */
    @PutMapping("/delivery/{id}")
    public Result deliveryOrder(@PathVariable Long id) {
        log.info("订单派送,id={}", id);
        orderService.deliveryOrder(id);
        return Result.success();
    }

    /**
     * 订单完成
     * @param id
     * @return
     */
    @PutMapping("/complete/{id}")
    public Result completeOrder(@PathVariable Long id) {
        log.info("订单完成,id={}", id);
        orderService.completeOrder(id);
        return Result.success();
    }
}
