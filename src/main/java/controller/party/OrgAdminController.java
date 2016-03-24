package controller.party;

import controller.BaseController;
import domain.OrgAdmin;
import domain.OrgAdminExample;
import domain.OrgAdminExample.Criteria;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class OrgAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("orgAdmin:list")
    @RequestMapping("/org_admin")
    public String org_admin(Integer partyId, Integer branchId,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (partyId != null || branchId!=null) {
            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            OrgAdminExample example = new OrgAdminExample();
            OrgAdminExample.Criteria criteria = example.createCriteria();
            if(partyId!=null){
                modelMap.put("party", partyService.findAll().get(partyId));
                criteria.andPartyIdEqualTo(partyId);
            }
            if(branchId!=null){
                modelMap.put("branch", branchService.findAll().get(branchId));
                criteria.andBranchIdEqualTo(branchId);
            }

            int count = orgAdminMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<OrgAdmin> orgAdmins = orgAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("orgAdmins", orgAdmins);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (partyId!=null) {
                searchStr += "&partyId=" + partyId;
            }
            if (branchId!=null) {
                searchStr += "&branchId=" + branchId;
            }
            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "party/org_admin";
    }

    @RequiresPermissions("orgAdmin:list")
    @RequestMapping("/orgAdmin")
    public String orgAdmin() {

        return "index";
    }

    @RequiresPermissions("orgAdmin:list")
    @RequestMapping("/orgAdmin_page")
    public String orgAdmin_page(HttpServletResponse response,
                                Integer userId,
                                Integer partyId,
                                Integer branchId,
                                Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OrgAdminExample example = new OrgAdminExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        int count = orgAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OrgAdmin> orgAdmins = orgAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("orgAdmins", orgAdmins);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        if (userId != null) {
            searchStr += "&userId=" + userId;
        }
        if (partyId != null) {
            searchStr += "&partyId=" + partyId;
        }
        if (branchId != null) {
            searchStr += "&branchId=" + branchId;
        }

        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "party/orgAdmin/orgAdmin_page";
    }

    @RequiresPermissions("orgAdmin:edit")
    @RequestMapping(value = "/orgAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_orgAdmin_au(OrgAdmin record, HttpServletRequest request) {

        if (orgAdminService.idDuplicate(null, record.getUserId(), record.getPartyId(), record.getBranchId())) {
            return failed("添加重复");
        }

        if (record.getPartyId() != null) {
            orgAdminService.addPartyAdmin(record.getUserId(), record.getPartyId());
        } else if (record.getBranchId() != null) {
            orgAdminService.addBranchAdmin(record.getUserId(), record.getBranchId());
        }
        logger.info(addLog(request, SystemConstants.LOG_OW, "添加党组织管理员：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("orgAdmin:edit")
    @RequestMapping("/orgAdmin_au")
    public String orgAdmin_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            OrgAdmin orgAdmin = orgAdminMapper.selectByPrimaryKey(id);
            modelMap.put("orgAdmin", orgAdmin);
        }
        return "party/orgAdmin/orgAdmin_au";
    }

    @RequiresPermissions("orgAdmin:del")
    @RequestMapping(value = "/orgAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_orgAdmin_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            OrgAdmin orgAdmin = orgAdminMapper.selectByPrimaryKey(id);
            orgAdminService.del(id, orgAdmin.getUserId());
            logger.info(addLog(request, SystemConstants.LOG_OW, "删除党组织管理员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }
}
