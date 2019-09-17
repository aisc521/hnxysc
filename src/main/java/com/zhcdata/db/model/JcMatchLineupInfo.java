package com.zhcdata.db.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "jc_match_lineup")
public class JcMatchLineupInfo {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "HOME_ARRAY")
    private String homeArray;
    @Column(name = "AWAY_ARRAY")
    private String awayArray;
    @Column(name = "HOME_LINEUP")
    private String homeLineup;
    @Column(name = "AWAY_LINEUP")
    private String awayLineup;
    @Column(name = "HOME_BACKUP")
    private String homeBackup;
    @Column(name = "AWAY_BACKUP")
    private String awayBackup;
    @Column(name = "CREATE_TIME")
    private String createTime;
    @Column(name = "UPDATE_TIME")
    private String updateTime;
}
