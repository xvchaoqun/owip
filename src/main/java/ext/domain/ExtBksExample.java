package ext.domain;

import java.util.ArrayList;
import java.util.List;

public class ExtBksExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExtBksExample() {
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

        public Criteria andXbIsNull() {
            addCriterion("xb is null");
            return (Criteria) this;
        }

        public Criteria andXbIsNotNull() {
            addCriterion("xb is not null");
            return (Criteria) this;
        }

        public Criteria andXbEqualTo(String value) {
            addCriterion("xb =", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbNotEqualTo(String value) {
            addCriterion("xb <>", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbGreaterThan(String value) {
            addCriterion("xb >", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbGreaterThanOrEqualTo(String value) {
            addCriterion("xb >=", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbLessThan(String value) {
            addCriterion("xb <", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbLessThanOrEqualTo(String value) {
            addCriterion("xb <=", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbLike(String value) {
            addCriterion("xb like", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbNotLike(String value) {
            addCriterion("xb not like", value, "xb");
            return (Criteria) this;
        }

        public Criteria andXbIn(List<String> values) {
            addCriterion("xb in", values, "xb");
            return (Criteria) this;
        }

        public Criteria andXbNotIn(List<String> values) {
            addCriterion("xb not in", values, "xb");
            return (Criteria) this;
        }

        public Criteria andXbBetween(String value1, String value2) {
            addCriterion("xb between", value1, value2, "xb");
            return (Criteria) this;
        }

        public Criteria andXbNotBetween(String value1, String value2) {
            addCriterion("xb not between", value1, value2, "xb");
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

        public Criteria andNjIsNull() {
            addCriterion("nj is null");
            return (Criteria) this;
        }

        public Criteria andNjIsNotNull() {
            addCriterion("nj is not null");
            return (Criteria) this;
        }

        public Criteria andNjEqualTo(String value) {
            addCriterion("nj =", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotEqualTo(String value) {
            addCriterion("nj <>", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjGreaterThan(String value) {
            addCriterion("nj >", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjGreaterThanOrEqualTo(String value) {
            addCriterion("nj >=", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjLessThan(String value) {
            addCriterion("nj <", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjLessThanOrEqualTo(String value) {
            addCriterion("nj <=", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjLike(String value) {
            addCriterion("nj like", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotLike(String value) {
            addCriterion("nj not like", value, "nj");
            return (Criteria) this;
        }

        public Criteria andNjIn(List<String> values) {
            addCriterion("nj in", values, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotIn(List<String> values) {
            addCriterion("nj not in", values, "nj");
            return (Criteria) this;
        }

        public Criteria andNjBetween(String value1, String value2) {
            addCriterion("nj between", value1, value2, "nj");
            return (Criteria) this;
        }

        public Criteria andNjNotBetween(String value1, String value2) {
            addCriterion("nj not between", value1, value2, "nj");
            return (Criteria) this;
        }

        public Criteria andYxmcIsNull() {
            addCriterion("yxmc is null");
            return (Criteria) this;
        }

        public Criteria andYxmcIsNotNull() {
            addCriterion("yxmc is not null");
            return (Criteria) this;
        }

        public Criteria andYxmcEqualTo(String value) {
            addCriterion("yxmc =", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcNotEqualTo(String value) {
            addCriterion("yxmc <>", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcGreaterThan(String value) {
            addCriterion("yxmc >", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcGreaterThanOrEqualTo(String value) {
            addCriterion("yxmc >=", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcLessThan(String value) {
            addCriterion("yxmc <", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcLessThanOrEqualTo(String value) {
            addCriterion("yxmc <=", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcLike(String value) {
            addCriterion("yxmc like", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcNotLike(String value) {
            addCriterion("yxmc not like", value, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcIn(List<String> values) {
            addCriterion("yxmc in", values, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcNotIn(List<String> values) {
            addCriterion("yxmc not in", values, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcBetween(String value1, String value2) {
            addCriterion("yxmc between", value1, value2, "yxmc");
            return (Criteria) this;
        }

        public Criteria andYxmcNotBetween(String value1, String value2) {
            addCriterion("yxmc not between", value1, value2, "yxmc");
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

        public Criteria andFgyxIsNull() {
            addCriterion("fgyx is null");
            return (Criteria) this;
        }

        public Criteria andFgyxIsNotNull() {
            addCriterion("fgyx is not null");
            return (Criteria) this;
        }

        public Criteria andFgyxEqualTo(String value) {
            addCriterion("fgyx =", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxNotEqualTo(String value) {
            addCriterion("fgyx <>", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxGreaterThan(String value) {
            addCriterion("fgyx >", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxGreaterThanOrEqualTo(String value) {
            addCriterion("fgyx >=", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxLessThan(String value) {
            addCriterion("fgyx <", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxLessThanOrEqualTo(String value) {
            addCriterion("fgyx <=", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxLike(String value) {
            addCriterion("fgyx like", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxNotLike(String value) {
            addCriterion("fgyx not like", value, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxIn(List<String> values) {
            addCriterion("fgyx in", values, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxNotIn(List<String> values) {
            addCriterion("fgyx not in", values, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxBetween(String value1, String value2) {
            addCriterion("fgyx between", value1, value2, "fgyx");
            return (Criteria) this;
        }

        public Criteria andFgyxNotBetween(String value1, String value2) {
            addCriterion("fgyx not between", value1, value2, "fgyx");
            return (Criteria) this;
        }

        public Criteria andPyfsIsNull() {
            addCriterion("pyfs is null");
            return (Criteria) this;
        }

        public Criteria andPyfsIsNotNull() {
            addCriterion("pyfs is not null");
            return (Criteria) this;
        }

        public Criteria andPyfsEqualTo(String value) {
            addCriterion("pyfs =", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsNotEqualTo(String value) {
            addCriterion("pyfs <>", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsGreaterThan(String value) {
            addCriterion("pyfs >", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsGreaterThanOrEqualTo(String value) {
            addCriterion("pyfs >=", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsLessThan(String value) {
            addCriterion("pyfs <", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsLessThanOrEqualTo(String value) {
            addCriterion("pyfs <=", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsLike(String value) {
            addCriterion("pyfs like", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsNotLike(String value) {
            addCriterion("pyfs not like", value, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsIn(List<String> values) {
            addCriterion("pyfs in", values, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsNotIn(List<String> values) {
            addCriterion("pyfs not in", values, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsBetween(String value1, String value2) {
            addCriterion("pyfs between", value1, value2, "pyfs");
            return (Criteria) this;
        }

        public Criteria andPyfsNotBetween(String value1, String value2) {
            addCriterion("pyfs not between", value1, value2, "pyfs");
            return (Criteria) this;
        }

        public Criteria andMzIsNull() {
            addCriterion("mz is null");
            return (Criteria) this;
        }

        public Criteria andMzIsNotNull() {
            addCriterion("mz is not null");
            return (Criteria) this;
        }

        public Criteria andMzEqualTo(String value) {
            addCriterion("mz =", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzNotEqualTo(String value) {
            addCriterion("mz <>", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzGreaterThan(String value) {
            addCriterion("mz >", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzGreaterThanOrEqualTo(String value) {
            addCriterion("mz >=", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzLessThan(String value) {
            addCriterion("mz <", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzLessThanOrEqualTo(String value) {
            addCriterion("mz <=", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzLike(String value) {
            addCriterion("mz like", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzNotLike(String value) {
            addCriterion("mz not like", value, "mz");
            return (Criteria) this;
        }

        public Criteria andMzIn(List<String> values) {
            addCriterion("mz in", values, "mz");
            return (Criteria) this;
        }

        public Criteria andMzNotIn(List<String> values) {
            addCriterion("mz not in", values, "mz");
            return (Criteria) this;
        }

        public Criteria andMzBetween(String value1, String value2) {
            addCriterion("mz between", value1, value2, "mz");
            return (Criteria) this;
        }

        public Criteria andMzNotBetween(String value1, String value2) {
            addCriterion("mz not between", value1, value2, "mz");
            return (Criteria) this;
        }

        public Criteria andZzmmIsNull() {
            addCriterion("zzmm is null");
            return (Criteria) this;
        }

        public Criteria andZzmmIsNotNull() {
            addCriterion("zzmm is not null");
            return (Criteria) this;
        }

        public Criteria andZzmmEqualTo(String value) {
            addCriterion("zzmm =", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmNotEqualTo(String value) {
            addCriterion("zzmm <>", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmGreaterThan(String value) {
            addCriterion("zzmm >", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmGreaterThanOrEqualTo(String value) {
            addCriterion("zzmm >=", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmLessThan(String value) {
            addCriterion("zzmm <", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmLessThanOrEqualTo(String value) {
            addCriterion("zzmm <=", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmLike(String value) {
            addCriterion("zzmm like", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmNotLike(String value) {
            addCriterion("zzmm not like", value, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmIn(List<String> values) {
            addCriterion("zzmm in", values, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmNotIn(List<String> values) {
            addCriterion("zzmm not in", values, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmBetween(String value1, String value2) {
            addCriterion("zzmm between", value1, value2, "zzmm");
            return (Criteria) this;
        }

        public Criteria andZzmmNotBetween(String value1, String value2) {
            addCriterion("zzmm not between", value1, value2, "zzmm");
            return (Criteria) this;
        }

        public Criteria andXjbdIsNull() {
            addCriterion("xjbd is null");
            return (Criteria) this;
        }

        public Criteria andXjbdIsNotNull() {
            addCriterion("xjbd is not null");
            return (Criteria) this;
        }

        public Criteria andXjbdEqualTo(String value) {
            addCriterion("xjbd =", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdNotEqualTo(String value) {
            addCriterion("xjbd <>", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdGreaterThan(String value) {
            addCriterion("xjbd >", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdGreaterThanOrEqualTo(String value) {
            addCriterion("xjbd >=", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdLessThan(String value) {
            addCriterion("xjbd <", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdLessThanOrEqualTo(String value) {
            addCriterion("xjbd <=", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdLike(String value) {
            addCriterion("xjbd like", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdNotLike(String value) {
            addCriterion("xjbd not like", value, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdIn(List<String> values) {
            addCriterion("xjbd in", values, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdNotIn(List<String> values) {
            addCriterion("xjbd not in", values, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdBetween(String value1, String value2) {
            addCriterion("xjbd between", value1, value2, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjbdNotBetween(String value1, String value2) {
            addCriterion("xjbd not between", value1, value2, "xjbd");
            return (Criteria) this;
        }

        public Criteria andXjrqIsNull() {
            addCriterion("xjrq is null");
            return (Criteria) this;
        }

        public Criteria andXjrqIsNotNull() {
            addCriterion("xjrq is not null");
            return (Criteria) this;
        }

        public Criteria andXjrqEqualTo(String value) {
            addCriterion("xjrq =", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqNotEqualTo(String value) {
            addCriterion("xjrq <>", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqGreaterThan(String value) {
            addCriterion("xjrq >", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqGreaterThanOrEqualTo(String value) {
            addCriterion("xjrq >=", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqLessThan(String value) {
            addCriterion("xjrq <", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqLessThanOrEqualTo(String value) {
            addCriterion("xjrq <=", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqLike(String value) {
            addCriterion("xjrq like", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqNotLike(String value) {
            addCriterion("xjrq not like", value, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqIn(List<String> values) {
            addCriterion("xjrq in", values, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqNotIn(List<String> values) {
            addCriterion("xjrq not in", values, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqBetween(String value1, String value2) {
            addCriterion("xjrq between", value1, value2, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjrqNotBetween(String value1, String value2) {
            addCriterion("xjrq not between", value1, value2, "xjrq");
            return (Criteria) this;
        }

        public Criteria andXjyyIsNull() {
            addCriterion("xjyy is null");
            return (Criteria) this;
        }

        public Criteria andXjyyIsNotNull() {
            addCriterion("xjyy is not null");
            return (Criteria) this;
        }

        public Criteria andXjyyEqualTo(String value) {
            addCriterion("xjyy =", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyNotEqualTo(String value) {
            addCriterion("xjyy <>", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyGreaterThan(String value) {
            addCriterion("xjyy >", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyGreaterThanOrEqualTo(String value) {
            addCriterion("xjyy >=", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyLessThan(String value) {
            addCriterion("xjyy <", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyLessThanOrEqualTo(String value) {
            addCriterion("xjyy <=", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyLike(String value) {
            addCriterion("xjyy like", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyNotLike(String value) {
            addCriterion("xjyy not like", value, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyIn(List<String> values) {
            addCriterion("xjyy in", values, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyNotIn(List<String> values) {
            addCriterion("xjyy not in", values, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyBetween(String value1, String value2) {
            addCriterion("xjyy between", value1, value2, "xjyy");
            return (Criteria) this;
        }

        public Criteria andXjyyNotBetween(String value1, String value2) {
            addCriterion("xjyy not between", value1, value2, "xjyy");
            return (Criteria) this;
        }

        public Criteria andSfIsNull() {
            addCriterion("sf is null");
            return (Criteria) this;
        }

        public Criteria andSfIsNotNull() {
            addCriterion("sf is not null");
            return (Criteria) this;
        }

        public Criteria andSfEqualTo(String value) {
            addCriterion("sf =", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfNotEqualTo(String value) {
            addCriterion("sf <>", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfGreaterThan(String value) {
            addCriterion("sf >", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfGreaterThanOrEqualTo(String value) {
            addCriterion("sf >=", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfLessThan(String value) {
            addCriterion("sf <", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfLessThanOrEqualTo(String value) {
            addCriterion("sf <=", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfLike(String value) {
            addCriterion("sf like", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfNotLike(String value) {
            addCriterion("sf not like", value, "sf");
            return (Criteria) this;
        }

        public Criteria andSfIn(List<String> values) {
            addCriterion("sf in", values, "sf");
            return (Criteria) this;
        }

        public Criteria andSfNotIn(List<String> values) {
            addCriterion("sf not in", values, "sf");
            return (Criteria) this;
        }

        public Criteria andSfBetween(String value1, String value2) {
            addCriterion("sf between", value1, value2, "sf");
            return (Criteria) this;
        }

        public Criteria andSfNotBetween(String value1, String value2) {
            addCriterion("sf not between", value1, value2, "sf");
            return (Criteria) this;
        }

        public Criteria andDqIsNull() {
            addCriterion("dq is null");
            return (Criteria) this;
        }

        public Criteria andDqIsNotNull() {
            addCriterion("dq is not null");
            return (Criteria) this;
        }

        public Criteria andDqEqualTo(String value) {
            addCriterion("dq =", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqNotEqualTo(String value) {
            addCriterion("dq <>", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqGreaterThan(String value) {
            addCriterion("dq >", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqGreaterThanOrEqualTo(String value) {
            addCriterion("dq >=", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqLessThan(String value) {
            addCriterion("dq <", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqLessThanOrEqualTo(String value) {
            addCriterion("dq <=", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqLike(String value) {
            addCriterion("dq like", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqNotLike(String value) {
            addCriterion("dq not like", value, "dq");
            return (Criteria) this;
        }

        public Criteria andDqIn(List<String> values) {
            addCriterion("dq in", values, "dq");
            return (Criteria) this;
        }

        public Criteria andDqNotIn(List<String> values) {
            addCriterion("dq not in", values, "dq");
            return (Criteria) this;
        }

        public Criteria andDqBetween(String value1, String value2) {
            addCriterion("dq between", value1, value2, "dq");
            return (Criteria) this;
        }

        public Criteria andDqNotBetween(String value1, String value2) {
            addCriterion("dq not between", value1, value2, "dq");
            return (Criteria) this;
        }

        public Criteria andSyxxIsNull() {
            addCriterion("syxx is null");
            return (Criteria) this;
        }

        public Criteria andSyxxIsNotNull() {
            addCriterion("syxx is not null");
            return (Criteria) this;
        }

        public Criteria andSyxxEqualTo(String value) {
            addCriterion("syxx =", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxNotEqualTo(String value) {
            addCriterion("syxx <>", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxGreaterThan(String value) {
            addCriterion("syxx >", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxGreaterThanOrEqualTo(String value) {
            addCriterion("syxx >=", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxLessThan(String value) {
            addCriterion("syxx <", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxLessThanOrEqualTo(String value) {
            addCriterion("syxx <=", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxLike(String value) {
            addCriterion("syxx like", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxNotLike(String value) {
            addCriterion("syxx not like", value, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxIn(List<String> values) {
            addCriterion("syxx in", values, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxNotIn(List<String> values) {
            addCriterion("syxx not in", values, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxBetween(String value1, String value2) {
            addCriterion("syxx between", value1, value2, "syxx");
            return (Criteria) this;
        }

        public Criteria andSyxxNotBetween(String value1, String value2) {
            addCriterion("syxx not between", value1, value2, "syxx");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwIsNull() {
            addCriterion("dxhwpdw is null");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwIsNotNull() {
            addCriterion("dxhwpdw is not null");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwEqualTo(String value) {
            addCriterion("dxhwpdw =", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwNotEqualTo(String value) {
            addCriterion("dxhwpdw <>", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwGreaterThan(String value) {
            addCriterion("dxhwpdw >", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwGreaterThanOrEqualTo(String value) {
            addCriterion("dxhwpdw >=", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwLessThan(String value) {
            addCriterion("dxhwpdw <", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwLessThanOrEqualTo(String value) {
            addCriterion("dxhwpdw <=", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwLike(String value) {
            addCriterion("dxhwpdw like", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwNotLike(String value) {
            addCriterion("dxhwpdw not like", value, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwIn(List<String> values) {
            addCriterion("dxhwpdw in", values, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwNotIn(List<String> values) {
            addCriterion("dxhwpdw not in", values, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwBetween(String value1, String value2) {
            addCriterion("dxhwpdw between", value1, value2, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxhwpdwNotBetween(String value1, String value2) {
            addCriterion("dxhwpdw not between", value1, value2, "dxhwpdw");
            return (Criteria) this;
        }

        public Criteria andDxdwszdIsNull() {
            addCriterion("dxdwszd is null");
            return (Criteria) this;
        }

        public Criteria andDxdwszdIsNotNull() {
            addCriterion("dxdwszd is not null");
            return (Criteria) this;
        }

        public Criteria andDxdwszdEqualTo(String value) {
            addCriterion("dxdwszd =", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdNotEqualTo(String value) {
            addCriterion("dxdwszd <>", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdGreaterThan(String value) {
            addCriterion("dxdwszd >", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdGreaterThanOrEqualTo(String value) {
            addCriterion("dxdwszd >=", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdLessThan(String value) {
            addCriterion("dxdwszd <", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdLessThanOrEqualTo(String value) {
            addCriterion("dxdwszd <=", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdLike(String value) {
            addCriterion("dxdwszd like", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdNotLike(String value) {
            addCriterion("dxdwszd not like", value, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdIn(List<String> values) {
            addCriterion("dxdwszd in", values, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdNotIn(List<String> values) {
            addCriterion("dxdwszd not in", values, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdBetween(String value1, String value2) {
            addCriterion("dxdwszd between", value1, value2, "dxdwszd");
            return (Criteria) this;
        }

        public Criteria andDxdwszdNotBetween(String value1, String value2) {
            addCriterion("dxdwszd not between", value1, value2, "dxdwszd");
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

        public Criteria andKslbIsNull() {
            addCriterion("kslb is null");
            return (Criteria) this;
        }

        public Criteria andKslbIsNotNull() {
            addCriterion("kslb is not null");
            return (Criteria) this;
        }

        public Criteria andKslbEqualTo(String value) {
            addCriterion("kslb =", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbNotEqualTo(String value) {
            addCriterion("kslb <>", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbGreaterThan(String value) {
            addCriterion("kslb >", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbGreaterThanOrEqualTo(String value) {
            addCriterion("kslb >=", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbLessThan(String value) {
            addCriterion("kslb <", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbLessThanOrEqualTo(String value) {
            addCriterion("kslb <=", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbLike(String value) {
            addCriterion("kslb like", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbNotLike(String value) {
            addCriterion("kslb not like", value, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbIn(List<String> values) {
            addCriterion("kslb in", values, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbNotIn(List<String> values) {
            addCriterion("kslb not in", values, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbBetween(String value1, String value2) {
            addCriterion("kslb between", value1, value2, "kslb");
            return (Criteria) this;
        }

        public Criteria andKslbNotBetween(String value1, String value2) {
            addCriterion("kslb not between", value1, value2, "kslb");
            return (Criteria) this;
        }

        public Criteria andWyyzIsNull() {
            addCriterion("wyyz is null");
            return (Criteria) this;
        }

        public Criteria andWyyzIsNotNull() {
            addCriterion("wyyz is not null");
            return (Criteria) this;
        }

        public Criteria andWyyzEqualTo(String value) {
            addCriterion("wyyz =", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzNotEqualTo(String value) {
            addCriterion("wyyz <>", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzGreaterThan(String value) {
            addCriterion("wyyz >", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzGreaterThanOrEqualTo(String value) {
            addCriterion("wyyz >=", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzLessThan(String value) {
            addCriterion("wyyz <", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzLessThanOrEqualTo(String value) {
            addCriterion("wyyz <=", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzLike(String value) {
            addCriterion("wyyz like", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzNotLike(String value) {
            addCriterion("wyyz not like", value, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzIn(List<String> values) {
            addCriterion("wyyz in", values, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzNotIn(List<String> values) {
            addCriterion("wyyz not in", values, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzBetween(String value1, String value2) {
            addCriterion("wyyz between", value1, value2, "wyyz");
            return (Criteria) this;
        }

        public Criteria andWyyzNotBetween(String value1, String value2) {
            addCriterion("wyyz not between", value1, value2, "wyyz");
            return (Criteria) this;
        }

        public Criteria andJtdzIsNull() {
            addCriterion("jtdz is null");
            return (Criteria) this;
        }

        public Criteria andJtdzIsNotNull() {
            addCriterion("jtdz is not null");
            return (Criteria) this;
        }

        public Criteria andJtdzEqualTo(String value) {
            addCriterion("jtdz =", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzNotEqualTo(String value) {
            addCriterion("jtdz <>", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzGreaterThan(String value) {
            addCriterion("jtdz >", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzGreaterThanOrEqualTo(String value) {
            addCriterion("jtdz >=", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzLessThan(String value) {
            addCriterion("jtdz <", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzLessThanOrEqualTo(String value) {
            addCriterion("jtdz <=", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzLike(String value) {
            addCriterion("jtdz like", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzNotLike(String value) {
            addCriterion("jtdz not like", value, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzIn(List<String> values) {
            addCriterion("jtdz in", values, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzNotIn(List<String> values) {
            addCriterion("jtdz not in", values, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzBetween(String value1, String value2) {
            addCriterion("jtdz between", value1, value2, "jtdz");
            return (Criteria) this;
        }

        public Criteria andJtdzNotBetween(String value1, String value2) {
            addCriterion("jtdz not between", value1, value2, "jtdz");
            return (Criteria) this;
        }

        public Criteria andYzbmIsNull() {
            addCriterion("yzbm is null");
            return (Criteria) this;
        }

        public Criteria andYzbmIsNotNull() {
            addCriterion("yzbm is not null");
            return (Criteria) this;
        }

        public Criteria andYzbmEqualTo(String value) {
            addCriterion("yzbm =", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmNotEqualTo(String value) {
            addCriterion("yzbm <>", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmGreaterThan(String value) {
            addCriterion("yzbm >", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmGreaterThanOrEqualTo(String value) {
            addCriterion("yzbm >=", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmLessThan(String value) {
            addCriterion("yzbm <", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmLessThanOrEqualTo(String value) {
            addCriterion("yzbm <=", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmLike(String value) {
            addCriterion("yzbm like", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmNotLike(String value) {
            addCriterion("yzbm not like", value, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmIn(List<String> values) {
            addCriterion("yzbm in", values, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmNotIn(List<String> values) {
            addCriterion("yzbm not in", values, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmBetween(String value1, String value2) {
            addCriterion("yzbm between", value1, value2, "yzbm");
            return (Criteria) this;
        }

        public Criteria andYzbmNotBetween(String value1, String value2) {
            addCriterion("yzbm not between", value1, value2, "yzbm");
            return (Criteria) this;
        }

        public Criteria andLxdhIsNull() {
            addCriterion("lxdh is null");
            return (Criteria) this;
        }

        public Criteria andLxdhIsNotNull() {
            addCriterion("lxdh is not null");
            return (Criteria) this;
        }

        public Criteria andLxdhEqualTo(String value) {
            addCriterion("lxdh =", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotEqualTo(String value) {
            addCriterion("lxdh <>", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhGreaterThan(String value) {
            addCriterion("lxdh >", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhGreaterThanOrEqualTo(String value) {
            addCriterion("lxdh >=", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhLessThan(String value) {
            addCriterion("lxdh <", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhLessThanOrEqualTo(String value) {
            addCriterion("lxdh <=", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhLike(String value) {
            addCriterion("lxdh like", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotLike(String value) {
            addCriterion("lxdh not like", value, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhIn(List<String> values) {
            addCriterion("lxdh in", values, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotIn(List<String> values) {
            addCriterion("lxdh not in", values, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhBetween(String value1, String value2) {
            addCriterion("lxdh between", value1, value2, "lxdh");
            return (Criteria) this;
        }

        public Criteria andLxdhNotBetween(String value1, String value2) {
            addCriterion("lxdh not between", value1, value2, "lxdh");
            return (Criteria) this;
        }

        public Criteria andKlIsNull() {
            addCriterion("kl is null");
            return (Criteria) this;
        }

        public Criteria andKlIsNotNull() {
            addCriterion("kl is not null");
            return (Criteria) this;
        }

        public Criteria andKlEqualTo(String value) {
            addCriterion("kl =", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlNotEqualTo(String value) {
            addCriterion("kl <>", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlGreaterThan(String value) {
            addCriterion("kl >", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlGreaterThanOrEqualTo(String value) {
            addCriterion("kl >=", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlLessThan(String value) {
            addCriterion("kl <", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlLessThanOrEqualTo(String value) {
            addCriterion("kl <=", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlLike(String value) {
            addCriterion("kl like", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlNotLike(String value) {
            addCriterion("kl not like", value, "kl");
            return (Criteria) this;
        }

        public Criteria andKlIn(List<String> values) {
            addCriterion("kl in", values, "kl");
            return (Criteria) this;
        }

        public Criteria andKlNotIn(List<String> values) {
            addCriterion("kl not in", values, "kl");
            return (Criteria) this;
        }

        public Criteria andKlBetween(String value1, String value2) {
            addCriterion("kl between", value1, value2, "kl");
            return (Criteria) this;
        }

        public Criteria andKlNotBetween(String value1, String value2) {
            addCriterion("kl not between", value1, value2, "kl");
            return (Criteria) this;
        }

        public Criteria andLqzymcIsNull() {
            addCriterion("lqzymc is null");
            return (Criteria) this;
        }

        public Criteria andLqzymcIsNotNull() {
            addCriterion("lqzymc is not null");
            return (Criteria) this;
        }

        public Criteria andLqzymcEqualTo(String value) {
            addCriterion("lqzymc =", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcNotEqualTo(String value) {
            addCriterion("lqzymc <>", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcGreaterThan(String value) {
            addCriterion("lqzymc >", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcGreaterThanOrEqualTo(String value) {
            addCriterion("lqzymc >=", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcLessThan(String value) {
            addCriterion("lqzymc <", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcLessThanOrEqualTo(String value) {
            addCriterion("lqzymc <=", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcLike(String value) {
            addCriterion("lqzymc like", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcNotLike(String value) {
            addCriterion("lqzymc not like", value, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcIn(List<String> values) {
            addCriterion("lqzymc in", values, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcNotIn(List<String> values) {
            addCriterion("lqzymc not in", values, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcBetween(String value1, String value2) {
            addCriterion("lqzymc between", value1, value2, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqzymcNotBetween(String value1, String value2) {
            addCriterion("lqzymc not between", value1, value2, "lqzymc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcIsNull() {
            addCriterion("lqyxmc is null");
            return (Criteria) this;
        }

        public Criteria andLqyxmcIsNotNull() {
            addCriterion("lqyxmc is not null");
            return (Criteria) this;
        }

        public Criteria andLqyxmcEqualTo(String value) {
            addCriterion("lqyxmc =", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcNotEqualTo(String value) {
            addCriterion("lqyxmc <>", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcGreaterThan(String value) {
            addCriterion("lqyxmc >", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcGreaterThanOrEqualTo(String value) {
            addCriterion("lqyxmc >=", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcLessThan(String value) {
            addCriterion("lqyxmc <", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcLessThanOrEqualTo(String value) {
            addCriterion("lqyxmc <=", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcLike(String value) {
            addCriterion("lqyxmc like", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcNotLike(String value) {
            addCriterion("lqyxmc not like", value, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcIn(List<String> values) {
            addCriterion("lqyxmc in", values, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcNotIn(List<String> values) {
            addCriterion("lqyxmc not in", values, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcBetween(String value1, String value2) {
            addCriterion("lqyxmc between", value1, value2, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andLqyxmcNotBetween(String value1, String value2) {
            addCriterion("lqyxmc not between", value1, value2, "lqyxmc");
            return (Criteria) this;
        }

        public Criteria andMfsfsIsNull() {
            addCriterion("mfsfs is null");
            return (Criteria) this;
        }

        public Criteria andMfsfsIsNotNull() {
            addCriterion("mfsfs is not null");
            return (Criteria) this;
        }

        public Criteria andMfsfsEqualTo(String value) {
            addCriterion("mfsfs =", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsNotEqualTo(String value) {
            addCriterion("mfsfs <>", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsGreaterThan(String value) {
            addCriterion("mfsfs >", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsGreaterThanOrEqualTo(String value) {
            addCriterion("mfsfs >=", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsLessThan(String value) {
            addCriterion("mfsfs <", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsLessThanOrEqualTo(String value) {
            addCriterion("mfsfs <=", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsLike(String value) {
            addCriterion("mfsfs like", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsNotLike(String value) {
            addCriterion("mfsfs not like", value, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsIn(List<String> values) {
            addCriterion("mfsfs in", values, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsNotIn(List<String> values) {
            addCriterion("mfsfs not in", values, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsBetween(String value1, String value2) {
            addCriterion("mfsfs between", value1, value2, "mfsfs");
            return (Criteria) this;
        }

        public Criteria andMfsfsNotBetween(String value1, String value2) {
            addCriterion("mfsfs not between", value1, value2, "mfsfs");
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

        public Criteria andKstcIsNull() {
            addCriterion("kstc is null");
            return (Criteria) this;
        }

        public Criteria andKstcIsNotNull() {
            addCriterion("kstc is not null");
            return (Criteria) this;
        }

        public Criteria andKstcEqualTo(String value) {
            addCriterion("kstc =", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcNotEqualTo(String value) {
            addCriterion("kstc <>", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcGreaterThan(String value) {
            addCriterion("kstc >", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcGreaterThanOrEqualTo(String value) {
            addCriterion("kstc >=", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcLessThan(String value) {
            addCriterion("kstc <", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcLessThanOrEqualTo(String value) {
            addCriterion("kstc <=", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcLike(String value) {
            addCriterion("kstc like", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcNotLike(String value) {
            addCriterion("kstc not like", value, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcIn(List<String> values) {
            addCriterion("kstc in", values, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcNotIn(List<String> values) {
            addCriterion("kstc not in", values, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcBetween(String value1, String value2) {
            addCriterion("kstc between", value1, value2, "kstc");
            return (Criteria) this;
        }

        public Criteria andKstcNotBetween(String value1, String value2) {
            addCriterion("kstc not between", value1, value2, "kstc");
            return (Criteria) this;
        }

        public Criteria andGrpjIsNull() {
            addCriterion("grpj is null");
            return (Criteria) this;
        }

        public Criteria andGrpjIsNotNull() {
            addCriterion("grpj is not null");
            return (Criteria) this;
        }

        public Criteria andGrpjEqualTo(String value) {
            addCriterion("grpj =", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjNotEqualTo(String value) {
            addCriterion("grpj <>", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjGreaterThan(String value) {
            addCriterion("grpj >", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjGreaterThanOrEqualTo(String value) {
            addCriterion("grpj >=", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjLessThan(String value) {
            addCriterion("grpj <", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjLessThanOrEqualTo(String value) {
            addCriterion("grpj <=", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjLike(String value) {
            addCriterion("grpj like", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjNotLike(String value) {
            addCriterion("grpj not like", value, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjIn(List<String> values) {
            addCriterion("grpj in", values, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjNotIn(List<String> values) {
            addCriterion("grpj not in", values, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjBetween(String value1, String value2) {
            addCriterion("grpj between", value1, value2, "grpj");
            return (Criteria) this;
        }

        public Criteria andGrpjNotBetween(String value1, String value2) {
            addCriterion("grpj not between", value1, value2, "grpj");
            return (Criteria) this;
        }

        public Criteria andKsjlIsNull() {
            addCriterion("ksjl is null");
            return (Criteria) this;
        }

        public Criteria andKsjlIsNotNull() {
            addCriterion("ksjl is not null");
            return (Criteria) this;
        }

        public Criteria andKsjlEqualTo(String value) {
            addCriterion("ksjl =", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlNotEqualTo(String value) {
            addCriterion("ksjl <>", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlGreaterThan(String value) {
            addCriterion("ksjl >", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlGreaterThanOrEqualTo(String value) {
            addCriterion("ksjl >=", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlLessThan(String value) {
            addCriterion("ksjl <", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlLessThanOrEqualTo(String value) {
            addCriterion("ksjl <=", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlLike(String value) {
            addCriterion("ksjl like", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlNotLike(String value) {
            addCriterion("ksjl not like", value, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlIn(List<String> values) {
            addCriterion("ksjl in", values, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlNotIn(List<String> values) {
            addCriterion("ksjl not in", values, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlBetween(String value1, String value2) {
            addCriterion("ksjl between", value1, value2, "ksjl");
            return (Criteria) this;
        }

        public Criteria andKsjlNotBetween(String value1, String value2) {
            addCriterion("ksjl not between", value1, value2, "ksjl");
            return (Criteria) this;
        }

        public Criteria andXkmlIsNull() {
            addCriterion("xkml is null");
            return (Criteria) this;
        }

        public Criteria andXkmlIsNotNull() {
            addCriterion("xkml is not null");
            return (Criteria) this;
        }

        public Criteria andXkmlEqualTo(String value) {
            addCriterion("xkml =", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlNotEqualTo(String value) {
            addCriterion("xkml <>", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlGreaterThan(String value) {
            addCriterion("xkml >", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlGreaterThanOrEqualTo(String value) {
            addCriterion("xkml >=", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlLessThan(String value) {
            addCriterion("xkml <", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlLessThanOrEqualTo(String value) {
            addCriterion("xkml <=", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlLike(String value) {
            addCriterion("xkml like", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlNotLike(String value) {
            addCriterion("xkml not like", value, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlIn(List<String> values) {
            addCriterion("xkml in", values, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlNotIn(List<String> values) {
            addCriterion("xkml not in", values, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlBetween(String value1, String value2) {
            addCriterion("xkml between", value1, value2, "xkml");
            return (Criteria) this;
        }

        public Criteria andXkmlNotBetween(String value1, String value2) {
            addCriterion("xkml not between", value1, value2, "xkml");
            return (Criteria) this;
        }

        public Criteria andZkzhIsNull() {
            addCriterion("zkzh is null");
            return (Criteria) this;
        }

        public Criteria andZkzhIsNotNull() {
            addCriterion("zkzh is not null");
            return (Criteria) this;
        }

        public Criteria andZkzhEqualTo(String value) {
            addCriterion("zkzh =", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhNotEqualTo(String value) {
            addCriterion("zkzh <>", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhGreaterThan(String value) {
            addCriterion("zkzh >", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhGreaterThanOrEqualTo(String value) {
            addCriterion("zkzh >=", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhLessThan(String value) {
            addCriterion("zkzh <", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhLessThanOrEqualTo(String value) {
            addCriterion("zkzh <=", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhLike(String value) {
            addCriterion("zkzh like", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhNotLike(String value) {
            addCriterion("zkzh not like", value, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhIn(List<String> values) {
            addCriterion("zkzh in", values, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhNotIn(List<String> values) {
            addCriterion("zkzh not in", values, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhBetween(String value1, String value2) {
            addCriterion("zkzh between", value1, value2, "zkzh");
            return (Criteria) this;
        }

        public Criteria andZkzhNotBetween(String value1, String value2) {
            addCriterion("zkzh not between", value1, value2, "zkzh");
            return (Criteria) this;
        }

        public Criteria andByxxIsNull() {
            addCriterion("byxx is null");
            return (Criteria) this;
        }

        public Criteria andByxxIsNotNull() {
            addCriterion("byxx is not null");
            return (Criteria) this;
        }

        public Criteria andByxxEqualTo(String value) {
            addCriterion("byxx =", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxNotEqualTo(String value) {
            addCriterion("byxx <>", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxGreaterThan(String value) {
            addCriterion("byxx >", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxGreaterThanOrEqualTo(String value) {
            addCriterion("byxx >=", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxLessThan(String value) {
            addCriterion("byxx <", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxLessThanOrEqualTo(String value) {
            addCriterion("byxx <=", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxLike(String value) {
            addCriterion("byxx like", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxNotLike(String value) {
            addCriterion("byxx not like", value, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxIn(List<String> values) {
            addCriterion("byxx in", values, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxNotIn(List<String> values) {
            addCriterion("byxx not in", values, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxBetween(String value1, String value2) {
            addCriterion("byxx between", value1, value2, "byxx");
            return (Criteria) this;
        }

        public Criteria andByxxNotBetween(String value1, String value2) {
            addCriterion("byxx not between", value1, value2, "byxx");
            return (Criteria) this;
        }

        public Criteria andBzIsNull() {
            addCriterion("bz is null");
            return (Criteria) this;
        }

        public Criteria andBzIsNotNull() {
            addCriterion("bz is not null");
            return (Criteria) this;
        }

        public Criteria andBzEqualTo(String value) {
            addCriterion("bz =", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotEqualTo(String value) {
            addCriterion("bz <>", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThan(String value) {
            addCriterion("bz >", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThanOrEqualTo(String value) {
            addCriterion("bz >=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThan(String value) {
            addCriterion("bz <", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThanOrEqualTo(String value) {
            addCriterion("bz <=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLike(String value) {
            addCriterion("bz like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotLike(String value) {
            addCriterion("bz not like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzIn(List<String> values) {
            addCriterion("bz in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotIn(List<String> values) {
            addCriterion("bz not in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzBetween(String value1, String value2) {
            addCriterion("bz between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotBetween(String value1, String value2) {
            addCriterion("bz not between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andSfgfsIsNull() {
            addCriterion("sfgfs is null");
            return (Criteria) this;
        }

        public Criteria andSfgfsIsNotNull() {
            addCriterion("sfgfs is not null");
            return (Criteria) this;
        }

        public Criteria andSfgfsEqualTo(String value) {
            addCriterion("sfgfs =", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsNotEqualTo(String value) {
            addCriterion("sfgfs <>", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsGreaterThan(String value) {
            addCriterion("sfgfs >", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsGreaterThanOrEqualTo(String value) {
            addCriterion("sfgfs >=", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsLessThan(String value) {
            addCriterion("sfgfs <", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsLessThanOrEqualTo(String value) {
            addCriterion("sfgfs <=", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsLike(String value) {
            addCriterion("sfgfs like", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsNotLike(String value) {
            addCriterion("sfgfs not like", value, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsIn(List<String> values) {
            addCriterion("sfgfs in", values, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsNotIn(List<String> values) {
            addCriterion("sfgfs not in", values, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsBetween(String value1, String value2) {
            addCriterion("sfgfs between", value1, value2, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andSfgfsNotBetween(String value1, String value2) {
            addCriterion("sfgfs not between", value1, value2, "sfgfs");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisIsNull() {
            addCriterion("import_time_millis is null");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisIsNotNull() {
            addCriterion("import_time_millis is not null");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisEqualTo(String value) {
            addCriterion("import_time_millis =", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisNotEqualTo(String value) {
            addCriterion("import_time_millis <>", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisGreaterThan(String value) {
            addCriterion("import_time_millis >", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisGreaterThanOrEqualTo(String value) {
            addCriterion("import_time_millis >=", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisLessThan(String value) {
            addCriterion("import_time_millis <", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisLessThanOrEqualTo(String value) {
            addCriterion("import_time_millis <=", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisLike(String value) {
            addCriterion("import_time_millis like", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisNotLike(String value) {
            addCriterion("import_time_millis not like", value, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisIn(List<String> values) {
            addCriterion("import_time_millis in", values, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisNotIn(List<String> values) {
            addCriterion("import_time_millis not in", values, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisBetween(String value1, String value2) {
            addCriterion("import_time_millis between", value1, value2, "importTimeMillis");
            return (Criteria) this;
        }

        public Criteria andImportTimeMillisNotBetween(String value1, String value2) {
            addCriterion("import_time_millis not between", value1, value2, "importTimeMillis");
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

        public Criteria andXjztIsNull() {
            addCriterion("xjzt is null");
            return (Criteria) this;
        }

        public Criteria andXjztIsNotNull() {
            addCriterion("xjzt is not null");
            return (Criteria) this;
        }

        public Criteria andXjztEqualTo(String value) {
            addCriterion("xjzt =", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztNotEqualTo(String value) {
            addCriterion("xjzt <>", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztGreaterThan(String value) {
            addCriterion("xjzt >", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztGreaterThanOrEqualTo(String value) {
            addCriterion("xjzt >=", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztLessThan(String value) {
            addCriterion("xjzt <", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztLessThanOrEqualTo(String value) {
            addCriterion("xjzt <=", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztLike(String value) {
            addCriterion("xjzt like", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztNotLike(String value) {
            addCriterion("xjzt not like", value, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztIn(List<String> values) {
            addCriterion("xjzt in", values, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztNotIn(List<String> values) {
            addCriterion("xjzt not in", values, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztBetween(String value1, String value2) {
            addCriterion("xjzt between", value1, value2, "xjzt");
            return (Criteria) this;
        }

        public Criteria andXjztNotBetween(String value1, String value2) {
            addCriterion("xjzt not between", value1, value2, "xjzt");
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