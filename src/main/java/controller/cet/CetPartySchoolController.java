package controller.cet;

import domain.cet.CetPartySchool;
import domain.cet.CetPartySchoolView;
import domain.cet.CetPartySchoolViewExample;
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
public class CetPartySchoolController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetPartySchool:list")
    @RequestMapping("/cetPartySchool")
    public String cetPartySchool(Integer partySchoolId, ModelMap modelMap) {

        if(partySchoolId!=null){
            modelMap.put("partySchool", CmTag.getPsInfo(partySchoolId));
        }
        return "cet/cetPartySchool/cetPartySchool_page";
    }

    @RequiresPermissions("cetPartySchool:list")
    @RequestMapping("/cetPartySchool_data")
    public void cetPartySchool_data(HttpServletResponse response,
                                    Integer partySchoolId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPartySchoolViewExample example = new CetPartySchoolViewExample();
        CetPartySchoolViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (partySchoolId!=null) {
            criteria.andPartySchoolIdEqualTo(partySchoolId);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetPartySchool_export(example, response);
            return;
        }

        long count = cetPartySchoolViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetPartySchoolView> records= cetPartySchoolViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetPartySchool.class, cetPartySchoolMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetPartySchool:edit")
    @RequestMapping(value = "/cetPartySchool_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPartySchool_au(CetPartySchool record, HttpServletRequest request) {

        Integer id = record.getId();

        if (id == null) {
            cetPartySchoolService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "添加二级党校：%s", record.getPartySchoolId()));
        } else {

            cetPartySchoolService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_CET, "更新二级党校：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetPartySchool:edit")
    @RequestMapping("/cetPartySchool_au")
    public String cetPartySchool_au(Integer id,
                                    Integer partySchoolId,
                                    //Integer userId,
                                    ModelMap modelMap) {

        if (id != null) {
            CetPartySchool cetPartySchool = cetPartySchoolMapper.selectByPrimaryKey(id);
            modelMap.put("cetPartySchool", cetPartySchool);

            partySchoolId = cetPartySchool.getPartySchoolId();
            //userId = cetPartySchool.getUserId();
        }

        if(partySchoolId!=null){

            modelMap.put("partySchool", CmTag.getPsInfo(partySchoolId));
        }

        /*if(userId!=null){
            modelMap.put("sysUser", CmTag.getUserById(userId));
        }*/

        return "cet/cetPartySchool/cetPartySchool_au";
    }

    @RequiresPermissions("cetPartySchool:setAdmin")
    @RequestMapping(value = "/cetPartySchool_setAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetPartySchool_setAdmin(int id,
                                          Integer userId,
                                          HttpServletRequest request) {

        cetPartySchoolService.setAdmin(id, userId);
        logger.info(addLog(LogConstants.LOG_CET, "更新二级党校管理员：%s, %s", id ,userId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetPartySchool:setAdmin")
    @RequestMapping("/cetPartySchool_setAdmin")
    public String cetPartySchool_setAdmin(int id,
                                    ModelMap modelMap) {

        CetPartySchoolView cetPartySchool = cetPartySchoolService.getView(id);
        modelMap.put("cetPartySchool", cetPartySchool);

        Integer userId = cetPartySchool.getUserId();
        if(userId!=null)
            modelMap.put("sysUser", CmTag.getUserById(userId));

        return "cet/cetPartySchool/cetPartySchool_setAdmin";
    }

    @RequiresPermissions("cetPartySchool:del")
    @RequestMapping(value = "/cetPartySchool_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetPartySchool_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetPartySchoolService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除二级党校：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void cetPartySchool_export(CetPartySchoolViewExample example, HttpServletResponse response) {

        List<CetPartySchoolView> records = cetPartySchoolViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"所属二级党校|100","管理员|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetPartySchoolView record = records.get(i);
            String[] values = {
                record.getPartySchoolId()+"",
                            record.getUserId()+""
            };
            valuesList.add(values);
        }
        String fileName = "二级党校_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    @RequestMapping("/cetPartySchool_selects")
    @ResponseBody
    public Map cetPartySchool_selects(Integer pageSize,
                                   Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPartySchoolViewExample example = new CetPartySchoolViewExample();
        CetPartySchoolViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("party_school_is_history asc, sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andPartySchoolNameLike(SqlUtils.like(searchStr));
        }

        long count = cetPartySchoolViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetPartySchoolView> records = cetPartySchoolViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(CetPartySchoolView record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getPartySchoolName());
                option.put("id", record.getId() + "");
                if(record.getUserId()!=null) {
                    SysUserView uv = CmTag.getUserById(record.getUserId());
                    option.put("userId", uv.getId());
                    option.put("realname", uv.getRealname());
                    option.put("code", uv.getCode());
                }
                option.put("del", record.getPartySchoolIsHistory());
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
