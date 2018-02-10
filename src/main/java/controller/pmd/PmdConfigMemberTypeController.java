package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdConfigMemberTypeExample;
import domain.pmd.PmdConfigMemberTypeExample.Criteria;
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
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
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
@RequestMapping("/pmd")
public class PmdConfigMemberTypeController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdConfigMemberType:list")
    @RequestMapping("/pmdConfigMemberType")
    public String pmdConfigMemberType() {

        return "pmd/pmdConfigMemberType/pmdConfigMemberType_page";
    }

    @RequiresPermissions("pmdConfigMemberType:list")
    @RequestMapping("/pmdConfigMemberType_data")
    public void pmdConfigMemberType_data(HttpServletResponse response,
                                  Byte type,
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdConfigMemberTypeExample example = new PmdConfigMemberTypeExample();
        Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("sort_order asc");

        if (type != null) {

            criteria.andTypeEqualTo(type);
        }

        long count = pmdConfigMemberTypeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdConfigMemberType> records= pmdConfigMemberTypeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdConfigMemberType.class, pmdConfigMemberTypeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdConfigMemberType:edit")
    @RequestMapping(value = "/pmdConfigMemberType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMemberType_au(PmdConfigMemberType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setIsDeleted(false);
            pmdConfigMemberTypeService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_PMD, "添加党员计费类别：%s", record.getId()));
        } else {

            pmdConfigMemberTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_PMD, "更新党员计费类别：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdConfigMemberType:edit")
    @RequestMapping("/pmdConfigMemberType_au")
    public String pmdConfigMemberType_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeMapper.selectByPrimaryKey(id);
            modelMap.put("pmdConfigMemberType", pmdConfigMemberType);
        }

        modelMap.put("pmdNorms", pmdNormService.list(PmdConstants.PMD_NORM_TYPE_PAY, null));

        return "pmd/pmdConfigMemberType/pmdConfigMemberType_au";
    }

    @RequiresPermissions("pmdConfigMemberType:del")
    @RequestMapping(value = "/pmdConfigMemberType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMemberType_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdConfigMemberTypeService.del(id);
            logger.info(addLog( SystemConstants.LOG_PMD, "删除党员计费类别：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdConfigMemberType:del")
    @RequestMapping(value = "/pmdConfigMemberType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmdConfigMemberTypeService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_PMD, "批量删除党员计费类别：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdConfigMemberType:changeOrder")
    @RequestMapping(value = "/pmdConfigMemberType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMemberType_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pmdConfigMemberTypeService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_PMD, "党员计费类别调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
