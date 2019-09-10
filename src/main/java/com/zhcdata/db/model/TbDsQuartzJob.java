package com.zhcdata.db.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_ds_quartz_job")
public class TbDsQuartzJob implements Serializable {
    private static final long serialVersionUID = 7775182837707043608L;
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private Integer id;

    /**
     * 任务名
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 任务组
     */
    @Column(name = "JOB_GROUP")
    private String jobGroup;

    /**
     * 说明
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 任务执行类
     */
    @Column(name = "JOB_CLASS")
    private String jobClass;

    /**
     * cron表达式
     */
    @Column(name = "CRON")
    private String cron;

    /**
     * 任务执行参数Json格式
     */
    @Column(name = "JOB_DATA_MAP")
    private String jobDataMap;

    /**
     * 启动运行
     */
    @Column(name = "START_RUN")
    private Boolean startRun;

    /**
     * 是否删除
     */
    @Column(name = "DEL")
    private Boolean del;

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
     * 获取主键
     *
     * @return ID - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务名
     *
     * @return JOB_NAME - 任务名
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置任务名
     *
     * @param jobName 任务名
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取任务组
     *
     * @return JOB_GROUP - 任务组
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * 设置任务组
     *
     * @param jobGroup 任务组
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 获取说明
     *
     * @return REMARK - 说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置说明
     *
     * @param remark 说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取任务执行类
     *
     * @return JOB_CLASS - 任务执行类
     */
    public String getJobClass() {
        return jobClass;
    }

    /**
     * 设置任务执行类
     *
     * @param jobClass 任务执行类
     */
    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    /**
     * 获取cron表达式
     *
     * @return CRON - cron表达式
     */
    public String getCron() {
        return cron;
    }

    /**
     * 设置cron表达式
     *
     * @param cron cron表达式
     */
    public void setCron(String cron) {
        this.cron = cron;
    }

    /**
     * 获取任务执行参数Json格式
     *
     * @return JOB_DATA_MAP - 任务执行参数Json格式
     */
    public String getJobDataMap() {
        return jobDataMap;
    }

    /**
     * 设置任务执行参数Json格式
     *
     * @param jobDataMap 任务执行参数Json格式
     */
    public void setJobDataMap(String jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    /**
     * 获取启动运行
     *
     * @return START_RUN - 启动运行
     */
    public Boolean getStartRun() {
        return startRun;
    }

    /**
     * 设置启动运行
     *
     * @param startRun 启动运行
     */
    public void setStartRun(Boolean startRun) {
        this.startRun = startRun;
    }

    /**
     * 获取是否删除
     *
     * @return DEL - 是否删除
     */
    public Boolean getDel() {
        return del;
    }

    /**
     * 设置是否删除
     *
     * @param del 是否删除
     */
    public void setDel(Boolean del) {
        this.del = del;
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
}