package controller.dr;

import domain.dr.DrOnlineInspectorType;
import domain.dr.DrOnlinePost;
import domain.unit.Unit;
import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.dr.common.DrFinalResult;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.JSONUtils;
import sys.utils.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dr/drOnline")
public class DrOnlineResultController extends DrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult")
    public String drOnlineResult(Integer onlineId,
                                 @RequestParam(required = false, value = "typeIds") Integer[] typeIds,
                                 ModelMap modelMap) {

        List<Integer> unitIds = iDrMapper.selectUnitIds(onlineId);
        if (unitIds != null && unitIds.size() > 0){
            List<Unit> unitList = new ArrayList<>();
            for (Integer unitId : unitIds) {
                unitList.add(unitMapper.selectByPrimaryKey(unitId));
            }
            modelMap.put("unitList", unitList);
        }

        List<Integer> inspectorTypeIds = iDrMapper.selectTypeIds(onlineId);
        List<DrOnlineInspectorType> inspectorTypes = new ArrayList<>();
        if (inspectorTypeIds != null && inspectorTypeIds.size() > 0){
            for (Integer inspectorTypeId : inspectorTypeIds) {
                inspectorTypes.add(drOnlineInspectorTypeMapper.selectByPrimaryKey(inspectorTypeId));
            }
            modelMap.put("inspectorTypes", inspectorTypes);
        }
        if (typeIds != null){
            modelMap.put("selectTypeIds", Arrays.asList(typeIds));
        }
        List<DrOnlinePost>  drOnlinePosts = drOnlinePostService.getAllByOnlineId(onlineId);
        modelMap.put("drOnlinePosts", drOnlinePosts);

        return "dr/drOnline/drOnlineResult/drOnlineResult_page";
    }

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_data")
    @ResponseBody
    public void drOnlineResult_data(HttpServletResponse response,
                                    Integer onlineId,
                                    String _typeIds,
                                    Integer postId,
                                    Integer unitId,
                                    @RequestParam(required = false, value = "typeIds") Integer[] typeIds,
                                    String realname,//推荐人选
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        List<Integer> typeIdlist = new ArrayList<>();
        if (null != typeIds && typeIds.length > 0) {
            for (Integer typeId : typeIds) {
                typeIdlist.add(typeId);
            }
        }
        if (StringUtils.isBlank(realname)){
            realname = null;
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);


        if (export == 1) {
            List<Integer> typesFilter = new ArrayList<Integer>();
            _typeIds = _typeIds.replaceAll("(\\[)|(\\])", "");
            if (StringUtils.isNotBlank(_typeIds)) {
                List<String> _typesFilter = Arrays.asList(_typeIds.split(","));
                if (_typesFilter != null && _typesFilter.size() > 0) {
                    for (String typeId : _typesFilter) {
                        typesFilter.add(Integer.valueOf(StringUtil.trim(typeId)));
                    }
                }
            }
            List<DrFinalResult> drFinalResults = iDrMapper.selectResultList(typesFilter, postId, onlineId, realname, unitId, new RowBounds());
            drExportService.exportOnlineResult(onlineId, drFinalResults, response);
            return;
        }
        long count = iDrMapper.countResult(typeIdlist, postId, onlineId, realname, unitId);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<DrFinalResult> drFinalResults = iDrMapper.selectResultList(typeIdlist, postId, onlineId, realname, unitId,
                new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", drFinalResults);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(drOnlineResult.class, drOnlineResultMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("drOnlineResult:list")
    @RequestMapping("/drOnlineResult_filter")
    public String drOnlineResult_filter(ModelMap modelMap,
                                      HttpServletResponse response) throws IOException {

        return "/dr/drOnline/drOnlineResult/drOnlineResult_filter";
    }

    @RequestMapping("/unitPost_selects")
    @ResponseBody
    public Map unitPost_selects(Integer pageSize,
                                Integer pageNo,
                                Byte status,
                                Integer onlineId,
                                Integer unitId, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        List<DrOnlinePost> postViews = drOnlinePostService.getAllByOnlineId(onlineId);
        List<Integer> postIds = new ArrayList<>();
        for (DrOnlinePost postView : postViews){
            postIds.add(postView.getUnitPostId());
        }

        UnitPostViewExample example = new UnitPostViewExample();
        UnitPostViewExample.Criteria criteria = example.createCriteria();
        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        if (postIds.size() > 0){
            criteria.andIdIn(postIds);
        }

        example.setOrderByClause("status asc, unit_status asc, unit_sort_order asc, sort_order asc");
        if(unitId!=null){
            criteria.andUnitIdEqualTo(unitId);
        }
        if(StringUtils.isNotBlank(searchStr)){
            criteria.search(searchStr.trim());
        }

        long count = unitPostViewMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<UnitPostView> records = unitPostViewMapper.selectByExampleWithRowbounds(example,
                new RowBounds((pageNo-1)*pageSize, pageSize));

        List options = new ArrayList<>();
        if(null != records && records.size()>0){

            for(UnitPostView record:records){

                Map<String, Object> option = new HashMap<>();
                option.put("text", record.getCode() + "-" + record.getName());
                option.put("adminLevel", record.getAdminLevel());
                option.put("id", record.getId() + "");
                option.put("up", record);
                option.put("del", record.getStatus()== SystemConstants.UNIT_POST_STATUS_ABOLISH);
                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
