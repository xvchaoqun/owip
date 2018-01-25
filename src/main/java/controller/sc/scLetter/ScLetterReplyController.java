package controller.sc.scLetter;

import controller.ScLetterBaseController;
import domain.sc.scLetter.ScLetterItemView;
import domain.sc.scLetter.ScLetterItemViewExample;
import domain.sc.scLetter.ScLetterReply;
import domain.sc.scLetter.ScLetterReplyItem;
import domain.sc.scLetter.ScLetterReplyItemView;
import domain.sc.scLetter.ScLetterReplyView;
import domain.sc.scLetter.ScLetterReplyViewExample;
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
import sys.gson.GsonUtils;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScLetterReplyController extends ScLetterBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scLetterReply:list")
    @RequestMapping("/scLetterReply")
    public String scLetterReply(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        return "sc/scLetter/scLetterReply/scLetterReply_page";
    }

    @RequiresPermissions("scLetterReply:list")
    @RequestMapping("/scLetterReply_data")
    public void scLetterReply_data(HttpServletResponse response,
                                    Integer letterYear,
                                    Integer letterType,
                                    Integer letterNum,
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

        ScLetterReplyViewExample example = new ScLetterReplyViewExample();
        ScLetterReplyViewExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("letter_id desc, reply_date desc");

        if (letterYear!=null) {
            criteria.andLetterYearEqualTo(letterYear);
        }
        if (letterType!=null) {
            criteria.andLetterTypeEqualTo(letterType);
        }
        if (letterNum!=null) {
            criteria.andLetterNumEqualTo(letterNum);
        }

        long count = scLetterReplyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScLetterReplyView> records= scLetterReplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scLetterReply.class, scLetterReplyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scLetterReply:edit")
    @RequestMapping(value = "/scLetterReply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scLetterReply_au(ScLetterReply record,
                                   String items,
                                   HttpServletRequest request) throws UnsupportedEncodingException {

        List<ScLetterReplyItem> scLetterReplyItems = GsonUtils.toBeans(items, ScLetterReplyItem.class);
        Integer id = record.getId();

        if (id == null) {

            scLetterReplyService.insertSelective(record, scLetterReplyItems);
            logger.info(addLog( SystemConstants.LOG_SC_LETTER, "添加纪委回复文件：%s", record.getId()));
        } else {

            scLetterReplyService.updateByPrimaryKeySelective(record, scLetterReplyItems);
            logger.info(addLog( SystemConstants.LOG_SC_LETTER, "更新纪委回复文件：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scLetterReply:edit")
    @RequestMapping("/scLetterReply_au")
    public String scLetterReply_au(Integer id, Integer letterId, ModelMap modelMap) {

        if (id != null) {
            ScLetterReply scLetterReply = scLetterReplyMapper.selectByPrimaryKey(id);
            modelMap.put("scLetterReply", scLetterReply);

            letterId = scLetterReply.getLetterId();
        }

        modelMap.put("letterId", letterId);

        List<ScLetterReplyItemView> itemList = new ArrayList<>();
        ScLetterItemViewExample example = new ScLetterItemViewExample();
        example.createCriteria().andLetterIdEqualTo(letterId);
        example.setOrderByClause("id asc");
        List<ScLetterItemView> scLetterItemViews = scLetterItemViewMapper.selectByExample(example);
        for (ScLetterItemView scLetterItemView : scLetterItemViews) {

            Integer userId = scLetterItemView.getUserId();
            ScLetterReplyItemView record = null;
            if(id!=null) {
                record = scLetterReplyService.getScLetterReplyItemView(id, userId);
            }

            if(record==null){
                record = new ScLetterReplyItemView();
                record.setUserId(userId);
                record.setCode(scLetterItemView.getCode());
                record.setRealname(scLetterItemView.getRealname());
            }
            itemList.add(record);
        }

        modelMap.put("itemList", itemList);

        return "sc/scLetter/scLetterReply/scLetterReply_au";
    }

    @RequiresPermissions("scLetterReply:del")
    @RequestMapping(value = "/scLetterReply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scLetterReplyService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_SC_LETTER, "批量删除纪委回复文件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
