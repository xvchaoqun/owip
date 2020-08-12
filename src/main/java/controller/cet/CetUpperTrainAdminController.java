package controller.cet;

import domain.cet.CetUpperTrainAdmin;
import domain.cet.CetUpperTrainAdminExample;
import domain.cet.CetUpperTrainAdminExample.Criteria;
import domain.sys.SysUserView;
import domain.unit.Unit;
import mixin.MixinUtils;
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
import sys.constants.LogConstants;
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
@RequestMapping("/cet")
public class CetUpperTrainAdminController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetUpperTrainAdmin:list")
    @RequestMapping("/cetUpperTrainAdmin")
    public String cetUpperTrainAdmin(Integer unitId,
                                     Integer userId,
                                     ModelMap modelMap) {

        if(unitId!=null){
            modelMap.put("unit", CmTag.getUnit(unitId));
        }
        if(userId!=null){
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }
        

        return "cet/cetUpperTrainAdmin/cetUpperTrainAdmin_page";
    }

    @RequiresPermissions("cetUpperTrainAdmin:list")
    @RequestMapping("/cetUpperTrainAdmin_data")
    @ResponseBody
    public void cetUpperTrainAdmin_data(HttpServletResponse response,
                                    Integer unitId,
                                    Integer userId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetUpperTrainAdminExample example = new CetUpperTrainAdminExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUpperTrainAdmin_export(example, response);
            return;
        }

        long count = cetUpperTrainAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUpperTrainAdmin> records= cetUpperTrainAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetUpperTrainAdmin.class, cetUpperTrainAdminMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetUpperTrainAdmin:edit")
    @RequestMapping(value = "/cetUpperTrainAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrainAdmin_au(CetUpperTrainAdmin record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            cetUpperTrainAdminService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "添加培训单位管理员：%s", record.getUserId()));
        } else {

            cetUpperTrainAdminService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "更新培训单位管理员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUpperTrainAdmin:edit")
    @RequestMapping("/cetUpperTrainAdmin_au")
    public String cetUpperTrainAdmin_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CetUpperTrainAdmin cetUpperTrainAdmin = cetUpperTrainAdminMapper.selectByPrimaryKey(id);
            modelMap.put("cetUpperTrainAdmin", cetUpperTrainAdmin);
        }

        return "cet/cetUpperTrainAdmin/cetUpperTrainAdmin_au";
    }

    @RequiresPermissions("cetUpperTrainAdmin:del")
    @RequestMapping(value = "/cetUpperTrainAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrainAdmin_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetUpperTrainAdminService.del(id);
            logger.info(addLog( LogConstants.LOG_CET, "删除培训单位管理员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetUpperTrainAdmin:del")
    @RequestMapping(value = "/cetUpperTrainAdmin_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUpperTrainAdmin_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetUpperTrainAdminService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_CET, "批量删除培训单位管理员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetUpperTrainAdmin_export(CetUpperTrainAdminExample example, HttpServletResponse response) {

        List<CetUpperTrainAdmin> records = cetUpperTrainAdminMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|100", "所属单位|300|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUpperTrainAdmin record = records.get(i);
            SysUserView uv = CmTag.getUserById(record.getUserId());
            Unit unit = CmTag.getUnit(record.getUnitId());
            String[] values = {
                            uv.getCode(),
                            uv.getRealname(),
                            unit!=null?unit.getName():""
            };
            valuesList.add(values);
        }
        String fileName = "培训单位管理员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
