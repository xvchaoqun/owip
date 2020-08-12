package service.cadre;

import domain.cadre.CadreInfo;
import domain.cadre.CadreInfoExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import service.BaseMapper;
import sys.utils.ContentUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CadreInfoService extends BaseMapper {

    @Transactional
    public void del(Integer cadreId){

        cadreInfoMapper.deleteByPrimaryKey(cadreId);
    }

    @Transactional
    public void batchDel(Integer[] cadreIds){

        if(cadreIds==null || cadreIds.length==0) return;

        CadreInfoExample example = new CadreInfoExample();
        example.createCriteria().andCadreIdIn(Arrays.asList(cadreIds));
        cadreInfoMapper.deleteByExample(example);
    }

    public String getTrimContent(int cadreId, byte type){

        CadreInfo cadreInfo = get(cadreId, type);
        if(cadreInfo==null) return null;
        String content = HtmlUtils.htmlUnescape(cadreInfo.getContent()); // 去掉 &nbsp;

        if(StringUtils.isBlank(Jsoup.parse(content).text())) return null; // 防止出现<p></p>和<br/>之类的文本

        return ContentUtils.trim(content);
    }

    public CadreInfo get(int cadreId, byte type){

        CadreInfoExample example = new CadreInfoExample();
        example.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type);

        CadreInfo cadreInfo = null;
        List<CadreInfo> cadreInfos = cadreInfoMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(cadreInfos.size()>0) cadreInfo = cadreInfos.get(0);

        return cadreInfo;
    }

    @Transactional
    public void insertOrUpdate(int cadreId, String content, byte type){

        CadreInfo record = new CadreInfo();
        record.setCadreId(cadreId);
        record.setContent(content);
        record.setLastSaveDate(new Date());
        record.setType(type);

        CadreInfo cadreInfo = get(cadreId, type);
        if (cadreInfo == null) {
            cadreInfoMapper.insertSelective(record);
        } else {
            record.setId(cadreInfo.getId());
            cadreInfoMapper.updateByPrimaryKeySelective(record);
        }
    }
}
