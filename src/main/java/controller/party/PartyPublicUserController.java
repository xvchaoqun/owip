package controller.party;

import controller.BaseController;
import domain.party.PartyPublicUser;
import domain.party.PartyPublicUserExample;
import domain.party.PartyPublicUserExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class PartyPublicUserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("partyPublicUser:list")
    @RequestMapping("/partyPublicUser")
    public String partyPublicUser() {

        return "party/partyPublicUser/partyPublicUser_page";
    }

    @RequiresPermissions("partyPublicUser:list")
    @RequestMapping("/partyPublicUser_data")
    @ResponseBody
    public void partyPublicUser_data(HttpServletResponse response,
                                    Integer publicId,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PartyPublicUserExample example = new PartyPublicUserExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("public_id desc, id desc");

        if (publicId!=null) {
            criteria.andPublicIdEqualTo(publicId);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            partyPublicUser_export(example, response);
            return;
        }

        long count = partyPublicUserMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PartyPublicUser> records= partyPublicUserMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(partyPublicUser.class, partyPublicUserMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("partyPublicUser:edit")
    @RequestMapping(value = "/partyPublicUser_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_partyPublicUser_au(PartyPublicUser record, HttpServletRequest request) {

        Integer id = record.getId();

        if (partyPublicUserService.idDuplicate(id, record.getPublicId(), record.getUserId())) {
            return failed("添加重复");
        }
        if (id == null) {
            
            partyPublicUserService.insertSelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "添加党员公示：{0}", record.getId()));
        } else {

            partyPublicUserService.updateByPrimaryKeySelective(record);
            logger.info(log( LogConstants.LOG_PARTY, "更新党员公示：{0}", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("partyPublicUser:edit")
    @RequestMapping("/partyPublicUser_au")
    public String partyPublicUser_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            PartyPublicUser partyPublicUser = partyPublicUserMapper.selectByPrimaryKey(id);
            modelMap.put("partyPublicUser", partyPublicUser);
        }
        return "party/partyPublicUser/partyPublicUser_au";
    }

    @RequiresPermissions("partyPublicUser:del")
    @RequestMapping(value = "/partyPublicUser_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map partyPublicUser_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            partyPublicUserService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PARTY, "批量删除党员公示：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void partyPublicUser_export(PartyPublicUserExample example, HttpServletResponse response) {

        List<PartyPublicUser> records = partyPublicUserMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属公示|100","公示对象|100","所属党委|100","所属支部|100","党委名称|100","支部名称|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            PartyPublicUser record = records.get(i);
            String[] values = {
                record.getPublicId()+"",
                            record.getUserId()+"",
                            record.getPartyId()+"",
                            record.getBranchId()+"",
                            record.getPartyName(),
                            record.getBranchName()
            };
            valuesList.add(values);
        }
        String fileName = "党员公示_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
