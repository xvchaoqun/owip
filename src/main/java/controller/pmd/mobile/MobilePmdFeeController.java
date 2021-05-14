package controller.pmd.mobile;

import controller.pmd.PmdBaseController;
import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/m/pmd")
public class MobilePmdFeeController extends PmdBaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 缴费记录列表
	@RequiresPermissions("m:pmdFee:list")
	@RequestMapping("/pmdFee")
	public String pmdFee(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:pmdFee:list")
	@RequestMapping("/pmdFee_page")
	public String pmdFee_page(HttpServletResponse response,
							   Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

		if (null == pageSize) {
			pageSize = springProps.mPageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		int userId = ShiroHelper.getCurrentUserId();

		PmdFeeExample example = new PmdFeeExample();
        example.createCriteria().andUserIdEqualTo(userId)
				.andStatusEqualTo(PmdConstants.PMD_FEE_STATUS_NORMAL);
        example.setOrderByClause("id desc");

		long count = pmdFeeMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}
		List<PmdFee> records = pmdFeeMapper.selectByExampleWithRowbounds(example,
				new RowBounds((pageNo - 1) * pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		modelMap.put("records", records);
		modelMap.put("commonList", commonList);

		return "pmd/mobile/pmdFee_page";
	}
}
