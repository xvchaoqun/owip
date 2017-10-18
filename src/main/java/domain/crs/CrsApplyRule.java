package domain.crs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import service.crs.CrsPostService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrsApplyRule implements Serializable {

    public List<CrsPost> getCrsPosts(){

        List<CrsPost> list = new ArrayList<>();
        if(StringUtils.isNotBlank(postIds)){
            String[] postIdStr = postIds.split(",");
            CrsPostService crsPostService = CmTag.getBean(CrsPostService.class);
            for (String idStr : postIdStr) {
                CrsPost crsPost = crsPostService.get(Integer.valueOf(idStr));
                if(crsPost!=null){
                    list.add(crsPost);
                }
            }
        }
        return list;
    }

    private Integer id;

    private String postIds;

    private Integer num;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds == null ? null : postIds.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}