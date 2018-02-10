package domain.member;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import sys.constants.RoleConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MemberAbroadViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberAbroadViewExample() {
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

        public Criteria andLshIsNull() {
            addCriterion("lsh is null");
            return (Criteria) this;
        }

        public Criteria andLshIsNotNull() {
            addCriterion("lsh is not null");
            return (Criteria) this;
        }

        public Criteria andLshEqualTo(String value) {
            addCriterion("lsh =", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotEqualTo(String value) {
            addCriterion("lsh <>", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshGreaterThan(String value) {
            addCriterion("lsh >", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshGreaterThanOrEqualTo(String value) {
            addCriterion("lsh >=", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshLessThan(String value) {
            addCriterion("lsh <", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshLessThanOrEqualTo(String value) {
            addCriterion("lsh <=", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshLike(String value) {
            addCriterion("lsh like", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotLike(String value) {
            addCriterion("lsh not like", value, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshIn(List<String> values) {
            addCriterion("lsh in", values, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotIn(List<String> values) {
            addCriterion("lsh not in", values, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshBetween(String value1, String value2) {
            addCriterion("lsh between", value1, value2, "lsh");
            return (Criteria) this;
        }

        public Criteria andLshNotBetween(String value1, String value2) {
            addCriterion("lsh not between", value1, value2, "lsh");
            return (Criteria) this;
        }

        public Criteria andGzzhIsNull() {
            addCriterion("gzzh is null");
            return (Criteria) this;
        }

        public Criteria andGzzhIsNotNull() {
            addCriterion("gzzh is not null");
            return (Criteria) this;
        }

        public Criteria andGzzhEqualTo(String value) {
            addCriterion("gzzh =", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhNotEqualTo(String value) {
            addCriterion("gzzh <>", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhGreaterThan(String value) {
            addCriterion("gzzh >", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhGreaterThanOrEqualTo(String value) {
            addCriterion("gzzh >=", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhLessThan(String value) {
            addCriterion("gzzh <", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhLessThanOrEqualTo(String value) {
            addCriterion("gzzh <=", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhLike(String value) {
            addCriterion("gzzh like", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhNotLike(String value) {
            addCriterion("gzzh not like", value, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhIn(List<String> values) {
            addCriterion("gzzh in", values, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhNotIn(List<String> values) {
            addCriterion("gzzh not in", values, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhBetween(String value1, String value2) {
            addCriterion("gzzh between", value1, value2, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGzzhNotBetween(String value1, String value2) {
            addCriterion("gzzh not between", value1, value2, "gzzh");
            return (Criteria) this;
        }

        public Criteria andGjIsNull() {
            addCriterion("gj is null");
            return (Criteria) this;
        }

        public Criteria andGjIsNotNull() {
            addCriterion("gj is not null");
            return (Criteria) this;
        }

        public Criteria andGjEqualTo(String value) {
            addCriterion("gj =", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjNotEqualTo(String value) {
            addCriterion("gj <>", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjGreaterThan(String value) {
            addCriterion("gj >", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjGreaterThanOrEqualTo(String value) {
            addCriterion("gj >=", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjLessThan(String value) {
            addCriterion("gj <", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjLessThanOrEqualTo(String value) {
            addCriterion("gj <=", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjLike(String value) {
            addCriterion("gj like", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjNotLike(String value) {
            addCriterion("gj not like", value, "gj");
            return (Criteria) this;
        }

        public Criteria andGjIn(List<String> values) {
            addCriterion("gj in", values, "gj");
            return (Criteria) this;
        }

        public Criteria andGjNotIn(List<String> values) {
            addCriterion("gj not in", values, "gj");
            return (Criteria) this;
        }

        public Criteria andGjBetween(String value1, String value2) {
            addCriterion("gj between", value1, value2, "gj");
            return (Criteria) this;
        }

        public Criteria andGjNotBetween(String value1, String value2) {
            addCriterion("gj not between", value1, value2, "gj");
            return (Criteria) this;
        }

        public Criteria andJflyIsNull() {
            addCriterion("jfly is null");
            return (Criteria) this;
        }

        public Criteria andJflyIsNotNull() {
            addCriterion("jfly is not null");
            return (Criteria) this;
        }

        public Criteria andJflyEqualTo(String value) {
            addCriterion("jfly =", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyNotEqualTo(String value) {
            addCriterion("jfly <>", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyGreaterThan(String value) {
            addCriterion("jfly >", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyGreaterThanOrEqualTo(String value) {
            addCriterion("jfly >=", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyLessThan(String value) {
            addCriterion("jfly <", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyLessThanOrEqualTo(String value) {
            addCriterion("jfly <=", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyLike(String value) {
            addCriterion("jfly like", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyNotLike(String value) {
            addCriterion("jfly not like", value, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyIn(List<String> values) {
            addCriterion("jfly in", values, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyNotIn(List<String> values) {
            addCriterion("jfly not in", values, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyBetween(String value1, String value2) {
            addCriterion("jfly between", value1, value2, "jfly");
            return (Criteria) this;
        }

        public Criteria andJflyNotBetween(String value1, String value2) {
            addCriterion("jfly not between", value1, value2, "jfly");
            return (Criteria) this;
        }

        public Criteria andCgjlbIsNull() {
            addCriterion("cgjlb is null");
            return (Criteria) this;
        }

        public Criteria andCgjlbIsNotNull() {
            addCriterion("cgjlb is not null");
            return (Criteria) this;
        }

        public Criteria andCgjlbEqualTo(String value) {
            addCriterion("cgjlb =", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbNotEqualTo(String value) {
            addCriterion("cgjlb <>", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbGreaterThan(String value) {
            addCriterion("cgjlb >", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbGreaterThanOrEqualTo(String value) {
            addCriterion("cgjlb >=", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbLessThan(String value) {
            addCriterion("cgjlb <", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbLessThanOrEqualTo(String value) {
            addCriterion("cgjlb <=", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbLike(String value) {
            addCriterion("cgjlb like", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbNotLike(String value) {
            addCriterion("cgjlb not like", value, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbIn(List<String> values) {
            addCriterion("cgjlb in", values, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbNotIn(List<String> values) {
            addCriterion("cgjlb not in", values, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbBetween(String value1, String value2) {
            addCriterion("cgjlb between", value1, value2, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjlbNotBetween(String value1, String value2) {
            addCriterion("cgjlb not between", value1, value2, "cgjlb");
            return (Criteria) this;
        }

        public Criteria andCgjfsIsNull() {
            addCriterion("cgjfs is null");
            return (Criteria) this;
        }

        public Criteria andCgjfsIsNotNull() {
            addCriterion("cgjfs is not null");
            return (Criteria) this;
        }

        public Criteria andCgjfsEqualTo(String value) {
            addCriterion("cgjfs =", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsNotEqualTo(String value) {
            addCriterion("cgjfs <>", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsGreaterThan(String value) {
            addCriterion("cgjfs >", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsGreaterThanOrEqualTo(String value) {
            addCriterion("cgjfs >=", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsLessThan(String value) {
            addCriterion("cgjfs <", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsLessThanOrEqualTo(String value) {
            addCriterion("cgjfs <=", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsLike(String value) {
            addCriterion("cgjfs like", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsNotLike(String value) {
            addCriterion("cgjfs not like", value, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsIn(List<String> values) {
            addCriterion("cgjfs in", values, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsNotIn(List<String> values) {
            addCriterion("cgjfs not in", values, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsBetween(String value1, String value2) {
            addCriterion("cgjfs between", value1, value2, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andCgjfsNotBetween(String value1, String value2) {
            addCriterion("cgjfs not between", value1, value2, "cgjfs");
            return (Criteria) this;
        }

        public Criteria andYqdwIsNull() {
            addCriterion("yqdw is null");
            return (Criteria) this;
        }

        public Criteria andYqdwIsNotNull() {
            addCriterion("yqdw is not null");
            return (Criteria) this;
        }

        public Criteria andYqdwEqualTo(String value) {
            addCriterion("yqdw =", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwNotEqualTo(String value) {
            addCriterion("yqdw <>", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwGreaterThan(String value) {
            addCriterion("yqdw >", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwGreaterThanOrEqualTo(String value) {
            addCriterion("yqdw >=", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwLessThan(String value) {
            addCriterion("yqdw <", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwLessThanOrEqualTo(String value) {
            addCriterion("yqdw <=", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwLike(String value) {
            addCriterion("yqdw like", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwNotLike(String value) {
            addCriterion("yqdw not like", value, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwIn(List<String> values) {
            addCriterion("yqdw in", values, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwNotIn(List<String> values) {
            addCriterion("yqdw not in", values, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwBetween(String value1, String value2) {
            addCriterion("yqdw between", value1, value2, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwNotBetween(String value1, String value2) {
            addCriterion("yqdw not between", value1, value2, "yqdw");
            return (Criteria) this;
        }

        public Criteria andYqdwdzIsNull() {
            addCriterion("yqdwdz is null");
            return (Criteria) this;
        }

        public Criteria andYqdwdzIsNotNull() {
            addCriterion("yqdwdz is not null");
            return (Criteria) this;
        }

        public Criteria andYqdwdzEqualTo(String value) {
            addCriterion("yqdwdz =", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzNotEqualTo(String value) {
            addCriterion("yqdwdz <>", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzGreaterThan(String value) {
            addCriterion("yqdwdz >", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzGreaterThanOrEqualTo(String value) {
            addCriterion("yqdwdz >=", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzLessThan(String value) {
            addCriterion("yqdwdz <", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzLessThanOrEqualTo(String value) {
            addCriterion("yqdwdz <=", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzLike(String value) {
            addCriterion("yqdwdz like", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzNotLike(String value) {
            addCriterion("yqdwdz not like", value, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzIn(List<String> values) {
            addCriterion("yqdwdz in", values, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzNotIn(List<String> values) {
            addCriterion("yqdwdz not in", values, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzBetween(String value1, String value2) {
            addCriterion("yqdwdz between", value1, value2, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqdwdzNotBetween(String value1, String value2) {
            addCriterion("yqdwdz not between", value1, value2, "yqdwdz");
            return (Criteria) this;
        }

        public Criteria andYqrIsNull() {
            addCriterion("yqr is null");
            return (Criteria) this;
        }

        public Criteria andYqrIsNotNull() {
            addCriterion("yqr is not null");
            return (Criteria) this;
        }

        public Criteria andYqrEqualTo(String value) {
            addCriterion("yqr =", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrNotEqualTo(String value) {
            addCriterion("yqr <>", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrGreaterThan(String value) {
            addCriterion("yqr >", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrGreaterThanOrEqualTo(String value) {
            addCriterion("yqr >=", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrLessThan(String value) {
            addCriterion("yqr <", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrLessThanOrEqualTo(String value) {
            addCriterion("yqr <=", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrLike(String value) {
            addCriterion("yqr like", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrNotLike(String value) {
            addCriterion("yqr not like", value, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrIn(List<String> values) {
            addCriterion("yqr in", values, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrNotIn(List<String> values) {
            addCriterion("yqr not in", values, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrBetween(String value1, String value2) {
            addCriterion("yqr between", value1, value2, "yqr");
            return (Criteria) this;
        }

        public Criteria andYqrNotBetween(String value1, String value2) {
            addCriterion("yqr not between", value1, value2, "yqr");
            return (Criteria) this;
        }

        public Criteria andSqrzcIsNull() {
            addCriterion("sqrzc is null");
            return (Criteria) this;
        }

        public Criteria andSqrzcIsNotNull() {
            addCriterion("sqrzc is not null");
            return (Criteria) this;
        }

        public Criteria andSqrzcEqualTo(String value) {
            addCriterion("sqrzc =", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcNotEqualTo(String value) {
            addCriterion("sqrzc <>", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcGreaterThan(String value) {
            addCriterion("sqrzc >", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcGreaterThanOrEqualTo(String value) {
            addCriterion("sqrzc >=", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcLessThan(String value) {
            addCriterion("sqrzc <", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcLessThanOrEqualTo(String value) {
            addCriterion("sqrzc <=", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcLike(String value) {
            addCriterion("sqrzc like", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcNotLike(String value) {
            addCriterion("sqrzc not like", value, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcIn(List<String> values) {
            addCriterion("sqrzc in", values, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcNotIn(List<String> values) {
            addCriterion("sqrzc not in", values, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcBetween(String value1, String value2) {
            addCriterion("sqrzc between", value1, value2, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrzcNotBetween(String value1, String value2) {
            addCriterion("sqrzc not between", value1, value2, "sqrzc");
            return (Criteria) this;
        }

        public Criteria andSqrsjhIsNull() {
            addCriterion("sqrsjh is null");
            return (Criteria) this;
        }

        public Criteria andSqrsjhIsNotNull() {
            addCriterion("sqrsjh is not null");
            return (Criteria) this;
        }

        public Criteria andSqrsjhEqualTo(String value) {
            addCriterion("sqrsjh =", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhNotEqualTo(String value) {
            addCriterion("sqrsjh <>", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhGreaterThan(String value) {
            addCriterion("sqrsjh >", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhGreaterThanOrEqualTo(String value) {
            addCriterion("sqrsjh >=", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhLessThan(String value) {
            addCriterion("sqrsjh <", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhLessThanOrEqualTo(String value) {
            addCriterion("sqrsjh <=", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhLike(String value) {
            addCriterion("sqrsjh like", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhNotLike(String value) {
            addCriterion("sqrsjh not like", value, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhIn(List<String> values) {
            addCriterion("sqrsjh in", values, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhNotIn(List<String> values) {
            addCriterion("sqrsjh not in", values, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhBetween(String value1, String value2) {
            addCriterion("sqrsjh between", value1, value2, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqrsjhNotBetween(String value1, String value2) {
            addCriterion("sqrsjh not between", value1, value2, "sqrsjh");
            return (Criteria) this;
        }

        public Criteria andSqryxIsNull() {
            addCriterion("sqryx is null");
            return (Criteria) this;
        }

        public Criteria andSqryxIsNotNull() {
            addCriterion("sqryx is not null");
            return (Criteria) this;
        }

        public Criteria andSqryxEqualTo(String value) {
            addCriterion("sqryx =", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxNotEqualTo(String value) {
            addCriterion("sqryx <>", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxGreaterThan(String value) {
            addCriterion("sqryx >", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxGreaterThanOrEqualTo(String value) {
            addCriterion("sqryx >=", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxLessThan(String value) {
            addCriterion("sqryx <", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxLessThanOrEqualTo(String value) {
            addCriterion("sqryx <=", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxLike(String value) {
            addCriterion("sqryx like", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxNotLike(String value) {
            addCriterion("sqryx not like", value, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxIn(List<String> values) {
            addCriterion("sqryx in", values, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxNotIn(List<String> values) {
            addCriterion("sqryx not in", values, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxBetween(String value1, String value2) {
            addCriterion("sqryx between", value1, value2, "sqryx");
            return (Criteria) this;
        }

        public Criteria andSqryxNotBetween(String value1, String value2) {
            addCriterion("sqryx not between", value1, value2, "sqryx");
            return (Criteria) this;
        }

        public Criteria andYjcfsjIsNull() {
            addCriterion("yjcfsj is null");
            return (Criteria) this;
        }

        public Criteria andYjcfsjIsNotNull() {
            addCriterion("yjcfsj is not null");
            return (Criteria) this;
        }

        public Criteria andYjcfsjEqualTo(Date value) {
            addCriterionForJDBCDate("yjcfsj =", value, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("yjcfsj <>", value, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjGreaterThan(Date value) {
            addCriterionForJDBCDate("yjcfsj >", value, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yjcfsj >=", value, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjLessThan(Date value) {
            addCriterionForJDBCDate("yjcfsj <", value, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yjcfsj <=", value, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjIn(List<Date> values) {
            addCriterionForJDBCDate("yjcfsj in", values, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("yjcfsj not in", values, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yjcfsj between", value1, value2, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYjcfsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yjcfsj not between", value1, value2, "yjcfsj");
            return (Criteria) this;
        }

        public Criteria andYgsjIsNull() {
            addCriterion("ygsj is null");
            return (Criteria) this;
        }

        public Criteria andYgsjIsNotNull() {
            addCriterion("ygsj is not null");
            return (Criteria) this;
        }

        public Criteria andYgsjEqualTo(Date value) {
            addCriterionForJDBCDate("ygsj =", value, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("ygsj <>", value, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjGreaterThan(Date value) {
            addCriterionForJDBCDate("ygsj >", value, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ygsj >=", value, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjLessThan(Date value) {
            addCriterionForJDBCDate("ygsj <", value, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ygsj <=", value, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjIn(List<Date> values) {
            addCriterionForJDBCDate("ygsj in", values, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("ygsj not in", values, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ygsj between", value1, value2, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYgsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ygsj not between", value1, value2, "ygsj");
            return (Criteria) this;
        }

        public Criteria andYjtltsIsNull() {
            addCriterion("yjtlts is null");
            return (Criteria) this;
        }

        public Criteria andYjtltsIsNotNull() {
            addCriterion("yjtlts is not null");
            return (Criteria) this;
        }

        public Criteria andYjtltsEqualTo(Integer value) {
            addCriterion("yjtlts =", value, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsNotEqualTo(Integer value) {
            addCriterion("yjtlts <>", value, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsGreaterThan(Integer value) {
            addCriterion("yjtlts >", value, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsGreaterThanOrEqualTo(Integer value) {
            addCriterion("yjtlts >=", value, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsLessThan(Integer value) {
            addCriterion("yjtlts <", value, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsLessThanOrEqualTo(Integer value) {
            addCriterion("yjtlts <=", value, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsIn(List<Integer> values) {
            addCriterion("yjtlts in", values, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsNotIn(List<Integer> values) {
            addCriterion("yjtlts not in", values, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsBetween(Integer value1, Integer value2) {
            addCriterion("yjtlts between", value1, value2, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andYjtltsNotBetween(Integer value1, Integer value2) {
            addCriterion("yjtlts not between", value1, value2, "yjtlts");
            return (Criteria) this;
        }

        public Criteria andSjcfsjIsNull() {
            addCriterion("sjcfsj is null");
            return (Criteria) this;
        }

        public Criteria andSjcfsjIsNotNull() {
            addCriterion("sjcfsj is not null");
            return (Criteria) this;
        }

        public Criteria andSjcfsjEqualTo(Date value) {
            addCriterionForJDBCDate("sjcfsj =", value, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("sjcfsj <>", value, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjGreaterThan(Date value) {
            addCriterionForJDBCDate("sjcfsj >", value, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sjcfsj >=", value, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjLessThan(Date value) {
            addCriterionForJDBCDate("sjcfsj <", value, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sjcfsj <=", value, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjIn(List<Date> values) {
            addCriterionForJDBCDate("sjcfsj in", values, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("sjcfsj not in", values, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sjcfsj between", value1, value2, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSjcfsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sjcfsj not between", value1, value2, "sjcfsj");
            return (Criteria) this;
        }

        public Criteria andSgsjIsNull() {
            addCriterion("sgsj is null");
            return (Criteria) this;
        }

        public Criteria andSgsjIsNotNull() {
            addCriterion("sgsj is not null");
            return (Criteria) this;
        }

        public Criteria andSgsjEqualTo(Date value) {
            addCriterionForJDBCDate("sgsj =", value, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjNotEqualTo(Date value) {
            addCriterionForJDBCDate("sgsj <>", value, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjGreaterThan(Date value) {
            addCriterionForJDBCDate("sgsj >", value, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sgsj >=", value, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjLessThan(Date value) {
            addCriterionForJDBCDate("sgsj <", value, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("sgsj <=", value, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjIn(List<Date> values) {
            addCriterionForJDBCDate("sgsj in", values, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjNotIn(List<Date> values) {
            addCriterionForJDBCDate("sgsj not in", values, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sgsj between", value1, value2, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSgsjNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("sgsj not between", value1, value2, "sgsj");
            return (Criteria) this;
        }

        public Criteria andSjtltsIsNull() {
            addCriterion("sjtlts is null");
            return (Criteria) this;
        }

        public Criteria andSjtltsIsNotNull() {
            addCriterion("sjtlts is not null");
            return (Criteria) this;
        }

        public Criteria andSjtltsEqualTo(Integer value) {
            addCriterion("sjtlts =", value, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsNotEqualTo(Integer value) {
            addCriterion("sjtlts <>", value, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsGreaterThan(Integer value) {
            addCriterion("sjtlts >", value, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsGreaterThanOrEqualTo(Integer value) {
            addCriterion("sjtlts >=", value, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsLessThan(Integer value) {
            addCriterion("sjtlts <", value, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsLessThanOrEqualTo(Integer value) {
            addCriterion("sjtlts <=", value, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsIn(List<Integer> values) {
            addCriterion("sjtlts in", values, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsNotIn(List<Integer> values) {
            addCriterion("sjtlts not in", values, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsBetween(Integer value1, Integer value2) {
            addCriterion("sjtlts between", value1, value2, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andSjtltsNotBetween(Integer value1, Integer value2) {
            addCriterion("sjtlts not between", value1, value2, "sjtlts");
            return (Criteria) this;
        }

        public Criteria andYq1sIsNull() {
            addCriterion("yq1s is null");
            return (Criteria) this;
        }

        public Criteria andYq1sIsNotNull() {
            addCriterion("yq1s is not null");
            return (Criteria) this;
        }

        public Criteria andYq1sEqualTo(Date value) {
            addCriterionForJDBCDate("yq1s =", value, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sNotEqualTo(Date value) {
            addCriterionForJDBCDate("yq1s <>", value, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sGreaterThan(Date value) {
            addCriterionForJDBCDate("yq1s >", value, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq1s >=", value, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sLessThan(Date value) {
            addCriterionForJDBCDate("yq1s <", value, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq1s <=", value, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sIn(List<Date> values) {
            addCriterionForJDBCDate("yq1s in", values, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sNotIn(List<Date> values) {
            addCriterionForJDBCDate("yq1s not in", values, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq1s between", value1, value2, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1sNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq1s not between", value1, value2, "yq1s");
            return (Criteria) this;
        }

        public Criteria andYq1zIsNull() {
            addCriterion("yq1z is null");
            return (Criteria) this;
        }

        public Criteria andYq1zIsNotNull() {
            addCriterion("yq1z is not null");
            return (Criteria) this;
        }

        public Criteria andYq1zEqualTo(Date value) {
            addCriterionForJDBCDate("yq1z =", value, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zNotEqualTo(Date value) {
            addCriterionForJDBCDate("yq1z <>", value, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zGreaterThan(Date value) {
            addCriterionForJDBCDate("yq1z >", value, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq1z >=", value, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zLessThan(Date value) {
            addCriterionForJDBCDate("yq1z <", value, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq1z <=", value, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zIn(List<Date> values) {
            addCriterionForJDBCDate("yq1z in", values, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zNotIn(List<Date> values) {
            addCriterionForJDBCDate("yq1z not in", values, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq1z between", value1, value2, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq1zNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq1z not between", value1, value2, "yq1z");
            return (Criteria) this;
        }

        public Criteria andYq2sIsNull() {
            addCriterion("yq2s is null");
            return (Criteria) this;
        }

        public Criteria andYq2sIsNotNull() {
            addCriterion("yq2s is not null");
            return (Criteria) this;
        }

        public Criteria andYq2sEqualTo(Date value) {
            addCriterionForJDBCDate("yq2s =", value, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sNotEqualTo(Date value) {
            addCriterionForJDBCDate("yq2s <>", value, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sGreaterThan(Date value) {
            addCriterionForJDBCDate("yq2s >", value, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq2s >=", value, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sLessThan(Date value) {
            addCriterionForJDBCDate("yq2s <", value, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq2s <=", value, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sIn(List<Date> values) {
            addCriterionForJDBCDate("yq2s in", values, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sNotIn(List<Date> values) {
            addCriterionForJDBCDate("yq2s not in", values, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq2s between", value1, value2, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2sNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq2s not between", value1, value2, "yq2s");
            return (Criteria) this;
        }

        public Criteria andYq2zIsNull() {
            addCriterion("yq2z is null");
            return (Criteria) this;
        }

        public Criteria andYq2zIsNotNull() {
            addCriterion("yq2z is not null");
            return (Criteria) this;
        }

        public Criteria andYq2zEqualTo(Date value) {
            addCriterionForJDBCDate("yq2z =", value, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zNotEqualTo(Date value) {
            addCriterionForJDBCDate("yq2z <>", value, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zGreaterThan(Date value) {
            addCriterionForJDBCDate("yq2z >", value, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq2z >=", value, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zLessThan(Date value) {
            addCriterionForJDBCDate("yq2z <", value, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("yq2z <=", value, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zIn(List<Date> values) {
            addCriterionForJDBCDate("yq2z in", values, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zNotIn(List<Date> values) {
            addCriterionForJDBCDate("yq2z not in", values, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq2z between", value1, value2, "yq2z");
            return (Criteria) this;
        }

        public Criteria andYq2zNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("yq2z not between", value1, value2, "yq2z");
            return (Criteria) this;
        }

        public Criteria andPzwhIsNull() {
            addCriterion("pzwh is null");
            return (Criteria) this;
        }

        public Criteria andPzwhIsNotNull() {
            addCriterion("pzwh is not null");
            return (Criteria) this;
        }

        public Criteria andPzwhEqualTo(Date value) {
            addCriterionForJDBCDate("pzwh =", value, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhNotEqualTo(Date value) {
            addCriterionForJDBCDate("pzwh <>", value, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhGreaterThan(Date value) {
            addCriterionForJDBCDate("pzwh >", value, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pzwh >=", value, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhLessThan(Date value) {
            addCriterionForJDBCDate("pzwh <", value, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pzwh <=", value, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhIn(List<Date> values) {
            addCriterionForJDBCDate("pzwh in", values, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhNotIn(List<Date> values) {
            addCriterionForJDBCDate("pzwh not in", values, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pzwh between", value1, value2, "pzwh");
            return (Criteria) this;
        }

        public Criteria andPzwhNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pzwh not between", value1, value2, "pzwh");
            return (Criteria) this;
        }

        public Criteria andCgjztIsNull() {
            addCriterion("cgjzt is null");
            return (Criteria) this;
        }

        public Criteria andCgjztIsNotNull() {
            addCriterion("cgjzt is not null");
            return (Criteria) this;
        }

        public Criteria andCgjztEqualTo(String value) {
            addCriterion("cgjzt =", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztNotEqualTo(String value) {
            addCriterion("cgjzt <>", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztGreaterThan(String value) {
            addCriterion("cgjzt >", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztGreaterThanOrEqualTo(String value) {
            addCriterion("cgjzt >=", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztLessThan(String value) {
            addCriterion("cgjzt <", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztLessThanOrEqualTo(String value) {
            addCriterion("cgjzt <=", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztLike(String value) {
            addCriterion("cgjzt like", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztNotLike(String value) {
            addCriterion("cgjzt not like", value, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztIn(List<String> values) {
            addCriterion("cgjzt in", values, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztNotIn(List<String> values) {
            addCriterion("cgjzt not in", values, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztBetween(String value1, String value2) {
            addCriterion("cgjzt between", value1, value2, "cgjzt");
            return (Criteria) this;
        }

        public Criteria andCgjztNotBetween(String value1, String value2) {
            addCriterion("cgjzt not between", value1, value2, "cgjzt");
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

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
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

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Byte value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Byte value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Byte value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Byte value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Byte value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Byte value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Byte> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Byte> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Byte value1, Byte value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Byte value1, Byte value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNull() {
            addCriterion("party_id is null");
            return (Criteria) this;
        }

        public Criteria andPartyIdIsNotNull() {
            addCriterion("party_id is not null");
            return (Criteria) this;
        }

        public Criteria andPartyIdEqualTo(Integer value) {
            addCriterion("party_id =", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotEqualTo(Integer value) {
            addCriterion("party_id <>", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThan(Integer value) {
            addCriterion("party_id >", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("party_id >=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThan(Integer value) {
            addCriterion("party_id <", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdLessThanOrEqualTo(Integer value) {
            addCriterion("party_id <=", value, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdIn(List<Integer> values) {
            addCriterion("party_id in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotIn(List<Integer> values) {
            addCriterion("party_id not in", values, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdBetween(Integer value1, Integer value2) {
            addCriterion("party_id between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andPartyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("party_id not between", value1, value2, "partyId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(Integer value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(Integer value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(Integer value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(Integer value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(Integer value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<Integer> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<Integer> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(Integer value1, Integer value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
        public Criteria addPermits(List<Integer> partyIdList, List<Integer> branchIdList) {

            Subject subject = SecurityUtils.getSubject();
            if(subject.hasRole(RoleConstants.ROLE_ADMIN)
                    || subject.hasRole(RoleConstants.ROLE_ODADMIN))
                return this;

            if(partyIdList==null) partyIdList = new ArrayList<>();
            if(branchIdList==null) branchIdList = new ArrayList<>();

            if(!partyIdList.isEmpty() && !branchIdList.isEmpty())
                addCriterion("(party_id in(" + StringUtils.join(partyIdList, ",") + ") OR branch_id in(" + StringUtils.join(branchIdList, ",") + "))");
            if(partyIdList.isEmpty() && !branchIdList.isEmpty())
                andBranchIdIn(branchIdList);
            if(branchIdList.isEmpty() && !partyIdList.isEmpty())
                andPartyIdIn(partyIdList);
            if(branchIdList.isEmpty() && partyIdList.isEmpty())
                andIdIsNull();
            return this;
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