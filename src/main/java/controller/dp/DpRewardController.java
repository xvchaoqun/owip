package controller.dp;

import domain.dp.DpReward;
import domain.dp.DpRewardExample;
import domain.dp.DpRewardExample.Criteria;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dp")
public class DpRewardController extends DpBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("dpReward:list")
    @RequestMapping("/dpReward")
    public String dpReward() {

        return "dp/dpReward/dpReward_page";
    }

    @RequiresPermissions("dpReward:list")
    @RequestMapping("/dpReward_data")
    @ResponseBody
    public void dpReward_data(HttpServletResponse response,
                                    Integer userId,
                                    Date rewardTime,
                                    String name,
                                    String unit,
                                Byte status,
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

        DpRewardExample example = new DpRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (rewardTime!=null) {
        criteria.andRewardTimeGreaterThan(rewardTime);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(SqlUtils.like(name));
        }
        if (StringUtils.isNotBlank(unit)) {
            criteria.andUnitLike(SqlUtils.like(unit));
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            dpReward_export(example, response);
            return;
        }

        long count = dpRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DpReward> records= dpRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dpReward.class, dpRewardMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("dpReward:edit")
    @RequestMapping(value = "/dpReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpReward_au(DpReward record,
                              String _rewardTime,
                              MultipartFile _proof,
                              HttpServletRequest request) {

        Integer id = record.getId();

        if (StringUtils.isNotBlank(_rewardTime)){
            record.setRewardTime(DateUtils.parseDate(_rewardTime, "yyyy.MM"));
        }

        if (_proof != null){
            String originalFilename = _proof.getOriginalFilename();
            String filename = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR + "dp" + FILE_SEPARATOR + "file" + FILE_SEPARATOR + filename;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_proof, new File(springProps.uploadPath + savePath));

            record.setProofFilename(originalFilename);
            record.setProof(savePath);
        }

        if (id == null) {
            dpRewardService.insertSelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "添加统战人员奖励信息：{0}", record.getId()));
        } else {

            dpRewardService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_DPPARTY, "更新统战人员奖励信息：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpReward:edit")
    @RequestMapping("/dpReward_au")
    public String dpReward_au(Integer id,
                              Integer userId,
                              ModelMap modelMap) {

        if (id != null) {
            DpReward dpReward = dpRewardMapper.selectByPrimaryKey(id);
            modelMap.put("dpReward", dpReward);
        }

        SysUserView sysUser = CmTag.getUserById(userId);
        modelMap.put("sysUser", sysUser);

        return "dp/dpReward/dpReward_au";
    }

    @RequiresPermissions("dpReward:del")
    @RequestMapping(value = "/dpReward_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpReward_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            dpRewardService.del(id);
            logger.info(log( LogConstants.LOG_DPPARTY, "删除统战人员奖励信息：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("dpReward:del")
    @RequestMapping(value = "/dpReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map dpReward_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            dpRewardService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DPPARTY, "批量删除统战人员奖励信息：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("dpReward:changeOrder")
    @RequestMapping(value = "/dpReward_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_dpReward_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        dpRewardService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DPPARTY, "统战人员奖励信息调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    public void dpReward_export(DpRewardExample example, HttpServletResponse response) {

        List<DpReward> records = dpRewardMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"日期|100","获得奖项|100","颁奖单位|100","获奖证书|100","排名|100","类别|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DpReward record = records.get(i);
            String[] values = {
                DateUtils.formatDate(record.getRewardTime(), DateUtils.YYYY_MM_DD),
                            record.getName(),
                            record.getUnit(),
                            record.getProof(),
                            record.getRank()+"",
                            record.getRewardType()+"",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = String.format("统战人员奖励信息(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/dpReward_selects")
    @ResponseBody
    public Map dpReward_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        DpRewardExample example = new DpRewardExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(SqlUtils.like(searchStr));
        }

        long count = dpRewardMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<DpReward> records = dpRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(DpReward record:records){

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
    }
}
