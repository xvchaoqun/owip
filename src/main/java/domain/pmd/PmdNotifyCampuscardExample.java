package domain.pmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmdNotifyCampuscardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdNotifyCampuscardExample() {
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

        public Criteria andPaycodeIsNull() {
            addCriterion("paycode is null");
            return (Criteria) this;
        }

        public Criteria andPaycodeIsNotNull() {
            addCriterion("paycode is not null");
            return (Criteria) this;
        }

        public Criteria andPaycodeEqualTo(String value) {
            addCriterion("paycode =", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeNotEqualTo(String value) {
            addCriterion("paycode <>", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeGreaterThan(String value) {
            addCriterion("paycode >", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeGreaterThanOrEqualTo(String value) {
            addCriterion("paycode >=", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeLessThan(String value) {
            addCriterion("paycode <", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeLessThanOrEqualTo(String value) {
            addCriterion("paycode <=", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeLike(String value) {
            addCriterion("paycode like", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeNotLike(String value) {
            addCriterion("paycode not like", value, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeIn(List<String> values) {
            addCriterion("paycode in", values, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeNotIn(List<String> values) {
            addCriterion("paycode not in", values, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeBetween(String value1, String value2) {
            addCriterion("paycode between", value1, value2, "paycode");
            return (Criteria) this;
        }

        public Criteria andPaycodeNotBetween(String value1, String value2) {
            addCriterion("paycode not between", value1, value2, "paycode");
            return (Criteria) this;
        }

        public Criteria andPayitemIsNull() {
            addCriterion("payitem is null");
            return (Criteria) this;
        }

        public Criteria andPayitemIsNotNull() {
            addCriterion("payitem is not null");
            return (Criteria) this;
        }

        public Criteria andPayitemEqualTo(String value) {
            addCriterion("payitem =", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemNotEqualTo(String value) {
            addCriterion("payitem <>", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemGreaterThan(String value) {
            addCriterion("payitem >", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemGreaterThanOrEqualTo(String value) {
            addCriterion("payitem >=", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemLessThan(String value) {
            addCriterion("payitem <", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemLessThanOrEqualTo(String value) {
            addCriterion("payitem <=", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemLike(String value) {
            addCriterion("payitem like", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemNotLike(String value) {
            addCriterion("payitem not like", value, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemIn(List<String> values) {
            addCriterion("payitem in", values, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemNotIn(List<String> values) {
            addCriterion("payitem not in", values, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemBetween(String value1, String value2) {
            addCriterion("payitem between", value1, value2, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayitemNotBetween(String value1, String value2) {
            addCriterion("payitem not between", value1, value2, "payitem");
            return (Criteria) this;
        }

        public Criteria andPayerIsNull() {
            addCriterion("payer is null");
            return (Criteria) this;
        }

        public Criteria andPayerIsNotNull() {
            addCriterion("payer is not null");
            return (Criteria) this;
        }

        public Criteria andPayerEqualTo(String value) {
            addCriterion("payer =", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotEqualTo(String value) {
            addCriterion("payer <>", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerGreaterThan(String value) {
            addCriterion("payer >", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerGreaterThanOrEqualTo(String value) {
            addCriterion("payer >=", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLessThan(String value) {
            addCriterion("payer <", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLessThanOrEqualTo(String value) {
            addCriterion("payer <=", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLike(String value) {
            addCriterion("payer like", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotLike(String value) {
            addCriterion("payer not like", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerIn(List<String> values) {
            addCriterion("payer in", values, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotIn(List<String> values) {
            addCriterion("payer not in", values, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerBetween(String value1, String value2) {
            addCriterion("payer between", value1, value2, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotBetween(String value1, String value2) {
            addCriterion("payer not between", value1, value2, "payer");
            return (Criteria) this;
        }

        public Criteria andPayertypeIsNull() {
            addCriterion("payertype is null");
            return (Criteria) this;
        }

        public Criteria andPayertypeIsNotNull() {
            addCriterion("payertype is not null");
            return (Criteria) this;
        }

        public Criteria andPayertypeEqualTo(String value) {
            addCriterion("payertype =", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeNotEqualTo(String value) {
            addCriterion("payertype <>", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeGreaterThan(String value) {
            addCriterion("payertype >", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeGreaterThanOrEqualTo(String value) {
            addCriterion("payertype >=", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeLessThan(String value) {
            addCriterion("payertype <", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeLessThanOrEqualTo(String value) {
            addCriterion("payertype <=", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeLike(String value) {
            addCriterion("payertype like", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeNotLike(String value) {
            addCriterion("payertype not like", value, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeIn(List<String> values) {
            addCriterion("payertype in", values, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeNotIn(List<String> values) {
            addCriterion("payertype not in", values, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeBetween(String value1, String value2) {
            addCriterion("payertype between", value1, value2, "payertype");
            return (Criteria) this;
        }

        public Criteria andPayertypeNotBetween(String value1, String value2) {
            addCriterion("payertype not between", value1, value2, "payertype");
            return (Criteria) this;
        }

        public Criteria andSnIsNull() {
            addCriterion("sn is null");
            return (Criteria) this;
        }

        public Criteria andSnIsNotNull() {
            addCriterion("sn is not null");
            return (Criteria) this;
        }

        public Criteria andSnEqualTo(String value) {
            addCriterion("sn =", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotEqualTo(String value) {
            addCriterion("sn <>", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThan(String value) {
            addCriterion("sn >", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThanOrEqualTo(String value) {
            addCriterion("sn >=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThan(String value) {
            addCriterion("sn <", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThanOrEqualTo(String value) {
            addCriterion("sn <=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLike(String value) {
            addCriterion("sn like", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotLike(String value) {
            addCriterion("sn not like", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnIn(List<String> values) {
            addCriterion("sn in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotIn(List<String> values) {
            addCriterion("sn not in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnBetween(String value1, String value2) {
            addCriterion("sn between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotBetween(String value1, String value2) {
            addCriterion("sn not between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andAmtIsNull() {
            addCriterion("amt is null");
            return (Criteria) this;
        }

        public Criteria andAmtIsNotNull() {
            addCriterion("amt is not null");
            return (Criteria) this;
        }

        public Criteria andAmtEqualTo(String value) {
            addCriterion("amt =", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotEqualTo(String value) {
            addCriterion("amt <>", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThan(String value) {
            addCriterion("amt >", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtGreaterThanOrEqualTo(String value) {
            addCriterion("amt >=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThan(String value) {
            addCriterion("amt <", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLessThanOrEqualTo(String value) {
            addCriterion("amt <=", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtLike(String value) {
            addCriterion("amt like", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotLike(String value) {
            addCriterion("amt not like", value, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtIn(List<String> values) {
            addCriterion("amt in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotIn(List<String> values) {
            addCriterion("amt not in", values, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtBetween(String value1, String value2) {
            addCriterion("amt between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andAmtNotBetween(String value1, String value2) {
            addCriterion("amt not between", value1, value2, "amt");
            return (Criteria) this;
        }

        public Criteria andPaidIsNull() {
            addCriterion("paid is null");
            return (Criteria) this;
        }

        public Criteria andPaidIsNotNull() {
            addCriterion("paid is not null");
            return (Criteria) this;
        }

        public Criteria andPaidEqualTo(String value) {
            addCriterion("paid =", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidNotEqualTo(String value) {
            addCriterion("paid <>", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidGreaterThan(String value) {
            addCriterion("paid >", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidGreaterThanOrEqualTo(String value) {
            addCriterion("paid >=", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidLessThan(String value) {
            addCriterion("paid <", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidLessThanOrEqualTo(String value) {
            addCriterion("paid <=", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidLike(String value) {
            addCriterion("paid like", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidNotLike(String value) {
            addCriterion("paid not like", value, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidIn(List<String> values) {
            addCriterion("paid in", values, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidNotIn(List<String> values) {
            addCriterion("paid not in", values, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidBetween(String value1, String value2) {
            addCriterion("paid between", value1, value2, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidNotBetween(String value1, String value2) {
            addCriterion("paid not between", value1, value2, "paid");
            return (Criteria) this;
        }

        public Criteria andPaidtimeIsNull() {
            addCriterion("paidtime is null");
            return (Criteria) this;
        }

        public Criteria andPaidtimeIsNotNull() {
            addCriterion("paidtime is not null");
            return (Criteria) this;
        }

        public Criteria andPaidtimeEqualTo(String value) {
            addCriterion("paidtime =", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeNotEqualTo(String value) {
            addCriterion("paidtime <>", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeGreaterThan(String value) {
            addCriterion("paidtime >", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeGreaterThanOrEqualTo(String value) {
            addCriterion("paidtime >=", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeLessThan(String value) {
            addCriterion("paidtime <", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeLessThanOrEqualTo(String value) {
            addCriterion("paidtime <=", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeLike(String value) {
            addCriterion("paidtime like", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeNotLike(String value) {
            addCriterion("paidtime not like", value, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeIn(List<String> values) {
            addCriterion("paidtime in", values, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeNotIn(List<String> values) {
            addCriterion("paidtime not in", values, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeBetween(String value1, String value2) {
            addCriterion("paidtime between", value1, value2, "paidtime");
            return (Criteria) this;
        }

        public Criteria andPaidtimeNotBetween(String value1, String value2) {
            addCriterion("paidtime not between", value1, value2, "paidtime");
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

        public Criteria andVerifySignIsNull() {
            addCriterion("verify_sign is null");
            return (Criteria) this;
        }

        public Criteria andVerifySignIsNotNull() {
            addCriterion("verify_sign is not null");
            return (Criteria) this;
        }

        public Criteria andVerifySignEqualTo(Boolean value) {
            addCriterion("verify_sign =", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignNotEqualTo(Boolean value) {
            addCriterion("verify_sign <>", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignGreaterThan(Boolean value) {
            addCriterion("verify_sign >", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignGreaterThanOrEqualTo(Boolean value) {
            addCriterion("verify_sign >=", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignLessThan(Boolean value) {
            addCriterion("verify_sign <", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignLessThanOrEqualTo(Boolean value) {
            addCriterion("verify_sign <=", value, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignIn(List<Boolean> values) {
            addCriterion("verify_sign in", values, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignNotIn(List<Boolean> values) {
            addCriterion("verify_sign not in", values, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignBetween(Boolean value1, Boolean value2) {
            addCriterion("verify_sign between", value1, value2, "verifySign");
            return (Criteria) this;
        }

        public Criteria andVerifySignNotBetween(Boolean value1, Boolean value2) {
            addCriterion("verify_sign not between", value1, value2, "verifySign");
            return (Criteria) this;
        }

        public Criteria andRetTimeIsNull() {
            addCriterion("ret_time is null");
            return (Criteria) this;
        }

        public Criteria andRetTimeIsNotNull() {
            addCriterion("ret_time is not null");
            return (Criteria) this;
        }

        public Criteria andRetTimeEqualTo(Date value) {
            addCriterion("ret_time =", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotEqualTo(Date value) {
            addCriterion("ret_time <>", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeGreaterThan(Date value) {
            addCriterion("ret_time >", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ret_time >=", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeLessThan(Date value) {
            addCriterion("ret_time <", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeLessThanOrEqualTo(Date value) {
            addCriterion("ret_time <=", value, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeIn(List<Date> values) {
            addCriterion("ret_time in", values, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotIn(List<Date> values) {
            addCriterion("ret_time not in", values, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeBetween(Date value1, Date value2) {
            addCriterion("ret_time between", value1, value2, "retTime");
            return (Criteria) this;
        }

        public Criteria andRetTimeNotBetween(Date value1, Date value2) {
            addCriterion("ret_time not between", value1, value2, "retTime");
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