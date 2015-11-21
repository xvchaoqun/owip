package domain;

import java.util.ArrayList;
import java.util.List;

public class ExtYjsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExtYjsExample() {
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

        public Criteria andXhIsNull() {
            addCriterion("xh is null");
            return (Criteria) this;
        }

        public Criteria andXhIsNotNull() {
            addCriterion("xh is not null");
            return (Criteria) this;
        }

        public Criteria andXhEqualTo(String value) {
            addCriterion("xh =", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhNotEqualTo(String value) {
            addCriterion("xh <>", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhGreaterThan(String value) {
            addCriterion("xh >", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhGreaterThanOrEqualTo(String value) {
            addCriterion("xh >=", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhLessThan(String value) {
            addCriterion("xh <", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhLessThanOrEqualTo(String value) {
            addCriterion("xh <=", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhLike(String value) {
            addCriterion("xh like", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhNotLike(String value) {
            addCriterion("xh not like", value, "xh");
            return (Criteria) this;
        }

        public Criteria andXhIn(List<String> values) {
            addCriterion("xh in", values, "xh");
            return (Criteria) this;
        }

        public Criteria andXhNotIn(List<String> values) {
            addCriterion("xh not in", values, "xh");
            return (Criteria) this;
        }

        public Criteria andXhBetween(String value1, String value2) {
            addCriterion("xh between", value1, value2, "xh");
            return (Criteria) this;
        }

        public Criteria andXhNotBetween(String value1, String value2) {
            addCriterion("xh not between", value1, value2, "xh");
            return (Criteria) this;
        }

        public Criteria andZtmIsNull() {
            addCriterion("ztm is null");
            return (Criteria) this;
        }

        public Criteria andZtmIsNotNull() {
            addCriterion("ztm is not null");
            return (Criteria) this;
        }

        public Criteria andZtmEqualTo(String value) {
            addCriterion("ztm =", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmNotEqualTo(String value) {
            addCriterion("ztm <>", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmGreaterThan(String value) {
            addCriterion("ztm >", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmGreaterThanOrEqualTo(String value) {
            addCriterion("ztm >=", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmLessThan(String value) {
            addCriterion("ztm <", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmLessThanOrEqualTo(String value) {
            addCriterion("ztm <=", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmLike(String value) {
            addCriterion("ztm like", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmNotLike(String value) {
            addCriterion("ztm not like", value, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmIn(List<String> values) {
            addCriterion("ztm in", values, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmNotIn(List<String> values) {
            addCriterion("ztm not in", values, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmBetween(String value1, String value2) {
            addCriterion("ztm between", value1, value2, "ztm");
            return (Criteria) this;
        }

        public Criteria andZtmNotBetween(String value1, String value2) {
            addCriterion("ztm not between", value1, value2, "ztm");
            return (Criteria) this;
        }

        public Criteria andGbmIsNull() {
            addCriterion("gbm is null");
            return (Criteria) this;
        }

        public Criteria andGbmIsNotNull() {
            addCriterion("gbm is not null");
            return (Criteria) this;
        }

        public Criteria andGbmEqualTo(String value) {
            addCriterion("gbm =", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmNotEqualTo(String value) {
            addCriterion("gbm <>", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmGreaterThan(String value) {
            addCriterion("gbm >", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmGreaterThanOrEqualTo(String value) {
            addCriterion("gbm >=", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmLessThan(String value) {
            addCriterion("gbm <", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmLessThanOrEqualTo(String value) {
            addCriterion("gbm <=", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmLike(String value) {
            addCriterion("gbm like", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmNotLike(String value) {
            addCriterion("gbm not like", value, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmIn(List<String> values) {
            addCriterion("gbm in", values, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmNotIn(List<String> values) {
            addCriterion("gbm not in", values, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmBetween(String value1, String value2) {
            addCriterion("gbm between", value1, value2, "gbm");
            return (Criteria) this;
        }

        public Criteria andGbmNotBetween(String value1, String value2) {
            addCriterion("gbm not between", value1, value2, "gbm");
            return (Criteria) this;
        }

        public Criteria andXslbm2IsNull() {
            addCriterion("xslbm2 is null");
            return (Criteria) this;
        }

        public Criteria andXslbm2IsNotNull() {
            addCriterion("xslbm2 is not null");
            return (Criteria) this;
        }

        public Criteria andXslbm2EqualTo(String value) {
            addCriterion("xslbm2 =", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2NotEqualTo(String value) {
            addCriterion("xslbm2 <>", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2GreaterThan(String value) {
            addCriterion("xslbm2 >", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2GreaterThanOrEqualTo(String value) {
            addCriterion("xslbm2 >=", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2LessThan(String value) {
            addCriterion("xslbm2 <", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2LessThanOrEqualTo(String value) {
            addCriterion("xslbm2 <=", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2Like(String value) {
            addCriterion("xslbm2 like", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2NotLike(String value) {
            addCriterion("xslbm2 not like", value, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2In(List<String> values) {
            addCriterion("xslbm2 in", values, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2NotIn(List<String> values) {
            addCriterion("xslbm2 not in", values, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2Between(String value1, String value2) {
            addCriterion("xslbm2 between", value1, value2, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXslbm2NotBetween(String value1, String value2) {
            addCriterion("xslbm2 not between", value1, value2, "xslbm2");
            return (Criteria) this;
        }

        public Criteria andXmIsNull() {
            addCriterion("xm is null");
            return (Criteria) this;
        }

        public Criteria andXmIsNotNull() {
            addCriterion("xm is not null");
            return (Criteria) this;
        }

        public Criteria andXmEqualTo(String value) {
            addCriterion("xm =", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmNotEqualTo(String value) {
            addCriterion("xm <>", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmGreaterThan(String value) {
            addCriterion("xm >", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmGreaterThanOrEqualTo(String value) {
            addCriterion("xm >=", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmLessThan(String value) {
            addCriterion("xm <", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmLessThanOrEqualTo(String value) {
            addCriterion("xm <=", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmLike(String value) {
            addCriterion("xm like", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmNotLike(String value) {
            addCriterion("xm not like", value, "xm");
            return (Criteria) this;
        }

        public Criteria andXmIn(List<String> values) {
            addCriterion("xm in", values, "xm");
            return (Criteria) this;
        }

        public Criteria andXmNotIn(List<String> values) {
            addCriterion("xm not in", values, "xm");
            return (Criteria) this;
        }

        public Criteria andXmBetween(String value1, String value2) {
            addCriterion("xm between", value1, value2, "xm");
            return (Criteria) this;
        }

        public Criteria andXmNotBetween(String value1, String value2) {
            addCriterion("xm not between", value1, value2, "xm");
            return (Criteria) this;
        }

        public Criteria andXmpyIsNull() {
            addCriterion("xmpy is null");
            return (Criteria) this;
        }

        public Criteria andXmpyIsNotNull() {
            addCriterion("xmpy is not null");
            return (Criteria) this;
        }

        public Criteria andXmpyEqualTo(String value) {
            addCriterion("xmpy =", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyNotEqualTo(String value) {
            addCriterion("xmpy <>", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyGreaterThan(String value) {
            addCriterion("xmpy >", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyGreaterThanOrEqualTo(String value) {
            addCriterion("xmpy >=", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyLessThan(String value) {
            addCriterion("xmpy <", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyLessThanOrEqualTo(String value) {
            addCriterion("xmpy <=", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyLike(String value) {
            addCriterion("xmpy like", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyNotLike(String value) {
            addCriterion("xmpy not like", value, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyIn(List<String> values) {
            addCriterion("xmpy in", values, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyNotIn(List<String> values) {
            addCriterion("xmpy not in", values, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyBetween(String value1, String value2) {
            addCriterion("xmpy between", value1, value2, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXmpyNotBetween(String value1, String value2) {
            addCriterion("xmpy not between", value1, value2, "xmpy");
            return (Criteria) this;
        }

        public Criteria andXbmIsNull() {
            addCriterion("xbm is null");
            return (Criteria) this;
        }

        public Criteria andXbmIsNotNull() {
            addCriterion("xbm is not null");
            return (Criteria) this;
        }

        public Criteria andXbmEqualTo(String value) {
            addCriterion("xbm =", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmNotEqualTo(String value) {
            addCriterion("xbm <>", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmGreaterThan(String value) {
            addCriterion("xbm >", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmGreaterThanOrEqualTo(String value) {
            addCriterion("xbm >=", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmLessThan(String value) {
            addCriterion("xbm <", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmLessThanOrEqualTo(String value) {
            addCriterion("xbm <=", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmLike(String value) {
            addCriterion("xbm like", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmNotLike(String value) {
            addCriterion("xbm not like", value, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmIn(List<String> values) {
            addCriterion("xbm in", values, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmNotIn(List<String> values) {
            addCriterion("xbm not in", values, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmBetween(String value1, String value2) {
            addCriterion("xbm between", value1, value2, "xbm");
            return (Criteria) this;
        }

        public Criteria andXbmNotBetween(String value1, String value2) {
            addCriterion("xbm not between", value1, value2, "xbm");
            return (Criteria) this;
        }

        public Criteria andCsrqIsNull() {
            addCriterion("csrq is null");
            return (Criteria) this;
        }

        public Criteria andCsrqIsNotNull() {
            addCriterion("csrq is not null");
            return (Criteria) this;
        }

        public Criteria andCsrqEqualTo(String value) {
            addCriterion("csrq =", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotEqualTo(String value) {
            addCriterion("csrq <>", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqGreaterThan(String value) {
            addCriterion("csrq >", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqGreaterThanOrEqualTo(String value) {
            addCriterion("csrq >=", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqLessThan(String value) {
            addCriterion("csrq <", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqLessThanOrEqualTo(String value) {
            addCriterion("csrq <=", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqLike(String value) {
            addCriterion("csrq like", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotLike(String value) {
            addCriterion("csrq not like", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqIn(List<String> values) {
            addCriterion("csrq in", values, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotIn(List<String> values) {
            addCriterion("csrq not in", values, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqBetween(String value1, String value2) {
            addCriterion("csrq between", value1, value2, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotBetween(String value1, String value2) {
            addCriterion("csrq not between", value1, value2, "csrq");
            return (Criteria) this;
        }

        public Criteria andSfzhIsNull() {
            addCriterion("sfzh is null");
            return (Criteria) this;
        }

        public Criteria andSfzhIsNotNull() {
            addCriterion("sfzh is not null");
            return (Criteria) this;
        }

        public Criteria andSfzhEqualTo(String value) {
            addCriterion("sfzh =", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhNotEqualTo(String value) {
            addCriterion("sfzh <>", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhGreaterThan(String value) {
            addCriterion("sfzh >", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhGreaterThanOrEqualTo(String value) {
            addCriterion("sfzh >=", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhLessThan(String value) {
            addCriterion("sfzh <", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhLessThanOrEqualTo(String value) {
            addCriterion("sfzh <=", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhLike(String value) {
            addCriterion("sfzh like", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhNotLike(String value) {
            addCriterion("sfzh not like", value, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhIn(List<String> values) {
            addCriterion("sfzh in", values, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhNotIn(List<String> values) {
            addCriterion("sfzh not in", values, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhBetween(String value1, String value2) {
            addCriterion("sfzh between", value1, value2, "sfzh");
            return (Criteria) this;
        }

        public Criteria andSfzhNotBetween(String value1, String value2) {
            addCriterion("sfzh not between", value1, value2, "sfzh");
            return (Criteria) this;
        }

        public Criteria andMzmIsNull() {
            addCriterion("mzm is null");
            return (Criteria) this;
        }

        public Criteria andMzmIsNotNull() {
            addCriterion("mzm is not null");
            return (Criteria) this;
        }

        public Criteria andMzmEqualTo(String value) {
            addCriterion("mzm =", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmNotEqualTo(String value) {
            addCriterion("mzm <>", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmGreaterThan(String value) {
            addCriterion("mzm >", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmGreaterThanOrEqualTo(String value) {
            addCriterion("mzm >=", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmLessThan(String value) {
            addCriterion("mzm <", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmLessThanOrEqualTo(String value) {
            addCriterion("mzm <=", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmLike(String value) {
            addCriterion("mzm like", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmNotLike(String value) {
            addCriterion("mzm not like", value, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmIn(List<String> values) {
            addCriterion("mzm in", values, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmNotIn(List<String> values) {
            addCriterion("mzm not in", values, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmBetween(String value1, String value2) {
            addCriterion("mzm between", value1, value2, "mzm");
            return (Criteria) this;
        }

        public Criteria andMzmNotBetween(String value1, String value2) {
            addCriterion("mzm not between", value1, value2, "mzm");
            return (Criteria) this;
        }

        public Criteria andPyccmIsNull() {
            addCriterion("pyccm is null");
            return (Criteria) this;
        }

        public Criteria andPyccmIsNotNull() {
            addCriterion("pyccm is not null");
            return (Criteria) this;
        }

        public Criteria andPyccmEqualTo(String value) {
            addCriterion("pyccm =", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmNotEqualTo(String value) {
            addCriterion("pyccm <>", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmGreaterThan(String value) {
            addCriterion("pyccm >", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmGreaterThanOrEqualTo(String value) {
            addCriterion("pyccm >=", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmLessThan(String value) {
            addCriterion("pyccm <", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmLessThanOrEqualTo(String value) {
            addCriterion("pyccm <=", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmLike(String value) {
            addCriterion("pyccm like", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmNotLike(String value) {
            addCriterion("pyccm not like", value, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmIn(List<String> values) {
            addCriterion("pyccm in", values, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmNotIn(List<String> values) {
            addCriterion("pyccm not in", values, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmBetween(String value1, String value2) {
            addCriterion("pyccm between", value1, value2, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPyccmNotBetween(String value1, String value2) {
            addCriterion("pyccm not between", value1, value2, "pyccm");
            return (Criteria) this;
        }

        public Criteria andPylxmIsNull() {
            addCriterion("pylxm is null");
            return (Criteria) this;
        }

        public Criteria andPylxmIsNotNull() {
            addCriterion("pylxm is not null");
            return (Criteria) this;
        }

        public Criteria andPylxmEqualTo(String value) {
            addCriterion("pylxm =", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmNotEqualTo(String value) {
            addCriterion("pylxm <>", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmGreaterThan(String value) {
            addCriterion("pylxm >", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmGreaterThanOrEqualTo(String value) {
            addCriterion("pylxm >=", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmLessThan(String value) {
            addCriterion("pylxm <", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmLessThanOrEqualTo(String value) {
            addCriterion("pylxm <=", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmLike(String value) {
            addCriterion("pylxm like", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmNotLike(String value) {
            addCriterion("pylxm not like", value, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmIn(List<String> values) {
            addCriterion("pylxm in", values, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmNotIn(List<String> values) {
            addCriterion("pylxm not in", values, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmBetween(String value1, String value2) {
            addCriterion("pylxm between", value1, value2, "pylxm");
            return (Criteria) this;
        }

        public Criteria andPylxmNotBetween(String value1, String value2) {
            addCriterion("pylxm not between", value1, value2, "pylxm");
            return (Criteria) this;
        }

        public Criteria andJylbmIsNull() {
            addCriterion("jylbm is null");
            return (Criteria) this;
        }

        public Criteria andJylbmIsNotNull() {
            addCriterion("jylbm is not null");
            return (Criteria) this;
        }

        public Criteria andJylbmEqualTo(String value) {
            addCriterion("jylbm =", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmNotEqualTo(String value) {
            addCriterion("jylbm <>", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmGreaterThan(String value) {
            addCriterion("jylbm >", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmGreaterThanOrEqualTo(String value) {
            addCriterion("jylbm >=", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmLessThan(String value) {
            addCriterion("jylbm <", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmLessThanOrEqualTo(String value) {
            addCriterion("jylbm <=", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmLike(String value) {
            addCriterion("jylbm like", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmNotLike(String value) {
            addCriterion("jylbm not like", value, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmIn(List<String> values) {
            addCriterion("jylbm in", values, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmNotIn(List<String> values) {
            addCriterion("jylbm not in", values, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmBetween(String value1, String value2) {
            addCriterion("jylbm between", value1, value2, "jylbm");
            return (Criteria) this;
        }

        public Criteria andJylbmNotBetween(String value1, String value2) {
            addCriterion("jylbm not between", value1, value2, "jylbm");
            return (Criteria) this;
        }

        public Criteria andPyfsmIsNull() {
            addCriterion("pyfsm is null");
            return (Criteria) this;
        }

        public Criteria andPyfsmIsNotNull() {
            addCriterion("pyfsm is not null");
            return (Criteria) this;
        }

        public Criteria andPyfsmEqualTo(String value) {
            addCriterion("pyfsm =", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmNotEqualTo(String value) {
            addCriterion("pyfsm <>", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmGreaterThan(String value) {
            addCriterion("pyfsm >", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmGreaterThanOrEqualTo(String value) {
            addCriterion("pyfsm >=", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmLessThan(String value) {
            addCriterion("pyfsm <", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmLessThanOrEqualTo(String value) {
            addCriterion("pyfsm <=", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmLike(String value) {
            addCriterion("pyfsm like", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmNotLike(String value) {
            addCriterion("pyfsm not like", value, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmIn(List<String> values) {
            addCriterion("pyfsm in", values, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmNotIn(List<String> values) {
            addCriterion("pyfsm not in", values, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmBetween(String value1, String value2) {
            addCriterion("pyfsm between", value1, value2, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andPyfsmNotBetween(String value1, String value2) {
            addCriterion("pyfsm not between", value1, value2, "pyfsm");
            return (Criteria) this;
        }

        public Criteria andKshIsNull() {
            addCriterion("ksh is null");
            return (Criteria) this;
        }

        public Criteria andKshIsNotNull() {
            addCriterion("ksh is not null");
            return (Criteria) this;
        }

        public Criteria andKshEqualTo(String value) {
            addCriterion("ksh =", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshNotEqualTo(String value) {
            addCriterion("ksh <>", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshGreaterThan(String value) {
            addCriterion("ksh >", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshGreaterThanOrEqualTo(String value) {
            addCriterion("ksh >=", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshLessThan(String value) {
            addCriterion("ksh <", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshLessThanOrEqualTo(String value) {
            addCriterion("ksh <=", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshLike(String value) {
            addCriterion("ksh like", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshNotLike(String value) {
            addCriterion("ksh not like", value, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshIn(List<String> values) {
            addCriterion("ksh in", values, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshNotIn(List<String> values) {
            addCriterion("ksh not in", values, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshBetween(String value1, String value2) {
            addCriterion("ksh between", value1, value2, "ksh");
            return (Criteria) this;
        }

        public Criteria andKshNotBetween(String value1, String value2) {
            addCriterion("ksh not between", value1, value2, "ksh");
            return (Criteria) this;
        }

        public Criteria andYxshIsNull() {
            addCriterion("yxsh is null");
            return (Criteria) this;
        }

        public Criteria andYxshIsNotNull() {
            addCriterion("yxsh is not null");
            return (Criteria) this;
        }

        public Criteria andYxshEqualTo(String value) {
            addCriterion("yxsh =", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshNotEqualTo(String value) {
            addCriterion("yxsh <>", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshGreaterThan(String value) {
            addCriterion("yxsh >", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshGreaterThanOrEqualTo(String value) {
            addCriterion("yxsh >=", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshLessThan(String value) {
            addCriterion("yxsh <", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshLessThanOrEqualTo(String value) {
            addCriterion("yxsh <=", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshLike(String value) {
            addCriterion("yxsh like", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshNotLike(String value) {
            addCriterion("yxsh not like", value, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshIn(List<String> values) {
            addCriterion("yxsh in", values, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshNotIn(List<String> values) {
            addCriterion("yxsh not in", values, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshBetween(String value1, String value2) {
            addCriterion("yxsh between", value1, value2, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxshNotBetween(String value1, String value2) {
            addCriterion("yxsh not between", value1, value2, "yxsh");
            return (Criteria) this;
        }

        public Criteria andYxsmIsNull() {
            addCriterion("yxsm is null");
            return (Criteria) this;
        }

        public Criteria andYxsmIsNotNull() {
            addCriterion("yxsm is not null");
            return (Criteria) this;
        }

        public Criteria andYxsmEqualTo(String value) {
            addCriterion("yxsm =", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmNotEqualTo(String value) {
            addCriterion("yxsm <>", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmGreaterThan(String value) {
            addCriterion("yxsm >", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmGreaterThanOrEqualTo(String value) {
            addCriterion("yxsm >=", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmLessThan(String value) {
            addCriterion("yxsm <", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmLessThanOrEqualTo(String value) {
            addCriterion("yxsm <=", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmLike(String value) {
            addCriterion("yxsm like", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmNotLike(String value) {
            addCriterion("yxsm not like", value, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmIn(List<String> values) {
            addCriterion("yxsm in", values, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmNotIn(List<String> values) {
            addCriterion("yxsm not in", values, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmBetween(String value1, String value2) {
            addCriterion("yxsm between", value1, value2, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmNotBetween(String value1, String value2) {
            addCriterion("yxsm not between", value1, value2, "yxsm");
            return (Criteria) this;
        }

        public Criteria andYxsmcIsNull() {
            addCriterion("yxsmc is null");
            return (Criteria) this;
        }

        public Criteria andYxsmcIsNotNull() {
            addCriterion("yxsmc is not null");
            return (Criteria) this;
        }

        public Criteria andYxsmcEqualTo(String value) {
            addCriterion("yxsmc =", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcNotEqualTo(String value) {
            addCriterion("yxsmc <>", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcGreaterThan(String value) {
            addCriterion("yxsmc >", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcGreaterThanOrEqualTo(String value) {
            addCriterion("yxsmc >=", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcLessThan(String value) {
            addCriterion("yxsmc <", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcLessThanOrEqualTo(String value) {
            addCriterion("yxsmc <=", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcLike(String value) {
            addCriterion("yxsmc like", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcNotLike(String value) {
            addCriterion("yxsmc not like", value, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcIn(List<String> values) {
            addCriterion("yxsmc in", values, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcNotIn(List<String> values) {
            addCriterion("yxsmc not in", values, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcBetween(String value1, String value2) {
            addCriterion("yxsmc between", value1, value2, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYxsmcNotBetween(String value1, String value2) {
            addCriterion("yxsmc not between", value1, value2, "yxsmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmIsNull() {
            addCriterion("yjxkm is null");
            return (Criteria) this;
        }

        public Criteria andYjxkmIsNotNull() {
            addCriterion("yjxkm is not null");
            return (Criteria) this;
        }

        public Criteria andYjxkmEqualTo(String value) {
            addCriterion("yjxkm =", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmNotEqualTo(String value) {
            addCriterion("yjxkm <>", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmGreaterThan(String value) {
            addCriterion("yjxkm >", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmGreaterThanOrEqualTo(String value) {
            addCriterion("yjxkm >=", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmLessThan(String value) {
            addCriterion("yjxkm <", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmLessThanOrEqualTo(String value) {
            addCriterion("yjxkm <=", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmLike(String value) {
            addCriterion("yjxkm like", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmNotLike(String value) {
            addCriterion("yjxkm not like", value, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmIn(List<String> values) {
            addCriterion("yjxkm in", values, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmNotIn(List<String> values) {
            addCriterion("yjxkm not in", values, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmBetween(String value1, String value2) {
            addCriterion("yjxkm between", value1, value2, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmNotBetween(String value1, String value2) {
            addCriterion("yjxkm not between", value1, value2, "yjxkm");
            return (Criteria) this;
        }

        public Criteria andYjxkmcIsNull() {
            addCriterion("yjxkmc is null");
            return (Criteria) this;
        }

        public Criteria andYjxkmcIsNotNull() {
            addCriterion("yjxkmc is not null");
            return (Criteria) this;
        }

        public Criteria andYjxkmcEqualTo(String value) {
            addCriterion("yjxkmc =", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcNotEqualTo(String value) {
            addCriterion("yjxkmc <>", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcGreaterThan(String value) {
            addCriterion("yjxkmc >", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcGreaterThanOrEqualTo(String value) {
            addCriterion("yjxkmc >=", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcLessThan(String value) {
            addCriterion("yjxkmc <", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcLessThanOrEqualTo(String value) {
            addCriterion("yjxkmc <=", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcLike(String value) {
            addCriterion("yjxkmc like", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcNotLike(String value) {
            addCriterion("yjxkmc not like", value, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcIn(List<String> values) {
            addCriterion("yjxkmc in", values, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcNotIn(List<String> values) {
            addCriterion("yjxkmc not in", values, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcBetween(String value1, String value2) {
            addCriterion("yjxkmc between", value1, value2, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andYjxkmcNotBetween(String value1, String value2) {
            addCriterion("yjxkmc not between", value1, value2, "yjxkmc");
            return (Criteria) this;
        }

        public Criteria andZydmIsNull() {
            addCriterion("zydm is null");
            return (Criteria) this;
        }

        public Criteria andZydmIsNotNull() {
            addCriterion("zydm is not null");
            return (Criteria) this;
        }

        public Criteria andZydmEqualTo(String value) {
            addCriterion("zydm =", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmNotEqualTo(String value) {
            addCriterion("zydm <>", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmGreaterThan(String value) {
            addCriterion("zydm >", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmGreaterThanOrEqualTo(String value) {
            addCriterion("zydm >=", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmLessThan(String value) {
            addCriterion("zydm <", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmLessThanOrEqualTo(String value) {
            addCriterion("zydm <=", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmLike(String value) {
            addCriterion("zydm like", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmNotLike(String value) {
            addCriterion("zydm not like", value, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmIn(List<String> values) {
            addCriterion("zydm in", values, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmNotIn(List<String> values) {
            addCriterion("zydm not in", values, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmBetween(String value1, String value2) {
            addCriterion("zydm between", value1, value2, "zydm");
            return (Criteria) this;
        }

        public Criteria andZydmNotBetween(String value1, String value2) {
            addCriterion("zydm not between", value1, value2, "zydm");
            return (Criteria) this;
        }

        public Criteria andZymcIsNull() {
            addCriterion("zymc is null");
            return (Criteria) this;
        }

        public Criteria andZymcIsNotNull() {
            addCriterion("zymc is not null");
            return (Criteria) this;
        }

        public Criteria andZymcEqualTo(String value) {
            addCriterion("zymc =", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcNotEqualTo(String value) {
            addCriterion("zymc <>", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcGreaterThan(String value) {
            addCriterion("zymc >", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcGreaterThanOrEqualTo(String value) {
            addCriterion("zymc >=", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcLessThan(String value) {
            addCriterion("zymc <", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcLessThanOrEqualTo(String value) {
            addCriterion("zymc <=", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcLike(String value) {
            addCriterion("zymc like", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcNotLike(String value) {
            addCriterion("zymc not like", value, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcIn(List<String> values) {
            addCriterion("zymc in", values, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcNotIn(List<String> values) {
            addCriterion("zymc not in", values, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcBetween(String value1, String value2) {
            addCriterion("zymc between", value1, value2, "zymc");
            return (Criteria) this;
        }

        public Criteria andZymcNotBetween(String value1, String value2) {
            addCriterion("zymc not between", value1, value2, "zymc");
            return (Criteria) this;
        }

        public Criteria andYjfxmIsNull() {
            addCriterion("yjfxm is null");
            return (Criteria) this;
        }

        public Criteria andYjfxmIsNotNull() {
            addCriterion("yjfxm is not null");
            return (Criteria) this;
        }

        public Criteria andYjfxmEqualTo(String value) {
            addCriterion("yjfxm =", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmNotEqualTo(String value) {
            addCriterion("yjfxm <>", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmGreaterThan(String value) {
            addCriterion("yjfxm >", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmGreaterThanOrEqualTo(String value) {
            addCriterion("yjfxm >=", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmLessThan(String value) {
            addCriterion("yjfxm <", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmLessThanOrEqualTo(String value) {
            addCriterion("yjfxm <=", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmLike(String value) {
            addCriterion("yjfxm like", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmNotLike(String value) {
            addCriterion("yjfxm not like", value, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmIn(List<String> values) {
            addCriterion("yjfxm in", values, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmNotIn(List<String> values) {
            addCriterion("yjfxm not in", values, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmBetween(String value1, String value2) {
            addCriterion("yjfxm between", value1, value2, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmNotBetween(String value1, String value2) {
            addCriterion("yjfxm not between", value1, value2, "yjfxm");
            return (Criteria) this;
        }

        public Criteria andYjfxmcIsNull() {
            addCriterion("yjfxmc is null");
            return (Criteria) this;
        }

        public Criteria andYjfxmcIsNotNull() {
            addCriterion("yjfxmc is not null");
            return (Criteria) this;
        }

        public Criteria andYjfxmcEqualTo(String value) {
            addCriterion("yjfxmc =", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcNotEqualTo(String value) {
            addCriterion("yjfxmc <>", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcGreaterThan(String value) {
            addCriterion("yjfxmc >", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcGreaterThanOrEqualTo(String value) {
            addCriterion("yjfxmc >=", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcLessThan(String value) {
            addCriterion("yjfxmc <", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcLessThanOrEqualTo(String value) {
            addCriterion("yjfxmc <=", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcLike(String value) {
            addCriterion("yjfxmc like", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcNotLike(String value) {
            addCriterion("yjfxmc not like", value, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcIn(List<String> values) {
            addCriterion("yjfxmc in", values, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcNotIn(List<String> values) {
            addCriterion("yjfxmc not in", values, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcBetween(String value1, String value2) {
            addCriterion("yjfxmc between", value1, value2, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andYjfxmcNotBetween(String value1, String value2) {
            addCriterion("yjfxmc not between", value1, value2, "yjfxmc");
            return (Criteria) this;
        }

        public Criteria andDshIsNull() {
            addCriterion("dsh is null");
            return (Criteria) this;
        }

        public Criteria andDshIsNotNull() {
            addCriterion("dsh is not null");
            return (Criteria) this;
        }

        public Criteria andDshEqualTo(String value) {
            addCriterion("dsh =", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshNotEqualTo(String value) {
            addCriterion("dsh <>", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshGreaterThan(String value) {
            addCriterion("dsh >", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshGreaterThanOrEqualTo(String value) {
            addCriterion("dsh >=", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshLessThan(String value) {
            addCriterion("dsh <", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshLessThanOrEqualTo(String value) {
            addCriterion("dsh <=", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshLike(String value) {
            addCriterion("dsh like", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshNotLike(String value) {
            addCriterion("dsh not like", value, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshIn(List<String> values) {
            addCriterion("dsh in", values, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshNotIn(List<String> values) {
            addCriterion("dsh not in", values, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshBetween(String value1, String value2) {
            addCriterion("dsh between", value1, value2, "dsh");
            return (Criteria) this;
        }

        public Criteria andDshNotBetween(String value1, String value2) {
            addCriterion("dsh not between", value1, value2, "dsh");
            return (Criteria) this;
        }

        public Criteria andDsxmIsNull() {
            addCriterion("dsxm is null");
            return (Criteria) this;
        }

        public Criteria andDsxmIsNotNull() {
            addCriterion("dsxm is not null");
            return (Criteria) this;
        }

        public Criteria andDsxmEqualTo(String value) {
            addCriterion("dsxm =", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmNotEqualTo(String value) {
            addCriterion("dsxm <>", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmGreaterThan(String value) {
            addCriterion("dsxm >", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmGreaterThanOrEqualTo(String value) {
            addCriterion("dsxm >=", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmLessThan(String value) {
            addCriterion("dsxm <", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmLessThanOrEqualTo(String value) {
            addCriterion("dsxm <=", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmLike(String value) {
            addCriterion("dsxm like", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmNotLike(String value) {
            addCriterion("dsxm not like", value, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmIn(List<String> values) {
            addCriterion("dsxm in", values, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmNotIn(List<String> values) {
            addCriterion("dsxm not in", values, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmBetween(String value1, String value2) {
            addCriterion("dsxm between", value1, value2, "dsxm");
            return (Criteria) this;
        }

        public Criteria andDsxmNotBetween(String value1, String value2) {
            addCriterion("dsxm not between", value1, value2, "dsxm");
            return (Criteria) this;
        }

        public Criteria andLqlbmIsNull() {
            addCriterion("lqlbm is null");
            return (Criteria) this;
        }

        public Criteria andLqlbmIsNotNull() {
            addCriterion("lqlbm is not null");
            return (Criteria) this;
        }

        public Criteria andLqlbmEqualTo(String value) {
            addCriterion("lqlbm =", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmNotEqualTo(String value) {
            addCriterion("lqlbm <>", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmGreaterThan(String value) {
            addCriterion("lqlbm >", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmGreaterThanOrEqualTo(String value) {
            addCriterion("lqlbm >=", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmLessThan(String value) {
            addCriterion("lqlbm <", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmLessThanOrEqualTo(String value) {
            addCriterion("lqlbm <=", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmLike(String value) {
            addCriterion("lqlbm like", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmNotLike(String value) {
            addCriterion("lqlbm not like", value, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmIn(List<String> values) {
            addCriterion("lqlbm in", values, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmNotIn(List<String> values) {
            addCriterion("lqlbm not in", values, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmBetween(String value1, String value2) {
            addCriterion("lqlbm between", value1, value2, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andLqlbmNotBetween(String value1, String value2) {
            addCriterion("lqlbm not between", value1, value2, "lqlbm");
            return (Criteria) this;
        }

        public Criteria andDxwpdwIsNull() {
            addCriterion("dxwpdw is null");
            return (Criteria) this;
        }

        public Criteria andDxwpdwIsNotNull() {
            addCriterion("dxwpdw is not null");
            return (Criteria) this;
        }

        public Criteria andDxwpdwEqualTo(String value) {
            addCriterion("dxwpdw =", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwNotEqualTo(String value) {
            addCriterion("dxwpdw <>", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwGreaterThan(String value) {
            addCriterion("dxwpdw >", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwGreaterThanOrEqualTo(String value) {
            addCriterion("dxwpdw >=", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwLessThan(String value) {
            addCriterion("dxwpdw <", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwLessThanOrEqualTo(String value) {
            addCriterion("dxwpdw <=", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwLike(String value) {
            addCriterion("dxwpdw like", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwNotLike(String value) {
            addCriterion("dxwpdw not like", value, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwIn(List<String> values) {
            addCriterion("dxwpdw in", values, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwNotIn(List<String> values) {
            addCriterion("dxwpdw not in", values, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwBetween(String value1, String value2) {
            addCriterion("dxwpdw between", value1, value2, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDxwpdwNotBetween(String value1, String value2) {
            addCriterion("dxwpdw not between", value1, value2, "dxwpdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwIsNull() {
            addCriterion("daszdw is null");
            return (Criteria) this;
        }

        public Criteria andDaszdwIsNotNull() {
            addCriterion("daszdw is not null");
            return (Criteria) this;
        }

        public Criteria andDaszdwEqualTo(String value) {
            addCriterion("daszdw =", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwNotEqualTo(String value) {
            addCriterion("daszdw <>", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwGreaterThan(String value) {
            addCriterion("daszdw >", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwGreaterThanOrEqualTo(String value) {
            addCriterion("daszdw >=", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwLessThan(String value) {
            addCriterion("daszdw <", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwLessThanOrEqualTo(String value) {
            addCriterion("daszdw <=", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwLike(String value) {
            addCriterion("daszdw like", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwNotLike(String value) {
            addCriterion("daszdw not like", value, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwIn(List<String> values) {
            addCriterion("daszdw in", values, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwNotIn(List<String> values) {
            addCriterion("daszdw not in", values, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwBetween(String value1, String value2) {
            addCriterion("daszdw between", value1, value2, "daszdw");
            return (Criteria) this;
        }

        public Criteria andDaszdwNotBetween(String value1, String value2) {
            addCriterion("daszdw not between", value1, value2, "daszdw");
            return (Criteria) this;
        }

        public Criteria andZsndIsNull() {
            addCriterion("zsnd is null");
            return (Criteria) this;
        }

        public Criteria andZsndIsNotNull() {
            addCriterion("zsnd is not null");
            return (Criteria) this;
        }

        public Criteria andZsndEqualTo(Integer value) {
            addCriterion("zsnd =", value, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndNotEqualTo(Integer value) {
            addCriterion("zsnd <>", value, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndGreaterThan(Integer value) {
            addCriterion("zsnd >", value, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndGreaterThanOrEqualTo(Integer value) {
            addCriterion("zsnd >=", value, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndLessThan(Integer value) {
            addCriterion("zsnd <", value, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndLessThanOrEqualTo(Integer value) {
            addCriterion("zsnd <=", value, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndIn(List<Integer> values) {
            addCriterion("zsnd in", values, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndNotIn(List<Integer> values) {
            addCriterion("zsnd not in", values, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndBetween(Integer value1, Integer value2) {
            addCriterion("zsnd between", value1, value2, "zsnd");
            return (Criteria) this;
        }

        public Criteria andZsndNotBetween(Integer value1, Integer value2) {
            addCriterion("zsnd not between", value1, value2, "zsnd");
            return (Criteria) this;
        }

        public Criteria andBlzgnxIsNull() {
            addCriterion("blzgnx is null");
            return (Criteria) this;
        }

        public Criteria andBlzgnxIsNotNull() {
            addCriterion("blzgnx is not null");
            return (Criteria) this;
        }

        public Criteria andBlzgnxEqualTo(Integer value) {
            addCriterion("blzgnx =", value, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxNotEqualTo(Integer value) {
            addCriterion("blzgnx <>", value, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxGreaterThan(Integer value) {
            addCriterion("blzgnx >", value, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxGreaterThanOrEqualTo(Integer value) {
            addCriterion("blzgnx >=", value, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxLessThan(Integer value) {
            addCriterion("blzgnx <", value, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxLessThanOrEqualTo(Integer value) {
            addCriterion("blzgnx <=", value, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxIn(List<Integer> values) {
            addCriterion("blzgnx in", values, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxNotIn(List<Integer> values) {
            addCriterion("blzgnx not in", values, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxBetween(Integer value1, Integer value2) {
            addCriterion("blzgnx between", value1, value2, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgnxNotBetween(Integer value1, Integer value2) {
            addCriterion("blzgnx not between", value1, value2, "blzgnx");
            return (Criteria) this;
        }

        public Criteria andBlzgmIsNull() {
            addCriterion("blzgm is null");
            return (Criteria) this;
        }

        public Criteria andBlzgmIsNotNull() {
            addCriterion("blzgm is not null");
            return (Criteria) this;
        }

        public Criteria andBlzgmEqualTo(String value) {
            addCriterion("blzgm =", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmNotEqualTo(String value) {
            addCriterion("blzgm <>", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmGreaterThan(String value) {
            addCriterion("blzgm >", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmGreaterThanOrEqualTo(String value) {
            addCriterion("blzgm >=", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmLessThan(String value) {
            addCriterion("blzgm <", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmLessThanOrEqualTo(String value) {
            addCriterion("blzgm <=", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmLike(String value) {
            addCriterion("blzgm like", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmNotLike(String value) {
            addCriterion("blzgm not like", value, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmIn(List<String> values) {
            addCriterion("blzgm in", values, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmNotIn(List<String> values) {
            addCriterion("blzgm not in", values, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmBetween(String value1, String value2) {
            addCriterion("blzgm between", value1, value2, "blzgm");
            return (Criteria) this;
        }

        public Criteria andBlzgmNotBetween(String value1, String value2) {
            addCriterion("blzgm not between", value1, value2, "blzgm");
            return (Criteria) this;
        }

        public Criteria andNjIsNull() {
            addCriterion("nj is null");
            return (Criteria) this;
        }

        public Criteria andNjIsNotNull() {
            addCriterion("nj is not null");
            return (Criteria) this;
        }

        public Criteria andNjEqualTo(Integer value) {
            addCriterion("nj =", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotEqualTo(Integer value) {
            addCriterion("nj <>", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjGreaterThan(Integer value) {
            addCriterion("nj >", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjGreaterThanOrEqualTo(Integer value) {
            addCriterion("nj >=", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjLessThan(Integer value) {
            addCriterion("nj <", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjLessThanOrEqualTo(Integer value) {
            addCriterion("nj <=", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjIn(List<Integer> values) {
            addCriterion("nj in", values, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotIn(List<Integer> values) {
            addCriterion("nj not in", values, "nj");
            return (Criteria) this;
        }

        public Criteria andNjBetween(Integer value1, Integer value2) {
            addCriterion("nj between", value1, value2, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotBetween(Integer value1, Integer value2) {
            addCriterion("nj not between", value1, value2, "nj");
            return (Criteria) this;
        }

        public Criteria andXzIsNull() {
            addCriterion("xz is null");
            return (Criteria) this;
        }

        public Criteria andXzIsNotNull() {
            addCriterion("xz is not null");
            return (Criteria) this;
        }

        public Criteria andXzEqualTo(Integer value) {
            addCriterion("xz =", value, "xz");
            return (Criteria) this;
        }

        public Criteria andXzNotEqualTo(Integer value) {
            addCriterion("xz <>", value, "xz");
            return (Criteria) this;
        }

        public Criteria andXzGreaterThan(Integer value) {
            addCriterion("xz >", value, "xz");
            return (Criteria) this;
        }

        public Criteria andXzGreaterThanOrEqualTo(Integer value) {
            addCriterion("xz >=", value, "xz");
            return (Criteria) this;
        }

        public Criteria andXzLessThan(Integer value) {
            addCriterion("xz <", value, "xz");
            return (Criteria) this;
        }

        public Criteria andXzLessThanOrEqualTo(Integer value) {
            addCriterion("xz <=", value, "xz");
            return (Criteria) this;
        }

        public Criteria andXzIn(List<Integer> values) {
            addCriterion("xz in", values, "xz");
            return (Criteria) this;
        }

        public Criteria andXzNotIn(List<Integer> values) {
            addCriterion("xz not in", values, "xz");
            return (Criteria) this;
        }

        public Criteria andXzBetween(Integer value1, Integer value2) {
            addCriterion("xz between", value1, value2, "xz");
            return (Criteria) this;
        }

        public Criteria andXzNotBetween(Integer value1, Integer value2) {
            addCriterion("xz not between", value1, value2, "xz");
            return (Criteria) this;
        }

        public Criteria andYjrxnyIsNull() {
            addCriterion("yjrxny is null");
            return (Criteria) this;
        }

        public Criteria andYjrxnyIsNotNull() {
            addCriterion("yjrxny is not null");
            return (Criteria) this;
        }

        public Criteria andYjrxnyEqualTo(String value) {
            addCriterion("yjrxny =", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyNotEqualTo(String value) {
            addCriterion("yjrxny <>", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyGreaterThan(String value) {
            addCriterion("yjrxny >", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyGreaterThanOrEqualTo(String value) {
            addCriterion("yjrxny >=", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyLessThan(String value) {
            addCriterion("yjrxny <", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyLessThanOrEqualTo(String value) {
            addCriterion("yjrxny <=", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyLike(String value) {
            addCriterion("yjrxny like", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyNotLike(String value) {
            addCriterion("yjrxny not like", value, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyIn(List<String> values) {
            addCriterion("yjrxny in", values, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyNotIn(List<String> values) {
            addCriterion("yjrxny not in", values, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyBetween(String value1, String value2) {
            addCriterion("yjrxny between", value1, value2, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andYjrxnyNotBetween(String value1, String value2) {
            addCriterion("yjrxny not between", value1, value2, "yjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyIsNull() {
            addCriterion("sjrxny is null");
            return (Criteria) this;
        }

        public Criteria andSjrxnyIsNotNull() {
            addCriterion("sjrxny is not null");
            return (Criteria) this;
        }

        public Criteria andSjrxnyEqualTo(String value) {
            addCriterion("sjrxny =", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyNotEqualTo(String value) {
            addCriterion("sjrxny <>", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyGreaterThan(String value) {
            addCriterion("sjrxny >", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyGreaterThanOrEqualTo(String value) {
            addCriterion("sjrxny >=", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyLessThan(String value) {
            addCriterion("sjrxny <", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyLessThanOrEqualTo(String value) {
            addCriterion("sjrxny <=", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyLike(String value) {
            addCriterion("sjrxny like", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyNotLike(String value) {
            addCriterion("sjrxny not like", value, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyIn(List<String> values) {
            addCriterion("sjrxny in", values, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyNotIn(List<String> values) {
            addCriterion("sjrxny not in", values, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyBetween(String value1, String value2) {
            addCriterion("sjrxny between", value1, value2, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andSjrxnyNotBetween(String value1, String value2) {
            addCriterion("sjrxny not between", value1, value2, "sjrxny");
            return (Criteria) this;
        }

        public Criteria andYjbynyIsNull() {
            addCriterion("yjbyny is null");
            return (Criteria) this;
        }

        public Criteria andYjbynyIsNotNull() {
            addCriterion("yjbyny is not null");
            return (Criteria) this;
        }

        public Criteria andYjbynyEqualTo(String value) {
            addCriterion("yjbyny =", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyNotEqualTo(String value) {
            addCriterion("yjbyny <>", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyGreaterThan(String value) {
            addCriterion("yjbyny >", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyGreaterThanOrEqualTo(String value) {
            addCriterion("yjbyny >=", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyLessThan(String value) {
            addCriterion("yjbyny <", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyLessThanOrEqualTo(String value) {
            addCriterion("yjbyny <=", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyLike(String value) {
            addCriterion("yjbyny like", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyNotLike(String value) {
            addCriterion("yjbyny not like", value, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyIn(List<String> values) {
            addCriterion("yjbyny in", values, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyNotIn(List<String> values) {
            addCriterion("yjbyny not in", values, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyBetween(String value1, String value2) {
            addCriterion("yjbyny between", value1, value2, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYjbynyNotBetween(String value1, String value2) {
            addCriterion("yjbyny not between", value1, value2, "yjbyny");
            return (Criteria) this;
        }

        public Criteria andYqbynxIsNull() {
            addCriterion("yqbynx is null");
            return (Criteria) this;
        }

        public Criteria andYqbynxIsNotNull() {
            addCriterion("yqbynx is not null");
            return (Criteria) this;
        }

        public Criteria andYqbynxEqualTo(Float value) {
            addCriterion("yqbynx =", value, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxNotEqualTo(Float value) {
            addCriterion("yqbynx <>", value, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxGreaterThan(Float value) {
            addCriterion("yqbynx >", value, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxGreaterThanOrEqualTo(Float value) {
            addCriterion("yqbynx >=", value, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxLessThan(Float value) {
            addCriterion("yqbynx <", value, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxLessThanOrEqualTo(Float value) {
            addCriterion("yqbynx <=", value, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxIn(List<Float> values) {
            addCriterion("yqbynx in", values, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxNotIn(List<Float> values) {
            addCriterion("yqbynx not in", values, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxBetween(Float value1, Float value2) {
            addCriterion("yqbynx between", value1, value2, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andYqbynxNotBetween(Float value1, Float value2) {
            addCriterion("yqbynx not between", value1, value2, "yqbynx");
            return (Criteria) this;
        }

        public Criteria andSjbynyIsNull() {
            addCriterion("sjbyny is null");
            return (Criteria) this;
        }

        public Criteria andSjbynyIsNotNull() {
            addCriterion("sjbyny is not null");
            return (Criteria) this;
        }

        public Criteria andSjbynyEqualTo(String value) {
            addCriterion("sjbyny =", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyNotEqualTo(String value) {
            addCriterion("sjbyny <>", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyGreaterThan(String value) {
            addCriterion("sjbyny >", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyGreaterThanOrEqualTo(String value) {
            addCriterion("sjbyny >=", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyLessThan(String value) {
            addCriterion("sjbyny <", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyLessThanOrEqualTo(String value) {
            addCriterion("sjbyny <=", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyLike(String value) {
            addCriterion("sjbyny like", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyNotLike(String value) {
            addCriterion("sjbyny not like", value, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyIn(List<String> values) {
            addCriterion("sjbyny in", values, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyNotIn(List<String> values) {
            addCriterion("sjbyny not in", values, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyBetween(String value1, String value2) {
            addCriterion("sjbyny between", value1, value2, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjbynyNotBetween(String value1, String value2) {
            addCriterion("sjbyny not between", value1, value2, "sjbyny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyIsNull() {
            addCriterion("sjxwny is null");
            return (Criteria) this;
        }

        public Criteria andSjxwnyIsNotNull() {
            addCriterion("sjxwny is not null");
            return (Criteria) this;
        }

        public Criteria andSjxwnyEqualTo(String value) {
            addCriterion("sjxwny =", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyNotEqualTo(String value) {
            addCriterion("sjxwny <>", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyGreaterThan(String value) {
            addCriterion("sjxwny >", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyGreaterThanOrEqualTo(String value) {
            addCriterion("sjxwny >=", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyLessThan(String value) {
            addCriterion("sjxwny <", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyLessThanOrEqualTo(String value) {
            addCriterion("sjxwny <=", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyLike(String value) {
            addCriterion("sjxwny like", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyNotLike(String value) {
            addCriterion("sjxwny not like", value, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyIn(List<String> values) {
            addCriterion("sjxwny in", values, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyNotIn(List<String> values) {
            addCriterion("sjxwny not in", values, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyBetween(String value1, String value2) {
            addCriterion("sjxwny between", value1, value2, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andSjxwnyNotBetween(String value1, String value2) {
            addCriterion("sjxwny not between", value1, value2, "sjxwny");
            return (Criteria) this;
        }

        public Criteria andXjglmIsNull() {
            addCriterion("xjglm is null");
            return (Criteria) this;
        }

        public Criteria andXjglmIsNotNull() {
            addCriterion("xjglm is not null");
            return (Criteria) this;
        }

        public Criteria andXjglmEqualTo(String value) {
            addCriterion("xjglm =", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmNotEqualTo(String value) {
            addCriterion("xjglm <>", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmGreaterThan(String value) {
            addCriterion("xjglm >", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmGreaterThanOrEqualTo(String value) {
            addCriterion("xjglm >=", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmLessThan(String value) {
            addCriterion("xjglm <", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmLessThanOrEqualTo(String value) {
            addCriterion("xjglm <=", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmLike(String value) {
            addCriterion("xjglm like", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmNotLike(String value) {
            addCriterion("xjglm not like", value, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmIn(List<String> values) {
            addCriterion("xjglm in", values, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmNotIn(List<String> values) {
            addCriterion("xjglm not in", values, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmBetween(String value1, String value2) {
            addCriterion("xjglm between", value1, value2, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglmNotBetween(String value1, String value2) {
            addCriterion("xjglm not between", value1, value2, "xjglm");
            return (Criteria) this;
        }

        public Criteria andXjglnyIsNull() {
            addCriterion("xjglny is null");
            return (Criteria) this;
        }

        public Criteria andXjglnyIsNotNull() {
            addCriterion("xjglny is not null");
            return (Criteria) this;
        }

        public Criteria andXjglnyEqualTo(String value) {
            addCriterion("xjglny =", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyNotEqualTo(String value) {
            addCriterion("xjglny <>", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyGreaterThan(String value) {
            addCriterion("xjglny >", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyGreaterThanOrEqualTo(String value) {
            addCriterion("xjglny >=", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyLessThan(String value) {
            addCriterion("xjglny <", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyLessThanOrEqualTo(String value) {
            addCriterion("xjglny <=", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyLike(String value) {
            addCriterion("xjglny like", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyNotLike(String value) {
            addCriterion("xjglny not like", value, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyIn(List<String> values) {
            addCriterion("xjglny in", values, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyNotIn(List<String> values) {
            addCriterion("xjglny not in", values, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyBetween(String value1, String value2) {
            addCriterion("xjglny between", value1, value2, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjglnyNotBetween(String value1, String value2) {
            addCriterion("xjglny not between", value1, value2, "xjglny");
            return (Criteria) this;
        }

        public Criteria andXjbzIsNull() {
            addCriterion("xjbz is null");
            return (Criteria) this;
        }

        public Criteria andXjbzIsNotNull() {
            addCriterion("xjbz is not null");
            return (Criteria) this;
        }

        public Criteria andXjbzEqualTo(String value) {
            addCriterion("xjbz =", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzNotEqualTo(String value) {
            addCriterion("xjbz <>", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzGreaterThan(String value) {
            addCriterion("xjbz >", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzGreaterThanOrEqualTo(String value) {
            addCriterion("xjbz >=", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzLessThan(String value) {
            addCriterion("xjbz <", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzLessThanOrEqualTo(String value) {
            addCriterion("xjbz <=", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzLike(String value) {
            addCriterion("xjbz like", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzNotLike(String value) {
            addCriterion("xjbz not like", value, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzIn(List<String> values) {
            addCriterion("xjbz in", values, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzNotIn(List<String> values) {
            addCriterion("xjbz not in", values, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzBetween(String value1, String value2) {
            addCriterion("xjbz between", value1, value2, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjbzNotBetween(String value1, String value2) {
            addCriterion("xjbz not between", value1, value2, "xjbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzIsNull() {
            addCriterion("xjydbz is null");
            return (Criteria) this;
        }

        public Criteria andXjydbzIsNotNull() {
            addCriterion("xjydbz is not null");
            return (Criteria) this;
        }

        public Criteria andXjydbzEqualTo(String value) {
            addCriterion("xjydbz =", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzNotEqualTo(String value) {
            addCriterion("xjydbz <>", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzGreaterThan(String value) {
            addCriterion("xjydbz >", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzGreaterThanOrEqualTo(String value) {
            addCriterion("xjydbz >=", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzLessThan(String value) {
            addCriterion("xjydbz <", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzLessThanOrEqualTo(String value) {
            addCriterion("xjydbz <=", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzLike(String value) {
            addCriterion("xjydbz like", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzNotLike(String value) {
            addCriterion("xjydbz not like", value, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzIn(List<String> values) {
            addCriterion("xjydbz in", values, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzNotIn(List<String> values) {
            addCriterion("xjydbz not in", values, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzBetween(String value1, String value2) {
            addCriterion("xjydbz between", value1, value2, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andXjydbzNotBetween(String value1, String value2) {
            addCriterion("xjydbz not between", value1, value2, "xjydbz");
            return (Criteria) this;
        }

        public Criteria andQtbzIsNull() {
            addCriterion("qtbz is null");
            return (Criteria) this;
        }

        public Criteria andQtbzIsNotNull() {
            addCriterion("qtbz is not null");
            return (Criteria) this;
        }

        public Criteria andQtbzEqualTo(String value) {
            addCriterion("qtbz =", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzNotEqualTo(String value) {
            addCriterion("qtbz <>", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzGreaterThan(String value) {
            addCriterion("qtbz >", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzGreaterThanOrEqualTo(String value) {
            addCriterion("qtbz >=", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzLessThan(String value) {
            addCriterion("qtbz <", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzLessThanOrEqualTo(String value) {
            addCriterion("qtbz <=", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzLike(String value) {
            addCriterion("qtbz like", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzNotLike(String value) {
            addCriterion("qtbz not like", value, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzIn(List<String> values) {
            addCriterion("qtbz in", values, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzNotIn(List<String> values) {
            addCriterion("qtbz not in", values, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzBetween(String value1, String value2) {
            addCriterion("qtbz between", value1, value2, "qtbz");
            return (Criteria) this;
        }

        public Criteria andQtbzNotBetween(String value1, String value2) {
            addCriterion("qtbz not between", value1, value2, "qtbz");
            return (Criteria) this;
        }

        public Criteria andHkszdmIsNull() {
            addCriterion("hkszdm is null");
            return (Criteria) this;
        }

        public Criteria andHkszdmIsNotNull() {
            addCriterion("hkszdm is not null");
            return (Criteria) this;
        }

        public Criteria andHkszdmEqualTo(String value) {
            addCriterion("hkszdm =", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmNotEqualTo(String value) {
            addCriterion("hkszdm <>", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmGreaterThan(String value) {
            addCriterion("hkszdm >", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmGreaterThanOrEqualTo(String value) {
            addCriterion("hkszdm >=", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmLessThan(String value) {
            addCriterion("hkszdm <", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmLessThanOrEqualTo(String value) {
            addCriterion("hkszdm <=", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmLike(String value) {
            addCriterion("hkszdm like", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmNotLike(String value) {
            addCriterion("hkszdm not like", value, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmIn(List<String> values) {
            addCriterion("hkszdm in", values, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmNotIn(List<String> values) {
            addCriterion("hkszdm not in", values, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmBetween(String value1, String value2) {
            addCriterion("hkszdm between", value1, value2, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andHkszdmNotBetween(String value1, String value2) {
            addCriterion("hkszdm not between", value1, value2, "hkszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmIsNull() {
            addCriterion("syszdm is null");
            return (Criteria) this;
        }

        public Criteria andSyszdmIsNotNull() {
            addCriterion("syszdm is not null");
            return (Criteria) this;
        }

        public Criteria andSyszdmEqualTo(String value) {
            addCriterion("syszdm =", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmNotEqualTo(String value) {
            addCriterion("syszdm <>", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmGreaterThan(String value) {
            addCriterion("syszdm >", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmGreaterThanOrEqualTo(String value) {
            addCriterion("syszdm >=", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmLessThan(String value) {
            addCriterion("syszdm <", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmLessThanOrEqualTo(String value) {
            addCriterion("syszdm <=", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmLike(String value) {
            addCriterion("syszdm like", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmNotLike(String value) {
            addCriterion("syszdm not like", value, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmIn(List<String> values) {
            addCriterion("syszdm in", values, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmNotIn(List<String> values) {
            addCriterion("syszdm not in", values, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmBetween(String value1, String value2) {
            addCriterion("syszdm between", value1, value2, "syszdm");
            return (Criteria) this;
        }

        public Criteria andSyszdmNotBetween(String value1, String value2) {
            addCriterion("syszdm not between", value1, value2, "syszdm");
            return (Criteria) this;
        }

        public Criteria andXxgzdwIsNull() {
            addCriterion("xxgzdw is null");
            return (Criteria) this;
        }

        public Criteria andXxgzdwIsNotNull() {
            addCriterion("xxgzdw is not null");
            return (Criteria) this;
        }

        public Criteria andXxgzdwEqualTo(String value) {
            addCriterion("xxgzdw =", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwNotEqualTo(String value) {
            addCriterion("xxgzdw <>", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwGreaterThan(String value) {
            addCriterion("xxgzdw >", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwGreaterThanOrEqualTo(String value) {
            addCriterion("xxgzdw >=", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwLessThan(String value) {
            addCriterion("xxgzdw <", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwLessThanOrEqualTo(String value) {
            addCriterion("xxgzdw <=", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwLike(String value) {
            addCriterion("xxgzdw like", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwNotLike(String value) {
            addCriterion("xxgzdw not like", value, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwIn(List<String> values) {
            addCriterion("xxgzdw in", values, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwNotIn(List<String> values) {
            addCriterion("xxgzdw not in", values, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwBetween(String value1, String value2) {
            addCriterion("xxgzdw between", value1, value2, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andXxgzdwNotBetween(String value1, String value2) {
            addCriterion("xxgzdw not between", value1, value2, "xxgzdw");
            return (Criteria) this;
        }

        public Criteria andZslqlbmIsNull() {
            addCriterion("zslqlbm is null");
            return (Criteria) this;
        }

        public Criteria andZslqlbmIsNotNull() {
            addCriterion("zslqlbm is not null");
            return (Criteria) this;
        }

        public Criteria andZslqlbmEqualTo(String value) {
            addCriterion("zslqlbm =", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmNotEqualTo(String value) {
            addCriterion("zslqlbm <>", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmGreaterThan(String value) {
            addCriterion("zslqlbm >", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmGreaterThanOrEqualTo(String value) {
            addCriterion("zslqlbm >=", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmLessThan(String value) {
            addCriterion("zslqlbm <", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmLessThanOrEqualTo(String value) {
            addCriterion("zslqlbm <=", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmLike(String value) {
            addCriterion("zslqlbm like", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmNotLike(String value) {
            addCriterion("zslqlbm not like", value, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmIn(List<String> values) {
            addCriterion("zslqlbm in", values, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmNotIn(List<String> values) {
            addCriterion("zslqlbm not in", values, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmBetween(String value1, String value2) {
            addCriterion("zslqlbm between", value1, value2, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZslqlbmNotBetween(String value1, String value2) {
            addCriterion("zslqlbm not between", value1, value2, "zslqlbm");
            return (Criteria) this;
        }

        public Criteria andZxjhmIsNull() {
            addCriterion("zxjhm is null");
            return (Criteria) this;
        }

        public Criteria andZxjhmIsNotNull() {
            addCriterion("zxjhm is not null");
            return (Criteria) this;
        }

        public Criteria andZxjhmEqualTo(String value) {
            addCriterion("zxjhm =", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmNotEqualTo(String value) {
            addCriterion("zxjhm <>", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmGreaterThan(String value) {
            addCriterion("zxjhm >", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmGreaterThanOrEqualTo(String value) {
            addCriterion("zxjhm >=", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmLessThan(String value) {
            addCriterion("zxjhm <", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmLessThanOrEqualTo(String value) {
            addCriterion("zxjhm <=", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmLike(String value) {
            addCriterion("zxjhm like", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmNotLike(String value) {
            addCriterion("zxjhm not like", value, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmIn(List<String> values) {
            addCriterion("zxjhm in", values, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmNotIn(List<String> values) {
            addCriterion("zxjhm not in", values, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmBetween(String value1, String value2) {
            addCriterion("zxjhm between", value1, value2, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andZxjhmNotBetween(String value1, String value2) {
            addCriterion("zxjhm not between", value1, value2, "zxjhm");
            return (Criteria) this;
        }

        public Criteria andKsfsmIsNull() {
            addCriterion("ksfsm is null");
            return (Criteria) this;
        }

        public Criteria andKsfsmIsNotNull() {
            addCriterion("ksfsm is not null");
            return (Criteria) this;
        }

        public Criteria andKsfsmEqualTo(String value) {
            addCriterion("ksfsm =", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmNotEqualTo(String value) {
            addCriterion("ksfsm <>", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmGreaterThan(String value) {
            addCriterion("ksfsm >", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmGreaterThanOrEqualTo(String value) {
            addCriterion("ksfsm >=", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmLessThan(String value) {
            addCriterion("ksfsm <", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmLessThanOrEqualTo(String value) {
            addCriterion("ksfsm <=", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmLike(String value) {
            addCriterion("ksfsm like", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmNotLike(String value) {
            addCriterion("ksfsm not like", value, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmIn(List<String> values) {
            addCriterion("ksfsm in", values, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmNotIn(List<String> values) {
            addCriterion("ksfsm not in", values, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmBetween(String value1, String value2) {
            addCriterion("ksfsm between", value1, value2, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKsfsmNotBetween(String value1, String value2) {
            addCriterion("ksfsm not between", value1, value2, "ksfsm");
            return (Criteria) this;
        }

        public Criteria andKslymIsNull() {
            addCriterion("kslym is null");
            return (Criteria) this;
        }

        public Criteria andKslymIsNotNull() {
            addCriterion("kslym is not null");
            return (Criteria) this;
        }

        public Criteria andKslymEqualTo(String value) {
            addCriterion("kslym =", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymNotEqualTo(String value) {
            addCriterion("kslym <>", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymGreaterThan(String value) {
            addCriterion("kslym >", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymGreaterThanOrEqualTo(String value) {
            addCriterion("kslym >=", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymLessThan(String value) {
            addCriterion("kslym <", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymLessThanOrEqualTo(String value) {
            addCriterion("kslym <=", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymLike(String value) {
            addCriterion("kslym like", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymNotLike(String value) {
            addCriterion("kslym not like", value, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymIn(List<String> values) {
            addCriterion("kslym in", values, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymNotIn(List<String> values) {
            addCriterion("kslym not in", values, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymBetween(String value1, String value2) {
            addCriterion("kslym between", value1, value2, "kslym");
            return (Criteria) this;
        }

        public Criteria andKslymNotBetween(String value1, String value2) {
            addCriterion("kslym not between", value1, value2, "kslym");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmIsNull() {
            addCriterion("pyzxjhm is null");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmIsNotNull() {
            addCriterion("pyzxjhm is not null");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmEqualTo(String value) {
            addCriterion("pyzxjhm =", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmNotEqualTo(String value) {
            addCriterion("pyzxjhm <>", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmGreaterThan(String value) {
            addCriterion("pyzxjhm >", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmGreaterThanOrEqualTo(String value) {
            addCriterion("pyzxjhm >=", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmLessThan(String value) {
            addCriterion("pyzxjhm <", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmLessThanOrEqualTo(String value) {
            addCriterion("pyzxjhm <=", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmLike(String value) {
            addCriterion("pyzxjhm like", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmNotLike(String value) {
            addCriterion("pyzxjhm not like", value, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmIn(List<String> values) {
            addCriterion("pyzxjhm in", values, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmNotIn(List<String> values) {
            addCriterion("pyzxjhm not in", values, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmBetween(String value1, String value2) {
            addCriterion("pyzxjhm between", value1, value2, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andPyzxjhmNotBetween(String value1, String value2) {
            addCriterion("pyzxjhm not between", value1, value2, "pyzxjhm");
            return (Criteria) this;
        }

        public Criteria andZzmmmIsNull() {
            addCriterion("zzmmm is null");
            return (Criteria) this;
        }

        public Criteria andZzmmmIsNotNull() {
            addCriterion("zzmmm is not null");
            return (Criteria) this;
        }

        public Criteria andZzmmmEqualTo(String value) {
            addCriterion("zzmmm =", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmNotEqualTo(String value) {
            addCriterion("zzmmm <>", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmGreaterThan(String value) {
            addCriterion("zzmmm >", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmGreaterThanOrEqualTo(String value) {
            addCriterion("zzmmm >=", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmLessThan(String value) {
            addCriterion("zzmmm <", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmLessThanOrEqualTo(String value) {
            addCriterion("zzmmm <=", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmLike(String value) {
            addCriterion("zzmmm like", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmNotLike(String value) {
            addCriterion("zzmmm not like", value, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmIn(List<String> values) {
            addCriterion("zzmmm in", values, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmNotIn(List<String> values) {
            addCriterion("zzmmm not in", values, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmBetween(String value1, String value2) {
            addCriterion("zzmmm between", value1, value2, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andZzmmmNotBetween(String value1, String value2) {
            addCriterion("zzmmm not between", value1, value2, "zzmmm");
            return (Criteria) this;
        }

        public Criteria andHfmIsNull() {
            addCriterion("hfm is null");
            return (Criteria) this;
        }

        public Criteria andHfmIsNotNull() {
            addCriterion("hfm is not null");
            return (Criteria) this;
        }

        public Criteria andHfmEqualTo(String value) {
            addCriterion("hfm =", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmNotEqualTo(String value) {
            addCriterion("hfm <>", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmGreaterThan(String value) {
            addCriterion("hfm >", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmGreaterThanOrEqualTo(String value) {
            addCriterion("hfm >=", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmLessThan(String value) {
            addCriterion("hfm <", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmLessThanOrEqualTo(String value) {
            addCriterion("hfm <=", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmLike(String value) {
            addCriterion("hfm like", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmNotLike(String value) {
            addCriterion("hfm not like", value, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmIn(List<String> values) {
            addCriterion("hfm in", values, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmNotIn(List<String> values) {
            addCriterion("hfm not in", values, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmBetween(String value1, String value2) {
            addCriterion("hfm between", value1, value2, "hfm");
            return (Criteria) this;
        }

        public Criteria andHfmNotBetween(String value1, String value2) {
            addCriterion("hfm not between", value1, value2, "hfm");
            return (Criteria) this;
        }

        public Criteria andXyjrmIsNull() {
            addCriterion("xyjrm is null");
            return (Criteria) this;
        }

        public Criteria andXyjrmIsNotNull() {
            addCriterion("xyjrm is not null");
            return (Criteria) this;
        }

        public Criteria andXyjrmEqualTo(String value) {
            addCriterion("xyjrm =", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmNotEqualTo(String value) {
            addCriterion("xyjrm <>", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmGreaterThan(String value) {
            addCriterion("xyjrm >", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmGreaterThanOrEqualTo(String value) {
            addCriterion("xyjrm >=", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmLessThan(String value) {
            addCriterion("xyjrm <", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmLessThanOrEqualTo(String value) {
            addCriterion("xyjrm <=", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmLike(String value) {
            addCriterion("xyjrm like", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmNotLike(String value) {
            addCriterion("xyjrm not like", value, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmIn(List<String> values) {
            addCriterion("xyjrm in", values, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmNotIn(List<String> values) {
            addCriterion("xyjrm not in", values, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmBetween(String value1, String value2) {
            addCriterion("xyjrm between", value1, value2, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXyjrmNotBetween(String value1, String value2) {
            addCriterion("xyjrm not between", value1, value2, "xyjrm");
            return (Criteria) this;
        }

        public Criteria andXslbm5IsNull() {
            addCriterion("xslbm5 is null");
            return (Criteria) this;
        }

        public Criteria andXslbm5IsNotNull() {
            addCriterion("xslbm5 is not null");
            return (Criteria) this;
        }

        public Criteria andXslbm5EqualTo(String value) {
            addCriterion("xslbm5 =", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5NotEqualTo(String value) {
            addCriterion("xslbm5 <>", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5GreaterThan(String value) {
            addCriterion("xslbm5 >", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5GreaterThanOrEqualTo(String value) {
            addCriterion("xslbm5 >=", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5LessThan(String value) {
            addCriterion("xslbm5 <", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5LessThanOrEqualTo(String value) {
            addCriterion("xslbm5 <=", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5Like(String value) {
            addCriterion("xslbm5 like", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5NotLike(String value) {
            addCriterion("xslbm5 not like", value, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5In(List<String> values) {
            addCriterion("xslbm5 in", values, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5NotIn(List<String> values) {
            addCriterion("xslbm5 not in", values, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5Between(String value1, String value2) {
            addCriterion("xslbm5 between", value1, value2, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andXslbm5NotBetween(String value1, String value2) {
            addCriterion("xslbm5 not between", value1, value2, "xslbm5");
            return (Criteria) this;
        }

        public Criteria andTjzydmIsNull() {
            addCriterion("tjzydm is null");
            return (Criteria) this;
        }

        public Criteria andTjzydmIsNotNull() {
            addCriterion("tjzydm is not null");
            return (Criteria) this;
        }

        public Criteria andTjzydmEqualTo(String value) {
            addCriterion("tjzydm =", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmNotEqualTo(String value) {
            addCriterion("tjzydm <>", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmGreaterThan(String value) {
            addCriterion("tjzydm >", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmGreaterThanOrEqualTo(String value) {
            addCriterion("tjzydm >=", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmLessThan(String value) {
            addCriterion("tjzydm <", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmLessThanOrEqualTo(String value) {
            addCriterion("tjzydm <=", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmLike(String value) {
            addCriterion("tjzydm like", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmNotLike(String value) {
            addCriterion("tjzydm not like", value, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmIn(List<String> values) {
            addCriterion("tjzydm in", values, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmNotIn(List<String> values) {
            addCriterion("tjzydm not in", values, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmBetween(String value1, String value2) {
            addCriterion("tjzydm between", value1, value2, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzydmNotBetween(String value1, String value2) {
            addCriterion("tjzydm not between", value1, value2, "tjzydm");
            return (Criteria) this;
        }

        public Criteria andTjzymcIsNull() {
            addCriterion("tjzymc is null");
            return (Criteria) this;
        }

        public Criteria andTjzymcIsNotNull() {
            addCriterion("tjzymc is not null");
            return (Criteria) this;
        }

        public Criteria andTjzymcEqualTo(String value) {
            addCriterion("tjzymc =", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcNotEqualTo(String value) {
            addCriterion("tjzymc <>", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcGreaterThan(String value) {
            addCriterion("tjzymc >", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcGreaterThanOrEqualTo(String value) {
            addCriterion("tjzymc >=", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcLessThan(String value) {
            addCriterion("tjzymc <", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcLessThanOrEqualTo(String value) {
            addCriterion("tjzymc <=", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcLike(String value) {
            addCriterion("tjzymc like", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcNotLike(String value) {
            addCriterion("tjzymc not like", value, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcIn(List<String> values) {
            addCriterion("tjzymc in", values, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcNotIn(List<String> values) {
            addCriterion("tjzymc not in", values, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcBetween(String value1, String value2) {
            addCriterion("tjzymc between", value1, value2, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andTjzymcNotBetween(String value1, String value2) {
            addCriterion("tjzymc not between", value1, value2, "tjzymc");
            return (Criteria) this;
        }

        public Criteria andKsh16IsNull() {
            addCriterion("ksh16 is null");
            return (Criteria) this;
        }

        public Criteria andKsh16IsNotNull() {
            addCriterion("ksh16 is not null");
            return (Criteria) this;
        }

        public Criteria andKsh16EqualTo(String value) {
            addCriterion("ksh16 =", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16NotEqualTo(String value) {
            addCriterion("ksh16 <>", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16GreaterThan(String value) {
            addCriterion("ksh16 >", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16GreaterThanOrEqualTo(String value) {
            addCriterion("ksh16 >=", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16LessThan(String value) {
            addCriterion("ksh16 <", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16LessThanOrEqualTo(String value) {
            addCriterion("ksh16 <=", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16Like(String value) {
            addCriterion("ksh16 like", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16NotLike(String value) {
            addCriterion("ksh16 not like", value, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16In(List<String> values) {
            addCriterion("ksh16 in", values, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16NotIn(List<String> values) {
            addCriterion("ksh16 not in", values, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16Between(String value1, String value2) {
            addCriterion("ksh16 between", value1, value2, "ksh16");
            return (Criteria) this;
        }

        public Criteria andKsh16NotBetween(String value1, String value2) {
            addCriterion("ksh16 not between", value1, value2, "ksh16");
            return (Criteria) this;
        }

        public Criteria andSjcqsjIsNull() {
            addCriterion("sjcqsj is null");
            return (Criteria) this;
        }

        public Criteria andSjcqsjIsNotNull() {
            addCriterion("sjcqsj is not null");
            return (Criteria) this;
        }

        public Criteria andSjcqsjEqualTo(String value) {
            addCriterion("sjcqsj =", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjNotEqualTo(String value) {
            addCriterion("sjcqsj <>", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjGreaterThan(String value) {
            addCriterion("sjcqsj >", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjGreaterThanOrEqualTo(String value) {
            addCriterion("sjcqsj >=", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjLessThan(String value) {
            addCriterion("sjcqsj <", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjLessThanOrEqualTo(String value) {
            addCriterion("sjcqsj <=", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjLike(String value) {
            addCriterion("sjcqsj like", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjNotLike(String value) {
            addCriterion("sjcqsj not like", value, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjIn(List<String> values) {
            addCriterion("sjcqsj in", values, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjNotIn(List<String> values) {
            addCriterion("sjcqsj not in", values, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjBetween(String value1, String value2) {
            addCriterion("sjcqsj between", value1, value2, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andSjcqsjNotBetween(String value1, String value2) {
            addCriterion("sjcqsj not between", value1, value2, "sjcqsj");
            return (Criteria) this;
        }

        public Criteria andQzdwmIsNull() {
            addCriterion("qzdwm is null");
            return (Criteria) this;
        }

        public Criteria andQzdwmIsNotNull() {
            addCriterion("qzdwm is not null");
            return (Criteria) this;
        }

        public Criteria andQzdwmEqualTo(String value) {
            addCriterion("qzdwm =", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmNotEqualTo(String value) {
            addCriterion("qzdwm <>", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmGreaterThan(String value) {
            addCriterion("qzdwm >", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmGreaterThanOrEqualTo(String value) {
            addCriterion("qzdwm >=", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmLessThan(String value) {
            addCriterion("qzdwm <", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmLessThanOrEqualTo(String value) {
            addCriterion("qzdwm <=", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmLike(String value) {
            addCriterion("qzdwm like", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmNotLike(String value) {
            addCriterion("qzdwm not like", value, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmIn(List<String> values) {
            addCriterion("qzdwm in", values, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmNotIn(List<String> values) {
            addCriterion("qzdwm not in", values, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmBetween(String value1, String value2) {
            addCriterion("qzdwm between", value1, value2, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwmNotBetween(String value1, String value2) {
            addCriterion("qzdwm not between", value1, value2, "qzdwm");
            return (Criteria) this;
        }

        public Criteria andQzdwIsNull() {
            addCriterion("qzdw is null");
            return (Criteria) this;
        }

        public Criteria andQzdwIsNotNull() {
            addCriterion("qzdw is not null");
            return (Criteria) this;
        }

        public Criteria andQzdwEqualTo(String value) {
            addCriterion("qzdw =", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwNotEqualTo(String value) {
            addCriterion("qzdw <>", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwGreaterThan(String value) {
            addCriterion("qzdw >", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwGreaterThanOrEqualTo(String value) {
            addCriterion("qzdw >=", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwLessThan(String value) {
            addCriterion("qzdw <", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwLessThanOrEqualTo(String value) {
            addCriterion("qzdw <=", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwLike(String value) {
            addCriterion("qzdw like", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwNotLike(String value) {
            addCriterion("qzdw not like", value, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwIn(List<String> values) {
            addCriterion("qzdw in", values, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwNotIn(List<String> values) {
            addCriterion("qzdw not in", values, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwBetween(String value1, String value2) {
            addCriterion("qzdw between", value1, value2, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQzdwNotBetween(String value1, String value2) {
            addCriterion("qzdw not between", value1, value2, "qzdw");
            return (Criteria) this;
        }

        public Criteria andQznyIsNull() {
            addCriterion("qzny is null");
            return (Criteria) this;
        }

        public Criteria andQznyIsNotNull() {
            addCriterion("qzny is not null");
            return (Criteria) this;
        }

        public Criteria andQznyEqualTo(String value) {
            addCriterion("qzny =", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyNotEqualTo(String value) {
            addCriterion("qzny <>", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyGreaterThan(String value) {
            addCriterion("qzny >", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyGreaterThanOrEqualTo(String value) {
            addCriterion("qzny >=", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyLessThan(String value) {
            addCriterion("qzny <", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyLessThanOrEqualTo(String value) {
            addCriterion("qzny <=", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyLike(String value) {
            addCriterion("qzny like", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyNotLike(String value) {
            addCriterion("qzny not like", value, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyIn(List<String> values) {
            addCriterion("qzny in", values, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyNotIn(List<String> values) {
            addCriterion("qzny not in", values, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyBetween(String value1, String value2) {
            addCriterion("qzny between", value1, value2, "qzny");
            return (Criteria) this;
        }

        public Criteria andQznyNotBetween(String value1, String value2) {
            addCriterion("qzny not between", value1, value2, "qzny");
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