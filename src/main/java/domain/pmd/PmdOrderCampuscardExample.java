package domain.pmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmdOrderCampuscardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmdOrderCampuscardExample() {
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andIsBatchIsNull() {
            addCriterion("is_batch is null");
            return (Criteria) this;
        }

        public Criteria andIsBatchIsNotNull() {
            addCriterion("is_batch is not null");
            return (Criteria) this;
        }

        public Criteria andIsBatchEqualTo(Boolean value) {
            addCriterion("is_batch =", value, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchNotEqualTo(Boolean value) {
            addCriterion("is_batch <>", value, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchGreaterThan(Boolean value) {
            addCriterion("is_batch >", value, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_batch >=", value, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchLessThan(Boolean value) {
            addCriterion("is_batch <", value, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchLessThanOrEqualTo(Boolean value) {
            addCriterion("is_batch <=", value, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchIn(List<Boolean> values) {
            addCriterion("is_batch in", values, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchNotIn(List<Boolean> values) {
            addCriterion("is_batch not in", values, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchBetween(Boolean value1, Boolean value2) {
            addCriterion("is_batch between", value1, value2, "isBatch");
            return (Criteria) this;
        }

        public Criteria andIsBatchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_batch not between", value1, value2, "isBatch");
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

        public Criteria andPayMonthEqualTo(String value) {
            addCriterion("pay_month =", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotEqualTo(String value) {
            addCriterion("pay_month <>", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThan(String value) {
            addCriterion("pay_month >", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthGreaterThanOrEqualTo(String value) {
            addCriterion("pay_month >=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThan(String value) {
            addCriterion("pay_month <", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLessThanOrEqualTo(String value) {
            addCriterion("pay_month <=", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthLike(String value) {
            addCriterion("pay_month like", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotLike(String value) {
            addCriterion("pay_month not like", value, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthIn(List<String> values) {
            addCriterion("pay_month in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotIn(List<String> values) {
            addCriterion("pay_month not in", values, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthBetween(String value1, String value2) {
            addCriterion("pay_month between", value1, value2, "payMonth");
            return (Criteria) this;
        }

        public Criteria andPayMonthNotBetween(String value1, String value2) {
            addCriterion("pay_month not between", value1, value2, "payMonth");
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

        public Criteria andPayernameIsNull() {
            addCriterion("payername is null");
            return (Criteria) this;
        }

        public Criteria andPayernameIsNotNull() {
            addCriterion("payername is not null");
            return (Criteria) this;
        }

        public Criteria andPayernameEqualTo(String value) {
            addCriterion("payername =", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotEqualTo(String value) {
            addCriterion("payername <>", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameGreaterThan(String value) {
            addCriterion("payername >", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameGreaterThanOrEqualTo(String value) {
            addCriterion("payername >=", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameLessThan(String value) {
            addCriterion("payername <", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameLessThanOrEqualTo(String value) {
            addCriterion("payername <=", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameLike(String value) {
            addCriterion("payername like", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotLike(String value) {
            addCriterion("payername not like", value, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameIn(List<String> values) {
            addCriterion("payername in", values, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotIn(List<String> values) {
            addCriterion("payername not in", values, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameBetween(String value1, String value2) {
            addCriterion("payername between", value1, value2, "payername");
            return (Criteria) this;
        }

        public Criteria andPayernameNotBetween(String value1, String value2) {
            addCriterion("payername not between", value1, value2, "payername");
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

        public Criteria andMaccIsNull() {
            addCriterion("macc is null");
            return (Criteria) this;
        }

        public Criteria andMaccIsNotNull() {
            addCriterion("macc is not null");
            return (Criteria) this;
        }

        public Criteria andMaccEqualTo(String value) {
            addCriterion("macc =", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccNotEqualTo(String value) {
            addCriterion("macc <>", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccGreaterThan(String value) {
            addCriterion("macc >", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccGreaterThanOrEqualTo(String value) {
            addCriterion("macc >=", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccLessThan(String value) {
            addCriterion("macc <", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccLessThanOrEqualTo(String value) {
            addCriterion("macc <=", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccLike(String value) {
            addCriterion("macc like", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccNotLike(String value) {
            addCriterion("macc not like", value, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccIn(List<String> values) {
            addCriterion("macc in", values, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccNotIn(List<String> values) {
            addCriterion("macc not in", values, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccBetween(String value1, String value2) {
            addCriterion("macc between", value1, value2, "macc");
            return (Criteria) this;
        }

        public Criteria andMaccNotBetween(String value1, String value2) {
            addCriterion("macc not between", value1, value2, "macc");
            return (Criteria) this;
        }

        public Criteria andCommnetIsNull() {
            addCriterion("commnet is null");
            return (Criteria) this;
        }

        public Criteria andCommnetIsNotNull() {
            addCriterion("commnet is not null");
            return (Criteria) this;
        }

        public Criteria andCommnetEqualTo(String value) {
            addCriterion("commnet =", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetNotEqualTo(String value) {
            addCriterion("commnet <>", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetGreaterThan(String value) {
            addCriterion("commnet >", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetGreaterThanOrEqualTo(String value) {
            addCriterion("commnet >=", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetLessThan(String value) {
            addCriterion("commnet <", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetLessThanOrEqualTo(String value) {
            addCriterion("commnet <=", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetLike(String value) {
            addCriterion("commnet like", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetNotLike(String value) {
            addCriterion("commnet not like", value, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetIn(List<String> values) {
            addCriterion("commnet in", values, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetNotIn(List<String> values) {
            addCriterion("commnet not in", values, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetBetween(String value1, String value2) {
            addCriterion("commnet between", value1, value2, "commnet");
            return (Criteria) this;
        }

        public Criteria andCommnetNotBetween(String value1, String value2) {
            addCriterion("commnet not between", value1, value2, "commnet");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameIsNull() {
            addCriterion("sno_id_name is null");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameIsNotNull() {
            addCriterion("sno_id_name is not null");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameEqualTo(String value) {
            addCriterion("sno_id_name =", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameNotEqualTo(String value) {
            addCriterion("sno_id_name <>", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameGreaterThan(String value) {
            addCriterion("sno_id_name >", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameGreaterThanOrEqualTo(String value) {
            addCriterion("sno_id_name >=", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameLessThan(String value) {
            addCriterion("sno_id_name <", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameLessThanOrEqualTo(String value) {
            addCriterion("sno_id_name <=", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameLike(String value) {
            addCriterion("sno_id_name like", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameNotLike(String value) {
            addCriterion("sno_id_name not like", value, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameIn(List<String> values) {
            addCriterion("sno_id_name in", values, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameNotIn(List<String> values) {
            addCriterion("sno_id_name not in", values, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameBetween(String value1, String value2) {
            addCriterion("sno_id_name between", value1, value2, "snoIdName");
            return (Criteria) this;
        }

        public Criteria andSnoIdNameNotBetween(String value1, String value2) {
            addCriterion("sno_id_name not between", value1, value2, "snoIdName");
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

        public Criteria andIsClosedIsNull() {
            addCriterion("is_closed is null");
            return (Criteria) this;
        }

        public Criteria andIsClosedIsNotNull() {
            addCriterion("is_closed is not null");
            return (Criteria) this;
        }

        public Criteria andIsClosedEqualTo(Boolean value) {
            addCriterion("is_closed =", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotEqualTo(Boolean value) {
            addCriterion("is_closed <>", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedGreaterThan(Boolean value) {
            addCriterion("is_closed >", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_closed >=", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedLessThan(Boolean value) {
            addCriterion("is_closed <", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_closed <=", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedIn(List<Boolean> values) {
            addCriterion("is_closed in", values, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotIn(List<Boolean> values) {
            addCriterion("is_closed not in", values, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_closed between", value1, value2, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_closed not between", value1, value2, "isClosed");
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