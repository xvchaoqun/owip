package controller.member;

import domain.member.MemberCertify;
import domain.member.MemberCertifyExample;
import domain.member.MemberCertifyExample.Criteria;
import domain.sys.SysUserView;
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
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberCertifyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberCertify:list")
    @RequestMapping("/memberCertify")
    public String memberCertify(Integer userId,
                                ModelMap modelMap) {

        if (userId != null) {
            SysUserView sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
        }

        return "member/memberCertify/memberCertify_page";
    }

    @RequiresPermissions("memberCertify:list")
    @RequestMapping("/memberCertify_data")
    @ResponseBody
    public void memberCertify_data(HttpServletResponse response,
                                   Integer userId,
                                   Integer sn,
                                   Integer year,
                                   Byte politicalStatus,
                                 String fromUnit,
                                 String toTitle,
                                 String toUnit,
                                
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

        MemberCertifyExample example = new MemberCertifyExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("certify_date desc,sn desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (sn!=null) {
            criteria.andSnEqualTo(sn);
        }
        if (politicalStatus!=null) {
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (year != null){
            criteria.andYearEqualTo(year);
        }
        if (StringUtils.isNotBlank(fromUnit)){
            criteria.andFromUnitLike(SqlUtils.trimLike(fromUnit));
        }
        if (StringUtils.isNotBlank(toUnit)){
            criteria.andToUnitLike(SqlUtils.trimLike(toUnit));
        }
        if (StringUtils.isNotBlank(toTitle)) {
            criteria.andToTitleLike(SqlUtils.trimLike(toTitle));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberCertify_export(example, response);
            return;
        }

        long count = memberCertifyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberCertify> records= memberCertifyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(memberCertify.class, memberCertifyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberCertify:edit")
    @RequestMapping(value = "/memberCertify_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberCertify_au(MemberCertify record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            
            memberCertifyService.insertSelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "添加组织关系介绍信：{0}", record.getId()));
        } else {

            memberCertifyService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_MEMBER, "更新组织关系介绍信：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberCertify:edit")
    @RequestMapping("/memberCertify_au")
    public String memberCertify_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberCertify memberCertify = memberCertifyMapper.selectByPrimaryKey(id);
            modelMap.put("sysUser", memberCertify.getUser());
            modelMap.put("memberCertify", memberCertify);
        }
        return "member/memberCertify/memberCertify_au";
    }

    @RequiresPermissions("memberCertify:del")
    @RequestMapping(value = "/memberCertify_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map memberCertify_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberCertifyService.batchDel(ids);
            logger.info(log( LogConstants.LOG_MEMBER, "批量删除组织关系介绍信：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    public void memberCertify_export(MemberCertifyExample example, HttpServletResponse response) {

        List<MemberCertify> records = memberCertifyMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100","姓名|100","年份|70","证明信编号|100","政治面貌|100","转出单位|200","转入单位抬头|200","转入单位|200","证明信日期|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberCertify record = records.get(i);
            String[] values = {
                    record.getUser().getCode(),
                    record.getUser().getRealname(),
                    record.getYear() + "",
                    record.getSn() + "",
                    MemberConstants.MEMBER_POLITICAL_STATUS_MAP.get(record.getPoliticalStatus()),
                    record.getFromUnit(),
                    record.getToTitle(),
                    record.getToUnit(),
                    DateUtils.formatDate(record.getCertifyDate(), DateUtils.YYYYMMDD_DOT),
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };
            valuesList.add(values);
        }
        String fileName = String.format("组织关系介绍信_(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
