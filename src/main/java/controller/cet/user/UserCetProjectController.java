package controller.cet.user;

import controller.cet.CetBaseController;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/cet")
public class UserCetProjectController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 学员 第一级：培训
    @RequiresPermissions("userCetProject:list")
    @RequestMapping("/cetProject")
    public String cetProject() {

        return "cet/user/cetProject_page";
    }

    @RequiresPermissions("userCetProject:list")
    @RequestMapping("/cetProject_data")
    @ResponseBody
    public void cetProject_data(HttpServletRequest request,
                                byte type, // 培训类型：专题培训、年度培训
                                @RequestParam(required = false, defaultValue = "0") boolean isPartyProject, // 是否二级党委培训
                                Integer year,
                                String name,
                                Integer pageSize, Integer pageNo)  throws IOException {

        int userId = ShiroHelper.getCurrentUserId();

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        long count = iCetMapper.countCetProjectList(userId, type, isPartyProject, year, name);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProject> records= iCetMapper.selectCetProjectList(userId, type, isPartyProject, year, name,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        request.setAttribute("userId", userId);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProject.class, cetProjectMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("userCetProject:edit")
    @RequestMapping(value = "/cetProjectObj_uploadWrite", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectObj_uploadWrite(int id, MultipartFile _writeFilePath,
                                            HttpServletRequest request) throws IOException, InterruptedException {

        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(id);
        if(cetProjectObj.getUserId().intValue()!=ShiroHelper.getCurrentUserId()){
            throw new UnauthorizedException();
        }

        String writeFilePath = upload(_writeFilePath, "cetProjectObj");

        if(StringUtils.isNotBlank(writeFilePath)) {

            CetProjectObj record = new CetProjectObj();
            record.setId(id);
            record.setWriteFilePath(writeFilePath);
            cetProjectObjService.updateByPrimaryKeySelective(record);

            logger.info(addLog(LogConstants.LOG_CET, "上传心得体会：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userCetProject:edit")
    @RequestMapping("/cetProjectObj_uploadWrite")
    public String cetProjectObj_uploadWrite(int id, ModelMap modelMap) {

        return "cet/user/cetProjectObj_uploadWrite";
    }

    @RequiresPermissions("userCetProject:edit")
    @RequestMapping("/cetProjectObj_write")
    public String cetProjectObj_write(int projectId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
        modelMap.put("cetProjectObj", cetProjectObj);

       /* CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);*/

        return "cet/user/cetProjectObj_write";
    }
}
