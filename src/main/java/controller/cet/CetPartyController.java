package controller.cet;

import controller.global.OpException;
import domain.cet.CetParty;
import domain.cet.CetPartyView;
import domain.cet.CetPartyViewExample;
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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.SqlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetPartyController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty")
    public String cetParty(@RequestParam(required = false, defaultValue = "0") Byte cls,
                           String partyName,
                           String partyId,
                           ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("partyName", partyName);
        modelMap.put("partyId", partyId);

        return "cet/cetParty/cetParty_page";
    }

    @RequiresPermissions("cetParty:list")
    @RequestMapping("/cetParty_data")
    public void cetParty_data(HttpServletResponse response,
                              Integer partyId,
                              String partyName,
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

        CetPartyViewExample example = new CetPartyViewExample();
        CetPartyViewExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("id desc");

        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (null != cls){
            criteria.andIsDeletedEqualTo(cls == 1 ? true : false);
        }
        if (StringUtils.isNotBlank(partyName)){
            criteria.andPartyNameLike(SqlUtils.like(StringUtils.trimToNull(partyName)));
        }

        /*if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetParty_export(example, response);
            return;
        }*/

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
            if (cetPartyService.idDuplicate(null, record.getPartyId()))
                throw new OpException("添加重复");
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
        if(partyId!=null) {
            modelMap.put("party", CmTag.getParty(partyId));
        }

        return "cet/cetParty/cetParty_au";
    }

    @RequiresPermissions("cetParty:del")
    @RequestMapping(value = "/cetParty_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetParty_cancel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, Integer delete, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetPartyService.cancel(ids, delete);
            logger.info(addLog(LogConstants.LOG_CET, "删除/撤销/恢复二级党委培训管理员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

}
