package domain.party;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PartyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PartyExample() {
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

        public Criteria andCodeStartLike(String value) {
            addCriterion("code like", value + "%", "code");
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andShortNameIsNull() {
            addCriterion("short_name is null");
            return (Criteria) this;
        }

        public Criteria andShortNameIsNotNull() {
            addCriterion("short_name is not null");
            return (Criteria) this;
        }

        public Criteria andShortNameEqualTo(String value) {
            addCriterion("short_name =", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotEqualTo(String value) {
            addCriterion("short_name <>", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThan(String value) {
            addCriterion("short_name >", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameGreaterThanOrEqualTo(String value) {
            addCriterion("short_name >=", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThan(String value) {
            addCriterion("short_name <", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLessThanOrEqualTo(String value) {
            addCriterion("short_name <=", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameLike(String value) {
            addCriterion("short_name like", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotLike(String value) {
            addCriterion("short_name not like", value, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameIn(List<String> values) {
            addCriterion("short_name in", values, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotIn(List<String> values) {
            addCriterion("short_name not in", values, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameBetween(String value1, String value2) {
            addCriterion("short_name between", value1, value2, "shortName");
            return (Criteria) this;
        }

        public Criteria andShortNameNotBetween(String value1, String value2) {
            addCriterion("short_name not between", value1, value2, "shortName");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Integer value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Integer value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Integer value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Integer value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Integer> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Integer> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
            return (Criteria) this;
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

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("class_id not between", value1, value2, "classId");
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

        public Criteria andUnitTypeIdIsNull() {
            addCriterion("unit_type_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIsNotNull() {
            addCriterion("unit_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdEqualTo(Integer value) {
            addCriterion("unit_type_id =", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotEqualTo(Integer value) {
            addCriterion("unit_type_id <>", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThan(Integer value) {
            addCriterion("unit_type_id >", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id >=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThan(Integer value) {
            addCriterion("unit_type_id <", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_type_id <=", value, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdIn(List<Integer> values) {
            addCriterion("unit_type_id in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotIn(List<Integer> values) {
            addCriterion("unit_type_id not in", values, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_id not between", value1, value2, "unitTypeId");
            return (Criteria) this;
        }

        public Criteria andBranchTypeIsNull() {
            addCriterion("branch_type is null");
            return (Criteria) this;
        }

        public Criteria andBranchTypeIsNotNull() {
            addCriterion("branch_type is not null");
            return (Criteria) this;
        }

        public Criteria andBranchTypeEqualTo(Integer value) {
            addCriterion("branch_type =", value, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeNotEqualTo(Integer value) {
            addCriterion("branch_type <>", value, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeGreaterThan(Integer value) {
            addCriterion("branch_type >", value, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_type >=", value, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeLessThan(Integer value) {
            addCriterion("branch_type <", value, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeLessThanOrEqualTo(Integer value) {
            addCriterion("branch_type <=", value, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeIn(List<Integer> values) {
            addCriterion("branch_type in", values, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeNotIn(List<Integer> values) {
            addCriterion("branch_type not in", values, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeBetween(Integer value1, Integer value2) {
            addCriterion("branch_type between", value1, value2, "branchType");
            return (Criteria) this;
        }

        public Criteria andBranchTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_type not between", value1, value2, "branchType");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigIsNull() {
            addCriterion("is_enterprise_big is null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigIsNotNull() {
            addCriterion("is_enterprise_big is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigEqualTo(Boolean value) {
            addCriterion("is_enterprise_big =", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigNotEqualTo(Boolean value) {
            addCriterion("is_enterprise_big <>", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigGreaterThan(Boolean value) {
            addCriterion("is_enterprise_big >", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_big >=", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigLessThan(Boolean value) {
            addCriterion("is_enterprise_big <", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigLessThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_big <=", value, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigIn(List<Boolean> values) {
            addCriterion("is_enterprise_big in", values, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigNotIn(List<Boolean> values) {
            addCriterion("is_enterprise_big not in", values, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_big between", value1, value2, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseBigNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_big not between", value1, value2, "isEnterpriseBig");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedIsNull() {
            addCriterion("is_enterprise_nationalized is null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedIsNotNull() {
            addCriterion("is_enterprise_nationalized is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized =", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedNotEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized <>", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedGreaterThan(Boolean value) {
            addCriterion("is_enterprise_nationalized >", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized >=", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedLessThan(Boolean value) {
            addCriterion("is_enterprise_nationalized <", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_enterprise_nationalized <=", value, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedIn(List<Boolean> values) {
            addCriterion("is_enterprise_nationalized in", values, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedNotIn(List<Boolean> values) {
            addCriterion("is_enterprise_nationalized not in", values, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_nationalized between", value1, value2, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseNationalizedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enterprise_nationalized not between", value1, value2, "isEnterpriseNationalized");
            return (Criteria) this;
        }

        public Criteria andIsSeparateIsNull() {
            addCriterion("is_separate is null");
            return (Criteria) this;
        }

        public Criteria andIsSeparateIsNotNull() {
            addCriterion("is_separate is not null");
            return (Criteria) this;
        }

        public Criteria andIsSeparateEqualTo(Boolean value) {
            addCriterion("is_separate =", value, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateNotEqualTo(Boolean value) {
            addCriterion("is_separate <>", value, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateGreaterThan(Boolean value) {
            addCriterion("is_separate >", value, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_separate >=", value, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateLessThan(Boolean value) {
            addCriterion("is_separate <", value, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateLessThanOrEqualTo(Boolean value) {
            addCriterion("is_separate <=", value, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateIn(List<Boolean> values) {
            addCriterion("is_separate in", values, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateNotIn(List<Boolean> values) {
            addCriterion("is_separate not in", values, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateBetween(Boolean value1, Boolean value2) {
            addCriterion("is_separate between", value1, value2, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andIsSeparateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_separate not between", value1, value2, "isSeparate");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andFaxIsNull() {
            addCriterion("fax is null");
            return (Criteria) this;
        }

        public Criteria andFaxIsNotNull() {
            addCriterion("fax is not null");
            return (Criteria) this;
        }

        public Criteria andFaxEqualTo(String value) {
            addCriterion("fax =", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotEqualTo(String value) {
            addCriterion("fax <>", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThan(String value) {
            addCriterion("fax >", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxGreaterThanOrEqualTo(String value) {
            addCriterion("fax >=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThan(String value) {
            addCriterion("fax <", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLessThanOrEqualTo(String value) {
            addCriterion("fax <=", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxLike(String value) {
            addCriterion("fax like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotLike(String value) {
            addCriterion("fax not like", value, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxIn(List<String> values) {
            addCriterion("fax in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotIn(List<String> values) {
            addCriterion("fax not in", values, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxBetween(String value1, String value2) {
            addCriterion("fax between", value1, value2, "fax");
            return (Criteria) this;
        }

        public Criteria andFaxNotBetween(String value1, String value2) {
            addCriterion("fax not between", value1, value2, "fax");
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

        public Criteria andMailboxIsNull() {
            addCriterion("mailbox is null");
            return (Criteria) this;
        }

        public Criteria andMailboxIsNotNull() {
            addCriterion("mailbox is not null");
            return (Criteria) this;
        }

        public Criteria andMailboxEqualTo(String value) {
            addCriterion("mailbox =", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxNotEqualTo(String value) {
            addCriterion("mailbox <>", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxGreaterThan(String value) {
            addCriterion("mailbox >", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxGreaterThanOrEqualTo(String value) {
            addCriterion("mailbox >=", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxLessThan(String value) {
            addCriterion("mailbox <", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxLessThanOrEqualTo(String value) {
            addCriterion("mailbox <=", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxLike(String value) {
            addCriterion("mailbox like", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxNotLike(String value) {
            addCriterion("mailbox not like", value, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxIn(List<String> values) {
            addCriterion("mailbox in", values, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxNotIn(List<String> values) {
            addCriterion("mailbox not in", values, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxBetween(String value1, String value2) {
            addCriterion("mailbox between", value1, value2, "mailbox");
            return (Criteria) this;
        }

        public Criteria andMailboxNotBetween(String value1, String value2) {
            addCriterion("mailbox not between", value1, value2, "mailbox");
            return (Criteria) this;
        }

        public Criteria andFoundTimeIsNull() {
            addCriterion("found_time is null");
            return (Criteria) this;
        }

        public Criteria andFoundTimeIsNotNull() {
            addCriterion("found_time is not null");
            return (Criteria) this;
        }

        public Criteria andFoundTimeEqualTo(Date value) {
            addCriterionForJDBCDate("found_time =", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("found_time <>", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("found_time >", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("found_time >=", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeLessThan(Date value) {
            addCriterionForJDBCDate("found_time <", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("found_time <=", value, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeIn(List<Date> values) {
            addCriterionForJDBCDate("found_time in", values, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("found_time not in", values, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("found_time between", value1, value2, "foundTime");
            return (Criteria) this;
        }

        public Criteria andFoundTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("found_time not between", value1, value2, "foundTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeIsNull() {
            addCriterion("abolish_time is null");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeIsNotNull() {
            addCriterion("abolish_time is not null");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_time =", value, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_time <>", value, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("abolish_time >", value, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_time >=", value, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeLessThan(Date value) {
            addCriterionForJDBCDate("abolish_time <", value, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_time <=", value, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeIn(List<Date> values) {
            addCriterionForJDBCDate("abolish_time in", values, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("abolish_time not in", values, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abolish_time between", value1, value2, "abolishTime");
            return (Criteria) this;
        }

        public Criteria andAbolishTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abolish_time not between", value1, value2, "abolishTime");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andIsPycjIsNull() {
            addCriterion("is_pycj is null");
            return (Criteria) this;
        }

        public Criteria andIsPycjIsNotNull() {
            addCriterion("is_pycj is not null");
            return (Criteria) this;
        }

        public Criteria andIsPycjEqualTo(Boolean value) {
            addCriterion("is_pycj =", value, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjNotEqualTo(Boolean value) {
            addCriterion("is_pycj <>", value, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjGreaterThan(Boolean value) {
            addCriterion("is_pycj >", value, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_pycj >=", value, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjLessThan(Boolean value) {
            addCriterion("is_pycj <", value, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjLessThanOrEqualTo(Boolean value) {
            addCriterion("is_pycj <=", value, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjIn(List<Boolean> values) {
            addCriterion("is_pycj in", values, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjNotIn(List<Boolean> values) {
            addCriterion("is_pycj not in", values, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pycj between", value1, value2, "isPycj");
            return (Criteria) this;
        }

        public Criteria andIsPycjNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pycj not between", value1, value2, "isPycj");
            return (Criteria) this;
        }

        public Criteria andPycjDateIsNull() {
            addCriterion("pycj_date is null");
            return (Criteria) this;
        }

        public Criteria andPycjDateIsNotNull() {
            addCriterion("pycj_date is not null");
            return (Criteria) this;
        }

        public Criteria andPycjDateEqualTo(Date value) {
            addCriterionForJDBCDate("pycj_date =", value, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("pycj_date <>", value, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateGreaterThan(Date value) {
            addCriterionForJDBCDate("pycj_date >", value, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pycj_date >=", value, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateLessThan(Date value) {
            addCriterionForJDBCDate("pycj_date <", value, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pycj_date <=", value, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateIn(List<Date> values) {
            addCriterionForJDBCDate("pycj_date in", values, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("pycj_date not in", values, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pycj_date between", value1, value2, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andPycjDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pycj_date not between", value1, value2, "pycjDate");
            return (Criteria) this;
        }

        public Criteria andIsBgIsNull() {
            addCriterion("is_bg is null");
            return (Criteria) this;
        }

        public Criteria andIsBgIsNotNull() {
            addCriterion("is_bg is not null");
            return (Criteria) this;
        }

        public Criteria andIsBgEqualTo(Boolean value) {
            addCriterion("is_bg =", value, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgNotEqualTo(Boolean value) {
            addCriterion("is_bg <>", value, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgGreaterThan(Boolean value) {
            addCriterion("is_bg >", value, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_bg >=", value, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgLessThan(Boolean value) {
            addCriterion("is_bg <", value, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgLessThanOrEqualTo(Boolean value) {
            addCriterion("is_bg <=", value, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgIn(List<Boolean> values) {
            addCriterion("is_bg in", values, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgNotIn(List<Boolean> values) {
            addCriterion("is_bg not in", values, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgBetween(Boolean value1, Boolean value2) {
            addCriterion("is_bg between", value1, value2, "isBg");
            return (Criteria) this;
        }

        public Criteria andIsBgNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_bg not between", value1, value2, "isBg");
            return (Criteria) this;
        }

        public Criteria andBgDateIsNull() {
            addCriterion("bg_date is null");
            return (Criteria) this;
        }

        public Criteria andBgDateIsNotNull() {
            addCriterion("bg_date is not null");
            return (Criteria) this;
        }

        public Criteria andBgDateEqualTo(Date value) {
            addCriterionForJDBCDate("bg_date =", value, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("bg_date <>", value, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateGreaterThan(Date value) {
            addCriterionForJDBCDate("bg_date >", value, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("bg_date >=", value, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateLessThan(Date value) {
            addCriterionForJDBCDate("bg_date <", value, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("bg_date <=", value, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateIn(List<Date> values) {
            addCriterionForJDBCDate("bg_date in", values, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("bg_date not in", values, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("bg_date between", value1, value2, "bgDate");
            return (Criteria) this;
        }

        public Criteria andBgDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("bg_date not between", value1, value2, "bgDate");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIntegrityIsNull() {
            addCriterion("integrity is null");
            return (Criteria) this;
        }

        public Criteria andIntegrityIsNotNull() {
            addCriterion("integrity is not null");
            return (Criteria) this;
        }

        public Criteria andIntegrityEqualTo(BigDecimal value) {
            addCriterion("integrity =", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityNotEqualTo(BigDecimal value) {
            addCriterion("integrity <>", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityGreaterThan(BigDecimal value) {
            addCriterion("integrity >", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("integrity >=", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityLessThan(BigDecimal value) {
            addCriterion("integrity <", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("integrity <=", value, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityIn(List<BigDecimal> values) {
            addCriterion("integrity in", values, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityNotIn(List<BigDecimal> values) {
            addCriterion("integrity not in", values, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integrity between", value1, value2, "integrity");
            return (Criteria) this;
        }

        public Criteria andIntegrityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integrity not between", value1, value2, "integrity");
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