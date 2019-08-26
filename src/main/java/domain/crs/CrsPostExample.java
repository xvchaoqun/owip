package domain.crs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CrsPostExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrsPostExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andJobIsNull() {
            addCriterion("job is null");
            return (Criteria) this;
        }

        public Criteria andJobIsNotNull() {
            addCriterion("job is not null");
            return (Criteria) this;
        }

        public Criteria andJobEqualTo(String value) {
            addCriterion("job =", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotEqualTo(String value) {
            addCriterion("job <>", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThan(String value) {
            addCriterion("job >", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThanOrEqualTo(String value) {
            addCriterion("job >=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThan(String value) {
            addCriterion("job <", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThanOrEqualTo(String value) {
            addCriterion("job <=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLike(String value) {
            addCriterion("job like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotLike(String value) {
            addCriterion("job not like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobIn(List<String> values) {
            addCriterion("job in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotIn(List<String> values) {
            addCriterion("job not in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobBetween(String value1, String value2) {
            addCriterion("job between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotBetween(String value1, String value2) {
            addCriterion("job not between", value1, value2, "job");
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

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNoticeIsNull() {
            addCriterion("notice is null");
            return (Criteria) this;
        }

        public Criteria andNoticeIsNotNull() {
            addCriterion("notice is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeEqualTo(String value) {
            addCriterion("notice =", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotEqualTo(String value) {
            addCriterion("notice <>", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeGreaterThan(String value) {
            addCriterion("notice >", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeGreaterThanOrEqualTo(String value) {
            addCriterion("notice >=", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLessThan(String value) {
            addCriterion("notice <", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLessThanOrEqualTo(String value) {
            addCriterion("notice <=", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeLike(String value) {
            addCriterion("notice like", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotLike(String value) {
            addCriterion("notice not like", value, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeIn(List<String> values) {
            addCriterion("notice in", values, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotIn(List<String> values) {
            addCriterion("notice not in", values, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeBetween(String value1, String value2) {
            addCriterion("notice between", value1, value2, "notice");
            return (Criteria) this;
        }

        public Criteria andNoticeNotBetween(String value1, String value2) {
            addCriterion("notice not between", value1, value2, "notice");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdIsNull() {
            addCriterion("post_require_id is null");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdIsNotNull() {
            addCriterion("post_require_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdEqualTo(Integer value) {
            addCriterion("post_require_id =", value, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdNotEqualTo(Integer value) {
            addCriterion("post_require_id <>", value, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdGreaterThan(Integer value) {
            addCriterion("post_require_id >", value, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_require_id >=", value, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdLessThan(Integer value) {
            addCriterion("post_require_id <", value, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_require_id <=", value, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdIn(List<Integer> values) {
            addCriterion("post_require_id in", values, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdNotIn(List<Integer> values) {
            addCriterion("post_require_id not in", values, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdBetween(Integer value1, Integer value2) {
            addCriterion("post_require_id between", value1, value2, "postRequireId");
            return (Criteria) this;
        }

        public Criteria andPostRequireIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_require_id not between", value1, value2, "postRequireId");
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
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
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
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIsNull() {
            addCriterion("enroll_status is null");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIsNotNull() {
            addCriterion("enroll_status is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusEqualTo(Byte value) {
            addCriterion("enroll_status =", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotEqualTo(Byte value) {
            addCriterion("enroll_status <>", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusGreaterThan(Byte value) {
            addCriterion("enroll_status >", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("enroll_status >=", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLessThan(Byte value) {
            addCriterion("enroll_status <", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusLessThanOrEqualTo(Byte value) {
            addCriterion("enroll_status <=", value, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusIn(List<Byte> values) {
            addCriterion("enroll_status in", values, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotIn(List<Byte> values) {
            addCriterion("enroll_status not in", values, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusBetween(Byte value1, Byte value2) {
            addCriterion("enroll_status between", value1, value2, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andEnrollStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("enroll_status not between", value1, value2, "enrollStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountIsNull() {
            addCriterion("meeting_apply_count is null");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountIsNotNull() {
            addCriterion("meeting_apply_count is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountEqualTo(Integer value) {
            addCriterion("meeting_apply_count =", value, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountNotEqualTo(Integer value) {
            addCriterion("meeting_apply_count <>", value, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountGreaterThan(Integer value) {
            addCriterion("meeting_apply_count >", value, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("meeting_apply_count >=", value, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountLessThan(Integer value) {
            addCriterion("meeting_apply_count <", value, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountLessThanOrEqualTo(Integer value) {
            addCriterion("meeting_apply_count <=", value, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountIn(List<Integer> values) {
            addCriterion("meeting_apply_count in", values, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountNotIn(List<Integer> values) {
            addCriterion("meeting_apply_count not in", values, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountBetween(Integer value1, Integer value2) {
            addCriterion("meeting_apply_count between", value1, value2, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingApplyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("meeting_apply_count not between", value1, value2, "meetingApplyCount");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNull() {
            addCriterion("meeting_time is null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIsNotNull() {
            addCriterion("meeting_time is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeEqualTo(Date value) {
            addCriterion("meeting_time =", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotEqualTo(Date value) {
            addCriterion("meeting_time <>", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThan(Date value) {
            addCriterion("meeting_time >", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("meeting_time >=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThan(Date value) {
            addCriterion("meeting_time <", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeLessThanOrEqualTo(Date value) {
            addCriterion("meeting_time <=", value, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeIn(List<Date> values) {
            addCriterion("meeting_time in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotIn(List<Date> values) {
            addCriterion("meeting_time not in", values, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeBetween(Date value1, Date value2) {
            addCriterion("meeting_time between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingTimeNotBetween(Date value1, Date value2) {
            addCriterion("meeting_time not between", value1, value2, "meetingTime");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressIsNull() {
            addCriterion("meeting_address is null");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressIsNotNull() {
            addCriterion("meeting_address is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressEqualTo(String value) {
            addCriterion("meeting_address =", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotEqualTo(String value) {
            addCriterion("meeting_address <>", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressGreaterThan(String value) {
            addCriterion("meeting_address >", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("meeting_address >=", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressLessThan(String value) {
            addCriterion("meeting_address <", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressLessThanOrEqualTo(String value) {
            addCriterion("meeting_address <=", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressLike(String value) {
            addCriterion("meeting_address like", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotLike(String value) {
            addCriterion("meeting_address not like", value, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressIn(List<String> values) {
            addCriterion("meeting_address in", values, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotIn(List<String> values) {
            addCriterion("meeting_address not in", values, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressBetween(String value1, String value2) {
            addCriterion("meeting_address between", value1, value2, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andMeetingAddressNotBetween(String value1, String value2) {
            addCriterion("meeting_address not between", value1, value2, "meetingAddress");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineIsNull() {
            addCriterion("report_deadline is null");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineIsNotNull() {
            addCriterion("report_deadline is not null");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineEqualTo(Date value) {
            addCriterion("report_deadline =", value, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineNotEqualTo(Date value) {
            addCriterion("report_deadline <>", value, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineGreaterThan(Date value) {
            addCriterion("report_deadline >", value, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("report_deadline >=", value, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineLessThan(Date value) {
            addCriterion("report_deadline <", value, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("report_deadline <=", value, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineIn(List<Date> values) {
            addCriterion("report_deadline in", values, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineNotIn(List<Date> values) {
            addCriterion("report_deadline not in", values, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineBetween(Date value1, Date value2) {
            addCriterion("report_deadline between", value1, value2, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andReportDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("report_deadline not between", value1, value2, "reportDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineIsNull() {
            addCriterion("quit_deadline is null");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineIsNotNull() {
            addCriterion("quit_deadline is not null");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineEqualTo(Date value) {
            addCriterion("quit_deadline =", value, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineNotEqualTo(Date value) {
            addCriterion("quit_deadline <>", value, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineGreaterThan(Date value) {
            addCriterion("quit_deadline >", value, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("quit_deadline >=", value, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineLessThan(Date value) {
            addCriterion("quit_deadline <", value, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("quit_deadline <=", value, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineIn(List<Date> values) {
            addCriterion("quit_deadline in", values, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineNotIn(List<Date> values) {
            addCriterion("quit_deadline not in", values, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineBetween(Date value1, Date value2) {
            addCriterion("quit_deadline between", value1, value2, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andQuitDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("quit_deadline not between", value1, value2, "quitDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineIsNull() {
            addCriterion("ppt_deadline is null");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineIsNotNull() {
            addCriterion("ppt_deadline is not null");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineEqualTo(Date value) {
            addCriterion("ppt_deadline =", value, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineNotEqualTo(Date value) {
            addCriterion("ppt_deadline <>", value, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineGreaterThan(Date value) {
            addCriterion("ppt_deadline >", value, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("ppt_deadline >=", value, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineLessThan(Date value) {
            addCriterion("ppt_deadline <", value, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("ppt_deadline <=", value, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineIn(List<Date> values) {
            addCriterion("ppt_deadline in", values, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineNotIn(List<Date> values) {
            addCriterion("ppt_deadline not in", values, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineBetween(Date value1, Date value2) {
            addCriterion("ppt_deadline between", value1, value2, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("ppt_deadline not between", value1, value2, "pptDeadline");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedIsNull() {
            addCriterion("ppt_upload_closed is null");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedIsNotNull() {
            addCriterion("ppt_upload_closed is not null");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedEqualTo(Boolean value) {
            addCriterion("ppt_upload_closed =", value, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedNotEqualTo(Boolean value) {
            addCriterion("ppt_upload_closed <>", value, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedGreaterThan(Boolean value) {
            addCriterion("ppt_upload_closed >", value, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ppt_upload_closed >=", value, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedLessThan(Boolean value) {
            addCriterion("ppt_upload_closed <", value, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedLessThanOrEqualTo(Boolean value) {
            addCriterion("ppt_upload_closed <=", value, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedIn(List<Boolean> values) {
            addCriterion("ppt_upload_closed in", values, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedNotIn(List<Boolean> values) {
            addCriterion("ppt_upload_closed not in", values, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedBetween(Boolean value1, Boolean value2) {
            addCriterion("ppt_upload_closed between", value1, value2, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andPptUploadClosedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ppt_upload_closed not between", value1, value2, "pptUploadClosed");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusIsNull() {
            addCriterion("meeting_status is null");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusIsNotNull() {
            addCriterion("meeting_status is not null");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusEqualTo(Boolean value) {
            addCriterion("meeting_status =", value, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusNotEqualTo(Boolean value) {
            addCriterion("meeting_status <>", value, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusGreaterThan(Boolean value) {
            addCriterion("meeting_status >", value, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("meeting_status >=", value, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusLessThan(Boolean value) {
            addCriterion("meeting_status <", value, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("meeting_status <=", value, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusIn(List<Boolean> values) {
            addCriterion("meeting_status in", values, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusNotIn(List<Boolean> values) {
            addCriterion("meeting_status not in", values, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("meeting_status between", value1, value2, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andMeetingStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("meeting_status not between", value1, value2, "meetingStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusIsNull() {
            addCriterion("committee_status is null");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusIsNotNull() {
            addCriterion("committee_status is not null");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusEqualTo(Boolean value) {
            addCriterion("committee_status =", value, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusNotEqualTo(Boolean value) {
            addCriterion("committee_status <>", value, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusGreaterThan(Boolean value) {
            addCriterion("committee_status >", value, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("committee_status >=", value, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusLessThan(Boolean value) {
            addCriterion("committee_status <", value, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("committee_status <=", value, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusIn(List<Boolean> values) {
            addCriterion("committee_status in", values, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusNotIn(List<Boolean> values) {
            addCriterion("committee_status not in", values, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("committee_status between", value1, value2, "committeeStatus");
            return (Criteria) this;
        }

        public Criteria andCommitteeStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("committee_status not between", value1, value2, "committeeStatus");
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

        public Criteria andPubStatusIsNull() {
            addCriterion("pub_status is null");
            return (Criteria) this;
        }

        public Criteria andPubStatusIsNotNull() {
            addCriterion("pub_status is not null");
            return (Criteria) this;
        }

        public Criteria andPubStatusEqualTo(Byte value) {
            addCriterion("pub_status =", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotEqualTo(Byte value) {
            addCriterion("pub_status <>", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusGreaterThan(Byte value) {
            addCriterion("pub_status >", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("pub_status >=", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusLessThan(Byte value) {
            addCriterion("pub_status <", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusLessThanOrEqualTo(Byte value) {
            addCriterion("pub_status <=", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusIn(List<Byte> values) {
            addCriterion("pub_status in", values, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotIn(List<Byte> values) {
            addCriterion("pub_status not in", values, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusBetween(Byte value1, Byte value2) {
            addCriterion("pub_status between", value1, value2, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("pub_status not between", value1, value2, "pubStatus");
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

        public Criteria andStatGiveCountIsNull() {
            addCriterion("stat_give_count is null");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountIsNotNull() {
            addCriterion("stat_give_count is not null");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountEqualTo(Integer value) {
            addCriterion("stat_give_count =", value, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountNotEqualTo(Integer value) {
            addCriterion("stat_give_count <>", value, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountGreaterThan(Integer value) {
            addCriterion("stat_give_count >", value, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("stat_give_count >=", value, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountLessThan(Integer value) {
            addCriterion("stat_give_count <", value, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountLessThanOrEqualTo(Integer value) {
            addCriterion("stat_give_count <=", value, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountIn(List<Integer> values) {
            addCriterion("stat_give_count in", values, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountNotIn(List<Integer> values) {
            addCriterion("stat_give_count not in", values, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountBetween(Integer value1, Integer value2) {
            addCriterion("stat_give_count between", value1, value2, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatGiveCountNotBetween(Integer value1, Integer value2) {
            addCriterion("stat_give_count not between", value1, value2, "statGiveCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountIsNull() {
            addCriterion("stat_back_count is null");
            return (Criteria) this;
        }

        public Criteria andStatBackCountIsNotNull() {
            addCriterion("stat_back_count is not null");
            return (Criteria) this;
        }

        public Criteria andStatBackCountEqualTo(Integer value) {
            addCriterion("stat_back_count =", value, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountNotEqualTo(Integer value) {
            addCriterion("stat_back_count <>", value, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountGreaterThan(Integer value) {
            addCriterion("stat_back_count >", value, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("stat_back_count >=", value, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountLessThan(Integer value) {
            addCriterion("stat_back_count <", value, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountLessThanOrEqualTo(Integer value) {
            addCriterion("stat_back_count <=", value, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountIn(List<Integer> values) {
            addCriterion("stat_back_count in", values, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountNotIn(List<Integer> values) {
            addCriterion("stat_back_count not in", values, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountBetween(Integer value1, Integer value2) {
            addCriterion("stat_back_count between", value1, value2, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatBackCountNotBetween(Integer value1, Integer value2) {
            addCriterion("stat_back_count not between", value1, value2, "statBackCount");
            return (Criteria) this;
        }

        public Criteria andStatFileIsNull() {
            addCriterion("stat_file is null");
            return (Criteria) this;
        }

        public Criteria andStatFileIsNotNull() {
            addCriterion("stat_file is not null");
            return (Criteria) this;
        }

        public Criteria andStatFileEqualTo(String value) {
            addCriterion("stat_file =", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileNotEqualTo(String value) {
            addCriterion("stat_file <>", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileGreaterThan(String value) {
            addCriterion("stat_file >", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileGreaterThanOrEqualTo(String value) {
            addCriterion("stat_file >=", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileLessThan(String value) {
            addCriterion("stat_file <", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileLessThanOrEqualTo(String value) {
            addCriterion("stat_file <=", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileLike(String value) {
            addCriterion("stat_file like", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileNotLike(String value) {
            addCriterion("stat_file not like", value, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileIn(List<String> values) {
            addCriterion("stat_file in", values, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileNotIn(List<String> values) {
            addCriterion("stat_file not in", values, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileBetween(String value1, String value2) {
            addCriterion("stat_file between", value1, value2, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileNotBetween(String value1, String value2) {
            addCriterion("stat_file not between", value1, value2, "statFile");
            return (Criteria) this;
        }

        public Criteria andStatFileNameIsNull() {
            addCriterion("stat_file_name is null");
            return (Criteria) this;
        }

        public Criteria andStatFileNameIsNotNull() {
            addCriterion("stat_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andStatFileNameEqualTo(String value) {
            addCriterion("stat_file_name =", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameNotEqualTo(String value) {
            addCriterion("stat_file_name <>", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameGreaterThan(String value) {
            addCriterion("stat_file_name >", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("stat_file_name >=", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameLessThan(String value) {
            addCriterion("stat_file_name <", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameLessThanOrEqualTo(String value) {
            addCriterion("stat_file_name <=", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameLike(String value) {
            addCriterion("stat_file_name like", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameNotLike(String value) {
            addCriterion("stat_file_name not like", value, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameIn(List<String> values) {
            addCriterion("stat_file_name in", values, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameNotIn(List<String> values) {
            addCriterion("stat_file_name not in", values, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameBetween(String value1, String value2) {
            addCriterion("stat_file_name between", value1, value2, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatFileNameNotBetween(String value1, String value2) {
            addCriterion("stat_file_name not between", value1, value2, "statFileName");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNull() {
            addCriterion("stat_date is null");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNotNull() {
            addCriterion("stat_date is not null");
            return (Criteria) this;
        }

        public Criteria andStatDateEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date =", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date <>", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThan(Date value) {
            addCriterionForJDBCDate("stat_date >", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date >=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThan(Date value) {
            addCriterionForJDBCDate("stat_date <", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date <=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateIn(List<Date> values) {
            addCriterionForJDBCDate("stat_date in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("stat_date not in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stat_date between", value1, value2, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stat_date not between", value1, value2, "statDate");
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