package com.zhcdata.db.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JcMatchLineupInfo info = (JcMatchLineupInfo) o;
        return Objects.equals(id, info.id) &&
                Objects.equals(homeArray, info.homeArray) &&
                Objects.equals(awayArray, info.awayArray) &&
                Objects.equals(homeLineup, info.homeLineup) &&
                Objects.equals(awayLineup, info.awayLineup) &&
                Objects.equals(homeBackup, info.homeBackup) &&
                Objects.equals(awayBackup, info.awayBackup);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, homeArray, awayArray, homeLineup, awayLineup, homeBackup, awayBackup);
    }
}
