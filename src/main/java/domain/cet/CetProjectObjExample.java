package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CetProjectObjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetProjectObjExample() {
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

        public Criteria andShouldFinishPeriodIsNull() {
            addCriterion("should_finish_period is null");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodIsNotNull() {
            addCriterion("should_finish_period is not null");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodEqualTo(BigDecimal value) {
            addCriterion("should_finish_period =", value, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodNotEqualTo(BigDecimal value) {
            addCriterion("should_finish_period <>", value, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodGreaterThan(BigDecimal value) {
            addCriterion("should_finish_period >", value, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("should_finish_period >=", value, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodLessThan(BigDecimal value) {
            addCriterion("should_finish_period <", value, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("should_finish_period <=", value, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodIn(List<BigDecimal> values) {
            addCriterion("should_finish_period in", values, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodNotIn(List<BigDecimal> values) {
            addCriterion("should_finish_period not in", values, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_finish_period between", value1, value2, "shouldFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andShouldFinishPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("should_finish_period not between", value1, value2, "shouldFinishPeriod");
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

        public Criteria andIsGraduateIsNull() {
            addCriterion("is_graduate is null");
            return (Criteria) this;
        }

        public Criteria andIsGraduateIsNotNull() {
            addCriterion("is_graduate is not null");
            return (Criteria) this;
        }

        public Criteria andIsGraduateEqualTo(Boolean value) {
            addCriterion("is_graduate =", value, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateNotEqualTo(Boolean value) {
            addCriterion("is_graduate <>", value, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateGreaterThan(Boolean value) {
            addCriterion("is_graduate >", value, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_graduate >=", value, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateLessThan(Boolean value) {
            addCriterion("is_graduate <", value, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateLessThanOrEqualTo(Boolean value) {
            addCriterion("is_graduate <=", value, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateIn(List<Boolean> values) {
            addCriterion("is_graduate in", values, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateNotIn(List<Boolean> values) {
            addCriterion("is_graduate not in", values, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateBetween(Boolean value1, Boolean value2) {
            addCriterion("is_graduate between", value1, value2, "isGraduate");
            return (Criteria) this;
        }

        public Criteria andIsGraduateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_graduate not between", value1, value2, "isGraduate");
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

        public Criteria andWriteFilePathIsNull() {
            addCriterion("write_file_path is null");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathIsNotNull() {
            addCriterion("write_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathEqualTo(String value) {
            addCriterion("write_file_path =", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathNotEqualTo(String value) {
            addCriterion("write_file_path <>", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathGreaterThan(String value) {
            addCriterion("write_file_path >", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("write_file_path >=", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathLessThan(String value) {
            addCriterion("write_file_path <", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathLessThanOrEqualTo(String value) {
            addCriterion("write_file_path <=", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathLike(String value) {
            addCriterion("write_file_path like", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathNotLike(String value) {
            addCriterion("write_file_path not like", value, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathIn(List<String> values) {
            addCriterion("write_file_path in", values, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathNotIn(List<String> values) {
            addCriterion("write_file_path not in", values, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathBetween(String value1, String value2) {
            addCriterion("write_file_path between", value1, value2, "writeFilePath");
            return (Criteria) this;
        }

        public Criteria andWriteFilePathNotBetween(String value1, String value2) {
            addCriterion("write_file_path not between", value1, value2, "writeFilePath");
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

        public Criteria andCadreIdIsNull() {
            addCriterion("cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNotNull() {
            addCriterion("cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIdEqualTo(Integer value) {
            addCriterion("cadre_id =", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotEqualTo(Integer value) {
            addCriterion("cadre_id <>", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThan(Integer value) {
            addCriterion("cadre_id >", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_id >=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThan(Integer value) {
            addCriterion("cadre_id <", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_id <=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIn(List<Integer> values) {
            addCriterion("cadre_id in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotIn(List<Integer> values) {
            addCriterion("cadre_id not in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id not between", value1, value2, "cadreId");
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

        public Criteria andDpTypeIdIn(List<Integer> values) {
            addCriterion("dp_type_id in", values, "dpTypeId");
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

        public Criteria andCadreStatusIsNull() {
            addCriterion("cadre_status is null");
            return (Criteria) this;
        }

        public Criteria andCadreStatusIsNotNull() {
            addCriterion("cadre_status is not null");
            return (Criteria) this;
        }

        public Criteria andCadreStatusEqualTo(Byte value) {
            addCriterion("cadre_status =", value, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusNotEqualTo(Byte value) {
            addCriterion("cadre_status <>", value, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusGreaterThan(Byte value) {
            addCriterion("cadre_status >", value, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("cadre_status >=", value, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusLessThan(Byte value) {
            addCriterion("cadre_status <", value, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusLessThanOrEqualTo(Byte value) {
            addCriterion("cadre_status <=", value, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusIn(List<Byte> values) {
            addCriterion("cadre_status in", values, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusNotIn(List<Byte> values) {
            addCriterion("cadre_status not in", values, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusBetween(Byte value1, Byte value2) {
            addCriterion("cadre_status between", value1, value2, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("cadre_status not between", value1, value2, "cadreStatus");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderIsNull() {
            addCriterion("cadre_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderIsNotNull() {
            addCriterion("cadre_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderEqualTo(Integer value) {
            addCriterion("cadre_sort_order =", value, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderNotEqualTo(Integer value) {
            addCriterion("cadre_sort_order <>", value, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderGreaterThan(Integer value) {
            addCriterion("cadre_sort_order >", value, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_sort_order >=", value, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderLessThan(Integer value) {
            addCriterion("cadre_sort_order <", value, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_sort_order <=", value, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderIn(List<Integer> values) {
            addCriterion("cadre_sort_order in", values, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderNotIn(List<Integer> values) {
            addCriterion("cadre_sort_order not in", values, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("cadre_sort_order between", value1, value2, "cadreSortOrder");
            return (Criteria) this;
        }

        public Criteria andCadreSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_sort_order not between", value1, value2, "cadreSortOrder");
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