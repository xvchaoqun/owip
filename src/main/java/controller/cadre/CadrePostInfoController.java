package controller.cadre;

import controller.BaseController;
import domain.cadre.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CadrePostInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostInfo_page")
    public String cadrePostInfo_page(int cadreId) {

        return "cadre/cadrePostInfo/cadrePostInfo_page";
    }

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostPro_data")
    @ResponseBody
    public void cadrePostPro_data(HttpServletResponse response,
                              Integer cadreId,
                              Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostProExample example = new CadrePostProExample();
        CadrePostProExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("grade_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }


        int count = cadrePostProMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePostPro> CadrePostPros = cadrePostProMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", CadrePostPros);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostAdmin_data")
    @ResponseBody
    public void cadrePostAdmin_data(HttpServletResponse response,
                                  Integer cadreId,
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostAdminExample example = new CadrePostAdminExample();
        CadrePostAdminExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("grade_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }


        int count = cadrePostAdminMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePostAdmin> cadrePostAdmins = cadrePostAdminMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadrePostAdmins);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostWork_data")
    @ResponseBody
    public void cadrePostWork_data(HttpServletResponse response,
                                  Integer cadreId,
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadrePostWorkExample example = new CadrePostWorkExample();
        CadrePostWorkExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("grade_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }


        int count = cadrePostWorkMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadrePostWork> cadrePostWorks = cadrePostWorkMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadrePostWorks);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }
}
