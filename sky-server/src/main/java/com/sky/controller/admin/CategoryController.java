package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品分类管理
 */
@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 删除菜品分类
     *
     * @param categoryDTO
     * @return
     */
    @DeleteMapping
    public Result deleteCategoryById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.deleteCategoryById(id);
        return Result.success();
    }

    /**
     * 修改菜品分类状态
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateCategoryStatus(@PathVariable Integer status, @RequestParam("id") Long id) {
        log.info("修改分类状态：{}, {}", status, id);
        categoryService.updateCategoryStatus(status, id);
        return Result.success();
    }

    /**
     * 修改菜品分类
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类信息：{}", categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 分页查询菜品分类
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类：{}", categoryPageQueryDTO);
        PageResult page = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(page);
    }

    /**
     * 根据类型查询分类列表
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result listCategory(Integer type) {
        List<Category> list = categoryService.listCategory(type);
        return Result.success(list);
    }
}
