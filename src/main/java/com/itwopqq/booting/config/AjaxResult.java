package com.itwopqq.booting.config;

import java.io.Serializable;

/**
 * @author fanzhen
 * @desx
 * @date 2020-04-03
 */
public class AjaxResult implements Serializable {

    /***
     * @desc success or error 返回结果状态值
     */
    private String status;

    /***
     * @desc 返回结果提示信息
     */
    private String msg;

    /***
     * @desc 结果数据
     */
    private Object data;

    /***
     * @desc 成功返回的数据
     */
    public static AjaxResult success(String msg, Object data){

        AjaxResult result = new AjaxResult();
        result.setStatus("success");
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /***
     * @desc 失败返回的数据
     */
    public static AjaxResult error(String msg){
        AjaxResult result = new AjaxResult();
        result.setStatus("error");
        result.setMsg(msg);
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
