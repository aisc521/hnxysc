package com.zhcdata.db.mapper;


import com.zhcdata.db.model.JcMatchLineupInfo;
import com.zhcdata.jc.tools.CustomInterfaceMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbJcMatchLineupMapper extends CustomInterfaceMapper<JcMatchLineupInfo> {

    List<JcMatchLineupInfo> queryLineup(@Param("id") String id);

    int insertSelective(JcMatchLineupInfo jcMatchLineupInfo);

    int updateByPrimaryKeySelective(JcMatchLineupInfo jcMatchLineupInfo);

}
