package controller.cet;

import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cet.CetUpperTrain;
import domain.cet.CetUpperTrainExample;
import domain.cet.CetUpperTrainExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
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
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/cet")
public class CetUpperTrainController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetUpperTrain:list")
    @RequestMapping("/cetUpperTrain")
    public String cetUpperTrain(@RequestParam(required = false, defaultValue = "1")Byte cls,
                                @RequestParam(required = false, defaultValue = "0")Boolean type,
                                ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);

        return "cet/cetUpperTrain/cetUpperTrain_page";
    }

    @RequiresPermissions("cetUpperTrain:list")
    @RequestMapping("/cetUpperTrain_data")
    @ResponseBody
    public void cetUpperTrain_data(HttpServletResponse response,
                                   @RequestParam(required = false, defaultValue = "1")Byte cls,
                                   @RequestParam(required = false, defaultValue = "0")Boolean type,
                                    Integer unitId,
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

        CetUpperTrainExample example = new CetUpperTrainExample();
        Criteria criteria = example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("id desc");

        if(cls==1){
            criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
        }else if(cls==2){
            criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
        }else{
            criteria.andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS);
        }

        if (unitId!=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetUpperTrain_export(example, response);
            return;
        }

        long count = cetUpperTrainMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetUpperTrain> records= cetUpperTrainMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetUpperTrain.class, cetUpperTrainMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping(value = "/cetUpperTrain_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_au(CetUpperTrain record,
                                   @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                   Byte addType,
                                   HttpServletRequest request) {

        CetUpperTrain oldRecord = null;
        Integer id = record.getId();
        if(id==null) {
            record.setType(BooleanUtils.isTrue(record.getType()));
            record.setIsValid(BooleanUtils.isTrue(record.getIsValid()));
            record.setAddType(addType);
        }else{
            oldRecord = cetUpperTrainMapper.selectByPrimaryKey(id);
            addType = oldRecord.getAddType();
        }

        if(addType==CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");

            if(id==null) {
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
            }
        } else if(addType==CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT){

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Integer unitId = record.getUnitId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);
            if(unitId==null || !adminUnitIdSet.contains(unitId)){
                throw new UnauthorizedException();
            }

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UNIT);
            if(id==null) {
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);
                record.setIsValid(false);
            }

        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF) {

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            userIds = new Integer[]{currentUserId};

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_TRAINEE);

            if(id==null) {
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
                record.setIsValid(false);
            }
        }
        if(id==null) {
            cetUpperTrainService.insertSelective(record, userIds);
            logger.info(addLog( LogConstants.LOG_CET, "添加上级调训：%s", StringUtils.join(userIds, ",")));
        }else{
            cetUpperTrainService.updateByPrimaryKeySelective(record);
            logger.info(addLog( LogConstants.LOG_CET, "更新上级调训：%s", StringUtils.join(userIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping("/cetUpperTrain_au")
    public String cetUpperTrain_au(Integer id,
                                   Boolean type,
                                   ModelMap modelMap) {

        if (id != null) {
            CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);
            modelMap.put("cetUpperTrain", cetUpperTrain);
            type = cetUpperTrain.getType();
        }
        modelMap.put("type", BooleanUtils.isTrue(type)?1:0);

        return "cet/cetUpperTrain/cetUpperTrain_au";
    }

    @RequiresPermissions("cetUpperTrain:del")
    @RequestMapping(value = "/cetUpperTrain_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetUpperTrain_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetUpperTrainService.batchDel(ids);
            logger.info(addLog( LogConstants.LOG_CET, "批量删除上级调训：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 上传培训总结
    @RequiresPermissions("cetUpperTrain:edit")
    @RequestMapping(value = "/cetUpperTrain_uploadNote", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetUpperTrain_uploadNote(int id, MultipartFile _word, MultipartFile _pdf,
                                              HttpServletRequest request) throws IOException, InterruptedException {

        if((_word!=null && !_word.isEmpty()) || (_pdf!=null && !_pdf.isEmpty())) {

            CetUpperTrain record = new CetUpperTrain();
            record.setId(id);
            record.setWordNote(upload(_word, "cetUpperTrain_note"));
            record.setPdfNote(uploadPdf(_pdf, "cetUpperTrain_note"));

            cetUpperTrainService.updateByPrimaryKeySelective(record);

            CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);
            sysApprovalLogService.add(cetUpperTrain.getId(), cetUpperTrain.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                    "上传培训总结", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);

            logger.info(addLog(LogConstants.LOG_CET, "上传培训总结：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetUpperTrain_uploadNote")
    public String cetUpperTrain_uploadNote(int id, ModelMap modelMap) {

        CetUpperTrain cetUpperTrain = cetUpperTrainMapper.selectByPrimaryKey(id);
        Integer userId = cetUpperTrain.getUserId();

        modelMap.put("cetUpperTrain", cetUpperTrain);
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "cet/cetUpperTrain/cetUpperTrain_uploadNote";
    }

    //@RequiresPermissions("cetUpperTrain:*")
    @RequestMapping("/cetUpperTrain_selectCadres_tree")
    @ResponseBody
    public Map cetUpperTrain_selectCadres_tree(Byte addType) throws IOException {

        if(addType==null) return null;

        LinkedHashSet<CadreView> cadreViews = null;
        if(addType==CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW){
            SecurityUtils.getSubject().checkPermission("cetUpperTrain:edit");
            cadreViews = new LinkedHashSet<CadreView>(cadreService.findAll().values());
        }else if(addType==CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT){

            SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UNIT);

            Integer currentUserId = ShiroHelper.getCurrentUserId();
            Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);

            CadreViewExample example = new CadreViewExample();
            example.createCriteria().andUnitIdIn(new ArrayList<>(adminUnitIdSet));
            List<CadreView> cadres = cadreViewMapper.selectByExample(example);

            cadreViews = new LinkedHashSet<CadreView>(cadres);
        }

        TreeNode tree = cadreCommonService.getTree(cadreViews,
                new HashSet<>(Arrays.asList(CadreConstants.CADRE_STATUS_MIDDLE,
                        CadreConstants.CADRE_STATUS_LEADER)), null, null, false, true, false);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    public void cetUpperTrain_export(CetUpperTrainExample example, HttpServletResponse response) {

        List<CetUpperTrain> records = cetUpperTrainMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"派出类型|100","派出单位|100","参训人|100","时任单位及职务|100","职务属性|100","培训班主办方|100","培训班类型|100","专项培训班|100","培训班名称|100","培训开始时间|100","培训结束时间|100","培训学时|100","是否计入年度学习任务|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetUpperTrain record = records.get(i);
            String[] values = {
                record.getType()+"",
                            record.getUnitId()+"",
                            record.getUserId()+"",
                            record.getTitle(),
                            record.getPostId()+"",
                            record.getOrganizer()+"",
                            record.getTrainType()+"",
                            record.getSpecialType()+"",
                            record.getTrainName(),
                            DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getPeriod()+"",
                            record.getIsValid()+""
            };
            valuesList.add(values);
        }
        String fileName = "上级调训_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
