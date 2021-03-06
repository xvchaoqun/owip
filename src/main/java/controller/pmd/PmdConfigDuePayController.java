package controller.pmd;

import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdConfigMemberView;
import domain.pmd.PmdConfigMemberViewExample;
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
import sys.constants.PmdConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdConfigDuePayController extends PmdBaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("pmdConfigDuePay:list")
    @RequestMapping("/pmdConfigDuePay")
    public String pmdConfigMember(Integer userId, ModelMap modelMap) {
        
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        
        Map<Byte, List<PmdConfigMemberType>> typeMap = new HashMap<>();
        for (Byte pmdMemberType : PmdConstants.PMD_MEMBER_TYPE_MAP.keySet()) {
            typeMap.put(pmdMemberType, pmdConfigMemberTypeService.list(pmdMemberType));
        }
        modelMap.put("typeMap", typeMap);
        
        return "pmd/pmdConfigDuePay/pmdConfigDuePay_page";
    }
    
    @RequiresPermissions("pmdConfigDuePay:list")
    @RequestMapping("/pmdConfigDuePay_data")
    public void pmdConfigDuePay_data(HttpServletResponse response,
                                     Integer userId,
                                     Integer partyId,
                                     Integer branchId,
                                     Byte configMemberType,
                                     Integer configMemberTypeId,
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

        List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
        List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());

        PmdConfigMemberViewExample example = new PmdConfigMemberViewExample();
        PmdConfigMemberViewExample.Criteria criteria =example.createCriteria();

        criteria.addPermits(adminPartyIds, adminBranchIds);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (configMemberType != null) {
            criteria.andConfigMemberTypeEqualTo(configMemberType);
        }
        if (configMemberTypeId != null) {
            criteria.andConfigMemberTypeIdEqualTo(configMemberTypeId);
        }
        
        long count = pmdConfigMemberViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdConfigMemberView> records = pmdConfigMemberViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdConfigMember.class, pmdConfigMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
}
