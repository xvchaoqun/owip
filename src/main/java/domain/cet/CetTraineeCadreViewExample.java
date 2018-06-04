package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CetTraineeCadreViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTraineeCadreViewExample() {
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

        public Criteria andObjIdIsNull() {
            addCriterion("obj_id is null");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNotNull() {
            addCriterion("obj_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjIdEqualTo(Integer value) {
            addCriterion("obj_id =", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotEqualTo(Integer value) {
            addCriterion("obj_id <>", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThan(Integer value) {
            addCriterion("obj_id >", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_id >=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThan(Integer value) {
            addCriterion("obj_id <", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThanOrEqualTo(Integer value) {
            addCriterion("obj_id <=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdIn(List<Integer> values) {
            addCriterion("obj_id in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotIn(List<Integer> values) {
            addCriterion("obj_id not in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdBetween(Integer value1, Integer value2) {
            addCriterion("obj_id between", value1, value2, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_id not between", value1, value2, "objId");
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

        public Criteria andObjIsQuitIsNull() {
            addCriterion("obj_is_quit is null");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitIsNotNull() {
            addCriterion("obj_is_quit is not null");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitEqualTo(Boolean value) {
            addCriterion("obj_is_quit =", value, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitNotEqualTo(Boolean value) {
            addCriterion("obj_is_quit <>", value, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitGreaterThan(Boolean value) {
            addCriterion("obj_is_quit >", value, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("obj_is_quit >=", value, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitLessThan(Boolean value) {
            addCriterion("obj_is_quit <", value, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitLessThanOrEqualTo(Boolean value) {
            addCriterion("obj_is_quit <=", value, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitIn(List<Boolean> values) {
            addCriterion("obj_is_quit in", values, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitNotIn(List<Boolean> values) {
            addCriterion("obj_is_quit not in", values, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitBetween(Boolean value1, Boolean value2) {
            addCriterion("obj_is_quit between", value1, value2, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andObjIsQuitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("obj_is_quit not between", value1, value2, "objIsQuit");
            return (Criteria) this;
        }

        public Criteria andPlanIdIsNull() {
            addCriterion("plan_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanIdIsNotNull() {
            addCriterion("plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanIdEqualTo(Integer value) {
            addCriterion("plan_id =", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotEqualTo(Integer value) {
            addCriterion("plan_id <>", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThan(Integer value) {
            addCriterion("plan_id >", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_id >=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThan(Integer value) {
            addCriterion("plan_id <", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("plan_id <=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdIn(List<Integer> values) {
            addCriterion("plan_id in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotIn(List<Integer> values) {
            addCriterion("plan_id not in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("plan_id between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_id not between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodIsNull() {
            addCriterion("total_period is null");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodIsNotNull() {
            addCriterion("total_period is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodEqualTo(BigDecimal value) {
            addCriterion("total_period =", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodNotEqualTo(BigDecimal value) {
            addCriterion("total_period <>", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodGreaterThan(BigDecimal value) {
            addCriterion("total_period >", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_period >=", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodLessThan(BigDecimal value) {
            addCriterion("total_period <", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_period <=", value, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodIn(List<BigDecimal> values) {
            addCriterion("total_period in", values, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodNotIn(List<BigDecimal> values) {
            addCriterion("total_period not in", values, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_period between", value1, value2, "totalPeriod");
            return (Criteria) this;
        }

        public Criteria andTotalPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_period not between", value1, value2, "totalPeriod");
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

        public Criteria andCourseCountIsNull() {
            addCriterion("course_count is null");
            return (Criteria) this;
        }

        public Criteria andCourseCountIsNotNull() {
            addCriterion("course_count is not null");
            return (Criteria) this;
        }

        public Criteria andCourseCountEqualTo(Integer value) {
            addCriterion("course_count =", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotEqualTo(Integer value) {
            addCriterion("course_count <>", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountGreaterThan(Integer value) {
            addCriterion("course_count >", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_count >=", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountLessThan(Integer value) {
            addCriterion("course_count <", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountLessThanOrEqualTo(Integer value) {
            addCriterion("course_count <=", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountIn(List<Integer> values) {
            addCriterion("course_count in", values, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotIn(List<Integer> values) {
            addCriterion("course_count not in", values, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountBetween(Integer value1, Integer value2) {
            addCriterion("course_count between", value1, value2, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotBetween(Integer value1, Integer value2) {
            addCriterion("course_count not between", value1, value2, "courseCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIsNull() {
            addCriterion("finish_count is null");
            return (Criteria) this;
        }

        public Criteria andFinishCountIsNotNull() {
            addCriterion("finish_count is not null");
            return (Criteria) this;
        }

        public Criteria andFinishCountEqualTo(Integer value) {
            addCriterion("finish_count =", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotEqualTo(Integer value) {
            addCriterion("finish_count <>", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThan(Integer value) {
            addCriterion("finish_count >", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_count >=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThan(Integer value) {
            addCriterion("finish_count <", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThanOrEqualTo(Integer value) {
            addCriterion("finish_count <=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIn(List<Integer> values) {
            addCriterion("finish_count in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotIn(List<Integer> values) {
            addCriterion("finish_count not in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountBetween(Integer value1, Integer value2) {
            addCriterion("finish_count between", value1, value2, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_count not between", value1, value2, "finishCount");
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

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
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