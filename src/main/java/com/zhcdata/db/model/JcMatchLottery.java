package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_match_lottery")
public class JcMatchLottery implements Serializable {
    @Column(name = "ID")
    private Long id;

    /**
     * 彩种名称
     */
    @Column(name = "LOTTERY_NAME")
    private String lotteryName;

    /**
     * 彩种标识
     */
    @Column(name = "LOTTERY")
    private String lottery;

    /**
     * 【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     */
    @Column(name = "ISSUE_NUM")
    private String issueNum;

    /**
     * 【场次】用于匹配彩票赛事场次/赔率
     */
    @Column(name = "NO_ID")
    private String noId;

    /**
     * 【球探网比赛ID】用于匹配球探网比赛数据
     */
    @Column(name = "ID_BET007")
    private Long idBet007;

    /**
     * 【是否与球探的比赛主客相反】true:相反，false:相同
     */
    @Column(name = "TURN")
    private String turn;

    /**
     * 记录ID
     */
    @Column(name = "RECORD_ID")
    private Long recordId;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 2五大联赛 3.北单竟彩4其它
     */
    private String type;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
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
     * 获取彩种名称
     *
     * @return LOTTERY_NAME - 彩种名称
     */
    public String getLotteryName() {
        return lotteryName;
    }

    /**
     * 设置彩种名称
     *
     * @param lotteryName 彩种名称
     */
    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    /**
     * 获取彩种标识
     *
     * @return LOTTERY - 彩种标识
     */
    public String getLottery() {
        return lottery;
    }

    /**
     * 设置彩种标识
     *
     * @param lottery 彩种标识
     */
    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    /**
     * 获取【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     *
     * @return ISSUE_NUM - 【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     */
    public String getIssueNum() {
        return issueNum;
    }

    /**
     * 设置【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     *
     * @param issueNum 【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     */
    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum;
    }

    /**
     * 获取【场次】用于匹配彩票赛事场次/赔率
     *
     * @return NO_ID - 【场次】用于匹配彩票赛事场次/赔率
     */
    public String getNoId() {
        return noId;
    }

    /**
     * 设置【场次】用于匹配彩票赛事场次/赔率
     *
     * @param noId 【场次】用于匹配彩票赛事场次/赔率
     */
    public void setNoId(String noId) {
        this.noId = noId;
    }

    /**
     * 获取【球探网比赛ID】用于匹配球探网比赛数据
     *
     * @return ID_BET007 - 【球探网比赛ID】用于匹配球探网比赛数据
     */
    public Long getIdBet007() {
        return idBet007;
    }

    /**
     * 设置【球探网比赛ID】用于匹配球探网比赛数据
     *
     * @param idBet007 【球探网比赛ID】用于匹配球探网比赛数据
     */
    public void setIdBet007(Long idBet007) {
        this.idBet007 = idBet007;
    }

    /**
     * 获取【是否与球探的比赛主客相反】true:相反，false:相同
     *
     * @return TURN - 【是否与球探的比赛主客相反】true:相反，false:相同
     */
    public String getTurn() {
        return turn;
    }

    /**
     * 设置【是否与球探的比赛主客相反】true:相反，false:相同
     *
     * @param turn 【是否与球探的比赛主客相反】true:相反，false:相同
     */
    public void setTurn(String turn) {
        this.turn = turn;
    }

    /**
     * 获取记录ID
     *
     * @return RECORD_ID - 记录ID
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * 设置记录ID
     *
     * @param recordId 记录ID
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
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
     * @return CREATE_TIME - 创建时间
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

    /**
     * 获取2五大联赛 3.北单竟彩4其它
     *
     * @return type - 2五大联赛 3.北单竟彩4其它
     */
    public String getType() {
        return type;
    }

    /**
     * 设置2五大联赛 3.北单竟彩4其它
     *
     * @param type 2五大联赛 3.北单竟彩4其它
     */
    public void setType(String type) {
        this.type = type;
    }
}