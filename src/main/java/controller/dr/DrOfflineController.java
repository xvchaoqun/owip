package controller.dr;

import domain.base.MetaType;
import domain.dr.*;
import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.DrConstants;
import sys.constants.LogConstants;
import sys.constants.ScConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class DrOfflineController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOffline:list")
    @RequestMapping("/drOffline_result_export")
    public void drOffline_result_export(int offlineId,
                                    Boolean needVoterType,
                                    HttpServletResponse response) throws IOException {

        drExportService.exportOffline(offlineId, needVoterType, response);
    }

    @RequiresPermissions("drOffline:list")
    @RequestMapping("/drOffline_selectMembers_tree")
    @ResponseBody
    public Map drOffline_selectMembers_tree(int offlineId) throws IOException {

        DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(offlineId);
        Set<Integer> selectIdSet = new HashSet<>();

        String members = drOffline.getMembers();
        if (StringUtils.isNotBlank(members)) {
            for (String memberIdStr : members.split(",")) {
                selectIdSet.add(Integer.valueOf(memberIdStr));
            }
        }

        TreeNode tree = drOfflineService.getSelectMemberTree(selectIdSet);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("drOffline:list")
    @RequestMapping("/drOffline_selectMembers")
    public String drOffline_selectMembers() throws IOException {

        return "dr/drOffline/drOffline_selectMembers";
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping(value = "/drOffline_selectMembers", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOffline_selectMembers(Integer offlineId,
                                          @RequestParam(value = "memberIds[]", required = false) Integer[] memberIds) {

        DrOffline record = new DrOffline();
        record.setId(offlineId);
        record.setMembers(StringUtils.join(memberIds, ","));

        drOfflineMapper.updateByPrimaryKeySelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping(value = "/dfOffline_uploadBallotSample", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dfOffline_uploadBallotSample(int offlineId,
                                               MultipartFile _ballotSample,
                                               HttpServletRequest request) throws IOException, InterruptedException {

        String ballotSample = upload(_ballotSample, "dfOffline_ballotSample");

        if (StringUtils.isNotBlank(ballotSample)) {

            DrOffline record = new DrOffline();
            record.setId(offlineId);
            record.setBallotSample(ballotSample);
            drOfflineMapper.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_DR, "上传推荐票样：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping("/dfOffline_uploadBallotSample")
    public String dfOffline_uploadBallotSample(int offlineId, ModelMap modelMap) {

        DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(offlineId);
        modelMap.put("drOffline", drOffline);

        return "dr/drOffline/dfOffline_uploadBallotSample";
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping("/drOffline_result")
    public String drOffline_result(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrOfflineView drOffline = iDrMapper.getDrOfflineView(id);
            modelMap.put("drOffline", drOffline);

            Integer tplId = drOffline.getVoterTypeTplId();
            if (tplId != null) {
                Map<Integer, DrVoterType> typeMap = drVoterTypeService.findAll(tplId);
                modelMap.put("typeMap", typeMap);
            }
        }

        Map<Integer, DrVoterTypeTpl> tplMap = drVoterTypeTplService.findAll();
        modelMap.put("tplMap", tplMap);

        return "dr/drOffline/drOffline_result";
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping(value = "/drOffline_result", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOffline_result(DrOffline record, HttpServletRequest request) throws IOException, InterruptedException {

        record.setNeedVoterType(BooleanUtils.isTrue(record.getNeedVoterType()));

        Map<Integer, Integer> voterMap = new HashMap<>();
        Integer tplId = record.getVoterTypeTplId();
        if (tplId != null) {
            Map<Integer, DrVoterType> typeMap = drVoterTypeService.findAll(tplId);
            for (DrVoterType drVoterType : typeMap.values()) {
                int id = drVoterType.getId();
                int count = Integer.valueOf(request.getParameter("type_" + id));
                voterMap.put(id, count);
            }
            record.setVoters(XmlSerializeUtils.serialize(voterMap));
        }

        drOfflineService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_DR, "更新推荐结果：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping("/drOffline_addCandidate")
    public String drOffline_addCandidate(Integer candidateId, int offlineId,
                    Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = 10;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        if (candidateId != null) {
            DrOfflineCandidate candidate = drOfflineCandidateMapper.selectByPrimaryKey(candidateId);
            modelMap.put("candidate", candidate);

            String voters = candidate.getVoters();
            if (StringUtils.isNotBlank(voters)) {
                Map<Integer, Integer> voterMap = XmlSerializeUtils.unserialize(voters, Map.class);
                modelMap.put("candidateVoterMap", voterMap);
            }
        }

        DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(offlineId);
        modelMap.put("drOffline", drOffline);

        Integer tplId = drOffline.getVoterTypeTplId();
        if (tplId != null) {
            Map<Integer, DrVoterType> typeMap = drVoterTypeService.findAll(tplId);
            modelMap.put("typeMap", typeMap);
        }

        String voters = drOffline.getVoters();
        if (StringUtils.isNotBlank(voters)) {
            Map<Integer, Integer> voterMap = XmlSerializeUtils.unserialize(voters, Map.class);
            modelMap.put("voterMap", voterMap);
        }

        DrOfflineCandidateExample example = new DrOfflineCandidateExample();
        example.createCriteria().andOfflineIdEqualTo(offlineId);
        example.setOrderByClause("weight desc, id asc");

        int count = (int) drOfflineCandidateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        modelMap.put("commonList", commonList);
        List<DrOfflineCandidate> candidates =
                drOfflineCandidateMapper.selectByExampleWithRowbounds(example,
            new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("candidates", candidates);

        return "dr/drOffline/drOffline_addCandidate";
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping(value = "/drOffline_addCandidate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOffline_addCandidate(Integer id, int offlineId, int userId,
                                         Integer tplId, Integer vote, HttpServletRequest request){

        {
            DrOfflineCandidateExample example = new DrOfflineCandidateExample();
            DrOfflineCandidateExample.Criteria criteria = example.createCriteria()
                    .andOfflineIdEqualTo(offlineId).andUserIdEqualTo(userId);
            if (id != null) {
                criteria.andIdNotEqualTo(id);
            }
            if (drOfflineCandidateMapper.countByExample(example) > 0) {
                return failed("添加重复。");
            }
        }

        String weight = "";
        DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(offlineId);
        Map<Integer, Integer> voterMap = new HashMap<>();
        if (BooleanUtils.isTrue(drOffline.getNeedVoterType())) {
            vote = 0;
            Map<Integer, DrVoterType> typeMap = drVoterTypeService.findAll(tplId);
            for (DrVoterType drVoterType : typeMap.values()) {
                int drVoterTypeId = drVoterType.getId();
                int count = Integer.valueOf(request.getParameter("type_" + drVoterTypeId));
                vote += count;
                voterMap.put(drVoterTypeId, count);
                weight += String.format("%04d", count);
            }
            weight = String.format("%06d", vote) + weight;
        } else {
            if (vote == null)
                return failed("请输入得票总数。");
            weight = String.valueOf(vote);
        }

        DrOfflineCandidate record = new DrOfflineCandidate();
        record.setVote(vote);
        if (voterMap.size() > 0)
            record.setVoters(XmlSerializeUtils.serialize(voterMap));
        record.setWeight(weight);

        if (id == null) {

            record.setOfflineId(offlineId);
            record.setUserId(userId);
            drOfflineCandidateMapper.insert(record);
            logger.info(addLog(LogConstants.LOG_DR, "添加候选人：%s", record.getId()));
        } else {
            record.setId(id);
            drOfflineCandidateMapper.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_DR, "更新候选人：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping(value = "/drOffline_delCandidate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOffline_delCandidate(Integer id,
                                         Integer offlineId, HttpServletRequest request) throws IOException, InterruptedException {
        if (id != null) {
            drOfflineCandidateMapper.deleteByPrimaryKey(id);
            logger.info(addLog(LogConstants.LOG_DR, "删除候选人：%s", id));
        } else if (offlineId != null) {

            DrOfflineCandidateExample example = new DrOfflineCandidateExample();
            example.createCriteria().andOfflineIdEqualTo(offlineId);
            drOfflineCandidateMapper.deleteByExample(example);

            logger.info(addLog(LogConstants.LOG_DR, "清空候选人：%s", offlineId));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping("/drOffline_result_voterTypes")
    public String drOffline_result_voterTypes(Integer offlineId, Integer tplId, ModelMap modelMap) {

        if (offlineId != null) {
            DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(offlineId);
            modelMap.put("drOffline", drOffline);

            String voters = drOffline.getVoters();
            if (StringUtils.isNotBlank(voters)) {
                Map<Integer, Integer> voterMap = XmlSerializeUtils.unserialize(voters, Map.class);
                modelMap.put("voterMap", voterMap);
            }

            if (tplId == null) {
                tplId = drOffline.getVoterTypeTplId();
            }
        }
        if (tplId != null) {
            Map<Integer, DrVoterType> typeMap = drVoterTypeService.findAll(tplId);
            modelMap.put("typeMap", typeMap);
        }

        return "dr/drOffline/drOffline_result_voterTypes";
    }

    @RequiresPermissions("drOffline:list")
    @RequestMapping("/drOffline")
    public String drOffline() {

        return "dr/drOffline/drOffline_page";
    }

    @RequiresPermissions("drOffline:list")
    @RequestMapping("/drOffline_data")
    @ResponseBody
    public void drOffline_data(HttpServletResponse response,
                               Short year,
                               Integer type,
                               Date recommendDate,
                               @RequestParam(required = false, defaultValue = "0") int export,
                               @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DrOfflineViewExample example = new DrOfflineViewExample();
        DrOfflineViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("recommend_date desc, seq asc");

        if (year != null) {
            criteria.andYearEqualTo(year);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (recommendDate != null) {
            criteria.andRecommendDateGreaterThan(recommendDate);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            drOffline_export(example, response);
            return;
        }

        long count = drOfflineViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOfflineView> records = drOfflineViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOffline.class, drOfflineMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping(value = "/drOffline_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOffline_au(DrOffline record,
                               @RequestParam(value = "memberIds", required = false) Integer[] memberIds,
                               HttpServletRequest request) {

        Integer id = record.getId();
        record.setMembers(StringUtils.trimToEmpty(StringUtils.join(memberIds, ",")));

        if (id == null) {

            drOfflineService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_DR, "添加线下民主推荐：%s", record.getId()));
        } else {

            drOfflineService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_DR, "更新线下民主推荐：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping("/drOffline_au")
    public String drOffline_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(id);
            modelMap.put("drOffline", drOffline);

            Integer recordId = drOffline.getRecordId();
            ScRecordView scRecordView = iScMapper.getScRecordView(recordId);
            modelMap.put("scRecord", scRecordView);
            modelMap.put("chiefMember", drMemberMapper.selectByPrimaryKey(drOffline.getChiefMemberId()));
            modelMap.put("superviceUser", CmTag.getUserById(drOffline.getSuperviceUserId()));

            Set<Integer> selectMemberIds = new HashSet<>();
            String members = drOffline.getMembers();
            if (StringUtils.isNotBlank(members)) {
                for (String memberIdStr : members.split(",")) {
                    selectMemberIds.add(Integer.valueOf(memberIdStr));
                }
            }
            modelMap.put("selectMemberIds", new ArrayList<>(selectMemberIds));
        }

        Map<Byte, List<DrMember>> drMemberListMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : DrConstants.DR_MEMBER_STATUS_MAP.entrySet()) {
            List<DrMember> drMembers = drMemberService.getMembers(entry.getKey());
            if(drMembers.size()>0) {
                drMemberListMap.put(entry.getKey(), drMembers);
            }
        }
        modelMap.put("drMemberListMap", drMemberListMap);

        return "dr/drOffline/drOffline_au";
    }

    @RequiresPermissions("drOffline:edit")
    @RequestMapping("/drOffline_selectPost")
    public String drOffline_selectPost(Integer id, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {
            DrOffline drOffline = drOfflineMapper.selectByPrimaryKey(id);
            modelMap.put("drOffline", drOffline);
        }

        if (null == pageSize) {
            pageSize = 5;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        // 从干部选任纪实-单个岗位调整（正在进行、选任暂停，选拔方式为“民主推荐”）中选择岗位
        MetaType scTypeDr = CmTag.getMetaTypeByCode("mt_sctype_dr");
        List<Byte> statusList = new ArrayList<>();
        statusList.add(ScConstants.SC_RECORD_STATUS_INIT);
        statusList.add(ScConstants.SC_RECORD_STATUS_SUSPEND);
        ScRecordViewExample example = new ScRecordViewExample();
        example.createCriteria().andStatusIn(statusList)
                .andScTypeEqualTo(scTypeDr.getId());

        long count = scRecordViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScRecordView> records = scRecordViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        modelMap.put("records", records);
        modelMap.put("commonList", commonList);

        return "dr/drOffline/drOffline_selectPost";
    }

    @RequiresPermissions("drOffline:del")
    @RequestMapping(value = "/drOffline_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOffline_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            drOfflineService.del(id);
            logger.info(addLog(LogConstants.LOG_DR, "删除线下民主推荐：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOffline:del")
    @RequestMapping(value = "/drOffline_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOffline_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            drOfflineService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_DR, "批量删除线下民主推荐：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void drOffline_export(DrOfflineViewExample example, HttpServletResponse response) {

        List<DrOfflineView> records = drOfflineViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100", "编号|100", "推荐类型|100", "推荐日期|100", "所属纪实|100", "推荐组负责人|100", "推荐组成员|100", "推荐票样|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOfflineView record = records.get(i);
            String[] values = {
                    record.getYear() + "",
                    record.getSeq() + "",
                    record.getType() + "",
                    DateUtils.formatDate(record.getRecommendDate(), DateUtils.YYYY_MM_DD),
                    record.getRecordId() + "",
                    record.getChiefMemberId() + "",
                    record.getMembers(),
                    record.getBallotSample()
            };
            valuesList.add(values);
        }
        String fileName = "线下民主推荐_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
