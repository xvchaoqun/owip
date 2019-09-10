package service.cg;

import domain.cg.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CgConstants;

import java.util.*;

@Service
public class CgTeamService extends CgBaseMapper {

    @Transactional
    public void insertSelective(CgTeam record){

        record.setSortOrder(getNextSortOrder("cg_team", "is_current="+record.getIsCurrent()));
        cgTeamMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgTeamMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgTeamExample example = new CgTeamExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgTeamMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CgTeam record){
        cgTeamMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(id);
        changeOrder("cg_team", "is_current="+cgTeam.getIsCurrent(), ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateTeamStatus(Integer[] ids, boolean isCurrent){

        for (Integer id : ids){

            CgTeam record = new CgTeam();
            record.setId(id);
            record.setIsCurrent(isCurrent);
            record.setSortOrder(getNextSortOrder("cg_team", "is_current="+record.getIsCurrent()));
            cgTeamMapper.updateByPrimaryKeySelective(record);
        }
    }

    //根据委员会和领导小组ID获取组成规则
    @Transactional
    public Map getRuleContent(Integer id){

        CgRuleExample cgRuleExample = new CgRuleExample();
        cgRuleExample.createCriteria()
                .andTeamIdEqualTo(id)
                .andIsCurrentEqualTo(true);
        List<CgRule> cgRules = cgRuleMapper.selectByExample(cgRuleExample);

        Map cgRuleContentMap = new LinkedHashMap();
        for (CgRule cgRule : cgRules) {

            cgRuleContentMap.put(cgRule.getType(),cgRule.getContent());
        }
        return cgRuleContentMap;
    }

    @Transactional
    public Map getMember(Integer id){

        //查询所有现任成员
        CgMemberExample cgMemberExample = new CgMemberExample();
        cgMemberExample.setOrderByClause("sort_order desc");
        cgMemberExample.createCriteria()
                .andTeamIdEqualTo(id)
                .andIsCurrentEqualTo(true);
        List<CgMember> cgMembers = cgMemberMapper.selectByExample(cgMemberExample);

        Map<Integer,Set> cgMemberTypeMap = new LinkedHashMap<>();

        //将现任成员类型放入map的key，并初始化map的value的数据类型；
        for (CgMember cgMember : cgMembers){

            cgMemberTypeMap.put(cgMember.getPost(),new LinkedHashSet());
        }

        //将userId、userIds放入value中
        for (CgMember cgMember : cgMembers){

            Set typeValue = cgMemberTypeMap.get(cgMember.getPost());
            if (cgMember.getUserId() != null){

                typeValue.add(cgMember.getUserId());
            }

            cgMemberTypeMap.put(cgMember.getPost(),typeValue);
        }
        return cgMemberTypeMap;
    }

    public Byte findByType(String _type){

        Byte type = null;
        for (Map.Entry<Byte,String> entry:CgConstants.CG_TEAM_TYPE_MAP.entrySet() ){

            if (StringUtils.equals(entry.getValue(),_type)) type = entry.getKey();
        }
        return type;
    }

    @Transactional
    public int batchImport(List<CgTeam> records) {

        int addCount = 0;
        for (CgTeam record : records) {

            insertSelective(record);
            addCount++;
        }

        return addCount;
    }

    @Transactional
    public CgLeader getCgLeader(Integer id){

        CgLeaderExample cgLeaderExample = new CgLeaderExample();
        cgLeaderExample.createCriteria().andTeamIdEqualTo(id).andIsCurrentEqualTo(true);
        List<CgLeader> cgLeaders = cgLeaderMapper.selectByExample(cgLeaderExample);

        return cgLeaders.size()>0?cgLeaders.get(0):null;
    }

    public String getContentByType(Integer teamId, Byte type){

        CgRuleExample cgRuleExample = new CgRuleExample();
        cgRuleExample.createCriteria()
                .andTypeEqualTo(type)
                .andIsCurrentEqualTo(true)
                .andTeamIdEqualTo(teamId);
        List<CgRule> cgRules = cgRuleMapper.selectByExample(cgRuleExample);

        return cgRules.size()>0?"":cgRules.get(0).getContent();
    }

    public List<String> formatContent(String content){

        Document doc= Jsoup.parseBodyFragment(content);
        Elements tagElement = doc.getElementsByTag("p");

        List<String> contentList = new ArrayList<>();
        Element element=null;
        for(int i=0;i<tagElement.size();i++){
            element=tagElement.get(i);
            String pHtml=element.html();
            pHtml=pHtml.replaceAll("&nbsp;"," ");
            pHtml=pHtml.replaceAll("&amp;nbsp;\\s{0,1}"," ");

            contentList.add(pHtml);
        }

        return contentList;
    }
}
