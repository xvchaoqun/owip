package controller.sys;

import bean.ShortMsgBean;
import controller.BaseController;
import domain.ShortMsg;
import domain.ShortMsgExample;
import domain.ShortMsgExample.Criteria;
import domain.SysUser;
import mixin.ShortMsgMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShortMsgController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("shortMsg:view")
    @RequestMapping("/shortMsg_view")
    public String shortMsg_view(@CurrentUser SysUser loginUser,
                                String type, // passport
                                Integer id, ModelMap modelMap) {

        ShortMsgBean shortMsgBean = shortMsgService.getShortMsgBean(loginUser.getId(), null, type, id);
        modelMap.put("shortMsgBean", shortMsgBean);

        return "sys/shortMsg/short_msg_view";
    }

    @RequiresPermissions("ShortMsg:*")
    @RequestMapping(value = "/shortMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsg(@CurrentUser SysUser loginUser, String type, Integer id, HttpServletRequest request) {

        ShortMsgBean shortMsgBean = shortMsgService.getShortMsgBean(loginUser.getId(), null, type, id);

        if(shortMsgService.send(shortMsgBean, IpUtils.getRealIp(request))){
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    @RequiresPermissions("shortMsg:list")
    @RequestMapping("/shortMsg")
    public String shortMsg() {

        return "index";
    }

    @RequiresPermissions("shortMsg:list")
    @RequestMapping("/shortMsg_page")
    public String shortMsg_page(Integer receiverId, ModelMap modelMap) {
        if (receiverId!=null) {

            SysUser sysUser = sysUserService.findById(receiverId);
            modelMap.put("sysUser", sysUser);
        }
        return "sys/shortMsg/shortMsg_page";
    }
    @RequiresPermissions("shortMsg:list")
    @RequestMapping("/shortMsg_data")
    @ResponseBody
    public void shortMsg_data(HttpServletResponse response,
                                    Integer receiverId,
                                    String mobile,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ShortMsgExample example = new ShortMsgExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (receiverId!=null) {
            criteria.andReceiverIdEqualTo(receiverId);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike("%" + mobile + "%");
        }

        int count = shortMsgMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ShortMsg> shortMsgs = shortMsgMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", shortMsgs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(ShortMsg.class, ShortMsgMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("shortMsg:edit")
    @RequestMapping(value = "/shortMsg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsg_au(ShortMsg record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            record.setStatus(true);
            shortMsgService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "添加短信：%s", record.getId()));
        } else {

            shortMsgService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "更新短信：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsg:edit")
    @RequestMapping("/shortMsg_au")
    public String shortMsg_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ShortMsg shortMsg = shortMsgMapper.selectByPrimaryKey(id);
            modelMap.put("shortMsg", shortMsg);
        }
        return "sys/shortMsg/shortMsg_au";
    }
/*
    @RequiresPermissions("shortMsg:del")
    @RequestMapping(value = "/shortMsg_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_shortMsg_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            shortMsgService.del(id);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "删除短信：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("shortMsg:del")
    @RequestMapping(value = "/shortMsg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            shortMsgService.batchDel(ids);
            logger.info(addLog(request, SystemConstants.LOG_ADMIN, "批量删除短信：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
