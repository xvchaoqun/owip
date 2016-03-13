package controller.abroad;

import bean.SafeBoxBean;
import controller.BaseController;
import domain.SafeBox;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class SafeBoxController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("safeBox:list")
    @RequestMapping("/safeBox")
    public String safeBox() {

        return "index";
    }
    @RequiresPermissions("safeBox:list")
    @RequestMapping("/safeBox_page")
    public String safeBox_page(HttpServletResponse response,
                                 //@SortParam(required = false, defaultValue = "sort_order", tableName = "abroad_safe_box") String sort,
                                 //@OrderParam(required = false, defaultValue = "desc") String order,
                                 // 1:集中管理证件 2:取消集中保管证件 3:丢失证件 4：作废证件
                                 @RequestParam(required = false, defaultValue = "1") byte status,
                                    String code,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        /*SafeBoxExample example = new SafeBoxExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause(String.format("%s %s", sort, order));

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeLike("%" + code + "%");
        }

        int count = safeBoxMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SafeBox> safeBoxs = safeBoxMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));*/

        List<SafeBoxBean> safeBoxBeans = selectMapper.listAllSafeBoxs();
        int count = safeBoxBeans.size();
        modelMap.put("safeBoxBeans", safeBoxBeans);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;

        modelMap.put("status", status);
        searchStr += "&status=" + status;

        if (StringUtils.isNotBlank(code)) {
            searchStr += "&code=" + code;
        }
       /* if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }*/
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        return "abroad/safeBox/safeBox_page";
    }

    @RequiresPermissions("safeBox:edit")
    @RequestMapping(value = "/safeBox_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_safeBox_au(SafeBox record, HttpServletRequest request) {

        Integer id = record.getId();

        if (safeBoxService.idDuplicate(id, record.getCode())) {
            return failed("添加重复");
        }
        if (id == null) {
            safeBoxService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "添加保险柜：%s", record.getId()));
        } else {

            safeBoxService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "更新保险柜：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("safeBox:edit")
    @RequestMapping("/safeBox_au")
    public String safeBox_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SafeBox safeBox = safeBoxMapper.selectByPrimaryKey(id);
            modelMap.put("safeBox", safeBox);
        }
        return "abroad/safeBox/safeBox_au";
    }

    @RequiresPermissions("safeBox:del")
    @RequestMapping(value = "/safeBox_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_safeBox_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            safeBoxService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "删除保险柜：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("safeBox:del")
    @RequestMapping(value = "/safeBox_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            safeBoxService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ABROAD, "批量删除保险柜：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("safeBox:changeOrder")
    @RequestMapping(value = "/safeBox_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_safeBox_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        safeBoxService.changeOrder(id, addNum);
        logger.info(addLog(request, SystemConstants.LOG_ABROAD, "保险柜调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
