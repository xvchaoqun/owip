package controller.sc.scLetter;

import controller.ScLetterBaseController;
import domain.sc.scLetter.ScLetterItem;
import domain.sc.scLetter.ScLetterItemExample;
import domain.sc.scLetter.ScLetterItemExample.Criteria;
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
public class ScLetterItemController extends ScLetterBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scLetterItem:list")
    @RequestMapping("/scLetterItem")
    public String scLetterItem() {

        return "sc/scLetter/scLetterItem/scLetterItem_page";
    }

    @RequiresPermissions("scLetterItem:list")
    @RequestMapping("/scLetterItem_data")
    public void scLetterItem_data(HttpServletResponse response,
                                    Integer letterId,
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

        ScLetterItemExample example = new ScLetterItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("letter_id desc");

        if (letterId!=null) {
            criteria.andLetterIdEqualTo(letterId);
        }

        long count = scLetterItemMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScLetterItem> records= scLetterItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scLetterItem.class, scLetterItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scLetterItem:edit")
    @RequestMapping(value = "/scLetterItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scLetterItem_au(ScLetterItem record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scLetterItemService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_SC_LETTER, "添加函询对象：%s", record.getId()));
        } else {

            scLetterItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_SC_LETTER, "更新函询对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scLetterItem:edit")
    @RequestMapping("/scLetterItem_au")
    public String scLetterItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScLetterItem scLetterItem = scLetterItemMapper.selectByPrimaryKey(id);
            modelMap.put("scLetterItem", scLetterItem);
        }
        return "sc/scLetter/scLetterItem/scLetterItem_au";
    }

    @RequiresPermissions("scLetterItem:del")
    @RequestMapping(value = "/scLetterItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scLetterItemService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_LETTER, "批量删除函询对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
