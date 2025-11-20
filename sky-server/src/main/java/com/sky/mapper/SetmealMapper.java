package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
