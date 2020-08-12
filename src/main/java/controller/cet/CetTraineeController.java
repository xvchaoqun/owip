package controller.cet;

import domain.cet.*;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public String cetTrainee(Integer trainId, int projectId, Integer traineeTypeId,
                             Integer userId,
                             ModelMap modelMap) {

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        modelMap.put("cetTraineeTypes", cetTraineeTypes);

        Map<Integer, Integer> typeCountMap = new HashMap<>();
        List<Map> typeCountList = iCetMapper.projectObj_typeCount(projectId, false);
        for (Map resultMap : typeCountList) {
            int _traineeTypeId = ((Long) resultMap.get("trainee_type_id")).intValue();
            int num = ((Long) resultMap.get("num")).intValue();
            typeCountMap.put(_traineeTypeId, num);
        }
        modelMap.put("typeCountMap", typeCountMap);

        if (traineeTypeId == null) {
            traineeTypeId = cetTraineeTypes.get(0).getId();
        }
        modelMap.put("traineeTypeId", traineeTypeId);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(traineeTypeId);
        modelMap.put("cetTraineeType", cetTraineeType);

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
                                int traineeTypeId,
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
                example.createCriteria().andProjectIdEqualTo(projectId)
                        .andTraineeTypeIdEqualTo(traineeTypeId);

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
}
