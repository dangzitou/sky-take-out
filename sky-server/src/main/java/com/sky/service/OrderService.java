package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 查询历史订单
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult getHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单详情
     * @param id
     * @return
     */
    OrderVO getOrderDetailById(Long id);

    /**
     * 用户取消订单
     * @param id
     */
    void cancleOrder(Long id);

    /**
     * 用户再次下单
     * @param id
     */
    void repetition(Long id);

    /**
     * 管理员端条件查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult getOrdersByCondition(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 订单统计
     * @return
     */
    OrderStatisticsVO orderStatistics();

    /**
     * 确认订单
     * @param ordersConfirmDTO
     */
    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     * @param ordersRejectionDTO
     */
    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 取消订单
     * @param ordersCancelDTO
     */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    /**
     * 订单派送
     * @param id
     */
    void deliveryOrder(Long id);

    /**
     * 完成订单
     * @param id
     */
    void completeOrder(Long id);
}
