package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {
    /**
     * 根据分类id查询该分类下的套餐数量
     *
     * @param id 分类id
     * @return 该分类下的套餐数量
     */
    @Select("select count(*) from setmeal where category_id = #{id}")
    int countSetmealByCategoryId(Long id);

    /**
     * 新增套餐
     *
     * @param setmeal 套餐信息
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO 分页查询参数
     * @return 分页查询结果
     */
    Page<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 修改套餐信息
     *
     * @param setmeal 套餐信息
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 根据id查询套餐信息
     *
     * @param id 套餐id
     * @return 套餐信息
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 根据ids批量删除套餐
     *
     * @param ids 套餐ids
     */
    void deleteByIds(List<Long> ids);
}
