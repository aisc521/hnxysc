package com.zhcdata.jc.service;

import com.github.pagehelper.PageInfo;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ExpertInfoBdDto;
import com.zhcdata.jc.dto.ExpertInfoDto;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface TbJcExpertService {
    /**
     * 热门专家
     * @param s
     * @return
     */
    List<ExpertInfo> queryHotExperts(String s);

    /**
     * 查询专家详情
     * @param expertId
     * @return
     */
    ExpertInfo queryExpertDetails(String expertId);

    /**
     * 所有专家列表
     * @return
     */
    List<ExpertInfo> queryExperts();

    /**
     * 专家榜
     * @param type
     * @return
     */
    PageInfo<ExpertInfoBdDto> queryExpertsByType(String type, Integer pageNo, Integer pageAmouont);

    List<ExpertInfo> query(String userId);

    TbJcExpert queryExpertDetailsById(int id);

    void updatePopAddOne(int eid,int pop);

    int updateByExample(TbJcExpert tbJcExpert, Example example1);

    ExpertInfo queryExpertDetailsAndUser(String expertId, String userId);

    List<ExpertInfoDto> queryExpertInfo(String userId);
}
