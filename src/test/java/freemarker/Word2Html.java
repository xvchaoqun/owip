package freemarker; /**
 * 
 */


import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author: Chembo Huang
 * @since: May 3, 2012
 * @modified: May 3, 2012
 * @version:
 */
public class Word2Html {

	//public static String ENCODING = "GB2312";
	public static String ENCODING = "UTF-8";
	public static void main(String argv[]) {
		/*try {
			convert2Html("D://tmp//【干部信息采集表2015版】样表.doc","D://tmp/【干部信息采集表2015版】样表.html");
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		/*try {
			convert2Html("E:\\软件开发\\寒假\\培训\\专题班评估表.doc","E:\\软件开发\\寒假\\培训\\专题班评估表.html");
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		try {
			convert2Html("C:\\Users\\fafa\\Desktop\\需求\\2017.05.18 干部职数管理模块--附件（北京师范大学内设机构干部配备情况）.xlsx","E:\\软件开发\\寒假\\培训\\专题班评估表.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String content, String path) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos,ENCODING));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

	public static void convert2Html(String fileName, String outPutFile)
			throws TransformerException, IOException,
			ParserConfigurationException {
		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());
		 wordToHtmlConverter.setPicturesManager( new PicturesManager()
         {
             public String savePicture( byte[] content,
                     PictureType pictureType, String suggestedName,
                     float widthInches, float heightInches )
             {
                 return "test/"+suggestedName;
             }
         } );
		wordToHtmlConverter.processDocument(wordDocument);
		//save pictures
		List pics=wordDocument.getPicturesTable().getAllPictures();
		if(pics!=null){
			for(int i=0;i<pics.size();i++){
				Picture pic = (Picture)pics.get(i);
				System.out.println();
				try {
					pic.writeImageContent(new FileOutputStream("D:/test/"
							+ pic.suggestFullFileName()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}  
			}
		}
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		writeFile(new String(out.toByteArray()), outPutFile);
	}
}
