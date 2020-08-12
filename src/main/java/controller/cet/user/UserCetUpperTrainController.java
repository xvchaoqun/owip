package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetUpperTrain;
import domain.cet.CetUpperTrainExample;
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
import shiro.ShiroHelper;
import sys.constants.CetConstants;
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
@RequestMapping("/user/cet")
public class UserCetUpperTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 学员 第三级：培训班
    @RequiresPermissions("userCetUpperTrain:list")
    @RequestMapping("/cetUpperTrain")
    public String cetUpperTrain(@RequestParam(required = false, defaultValue = "1")Byte cls,
                                ModelMap modelMap) {
        
        modelMap.put("cls", cls);

        return "cet/user/cetUpperTrain_page";
    }

    @RequiresPermissions("userCetUpperTrain:list")
    @RequestMapping("/cetUpperTrain_data")
    @ResponseBody
    public void cetUpperTrain_data(HttpServletResponse response,
                                   Byte type,
                                   @RequestParam(required = false, defaultValue = "1")Byte cls,
                                   Integer pageSize, Integer pageNo) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetUpperTrainExample example = new CetUpperTrainExample();
        CetUpperTrainExample.Criteria criteria =
                example.createCriteria().andUserIdEqualTo(userId)
                .andIsDeletedEqualTo(false);
        example.setOrderByClause("id desc");

        if(cls==1){
            criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
        }else if(cls==2){
            criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
        }else{
            criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS);
        }
        if(type==null){
            criteria.andTypeNotEqualTo(CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD);
        }else if(type==CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD){
            criteria.andTypeEqualTo(CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD);
        }else if(type==CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL){
            criteria.andTypeEqualTo(CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL);
        }

        long count = cetUpperTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUpperTrain> records = cetUpperTrainMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("userCetUpperTrain:del")
    @RequestMapping(value = "/cetUpperTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUpperTrain_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cetUpperTrainService.batchDelBySelf(ids);
            logger.info(addLog(LogConstants.LOG_CET, "本人批量删除上级调训：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
