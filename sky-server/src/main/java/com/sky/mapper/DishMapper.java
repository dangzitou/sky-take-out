package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    /**
     * 根据分类id查询该分类下的菜品数量
     *
     * @param id 分类id
     * @return 该分类下的菜品数量
     */
    @Select("select count(*) from dish where category_id = #{id}")
    int countDishByCategoryId(Long id);

    /**
     * 新增菜品
     *
     * @param dish 菜品信息
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);
}
