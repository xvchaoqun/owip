package domain.cr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrApplicantExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrApplicantExample() {
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

        public Criteria andInfoIdIsNull() {
            addCriterion("info_id is null");
            return (Criteria) this;
        }

        public Criteria andInfoIdIsNotNull() {
            addCriterion("info_id is not null");
            return (Criteria) this;
        }

        public Criteria andInfoIdEqualTo(Integer value) {
            addCriterion("info_id =", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotEqualTo(Integer value) {
            addCriterion("info_id <>", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThan(Integer value) {
            addCriterion("info_id >", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("info_id >=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThan(Integer value) {
            addCriterion("info_id <", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThanOrEqualTo(Integer value) {
            addCriterion("info_id <=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdIn(List<Integer> values) {
            addCriterion("info_id in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotIn(List<Integer> values) {
            addCriterion("info_id not in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdBetween(Integer value1, Integer value2) {
            addCriterion("info_id between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("info_id not between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdIsNull() {
            addCriterion("first_post_id is null");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdIsNotNull() {
            addCriterion("first_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdEqualTo(Integer value) {
            addCriterion("first_post_id =", value, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdNotEqualTo(Integer value) {
            addCriterion("first_post_id <>", value, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdGreaterThan(Integer value) {
            addCriterion("first_post_id >", value, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("first_post_id >=", value, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdLessThan(Integer value) {
            addCriterion("first_post_id <", value, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("first_post_id <=", value, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdIn(List<Integer> values) {
            addCriterion("first_post_id in", values, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdNotIn(List<Integer> values) {
            addCriterion("first_post_id not in", values, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdBetween(Integer value1, Integer value2) {
            addCriterion("first_post_id between", value1, value2, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andFirstPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("first_post_id not between", value1, value2, "firstPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdIsNull() {
            addCriterion("second_post_id is null");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdIsNotNull() {
            addCriterion("second_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdEqualTo(Integer value) {
            addCriterion("second_post_id =", value, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdNotEqualTo(Integer value) {
            addCriterion("second_post_id <>", value, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdGreaterThan(Integer value) {
            addCriterion("second_post_id >", value, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("second_post_id >=", value, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdLessThan(Integer value) {
            addCriterion("second_post_id <", value, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("second_post_id <=", value, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdIn(List<Integer> values) {
            addCriterion("second_post_id in", values, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdNotIn(List<Integer> values) {
            addCriterion("second_post_id not in", values, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdBetween(Integer value1, Integer value2) {
            addCriterion("second_post_id between", value1, value2, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andSecondPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("second_post_id not between", value1, value2, "secondPostId");
            return (Criteria) this;
        }

        public Criteria andEvaIsNull() {
            addCriterion("eva is null");
            return (Criteria) this;
        }

        public Criteria andEvaIsNotNull() {
            addCriterion("eva is not null");
            return (Criteria) this;
        }

        public Criteria andEvaEqualTo(String value) {
            addCriterion("eva =", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaNotEqualTo(String value) {
            addCriterion("eva <>", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaGreaterThan(String value) {
            addCriterion("eva >", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaGreaterThanOrEqualTo(String value) {
            addCriterion("eva >=", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaLessThan(String value) {
            addCriterion("eva <", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaLessThanOrEqualTo(String value) {
            addCriterion("eva <=", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaLike(String value) {
            addCriterion("eva like", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaNotLike(String value) {
            addCriterion("eva not like", value, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaIn(List<String> values) {
            addCriterion("eva in", values, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaNotIn(List<String> values) {
            addCriterion("eva not in", values, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaBetween(String value1, String value2) {
            addCriterion("eva between", value1, value2, "eva");
            return (Criteria) this;
        }

        public Criteria andEvaNotBetween(String value1, String value2) {
            addCriterion("eva not between", value1, value2, "eva");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
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

        public Criteria andFirstCheckStatusIsNull() {
            addCriterion("first_check_status is null");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusIsNotNull() {
            addCriterion("first_check_status is not null");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusEqualTo(Byte value) {
            addCriterion("first_check_status =", value, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusNotEqualTo(Byte value) {
            addCriterion("first_check_status <>", value, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusGreaterThan(Byte value) {
            addCriterion("first_check_status >", value, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("first_check_status >=", value, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusLessThan(Byte value) {
            addCriterion("first_check_status <", value, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("first_check_status <=", value, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusIn(List<Byte> values) {
            addCriterion("first_check_status in", values, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusNotIn(List<Byte> values) {
            addCriterion("first_check_status not in", values, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("first_check_status between", value1, value2, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("first_check_status not between", value1, value2, "firstCheckStatus");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkIsNull() {
            addCriterion("first_check_remark is null");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkIsNotNull() {
            addCriterion("first_check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkEqualTo(String value) {
            addCriterion("first_check_remark =", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkNotEqualTo(String value) {
            addCriterion("first_check_remark <>", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkGreaterThan(String value) {
            addCriterion("first_check_remark >", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("first_check_remark >=", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkLessThan(String value) {
            addCriterion("first_check_remark <", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("first_check_remark <=", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkLike(String value) {
            addCriterion("first_check_remark like", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkNotLike(String value) {
            addCriterion("first_check_remark not like", value, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkIn(List<String> values) {
            addCriterion("first_check_remark in", values, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkNotIn(List<String> values) {
            addCriterion("first_check_remark not in", values, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkBetween(String value1, String value2) {
            addCriterion("first_check_remark between", value1, value2, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andFirstCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("first_check_remark not between", value1, value2, "firstCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusIsNull() {
            addCriterion("second_check_status is null");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusIsNotNull() {
            addCriterion("second_check_status is not null");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusEqualTo(Byte value) {
            addCriterion("second_check_status =", value, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusNotEqualTo(Byte value) {
            addCriterion("second_check_status <>", value, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusGreaterThan(Byte value) {
            addCriterion("second_check_status >", value, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("second_check_status >=", value, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusLessThan(Byte value) {
            addCriterion("second_check_status <", value, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusLessThanOrEqualTo(Byte value) {
            addCriterion("second_check_status <=", value, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusIn(List<Byte> values) {
            addCriterion("second_check_status in", values, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusNotIn(List<Byte> values) {
            addCriterion("second_check_status not in", values, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusBetween(Byte value1, Byte value2) {
            addCriterion("second_check_status between", value1, value2, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("second_check_status not between", value1, value2, "secondCheckStatus");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkIsNull() {
            addCriterion("second_check_remark is null");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkIsNotNull() {
            addCriterion("second_check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkEqualTo(String value) {
            addCriterion("second_check_remark =", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkNotEqualTo(String value) {
            addCriterion("second_check_remark <>", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkGreaterThan(String value) {
            addCriterion("second_check_remark >", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("second_check_remark >=", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkLessThan(String value) {
            addCriterion("second_check_remark <", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("second_check_remark <=", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkLike(String value) {
            addCriterion("second_check_remark like", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkNotLike(String value) {
            addCriterion("second_check_remark not like", value, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkIn(List<String> values) {
            addCriterion("second_check_remark in", values, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkNotIn(List<String> values) {
            addCriterion("second_check_remark not in", values, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkBetween(String value1, String value2) {
            addCriterion("second_check_remark between", value1, value2, "secondCheckRemark");
            return (Criteria) this;
        }

        public Criteria andSecondCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("second_check_remark not between", value1, value2, "secondCheckRemark");
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

        public Criteria andHasSubmitIsNull() {
            addCriterion("has_submit is null");
            return (Criteria) this;
        }

        public Criteria andHasSubmitIsNotNull() {
            addCriterion("has_submit is not null");
            return (Criteria) this;
        }

        public Criteria andHasSubmitEqualTo(Boolean value) {
            addCriterion("has_submit =", value, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitNotEqualTo(Boolean value) {
            addCriterion("has_submit <>", value, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitGreaterThan(Boolean value) {
            addCriterion("has_submit >", value, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_submit >=", value, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitLessThan(Boolean value) {
            addCriterion("has_submit <", value, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitLessThanOrEqualTo(Boolean value) {
            addCriterion("has_submit <=", value, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitIn(List<Boolean> values) {
            addCriterion("has_submit in", values, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitNotIn(List<Boolean> values) {
            addCriterion("has_submit not in", values, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitBetween(Boolean value1, Boolean value2) {
            addCriterion("has_submit between", value1, value2, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andHasSubmitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_submit not between", value1, value2, "hasSubmit");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNull() {
            addCriterion("submit_time is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(Date value) {
            addCriterion("submit_time =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(Date value) {
            addCriterion("submit_time <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(Date value) {
            addCriterion("submit_time >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("submit_time >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(Date value) {
            addCriterion("submit_time <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("submit_time <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<Date> values) {
            addCriterion("submit_time in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<Date> values) {
            addCriterion("submit_time not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("submit_time between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("submit_time not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andHasReportIsNull() {
            addCriterion("has_report is null");
            return (Criteria) this;
        }

        public Criteria andHasReportIsNotNull() {
            addCriterion("has_report is not null");
            return (Criteria) this;
        }

        public Criteria andHasReportEqualTo(Boolean value) {
            addCriterion("has_report =", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotEqualTo(Boolean value) {
            addCriterion("has_report <>", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportGreaterThan(Boolean value) {
            addCriterion("has_report >", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_report >=", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportLessThan(Boolean value) {
            addCriterion("has_report <", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportLessThanOrEqualTo(Boolean value) {
            addCriterion("has_report <=", value, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportIn(List<Boolean> values) {
            addCriterion("has_report in", values, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotIn(List<Boolean> values) {
            addCriterion("has_report not in", values, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportBetween(Boolean value1, Boolean value2) {
            addCriterion("has_report between", value1, value2, "hasReport");
            return (Criteria) this;
        }

        public Criteria andHasReportNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_report not between", value1, value2, "hasReport");
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