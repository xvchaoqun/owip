package service.pcs;

import controller.global.OpException;
import domain.pcs.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.tool.qrcode.QRCodeUtil;
import sys.utils.ExportHelper;
import sys.utils.MSUtils;
import sys.utils.RequestUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class PcsPollInspectorService extends PcsBaseMapper {

    @Autowired
    PcsConfigService pcsConfigService;

    // 检测账号的投票权限
    public void checkPollStatus(int inspectorId){

        PcsPollInspector inspector = pcsPollInspectorMapper.selectByPrimaryKey(inspectorId);
        int pollId = inspector.getPollId();
        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if (pcsConfig == null || pcsPoll.getConfigId() != pcsConfig.getId()
                || pcsPoll==null || pcsPoll.getIsDeleted() || pcsPoll.getHasReport()){
            throw new OpException("投票已过期");
        }else if (pcsPoll.getStartTime().after(new Date())){
            throw new OpException("投票未开始");
        }else if (pcsPoll.getEndTime().before(new Date())){
            throw new OpException("投票已结束");
        }else if (inspector.getIsFinished()){
            throw new OpException("该账号已完成投票");
        }
    }

    @Transactional
    public void insertSelective(PcsPollInspector record){

        pcsPollInspectorMapper.insertSelective(record);

        iPcsMapper.updatePollInspectorCount(record.getPollId());
    }

    @Transactional
    public void batchDel(Integer[] ids, Integer pollId){

        if(ids==null || ids.length==0) return;

        PcsPollResultExample example1 = new PcsPollResultExample();
        example1.createCriteria().andInspectorIdIn(Arrays.asList(ids));
        pcsPollResultMapper.deleteByExample(example1);

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pcsPollInspectorMapper.deleteByExample(example);

        iPcsMapper.updatePollInspectorCount(pollId);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PcsPollInspector record){

        pcsPollInspectorMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, PcsPollInspector> findAll() {

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PcsPollInspector> records = pcsPollInspectorMapper.selectByExample(example);
        Map<Integer, PcsPollInspector> map = new LinkedHashMap<>();
        for (PcsPollInspector record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public void genInspector(Integer pollId, Integer count) {

        PcsPoll pcsPoll = pcsPollMapper.selectByPrimaryKey(pollId);

        for (Integer i = 0; i < count; i++) {
            PcsPollInspector record = new PcsPollInspector();
            record.setPollId(pollId);
            record.setUsername(buildUsername());
            record.setPasswd(RandomStringUtils.randomNumeric(6));
            record.setPartyId(pcsPoll.getPartyId());
            record.setBranchId(pcsPoll.getBranchId());
            record.setCreateTime(new Date());

            pcsPollInspectorMapper.insertSelective(record);
        }

        iPcsMapper.updatePollInspectorCount(pollId);

    }

    private String buildUsername() {

        PcsPollInspectorExample example = null;
        String username = null;

        do{
            username = RandomStringUtils.random(6, "abcdefghijkmnpqrstuvwxy")
                + RandomStringUtils.random(2, "23456789");

            example = new PcsPollInspectorExample();
            example.createCriteria().andUsernameEqualTo(username);

        }while (pcsPollInspectorMapper.countByExample(example) > 0);

        return username;
    }

    public PcsPollInspector tryLogin(String username, String passwd) {

        username = StringUtils.trimToNull(username);
        passwd = StringUtils.trimToNull(passwd);

        PcsPollInspectorExample example = new PcsPollInspectorExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswdEqualTo(passwd);
        List<PcsPollInspector> inspectors = pcsPollInspectorMapper.selectByExample(example);

        return inspectors.size() > 0 ? inspectors.get(0) : null;
    }

    //将二维码在excel中导出
    public void export(String[] titles,
                       List<String[]> valuesList,
                       String fileName,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {

        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        SXSSFSheet sheet = (SXSSFSheet) ExportHelper.createSafeSheet(wb, fileName);

        //excel行高列宽像素换算1unit=1.3651px=0.03612cm
        sheet.setDefaultRowHeightInPoints(50);//excel// 行高
        Row firstRow = sheet.createRow(0);

        // 创建表头样式
        CellStyle cellStyle = MSUtils.getHeadStyle2(wb);

        String[] aligns = new String[titles.length];
        int width;
        for (int i = 0; i < titles.length; i++) {

            String _title = titles[i];
            String[] split = _title.split("\\|");
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(split[0]);

            cell.setCellStyle(cellStyle);

            if (split.length > 1) {
                try {
                    width = Integer.valueOf(split[1]);
                    sheet.setColumnWidth(i, (short) (35.7 * width));
                } catch (Exception e) {
                    throw new OpException("export error");
                }
            }
            if (split.length > 2) {
                aligns[i] = split[2];
            } else {
                aligns[i] = null;
            }
        }
        CellStyle centerCellStyle = ExportHelper.createCenterCellStyle(wb);
        CellStyle leftCellStyle = ExportHelper.createCenterCellStyle(wb);
        CellStyle rightCellStyle = ExportHelper.createCenterCellStyle(wb);

        for (int i = 0; i < valuesList.size(); i++) {

            String[] values = valuesList.get(i);
            Row row = sheet.createRow(i + 1);

            //生成、设置二维码
            String content = RequestUtils.getHomeURL(request) + "/pcs/login?u=" + values[0] + "&p=" + values[1];
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();//读进图片
            BufferedImage bufferImg = QRCodeUtil.createImage(content, null, true);
            ImageIO.write(bufferImg, "png", byteArrayOut);

            SXSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();
            //调整图片的大小（坐标参数比较大），然后把图片插入excel中
            XSSFClientAnchor anchor = new XSSFClientAnchor(4* Units.EMU_PER_PIXEL, 4* Units.EMU_PER_PIXEL,
                    -4* Units.EMU_PER_PIXEL,  -4* Units.EMU_PER_PIXEL, titles.length-1, i+1, titles.length, i+2);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
            drawingPatriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));

            //插入数据
            for (int j = 0; j < titles.length-1; j++) {

                Cell cell = row.createCell(j);
                cell.setCellValue(values[j]);

                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(aligns[j], "left"))
                    cell.setCellStyle(leftCellStyle);
                else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(aligns[j], "right"))
                    cell.setCellStyle(rightCellStyle);
                else
                    cell.setCellStyle(centerCellStyle);
            }
        }

        ExportHelper.output(wb, fileName + ".xlsx", request, response);

    }
}
