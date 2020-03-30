package controller.pmd;

import controller.BaseController;
import domain.member.Member;
import domain.pmd.PmdPartyDonate;
import domain.pmd.PmdPartyDonateExample;
import domain.pmd.PmdPartyDonateExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import persistence.pmd.PmdPartyDonateMapper;
import service.pmd.PmdPartyDonateService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdPartyDonateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PmdPartyDonateMapper pmdPartyDonateMapper;
    @Autowired
    private PmdPartyDonateService pmdPartyDonateService;

    @RequiresPermissions("pmdPartyDonate:list")
    @RequestMapping("/pmdPartyDonate")
    public String pmdPartyDonate() {

        return "pmd/pmdPartyDonate/pmdPartyDonate_page";
    }

    @RequiresPermissions("pmdPartyDonate:list")
    @RequestMapping("/pmdPartyDonate_data")
    @ResponseBody
    public void pmdPartyDonate_data(HttpServletResponse response,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
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

        PmdPartyDonateExample example = new PmdPartyDonateExample();
        Criteria criteria = example.createCriteria();
        //example.setOrderByClause("id desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pmdPartyDonate_export(example, response);
            return;
        }

        long count = pmdPartyDonateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdPartyDonate> records= pmdPartyDonateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdPartyDonate.class, pmdPartyDonateMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pmdPartyDonate:edit")
    @RequestMapping(value = "/pmdPartyDonate_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdPartyDonate_au(PmdPartyDonate record, HttpServletRequest request) {

        if (record.getUserId() == null) {
            record.setUserId(ShiroHelper.getCurrentUserId());
        }

        Member member = memberMapper.selectByPrimaryKey(record.getUserId());

        if (member == null){

        }
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());
        Integer id = record.getId();

        if (id == null) {
            
            pmdPartyDonateService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PMD, "添加党员捐赠党费：{0}", record.getId()));
        } else {

            pmdPartyDonateService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PMD, "更新党员捐赠党费：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdPartyDonate:edit")
    @RequestMapping("/pmdPartyDonate_au")
    public String pmdPartyDonate_au(Integer id, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        //党费收缴——分党委管理员
        modelMap.put("isPmdParty",ShiroHelper.hasRole("role_pmd_party"));
        //党费收缴——支部管理员
        modelMap.put("isPmdBranch",ShiroHelper.hasRole("role_pmd_branch"));

        if (id != null) {
            PmdPartyDonate pmdPartyDonate = pmdPartyDonateMapper.selectByPrimaryKey(id);
            modelMap.put("pmdPartyDonate", pmdPartyDonate);
        }
        return "pmd/pmdPartyDonate/pmdPartyDonate_au";
    }

    @RequiresPermissions("pmdPartyDonate:del")
    @RequestMapping(value = "/pmdPartyDonate_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdPartyDonate_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            pmdPartyDonateService.del(id);
            logger.info(log( LogConstants.LOG_PMD, "删除党员捐赠党费：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdPartyDonate:del")
    @RequestMapping(value = "/pmdPartyDonate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdPartyDonate_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pmdPartyDonateService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PMD, "批量删除党员捐赠党费：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void pmdPartyDonate_export(PmdPartyDonateExample example, HttpServletResponse response) {

        List<PmdPartyDonate> records = pmdPartyDonateMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"用户ID|100","所属分党委|100","所在党支部|100","缴费日期|100","金额|100","说明|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PmdPartyDonate record = records.get(i);
            String[] values = {
                record.getUserId()+"",
                            record.getPartyId()+"",
                            record.getBranchId()+"",
                            DateUtils.formatDate(record.getDonateDate(), DateUtils.YYYY_MM_DD),
                            //record.getMoney(),
                            //record.getExplain()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党员捐赠党费(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/pmdPartyDonate_selects")
    @ResponseBody
    public Map pmdPartyDonate_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdPartyDonateExample example = new PmdPartyDonateExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if(StringUtils.isNotBlank(searchStr)){
            //criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = pmdPartyDonateMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<PmdPartyDonate> records = pmdPartyDonateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(PmdPartyDonate record:records){

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
