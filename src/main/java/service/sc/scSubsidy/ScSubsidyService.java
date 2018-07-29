package service.sc.scSubsidy;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sc.scSubsidy.ScSubsidy;
import domain.sc.scSubsidy.ScSubsidyCadre;
import domain.sc.scSubsidy.ScSubsidyCadreExample;
import domain.sc.scSubsidy.ScSubsidyDc;
import domain.sc.scSubsidy.ScSubsidyDcExample;
import domain.sc.scSubsidy.ScSubsidyDispatch;
import domain.sc.scSubsidy.ScSubsidyExample;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.unit.UnitService;
import sys.constants.CadreConstants;
import sys.constants.DispatchConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScSubsidyService extends BaseMapper {

    @Autowired
    private UnitService unitService;
    @Autowired
    private MetaTypeService metaTypeService;

    @Transactional
    public void insertSelective(ScSubsidy record, Integer[] dispatchIds){

        short year = record.getYear();
        int hrType = record.getHrType();
        int hrNum = record.getHrNum();
        int feType = record.getFeType();
        int feNum = record.getFeNum();

        {
            ScSubsidyExample example = new ScSubsidyExample();
            example.createCriteria().andYearEqualTo(year).andHrTypeEqualTo(hrType).andHrNumEqualTo(hrNum);
            if(scSubsidyMapper.countByExample(example)>0){
                throw new OpException("发人事处通知编号重复。");
            }
        }
        {
            ScSubsidyExample example = new ScSubsidyExample();
            example.createCriteria().andYearEqualTo(year).andFeTypeEqualTo(feType).andFeNumEqualTo(feNum);
            if(scSubsidyMapper.countByExample(example)>0){
                throw new OpException("发财经处通知编号重复。");
            }
        }


        scSubsidyMapper.insertSelective(record);
        Integer subsidyId = record.getId();
        for (Integer dispatchId : dispatchIds) {

            ScSubsidyDispatch ssd = new ScSubsidyDispatch();
            ssd.setSubsidyId(subsidyId);
            ssd.setDispatchId(dispatchId);
            scSubsidyDispatchMapper.insertSelective(ssd);
        }

        List<DispatchCadre> dispatchCadres = iScMapper.getDispatchCadres(StringUtils.join(dispatchIds, ","));
        Set<Integer> cadreIdSet = new LinkedHashSet<>();
        for (DispatchCadre dispatchCadre : dispatchCadres) {
            int cadreId = dispatchCadre.getCadreId();
            if(!cadreIdSet.contains(cadreId)){
                cadreIdSet.add(cadreId);
            }
            ScSubsidyDc scSubsidyDc = new ScSubsidyDc();
            scSubsidyDc.setSubsidyId(subsidyId);
            scSubsidyDc.setCadreId(cadreId);
            scSubsidyDc.setWorkTime(dispatchCadre.getDispatch().getWorkTime());
            scSubsidyDc.setType(dispatchCadre.getType());
            scSubsidyDc.setPost(dispatchCadre.getPost());
            scSubsidyDc.setAdminLevel(dispatchCadre.getAdminLevelId());

            scSubsidyDcMapper.insertSelective(scSubsidyDc);
        }

        for (Integer cadreId : cadreIdSet) {
            Cadre cadre = cadreMapper.selectByPrimaryKey(cadreId);
            ScSubsidyCadre scSubsidyCadre = new ScSubsidyCadre();
            scSubsidyCadre.setSubsidyId(subsidyId);
            scSubsidyCadre.setCadreId(cadreId);
            scSubsidyCadre.setUnitId(cadre.getUnitId());
            scSubsidyCadre.setPost(cadre.getPost());
            // 已离任行政级别显示为无
            if(CadreConstants.CADRE_STATUS_NOW_SET.contains(cadre.getStatus()))
                scSubsidyCadre.setAdminLevel(cadre.getTypeId());

            scSubsidyCadreMapper.insertSelective(scSubsidyCadre);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ScSubsidyExample example = new ScSubsidyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scSubsidyMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ScSubsidy record){

        return scSubsidyMapper.updateByPrimaryKeySelective(record);
    }

    // 选择任免文件
    public TreeNode getDispatchTree(List<Dispatch> dispatches, Set<Integer> scSubsidyDispatchIdSet) {

        TreeNode root = new TreeNode();
        root.title = "任免文件";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;

        for (Dispatch dispatch : dispatches) {

            TreeNode node = new TreeNode();
            node.title = dispatch.getDispatchCode();
            int key = dispatch.getId();
            node.key =  key + "";

            if (scSubsidyDispatchIdSet.contains(key))
                node.hideCheckbox = true;

            rootChildren.add(node);
        }
        return root;
    }

    // 导出
    public void export(Integer id, byte type, HttpServletResponse response) throws IOException {

        ScSubsidy scSubsidy = scSubsidyMapper.selectByPrimaryKey(id);
        String code = (type==1)?scSubsidy.getHrCode():scSubsidy.getFeCode();
        String schoolName = CmTag.getSysConfig().getSchoolName();
        String date = DateUtils.formatDate(scSubsidy.getInfoDate(), "yyyy年MM月dd日");

        ScSubsidyCadreExample example = new ScSubsidyCadreExample();
        example.createCriteria().andSubsidyIdEqualTo(scSubsidy.getId());
        List<ScSubsidyCadre> scSubsidyCadres = scSubsidyCadreMapper.selectByExample(example);

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/sc/sc_subsidy.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(6);
        XSSFCell cell = row.getCell(0);
        String str = cell.getStringCellValue()
                .replace("code", code);
        cell.setCellValue(str);

        row = sheet.getRow(16);
        cell = row.getCell(6);
        str = cell.getStringCellValue()
                .replace("school", schoolName);
        cell.setCellValue(str);

        row = sheet.getRow(17);
        cell = row.getCell(6);
        str = cell.getStringCellValue()
                .replace("date", date);
        cell.setCellValue(str);

        // 附表
        sheet = wb.getSheetAt(1);
        {
            ScSubsidyCadre scSubsidyCadre = scSubsidyCadres.get(0);
            renderCadre(sheet, scSubsidyCadre, 0);
        }
        int size = scSubsidyCadres.size();
        int i = 0;
        for (; i < size - 1; i++) {

            ExcelUtils.copyRows(1, 8, 8 * (i + 1), sheet);

            XSSFRow indexRow = sheet.getRow(1 + 8 * (i + 1));
            indexRow.getCell(0).setCellValue(i + 2);

            ScSubsidyCadre scSubsidyCadre = scSubsidyCadres.get(i + 1);
            renderCadre(sheet, scSubsidyCadre, 8 * (i + 1));
        }

        ExportHelper.output(wb, code + ".xlsx", response);
    }

    private void renderCadre(XSSFSheet sheet, ScSubsidyCadre scSubsidyCadre, int rowNum) {

        int subsidyId = scSubsidyCadre.getSubsidyId();
        int cadreId = scSubsidyCadre.getCadreId();
        CadreView cadre = scSubsidyCadre.getCadre();
        Integer unitId = scSubsidyCadre.getUnitId();
        Unit unit = unitService.findAll().get(unitId);
        String post = scSubsidyCadre.getPost();
        Integer adminLevel = scSubsidyCadre.getAdminLevel();

        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cel = row.getCell(2);
        cel.setCellValue(cadre.getRealname());
        cel = row.getCell(4);
        cel.setCellValue(unit.getName());
        cel = row.getCell(6);
        cel.setCellValue(cadre.getCode());

        row = sheet.getRow(rowNum + 1);
        cel = row.getCell(2);
        cel.setCellValue(post);
        cel = row.getCell(6);
        cel.setCellValue(adminLevel == null ? "无" : metaTypeService.getName(adminLevel));

        ScSubsidyDcExample example = new ScSubsidyDcExample();
        example.createCriteria().andSubsidyIdEqualTo(subsidyId).andCadreIdEqualTo(cadreId);
        List<ScSubsidyDc> scSubsidyDcs = scSubsidyDcMapper.selectByExample(example);
        if(scSubsidyDcs.size()>4){
            scSubsidyDcs = scSubsidyDcs.subList(0, 4);
        }

        rowNum = rowNum + 4;
        for (ScSubsidyDc scSubsidyDc : scSubsidyDcs) {

            row = sheet.getRow(rowNum);

            cel = row.getCell(1);
            cel.setCellValue(DispatchConstants.DISPATCH_CADRE_TYPE_MAP.get(scSubsidyDc.getType()));

            cel = row.getCell(2);
            cel.setCellValue(DateUtils.formatDate(scSubsidyDc.getWorkTime(), "yyyy.MM.dd"));

            cel = row.getCell(3);
            cel.setCellValue(scSubsidyDc.getPost());

            cel = row.getCell(6);
            cel.setCellValue(metaTypeService.getName(scSubsidyDc.getAdminLevel()));
        }
    }
}
