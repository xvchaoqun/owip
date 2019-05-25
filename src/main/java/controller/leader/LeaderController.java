package controller.leader;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.leader.*;
import domain.sys.SysUserView;
import mixin.CadreMixin;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class LeaderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("leaderInfo:list")
    @RequestMapping("/leaderInfo")
    public String leaderInfo(@RequestParam(required = false,
            defaultValue = CadreConstants.CADRE_STATUS_LEADER+"")Byte status,
                             Integer cadreId,ModelMap modelMap) {

        modelMap.put("status", status);

        if (cadreId!=null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
            if(cadre!=null) {
                SysUserView sysUser = sysUserService.findById(cadre.getUserId());
                modelMap.put("sysUser", sysUser);
            }
        }

        return "leader/leader/leaderInfo_page";
    }

    @RequiresPermissions("leader:list")
    @RequestMapping("/leader")
    public String leader(HttpServletResponse response,
                              @RequestParam(required = false, defaultValue = "1")Byte cls,
                              Integer cadreId,ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(cls==2){
            return "forward:/leaderUnit";
        }

        if (cadreId!=null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        return "leader/leader/leader_page";
    }
    @RequiresPermissions("leader:list")
    @RequestMapping("/leader_data")
    @ResponseBody
    public void leader_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer typeId,
                                    String job,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        LeaderViewExample example = new LeaderViewExample();
        LeaderViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (typeId!=null) {
            criteria.andTypeIdEqualTo(typeId);
        }
        if (StringUtils.isNotBlank(job)) {
            criteria.andJobLike(SqlUtils.like(job));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            leader_export(example, response);
            return ;
        }

        int count = (int) leaderViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<LeaderView> Leaders = leaderViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", Leaders);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(Cadre.class, CadreMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 从校领导信息中提取
    @RequiresPermissions("leader:edit")
    @RequestMapping("/leader_fromLeader")
    public String leader_fromLeader(ModelMap modelMap) {

        return "leader/leader/leader_fromLeader";
    }

    @RequiresPermissions("leader:edit")
    @RequestMapping(value = "/leader_fromLeader", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_fromLeader(HttpServletRequest request,
                                @RequestParam(value = "cadreIds[]") Integer[] cadreIds, ModelMap modelMap) {

        if (null != cadreIds && cadreIds.length > 0) {

            leaderService.fromLeader(cadreIds);
            logger.info(addLog(LogConstants.LOG_PCS, "从校领导中提取校级领导：%s",
                    StringUtils.join(cadreIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 从党委常委中提取
    @RequiresPermissions("leader:edit")
    @RequestMapping("/leader_fromCm")
    public String leader_fromCm(ModelMap modelMap) {

        return "leader/leader/leader_fromCm";
    }

    @RequiresPermissions("leader:edit")
    @RequestMapping(value = "/leader_fromCm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_fromCm(HttpServletRequest request,
                                @RequestParam(value = "memberIds[]") Integer[] memberIds, ModelMap modelMap) {

        if (null != memberIds && memberIds.length > 0) {

            leaderService.fromCm(memberIds);
            logger.info(addLog(LogConstants.LOG_PCS, "从党委常委中提取校级领导：%s",
                    StringUtils.join(memberIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:edit")
    @RequestMapping(value = "/leader_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_au(Leader record, ModelMap modelMap, HttpServletRequest request) {

        leaderService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "编辑校级领导信息：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:edit")
    @RequestMapping("/leader_au")
    public String leader_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            Leader leader = leaderMapper.selectByPrimaryKey(id);
            modelMap.put("leader", leader);
            CadreView cadre = leader.getCadre();
            modelMap.put("cadre", cadre);
            SysUserView sysUser = leader.getUser();
            modelMap.put("sysUser", sysUser);
        }
        return "leader/leader/leader_au";
    }

    @RequiresPermissions("leader:del")
    @RequestMapping(value = "/leader_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_del(Integer id, HttpServletRequest request) {

        if (id != null) {

            leaderService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除校领导：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:del")
    @RequestMapping(value = "/leader_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            leaderService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除校领导：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("leader:changeOrder")
    @RequestMapping(value = "/leader_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_leader_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        leaderService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "校领导调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void leader_export(LeaderViewExample example, HttpServletResponse response) {

        List<LeaderView> leaders = leaderViewMapper.selectByExample(example);
        int rownum = (int) leaderViewMapper.countByExample(example);

        String[] titles = {"工作证号|100", "姓名|100", "所在单位及职务|300|left",
                "行政级别|100", "类别|100", "是否校领导|100", "是否常委|100", "分管工作|500|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {

            LeaderView leader = leaders.get(i);
            CadreView cadre = leader.getCadre();

            String[] values = {
                    cadre.getCode(),
                    cadre.getRealname(),
                    cadre.getTitle(),
                    metaTypeService.getName(cadre.getAdminLevel()),
                    metaTypeService.getName(leader.getTypeId()),
                    (cadre.getStatus()==CadreConstants.CADRE_STATUS_LEADER
                            || cadre.getStatus()==CadreConstants.CADRE_STATUS_LEADER_LEAVE)?"是":"否",
                    BooleanUtils.isTrue(leader.getIsCommitteeMember()) ?"是":"否",
                    leader.getJob()
                    };

            valuesList.add(values);
        }

        String fileName = "校级领导分工";
        ExportHelper.export(titles, valuesList,  fileName, response);
    }

    @RequiresPermissions("leader:unit")
    @RequestMapping("/leader_unit")
    public String leader_unit(Integer id,  Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (id != null) {

            Leader leader = leaderMapper.selectByPrimaryKey(id);
            modelMap.put("leader", leader);

            Integer userId = leader.getCadre().getUserId();

            if (null == pageSize) {
                pageSize = springProps.pageSize;
            }
            if (null == pageNo) {
                pageNo = 1;
            }
            pageNo = Math.max(1, pageNo);

            LeaderUnitExample example = new LeaderUnitExample();
            LeaderUnitExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
            example.setOrderByClause("sort_order asc");

            long count = leaderUnitMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {

                pageNo = Math.max(1, pageNo - 1);
            }
            List<LeaderUnit> leaderUnits = leaderUnitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            modelMap.put("leaderUnits", leaderUnits);

            CommonList commonList = new CommonList(count, pageNo, pageSize);

            String searchStr = "&pageSize=" + pageSize;

            if (userId!=null) {
                searchStr += "&id=" + id;
            }

            commonList.setSearchStr(searchStr);
            modelMap.put("commonList", commonList);
        }

        return "leader/leader/leader_unit";
    }
}
