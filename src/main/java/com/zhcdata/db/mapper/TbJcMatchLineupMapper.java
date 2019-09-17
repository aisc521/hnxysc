package com.zhcdata.db.mapper;


import com.zhcdata.db.model.JcMatchLineupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbJcMatchLineupMapper {

    List<JcMatchLineupInfo> queryLineup(@Param("id") String id);

    int insertSelective(JcMatchLineupInfo jcMatchLineupInfo);

}
