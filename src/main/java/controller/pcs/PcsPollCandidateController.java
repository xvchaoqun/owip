package controller.pcs;

import controller.global.OpException;
import domain.pcs.PcsPollCandidate;
import domain.pcs.PcsPollCandidateExample;
import domain.pcs.PcsPollCandidateExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sys.constants.LogConstants;
import sys.tool.paging.CommonList;
import sys.utils.ExcelUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequestMapping("/pcs")
@Controller
public class PcsPollCandidateController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPollCandidate:list")
    @RequestMapping("/pcsPollCandidate")
    public String pcsPollCandidate() {

        return "pcs/pcsPoll/pcsPollCandidate/pcsPollCandidate_page";
    }

    @RequiresPermissions("pcsPollCandidate:list")
    @RequestMapping("/pcsPollCandidate_data")
    @ResponseBody
    public void pcsPollCandidate_data(HttpServletResponse response,
                                    Integer userId,
                                    Byte type,
                                 Integer pollId,
                                
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PcsPollCandidateExample example = new PcsPollCandidateExample();
        Criteria criteria = example.createCriteria().andPollIdEqualTo(pollId);
        example.setOrderByClause("sort_order desc");

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        /*if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            pcsPollCandidate_export(example, response);
            return;
        }*/

        long count = pcsPollCandidateMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PcsPollCandidate> records= pcsPollCandidateMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pcsPollCandidate.class, pcsPollCandidateMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("pcsPollCandidate:list")
    @RequestMapping("/pcsPollCandidate_import")
    public String pcsPollCandidate_import() {

        return "pcs/pcsPoll/pcsPollCandidate/pcsPollCandidate_import";
    }

    @RequiresPermissions("pcsPollCandidate:del")
    @RequestMapping(value = "/pcsPollCandidate_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pcsPollCandidate_batchDel(HttpServletRequest request, Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            pcsPollCandidateService.batchDel(ids);
            logger.info(log( LogConstants.LOG_PCS, "批量删除党代会投票候选人：{0}", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPollCandidate:changeOrder")
    @RequestMapping(value = "/pcsPollCandidate_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollCandidate_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        pcsPollCandidateService.changeOrder(id, addNum);
        logger.info(log( LogConstants.LOG_PCS, "党代会投票推荐人调序：{0}, {1}", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPollCandidate:import")
    @RequestMapping(value = "/pcsPollCandidate_import", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPollCandidate_import(Integer pollId, Byte type, HttpServletRequest request) throws InvalidFormatException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile xlsx = multipartRequest.getFile("xlsx");

        OPCPackage pkg = OPCPackage.open(xlsx.getInputStream());
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        List<PcsPollCandidate> records = new ArrayList<>();
        int row = 1;
        for (Map<Integer, String> xlsRow : xlsRows) {

            PcsPollCandidate record = new PcsPollCandidate();
            row++;

            String usercode = StringUtils.trimToNull(xlsRow.get(0));
            if (StringUtils.isBlank(usercode)){
                continue;
            }
            SysUserView user = sysUserService.findByCode(usercode);
            if (user == null){
                throw new OpException("第{0}行学工号[{1}]不存在", row, usercode);
            }
            record.setPollId(pollId);
            record.setUserId(user.getId());
            record.setType(type);

            records.add(record);
        }

        Collections.reverse(records); // 逆序排列，保证导入的顺序正确

        int addCount = pcsPollCandidateService.bacthImport(records, pollId, type);
        int totalCount = records.size();
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("addCount", addCount);
        resultMap.put("total", totalCount);

        logger.info(log(LogConstants.LOG_ADMIN,
                "导入培训专家成功，总共{0}条记录，其中成功导入{1}条记录，{2}条覆盖",
                totalCount, addCount, totalCount - addCount));

        return resultMap;
    }
}
