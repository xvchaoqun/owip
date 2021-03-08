package controller.analysis;

import controller.BaseController;
import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import domain.member.MemberView;
import domain.member.MemberViewExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.member.MemberApplyViewMapper;
import service.analysis.StatCodService;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/stat")
@Controller
public class statCodController extends BaseController {

    @Autowired
    private StatCodService statCodService;
    @Autowired
    private MemberApplyViewMapper memberApplyViewMapper;


    @RequiresPermissions("statCodAppply:list")
    @RequestMapping("/statCod")
    public String statCodAppply(ModelMap modelMap ,
                                Integer userId,
                                Integer status,
                                Integer partyId,
                                Integer branchId,
                                @RequestParam(required = false, defaultValue = "1") int cls) {

        if (userId != null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (status != null){
            modelMap.put(status.toString(),OwConstants.OW_APPLY_STAGE_MAP.get(status));
        }
        if (partyId != null){
            Party party = CmTag.getParty(partyId);
            modelMap.put("party",party);
        }
        if (branchId != null){
            Branch branch = CmTag.getBranch(branchId);
            modelMap.put("branch",branch);
        }

        if (cls==1){
            return "analysis/statCod/stat_cod_apply";
        }else if (cls == 2){
            return "analysis/statCod/stat_cod_member";
        }
        return "analysis/statCod/stat_cod_apply";
    }


    @RequestMapping("/statCod_data")
    public void statCod_data(HttpServletResponse response,
                             @RequestParam(required = false, defaultValue = "1") int cls,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             Integer[] ids, // 导出的记录
                             Integer userId,
                             Integer gender,
                             Integer stage,
                             Integer type,
                             Integer partyId,
                             Integer branchId,
                             Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        if (cls == 1){
            //中组部申请人
            Map resultMap = new HashMap();
            MemberApplyViewExample example = new MemberApplyViewExample();
            MemberApplyViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
            example.setOrderByClause("branch_sort_order desc");

            if (export == 1){
                if (ids!=null && ids.length>0){
                   criteria.andUserIdIn(Arrays.asList(ids));
                }
                statCodService.codApplyExport(example,response);
                return ;
            }
            if (userId != null){
                criteria.andUserIdEqualTo(userId);
            }
            if (stage != null){
                criteria.andStageEqualTo(stage.byteValue());
            }
            if (type != null){
                criteria.andTypeEqualTo(type.byteValue());
            }
            if (partyId != null){
                criteria.andPartyIdEqualTo(partyId);
            }
            if (branchId != null){
                criteria.andBranchIdEqualTo(branchId);
            }

            long count = memberApplyViewMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            List<MemberApplyView> memberApplyViews = memberApplyViewMapper.
                    selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

            CommonList commonList = new CommonList(count, pageNo, pageSize);
            resultMap.put("rows", memberApplyViews);
            resultMap.put("records", count);
            resultMap.put("page", pageNo);
            resultMap.put("total", commonList.pageNum);

            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            baseMixins.remove(SysUserView.class);
            JSONUtils.jsonp(resultMap, baseMixins);
        }
        if (cls == 2){
            //中组部党员
            Map resultMap = new HashMap();
            MemberViewExample example = new MemberViewExample();
            MemberViewExample.Criteria criteria = example.createCriteria().andStatusEqualTo(MemberConstants.MEMBER_STATUS_NORMAL);
            example.setOrderByClause("sort_order desc");

            if (export == 1){
                if (ids!=null && ids.length>0){
                    criteria.andUserIdIn(Arrays.asList(ids));
                }
                statCodService.codMemberExport(example,response);
                return ;
            }

            if (userId != null){
                criteria.andUserIdEqualTo(userId);
            }
            if (gender != null){
                criteria.andGenderEqualTo(gender.byteValue());
            }
            if (type != null){
                criteria.andTypeEqualTo(type.byteValue());
            }
            if (partyId != null){
                criteria.andPartyIdEqualTo(partyId);
            }
            if (branchId != null){
                criteria.andBranchIdEqualTo(branchId);
            }

            long count = memberViewMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            List<MemberView> memberViews = memberViewMapper.
                    selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

            CommonList commonList = new CommonList(count, pageNo, pageSize);
            resultMap.put("rows", memberViews);
            resultMap.put("records", count);
            resultMap.put("page", pageNo);
            resultMap.put("total", commonList.pageNum);

            Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
            baseMixins.remove(MemberView.class);
            JSONUtils.jsonp(resultMap, baseMixins);
        }
        return;
    }

    @RequestMapping("/statCod_selects")
    @ResponseBody
    public Map statCodSelects(Integer pageSize,
                                Integer pageNo,
                                @RequestParam(required = false, defaultValue = "1") Byte cls,
                                String searchStr)  {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = StringUtils.trimToNull(searchStr);

        if (searchStr != null) searchStr = searchStr.trim() + "%";
        long count = 0;
        List<Map<String,Object>> options = new ArrayList<Map<String,Object>>();

        if (cls==1){

        }else if (cls==2){

            //中组部党员
            MemberViewExample example = new MemberViewExample();
            MemberViewExample.Criteria criteria = example.createCriteria();
            example.setOrderByClause("sort_order desc");
            count = memberViewMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            List<MemberView> memberViews = memberViewMapper.
                    selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
            if(null != memberViews && memberViews.size()>0) {
                for (MemberView memberView : memberViews) {
                    Map<String, Object> option = new HashMap<>();
                    option.put("id", memberView.getUserId() + "");
                    option.put("username", memberView.getUsername());
                    option.put("nation", memberView.getNation());
                    options.add(option);
                }
            }
        }
        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;

    }


}
