package controller.dr;

import domain.dr.DrOnline;
import domain.dr.DrOnlinePost;
import domain.dr.DrOnlinePostView;
import domain.dr.DrOnlinePostViewExample;
import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import domain.sys.SysUserView;
import domain.unit.Unit;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dr")
public class DrOnlinePostController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /*@Autowired
    private ScRecordViewMapper scRecordViewMapper;*/

    @RequiresPermissions("drOnlinePost:list")
    @RequestMapping("/drOnlinePost_menu")
    public String drOnlinePost(Integer onlineId, ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);

        return "dr/drOnlinePost/menu";
    }

    @RequiresPermissions("drOnlinePost:list")
    @RequestMapping("/drOnlinePost")
    public String drOnlinePost(HttpServletResponse response,
                                Integer onlineId,
                                ModelMap modelMap) throws IOException {

        modelMap.put("onlineId", onlineId);
        return "dr/drOnlinePost/drOnlinePost_page";
    }

    @RequiresPermissions("drOnlinePost:list")
    @RequestMapping("/drOnlinePost_data")
    @ResponseBody
    public void drOnlinePost_data(HttpServletResponse response,
                                    Integer onlineId,
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

        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        DrOnlinePostViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (onlineId != null){
            criteria.andOnlineIdEqualTo(onlineId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            drOnlinePost_export(example, response);
            return;
        }

        long count = drOnlinePostViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrOnlinePostView> records= drOnlinePostViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlinePost.class, drOnlinePostMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping(value = "/drOnlinePost_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlinePost_au(DrOnlinePost record, Integer recordId, Integer[] candidates, HttpServletRequest request) {

        Integer id = record.getId();
        ScRecordViewExample example = new ScRecordViewExample();
        example.createCriteria().andIdEqualTo(recordId);
        List<ScRecordView> scRecordViews = scRecordViewMapper.selectByExample(example);
        record.setUnitPostId(scRecordViews.get(0).getUnitPostId());
        if (record.getHasCompetitive() == null ? false : record.getHasCompetitive()) {
            record.setCandidates(StringUtils.trimToEmpty(StringUtils.join(candidates, ",")));
        }else {
            record.setCandidates(null);
        }

        if (id == null) {
            
            drOnlinePostService.insertSelective(record);
            if (candidates != null)
                drOnlineCandidateService.insertCandidate(record.getId(), candidates);
            logger.info(log( LogConstants.LOG_DR, "添加推荐职务：{0}", record.getId()));
        } else {

            drOnlinePostService.updateByPrimaryKeySelective(record, candidates);
            logger.info(log( LogConstants.LOG_DR, "更新推荐职务：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("drOnlinePost:edit")
    @RequestMapping("/drOnlinePost_au")
    public String drOnlinePost_au(Integer id, Integer onlineId, ModelMap modelMap) {

        modelMap.put("onlineId", onlineId);

        if (id != null) {
            DrOnlinePostView drOnlinePostView = drOnlinePostService.getPost(id);
            modelMap.put("drOnlinePost", drOnlinePostView);
            ScRecordView scRecordView = iScMapper.getScRecordView(drOnlinePostView.getUnitPostId());
            modelMap.put("scRecord", scRecordView);
        }
        return "dr/drOnlinePost/drOnlinePost_au";
    }

    @RequiresPermissions("drOnlinePost:del")
    @RequestMapping(value = "/drOnlinePost_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map drOnlinePost_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            drOnlinePostService.batchDel(ids);
            logger.info(log( LogConstants.LOG_DR, "批量删除推荐职务：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("drOnlinePost:changeOrder")
    @RequestMapping(value = "/drOnlinePost_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_drOnlinePost_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        drOnlinePostService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_DR, "推荐职务调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void drOnlinePost_export(DrOnlinePostViewExample example, HttpServletResponse response) {

        List<DrOnlinePostView> records = drOnlinePostViewMapper.selectByExample(example);
        int rownum = records.size();
        logger.info("共" + rownum + "条记录");
        String[] titles = {"所属批次|180","推荐类型|110","推荐职务|220","分管工作|150","岗位级别|80","职务属性|110","所属单位|220",
                "单位类型|150","是否有候选人|50","候选人|220","是否差额|50","最多推荐人数|50"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            DrOnlinePostView record = records.get(i);
            Integer onlineId = record.getOnlineId();
            DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
            Unit unit = unitMapper.selectByPrimaryKey(record.getUnitId());
            List<SysUserView> users = record.getUsers();
            List<String> usernames = new ArrayList<>();
            for (SysUserView user : users){
                usernames.add(user.getRealname());
            }
            String[] values = {
                    drOnline.getCode(),
                    metaTypeService.getName(record.getOnlineType()),
                    record.getName(),
                    record.getJob(),
                    metaTypeService.getName(record.getAdminLevel()),
                    metaTypeService.getName(record.getPostType()),
                    unit.getName(),
                    metaTypeService.getName(record.getTypeId()),
                    record.getHasCandidate() == true ? "是" : "否",
                    StringUtils.join(usernames, ","),
                    record.getHasCompetitive() == true ? "是" : "否",
                    String.valueOf(record.getCompetitiveNum())
            };
            valuesList.add(values);
        }
        String fileName = String.format("推荐职务(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
