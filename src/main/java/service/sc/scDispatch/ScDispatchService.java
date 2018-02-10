package service.sc.scDispatch;

import domain.sc.scCommittee.ScCommitteeVote;
import domain.sc.scDispatch.ScDispatch;
import domain.sc.scDispatch.ScDispatchCommittee;
import domain.sc.scDispatch.ScDispatchCommitteeExample;
import domain.sc.scDispatch.ScDispatchExample;
import domain.sc.scDispatch.ScDispatchUser;
import domain.sc.scDispatch.ScDispatchUserExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.SpringProps;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ScDispatchService extends BaseMapper {

    @Autowired
    protected SpringProps springProps;

    public boolean idDuplicate(Integer id, int year, int code) {

        ScDispatchExample example = new ScDispatchExample();
        ScDispatchExample.Criteria criteria = example.createCriteria().andYearEqualTo(year)
                .andCodeEqualTo(code);
        if (id != null) criteria.andIdNotEqualTo(id);

        return scDispatchMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(ScDispatch record, Integer[] committeeIds, Integer[] voteIds) {

        if (record.getYear() != null && record.getCode() != null)
            Assert.isTrue(!idDuplicate(null, record.getYear(), record.getCode()), "编号重复");

        scDispatchMapper.insertSelective(record);

        processCommittee(record.getId(), committeeIds, voteIds);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;
        List<Integer> dispatchIdList = Arrays.asList(ids);
        {
            // 删除关联常委会
            ScDispatchCommitteeExample example = new ScDispatchCommitteeExample();
            example.createCriteria().andDispatchIdIn(dispatchIdList);
            scDispatchCommitteeMapper.deleteByExample(example);
        }
        {
            // 删除关联任免对象
            ScDispatchUserExample example = new ScDispatchUserExample();
            example.createCriteria().andDispatchIdIn(dispatchIdList);
            scDispatchUserMapper.deleteByExample(example);
        }
        {
            ScDispatchExample example = new ScDispatchExample();
            example.createCriteria().andIdIn(dispatchIdList);
            scDispatchMapper.deleteByExample(example);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(ScDispatch record, Integer[] committeeIds, Integer[] voteIds) {

        if (record.getYear() != null && record.getCode() != null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getYear(), record.getCode()), "编号重复");
        scDispatchMapper.updateByPrimaryKeySelective(record);

        processCommittee(record.getId(), committeeIds, voteIds);
    }

    private void processCommittee(int dispatchId, Integer[] committeeIds, Integer[] voteIds) {

        {
            ScDispatchCommitteeExample example = new ScDispatchCommitteeExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            scDispatchCommitteeMapper.deleteByExample(example);
        }
        for (Integer committeeId : committeeIds) {
            ScDispatchCommittee record = new ScDispatchCommittee();
            record.setDispatchId(dispatchId);
            record.setCommitteeId(committeeId);

            scDispatchCommitteeMapper.insertSelective(record);
        }

        {
            ScDispatchUserExample example = new ScDispatchUserExample();
            example.createCriteria().andDispatchIdEqualTo(dispatchId);
            scDispatchUserMapper.deleteByExample(example);
        }

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

    /**
     * 签发单.xls
     */
    public XSSFWorkbook exportSign(int dispatchId) throws IOException {

        ScDispatch scDispatch = scDispatchMapper.selectByPrimaryKey(dispatchId);
        String dispatchCode = CmTag.getDispatchCode(scDispatch.getCode(), scDispatch.getDispatchTypeId(), scDispatch.getYear());

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/sc/sc_public_sign.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        //wb.setSheetName(0, dispatchCode);
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = sheet.getRow(1);
        XSSFCell cell = row.getCell(2);
        String str = cell.getStringCellValue()
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
    }

}
