package controller.sc.scLetter;

import controller.global.OpException;
import controller.sc.ScBaseController;
import domain.sc.scLetter.ScLetter;
import domain.sc.scLetter.ScLetterView;
import domain.sc.scLetter.ScLetterViewExample;
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
import service.sc.scLetter.ScLetterUser;
import sys.constants.LogConstants;
import sys.gson.GsonUtils;
import sys.tags.CmTag;
import sys.spring.UserRes;
import sys.spring.UserResUtils;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/sc")
public class ScLetterController extends ScBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("scLetter:list")
    @RequestMapping("/scLetter")
    public String scLetter(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            return "forward:/sc/scLetterReplyItem";
        }else if (cls == 3) {
            return "forward:/sc/scLetterReply";
        }else if (cls == 4) {
            return "sc/scLetter/scLetter/scLetter_setting_page";
        }

        return "sc/scLetter/scLetter/scLetter_page";
    }

    @RequiresPermissions("scLetter:list")
    @RequestMapping("/scLetter_data")
    public void scLetter_data(HttpServletResponse response,
                                    Integer year,
                                    Integer type,
                                    Integer num,
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

        ScLetterViewExample example = new ScLetterViewExample();
        ScLetterViewExample.Criteria criteria = example.createCriteria().andIsDeletedEqualTo(false);
        example.setOrderByClause("query_date desc");

        if (year!=null) {
            criteria.andYearEqualTo(year);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (num!=null) {
            criteria.andNumEqualTo(num);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            scLetter_export(example, response);
            return;
        }

        long count = scLetterViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ScLetterView> records= scLetterViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(scLetter.class, scLetterMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("scLetter:edit")
    @RequestMapping(value = "/scLetter_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scLetter_upload(MultipartFile file) throws InterruptedException, IOException {

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtils.getExtention(originalFilename);
        if (!StringUtils.equalsIgnoreCase(ext, ".pdf")
                && !ContentTypeUtils.isFormat(file, "pdf")) {
            throw new OpException("核查文件格式错误，请上传pdf文件");
        }

        String savePath = uploadPdf(file, "scLetter");

        Map<String, Object> resultMap = success();
        resultMap.put("fileName", file.getOriginalFilename());
        resultMap.put("filePath", UserResUtils.sign(savePath));

        return resultMap;
    }

    @RequiresPermissions("scLetter:edit")
    @RequestMapping("/scLetter_items")
    public String scLetter_items(Integer letterId, ModelMap modelMap) {

        List<ScLetterUser> itemList = scLetterService.getItemList(letterId);
        modelMap.put("itemList", itemList);

        return "sc/scLetter/scLetter/scLetter_items";
    }

    @RequiresPermissions("scLetter:edit")
    @RequestMapping(value = "/scLetter_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_scLetter_au(ScLetter record,
                              String users,
                              HttpServletRequest request) throws UnsupportedEncodingException {

        Integer id = record.getId();

        if(record.getFilePath()!=null) {
            UserRes resBean = UserResUtils.decode(record.getFilePath());
            record.setFilePath(resBean.getRes());
        }

        List<ScLetterUser> scLetterUsers = GsonUtils.toBeans(users, ScLetterUser.class);

        if (id == null) {
            record.setIsDeleted(false);
            scLetterService.insertSelective(record, scLetterUsers);
            logger.info(addLog(LogConstants.LOG_SC_LETTER, "添加纪委函询文件管理：%s", record.getId()));
        } else {

            scLetterService.updateByPrimaryKeySelective(record, scLetterUsers);
            logger.info(addLog(LogConstants.LOG_SC_LETTER, "更新纪委函询文件管理：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scLetter:edit")
    @RequestMapping("/scLetter_au")
    public String scLetter_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            ScLetter scLetter = scLetterMapper.selectByPrimaryKey(id);
            modelMap.put("scLetter", scLetter);

            List<ScLetterUser> itemList = scLetterService.getItemList(id);
            modelMap.put("itemList", itemList);
        }
        return "sc/scLetter/scLetter/scLetter_au";
    }

    @RequiresPermissions("scLetter:del")
    @RequestMapping(value = "/scLetter_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            scLetterService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_SC_LETTER, "批量删除纪委函询文件管理：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("scLetter:edit")
    @RequestMapping("/scLetter_selectScRecord")
    public String scLetter_selectScRecord(int userId,
                                      ModelMap modelMap) {

        modelMap.put("sysUser", CmTag.getUserById(userId));

        return "sc/scLetter/scLetter/scLetter_selectScRecord";
    }

    public void scLetter_export(ScLetterViewExample example, HttpServletResponse response) {

        List<ScLetterView> records = scLetterViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"年份|100","类型|100","编号|100","函询日期|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ScLetterView record = records.get(i);
            String[] values = {
                record.getYear()+"",
                            record.getType()+"",
                            record.getNum()+"",
                            DateUtils.formatDate(record.getQueryDate(), DateUtils.YYYY_MM_DD),
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "纪委函询文件管理_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
