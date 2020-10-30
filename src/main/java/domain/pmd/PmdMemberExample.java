package domain.pmd;

import org.apache.commons.lang3.StringUtils;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PmdMemberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdMemberExample() {
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

        public Criteria andMonthIdIsNull() {
            addCriterion("month_id is null");
            return (Criteria) this;
        }

        public Criteria andMonthIdIsNotNull() {
            addCriterion("month_id is not null");
            return (Criteria) this;
        }

        public Criteria andMonthIdEqualTo(Integer value) {
            addCriterion("month_id =", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotEqualTo(Integer value) {
            addCriterion("month_id <>", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdGreaterThan(Integer value) {
            addCriterion("month_id >", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("month_id >=", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdLessThan(Integer value) {
            addCriterion("month_id <", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdLessThanOrEqualTo(Integer value) {
            addCriterion("month_id <=", value, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdIn(List<Integer> values) {
            addCriterion("month_id in", values, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotIn(List<Integer> values) {
            addCriterion("month_id not in", values, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdBetween(Integer value1, Integer value2) {
            addCriterion("month_id between", value1, value2, "monthId");
            return (Criteria) this;
        }

        public Criteria andMonthIdNotBetween(Integer value1, Integer value2) {
            addCriterion("month_id not between", value1, value2, "monthId");
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

        public Criteria andPayMonthIsNull() {
            addCriterion("pay_month is null");
            return (Criteria) this;
        }

        public Criteria andPayMonthIsNotNull() {
            addCriterion("pay_month is not null");
            return (Criteria) this;
        }

        public Criteria andPayMonthEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month =", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month <>", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_month >", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month >=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThan(Date value) {
            addCriterionForJDBCDate("pay_month <", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_month <=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthIn(List<Date> values) {
            addCriterionForJDBCDate("pay_month in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_month not in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_month between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_month not between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("party_id is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("party_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Integer value) {
            addCriterion("party_id =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Integer value) {
            addCriterion("party_id <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Integer value) {
            addCriterion("party_id >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_id >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Integer value) {
            addCriterion("party_id <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_id <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Integer> values) {
            addCriterion("party_id in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Integer> values) {
            addCriterion("party_id not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("party_id between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_id not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
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

        public Criteria andConfigMemberTypeIdIsNull() {
            addCriterion("config_member_type_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdIsNotNull() {
            addCriterion("config_member_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdEqualTo(Integer value) {
            addCriterion("config_member_type_id =", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdNotEqualTo(Integer value) {
            addCriterion("config_member_type_id <>", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdGreaterThan(Integer value) {
            addCriterion("config_member_type_id >", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_member_type_id >=", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdLessThan(Integer value) {
            addCriterion("config_member_type_id <", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_member_type_id <=", value, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdIn(List<Integer> values) {
            addCriterion("config_member_type_id in", values, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdNotIn(List<Integer> values) {
            addCriterion("config_member_type_id not in", values, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("config_member_type_id between", value1, value2, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_member_type_id not between", value1, value2, "configMemberTypeId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameIsNull() {
            addCriterion("config_member_type_name is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameIsNotNull() {
            addCriterion("config_member_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameEqualTo(String value) {
            addCriterion("config_member_type_name =", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameNotEqualTo(String value) {
            addCriterion("config_member_type_name <>", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameGreaterThan(String value) {
            addCriterion("config_member_type_name >", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("config_member_type_name >=", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameLessThan(String value) {
            addCriterion("config_member_type_name <", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameLessThanOrEqualTo(String value) {
            addCriterion("config_member_type_name <=", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameLike(String value) {
            addCriterion("config_member_type_name like", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameNotLike(String value) {
            addCriterion("config_member_type_name not like", value, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameIn(List<String> values) {
            addCriterion("config_member_type_name in", values, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameNotIn(List<String> values) {
            addCriterion("config_member_type_name not in", values, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameBetween(String value1, String value2) {
            addCriterion("config_member_type_name between", value1, value2, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNameNotBetween(String value1, String value2) {
            addCriterion("config_member_type_name not between", value1, value2, "configMemberTypeName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdIsNull() {
            addCriterion("config_member_type_norm_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdIsNotNull() {
            addCriterion("config_member_type_norm_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdEqualTo(Integer value) {
            addCriterion("config_member_type_norm_id =", value, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdNotEqualTo(Integer value) {
            addCriterion("config_member_type_norm_id <>", value, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdGreaterThan(Integer value) {
            addCriterion("config_member_type_norm_id >", value, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_member_type_norm_id >=", value, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdLessThan(Integer value) {
            addCriterion("config_member_type_norm_id <", value, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_member_type_norm_id <=", value, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdIn(List<Integer> values) {
            addCriterion("config_member_type_norm_id in", values, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdNotIn(List<Integer> values) {
            addCriterion("config_member_type_norm_id not in", values, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdBetween(Integer value1, Integer value2) {
            addCriterion("config_member_type_norm_id between", value1, value2, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_member_type_norm_id not between", value1, value2, "configMemberTypeNormId");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameIsNull() {
            addCriterion("config_member_type_norm_name is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameIsNotNull() {
            addCriterion("config_member_type_norm_name is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameEqualTo(String value) {
            addCriterion("config_member_type_norm_name =", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameNotEqualTo(String value) {
            addCriterion("config_member_type_norm_name <>", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameGreaterThan(String value) {
            addCriterion("config_member_type_norm_name >", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameGreaterThanOrEqualTo(String value) {
            addCriterion("config_member_type_norm_name >=", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameLessThan(String value) {
            addCriterion("config_member_type_norm_name <", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameLessThanOrEqualTo(String value) {
            addCriterion("config_member_type_norm_name <=", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameLike(String value) {
            addCriterion("config_member_type_norm_name like", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameNotLike(String value) {
            addCriterion("config_member_type_norm_name not like", value, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameIn(List<String> values) {
            addCriterion("config_member_type_norm_name in", values, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameNotIn(List<String> values) {
            addCriterion("config_member_type_norm_name not in", values, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameBetween(String value1, String value2) {
            addCriterion("config_member_type_norm_name between", value1, value2, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberTypeNormNameNotBetween(String value1, String value2) {
            addCriterion("config_member_type_norm_name not between", value1, value2, "configMemberTypeNormName");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayIsNull() {
            addCriterion("config_member_due_pay is null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayIsNotNull() {
            addCriterion("config_member_due_pay is not null");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayEqualTo(BigDecimal value) {
            addCriterion("config_member_due_pay =", value, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayNotEqualTo(BigDecimal value) {
            addCriterion("config_member_due_pay <>", value, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayGreaterThan(BigDecimal value) {
            addCriterion("config_member_due_pay >", value, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("config_member_due_pay >=", value, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayLessThan(BigDecimal value) {
            addCriterion("config_member_due_pay <", value, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("config_member_due_pay <=", value, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayIn(List<BigDecimal> values) {
            addCriterion("config_member_due_pay in", values, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayNotIn(List<BigDecimal> values) {
            addCriterion("config_member_due_pay not in", values, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("config_member_due_pay between", value1, value2, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andConfigMemberDuePayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("config_member_due_pay not between", value1, value2, "configMemberDuePay");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryIsNull() {
            addCriterion("need_set_salary is null");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryIsNotNull() {
            addCriterion("need_set_salary is not null");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryEqualTo(Boolean value) {
            addCriterion("need_set_salary =", value, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryNotEqualTo(Boolean value) {
            addCriterion("need_set_salary <>", value, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryGreaterThan(Boolean value) {
            addCriterion("need_set_salary >", value, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("need_set_salary >=", value, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryLessThan(Boolean value) {
            addCriterion("need_set_salary <", value, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryLessThanOrEqualTo(Boolean value) {
            addCriterion("need_set_salary <=", value, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryIn(List<Boolean> values) {
            addCriterion("need_set_salary in", values, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryNotIn(List<Boolean> values) {
            addCriterion("need_set_salary not in", values, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryBetween(Boolean value1, Boolean value2) {
            addCriterion("need_set_salary between", value1, value2, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andNeedSetSalaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("need_set_salary not between", value1, value2, "needSetSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryIsNull() {
            addCriterion("has_salary is null");
            return (Criteria) this;
        }

        public Criteria andHasSalaryIsNotNull() {
            addCriterion("has_salary is not null");
            return (Criteria) this;
        }

        public Criteria andHasSalaryEqualTo(Boolean value) {
            addCriterion("has_salary =", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryNotEqualTo(Boolean value) {
            addCriterion("has_salary <>", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryGreaterThan(Boolean value) {
            addCriterion("has_salary >", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_salary >=", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryLessThan(Boolean value) {
            addCriterion("has_salary <", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryLessThanOrEqualTo(Boolean value) {
            addCriterion("has_salary <=", value, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryIn(List<Boolean> values) {
            addCriterion("has_salary in", values, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryNotIn(List<Boolean> values) {
            addCriterion("has_salary not in", values, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryBetween(Boolean value1, Boolean value2) {
            addCriterion("has_salary between", value1, value2, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andHasSalaryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_salary not between", value1, value2, "hasSalary");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNull() {
            addCriterion("talent_title is null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIsNotNull() {
            addCriterion("talent_title is not null");
            return (Criteria) this;
        }

        public Criteria andTalentTitleEqualTo(String value) {
            addCriterion("talent_title =", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotEqualTo(String value) {
            addCriterion("talent_title <>", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThan(String value) {
            addCriterion("talent_title >", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleGreaterThanOrEqualTo(String value) {
            addCriterion("talent_title >=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThan(String value) {
            addCriterion("talent_title <", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLessThanOrEqualTo(String value) {
            addCriterion("talent_title <=", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleLike(String value) {
            addCriterion("talent_title like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotLike(String value) {
            addCriterion("talent_title not like", value, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleIn(List<String> values) {
            addCriterion("talent_title in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotIn(List<String> values) {
            addCriterion("talent_title not in", values, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleBetween(String value1, String value2) {
            addCriterion("talent_title between", value1, value2, "talentTitle");
            return (Criteria) this;
        }

        public Criteria andTalentTitleNotBetween(String value1, String value2) {
            addCriterion("talent_title not between", value1, value2, "talentTitle");
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

        public Criteria andPostClassEqualTo(String value) {
            addCriterion("post_class =", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotEqualTo(String value) {
            addCriterion("post_class <>", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThan(String value) {
            addCriterion("post_class >", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassGreaterThanOrEqualTo(String value) {
            addCriterion("post_class >=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThan(String value) {
            addCriterion("post_class <", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLessThanOrEqualTo(String value) {
            addCriterion("post_class <=", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassLike(String value) {
            addCriterion("post_class like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotLike(String value) {
            addCriterion("post_class not like", value, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassIn(List<String> values) {
            addCriterion("post_class in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotIn(List<String> values) {
            addCriterion("post_class not in", values, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassBetween(String value1, String value2) {
            addCriterion("post_class between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andPostClassNotBetween(String value1, String value2) {
            addCriterion("post_class not between", value1, value2, "postClass");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNull() {
            addCriterion("main_post_level is null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIsNotNull() {
            addCriterion("main_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelEqualTo(String value) {
            addCriterion("main_post_level =", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotEqualTo(String value) {
            addCriterion("main_post_level <>", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThan(String value) {
            addCriterion("main_post_level >", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("main_post_level >=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThan(String value) {
            addCriterion("main_post_level <", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLessThanOrEqualTo(String value) {
            addCriterion("main_post_level <=", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelLike(String value) {
            addCriterion("main_post_level like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotLike(String value) {
            addCriterion("main_post_level not like", value, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelIn(List<String> values) {
            addCriterion("main_post_level in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotIn(List<String> values) {
            addCriterion("main_post_level not in", values, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelBetween(String value1, String value2) {
            addCriterion("main_post_level between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andMainPostLevelNotBetween(String value1, String value2) {
            addCriterion("main_post_level not between", value1, value2, "mainPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNull() {
            addCriterion("pro_post_level is null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIsNotNull() {
            addCriterion("pro_post_level is not null");
            return (Criteria) this;
        }

        public Criteria andProPostLevelEqualTo(String value) {
            addCriterion("pro_post_level =", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotEqualTo(String value) {
            addCriterion("pro_post_level <>", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThan(String value) {
            addCriterion("pro_post_level >", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelGreaterThanOrEqualTo(String value) {
            addCriterion("pro_post_level >=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThan(String value) {
            addCriterion("pro_post_level <", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLessThanOrEqualTo(String value) {
            addCriterion("pro_post_level <=", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelLike(String value) {
            addCriterion("pro_post_level like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotLike(String value) {
            addCriterion("pro_post_level not like", value, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelIn(List<String> values) {
            addCriterion("pro_post_level in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotIn(List<String> values) {
            addCriterion("pro_post_level not in", values, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelBetween(String value1, String value2) {
            addCriterion("pro_post_level between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andProPostLevelNotBetween(String value1, String value2) {
            addCriterion("pro_post_level not between", value1, value2, "proPostLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNull() {
            addCriterion("manage_level is null");
            return (Criteria) this;
        }

        public Criteria andManageLevelIsNotNull() {
            addCriterion("manage_level is not null");
            return (Criteria) this;
        }

        public Criteria andManageLevelEqualTo(String value) {
            addCriterion("manage_level =", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotEqualTo(String value) {
            addCriterion("manage_level <>", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThan(String value) {
            addCriterion("manage_level >", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelGreaterThanOrEqualTo(String value) {
            addCriterion("manage_level >=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThan(String value) {
            addCriterion("manage_level <", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLessThanOrEqualTo(String value) {
            addCriterion("manage_level <=", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelLike(String value) {
            addCriterion("manage_level like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotLike(String value) {
            addCriterion("manage_level not like", value, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelIn(List<String> values) {
            addCriterion("manage_level in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotIn(List<String> values) {
            addCriterion("manage_level not in", values, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelBetween(String value1, String value2) {
            addCriterion("manage_level between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andManageLevelNotBetween(String value1, String value2) {
            addCriterion("manage_level not between", value1, value2, "manageLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIsNull() {
            addCriterion("office_level is null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIsNotNull() {
            addCriterion("office_level is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelEqualTo(String value) {
            addCriterion("office_level =", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotEqualTo(String value) {
            addCriterion("office_level <>", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelGreaterThan(String value) {
            addCriterion("office_level >", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelGreaterThanOrEqualTo(String value) {
            addCriterion("office_level >=", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLessThan(String value) {
            addCriterion("office_level <", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLessThanOrEqualTo(String value) {
            addCriterion("office_level <=", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelLike(String value) {
            addCriterion("office_level like", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotLike(String value) {
            addCriterion("office_level not like", value, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelIn(List<String> values) {
            addCriterion("office_level in", values, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotIn(List<String> values) {
            addCriterion("office_level not in", values, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelBetween(String value1, String value2) {
            addCriterion("office_level between", value1, value2, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andOfficeLevelNotBetween(String value1, String value2) {
            addCriterion("office_level not between", value1, value2, "officeLevel");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIsNull() {
            addCriterion("authorized_type is null");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIsNotNull() {
            addCriterion("authorized_type is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeEqualTo(String value) {
            addCriterion("authorized_type =", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotEqualTo(String value) {
            addCriterion("authorized_type <>", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeGreaterThan(String value) {
            addCriterion("authorized_type >", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeGreaterThanOrEqualTo(String value) {
            addCriterion("authorized_type >=", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLessThan(String value) {
            addCriterion("authorized_type <", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLessThanOrEqualTo(String value) {
            addCriterion("authorized_type <=", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeLike(String value) {
            addCriterion("authorized_type like", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotLike(String value) {
            addCriterion("authorized_type not like", value, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeIn(List<String> values) {
            addCriterion("authorized_type in", values, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotIn(List<String> values) {
            addCriterion("authorized_type not in", values, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeBetween(String value1, String value2) {
            addCriterion("authorized_type between", value1, value2, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andAuthorizedTypeNotBetween(String value1, String value2) {
            addCriterion("authorized_type not between", value1, value2, "authorizedType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIsNull() {
            addCriterion("staff_type is null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIsNotNull() {
            addCriterion("staff_type is not null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeEqualTo(String value) {
            addCriterion("staff_type =", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotEqualTo(String value) {
            addCriterion("staff_type <>", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeGreaterThan(String value) {
            addCriterion("staff_type >", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeGreaterThanOrEqualTo(String value) {
            addCriterion("staff_type >=", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLessThan(String value) {
            addCriterion("staff_type <", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLessThanOrEqualTo(String value) {
            addCriterion("staff_type <=", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeLike(String value) {
            addCriterion("staff_type like", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotLike(String value) {
            addCriterion("staff_type not like", value, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIn(List<String> values) {
            addCriterion("staff_type in", values, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotIn(List<String> values) {
            addCriterion("staff_type not in", values, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeBetween(String value1, String value2) {
            addCriterion("staff_type between", value1, value2, "staffType");
            return (Criteria) this;
        }

        public Criteria andStaffTypeNotBetween(String value1, String value2) {
            addCriterion("staff_type not between", value1, value2, "staffType");
            return (Criteria) this;
        }

        public Criteria andSalaryIsNull() {
            addCriterion("salary is null");
            return (Criteria) this;
        }

        public Criteria andSalaryIsNotNull() {
            addCriterion("salary is not null");
            return (Criteria) this;
        }

        public Criteria andSalaryEqualTo(BigDecimal value) {
            addCriterion("salary =", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotEqualTo(BigDecimal value) {
            addCriterion("salary <>", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryGreaterThan(BigDecimal value) {
            addCriterion("salary >", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("salary >=", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLessThan(BigDecimal value) {
            addCriterion("salary <", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLessThanOrEqualTo(BigDecimal value) {
            addCriterion("salary <=", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryIn(List<BigDecimal> values) {
            addCriterion("salary in", values, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotIn(List<BigDecimal> values) {
            addCriterion("salary not in", values, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("salary between", value1, value2, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("salary not between", value1, value2, "salary");
            return (Criteria) this;
        }

        public Criteria andNormIdIsNull() {
            addCriterion("norm_id is null");
            return (Criteria) this;
        }

        public Criteria andNormIdIsNotNull() {
            addCriterion("norm_id is not null");
            return (Criteria) this;
        }

        public Criteria andNormIdEqualTo(Integer value) {
            addCriterion("norm_id =", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdNotEqualTo(Integer value) {
            addCriterion("norm_id <>", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdGreaterThan(Integer value) {
            addCriterion("norm_id >", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("norm_id >=", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdLessThan(Integer value) {
            addCriterion("norm_id <", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdLessThanOrEqualTo(Integer value) {
            addCriterion("norm_id <=", value, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdIn(List<Integer> values) {
            addCriterion("norm_id in", values, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdNotIn(List<Integer> values) {
            addCriterion("norm_id not in", values, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdBetween(Integer value1, Integer value2) {
            addCriterion("norm_id between", value1, value2, "normId");
            return (Criteria) this;
        }

        public Criteria andNormIdNotBetween(Integer value1, Integer value2) {
            addCriterion("norm_id not between", value1, value2, "normId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdIsNull() {
            addCriterion("norm_value_id is null");
            return (Criteria) this;
        }

        public Criteria andNormValueIdIsNotNull() {
            addCriterion("norm_value_id is not null");
            return (Criteria) this;
        }

        public Criteria andNormValueIdEqualTo(Integer value) {
            addCriterion("norm_value_id =", value, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdNotEqualTo(Integer value) {
            addCriterion("norm_value_id <>", value, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdGreaterThan(Integer value) {
            addCriterion("norm_value_id >", value, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("norm_value_id >=", value, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdLessThan(Integer value) {
            addCriterion("norm_value_id <", value, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdLessThanOrEqualTo(Integer value) {
            addCriterion("norm_value_id <=", value, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdIn(List<Integer> values) {
            addCriterion("norm_value_id in", values, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdNotIn(List<Integer> values) {
            addCriterion("norm_value_id not in", values, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdBetween(Integer value1, Integer value2) {
            addCriterion("norm_value_id between", value1, value2, "normValueId");
            return (Criteria) this;
        }

        public Criteria andNormValueIdNotBetween(Integer value1, Integer value2) {
            addCriterion("norm_value_id not between", value1, value2, "normValueId");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonIsNull() {
            addCriterion("due_pay_reason is null");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonIsNotNull() {
            addCriterion("due_pay_reason is not null");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonEqualTo(String value) {
            addCriterion("due_pay_reason =", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonNotEqualTo(String value) {
            addCriterion("due_pay_reason <>", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonGreaterThan(String value) {
            addCriterion("due_pay_reason >", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonGreaterThanOrEqualTo(String value) {
            addCriterion("due_pay_reason >=", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonLessThan(String value) {
            addCriterion("due_pay_reason <", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonLessThanOrEqualTo(String value) {
            addCriterion("due_pay_reason <=", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonLike(String value) {
            addCriterion("due_pay_reason like", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonNotLike(String value) {
            addCriterion("due_pay_reason not like", value, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonIn(List<String> values) {
            addCriterion("due_pay_reason in", values, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonNotIn(List<String> values) {
            addCriterion("due_pay_reason not in", values, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonBetween(String value1, String value2) {
            addCriterion("due_pay_reason between", value1, value2, "duePayReason");
            return (Criteria) this;
        }

        public Criteria andDuePayReasonNotBetween(String value1, String value2) {
            addCriterion("due_pay_reason not between", value1, value2, "duePayReason");
            return (Criteria) this;
        }

        // 不允许报送（未设置党员类别或应缴额度）
        public Criteria andCannotReport() {
            addCriterion("(config_member_type_id is null or due_pay is null)");
            return (Criteria) this;
        }

        public Criteria andDuePayIsNull() {
            addCriterion("due_pay is null");
            return (Criteria) this;
        }

        public Criteria andDuePayIsNotNull() {
            addCriterion("due_pay is not null");
            return (Criteria) this;
        }

        public Criteria andDuePayEqualTo(BigDecimal value) {
            addCriterion("due_pay =", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotEqualTo(BigDecimal value) {
            addCriterion("due_pay <>", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayGreaterThan(BigDecimal value) {
            addCriterion("due_pay >", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("due_pay >=", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayLessThan(BigDecimal value) {
            addCriterion("due_pay <", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("due_pay <=", value, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayIn(List<BigDecimal> values) {
            addCriterion("due_pay in", values, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotIn(List<BigDecimal> values) {
            addCriterion("due_pay not in", values, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("due_pay between", value1, value2, "duePay");
            return (Criteria) this;
        }

        public Criteria andDuePayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("due_pay not between", value1, value2, "duePay");
            return (Criteria) this;
        }

        public Criteria andRealPayIsNull() {
            addCriterion("real_pay is null");
            return (Criteria) this;
        }

        public Criteria andRealPayIsNotNull() {
            addCriterion("real_pay is not null");
            return (Criteria) this;
        }

        public Criteria andRealPayEqualTo(BigDecimal value) {
            addCriterion("real_pay =", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotEqualTo(BigDecimal value) {
            addCriterion("real_pay <>", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayGreaterThan(BigDecimal value) {
            addCriterion("real_pay >", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("real_pay >=", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayLessThan(BigDecimal value) {
            addCriterion("real_pay <", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("real_pay <=", value, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayIn(List<BigDecimal> values) {
            addCriterion("real_pay in", values, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotIn(List<BigDecimal> values) {
            addCriterion("real_pay not in", values, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_pay between", value1, value2, "realPay");
            return (Criteria) this;
        }

        public Criteria andRealPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("real_pay not between", value1, value2, "realPay");
            return (Criteria) this;
        }

        public Criteria andIsDelayIsNull() {
            addCriterion("is_delay is null");
            return (Criteria) this;
        }

        public Criteria andIsDelayIsNotNull() {
            addCriterion("is_delay is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelayEqualTo(Boolean value) {
            addCriterion("is_delay =", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayNotEqualTo(Boolean value) {
            addCriterion("is_delay <>", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayGreaterThan(Boolean value) {
            addCriterion("is_delay >", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delay >=", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayLessThan(Boolean value) {
            addCriterion("is_delay <", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delay <=", value, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayIn(List<Boolean> values) {
            addCriterion("is_delay in", values, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayNotIn(List<Boolean> values) {
            addCriterion("is_delay not in", values, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delay between", value1, value2, "isDelay");
            return (Criteria) this;
        }

        public Criteria andIsDelayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delay not between", value1, value2, "isDelay");
            return (Criteria) this;
        }

        public Criteria andDelayReasonIsNull() {
            addCriterion("delay_reason is null");
            return (Criteria) this;
        }

        public Criteria andDelayReasonIsNotNull() {
            addCriterion("delay_reason is not null");
            return (Criteria) this;
        }

        public Criteria andDelayReasonEqualTo(String value) {
            addCriterion("delay_reason =", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonNotEqualTo(String value) {
            addCriterion("delay_reason <>", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonGreaterThan(String value) {
            addCriterion("delay_reason >", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonGreaterThanOrEqualTo(String value) {
            addCriterion("delay_reason >=", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonLessThan(String value) {
            addCriterion("delay_reason <", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonLessThanOrEqualTo(String value) {
            addCriterion("delay_reason <=", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonLike(String value) {
            addCriterion("delay_reason like", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonNotLike(String value) {
            addCriterion("delay_reason not like", value, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonIn(List<String> values) {
            addCriterion("delay_reason in", values, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonNotIn(List<String> values) {
            addCriterion("delay_reason not in", values, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonBetween(String value1, String value2) {
            addCriterion("delay_reason between", value1, value2, "delayReason");
            return (Criteria) this;
        }

        public Criteria andDelayReasonNotBetween(String value1, String value2) {
            addCriterion("delay_reason not between", value1, value2, "delayReason");
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

        public Criteria andIsOnlinePayIsNull() {
            addCriterion("is_online_pay is null");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayIsNotNull() {
            addCriterion("is_online_pay is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayEqualTo(Boolean value) {
            addCriterion("is_online_pay =", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayNotEqualTo(Boolean value) {
            addCriterion("is_online_pay <>", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayGreaterThan(Boolean value) {
            addCriterion("is_online_pay >", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_online_pay >=", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayLessThan(Boolean value) {
            addCriterion("is_online_pay <", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayLessThanOrEqualTo(Boolean value) {
            addCriterion("is_online_pay <=", value, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayIn(List<Boolean> values) {
            addCriterion("is_online_pay in", values, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayNotIn(List<Boolean> values) {
            addCriterion("is_online_pay not in", values, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayBetween(Boolean value1, Boolean value2) {
            addCriterion("is_online_pay between", value1, value2, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsOnlinePayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_online_pay not between", value1, value2, "isOnlinePay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayIsNull() {
            addCriterion("is_self_pay is null");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayIsNotNull() {
            addCriterion("is_self_pay is not null");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayEqualTo(Boolean value) {
            addCriterion("is_self_pay =", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayNotEqualTo(Boolean value) {
            addCriterion("is_self_pay <>", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayGreaterThan(Boolean value) {
            addCriterion("is_self_pay >", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_self_pay >=", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayLessThan(Boolean value) {
            addCriterion("is_self_pay <", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayLessThanOrEqualTo(Boolean value) {
            addCriterion("is_self_pay <=", value, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayIn(List<Boolean> values) {
            addCriterion("is_self_pay in", values, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayNotIn(List<Boolean> values) {
            addCriterion("is_self_pay not in", values, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayBetween(Boolean value1, Boolean value2) {
            addCriterion("is_self_pay between", value1, value2, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andIsSelfPayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_self_pay not between", value1, value2, "isSelfPay");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdIsNull() {
            addCriterion("charge_user_id is null");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdIsNotNull() {
            addCriterion("charge_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdEqualTo(Integer value) {
            addCriterion("charge_user_id =", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdNotEqualTo(Integer value) {
            addCriterion("charge_user_id <>", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdGreaterThan(Integer value) {
            addCriterion("charge_user_id >", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_user_id >=", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdLessThan(Integer value) {
            addCriterion("charge_user_id <", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("charge_user_id <=", value, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdIn(List<Integer> values) {
            addCriterion("charge_user_id in", values, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdNotIn(List<Integer> values) {
            addCriterion("charge_user_id not in", values, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdBetween(Integer value1, Integer value2) {
            addCriterion("charge_user_id between", value1, value2, "chargeUserId");
            return (Criteria) this;
        }

        public Criteria andChargeUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_user_id not between", value1, value2, "chargeUserId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            if(ShiroHelper.isPermitted(SystemConstants.PERMISSION_PMDVIEWALL))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
            if(branchIdList.isEmpty() && partyIdList.isEmpty())
                andIdIsNull();
            return this;
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