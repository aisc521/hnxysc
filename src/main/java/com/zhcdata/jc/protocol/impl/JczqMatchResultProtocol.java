package com.zhcdata.jc.protocol.impl;

import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.JcSchedulespService;
import com.zhcdata.jc.tools.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/16 14:55
 * JDK version : JDK1.8
 * Comments :
 * 给国彩公共号开奖中心用
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("20200902")
public class JczqMatchResultProtocol implements BaseProtocol {
  @Resource
  private CommonUtils commonUtils;
  @Resource
  JcSchedulespService JcSchedulespService;
  @Override
  public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
    Map<String, Object> map = new HashMap<String, Object>(2);

    if (!commonUtils.validParamExist(map, paramMap, "date", ProtocolCodeMsg.BDJC_DAY)) {
        return map;
    }
    if (!commonUtils.validParamExist(map, paramMap, "flag", ProtocolCodeMsg.BDJC_FLAG)) {
      return map;
    }
    return null;
  }

  @Override
  public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {

    String date = paramMap.get("date");
    //查询竟彩的数据
    List<Map<String, String>> list =  JcSchedulespService.queryJczqListReuslt(date);
    //需要对数据做处理给第三国彩公众号用
    List<Map<String,String>> returnList = new ArrayList<>();
    int openNumTatol = 0 ;
    for(Map<String, String> map :list){
      try{
        Map<String,String> reMap = new HashMap<>();
        reMap.put("issueNum",map.get("issueNum"));//周几00几
        reMap.put("matchName",map.get("matchName"));//联赛名称
        reMap.put("matchTime",map.get("matchTime"));//比赛时间
        reMap.put("hostname",map.get("hostname"));//主队名称
        reMap.put("guestName",map.get("guestName"));//客队名称
        String homeScore= map.get("HomeScore");
        String guestScore = map.get("GuestScore");
        String homeHalfScore = map.get("HomeHalfScore");
        String guestHalfScore = map.get("GuestHalfScore");
        String polyGoal = map.get("PolyGoal");
        reMap.put("hostScore",homeScore+":"+guestScore);//全场比分
        reMap.put("guestScore",homeHalfScore+":"+guestHalfScore);//半场比分

        String reslutPl[] = matchReslutHandle("BQC",homeScore,guestScore,homeHalfScore,guestHalfScore);
        reMap.put("bqcKey",reslutPl[0]);//半全场
        reMap.put("bqcVal",StringUntil(map.get(reslutPl[1])));

        reslutPl = matchReslutHandle("GOLF",homeScore,guestScore,homeHalfScore,guestHalfScore);
        reMap.put("goalKey",reslutPl[0]);//进球数
        reMap.put("goalVal",StringUntil(map.get(reslutPl[1])));

        int intHomeScore = Integer.parseInt(homeScore)+Integer.parseInt(polyGoal);
        reslutPl = matchReslutHandle("RQSPF",intHomeScore+"",guestScore,homeHalfScore,guestHalfScore);
        reMap.put("rqspfKey","("+polyGoal+")"+reslutPl[0]);//(-1)平 让球胜名负
        reMap.put("rqspfVal",StringUntil(map.get(reslutPl[1])));

        reslutPl = matchReslutHandle("BF",homeScore,guestScore,homeHalfScore,guestHalfScore);
        reMap.put("scoreKey",reslutPl[0]);//比分
        reMap.put("scoreVal",StringUntil(map.get(reslutPl[1])));

        reslutPl = matchReslutHandle("SPF",homeScore,guestScore,homeHalfScore,guestHalfScore);
        reMap.put("spfKey",reslutPl[0]); //胜名负
        reMap.put("spfVal",StringUntil(map.get(reslutPl[1])));
        returnList.add(reMap);
      }catch (Exception e){
        log.error(map.get("issueNum")+" 数据异常",e);
      }

    }
    openNumTatol =  JcSchedulespService.queryTodayMatchCount(date);

    Map<String, Object>  returnMap = new HashMap<String,Object>();

    returnMap.put("pageTotal", "1");
    returnMap.put("openNum", list.size());//已开奖的场次

    returnMap.put("openNumTatol", openNumTatol+"");//需要的开奖场次
    returnMap.put("pageNo", "1");
    returnMap.put("content",returnList);
    returnMap.put("dates",date);
    return returnMap;
  }
  public String StringUntil(String value){
    if(StringUtils.isBlank(value)){
      value = "0";
    }
    double d = new BigDecimal(value).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
    DecimalFormat df = new DecimalFormat("##0.00");
    return df.format(d);
  }
  /**
   *
   * @param type  比赛玩法
   * @param hostScore  如果是半全场是半场主队比分
   * @param guestScore 如果是半全场是客队半场比分
   * @return
   */
  //胜平负
  private String SPFARLT []= {"胜","平","负"};
  private String SPFPL [] = {"sf3","sf1","sf0"};
  private String RQSPFPL [] = {"wl3","wl1","wl0"};
  //比分
  private String BFARLT [] = {"1:0","2:0","2:1","3:0","3:1","3:2","4:0","4:1","4:2","5:0","5:1","5:2","胜其他",
                                "0:0","1:1","2:2","3:3","平其他",
                                "0:1","0:2","1:2","0:3","1:3","2:3","0:4","1:4","2:4","0:5","1:5","2:5","负其他"
                                };
  private String BFARLTPL[] = {"sw10","sw20","sw21","sw30","sw31","sw32","sw40","sw41","sw42","sw50","sw51","sw52","sw5",
                                "sd00","sd11","sd22","sd33","sd4",
                                "sl01","sl02","sl12","sl03","sl13","sl23","sl04","sl14","sl24","sl05","sl15","sl25","sl5"};
  //总进球
  private String GOLFARLT [] = {"0","1","2","3","4","5","6","7+"};
  private String GOLFARLTPL [] = {"t0","t1","t2","t3","t4","t5","t6","t7"};

  //半全场
  private String BQCARLT[] = {"胜胜","胜平","胜负","平胜","平平","平负","负胜","负平","负负"};
  private String BQCARLTPL[] = {"ht33","ht31","ht30","ht13","ht11","ht10","ht03","ht01","ht00"};
  public String [] matchReslutHandle(String type ,String hostScore,String guestScore,String homeHalfScore,String guestHalfScore){

      int intHostScore = Integer.parseInt(hostScore);
      int intGuestScore = Integer.parseInt(guestScore);
      String reslut = "";
      String matchReslutPl [] = new String[2];
      int index = 0;
      if("SPF".equals(type)||"RQSPF".equals(type)){  //胜平负
          if(intHostScore>intGuestScore){
            reslut = "胜";
          }
          else if(intHostScore==intGuestScore){
            reslut = "平";
          }
          else{
            reslut = "负";
          }
          if("SPF".equals(type)){
            index = ArrayUtils.indexOf(SPFARLT,reslut);
            matchReslutPl[1] =SPFPL[index];
          }
          else{
            index = ArrayUtils.indexOf(SPFARLT,reslut);
            matchReslutPl[1] =RQSPFPL[index];
          }
          matchReslutPl[0] = reslut;


          return matchReslutPl;
      }
      else if("BF".equals(type)){ //比分
        reslut = hostScore+":"+guestScore;
        index = ArrayUtils.indexOf(BFARLT,reslut);
        if(index==-1){
          if(intHostScore>intGuestScore){
            reslut = "胜其他";
          }
          else if(intHostScore==intGuestScore){
            reslut = "平其他";
          }
          else{
            reslut = "负其他";
          }
          index = ArrayUtils.indexOf(BFARLT,reslut);
        }
        matchReslutPl[0] = reslut;
        matchReslutPl[1] =BFARLTPL[index];

        return matchReslutPl;
      }
      else if("GOLF".equals(type)){//进球数
        int total = intHostScore+intGuestScore;
        reslut = total+"";
        if(total>6){
          reslut = "7+";
        }
        index = ArrayUtils.indexOf(GOLFARLT,reslut);
        matchReslutPl[0] = reslut;
        matchReslutPl[1] =GOLFARLTPL[index];
        return matchReslutPl;
      }
      else if("BQC".equals(type)){//半全场
        int intHomeHalfScore = Integer.parseInt(homeHalfScore);
        int intGuestHalfScore= Integer.parseInt(guestHalfScore);
        String qc="";
        String bc = "";
        if(intHostScore>intGuestScore){
          qc = "胜";
        }
        else if(intHostScore==intGuestScore){
          qc = "平";
        }
        else{
          qc = "负";
        }
        if(intHomeHalfScore>intGuestHalfScore){
          bc = "胜";
        }
        else if(intHomeHalfScore==intGuestHalfScore){
          bc = "平";
        }
        else{
          bc = "负";
        }
        reslut= bc+qc;
        index = ArrayUtils.indexOf(BQCARLT,reslut);
        matchReslutPl[0] = reslut;
        matchReslutPl[1] =BQCARLTPL[index];
        return matchReslutPl;
      }
      return null;
  }
  public static void main(String args[]){
    System.out.println(Integer.parseInt("2")+Integer.parseInt("-1"));
  }
}
