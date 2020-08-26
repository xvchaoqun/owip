package controller.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainInspector;
import domain.cet.CetTrainInspectorView;
import domain.cet.CetTrainInspectorViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import sys.constants.CetConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
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
@RequestMapping("/cet")
public class CetTrainInspectorController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainInspector:list")
    @RequestMapping(value="/cetTrainInspector_list")
    public String cetTrainInspector_list(HttpServletResponse response, int trainId, Integer type, Integer pageSize,
                                      Integer pageNo,  ModelMap modelMap,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      Integer[] ids, // 导出的记录
                                      HttpServletRequest request) throws IOException {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        modelMap.put("cetTrainCourses", cetTrainCourseService.findAll(trainId));

        if(export == 2){

            if(cetTrain.getEvaAnonymous()) {
                CetTrainInspectorViewExample example = new CetTrainInspectorViewExample();
                CetTrainInspectorViewExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId)
                        .andStatusNotEqualTo(CetConstants.CET_TRAIN_INSPECTOR_STATUS_ABOLISH);
                if (ids != null && ids.length > 0) criteria.andIdIn(Arrays.asList(ids));
                //example.setOrderByClause(" type asc, create_time desc");
                List<CetTrainInspectorView> cetTrainInspectors = cetTrainInspectorViewMapper.selectByExample(example);
                modelMap.put("cetTrainInspectors", cetTrainInspectors);

                return "cet/cetTrainInspector/cetTrainInspector_print";
            }else{

                return "cet/cetTrainInspector/cetTrainInspector_print_qr";
            }
        }

        if (null == pageSize) {
            pageSize = /*springProps.pageSize*/ 10;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainInspectorViewExample example = new CetTrainInspectorViewExample();
        CetTrainInspectorViewExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId);
        //example.setOrderByClause(" type asc, create_time desc");
        example.setOrderByClause("id asc");

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetTrainInspector_export(trainId, example, response);
            return null;
        }

        List<CetTrainInspectorView> inspectors = cetTrainInspectorViewMapper.selectByExampleWithRowbounds(example , new RowBounds((pageNo-1)*pageSize, pageSize));
        int count = (int) cetTrainInspectorViewMapper.countByExample(example);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize="+pageSize + "&trainId="+trainId;
        if(type!=null && type==1){
            searchStr += "&type=1";
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        modelMap.put("inspectors", inspectors);

        return "cet/cetTrainInspector/cetTrainInspector_list";
    }

    @RequiresPermissions("cetTrainInspector:list")
    @RequestMapping("/cetTrainInspector_data")
    public void cetTrainInspector_data(HttpServletResponse response,
                                       int trainId,
                                      String realname,
                                      Boolean status,
                                      Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTrainInspectorViewExample example = new CetTrainInspectorViewExample();
        CetTrainInspectorViewExample.Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId);
        example.setOrderByClause("id asc");

        if (realname!=null) {
            criteria.andRealnameLike(SqlUtils.like(realname));
        }
        if (status!=null) {
            if(BooleanUtils.isTrue(status)){
                criteria.andUnEvaCourseNumEqualTo(0);
            }else{
                criteria.andUnEvaCourseNumGreaterThan(0);
            }
        }

        long count = cetTrainInspectorViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTrainInspectorView> records= cetTrainInspectorViewMapper.selectByExampleWithRowbounds(example,
                        new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainInspectorView.class, cetTrainInspectorViewMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void cetTrainInspector_export(int trainId, CetTrainInspectorViewExample example, HttpServletResponse response) throws IOException {

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        String[] titles ={"账号|150","密码", "培训班次|450|left", "测评状态"};
        if(!cetTrain.getEvaAnonymous()) {
            titles[0] = "手机号|150";
            titles[1] = "姓名";
        }
        List<String[]> valuesList = new ArrayList<>();
        List<CetTrainInspectorView> cetTrainInspectors = cetTrainInspectorViewMapper.selectByExample(example);
        for(CetTrainInspectorView cetTrainInspector:cetTrainInspectors){

            String[] values ={"",
                    "",
                    cetTrain.getName(),
                    CetConstants.CET_TRAIN_INSPECTOR_STATUS_MAP.get(cetTrainInspector.getStatus())};

            if(cetTrain.getEvaAnonymous()) {
                String passwd = cetTrainInspector.getPasswd();
                if (!ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN)) {
                    if (cetTrainInspector.getPasswdChangeType() != null) {
                        passwd = "******"; // 本人修改过密码或者管理员重置过密码，则单位管理员不可以看到
                    }
                } else {
                    // 本人修改了密码，管理员也不能看到
                    if (cetTrainInspector.getPasswdChangeType() != null &&
                            cetTrainInspector.getPasswdChangeType() == CetConstants.CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF) {
                        passwd = "******";
                    }
                }
                values[0] = cetTrainInspector.getUsername();
                values[1] = passwd;
            }else{
                values[0] = cetTrainInspector.getMobile();
                values[1] = cetTrainInspector.getRealname();
            }

            valuesList.add(values);
        }

        String filename = "评课账号";
        ExportHelper.export(titles, valuesList, filename, response);
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequestMapping(value="/cetTrainInspector_abolish", method=RequestMethod.POST)
    @ResponseBody
    public Map cetTrainInspector_abolish(int id, HttpServletRequest request){

        cetTrainInspectorService.abolish(id);

        CetTrainInspector cetTrainInspector = cetTrainInspectorMapper.selectByPrimaryKey(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, String.format("作废参评人账号%s", cetTrainInspector.getUsername())));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequestMapping(value="/cetTrainInspector_password_reset", method=RequestMethod.POST)
    @ResponseBody
    public Map cetTrainInspector_password_reset(int id, HttpServletRequest request){

        CetTrainInspector record = new CetTrainInspector();
        record.setId(id);
        record.setPasswd(RandomStringUtils.randomNumeric(6));
        record.setPasswdChangeType(CetConstants.CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET);
        cetTrainInspectorMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(LogConstants.LOG_ADMIN, String.format("重置参评人密码%s", id)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequestMapping(value="/cetTrainInspector_delAbolished", method=RequestMethod.POST)
    @ResponseBody
    public Map cetTrainInspector_del(int id, HttpServletRequest request){

        CetTrainInspector cetTrainInspector = cetTrainInspectorMapper.selectByPrimaryKey(id);
        cetTrainInspectorService.delAbolished(id);

        logger.info(addLog(LogConstants.LOG_ADMIN, String.format("删除已作废的参评人账号%s", cetTrainInspector.getUsername())));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_CET_ADMIN)
    @RequestMapping(value="/cetTrainInspector_batchDel", method=RequestMethod.POST)
    @ResponseBody
    public Map cetTrainInspector_batchDel(int trainId,Integer[] ids, HttpServletRequest request){

        cetTrainInspectorService.batchDel(trainId, ids);

        logger.info(addLog(LogConstants.LOG_ADMIN, String.format("删除参评人账号，总共%s个", StringUtils.join(ids, ","))));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainInspector:edit")
    @RequestMapping(value = "/cetTrainInspector_gen", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainInspector_gen(int trainId,
                                     byte type,
                                     Boolean evaAnonymous,
                                     Integer count, // 匿名测评账号数量
                                     MultipartFile xlsx,  // 实名测评人员信息
                                     HttpServletRequest request) throws IOException, InvalidFormatException {

        cetTrainInspectorService.generateInspector(trainId, type, BooleanUtils.isTrue(evaAnonymous), count, xlsx);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetTrainInspector:edit")
    @RequestMapping(value = "/cetTrainInspector_gen_oncampus", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetTrainInspector_gen_oncampus(int trainId, HttpServletRequest request) throws IOException, InvalidFormatException {

        cetTrainInspectorService.generateInspectorOnCampus(trainId);

        return success(FormUtils.SUCCESS);
    }

}
