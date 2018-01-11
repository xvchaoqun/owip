package domain.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OneSendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OneSendExample() {
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

        public Criteria andSendUserIdIsNull() {
            addCriterion("send_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSendUserIdIsNotNull() {
            addCriterion("send_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSendUserIdEqualTo(Integer value) {
            addCriterion("send_user_id =", value, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdNotEqualTo(Integer value) {
            addCriterion("send_user_id <>", value, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdGreaterThan(Integer value) {
            addCriterion("send_user_id >", value, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_user_id >=", value, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdLessThan(Integer value) {
            addCriterion("send_user_id <", value, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("send_user_id <=", value, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdIn(List<Integer> values) {
            addCriterion("send_user_id in", values, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdNotIn(List<Integer> values) {
            addCriterion("send_user_id not in", values, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdBetween(Integer value1, Integer value2) {
            addCriterion("send_user_id between", value1, value2, "sendUserId");
            return (Criteria) this;
        }

        public Criteria andSendUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("send_user_id not between", value1, value2, "sendUserId");
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

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andReciversIsNull() {
            addCriterion("recivers is null");
            return (Criteria) this;
        }

        public Criteria andReciversIsNotNull() {
            addCriterion("recivers is not null");
            return (Criteria) this;
        }

        public Criteria andReciversEqualTo(String value) {
            addCriterion("recivers =", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversNotEqualTo(String value) {
            addCriterion("recivers <>", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversGreaterThan(String value) {
            addCriterion("recivers >", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversGreaterThanOrEqualTo(String value) {
            addCriterion("recivers >=", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversLessThan(String value) {
            addCriterion("recivers <", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversLessThanOrEqualTo(String value) {
            addCriterion("recivers <=", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversLike(String value) {
            addCriterion("recivers like", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversNotLike(String value) {
            addCriterion("recivers not like", value, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversIn(List<String> values) {
            addCriterion("recivers in", values, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversNotIn(List<String> values) {
            addCriterion("recivers not in", values, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversBetween(String value1, String value2) {
            addCriterion("recivers between", value1, value2, "recivers");
            return (Criteria) this;
        }

        public Criteria andReciversNotBetween(String value1, String value2) {
            addCriterion("recivers not between", value1, value2, "recivers");
            return (Criteria) this;
        }

        public Criteria andCodesIsNull() {
            addCriterion("codes is null");
            return (Criteria) this;
        }

        public Criteria andCodesIsNotNull() {
            addCriterion("codes is not null");
            return (Criteria) this;
        }

        public Criteria andCodesEqualTo(String value) {
            addCriterion("codes =", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesNotEqualTo(String value) {
            addCriterion("codes <>", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesGreaterThan(String value) {
            addCriterion("codes >", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesGreaterThanOrEqualTo(String value) {
            addCriterion("codes >=", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesLessThan(String value) {
            addCriterion("codes <", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesLessThanOrEqualTo(String value) {
            addCriterion("codes <=", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesLike(String value) {
            addCriterion("codes like", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesNotLike(String value) {
            addCriterion("codes not like", value, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesIn(List<String> values) {
            addCriterion("codes in", values, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesNotIn(List<String> values) {
            addCriterion("codes not in", values, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesBetween(String value1, String value2) {
            addCriterion("codes between", value1, value2, "codes");
            return (Criteria) this;
        }

        public Criteria andCodesNotBetween(String value1, String value2) {
            addCriterion("codes not between", value1, value2, "codes");
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

        public Criteria andIsSuccessIsNull() {
            addCriterion("is_success is null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIsNotNull() {
            addCriterion("is_success is not null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessEqualTo(Boolean value) {
            addCriterion("is_success =", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotEqualTo(Boolean value) {
            addCriterion("is_success <>", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessGreaterThan(Boolean value) {
            addCriterion("is_success >", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_success >=", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessLessThan(Boolean value) {
            addCriterion("is_success <", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("is_success <=", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIn(List<Boolean> values) {
            addCriterion("is_success in", values, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotIn(List<Boolean> values) {
            addCriterion("is_success not in", values, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("is_success between", value1, value2, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_success not between", value1, value2, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andRetIsNull() {
            addCriterion("ret is null");
            return (Criteria) this;
        }

        public Criteria andRetIsNotNull() {
            addCriterion("ret is not null");
            return (Criteria) this;
        }

        public Criteria andRetEqualTo(String value) {
            addCriterion("ret =", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotEqualTo(String value) {
            addCriterion("ret <>", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetGreaterThan(String value) {
            addCriterion("ret >", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetGreaterThanOrEqualTo(String value) {
            addCriterion("ret >=", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetLessThan(String value) {
            addCriterion("ret <", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetLessThanOrEqualTo(String value) {
            addCriterion("ret <=", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetLike(String value) {
            addCriterion("ret like", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotLike(String value) {
            addCriterion("ret not like", value, "ret");
            return (Criteria) this;
        }

        public Criteria andRetIn(List<String> values) {
            addCriterion("ret in", values, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotIn(List<String> values) {
            addCriterion("ret not in", values, "ret");
            return (Criteria) this;
        }

        public Criteria andRetBetween(String value1, String value2) {
            addCriterion("ret between", value1, value2, "ret");
            return (Criteria) this;
        }

        public Criteria andRetNotBetween(String value1, String value2) {
            addCriterion("ret not between", value1, value2, "ret");
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