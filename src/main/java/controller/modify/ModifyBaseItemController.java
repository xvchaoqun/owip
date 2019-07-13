package controller.modify;

import domain.cadre.CadreParty;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseItem;
import domain.modify.ModifyBaseItemExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/11/27.
 */
@Controller
public class ModifyBaseItemController extends ModifyBaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("modifyBaseItem:list")
    @RequestMapping("/modifyBaseItem")
    public String modifyBaseItem(Integer applyId, ModelMap modelMap) {

        ModifyBaseApply apply = modifyBaseApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("apply", apply);
        Integer userId = apply.getUserId();
        SysUserView uv = sysUserService.findById(userId);
        modelMap.put("uv", uv);
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);

        return "modify/modifyBaseItem/modifyBaseItem_page";
    }

    @RequiresPermissions("modifyBaseItem:list")
    @RequestMapping("/modifyBaseItem_data")
    public void modifyBaseItem_data(HttpServletResponse response,
                           Integer applyId,
                           Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ModifyBaseItemExample example = new ModifyBaseItemExample();
        ModifyBaseItemExample.Criteria criteria = example.createCriteria();
        //example.setOrderByClause("check_time desc");

        criteria.andApplyIdEqualTo(applyId);

        int count = modifyBaseItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ModifyBaseItem> records = modifyBaseItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    // 审核
    @RequiresPermissions("modifyBaseItem:approval")
    @RequestMapping("/modifyBaseItem_approval")
    public String modifyBaseItem_approval(int id, ModelMap modelMap) {

        ModifyBaseItem mbi = modifyBaseItemMapper.selectByPrimaryKey(id);
        modelMap.put("record", mbi);
        ModifyBaseApply mba = modifyBaseApplyMapper.selectByPrimaryKey(mbi.getApplyId());
        Integer userId = mba.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));
        modelMap.put("cadre", cadreService.dbFindByUserId(userId));

        String code = mbi.getCode();
        if (StringUtils.equals(code, "grow_time")){

            // 修改 党派加入时间，要判断一下当前账号是否拥有两个党派：
            Member member = memberService.get(userId);
            boolean isOwParty = (member!=null && (member.getStatus()==1 || member.getStatus()==4));
            if(!isOwParty) {
                CadreParty owParty = cadrePartyService.get(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
                isOwParty = (owParty != null);
            }
            CadreParty dpParty = cadrePartyService.get(userId, CadreConstants.CADRE_PARTY_TYPE_DP);
            boolean isDpParty = (dpParty != null);

            modelMap.put("isOwParty", isOwParty);
            modelMap.put("isDpParty", isDpParty);
            modelMap.put("dpParty", dpParty);
        }

        return "modify/modifyBaseItem/modifyBaseItem_approval";
    }

    @RequiresPermissions("modifyBaseItem:approval")
    @RequestMapping(value = "/modifyBaseItem_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyBaseItem_approval(Integer id, Boolean status,
                                          String checkRemark, String checkReason){

        if (null != id){
            modifyBaseItemService.approval(id, status, checkRemark, checkReason);
            logger.info(addLog(LogConstants.LOG_ADMIN, "审核基本信息修改申请：%s, %s", id, status));
        }

        return success(FormUtils.SUCCESS);
    }
}
