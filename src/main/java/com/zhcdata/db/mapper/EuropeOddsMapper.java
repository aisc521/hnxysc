package com.zhcdata.db.mapper;

import com.zhcdata.db.model.EuropeOdds;
import com.zhcdata.jc.dto.AnalysisDto;
import com.zhcdata.jc.dto.AnalysisMatchDto;
import com.zhcdata.jc.dto.SimplifyOdds;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EuropeOddsMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(EuropeOdds record);

    int insertSelective(EuropeOdds record);

    EuropeOdds selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(EuropeOdds record);

    int updateByPrimaryKey(EuropeOdds record);

    EuropeOdds selectByMidAndCpyAnd(@Param("mid") String mid, @Param("cpy") String cpy);

    List<EuropeOdds> selectByMid(String mid);

    void deleteByMid(String mid);

    AnalysisDto queryOddsByCompanyAndMatch(@Param("matchId") Integer matchId, @Param("companyId") Integer companyId,
                                           @Param("matchType")Integer matchType,@Param("beginDate")String beginDate,
                                           @Param("satOdds") Double satOdds,@Param("endOdds") Double endOdds);

    List<AnalysisMatchDto> querySameOddsMatchByOdds(@Param("companyId")Integer companyId,@Param("matchType")Integer matchType,
                                                    @Param("satOdds") Double satOdds,@Param("endOdds") Double endOdds,
                                                    @Param("beginDate") String beginDate,@Param("satWinMod") String satWinMod,
                                                    @Param("satLoseMod") String satLoseMod,@Param("satFlatMod") String satFlatMod,
                                                    @Param("satWin") Float satWin,@Param("satLose") Float satLose,
                                                    @Param("satFlat") Float satFlat,@Param("oddsId") Integer oddsId);

    List<AnalysisMatchDto> querySameOddsMatchByFlagAndOdds(@Param("companyId")Integer companyId,@Param("matchType")Integer matchType,
                                                    @Param("satChange") Double satChange,@Param("endChange") Double endChange,
                                                    @Param("beginDate") String beginDate,@Param("hgFlag") String hgFlag,
                                                    @Param("satOdd") Float satOdd,@Param("endOdd") Float endOdd);
}