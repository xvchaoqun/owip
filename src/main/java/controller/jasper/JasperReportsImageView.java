package controller.jasper;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

public class JasperReportsImageView extends AbstractJasperReportsSingleFormatView {
    public JasperReportsImageView() {
        this.setContentType("image/jpeg");
    }

    protected void renderReport(JasperPrint jasperPrint, Map<String, Object> model, HttpServletResponse response) throws Exception {

        JRGraphics2DExporter exporter = new JRGraphics2DExporter();//创建graphics输出器
        //创建一个影像对象
        BufferedImage bufferedImage = new BufferedImage(jasperPrint.getPageWidth(),
                jasperPrint.getPageHeight(), BufferedImage.TYPE_INT_RGB);
        //取graphics
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        //设置相应参数信息
        exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g);
        //exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, 0.2f);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.exportReport();
        g.dispose();//释放资源信息

        //这里的bufferedImage就是最终的影像图像信息,可以通过这个对象导入到cm中了.
        ImageIO.write(Thumbnails.of(bufferedImage).scale(0.25f).asBufferedImage(), "JPEG", response.getOutputStream());
    }

    @Override
    protected JRExporter createExporter() {
        return null;
    }

    @Override
    protected boolean useWriter() {
        return false;
    }

}