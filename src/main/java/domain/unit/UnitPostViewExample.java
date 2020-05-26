package domain.unit;

import sys.utils.SqlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UnitPostViewExample {
    protected String orderByClause;
    protected String groupByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UnitPostViewExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public String getGroupByClause() {
        return groupByClause;
    }

    public UnitPostViewExample setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
        return this;
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

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
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

        public Criteria search(String value) {
            addCriterion("(name like '" + SqlUtils.like(value) + "' or code like '" + SqlUtils.like(value)+"')");
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

        public Criteria andIsPrincipalIsNull() {
            addCriterion("is_principal is null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalIsNotNull() {
            addCriterion("is_principal is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalEqualTo(Boolean value) {
            addCriterion("is_principal =", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalNotEqualTo(Boolean value) {
            addCriterion("is_principal <>", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalGreaterThan(Boolean value) {
            addCriterion("is_principal >", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_principal >=", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalLessThan(Boolean value) {
            addCriterion("is_principal <", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalLessThanOrEqualTo(Boolean value) {
            addCriterion("is_principal <=", value, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalIn(List<Boolean> values) {
            addCriterion("is_principal in", values, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalNotIn(List<Boolean> values) {
            addCriterion("is_principal not in", values, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal between", value1, value2, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andIsPrincipalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_principal not between", value1, value2, "isPrincipal");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeIsNull() {
            addCriterion("leader_type is null");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeIsNotNull() {
            addCriterion("leader_type is not null");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeEqualTo(Byte value) {
            addCriterion("leader_type =", value, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeNotEqualTo(Byte value) {
            addCriterion("leader_type <>", value, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeGreaterThan(Byte value) {
            addCriterion("leader_type >", value, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("leader_type >=", value, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeLessThan(Byte value) {
            addCriterion("leader_type <", value, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeLessThanOrEqualTo(Byte value) {
            addCriterion("leader_type <=", value, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeIn(List<Byte> values) {
            addCriterion("leader_type in", values, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeNotIn(List<Byte> values) {
            addCriterion("leader_type not in", values, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeBetween(Byte value1, Byte value2) {
            addCriterion("leader_type between", value1, value2, "leaderType");
            return (Criteria) this;
        }

        public Criteria andLeaderTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("leader_type not between", value1, value2, "leaderType");
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

        public Criteria andPostClassIsNull() {
            addCriterion("post_class is null");
            return (Criteria) this;
        }

        public Criteria andPostClassIsNotNull() {
            addCriterion("post_class is not null");
            return (Criteria) this;
        }

        public Criteria andPostClassEqualTo(Integer value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(Integer value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(Integer value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(Integer value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(Integer value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<Integer> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<Integer> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(Integer value1, Integer value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(Integer value1, Integer value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andIsCpcIsNull() {
            addCriterion("is_cpc is null");
            return (Criteria) this;
        }

        public Criteria andIsCpcIsNotNull() {
            addCriterion("is_cpc is not null");
            return (Criteria) this;
        }

        public Criteria andIsCpcEqualTo(Boolean value) {
            addCriterion("is_cpc =", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcNotEqualTo(Boolean value) {
            addCriterion("is_cpc <>", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcGreaterThan(Boolean value) {
            addCriterion("is_cpc >", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_cpc >=", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcLessThan(Boolean value) {
            addCriterion("is_cpc <", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcLessThanOrEqualTo(Boolean value) {
            addCriterion("is_cpc <=", value, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcIn(List<Boolean> values) {
            addCriterion("is_cpc in", values, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcNotIn(List<Boolean> values) {
            addCriterion("is_cpc not in", values, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cpc between", value1, value2, "isCpc");
            return (Criteria) this;
        }

        public Criteria andIsCpcNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cpc not between", value1, value2, "isCpc");
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

        public Criteria andAbolishDateIsNull() {
            addCriterion("abolish_date is null");
            return (Criteria) this;
        }

        public Criteria andAbolishDateIsNotNull() {
            addCriterion("abolish_date is not null");
            return (Criteria) this;
        }

        public Criteria andAbolishDateEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date =", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date <>", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateGreaterThan(Date value) {
            addCriterionForJDBCDate("abolish_date >", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date >=", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateLessThan(Date value) {
            addCriterionForJDBCDate("abolish_date <", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("abolish_date <=", value, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateIn(List<Date> values) {
            addCriterionForJDBCDate("abolish_date in", values, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("abolish_date not in", values, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abolish_date between", value1, value2, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andAbolishDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("abolish_date not between", value1, value2, "abolishDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateIsNull() {
            addCriterion("open_date is null");
            return (Criteria) this;
        }

        public Criteria andOpenDateIsNotNull() {
            addCriterion("open_date is not null");
            return (Criteria) this;
        }

        public Criteria andOpenDateEqualTo(Date value) {
            addCriterionForJDBCDate("open_date =", value, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("open_date <>", value, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateGreaterThan(Date value) {
            addCriterionForJDBCDate("open_date >", value, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("open_date >=", value, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateLessThan(Date value) {
            addCriterionForJDBCDate("open_date <", value, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("open_date <=", value, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateIn(List<Date> values) {
            addCriterionForJDBCDate("open_date in", values, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("open_date not in", values, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("open_date between", value1, value2, "openDate");
            return (Criteria) this;
        }

        public Criteria andOpenDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("open_date not between", value1, value2, "openDate");
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

        public Criteria andUnitNameIsNull() {
            addCriterion("unit_name is null");
            return (Criteria) this;
        }

        public Criteria andUnitNameIsNotNull() {
            addCriterion("unit_name is not null");
            return (Criteria) this;
        }

        public Criteria andUnitNameEqualTo(String value) {
            addCriterion("unit_name =", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotEqualTo(String value) {
            addCriterion("unit_name <>", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThan(String value) {
            addCriterion("unit_name >", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("unit_name >=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThan(String value) {
            addCriterion("unit_name <", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLessThanOrEqualTo(String value) {
            addCriterion("unit_name <=", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameLike(String value) {
            addCriterion("unit_name like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotLike(String value) {
            addCriterion("unit_name not like", value, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameIn(List<String> values) {
            addCriterion("unit_name in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotIn(List<String> values) {
            addCriterion("unit_name not in", values, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameBetween(String value1, String value2) {
            addCriterion("unit_name between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitNameNotBetween(String value1, String value2) {
            addCriterion("unit_name not between", value1, value2, "unitName");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIsNull() {
            addCriterion("unit_code is null");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIsNotNull() {
            addCriterion("unit_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnitCodeEqualTo(String value) {
            addCriterion("unit_code =", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotEqualTo(String value) {
            addCriterion("unit_code <>", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeGreaterThan(String value) {
            addCriterion("unit_code >", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unit_code >=", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLessThan(String value) {
            addCriterion("unit_code <", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLessThanOrEqualTo(String value) {
            addCriterion("unit_code <=", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeLike(String value) {
            addCriterion("unit_code like", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotLike(String value) {
            addCriterion("unit_code not like", value, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeIn(List<String> values) {
            addCriterion("unit_code in", values, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotIn(List<String> values) {
            addCriterion("unit_code not in", values, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeBetween(String value1, String value2) {
            addCriterion("unit_code between", value1, value2, "unitCode");
            return (Criteria) this;
        }

        public Criteria andUnitCodeNotBetween(String value1, String value2) {
            addCriterion("unit_code not between", value1, value2, "unitCode");
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

        public Criteria andUnitStatusIsNull() {
            addCriterion("unit_status is null");
            return (Criteria) this;
        }

        public Criteria andUnitStatusIsNotNull() {
            addCriterion("unit_status is not null");
            return (Criteria) this;
        }

        public Criteria andUnitStatusEqualTo(Byte value) {
            addCriterion("unit_status =", value, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusNotEqualTo(Byte value) {
            addCriterion("unit_status <>", value, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusGreaterThan(Byte value) {
            addCriterion("unit_status >", value, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("unit_status >=", value, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusLessThan(Byte value) {
            addCriterion("unit_status <", value, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusLessThanOrEqualTo(Byte value) {
            addCriterion("unit_status <=", value, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusIn(List<Byte> values) {
            addCriterion("unit_status in", values, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusNotIn(List<Byte> values) {
            addCriterion("unit_status not in", values, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusBetween(Byte value1, Byte value2) {
            addCriterion("unit_status between", value1, value2, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("unit_status not between", value1, value2, "unitStatus");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderIsNull() {
            addCriterion("unit_sort_order is null");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderIsNotNull() {
            addCriterion("unit_sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderEqualTo(Integer value) {
            addCriterion("unit_sort_order =", value, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderNotEqualTo(Integer value) {
            addCriterion("unit_sort_order <>", value, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderGreaterThan(Integer value) {
            addCriterion("unit_sort_order >", value, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_sort_order >=", value, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderLessThan(Integer value) {
            addCriterion("unit_sort_order <", value, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("unit_sort_order <=", value, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderIn(List<Integer> values) {
            addCriterion("unit_sort_order in", values, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderNotIn(List<Integer> values) {
            addCriterion("unit_sort_order not in", values, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("unit_sort_order between", value1, value2, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andUnitSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_sort_order not between", value1, value2, "unitSortOrder");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("group_name is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("group_name is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("group_name =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("group_name <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("group_name >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("group_name >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("group_name <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("group_name <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("group_name like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("group_name not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("group_name in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("group_name not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("group_name between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("group_name not between", value1, value2, "groupName");
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

        public Criteria andCadrePostIdIsNull() {
            addCriterion("cadre_post_id is null");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdIsNotNull() {
            addCriterion("cadre_post_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdEqualTo(Integer value) {
            addCriterion("cadre_post_id =", value, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdNotEqualTo(Integer value) {
            addCriterion("cadre_post_id <>", value, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdGreaterThan(Integer value) {
            addCriterion("cadre_post_id >", value, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_post_id >=", value, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdLessThan(Integer value) {
            addCriterion("cadre_post_id <", value, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_post_id <=", value, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdIn(List<Integer> values) {
            addCriterion("cadre_post_id in", values, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdNotIn(List<Integer> values) {
            addCriterion("cadre_post_id not in", values, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_post_id between", value1, value2, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCadrePostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_post_id not between", value1, value2, "cadrePostId");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelIsNull() {
            addCriterion("cp_admin_level is null");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelIsNotNull() {
            addCriterion("cp_admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelEqualTo(Integer value) {
            addCriterion("cp_admin_level =", value, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelNotEqualTo(Integer value) {
            addCriterion("cp_admin_level <>", value, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelGreaterThan(Integer value) {
            addCriterion("cp_admin_level >", value, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("cp_admin_level >=", value, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelLessThan(Integer value) {
            addCriterion("cp_admin_level <", value, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("cp_admin_level <=", value, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelIn(List<Integer> values) {
            addCriterion("cp_admin_level in", values, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelNotIn(List<Integer> values) {
            addCriterion("cp_admin_level not in", values, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("cp_admin_level between", value1, value2, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCpAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("cp_admin_level not between", value1, value2, "cpAdminLevel");
            return (Criteria) this;
        }

        public Criteria andIsMainPostIsNull() {
            addCriterion("is_main_post is null");
            return (Criteria) this;
        }

        public Criteria andIsMainPostIsNotNull() {
            addCriterion("is_main_post is not null");
            return (Criteria) this;
        }

        public Criteria andIsMainPostEqualTo(Boolean value) {
            addCriterion("is_main_post =", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostNotEqualTo(Boolean value) {
            addCriterion("is_main_post <>", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostGreaterThan(Boolean value) {
            addCriterion("is_main_post >", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_main_post >=", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostLessThan(Boolean value) {
            addCriterion("is_main_post <", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostLessThanOrEqualTo(Boolean value) {
            addCriterion("is_main_post <=", value, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostIn(List<Boolean> values) {
            addCriterion("is_main_post in", values, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostNotIn(List<Boolean> values) {
            addCriterion("is_main_post not in", values, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostBetween(Boolean value1, Boolean value2) {
            addCriterion("is_main_post between", value1, value2, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andIsMainPostNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_main_post not between", value1, value2, "isMainPost");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Byte value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Byte value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Byte value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Byte value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Byte value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Byte value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Byte> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Byte> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Byte value1, Byte value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Byte value1, Byte value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelIsNull() {
            addCriterion("cadre_admin_level is null");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelIsNotNull() {
            addCriterion("cadre_admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelEqualTo(Integer value) {
            addCriterion("cadre_admin_level =", value, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelNotEqualTo(Integer value) {
            addCriterion("cadre_admin_level <>", value, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelGreaterThan(Integer value) {
            addCriterion("cadre_admin_level >", value, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_admin_level >=", value, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelLessThan(Integer value) {
            addCriterion("cadre_admin_level <", value, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_admin_level <=", value, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelIn(List<Integer> values) {
            addCriterion("cadre_admin_level in", values, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelNotIn(List<Integer> values) {
            addCriterion("cadre_admin_level not in", values, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("cadre_admin_level between", value1, value2, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadreAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_admin_level not between", value1, value2, "cadreAdminLevel");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeIsNull() {
            addCriterion("cadre_post_type is null");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeIsNotNull() {
            addCriterion("cadre_post_type is not null");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeEqualTo(Integer value) {
            addCriterion("cadre_post_type =", value, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeNotEqualTo(Integer value) {
            addCriterion("cadre_post_type <>", value, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeGreaterThan(Integer value) {
            addCriterion("cadre_post_type >", value, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_post_type >=", value, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeLessThan(Integer value) {
            addCriterion("cadre_post_type <", value, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_post_type <=", value, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeIn(List<Integer> values) {
            addCriterion("cadre_post_type in", values, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeNotIn(List<Integer> values) {
            addCriterion("cadre_post_type not in", values, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeBetween(Integer value1, Integer value2) {
            addCriterion("cadre_post_type between", value1, value2, "cadrePostType");
            return (Criteria) this;
        }

        public Criteria andCadrePostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_post_type not between", value1, value2, "cadrePostType");
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

        public Criteria andSWorkTimeIsNull() {
            addCriterion("s_work_time is null");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIsNotNull() {
            addCriterion("s_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time =", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time <>", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("s_work_time >", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time >=", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("s_work_time <", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("s_work_time <=", value, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("s_work_time in", values, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("s_work_time not in", values, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("s_work_time between", value1, value2, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andSWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("s_work_time not between", value1, value2, "sWorkTime");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalIsNull() {
            addCriterion("cadre_is_principal is null");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalIsNotNull() {
            addCriterion("cadre_is_principal is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalEqualTo(Boolean value) {
            addCriterion("cadre_is_principal =", value, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalNotEqualTo(Boolean value) {
            addCriterion("cadre_is_principal <>", value, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalGreaterThan(Boolean value) {
            addCriterion("cadre_is_principal >", value, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("cadre_is_principal >=", value, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalLessThan(Boolean value) {
            addCriterion("cadre_is_principal <", value, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalLessThanOrEqualTo(Boolean value) {
            addCriterion("cadre_is_principal <=", value, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalIn(List<Boolean> values) {
            addCriterion("cadre_is_principal in", values, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalNotIn(List<Boolean> values) {
            addCriterion("cadre_is_principal not in", values, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalBetween(Boolean value1, Boolean value2) {
            addCriterion("cadre_is_principal between", value1, value2, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadreIsPrincipalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("cadre_is_principal not between", value1, value2, "cadreIsPrincipal");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearIsNull() {
            addCriterion("cadre_post_year is null");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearIsNotNull() {
            addCriterion("cadre_post_year is not null");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearEqualTo(Integer value) {
            addCriterion("cadre_post_year =", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearNotEqualTo(Integer value) {
            addCriterion("cadre_post_year <>", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearGreaterThan(Integer value) {
            addCriterion("cadre_post_year >", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_post_year >=", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearLessThan(Integer value) {
            addCriterion("cadre_post_year <", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_post_year <=", value, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearIn(List<Integer> values) {
            addCriterion("cadre_post_year in", values, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearNotIn(List<Integer> values) {
            addCriterion("cadre_post_year not in", values, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearBetween(Integer value1, Integer value2) {
            addCriterion("cadre_post_year between", value1, value2, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andCadrePostYearNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_post_year not between", value1, value2, "cadrePostYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearIsNull() {
            addCriterion("admin_level_year is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearIsNotNull() {
            addCriterion("admin_level_year is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearEqualTo(Integer value) {
            addCriterion("admin_level_year =", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearNotEqualTo(Integer value) {
            addCriterion("admin_level_year <>", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearGreaterThan(Integer value) {
            addCriterion("admin_level_year >", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level_year >=", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearLessThan(Integer value) {
            addCriterion("admin_level_year <", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level_year <=", value, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearIn(List<Integer> values) {
            addCriterion("admin_level_year in", values, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearNotIn(List<Integer> values) {
            addCriterion("admin_level_year not in", values, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearBetween(Integer value1, Integer value2) {
            addCriterion("admin_level_year between", value1, value2, "adminLevelYear");
            return (Criteria) this;
        }

        public Criteria andAdminLevelYearNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level_year not between", value1, value2, "adminLevelYear");
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