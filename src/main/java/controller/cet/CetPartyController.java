package controller.cet;

import controller.global.OpException;
import domain.cet.CetParty;
import domain.cet.CetPartyExample;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

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
    public String cetParty(@RequestParam(required = false, defaultValue = "0") Byte cls,
                           String name,
                           String partyId,
                           ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("name", name);
        modelMap.put("partyId", partyId);

        return "cet/cetParty/cetParty_page";
    }

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty_data")
    public void cetParty_data(HttpServletResponse response,
                              Integer partyId,
                              String name,
                              @RequestParam(required = false, defaultValue = "0") Byte cls,
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

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (null != cls){
            criteria.andIsDeletedEqualTo(cls == 1 ? true : false);
        }
        if (StringUtils.isNotBlank(name)){
            criteria.andNameLike(SqlUtils.like(StringUtils.trimToNull(name)));
        }

        /*if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetParty_export(example, response);
            return;
        }*/

        long count = cetPartyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetParty> records= cetPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
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
    public Map do_cetParty_au(CetParty record,
                              @RequestParam(value="partyIds[]",required=false) Integer[] partyIds,
                              HttpServletRequest request) {


        Integer id = record.getId();
        if (null == partyIds) {
            if (cetPartyService.idDuplicate(id, record.getPartyId())) {
                throw new OpException("添加重复");
            }
        }
        if (id == null) {
            if (null != partyIds){
                cetPartyService.batchInsert(partyIds);
                logger.info(addLog(LogConstants.LOG_CET, "添加院系级党委：%s", StringUtils.join(partyIds, ",")));
            }else {
                cetPartyService.insertSelective(record);
                logger.info(addLog(LogConstants.LOG_CET, "添加院系级党委：%s", record.getId()));
            }
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

        if(partyId!=null) {
            modelMap.put("party", CmTag.getParty(partyId));
        }

        return "cet/cetParty/cetParty_au";
    }

    //生成关联基层党组织树
    @RequiresPermissions("cetParty:edit")
    @RequestMapping("/selectparties_tree")
    @ResponseBody
    public Map selectParties_tree() throws IOException{

        //得到所有基层党组织
        CetPartyExample example = new CetPartyExample();
        example.createCriteria().andIsDeletedEqualTo(false);
        List<CetParty> cetParties = cetPartyMapper.selectByExample(example);
        Set<Integer> partyIds = new HashSet<Integer>();
        for (CetParty cetParty : cetParties) {
            if(cetParty.getPartyId()!=null) {
                partyIds.add(cetParty.getPartyId());
            }
        }

        TreeNode tree = cetPartyService.getTree(partyIds);

        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }

    @RequiresPermissions("cetParty:edit")
    @RequestMapping(value = "/cetParty_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetParty_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids,
                                    Boolean isDeleted,
                                    ModelMap modelMap) {

        if (null != ids && ids.length > 0) {
            isDeleted = BooleanUtils.isTrue(isDeleted);
            cetPartyService.batchDel(ids, isDeleted);
            logger.info(addLog(LogConstants.LOG_CET, "批量"+(isDeleted?"删除":"恢复")+"二级党委管理员：%s",
                    StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetParty:changeOrder")
    @RequestMapping(value = "/cetParty_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetParty_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetPartyService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "二级党委调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/cetParty_selects")
    @ResponseBody
    public Map cetParty_selects(Integer pageSize, Boolean auth, Boolean del, Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetPartyExample example = new CetPartyExample();
        CetPartyExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("is_deleted asc, sort_order desc");

        if(del!=null){
            criteria.andIsDeletedEqualTo(del);
        }

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }

        //===========权限
        if(BooleanUtils.isTrue(auth)) {
            if (!ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN)) {
                List<Integer> cetPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                if (cetPartyIdList.size() > 0)
                    criteria.andIdIn(cetPartyIdList);
                else
                    criteria.andIdIsNull();
            }
        }

        long count = cetPartyMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<CetParty> records = cetPartyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));
        List<Map<String, Object>> options = new ArrayList<>();
        for(CetParty record:records){

            Map<String, Object> option = new HashMap<>();
            option.put("text", record.getName());
            option.put("id", record.getId());
            option.put("del", record.getIsDeleted());

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
