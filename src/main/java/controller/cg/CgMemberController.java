package controller.cg;

import domain.cg.CgMember;
import domain.cg.CgMemberExample;
import domain.cg.CgMemberExample.Criteria;
import domain.unit.UnitPost;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import persistence.unit.UnitPostMapper;
import service.sys.SysUserService;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cg")
public class CgMemberController extends CgBaseController {
    @Autowired
    private UnitPostMapper unitPostMapper;
    @Autowired
    private SysUserService sysUserService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cgMember:list")
    @RequestMapping("/cgMember")
    public String cgMember(Integer teamId) {

        return "cg/cgMember/cgMember_page";
    }

    @RequiresPermissions("cgMember:list")
    @RequestMapping("/cgMember_data")
    @ResponseBody
    public void cgMember_data(HttpServletResponse response,
                                    Integer teamId,
                                    Integer post,
                                    Byte type,
                                    Integer unitPostId,
                                    Integer userId,
                                
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

        CgMemberExample example = new CgMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (teamId!=null) {
            criteria.andTeamIdEqualTo(teamId);
        }
        if (post!=null) {
            criteria.andPostEqualTo(post);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (unitPostId!=null) {
            criteria.andUnitPostIdEqualTo(unitPostId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cgMember_export(example, response);
            return;
        }

        long count = cgMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CgMember> records= cgMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cgMember.class, cgMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cgMember:edit")
    @RequestMapping(value = "/cgMember_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgMember_au(@RequestParam(value = "userIdsList[]", required = false) Integer[] userIds,
                              CgMember record,HttpServletRequest request) {

        Integer id = record.getId();

        record.setIsCurrent(BooleanUtils.isTrue(record.getIsCurrent()));
        /*if (cgMemberService.idDuplicate(id, record.getTeamId(), record.getUserId(), record.getIsCurrent())) {
            return failed("添加重复");
        }*/
        if (id == null) {
            
            cgMemberService.insertSelective(record);
            logger.info(log( LogConstants.LOG_CG, "添加委员会和领导小组成员：{0}", record.getId()));
        } else {

            cgMemberService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_CG, "更新委员会和领导小组成员：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgMember:edit")
    @RequestMapping("/cgMember_au")
    public String cgMember_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CgMember cgMember = cgMemberMapper.selectByPrimaryKey(id);
            UnitPost unitPost = unitPostMapper.selectByPrimaryKey(cgMember.getUnitPostId());
            modelMap.put("sysUser",sysUserService.findById(cgMember.getUserId()));
            modelMap.put("unitPost",unitPost);
            modelMap.put("cgMember", cgMember);
        }
        return "cg/cgMember/cgMember_au";
    }

    @RequiresPermissions("cgMember:del")
    @RequestMapping(value = "/cgMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgMember_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cgMemberService.del(id);
            logger.info(log( LogConstants.LOG_CG, "删除委员会和领导小组成员：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cgMember:del")
    @RequestMapping(value = "/cgMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cgMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cgMemberService.batchDel(ids);
            logger.info(log( LogConstants.LOG_CG, "批量删除委员会和领导小组成员：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("cgMember:changeOrder")
    @RequestMapping(value = "/cgMember_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cgMember_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cgMemberService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_CG, "委员会和领导小组成员调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void cgMember_export(CgMemberExample example, HttpServletResponse response) {

        List<CgMember> records = cgMemberMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属委员会或领导小组|100","职务|100","人员类型|100","关联岗位|100","现任干部|100","代表类型|100","添加日期|100","移除日期|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CgMember record = records.get(i);
            String[] values = {
                record.getTeamId()+"",
                            record.getPost()+"",
                            record.getType()+"",
                            record.getUnitPostId()+"",
                            record.getUserId()+"",
                            record.getTag(),
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("委员会和领导小组成员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    /*@RequestMapping("/cgMember_selects")
    @ResponseBody
    public Map cgMember_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CgMemberExample example = new CgMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = cgMemberMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CgMember> records = cgMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CgMember record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }*/
}
