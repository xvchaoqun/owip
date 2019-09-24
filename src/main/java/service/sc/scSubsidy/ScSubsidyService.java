package service.sc.scSubsidy;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sc.scSubsidy.*;
import domain.unit.Unit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import service.base.MetaTypeService;
import service.sc.ScBaseMapper;
import service.unit.UnitService;
import sys.constants.CadreConstants;
import sys.constants.DispatchConstants;
import sys.tags.CmTag;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;
import sys.utils.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service
public class ScSubsidyService extends ScBaseMapper {

    @Autowired
    private UnitService unitService;
    @Autowired
    private MetaTypeService metaTypeService;

    @Transactional
    public void insertOrUpdateSelective(ScSubsidy record, Integer[] dispatchIds){

        Integer id = record.getId();
        
        short year = record.getYear();
        Integer hrType = record.getHrType();
        Integer hrNum = record.getHrNum();
        if((hrType==null && hrNum!=null) || (hrType!=null && hrNum==null)){
            throw new OpException("请填写完整发人事处通知文号和编码。");
        }
        Integer feType = record.getFeType();
        Integer feNum = record.getFeNum();
        if((feType==null && feNum!=null) || (feType!=null && feNum==null)){
            throw new OpException("请填写完整发财经处通知文号和编码。");
        }
        if(hrType==null && feType==null){
            throw new OpException("人事处或财经处通知至少填写一个。");
        }

        if(hrType!=null && hrNum!=null){
            ScSubsidyExample example = new ScSubsidyExample();
            ScSubsidyExample.Criteria criteria = example.createCriteria().andYearEqualTo(year)
                    .andHrTypeEqualTo(hrType).andHrNumEqualTo(hrNum);
            if(id!=null){
                criteria.andIdNotEqualTo(id);
            }
            if(scSubsidyMapper.countByExample(example)>0){
                throw new OpException("发人事处通知编号重复。");
            }
        }
        if(feType!=null && feNum!=null){
            ScSubsidyExample example = new ScSubsidyExample();
            ScSubsidyExample.Criteria criteria = example.createCriteria().andYearEqualTo(year)
                    .andFeTypeEqualTo(feType).andFeNumEqualTo(feNum);
    
            if(id!=null){
                criteria.andIdNotEqualTo(id);
            }
            
            if(scSubsidyMapper.countByExample(example)>0){
                throw new OpException("发财经处通知编号重复。");
            }
        }

        if(id==null) {
            
            scSubsidyMapper.insertSelective(record);
        }else{
            scSubsidyMapper.updateByPrimaryKeySelective(record);
        }
        
        Integer subsidyId = record.getId();
        {
            ScSubsidyDispatchExample example = new ScSubsidyDispatchExample();
            example.createCriteria().andSubsidyIdEqualTo(subsidyId);
            scSubsidyDispatchMapper.deleteByExample(example);
        }
        for (Integer dispatchId : dispatchIds) {

            ScSubsidyDispatch ssd = new ScSubsidyDispatch();
            ssd.setSubsidyId(subsidyId);
            ssd.setDispatchId(dispatchId);
            scSubsidyDispatchMapper.insertSelective(ssd);
        }

        {
            ScSubsidyDcExample example = new ScSubsidyDcExample();
            example.createCriteria().andSubsidyIdEqualTo(subsidyId);
            scSubsidyDcMapper.deleteByExample(example);
        }
        
        {
            ScSubsidyCadreExample example = new ScSubsidyCadreExample();
            example.createCriteria().andSubsidyIdEqualTo(subsidyId);
            scSubsidyCadreMapper.deleteByExample(example);
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
            scSubsidyDc.setAdminLevel(dispatchCadre.getAdminLevel());

            scSubsidyDcMapper.insertSelective(scSubsidyDc);
        }
        for (Integer cadreId : cadreIdSet) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            ScSubsidyCadre scSubsidyCadre = new ScSubsidyCadre();
            scSubsidyCadre.setSubsidyId(subsidyId);
            scSubsidyCadre.setCadreId(cadreId);
            scSubsidyCadre.setUnitId(cadre.getUnitId());
            scSubsidyCadre.setPost(cadre.getPost());
            scSubsidyCadre.setTitle(cadre.getTitle());
            // 已离任行政级别显示为无
            if(CadreConstants.CADRE_STATUS_NOW_SET.contains(cadre.getStatus()))
                scSubsidyCadre.setAdminLevel(cadre.getAdminLevel());

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
    
    // 包含的任免文件ID
    public Set<Integer> getDispatchIdSet(Integer subsidyId){
    
        Set<Integer> dispatchIdSet = new HashSet<>();
        if(subsidyId==null) return dispatchIdSet;
        
        ScSubsidyDispatchExample example = new ScSubsidyDispatchExample();
        example.createCriteria().andSubsidyIdEqualTo(subsidyId);
        List<ScSubsidyDispatch> scSubsidyDispatches = scSubsidyDispatchMapper.selectByExample(example);
        
        for (ScSubsidyDispatch scSubsidyDispatch : scSubsidyDispatches) {
            dispatchIdSet.add(scSubsidyDispatch.getDispatchId());
        }
        
        return dispatchIdSet;
    }

    // 选择任免文件
    public TreeNode getDispatchTree(List<Dispatch> dispatches, Integer subsidyId, Set<Integer> scSubsidyDispatchIdSet) {

        TreeNode root = new TreeNode();
        root.title = "任免文件";
        root.expand = true;
        root.isFolder = true;
        root.hideCheckbox = true;
        List<TreeNode> rootChildren = new ArrayList<TreeNode>();
        root.children = rootChildren;
    
        Set<Integer> dispatchIdSet = getDispatchIdSet(subsidyId);
    
        for (Dispatch dispatch : dispatches) {

            TreeNode node = new TreeNode();
            node.title = dispatch.getDispatchCode();
            int key = dispatch.getId();
            node.key =  key + "";

            if (scSubsidyDispatchIdSet.contains(key) && !dispatchIdSet.contains(key)) {
                node.hideCheckbox = true;
            }
            if(dispatchIdSet.contains(key)){
                node.select = true;
            }

            rootChildren.add(node);
        }
        return root;
    }

    // 导出
    public void export(Integer id, byte type, HttpServletResponse response) throws IOException {

        ScSubsidy scSubsidy = scSubsidyMapper.selectByPrimaryKey(id);
        String code = (type==1)?scSubsidy.getHrCode():scSubsidy.getFeCode();
        String filename = (type==1)?"sc_subsidy_hr.xlsx":"sc_subsidy_fe.xlsx";
        String schoolName = CmTag.getSysConfig().getSchoolName();
        String date = DateUtils.formatDate(scSubsidy.getInfoDate(), "yyyy年MM月dd日");

        ScSubsidyCadreExample example = new ScSubsidyCadreExample();
        example.createCriteria().andSubsidyIdEqualTo(scSubsidy.getId());
        List<ScSubsidyCadre> scSubsidyCadres = scSubsidyCadreMapper.selectByExample(example);

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/sc/"+filename));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        String scSubsidyLogo = CmTag.getStringProperty("scSubsidyLogo");
        if(StringUtils.isNotBlank(scSubsidyLogo)
                && FileUtils.exists(springProps.uploadPath + scSubsidyLogo)) {
            BufferedImage bufferImg = ImageIO.read(new File(springProps.uploadPath + scSubsidyLogo));
            ImageIO.write(bufferImg, "png", byteArrayOut);
            XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0,
                    0, -20 * Units.EMU_PER_PIXEL, 0, 0, 10, 6);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
            drawingPatriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
        }

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


        int size = scSubsidyCadres.size();
        if(size>0) {
            // 附表
            sheet = wb.getSheetAt(1);
            XSSFSheet templateSheet = wb.getSheetAt(2);
            {
                ScSubsidyCadre scSubsidyCadre = scSubsidyCadres.get(0);
                renderCadre(sheet, scSubsidyCadre, 0);
            }

            int i = 0;
            for (; i < size - 1; i++) {

                ExcelUtils.copyRows(1, 8, 8 * (i + 1), templateSheet, sheet);

                XSSFRow indexRow = sheet.getRow(1 + 8 * (i + 1));
                indexRow.getCell(0).setCellValue(i + 2);

                ScSubsidyCadre scSubsidyCadre = scSubsidyCadres.get(i + 1);
                renderCadre(sheet, scSubsidyCadre, 8 * (i + 1));
            }
        }

        wb.removeSheetAt(2);// 移除模板
        ExportHelper.output(wb, code + ".xlsx", response);
    }

    private void renderCadre(XSSFSheet sheet, ScSubsidyCadre scSubsidyCadre, int rowNum) {

        int subsidyId = scSubsidyCadre.getSubsidyId();
        int cadreId = scSubsidyCadre.getCadreId();
        CadreView cadre = scSubsidyCadre.getCadre();
        Integer unitId = scSubsidyCadre.getUnitId();
        Unit unit = CmTag.getUnit(unitId);
        String title = scSubsidyCadre.getTitle();
        Integer adminLevel = scSubsidyCadre.getAdminLevel();

        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cel = row.getCell(2);
        cel.setCellValue(cadre.getRealname());
        cel = row.getCell(4);

        cel.setCellValue(unit==null?"":unit.getName());
        cel = row.getCell(6);
        cel.setCellValue(cadre.getCode());

        row = sheet.getRow(rowNum + 1);
        cel = row.getCell(2);
        cel.setCellValue(title);
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

            rowNum++;
        }
    }
}
