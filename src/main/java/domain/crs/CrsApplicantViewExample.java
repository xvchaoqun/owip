package domain.crs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrsApplicantViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrsApplicantViewExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNull() {
            addCriterion("post_id is null");
            return (Criteria) this;
        }

        public Criteria andPostIdIsNotNull() {
            addCriterion("post_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostIdEqualTo(Integer value) {
            addCriterion("post_id =", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotEqualTo(Integer value) {
            addCriterion("post_id <>", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThan(Integer value) {
            addCriterion("post_id >", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_id >=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThan(Integer value) {
            addCriterion("post_id <", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_id <=", value, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdIn(List<Integer> values) {
            addCriterion("post_id in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotIn(List<Integer> values) {
            addCriterion("post_id not in", values, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdBetween(Integer value1, Integer value2) {
            addCriterion("post_id between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_id not between", value1, value2, "postId");
            return (Criteria) this;
        }

        public Criteria andReportIsNull() {
            addCriterion("report is null");
            return (Criteria) this;
        }

        public Criteria andReportIsNotNull() {
            addCriterion("report is not null");
            return (Criteria) this;
        }

        public Criteria andReportEqualTo(String value) {
            addCriterion("report =", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotEqualTo(String value) {
            addCriterion("report <>", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportGreaterThan(String value) {
            addCriterion("report >", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportGreaterThanOrEqualTo(String value) {
            addCriterion("report >=", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportLessThan(String value) {
            addCriterion("report <", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportLessThanOrEqualTo(String value) {
            addCriterion("report <=", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportLike(String value) {
            addCriterion("report like", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotLike(String value) {
            addCriterion("report not like", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportIn(List<String> values) {
            addCriterion("report in", values, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotIn(List<String> values) {
            addCriterion("report not in", values, "report");
            return (Criteria) this;
        }

        public Criteria andReportBetween(String value1, String value2) {
            addCriterion("report between", value1, value2, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotBetween(String value1, String value2) {
            addCriterion("report not between", value1, value2, "report");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeIsNull() {
            addCriterion("enroll_time is null");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeIsNotNull() {
            addCriterion("enroll_time is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeEqualTo(Date value) {
            addCriterion("enroll_time =", value, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeNotEqualTo(Date value) {
            addCriterion("enroll_time <>", value, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeGreaterThan(Date value) {
            addCriterion("enroll_time >", value, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("enroll_time >=", value, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeLessThan(Date value) {
            addCriterion("enroll_time <", value, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeLessThanOrEqualTo(Date value) {
            addCriterion("enroll_time <=", value, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeIn(List<Date> values) {
            addCriterion("enroll_time in", values, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeNotIn(List<Date> values) {
            addCriterion("enroll_time not in", values, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeBetween(Date value1, Date value2) {
            addCriterion("enroll_time between", value1, value2, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andEnrollTimeNotBetween(Date value1, Date value2) {
            addCriterion("enroll_time not between", value1, value2, "enrollTime");
            return (Criteria) this;
        }

        public Criteria andIsQuitIsNull() {
            addCriterion("is_quit is null");
            return (Criteria) this;
        }

        public Criteria andIsQuitIsNotNull() {
            addCriterion("is_quit is not null");
            return (Criteria) this;
        }

        public Criteria andIsQuitEqualTo(Boolean value) {
            addCriterion("is_quit =", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotEqualTo(Boolean value) {
            addCriterion("is_quit <>", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitGreaterThan(Boolean value) {
            addCriterion("is_quit >", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_quit >=", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitLessThan(Boolean value) {
            addCriterion("is_quit <", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitLessThanOrEqualTo(Boolean value) {
            addCriterion("is_quit <=", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitIn(List<Boolean> values) {
            addCriterion("is_quit in", values, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotIn(List<Boolean> values) {
            addCriterion("is_quit not in", values, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitBetween(Boolean value1, Boolean value2) {
            addCriterion("is_quit between", value1, value2, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_quit not between", value1, value2, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsRecommendIsNull() {
            addCriterion("is_recommend is null");
            return (Criteria) this;
        }

        public Criteria andIsRecommendIsNotNull() {
            addCriterion("is_recommend is not null");
            return (Criteria) this;
        }

        public Criteria andIsRecommendEqualTo(Boolean value) {
            addCriterion("is_recommend =", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotEqualTo(Boolean value) {
            addCriterion("is_recommend <>", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendGreaterThan(Boolean value) {
            addCriterion("is_recommend >", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_recommend >=", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendLessThan(Boolean value) {
            addCriterion("is_recommend <", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendLessThanOrEqualTo(Boolean value) {
            addCriterion("is_recommend <=", value, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendIn(List<Boolean> values) {
            addCriterion("is_recommend in", values, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotIn(List<Boolean> values) {
            addCriterion("is_recommend not in", values, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendBetween(Boolean value1, Boolean value2) {
            addCriterion("is_recommend between", value1, value2, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andIsRecommendNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_recommend not between", value1, value2, "isRecommend");
            return (Criteria) this;
        }

        public Criteria andRecommendOwIsNull() {
            addCriterion("recommend_ow is null");
            return (Criteria) this;
        }

        public Criteria andRecommendOwIsNotNull() {
            addCriterion("recommend_ow is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendOwEqualTo(String value) {
            addCriterion("recommend_ow =", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwNotEqualTo(String value) {
            addCriterion("recommend_ow <>", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwGreaterThan(String value) {
            addCriterion("recommend_ow >", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwGreaterThanOrEqualTo(String value) {
            addCriterion("recommend_ow >=", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwLessThan(String value) {
            addCriterion("recommend_ow <", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwLessThanOrEqualTo(String value) {
            addCriterion("recommend_ow <=", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwLike(String value) {
            addCriterion("recommend_ow like", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwNotLike(String value) {
            addCriterion("recommend_ow not like", value, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwIn(List<String> values) {
            addCriterion("recommend_ow in", values, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwNotIn(List<String> values) {
            addCriterion("recommend_ow not in", values, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwBetween(String value1, String value2) {
            addCriterion("recommend_ow between", value1, value2, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendOwNotBetween(String value1, String value2) {
            addCriterion("recommend_ow not between", value1, value2, "recommendOw");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreIsNull() {
            addCriterion("recommend_cadre is null");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreIsNotNull() {
            addCriterion("recommend_cadre is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreEqualTo(String value) {
            addCriterion("recommend_cadre =", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreNotEqualTo(String value) {
            addCriterion("recommend_cadre <>", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreGreaterThan(String value) {
            addCriterion("recommend_cadre >", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreGreaterThanOrEqualTo(String value) {
            addCriterion("recommend_cadre >=", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreLessThan(String value) {
            addCriterion("recommend_cadre <", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreLessThanOrEqualTo(String value) {
            addCriterion("recommend_cadre <=", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreLike(String value) {
            addCriterion("recommend_cadre like", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreNotLike(String value) {
            addCriterion("recommend_cadre not like", value, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreIn(List<String> values) {
            addCriterion("recommend_cadre in", values, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreNotIn(List<String> values) {
            addCriterion("recommend_cadre not in", values, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreBetween(String value1, String value2) {
            addCriterion("recommend_cadre between", value1, value2, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCadreNotBetween(String value1, String value2) {
            addCriterion("recommend_cadre not between", value1, value2, "recommendCadre");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdIsNull() {
            addCriterion("recommend_crowd is null");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdIsNotNull() {
            addCriterion("recommend_crowd is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdEqualTo(String value) {
            addCriterion("recommend_crowd =", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdNotEqualTo(String value) {
            addCriterion("recommend_crowd <>", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdGreaterThan(String value) {
            addCriterion("recommend_crowd >", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdGreaterThanOrEqualTo(String value) {
            addCriterion("recommend_crowd >=", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdLessThan(String value) {
            addCriterion("recommend_crowd <", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdLessThanOrEqualTo(String value) {
            addCriterion("recommend_crowd <=", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdLike(String value) {
            addCriterion("recommend_crowd like", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdNotLike(String value) {
            addCriterion("recommend_crowd not like", value, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdIn(List<String> values) {
            addCriterion("recommend_crowd in", values, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdNotIn(List<String> values) {
            addCriterion("recommend_crowd not in", values, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdBetween(String value1, String value2) {
            addCriterion("recommend_crowd between", value1, value2, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendCrowdNotBetween(String value1, String value2) {
            addCriterion("recommend_crowd not between", value1, value2, "recommendCrowd");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfIsNull() {
            addCriterion("recommend_pdf is null");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfIsNotNull() {
            addCriterion("recommend_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfEqualTo(String value) {
            addCriterion("recommend_pdf =", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfNotEqualTo(String value) {
            addCriterion("recommend_pdf <>", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfGreaterThan(String value) {
            addCriterion("recommend_pdf >", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfGreaterThanOrEqualTo(String value) {
            addCriterion("recommend_pdf >=", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfLessThan(String value) {
            addCriterion("recommend_pdf <", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfLessThanOrEqualTo(String value) {
            addCriterion("recommend_pdf <=", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfLike(String value) {
            addCriterion("recommend_pdf like", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfNotLike(String value) {
            addCriterion("recommend_pdf not like", value, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfIn(List<String> values) {
            addCriterion("recommend_pdf in", values, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfNotIn(List<String> values) {
            addCriterion("recommend_pdf not in", values, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfBetween(String value1, String value2) {
            addCriterion("recommend_pdf between", value1, value2, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andRecommendPdfNotBetween(String value1, String value2) {
            addCriterion("recommend_pdf not between", value1, value2, "recommendPdf");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusIsNull() {
            addCriterion("info_check_status is null");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusIsNotNull() {
            addCriterion("info_check_status is not null");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusEqualTo(Byte value) {
            addCriterion("info_check_status =", value, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusNotEqualTo(Byte value) {
            addCriterion("info_check_status <>", value, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusGreaterThan(Byte value) {
            addCriterion("info_check_status >", value, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("info_check_status >=", value, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusLessThan(Byte value) {
            addCriterion("info_check_status <", value, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("info_check_status <=", value, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusIn(List<Byte> values) {
            addCriterion("info_check_status in", values, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusNotIn(List<Byte> values) {
            addCriterion("info_check_status not in", values, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("info_check_status between", value1, value2, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("info_check_status not between", value1, value2, "infoCheckStatus");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkIsNull() {
            addCriterion("info_check_remark is null");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkIsNotNull() {
            addCriterion("info_check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkEqualTo(String value) {
            addCriterion("info_check_remark =", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkNotEqualTo(String value) {
            addCriterion("info_check_remark <>", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkGreaterThan(String value) {
            addCriterion("info_check_remark >", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("info_check_remark >=", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkLessThan(String value) {
            addCriterion("info_check_remark <", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("info_check_remark <=", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkLike(String value) {
            addCriterion("info_check_remark like", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkNotLike(String value) {
            addCriterion("info_check_remark not like", value, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkIn(List<String> values) {
            addCriterion("info_check_remark in", values, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkNotIn(List<String> values) {
            addCriterion("info_check_remark not in", values, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkBetween(String value1, String value2) {
            addCriterion("info_check_remark between", value1, value2, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andInfoCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("info_check_remark not between", value1, value2, "infoCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusIsNull() {
            addCriterion("require_check_status is null");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusIsNotNull() {
            addCriterion("require_check_status is not null");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusEqualTo(Byte value) {
            addCriterion("require_check_status =", value, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusNotEqualTo(Byte value) {
            addCriterion("require_check_status <>", value, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusGreaterThan(Byte value) {
            addCriterion("require_check_status >", value, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("require_check_status >=", value, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusLessThan(Byte value) {
            addCriterion("require_check_status <", value, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("require_check_status <=", value, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusIn(List<Byte> values) {
            addCriterion("require_check_status in", values, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusNotIn(List<Byte> values) {
            addCriterion("require_check_status not in", values, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("require_check_status between", value1, value2, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("require_check_status not between", value1, value2, "requireCheckStatus");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkIsNull() {
            addCriterion("require_check_remark is null");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkIsNotNull() {
            addCriterion("require_check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkEqualTo(String value) {
            addCriterion("require_check_remark =", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkNotEqualTo(String value) {
            addCriterion("require_check_remark <>", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkGreaterThan(String value) {
            addCriterion("require_check_remark >", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("require_check_remark >=", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkLessThan(String value) {
            addCriterion("require_check_remark <", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("require_check_remark <=", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkLike(String value) {
            addCriterion("require_check_remark like", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkNotLike(String value) {
            addCriterion("require_check_remark not like", value, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkIn(List<String> values) {
            addCriterion("require_check_remark in", values, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkNotIn(List<String> values) {
            addCriterion("require_check_remark not in", values, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkBetween(String value1, String value2) {
            addCriterion("require_check_remark between", value1, value2, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andRequireCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("require_check_remark not between", value1, value2, "requireCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusIsNull() {
            addCriterion("special_status is null");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusIsNotNull() {
            addCriterion("special_status is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusEqualTo(Boolean value) {
            addCriterion("special_status =", value, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusNotEqualTo(Boolean value) {
            addCriterion("special_status <>", value, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusGreaterThan(Boolean value) {
            addCriterion("special_status >", value, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("special_status >=", value, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusLessThan(Boolean value) {
            addCriterion("special_status <", value, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("special_status <=", value, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusIn(List<Boolean> values) {
            addCriterion("special_status in", values, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusNotIn(List<Boolean> values) {
            addCriterion("special_status not in", values, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("special_status between", value1, value2, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("special_status not between", value1, value2, "specialStatus");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfIsNull() {
            addCriterion("special_pdf is null");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfIsNotNull() {
            addCriterion("special_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfEqualTo(String value) {
            addCriterion("special_pdf =", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfNotEqualTo(String value) {
            addCriterion("special_pdf <>", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfGreaterThan(String value) {
            addCriterion("special_pdf >", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfGreaterThanOrEqualTo(String value) {
            addCriterion("special_pdf >=", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfLessThan(String value) {
            addCriterion("special_pdf <", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfLessThanOrEqualTo(String value) {
            addCriterion("special_pdf <=", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfLike(String value) {
            addCriterion("special_pdf like", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfNotLike(String value) {
            addCriterion("special_pdf not like", value, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfIn(List<String> values) {
            addCriterion("special_pdf in", values, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfNotIn(List<String> values) {
            addCriterion("special_pdf not in", values, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfBetween(String value1, String value2) {
            addCriterion("special_pdf between", value1, value2, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialPdfNotBetween(String value1, String value2) {
            addCriterion("special_pdf not between", value1, value2, "specialPdf");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkIsNull() {
            addCriterion("special_remark is null");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkIsNotNull() {
            addCriterion("special_remark is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkEqualTo(String value) {
            addCriterion("special_remark =", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkNotEqualTo(String value) {
            addCriterion("special_remark <>", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkGreaterThan(String value) {
            addCriterion("special_remark >", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("special_remark >=", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkLessThan(String value) {
            addCriterion("special_remark <", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkLessThanOrEqualTo(String value) {
            addCriterion("special_remark <=", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkLike(String value) {
            addCriterion("special_remark like", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkNotLike(String value) {
            addCriterion("special_remark not like", value, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkIn(List<String> values) {
            addCriterion("special_remark in", values, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkNotIn(List<String> values) {
            addCriterion("special_remark not in", values, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkBetween(String value1, String value2) {
            addCriterion("special_remark between", value1, value2, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andSpecialRemarkNotBetween(String value1, String value2) {
            addCriterion("special_remark not between", value1, value2, "specialRemark");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountIsNull() {
            addCriterion("recommend_first_count is null");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountIsNotNull() {
            addCriterion("recommend_first_count is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountEqualTo(Integer value) {
            addCriterion("recommend_first_count =", value, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountNotEqualTo(Integer value) {
            addCriterion("recommend_first_count <>", value, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountGreaterThan(Integer value) {
            addCriterion("recommend_first_count >", value, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend_first_count >=", value, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountLessThan(Integer value) {
            addCriterion("recommend_first_count <", value, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountLessThanOrEqualTo(Integer value) {
            addCriterion("recommend_first_count <=", value, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountIn(List<Integer> values) {
            addCriterion("recommend_first_count in", values, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountNotIn(List<Integer> values) {
            addCriterion("recommend_first_count not in", values, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountBetween(Integer value1, Integer value2) {
            addCriterion("recommend_first_count between", value1, value2, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendFirstCountNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend_first_count not between", value1, value2, "recommendFirstCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountIsNull() {
            addCriterion("recommend_second_count is null");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountIsNotNull() {
            addCriterion("recommend_second_count is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountEqualTo(Integer value) {
            addCriterion("recommend_second_count =", value, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountNotEqualTo(Integer value) {
            addCriterion("recommend_second_count <>", value, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountGreaterThan(Integer value) {
            addCriterion("recommend_second_count >", value, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend_second_count >=", value, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountLessThan(Integer value) {
            addCriterion("recommend_second_count <", value, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountLessThanOrEqualTo(Integer value) {
            addCriterion("recommend_second_count <=", value, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountIn(List<Integer> values) {
            addCriterion("recommend_second_count in", values, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountNotIn(List<Integer> values) {
            addCriterion("recommend_second_count not in", values, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountBetween(Integer value1, Integer value2) {
            addCriterion("recommend_second_count between", value1, value2, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andRecommendSecondCountNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend_second_count not between", value1, value2, "recommendSecondCount");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassIsNull() {
            addCriterion("is_require_check_pass is null");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassIsNotNull() {
            addCriterion("is_require_check_pass is not null");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassEqualTo(Boolean value) {
            addCriterion("is_require_check_pass =", value, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassNotEqualTo(Boolean value) {
            addCriterion("is_require_check_pass <>", value, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassGreaterThan(Boolean value) {
            addCriterion("is_require_check_pass >", value, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_require_check_pass >=", value, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassLessThan(Boolean value) {
            addCriterion("is_require_check_pass <", value, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassLessThanOrEqualTo(Boolean value) {
            addCriterion("is_require_check_pass <=", value, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassIn(List<Boolean> values) {
            addCriterion("is_require_check_pass in", values, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassNotIn(List<Boolean> values) {
            addCriterion("is_require_check_pass not in", values, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassBetween(Boolean value1, Boolean value2) {
            addCriterion("is_require_check_pass between", value1, value2, "isRequireCheckPass");
            return (Criteria) this;
        }

        public Criteria andIsRequireCheckPassNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_require_check_pass not between", value1, value2, "isRequireCheckPass");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}