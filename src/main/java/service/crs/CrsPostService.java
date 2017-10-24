package service.crs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import domain.crs.CrsApplicant;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CrsPostService extends BaseMapper {

    @Transactional
    public void stat(String jsonResult, String statFile, String statFileName) {

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        }).registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {

            @Override
            public Integer deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {

                String str = StringUtils.trimToNull(json.getAsString());
                if (str == null) return 0;
                try {
                    return Integer.valueOf(str);
                } catch (Exception e) {
                    return 0;
                }
            }
        }).create();
        CrsPostStatBean statBean = gson.fromJson(jsonResult, CrsPostStatBean.class);

        CrsPost cp = new CrsPost();
        cp.setId(statBean.getPostId());
        cp.setStatGiveCount(statBean.getStatGiveCount());
        cp.setStatBackCount(statBean.getStatBackCount());
        //cp.setStatExpertCount(statBean.getStatExpertCount());
        cp.setStatDate(statBean.getStatDate());
        cp.setStatFirstUserId(statBean.getStatFirstUserId());
        cp.setStatSecondUserId(statBean.getStatSecondUserId());
        cp.setStatFile(statFile);
        cp.setStatFileName(statFileName);
        crsPostMapper.updateByPrimaryKeySelective(cp);

        List<CrsApplicatStatBean> applicatStatBeans = statBean.getApplicatStatBeans();
        if (applicatStatBeans != null) {
            for (CrsApplicatStatBean applicatStatBean : applicatStatBeans) {
                CrsApplicant ca = new CrsApplicant();
                ca.setId(applicatStatBean.getApplicantId());
                ca.setRecommendFirstCount(applicatStatBean.getFirstCount());
                ca.setRecommendSecondCount(applicatStatBean.getSecondCount());

                crsApplicantMapper.updateByPrimaryKeySelective(ca);
            }
        }
    }

    public CrsPost get(int id) {

        return crsPostMapper.selectByPrimaryKey(id);
    }

    public List<CrsPost> get(List<Integer> ids) {

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(ids);
        return crsPostMapper.selectByExample(example);
    }

    // 生成编号
    public int genSeq(byte type, int year) {

        int seq;
        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andYearEqualTo(year).andTypeEqualTo(type);
        example.setOrderByClause("seq desc");
        List<CrsPost> records = crsPostMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (records.size() > 0) {
            seq = records.get(0).getSeq() + 1;
        } else {
            seq = 1;
        }

        return seq;
    }

    @Transactional
    public void insertSelective(CrsPost record) {

        if (record.getSeq() == null) {
            record.setSeq(genSeq(record.getType(), record.getYear()));
        }

        record.setEnrollStatus(SystemConstants.CRS_POST_ENROLL_STATUS_DEFAULT);
        record.setCommitteeStatus(false);
        record.setCreateTime(new Date());
        record.setPubStatus(SystemConstants.CRS_POST_PUB_STATUS_UNPUBLISHED);
        record.setMeetingStatus(false);
        record.setStatus(SystemConstants.CRS_POST_STATUS_NORMAL);

        crsPostMapper.insertSelective(record);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        CrsPost record = new CrsPost();
        record.setStatus(SystemConstants.CRS_POST_STATUS_DELETE);
        crsPostMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void realDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(SystemConstants.CRS_POST_STATUS_DELETE);

        crsPostMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsPost record) {

        return crsPostMapper.updateByPrimaryKeySelective(record);
    }
}
