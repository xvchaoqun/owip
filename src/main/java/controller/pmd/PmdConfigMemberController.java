package controller.pmd;

import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberExample;
import domain.pmd.PmdConfigMemberExample.Criteria;
import domain.pmd.PmdConfigMemberType;
import mixin.MixinUtils;
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
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdConfigMemberController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdConfigMember:list")
    @RequestMapping("/pmdConfigMember")
    public String pmdConfigMember(Integer userId, ModelMap modelMap) {

        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        Map<Byte, List<PmdConfigMemberType>> typeMap = new HashMap<>();
        for (Byte pmdMemberType : PmdConstants.PMD_MEMBER_TYPE_MAP.keySet()) {
            typeMap.put(pmdMemberType, pmdConfigMemberTypeService.list(pmdMemberType));
        }
        modelMap.put("typeMap", typeMap);

        return "pmd/pmdConfigMember/pmdConfigMember_page";
    }

    @RequiresPermissions("pmdConfigMember:list")
    @RequestMapping("/pmdConfigMember_data")
    public void pmdConfigMember_data(HttpServletResponse response,
                                     Boolean isOnlinePay,
                                     Boolean hasReset,
                                     Integer userId,
                                     Byte configMemberType,
                                     Integer configMemberTypeId,
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

        PmdConfigMemberExample example = new PmdConfigMemberExample();
        Criteria criteria = example.createCriteria();

        if (isOnlinePay != null) {
            criteria.andIsOnlinePayEqualTo(isOnlinePay);
        }
        if (hasReset != null) {
            criteria.andHasResetEqualTo(hasReset);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (configMemberType != null) {
            criteria.andConfigMemberTypeEqualTo(configMemberType);
        }
        if (configMemberTypeId != null) {
            criteria.andConfigMemberTypeIdEqualTo(configMemberTypeId);
        }

        long count = pmdConfigMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdConfigMember> records = pmdConfigMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdConfigMember.class, pmdConfigMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdConfigMember:edit")
    @RequestMapping(value = "/pmdConfigMember_updateType", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMember_updateType(int userId, byte type, HttpServletRequest request) {

        PmdConfigMember record = new PmdConfigMember();
        record.setUserId(userId);
        record.setConfigMemberType(type);

        pmdConfigMemberService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_PMD, "更新缴费党员类别：%s", record.getUserId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdConfigMember:edit")
    @RequestMapping("/pmdConfigMember_updateType")
    public String pmdConfigMember_updateType(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            PmdConfigMember pmdConfigMember = pmdConfigMemberMapper.selectByPrimaryKey(userId);
            modelMap.put("pmdConfigMember", pmdConfigMember);
        }

        Map<Byte, List<PmdConfigMemberType>> typeMap = new HashMap<>();
        for (Byte pmdMemberType : PmdConstants.PMD_MEMBER_TYPE_MAP.keySet()) {
            typeMap.put(pmdMemberType, pmdConfigMemberTypeService.list(pmdMemberType));
        }
        modelMap.put("typeMap", typeMap);

        return "pmd/pmdConfigMember/pmdConfigMember_updateType";
    }

    /*@RequiresPermissions("pmdConfigMember:del")
    @RequestMapping(value = "/pmdConfigMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMember_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            pmdConfigMemberService.del(userId);
            logger.info(addLog(LogConstants.LOG_PMD, "删除党员缴费分类：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }*/

    /*@RequiresPermissions("pmdConfigMember:del")
    @RequestMapping(value = "/pmdConfigMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdConfigMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {

        if (null != userIds && userIds.length > 0) {

            logger.info(addLog(LogConstants.LOG_PMD, "批量删除党员缴费分类：%s", StringUtils.join(userIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
