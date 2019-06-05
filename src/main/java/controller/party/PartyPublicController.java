package controller.party;

import controller.BaseController;
import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import domain.party.PartyPublic;
import domain.party.PartyPublicExample;
import domain.party.PartyPublicExample.Criteria;
import domain.party.PartyPublicUser;
import domain.party.PartyPublicUserExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import persistence.member.MemberApplyViewMapper;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.security.Base64Utils;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyPublicController extends BaseController {

    @Autowired(required = false)
    protected MemberApplyViewMapper memberApplyViewMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/public/partyPublics")
    public String public_partyPublics(ModelMap modelMap) throws Exception {

        List<PartyPublic> partyPublics = partyPublicService.getPartyPublics();
        modelMap.put("partyPublics", partyPublics);

        return "party/partyPublic/partyPublics";
    }

    @RequestMapping("/public/{id}/partyPublic")
    public String public_partyPublic(){

        return "page";
    }

    @RequestMapping("/public/{id}/partyPublic_page")
    public String public_partyPublic_page(@PathVariable String id,
                                     HttpServletResponse response,
                                     ModelMap modelMap) throws Exception {

        PartyPublic partyPublic = null;
        String idStr = null;
        try {
            idStr = Base64Utils.decodeStr(id);
            partyPublic = partyPublicService.get(Integer.valueOf(idStr));
        } catch (Exception e) {
            logger.warn("读取公示文件异常，{}, {}", id, idStr);
        }

        if (partyPublic == null
                || (BooleanUtils.isNotTrue(partyPublic.getIsPublish())
                && !ShiroHelper.isPermitted("partyPublic:edit"))) {
            response.getWriter().write("");
            return null;
        }
        Date pubDate = partyPublic.getPubDate();
        Date deadline = DateUtils.getDateBeforeOrAfterDays(pubDate, 7);

        modelMap.put("deadline", deadline);
        modelMap.put("partyPublic", partyPublic);
        byte type = partyPublic.getType();
        if (type == OwConstants.OW_PARTY_PUBLIC_TYPE_GROW) {

            return "party/partyPublic/partyPublic_grow";
        }

        return "party/partyPublic/partyPublic_positive";
    }

    @RequiresPermissions("partyPublic:pub")
    @RequestMapping(value = "/partyPublic_pub", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPublic_pub(@RequestParam(value = "ids[]") Integer[] ids, Boolean publish) {

        publish = BooleanUtils.isTrue(publish);
        if (ids != null && ids.length > 0) {

            for (Integer id : ids) {

                PartyPublic partyPublic = partyPublicMapper.selectByPrimaryKey(id);
                // 权限控制
                if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
                    // 要求是分党委管理员
                    Integer partyId = partyPublic.getPartyId();
                    if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                        throw new UnauthorizedException();
                    }
                }

                PartyPublic record = new PartyPublic();
                record.setId(id);
                record.setIsPublish(publish);

                partyPublicService.updateByPrimaryKeySelective(record);
            }
            logger.info(addLog(LogConstants.LOG_PARTY,
                    (publish ? "发布" : "取消发布") + "公示：%s", StringUtils.join(ids)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPublic:pub")
    @RequestMapping(value = "/partyPublics_refresh", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPublics_refresh() {

        partyPublicService.refreshPartyPublics();

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPublic:list")
    @RequestMapping("/partyPublics_preview")
    public String partyPublics_preview() {

        return "party/partyPublic/partyPublics_preview";
    }

    @RequiresPermissions("partyPublic:list")
    @RequestMapping("/partyPublic")
    public String partyPublic(@RequestParam(defaultValue = "1") int cls,
                              Integer partyId,
                              Integer pubUserId,
                              Integer userId,
                              ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("party", CmTag.getParty(partyId));
        modelMap.put("pubUser", CmTag.getUserById(pubUserId));
        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "party/partyPublic/partyPublic_page";
    }

    @RequiresPermissions("partyPublic:list")
    @RequestMapping("/partyPublic_data")
    @ResponseBody
    public void partyPublic_data(HttpServletResponse response,
                                 Byte type,
                                 Integer partyId,
                                 @RequestDateRange DateRange pubDate,
                                 Boolean isPublish,
                                 Integer pubUserId,
                                 Integer userId,
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

        PartyPublicExample example = new PartyPublicExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("pub_date desc, id desc");

        //===========权限
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            List<Integer> partyIdList = loginUserService.adminPartyIdList();
                if (partyIdList.size() > 0)
                    criteria.andPartyIdIn(partyIdList);
                else criteria.andPartyIdIsNull();
        }

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (pubDate.getStart() != null) {
            criteria.andPubDateGreaterThanOrEqualTo(pubDate.getStart());
        }
        if (pubDate.getEnd() != null) {
            criteria.andPubDateLessThanOrEqualTo(pubDate.getEnd());
        }
        if (isPublish != null) {
            criteria.andIsPublishEqualTo(isPublish);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (pubUserId != null) {
            criteria.andPubUsersLike("%," + pubUserId + ",%");
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            partyPublic_export(example, response);
            return;
        }

        long count = partyPublicMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyPublic> records = partyPublicMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyPublic.class, partyPublicMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyPublic:edit")
    @RequestMapping(value = "/partyPublic_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPublic_au(PartyPublic record,
                                 @RequestParam(required = false, value = "voteIds[]") Integer[] userIds,
                                 HttpServletRequest request) {

        // 权限控制
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            // 要求是分党委管理员
            Integer partyId = record.getPartyId();
            if (!partyMemberService.isPresentAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        Integer id = record.getId();

        record.setPubDate(new Date());
        PartyPublic partyPublic = partyPublicService.get(record.getPartyId(),
                record.getType(), record.getPubDate());
        if(partyPublic != null) {
            if (id == null || partyPublic.getId() != id) {

                return failed("已经存在今天的{0}（{1}），不可重复生成公示文件。",
                        OwConstants.OW_PARTY_PUBLIC_TYPE_MAP.get(partyPublic.getType()),
                        partyPublic.getPartyName());
            }
        }

        if (id == null) {

            partyPublicService.insertSelective(record, userIds);
            logger.info(log(LogConstants.LOG_PARTY, "添加党员公示文件：{0}", record.getId()));
        } else {

            partyPublicService.update(record, userIds);
            logger.info(log(LogConstants.LOG_PARTY, "更新党员公示文件：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPublic:edit")
    @RequestMapping("/partyPublic_au")
    public String partyPublic_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyPublic partyPublic = partyPublicMapper.selectByPrimaryKey(id);
            modelMap.put("partyPublic", partyPublic);

            modelMap.put("party", CmTag.getParty(partyPublic.getPartyId()));

            List<Integer> userIds = new ArrayList<>();
            {
                PartyPublicUserExample example = new PartyPublicUserExample();
                example.createCriteria().andPublicIdEqualTo(id);
                example.setOrderByClause("id asc");
                List<PartyPublicUser> partyPublicUsers = partyPublicUserMapper.selectByExample(example);
                for (PartyPublicUser partyPublicUser : partyPublicUsers) {
                    userIds.add(partyPublicUser.getUserId());
                }
            }

            List<MemberApplyView> votes = new ArrayList<>();
            if (userIds.size() > 0) {
                MemberApplyViewExample example = new MemberApplyViewExample();
                example.createCriteria().andUserIdIn(userIds);
                example.setOrderByClause("field(user_id," + StringUtils.join(userIds, ",") + ") asc");
                votes = memberApplyViewMapper.selectByExample(example);
            }

            modelMap.put("votes", votes);
        }

        return "party/partyPublic/partyPublic_au";
    }

    @RequiresPermissions("partyPublic:edit")
    @RequestMapping("/partyPublic_users")
    public String partyPublic_users(byte type, int partyId,
                                    Integer branchId, Integer userId, Byte applyType,
                                    Integer publicId,
                                    @RequestParam(value = "selectUserIds", required = false) Integer[] selectUserIds,
                                    ModelMap modelMap) {

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andPartyIdEqualTo(partyId);
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());
        example.setOrderByClause("branch_sort_order desc, create_time desc");

        if (type == OwConstants.OW_PARTY_PUBLIC_TYPE_GROW) {
            criteria.andStageEqualTo(OwConstants.OW_APPLY_STAGE_PLAN);
        } else if (type == OwConstants.OW_PARTY_PUBLIC_TYPE_POSITIVE) {
            criteria.andStageEqualTo(OwConstants.OW_APPLY_STAGE_GROW);
        } else {
            criteria.andUserIdIsNull();
        }

        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
            modelMap.put("branch", branchService.findAll().get(branchId));
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (applyType != null) {
            criteria.andTypeEqualTo(applyType);
        }

        criteria.andPublicIdIsNullOrThis(publicId, type);

        if (selectUserIds != null && selectUserIds.length > 0) {
            criteria.andUserIdNotIn(Arrays.asList(selectUserIds));
        }

        List<MemberApplyView> memberApplys = memberApplyViewMapper.selectByExample(example);
        modelMap.put("memberApplys", memberApplys);

        return "party/partyPublic/partyPublic_users";
    }

    @RequiresPermissions("partyPublic:edit")
    @RequestMapping(value = "/partyPublic_selectUser", method = RequestMethod.POST)
    public void do_partyPublic_selectUser(
            @RequestParam(value = "userIds[]") Integer[] userIds,
            HttpServletResponse response) throws IOException {

        List<MemberApplyView> votes = new ArrayList<>();
        if (userIds != null) {

            MemberApplyViewExample example = new MemberApplyViewExample();
            example.createCriteria().andUserIdIn(Arrays.asList(userIds));
            example.setOrderByClause("branch_sort_order desc, create_time desc");
            votes = memberApplyViewMapper.selectByExample(example);
        }

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("votes", votes);
        JSONUtils.write(response, resultMap);
    }

    @RequiresPermissions("partyPublic:del")
    @RequestMapping(value = "/partyPublic_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyPublic_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            partyPublicService.batchDel(ids);
            logger.info(log(LogConstants.LOG_PARTY, "批量删除党员公示文件：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void partyPublic_export(PartyPublicExample example, HttpServletResponse response) {

        List<PartyPublic> records = partyPublicMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"类型|100", "所属党委|100", "公示日期|100", "党委名称|100", "邮箱|100", "联系电话|100", "信箱|100", "创建人|100", "是否发布|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyPublic record = records.get(i);
            String[] values = {
                    record.getType() + "",
                    record.getPartyId() + "",
                    DateUtils.formatDate(record.getPubDate(), DateUtils.YYYY_MM_DD),
                    record.getPartyName(),
                    record.getEmail(),
                    record.getPhone(),
                    record.getMailbox(),
                    record.getUserId() + "",
                    record.getIsPublish() + ""
            };
            valuesList.add(values);
        }
        String fileName = "党员公示文件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
