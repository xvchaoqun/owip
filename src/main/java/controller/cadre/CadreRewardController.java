package controller.cadre;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cadre.CadreInfo;
import domain.cadre.CadreReward;
import domain.cadre.CadreRewardExample;
import domain.cadre.CadreRewardExample.Criteria;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CadreRewardController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadreReward:list")
    @RequestMapping("/cadreReward")
    public String cadreReward() {

        return "index";
    }
    @RequiresPermissions("cadreReward:list")
    @RequestMapping("/cadreReward_page")
    public String cadreReward_page(
            @RequestParam(defaultValue = "1") Byte type, // 1 列表 2 预览
            Integer cadreId, ModelMap modelMap) {

        modelMap.put("type", type);
        if (type == 2) {

            CadreRewardExample example = new CadreRewardExample();
            example.createCriteria().andCadreIdEqualTo(cadreId).andRewardTypeEqualTo(SystemConstants.CADRE_REWARD_TYPE_OTHER)
                    .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            example.setOrderByClause("reward_time asc");
            List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
            modelMap.put("cadreRewards", cadreRewards);

            CadreInfo cadreInfo = cadreInfoService.get(cadreId, SystemConstants.CADRE_INFO_TYPE_REWARD_OTHER);
            modelMap.put("cadreInfo", cadreInfo);
        }
        return "cadre/cadreReward/cadreReward_page";
    }
    @RequiresPermissions("cadreReward:list")
    @RequestMapping("/cadreReward_data")
    public void cadreReward_data(HttpServletResponse response,
                                    Integer cadreId,
                                    byte rewardType, //  1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CadreRewardExample example = new CadreRewardExample();
        Criteria criteria = example.createCriteria().andRewardTypeEqualTo(rewardType)
                .andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("reward_time desc");

        if (cadreId!=null) {
            criteria.andCadreIdEqualTo(cadreId);
        }

        if (export == 1) {
            cadreReward_export(example, response);
            return;
        }

        int count = cadreRewardMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CadreReward> cadreRewards = cadreRewardMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cadreRewards);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(Party.class, PartyMixin.class);
        //JSONUtils.write(response, resultMap, sourceMixins);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cadreReward:edit")
    @RequestMapping(value = "/cadreReward_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReward_au(
            // toApply、_isUpdate、applyId 是干部本人修改申请时传入
            @RequestParam(required = true, defaultValue = "0") boolean toApply,
            // 否：添加[添加或修改申请] ， 是：更新[添加或修改申请]。
            @RequestParam(required = true, defaultValue = "0") boolean _isUpdate,
            Integer applyId, // _isUpdate=true时，传入
            CadreReward record, String _rewardTime,MultipartFile _proof, HttpServletRequest request) {

        Integer id = record.getId();
        Assert.isTrue(record.getRewardType()!=null);

        if(StringUtils.isNotBlank(_rewardTime)){
            record.setRewardTime(DateUtils.parseDate(_rewardTime, "yyyy.MM"));
        }

        if(_proof!=null){
            //String ext = FileUtils.getExtention(_proof.getOriginalFilename());
            String originalFilename = _proof.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath =  FILE_SEPARATOR
                    + "cadre" + FILE_SEPARATOR
                    + "file" + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + FileUtils.getExtention(originalFilename);
            FileUtils.copyFile(_proof, new File(springProps.uploadPath + savePath));

            record.setProofFilename(originalFilename);
            record.setProof(savePath);
        }

        if (id == null) {

            if(!toApply) {
                cadreRewardService.insertSelective(record);
                logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部教学奖励：%s", record.getId()));
            }else{
                cadreRewardService.modifyApply(record, null, record.getRewardType(), false);
                logger.info(addLog(SystemConstants.LOG_USER, "提交添加申请-干部教学奖励：%s", record.getId()));
            }

        } else {
            // 干部信息本人直接修改数据校验
            CadreReward _record = cadreRewardMapper.selectByPrimaryKey(id);
            if(_record.getCadreId().intValue() != record.getCadreId()){
                throw new IllegalArgumentException("数据异常");
            }

            if(!toApply) {
                cadreRewardService.updateByPrimaryKeySelective(record);
                logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部教学奖励：%s", record.getId()));
            }else{
                if(_isUpdate==false) {
                    cadreRewardService.modifyApply(record, id, record.getRewardType(), false);
                    logger.info(addLog(SystemConstants.LOG_USER, "提交修改申请-干部教学奖励：%s", record.getId()));
                }else{
                    // 更新修改申请的内容
                    cadreRewardService.updateModify(record, applyId);
                    logger.info(addLog(SystemConstants.LOG_USER, "修改申请内容-干部教学奖励：%s", record.getId()));
                }
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cadreReward:edit")
    @RequestMapping("/cadreReward_au")
    public String cadreReward_au(Integer id, int cadreId, ModelMap modelMap) {

        if (id != null) {
            CadreReward cadreReward = cadreRewardMapper.selectByPrimaryKey(id);
            modelMap.put("cadreReward", cadreReward);
        }
        Cadre cadre = cadreService.findAll().get(cadreId);
        modelMap.put("cadre", cadre);
        SysUserView sysUser = sysUserService.findById(cadre.getUserId());
        modelMap.put("sysUser", sysUser);
        return "cadre/cadreReward/cadreReward_au";
    }

    @RequiresPermissions("cadreReward:del")
    @RequestMapping(value = "/cadreReward_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request,
                        int cadreId, // 干部直接修改权限校验用
                        @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length>0){
            cadreRewardService.batchDel(ids, cadreId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部教学奖励：%s", StringUtils.join(ids, ",")));
        }
        return success(FormUtils.SUCCESS);
    }

   /* @RequiresPermissions("cadreReward:changeOrder")
    @RequestMapping(value = "/cadreReward_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreReward_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cadreRewardService.changeOrder(id, addNum);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "干部教学奖励调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }*/

    public void cadreReward_export(CadreRewardExample example, HttpServletResponse response) {

        List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
        int rownum = cadreRewardMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"所属干部","日期","获得奖项","颁奖单位","排名"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            CadreReward cadreReward = cadreRewards.get(i);
            String[] values = {
                        cadreReward.getCadreId()+"",
                                            DateUtils.formatDate(cadreReward.getRewardTime(), DateUtils.YYYY_MM_DD),
                                            cadreReward.getName()+"",
                                            cadreReward.getUnit(),
                                            cadreReward.getRank()+""
                    };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "干部教学奖励_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
