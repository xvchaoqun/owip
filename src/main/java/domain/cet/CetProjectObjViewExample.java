package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CetProjectObjViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetProjectObjViewExample() {
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

        public Criteria andWordWriteIsNull() {
            addCriterion("word_write is null");
            return (Criteria) this;
        }

        public Criteria andWordWriteIsNotNull() {
            addCriterion("word_write is not null");
            return (Criteria) this;
        }

        public Criteria andWordWriteEqualTo(String value) {
            addCriterion("word_write =", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteNotEqualTo(String value) {
            addCriterion("word_write <>", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteGreaterThan(String value) {
            addCriterion("word_write >", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteGreaterThanOrEqualTo(String value) {
            addCriterion("word_write >=", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteLessThan(String value) {
            addCriterion("word_write <", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteLessThanOrEqualTo(String value) {
            addCriterion("word_write <=", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteLike(String value) {
            addCriterion("word_write like", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteNotLike(String value) {
            addCriterion("word_write not like", value, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteIn(List<String> values) {
            addCriterion("word_write in", values, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteNotIn(List<String> values) {
            addCriterion("word_write not in", values, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteBetween(String value1, String value2) {
            addCriterion("word_write between", value1, value2, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andWordWriteNotBetween(String value1, String value2) {
            addCriterion("word_write not between", value1, value2, "wordWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteIsNull() {
            addCriterion("pdf_write is null");
            return (Criteria) this;
        }

        public Criteria andPdfWriteIsNotNull() {
            addCriterion("pdf_write is not null");
            return (Criteria) this;
        }

        public Criteria andPdfWriteEqualTo(String value) {
            addCriterion("pdf_write =", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteNotEqualTo(String value) {
            addCriterion("pdf_write <>", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteGreaterThan(String value) {
            addCriterion("pdf_write >", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteGreaterThanOrEqualTo(String value) {
            addCriterion("pdf_write >=", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteLessThan(String value) {
            addCriterion("pdf_write <", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteLessThanOrEqualTo(String value) {
            addCriterion("pdf_write <=", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteLike(String value) {
            addCriterion("pdf_write like", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteNotLike(String value) {
            addCriterion("pdf_write not like", value, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteIn(List<String> values) {
            addCriterion("pdf_write in", values, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteNotIn(List<String> values) {
            addCriterion("pdf_write not in", values, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteBetween(String value1, String value2) {
            addCriterion("pdf_write between", value1, value2, "pdfWrite");
            return (Criteria) this;
        }

        public Criteria andPdfWriteNotBetween(String value1, String value2) {
            addCriterion("pdf_write not between", value1, value2, "pdfWrite");
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

        public Criteria andFinishPeriodIsNull() {
            addCriterion("finish_period is null");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodIsNotNull() {
            addCriterion("finish_period is not null");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodEqualTo(BigDecimal value) {
            addCriterion("finish_period =", value, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodNotEqualTo(BigDecimal value) {
            addCriterion("finish_period <>", value, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodGreaterThan(BigDecimal value) {
            addCriterion("finish_period >", value, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_period >=", value, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodLessThan(BigDecimal value) {
            addCriterion("finish_period <", value, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_period <=", value, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodIn(List<BigDecimal> values) {
            addCriterion("finish_period in", values, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodNotIn(List<BigDecimal> values) {
            addCriterion("finish_period not in", values, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_period between", value1, value2, "finishPeriod");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_period not between", value1, value2, "finishPeriod");
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