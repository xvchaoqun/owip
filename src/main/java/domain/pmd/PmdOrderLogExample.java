package domain.pmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmdOrderLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdOrderLogExample() {
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

        public Criteria andDateIdIsNull() {
            addCriterion("date_id is null");
            return (Criteria) this;
        }

        public Criteria andDateIdIsNotNull() {
            addCriterion("date_id is not null");
            return (Criteria) this;
        }

        public Criteria andDateIdEqualTo(Integer value) {
            addCriterion("date_id =", value, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdNotEqualTo(Integer value) {
            addCriterion("date_id <>", value, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdGreaterThan(Integer value) {
            addCriterion("date_id >", value, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("date_id >=", value, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdLessThan(Integer value) {
            addCriterion("date_id <", value, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdLessThanOrEqualTo(Integer value) {
            addCriterion("date_id <=", value, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdIn(List<Integer> values) {
            addCriterion("date_id in", values, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdNotIn(List<Integer> values) {
            addCriterion("date_id not in", values, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdBetween(Integer value1, Integer value2) {
            addCriterion("date_id between", value1, value2, "dateId");
            return (Criteria) this;
        }

        public Criteria andDateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("date_id not between", value1, value2, "dateId");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(Integer value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(Integer value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(Integer value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(Integer value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(Integer value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<Integer> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<Integer> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(Integer value1, Integer value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdIsNull() {
            addCriterion("third_order_id is null");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdIsNotNull() {
            addCriterion("third_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdEqualTo(String value) {
            addCriterion("third_order_id =", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdNotEqualTo(String value) {
            addCriterion("third_order_id <>", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdGreaterThan(String value) {
            addCriterion("third_order_id >", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("third_order_id >=", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdLessThan(String value) {
            addCriterion("third_order_id <", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdLessThanOrEqualTo(String value) {
            addCriterion("third_order_id <=", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdLike(String value) {
            addCriterion("third_order_id like", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdNotLike(String value) {
            addCriterion("third_order_id not like", value, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdIn(List<String> values) {
            addCriterion("third_order_id in", values, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdNotIn(List<String> values) {
            addCriterion("third_order_id not in", values, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdBetween(String value1, String value2) {
            addCriterion("third_order_id between", value1, value2, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andThirdOrderIdNotBetween(String value1, String value2) {
            addCriterion("third_order_id not between", value1, value2, "thirdOrderId");
            return (Criteria) this;
        }

        public Criteria andToAccountIsNull() {
            addCriterion("to_account is null");
            return (Criteria) this;
        }

        public Criteria andToAccountIsNotNull() {
            addCriterion("to_account is not null");
            return (Criteria) this;
        }

        public Criteria andToAccountEqualTo(Integer value) {
            addCriterion("to_account =", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotEqualTo(Integer value) {
            addCriterion("to_account <>", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountGreaterThan(Integer value) {
            addCriterion("to_account >", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("to_account >=", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountLessThan(Integer value) {
            addCriterion("to_account <", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountLessThanOrEqualTo(Integer value) {
            addCriterion("to_account <=", value, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountIn(List<Integer> values) {
            addCriterion("to_account in", values, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotIn(List<Integer> values) {
            addCriterion("to_account not in", values, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountBetween(Integer value1, Integer value2) {
            addCriterion("to_account between", value1, value2, "toAccount");
            return (Criteria) this;
        }

        public Criteria andToAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("to_account not between", value1, value2, "toAccount");
            return (Criteria) this;
        }

        public Criteria andTranamtIsNull() {
            addCriterion("tranamt is null");
            return (Criteria) this;
        }

        public Criteria andTranamtIsNotNull() {
            addCriterion("tranamt is not null");
            return (Criteria) this;
        }

        public Criteria andTranamtEqualTo(Integer value) {
            addCriterion("tranamt =", value, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtNotEqualTo(Integer value) {
            addCriterion("tranamt <>", value, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtGreaterThan(Integer value) {
            addCriterion("tranamt >", value, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtGreaterThanOrEqualTo(Integer value) {
            addCriterion("tranamt >=", value, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtLessThan(Integer value) {
            addCriterion("tranamt <", value, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtLessThanOrEqualTo(Integer value) {
            addCriterion("tranamt <=", value, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtIn(List<Integer> values) {
            addCriterion("tranamt in", values, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtNotIn(List<Integer> values) {
            addCriterion("tranamt not in", values, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtBetween(Integer value1, Integer value2) {
            addCriterion("tranamt between", value1, value2, "tranamt");
            return (Criteria) this;
        }

        public Criteria andTranamtNotBetween(Integer value1, Integer value2) {
            addCriterion("tranamt not between", value1, value2, "tranamt");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("order_id like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("order_id not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdIsNull() {
            addCriterion("reforder_id is null");
            return (Criteria) this;
        }

        public Criteria andReforderIdIsNotNull() {
            addCriterion("reforder_id is not null");
            return (Criteria) this;
        }

        public Criteria andReforderIdEqualTo(String value) {
            addCriterion("reforder_id =", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdNotEqualTo(String value) {
            addCriterion("reforder_id <>", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdGreaterThan(String value) {
            addCriterion("reforder_id >", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdGreaterThanOrEqualTo(String value) {
            addCriterion("reforder_id >=", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdLessThan(String value) {
            addCriterion("reforder_id <", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdLessThanOrEqualTo(String value) {
            addCriterion("reforder_id <=", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdLike(String value) {
            addCriterion("reforder_id like", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdNotLike(String value) {
            addCriterion("reforder_id not like", value, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdIn(List<String> values) {
            addCriterion("reforder_id in", values, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdNotIn(List<String> values) {
            addCriterion("reforder_id not in", values, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdBetween(String value1, String value2) {
            addCriterion("reforder_id between", value1, value2, "reforderId");
            return (Criteria) this;
        }

        public Criteria andReforderIdNotBetween(String value1, String value2) {
            addCriterion("reforder_id not between", value1, value2, "reforderId");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNull() {
            addCriterion("oper_type is null");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNotNull() {
            addCriterion("oper_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperTypeEqualTo(Integer value) {
            addCriterion("oper_type =", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotEqualTo(Integer value) {
            addCriterion("oper_type <>", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThan(Integer value) {
            addCriterion("oper_type >", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("oper_type >=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThan(Integer value) {
            addCriterion("oper_type <", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThanOrEqualTo(Integer value) {
            addCriterion("oper_type <=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeIn(List<Integer> values) {
            addCriterion("oper_type in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotIn(List<Integer> values) {
            addCriterion("oper_type not in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeBetween(Integer value1, Integer value2) {
            addCriterion("oper_type between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("oper_type not between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOrderDescIsNull() {
            addCriterion("order_desc is null");
            return (Criteria) this;
        }

        public Criteria andOrderDescIsNotNull() {
            addCriterion("order_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDescEqualTo(String value) {
            addCriterion("order_desc =", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotEqualTo(String value) {
            addCriterion("order_desc <>", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescGreaterThan(String value) {
            addCriterion("order_desc >", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescGreaterThanOrEqualTo(String value) {
            addCriterion("order_desc >=", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLessThan(String value) {
            addCriterion("order_desc <", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLessThanOrEqualTo(String value) {
            addCriterion("order_desc <=", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLike(String value) {
            addCriterion("order_desc like", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotLike(String value) {
            addCriterion("order_desc not like", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescIn(List<String> values) {
            addCriterion("order_desc in", values, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotIn(List<String> values) {
            addCriterion("order_desc not in", values, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescBetween(String value1, String value2) {
            addCriterion("order_desc between", value1, value2, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotBetween(String value1, String value2) {
            addCriterion("order_desc not between", value1, value2, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andPraram1IsNull() {
            addCriterion("praram1 is null");
            return (Criteria) this;
        }

        public Criteria andPraram1IsNotNull() {
            addCriterion("praram1 is not null");
            return (Criteria) this;
        }

        public Criteria andPraram1EqualTo(String value) {
            addCriterion("praram1 =", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1NotEqualTo(String value) {
            addCriterion("praram1 <>", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1GreaterThan(String value) {
            addCriterion("praram1 >", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1GreaterThanOrEqualTo(String value) {
            addCriterion("praram1 >=", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1LessThan(String value) {
            addCriterion("praram1 <", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1LessThanOrEqualTo(String value) {
            addCriterion("praram1 <=", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1Like(String value) {
            addCriterion("praram1 like", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1NotLike(String value) {
            addCriterion("praram1 not like", value, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1In(List<String> values) {
            addCriterion("praram1 in", values, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1NotIn(List<String> values) {
            addCriterion("praram1 not in", values, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1Between(String value1, String value2) {
            addCriterion("praram1 between", value1, value2, "praram1");
            return (Criteria) this;
        }

        public Criteria andPraram1NotBetween(String value1, String value2) {
            addCriterion("praram1 not between", value1, value2, "praram1");
            return (Criteria) this;
        }

        public Criteria andSnoIsNull() {
            addCriterion("sno is null");
            return (Criteria) this;
        }

        public Criteria andSnoIsNotNull() {
            addCriterion("sno is not null");
            return (Criteria) this;
        }

        public Criteria andSnoEqualTo(String value) {
            addCriterion("sno =", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoNotEqualTo(String value) {
            addCriterion("sno <>", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoGreaterThan(String value) {
            addCriterion("sno >", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoGreaterThanOrEqualTo(String value) {
            addCriterion("sno >=", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoLessThan(String value) {
            addCriterion("sno <", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoLessThanOrEqualTo(String value) {
            addCriterion("sno <=", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoLike(String value) {
            addCriterion("sno like", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoNotLike(String value) {
            addCriterion("sno not like", value, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoIn(List<String> values) {
            addCriterion("sno in", values, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoNotIn(List<String> values) {
            addCriterion("sno not in", values, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoBetween(String value1, String value2) {
            addCriterion("sno between", value1, value2, "sno");
            return (Criteria) this;
        }

        public Criteria andSnoNotBetween(String value1, String value2) {
            addCriterion("sno not between", value1, value2, "sno");
            return (Criteria) this;
        }

        public Criteria andActuaLamtIsNull() {
            addCriterion("actua_lamt is null");
            return (Criteria) this;
        }

        public Criteria andActuaLamtIsNotNull() {
            addCriterion("actua_lamt is not null");
            return (Criteria) this;
        }

        public Criteria andActuaLamtEqualTo(Integer value) {
            addCriterion("actua_lamt =", value, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtNotEqualTo(Integer value) {
            addCriterion("actua_lamt <>", value, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtGreaterThan(Integer value) {
            addCriterion("actua_lamt >", value, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtGreaterThanOrEqualTo(Integer value) {
            addCriterion("actua_lamt >=", value, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtLessThan(Integer value) {
            addCriterion("actua_lamt <", value, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtLessThanOrEqualTo(Integer value) {
            addCriterion("actua_lamt <=", value, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtIn(List<Integer> values) {
            addCriterion("actua_lamt in", values, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtNotIn(List<Integer> values) {
            addCriterion("actua_lamt not in", values, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtBetween(Integer value1, Integer value2) {
            addCriterion("actua_lamt between", value1, value2, "actuaLamt");
            return (Criteria) this;
        }

        public Criteria andActuaLamtNotBetween(Integer value1, Integer value2) {
            addCriterion("actua_lamt not between", value1, value2, "actuaLamt");
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

        public Criteria andStateEqualTo(Boolean value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Boolean value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Boolean value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Boolean value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Boolean value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Boolean> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Boolean> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Boolean value1, Boolean value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andPayNameIsNull() {
            addCriterion("pay_name is null");
            return (Criteria) this;
        }

        public Criteria andPayNameIsNotNull() {
            addCriterion("pay_name is not null");
            return (Criteria) this;
        }

        public Criteria andPayNameEqualTo(String value) {
            addCriterion("pay_name =", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameNotEqualTo(String value) {
            addCriterion("pay_name <>", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameGreaterThan(String value) {
            addCriterion("pay_name >", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameGreaterThanOrEqualTo(String value) {
            addCriterion("pay_name >=", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameLessThan(String value) {
            addCriterion("pay_name <", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameLessThanOrEqualTo(String value) {
            addCriterion("pay_name <=", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameLike(String value) {
            addCriterion("pay_name like", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameNotLike(String value) {
            addCriterion("pay_name not like", value, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameIn(List<String> values) {
            addCriterion("pay_name in", values, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameNotIn(List<String> values) {
            addCriterion("pay_name not in", values, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameBetween(String value1, String value2) {
            addCriterion("pay_name between", value1, value2, "payName");
            return (Criteria) this;
        }

        public Criteria andPayNameNotBetween(String value1, String value2) {
            addCriterion("pay_name not between", value1, value2, "payName");
            return (Criteria) this;
        }

        public Criteria andRzDateIsNull() {
            addCriterion("rz_date is null");
            return (Criteria) this;
        }

        public Criteria andRzDateIsNotNull() {
            addCriterion("rz_date is not null");
            return (Criteria) this;
        }

        public Criteria andRzDateEqualTo(Date value) {
            addCriterion("rz_date =", value, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateNotEqualTo(Date value) {
            addCriterion("rz_date <>", value, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateGreaterThan(Date value) {
            addCriterion("rz_date >", value, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateGreaterThanOrEqualTo(Date value) {
            addCriterion("rz_date >=", value, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateLessThan(Date value) {
            addCriterion("rz_date <", value, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateLessThanOrEqualTo(Date value) {
            addCriterion("rz_date <=", value, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateIn(List<Date> values) {
            addCriterion("rz_date in", values, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateNotIn(List<Date> values) {
            addCriterion("rz_date not in", values, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateBetween(Date value1, Date value2) {
            addCriterion("rz_date between", value1, value2, "rzDate");
            return (Criteria) this;
        }

        public Criteria andRzDateNotBetween(Date value1, Date value2) {
            addCriterion("rz_date not between", value1, value2, "rzDate");
            return (Criteria) this;
        }

        public Criteria andJyDateIsNull() {
            addCriterion("jy_date is null");
            return (Criteria) this;
        }

        public Criteria andJyDateIsNotNull() {
            addCriterion("jy_date is not null");
            return (Criteria) this;
        }

        public Criteria andJyDateEqualTo(Date value) {
            addCriterion("jy_date =", value, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateNotEqualTo(Date value) {
            addCriterion("jy_date <>", value, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateGreaterThan(Date value) {
            addCriterion("jy_date >", value, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("jy_date >=", value, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateLessThan(Date value) {
            addCriterion("jy_date <", value, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateLessThanOrEqualTo(Date value) {
            addCriterion("jy_date <=", value, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateIn(List<Date> values) {
            addCriterion("jy_date in", values, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateNotIn(List<Date> values) {
            addCriterion("jy_date not in", values, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateBetween(Date value1, Date value2) {
            addCriterion("jy_date between", value1, value2, "jyDate");
            return (Criteria) this;
        }

        public Criteria andJyDateNotBetween(Date value1, Date value2) {
            addCriterion("jy_date not between", value1, value2, "jyDate");
            return (Criteria) this;
        }

        public Criteria andThirdSystemIsNull() {
            addCriterion("third_system is null");
            return (Criteria) this;
        }

        public Criteria andThirdSystemIsNotNull() {
            addCriterion("third_system is not null");
            return (Criteria) this;
        }

        public Criteria andThirdSystemEqualTo(String value) {
            addCriterion("third_system =", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemNotEqualTo(String value) {
            addCriterion("third_system <>", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemGreaterThan(String value) {
            addCriterion("third_system >", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemGreaterThanOrEqualTo(String value) {
            addCriterion("third_system >=", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemLessThan(String value) {
            addCriterion("third_system <", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemLessThanOrEqualTo(String value) {
            addCriterion("third_system <=", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemLike(String value) {
            addCriterion("third_system like", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemNotLike(String value) {
            addCriterion("third_system not like", value, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemIn(List<String> values) {
            addCriterion("third_system in", values, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemNotIn(List<String> values) {
            addCriterion("third_system not in", values, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemBetween(String value1, String value2) {
            addCriterion("third_system between", value1, value2, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andThirdSystemNotBetween(String value1, String value2) {
            addCriterion("third_system not between", value1, value2, "thirdSystem");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(String value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(String value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(String value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(String value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(String value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(String value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(String value) {
            addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(String value) {
            addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<String> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<String> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(String value1, String value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(String value1, String value2) {
            addCriterion("sign not between", value1, value2, "sign");
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