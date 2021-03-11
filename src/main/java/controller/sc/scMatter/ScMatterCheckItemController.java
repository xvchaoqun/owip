package controller.sc.scMatter;

import controller.global.OpException;
import controller.sc.ScBaseController;
import domain.sc.scMatter.ScMatterCheckItem;
import domain.sc.scMatter.ScMatterCheckItemExample;
import domain.sc.scMatter.ScMatterCheckItemExample.Criteria;
import domain.sc.scMatter.ScMatterCheckItemView;
import domain.sc.scMatter.ScMatterCheckItemViewExample;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.LogConstants;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.ContentTypeUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sc")
public class ScMatterCheckItemController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterCheckItem:list")
    @RequestMapping("/scMatterCheckItem")
    public String scMatterCheckItem(@RequestParam(defaultValue = "1") Integer cls,
                                    Integer userId,
                                    ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(userId!=null){
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        return "sc/scMatter/scMatterCheckItem/scMatterCheckItem_page";
    }

    @RequiresPermissions("scMatterCheckItem:list")
    @RequestMapping("/scMatterCheckItem_data")
    public void scMatterCheckItem_data(HttpServletResponse response,
                                    Integer year,
                                       Integer num,
                                       Boolean isRandom,
                                       Integer userId,
                                       Byte confirmType,
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

        ScMatterCheckItemViewExample example = new ScMatterCheckItemViewExample();
        ScMatterCheckItemViewExample.Criteria criteria = example.createCriteria().andCheckIsDeletedEqualTo(false);
        example.setOrderByClause("check_date desc, id asc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (num!=null) {
            criteria.andNumEqualTo(num);
        }
        if (isRandom!=null) {
            criteria.andIsRandomEqualTo(isRandom);
        }
        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (confirmType!=null) {
            criteria.andConfirmTypeEqualTo(confirmType);
        }

        long count = scMatterCheckItemViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScMatterCheckItemView> records= scMatterCheckItemViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterCheckItem.class, scMatterCheckItemMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping(value = "/scMatterCheckItem_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isAnyFormat(file, "pdf")) {
            throw new OpException("核查情况表格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "scMatterCheckItem");

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", UserResUtils.sign(savePath));

        return resultMap;
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping(value = "/scMatterCheckItem_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_au(ScMatterCheckItem record,
                                       MultipartFile _selfFile,
                                       HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        if(record.getCheckFile()!=null) {
            UserRes resBean = UserResUtils.decode(record.getCheckFile());
            record.setCheckFile(resBean.getRes());
        }

        String selfFile = uploadPdf(_selfFile, "scMatterCheckItem-selfFile");
        record.setSelfFile(selfFile);

        if (id == null) {
            scMatterCheckItemService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "添加个人有关事项-核查对象：%s", record.getId()));
        } else {

            scMatterCheckItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "更新个人有关事项-核查对象：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping("/scMatterCheckItem_au")
    public String scMatterCheckItem_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterCheckItem scMatterCheckItem = scMatterCheckItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterCheckItem", scMatterCheckItem);
        }
        return "sc/scMatter/scMatterCheckItem/scMatterCheckItem_au";
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping(value = "/scMatterCheckItem_ow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_ow(ScMatterCheckItem record,
                                       MultipartFile _owHandleFile,
                                       HttpServletRequest request) throws IOException, InterruptedException {

        Integer id = record.getId();

        String owHandleFile = uploadPdf(_owHandleFile, "scMatterCheckItem-owHandleFile");
        record.setOwHandleFile(owHandleFile);

        if (id == null) {
            scMatterCheckItemService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "添加组织处理：%s", record.getId()));
        } else {

            scMatterCheckItemService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "更新组织处理：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping("/scMatterCheckItem_ow")
    public String scMatterCheckItem_ow(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterCheckItem scMatterCheckItem = scMatterCheckItemMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterCheckItem", scMatterCheckItem);
        }
        return "sc/scMatter/scMatterCheckItem/scMatterCheckItem_ow";
    }

    @RequiresPermissions("scMatterCheckItem:del")
    @RequestMapping(value = "/scMatterCheckItem_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            scMatterCheckItemService.del(id);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "删除个人有关事项-核查对象：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheckItem:del")
    @RequestMapping(value = "/scMatterCheckItem_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map scMatterCheckItem_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterCheckItemService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "批量删除个人有关事项-核查对象：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping("/scMatterCheckItem_reuse")
    public String scMatterCheckItem_reuse(Integer replyItemId,
                                      ModelMap modelMap) {

        ScMatterCheckItemView smci = iScMapper.getScMatterCheckItemView(replyItemId);
        modelMap.put("smci", smci);

        return "sc/scMatter/scMatterCheckItem/scMatterCheckItem_reuse";
    }

    @RequiresPermissions("scMatterCheckItem:edit")
    @RequestMapping(value = "/scMatterCheckItem_reuse", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterCheckItem_reuse(int itemId, Integer[] recordIds,
                                      HttpServletRequest request) {

        scMatterCheckItemService.reuse(itemId, recordIds);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新核查复用：%s, %s",
                itemId, StringUtils.join(recordIds, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scMatterCheckItem_selects")
    @ResponseBody
    public Map scMatterCheckItem_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterCheckItemExample example = new ScMatterCheckItemExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }
*/
        long count = scMatterCheckItemMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterCheckItem> scMatterCheckItems = scMatterCheckItemMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterCheckItems && scMatterCheckItems.size()>0){

            for(ScMatterCheckItem scMatterCheckItem:scMatterCheckItems){

                Select2Option option = new Select2Option();
                option.setText(scMatterCheckItem.getRemark());
                option.setId(scMatterCheckItem.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
