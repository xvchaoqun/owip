package controller.crs.mobile;

import controller.crs.CrsBaseController;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/m/crs")
public class MobileCrsPostController extends CrsBaseController {

	@RequiresPermissions("m:crsPost:*")
	@RequestMapping("/crsPost")
	public String crsPost(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:crsPost:*")
	@RequestMapping("/crsPost_page")
	public String crsPost_page(HttpServletResponse response,
							   @RequestParam(required = false, defaultValue = "1") Byte status, Integer pageSize,
							   Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

		modelMap.put("status", status);

		if (null == pageSize) {
			pageSize = springProps.mPageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		CrsPostExample example = new CrsPostExample();
		CrsPostExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause("create_time desc");

		if (status != -1) criteria.andStatusEqualTo(status);

		long count = crsPostMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}
		List<CrsPost> records = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		modelMap.put("crsPosts", records);

		String searchStr = "&status=" + status;
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		return "crs/mobile/crsPost_page";
	}
}
