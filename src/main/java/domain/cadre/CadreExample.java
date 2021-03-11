package domain.cadre;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CadreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreExample() {
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

        public Criteria andHasCrpIsNull() {
            addCriterion("has_crp is null");
            return (Criteria) this;
        }

        public Criteria andHasCrpIsNotNull() {
            addCriterion("has_crp is not null");
            return (Criteria) this;
        }

        public Criteria andHasCrpEqualTo(Boolean value) {
            addCriterion("has_crp =", value, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpNotEqualTo(Boolean value) {
            addCriterion("has_crp <>", value, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpGreaterThan(Boolean value) {
            addCriterion("has_crp >", value, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_crp >=", value, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpLessThan(Boolean value) {
            addCriterion("has_crp <", value, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpLessThanOrEqualTo(Boolean value) {
            addCriterion("has_crp <=", value, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpIn(List<Boolean> values) {
            addCriterion("has_crp in", values, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpNotIn(List<Boolean> values) {
            addCriterion("has_crp not in", values, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpBetween(Boolean value1, Boolean value2) {
            addCriterion("has_crp between", value1, value2, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andHasCrpNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_crp not between", value1, value2, "hasCrp");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIsNull() {
            addCriterion("is_double is null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIsNotNull() {
            addCriterion("is_double is not null");
            return (Criteria) this;
        }

        public Criteria andIsDoubleEqualTo(Boolean value) {
            addCriterion("is_double =", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotEqualTo(Boolean value) {
            addCriterion("is_double <>", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleGreaterThan(Boolean value) {
            addCriterion("is_double >", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_double >=", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLessThan(Boolean value) {
            addCriterion("is_double <", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_double <=", value, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleIn(List<Boolean> values) {
            addCriterion("is_double in", values, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotIn(List<Boolean> values) {
            addCriterion("is_double not in", values, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double between", value1, value2, "isDouble");
            return (Criteria) this;
        }

        public Criteria andIsDoubleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_double not between", value1, value2, "isDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsIsNull() {
            addCriterion("double_unit_ids is null");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsIsNotNull() {
            addCriterion("double_unit_ids is not null");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsEqualTo(String value) {
            addCriterion("double_unit_ids =", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotEqualTo(String value) {
            addCriterion("double_unit_ids <>", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsGreaterThan(String value) {
            addCriterion("double_unit_ids >", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsGreaterThanOrEqualTo(String value) {
            addCriterion("double_unit_ids >=", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsLessThan(String value) {
            addCriterion("double_unit_ids <", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsLessThanOrEqualTo(String value) {
            addCriterion("double_unit_ids <=", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsLike(String value) {
            addCriterion("double_unit_ids like", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotLike(String value) {
            addCriterion("double_unit_ids not like", value, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsIn(List<String> values) {
            addCriterion("double_unit_ids in", values, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotIn(List<String> values) {
            addCriterion("double_unit_ids not in", values, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsBetween(String value1, String value2) {
            addCriterion("double_unit_ids between", value1, value2, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andDoubleUnitIdsNotBetween(String value1, String value2) {
            addCriterion("double_unit_ids not between", value1, value2, "doubleUnitIds");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andIsOutsideIsNull() {
            addCriterion("is_outside is null");
            return (Criteria) this;
        }

        public Criteria andIsOutsideIsNotNull() {
            addCriterion("is_outside is not null");
            return (Criteria) this;
        }

        public Criteria andIsOutsideEqualTo(Boolean value) {
            addCriterion("is_outside =", value, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideNotEqualTo(Boolean value) {
            addCriterion("is_outside <>", value, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideGreaterThan(Boolean value) {
            addCriterion("is_outside >", value, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_outside >=", value, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideLessThan(Boolean value) {
            addCriterion("is_outside <", value, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideLessThanOrEqualTo(Boolean value) {
            addCriterion("is_outside <=", value, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideIn(List<Boolean> values) {
            addCriterion("is_outside in", values, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideNotIn(List<Boolean> values) {
            addCriterion("is_outside not in", values, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideBetween(Boolean value1, Boolean value2) {
            addCriterion("is_outside between", value1, value2, "isOutside");
            return (Criteria) this;
        }

        public Criteria andIsOutsideNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_outside not between", value1, value2, "isOutside");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIsNull() {
            addCriterion("dispatch_cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIsNotNull() {
            addCriterion("dispatch_cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id =", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <>", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThan(Integer value) {
            addCriterion("dispatch_cadre_id >", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id >=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThan(Integer value) {
            addCriterion("dispatch_cadre_id <", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_cadre_id <=", value, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotIn(List<Integer> values) {
            addCriterion("dispatch_cadre_id not in", values, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andDispatchCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_cadre_id not between", value1, value2, "dispatchCadreId");
            return (Criteria) this;
        }

        public Criteria andLabelIsNull() {
            addCriterion("label is null");
            return (Criteria) this;
        }

        public Criteria andLabelIsNotNull() {
            addCriterion("label is not null");
            return (Criteria) this;
        }

        public Criteria andLabelEqualTo(String value) {
            addCriterion("label =", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotEqualTo(String value) {
            addCriterion("label <>", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThan(String value) {
            addCriterion("label >", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThanOrEqualTo(String value) {
            addCriterion("label >=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThan(String value) {
            addCriterion("label <", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThanOrEqualTo(String value) {
            addCriterion("label <=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLike(String value) {
            addCriterion("label like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotLike(String value) {
            addCriterion("label not like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelIn(List<String> values) {
            addCriterion("label in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotIn(List<String> values) {
            addCriterion("label not in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelBetween(String value1, String value2) {
            addCriterion("label between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotBetween(String value1, String value2) {
            addCriterion("label not between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andProfileIsNull() {
            addCriterion("profile is null");
            return (Criteria) this;
        }

        public Criteria andProfileIsNotNull() {
            addCriterion("profile is not null");
            return (Criteria) this;
        }

        public Criteria andProfileEqualTo(String value) {
            addCriterion("profile =", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotEqualTo(String value) {
            addCriterion("profile <>", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileGreaterThan(String value) {
            addCriterion("profile >", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileGreaterThanOrEqualTo(String value) {
            addCriterion("profile >=", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileLessThan(String value) {
            addCriterion("profile <", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileLessThanOrEqualTo(String value) {
            addCriterion("profile <=", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileLike(String value) {
            addCriterion("profile like", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotLike(String value) {
            addCriterion("profile not like", value, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileIn(List<String> values) {
            addCriterion("profile in", values, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotIn(List<String> values) {
            addCriterion("profile not in", values, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileBetween(String value1, String value2) {
            addCriterion("profile between", value1, value2, "profile");
            return (Criteria) this;
        }

        public Criteria andProfileNotBetween(String value1, String value2) {
            addCriterion("profile not between", value1, value2, "profile");
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

        public Criteria andOriginalPostIsNull() {
            addCriterion("original_post is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIsNotNull() {
            addCriterion("original_post is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPostEqualTo(String value) {
            addCriterion("original_post =", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotEqualTo(String value) {
            addCriterion("original_post <>", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostGreaterThan(String value) {
            addCriterion("original_post >", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostGreaterThanOrEqualTo(String value) {
            addCriterion("original_post >=", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLessThan(String value) {
            addCriterion("original_post <", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLessThanOrEqualTo(String value) {
            addCriterion("original_post <=", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostLike(String value) {
            addCriterion("original_post like", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotLike(String value) {
            addCriterion("original_post not like", value, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostIn(List<String> values) {
            addCriterion("original_post in", values, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotIn(List<String> values) {
            addCriterion("original_post not in", values, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostBetween(String value1, String value2) {
            addCriterion("original_post between", value1, value2, "originalPost");
            return (Criteria) this;
        }

        public Criteria andOriginalPostNotBetween(String value1, String value2) {
            addCriterion("original_post not between", value1, value2, "originalPost");
            return (Criteria) this;
        }

        public Criteria andAppointDateIsNull() {
            addCriterion("appoint_date is null");
            return (Criteria) this;
        }

        public Criteria andAppointDateIsNotNull() {
            addCriterion("appoint_date is not null");
            return (Criteria) this;
        }

        public Criteria andAppointDateEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date =", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date <>", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateGreaterThan(Date value) {
            addCriterionForJDBCDate("appoint_date >", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date >=", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateLessThan(Date value) {
            addCriterionForJDBCDate("appoint_date <", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("appoint_date <=", value, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_date in", values, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("appoint_date not in", values, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_date between", value1, value2, "appointDate");
            return (Criteria) this;
        }

        public Criteria andAppointDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("appoint_date not between", value1, value2, "appointDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateIsNull() {
            addCriterion("depose_date is null");
            return (Criteria) this;
        }

        public Criteria andDeposeDateIsNotNull() {
            addCriterion("depose_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeposeDateEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date =", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date <>", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateGreaterThan(Date value) {
            addCriterionForJDBCDate("depose_date >", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date >=", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateLessThan(Date value) {
            addCriterionForJDBCDate("depose_date <", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("depose_date <=", value, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateIn(List<Date> values) {
            addCriterionForJDBCDate("depose_date in", values, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("depose_date not in", values, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("depose_date between", value1, value2, "deposeDate");
            return (Criteria) this;
        }

        public Criteria andDeposeDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("depose_date not between", value1, value2, "deposeDate");
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