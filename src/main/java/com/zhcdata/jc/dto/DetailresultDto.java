package com.zhcdata.jc.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/31 16:27
 */
@Getter
@Setter
public class DetailresultDto {

    private Integer id;
    private Integer scheduleid;
    private Short happentime;
    private Integer teamid;
    private String playername;
    private Integer playerid;
    private Short kind;
    private Date modifytime;
    private String playernameE;
    private String playernameJ;
    private Integer playeridIn;
    private String teamname;
    private String pname;
}
