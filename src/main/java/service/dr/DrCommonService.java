package service.dr;

import bean.TempResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class DrCommonService {

    public TempResult getTempResult(String tempData){

        TempResult tempResult = null;

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("tempResult", TempResult.class);

        if (StringUtils.isNotBlank(tempData)){
            tempResult = (TempResult) xStream.fromXML(tempData);
        }

        tempResult = (tempResult == null) ? new TempResult() : tempResult;

        return tempResult;
    }

}
