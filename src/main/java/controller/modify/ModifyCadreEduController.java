package controller.modify;

import com.google.gson.Gson;
import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreEdu;
import domain.cadre.CadreEduExample;
import domain.cadre.CadreEduExample.Criteria;
import domain.cadre.CadreInfo;
import domain.modify.ModifyTableApply;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.helper.ShiroSecurityHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ModifyCadreEduController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 左侧菜单
    @RequiresPermissions("modifyCadreEdu:list")
    @RequestMapping("/modifyCadreEdu")
    public String modifyCadreEdu() {

        return "index";
    }

    @RequiresPermissions("modifyCadreEdu:list")
    @RequestMapping("/modifyCadreEdu_page")
    public String modifyCadreEdu_page(@CurrentUser SysUserView loginUser, Byte cls, // 0 学习经历列表 1 修改申请 2 完成审核 3 删除
                                      Integer cadreId, ModelMap modelMap) {

        if (cls == null) {
            cls = (byte) (SecurityUtils.getSubject().hasRole("cadre") ? 0 : 1);
        }
        modelMap.put("cls", cls);
        if (cadreId != null) {

            Cadre cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        byte module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU;
        modelMap.put("module", module);
        if (cls == 0) {

            CadreEduExample example = new CadreEduExample();
            Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            // 干部只能看到自己的
            Cadre cadre = cadreService.findByUserId(loginUser.getUserId());
            criteria.andCadreIdEqualTo(cadre.getId());
            List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);
            modelMap.put("cadreEdus", cadreEdus);

            return "modify/modifyCadreEdu/modifyCadreEdu_page";
        } else {
            return "forward:/modifyTableApply_page?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreEdu:list")
    @RequestMapping("/modifyCadreEdu_detail")
    public String modifyCadreEdu_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreEduExample example = new CadreEduExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        Cadre cadre = cadreService.findByUserId(userId);
        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreEdu> cadreEdus = cadreEduMapper.selectByExample(example);
        modelMap.put("cadreEdus", cadreEdus);

        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreEduMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreEdu/modifyCadreEdu_detail";

    }
}
