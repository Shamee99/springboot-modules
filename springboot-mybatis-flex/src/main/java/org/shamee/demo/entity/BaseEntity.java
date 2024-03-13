package org.shamee.demo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 持久化实体基类
 * @author 彭耀煌
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    @Column(onInsertValue = "now()")
    private Date createdTime;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private Date updatedTime;

    @Column(isLogicDelete = true)
    private Boolean isDeleted = false;

}
