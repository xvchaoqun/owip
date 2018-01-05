package domain.ext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExtJzgSalaryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExtJzgSalaryExample() {
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

        public Criteria andZghIsNull() {
            addCriterion("zgh is null");
            return (Criteria) this;
        }

        public Criteria andZghIsNotNull() {
            addCriterion("zgh is not null");
            return (Criteria) this;
        }

        public Criteria andZghEqualTo(String value) {
            addCriterion("zgh =", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotEqualTo(String value) {
            addCriterion("zgh <>", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghGreaterThan(String value) {
            addCriterion("zgh >", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghGreaterThanOrEqualTo(String value) {
            addCriterion("zgh >=", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghLessThan(String value) {
            addCriterion("zgh <", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghLessThanOrEqualTo(String value) {
            addCriterion("zgh <=", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghLike(String value) {
            addCriterion("zgh like", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotLike(String value) {
            addCriterion("zgh not like", value, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghIn(List<String> values) {
            addCriterion("zgh in", values, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotIn(List<String> values) {
            addCriterion("zgh not in", values, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghBetween(String value1, String value2) {
            addCriterion("zgh between", value1, value2, "zgh");
            return (Criteria) this;
        }

        public Criteria andZghNotBetween(String value1, String value2) {
            addCriterion("zgh not between", value1, value2, "zgh");
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

        public Criteria andRqIsNull() {
            addCriterion("rq is null");
            return (Criteria) this;
        }

        public Criteria andRqIsNotNull() {
            addCriterion("rq is not null");
            return (Criteria) this;
        }

        public Criteria andRqEqualTo(String value) {
            addCriterion("rq =", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotEqualTo(String value) {
            addCriterion("rq <>", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqGreaterThan(String value) {
            addCriterion("rq >", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqGreaterThanOrEqualTo(String value) {
            addCriterion("rq >=", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqLessThan(String value) {
            addCriterion("rq <", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqLessThanOrEqualTo(String value) {
            addCriterion("rq <=", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqLike(String value) {
            addCriterion("rq like", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotLike(String value) {
            addCriterion("rq not like", value, "rq");
            return (Criteria) this;
        }

        public Criteria andRqIn(List<String> values) {
            addCriterion("rq in", values, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotIn(List<String> values) {
            addCriterion("rq not in", values, "rq");
            return (Criteria) this;
        }

        public Criteria andRqBetween(String value1, String value2) {
            addCriterion("rq between", value1, value2, "rq");
            return (Criteria) this;
        }

        public Criteria andRqNotBetween(String value1, String value2) {
            addCriterion("rq not between", value1, value2, "rq");
            return (Criteria) this;
        }

        public Criteria andXpgzIsNull() {
            addCriterion("xpgz is null");
            return (Criteria) this;
        }

        public Criteria andXpgzIsNotNull() {
            addCriterion("xpgz is not null");
            return (Criteria) this;
        }

        public Criteria andXpgzEqualTo(BigDecimal value) {
            addCriterion("xpgz =", value, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzNotEqualTo(BigDecimal value) {
            addCriterion("xpgz <>", value, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzGreaterThan(BigDecimal value) {
            addCriterion("xpgz >", value, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("xpgz >=", value, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzLessThan(BigDecimal value) {
            addCriterion("xpgz <", value, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzLessThanOrEqualTo(BigDecimal value) {
            addCriterion("xpgz <=", value, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzIn(List<BigDecimal> values) {
            addCriterion("xpgz in", values, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzNotIn(List<BigDecimal> values) {
            addCriterion("xpgz not in", values, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xpgz between", value1, value2, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXpgzNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xpgz not between", value1, value2, "xpgz");
            return (Criteria) this;
        }

        public Criteria andXjgzIsNull() {
            addCriterion("xjgz is null");
            return (Criteria) this;
        }

        public Criteria andXjgzIsNotNull() {
            addCriterion("xjgz is not null");
            return (Criteria) this;
        }

        public Criteria andXjgzEqualTo(BigDecimal value) {
            addCriterion("xjgz =", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzNotEqualTo(BigDecimal value) {
            addCriterion("xjgz <>", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzGreaterThan(BigDecimal value) {
            addCriterion("xjgz >", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("xjgz >=", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzLessThan(BigDecimal value) {
            addCriterion("xjgz <", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzLessThanOrEqualTo(BigDecimal value) {
            addCriterion("xjgz <=", value, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzIn(List<BigDecimal> values) {
            addCriterion("xjgz in", values, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzNotIn(List<BigDecimal> values) {
            addCriterion("xjgz not in", values, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xjgz between", value1, value2, "xjgz");
            return (Criteria) this;
        }

        public Criteria andXjgzNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xjgz not between", value1, value2, "xjgz");
            return (Criteria) this;
        }

        public Criteria andGwgzIsNull() {
            addCriterion("gwgz is null");
            return (Criteria) this;
        }

        public Criteria andGwgzIsNotNull() {
            addCriterion("gwgz is not null");
            return (Criteria) this;
        }

        public Criteria andGwgzEqualTo(BigDecimal value) {
            addCriterion("gwgz =", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzNotEqualTo(BigDecimal value) {
            addCriterion("gwgz <>", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzGreaterThan(BigDecimal value) {
            addCriterion("gwgz >", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gwgz >=", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzLessThan(BigDecimal value) {
            addCriterion("gwgz <", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gwgz <=", value, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzIn(List<BigDecimal> values) {
            addCriterion("gwgz in", values, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzNotIn(List<BigDecimal> values) {
            addCriterion("gwgz not in", values, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwgz between", value1, value2, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwgzNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwgz not between", value1, value2, "gwgz");
            return (Criteria) this;
        }

        public Criteria andGwjtIsNull() {
            addCriterion("gwjt is null");
            return (Criteria) this;
        }

        public Criteria andGwjtIsNotNull() {
            addCriterion("gwjt is not null");
            return (Criteria) this;
        }

        public Criteria andGwjtEqualTo(BigDecimal value) {
            addCriterion("gwjt =", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtNotEqualTo(BigDecimal value) {
            addCriterion("gwjt <>", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtGreaterThan(BigDecimal value) {
            addCriterion("gwjt >", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gwjt >=", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtLessThan(BigDecimal value) {
            addCriterion("gwjt <", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gwjt <=", value, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtIn(List<BigDecimal> values) {
            addCriterion("gwjt in", values, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtNotIn(List<BigDecimal> values) {
            addCriterion("gwjt not in", values, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwjt between", value1, value2, "gwjt");
            return (Criteria) this;
        }

        public Criteria andGwjtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gwjt not between", value1, value2, "gwjt");
            return (Criteria) this;
        }

        public Criteria andZwbtIsNull() {
            addCriterion("zwbt is null");
            return (Criteria) this;
        }

        public Criteria andZwbtIsNotNull() {
            addCriterion("zwbt is not null");
            return (Criteria) this;
        }

        public Criteria andZwbtEqualTo(BigDecimal value) {
            addCriterion("zwbt =", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtNotEqualTo(BigDecimal value) {
            addCriterion("zwbt <>", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtGreaterThan(BigDecimal value) {
            addCriterion("zwbt >", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt >=", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtLessThan(BigDecimal value) {
            addCriterion("zwbt <", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt <=", value, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtIn(List<BigDecimal> values) {
            addCriterion("zwbt in", values, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtNotIn(List<BigDecimal> values) {
            addCriterion("zwbt not in", values, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt between", value1, value2, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt not between", value1, value2, "zwbt");
            return (Criteria) this;
        }

        public Criteria andZwbt1IsNull() {
            addCriterion("zwbt1 is null");
            return (Criteria) this;
        }

        public Criteria andZwbt1IsNotNull() {
            addCriterion("zwbt1 is not null");
            return (Criteria) this;
        }

        public Criteria andZwbt1EqualTo(BigDecimal value) {
            addCriterion("zwbt1 =", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1NotEqualTo(BigDecimal value) {
            addCriterion("zwbt1 <>", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1GreaterThan(BigDecimal value) {
            addCriterion("zwbt1 >", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt1 >=", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1LessThan(BigDecimal value) {
            addCriterion("zwbt1 <", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("zwbt1 <=", value, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1In(List<BigDecimal> values) {
            addCriterion("zwbt1 in", values, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1NotIn(List<BigDecimal> values) {
            addCriterion("zwbt1 not in", values, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt1 between", value1, value2, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andZwbt1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zwbt1 not between", value1, value2, "zwbt1");
            return (Criteria) this;
        }

        public Criteria andShbtIsNull() {
            addCriterion("shbt is null");
            return (Criteria) this;
        }

        public Criteria andShbtIsNotNull() {
            addCriterion("shbt is not null");
            return (Criteria) this;
        }

        public Criteria andShbtEqualTo(BigDecimal value) {
            addCriterion("shbt =", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtNotEqualTo(BigDecimal value) {
            addCriterion("shbt <>", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtGreaterThan(BigDecimal value) {
            addCriterion("shbt >", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shbt >=", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtLessThan(BigDecimal value) {
            addCriterion("shbt <", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shbt <=", value, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtIn(List<BigDecimal> values) {
            addCriterion("shbt in", values, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtNotIn(List<BigDecimal> values) {
            addCriterion("shbt not in", values, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shbt between", value1, value2, "shbt");
            return (Criteria) this;
        }

        public Criteria andShbtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shbt not between", value1, value2, "shbt");
            return (Criteria) this;
        }

        public Criteria andSbfIsNull() {
            addCriterion("sbf is null");
            return (Criteria) this;
        }

        public Criteria andSbfIsNotNull() {
            addCriterion("sbf is not null");
            return (Criteria) this;
        }

        public Criteria andSbfEqualTo(BigDecimal value) {
            addCriterion("sbf =", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfNotEqualTo(BigDecimal value) {
            addCriterion("sbf <>", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfGreaterThan(BigDecimal value) {
            addCriterion("sbf >", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sbf >=", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfLessThan(BigDecimal value) {
            addCriterion("sbf <", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sbf <=", value, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfIn(List<BigDecimal> values) {
            addCriterion("sbf in", values, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfNotIn(List<BigDecimal> values) {
            addCriterion("sbf not in", values, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sbf between", value1, value2, "sbf");
            return (Criteria) this;
        }

        public Criteria andSbfNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sbf not between", value1, value2, "sbf");
            return (Criteria) this;
        }

        public Criteria andXlfIsNull() {
            addCriterion("xlf is null");
            return (Criteria) this;
        }

        public Criteria andXlfIsNotNull() {
            addCriterion("xlf is not null");
            return (Criteria) this;
        }

        public Criteria andXlfEqualTo(BigDecimal value) {
            addCriterion("xlf =", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfNotEqualTo(BigDecimal value) {
            addCriterion("xlf <>", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfGreaterThan(BigDecimal value) {
            addCriterion("xlf >", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("xlf >=", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfLessThan(BigDecimal value) {
            addCriterion("xlf <", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfLessThanOrEqualTo(BigDecimal value) {
            addCriterion("xlf <=", value, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfIn(List<BigDecimal> values) {
            addCriterion("xlf in", values, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfNotIn(List<BigDecimal> values) {
            addCriterion("xlf not in", values, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xlf between", value1, value2, "xlf");
            return (Criteria) this;
        }

        public Criteria andXlfNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xlf not between", value1, value2, "xlf");
            return (Criteria) this;
        }

        public Criteria andGzcxIsNull() {
            addCriterion("gzcx is null");
            return (Criteria) this;
        }

        public Criteria andGzcxIsNotNull() {
            addCriterion("gzcx is not null");
            return (Criteria) this;
        }

        public Criteria andGzcxEqualTo(BigDecimal value) {
            addCriterion("gzcx =", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxNotEqualTo(BigDecimal value) {
            addCriterion("gzcx <>", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxGreaterThan(BigDecimal value) {
            addCriterion("gzcx >", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gzcx >=", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxLessThan(BigDecimal value) {
            addCriterion("gzcx <", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gzcx <=", value, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxIn(List<BigDecimal> values) {
            addCriterion("gzcx in", values, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxNotIn(List<BigDecimal> values) {
            addCriterion("gzcx not in", values, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gzcx between", value1, value2, "gzcx");
            return (Criteria) this;
        }

        public Criteria andGzcxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gzcx not between", value1, value2, "gzcx");
            return (Criteria) this;
        }

        public Criteria andSygrIsNull() {
            addCriterion("sygr is null");
            return (Criteria) this;
        }

        public Criteria andSygrIsNotNull() {
            addCriterion("sygr is not null");
            return (Criteria) this;
        }

        public Criteria andSygrEqualTo(BigDecimal value) {
            addCriterion("sygr =", value, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrNotEqualTo(BigDecimal value) {
            addCriterion("sygr <>", value, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrGreaterThan(BigDecimal value) {
            addCriterion("sygr >", value, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sygr >=", value, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrLessThan(BigDecimal value) {
            addCriterion("sygr <", value, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sygr <=", value, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrIn(List<BigDecimal> values) {
            addCriterion("sygr in", values, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrNotIn(List<BigDecimal> values) {
            addCriterion("sygr not in", values, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sygr between", value1, value2, "sygr");
            return (Criteria) this;
        }

        public Criteria andSygrNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sygr not between", value1, value2, "sygr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrIsNull() {
            addCriterion("yanglaogr is null");
            return (Criteria) this;
        }

        public Criteria andYanglaogrIsNotNull() {
            addCriterion("yanglaogr is not null");
            return (Criteria) this;
        }

        public Criteria andYanglaogrEqualTo(BigDecimal value) {
            addCriterion("yanglaogr =", value, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrNotEqualTo(BigDecimal value) {
            addCriterion("yanglaogr <>", value, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrGreaterThan(BigDecimal value) {
            addCriterion("yanglaogr >", value, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yanglaogr >=", value, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrLessThan(BigDecimal value) {
            addCriterion("yanglaogr <", value, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yanglaogr <=", value, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrIn(List<BigDecimal> values) {
            addCriterion("yanglaogr in", values, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrNotIn(List<BigDecimal> values) {
            addCriterion("yanglaogr not in", values, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yanglaogr between", value1, value2, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYanglaogrNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yanglaogr not between", value1, value2, "yanglaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrIsNull() {
            addCriterion("yiliaogr is null");
            return (Criteria) this;
        }

        public Criteria andYiliaogrIsNotNull() {
            addCriterion("yiliaogr is not null");
            return (Criteria) this;
        }

        public Criteria andYiliaogrEqualTo(BigDecimal value) {
            addCriterion("yiliaogr =", value, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrNotEqualTo(BigDecimal value) {
            addCriterion("yiliaogr <>", value, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrGreaterThan(BigDecimal value) {
            addCriterion("yiliaogr >", value, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("yiliaogr >=", value, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrLessThan(BigDecimal value) {
            addCriterion("yiliaogr <", value, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrLessThanOrEqualTo(BigDecimal value) {
            addCriterion("yiliaogr <=", value, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrIn(List<BigDecimal> values) {
            addCriterion("yiliaogr in", values, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrNotIn(List<BigDecimal> values) {
            addCriterion("yiliaogr not in", values, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yiliaogr between", value1, value2, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andYiliaogrNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("yiliaogr not between", value1, value2, "yiliaogr");
            return (Criteria) this;
        }

        public Criteria andNjgrIsNull() {
            addCriterion("njgr is null");
            return (Criteria) this;
        }

        public Criteria andNjgrIsNotNull() {
            addCriterion("njgr is not null");
            return (Criteria) this;
        }

        public Criteria andNjgrEqualTo(BigDecimal value) {
            addCriterion("njgr =", value, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrNotEqualTo(BigDecimal value) {
            addCriterion("njgr <>", value, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrGreaterThan(BigDecimal value) {
            addCriterion("njgr >", value, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("njgr >=", value, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrLessThan(BigDecimal value) {
            addCriterion("njgr <", value, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrLessThanOrEqualTo(BigDecimal value) {
            addCriterion("njgr <=", value, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrIn(List<BigDecimal> values) {
            addCriterion("njgr in", values, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrNotIn(List<BigDecimal> values) {
            addCriterion("njgr not in", values, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("njgr between", value1, value2, "njgr");
            return (Criteria) this;
        }

        public Criteria andNjgrNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("njgr not between", value1, value2, "njgr");
            return (Criteria) this;
        }

        public Criteria andZfgjjIsNull() {
            addCriterion("zfgjj is null");
            return (Criteria) this;
        }

        public Criteria andZfgjjIsNotNull() {
            addCriterion("zfgjj is not null");
            return (Criteria) this;
        }

        public Criteria andZfgjjEqualTo(BigDecimal value) {
            addCriterion("zfgjj =", value, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjNotEqualTo(BigDecimal value) {
            addCriterion("zfgjj <>", value, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjGreaterThan(BigDecimal value) {
            addCriterion("zfgjj >", value, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zfgjj >=", value, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjLessThan(BigDecimal value) {
            addCriterion("zfgjj <", value, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("zfgjj <=", value, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjIn(List<BigDecimal> values) {
            addCriterion("zfgjj in", values, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjNotIn(List<BigDecimal> values) {
            addCriterion("zfgjj not in", values, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zfgjj between", value1, value2, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZfgjjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zfgjj not between", value1, value2, "zfgjj");
            return (Criteria) this;
        }

        public Criteria andZzryhjIsNull() {
            addCriterion("zzryhj is null");
            return (Criteria) this;
        }

        public Criteria andZzryhjIsNotNull() {
            addCriterion("zzryhj is not null");
            return (Criteria) this;
        }

        public Criteria andZzryhjEqualTo(BigDecimal value) {
            addCriterion("zzryhj =", value, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjNotEqualTo(BigDecimal value) {
            addCriterion("zzryhj <>", value, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjGreaterThan(BigDecimal value) {
            addCriterion("zzryhj >", value, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("zzryhj >=", value, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjLessThan(BigDecimal value) {
            addCriterion("zzryhj <", value, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("zzryhj <=", value, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjIn(List<BigDecimal> values) {
            addCriterion("zzryhj in", values, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjNotIn(List<BigDecimal> values) {
            addCriterion("zzryhj not in", values, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zzryhj between", value1, value2, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andZzryhjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("zzryhj not between", value1, value2, "zzryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjIsNull() {
            addCriterion("xpryhj is null");
            return (Criteria) this;
        }

        public Criteria andXpryhjIsNotNull() {
            addCriterion("xpryhj is not null");
            return (Criteria) this;
        }

        public Criteria andXpryhjEqualTo(BigDecimal value) {
            addCriterion("xpryhj =", value, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjNotEqualTo(BigDecimal value) {
            addCriterion("xpryhj <>", value, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjGreaterThan(BigDecimal value) {
            addCriterion("xpryhj >", value, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("xpryhj >=", value, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjLessThan(BigDecimal value) {
            addCriterion("xpryhj <", value, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjLessThanOrEqualTo(BigDecimal value) {
            addCriterion("xpryhj <=", value, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjIn(List<BigDecimal> values) {
            addCriterion("xpryhj in", values, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjNotIn(List<BigDecimal> values) {
            addCriterion("xpryhj not in", values, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xpryhj between", value1, value2, "xpryhj");
            return (Criteria) this;
        }

        public Criteria andXpryhjNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("xpryhj not between", value1, value2, "xpryhj");
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