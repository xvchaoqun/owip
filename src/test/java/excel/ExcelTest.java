package excel;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import sys.utils.ExcelUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/2/10.
 */
public class ExcelTest {

    @Test
    public void t() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/sc/sc_public_sign.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg = ImageIO.read(new File("E:\\廖敏工作文件夹\\电子签名\\廖敏.png"));
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
                -57* Units.EMU_PER_PIXEL, -10* Units.EMU_PER_PIXEL, 3, 4, 5, 5);
        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
        drawingPatriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));

        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test.xlsx"));  //读取的文件路径

        wb.write(output);
        output.close();
    }
    @Test
    public void t2() throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cet/1.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        //XSSFSheet sheet = wb.getSheetAt(0);


        FileOutputStream output = new FileOutputStream(new File("D:/tmp/test2.xlsx"));  //读取的文件路径

        wb.write(output);
        output.close();
    }
    @Test
    public void t3() throws IOException {

        InputStream is = new FileInputStream(new File("C:\\Users\\lm\\Desktop\\党员信息表-特殊党员库.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<Map<Integer, String>> xlsRows = ExcelUtils.getRowData(sheet);

        for (Map<Integer, String> xlsRow : xlsRows) {
            System.out.println(xlsRow);
        }
    }
}
