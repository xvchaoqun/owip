package persistence.pmd.common;

import domain.pmd.PmdOrderCampuscard;
import domain.sys.SysUserView;
import sys.tags.CmTag;

/**
 * Created by lm on 2018/5/19.
 */
public class IPmdOrderCampuscard extends PmdOrderCampuscard {

    public SysUserView getUser(){
        if(memberUserId!=null){
            return CmTag.getUserById(memberUserId);
        }
        return null;
    }

    private Integer memberUserId;

    public Integer getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(Integer memberUserId) {
        this.memberUserId = memberUserId;
    }
}
