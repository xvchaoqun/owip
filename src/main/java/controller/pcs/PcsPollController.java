package controller.pcs;

import controller.global.OpException;
import domain.pcs.PcsConfig;
import domain.pcs.PcsConfigExample;
import domain.pcs.PcsPoll;
import domain.pcs.PcsPollExample;
import domain.pcs.PcsPollExample.Criteria;
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
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pcs")
public class PcsPollController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPoll:list")
    @RequestMapping("/pcsPoll")
    public String pcsPoll(@RequestParam(required = false, defaultValue = "1") int cls,
                          Boolean isSecond,
                          Integer partyId,
                          Integer branchId,
                          Integer userId,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (partyId != null) {
            modelMap.put("party", partyMapper.selectByPrimaryKey(partyId));
        }
        if (branchId != null){
            modelMap.put("branch", branchMapper.selectByPrimaryKey(branchId));
        }
        if (isSecond != null) {
            modelMap.put("isSecond", isSecond);
        }
        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        if (cls == 2){
            return "/pcs/pcsPoll/pcsPollResult";
        }else if (cls == 3){
            return "/pcs/pcsPoll/pcsPollResult";
        }

        return "/pcs/pcsPoll/pcsPoll_page";
    }

    @RequiresPermissions("pcsPoll:list")
    @RequestMapping("/pcsPoll_data")
    @ResponseBody
    public void pcsPoll_data(HttpServletResponse response,
                                    String name,
                                    Integer partyId,
                                    Integer branchId,
                                    String configName,
                                 Boolean isSecond,
                                 Boolean hasReport,
                                
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

        PcsPollExample example = new PcsPollExample();
        Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("id desc");

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.trimLike(name));
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(configName)) {
            PcsConfigExample configExample = new PcsConfigExample();
            PcsConfigExample.Criteria criteria1 = configExample.createCriteria().andNameLike(SqlUtils.trimLike(configName));
            List<PcsConfig> pcsConfigs = pcsConfigMapper.selectByExample(configExample);
            if (pcsConfigs != null && pcsConfigs.size() > 0) {
                List<Integer> configIds = new ArrayList<>();
                for (PcsConfig pcsConfig : pcsConfigs) {
                    configIds.add(pcsConfig.getId());
                }
                criteria.andConfigIdIn(configIds);
            }
        }
        if (isSecond != null){
            criteria.andIsSecondEqualTo(isSecond);
        }
        if (hasReport != null){
            criteria.andHasReportEqualTo(hasReport);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsPoll_export(example, response);
            return;
        }

        long count = pcsPollMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPoll> records= pcsPollMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPoll.class, pcsPollMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_au(PcsPoll record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            pcsPollService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PCS, "添加党代会投票：{0}", record.getId()));
        } else {

            pcsPollService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PCS, "更新党代会投票：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_au")
    public String pcsPoll_au(Integer id, ModelMap modelMap) {

        PcsConfig pcsConfig = null;
        if (id != null) {
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            pcsConfig = pcsConfigMapper.selectByPrimaryKey(pcsPoll.getConfigId());
            modelMap.put("pcsPoll", pcsPoll);
            Integer partyId = pcsPoll.getPartyId();
            Integer branchId = pcsPoll.getBranchId();
            if (partyId != null){
                modelMap.put("party", partyMapper.selectByPrimaryKey(partyId));
            }
            if (branchId != null){
                modelMap.put("branch", branchMapper.selectByPrimaryKey(branchId));
            }
        }else {
            PcsConfigExample example = new PcsConfigExample();
            PcsConfigExample.Criteria criteria = example.createCriteria();
            criteria.andIsCurrentEqualTo(true);
            List<PcsConfig> pcsConfigs = pcsConfigMapper.selectByExample(example);
            if (pcsConfigs.size() != 1){
                throw new OpException("当前党代会为空，请先设置当前党代会。");
            }
            pcsConfig = pcsConfigs.get(0);
        }
        modelMap.put("pcsConfig", pcsConfig);

        return "pcs/pcsPoll/pcsPoll_au";
    }

    @RequiresPermissions("pcsPoll:del")
    @RequestMapping(value = "/pcsPoll_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            pcsPollService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量删除党代会投票：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_report", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPoll_report(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            pcsPollService.batchReport(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量报送党代会投票：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping(value = "/pcsPoll_noticeEdit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPoll_noticeEdit(Integer id, String notice, Integer isMobile, HttpServletRequest request) {

        PcsPoll record = new PcsPoll();
        record.setId(id);
        if (isMobile == 1){
            record.setMobileNotice(notice);
        }else {
            record.setNotice(notice);
        }

        pcsPollService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_PCS, "更新党代会投票说明：{0}", record.getId() + "_" + record.getName()));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPoll:edit")
    @RequestMapping("/pcsPoll_noticeEdit")
    public String pcsPoll_noticeEdit(Integer id, Integer isMobile, ModelMap modelMap){

        if (id != null) {
            PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(id);
            modelMap.put("pcsPoll", pcsPoll);
        }
        if (isMobile == null){
            return "/pcs/pcsPoll/inspector_notice";
        }

        return "/pcs/pcsPoll/pcsPoll_noticeEdit";
    }

    @RequiresPermissions("drOnline:edit")
    @RequestMapping(value = "/inspectorNotice", method = RequestMethod.POST)
    @ResponseBody
    public Map do_inspectorNotice(Integer id, String inspectorNotice, HttpServletRequest request) {

        PcsPoll record = new PcsPoll();
        record.setId(id);
        if (null != inspectorNotice){
            record.setInspectorNotice(inspectorNotice);
        }

        pcsPollService.updateByPrimaryKeySelective(record);
        logger.info(log( LogConstants.LOG_DR, "更新党代会投票说明（纸质票）：{0}", record.getId() + "_" + record.getName()));
        return success(FormUtils.SUCCESS);
    }

    public void pcsPoll_export(PcsPollExample example, HttpServletResponse response) {

        List<PcsPoll> records = pcsPollMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级分党委|100","所属支部|100","投票名称|100","党代会|100","投票阶段 0一下阶段 1二下阶段|100","是否报送|100","代表最大推荐人数|100","党委委员最大推荐人数|100","纪委委员最大推荐人数|100","参评人数量|100","pc端投票说明|100","手机端投票说明|100","账号分发说明|100","投票起始时间|100","投票截止时间|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PcsPoll record = records.get(i);
            String[] values = {
                record.getPartyId()+"",
                            record.getBranchId()+"",
                            record.getName(),
                            record.getConfigId()+"",
                            record.getIsSecond()+"",
                            record.getHasReport()+"",
                            record.getPrNum()+"",
                            record.getDwNum()+"",
                            record.getJwNum()+"",
                            record.getInspectorNum()+"",
                            record.getNotice(),
                            record.getMobileNotice(),
                            record.getInspectorNotice(),
                            DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("党代会投票(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
