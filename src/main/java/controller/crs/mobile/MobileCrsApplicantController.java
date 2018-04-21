package controller.crs.mobile;

import controller.crs.CrsBaseController;
import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.CrsConstants;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/m/crs")
public class MobileCrsApplicantController extends CrsBaseController {

	@RequiresPermissions("m:crsPost:*")
	@RequestMapping("/crsApplicant_page")
	public String crsApplicant_page(HttpServletResponse response,
									@RequestParam(required = false, defaultValue = "1") int cls,
									int postId, Integer pageSize,
							   Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

		modelMap.put("cls", cls);

		if (null == pageSize) {
			pageSize = springProps.mPageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		CrsApplicantViewExample example = new CrsApplicantViewExample();
		CrsApplicantViewExample.Criteria criteria = example.createCriteria()
				.andPostIdEqualTo(postId)
				.andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
		example.setOrderByClause("enroll_time asc");

		switch (cls) {
			case 1:
				criteria.andRequireCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT)
						.andIsQuitEqualTo(false).andIsRequireCheckPassEqualTo(false);
				break;
			case 2: // 资格审核通过 或 破格
				criteria.andIsRequireCheckPassEqualTo(true).andIsQuitEqualTo(false);
				break;
			case 3:
				criteria.andIsRequireCheckPassEqualTo(false).andIsQuitEqualTo(false)
						.andRequireCheckStatusEqualTo(CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS);
				break;
			case 4:
				criteria.andIsQuitEqualTo(true);
				break;
		}

		long count = crsApplicantViewMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}
		List<CrsApplicantView> records = crsApplicantViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		modelMap.put("crsApplicants", records);

		String searchStr = "&postId="+postId+"&cls=" + cls;
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		modelMap.put("count", crsPostService.goupCount(postId));

		return "crs/mobile/crsApplicant_page";
	}
}
