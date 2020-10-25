package com.loiterer.listener.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loiterer.listener.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XieZhiJie
 * @date 2020/10/24 21:21
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有user
     * @return 返回带有所有user的list
     */
    List<User> selectAll();

}
