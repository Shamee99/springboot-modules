package org.shamee.demo.entity;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.core.mask.Masks;
import lombok.*;
import org.shamee.demo.listener.UserEntityOnInsertListener;

import java.io.Serializable;
import java.util.Date;


/**
 * @Table 注解自动映射实体类和表字段
 */
@Data
@Table(value = "t_user")
public class User implements Serializable {

    /**
     * 声明主键ID，并指定生成器为雪花ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * Column声明字段名称，onInsertValue 自动填充时间
     */
    @Column(value = "createdTime", onInsertValue = "now()")
    private Date createdTime;

    @Column(value = "updatedTime", onInsertValue = "now()", onUpdateValue = "now()")
    private Date updatedTime;

    /**
     * Column声明字段名称，isLogicDelete 逻辑删除
     */
    @Column(value = "isDeleted", isLogicDelete = true)
    private Boolean isDeleted = false;

    @Column(value = "userId")
    private String userId;

    @Column(value = "userName")
    @ColumnMask(Masks.CHINESE_NAME)
    private String userName;

    @Column(value = "isUse")
    private Boolean isUse;

    @Column(value = "battleNum")
    private Integer battleNum;

    @Column(value = "atk")
    private Integer atk;


    /**
     * 由于这里会直接拼接成为sql的一部分，因此这里字符串必须添加引号，不然sql执行会出错
     */
    @Column(onInsertValue = "'我是通过@Column注解的onInsertValue属性填充内容'")
    private String extension;
}
