package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 分页查询参数
     * @return 分页查询结果
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品信息
     *
     * @param id 菜品id
     * @return 菜品信息
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据id删除菜品
     *
     * @param dishId 菜品id
     */
    @Delete("delete from dish where id = #{dishId}")
    void deleteById(Long dishId);

    /**
     * 根据ids批量删除菜品
     *
     * @param ids 菜品ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 更新菜品信息
     *
     * @param dish 菜品信息
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据分类id查询该分类下的菜品信息
     *
     * @param categoryId 分类id
     * @return 该分类下的菜品信息
     */
    @Select("select * from dish where category_id = #{categoryId} and status = 1")
    List<Dish> getByCategoryId(Long categoryId);

    List<Dish> list(Dish dish);
}
