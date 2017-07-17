package freemarker;

import freemarker.template.TemplateException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sys.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by fafa on 2016/10/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Word2JPG {

    @Test
    public void t() throws IOException, TemplateException {

        int cadreId = 13;

        String folder = "D:/tmp/";
        /*String path = folder + cadreId + ".xml";
        File file = new File(path);
        if(!new File(folder).exists())
            new File(folder).mkdirs();

        Writer out = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
        cadreTableService.process(cadreId, out);
        out.flush();
        out.close();*/
        String path = folder + "/11.docx";
        FileUtils.word2pdf(path, folder + cadreId + ".pdf");

        try {
            PDDocument doc = PDDocument.load(new File(folder + cadreId + ".pdf"));
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();

            for(int i=0;i<pageCount;i++){
                BufferedImage image = renderer.renderImageWithDPI(i, 100);
//          BufferedImage image = renderer.renderImage(i, 2.5f);
                ImageIO.write(image, "PNG", new File(folder + cadreId + "/" + i +".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
