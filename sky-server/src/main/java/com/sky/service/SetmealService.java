package com.sky.service;

import com.sky.dto.SetmealDTO;

public interface SetmealService {
    /**
     * 新增套餐，同时新增套餐和菜品的联系
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);
}
