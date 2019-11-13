package controller.sys;

import controller.BaseController;
import domain.sys.SysMsg;
import domain.sys.SysMsgExample;
import domain.sys.SysMsgExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.sys.SysMsgMapper;
import service.sys.SysMsgService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller

public class SysMsgController extends BaseController {

    @Autowired
    private SysMsgMapper sysMsgMapper;
    @Autowired
    private SysMsgService sysMsgService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("sysMsg:list")
    @RequestMapping("/sysMsg")
    public String sysMsg(Integer userId, Integer type, ModelMap modelMap) {

        if (userId != null){
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        modelMap.put("type",type);
        return "sys/sysMsg/sysMsg_page";
    }

    @RequiresPermissions("sysMsg:list")
    @RequestMapping("/sysMsg_data")
    @ResponseBody
    public void sysMsg_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer type,
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

        SysMsgExample example = new SysMsgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (type == 2){
            userId = ShiroHelper.getCurrentUserId();
        }

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = sysMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<SysMsg> records= sysMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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

    @RequiresPermissions("sysMsg:edit")
    @RequestMapping(value = "/sysMsg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sysMsg_au(SysMsg record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            record.setCreateTime(new Date());
            record.setSendUserId(ShiroHelper.getCurrentUserId());
            record.setIp(ContextHelper.getRealIp());
            record.setStatus(SystemConstants.SYS_MSG_STATUS_UNREAD);

            sysMsgService.insertSelective(record);
            logger.info(log( LogConstants.LOG_USER, "添加系统提醒：{0}", record.getId()));
        } else {

            sysMsgService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_USER, "更新系统提醒：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("sysMsg:edit")
    @RequestMapping("/sysMsg_au")
    public String sysMsg_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            SysMsg sysMsg = sysMsgMapper.selectByPrimaryKey(id);
            modelMap.put("sysMsg", sysMsg);
        }
        return "sys/sysMsg/sysMsg_au";
    }

    @RequiresPermissions("sysMsg:edit")
    @RequestMapping(value = "/sysMsg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map sysMsg_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            sysMsgService.batchDel(ids);
            logger.info(log( LogConstants.LOG_USER, "批量删除系统提醒：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/sysMsg_selects")
    @ResponseBody
    public Map sysMsg_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SysMsgExample example = new SysMsgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        long count = sysMsgMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SysMsg> records = sysMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(SysMsg record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    @RequiresPermissions("sysMsg:list")
    @RequestMapping("/sysMsg_view")
    public String sysMsg_view(Integer id, Integer type, ModelMap modelMap) {

        if (id != null) {
            SysMsg sysMsg = sysMsgMapper.selectByPrimaryKey(id);
            modelMap.put("sysMsg", sysMsg);

            if (type == 2){

                sysMsg.setStatus(SystemConstants.SYS_MSG_STATUS_READ);
                sysMsgService.updateByPrimaryKeySelective(sysMsg);
            }
        }
        return "sys/sysMsg/sysMsg_view";
    }
}
