package bean;

import java.util.Date;

/**
 * Created by lm on 2018/6/22.
 * 学校单位信息
 */
public class SchoolUnit {

    // ID
    private String id;
    // 编号
    private String code;
    // 名称
    private String name;
    // 类型
    private String type;
    // 上级单位
    private String top;
    // 成立时间
    private Date workTime;
    // 网址
    private String url;
    // 备注
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SchoolUnit{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", top='" + top + '\'' +
                ", workTime=" + workTime +
                ", url='" + url + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
