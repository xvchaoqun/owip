package domain.pcs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PcsPartyViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PcsPartyViewExample() {
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andBranchCountIsNull() {
            addCriterion("branch_count is null");
            return (Criteria) this;
        }

        public Criteria andBranchCountIsNotNull() {
            addCriterion("branch_count is not null");
            return (Criteria) this;
        }

        public Criteria andBranchCountEqualTo(Long value) {
            addCriterion("branch_count =", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotEqualTo(Long value) {
            addCriterion("branch_count <>", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountGreaterThan(Long value) {
            addCriterion("branch_count >", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountGreaterThanOrEqualTo(Long value) {
            addCriterion("branch_count >=", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountLessThan(Long value) {
            addCriterion("branch_count <", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountLessThanOrEqualTo(Long value) {
            addCriterion("branch_count <=", value, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountIn(List<Long> values) {
            addCriterion("branch_count in", values, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotIn(List<Long> values) {
            addCriterion("branch_count not in", values, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountBetween(Long value1, Long value2) {
            addCriterion("branch_count between", value1, value2, "branchCount");
            return (Criteria) this;
        }

        public Criteria andBranchCountNotBetween(Long value1, Long value2) {
            addCriterion("branch_count not between", value1, value2, "branchCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNull() {
            addCriterion("member_count is null");
            return (Criteria) this;
        }

        public Criteria andMemberCountIsNotNull() {
            addCriterion("member_count is not null");
            return (Criteria) this;
        }

        public Criteria andMemberCountEqualTo(Long value) {
            addCriterion("member_count =", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotEqualTo(Long value) {
            addCriterion("member_count <>", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThan(Long value) {
            addCriterion("member_count >", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountGreaterThanOrEqualTo(Long value) {
            addCriterion("member_count >=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThan(Long value) {
            addCriterion("member_count <", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountLessThanOrEqualTo(Long value) {
            addCriterion("member_count <=", value, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountIn(List<Long> values) {
            addCriterion("member_count in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotIn(List<Long> values) {
            addCriterion("member_count not in", values, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountBetween(Long value1, Long value2) {
            addCriterion("member_count between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andMemberCountNotBetween(Long value1, Long value2) {
            addCriterion("member_count not between", value1, value2, "memberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIsNull() {
            addCriterion("student_member_count is null");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIsNotNull() {
            addCriterion("student_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountEqualTo(BigDecimal value) {
            addCriterion("student_member_count =", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotEqualTo(BigDecimal value) {
            addCriterion("student_member_count <>", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountGreaterThan(BigDecimal value) {
            addCriterion("student_member_count >", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("student_member_count >=", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountLessThan(BigDecimal value) {
            addCriterion("student_member_count <", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("student_member_count <=", value, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountIn(List<BigDecimal> values) {
            addCriterion("student_member_count in", values, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotIn(List<BigDecimal> values) {
            addCriterion("student_member_count not in", values, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_member_count between", value1, value2, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andStudentMemberCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("student_member_count not between", value1, value2, "studentMemberCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNull() {
            addCriterion("positive_count is null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNotNull() {
            addCriterion("positive_count is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountEqualTo(BigDecimal value) {
            addCriterion("positive_count =", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotEqualTo(BigDecimal value) {
            addCriterion("positive_count <>", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThan(BigDecimal value) {
            addCriterion("positive_count >", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_count >=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThan(BigDecimal value) {
            addCriterion("positive_count <", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("positive_count <=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIn(List<BigDecimal> values) {
            addCriterion("positive_count in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotIn(List<BigDecimal> values) {
            addCriterion("positive_count not in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_count between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("positive_count not between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIsNull() {
            addCriterion("teacher_member_count is null");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIsNotNull() {
            addCriterion("teacher_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count =", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count <>", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountGreaterThan(BigDecimal value) {
            addCriterion("teacher_member_count >", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count >=", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountLessThan(BigDecimal value) {
            addCriterion("teacher_member_count <", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("teacher_member_count <=", value, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountIn(List<BigDecimal> values) {
            addCriterion("teacher_member_count in", values, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotIn(List<BigDecimal> values) {
            addCriterion("teacher_member_count not in", values, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_member_count between", value1, value2, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andTeacherMemberCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("teacher_member_count not between", value1, value2, "teacherMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIsNull() {
            addCriterion("retire_member_count is null");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIsNotNull() {
            addCriterion("retire_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountEqualTo(BigDecimal value) {
            addCriterion("retire_member_count =", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotEqualTo(BigDecimal value) {
            addCriterion("retire_member_count <>", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountGreaterThan(BigDecimal value) {
            addCriterion("retire_member_count >", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_member_count >=", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountLessThan(BigDecimal value) {
            addCriterion("retire_member_count <", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("retire_member_count <=", value, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountIn(List<BigDecimal> values) {
            addCriterion("retire_member_count in", values, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotIn(List<BigDecimal> values) {
            addCriterion("retire_member_count not in", values, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_member_count between", value1, value2, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andRetireMemberCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("retire_member_count not between", value1, value2, "retireMemberCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountIsNull() {
            addCriterion("group_count is null");
            return (Criteria) this;
        }

        public Criteria andGroupCountIsNotNull() {
            addCriterion("group_count is not null");
            return (Criteria) this;
        }

        public Criteria andGroupCountEqualTo(Long value) {
            addCriterion("group_count =", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountNotEqualTo(Long value) {
            addCriterion("group_count <>", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountGreaterThan(Long value) {
            addCriterion("group_count >", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountGreaterThanOrEqualTo(Long value) {
            addCriterion("group_count >=", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountLessThan(Long value) {
            addCriterion("group_count <", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountLessThanOrEqualTo(Long value) {
            addCriterion("group_count <=", value, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountIn(List<Long> values) {
            addCriterion("group_count in", values, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountNotIn(List<Long> values) {
            addCriterion("group_count not in", values, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountBetween(Long value1, Long value2) {
            addCriterion("group_count between", value1, value2, "groupCount");
            return (Criteria) this;
        }

        public Criteria andGroupCountNotBetween(Long value1, Long value2) {
            addCriterion("group_count not between", value1, value2, "groupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountIsNull() {
            addCriterion("present_group_count is null");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountIsNotNull() {
            addCriterion("present_group_count is not null");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountEqualTo(Long value) {
            addCriterion("present_group_count =", value, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountNotEqualTo(Long value) {
            addCriterion("present_group_count <>", value, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountGreaterThan(Long value) {
            addCriterion("present_group_count >", value, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountGreaterThanOrEqualTo(Long value) {
            addCriterion("present_group_count >=", value, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountLessThan(Long value) {
            addCriterion("present_group_count <", value, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountLessThanOrEqualTo(Long value) {
            addCriterion("present_group_count <=", value, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountIn(List<Long> values) {
            addCriterion("present_group_count in", values, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountNotIn(List<Long> values) {
            addCriterion("present_group_count not in", values, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountBetween(Long value1, Long value2) {
            addCriterion("present_group_count between", value1, value2, "presentGroupCount");
            return (Criteria) this;
        }

        public Criteria andPresentGroupCountNotBetween(Long value1, Long value2) {
            addCriterion("present_group_count not between", value1, value2, "presentGroupCount");
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