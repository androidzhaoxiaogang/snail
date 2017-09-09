package com.snail.common.domain.po.task;

import com.snail.common.domain.po.BasePo;

/**
 * 定时调度任务执行记录
 */
public class TaskHistory extends BasePo<String> {
    //任务ID
    private String         jobId;
    //开始时间
    private java.util.Date startTime;
    //结束时间
    private java.util.Date endTime;
    //执行结果
    private String         result;
    //状态
    private byte           status;

    /**
     * 获取 任务ID
     *
     * @return String 任务ID
     */
    public String getJobId() {
        return this.jobId;
    }

    /**
     * 设置 任务ID
     *
     * @param jobId 任务ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取 开始时间
     *
     * @return java.util.Date 开始时间
     */
    public java.util.Date getStartTime() {
        return this.startTime;
    }

    /**
     * 设置 开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 结束时间
     *
     * @return java.util.Date 结束时间
     */
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    /**
     * 设置 结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 执行结果
     *
     * @return String 执行结果
     */
    public String getResult() {
        return this.result;
    }

    /**
     * 设置 执行结果
     *
     * @param result 执行结果
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取 状态
     *
     * @return long 状态
     */
    public byte getStatus() {
        return this.status;
    }

    /**
     * 设置 状态
     *
     * @param status 状态
     */
    public void setStatus(byte status) {
        this.status = status;
    }

    public Status getStatusInfo() {
        return Status.fromValue(getStatus());
    }

    public enum Status {
        RUNNING("运行中", (byte) 0),
        SUCCESS("成功", (byte) 1),
        FAIL("失败", (byte) -1),
        UNKNOW("未知", (byte) -2);

        private   String name;
        protected byte   value;

        Status(String name, byte value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public byte getValue() {
            return value;
        }

        public static Status fromValue(byte value) {
            switch (value) {
                case 0:
                    return RUNNING;
                case 1:
                    return SUCCESS;
                case -1:
                    return FAIL;
                default:
                    return UNKNOW;
            }
        }
    }

}