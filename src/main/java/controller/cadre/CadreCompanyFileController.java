package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreCompanyFile;
import domain.cadre.CadreCompanyFileExample;
import domain.cadre.CadreCompanyFileExample.Criteria;
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
import sys.constants.LogConstants;
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

public class CadreCompanyFileController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompanyFiles")
    public String cadreCompanyFiles(boolean type, ModelMap modelMap) {

        List<CadreCompanyFile> cadreCompanyFiles = cadreCompanyFileService.findAll(type);
        modelMap.put("cadreCompanyFiles", cadreCompanyFiles);

        return "cadre/cadreCompanyFile/cadreCompanyFiles";
    }
    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompanyFile")
    public String cadreCompanyFile() {

        return "cadre/cadreCompanyFile/cadreCompanyFile_page";
    }

    @RequiresPermissions("cadreCompany:list")
    @RequestMapping("/cadreCompanyFile_data")
    @ResponseBody
    public void cadreCompanyFile_data(HttpServletResponse response,
                                    Boolean type,
                                    Integer dispatchWorkFileId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreCompanyFileExample example = new CadreCompanyFileExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (dispatchWorkFileId!=null) {
            criteria.andDispatchWorkFileIdEqualTo(dispatchWorkFileId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cadreCompanyFile_export(example, response);
            return;
        }

        long count = cadreCompanyFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreCompanyFile> records= cadreCompanyFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cadreCompanyFile.class, cadreCompanyFileMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping(value = "/cadreCompanyFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompanyFile_au(CadreCompanyFile record, HttpServletRequest request) {

        Integer id = record.getId();

        record.setType(BooleanUtils.isTrue(record.getType()));

        if (id == null) {
            
            cadreCompanyFileService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加干部兼职管理文件：%s", record.getId()));
        } else {

            cadreCompanyFileService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新干部兼职管理文件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:edit")
    @RequestMapping("/cadreCompanyFile_au")
    public String cadreCompanyFile_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreCompanyFile cadreCompanyFile = cadreCompanyFileMapper.selectByPrimaryKey(id);
            modelMap.put("cadreCompanyFile", cadreCompanyFile);
        }
        return "cadre/cadreCompanyFile/cadreCompanyFile_au";
    }

    /*@RequiresPermissions("cadreCompany:del")
    @RequestMapping(value = "/cadreCompanyFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompanyFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreCompanyFileService.del(id);
            logger.info(addLog( LogConstants.LOG_ADMIN, "删除干部兼职管理文件：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("cadreCompany:del")
    @RequestMapping(value = "/cadreCompanyFile_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreCompanyFile_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreCompanyFileService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除干部兼职管理文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreCompany:changeOrder")
    @RequestMapping(value = "/cadreCompanyFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreCompanyFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreCompanyFileService.changeOrder(id, addNum);
        logger.info(addLog( LogConstants.LOG_ADMIN, "干部兼职管理文件调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cadreCompanyFile_export(CadreCompanyFileExample example, HttpServletResponse response) {

        List<CadreCompanyFile> records = cadreCompanyFileMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"类型|100","dispatch_work_file_id|100","排序|100","remark|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CadreCompanyFile record = records.get(i);
            String[] values = {
                record.getType()+"",
                            record.getDispatchWorkFileId()+"",
                            record.getSortOrder()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "干部兼职管理文件_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
