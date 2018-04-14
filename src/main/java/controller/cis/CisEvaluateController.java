package controller.cis;

import domain.cis.CisEvaluate;
import domain.cis.CisEvaluateExample;
import domain.cis.CisEvaluateExample.Criteria;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CisEvaluateController extends CisBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisEvaluate:list")
    @RequestMapping("/cisEvaluate")
    public String cisEvaluate(HttpServletResponse response,
                                   Integer cadreId,
                                   ModelMap modelMap) {

        if(cadreId!=null) {
            modelMap.put("cadre", CmTag.getCadreById(cadreId));
        }
        return "cis/cisEvaluate/cisEvaluate_page";
    }

    @RequiresPermissions("cisEvaluate:list")
    @RequestMapping("/cisEvaluate_data")
    public void cisEvaluate_data(HttpServletResponse response,
                                 Integer cadreId,
                                 Byte type,
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

        CisEvaluateExample example = new CisEvaluateExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_date desc");

        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        int count = cisEvaluateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisEvaluate> cisEvaluates = cisEvaluateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cisEvaluates);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cisEvaluate.class, cisEvaluateMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cisEvaluate:edit")
    @RequestMapping(value = "/cisEvaluate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisEvaluate_au(CisEvaluate record,
                                 String _createDate,
                                 MultipartFile _file,
                                 HttpServletRequest request) {

        Integer id = record.getId();
        if (StringUtils.isNotBlank(_createDate)) {
            record.setCreateDate(DateUtils.parseDate(_createDate, DateUtils.YYYY_MM_DD));
        }
        if(_file!=null){
            String ext = FileUtils.getExtention(_file.getOriginalFilename());
            if(!StringUtils.equalsIgnoreCase(ext, ".pdf")){
               return failed("文件格式错误，请上传pdf文档");
            }

            String originalFilename = _file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "cis" + FILE_SEPARATOR
                    + fileName;
            String savePath =  realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_file, new File(springProps.uploadPath + savePath));

            try {
                String swfPath = realPath + ".swf";
                pdf2Swf(savePath, swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            record.setFileName(originalFilename);
            record.setFilePath(savePath);
        }
        if (id == null) {
            cisEvaluateService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加现实表现材料和评价：%s", record.getId()));
        } else {

            cisEvaluateService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新现实表现材料和评价：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisEvaluate:edit")
    @RequestMapping("/cisEvaluate_au")
    public String cisEvaluate_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisEvaluate cisEvaluate = cisEvaluateMapper.selectByPrimaryKey(id);
            int cadreId = cisEvaluate.getCadreId();
            modelMap.put("cadre", CmTag.getCadreById(cadreId));
            modelMap.put("cisEvaluate", cisEvaluate);
        }
        return "cis/cisEvaluate/cisEvaluate_au";
    }

    @RequiresPermissions("cisEvaluate:del")
    @RequestMapping(value = "/cisEvaluate_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisEvaluate_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cisEvaluateService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除现实表现材料和评价：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisEvaluate:del")
    @RequestMapping(value = "/cisEvaluate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisEvaluateService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除现实表现材料和评价：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
