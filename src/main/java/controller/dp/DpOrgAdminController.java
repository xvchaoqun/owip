package controller.dp;

import domain.dp.DpOrgAdmin;
import domain.dp.DpOrgAdminExample;
import domain.dp.DpParty;
import domain.sys.SysUserView;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.DpPartyHelper;
import sys.shiro.CurrentUser;
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
@RequestMapping("/dp")
public class DpOrgAdminController extends DpBaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("dpOrgAdmin:list")
    @RequestMapping("/dpOrgAdmin")
    public String orgAdmin(ModelMap modelMap,
                           byte type,
                           Integer userId,
                           Integer partyId,
                           @RequestParam(required = false, defaultValue = "1")Byte cls) throws IOException {

        modelMap.put("type", type);
        modelMap.put("cls", cls);
        if(userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if(partyId!=null) {
            modelMap.put("dp", dpPartyService.findAll().get(partyId));
        }

        return "dp/dpOrgAdmin/dpOrgAdmin_page";
    }

    //@RequiresPermissions("orgAdmin:list")
    @RequestMapping("/dpOrgAdmin_data")
    public void dpOrgAdmin_data(HttpServletResponse response,
                              byte type,
                              Integer userId,
                              Integer partyId,
                              @RequestParam(required = false, defaultValue = "1")Byte cls,
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

        DpOrgAdminExample example = new DpOrgAdminExample();
        DpOrgAdminExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);

        criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));

        if(type== OwConstants.OW_ORG_ADMIN_DPPARTY){
            if(partyId!=null){
                criteria.andPartyIdEqualTo(partyId);
            }
            example.setOrderByClause("create_time desc");
        }
        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }

        long count = dpOrgAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<DpOrgAdmin> records = dpOrgAdminMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    @RequiresPermissions("dpOrgAdmin:list")
    @RequestMapping("/dp_org_admin")
    public String dp_org_admin(Integer partyId, Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (partyId != null) {
            if (null == pageSize) {
                pageSize = 5;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            DpOrgAdminExample example = new DpOrgAdminExample();
            DpOrgAdminExample.Criteria criteria = example.createCriteria();
            if(partyId!=null){
                modelMap.put("dpParty", dpPartyService.findAll().get(partyId));
                criteria.andPartyIdEqualTo(partyId);
            }

            criteria.addPermits(dpPartyMemberAdminService.adminDpPartyIdList(ShiroHelper.getCurrentUserId()));

            int count = (int) dpOrgAdminMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<DpOrgAdmin> dpOrgAdmins = dpOrgAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("dpOrgAdmins", dpOrgAdmins);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (partyId!=null) {
                searchStr += "&partyId=" + partyId;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "dp/dp_org_admin";
    }

    @RequiresPermissions("dpOrgAdmin:edit")
    @RequestMapping(value = "/dpOrgAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map dpOrgAdmin_au(DpOrgAdmin record, HttpServletRequest request) {

        if (dpOrgAdminService.idDuplicate(null, record.getUserId(), record.getPartyId())) {
            return failed("添加重复");
        }

        SysUserView uv = sysUserService.findById(record.getUserId());

        Integer partyId = record.getPartyId();
        DpParty dpParty = new DpParty();
        if ( partyId!= null) {
            DpPartyHelper.checkAuth(partyId);
            dpParty = dpPartyService.findAll().get(partyId);
            dpOrgAdminService.addDpPartyAdmin(record.getUserId(), partyId);
            logger.info(addLog(LogConstants.LOG_DPPARTY, "添加党派管理员：%s， %s", uv.getCode(), dpParty.getName()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpOrgAdmin:edit")
    @RequestMapping("/dpOrgAdmin_au")
    public String dpOrgAdmin_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            DpOrgAdmin dpOrgAdmin = dpOrgAdminMapper.selectByPrimaryKey(id);
            modelMap.put("dpOrgAdmin", dpOrgAdmin);
        }
        return "dp/dpOrgAdmin/dpOrgAdmin_au";
    }

    //@RequiresPermissions("dpOrgAdmin:del")
    @RequestMapping(value = "/dpOrgAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpOrgAdmin_del(@CurrentUser SysUserView loginUser, HttpServletRequest request, Integer id) {

        if (id != null) {
            DpOrgAdmin dpOrgAdmin = dpOrgAdminMapper.selectByPrimaryKey(id);
            if(dpOrgAdmin!=null) {
                if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_DPPARTYVIEWALL)) {
                    if (dpOrgAdmin.getUserId().intValue() == loginUser.getId()) {
                        return failed("不能删除自己");
                    }
                }
                SysUserView uv = sysUserService.findById(dpOrgAdmin.getUserId());
                DpParty dpParty = null;
                Integer partyId = dpOrgAdmin.getPartyId();
                if (partyId != null) {

                    dpParty = dpPartyService.findAll().get(partyId);
                    DpPartyHelper.checkAuth(partyId);
                }

                dpOrgAdminService.del(id, dpOrgAdmin.getUserId());
                logger.info(addLog(LogConstants.LOG_DPPARTY, "删除该民主党派管理员：%s, %s"
                        , uv.getCode(), dpParty == null ? "" : dpParty.getName()));
            }
        }
        return success(FormUtils.SUCCESS);
    }
}
