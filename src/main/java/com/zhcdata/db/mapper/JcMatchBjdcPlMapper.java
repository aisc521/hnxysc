package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchBjdcPl;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JcMatchBjdcPlMapper extends Mapper<JcMatchBjdcPl> {
    List<JcMatchBjdcPl> queryJcMatchBjdcPlByIssuemAndNoId(@Param("issueNum") String issueNum, @Param("noId") String noId);
}