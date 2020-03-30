package controller.dispatch.user;

import controller.dispatch.DispatchBaseController;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserDispatchWorkFileController extends DispatchBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userDispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile")
    public String dispatchWorkFile_page(ModelMap modelMap) {

        return "dispatch/user/dispatchWorkFile_page";
    }

    @RequiresPermissions("userDispatchWorkFile:list")
    @RequestMapping("/dispatchWorkFile_data")
    public void dispatchWorkFile_data(HttpServletResponse response,
                                      int type,
                                      String valid, // 校验码
                                      Integer pageSize, Integer pageNo) throws IOException {

        if(!StringUtils.equals(metaTypeService.getValid(type), valid)){
            // 权限校验
            return;
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }

        pageNo = Math.max(1, pageNo);

        DispatchWorkFileExample example = new DispatchWorkFileExample();
        example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");

        long count = dispatchWorkFileMapper.countByExample(example);

        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }

        List<DispatchWorkFile> records = dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(dispatchWorkFile.class, dispatchWorkFileMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
