package service.sc.scDispatch;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreExample;
import domain.dispatch.DispatchType;
import domain.sc.scCommittee.ScCommitteeVote;
import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scDispatch.*;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import service.SpringProps;
import service.dispatch.DispatchCadreService;
import service.dispatch.DispatchService;
import service.dispatch.DispatchTypeService;
import service.sc.ScBaseMapper;
import shiro.ShiroHelper;
import sys.tool.office.WordTemplate;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class ScDispatchService extends ScBaseMapper {

    @Autowired
    protected SpringProps springProps;
    @Autowired
    protected DispatchService dispatchService;
    @Autowired
    protected DispatchTypeService dispatchTypeService;
    @Autowired
    protected DispatchCadreService dispatchCadreService;


    public ScDispatchView get(int id){

        ScDispatchViewExample example = new ScDispatchViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<ScDispatchView> scDispatchViews = scDispatchViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return scDispatchViews.size()>0?scDispatchViews.get(0):null;
    }

    public boolean idDuplicate(Integer id, int year, int dispatchTypeId, int code) {

        ScDispatchExample example = new ScDispatchExample();
        ScDispatchExample.Criteria criteria = example.createCriteria().andYearEqualTo(year)
                .andDispatchTypeIdEqualTo(dispatchTypeId)
                .andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return scDispatchMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScDispatch record, Integer[] committeeIds, Integer[] voteIds) {

        if (record.getYear() != null && record.getCode() != null)
            Assert.isTrue(!idDuplicate(null, record.getYear(), record.getDispatchTypeId(), record.getCode()), "编号重复");

        scDispatchMapper.insertSelective(record);

        processCommittee(record.getId(), committeeIds, voteIds);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        ScDispatchExample example = new ScDispatchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        scDispatchMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScDispatch record, Integer[] committeeIds, Integer[] voteIds) {

        if (record.getYear() != null && record.getCode() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getYear(), record.getDispatchTypeId(), record.getCode()), "编号重复");
        scDispatchMapper.updateByPrimaryKeySelective(record);

        processCommittee(record.getId(), committeeIds, voteIds);
    }

    private void processCommittee(int dispatchId, Integer[] committeeIds, Integer[] voteIds) {

        {
            ScDispatchCommitteeExample example = new ScDispatchCommitteeExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            scDispatchCommitteeMapper.deleteByExample(example);
        }
        if(committeeIds!=null) {
            for (Integer committeeId : committeeIds) {
                ScDispatchCommittee record = new ScDispatchCommittee();
                record.setDispatchId(dispatchId);
                record.setCommitteeId(committeeId);

                scDispatchCommitteeMapper.insertSelective(record);
            }
        }

        {
            ScDispatchUserExample example = new ScDispatchUserExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            scDispatchUserMapper.deleteByExample(example);
        }

        if(voteIds!=null) {
            for (Integer voteId : voteIds) {

                ScCommitteeVote scCommitteeVote = scCommitteeVoteMapper.selectByPrimaryKey(voteId);
                ScDispatchUser record = new ScDispatchUser();
                record.setDispatchId(dispatchId);
                record.setVoteId(voteId);
                record.setType(scCommitteeVote.getType());
                record.setSortOrder(getNextSortOrder("sc_dispatch_user", "type=" + record.getType()));
                scDispatchUserMapper.insertSelective(record);
            }
        }
    }

    public void exportSign(int dispatchId, HttpServletResponse response) throws IOException {

        ScDispatch scDispatch = scDispatchMapper.selectByPrimaryKey(dispatchId);

        String sign = null;
        SysUserView _user = ShiroHelper.getCurrentUser();
        if (FileUtils.exists(springProps.uploadPath + _user.getSign()))
            sign = springProps.uploadPath + _user.getSign();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", scDispatch.getDispatchCode());
        param.put("date", DateUtils.formatDate(scDispatch.getPubTime(), DateUtils.YYYY_MM_DD_CHINA));
        param.put("title", StringUtils.trimToEmpty(scDispatch.getTitle()));
        param.put("signDate", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));

        if(sign!=null) {
            Map<String, String> signImage = new HashMap<String, String>();
            signImage.put("type", "image");
            signImage.put("width", "90");
            signImage.put("height", "60");
            signImage.put("filePath", sign);
            param.put("sign", signImage);
        }else{
            param.put("sign", "");
        }

        Map<Integer, DispatchType> dispatchTypeMap = dispatchTypeService.findAll();
        DispatchType dispatchType = dispatchTypeMap.get(scDispatch.getDispatchTypeId());


        WordTemplate wt = new WordTemplate(ResourceUtils.getFile("classpath:xlsx/sc/"
                +(StringUtils.equals(dispatchType.getAttr(), "党务")?"sc_dispatch_sign_ow"
                :"sc_dispatch_sign_ad")+".docx").getAbsolutePath());
        //long start = System.currentTimeMillis();
        XWPFDocument doc = wt.process(param);
        //long end = System.currentTimeMillis();
        //System.out.println("use time:" + (end-start));

        DownloadUtils.addFileDownloadCookieHeader(response);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        doc.write(out);
        out.flush();
        out.close();
    }

    /**
     * 签发单.xls
     */
    /*public XSSFWorkbook exportSign(int dispatchId) throws IOException {

        ScDispatch scDispatch = scDispatchMapper.selectByPrimaryKey(dispatchId);
        String dispatchCode = CmTag.getDispatchCode(scDispatch.getCode(), scDispatch.getDispatchTypeId(), scDispatch.getYear());

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/sc/sc_public_sign.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        //wb.setSheetName(0, dispatchCode);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String schoolName = CmTag.getSysConfig().getSchoolName();
        schoolName = schoolName.replaceAll ("(.{1})", "$1 "); // 每个字后添加一个空格
        String str = cell.getStringCellValue()
                .replace("school", schoolName);
        cell.setCellValue(str);

        row = sheet.getRow(1);
        cell = row.getCell(2);
        str = cell.getStringCellValue()
                .replace("code", StringUtils.trimToEmpty(dispatchCode));
        cell.setCellValue(str);

        cell = row.getCell(6);
        str = cell.getStringCellValue()
                .replace("pubTime", DateUtils.formatDate(scDispatch.getPubTime(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        row = sheet.getRow(2);
        cell = row.getCell(1);
        str = cell.getStringCellValue()
                .replace("title", "");
        cell.setCellValue(str);

        row = sheet.getRow(5);
        cell = row.getCell(1);
        str = cell.getStringCellValue()
                .replace("downloadTime", DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_CHINA));
        cell.setCellValue(str);

        String sign = null;
        SysUserView _user = ShiroHelper.getCurrentUser();
        if (FileUtils.exists(springProps.uploadPath + _user.getSign()))
            sign = springProps.uploadPath + _user.getSign();

        if(sign!=null) {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(new File(sign));
            ImageIO.write(bufferImg, "png", byteArrayOut);

            XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();
            //   XSSFClientAnchor的参数说明：
            //   参数   说明
            //  dx1  第1个单元格中x轴的偏移量
            //  dy1  第1个单元格中y轴的偏移量
            //  dx2     第2个单元格中x轴的偏移量
            //  dy2  第2个单元格中y轴的偏移量
            //  col1 第1个单元格的列号
            //  row1  第1个单元格的行号
            //  col2 第2个单元格的列号
            //  row2 第2个单元格的行号
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 90* Units.EMU_PER_PIXEL,
                    -20* Units.EMU_PER_PIXEL, -10* Units.EMU_PER_PIXEL, 3, 4, 5, 5);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
            drawingPatriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
        }

        return wb;
    }*/

    @Transactional
    public void sync(int dispatchId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Dispatch dispatch = dispatchMapper.selectByPrimaryKey(dispatchId);
       /*
        if(dispatch!=null && dispatch.getHasChecked()){
            return failed("已经复核，不可同步。");
        }*/

        {
            DispatchCadreExample example = new DispatchCadreExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            dispatchCadreMapper.deleteByExample(example);
        }

        {
            Dispatch record = new Dispatch();
            record.setId(dispatchId);
            record.setHasChecked(false);
            dispatchService.updateByPrimaryKeySelective(record, false);
        }

        Integer scDispatchId = dispatch.getScDispatchId();
        List<ScCommitteeVoteView> scDispatchVotes = iScMapper.getScDispatchVotes(scDispatchId);
        //Collections.reverse(scDispatchVotes); // 保持和文件起草签发中的顺序一致

        for (ScCommitteeVoteView sdv : scDispatchVotes) {

            DispatchCadre record = new DispatchCadre();
            record.setDispatchId(dispatchId);
            PropertyUtils.copyProperties(record, sdv);
            record.setId(null);
            dispatchCadreService.insertSelective(record);
        }
    }
}
