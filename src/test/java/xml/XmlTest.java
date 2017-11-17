package xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by lm on 2018/4/15.
 */
public class XmlTest {

    @Test
    public void dom4j() throws FileNotFoundException, DocumentException {

        SAXReader reader = new SAXReader();
        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xml/pmd/main_post.xml"));
        Document document = reader.read(is);

        List<Node> nodeList = document.selectNodes( "//main-post-list/main-post" );
        for (Node node : nodeList) {

            String mainPost = node.valueOf("@name");
            System.out.println(mainPost + ":" + node.getText());
        }
    }
}
