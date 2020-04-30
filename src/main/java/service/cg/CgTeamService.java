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

        String whereSql = record.getFid()==null?"is_current="+record.getIsCurrent():String.format("is_current=%s and fid=%s",record.getIsCurrent(),record.getFid()) ;
        record.setSortOrder(getNextSortOrder("cg_team", whereSql));
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
    public void changeOrder(int id, Integer fid, int addNum) {

        CgTeam cgTeam = cgTeamMapper.selectByPrimaryKey(id);
        String whereSql = fid == null? "is_current="+cgTeam.getIsCurrent(): String.format("is_current=%s and fid=%s",cgTeam.getIsCurrent(),cgTeam.getFid()) ;
        changeOrder("cg_team", whereSql, ORDER_BY_DESC, id, addNum);
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

    //根据委员会和领导小组ID,查询职务以及对应现任人员
    public Map<Integer,Set<Integer>> getMember(Integer id){

        //查询所有现任成员
        CgMemberExample cgMemberExample = new CgMemberExample();
        cgMemberExample.setOrderByClause("sort_order desc");
        cgMemberExample.createCriteria().andTeamIdEqualTo(id)
                .andIsCurrentEqualTo(true);
        List<CgMember> cgMembers = cgMemberMapper.selectByExample(cgMemberExample);

        //按照职务对现任成员分类   Map<post，Set<userId>>
        Map<Integer,Set<Integer>> cgMemberMap = new LinkedHashMap<>();
        for (CgMember cgMember : cgMembers){
            Integer postId = cgMember.getPost();
            Set userIds = cgMemberMap.get(postId);

            if (userIds == null)
                userIds = new LinkedHashSet();

            userIds.add(cgMember.getUserId());
            cgMemberMap.put(postId,userIds);
        }

        return cgMemberMap;
    }

    //格式化委员会和领导小组现有职务以及职位对应成员（用于导出）
    public List<String> formatCgMember(Map<Integer,Set<Integer>> cgMemberMap){

        List<String> postAndNameList = new ArrayList();
        for (Map.Entry<Integer, Set<Integer>> entry : cgMemberMap.entrySet()) {

            MetaType metaType = metaTypeMapper.selectByPrimaryKey(entry.getKey());
            StringBuffer postAndName = new StringBuffer();

            if (metaType == null) continue;
            postAndName.append(CmTag.realnameWithEmpty(metaType.getName())+"：");

            Set<Integer> userIds = entry.getValue();
            for (Integer userId : userIds){
                SysUserView user = CmTag.getUserById(userId);

                if (user != null){
                    postAndName.append(CmTag.realnameWithEmpty(user.getRealname())+"  ");
                }
            }
            postAndNameList.add(postAndName.toString());
        }
        return postAndNameList;
    }

    //获取委员会和领导小组的办公室主任
    public CgLeader getCgLeader(Integer cgTeamId){

        CgLeaderExample cgLeaderExample = new CgLeaderExample();
        cgLeaderExample.createCriteria().andTeamIdEqualTo(cgTeamId).andIsCurrentEqualTo(true);
        List<CgLeader> cgLeaders = cgLeaderMapper.selectByExample(cgLeaderExample);

        return cgLeaders.size()>0?cgLeaders.get(0):null;
    }

    //获取委员会和领导小组的相关规程
    public List<CgRule> getCgRuleList(Integer CgTeamId){

        CgRuleExample example = new CgRuleExample();
        example.setOrderByClause("sort_order desc");
        example.createCriteria().andIsCurrentEqualTo(true).andTeamIdEqualTo(CgTeamId);
        List<CgRule> cgRuleList = cgRuleMapper.selectByExample(example);

        return cgRuleList == null?new ArrayList<>():cgRuleList;
    }

    //格式化规程内容，将内容与样式标签进行分离
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
            if (StringUtils.isNotBlank(content) && contentList.size()==0)
                contentList.add(content);

        return contentList;
    }

    //导出委员会和领导小组概括（word）
    public void export(Integer[] teamIds, Writer out) throws IOException, TemplateException {

        List teamBaseList = new LinkedList();

        for (Integer teamId : teamIds) {

            Map cgTeamBaseMap = new HashMap();
            cgTeamBaseMap.put("cgTeamBase",getCgTeamBase(teamId));
            cgTeamBaseMap.put("cgBranchList",getCgTeamChildBaseList(teamId,CgConstants.CG_TEAM_TYPE_BRANCH));
            cgTeamBaseMap.put("cgWorkgroupList",getCgTeamChildBaseList(teamId,CgConstants.CG_TEAM_TYPE_WORKGROUP));
            teamBaseList.add(cgTeamBaseMap);
        }

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("teamBaseList",teamBaseList);
        dataMap.put("cgRuleType",formatSourceMap(CgConstants.CG_RULE_TYPE_MAP));
        dataMap.put("cgTeamType",formatSourceMap(CgConstants.CG_TEAM_TYPE_MAP));
        dataMap.put("cgRuleTypeStaff",CgConstants.CG_RULE_TYPE_STAFF);

        freemarkerService.process("/cg/cg1.ftl", dataMap, out);
    }

    //导出委员会和领导小组概况（zip）
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

    //获取所有正在运行的委员会和领导小组ID
    public Integer[] getAllTeamId(Integer[] teamIds){

            CgTeamExample cgTeamExample = new CgTeamExample();
            cgTeamExample.setOrderByClause("sort_order desc");
            cgTeamExample.createCriteria().andIsCurrentEqualTo(true).andFidIsNull();

            List<CgTeam> cgTeams = cgTeamMapper.selectByExample(cgTeamExample);
            List<Integer> idList = new ArrayList<>();
            for (CgTeam cgTeam : cgTeams){
                idList.add(cgTeam.getId());
            }

            teamIds = idList.toArray(new Integer[0]);

        return teamIds;
    }

    //获取委员会和领导小组的详细信息
    public Map getCgTeamBase(Integer cgTeamId){

        Map cgTeamBaseMap = new HashMap();
        //委员会和领导小组基本信息
        cgTeamBaseMap.put("cgTeam",cgTeamMapper.selectByPrimaryKey(cgTeamId));
        //委员会和领导小组相关规程
        cgTeamBaseMap.put("cgRuleList",getCgRuleList(cgTeamId));
        //委员会和领导小组的职务以及人员组成
        cgTeamBaseMap.put("cgMemberList",formatCgMember(getMember(cgTeamId)));
        //委员会和领导小组办公室主任
        cgTeamBaseMap.put("cgLeader",getCgLeader(cgTeamId));

        return cgTeamBaseMap;
    }

    //获取委员会和领导小组中分委会或者工作小组的详细信息
    public List<Map> getCgTeamChildBaseList(Integer fid,Byte type){

        CgTeamExample example = new CgTeamExample();
        example.createCriteria().andFidEqualTo(fid)
                .andIsCurrentEqualTo(true).andTypeEqualTo(type);
        List<CgTeam> cgTeamList = cgTeamMapper.selectByExample(example);
        List childCgTeamBases = new ArrayList();
        for (CgTeam cgTeam : cgTeamList){
            childCgTeamBases.add(getCgTeamBase(cgTeam.getId()));
        }

        return childCgTeamBases;
    }
    //格式化源数据map(用于word导出)
    public Map<String,String> formatSourceMap(Map<Byte,String> sourceMap){

        Map<String,String> typeMap = new HashMap();
        for (Byte key : sourceMap.keySet()){
            typeMap.put("k"+key,sourceMap.get(key));
        }
        return typeMap;
    }
}
