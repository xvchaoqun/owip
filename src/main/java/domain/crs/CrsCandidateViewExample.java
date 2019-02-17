package domain.crs;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class CrsCandidateViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrsCandidateViewExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andCandidateIdIsNull() {
            addCriterion("candidate_id is null");
            return (Criteria) this;
        }

        public Criteria andCandidateIdIsNotNull() {
            addCriterion("candidate_id is not null");
            return (Criteria) this;
        }

        public Criteria andCandidateIdEqualTo(Integer value) {
            addCriterion("candidate_id =", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdNotEqualTo(Integer value) {
            addCriterion("candidate_id <>", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdGreaterThan(Integer value) {
            addCriterion("candidate_id >", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("candidate_id >=", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdLessThan(Integer value) {
            addCriterion("candidate_id <", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdLessThanOrEqualTo(Integer value) {
            addCriterion("candidate_id <=", value, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdIn(List<Integer> values) {
            addCriterion("candidate_id in", values, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdNotIn(List<Integer> values) {
            addCriterion("candidate_id not in", values, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdBetween(Integer value1, Integer value2) {
            addCriterion("candidate_id between", value1, value2, "candidateId");
            return (Criteria) this;
        }

        public Criteria andCandidateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("candidate_id not between", value1, value2, "candidateId");
            return (Criteria) this;
        }

        public Criteria andIsFirstIsNull() {
            addCriterion("is_first is null");
            return (Criteria) this;
        }

        public Criteria andIsFirstIsNotNull() {
            addCriterion("is_first is not null");
            return (Criteria) this;
        }

        public Criteria andIsFirstEqualTo(Boolean value) {
            addCriterion("is_first =", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstNotEqualTo(Boolean value) {
            addCriterion("is_first <>", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstGreaterThan(Boolean value) {
            addCriterion("is_first >", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_first >=", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstLessThan(Boolean value) {
            addCriterion("is_first <", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstLessThanOrEqualTo(Boolean value) {
            addCriterion("is_first <=", value, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstIn(List<Boolean> values) {
            addCriterion("is_first in", values, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstNotIn(List<Boolean> values) {
            addCriterion("is_first not in", values, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstBetween(Boolean value1, Boolean value2) {
            addCriterion("is_first between", value1, value2, "isFirst");
            return (Criteria) this;
        }

        public Criteria andIsFirstNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_first not between", value1, value2, "isFirst");
            return (Criteria) this;
        }

        public Criteria andExpertCountIsNull() {
            addCriterion("expert_count is null");
            return (Criteria) this;
        }

        public Criteria andExpertCountIsNotNull() {
            addCriterion("expert_count is not null");
            return (Criteria) this;
        }

        public Criteria andExpertCountEqualTo(Long value) {
            addCriterion("expert_count =", value, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountNotEqualTo(Long value) {
            addCriterion("expert_count <>", value, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountGreaterThan(Long value) {
            addCriterion("expert_count >", value, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountGreaterThanOrEqualTo(Long value) {
            addCriterion("expert_count >=", value, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountLessThan(Long value) {
            addCriterion("expert_count <", value, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountLessThanOrEqualTo(Long value) {
            addCriterion("expert_count <=", value, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountIn(List<Long> values) {
            addCriterion("expert_count in", values, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountNotIn(List<Long> values) {
            addCriterion("expert_count not in", values, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountBetween(Long value1, Long value2) {
            addCriterion("expert_count between", value1, value2, "expertCount");
            return (Criteria) this;
        }

        public Criteria andExpertCountNotBetween(Long value1, Long value2) {
            addCriterion("expert_count not between", value1, value2, "expertCount");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdIsNull() {
            addCriterion("crs_post_id is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdIsNotNull() {
            addCriterion("crs_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdEqualTo(Integer value) {
            addCriterion("crs_post_id =", value, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdNotEqualTo(Integer value) {
            addCriterion("crs_post_id <>", value, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdGreaterThan(Integer value) {
            addCriterion("crs_post_id >", value, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("crs_post_id >=", value, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdLessThan(Integer value) {
            addCriterion("crs_post_id <", value, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("crs_post_id <=", value, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdIn(List<Integer> values) {
            addCriterion("crs_post_id in", values, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdNotIn(List<Integer> values) {
            addCriterion("crs_post_id not in", values, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdBetween(Integer value1, Integer value2) {
            addCriterion("crs_post_id between", value1, value2, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("crs_post_id not between", value1, value2, "crsPostId");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeIsNull() {
            addCriterion("crs_post_type is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeIsNotNull() {
            addCriterion("crs_post_type is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeEqualTo(Byte value) {
            addCriterion("crs_post_type =", value, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeNotEqualTo(Byte value) {
            addCriterion("crs_post_type <>", value, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeGreaterThan(Byte value) {
            addCriterion("crs_post_type >", value, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("crs_post_type >=", value, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeLessThan(Byte value) {
            addCriterion("crs_post_type <", value, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeLessThanOrEqualTo(Byte value) {
            addCriterion("crs_post_type <=", value, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeIn(List<Byte> values) {
            addCriterion("crs_post_type in", values, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeNotIn(List<Byte> values) {
            addCriterion("crs_post_type not in", values, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeBetween(Byte value1, Byte value2) {
            addCriterion("crs_post_type between", value1, value2, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("crs_post_type not between", value1, value2, "crsPostType");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearIsNull() {
            addCriterion("crs_post_year is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearIsNotNull() {
            addCriterion("crs_post_year is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearEqualTo(Integer value) {
            addCriterion("crs_post_year =", value, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearNotEqualTo(Integer value) {
            addCriterion("crs_post_year <>", value, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearGreaterThan(Integer value) {
            addCriterion("crs_post_year >", value, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("crs_post_year >=", value, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearLessThan(Integer value) {
            addCriterion("crs_post_year <", value, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearLessThanOrEqualTo(Integer value) {
            addCriterion("crs_post_year <=", value, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearIn(List<Integer> values) {
            addCriterion("crs_post_year in", values, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearNotIn(List<Integer> values) {
            addCriterion("crs_post_year not in", values, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearBetween(Integer value1, Integer value2) {
            addCriterion("crs_post_year between", value1, value2, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostYearNotBetween(Integer value1, Integer value2) {
            addCriterion("crs_post_year not between", value1, value2, "crsPostYear");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqIsNull() {
            addCriterion("crs_post_seq is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqIsNotNull() {
            addCriterion("crs_post_seq is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqEqualTo(Integer value) {
            addCriterion("crs_post_seq =", value, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqNotEqualTo(Integer value) {
            addCriterion("crs_post_seq <>", value, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqGreaterThan(Integer value) {
            addCriterion("crs_post_seq >", value, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("crs_post_seq >=", value, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqLessThan(Integer value) {
            addCriterion("crs_post_seq <", value, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqLessThanOrEqualTo(Integer value) {
            addCriterion("crs_post_seq <=", value, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqIn(List<Integer> values) {
            addCriterion("crs_post_seq in", values, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqNotIn(List<Integer> values) {
            addCriterion("crs_post_seq not in", values, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqBetween(Integer value1, Integer value2) {
            addCriterion("crs_post_seq between", value1, value2, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("crs_post_seq not between", value1, value2, "crsPostSeq");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameIsNull() {
            addCriterion("crs_post_name is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameIsNotNull() {
            addCriterion("crs_post_name is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameEqualTo(String value) {
            addCriterion("crs_post_name =", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameNotEqualTo(String value) {
            addCriterion("crs_post_name <>", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameGreaterThan(String value) {
            addCriterion("crs_post_name >", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameGreaterThanOrEqualTo(String value) {
            addCriterion("crs_post_name >=", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameLessThan(String value) {
            addCriterion("crs_post_name <", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameLessThanOrEqualTo(String value) {
            addCriterion("crs_post_name <=", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameLike(String value) {
            addCriterion("crs_post_name like", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameNotLike(String value) {
            addCriterion("crs_post_name not like", value, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameIn(List<String> values) {
            addCriterion("crs_post_name in", values, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameNotIn(List<String> values) {
            addCriterion("crs_post_name not in", values, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameBetween(String value1, String value2) {
            addCriterion("crs_post_name between", value1, value2, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostNameNotBetween(String value1, String value2) {
            addCriterion("crs_post_name not between", value1, value2, "crsPostName");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobIsNull() {
            addCriterion("crs_post_job is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobIsNotNull() {
            addCriterion("crs_post_job is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobEqualTo(String value) {
            addCriterion("crs_post_job =", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobNotEqualTo(String value) {
            addCriterion("crs_post_job <>", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobGreaterThan(String value) {
            addCriterion("crs_post_job >", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobGreaterThanOrEqualTo(String value) {
            addCriterion("crs_post_job >=", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobLessThan(String value) {
            addCriterion("crs_post_job <", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobLessThanOrEqualTo(String value) {
            addCriterion("crs_post_job <=", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobLike(String value) {
            addCriterion("crs_post_job like", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobNotLike(String value) {
            addCriterion("crs_post_job not like", value, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobIn(List<String> values) {
            addCriterion("crs_post_job in", values, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobNotIn(List<String> values) {
            addCriterion("crs_post_job not in", values, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobBetween(String value1, String value2) {
            addCriterion("crs_post_job between", value1, value2, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostJobNotBetween(String value1, String value2) {
            addCriterion("crs_post_job not between", value1, value2, "crsPostJob");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusIsNull() {
            addCriterion("crs_post_status is null");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusIsNotNull() {
            addCriterion("crs_post_status is not null");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusEqualTo(Byte value) {
            addCriterion("crs_post_status =", value, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusNotEqualTo(Byte value) {
            addCriterion("crs_post_status <>", value, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusGreaterThan(Byte value) {
            addCriterion("crs_post_status >", value, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("crs_post_status >=", value, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusLessThan(Byte value) {
            addCriterion("crs_post_status <", value, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusLessThanOrEqualTo(Byte value) {
            addCriterion("crs_post_status <=", value, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusIn(List<Byte> values) {
            addCriterion("crs_post_status in", values, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusNotIn(List<Byte> values) {
            addCriterion("crs_post_status not in", values, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusBetween(Byte value1, Byte value2) {
            addCriterion("crs_post_status between", value1, value2, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andCrsPostStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("crs_post_status not between", value1, value2, "crsPostStatus");
            return (Criteria) this;
        }

        public Criteria andApplicantIdIsNull() {
            addCriterion("applicant_id is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIdIsNotNull() {
            addCriterion("applicant_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantIdEqualTo(Integer value) {
            addCriterion("applicant_id =", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdNotEqualTo(Integer value) {
            addCriterion("applicant_id <>", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdGreaterThan(Integer value) {
            addCriterion("applicant_id >", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("applicant_id >=", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdLessThan(Integer value) {
            addCriterion("applicant_id <", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdLessThanOrEqualTo(Integer value) {
            addCriterion("applicant_id <=", value, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdIn(List<Integer> values) {
            addCriterion("applicant_id in", values, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdNotIn(List<Integer> values) {
            addCriterion("applicant_id not in", values, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdBetween(Integer value1, Integer value2) {
            addCriterion("applicant_id between", value1, value2, "applicantId");
            return (Criteria) this;
        }

        public Criteria andApplicantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("applicant_id not between", value1, value2, "applicantId");
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

        public Criteria andPptNameIsNull() {
            addCriterion("ppt_name is null");
            return (Criteria) this;
        }

        public Criteria andPptNameIsNotNull() {
            addCriterion("ppt_name is not null");
            return (Criteria) this;
        }

        public Criteria andPptNameEqualTo(String value) {
            addCriterion("ppt_name =", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotEqualTo(String value) {
            addCriterion("ppt_name <>", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameGreaterThan(String value) {
            addCriterion("ppt_name >", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameGreaterThanOrEqualTo(String value) {
            addCriterion("ppt_name >=", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameLessThan(String value) {
            addCriterion("ppt_name <", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameLessThanOrEqualTo(String value) {
            addCriterion("ppt_name <=", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameLike(String value) {
            addCriterion("ppt_name like", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotLike(String value) {
            addCriterion("ppt_name not like", value, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameIn(List<String> values) {
            addCriterion("ppt_name in", values, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotIn(List<String> values) {
            addCriterion("ppt_name not in", values, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameBetween(String value1, String value2) {
            addCriterion("ppt_name between", value1, value2, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptNameNotBetween(String value1, String value2) {
            addCriterion("ppt_name not between", value1, value2, "pptName");
            return (Criteria) this;
        }

        public Criteria andPptIsNull() {
            addCriterion("ppt is null");
            return (Criteria) this;
        }

        public Criteria andPptIsNotNull() {
            addCriterion("ppt is not null");
            return (Criteria) this;
        }

        public Criteria andPptEqualTo(String value) {
            addCriterion("ppt =", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotEqualTo(String value) {
            addCriterion("ppt <>", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptGreaterThan(String value) {
            addCriterion("ppt >", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptGreaterThanOrEqualTo(String value) {
            addCriterion("ppt >=", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptLessThan(String value) {
            addCriterion("ppt <", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptLessThanOrEqualTo(String value) {
            addCriterion("ppt <=", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptLike(String value) {
            addCriterion("ppt like", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotLike(String value) {
            addCriterion("ppt not like", value, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptIn(List<String> values) {
            addCriterion("ppt in", values, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotIn(List<String> values) {
            addCriterion("ppt not in", values, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptBetween(String value1, String value2) {
            addCriterion("ppt between", value1, value2, "ppt");
            return (Criteria) this;
        }

        public Criteria andPptNotBetween(String value1, String value2) {
            addCriterion("ppt not between", value1, value2, "ppt");
            return (Criteria) this;
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

        public Criteria andAdminLevelIsNull() {
            addCriterion("admin_level is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNotNull() {
            addCriterion("admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelEqualTo(Integer value) {
            addCriterion("admin_level =", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotEqualTo(Integer value) {
            addCriterion("admin_level <>", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThan(Integer value) {
            addCriterion("admin_level >", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level >=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThan(Integer value) {
            addCriterion("admin_level <", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level <=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIn(List<Integer> values) {
            addCriterion("admin_level in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotIn(List<Integer> values) {
            addCriterion("admin_level not in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("admin_level between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level not between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNull() {
            addCriterion("post_type is null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNotNull() {
            addCriterion("post_type is not null");
            return (Criteria) this;
        }

        public Criteria andPostTypeEqualTo(Integer value) {
            addCriterion("post_type =", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotEqualTo(Integer value) {
            addCriterion("post_type <>", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThan(Integer value) {
            addCriterion("post_type >", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_type >=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThan(Integer value) {
            addCriterion("post_type <", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("post_type <=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeIn(List<Integer> values) {
            addCriterion("post_type in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotIn(List<Integer> values) {
            addCriterion("post_type not in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeBetween(Integer value1, Integer value2) {
            addCriterion("post_type between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("post_type not between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Integer value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Integer value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Integer value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Integer value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Integer> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Integer> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Boolean value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Boolean value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Boolean value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Boolean value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Boolean value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Boolean> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Boolean> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Boolean value1, Boolean value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIsNull() {
            addCriterion("dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIsNotNull() {
            addCriterion("dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id =", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <>", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("dispatch_cadre_id >", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id >=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThan(Integer value) {
            addCriterion("dispatch_cadre_id <", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id not in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id not between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andPostIsNull() {
            addCriterion("post is null");
            return (Criteria) this;
        }

        public Criteria andPostIsNotNull() {
            addCriterion("post is not null");
            return (Criteria) this;
        }

        public Criteria andPostEqualTo(String value) {
            addCriterion("post =", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotEqualTo(String value) {
            addCriterion("post <>", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThan(String value) {
            addCriterion("post >", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostGreaterThanOrEqualTo(String value) {
            addCriterion("post >=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThan(String value) {
            addCriterion("post <", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLessThanOrEqualTo(String value) {
            addCriterion("post <=", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostLike(String value) {
            addCriterion("post like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotLike(String value) {
            addCriterion("post not like", value, "post");
            return (Criteria) this;
        }

        public Criteria andPostIn(List<String> values) {
            addCriterion("post in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotIn(List<String> values) {
            addCriterion("post not in", values, "post");
            return (Criteria) this;
        }

        public Criteria andPostBetween(String value1, String value2) {
            addCriterion("post between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andPostNotBetween(String value1, String value2) {
            addCriterion("post not between", value1, value2, "post");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
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

        public Criteria andIsCommitteeMemberIsNull() {
            addCriterion("is_committee_member is null");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberIsNotNull() {
            addCriterion("is_committee_member is not null");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberEqualTo(Boolean value) {
            addCriterion("is_committee_member =", value, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberNotEqualTo(Boolean value) {
            addCriterion("is_committee_member <>", value, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberGreaterThan(Boolean value) {
            addCriterion("is_committee_member >", value, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_committee_member >=", value, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberLessThan(Boolean value) {
            addCriterion("is_committee_member <", value, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberLessThanOrEqualTo(Boolean value) {
            addCriterion("is_committee_member <=", value, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberIn(List<Boolean> values) {
            addCriterion("is_committee_member in", values, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberNotIn(List<Boolean> values) {
            addCriterion("is_committee_member not in", values, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberBetween(Boolean value1, Boolean value2) {
            addCriterion("is_committee_member between", value1, value2, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andIsCommitteeMemberNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_committee_member not between", value1, value2, "isCommitteeMember");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNull() {
            addCriterion("msg_title is null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIsNotNull() {
            addCriterion("msg_title is not null");
            return (Criteria) this;
        }

        public Criteria andMsgTitleEqualTo(String value) {
            addCriterion("msg_title =", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotEqualTo(String value) {
            addCriterion("msg_title <>", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThan(String value) {
            addCriterion("msg_title >", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleGreaterThanOrEqualTo(String value) {
            addCriterion("msg_title >=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThan(String value) {
            addCriterion("msg_title <", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLessThanOrEqualTo(String value) {
            addCriterion("msg_title <=", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleLike(String value) {
            addCriterion("msg_title like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotLike(String value) {
            addCriterion("msg_title not like", value, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleIn(List<String> values) {
            addCriterion("msg_title in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotIn(List<String> values) {
            addCriterion("msg_title not in", values, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleBetween(String value1, String value2) {
            addCriterion("msg_title between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMsgTitleNotBetween(String value1, String value2) {
            addCriterion("msg_title not between", value1, value2, "msgTitle");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNull() {
            addCriterion("home_phone is null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNotNull() {
            addCriterion("home_phone is not null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneEqualTo(String value) {
            addCriterion("home_phone =", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotEqualTo(String value) {
            addCriterion("home_phone <>", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThan(String value) {
            addCriterion("home_phone >", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("home_phone >=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThan(String value) {
            addCriterion("home_phone <", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThanOrEqualTo(String value) {
            addCriterion("home_phone <=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLike(String value) {
            addCriterion("home_phone like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotLike(String value) {
            addCriterion("home_phone not like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIn(List<String> values) {
            addCriterion("home_phone in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotIn(List<String> values) {
            addCriterion("home_phone not in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneBetween(String value1, String value2) {
            addCriterion("home_phone between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotBetween(String value1, String value2) {
            addCriterion("home_phone not between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Byte value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Byte value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Byte value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Byte value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Byte value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Byte value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Byte> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Byte> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Byte value1, Byte value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Byte value1, Byte value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andBirthIsNull() {
            addCriterion("birth is null");
            return (Criteria) this;
        }

        public Criteria andBirthIsNotNull() {
            addCriterion("birth is not null");
            return (Criteria) this;
        }

        public Criteria andBirthEqualTo(Date value) {
            addCriterionForJDBCDate("birth =", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth <>", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("birth >", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth >=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThan(Date value) {
            addCriterionForJDBCDate("birth <", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth <=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthIn(List<Date> values) {
            addCriterionForJDBCDate("birth in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth not in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth not between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("party_id is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("party_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Integer value) {
            addCriterion("party_id =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Integer value) {
            addCriterion("party_id <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Integer value) {
            addCriterion("party_id >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_id >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Integer value) {
            addCriterion("party_id <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_id <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Integer> values) {
            addCriterion("party_id in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Integer> values) {
            addCriterion("party_id not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("party_id between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_id not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNull() {
            addCriterion("member_status is null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIsNotNull() {
            addCriterion("member_status is not null");
            return (Criteria) this;
        }

        public Criteria andMemberStatusEqualTo(Byte value) {
            addCriterion("member_status =", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotEqualTo(Byte value) {
            addCriterion("member_status <>", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThan(Byte value) {
            addCriterion("member_status >", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("member_status >=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThan(Byte value) {
            addCriterion("member_status <", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusLessThanOrEqualTo(Byte value) {
            addCriterion("member_status <=", value, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusIn(List<Byte> values) {
            addCriterion("member_status in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotIn(List<Byte> values) {
            addCriterion("member_status not in", values, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusBetween(Byte value1, Byte value2) {
            addCriterion("member_status between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andMemberStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("member_status not between", value1, value2, "memberStatus");
            return (Criteria) this;
        }

        public Criteria andDpIdIsNull() {
            addCriterion("dp_id is null");
            return (Criteria) this;
        }

        public Criteria andDpIdIsNotNull() {
            addCriterion("dp_id is not null");
            return (Criteria) this;
        }

        public Criteria andDpIdEqualTo(Integer value) {
            addCriterion("dp_id =", value, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdNotEqualTo(Integer value) {
            addCriterion("dp_id <>", value, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdGreaterThan(Integer value) {
            addCriterion("dp_id >", value, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dp_id >=", value, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdLessThan(Integer value) {
            addCriterion("dp_id <", value, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdLessThanOrEqualTo(Integer value) {
            addCriterion("dp_id <=", value, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdIn(List<Integer> values) {
            addCriterion("dp_id in", values, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdNotIn(List<Integer> values) {
            addCriterion("dp_id not in", values, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdBetween(Integer value1, Integer value2) {
            addCriterion("dp_id between", value1, value2, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dp_id not between", value1, value2, "dpId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIsNull() {
            addCriterion("dp_type_id is null");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIsNotNull() {
            addCriterion("dp_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdEqualTo(Integer value) {
            addCriterion("dp_type_id =", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotEqualTo(Integer value) {
            addCriterion("dp_type_id <>", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdGreaterThan(Integer value) {
            addCriterion("dp_type_id >", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dp_type_id >=", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdLessThan(Integer value) {
            addCriterion("dp_type_id <", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("dp_type_id <=", value, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdIn(Set<Integer> values) {

            List searchSqlList = new ArrayList<>();
            if(values.contains(-1)){
                searchSqlList.add("(dp_type_id is null and is_ow=0)");
            }else if(values.contains(0)){
                searchSqlList.add("is_ow=1");
            }
            values.remove(-1);
            values.remove(0);
            if(values.size()>0){
                searchSqlList.add("dp_type_id in (" + StringUtils.join(values, ",") + ")");
            }
            if(searchSqlList.size()>0)
                addCriterion("(" + StringUtils.join(searchSqlList, " or ") + ")");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotIn(List<Integer> values) {
            addCriterion("dp_type_id not in", values, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("dp_type_id between", value1, value2, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dp_type_id not between", value1, value2, "dpTypeId");
            return (Criteria) this;
        }

        public Criteria andDpPostIsNull() {
            addCriterion("dp_post is null");
            return (Criteria) this;
        }

        public Criteria andDpPostIsNotNull() {
            addCriterion("dp_post is not null");
            return (Criteria) this;
        }

        public Criteria andDpPostEqualTo(String value) {
            addCriterion("dp_post =", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotEqualTo(String value) {
            addCriterion("dp_post <>", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostGreaterThan(String value) {
            addCriterion("dp_post >", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostGreaterThanOrEqualTo(String value) {
            addCriterion("dp_post >=", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostLessThan(String value) {
            addCriterion("dp_post <", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostLessThanOrEqualTo(String value) {
            addCriterion("dp_post <=", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostLike(String value) {
            addCriterion("dp_post like", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotLike(String value) {
            addCriterion("dp_post not like", value, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostIn(List<String> values) {
            addCriterion("dp_post in", values, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotIn(List<String> values) {
            addCriterion("dp_post not in", values, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostBetween(String value1, String value2) {
            addCriterion("dp_post between", value1, value2, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpPostNotBetween(String value1, String value2) {
            addCriterion("dp_post not between", value1, value2, "dpPost");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeIsNull() {
            addCriterion("dp_grow_time is null");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeIsNotNull() {
            addCriterion("dp_grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time =", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time <>", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("dp_grow_time >", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time >=", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("dp_grow_time <", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("dp_grow_time <=", value, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("dp_grow_time in", values, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("dp_grow_time not in", values, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dp_grow_time between", value1, value2, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("dp_grow_time not between", value1, value2, "dpGrowTime");
            return (Criteria) this;
        }

        public Criteria andDpRemarkIsNull() {
            addCriterion("dp_remark is null");
            return (Criteria) this;
        }

        public Criteria andDpRemarkIsNotNull() {
            addCriterion("dp_remark is not null");
            return (Criteria) this;
        }

        public Criteria andDpRemarkEqualTo(String value) {
            addCriterion("dp_remark =", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotEqualTo(String value) {
            addCriterion("dp_remark <>", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkGreaterThan(String value) {
            addCriterion("dp_remark >", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("dp_remark >=", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkLessThan(String value) {
            addCriterion("dp_remark <", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkLessThanOrEqualTo(String value) {
            addCriterion("dp_remark <=", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkLike(String value) {
            addCriterion("dp_remark like", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotLike(String value) {
            addCriterion("dp_remark not like", value, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkIn(List<String> values) {
            addCriterion("dp_remark in", values, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotIn(List<String> values) {
            addCriterion("dp_remark not in", values, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkBetween(String value1, String value2) {
            addCriterion("dp_remark between", value1, value2, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andDpRemarkNotBetween(String value1, String value2) {
            addCriterion("dp_remark not between", value1, value2, "dpRemark");
            return (Criteria) this;
        }

        public Criteria andOwIdIsNull() {
            addCriterion("ow_id is null");
            return (Criteria) this;
        }

        public Criteria andOwIdIsNotNull() {
            addCriterion("ow_id is not null");
            return (Criteria) this;
        }

        public Criteria andOwIdEqualTo(Integer value) {
            addCriterion("ow_id =", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdNotEqualTo(Integer value) {
            addCriterion("ow_id <>", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdGreaterThan(Integer value) {
            addCriterion("ow_id >", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ow_id >=", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdLessThan(Integer value) {
            addCriterion("ow_id <", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdLessThanOrEqualTo(Integer value) {
            addCriterion("ow_id <=", value, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdIn(List<Integer> values) {
            addCriterion("ow_id in", values, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdNotIn(List<Integer> values) {
            addCriterion("ow_id not in", values, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdBetween(Integer value1, Integer value2) {
            addCriterion("ow_id between", value1, value2, "owId");
            return (Criteria) this;
        }

        public Criteria andOwIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ow_id not between", value1, value2, "owId");
            return (Criteria) this;
        }

        public Criteria andIsOwIsNull() {
            addCriterion("is_ow is null");
            return (Criteria) this;
        }

        public Criteria andIsOwIsNotNull() {
            addCriterion("is_ow is not null");
            return (Criteria) this;
        }

        public Criteria andIsOwEqualTo(Boolean value) {
            addCriterion("is_ow =", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwNotEqualTo(Boolean value) {
            addCriterion("is_ow <>", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwGreaterThan(Boolean value) {
            addCriterion("is_ow >", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_ow >=", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwLessThan(Boolean value) {
            addCriterion("is_ow <", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwLessThanOrEqualTo(Boolean value) {
            addCriterion("is_ow <=", value, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwIn(List<Boolean> values) {
            addCriterion("is_ow in", values, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwNotIn(List<Boolean> values) {
            addCriterion("is_ow not in", values, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ow between", value1, value2, "isOw");
            return (Criteria) this;
        }

        public Criteria andIsOwNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_ow not between", value1, value2, "isOw");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeIsNull() {
            addCriterion("ow_grow_time is null");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeIsNotNull() {
            addCriterion("ow_grow_time is not null");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time =", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time <>", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("ow_grow_time >", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time >=", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeLessThan(Date value) {
            addCriterionForJDBCDate("ow_grow_time <", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ow_grow_time <=", value, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeIn(List<Date> values) {
            addCriterionForJDBCDate("ow_grow_time in", values, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("ow_grow_time not in", values, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_grow_time between", value1, value2, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwGrowTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ow_grow_time not between", value1, value2, "owGrowTime");
            return (Criteria) this;
        }

        public Criteria andOwRemarkIsNull() {
            addCriterion("ow_remark is null");
            return (Criteria) this;
        }

        public Criteria andOwRemarkIsNotNull() {
            addCriterion("ow_remark is not null");
            return (Criteria) this;
        }

        public Criteria andOwRemarkEqualTo(String value) {
            addCriterion("ow_remark =", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotEqualTo(String value) {
            addCriterion("ow_remark <>", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkGreaterThan(String value) {
            addCriterion("ow_remark >", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("ow_remark >=", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkLessThan(String value) {
            addCriterion("ow_remark <", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkLessThanOrEqualTo(String value) {
            addCriterion("ow_remark <=", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkLike(String value) {
            addCriterion("ow_remark like", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotLike(String value) {
            addCriterion("ow_remark not like", value, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkIn(List<String> values) {
            addCriterion("ow_remark in", values, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotIn(List<String> values) {
            addCriterion("ow_remark not in", values, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkBetween(String value1, String value2) {
            addCriterion("ow_remark between", value1, value2, "owRemark");
            return (Criteria) this;
        }

        public Criteria andOwRemarkNotBetween(String value1, String value2) {
            addCriterion("ow_remark not between", value1, value2, "owRemark");
            return (Criteria) this;
        }

        public Criteria andEduIdIsNull() {
            addCriterion("edu_id is null");
            return (Criteria) this;
        }

        public Criteria andEduIdIsNotNull() {
            addCriterion("edu_id is not null");
            return (Criteria) this;
        }

        public Criteria andEduIdEqualTo(Integer value) {
            addCriterion("edu_id =", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotEqualTo(Integer value) {
            addCriterion("edu_id <>", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdGreaterThan(Integer value) {
            addCriterion("edu_id >", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("edu_id >=", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdLessThan(Integer value) {
            addCriterion("edu_id <", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdLessThanOrEqualTo(Integer value) {
            addCriterion("edu_id <=", value, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdIn(List<Integer> values) {
            addCriterion("edu_id in", values, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotIn(List<Integer> values) {
            addCriterion("edu_id not in", values, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdBetween(Integer value1, Integer value2) {
            addCriterion("edu_id between", value1, value2, "eduId");
            return (Criteria) this;
        }

        public Criteria andEduIdNotBetween(Integer value1, Integer value2) {
            addCriterion("edu_id not between", value1, value2, "eduId");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterionForJDBCDate("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterionForJDBCDate("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIsNull() {
            addCriterion("learn_style is null");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIsNotNull() {
            addCriterion("learn_style is not null");
            return (Criteria) this;
        }

        public Criteria andLearnStyleEqualTo(Integer value) {
            addCriterion("learn_style =", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotEqualTo(Integer value) {
            addCriterion("learn_style <>", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleGreaterThan(Integer value) {
            addCriterion("learn_style >", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleGreaterThanOrEqualTo(Integer value) {
            addCriterion("learn_style >=", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleLessThan(Integer value) {
            addCriterion("learn_style <", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleLessThanOrEqualTo(Integer value) {
            addCriterion("learn_style <=", value, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleIn(List<Integer> values) {
            addCriterion("learn_style in", values, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotIn(List<Integer> values) {
            addCriterion("learn_style not in", values, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleBetween(Integer value1, Integer value2) {
            addCriterion("learn_style between", value1, value2, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andLearnStyleNotBetween(Integer value1, Integer value2) {
            addCriterion("learn_style not between", value1, value2, "learnStyle");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andDepIsNull() {
            addCriterion("dep is null");
            return (Criteria) this;
        }

        public Criteria andDepIsNotNull() {
            addCriterion("dep is not null");
            return (Criteria) this;
        }

        public Criteria andDepEqualTo(String value) {
            addCriterion("dep =", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotEqualTo(String value) {
            addCriterion("dep <>", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepGreaterThan(String value) {
            addCriterion("dep >", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepGreaterThanOrEqualTo(String value) {
            addCriterion("dep >=", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLessThan(String value) {
            addCriterion("dep <", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLessThanOrEqualTo(String value) {
            addCriterion("dep <=", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepLike(String value) {
            addCriterion("dep like", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotLike(String value) {
            addCriterion("dep not like", value, "dep");
            return (Criteria) this;
        }

        public Criteria andDepIn(List<String> values) {
            addCriterion("dep in", values, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotIn(List<String> values) {
            addCriterion("dep not in", values, "dep");
            return (Criteria) this;
        }

        public Criteria andDepBetween(String value1, String value2) {
            addCriterion("dep between", value1, value2, "dep");
            return (Criteria) this;
        }

        public Criteria andDepNotBetween(String value1, String value2) {
            addCriterion("dep not between", value1, value2, "dep");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNull() {
            addCriterion("school_type is null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIsNotNull() {
            addCriterion("school_type is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeEqualTo(Byte value) {
            addCriterion("school_type =", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotEqualTo(Byte value) {
            addCriterion("school_type <>", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThan(Byte value) {
            addCriterion("school_type >", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("school_type >=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThan(Byte value) {
            addCriterion("school_type <", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeLessThanOrEqualTo(Byte value) {
            addCriterion("school_type <=", value, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeIn(List<Byte> values) {
            addCriterion("school_type in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotIn(List<Byte> values) {
            addCriterion("school_type not in", values, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeBetween(Byte value1, Byte value2) {
            addCriterion("school_type between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andSchoolTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("school_type not between", value1, value2, "schoolType");
            return (Criteria) this;
        }

        public Criteria andMajorIsNull() {
            addCriterion("major is null");
            return (Criteria) this;
        }

        public Criteria andMajorIsNotNull() {
            addCriterion("major is not null");
            return (Criteria) this;
        }

        public Criteria andMajorEqualTo(String value) {
            addCriterion("major =", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotEqualTo(String value) {
            addCriterion("major <>", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThan(String value) {
            addCriterion("major >", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThanOrEqualTo(String value) {
            addCriterion("major >=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThan(String value) {
            addCriterion("major <", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThanOrEqualTo(String value) {
            addCriterion("major <=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLike(String value) {
            addCriterion("major like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotLike(String value) {
            addCriterion("major not like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorIn(List<String> values) {
            addCriterion("major in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotIn(List<String> values) {
            addCriterion("major not in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorBetween(String value1, String value2) {
            addCriterion("major between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotBetween(String value1, String value2) {
            addCriterion("major not between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNull() {
            addCriterion("degree is null");
            return (Criteria) this;
        }

        public Criteria andDegreeIsNotNull() {
            addCriterion("degree is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeEqualTo(String value) {
            addCriterion("degree =", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotEqualTo(String value) {
            addCriterion("degree <>", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThan(String value) {
            addCriterion("degree >", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeGreaterThanOrEqualTo(String value) {
            addCriterion("degree >=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThan(String value) {
            addCriterion("degree <", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLessThanOrEqualTo(String value) {
            addCriterion("degree <=", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeLike(String value) {
            addCriterion("degree like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotLike(String value) {
            addCriterion("degree not like", value, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeIn(List<String> values) {
            addCriterion("degree in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotIn(List<String> values) {
            addCriterion("degree not in", values, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeBetween(String value1, String value2) {
            addCriterion("degree between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andDegreeNotBetween(String value1, String value2) {
            addCriterion("degree not between", value1, value2, "degree");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNull() {
            addCriterion("post_class is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNotNull() {
            addCriterion("post_class is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassEqualTo(String value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(String value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(String value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(String value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(String value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLike(String value) {
            addCriterion("post_class like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotLike(String value) {
            addCriterion("post_class not like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<String> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<String> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(String value1, String value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(String value1, String value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNull() {
            addCriterion("sub_post_class is null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIsNotNull() {
            addCriterion("sub_post_class is not null");
            return (Criteria) this;
        }

        public Criteria andSubPostClassEqualTo(String value) {
            addCriterion("sub_post_class =", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotEqualTo(String value) {
            addCriterion("sub_post_class <>", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThan(String value) {
            addCriterion("sub_post_class >", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("sub_post_class >=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThan(String value) {
            addCriterion("sub_post_class <", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLessThanOrEqualTo(String value) {
            addCriterion("sub_post_class <=", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassLike(String value) {
            addCriterion("sub_post_class like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotLike(String value) {
            addCriterion("sub_post_class not like", value, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassIn(List<String> values) {
            addCriterion("sub_post_class in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotIn(List<String> values) {
            addCriterion("sub_post_class not in", values, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassBetween(String value1, String value2) {
            addCriterion("sub_post_class between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andSubPostClassNotBetween(String value1, String value2) {
            addCriterion("sub_post_class not between", value1, value2, "subPostClass");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNull() {
            addCriterion("main_post_level is null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNotNull() {
            addCriterion("main_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelEqualTo(String value) {
            addCriterion("main_post_level =", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotEqualTo(String value) {
            addCriterion("main_post_level <>", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThan(String value) {
            addCriterion("main_post_level >", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("main_post_level >=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThan(String value) {
            addCriterion("main_post_level <", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThanOrEqualTo(String value) {
            addCriterion("main_post_level <=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLike(String value) {
            addCriterion("main_post_level like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotLike(String value) {
            addCriterion("main_post_level not like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIn(List<String> values) {
            addCriterion("main_post_level in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotIn(List<String> values) {
            addCriterion("main_post_level not in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelBetween(String value1, String value2) {
            addCriterion("main_post_level between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotBetween(String value1, String value2) {
            addCriterion("main_post_level not between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNull() {
            addCriterion("pro_post_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIsNotNull() {
            addCriterion("pro_post_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time =", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <>", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_time >", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time >=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_time <", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_time <=", value, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_time not in", values, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_time not between", value1, value2, "proPostTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNull() {
            addCriterion("pro_post_level is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNotNull() {
            addCriterion("pro_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelEqualTo(String value) {
            addCriterion("pro_post_level =", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotEqualTo(String value) {
            addCriterion("pro_post_level <>", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThan(String value) {
            addCriterion("pro_post_level >", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post_level >=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThan(String value) {
            addCriterion("pro_post_level <", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThanOrEqualTo(String value) {
            addCriterion("pro_post_level <=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLike(String value) {
            addCriterion("pro_post_level like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotLike(String value) {
            addCriterion("pro_post_level not like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIn(List<String> values) {
            addCriterion("pro_post_level in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotIn(List<String> values) {
            addCriterion("pro_post_level not in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelBetween(String value1, String value2) {
            addCriterion("pro_post_level between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotBetween(String value1, String value2) {
            addCriterion("pro_post_level not between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNull() {
            addCriterion("pro_post_level_time is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIsNotNull() {
            addCriterion("pro_post_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time =", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <>", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time >=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pro_post_level_time <=", value, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("pro_post_level_time not in", values, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pro_post_level_time not between", value1, value2, "proPostLevelTime");
            return (Criteria) this;
        }

        public Criteria andProPostIsNull() {
            addCriterion("pro_post is null");
            return (Criteria) this;
        }

        public Criteria andProPostIsNotNull() {
            addCriterion("pro_post is not null");
            return (Criteria) this;
        }

        public Criteria andProPostEqualTo(String value) {
            addCriterion("pro_post =", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotEqualTo(String value) {
            addCriterion("pro_post <>", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThan(String value) {
            addCriterion("pro_post >", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post >=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThan(String value) {
            addCriterion("pro_post <", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLessThanOrEqualTo(String value) {
            addCriterion("pro_post <=", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostLike(String value) {
            addCriterion("pro_post like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotLike(String value) {
            addCriterion("pro_post not like", value, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostIn(List<String> values) {
            addCriterion("pro_post in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotIn(List<String> values) {
            addCriterion("pro_post not in", values, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostBetween(String value1, String value2) {
            addCriterion("pro_post between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andProPostNotBetween(String value1, String value2) {
            addCriterion("pro_post not between", value1, value2, "proPost");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNull() {
            addCriterion("manage_level is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNotNull() {
            addCriterion("manage_level is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelEqualTo(String value) {
            addCriterion("manage_level =", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotEqualTo(String value) {
            addCriterion("manage_level <>", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThan(String value) {
            addCriterion("manage_level >", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThanOrEqualTo(String value) {
            addCriterion("manage_level >=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThan(String value) {
            addCriterion("manage_level <", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThanOrEqualTo(String value) {
            addCriterion("manage_level <=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLike(String value) {
            addCriterion("manage_level like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotLike(String value) {
            addCriterion("manage_level not like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIn(List<String> values) {
            addCriterion("manage_level in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotIn(List<String> values) {
            addCriterion("manage_level not in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelBetween(String value1, String value2) {
            addCriterion("manage_level between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotBetween(String value1, String value2) {
            addCriterion("manage_level not between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNull() {
            addCriterion("manage_level_time is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIsNotNull() {
            addCriterion("manage_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time =", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <>", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("manage_level_time >", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time >=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThan(Date value) {
            addCriterionForJDBCDate("manage_level_time <", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("manage_level_time <=", value, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("manage_level_time not in", values, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andManageLevelTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("manage_level_time not between", value1, value2, "manageLevelTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNull() {
            addCriterion("arrive_time is null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIsNotNull() {
            addCriterion("arrive_time is not null");
            return (Criteria) this;
        }

        public Criteria andArriveTimeEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time =", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <>", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("arrive_time >", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time >=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThan(Date value) {
            addCriterionForJDBCDate("arrive_time <", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrive_time <=", value, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("arrive_time not in", values, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andArriveTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrive_time not between", value1, value2, "arriveTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNull() {
            addCriterion("work_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("work_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_time =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_time >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_time <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_time <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_time in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_time not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_time not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIsNull() {
            addCriterion("work_start_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIsNotNull() {
            addCriterion("work_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time =", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time <>", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("work_start_time >", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time >=", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("work_start_time <", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("work_start_time <=", value, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("work_start_time in", values, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("work_start_time not in", values, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_start_time between", value1, value2, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andWorkStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("work_start_time not between", value1, value2, "workStartTime");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNull() {
            addCriterion("talent_title is null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNotNull() {
            addCriterion("talent_title is not null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleEqualTo(String value) {
            addCriterion("talent_title =", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotEqualTo(String value) {
            addCriterion("talent_title <>", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThan(String value) {
            addCriterion("talent_title >", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThanOrEqualTo(String value) {
            addCriterion("talent_title >=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThan(String value) {
            addCriterion("talent_title <", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThanOrEqualTo(String value) {
            addCriterion("talent_title <=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLike(String value) {
            addCriterion("talent_title like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotLike(String value) {
            addCriterion("talent_title not like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIn(List<String> values) {
            addCriterion("talent_title in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotIn(List<String> values) {
            addCriterion("talent_title not in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleBetween(String value1, String value2) {
            addCriterion("talent_title between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotBetween(String value1, String value2) {
            addCriterion("talent_title not between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdIsNull() {
            addCriterion("main_cadre_post_id is null");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdIsNotNull() {
            addCriterion("main_cadre_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdEqualTo(Integer value) {
            addCriterion("main_cadre_post_id =", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdNotEqualTo(Integer value) {
            addCriterion("main_cadre_post_id <>", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdGreaterThan(Integer value) {
            addCriterion("main_cadre_post_id >", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("main_cadre_post_id >=", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdLessThan(Integer value) {
            addCriterion("main_cadre_post_id <", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdLessThanOrEqualTo(Integer value) {
            addCriterion("main_cadre_post_id <=", value, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdIn(List<Integer> values) {
            addCriterion("main_cadre_post_id in", values, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdNotIn(List<Integer> values) {
            addCriterion("main_cadre_post_id not in", values, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdBetween(Integer value1, Integer value2) {
            addCriterion("main_cadre_post_id between", value1, value2, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andMainCadrePostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("main_cadre_post_id not between", value1, value2, "mainCadrePostId");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIsNull() {
            addCriterion("is_double is null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIsNotNull() {
            addCriterion("is_double is not null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleEqualTo(Boolean value) {
            addCriterion("is_double =", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotEqualTo(Boolean value) {
            addCriterion("is_double <>", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleGreaterThan(Boolean value) {
            addCriterion("is_double >", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_double >=", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLessThan(Boolean value) {
            addCriterion("is_double <", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_double <=", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIn(List<Boolean> values) {
            addCriterion("is_double in", values, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotIn(List<Boolean> values) {
            addCriterion("is_double not in", values, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double between", value1, value2, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double not between", value1, value2, "isDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsIsNull() {
            addCriterion("double_unit_ids is null");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsIsNotNull() {
            addCriterion("double_unit_ids is not null");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsEqualTo(String value) {
            addCriterion("double_unit_ids =", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotEqualTo(String value) {
            addCriterion("double_unit_ids <>", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsGreaterThan(String value) {
            addCriterion("double_unit_ids >", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsGreaterThanOrEqualTo(String value) {
            addCriterion("double_unit_ids >=", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsLessThan(String value) {
            addCriterion("double_unit_ids <", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsLessThanOrEqualTo(String value) {
            addCriterion("double_unit_ids <=", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsLike(String value) {
            addCriterion("double_unit_ids like", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotLike(String value) {
            addCriterion("double_unit_ids not like", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsIn(List<String> values) {
            addCriterion("double_unit_ids in", values, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotIn(List<String> values) {
            addCriterion("double_unit_ids not in", values, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsBetween(String value1, String value2) {
            addCriterion("double_unit_ids between", value1, value2, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotBetween(String value1, String value2) {
            addCriterion("double_unit_ids not between", value1, value2, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostIsNull() {
            addCriterion("is_principal_post is null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostIsNotNull() {
            addCriterion("is_principal_post is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostEqualTo(Boolean value) {
            addCriterion("is_principal_post =", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostNotEqualTo(Boolean value) {
            addCriterion("is_principal_post <>", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostGreaterThan(Boolean value) {
            addCriterion("is_principal_post >", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_principal_post >=", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostLessThan(Boolean value) {
            addCriterion("is_principal_post <", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostLessThanOrEqualTo(Boolean value) {
            addCriterion("is_principal_post <=", value, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostIn(List<Boolean> values) {
            addCriterion("is_principal_post in", values, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostNotIn(List<Boolean> values) {
            addCriterion("is_principal_post not in", values, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal_post between", value1, value2, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalPostNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal_post not between", value1, value2, "isPrincipalPost");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearIsNull() {
            addCriterion("cadre_post_year is null");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearIsNotNull() {
            addCriterion("cadre_post_year is not null");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearEqualTo(Long value) {
            addCriterion("cadre_post_year =", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearNotEqualTo(Long value) {
            addCriterion("cadre_post_year <>", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearGreaterThan(Long value) {
            addCriterion("cadre_post_year >", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearGreaterThanOrEqualTo(Long value) {
            addCriterion("cadre_post_year >=", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearLessThan(Long value) {
            addCriterion("cadre_post_year <", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearLessThanOrEqualTo(Long value) {
            addCriterion("cadre_post_year <=", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearIn(List<Long> values) {
            addCriterion("cadre_post_year in", values, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearNotIn(List<Long> values) {
            addCriterion("cadre_post_year not in", values, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearBetween(Long value1, Long value2) {
            addCriterion("cadre_post_year between", value1, value2, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearNotBetween(Long value1, Long value2) {
            addCriterion("cadre_post_year not between", value1, value2, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearIsNull() {
            addCriterion("admin_level_year is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearIsNotNull() {
            addCriterion("admin_level_year is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearEqualTo(Long value) {
            addCriterion("admin_level_year =", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearNotEqualTo(Long value) {
            addCriterion("admin_level_year <>", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearGreaterThan(Long value) {
            addCriterion("admin_level_year >", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearGreaterThanOrEqualTo(Long value) {
            addCriterion("admin_level_year >=", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearLessThan(Long value) {
            addCriterion("admin_level_year <", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearLessThanOrEqualTo(Long value) {
            addCriterion("admin_level_year <=", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearIn(List<Long> values) {
            addCriterion("admin_level_year in", values, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearNotIn(List<Long> values) {
            addCriterion("admin_level_year not in", values, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearBetween(Long value1, Long value2) {
            addCriterion("admin_level_year between", value1, value2, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearNotBetween(Long value1, Long value2) {
            addCriterion("admin_level_year not between", value1, value2, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdIsNull() {
            addCriterion("np_relate_id is null");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdIsNotNull() {
            addCriterion("np_relate_id is not null");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdEqualTo(Integer value) {
            addCriterion("np_relate_id =", value, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdNotEqualTo(Integer value) {
            addCriterion("np_relate_id <>", value, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdGreaterThan(Integer value) {
            addCriterion("np_relate_id >", value, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("np_relate_id >=", value, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdLessThan(Integer value) {
            addCriterion("np_relate_id <", value, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdLessThanOrEqualTo(Integer value) {
            addCriterion("np_relate_id <=", value, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdIn(List<Integer> values) {
            addCriterion("np_relate_id in", values, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdNotIn(List<Integer> values) {
            addCriterion("np_relate_id not in", values, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdBetween(Integer value1, Integer value2) {
            addCriterion("np_relate_id between", value1, value2, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpRelateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("np_relate_id not between", value1, value2, "npRelateId");
            return (Criteria) this;
        }

        public Criteria andNpIdIsNull() {
            addCriterion("np_id is null");
            return (Criteria) this;
        }

        public Criteria andNpIdIsNotNull() {
            addCriterion("np_id is not null");
            return (Criteria) this;
        }

        public Criteria andNpIdEqualTo(Integer value) {
            addCriterion("np_id =", value, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdNotEqualTo(Integer value) {
            addCriterion("np_id <>", value, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdGreaterThan(Integer value) {
            addCriterion("np_id >", value, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("np_id >=", value, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdLessThan(Integer value) {
            addCriterion("np_id <", value, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdLessThanOrEqualTo(Integer value) {
            addCriterion("np_id <=", value, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdIn(List<Integer> values) {
            addCriterion("np_id in", values, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdNotIn(List<Integer> values) {
            addCriterion("np_id not in", values, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdBetween(Integer value1, Integer value2) {
            addCriterion("np_id between", value1, value2, "npId");
            return (Criteria) this;
        }

        public Criteria andNpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("np_id not between", value1, value2, "npId");
            return (Criteria) this;
        }

        public Criteria andNpFileNameIsNull() {
            addCriterion("np_file_name is null");
            return (Criteria) this;
        }

        public Criteria andNpFileNameIsNotNull() {
            addCriterion("np_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andNpFileNameEqualTo(String value) {
            addCriterion("np_file_name =", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameNotEqualTo(String value) {
            addCriterion("np_file_name <>", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameGreaterThan(String value) {
            addCriterion("np_file_name >", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("np_file_name >=", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameLessThan(String value) {
            addCriterion("np_file_name <", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameLessThanOrEqualTo(String value) {
            addCriterion("np_file_name <=", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameLike(String value) {
            addCriterion("np_file_name like", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameNotLike(String value) {
            addCriterion("np_file_name not like", value, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameIn(List<String> values) {
            addCriterion("np_file_name in", values, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameNotIn(List<String> values) {
            addCriterion("np_file_name not in", values, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameBetween(String value1, String value2) {
            addCriterion("np_file_name between", value1, value2, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileNameNotBetween(String value1, String value2) {
            addCriterion("np_file_name not between", value1, value2, "npFileName");
            return (Criteria) this;
        }

        public Criteria andNpFileIsNull() {
            addCriterion("np_file is null");
            return (Criteria) this;
        }

        public Criteria andNpFileIsNotNull() {
            addCriterion("np_file is not null");
            return (Criteria) this;
        }

        public Criteria andNpFileEqualTo(String value) {
            addCriterion("np_file =", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileNotEqualTo(String value) {
            addCriterion("np_file <>", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileGreaterThan(String value) {
            addCriterion("np_file >", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileGreaterThanOrEqualTo(String value) {
            addCriterion("np_file >=", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileLessThan(String value) {
            addCriterion("np_file <", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileLessThanOrEqualTo(String value) {
            addCriterion("np_file <=", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileLike(String value) {
            addCriterion("np_file like", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileNotLike(String value) {
            addCriterion("np_file not like", value, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileIn(List<String> values) {
            addCriterion("np_file in", values, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileNotIn(List<String> values) {
            addCriterion("np_file not in", values, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileBetween(String value1, String value2) {
            addCriterion("np_file between", value1, value2, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpFileNotBetween(String value1, String value2) {
            addCriterion("np_file not between", value1, value2, "npFile");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeIsNull() {
            addCriterion("np_work_time is null");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeIsNotNull() {
            addCriterion("np_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time =", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time <>", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("np_work_time >", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time >=", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("np_work_time <", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("np_work_time <=", value, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("np_work_time in", values, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("np_work_time not in", values, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("np_work_time between", value1, value2, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andNpWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("np_work_time not between", value1, value2, "npWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdIsNull() {
            addCriterion("lp_relate_id is null");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdIsNotNull() {
            addCriterion("lp_relate_id is not null");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdEqualTo(Integer value) {
            addCriterion("lp_relate_id =", value, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdNotEqualTo(Integer value) {
            addCriterion("lp_relate_id <>", value, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdGreaterThan(Integer value) {
            addCriterion("lp_relate_id >", value, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lp_relate_id >=", value, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdLessThan(Integer value) {
            addCriterion("lp_relate_id <", value, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdLessThanOrEqualTo(Integer value) {
            addCriterion("lp_relate_id <=", value, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdIn(List<Integer> values) {
            addCriterion("lp_relate_id in", values, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdNotIn(List<Integer> values) {
            addCriterion("lp_relate_id not in", values, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdBetween(Integer value1, Integer value2) {
            addCriterion("lp_relate_id between", value1, value2, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpRelateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lp_relate_id not between", value1, value2, "lpRelateId");
            return (Criteria) this;
        }

        public Criteria andLpIdIsNull() {
            addCriterion("lp_id is null");
            return (Criteria) this;
        }

        public Criteria andLpIdIsNotNull() {
            addCriterion("lp_id is not null");
            return (Criteria) this;
        }

        public Criteria andLpIdEqualTo(Integer value) {
            addCriterion("lp_id =", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdNotEqualTo(Integer value) {
            addCriterion("lp_id <>", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdGreaterThan(Integer value) {
            addCriterion("lp_id >", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lp_id >=", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdLessThan(Integer value) {
            addCriterion("lp_id <", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdLessThanOrEqualTo(Integer value) {
            addCriterion("lp_id <=", value, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdIn(List<Integer> values) {
            addCriterion("lp_id in", values, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdNotIn(List<Integer> values) {
            addCriterion("lp_id not in", values, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdBetween(Integer value1, Integer value2) {
            addCriterion("lp_id between", value1, value2, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lp_id not between", value1, value2, "lpId");
            return (Criteria) this;
        }

        public Criteria andLpFileNameIsNull() {
            addCriterion("lp_file_name is null");
            return (Criteria) this;
        }

        public Criteria andLpFileNameIsNotNull() {
            addCriterion("lp_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andLpFileNameEqualTo(String value) {
            addCriterion("lp_file_name =", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameNotEqualTo(String value) {
            addCriterion("lp_file_name <>", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameGreaterThan(String value) {
            addCriterion("lp_file_name >", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("lp_file_name >=", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameLessThan(String value) {
            addCriterion("lp_file_name <", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameLessThanOrEqualTo(String value) {
            addCriterion("lp_file_name <=", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameLike(String value) {
            addCriterion("lp_file_name like", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameNotLike(String value) {
            addCriterion("lp_file_name not like", value, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameIn(List<String> values) {
            addCriterion("lp_file_name in", values, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameNotIn(List<String> values) {
            addCriterion("lp_file_name not in", values, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameBetween(String value1, String value2) {
            addCriterion("lp_file_name between", value1, value2, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileNameNotBetween(String value1, String value2) {
            addCriterion("lp_file_name not between", value1, value2, "lpFileName");
            return (Criteria) this;
        }

        public Criteria andLpFileIsNull() {
            addCriterion("lp_file is null");
            return (Criteria) this;
        }

        public Criteria andLpFileIsNotNull() {
            addCriterion("lp_file is not null");
            return (Criteria) this;
        }

        public Criteria andLpFileEqualTo(String value) {
            addCriterion("lp_file =", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileNotEqualTo(String value) {
            addCriterion("lp_file <>", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileGreaterThan(String value) {
            addCriterion("lp_file >", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileGreaterThanOrEqualTo(String value) {
            addCriterion("lp_file >=", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileLessThan(String value) {
            addCriterion("lp_file <", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileLessThanOrEqualTo(String value) {
            addCriterion("lp_file <=", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileLike(String value) {
            addCriterion("lp_file like", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileNotLike(String value) {
            addCriterion("lp_file not like", value, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileIn(List<String> values) {
            addCriterion("lp_file in", values, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileNotIn(List<String> values) {
            addCriterion("lp_file not in", values, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileBetween(String value1, String value2) {
            addCriterion("lp_file between", value1, value2, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpFileNotBetween(String value1, String value2) {
            addCriterion("lp_file not between", value1, value2, "lpFile");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIsNull() {
            addCriterion("lp_work_time is null");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIsNotNull() {
            addCriterion("lp_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time =", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time <>", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("lp_work_time >", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time >=", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("lp_work_time <", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time <=", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("lp_work_time in", values, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("lp_work_time not in", values, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lp_work_time between", value1, value2, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lp_work_time not between", value1, value2, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdIsNull() {
            addCriterion("s_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdIsNotNull() {
            addCriterion("s_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdEqualTo(Integer value) {
            addCriterion("s_dispatch_id =", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdNotEqualTo(Integer value) {
            addCriterion("s_dispatch_id <>", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdGreaterThan(Integer value) {
            addCriterion("s_dispatch_id >", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("s_dispatch_id >=", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdLessThan(Integer value) {
            addCriterion("s_dispatch_id <", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("s_dispatch_id <=", value, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdIn(List<Integer> values) {
            addCriterion("s_dispatch_id in", values, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdNotIn(List<Integer> values) {
            addCriterion("s_dispatch_id not in", values, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("s_dispatch_id between", value1, value2, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("s_dispatch_id not between", value1, value2, "sDispatchId");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIsNull() {
            addCriterion("s_work_time is null");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIsNotNull() {
            addCriterion("s_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time =", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time <>", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("s_work_time >", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time >=", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("s_work_time <", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time <=", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("s_work_time in", values, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("s_work_time not in", values, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("s_work_time between", value1, value2, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("s_work_time not between", value1, value2, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdIsNull() {
            addCriterion("e_dispatch_id is null");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdIsNotNull() {
            addCriterion("e_dispatch_id is not null");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdEqualTo(Integer value) {
            addCriterion("e_dispatch_id =", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdNotEqualTo(Integer value) {
            addCriterion("e_dispatch_id <>", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdGreaterThan(Integer value) {
            addCriterion("e_dispatch_id >", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("e_dispatch_id >=", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdLessThan(Integer value) {
            addCriterion("e_dispatch_id <", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("e_dispatch_id <=", value, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdIn(List<Integer> values) {
            addCriterion("e_dispatch_id in", values, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdNotIn(List<Integer> values) {
            addCriterion("e_dispatch_id not in", values, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdBetween(Integer value1, Integer value2) {
            addCriterion("e_dispatch_id between", value1, value2, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEDispatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("e_dispatch_id not between", value1, value2, "eDispatchId");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeIsNull() {
            addCriterion("e_work_time is null");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeIsNotNull() {
            addCriterion("e_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeEqualTo(Date value) {
            addCriterion("e_work_time =", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeNotEqualTo(Date value) {
            addCriterion("e_work_time <>", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeGreaterThan(Date value) {
            addCriterion("e_work_time >", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("e_work_time >=", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeLessThan(Date value) {
            addCriterion("e_work_time <", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeLessThanOrEqualTo(Date value) {
            addCriterion("e_work_time <=", value, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeIn(List<Date> values) {
            addCriterion("e_work_time in", values, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeNotIn(List<Date> values) {
            addCriterion("e_work_time not in", values, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeBetween(Date value1, Date value2) {
            addCriterion("e_work_time between", value1, value2, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andEWorkTimeNotBetween(Date value1, Date value2) {
            addCriterion("e_work_time not between", value1, value2, "eWorkTime");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeIsNull() {
            addCriterion("admin_level_code is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeIsNotNull() {
            addCriterion("admin_level_code is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeEqualTo(String value) {
            addCriterion("admin_level_code =", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotEqualTo(String value) {
            addCriterion("admin_level_code <>", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeGreaterThan(String value) {
            addCriterion("admin_level_code >", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("admin_level_code >=", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeLessThan(String value) {
            addCriterion("admin_level_code <", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeLessThanOrEqualTo(String value) {
            addCriterion("admin_level_code <=", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeLike(String value) {
            addCriterion("admin_level_code like", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotLike(String value) {
            addCriterion("admin_level_code not like", value, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeIn(List<String> values) {
            addCriterion("admin_level_code in", values, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotIn(List<String> values) {
            addCriterion("admin_level_code not in", values, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeBetween(String value1, String value2) {
            addCriterion("admin_level_code between", value1, value2, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelCodeNotBetween(String value1, String value2) {
            addCriterion("admin_level_code not between", value1, value2, "adminLevelCode");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameIsNull() {
            addCriterion("admin_level_name is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameIsNotNull() {
            addCriterion("admin_level_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameEqualTo(String value) {
            addCriterion("admin_level_name =", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotEqualTo(String value) {
            addCriterion("admin_level_name <>", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameGreaterThan(String value) {
            addCriterion("admin_level_name >", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameGreaterThanOrEqualTo(String value) {
            addCriterion("admin_level_name >=", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameLessThan(String value) {
            addCriterion("admin_level_name <", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameLessThanOrEqualTo(String value) {
            addCriterion("admin_level_name <=", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameLike(String value) {
            addCriterion("admin_level_name like", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotLike(String value) {
            addCriterion("admin_level_name not like", value, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameIn(List<String> values) {
            addCriterion("admin_level_name in", values, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotIn(List<String> values) {
            addCriterion("admin_level_name not in", values, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameBetween(String value1, String value2) {
            addCriterion("admin_level_name between", value1, value2, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNameNotBetween(String value1, String value2) {
            addCriterion("admin_level_name not between", value1, value2, "adminLevelName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrIsNull() {
            addCriterion("max_ce_edu_attr is null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrIsNotNull() {
            addCriterion("max_ce_edu_attr is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrEqualTo(String value) {
            addCriterion("max_ce_edu_attr =", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotEqualTo(String value) {
            addCriterion("max_ce_edu_attr <>", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrGreaterThan(String value) {
            addCriterion("max_ce_edu_attr >", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrGreaterThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_attr >=", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrLessThan(String value) {
            addCriterion("max_ce_edu_attr <", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrLessThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_attr <=", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrLike(String value) {
            addCriterion("max_ce_edu_attr like", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotLike(String value) {
            addCriterion("max_ce_edu_attr not like", value, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrIn(List<String> values) {
            addCriterion("max_ce_edu_attr in", values, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotIn(List<String> values) {
            addCriterion("max_ce_edu_attr not in", values, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrBetween(String value1, String value2) {
            addCriterion("max_ce_edu_attr between", value1, value2, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduAttrNotBetween(String value1, String value2) {
            addCriterion("max_ce_edu_attr not between", value1, value2, "maxCeEduAttr");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeIsNull() {
            addCriterion("max_ce_edu_code is null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeIsNotNull() {
            addCriterion("max_ce_edu_code is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeEqualTo(String value) {
            addCriterion("max_ce_edu_code =", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotEqualTo(String value) {
            addCriterion("max_ce_edu_code <>", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeGreaterThan(String value) {
            addCriterion("max_ce_edu_code >", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeGreaterThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_code >=", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeLessThan(String value) {
            addCriterion("max_ce_edu_code <", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeLessThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_code <=", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeLike(String value) {
            addCriterion("max_ce_edu_code like", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotLike(String value) {
            addCriterion("max_ce_edu_code not like", value, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeIn(List<String> values) {
            addCriterion("max_ce_edu_code in", values, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotIn(List<String> values) {
            addCriterion("max_ce_edu_code not in", values, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeBetween(String value1, String value2) {
            addCriterion("max_ce_edu_code between", value1, value2, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduCodeNotBetween(String value1, String value2) {
            addCriterion("max_ce_edu_code not between", value1, value2, "maxCeEduCode");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameIsNull() {
            addCriterion("max_ce_edu_name is null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameIsNotNull() {
            addCriterion("max_ce_edu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameEqualTo(String value) {
            addCriterion("max_ce_edu_name =", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotEqualTo(String value) {
            addCriterion("max_ce_edu_name <>", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameGreaterThan(String value) {
            addCriterion("max_ce_edu_name >", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameGreaterThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_name >=", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameLessThan(String value) {
            addCriterion("max_ce_edu_name <", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameLessThanOrEqualTo(String value) {
            addCriterion("max_ce_edu_name <=", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameLike(String value) {
            addCriterion("max_ce_edu_name like", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotLike(String value) {
            addCriterion("max_ce_edu_name not like", value, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameIn(List<String> values) {
            addCriterion("max_ce_edu_name in", values, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotIn(List<String> values) {
            addCriterion("max_ce_edu_name not in", values, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameBetween(String value1, String value2) {
            addCriterion("max_ce_edu_name between", value1, value2, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andMaxCeEduNameNotBetween(String value1, String value2) {
            addCriterion("max_ce_edu_name not between", value1, value2, "maxCeEduName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNull() {
            addCriterion("unit_name is null");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNotNull() {
            addCriterion("unit_name is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNameEqualTo(String value) {
            addCriterion("unit_name =", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotEqualTo(String value) {
            addCriterion("unit_name <>", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThan(String value) {
            addCriterion("unit_name >", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("unit_name >=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThan(String value) {
            addCriterion("unit_name <", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThanOrEqualTo(String value) {
            addCriterion("unit_name <=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLike(String value) {
            addCriterion("unit_name like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotLike(String value) {
            addCriterion("unit_name not like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIn(List<String> values) {
            addCriterion("unit_name in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotIn(List<String> values) {
            addCriterion("unit_name not in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameBetween(String value1, String value2) {
            addCriterion("unit_name between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotBetween(String value1, String value2) {
            addCriterion("unit_name not between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNull() {
            addCriterion("unit_type_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNotNull() {
            addCriterion("unit_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdEqualTo(Integer value) {
            addCriterion("unit_type_id =", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotEqualTo(Integer value) {
            addCriterion("unit_type_id <>", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThan(Integer value) {
            addCriterion("unit_type_id >", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id >=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThan(Integer value) {
            addCriterion("unit_type_id <", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id <=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIn(List<Integer> values) {
            addCriterion("unit_type_id in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotIn(List<Integer> values) {
            addCriterion("unit_type_id not in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id not between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIsNull() {
            addCriterion("unit_type_code is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIsNotNull() {
            addCriterion("unit_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeEqualTo(String value) {
            addCriterion("unit_type_code =", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotEqualTo(String value) {
            addCriterion("unit_type_code <>", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeGreaterThan(String value) {
            addCriterion("unit_type_code >", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unit_type_code >=", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLessThan(String value) {
            addCriterion("unit_type_code <", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("unit_type_code <=", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLike(String value) {
            addCriterion("unit_type_code like", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotLike(String value) {
            addCriterion("unit_type_code not like", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIn(List<String> values) {
            addCriterion("unit_type_code in", values, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotIn(List<String> values) {
            addCriterion("unit_type_code not in", values, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeBetween(String value1, String value2) {
            addCriterion("unit_type_code between", value1, value2, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotBetween(String value1, String value2) {
            addCriterion("unit_type_code not between", value1, value2, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameIsNull() {
            addCriterion("unit_type_name is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameIsNotNull() {
            addCriterion("unit_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameEqualTo(String value) {
            addCriterion("unit_type_name =", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotEqualTo(String value) {
            addCriterion("unit_type_name <>", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameGreaterThan(String value) {
            addCriterion("unit_type_name >", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("unit_type_name >=", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameLessThan(String value) {
            addCriterion("unit_type_name <", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameLessThanOrEqualTo(String value) {
            addCriterion("unit_type_name <=", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameLike(String value) {
            addCriterion("unit_type_name like", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotLike(String value) {
            addCriterion("unit_type_name not like", value, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameIn(List<String> values) {
            addCriterion("unit_type_name in", values, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotIn(List<String> values) {
            addCriterion("unit_type_name not in", values, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameBetween(String value1, String value2) {
            addCriterion("unit_type_name between", value1, value2, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeNameNotBetween(String value1, String value2) {
            addCriterion("unit_type_name not between", value1, value2, "unitTypeName");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrIsNull() {
            addCriterion("unit_type_attr is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrIsNotNull() {
            addCriterion("unit_type_attr is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrEqualTo(String value) {
            addCriterion("unit_type_attr =", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotEqualTo(String value) {
            addCriterion("unit_type_attr <>", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrGreaterThan(String value) {
            addCriterion("unit_type_attr >", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrGreaterThanOrEqualTo(String value) {
            addCriterion("unit_type_attr >=", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrLessThan(String value) {
            addCriterion("unit_type_attr <", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrLessThanOrEqualTo(String value) {
            addCriterion("unit_type_attr <=", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrLike(String value) {
            addCriterion("unit_type_attr like", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotLike(String value) {
            addCriterion("unit_type_attr not like", value, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrIn(List<String> values) {
            addCriterion("unit_type_attr in", values, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotIn(List<String> values) {
            addCriterion("unit_type_attr not in", values, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrBetween(String value1, String value2) {
            addCriterion("unit_type_attr between", value1, value2, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andUnitTypeAttrNotBetween(String value1, String value2) {
            addCriterion("unit_type_attr not between", value1, value2, "unitTypeAttr");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthIsNull() {
            addCriterion("verify_birth is null");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthIsNotNull() {
            addCriterion("verify_birth is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthEqualTo(Date value) {
            addCriterionForJDBCDate("verify_birth =", value, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("verify_birth <>", value, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("verify_birth >", value, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verify_birth >=", value, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthLessThan(Date value) {
            addCriterionForJDBCDate("verify_birth <", value, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verify_birth <=", value, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthIn(List<Date> values) {
            addCriterionForJDBCDate("verify_birth in", values, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("verify_birth not in", values, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verify_birth between", value1, value2, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verify_birth not between", value1, value2, "verifyBirth");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeIsNull() {
            addCriterion("verify_work_time is null");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeIsNotNull() {
            addCriterion("verify_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time =", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time <>", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("verify_work_time >", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time >=", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("verify_work_time <", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("verify_work_time <=", value, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("verify_work_time in", values, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("verify_work_time not in", values, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verify_work_time between", value1, value2, "verifyWorkTime");
            return (Criteria) this;
        }

        public Criteria andVerifyWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("verify_work_time not between", value1, value2, "verifyWorkTime");
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