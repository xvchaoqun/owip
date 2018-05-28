package service.crs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import domain.crs.CrsApplicantWithBLOBs;
import domain.crs.CrsCandidate;
import domain.crs.CrsCandidateExample;
import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.CrsConstants;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CrsPostService extends BaseMapper {

    public int[] groupCount(int postId){

        int[] count = new int[]{-1, 0,0,0,0};
        List<Map> sta = iCrsMapper.applicantStatic(postId, CrsConstants.CRS_APPLICANT_STATUS_SUBMIT);
        for (Map entity : sta) {

            byte require_check_status = -1;
            if(entity.get("require_check_status")!=null){
                require_check_status = ((Integer) entity.get("require_check_status")).byteValue();
            }
            boolean is_require_check_pass = ((Integer) entity.get("is_require_check_pass")==1);
            boolean is_quit = BooleanUtils.isTrue((Boolean) entity.get("is_quit"));

            int num = ((Long) entity.get("num")).intValue();

            // cls==1
            if(is_quit==false) {
                if (require_check_status == CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_INIT) {
                    count[1] += num;
                }
                if (is_require_check_pass) {
                    count[2] += num;
                } else if (require_check_status == CrsConstants.CRS_APPLICANT_REQUIRE_CHECK_STATUS_UNPASS) {
                    count[3] += num;
                }
            }else{
                count[4] += num;
            }
        }
        return count;
    }

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

        int postId = statBean.getPostId();
        CrsPost cp = new CrsPost();
        cp.setId(postId);
        cp.setStatGiveCount(statBean.getStatGiveCount());
        cp.setStatBackCount(statBean.getStatBackCount());
        //cp.setStatExpertCount(statBean.getStatExpertCount());
        cp.setStatDate(statBean.getStatDate());
        cp.setStatFile(statFile);
        cp.setStatFileName(statFileName);
        crsPostMapper.updateByPrimaryKeySelective(cp);

        {
            CrsCandidateExample example = new CrsCandidateExample();
            example.createCriteria().andPostIdEqualTo(postId);
            crsCandidateMapper.deleteByExample(example);
            if (statBean.getFirstUserId() != null && statBean.getFirstUserId()>0) {
                CrsCandidate crsCandidate = new CrsCandidate();
                crsCandidate.setPostId(postId);
                crsCandidate.setUserId(statBean.getFirstUserId());
                crsCandidate.setIsFirst(true);
                crsCandidateMapper.insertSelective(crsCandidate);
            }
            if (statBean.getSecondUserId() != null && statBean.getSecondUserId()>0) {
                CrsCandidate crsCandidate = new CrsCandidate();
                crsCandidate.setPostId(postId);
                crsCandidate.setUserId(statBean.getSecondUserId());
                crsCandidate.setIsFirst(false);
                crsCandidateMapper.insertSelective(crsCandidate);
            }
        }


        List<CrsApplicatStatBean> applicatStatBeans = statBean.getApplicatStatBeans();
        if (applicatStatBeans != null) {
            for (CrsApplicatStatBean applicatStatBean : applicatStatBeans) {
                CrsApplicantWithBLOBs ca = new CrsApplicantWithBLOBs();
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

        record.setEnrollStatus(CrsConstants.CRS_POST_ENROLL_STATUS_DEFAULT);
        record.setCommitteeStatus(false);
        record.setCreateTime(new Date());
        record.setPubStatus(CrsConstants.CRS_POST_PUB_STATUS_UNPUBLISHED);
        record.setMeetingStatus(false);
        record.setStatus(CrsConstants.CRS_POST_STATUS_NORMAL);

        crsPostMapper.insertSelective(record);
    }

    // 假删除/作废
    @Transactional
    public void batchDel(Integer[] ids, byte status) {

        if (ids == null || ids.length == 0) return;

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        CrsPost record = new CrsPost();
        record.setStatus(status);
        crsPostMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void realDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andStatusEqualTo(CrsConstants.CRS_POST_STATUS_DELETE);

        crsPostMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CrsPost record) {

        return crsPostMapper.updateByPrimaryKeySelective(record);
    }
}
