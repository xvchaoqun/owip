package domain.modify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModifyBaseItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ModifyBaseItemExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Integer value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Integer value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Integer value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Integer value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Integer> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Integer> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
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

        public Criteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            addCriterion("table_name =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            addCriterion("table_name <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            addCriterion("table_name >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("table_name >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            addCriterion("table_name <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("table_name <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            addCriterion("table_name like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            addCriterion("table_name not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            addCriterion("table_name in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            addCriterion("table_name not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            addCriterion("table_name between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("table_name not between", value1, value2, "tableName");
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

        public Criteria andOrginalValueIsNull() {
            addCriterion("orginal_value is null");
            return (Criteria) this;
        }

        public Criteria andOrginalValueIsNotNull() {
            addCriterion("orginal_value is not null");
            return (Criteria) this;
        }

        public Criteria andOrginalValueEqualTo(String value) {
            addCriterion("orginal_value =", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueNotEqualTo(String value) {
            addCriterion("orginal_value <>", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueGreaterThan(String value) {
            addCriterion("orginal_value >", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueGreaterThanOrEqualTo(String value) {
            addCriterion("orginal_value >=", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueLessThan(String value) {
            addCriterion("orginal_value <", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueLessThanOrEqualTo(String value) {
            addCriterion("orginal_value <=", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueLike(String value) {
            addCriterion("orginal_value like", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueNotLike(String value) {
            addCriterion("orginal_value not like", value, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueIn(List<String> values) {
            addCriterion("orginal_value in", values, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueNotIn(List<String> values) {
            addCriterion("orginal_value not in", values, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueBetween(String value1, String value2) {
            addCriterion("orginal_value between", value1, value2, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andOrginalValueNotBetween(String value1, String value2) {
            addCriterion("orginal_value not between", value1, value2, "orginalValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueIsNull() {
            addCriterion("modify_value is null");
            return (Criteria) this;
        }

        public Criteria andModifyValueIsNotNull() {
            addCriterion("modify_value is not null");
            return (Criteria) this;
        }

        public Criteria andModifyValueEqualTo(String value) {
            addCriterion("modify_value =", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueNotEqualTo(String value) {
            addCriterion("modify_value <>", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueGreaterThan(String value) {
            addCriterion("modify_value >", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueGreaterThanOrEqualTo(String value) {
            addCriterion("modify_value >=", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueLessThan(String value) {
            addCriterion("modify_value <", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueLessThanOrEqualTo(String value) {
            addCriterion("modify_value <=", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueLike(String value) {
            addCriterion("modify_value like", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueNotLike(String value) {
            addCriterion("modify_value not like", value, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueIn(List<String> values) {
            addCriterion("modify_value in", values, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueNotIn(List<String> values) {
            addCriterion("modify_value not in", values, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueBetween(String value1, String value2) {
            addCriterion("modify_value between", value1, value2, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andModifyValueNotBetween(String value1, String value2) {
            addCriterion("modify_value not between", value1, value2, "modifyValue");
            return (Criteria) this;
        }

        public Criteria andIsStringIsNull() {
            addCriterion("is_string is null");
            return (Criteria) this;
        }

        public Criteria andIsStringIsNotNull() {
            addCriterion("is_string is not null");
            return (Criteria) this;
        }

        public Criteria andIsStringEqualTo(Boolean value) {
            addCriterion("is_string =", value, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringNotEqualTo(Boolean value) {
            addCriterion("is_string <>", value, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringGreaterThan(Boolean value) {
            addCriterion("is_string >", value, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_string >=", value, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringLessThan(Boolean value) {
            addCriterion("is_string <", value, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringLessThanOrEqualTo(Boolean value) {
            addCriterion("is_string <=", value, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringIn(List<Boolean> values) {
            addCriterion("is_string in", values, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringNotIn(List<Boolean> values) {
            addCriterion("is_string not in", values, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringBetween(Boolean value1, Boolean value2) {
            addCriterion("is_string between", value1, value2, "isString");
            return (Criteria) this;
        }

        public Criteria andIsStringNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_string not between", value1, value2, "isString");
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
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

        public Criteria andCheckRemarkIsNull() {
            addCriterion("check_remark is null");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIsNotNull() {
            addCriterion("check_remark is not null");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkEqualTo(String value) {
            addCriterion("check_remark =", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotEqualTo(String value) {
            addCriterion("check_remark <>", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkGreaterThan(String value) {
            addCriterion("check_remark >", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("check_remark >=", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLessThan(String value) {
            addCriterion("check_remark <", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLessThanOrEqualTo(String value) {
            addCriterion("check_remark <=", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkLike(String value) {
            addCriterion("check_remark like", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotLike(String value) {
            addCriterion("check_remark not like", value, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkIn(List<String> values) {
            addCriterion("check_remark in", values, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotIn(List<String> values) {
            addCriterion("check_remark not in", values, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkBetween(String value1, String value2) {
            addCriterion("check_remark between", value1, value2, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckRemarkNotBetween(String value1, String value2) {
            addCriterion("check_remark not between", value1, value2, "checkRemark");
            return (Criteria) this;
        }

        public Criteria andCheckReasonIsNull() {
            addCriterion("check_reason is null");
            return (Criteria) this;
        }

        public Criteria andCheckReasonIsNotNull() {
            addCriterion("check_reason is not null");
            return (Criteria) this;
        }

        public Criteria andCheckReasonEqualTo(String value) {
            addCriterion("check_reason =", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotEqualTo(String value) {
            addCriterion("check_reason <>", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonGreaterThan(String value) {
            addCriterion("check_reason >", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonGreaterThanOrEqualTo(String value) {
            addCriterion("check_reason >=", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonLessThan(String value) {
            addCriterion("check_reason <", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonLessThanOrEqualTo(String value) {
            addCriterion("check_reason <=", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonLike(String value) {
            addCriterion("check_reason like", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotLike(String value) {
            addCriterion("check_reason not like", value, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonIn(List<String> values) {
            addCriterion("check_reason in", values, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotIn(List<String> values) {
            addCriterion("check_reason not in", values, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonBetween(String value1, String value2) {
            addCriterion("check_reason between", value1, value2, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckReasonNotBetween(String value1, String value2) {
            addCriterion("check_reason not between", value1, value2, "checkReason");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNull() {
            addCriterion("check_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNotNull() {
            addCriterion("check_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdEqualTo(Integer value) {
            addCriterion("check_user_id =", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotEqualTo(Integer value) {
            addCriterion("check_user_id <>", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThan(Integer value) {
            addCriterion("check_user_id >", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_user_id >=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThan(Integer value) {
            addCriterion("check_user_id <", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_user_id <=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIn(List<Integer> values) {
            addCriterion("check_user_id in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotIn(List<Integer> values) {
            addCriterion("check_user_id not in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id not between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckIpIsNull() {
            addCriterion("check_ip is null");
            return (Criteria) this;
        }

        public Criteria andCheckIpIsNotNull() {
            addCriterion("check_ip is not null");
            return (Criteria) this;
        }

        public Criteria andCheckIpEqualTo(String value) {
            addCriterion("check_ip =", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpNotEqualTo(String value) {
            addCriterion("check_ip <>", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpGreaterThan(String value) {
            addCriterion("check_ip >", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpGreaterThanOrEqualTo(String value) {
            addCriterion("check_ip >=", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpLessThan(String value) {
            addCriterion("check_ip <", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpLessThanOrEqualTo(String value) {
            addCriterion("check_ip <=", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpLike(String value) {
            addCriterion("check_ip like", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpNotLike(String value) {
            addCriterion("check_ip not like", value, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpIn(List<String> values) {
            addCriterion("check_ip in", values, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpNotIn(List<String> values) {
            addCriterion("check_ip not in", values, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpBetween(String value1, String value2) {
            addCriterion("check_ip between", value1, value2, "checkIp");
            return (Criteria) this;
        }

        public Criteria andCheckIpNotBetween(String value1, String value2) {
            addCriterion("check_ip not between", value1, value2, "checkIp");
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