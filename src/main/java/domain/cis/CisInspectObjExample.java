package domain.cis;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CisInspectObjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CisInspectObjExample() {
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

        public Criteria andRecordIdIsNull() {
            addCriterion("record_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNotNull() {
            addCriterion("record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdEqualTo(Integer value) {
            addCriterion("record_id =", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotEqualTo(Integer value) {
            addCriterion("record_id <>", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThan(Integer value) {
            addCriterion("record_id >", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_id >=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThan(Integer value) {
            addCriterion("record_id <", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_id <=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIn(List<Integer> values) {
            addCriterion("record_id in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotIn(List<Integer> values) {
            addCriterion("record_id not in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("record_id between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_id not between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdsIsNull() {
            addCriterion("record_ids is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdsIsNotNull() {
            addCriterion("record_ids is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdsEqualTo(String value) {
            addCriterion("record_ids =", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotEqualTo(String value) {
            addCriterion("record_ids <>", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsGreaterThan(String value) {
            addCriterion("record_ids >", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsGreaterThanOrEqualTo(String value) {
            addCriterion("record_ids >=", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsLessThan(String value) {
            addCriterion("record_ids <", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsLessThanOrEqualTo(String value) {
            addCriterion("record_ids <=", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsLike(String value) {
            addCriterion("record_ids like", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotLike(String value) {
            addCriterion("record_ids not like", value, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsIn(List<String> values) {
            addCriterion("record_ids in", values, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotIn(List<String> values) {
            addCriterion("record_ids not in", values, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsBetween(String value1, String value2) {
            addCriterion("record_ids between", value1, value2, "recordIds");
            return (Criteria) this;
        }

        public Criteria andRecordIdsNotBetween(String value1, String value2) {
            addCriterion("record_ids not between", value1, value2, "recordIds");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIsNull() {
            addCriterion("unit_post_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIsNotNull() {
            addCriterion("unit_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdEqualTo(Integer value) {
            addCriterion("unit_post_id =", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotEqualTo(Integer value) {
            addCriterion("unit_post_id <>", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThan(Integer value) {
            addCriterion("unit_post_id >", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id >=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThan(Integer value) {
            addCriterion("unit_post_id <", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_post_id <=", value, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdIn(List<Integer> values) {
            addCriterion("unit_post_id in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotIn(List<Integer> values) {
            addCriterion("unit_post_id not in", values, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id between", value1, value2, "unitPostId");
            return (Criteria) this;
        }

        public Criteria andUnitPostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_post_id not between", value1, value2, "unitPostId");
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

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andInspectDateIsNull() {
            addCriterion("inspect_date is null");
            return (Criteria) this;
        }

        public Criteria andInspectDateIsNotNull() {
            addCriterion("inspect_date is not null");
            return (Criteria) this;
        }

        public Criteria andInspectDateEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date =", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date <>", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateGreaterThan(Date value) {
            addCriterionForJDBCDate("inspect_date >", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date >=", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateLessThan(Date value) {
            addCriterionForJDBCDate("inspect_date <", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date <=", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateIn(List<Date> values) {
            addCriterionForJDBCDate("inspect_date in", values, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("inspect_date not in", values, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("inspect_date between", value1, value2, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("inspect_date not between", value1, value2, "inspectDate");
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

        public Criteria andInspectorTypeIsNull() {
            addCriterion("inspector_type is null");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIsNotNull() {
            addCriterion("inspector_type is not null");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeEqualTo(Byte value) {
            addCriterion("inspector_type =", value, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeNotEqualTo(Byte value) {
            addCriterion("inspector_type <>", value, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeGreaterThan(Byte value) {
            addCriterion("inspector_type >", value, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("inspector_type >=", value, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeLessThan(Byte value) {
            addCriterion("inspector_type <", value, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeLessThanOrEqualTo(Byte value) {
            addCriterion("inspector_type <=", value, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeIn(List<Byte> values) {
            addCriterion("inspector_type in", values, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeNotIn(List<Byte> values) {
            addCriterion("inspector_type not in", values, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeBetween(Byte value1, Byte value2) {
            addCriterion("inspector_type between", value1, value2, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andInspectorTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("inspector_type not between", value1, value2, "inspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeIsNull() {
            addCriterion("other_inspector_type is null");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeIsNotNull() {
            addCriterion("other_inspector_type is not null");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeEqualTo(String value) {
            addCriterion("other_inspector_type =", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeNotEqualTo(String value) {
            addCriterion("other_inspector_type <>", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeGreaterThan(String value) {
            addCriterion("other_inspector_type >", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeGreaterThanOrEqualTo(String value) {
            addCriterion("other_inspector_type >=", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeLessThan(String value) {
            addCriterion("other_inspector_type <", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeLessThanOrEqualTo(String value) {
            addCriterion("other_inspector_type <=", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeLike(String value) {
            addCriterion("other_inspector_type like", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeNotLike(String value) {
            addCriterion("other_inspector_type not like", value, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeIn(List<String> values) {
            addCriterion("other_inspector_type in", values, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeNotIn(List<String> values) {
            addCriterion("other_inspector_type not in", values, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeBetween(String value1, String value2) {
            addCriterion("other_inspector_type between", value1, value2, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andOtherInspectorTypeNotBetween(String value1, String value2) {
            addCriterion("other_inspector_type not between", value1, value2, "otherInspectorType");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdIsNull() {
            addCriterion("chief_inspector_id is null");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdIsNotNull() {
            addCriterion("chief_inspector_id is not null");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdEqualTo(Integer value) {
            addCriterion("chief_inspector_id =", value, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdNotEqualTo(Integer value) {
            addCriterion("chief_inspector_id <>", value, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdGreaterThan(Integer value) {
            addCriterion("chief_inspector_id >", value, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("chief_inspector_id >=", value, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdLessThan(Integer value) {
            addCriterion("chief_inspector_id <", value, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdLessThanOrEqualTo(Integer value) {
            addCriterion("chief_inspector_id <=", value, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdIn(List<Integer> values) {
            addCriterion("chief_inspector_id in", values, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdNotIn(List<Integer> values) {
            addCriterion("chief_inspector_id not in", values, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdBetween(Integer value1, Integer value2) {
            addCriterion("chief_inspector_id between", value1, value2, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andChiefInspectorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("chief_inspector_id not between", value1, value2, "chiefInspectorId");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountIsNull() {
            addCriterion("talk_user_count is null");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountIsNotNull() {
            addCriterion("talk_user_count is not null");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountEqualTo(Integer value) {
            addCriterion("talk_user_count =", value, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountNotEqualTo(Integer value) {
            addCriterion("talk_user_count <>", value, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountGreaterThan(Integer value) {
            addCriterion("talk_user_count >", value, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("talk_user_count >=", value, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountLessThan(Integer value) {
            addCriterion("talk_user_count <", value, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountLessThanOrEqualTo(Integer value) {
            addCriterion("talk_user_count <=", value, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountIn(List<Integer> values) {
            addCriterion("talk_user_count in", values, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountNotIn(List<Integer> values) {
            addCriterion("talk_user_count not in", values, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountBetween(Integer value1, Integer value2) {
            addCriterion("talk_user_count between", value1, value2, "talkUserCount");
            return (Criteria) this;
        }

        public Criteria andTalkUserCountNotBetween(Integer value1, Integer value2) {
            addCriterion("talk_user_count not between", value1, value2, "talkUserCount");
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

        public Criteria andAssignPostIsNull() {
            addCriterion("assign_post is null");
            return (Criteria) this;
        }

        public Criteria andAssignPostIsNotNull() {
            addCriterion("assign_post is not null");
            return (Criteria) this;
        }

        public Criteria andAssignPostEqualTo(String value) {
            addCriterion("assign_post =", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostNotEqualTo(String value) {
            addCriterion("assign_post <>", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostGreaterThan(String value) {
            addCriterion("assign_post >", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostGreaterThanOrEqualTo(String value) {
            addCriterion("assign_post >=", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostLessThan(String value) {
            addCriterion("assign_post <", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostLessThanOrEqualTo(String value) {
            addCriterion("assign_post <=", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostLike(String value) {
            addCriterion("assign_post like", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostNotLike(String value) {
            addCriterion("assign_post not like", value, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostIn(List<String> values) {
            addCriterion("assign_post in", values, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostNotIn(List<String> values) {
            addCriterion("assign_post not in", values, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostBetween(String value1, String value2) {
            addCriterion("assign_post between", value1, value2, "assignPost");
            return (Criteria) this;
        }

        public Criteria andAssignPostNotBetween(String value1, String value2) {
            addCriterion("assign_post not between", value1, value2, "assignPost");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andLogFileIsNull() {
            addCriterion("log_file is null");
            return (Criteria) this;
        }

        public Criteria andLogFileIsNotNull() {
            addCriterion("log_file is not null");
            return (Criteria) this;
        }

        public Criteria andLogFileEqualTo(String value) {
            addCriterion("log_file =", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotEqualTo(String value) {
            addCriterion("log_file <>", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileGreaterThan(String value) {
            addCriterion("log_file >", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileGreaterThanOrEqualTo(String value) {
            addCriterion("log_file >=", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLessThan(String value) {
            addCriterion("log_file <", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLessThanOrEqualTo(String value) {
            addCriterion("log_file <=", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileLike(String value) {
            addCriterion("log_file like", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotLike(String value) {
            addCriterion("log_file not like", value, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileIn(List<String> values) {
            addCriterion("log_file in", values, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotIn(List<String> values) {
            addCriterion("log_file not in", values, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileBetween(String value1, String value2) {
            addCriterion("log_file between", value1, value2, "logFile");
            return (Criteria) this;
        }

        public Criteria andLogFileNotBetween(String value1, String value2) {
            addCriterion("log_file not between", value1, value2, "logFile");
            return (Criteria) this;
        }

        public Criteria andReportIsNull() {
            addCriterion("report is null");
            return (Criteria) this;
        }

        public Criteria andReportIsNotNull() {
            addCriterion("report is not null");
            return (Criteria) this;
        }

        public Criteria andReportEqualTo(String value) {
            addCriterion("report =", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotEqualTo(String value) {
            addCriterion("report <>", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportGreaterThan(String value) {
            addCriterion("report >", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportGreaterThanOrEqualTo(String value) {
            addCriterion("report >=", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportLessThan(String value) {
            addCriterion("report <", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportLessThanOrEqualTo(String value) {
            addCriterion("report <=", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportLike(String value) {
            addCriterion("report like", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotLike(String value) {
            addCriterion("report not like", value, "report");
            return (Criteria) this;
        }

        public Criteria andReportIn(List<String> values) {
            addCriterion("report in", values, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotIn(List<String> values) {
            addCriterion("report not in", values, "report");
            return (Criteria) this;
        }

        public Criteria andReportBetween(String value1, String value2) {
            addCriterion("report between", value1, value2, "report");
            return (Criteria) this;
        }

        public Criteria andReportNotBetween(String value1, String value2) {
            addCriterion("report not between", value1, value2, "report");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNull() {
            addCriterion("record_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIsNotNull() {
            addCriterion("record_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdEqualTo(Integer value) {
            addCriterion("record_user_id =", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotEqualTo(Integer value) {
            addCriterion("record_user_id <>", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThan(Integer value) {
            addCriterion("record_user_id >", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_user_id >=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThan(Integer value) {
            addCriterion("record_user_id <", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("record_user_id <=", value, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdIn(List<Integer> values) {
            addCriterion("record_user_id in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotIn(List<Integer> values) {
            addCriterion("record_user_id not in", values, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdBetween(Integer value1, Integer value2) {
            addCriterion("record_user_id between", value1, value2, "recordUserId");
            return (Criteria) this;
        }

        public Criteria andRecordUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("record_user_id not between", value1, value2, "recordUserId");
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