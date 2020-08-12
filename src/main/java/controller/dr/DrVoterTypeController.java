package controller.dr;

import domain.dr.DrVoterType;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class DrVoterTypeController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drVoterTypeTpl:edit")
    @RequestMapping(value = "/drVoterType_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drVoterType_au(DrVoterType record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            drVoterTypeService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加：%s", id));
        } else {

            drVoterTypeService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:edit")
    @RequestMapping("/drVoterType_au")
    public String drVoterType_au(Integer id, Integer tplId, ModelMap modelMap) {

        if (id != null) {
            DrVoterType drVoterType = drVoterTypeMapper.selectByPrimaryKey(id);
            modelMap.put("drVoterType", drVoterType);
        } else if (tplId != null) {
            DrVoterType drVoterType = new DrVoterType();
            drVoterType.setTplId(tplId);
            modelMap.put("drVoterType", drVoterType);
        }

        modelMap.put("drVoterTypeTplMap", drVoterTypeTplService.findAll());
        return "dr/drVoterType/drVoterType_au";
    }

    @RequiresPermissions("drVoterTypeTpl:del")
    @RequestMapping(value = "/drVoterType_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drVoterType_del(Integer id, HttpServletRequest request) {

        if (id != null) {
            drVoterTypeService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:del")
    @RequestMapping(value = "/drVoterType_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids) {
            drVoterTypeService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drVoterTypeTpl:changeOrder")
    @RequestMapping(value = "/drVoterType_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drVoterType_changeOrder(Integer id, Integer tplId, Integer addNum, HttpServletRequest request) {

        Assert.isTrue(tplId > 0, "wrong tplId");
        drVoterTypeService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
