package com.example.springbootmapstruct.convert;

import com.example.springbootmapstruct.entity.User;
import com.example.springbootmapstruct.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper( UserConvert.class );

    @Mappings({
            @Mapping(target = "fullName", source = "name"),
            @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd")
    })
    UserVo entity2Vo(User user);

    @Mappings({
            @Mapping(target = "name", source = "fullName"),
            @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd")
    })
    User vo2Entity(UserVo userVo);


}
