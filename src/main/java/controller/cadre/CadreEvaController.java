package controller.cadre;

import controller.BaseController;
import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import domain.cadre.CadreEvaExample.Criteria;
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

public class CadreEvaController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreEva:list")
    @RequestMapping("/cadreEva_page")
    public String cadreEva_page() {

        return "cadre/cadreEva/cadreEva_page";
    }

    @RequiresPermissions("cadreEva:list")
    @RequestMapping("/cadreEva_data")
    @ResponseBody
    public void cadreEva_data(HttpServletResponse response,
                                    Integer cadreId,
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreEvaExample example = new CadreEvaExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("year desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        long count = cadreEvaMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreEva> records= cadreEvaMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cadreEva.class, cadreEvaMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cadreEva:edit")
    @RequestMapping(value = "/cadreEva_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEva_au(CadreEva record, HttpServletRequest request) {

        Integer id = record.getId();

        if (cadreEvaService.idDuplicate(id, record.getCadreId(), record.getYear())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            cadreEvaService.insertSelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "添加年度考核记录：%s", record.getId()));
        } else {

            cadreEvaService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_ADMIN, "更新年度考核记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEva:edit")
    @RequestMapping("/cadreEva_au")
    public String cadreEva_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CadreEva cadreEva = cadreEvaMapper.selectByPrimaryKey(id);
            modelMap.put("cadreEva", cadreEva);
        }
        return "cadre/cadreEva/cadreEva_au";
    }

    @RequiresPermissions("cadreEva:del")
    @RequestMapping(value = "/cadreEva_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEva_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cadreEvaService.del(id);
            logger.info(addLog( LogConstants.LOG_ADMIN, "删除年度考核记录：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreEva:del")
    @RequestMapping(value = "/cadreEva_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cadreEva_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cadreEvaService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_ADMIN, "批量删除年度考核记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
