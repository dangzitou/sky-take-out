package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品ids查询对应的套餐ids
     *
     * @param dishIds 菜品ids
     * @return 套餐ids
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量保存套餐菜品关系
     *
     * @param setmealDishes 套餐菜品关系集合
     * @param setmealId     套餐id
     */
    void saveSetmealDishBatch(List<SetmealDish> setmealDishes, Long setmealId);

    /**
     * 根据套餐id查询对应的套餐菜品关系
     *
     * @param id 套餐id
     * @return 套餐菜品关系集合
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);

    /**
     * 根据套餐ids批量删除套餐菜品关系
     *
     * @param ids 套餐ids
     */
    void deleteBySetmealIds(List<Long> setmealIds);

    /**
     * 根据套餐id删除对应的套餐菜品关系
     *
     * @param setmealId 套餐id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);
}
