package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void addToCart(ShoppingCartDTO shoppingCartDTO) {
        //判断是否存在购物车中
        ShoppingCart shoppingCartItem = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCartItem);
        Long userId = BaseContext.getCurrentId();
        shoppingCartItem.setUserId(userId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.list(shoppingCartItem);
        //如果已经存在，数量加1
        if(shoppingCarts != null && !shoppingCarts.isEmpty()) {
            ShoppingCart cart = shoppingCarts.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateById(cart);
        }
        //如果不存在，添加到购物车，数量默认为1
        else {
            //判断本次添加的是套餐还是菜品
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null) {
                //添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCartItem.setName(dish.getName());
                shoppingCartItem.setImage(dish.getImage());
                shoppingCartItem.setAmount(dish.getPrice());
            } else {
                //添加的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCartItem.setName(setmeal.getName());
                shoppingCartItem.setImage(setmeal.getImage());
                shoppingCartItem.setAmount(setmeal.getPrice());
            }
            shoppingCartItem.setNumber(1);
            shoppingCartItem.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCartItem);
        }
    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * 清空购物车
     */
    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.cleanByUserId(BaseContext.getCurrentId());
    }
}
