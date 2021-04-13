package controller.cet;

import controller.global.OpException;
import domain.cet.CetProjectFile;
import domain.cet.CetProjectFileExample;
import domain.cet.CetProjectFileExample.Criteria;
import domain.cet.CetProjectObj;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetProjectFileController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/cetProjectFile")
    public String cetProjectFile(int projectId,
                                 Integer pageSize,
                                 Integer pageNo,
                                 ModelMap modelMap){

        if(!ShiroHelper.isPermitted("cetProject:edit")){

            int userId = ShiroHelper.getCurrentUserId();
            CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
            if(cetProjectObj==null){
                return null; // 无权限查看
            }
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetProjectFileExample example = new CetProjectFileExample();
        Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("sort_order desc");


        long count = cetProjectFileMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectFile> records= cetProjectFileMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        modelMap.put("cetProjectFiles", records);
        modelMap.put("commonList", commonList);

        return "cet/cetProjectFile/cetProjectFile_page";
    }

    @RequiresPermissions("cetProject:edit")
    @RequestMapping(value = "/cetProjectFile_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectFile_au(Integer projectId,
                                    MultipartFile _file,
                                    String fileName,
                                    String website,
                                    HttpServletRequest request) throws IOException, InterruptedException {

        CetProjectFile record = null;
        if (_file == null && StringUtils.isBlank(website)) {
            throw new OpException("请上传培训材料或者填写培训材料网址");
        }
        if (_file != null) {
            record = new CetProjectFile();
            record.setProjectId(projectId);
            String originalFilename = _file.getOriginalFilename();
            String savePath = upload(_file, "cetProjectFile");
            record.setFilePath(savePath);
            if (StringUtils.isBlank(fileName)) {
                record.setFileName(originalFilename);
            }else {
                record.setFileName(fileName + FileUtils.getExtention(originalFilename));
            }
        }
        CetProjectFile record1 = null;
        if (StringUtils.isNotBlank(website)) {
            record1 = new CetProjectFile();
            record1.setProjectId(projectId);
            if (StringUtils.isBlank(fileName)){
                record1.setFileName(website);
            }else {
                record1.setFileName(fileName);
            }
            record1.setWebsite(website);
        }
        // 网址和文件分开存
        cetProjectFileService.insertSelective(record, record1);
        logger.info(log( LogConstants.LOG_CET, "添加培训材料：{0},{1}", record!=null?record.getId():"", record1!=null?record1.getId():""));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:del")
    @RequestMapping(value = "/cetProjectFile_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectFile_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetProjectFileService.del(id);
            logger.info(log( LogConstants.LOG_CET, "删除培训课件：{0}", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProject:changeOrder")
    @RequestMapping(value = "/cetProjectFile_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectFile_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetProjectFileService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_CET, "培训课件调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }
}
