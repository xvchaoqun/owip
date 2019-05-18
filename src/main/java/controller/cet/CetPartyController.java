package controller.cet;

import domain.cet.CetParty;
import domain.cet.CetPartyView;
import domain.cet.CetPartyViewExample;
import domain.sys.SysUserView;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetPartyController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty")
    public String cetParty() {

        return "cet/cetParty/cetParty_page";
    }

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty_data")
    public void cetParty_data(HttpServletResponse response,

                                    Integer partyId,
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

        CetPartyViewExample example = new CetPartyViewExample();
        CetPartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetParty_export(example, response);
            return;
        }

        long count = cetPartyViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPartyView> records= cetPartyViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetParty.class, cetPartyMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping(value = "/cetParty_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetParty_au(CetParty record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {

            cetPartyService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加院系级党委：%s", record.getId()));
        } else {

            cetPartyService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新院系级党委：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping("/cetParty_au")
    public String cetParty_au(Integer id,
                              Integer partyId,
                              ModelMap modelMap) {

        if (id != null) {
            CetParty cetParty = cetPartyMapper.selectByPrimaryKey(id);
            modelMap.put("cetParty", cetParty);

            partyId = cetParty.getPartyId();
        }
        if(partyId!=null)
            modelMap.put("party", CmTag.getParty(partyId));

        return "cet/cetParty/cetParty_au";
    }

    @RequiresPermissions("cetParty:setAdmin")
    @RequestMapping(value = "/cetParty_setAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetParty_setAdmin(int id,
                                          Integer userId,
                                          HttpServletRequest request) {

        cetPartyService.setAdmin(id, userId);
        logger.info(addLog(LogConstants.LOG_CET, "更新院系级党委管理员：%s, %s", id ,userId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetParty:setAdmin")
    @RequestMapping("/cetParty_setAdmin")
    public String cetParty_setAdmin(int id,
                                          ModelMap modelMap) {

        CetPartyView cetParty = cetPartyService.getView(id);
        modelMap.put("cetParty", cetParty);

        Integer userId = cetParty.getUserId();
        if(userId!=null)
            modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetParty/cetParty_setAdmin";
    }

    @RequiresPermissions("cetParty:del")
    @RequestMapping(value = "/cetParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetParty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetPartyService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除院系级党委：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetParty_export(CetPartyViewExample example, HttpServletResponse response) {

        List<CetPartyView> records = cetPartyViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属基层党组织|100","管理员|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetPartyView record = records.get(i);
            String[] values = {
                record.getPartyId()+"",
                            record.getUserId()+""
            };
            valuesList.add(values);
        }
        String fileName = "院系级党委_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetParty_selects")
    @ResponseBody
    public Map cetParty_selects(Integer pageSize,
                                      Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPartyViewExample example = new CetPartyViewExample();
        CetPartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_is_deleted asc, sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andPartyNameLike(SqlUtils.like(searchStr));
        }

        long count = cetPartyViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetPartyView> records = cetPartyViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CetPartyView record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getPartyName());
                option.put("id", record.getId() + "");
                if(record.getUserId()!=null) {
                    SysUserView uv = CmTag.getUserById(record.getUserId());
                    option.put("userId", uv.getId());
                    option.put("realname", uv.getRealname());
                    option.put("code", uv.getCode());
                }
                option.put("del", record.getPartyIsDeleted());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
