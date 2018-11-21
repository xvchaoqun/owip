package domain.cadre;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadreCompanyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreCompanyExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeOtherIsNull() {
            addCriterion("type_other is null");
            return (Criteria) this;
        }

        public Criteria andTypeOtherIsNotNull() {
            addCriterion("type_other is not null");
            return (Criteria) this;
        }

        public Criteria andTypeOtherEqualTo(String value) {
            addCriterion("type_other =", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherNotEqualTo(String value) {
            addCriterion("type_other <>", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherGreaterThan(String value) {
            addCriterion("type_other >", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherGreaterThanOrEqualTo(String value) {
            addCriterion("type_other >=", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherLessThan(String value) {
            addCriterion("type_other <", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherLessThanOrEqualTo(String value) {
            addCriterion("type_other <=", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherLike(String value) {
            addCriterion("type_other like", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherNotLike(String value) {
            addCriterion("type_other not like", value, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherIn(List<String> values) {
            addCriterion("type_other in", values, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherNotIn(List<String> values) {
            addCriterion("type_other not in", values, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherBetween(String value1, String value2) {
            addCriterion("type_other between", value1, value2, "typeOther");
            return (Criteria) this;
        }

        public Criteria andTypeOtherNotBetween(String value1, String value2) {
            addCriterion("type_other not between", value1, value2, "typeOther");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterionForJDBCDate("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterionForJDBCDate("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterionForJDBCDate("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_time not between", value1, value2, "startTime");
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

        public Criteria andApprovalUnitIsNull() {
            addCriterion("approval_unit is null");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitIsNotNull() {
            addCriterion("approval_unit is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitEqualTo(String value) {
            addCriterion("approval_unit =", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitNotEqualTo(String value) {
            addCriterion("approval_unit <>", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitGreaterThan(String value) {
            addCriterion("approval_unit >", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitGreaterThanOrEqualTo(String value) {
            addCriterion("approval_unit >=", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitLessThan(String value) {
            addCriterion("approval_unit <", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitLessThanOrEqualTo(String value) {
            addCriterion("approval_unit <=", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitLike(String value) {
            addCriterion("approval_unit like", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitNotLike(String value) {
            addCriterion("approval_unit not like", value, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitIn(List<String> values) {
            addCriterion("approval_unit in", values, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitNotIn(List<String> values) {
            addCriterion("approval_unit not in", values, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitBetween(String value1, String value2) {
            addCriterion("approval_unit between", value1, value2, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalUnitNotBetween(String value1, String value2) {
            addCriterion("approval_unit not between", value1, value2, "approvalUnit");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNull() {
            addCriterion("approval_date is null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNotNull() {
            addCriterion("approval_date is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date =", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date <>", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThan(Date value) {
            addCriterionForJDBCDate("approval_date >", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date >=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThan(Date value) {
            addCriterionForJDBCDate("approval_date <", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date <=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIn(List<Date> values) {
            addCriterionForJDBCDate("approval_date in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("approval_date not in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("approval_date between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("approval_date not between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalFileIsNull() {
            addCriterion("approval_file is null");
            return (Criteria) this;
        }

        public Criteria andApprovalFileIsNotNull() {
            addCriterion("approval_file is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalFileEqualTo(String value) {
            addCriterion("approval_file =", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileNotEqualTo(String value) {
            addCriterion("approval_file <>", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileGreaterThan(String value) {
            addCriterion("approval_file >", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileGreaterThanOrEqualTo(String value) {
            addCriterion("approval_file >=", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileLessThan(String value) {
            addCriterion("approval_file <", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileLessThanOrEqualTo(String value) {
            addCriterion("approval_file <=", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileLike(String value) {
            addCriterion("approval_file like", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileNotLike(String value) {
            addCriterion("approval_file not like", value, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileIn(List<String> values) {
            addCriterion("approval_file in", values, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileNotIn(List<String> values) {
            addCriterion("approval_file not in", values, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileBetween(String value1, String value2) {
            addCriterion("approval_file between", value1, value2, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFileNotBetween(String value1, String value2) {
            addCriterion("approval_file not between", value1, value2, "approvalFile");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameIsNull() {
            addCriterion("approval_filename is null");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameIsNotNull() {
            addCriterion("approval_filename is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameEqualTo(String value) {
            addCriterion("approval_filename =", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameNotEqualTo(String value) {
            addCriterion("approval_filename <>", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameGreaterThan(String value) {
            addCriterion("approval_filename >", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("approval_filename >=", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameLessThan(String value) {
            addCriterion("approval_filename <", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameLessThanOrEqualTo(String value) {
            addCriterion("approval_filename <=", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameLike(String value) {
            addCriterion("approval_filename like", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameNotLike(String value) {
            addCriterion("approval_filename not like", value, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameIn(List<String> values) {
            addCriterion("approval_filename in", values, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameNotIn(List<String> values) {
            addCriterion("approval_filename not in", values, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameBetween(String value1, String value2) {
            addCriterion("approval_filename between", value1, value2, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andApprovalFilenameNotBetween(String value1, String value2) {
            addCriterion("approval_filename not between", value1, value2, "approvalFilename");
            return (Criteria) this;
        }

        public Criteria andHasPayIsNull() {
            addCriterion("has_pay is null");
            return (Criteria) this;
        }

        public Criteria andHasPayIsNotNull() {
            addCriterion("has_pay is not null");
            return (Criteria) this;
        }

        public Criteria andHasPayEqualTo(Boolean value) {
            addCriterion("has_pay =", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayNotEqualTo(Boolean value) {
            addCriterion("has_pay <>", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayGreaterThan(Boolean value) {
            addCriterion("has_pay >", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_pay >=", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayLessThan(Boolean value) {
            addCriterion("has_pay <", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayLessThanOrEqualTo(Boolean value) {
            addCriterion("has_pay <=", value, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayIn(List<Boolean> values) {
            addCriterion("has_pay in", values, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayNotIn(List<Boolean> values) {
            addCriterion("has_pay not in", values, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayBetween(Boolean value1, Boolean value2) {
            addCriterion("has_pay between", value1, value2, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasPayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_pay not between", value1, value2, "hasPay");
            return (Criteria) this;
        }

        public Criteria andHasHandIsNull() {
            addCriterion("has_hand is null");
            return (Criteria) this;
        }

        public Criteria andHasHandIsNotNull() {
            addCriterion("has_hand is not null");
            return (Criteria) this;
        }

        public Criteria andHasHandEqualTo(Boolean value) {
            addCriterion("has_hand =", value, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandNotEqualTo(Boolean value) {
            addCriterion("has_hand <>", value, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandGreaterThan(Boolean value) {
            addCriterion("has_hand >", value, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_hand >=", value, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandLessThan(Boolean value) {
            addCriterion("has_hand <", value, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandLessThanOrEqualTo(Boolean value) {
            addCriterion("has_hand <=", value, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandIn(List<Boolean> values) {
            addCriterion("has_hand in", values, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandNotIn(List<Boolean> values) {
            addCriterion("has_hand not in", values, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandBetween(Boolean value1, Boolean value2) {
            addCriterion("has_hand between", value1, value2, "hasHand");
            return (Criteria) this;
        }

        public Criteria andHasHandNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_hand not between", value1, value2, "hasHand");
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