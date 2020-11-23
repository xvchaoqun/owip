package domain.cet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CetTraineeViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTraineeViewExample() {
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

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(Byte value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(Byte value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(Byte value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(Byte value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(Byte value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<Byte> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<Byte> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(Byte value1, Byte value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectIsNull() {
            addCriterion("is_party_project is null");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectIsNotNull() {
            addCriterion("is_party_project is not null");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectEqualTo(Boolean value) {
            addCriterion("is_party_project =", value, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectNotEqualTo(Boolean value) {
            addCriterion("is_party_project <>", value, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectGreaterThan(Boolean value) {
            addCriterion("is_party_project >", value, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_party_project >=", value, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectLessThan(Boolean value) {
            addCriterion("is_party_project <", value, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectLessThanOrEqualTo(Boolean value) {
            addCriterion("is_party_project <=", value, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectIn(List<Boolean> values) {
            addCriterion("is_party_project in", values, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectNotIn(List<Boolean> values) {
            addCriterion("is_party_project not in", values, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectBetween(Boolean value1, Boolean value2) {
            addCriterion("is_party_project between", value1, value2, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andIsPartyProjectNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_party_project not between", value1, value2, "isPartyProject");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdIsNull() {
            addCriterion("cet_party_id is null");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdIsNotNull() {
            addCriterion("cet_party_id is not null");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdEqualTo(Integer value) {
            addCriterion("cet_party_id =", value, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdNotEqualTo(Integer value) {
            addCriterion("cet_party_id <>", value, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdGreaterThan(Integer value) {
            addCriterion("cet_party_id >", value, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cet_party_id >=", value, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdLessThan(Integer value) {
            addCriterion("cet_party_id <", value, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("cet_party_id <=", value, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdIn(List<Integer> values) {
            addCriterion("cet_party_id in", values, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdNotIn(List<Integer> values) {
            addCriterion("cet_party_id not in", values, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("cet_party_id between", value1, value2, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andCetPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cet_party_id not between", value1, value2, "cetPartyId");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNull() {
            addCriterion("project_status is null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNotNull() {
            addCriterion("project_status is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusEqualTo(Byte value) {
            addCriterion("project_status =", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotEqualTo(Byte value) {
            addCriterion("project_status <>", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThan(Byte value) {
            addCriterion("project_status >", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("project_status >=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThan(Byte value) {
            addCriterion("project_status <", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThanOrEqualTo(Byte value) {
            addCriterion("project_status <=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIn(List<Byte> values) {
            addCriterion("project_status in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotIn(List<Byte> values) {
            addCriterion("project_status not in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusBetween(Byte value1, Byte value2) {
            addCriterion("project_status between", value1, value2, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("project_status not between", value1, value2, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedIsNull() {
            addCriterion("project_is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedIsNotNull() {
            addCriterion("project_is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedEqualTo(Boolean value) {
            addCriterion("project_is_deleted =", value, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedNotEqualTo(Boolean value) {
            addCriterion("project_is_deleted <>", value, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedGreaterThan(Boolean value) {
            addCriterion("project_is_deleted >", value, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("project_is_deleted >=", value, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedLessThan(Boolean value) {
            addCriterion("project_is_deleted <", value, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("project_is_deleted <=", value, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedIn(List<Boolean> values) {
            addCriterion("project_is_deleted in", values, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedNotIn(List<Boolean> values) {
            addCriterion("project_is_deleted not in", values, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("project_is_deleted between", value1, value2, "projectIsDeleted");
            return (Criteria) this;
        }

        public Criteria andProjectIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("project_is_deleted not between", value1, value2, "projectIsDeleted");
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

        public Criteria andCourseCountEqualTo(Long value) {
            addCriterion("course_count =", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotEqualTo(Long value) {
            addCriterion("course_count <>", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountGreaterThan(Long value) {
            addCriterion("course_count >", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountGreaterThanOrEqualTo(Long value) {
            addCriterion("course_count >=", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountLessThan(Long value) {
            addCriterion("course_count <", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountLessThanOrEqualTo(Long value) {
            addCriterion("course_count <=", value, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountIn(List<Long> values) {
            addCriterion("course_count in", values, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotIn(List<Long> values) {
            addCriterion("course_count not in", values, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountBetween(Long value1, Long value2) {
            addCriterion("course_count between", value1, value2, "courseCount");
            return (Criteria) this;
        }

        public Criteria andCourseCountNotBetween(Long value1, Long value2) {
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

        public Criteria andFinishCountEqualTo(BigDecimal value) {
            addCriterion("finish_count =", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotEqualTo(BigDecimal value) {
            addCriterion("finish_count <>", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThan(BigDecimal value) {
            addCriterion("finish_count >", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_count >=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThan(BigDecimal value) {
            addCriterion("finish_count <", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_count <=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIn(List<BigDecimal> values) {
            addCriterion("finish_count in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotIn(List<BigDecimal> values) {
            addCriterion("finish_count not in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_count between", value1, value2, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_count not between", value1, value2, "finishCount");
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

        public Criteria andOnlineFinishPeriodIsNull() {
            addCriterion("online_finish_period is null");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodIsNotNull() {
            addCriterion("online_finish_period is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodEqualTo(BigDecimal value) {
            addCriterion("online_finish_period =", value, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodNotEqualTo(BigDecimal value) {
            addCriterion("online_finish_period <>", value, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodGreaterThan(BigDecimal value) {
            addCriterion("online_finish_period >", value, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("online_finish_period >=", value, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodLessThan(BigDecimal value) {
            addCriterion("online_finish_period <", value, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("online_finish_period <=", value, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodIn(List<BigDecimal> values) {
            addCriterion("online_finish_period in", values, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodNotIn(List<BigDecimal> values) {
            addCriterion("online_finish_period not in", values, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("online_finish_period between", value1, value2, "onlineFinishPeriod");
            return (Criteria) this;
        }

        public Criteria andOnlineFinishPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("online_finish_period not between", value1, value2, "onlineFinishPeriod");
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