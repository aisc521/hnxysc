package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_champion_runner_odds")
public class JcChampionRunnerOdds implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 玩法表id
     */
    @Column(name = "odds_type")
    private Long oddsType;

    /**
     * 玩法类型
     */
    @Column(name = "game_type")
    private Long gameType;

    /**
     * 玩法类型代码(对应玩法表)
     */
    @Column(name = "play_code")
    private String playCode;

    /**
     * 编号
     */
    @Column(name = "match_id")
    private String matchId;

    /**
     * 球队
     */
    private String team;

    /**
     * 奖金(赔率)
     */
    private String odds;

    /**
     * 是否已淘汰(1 淘汰  0未淘汰)
     */
    @Column(name = "is_end")
    private Long isEnd;

    /**
     * 状态标识,是否过期 1未过期0已过期
     */
    private Long status;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取玩法表id
     *
     * @return odds_type - 玩法表id
     */
    public Long getOddsType() {
        return oddsType;
    }

    /**
     * 设置玩法表id
     *
     * @param oddsType 玩法表id
     */
    public void setOddsType(Long oddsType) {
        this.oddsType = oddsType;
    }

    /**
     * 获取玩法类型
     *
     * @return game_type - 玩法类型
     */
    public Long getGameType() {
        return gameType;
    }

    /**
     * 设置玩法类型
     *
     * @param gameType 玩法类型
     */
    public void setGameType(Long gameType) {
        this.gameType = gameType;
    }

    /**
     * 获取玩法类型代码(对应玩法表)
     *
     * @return play_code - 玩法类型代码(对应玩法表)
     */
    public String getPlayCode() {
        return playCode;
    }

    /**
     * 设置玩法类型代码(对应玩法表)
     *
     * @param playCode 玩法类型代码(对应玩法表)
     */
    public void setPlayCode(String playCode) {
        this.playCode = playCode;
    }

    /**
     * 获取编号
     *
     * @return match_id - 编号
     */
    public String getMatchId() {
        return matchId;
    }

    /**
     * 设置编号
     *
     * @param matchId 编号
     */
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取球队
     *
     * @return team - 球队
     */
    public String getTeam() {
        return team;
    }

    /**
     * 设置球队
     *
     * @param team 球队
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * 获取奖金(赔率)
     *
     * @return odds - 奖金(赔率)
     */
    public String getOdds() {
        return odds;
    }

    /**
     * 设置奖金(赔率)
     *
     * @param odds 奖金(赔率)
     */
    public void setOdds(String odds) {
        this.odds = odds;
    }

    /**
     * 获取是否已淘汰(1 淘汰  0未淘汰)
     *
     * @return is_end - 是否已淘汰(1 淘汰  0未淘汰)
     */
    public Long getIsEnd() {
        return isEnd;
    }

    /**
     * 设置是否已淘汰(1 淘汰  0未淘汰)
     *
     * @param isEnd 是否已淘汰(1 淘汰  0未淘汰)
     */
    public void setIsEnd(Long isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * 获取状态标识,是否过期 1未过期0已过期
     *
     * @return status - 状态标识,是否过期 1未过期0已过期
     */
    public Long getStatus() {
        return status;
    }

    /**
     * 设置状态标识,是否过期 1未过期0已过期
     *
     * @param status 状态标识,是否过期 1未过期0已过期
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}