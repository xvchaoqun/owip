package controller.abroad;

import controller.global.OpException;
import domain.abroad.ApplySelfModify;
import domain.abroad.ApplySelfModifyExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.DownloadUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/abroad")
public class ApplySelfModifyController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("applySelf:modifyLog")
    @RequestMapping("/applySelfModifyList")
    public String applySelfModifyList(int applyId, ModelMap modelMap) {

        /*List<ApplySelfModifyBean> applySelfModifyList = iAbroadMapper.getApplySelfModifyList(applyId);
        modelMap.put("modifyList", applySelfModifyList);*/
        ApplySelfModifyExample example = new ApplySelfModifyExample();
        example.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_MODIFY);
        List<ApplySelfModify> applySelfModifies = applySelfModifyMapper.selectByExample(example);
        modelMap.put("modifyList", applySelfModifies);
        return "abroad/applySelf/applySelfModifyList";
    }


    @RequiresPermissions("applySelf:modifyLog")
    @RequestMapping("/applySelfModify")
    public String applySelfModify(int applyId, ModelMap modelMap) {

        // 获取第一条原始记录
        ApplySelfModifyExample example2 = new ApplySelfModifyExample();
        example2.createCriteria().andApplyIdEqualTo(applyId).andModifyTypeEqualTo(AbroadConstants.ABROAD_APPLYSELF_MODIFY_TYPE_ORIGINAL);
        List<ApplySelfModify> applySelfModifies = applySelfModifyMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 1));
        if(applySelfModifies.size()>0){
            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            modelMap.put("record", JSONUtils.toString(applySelfModifies.get(0), baseMixins));
        }

        return "abroad/applySelf/applySelfModify_page";
    }

    @RequiresPermissions("applySelf:modifyLog")
    @RequestMapping("/applySelfModify_data")
    @ResponseBody
    public void applySelfModify_data(
            int applyId,
            Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ApplySelfModifyExample example = new ApplySelfModifyExample();
        ApplySelfModifyExample.Criteria criteria = example.createCriteria();
        criteria.andApplyIdEqualTo(applyId);
        //example.setOrderByClause("id asc");

        int count = applySelfModifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ApplySelfModify> applySelfModifys = applySelfModifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", applySelfModifys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequestMapping("/applySelfModify_download")
    public void applySelfModify_download(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ApplySelfModify applySelfModify = applySelfModifyMapper.selectByPrimaryKey(id);

        CadreView cadre = cadreService.dbFindByUserId(loginUser.getId());
        //只有个人或者因私管理员可以进行下载
        if (cadre.getId().equals(applySelfModify.getCadreId()) || ShiroHelper.isPermitted(SystemConstants.PERMISSION_ABROADADMIN)) {

            String path = applySelfModify.getModifyProof();
            String filename = applySelfModify.getModifyProofFileName();

            DownloadUtils.download(request, response, springProps.uploadPath + path, filename);
        }else {
            throw new OpException("您没有权限");
        }
    }
}
