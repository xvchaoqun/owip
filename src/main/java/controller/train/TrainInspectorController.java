package controller.train;

import controller.BaseController;
import domain.train.Train;
import domain.train.TrainInspector;
import domain.train.TrainInspectorExample;
import domain.train.TrainInspectorExample.Criteria;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class TrainInspectorController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("trainInspector:list")
    @RequestMapping(value="/trainInspector_list")
    public String trainInspector_list(HttpServletResponse response, int trainId, Integer type, Integer pageSize,
                                      Integer pageNo,  ModelMap modelMap,
                                      @RequestParam(required = false, defaultValue = "0") int export,
                                      @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                      HttpServletRequest request) throws IOException {

        modelMap.put("train", trainMapper.selectByPrimaryKey(trainId));
        modelMap.put("trainCourses", trainCourseService.findAll(trainId));

        if(export == 2){
            TrainInspectorExample example = new TrainInspectorExample();
            Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId)
                    .andStatusNotEqualTo(SystemConstants.TRAIN_INSPECTOR_STATUS_ABOLISH);
            if(ids!=null && ids.length>0) criteria.andIdIn(Arrays.asList(ids));
            example.setOrderByClause(" type asc, create_time desc");
            List<TrainInspector> trainInspectors = trainInspectorMapper.selectByExample(example);
            modelMap.put("trainInspectors", trainInspectors);
            return "train/trainInspector/trainInspector_print";
        }

        if (null == pageSize) {
            pageSize = /*springProps.pageSize*/ 10;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        TrainInspectorExample example = new TrainInspectorExample();
        Criteria criteria = example.createCriteria().andTrainIdEqualTo(trainId);
        example.setOrderByClause(" type asc, create_time desc");

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            trainInspector_export(example, response);
            return null;
        }

        List<TrainInspector> inspectors = trainInspectorMapper.selectByExampleWithRowbounds(example , new RowBounds((pageNo-1)*pageSize, pageSize));
        int count = trainInspectorMapper.countByExample(example);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize="+pageSize + "&trainId="+trainId;
        if(type!=null && type==1){
            searchStr += "&type=1";
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);
        modelMap.put("inspectors", inspectors);

        return "train/trainInspector/trainInspector_list";
    }

    @RequiresPermissions("trainInspector:list")
    @RequestMapping("/trainInspector")
    public String trainInspector() {

        return "index";
    }

    /*@RequiresPermissions("trainInspector:list")
    @RequestMapping("/trainInspector_page")
    public String trainInspector_page(
        int courseId,
    @RequestParam(required = false, defaultValue = "0") int export,
    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
    ModelMap modelMap) {

        if(export == 2){
            TrainInspectorExample example = new TrainInspectorExample();
            Criteria criteria = example.createCriteria().andCourseIdEqualTo(courseId)
                    .andStatusIn(Arrays.asList(new Byte[]{SystemConstants.TRAIN_INSPECTOR_STATUS_INIT,
                            SystemConstants.TRAIN_INSPECTOR_STATUS_SAVE}));
            if(ids!=null && ids.length>0) criteria.andIdIn(Arrays.asList(ids));
            List<TrainInspector> trainInspectors = trainInspectorMapper.selectByExample(example);
            modelMap.put("trainInspectors", trainInspectors);
            return "train/trainInspector/trainInspector_print";
        }

        return "train/trainInspector/trainInspector_page";
    }

    @RequiresPermissions("trainInspector:list")
    @RequestMapping("/trainInspector_data")
    public void trainInspector_data(HttpServletResponse response,
                                    int courseId, String username,
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

        TrainInspectorExample example = new TrainInspectorExample();
        Criteria criteria = example.createCriteria().andCourseIdEqualTo(courseId);
        example.setOrderByClause("create_time desc");

        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            trainInspector_export(example, response);
            return;
        }


        int count = trainInspectorMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<TrainInspector> records= trainInspectorMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(trainInspector.class, trainInspectorMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }*/

    public void trainInspector_export(TrainInspectorExample example, HttpServletResponse response) throws IOException {

        String[] titles ={"账号","密码", "培训班次", "是否已完成测评"};
        List<String[]> valuesList = new ArrayList<>();
        List<TrainInspector> trainInspectors = trainInspectorMapper.selectByExample(example);
        for(TrainInspector trainInspector:trainInspectors){

            String passwd = trainInspector.getPasswd();
            if(!SecurityUtils.getSubject().hasRole(SystemConstants.ROLE_ADMIN)){
                if(trainInspector.getPasswdChangeType()!=null){
                    passwd="******"; // 本人修改过密码或者管理员重置过密码，则单位管理员不可以看到
                }
            }else{
                // 本人修改了密码，管理员也不能看到
                if(trainInspector.getPasswdChangeType()!=null &&
                        trainInspector.getPasswdChangeType()==SystemConstants.TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF){
                    passwd="******";
                }
            }
            Train train = trainMapper.selectByPrimaryKey(trainInspector.getTrainId());

            String[] values ={trainInspector.getUsername(),
                    passwd,
                    train.getName(),
                    SystemConstants.TRAIN_INSPECTOR_STATUS_MAP.get(trainInspector.getStatus())};

            valuesList.add(values);
        }

        String filename = "评课账号";
        ExportHelper.export(titles, valuesList, filename, response);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value="/trainInspector_abolish", method=RequestMethod.POST)
    @ResponseBody
    public Map trainInspector_abolish(int id, HttpServletRequest request){

        trainInspectorService.abolish(id);

        TrainInspector trainInspector = trainInspectorMapper.selectByPrimaryKey(id);
        logger.info(addLog(SystemConstants.LOG_ADMIN, String.format("作废参评人账号%s", trainInspector.getUsername())));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value="/trainInspector_password_reset", method=RequestMethod.POST)
    @ResponseBody
    public Map trainInspector_password_reset(int id, HttpServletRequest request){

        TrainInspector record = new TrainInspector();
        record.setId(id);
        record.setPasswd(RandomStringUtils.randomNumeric(6));
        record.setPasswdChangeType(SystemConstants.TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET);
        trainInspectorMapper.updateByPrimaryKeySelective(record);

        logger.info(addLog(SystemConstants.LOG_ADMIN, String.format("重置参评人密码%s", id)));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value="/trainInspector_delAbolished", method=RequestMethod.POST)
    @ResponseBody
    public Map trainInspector_del(int id, HttpServletRequest request){

        TrainInspector trainInspector = trainInspectorMapper.selectByPrimaryKey(id);
        trainInspectorService.delAbolished(id);

        logger.info(addLog( SystemConstants.LOG_ADMIN, String.format("删除已作废的参评人账号%s", trainInspector.getUsername())));
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value="/trainInspector_delAllAbolished", method=RequestMethod.POST)
    @ResponseBody
    public Map trainInspector_delAllAbolished(int trainId, HttpServletRequest request){

        int count = trainInspectorService.delAllAbolished(trainId);

        logger.info(addLog(SystemConstants.LOG_ADMIN, String.format("删除所有的已作废的参评人账号，总共%s个", count)));
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresPermissions("trainInspector:edit")
    @RequestMapping(value = "/trainInspector_gen", method = RequestMethod.POST)
    @ResponseBody
    public Map do_trainInspector_gen(int trainId, int count, byte type,  HttpServletRequest request) {

        trainInspectorService.generateInspector(trainId, type, count);

        return success(FormUtils.SUCCESS);
    }

  /*  @RequiresPermissions("trainInspector:edit")
    @RequestMapping("/trainInspector_au")
    public String trainInspector_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            TrainInspector trainInspector = trainInspectorMapper.selectByPrimaryKey(id);
            modelMap.put("trainInspector", trainInspector);
        }
        return "train/trainInspector/trainInspector_au";
    }

*/
}
