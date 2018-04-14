package controller.sc.scGroup;

import domain.sc.scGroup.ScGroupParticipant;
import domain.sys.SysUserView;
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
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScGroupParticipantController extends ScGroupBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scGroupParticipant:list")
    @RequestMapping("/scGroupParticipant")
    public String scGroupParticipant(Integer groupId, ModelMap modelMap) {

        List<SysUserView> userList = scGroupService.getMemberUserList(groupId);
        modelMap.put("userList", userList);

        return "sc/scGroup/scGroupParticipant/scGroupParticipant_page";
    }

    /*@RequiresPermissions("scGroupParticipant:list")
    @RequestMapping("/scGroupParticipant_data")
    public void scGroupParticipant_data(HttpServletResponse response,
                                    Integer groupId,
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

        ScGroupParticipantExample example = new ScGroupParticipantExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id asc");

        if (groupId!=null) {
            criteria.andGroupIdEqualTo(groupId);
        }

        long count = scGroupParticipantMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScGroupParticipant> records= scGroupParticipantMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scGroupParticipant.class, scGroupParticipantMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }*/

    @RequiresPermissions("scGroupParticipant:edit")
    @RequestMapping(value = "/scGroupParticipant_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scGroupParticipant_au(ScGroupParticipant record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            scGroupParticipantService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "添加干部小组会参会人：%s", record.getId()));
        } else {

            scGroupParticipantService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "更新干部小组会参会人：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scGroupParticipant:edit")
    @RequestMapping("/scGroupParticipant_au")
    public String scGroupParticipant_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScGroupParticipant scGroupParticipant = scGroupParticipantMapper.selectByPrimaryKey(id);
            modelMap.put("scGroupParticipant", scGroupParticipant);
        }
        return "sc/scGroup/scGroupParticipant/scGroupParticipant_au";
    }


    @RequiresPermissions("scGroupParticipant:del")
    @RequestMapping(value = "/scGroupParticipant_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scGroupParticipantService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_GROUP, "批量删除干部小组会参会人：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
