package controller.pmd;

import domain.member.Member;
import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import domain.pmd.PmdFeeExample.Criteria;
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
import service.LoginUserService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
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
@RequestMapping("/pmd")
public class PmdFeeController extends PmdBaseController {

    @Autowired
    private LoginUserService loginUserService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdFee:list")
    @RequestMapping("/pmdFee")
    public String pmdFee(ModelMap modelMap) {

        Boolean hasShowUser = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) ||
                ShiroHelper.hasAnyRoles(RoleConstants.ROLE_PARTYADMIN,RoleConstants.ROLE_BRANCHADMIN);

        modelMap.put("hasShowUser",hasShowUser);
        return "pmd/pmdFee/pmdFee_page";
    }

    @RequiresPermissions("pmdFee:list")
    @RequestMapping("/pmdFee_data")
    @ResponseBody
    public void pmdFee_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    Byte isOnlinePay,
                                    Byte hasPay,
                                    Date payTime,
                                Byte status,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdFeeExample example = new PmdFeeExample();
        Criteria criteria = example.createCriteria();

            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            List<Integer> adminBranchIdList = loginUserService.adminBranchIdList();

            criteria.addPermits(adminPartyIdList,adminBranchIdList);

           /* //如果有管理的分党委，显示所管理的分党委成员
            if (!adminPartyIdList.isEmpty()){
                criteria.andPartyIdIn(adminPartyIdList);
            }

            //如果有管理的党支部，显示所管理的党支部成员
            if (!adminBranchIdList.isEmpty()){
                criteria.andBranchIdIn(adminBranchIdList);
            }

            //如果是党员，只显示自己的数据。
            if (adminPartyIdList.isEmpty() && adminBranchIdList.isEmpty()){
                criteria.andUserIdEqualTo(ShiroHelper.getCurrentUserId());
            }

            //如果有查看所有党委、支部的权限
            if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)){
                criteria = example.createCriteria();
            }*/

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        /*if (isOnlinePay!=null) {
            criteria.andIsOnlinePayEqualTo(isOnlinePay);
        }*/
        /*if (hasPay!=null) {
            criteria.andHasPayEqualTo(hasPay);
        }*/
        if (payTime!=null) {
        criteria.andPayTimeGreaterThan(payTime);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmdFee_export(example, response);
            return;
        }

        long count = pmdFeeMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdFee> records= pmdFeeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdFee.class, pmdFeeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdFee:edit")
    @RequestMapping(value = "/pmdFee_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdFee_au(PmdFee record, HttpServletRequest request) {

        Integer id = record.getId();
        //用户ID为空，个人添加
        if (record.getUserId()==null){
            record.setUserId(ShiroHelper.getCurrentUserId());
        }

        Member member = memberService.get(record.getUserId());

        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());
        /*if (pmdFeeService.idDuplicate(id, code)) {
            return failed("添加重复");
        }*/
        if (id == null) {

            pmdFeeService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PMD, "添加党员缴纳党费：{0}", record.getId()));
        } else {

            pmdFeeService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PMD, "更新党员缴纳党费：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdFee:edit")
    @RequestMapping("/pmdFee_au")
    public String pmdFee_au(Integer id, ModelMap modelMap) {

        Boolean hasShowUser = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) ||
                ShiroHelper.hasAnyRoles(RoleConstants.ROLE_PARTYADMIN,RoleConstants.ROLE_BRANCHADMIN);

        modelMap.put("hasShowUser",hasShowUser);
        if (id != null) {
            PmdFee pmdFee = pmdFeeMapper.selectByPrimaryKey(id);
            modelMap.put("pmdFee", pmdFee);
            modelMap.put("sysUser", CmTag.getUserById(pmdFee.getUserId()));
        }
        return "pmd/pmdFee/pmdFee_au";
    }

    @RequiresPermissions("pmdFee:del")
    @RequestMapping(value = "/pmdFee_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdFee_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdFeeService.del(id);
            logger.info(log( LogConstants.LOG_PMD, "删除党员缴纳党费：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdFee:del")
    @RequestMapping(value = "/pmdFee_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdFee_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmdFeeService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PMD, "批量删除党员缴纳党费：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void pmdFee_export(PmdFeeExample example, HttpServletResponse response) {

        List<PmdFee> records = pmdFeeMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"缴费类型|100","缴费月份|100","用户ID|100","所属分党委|100","所在党支部|100","缴费金额|100","缴费原因|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmdFee record = records.get(i);
            String[] values = {
                record.getType()+"",
                            DateUtils.formatDate(record.getPayMonth(), DateUtils.YYYY_MM_DD),
                            record.getUserId()+"",
                            record.getPartyId()+"",
                            record.getBranchId()+"",
                            //record.getAmt(),
                            record.getReason()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党员缴纳党费(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pmdFee_selects")
    @ResponseBody
    public Map pmdFee_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdFeeExample example = new PmdFeeExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }*/

        long count = pmdFeeMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PmdFee> records = pmdFeeMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PmdFee record:records){

                Map<String, Object> option = new HashMap<>();
                //option.put("text", record.getName());
                option.put("id", record.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
