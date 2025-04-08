package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐，同时新增套餐和菜品的联系
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    void startOrStop(Integer status, Long id);


    /**
     * 根据id查询菜品及其关系
     * @param id
     * @return
     */
    SetmealVO getByidWithDish(Long id);

    /**
     * 更新套餐及其菜品
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);
}
