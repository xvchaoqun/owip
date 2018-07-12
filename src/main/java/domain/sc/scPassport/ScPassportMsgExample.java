package domain.sc.scPassport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScPassportMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScPassportMsgExample() {
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

        public Criteria andHandIdIsNull() {
            addCriterion("hand_id is null");
            return (Criteria) this;
        }

        public Criteria andHandIdIsNotNull() {
            addCriterion("hand_id is not null");
            return (Criteria) this;
        }

        public Criteria andHandIdEqualTo(Integer value) {
            addCriterion("hand_id =", value, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdNotEqualTo(Integer value) {
            addCriterion("hand_id <>", value, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdGreaterThan(Integer value) {
            addCriterion("hand_id >", value, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("hand_id >=", value, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdLessThan(Integer value) {
            addCriterion("hand_id <", value, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdLessThanOrEqualTo(Integer value) {
            addCriterion("hand_id <=", value, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdIn(List<Integer> values) {
            addCriterion("hand_id in", values, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdNotIn(List<Integer> values) {
            addCriterion("hand_id not in", values, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdBetween(Integer value1, Integer value2) {
            addCriterion("hand_id between", value1, value2, "handId");
            return (Criteria) this;
        }

        public Criteria andHandIdNotBetween(Integer value1, Integer value2) {
            addCriterion("hand_id not between", value1, value2, "handId");
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

        public Criteria andTplKeyIsNull() {
            addCriterion("tpl_key is null");
            return (Criteria) this;
        }

        public Criteria andTplKeyIsNotNull() {
            addCriterion("tpl_key is not null");
            return (Criteria) this;
        }

        public Criteria andTplKeyEqualTo(String value) {
            addCriterion("tpl_key =", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyNotEqualTo(String value) {
            addCriterion("tpl_key <>", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyGreaterThan(String value) {
            addCriterion("tpl_key >", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyGreaterThanOrEqualTo(String value) {
            addCriterion("tpl_key >=", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyLessThan(String value) {
            addCriterion("tpl_key <", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyLessThanOrEqualTo(String value) {
            addCriterion("tpl_key <=", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyLike(String value) {
            addCriterion("tpl_key like", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyNotLike(String value) {
            addCriterion("tpl_key not like", value, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyIn(List<String> values) {
            addCriterion("tpl_key in", values, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyNotIn(List<String> values) {
            addCriterion("tpl_key not in", values, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyBetween(String value1, String value2) {
            addCriterion("tpl_key between", value1, value2, "tplKey");
            return (Criteria) this;
        }

        public Criteria andTplKeyNotBetween(String value1, String value2) {
            addCriterion("tpl_key not between", value1, value2, "tplKey");
            return (Criteria) this;
        }

        public Criteria andContentTplIdIsNull() {
            addCriterion("content_tpl_id is null");
            return (Criteria) this;
        }

        public Criteria andContentTplIdIsNotNull() {
            addCriterion("content_tpl_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentTplIdEqualTo(Integer value) {
            addCriterion("content_tpl_id =", value, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdNotEqualTo(Integer value) {
            addCriterion("content_tpl_id <>", value, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdGreaterThan(Integer value) {
            addCriterion("content_tpl_id >", value, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_tpl_id >=", value, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdLessThan(Integer value) {
            addCriterion("content_tpl_id <", value, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdLessThanOrEqualTo(Integer value) {
            addCriterion("content_tpl_id <=", value, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdIn(List<Integer> values) {
            addCriterion("content_tpl_id in", values, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdNotIn(List<Integer> values) {
            addCriterion("content_tpl_id not in", values, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdBetween(Integer value1, Integer value2) {
            addCriterion("content_tpl_id between", value1, value2, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andContentTplIdNotBetween(Integer value1, Integer value2) {
            addCriterion("content_tpl_id not between", value1, value2, "contentTplId");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("msg not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSuccessIsNull() {
            addCriterion("success is null");
            return (Criteria) this;
        }

        public Criteria andSuccessIsNotNull() {
            addCriterion("success is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessEqualTo(Boolean value) {
            addCriterion("success =", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotEqualTo(Boolean value) {
            addCriterion("success <>", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessGreaterThan(Boolean value) {
            addCriterion("success >", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("success >=", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessLessThan(Boolean value) {
            addCriterion("success <", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("success <=", value, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessIn(List<Boolean> values) {
            addCriterion("success in", values, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotIn(List<Boolean> values) {
            addCriterion("success not in", values, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("success between", value1, value2, "success");
            return (Criteria) this;
        }

        public Criteria andSuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("success not between", value1, value2, "success");
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