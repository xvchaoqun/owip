package bean;

import domain.sys.MetaType;
import sys.tags.CmTag;

import java.util.Map;

/**
 * Created by fafa on 2016/3/14.
 */
public class PassportStatByPostBean {

    public MetaType getPost(){

        Map<Integer, MetaType> postMap = CmTag.getMetaTypes("mc_post");
        return postMap.get(postId);
    }

    private Integer postId;
    private Integer num;
    private Integer selfNum;
    private Integer twNum;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSelfNum() {
        return selfNum;
    }

    public void setSelfNum(Integer selfNum) {
        this.selfNum = selfNum;
    }

    public Integer getTwNum() {
        return twNum;
    }

    public void setTwNum(Integer twNum) {
        this.twNum = twNum;
    }
}
