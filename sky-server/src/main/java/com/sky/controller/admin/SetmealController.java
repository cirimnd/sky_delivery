package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "套餐相关接口")
@RequestMapping("/admin/setmeal")
@Slf4j
@RequiredArgsConstructor
public class SetmealController {

    private final SetmealService setmealService;
    private final SetmealMapper setmealMapper;

    /**
     * 新增套餐及其与菜品的关系
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐及其与菜品的关系")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询，参数为：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteBatch(@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 启用禁用套餐
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用禁用套餐：{}，{}", status, id);
        setmealService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id获取套餐及其菜品关系
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取套餐及其菜品关系")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据id获取套餐及其菜品关系:{}",id);
        SetmealVO setmealVO = setmealService.getByidWithDish(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐及其菜品
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success();
    }
}
