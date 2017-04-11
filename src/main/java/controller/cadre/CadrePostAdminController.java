package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import domain.sys.SysUserView;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadrePostAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostAdmin_data")
    @ResponseBody
    public void cadrePostAdmin_data(HttpServletResponse response,
                                    Integer cadreId,
                                    Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostAdminExample example = new CadrePostAdminExample();
        CadrePostAdminExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("grade_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }


        int count = cadrePostAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePostAdmin> cadrePostAdmins = cadrePostAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadrePostAdmins);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }


    @RequiresPermissions("cadrePostInfo:edit")
    @RequestMapping(value = "/cadrePostAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadrePostAdmin_au(CadrePostAdmin record, String _gradeTime, HttpServletRequest request) {

        Integer id = record.getId();

        if(StringUtils.isNotBlank(_gradeTime)){
            record.setGradeTime(DateUtils.parseDate(_gradeTime, "yyyy.MM"));
        }
        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));

        if (id == null) {
            cadrePostAdminService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加管理岗位过程信息：%s", record.getId()));
        } else {
            // 干部信息本人直接修改数据校验
            CadrePostAdmin _record = cadrePostAdminMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }
            cadrePostAdminService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新管理岗位过程信息：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadrePostInfo:edit")
    @RequestMapping("/cadrePostAdmin_au")
    public String cadrePostAdmin_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadrePostAdmin cadrePostAdmin = cadrePostAdminMapper.selectByPrimaryKey(id);
            modelMap.put("cadrePostAdmin", cadrePostAdmin);
        }

        CadreView cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);

        return "cadre/cadrePostAdmin/cadrePostAdmin_au";
    }

    @RequiresPermissions("cadrePostInfo:del")
    @RequestMapping(value = "/cadrePostAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadrePostAdminService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除管理岗位过程信息：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
