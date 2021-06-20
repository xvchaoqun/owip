package controller.cet;

import domain.cet.CetTrainCourseExample;
import domain.cet.CetTraineeView;
import domain.cet.CetTraineeViewExample;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetTraineeController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee_detail")
    public String cetTrainee_detail(int userId, ModelMap modelMap) {

        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetTrainee/cetTrainee_detail";
    }

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee")
    public String cetTrainee(Integer trainId, int projectId,
                             Integer userId,
                             ModelMap modelMap) {

        if(userId!=null) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }

        // 培训班下的课程总数
        CetTrainCourseExample example = new CetTrainCourseExample();
        CetTrainCourseExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        if(trainId!=null){
            criteria.andTrainIdEqualTo(trainId);
        }
        modelMap.put("courseCount", cetTrainCourseMapper.countByExample(example));

        return "cet/cetTrainee/cetTrainee_page";
    }

    @RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTrainee_data")
    public void cetTrainee_data(HttpServletResponse response,
                                Integer trainId, int projectId,
                                //int traineeTypeId,
                                Integer userId,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer[] ids, // 导出的记录
                                Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeViewExample example = new CetTraineeViewExample();
        CetTraineeViewExample.Criteria criteria =
                example.createCriteria().andProjectIdEqualTo(projectId);

        if(trainId!=null){
            criteria.andTrainIdEqualTo(trainId);
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        int count = (int) cetTraineeViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeView> records = cetTraineeViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        int total = commonList.pageNum;

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", total);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainee.class, cetTraineeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 查看本党委培训记录
    //@RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTraineeList")
    public String cetTraineeList(Integer userId,
                             Integer cetPartyId,
                             ModelMap modelMap) {

        if(userId!=null) {
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }
        if (cetPartyId != null) {
            modelMap.put("cetParty", cetPartyMapper.selectByPrimaryKey(cetPartyId));
        }

        return "cet/cetTrainee/cetTraineeList_page";
    }

    // 二级党委过程培训记录汇总
    //@RequiresPermissions("cetTrainee:list")
    @RequestMapping("/cetTraineeList_data")
    public void cetTraineeList_data(HttpServletResponse response,
                                Integer userId,
                                Integer cetPartyId,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer[] ids, // 导出的记录
                                Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetTraineeViewExample example = new CetTraineeViewExample();
        CetTraineeViewExample.Criteria criteria = example.createCriteria()
                .andProjectIsDeletedEqualTo(false)
                .andIsPartyProjectEqualTo(true);

        example.setOrderByClause("start_date desc, end_date desc, obj_id desc");
        boolean addPermits = !RoleConstants.isCetAdmin();
        List<Integer> adminPartyIdList = new ArrayList<>();
        if(addPermits) {

            adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            if(adminPartyIdList.size()>0){
                criteria.andCetPartyIdIn(adminPartyIdList);
            }else{
                criteria.andObjIdIsNull();
            }
        }

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (cetPartyId != null) {
            criteria.andCetPartyIdEqualTo(cetPartyId);
        }

        int count = (int) cetTraineeViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetTraineeView> records = cetTraineeViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        int total = commonList.pageNum;

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", total);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetTrainee.class, cetTraineeMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
