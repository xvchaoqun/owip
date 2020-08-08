package controller.sc.scMatter;

import controller.global.OpException;
import controller.sc.ScBaseController;
import domain.sc.scMatter.*;
import domain.sc.scMatter.ScMatterAccessExample.Criteria;
import mixin.MixinUtils;
import net.coobird.thumbnailator.Thumbnails;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScMatterAccessController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scMatterAccess:list")
    @RequestMapping("/scMatterAccess")
    public String scMatterAccess(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(cls==-1){
            return "sc/scMatter/scMatterAccess/scMatterAccess_page_popup";
        }

        if (cls == 2) {
            return "forward:/sc/scMatterTransfer";
        }

        return "sc/scMatter/scMatterAccess/scMatterAccess_page";
    }

    @RequiresPermissions("scMatterAccess:list")
    @RequestMapping("/scMatterAccess_data")
    public void scMatterAccess_data(HttpServletResponse response,
                                    Integer matterItemId,
                                    Boolean isCopy,
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

        long count = 0 ;
        List<ScMatterAccess> records = null;
        if(matterItemId!=null){
            count = iScMapper.countScMatterAccessList(matterItemId);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            records = iScMapper.selectScMatterAccessList(matterItemId, new RowBounds((pageNo - 1) * pageSize, pageSize));
        }else {
            ScMatterAccessExample example = new ScMatterAccessExample();
            Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
            example.setOrderByClause("access_date desc, id desc");

            if (isCopy != null) {
                criteria.andIsCopyEqualTo(isCopy);
            }
            count = scMatterAccessMapper.countByExample(example);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            records = scMatterAccessMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scMatterAccess.class, scMatterAccessMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping(value = "/scMatterAccess_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccess_au(ScMatterAccess record,
                                    @RequestParam(value = "matterItemIds[]", required = false) Integer[] matterItemIds,
                                    HttpServletRequest request) {

        Integer id = record.getId();

        if(record.getAccessFile()!=null) {
            UserRes resBean = UserResUtils.decode(record.getAccessFile());
            record.setAccessFile(resBean.getRes());
        }

        if (id == null) {
            scMatterAccessService.insertSelective(record, matterItemIds);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "添加个人有关事项-调阅记录：%s", record.getId()));
        } else {

            scMatterAccessService.updateByPrimaryKeySelective(record, matterItemIds);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "更新个人有关事项-调阅记录：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping(value = "/scMatterAccess_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccess_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("核查文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "scMatterAccess");

        Map<String, Object> resultMap = success();
        //resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("file", UserResUtils.sign(savePath));

        return resultMap;
    }


    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping("/scMatterAccess_au")
    public String scMatterAccess_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterAccess scMatterAccess = scMatterAccessMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterAccess", scMatterAccess);

            if(scMatterAccess!=null){
                Integer unitId = scMatterAccess.getUnitId();
                modelMap.put("unit", unitService.findAll().get(unitId));
            }

            ScMatterAccessItemViewExample example = new ScMatterAccessItemViewExample();
            example.createCriteria().andAccessIdEqualTo(id);
            example.setOrderByClause("id asc");
            List<ScMatterAccessItemView> scMatterAccessItemViews = scMatterAccessItemViewMapper.selectByExample(example);
            modelMap.put("itemList", scMatterAccessItemViews);
        }

        return "sc/scMatter/scMatterAccess/scMatterAccess_au";
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping("/scMatterAccess_query")
    public String scMatterAccess_query(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterAccess scMatterAccess = scMatterAccessMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterAccess", scMatterAccess);
        }

        return "sc/scMatter/scMatterAccess/scMatterAccess_query";
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping(value = "/scMatterAccess_query", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccess_query(int id,
                                       String receiver,
                                   String _base64,
                                   @RequestParam(required = false, defaultValue = "0") Integer _rotate,
                                   MultipartFile _cancelPic, HttpServletRequest request) throws IOException {

        if ((_cancelPic == null || _cancelPic.isEmpty()) && StringUtils.isBlank(_base64))
            return failed("请选择签字图片或进行拍照");

        ScMatterAccess record = new ScMatterAccess();
        record.setId(id);

        record.setHandleDate(new Date());
        record.setHandleUserId(ShiroHelper.getCurrentUserId());
        record.setReceiver(receiver);

        if (_cancelPic != null && !_cancelPic.isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String savePath = FILE_SEPARATOR
                    + "scMatterAccess_query" + FILE_SEPARATOR
                    + fileName + FileUtils.getExtention(_cancelPic.getOriginalFilename());

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(_cancelPic.getInputStream())
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);

            record.setReceivePdf(savePath);
        }else if(StringUtils.isNotBlank(_base64)){

            String savePath = FILE_SEPARATOR
                    + "scMatterAccess_query" + FILE_SEPARATOR + UUID.randomUUID().toString() + ".jpg";

            FileUtils.mkdirs(springProps.uploadPath + savePath);
            Thumbnails.of(ImageUtils.decodeBase64ToBufferedImage(_base64.split("base64,")[1]))
                    .scale(1f)
                    .rotate(_rotate).toFile(springProps.uploadPath + savePath);
            //ImageUtils.decodeBase64ToImage(_base64.split("base64,")[1], springProps.uploadPath + realPath, fileName);

            record.setReceivePdf(savePath);
        }

        scMatterAccessMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ABROAD, "办理调阅：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping("/scMatterAccess_back")
    public String scMatterAccess_back(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScMatterAccess scMatterAccess = scMatterAccessMapper.selectByPrimaryKey(id);
            modelMap.put("scMatterAccess", scMatterAccess);
        }

        return "sc/scMatter/scMatterAccess/scMatterAccess_back";
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping(value = "/scMatterAccess_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scMatterAccess_back(ScMatterAccess record, HttpServletRequest request) throws IOException {


        ScMatterAccess _record = new ScMatterAccess();
        _record.setId(record.getId());
        _record.setReturnDate(record.getReturnDate());
        _record.setReturnUserId(ShiroHelper.getCurrentUserId());

        scMatterAccessMapper.updateByPrimaryKeySelective(_record);

        logger.info(addLog(LogConstants.LOG_ABROAD, "归还：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping("/scMatterAccess_items")
    public String scMatterAccess_items(Integer accessId, ModelMap modelMap) {

        ScMatterAccessItemViewExample example = new ScMatterAccessItemViewExample();
        example.createCriteria().andAccessIdEqualTo(accessId);
        example.setOrderByClause("id asc");
        List<ScMatterAccessItemView> scMatterAccessItemViews = scMatterAccessItemViewMapper.selectByExample(example);
        modelMap.put("itemList", scMatterAccessItemViews);

        return "sc/scMatter/scMatterAccess/scMatterAccess_items";
    }

    @RequiresPermissions("scMatterAccess:edit")
    @RequestMapping("/scMatterAccess_selectItems")
    public String scMatterAccess_selectItems(int userId, ModelMap modelMap) {

        ScMatterItemViewExample example = new ScMatterItemViewExample();
        ScMatterItemViewExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause("fill_time desc, id desc");

        List<ScMatterItemView> records= scMatterItemViewMapper.selectByExample(example);

        modelMap.put("matterItems", records);

        return "sc/scMatter/scMatterAccess/scMatterAccess_selectItems";
    }

    @RequiresPermissions("scMatterAccess:del")
    @RequestMapping(value = "/scMatterAccess_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scMatterAccessService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_MATTER, "批量删除个人有关事项-调阅记录：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/scMatterAccess_selects")
    @ResponseBody
    public Map scMatterAccess_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ScMatterAccessExample example = new ScMatterAccessExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("sort_order desc");

        /*if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike("%"+searchStr.trim()+"%");
        }*/

        long count = scMatterAccessMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<ScMatterAccess> scMatterAccesss = scMatterAccessMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        if(null != scMatterAccesss && scMatterAccesss.size()>0){

            for(ScMatterAccess scMatterAccess:scMatterAccesss){

                Select2Option option = new Select2Option();
                option.setText(scMatterAccess.getReceiver());
                option.setId(scMatterAccess.getId() + "");

                options.add(option);
            }
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }
}
