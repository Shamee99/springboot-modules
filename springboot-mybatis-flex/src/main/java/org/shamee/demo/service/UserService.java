package org.shamee.demo.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.shamee.demo.dao.UserDao;
import org.shamee.demo.entity.User;
import org.shamee.demo.entity.table.UserTableDef;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserDao, User> {

    /**
     * 查询全部
     * @return
     */
    public List<User> selectAll(){
        return this.getMapper().selectAll();
    }

    /**
     * 根据userId获取User数据
     * @param userId
     * @return
     */
    public User listById(String userId){
        QueryWrapper wrapper = QueryWrapper.create()
                // 这里可以指定查询字段
                .select(UserTableDef.USER.USER_ID, UserTableDef.USER.USER_NAME, UserTableDef.USER.ATK)
                // sql from表名
                .from(User.class)
                // 查询条件，这里的UserTableDef.USER代码自动编译生成，类似lombok
                .where(UserTableDef.USER.USER_ID.eq(userId));
        return this.getMapper().selectOneByQuery(wrapper);
    }


    /**
     * 新增
     * @param user
     */
    public void insert(User user){
        this.getMapper().insert(user);
    }

    /**
     * 更新User
     * @param user
     */
    public void updateEntity(User user){
        this.getMapper().update(user);
    }

    /**
     * 局部更新
     * @param userId
     * @param userName
     */
    public void updateRow(String userId, String userName){
        UpdateChain.of(User.class)
                .set(User::getUserName, userName)
                .where(User::getUserId).eq(userId).update();
    }

    /**
     * 删除
     * @param userName
     */
    public void deleteByWrapper(String userName){
        QueryWrapper queryWrapper = QueryWrapper.create().where(UserTableDef.USER.USER_NAME.eq(userName));
        this.getMapper().deleteByQuery(queryWrapper);
    }

}
