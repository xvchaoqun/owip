package service.dr;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import persistence.dr.common.DrTempResult;

import java.io.StringWriter;

@Service
public class DrCommonService extends DrBaseMapper{

    //转换暂存票数
    public DrTempResult getTempResult(String tempData){

        DrTempResult tempResult = null;

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", DrTempResult.class);

        if (StringUtils.isNotBlank(tempData)){
            tempResult = (DrTempResult) xStream.fromXML(tempData);
        }

        tempResult = (tempResult == null) ? new DrTempResult() : tempResult;

        return tempResult;
    }

    public String getStringTemp(DrTempResult record){

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", DrTempResult.class);

        StringWriter sw = new StringWriter();
        xStream.marshal(record, new CompactWriter(sw));
        return sw.toString();
    }
}
