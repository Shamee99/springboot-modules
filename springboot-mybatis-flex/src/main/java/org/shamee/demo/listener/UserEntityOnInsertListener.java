package org.shamee.demo.listener;

import com.mybatisflex.annotation.InsertListener;
import org.shamee.demo.entity.User;

public class UserEntityOnInsertListener implements InsertListener {

    /**
     * 重写该方法，自动填充extension字段
     * @param entity 实体类
     */
    @Override
    public void onInsert(Object entity) {
        User user = (User) entity;
        user.setExtension("我是通过@Table注解的OnInsert监听器填充内容");
    }
}
