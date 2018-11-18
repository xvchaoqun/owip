/**
 * @license Highcharts JS v2.3.3 (2012-11-02)
 *
 * (c) 20012-2014
 *
 * Author: Gert Vaartjes
 *
 * License: www.highcharts.com/license
 */
package controller.global;

import com.highcharts.export.util.MimeType;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.svg.PDFTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;

@Controller
public class ExportController extends HttpServlet {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/export", method = {RequestMethod.POST, RequestMethod.GET})
    public void export(
            @RequestParam(value = "svg", required = false) String svg,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "filename", required = false) String filename,
            @RequestParam(value = "width", required = false) String width,
            @RequestParam(value = "scale", required = false) String scale,
            @RequestParam(value = "options", required = false) String options,
            @RequestParam(value = "globaloptions", required = false) String globalOptions,
            @RequestParam(value = "constr", required = false) String constructor,
            @RequestParam(value = "callback", required = false) String callback,
            @RequestParam(value = "callbackHC", required = false) String callbackHC,
            @RequestParam(value = "async", required = false, defaultValue = "false") Boolean async,
            @RequestParam(value = "jsonp", required = false, defaultValue = "false") Boolean jsonp,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws IOException {

        request.setCharacterEncoding("utf-8");//注意编码
        ServletOutputStream out = response.getOutputStream();
        if (null != type && null != svg) {
            // This line is necessary due to a bug in the highcharts SVG generator for IE
            // I'm guessing it wont be needed later.
            svg = svg.replaceAll(":rect", "rect");
            String ext = "";
            Transcoder t = null;

            if (type.equals("image/png")) {
                ext = "png";
                t = new PNGTranscoder();

            } else if (type.equals("image/jpeg")) {
                ext = "jpg";
                t = new JPEGTranscoder();

            } else if (type.equals("application/pdf")) {
                ext = "pdf";
                t = new PDFTranscoder();

            } else if (type.equals("image/svg+xml")) {
                ext = "svg";
            }

            filename = getFilename(filename);
            filename = URLEncoder.encode(filename, "UTF-8");

            response.addHeader("Content-Disposition", "attachment; filename=" + filename.replace(" ", "_") + "." + ext);
            response.addHeader("Content-Type", type);

            if (null != t) {
                TranscoderInput input = new TranscoderInput(new StringReader(svg));
                TranscoderOutput output = new TranscoderOutput(out);
                try {
                    t.transcode(input, output);
                } catch (TranscoderException e) {
                    //out.print("Problem transcoding stream. See the web logs for more details.");
                    //e.printStackTrace();
                    logger.info("Problem transcoding stream. See the web logs for more details." + e.getMessage());
                }
            } else if (ext == "svg") {
                logger.info(svg);
            } else {
                logger.info("Invalid type: " + type);
            }
        } else {
            response.addHeader("Content-Type", "text/html");
            logger.info("Usage:\n\tParameter [svg]: The DOM Element to be converted.\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");
        }
        out.flush();
        out.close();
    }

    private String getFilename(String name) {
        name = sanitize(name);
        return (name != null) ? name : "chart";
    }

    private static MimeType getMime(String mime) {
        MimeType type = MimeType.get(mime);
        if (type != null) {
            return type;
        }
        return MimeType.PNG;
    }

    private static String sanitize(String parameter) {
        if (StringUtils.isBlank(parameter)
                || parameter.compareToIgnoreCase("undefined") == 0
                || parameter.compareTo("null") == 0
                || parameter.compareTo("{}") == 0) {
            return null;
        }
        return parameter.trim();
    }
}
