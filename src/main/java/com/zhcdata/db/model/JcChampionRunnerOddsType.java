package com.zhcdata.db.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_champion_runner_odds_type")
public class JcChampionRunnerOddsType implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 玩法  1冠军 2 冠亚军
     */
    @Column(name = "game_type")
    private Long gameType;

    /**
     * 玩法类型 ，对应竞彩冠亚军赔率表玩法字段自定义代码
     */
    @Column(name = "play_code")
    private String playCode;

    /**
     * 玩法类型 ，对应竞彩冠亚军赔率表玩法字段
     */
    @Column(name = "play_type_name")
    private String playTypeName;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 状态 0 未开始 1 进行中 2 已结束
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
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取玩法  1冠军 2 冠亚军
     *
     * @return game_type - 玩法  1冠军 2 冠亚军
     */
    public Long getGameType() {
        return gameType;
    }

    /**
     * 设置玩法  1冠军 2 冠亚军
     *
     * @param gameType 玩法  1冠军 2 冠亚军
     */
    public void setGameType(Long gameType) {
        this.gameType = gameType;
    }

    /**
     * 获取玩法类型 ，对应竞彩冠亚军赔率表玩法字段自定义代码
     *
     * @return play_code - 玩法类型 ，对应竞彩冠亚军赔率表玩法字段自定义代码
     */
    public String getPlayCode() {
        return playCode;
    }

    /**
     * 设置玩法类型 ，对应竞彩冠亚军赔率表玩法字段自定义代码
     *
     * @param playCode 玩法类型 ，对应竞彩冠亚军赔率表玩法字段自定义代码
     */
    public void setPlayCode(String playCode) {
        this.playCode = playCode;
    }

    /**
     * 获取玩法类型 ，对应竞彩冠亚军赔率表玩法字段
     *
     * @return play_type_name - 玩法类型 ，对应竞彩冠亚军赔率表玩法字段
     */
    public String getPlayTypeName() {
        return playTypeName;
    }

    /**
     * 设置玩法类型 ，对应竞彩冠亚军赔率表玩法字段
     *
     * @param playTypeName 玩法类型 ，对应竞彩冠亚军赔率表玩法字段
     */
    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取状态 0 未开始 1 进行中 2 已结束
     *
     * @return status - 状态 0 未开始 1 进行中 2 已结束
     */
    public Long getStatus() {
        return status;
    }

    /**
     * 设置状态 0 未开始 1 进行中 2 已结束
     *
     * @param status 状态 0 未开始 1 进行中 2 已结束
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