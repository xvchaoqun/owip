package domain.dp;

import domain.cadre.CadreWorkExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DpWorkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DpWorkExample() {
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

        public Criteria andFidIsNull() {
            addCriterion("fid is null");
            return (Criteria) this;
        }

        public Criteria andFidIsNotNull() {
            addCriterion("fid is not null");
            return (Criteria) this;
        }

        public Criteria andFidEqualTo(Integer value) {
            addCriterion("fid =", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotEqualTo(Integer value) {
            addCriterion("fid <>", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThan(Integer value) {
            addCriterion("fid >", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidGreaterThanOrEqualTo(Integer value) {
            addCriterion("fid >=", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThan(Integer value) {
            addCriterion("fid <", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidLessThanOrEqualTo(Integer value) {
            addCriterion("fid <=", value, "fid");
            return (Criteria) this;
        }

        public Criteria andFidIn(List<Integer> values) {
            addCriterion("fid in", values, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotIn(List<Integer> values) {
            addCriterion("fid not in", values, "fid");
            return (Criteria) this;
        }

        public Criteria andFidBetween(Integer value1, Integer value2) {
            addCriterion("fid between", value1, value2, "fid");
            return (Criteria) this;
        }

        public Criteria andFidNotBetween(Integer value1, Integer value2) {
            addCriterion("fid not between", value1, value2, "fid");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkIsNull() {
            addCriterion("is_edu_work is null");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkIsNotNull() {
            addCriterion("is_edu_work is not null");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkEqualTo(Boolean value) {
            addCriterion("is_edu_work =", value, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkNotEqualTo(Boolean value) {
            addCriterion("is_edu_work <>", value, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkGreaterThan(Boolean value) {
            addCriterion("is_edu_work >", value, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_edu_work >=", value, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkLessThan(Boolean value) {
            addCriterion("is_edu_work <", value, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkLessThanOrEqualTo(Boolean value) {
            addCriterion("is_edu_work <=", value, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkIn(List<Boolean> values) {
            addCriterion("is_edu_work in", values, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkNotIn(List<Boolean> values) {
            addCriterion("is_edu_work not in", values, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkBetween(Boolean value1, Boolean value2) {
            addCriterion("is_edu_work between", value1, value2, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andIsEduWorkNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_edu_work not between", value1, value2, "isEduWork");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountIsNull() {
            addCriterion("sub_work_count is null");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountIsNotNull() {
            addCriterion("sub_work_count is not null");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountEqualTo(Integer value) {
            addCriterion("sub_work_count =", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountNotEqualTo(Integer value) {
            addCriterion("sub_work_count <>", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountGreaterThan(Integer value) {
            addCriterion("sub_work_count >", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_work_count >=", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountLessThan(Integer value) {
            addCriterion("sub_work_count <", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountLessThanOrEqualTo(Integer value) {
            addCriterion("sub_work_count <=", value, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountIn(List<Integer> values) {
            addCriterion("sub_work_count in", values, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountNotIn(List<Integer> values) {
            addCriterion("sub_work_count not in", values, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountBetween(Integer value1, Integer value2) {
            addCriterion("sub_work_count between", value1, value2, "subWorkCount");
            return (Criteria) this;
        }

        public Criteria andSubWorkCountNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_work_count not between", value1, value2, "subWorkCount");
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

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterionForJDBCDate("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterionForJDBCDate("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterionForJDBCDate("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andDetailIsNull() {
            addCriterion("detail is null");
            return (Criteria) this;
        }

        public Criteria andDetailIsNotNull() {
            addCriterion("detail is not null");
            return (Criteria) this;
        }

        public Criteria andDetailEqualTo(String value) {
            addCriterion("detail =", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotEqualTo(String value) {
            addCriterion("detail <>", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThan(String value) {
            addCriterion("detail >", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThanOrEqualTo(String value) {
            addCriterion("detail >=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThan(String value) {
            addCriterion("detail <", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThanOrEqualTo(String value) {
            addCriterion("detail <=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLike(String value) {
            addCriterion("detail like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotLike(String value) {
            addCriterion("detail not like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailIn(List<String> values) {
            addCriterion("detail in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotIn(List<String> values) {
            addCriterion("detail not in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailBetween(String value1, String value2) {
            addCriterion("detail between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotBetween(String value1, String value2) {
            addCriterion("detail not between", value1, value2, "detail");
            return (Criteria) this;
        }

        public DpWorkExample.Criteria andUnitIdsContain(Integer unitId) {

            addCriterion("find_in_set("+unitId+", unit_ids)");
            return (DpWorkExample.Criteria) this;
        }

        public Criteria andUnitIdsIsNull() {
            addCriterion("unit_ids is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdsIsNotNull() {
            addCriterion("unit_ids is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdsEqualTo(String value) {
            addCriterion("unit_ids =", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsNotEqualTo(String value) {
            addCriterion("unit_ids <>", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsGreaterThan(String value) {
            addCriterion("unit_ids >", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsGreaterThanOrEqualTo(String value) {
            addCriterion("unit_ids >=", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsLessThan(String value) {
            addCriterion("unit_ids <", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsLessThanOrEqualTo(String value) {
            addCriterion("unit_ids <=", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsLike(String value) {
            addCriterion("unit_ids like", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsNotLike(String value) {
            addCriterion("unit_ids not like", value, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsIn(List<String> values) {
            addCriterion("unit_ids in", values, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsNotIn(List<String> values) {
            addCriterion("unit_ids not in", values, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsBetween(String value1, String value2) {
            addCriterion("unit_ids between", value1, value2, "unitIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdsNotBetween(String value1, String value2) {
            addCriterion("unit_ids not between", value1, value2, "unitIds");
            return (Criteria) this;
        }

        public Criteria andWorkTypesIsNull() {
            addCriterion("work_types is null");
            return (Criteria) this;
        }

        public Criteria andWorkTypesIsNotNull() {
            addCriterion("work_types is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTypesEqualTo(String value) {
            addCriterion("work_types =", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesNotEqualTo(String value) {
            addCriterion("work_types <>", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesGreaterThan(String value) {
            addCriterion("work_types >", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesGreaterThanOrEqualTo(String value) {
            addCriterion("work_types >=", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesLessThan(String value) {
            addCriterion("work_types <", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesLessThanOrEqualTo(String value) {
            addCriterion("work_types <=", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesLike(String value) {
            addCriterion("work_types like", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesNotLike(String value) {
            addCriterion("work_types not like", value, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesIn(List<String> values) {
            addCriterion("work_types in", values, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesNotIn(List<String> values) {
            addCriterion("work_types not in", values, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesBetween(String value1, String value2) {
            addCriterion("work_types between", value1, value2, "workTypes");
            return (Criteria) this;
        }

        public Criteria andWorkTypesNotBetween(String value1, String value2) {
            addCriterion("work_types not between", value1, value2, "workTypes");
            return (Criteria) this;
        }

        public Criteria andIsCadreIsNull() {
            addCriterion("is_cadre is null");
            return (Criteria) this;
        }

        public Criteria andIsCadreIsNotNull() {
            addCriterion("is_cadre is not null");
            return (Criteria) this;
        }

        public Criteria andIsCadreEqualTo(Boolean value) {
            addCriterion("is_cadre =", value, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreNotEqualTo(Boolean value) {
            addCriterion("is_cadre <>", value, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreGreaterThan(Boolean value) {
            addCriterion("is_cadre >", value, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_cadre >=", value, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreLessThan(Boolean value) {
            addCriterion("is_cadre <", value, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreLessThanOrEqualTo(Boolean value) {
            addCriterion("is_cadre <=", value, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreIn(List<Boolean> values) {
            addCriterion("is_cadre in", values, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreNotIn(List<Boolean> values) {
            addCriterion("is_cadre not in", values, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cadre between", value1, value2, "isCadre");
            return (Criteria) this;
        }

        public Criteria andIsCadreNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cadre not between", value1, value2, "isCadre");
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