package domain.cet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CetTraineeCourseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTraineeCourseExample() {
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