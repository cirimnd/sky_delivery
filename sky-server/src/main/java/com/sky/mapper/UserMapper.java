package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    //根据openid查询
    @Select("select * from user where openid=#{openId}")
    public User getByOpenId(String openId);

    /**
     * 插入数据
     * @param user
     */
//    @AutoFill(OperationType.INSERT)
    void insert(User user);

    @Select("select * from user where id=#{userId}")
    User getById(Long userId);

    /**
     * 根据动态条件统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
