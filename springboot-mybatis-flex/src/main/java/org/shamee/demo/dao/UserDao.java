package org.shamee.demo.dao;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.shamee.demo.entity.User;
@Mapper
public interface UserDao extends BaseMapper<User> {



}
