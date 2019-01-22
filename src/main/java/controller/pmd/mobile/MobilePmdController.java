package controller.pmd.mobile;

import controller.pmd.PmdBaseController;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMemberPayViewExample;
import domain.pmd.PmdOrder;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/pmd")
public class MobilePmdController extends PmdBaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 缴费记录列表
	@RequiresPermissions("userPmdMember:list")
	@RequestMapping("/pmdMember")
	public String pmdMember(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("userPmdMember:list")
	@RequestMapping("/pmdMember_page")
	public String pmdMember_page(HttpServletResponse response,
							   Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

		if (null == pageSize) {
			pageSize = springProps.mPageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		int userId = ShiroHelper.getCurrentUserId();
		PmdMemberPayViewExample example = new PmdMemberPayViewExample();
		PmdMemberPayViewExample.Criteria criteria = example.createCriteria()
				.andUserIdEqualTo(userId);
		example.setOrderByClause("month_id desc");

		long count = pmdMemberPayViewMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}
		List<PmdMemberPayView> records = pmdMemberPayViewMapper.selectByExampleWithRowbounds(example,
				new RowBounds((pageNo - 1) * pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		modelMap.put("records", records);
		modelMap.put("commonList", commonList);

		return "pmd/mobile/pmdMember_page";
	}

	// 页面通知支付结果
	@RequiresPermissions("userPmdMember:list")
	@RequestMapping("/callback")
	public String pay_callback(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("userPmdMember:list")
	@RequestMapping("/callback_page")
	public String pay_callback_page(HttpServletRequest request, ModelMap modelMap) throws IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();
		logger.info("pmd mobile callback request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));

		modelMap.put("verifySign", pmdOrderService.verifyNotifySign(request));

		if(parameterMap.size()>0) {

			String sn = request.getParameter("thirdorderid");
			PmdOrder pmdOrder = pmdOrderMapper.selectByPrimaryKey(sn);
			if(pmdOrder!=null && pmdOrder.getUserId().intValue()==ShiroHelper.getCurrentUserId()) {
				pmdOrderService.notify(request);
			}
		}

		return "pmd/mobile/callback_page";
	}
}
