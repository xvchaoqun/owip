package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CetTraineeCourseViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTraineeCourseViewExample() {
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

        public Criteria andTraineeIdIsNull() {
            addCriterion("trainee_id is null");
            return (Criteria) this;
        }

        public Criteria andTraineeIdIsNotNull() {
            addCriterion("trainee_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraineeIdEqualTo(Integer value) {
            addCriterion("trainee_id =", value, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdNotEqualTo(Integer value) {
            addCriterion("trainee_id <>", value, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdGreaterThan(Integer value) {
            addCriterion("trainee_id >", value, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trainee_id >=", value, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdLessThan(Integer value) {
            addCriterion("trainee_id <", value, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdLessThanOrEqualTo(Integer value) {
            addCriterion("trainee_id <=", value, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdIn(List<Integer> values) {
            addCriterion("trainee_id in", values, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdNotIn(List<Integer> values) {
            addCriterion("trainee_id not in", values, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdBetween(Integer value1, Integer value2) {
            addCriterion("trainee_id between", value1, value2, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTraineeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trainee_id not between", value1, value2, "traineeId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdIsNull() {
            addCriterion("train_course_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdIsNotNull() {
            addCriterion("train_course_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdEqualTo(Integer value) {
            addCriterion("train_course_id =", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdNotEqualTo(Integer value) {
            addCriterion("train_course_id <>", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdGreaterThan(Integer value) {
            addCriterion("train_course_id >", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_course_id >=", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdLessThan(Integer value) {
            addCriterion("train_course_id <", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("train_course_id <=", value, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdIn(List<Integer> values) {
            addCriterion("train_course_id in", values, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdNotIn(List<Integer> values) {
            addCriterion("train_course_id not in", values, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("train_course_id between", value1, value2, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andTrainCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("train_course_id not between", value1, value2, "trainCourseId");
            return (Criteria) this;
        }

        public Criteria andCanQuitIsNull() {
            addCriterion("can_quit is null");
            return (Criteria) this;
        }

        public Criteria andCanQuitIsNotNull() {
            addCriterion("can_quit is not null");
            return (Criteria) this;
        }

        public Criteria andCanQuitEqualTo(Boolean value) {
            addCriterion("can_quit =", value, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitNotEqualTo(Boolean value) {
            addCriterion("can_quit <>", value, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitGreaterThan(Boolean value) {
            addCriterion("can_quit >", value, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("can_quit >=", value, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitLessThan(Boolean value) {
            addCriterion("can_quit <", value, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitLessThanOrEqualTo(Boolean value) {
            addCriterion("can_quit <=", value, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitIn(List<Boolean> values) {
            addCriterion("can_quit in", values, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitNotIn(List<Boolean> values) {
            addCriterion("can_quit not in", values, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitBetween(Boolean value1, Boolean value2) {
            addCriterion("can_quit between", value1, value2, "canQuit");
            return (Criteria) this;
        }

        public Criteria andCanQuitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("can_quit not between", value1, value2, "canQuit");
            return (Criteria) this;
        }

        public Criteria andIsFinishedIsNull() {
            addCriterion("is_finished is null");
            return (Criteria) this;
        }

        public Criteria andIsFinishedIsNotNull() {
            addCriterion("is_finished is not null");
            return (Criteria) this;
        }

        public Criteria andIsFinishedEqualTo(Boolean value) {
            addCriterion("is_finished =", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedNotEqualTo(Boolean value) {
            addCriterion("is_finished <>", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedGreaterThan(Boolean value) {
            addCriterion("is_finished >", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_finished >=", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedLessThan(Boolean value) {
            addCriterion("is_finished <", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_finished <=", value, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedIn(List<Boolean> values) {
            addCriterion("is_finished in", values, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedNotIn(List<Boolean> values) {
            addCriterion("is_finished not in", values, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_finished between", value1, value2, "isFinished");
            return (Criteria) this;
        }

        public Criteria andIsFinishedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_finished not between", value1, value2, "isFinished");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNull() {
            addCriterion("sign_time is null");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNotNull() {
            addCriterion("sign_time is not null");
            return (Criteria) this;
        }

        public Criteria andSignTimeEqualTo(Date value) {
            addCriterion("sign_time =", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotEqualTo(Date value) {
            addCriterion("sign_time <>", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThan(Date value) {
            addCriterion("sign_time >", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sign_time >=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThan(Date value) {
            addCriterion("sign_time <", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThanOrEqualTo(Date value) {
            addCriterion("sign_time <=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeIn(List<Date> values) {
            addCriterion("sign_time in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotIn(List<Date> values) {
            addCriterion("sign_time not in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeBetween(Date value1, Date value2) {
            addCriterion("sign_time between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotBetween(Date value1, Date value2) {
            addCriterion("sign_time not between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTypeIsNull() {
            addCriterion("sign_type is null");
            return (Criteria) this;
        }

        public Criteria andSignTypeIsNotNull() {
            addCriterion("sign_type is not null");
            return (Criteria) this;
        }

        public Criteria andSignTypeEqualTo(Byte value) {
            addCriterion("sign_type =", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotEqualTo(Byte value) {
            addCriterion("sign_type <>", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThan(Byte value) {
            addCriterion("sign_type >", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("sign_type >=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThan(Byte value) {
            addCriterion("sign_type <", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThanOrEqualTo(Byte value) {
            addCriterion("sign_type <=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeIn(List<Byte> values) {
            addCriterion("sign_type in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotIn(List<Byte> values) {
            addCriterion("sign_type not in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeBetween(Byte value1, Byte value2) {
            addCriterion("sign_type between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("sign_type not between", value1, value2, "signType");
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

        public Criteria andChooseTimeIsNull() {
            addCriterion("choose_time is null");
            return (Criteria) this;
        }

        public Criteria andChooseTimeIsNotNull() {
            addCriterion("choose_time is not null");
            return (Criteria) this;
        }

        public Criteria andChooseTimeEqualTo(Date value) {
            addCriterion("choose_time =", value, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeNotEqualTo(Date value) {
            addCriterion("choose_time <>", value, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeGreaterThan(Date value) {
            addCriterion("choose_time >", value, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("choose_time >=", value, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeLessThan(Date value) {
            addCriterion("choose_time <", value, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeLessThanOrEqualTo(Date value) {
            addCriterion("choose_time <=", value, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeIn(List<Date> values) {
            addCriterion("choose_time in", values, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeNotIn(List<Date> values) {
            addCriterion("choose_time not in", values, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeBetween(Date value1, Date value2) {
            addCriterion("choose_time between", value1, value2, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseTimeNotBetween(Date value1, Date value2) {
            addCriterion("choose_time not between", value1, value2, "chooseTime");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdIsNull() {
            addCriterion("choose_user_id is null");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdIsNotNull() {
            addCriterion("choose_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdEqualTo(Integer value) {
            addCriterion("choose_user_id =", value, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdNotEqualTo(Integer value) {
            addCriterion("choose_user_id <>", value, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdGreaterThan(Integer value) {
            addCriterion("choose_user_id >", value, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("choose_user_id >=", value, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdLessThan(Integer value) {
            addCriterion("choose_user_id <", value, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("choose_user_id <=", value, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdIn(List<Integer> values) {
            addCriterion("choose_user_id in", values, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdNotIn(List<Integer> values) {
            addCriterion("choose_user_id not in", values, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdBetween(Integer value1, Integer value2) {
            addCriterion("choose_user_id between", value1, value2, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andChooseUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("choose_user_id not between", value1, value2, "chooseUserId");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andTrainIdIsNull() {
            addCriterion("train_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainIdIsNotNull() {
            addCriterion("train_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainIdEqualTo(Integer value) {
            addCriterion("train_id =", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotEqualTo(Integer value) {
            addCriterion("train_id <>", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdGreaterThan(Integer value) {
            addCriterion("train_id >", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_id >=", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdLessThan(Integer value) {
            addCriterion("train_id <", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdLessThanOrEqualTo(Integer value) {
            addCriterion("train_id <=", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdIn(List<Integer> values) {
            addCriterion("train_id in", values, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotIn(List<Integer> values) {
            addCriterion("train_id not in", values, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdBetween(Integer value1, Integer value2) {
            addCriterion("train_id between", value1, value2, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("train_id not between", value1, value2, "trainId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdIsNull() {
            addCriterion("trainee_type_id is null");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdIsNotNull() {
            addCriterion("trainee_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdEqualTo(Integer value) {
            addCriterion("trainee_type_id =", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdNotEqualTo(Integer value) {
            addCriterion("trainee_type_id <>", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdGreaterThan(Integer value) {
            addCriterion("trainee_type_id >", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trainee_type_id >=", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdLessThan(Integer value) {
            addCriterion("trainee_type_id <", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("trainee_type_id <=", value, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdIn(List<Integer> values) {
            addCriterion("trainee_type_id in", values, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdNotIn(List<Integer> values) {
            addCriterion("trainee_type_id not in", values, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("trainee_type_id between", value1, value2, "traineeTypeId");
            return (Criteria) this;
        }

        public Criteria andTraineeTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trainee_type_id not between", value1, value2, "traineeTypeId");
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

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(Integer value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(Integer value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(Integer value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(Integer value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<Integer> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<Integer> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(BigDecimal value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(BigDecimal value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(BigDecimal value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(BigDecimal value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<BigDecimal> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<BigDecimal> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period not between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Integer value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Integer value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Integer value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Integer value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Integer> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Integer> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Integer value1, Integer value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameIsNull() {
            addCriterion("choose_user_name is null");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameIsNotNull() {
            addCriterion("choose_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameEqualTo(String value) {
            addCriterion("choose_user_name =", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameNotEqualTo(String value) {
            addCriterion("choose_user_name <>", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameGreaterThan(String value) {
            addCriterion("choose_user_name >", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("choose_user_name >=", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameLessThan(String value) {
            addCriterion("choose_user_name <", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameLessThanOrEqualTo(String value) {
            addCriterion("choose_user_name <=", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameLike(String value) {
            addCriterion("choose_user_name like", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameNotLike(String value) {
            addCriterion("choose_user_name not like", value, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameIn(List<String> values) {
            addCriterion("choose_user_name in", values, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameNotIn(List<String> values) {
            addCriterion("choose_user_name not in", values, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameBetween(String value1, String value2) {
            addCriterion("choose_user_name between", value1, value2, "chooseUserName");
            return (Criteria) this;
        }

        public Criteria andChooseUserNameNotBetween(String value1, String value2) {
            addCriterion("choose_user_name not between", value1, value2, "chooseUserName");
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