package service.cg;

import domain.base.MetaType;
import domain.cg.*;
import domain.sys.SysUserView;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.common.FreemarkerService;
import sys.constants.CgConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class CgTeamService extends CgBaseMapper {

    @Autowired
    private FreemarkerService freemarkerService;

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
        cgRuleExample.setOrderByClause("sort_order desc");
        List<CgRule> cgRules = cgRuleMapper.selectByExample(cgRuleExample);

        Map cgRuleContentMap = new LinkedHashMap();
        for (CgRule cgRule : cgRules) {

            cgRuleContentMap.put(cgRule.getType(),cgRule.getContent());
        }
        return cgRuleContentMap;
    }

    //根据委员会和领导小组ID,查询职务以及对应现任人员
    @Transactional
    public Map<Integer,Set<Integer>> getMember(Integer id){

        //查询所有现任成员
        CgMemberExample cgMemberExample = new CgMemberExample();
        cgMemberExample.setOrderByClause("sort_order desc");
        cgMemberExample.createCriteria()
                .andTeamIdEqualTo(id)
                .andIsCurrentEqualTo(true);
        List<CgMember> cgMembers = cgMemberMapper.selectByExample(cgMemberExample);

        Map<Integer,Set<Integer>> cgMemberTypeMap = new LinkedHashMap<>();

        //将现任成员类型放入map的key，并初始化map的value的数据类型；
        for (CgMember cgMember : cgMembers){

            cgMemberTypeMap.put(cgMember.getPost(),new LinkedHashSet<Integer>());
        }

        //将userId放入value中
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

        return cgRules.size()>0?cgRules.get(0).getContent():null;
    }

    public List<String> formatContent(String content){

        if (StringUtils.isBlank(content)) return null;

            Document doc = Jsoup.parseBodyFragment(content);
            Elements tagElement = doc.getElementsByTag("p");

            List<String> contentList = new ArrayList<>();
            Element element = null;
            for (int i = 0; i < tagElement.size(); i++) {
                element = tagElement.get(i);
                String pHtml = element.html();
                pHtml = pHtml.replaceAll("&nbsp;", " ");
                pHtml = pHtml.replaceAll("&amp;nbsp;\\s{0,1}", " ");

                if (StringUtils.isBlank(pHtml)) continue;

                contentList.add(pHtml);
            }


        return contentList;
    }

    //导出委员会和领导小组概括
    public void export(Integer[] teamIds, Writer out) throws IOException, TemplateException {

        List cgTeams = new ArrayList<>();
        for (Integer teamId : teamIds) {

            Map<String, Object> dataMap = new HashMap<String, Object>();
            CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(teamId);
            //委员会和领导小组基本信息
            dataMap.put("cgTeam", cgTeam);

            //参数设置内容
            List staffContentList = formatContent(getContentByType(teamId, CgConstants.CG_RULE_TYPE_STAFF));
            List jobContentList = formatContent(getContentByType(teamId, CgConstants.CG_RULE_TYPE_JOB));
            List debateContentList = formatContent(getContentByType(teamId, CgConstants.CG_RULE_TYPE_DEBATE));

            dataMap.put("staffContentList", staffContentList);
            dataMap.put("jobContentList", jobContentList);
            dataMap.put("debateContentList", debateContentList);

            //办公室主任
            CgLeader cgLeader = getCgLeader(teamId);
            if (cgLeader != null && cgLeader.getUser() != null)
                dataMap.put("leaderName", cgLeader.getUser().getRealname());

            //人员组成
            Map<Integer, Set<Integer>> memberMap = getMember(teamId);
            List<String> memberAndUserList = new LinkedList<>();

            for (Map.Entry<Integer, Set<Integer>> entry : memberMap.entrySet()) {

                MetaType metaType = metaTypeMapper.selectByPrimaryKey(entry.getKey());
                if (metaType != null)
                    memberAndUserList.add(metaType.getName() + "：" + getUserNamesById(entry.getValue()));

            }

            dataMap.put("memberAndUserList", memberAndUserList);

            cgTeams.add(dataMap);
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("cgTeams",cgTeams);
        freemarkerService.process("/cg/cg.ftl", dataMap, out);
    }

    public void export(Integer[] teamIds, HttpServletRequest request, HttpServletResponse response)
            throws IOException, TemplateException{

        Map<String, File> fileMap = new LinkedHashMap<>();
        String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "cgTeam";
        FileUtils.mkdirs(tmpdir, false);

        Set<String> filenameSet = new HashSet<>();
        for (Integer teamId : teamIds){
            CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(teamId);

            String filename = null;
            String filepath = null;

            filename = DateUtils.formatDate(new Date(), "yyyy.MM.dd") + cgTeam.getName() + ".doc";

            // 保证文件名不重复
            if (filenameSet.contains(filename)) {
                filename = cgTeam.getId() + " " + filename;
            }
            filenameSet.add(filename);
            filepath = tmpdir + FILE_SEPARATOR + filename;
            FileOutputStream output = new FileOutputStream(new File(filepath));
            OutputStreamWriter osw = new OutputStreamWriter(output, "utf-8");

            export(new Integer[]{teamId},osw);

            fileMap.put(filename, new File(filepath));
        }
        String filename = String.format("%s委员会和领导小组",
                CmTag.getSysConfig().getSchoolName());
        DownloadUtils.addFileDownloadCookieHeader(response);
        DownloadUtils.zip(fileMap, filename, request, response);
        FileUtils.deleteDir(new File(tmpdir));
    }

    //查询Set中userId对应的用户姓名
    public String getUserNamesById(Set<Integer> userIds){

        StringBuffer userNames = new StringBuffer();
        for (Integer userId : userIds){

            SysUserView sysUserView = CmTag.getUserById(userId);
            if (sysUserView != null) userNames.append(CmTag.realnameWithEmpty(sysUserView.getRealname())+"  ");
        }
        return userNames.toString();
    }

    public Integer[] getAllTeamId(Integer[] teamIds){

            CgTeamExample cgTeamExample = new CgTeamExample();
            cgTeamExample.setOrderByClause("sort_order desc");
            cgTeamExample.createCriteria().andIsCurrentEqualTo(true);

            List<CgTeam> cgTeams = cgTeamMapper.selectByExample(cgTeamExample);
            List<Integer> idList = new ArrayList<>();
            for (CgTeam cgTeam : cgTeams){
                idList.add(cgTeam.getId());
            }

            teamIds = idList.toArray(new Integer[0]);

        return teamIds;
    }
    }
