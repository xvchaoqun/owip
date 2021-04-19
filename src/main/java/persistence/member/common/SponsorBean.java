package persistence.member.common;

import org.apache.commons.lang3.StringUtils;

// 入党介绍人
public class SponsorBean {

    // 1##userId,0##张三，1：校内 0：校外
    public static SponsorBean toBean(String sponsorUserIds){

        SponsorBean bean = null;
        if(StringUtils.isNotBlank(sponsorUserIds)){

            bean = new SponsorBean();

            String[] _users = sponsorUserIds.split(",");
            if(_users.length>0) {
                String[] _user1 = _users[0].split("##");
                if(_user1.length==2){
                    byte inSchool1 = Byte.valueOf(_user1[0]);
                    bean.setInSchool1(inSchool1);
                    if(inSchool1==1){
                        bean.setUserId1(Integer.parseInt(_user1[1]));
                    }else{
                        bean.setUser1(_user1[1]);
                    }
                }
            }
            if(_users.length>1) {
                String[] _user2 = _users[1].split("##");
                if(_user2.length==2){
                    byte inSchool2 = Byte.valueOf(_user2[0]);
                    bean.setInSchool2(inSchool2);
                    if(inSchool2==1){
                        bean.setUserId2(Integer.parseInt(_user2[1]));
                    }else{
                        bean.setUser2(_user2[1]);
                    }
                }
            }
        }

        return bean;
    }

    public static String toString(SponsorBean bean){

       String sponsorUserIds = "";
       if(bean!=null){
           if(bean.getInSchool1()!=null) {
               sponsorUserIds = bean.getInSchool1() + "##" + bean.getUserId1();
               if (bean.getInSchool2()!=null){
                   sponsorUserIds += "," + bean.getInSchool2() + "##" + bean.getUserId2();
               }
           }
       }

       return sponsorUserIds;
    }

    private Byte inSchool1; // 入党介绍人1类型，默认校内， 1：校内 0：校外
    private Integer userId1;
    private String user1;
    private Byte inSchool2; // 入党介绍人2类型，默认校内
    private Integer userId2;
    private String user2;

    public Byte getInSchool1() {
        return inSchool1;
    }

    public void setInSchool1(Byte inSchool1) {
        this.inSchool1 = inSchool1;
    }

    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public Byte getInSchool2() {
        return inSchool2;
    }

    public void setInSchool2(Byte inSchool2) {
        this.inSchool2 = inSchool2;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
