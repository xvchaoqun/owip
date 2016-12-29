package domain.ext;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExtJzgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExtJzgExample() {
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

        public Criteria andDwdmIsNull() {
            addCriterion("dwdm is null");
            return (Criteria) this;
        }

        public Criteria andDwdmIsNotNull() {
            addCriterion("dwdm is not null");
            return (Criteria) this;
        }

        public Criteria andDwdmEqualTo(String value) {
            addCriterion("dwdm =", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmNotEqualTo(String value) {
            addCriterion("dwdm <>", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmGreaterThan(String value) {
            addCriterion("dwdm >", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmGreaterThanOrEqualTo(String value) {
            addCriterion("dwdm >=", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmLessThan(String value) {
            addCriterion("dwdm <", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmLessThanOrEqualTo(String value) {
            addCriterion("dwdm <=", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmLike(String value) {
            addCriterion("dwdm like", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmNotLike(String value) {
            addCriterion("dwdm not like", value, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmIn(List<String> values) {
            addCriterion("dwdm in", values, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmNotIn(List<String> values) {
            addCriterion("dwdm not in", values, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmBetween(String value1, String value2) {
            addCriterion("dwdm between", value1, value2, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwdmNotBetween(String value1, String value2) {
            addCriterion("dwdm not between", value1, value2, "dwdm");
            return (Criteria) this;
        }

        public Criteria andDwmcIsNull() {
            addCriterion("dwmc is null");
            return (Criteria) this;
        }

        public Criteria andDwmcIsNotNull() {
            addCriterion("dwmc is not null");
            return (Criteria) this;
        }

        public Criteria andDwmcEqualTo(String value) {
            addCriterion("dwmc =", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotEqualTo(String value) {
            addCriterion("dwmc <>", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcGreaterThan(String value) {
            addCriterion("dwmc >", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcGreaterThanOrEqualTo(String value) {
            addCriterion("dwmc >=", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcLessThan(String value) {
            addCriterion("dwmc <", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcLessThanOrEqualTo(String value) {
            addCriterion("dwmc <=", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcLike(String value) {
            addCriterion("dwmc like", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotLike(String value) {
            addCriterion("dwmc not like", value, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcIn(List<String> values) {
            addCriterion("dwmc in", values, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotIn(List<String> values) {
            addCriterion("dwmc not in", values, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcBetween(String value1, String value2) {
            addCriterion("dwmc between", value1, value2, "dwmc");
            return (Criteria) this;
        }

        public Criteria andDwmcNotBetween(String value1, String value2) {
            addCriterion("dwmc not between", value1, value2, "dwmc");
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

        public Criteria andYjxkIsNull() {
            addCriterion("yjxk is null");
            return (Criteria) this;
        }

        public Criteria andYjxkIsNotNull() {
            addCriterion("yjxk is not null");
            return (Criteria) this;
        }

        public Criteria andYjxkEqualTo(String value) {
            addCriterion("yjxk =", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkNotEqualTo(String value) {
            addCriterion("yjxk <>", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkGreaterThan(String value) {
            addCriterion("yjxk >", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkGreaterThanOrEqualTo(String value) {
            addCriterion("yjxk >=", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkLessThan(String value) {
            addCriterion("yjxk <", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkLessThanOrEqualTo(String value) {
            addCriterion("yjxk <=", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkLike(String value) {
            addCriterion("yjxk like", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkNotLike(String value) {
            addCriterion("yjxk not like", value, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkIn(List<String> values) {
            addCriterion("yjxk in", values, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkNotIn(List<String> values) {
            addCriterion("yjxk not in", values, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkBetween(String value1, String value2) {
            addCriterion("yjxk between", value1, value2, "yjxk");
            return (Criteria) this;
        }

        public Criteria andYjxkNotBetween(String value1, String value2) {
            addCriterion("yjxk not between", value1, value2, "yjxk");
            return (Criteria) this;
        }

        public Criteria andLxrqIsNull() {
            addCriterion("lxrq is null");
            return (Criteria) this;
        }

        public Criteria andLxrqIsNotNull() {
            addCriterion("lxrq is not null");
            return (Criteria) this;
        }

        public Criteria andLxrqEqualTo(Date value) {
            addCriterionForJDBCDate("lxrq =", value, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqNotEqualTo(Date value) {
            addCriterionForJDBCDate("lxrq <>", value, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqGreaterThan(Date value) {
            addCriterionForJDBCDate("lxrq >", value, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lxrq >=", value, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqLessThan(Date value) {
            addCriterionForJDBCDate("lxrq <", value, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lxrq <=", value, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqIn(List<Date> values) {
            addCriterionForJDBCDate("lxrq in", values, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqNotIn(List<Date> values) {
            addCriterionForJDBCDate("lxrq not in", values, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lxrq between", value1, value2, "lxrq");
            return (Criteria) this;
        }

        public Criteria andLxrqNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lxrq not between", value1, value2, "lxrq");
            return (Criteria) this;
        }

        public Criteria andGwlbmIsNull() {
            addCriterion("gwlbm is null");
            return (Criteria) this;
        }

        public Criteria andGwlbmIsNotNull() {
            addCriterion("gwlbm is not null");
            return (Criteria) this;
        }

        public Criteria andGwlbmEqualTo(String value) {
            addCriterion("gwlbm =", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmNotEqualTo(String value) {
            addCriterion("gwlbm <>", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmGreaterThan(String value) {
            addCriterion("gwlbm >", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmGreaterThanOrEqualTo(String value) {
            addCriterion("gwlbm >=", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmLessThan(String value) {
            addCriterion("gwlbm <", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmLessThanOrEqualTo(String value) {
            addCriterion("gwlbm <=", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmLike(String value) {
            addCriterion("gwlbm like", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmNotLike(String value) {
            addCriterion("gwlbm not like", value, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmIn(List<String> values) {
            addCriterion("gwlbm in", values, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmNotIn(List<String> values) {
            addCriterion("gwlbm not in", values, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmBetween(String value1, String value2) {
            addCriterion("gwlbm between", value1, value2, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbmNotBetween(String value1, String value2) {
            addCriterion("gwlbm not between", value1, value2, "gwlbm");
            return (Criteria) this;
        }

        public Criteria andGwlbIsNull() {
            addCriterion("gwlb is null");
            return (Criteria) this;
        }

        public Criteria andGwlbIsNotNull() {
            addCriterion("gwlb is not null");
            return (Criteria) this;
        }

        public Criteria andGwlbEqualTo(String value) {
            addCriterion("gwlb =", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbNotEqualTo(String value) {
            addCriterion("gwlb <>", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbGreaterThan(String value) {
            addCriterion("gwlb >", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbGreaterThanOrEqualTo(String value) {
            addCriterion("gwlb >=", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbLessThan(String value) {
            addCriterion("gwlb <", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbLessThanOrEqualTo(String value) {
            addCriterion("gwlb <=", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbLike(String value) {
            addCriterion("gwlb like", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbNotLike(String value) {
            addCriterion("gwlb not like", value, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbIn(List<String> values) {
            addCriterion("gwlb in", values, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbNotIn(List<String> values) {
            addCriterion("gwlb not in", values, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbBetween(String value1, String value2) {
            addCriterion("gwlb between", value1, value2, "gwlb");
            return (Criteria) this;
        }

        public Criteria andGwlbNotBetween(String value1, String value2) {
            addCriterion("gwlb not between", value1, value2, "gwlb");
            return (Criteria) this;
        }

        public Criteria andZcmIsNull() {
            addCriterion("zcm is null");
            return (Criteria) this;
        }

        public Criteria andZcmIsNotNull() {
            addCriterion("zcm is not null");
            return (Criteria) this;
        }

        public Criteria andZcmEqualTo(String value) {
            addCriterion("zcm =", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmNotEqualTo(String value) {
            addCriterion("zcm <>", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmGreaterThan(String value) {
            addCriterion("zcm >", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmGreaterThanOrEqualTo(String value) {
            addCriterion("zcm >=", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmLessThan(String value) {
            addCriterion("zcm <", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmLessThanOrEqualTo(String value) {
            addCriterion("zcm <=", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmLike(String value) {
            addCriterion("zcm like", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmNotLike(String value) {
            addCriterion("zcm not like", value, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmIn(List<String> values) {
            addCriterion("zcm in", values, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmNotIn(List<String> values) {
            addCriterion("zcm not in", values, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmBetween(String value1, String value2) {
            addCriterion("zcm between", value1, value2, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcmNotBetween(String value1, String value2) {
            addCriterion("zcm not between", value1, value2, "zcm");
            return (Criteria) this;
        }

        public Criteria andZcIsNull() {
            addCriterion("zc is null");
            return (Criteria) this;
        }

        public Criteria andZcIsNotNull() {
            addCriterion("zc is not null");
            return (Criteria) this;
        }

        public Criteria andZcEqualTo(String value) {
            addCriterion("zc =", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcNotEqualTo(String value) {
            addCriterion("zc <>", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcGreaterThan(String value) {
            addCriterion("zc >", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcGreaterThanOrEqualTo(String value) {
            addCriterion("zc >=", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcLessThan(String value) {
            addCriterion("zc <", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcLessThanOrEqualTo(String value) {
            addCriterion("zc <=", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcLike(String value) {
            addCriterion("zc like", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcNotLike(String value) {
            addCriterion("zc not like", value, "zc");
            return (Criteria) this;
        }

        public Criteria andZcIn(List<String> values) {
            addCriterion("zc in", values, "zc");
            return (Criteria) this;
        }

        public Criteria andZcNotIn(List<String> values) {
            addCriterion("zc not in", values, "zc");
            return (Criteria) this;
        }

        public Criteria andZcBetween(String value1, String value2) {
            addCriterion("zc between", value1, value2, "zc");
            return (Criteria) this;
        }

        public Criteria andZcNotBetween(String value1, String value2) {
            addCriterion("zc not between", value1, value2, "zc");
            return (Criteria) this;
        }

        public Criteria andGwjbmIsNull() {
            addCriterion("gwjbm is null");
            return (Criteria) this;
        }

        public Criteria andGwjbmIsNotNull() {
            addCriterion("gwjbm is not null");
            return (Criteria) this;
        }

        public Criteria andGwjbmEqualTo(String value) {
            addCriterion("gwjbm =", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmNotEqualTo(String value) {
            addCriterion("gwjbm <>", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmGreaterThan(String value) {
            addCriterion("gwjbm >", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmGreaterThanOrEqualTo(String value) {
            addCriterion("gwjbm >=", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmLessThan(String value) {
            addCriterion("gwjbm <", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmLessThanOrEqualTo(String value) {
            addCriterion("gwjbm <=", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmLike(String value) {
            addCriterion("gwjbm like", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmNotLike(String value) {
            addCriterion("gwjbm not like", value, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmIn(List<String> values) {
            addCriterion("gwjbm in", values, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmNotIn(List<String> values) {
            addCriterion("gwjbm not in", values, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmBetween(String value1, String value2) {
            addCriterion("gwjbm between", value1, value2, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbmNotBetween(String value1, String value2) {
            addCriterion("gwjbm not between", value1, value2, "gwjbm");
            return (Criteria) this;
        }

        public Criteria andGwjbIsNull() {
            addCriterion("gwjb is null");
            return (Criteria) this;
        }

        public Criteria andGwjbIsNotNull() {
            addCriterion("gwjb is not null");
            return (Criteria) this;
        }

        public Criteria andGwjbEqualTo(String value) {
            addCriterion("gwjb =", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbNotEqualTo(String value) {
            addCriterion("gwjb <>", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbGreaterThan(String value) {
            addCriterion("gwjb >", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbGreaterThanOrEqualTo(String value) {
            addCriterion("gwjb >=", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbLessThan(String value) {
            addCriterion("gwjb <", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbLessThanOrEqualTo(String value) {
            addCriterion("gwjb <=", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbLike(String value) {
            addCriterion("gwjb like", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbNotLike(String value) {
            addCriterion("gwjb not like", value, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbIn(List<String> values) {
            addCriterion("gwjb in", values, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbNotIn(List<String> values) {
            addCriterion("gwjb not in", values, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbBetween(String value1, String value2) {
            addCriterion("gwjb between", value1, value2, "gwjb");
            return (Criteria) this;
        }

        public Criteria andGwjbNotBetween(String value1, String value2) {
            addCriterion("gwjb not between", value1, value2, "gwjb");
            return (Criteria) this;
        }

        public Criteria andRclxmIsNull() {
            addCriterion("rclxm is null");
            return (Criteria) this;
        }

        public Criteria andRclxmIsNotNull() {
            addCriterion("rclxm is not null");
            return (Criteria) this;
        }

        public Criteria andRclxmEqualTo(String value) {
            addCriterion("rclxm =", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmNotEqualTo(String value) {
            addCriterion("rclxm <>", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmGreaterThan(String value) {
            addCriterion("rclxm >", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmGreaterThanOrEqualTo(String value) {
            addCriterion("rclxm >=", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmLessThan(String value) {
            addCriterion("rclxm <", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmLessThanOrEqualTo(String value) {
            addCriterion("rclxm <=", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmLike(String value) {
            addCriterion("rclxm like", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmNotLike(String value) {
            addCriterion("rclxm not like", value, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmIn(List<String> values) {
            addCriterion("rclxm in", values, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmNotIn(List<String> values) {
            addCriterion("rclxm not in", values, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmBetween(String value1, String value2) {
            addCriterion("rclxm between", value1, value2, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxmNotBetween(String value1, String value2) {
            addCriterion("rclxm not between", value1, value2, "rclxm");
            return (Criteria) this;
        }

        public Criteria andRclxIsNull() {
            addCriterion("rclx is null");
            return (Criteria) this;
        }

        public Criteria andRclxIsNotNull() {
            addCriterion("rclx is not null");
            return (Criteria) this;
        }

        public Criteria andRclxEqualTo(String value) {
            addCriterion("rclx =", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxNotEqualTo(String value) {
            addCriterion("rclx <>", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxGreaterThan(String value) {
            addCriterion("rclx >", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxGreaterThanOrEqualTo(String value) {
            addCriterion("rclx >=", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxLessThan(String value) {
            addCriterion("rclx <", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxLessThanOrEqualTo(String value) {
            addCriterion("rclx <=", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxLike(String value) {
            addCriterion("rclx like", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxNotLike(String value) {
            addCriterion("rclx not like", value, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxIn(List<String> values) {
            addCriterion("rclx in", values, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxNotIn(List<String> values) {
            addCriterion("rclx not in", values, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxBetween(String value1, String value2) {
            addCriterion("rclx between", value1, value2, "rclx");
            return (Criteria) this;
        }

        public Criteria andRclxNotBetween(String value1, String value2) {
            addCriterion("rclx not between", value1, value2, "rclx");
            return (Criteria) this;
        }

        public Criteria andRcchmIsNull() {
            addCriterion("rcchm is null");
            return (Criteria) this;
        }

        public Criteria andRcchmIsNotNull() {
            addCriterion("rcchm is not null");
            return (Criteria) this;
        }

        public Criteria andRcchmEqualTo(String value) {
            addCriterion("rcchm =", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmNotEqualTo(String value) {
            addCriterion("rcchm <>", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmGreaterThan(String value) {
            addCriterion("rcchm >", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmGreaterThanOrEqualTo(String value) {
            addCriterion("rcchm >=", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmLessThan(String value) {
            addCriterion("rcchm <", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmLessThanOrEqualTo(String value) {
            addCriterion("rcchm <=", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmLike(String value) {
            addCriterion("rcchm like", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmNotLike(String value) {
            addCriterion("rcchm not like", value, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmIn(List<String> values) {
            addCriterion("rcchm in", values, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmNotIn(List<String> values) {
            addCriterion("rcchm not in", values, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmBetween(String value1, String value2) {
            addCriterion("rcchm between", value1, value2, "rcchm");
            return (Criteria) this;
        }

        public Criteria andRcchmNotBetween(String value1, String value2) {
            addCriterion("rcchm not between", value1, value2, "rcchm");
            return (Criteria) this;
        }

        public Criteria andXyjgmIsNull() {
            addCriterion("xyjgm is null");
            return (Criteria) this;
        }

        public Criteria andXyjgmIsNotNull() {
            addCriterion("xyjgm is not null");
            return (Criteria) this;
        }

        public Criteria andXyjgmEqualTo(String value) {
            addCriterion("xyjgm =", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmNotEqualTo(String value) {
            addCriterion("xyjgm <>", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmGreaterThan(String value) {
            addCriterion("xyjgm >", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmGreaterThanOrEqualTo(String value) {
            addCriterion("xyjgm >=", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmLessThan(String value) {
            addCriterion("xyjgm <", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmLessThanOrEqualTo(String value) {
            addCriterion("xyjgm <=", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmLike(String value) {
            addCriterion("xyjgm like", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmNotLike(String value) {
            addCriterion("xyjgm not like", value, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmIn(List<String> values) {
            addCriterion("xyjgm in", values, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmNotIn(List<String> values) {
            addCriterion("xyjgm not in", values, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmBetween(String value1, String value2) {
            addCriterion("xyjgm between", value1, value2, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgmNotBetween(String value1, String value2) {
            addCriterion("xyjgm not between", value1, value2, "xyjgm");
            return (Criteria) this;
        }

        public Criteria andXyjgIsNull() {
            addCriterion("xyjg is null");
            return (Criteria) this;
        }

        public Criteria andXyjgIsNotNull() {
            addCriterion("xyjg is not null");
            return (Criteria) this;
        }

        public Criteria andXyjgEqualTo(String value) {
            addCriterion("xyjg =", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgNotEqualTo(String value) {
            addCriterion("xyjg <>", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgGreaterThan(String value) {
            addCriterion("xyjg >", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgGreaterThanOrEqualTo(String value) {
            addCriterion("xyjg >=", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgLessThan(String value) {
            addCriterion("xyjg <", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgLessThanOrEqualTo(String value) {
            addCriterion("xyjg <=", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgLike(String value) {
            addCriterion("xyjg like", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgNotLike(String value) {
            addCriterion("xyjg not like", value, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgIn(List<String> values) {
            addCriterion("xyjg in", values, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgNotIn(List<String> values) {
            addCriterion("xyjg not in", values, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgBetween(String value1, String value2) {
            addCriterion("xyjg between", value1, value2, "xyjg");
            return (Criteria) this;
        }

        public Criteria andXyjgNotBetween(String value1, String value2) {
            addCriterion("xyjg not between", value1, value2, "xyjg");
            return (Criteria) this;
        }

        public Criteria andGjmIsNull() {
            addCriterion("gjm is null");
            return (Criteria) this;
        }

        public Criteria andGjmIsNotNull() {
            addCriterion("gjm is not null");
            return (Criteria) this;
        }

        public Criteria andGjmEqualTo(String value) {
            addCriterion("gjm =", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmNotEqualTo(String value) {
            addCriterion("gjm <>", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmGreaterThan(String value) {
            addCriterion("gjm >", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmGreaterThanOrEqualTo(String value) {
            addCriterion("gjm >=", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmLessThan(String value) {
            addCriterion("gjm <", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmLessThanOrEqualTo(String value) {
            addCriterion("gjm <=", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmLike(String value) {
            addCriterion("gjm like", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmNotLike(String value) {
            addCriterion("gjm not like", value, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmIn(List<String> values) {
            addCriterion("gjm in", values, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmNotIn(List<String> values) {
            addCriterion("gjm not in", values, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmBetween(String value1, String value2) {
            addCriterion("gjm between", value1, value2, "gjm");
            return (Criteria) this;
        }

        public Criteria andGjmNotBetween(String value1, String value2) {
            addCriterion("gjm not between", value1, value2, "gjm");
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

        public Criteria andZhxwmIsNull() {
            addCriterion("zhxwm is null");
            return (Criteria) this;
        }

        public Criteria andZhxwmIsNotNull() {
            addCriterion("zhxwm is not null");
            return (Criteria) this;
        }

        public Criteria andZhxwmEqualTo(String value) {
            addCriterion("zhxwm =", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmNotEqualTo(String value) {
            addCriterion("zhxwm <>", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmGreaterThan(String value) {
            addCriterion("zhxwm >", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmGreaterThanOrEqualTo(String value) {
            addCriterion("zhxwm >=", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmLessThan(String value) {
            addCriterion("zhxwm <", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmLessThanOrEqualTo(String value) {
            addCriterion("zhxwm <=", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmLike(String value) {
            addCriterion("zhxwm like", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmNotLike(String value) {
            addCriterion("zhxwm not like", value, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmIn(List<String> values) {
            addCriterion("zhxwm in", values, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmNotIn(List<String> values) {
            addCriterion("zhxwm not in", values, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmBetween(String value1, String value2) {
            addCriterion("zhxwm between", value1, value2, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwmNotBetween(String value1, String value2) {
            addCriterion("zhxwm not between", value1, value2, "zhxwm");
            return (Criteria) this;
        }

        public Criteria andZhxwIsNull() {
            addCriterion("zhxw is null");
            return (Criteria) this;
        }

        public Criteria andZhxwIsNotNull() {
            addCriterion("zhxw is not null");
            return (Criteria) this;
        }

        public Criteria andZhxwEqualTo(String value) {
            addCriterion("zhxw =", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwNotEqualTo(String value) {
            addCriterion("zhxw <>", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwGreaterThan(String value) {
            addCriterion("zhxw >", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwGreaterThanOrEqualTo(String value) {
            addCriterion("zhxw >=", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwLessThan(String value) {
            addCriterion("zhxw <", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwLessThanOrEqualTo(String value) {
            addCriterion("zhxw <=", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwLike(String value) {
            addCriterion("zhxw like", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwNotLike(String value) {
            addCriterion("zhxw not like", value, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwIn(List<String> values) {
            addCriterion("zhxw in", values, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwNotIn(List<String> values) {
            addCriterion("zhxw not in", values, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwBetween(String value1, String value2) {
            addCriterion("zhxw between", value1, value2, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxwNotBetween(String value1, String value2) {
            addCriterion("zhxw not between", value1, value2, "zhxw");
            return (Criteria) this;
        }

        public Criteria andZhxlIsNull() {
            addCriterion("zhxl is null");
            return (Criteria) this;
        }

        public Criteria andZhxlIsNotNull() {
            addCriterion("zhxl is not null");
            return (Criteria) this;
        }

        public Criteria andZhxlEqualTo(String value) {
            addCriterion("zhxl =", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlNotEqualTo(String value) {
            addCriterion("zhxl <>", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlGreaterThan(String value) {
            addCriterion("zhxl >", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlGreaterThanOrEqualTo(String value) {
            addCriterion("zhxl >=", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlLessThan(String value) {
            addCriterion("zhxl <", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlLessThanOrEqualTo(String value) {
            addCriterion("zhxl <=", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlLike(String value) {
            addCriterion("zhxl like", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlNotLike(String value) {
            addCriterion("zhxl not like", value, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlIn(List<String> values) {
            addCriterion("zhxl in", values, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlNotIn(List<String> values) {
            addCriterion("zhxl not in", values, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlBetween(String value1, String value2) {
            addCriterion("zhxl between", value1, value2, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlNotBetween(String value1, String value2) {
            addCriterion("zhxl not between", value1, value2, "zhxl");
            return (Criteria) this;
        }

        public Criteria andZhxlmcIsNull() {
            addCriterion("zhxlmc is null");
            return (Criteria) this;
        }

        public Criteria andZhxlmcIsNotNull() {
            addCriterion("zhxlmc is not null");
            return (Criteria) this;
        }

        public Criteria andZhxlmcEqualTo(String value) {
            addCriterion("zhxlmc =", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcNotEqualTo(String value) {
            addCriterion("zhxlmc <>", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcGreaterThan(String value) {
            addCriterion("zhxlmc >", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcGreaterThanOrEqualTo(String value) {
            addCriterion("zhxlmc >=", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcLessThan(String value) {
            addCriterion("zhxlmc <", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcLessThanOrEqualTo(String value) {
            addCriterion("zhxlmc <=", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcLike(String value) {
            addCriterion("zhxlmc like", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcNotLike(String value) {
            addCriterion("zhxlmc not like", value, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcIn(List<String> values) {
            addCriterion("zhxlmc in", values, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcNotIn(List<String> values) {
            addCriterion("zhxlmc not in", values, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcBetween(String value1, String value2) {
            addCriterion("zhxlmc between", value1, value2, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andZhxlmcNotBetween(String value1, String value2) {
            addCriterion("zhxlmc not between", value1, value2, "zhxlmc");
            return (Criteria) this;
        }

        public Criteria andXlbyxxIsNull() {
            addCriterion("xlbyxx is null");
            return (Criteria) this;
        }

        public Criteria andXlbyxxIsNotNull() {
            addCriterion("xlbyxx is not null");
            return (Criteria) this;
        }

        public Criteria andXlbyxxEqualTo(String value) {
            addCriterion("xlbyxx =", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxNotEqualTo(String value) {
            addCriterion("xlbyxx <>", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxGreaterThan(String value) {
            addCriterion("xlbyxx >", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxGreaterThanOrEqualTo(String value) {
            addCriterion("xlbyxx >=", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxLessThan(String value) {
            addCriterion("xlbyxx <", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxLessThanOrEqualTo(String value) {
            addCriterion("xlbyxx <=", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxLike(String value) {
            addCriterion("xlbyxx like", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxNotLike(String value) {
            addCriterion("xlbyxx not like", value, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxIn(List<String> values) {
            addCriterion("xlbyxx in", values, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxNotIn(List<String> values) {
            addCriterion("xlbyxx not in", values, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxBetween(String value1, String value2) {
            addCriterion("xlbyxx between", value1, value2, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXlbyxxNotBetween(String value1, String value2) {
            addCriterion("xlbyxx not between", value1, value2, "xlbyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxIsNull() {
            addCriterion("xwsyxx is null");
            return (Criteria) this;
        }

        public Criteria andXwsyxxIsNotNull() {
            addCriterion("xwsyxx is not null");
            return (Criteria) this;
        }

        public Criteria andXwsyxxEqualTo(String value) {
            addCriterion("xwsyxx =", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxNotEqualTo(String value) {
            addCriterion("xwsyxx <>", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxGreaterThan(String value) {
            addCriterion("xwsyxx >", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxGreaterThanOrEqualTo(String value) {
            addCriterion("xwsyxx >=", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxLessThan(String value) {
            addCriterion("xwsyxx <", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxLessThanOrEqualTo(String value) {
            addCriterion("xwsyxx <=", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxLike(String value) {
            addCriterion("xwsyxx like", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxNotLike(String value) {
            addCriterion("xwsyxx not like", value, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxIn(List<String> values) {
            addCriterion("xwsyxx in", values, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxNotIn(List<String> values) {
            addCriterion("xwsyxx not in", values, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxBetween(String value1, String value2) {
            addCriterion("xwsyxx between", value1, value2, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXwsyxxNotBetween(String value1, String value2) {
            addCriterion("xwsyxx not between", value1, value2, "xwsyxx");
            return (Criteria) this;
        }

        public Criteria andXzjbmIsNull() {
            addCriterion("xzjbm is null");
            return (Criteria) this;
        }

        public Criteria andXzjbmIsNotNull() {
            addCriterion("xzjbm is not null");
            return (Criteria) this;
        }

        public Criteria andXzjbmEqualTo(String value) {
            addCriterion("xzjbm =", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmNotEqualTo(String value) {
            addCriterion("xzjbm <>", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmGreaterThan(String value) {
            addCriterion("xzjbm >", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmGreaterThanOrEqualTo(String value) {
            addCriterion("xzjbm >=", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmLessThan(String value) {
            addCriterion("xzjbm <", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmLessThanOrEqualTo(String value) {
            addCriterion("xzjbm <=", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmLike(String value) {
            addCriterion("xzjbm like", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmNotLike(String value) {
            addCriterion("xzjbm not like", value, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmIn(List<String> values) {
            addCriterion("xzjbm in", values, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmNotIn(List<String> values) {
            addCriterion("xzjbm not in", values, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmBetween(String value1, String value2) {
            addCriterion("xzjbm between", value1, value2, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbmNotBetween(String value1, String value2) {
            addCriterion("xzjbm not between", value1, value2, "xzjbm");
            return (Criteria) this;
        }

        public Criteria andXzjbIsNull() {
            addCriterion("xzjb is null");
            return (Criteria) this;
        }

        public Criteria andXzjbIsNotNull() {
            addCriterion("xzjb is not null");
            return (Criteria) this;
        }

        public Criteria andXzjbEqualTo(String value) {
            addCriterion("xzjb =", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbNotEqualTo(String value) {
            addCriterion("xzjb <>", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbGreaterThan(String value) {
            addCriterion("xzjb >", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbGreaterThanOrEqualTo(String value) {
            addCriterion("xzjb >=", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbLessThan(String value) {
            addCriterion("xzjb <", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbLessThanOrEqualTo(String value) {
            addCriterion("xzjb <=", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbLike(String value) {
            addCriterion("xzjb like", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbNotLike(String value) {
            addCriterion("xzjb not like", value, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbIn(List<String> values) {
            addCriterion("xzjb in", values, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbNotIn(List<String> values) {
            addCriterion("xzjb not in", values, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbBetween(String value1, String value2) {
            addCriterion("xzjb between", value1, value2, "xzjb");
            return (Criteria) this;
        }

        public Criteria andXzjbNotBetween(String value1, String value2) {
            addCriterion("xzjb not between", value1, value2, "xzjb");
            return (Criteria) this;
        }

        public Criteria andSfzgmIsNull() {
            addCriterion("sfzgm is null");
            return (Criteria) this;
        }

        public Criteria andSfzgmIsNotNull() {
            addCriterion("sfzgm is not null");
            return (Criteria) this;
        }

        public Criteria andSfzgmEqualTo(String value) {
            addCriterion("sfzgm =", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmNotEqualTo(String value) {
            addCriterion("sfzgm <>", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmGreaterThan(String value) {
            addCriterion("sfzgm >", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmGreaterThanOrEqualTo(String value) {
            addCriterion("sfzgm >=", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmLessThan(String value) {
            addCriterion("sfzgm <", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmLessThanOrEqualTo(String value) {
            addCriterion("sfzgm <=", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmLike(String value) {
            addCriterion("sfzgm like", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmNotLike(String value) {
            addCriterion("sfzgm not like", value, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmIn(List<String> values) {
            addCriterion("sfzgm in", values, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmNotIn(List<String> values) {
            addCriterion("sfzgm not in", values, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmBetween(String value1, String value2) {
            addCriterion("sfzgm between", value1, value2, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgmNotBetween(String value1, String value2) {
            addCriterion("sfzgm not between", value1, value2, "sfzgm");
            return (Criteria) this;
        }

        public Criteria andSfzgIsNull() {
            addCriterion("sfzg is null");
            return (Criteria) this;
        }

        public Criteria andSfzgIsNotNull() {
            addCriterion("sfzg is not null");
            return (Criteria) this;
        }

        public Criteria andSfzgEqualTo(String value) {
            addCriterion("sfzg =", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgNotEqualTo(String value) {
            addCriterion("sfzg <>", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgGreaterThan(String value) {
            addCriterion("sfzg >", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgGreaterThanOrEqualTo(String value) {
            addCriterion("sfzg >=", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgLessThan(String value) {
            addCriterion("sfzg <", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgLessThanOrEqualTo(String value) {
            addCriterion("sfzg <=", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgLike(String value) {
            addCriterion("sfzg like", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgNotLike(String value) {
            addCriterion("sfzg not like", value, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgIn(List<String> values) {
            addCriterion("sfzg in", values, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgNotIn(List<String> values) {
            addCriterion("sfzg not in", values, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgBetween(String value1, String value2) {
            addCriterion("sfzg between", value1, value2, "sfzg");
            return (Criteria) this;
        }

        public Criteria andSfzgNotBetween(String value1, String value2) {
            addCriterion("sfzg not between", value1, value2, "sfzg");
            return (Criteria) this;
        }

        public Criteria andRyztmIsNull() {
            addCriterion("ryztm is null");
            return (Criteria) this;
        }

        public Criteria andRyztmIsNotNull() {
            addCriterion("ryztm is not null");
            return (Criteria) this;
        }

        public Criteria andRyztmEqualTo(String value) {
            addCriterion("ryztm =", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmNotEqualTo(String value) {
            addCriterion("ryztm <>", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmGreaterThan(String value) {
            addCriterion("ryztm >", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmGreaterThanOrEqualTo(String value) {
            addCriterion("ryztm >=", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmLessThan(String value) {
            addCriterion("ryztm <", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmLessThanOrEqualTo(String value) {
            addCriterion("ryztm <=", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmLike(String value) {
            addCriterion("ryztm like", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmNotLike(String value) {
            addCriterion("ryztm not like", value, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmIn(List<String> values) {
            addCriterion("ryztm in", values, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmNotIn(List<String> values) {
            addCriterion("ryztm not in", values, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmBetween(String value1, String value2) {
            addCriterion("ryztm between", value1, value2, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztmNotBetween(String value1, String value2) {
            addCriterion("ryztm not between", value1, value2, "ryztm");
            return (Criteria) this;
        }

        public Criteria andRyztIsNull() {
            addCriterion("ryzt is null");
            return (Criteria) this;
        }

        public Criteria andRyztIsNotNull() {
            addCriterion("ryzt is not null");
            return (Criteria) this;
        }

        public Criteria andRyztEqualTo(String value) {
            addCriterion("ryzt =", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztNotEqualTo(String value) {
            addCriterion("ryzt <>", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztGreaterThan(String value) {
            addCriterion("ryzt >", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztGreaterThanOrEqualTo(String value) {
            addCriterion("ryzt >=", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztLessThan(String value) {
            addCriterion("ryzt <", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztLessThanOrEqualTo(String value) {
            addCriterion("ryzt <=", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztLike(String value) {
            addCriterion("ryzt like", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztNotLike(String value) {
            addCriterion("ryzt not like", value, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztIn(List<String> values) {
            addCriterion("ryzt in", values, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztNotIn(List<String> values) {
            addCriterion("ryzt not in", values, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztBetween(String value1, String value2) {
            addCriterion("ryzt between", value1, value2, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRyztNotBetween(String value1, String value2) {
            addCriterion("ryzt not between", value1, value2, "ryzt");
            return (Criteria) this;
        }

        public Criteria andRylxmIsNull() {
            addCriterion("rylxm is null");
            return (Criteria) this;
        }

        public Criteria andRylxmIsNotNull() {
            addCriterion("rylxm is not null");
            return (Criteria) this;
        }

        public Criteria andRylxmEqualTo(String value) {
            addCriterion("rylxm =", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmNotEqualTo(String value) {
            addCriterion("rylxm <>", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmGreaterThan(String value) {
            addCriterion("rylxm >", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmGreaterThanOrEqualTo(String value) {
            addCriterion("rylxm >=", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmLessThan(String value) {
            addCriterion("rylxm <", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmLessThanOrEqualTo(String value) {
            addCriterion("rylxm <=", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmLike(String value) {
            addCriterion("rylxm like", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmNotLike(String value) {
            addCriterion("rylxm not like", value, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmIn(List<String> values) {
            addCriterion("rylxm in", values, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmNotIn(List<String> values) {
            addCriterion("rylxm not in", values, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmBetween(String value1, String value2) {
            addCriterion("rylxm between", value1, value2, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxmNotBetween(String value1, String value2) {
            addCriterion("rylxm not between", value1, value2, "rylxm");
            return (Criteria) this;
        }

        public Criteria andRylxIsNull() {
            addCriterion("rylx is null");
            return (Criteria) this;
        }

        public Criteria andRylxIsNotNull() {
            addCriterion("rylx is not null");
            return (Criteria) this;
        }

        public Criteria andRylxEqualTo(String value) {
            addCriterion("rylx =", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxNotEqualTo(String value) {
            addCriterion("rylx <>", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxGreaterThan(String value) {
            addCriterion("rylx >", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxGreaterThanOrEqualTo(String value) {
            addCriterion("rylx >=", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxLessThan(String value) {
            addCriterion("rylx <", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxLessThanOrEqualTo(String value) {
            addCriterion("rylx <=", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxLike(String value) {
            addCriterion("rylx like", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxNotLike(String value) {
            addCriterion("rylx not like", value, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxIn(List<String> values) {
            addCriterion("rylx in", values, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxNotIn(List<String> values) {
            addCriterion("rylx not in", values, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxBetween(String value1, String value2) {
            addCriterion("rylx between", value1, value2, "rylx");
            return (Criteria) this;
        }

        public Criteria andRylxNotBetween(String value1, String value2) {
            addCriterion("rylx not between", value1, value2, "rylx");
            return (Criteria) this;
        }

        public Criteria andBzlxmIsNull() {
            addCriterion("bzlxm is null");
            return (Criteria) this;
        }

        public Criteria andBzlxmIsNotNull() {
            addCriterion("bzlxm is not null");
            return (Criteria) this;
        }

        public Criteria andBzlxmEqualTo(String value) {
            addCriterion("bzlxm =", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmNotEqualTo(String value) {
            addCriterion("bzlxm <>", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmGreaterThan(String value) {
            addCriterion("bzlxm >", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmGreaterThanOrEqualTo(String value) {
            addCriterion("bzlxm >=", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmLessThan(String value) {
            addCriterion("bzlxm <", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmLessThanOrEqualTo(String value) {
            addCriterion("bzlxm <=", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmLike(String value) {
            addCriterion("bzlxm like", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmNotLike(String value) {
            addCriterion("bzlxm not like", value, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmIn(List<String> values) {
            addCriterion("bzlxm in", values, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmNotIn(List<String> values) {
            addCriterion("bzlxm not in", values, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmBetween(String value1, String value2) {
            addCriterion("bzlxm between", value1, value2, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxmNotBetween(String value1, String value2) {
            addCriterion("bzlxm not between", value1, value2, "bzlxm");
            return (Criteria) this;
        }

        public Criteria andBzlxIsNull() {
            addCriterion("bzlx is null");
            return (Criteria) this;
        }

        public Criteria andBzlxIsNotNull() {
            addCriterion("bzlx is not null");
            return (Criteria) this;
        }

        public Criteria andBzlxEqualTo(String value) {
            addCriterion("bzlx =", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxNotEqualTo(String value) {
            addCriterion("bzlx <>", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxGreaterThan(String value) {
            addCriterion("bzlx >", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxGreaterThanOrEqualTo(String value) {
            addCriterion("bzlx >=", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxLessThan(String value) {
            addCriterion("bzlx <", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxLessThanOrEqualTo(String value) {
            addCriterion("bzlx <=", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxLike(String value) {
            addCriterion("bzlx like", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxNotLike(String value) {
            addCriterion("bzlx not like", value, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxIn(List<String> values) {
            addCriterion("bzlx in", values, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxNotIn(List<String> values) {
            addCriterion("bzlx not in", values, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxBetween(String value1, String value2) {
            addCriterion("bzlx between", value1, value2, "bzlx");
            return (Criteria) this;
        }

        public Criteria andBzlxNotBetween(String value1, String value2) {
            addCriterion("bzlx not between", value1, value2, "bzlx");
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

        public Criteria andCsrqEqualTo(Date value) {
            addCriterionForJDBCDate("csrq =", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotEqualTo(Date value) {
            addCriterionForJDBCDate("csrq <>", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqGreaterThan(Date value) {
            addCriterionForJDBCDate("csrq >", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("csrq >=", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqLessThan(Date value) {
            addCriterionForJDBCDate("csrq <", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("csrq <=", value, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqIn(List<Date> values) {
            addCriterionForJDBCDate("csrq in", values, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotIn(List<Date> values) {
            addCriterionForJDBCDate("csrq not in", values, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("csrq between", value1, value2, "csrq");
            return (Criteria) this;
        }

        public Criteria andCsrqNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("csrq not between", value1, value2, "csrq");
            return (Criteria) this;
        }

        public Criteria andRszfmIsNull() {
            addCriterion("rszfm is null");
            return (Criteria) this;
        }

        public Criteria andRszfmIsNotNull() {
            addCriterion("rszfm is not null");
            return (Criteria) this;
        }

        public Criteria andRszfmEqualTo(String value) {
            addCriterion("rszfm =", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmNotEqualTo(String value) {
            addCriterion("rszfm <>", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmGreaterThan(String value) {
            addCriterion("rszfm >", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmGreaterThanOrEqualTo(String value) {
            addCriterion("rszfm >=", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmLessThan(String value) {
            addCriterion("rszfm <", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmLessThanOrEqualTo(String value) {
            addCriterion("rszfm <=", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmLike(String value) {
            addCriterion("rszfm like", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmNotLike(String value) {
            addCriterion("rszfm not like", value, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmIn(List<String> values) {
            addCriterion("rszfm in", values, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmNotIn(List<String> values) {
            addCriterion("rszfm not in", values, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmBetween(String value1, String value2) {
            addCriterion("rszfm between", value1, value2, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfmNotBetween(String value1, String value2) {
            addCriterion("rszfm not between", value1, value2, "rszfm");
            return (Criteria) this;
        }

        public Criteria andRszfIsNull() {
            addCriterion("rszf is null");
            return (Criteria) this;
        }

        public Criteria andRszfIsNotNull() {
            addCriterion("rszf is not null");
            return (Criteria) this;
        }

        public Criteria andRszfEqualTo(String value) {
            addCriterion("rszf =", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfNotEqualTo(String value) {
            addCriterion("rszf <>", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfGreaterThan(String value) {
            addCriterion("rszf >", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfGreaterThanOrEqualTo(String value) {
            addCriterion("rszf >=", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfLessThan(String value) {
            addCriterion("rszf <", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfLessThanOrEqualTo(String value) {
            addCriterion("rszf <=", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfLike(String value) {
            addCriterion("rszf like", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfNotLike(String value) {
            addCriterion("rszf not like", value, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfIn(List<String> values) {
            addCriterion("rszf in", values, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfNotIn(List<String> values) {
            addCriterion("rszf not in", values, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfBetween(String value1, String value2) {
            addCriterion("rszf between", value1, value2, "rszf");
            return (Criteria) this;
        }

        public Criteria andRszfNotBetween(String value1, String value2) {
            addCriterion("rszf not between", value1, value2, "rszf");
            return (Criteria) this;
        }

        public Criteria andSfnxzIsNull() {
            addCriterion("sfnxz is null");
            return (Criteria) this;
        }

        public Criteria andSfnxzIsNotNull() {
            addCriterion("sfnxz is not null");
            return (Criteria) this;
        }

        public Criteria andSfnxzEqualTo(String value) {
            addCriterion("sfnxz =", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzNotEqualTo(String value) {
            addCriterion("sfnxz <>", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzGreaterThan(String value) {
            addCriterion("sfnxz >", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzGreaterThanOrEqualTo(String value) {
            addCriterion("sfnxz >=", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzLessThan(String value) {
            addCriterion("sfnxz <", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzLessThanOrEqualTo(String value) {
            addCriterion("sfnxz <=", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzLike(String value) {
            addCriterion("sfnxz like", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzNotLike(String value) {
            addCriterion("sfnxz not like", value, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzIn(List<String> values) {
            addCriterion("sfnxz in", values, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzNotIn(List<String> values) {
            addCriterion("sfnxz not in", values, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzBetween(String value1, String value2) {
            addCriterion("sfnxz between", value1, value2, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfnxzNotBetween(String value1, String value2) {
            addCriterion("sfnxz not between", value1, value2, "sfnxz");
            return (Criteria) this;
        }

        public Criteria andSfzjlxIsNull() {
            addCriterion("sfzjlx is null");
            return (Criteria) this;
        }

        public Criteria andSfzjlxIsNotNull() {
            addCriterion("sfzjlx is not null");
            return (Criteria) this;
        }

        public Criteria andSfzjlxEqualTo(String value) {
            addCriterion("sfzjlx =", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxNotEqualTo(String value) {
            addCriterion("sfzjlx <>", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxGreaterThan(String value) {
            addCriterion("sfzjlx >", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxGreaterThanOrEqualTo(String value) {
            addCriterion("sfzjlx >=", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxLessThan(String value) {
            addCriterion("sfzjlx <", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxLessThanOrEqualTo(String value) {
            addCriterion("sfzjlx <=", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxLike(String value) {
            addCriterion("sfzjlx like", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxNotLike(String value) {
            addCriterion("sfzjlx not like", value, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxIn(List<String> values) {
            addCriterion("sfzjlx in", values, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxNotIn(List<String> values) {
            addCriterion("sfzjlx not in", values, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxBetween(String value1, String value2) {
            addCriterion("sfzjlx between", value1, value2, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andSfzjlxNotBetween(String value1, String value2) {
            addCriterion("sfzjlx not between", value1, value2, "sfzjlx");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
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

        public Criteria andYddhIsNull() {
            addCriterion("yddh is null");
            return (Criteria) this;
        }

        public Criteria andYddhIsNotNull() {
            addCriterion("yddh is not null");
            return (Criteria) this;
        }

        public Criteria andYddhEqualTo(String value) {
            addCriterion("yddh =", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhNotEqualTo(String value) {
            addCriterion("yddh <>", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhGreaterThan(String value) {
            addCriterion("yddh >", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhGreaterThanOrEqualTo(String value) {
            addCriterion("yddh >=", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhLessThan(String value) {
            addCriterion("yddh <", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhLessThanOrEqualTo(String value) {
            addCriterion("yddh <=", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhLike(String value) {
            addCriterion("yddh like", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhNotLike(String value) {
            addCriterion("yddh not like", value, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhIn(List<String> values) {
            addCriterion("yddh in", values, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhNotIn(List<String> values) {
            addCriterion("yddh not in", values, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhBetween(String value1, String value2) {
            addCriterion("yddh between", value1, value2, "yddh");
            return (Criteria) this;
        }

        public Criteria andYddhNotBetween(String value1, String value2) {
            addCriterion("yddh not between", value1, value2, "yddh");
            return (Criteria) this;
        }

        public Criteria andDzxxIsNull() {
            addCriterion("dzxx is null");
            return (Criteria) this;
        }

        public Criteria andDzxxIsNotNull() {
            addCriterion("dzxx is not null");
            return (Criteria) this;
        }

        public Criteria andDzxxEqualTo(String value) {
            addCriterion("dzxx =", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxNotEqualTo(String value) {
            addCriterion("dzxx <>", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxGreaterThan(String value) {
            addCriterion("dzxx >", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxGreaterThanOrEqualTo(String value) {
            addCriterion("dzxx >=", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxLessThan(String value) {
            addCriterion("dzxx <", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxLessThanOrEqualTo(String value) {
            addCriterion("dzxx <=", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxLike(String value) {
            addCriterion("dzxx like", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxNotLike(String value) {
            addCriterion("dzxx not like", value, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxIn(List<String> values) {
            addCriterion("dzxx in", values, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxNotIn(List<String> values) {
            addCriterion("dzxx not in", values, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxBetween(String value1, String value2) {
            addCriterion("dzxx between", value1, value2, "dzxx");
            return (Criteria) this;
        }

        public Criteria andDzxxNotBetween(String value1, String value2) {
            addCriterion("dzxx not between", value1, value2, "dzxx");
            return (Criteria) this;
        }

        public Criteria andBgdhIsNull() {
            addCriterion("bgdh is null");
            return (Criteria) this;
        }

        public Criteria andBgdhIsNotNull() {
            addCriterion("bgdh is not null");
            return (Criteria) this;
        }

        public Criteria andBgdhEqualTo(String value) {
            addCriterion("bgdh =", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhNotEqualTo(String value) {
            addCriterion("bgdh <>", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhGreaterThan(String value) {
            addCriterion("bgdh >", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhGreaterThanOrEqualTo(String value) {
            addCriterion("bgdh >=", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhLessThan(String value) {
            addCriterion("bgdh <", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhLessThanOrEqualTo(String value) {
            addCriterion("bgdh <=", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhLike(String value) {
            addCriterion("bgdh like", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhNotLike(String value) {
            addCriterion("bgdh not like", value, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhIn(List<String> values) {
            addCriterion("bgdh in", values, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhNotIn(List<String> values) {
            addCriterion("bgdh not in", values, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhBetween(String value1, String value2) {
            addCriterion("bgdh between", value1, value2, "bgdh");
            return (Criteria) this;
        }

        public Criteria andBgdhNotBetween(String value1, String value2) {
            addCriterion("bgdh not between", value1, value2, "bgdh");
            return (Criteria) this;
        }

        public Criteria andJtdhIsNull() {
            addCriterion("jtdh is null");
            return (Criteria) this;
        }

        public Criteria andJtdhIsNotNull() {
            addCriterion("jtdh is not null");
            return (Criteria) this;
        }

        public Criteria andJtdhEqualTo(String value) {
            addCriterion("jtdh =", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhNotEqualTo(String value) {
            addCriterion("jtdh <>", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhGreaterThan(String value) {
            addCriterion("jtdh >", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhGreaterThanOrEqualTo(String value) {
            addCriterion("jtdh >=", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhLessThan(String value) {
            addCriterion("jtdh <", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhLessThanOrEqualTo(String value) {
            addCriterion("jtdh <=", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhLike(String value) {
            addCriterion("jtdh like", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhNotLike(String value) {
            addCriterion("jtdh not like", value, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhIn(List<String> values) {
            addCriterion("jtdh in", values, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhNotIn(List<String> values) {
            addCriterion("jtdh not in", values, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhBetween(String value1, String value2) {
            addCriterion("jtdh between", value1, value2, "jtdh");
            return (Criteria) this;
        }

        public Criteria andJtdhNotBetween(String value1, String value2) {
            addCriterion("jtdh not between", value1, value2, "jtdh");
            return (Criteria) this;
        }

        public Criteria andZjgjmIsNull() {
            addCriterion("zjgjm is null");
            return (Criteria) this;
        }

        public Criteria andZjgjmIsNotNull() {
            addCriterion("zjgjm is not null");
            return (Criteria) this;
        }

        public Criteria andZjgjmEqualTo(String value) {
            addCriterion("zjgjm =", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmNotEqualTo(String value) {
            addCriterion("zjgjm <>", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmGreaterThan(String value) {
            addCriterion("zjgjm >", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmGreaterThanOrEqualTo(String value) {
            addCriterion("zjgjm >=", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmLessThan(String value) {
            addCriterion("zjgjm <", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmLessThanOrEqualTo(String value) {
            addCriterion("zjgjm <=", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmLike(String value) {
            addCriterion("zjgjm like", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmNotLike(String value) {
            addCriterion("zjgjm not like", value, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmIn(List<String> values) {
            addCriterion("zjgjm in", values, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmNotIn(List<String> values) {
            addCriterion("zjgjm not in", values, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmBetween(String value1, String value2) {
            addCriterion("zjgjm between", value1, value2, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgjmNotBetween(String value1, String value2) {
            addCriterion("zjgjm not between", value1, value2, "zjgjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjIsNull() {
            addCriterion("zjgwdj is null");
            return (Criteria) this;
        }

        public Criteria andZjgwdjIsNotNull() {
            addCriterion("zjgwdj is not null");
            return (Criteria) this;
        }

        public Criteria andZjgwdjEqualTo(String value) {
            addCriterion("zjgwdj =", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjNotEqualTo(String value) {
            addCriterion("zjgwdj <>", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjGreaterThan(String value) {
            addCriterion("zjgwdj >", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjGreaterThanOrEqualTo(String value) {
            addCriterion("zjgwdj >=", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjLessThan(String value) {
            addCriterion("zjgwdj <", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjLessThanOrEqualTo(String value) {
            addCriterion("zjgwdj <=", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjLike(String value) {
            addCriterion("zjgwdj like", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjNotLike(String value) {
            addCriterion("zjgwdj not like", value, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjIn(List<String> values) {
            addCriterion("zjgwdj in", values, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjNotIn(List<String> values) {
            addCriterion("zjgwdj not in", values, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjBetween(String value1, String value2) {
            addCriterion("zjgwdj between", value1, value2, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjNotBetween(String value1, String value2) {
            addCriterion("zjgwdj not between", value1, value2, "zjgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgjmIsNull() {
            addCriterion("glgjm is null");
            return (Criteria) this;
        }

        public Criteria andGlgjmIsNotNull() {
            addCriterion("glgjm is not null");
            return (Criteria) this;
        }

        public Criteria andGlgjmEqualTo(String value) {
            addCriterion("glgjm =", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmNotEqualTo(String value) {
            addCriterion("glgjm <>", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmGreaterThan(String value) {
            addCriterion("glgjm >", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmGreaterThanOrEqualTo(String value) {
            addCriterion("glgjm >=", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmLessThan(String value) {
            addCriterion("glgjm <", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmLessThanOrEqualTo(String value) {
            addCriterion("glgjm <=", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmLike(String value) {
            addCriterion("glgjm like", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmNotLike(String value) {
            addCriterion("glgjm not like", value, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmIn(List<String> values) {
            addCriterion("glgjm in", values, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmNotIn(List<String> values) {
            addCriterion("glgjm not in", values, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmBetween(String value1, String value2) {
            addCriterion("glgjm between", value1, value2, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgjmNotBetween(String value1, String value2) {
            addCriterion("glgjm not between", value1, value2, "glgjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjIsNull() {
            addCriterion("glgwdj is null");
            return (Criteria) this;
        }

        public Criteria andGlgwdjIsNotNull() {
            addCriterion("glgwdj is not null");
            return (Criteria) this;
        }

        public Criteria andGlgwdjEqualTo(String value) {
            addCriterion("glgwdj =", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjNotEqualTo(String value) {
            addCriterion("glgwdj <>", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjGreaterThan(String value) {
            addCriterion("glgwdj >", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjGreaterThanOrEqualTo(String value) {
            addCriterion("glgwdj >=", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjLessThan(String value) {
            addCriterion("glgwdj <", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjLessThanOrEqualTo(String value) {
            addCriterion("glgwdj <=", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjLike(String value) {
            addCriterion("glgwdj like", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjNotLike(String value) {
            addCriterion("glgwdj not like", value, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjIn(List<String> values) {
            addCriterion("glgwdj in", values, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjNotIn(List<String> values) {
            addCriterion("glgwdj not in", values, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjBetween(String value1, String value2) {
            addCriterion("glgwdj between", value1, value2, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjNotBetween(String value1, String value2) {
            addCriterion("glgwdj not between", value1, value2, "glgwdj");
            return (Criteria) this;
        }

        public Criteria andEmpidIsNull() {
            addCriterion("empid is null");
            return (Criteria) this;
        }

        public Criteria andEmpidIsNotNull() {
            addCriterion("empid is not null");
            return (Criteria) this;
        }

        public Criteria andEmpidEqualTo(String value) {
            addCriterion("empid =", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidNotEqualTo(String value) {
            addCriterion("empid <>", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidGreaterThan(String value) {
            addCriterion("empid >", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidGreaterThanOrEqualTo(String value) {
            addCriterion("empid >=", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidLessThan(String value) {
            addCriterion("empid <", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidLessThanOrEqualTo(String value) {
            addCriterion("empid <=", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidLike(String value) {
            addCriterion("empid like", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidNotLike(String value) {
            addCriterion("empid not like", value, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidIn(List<String> values) {
            addCriterion("empid in", values, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidNotIn(List<String> values) {
            addCriterion("empid not in", values, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidBetween(String value1, String value2) {
            addCriterion("empid between", value1, value2, "empid");
            return (Criteria) this;
        }

        public Criteria andEmpidNotBetween(String value1, String value2) {
            addCriterion("empid not between", value1, value2, "empid");
            return (Criteria) this;
        }

        public Criteria andJgIsNull() {
            addCriterion("jg is null");
            return (Criteria) this;
        }

        public Criteria andJgIsNotNull() {
            addCriterion("jg is not null");
            return (Criteria) this;
        }

        public Criteria andJgEqualTo(String value) {
            addCriterion("jg =", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgNotEqualTo(String value) {
            addCriterion("jg <>", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgGreaterThan(String value) {
            addCriterion("jg >", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgGreaterThanOrEqualTo(String value) {
            addCriterion("jg >=", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgLessThan(String value) {
            addCriterion("jg <", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgLessThanOrEqualTo(String value) {
            addCriterion("jg <=", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgLike(String value) {
            addCriterion("jg like", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgNotLike(String value) {
            addCriterion("jg not like", value, "jg");
            return (Criteria) this;
        }

        public Criteria andJgIn(List<String> values) {
            addCriterion("jg in", values, "jg");
            return (Criteria) this;
        }

        public Criteria andJgNotIn(List<String> values) {
            addCriterion("jg not in", values, "jg");
            return (Criteria) this;
        }

        public Criteria andJgBetween(String value1, String value2) {
            addCriterion("jg between", value1, value2, "jg");
            return (Criteria) this;
        }

        public Criteria andJgNotBetween(String value1, String value2) {
            addCriterion("jg not between", value1, value2, "jg");
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

        public Criteria andZwIsNull() {
            addCriterion("zw is null");
            return (Criteria) this;
        }

        public Criteria andZwIsNotNull() {
            addCriterion("zw is not null");
            return (Criteria) this;
        }

        public Criteria andZwEqualTo(String value) {
            addCriterion("zw =", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwNotEqualTo(String value) {
            addCriterion("zw <>", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwGreaterThan(String value) {
            addCriterion("zw >", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwGreaterThanOrEqualTo(String value) {
            addCriterion("zw >=", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwLessThan(String value) {
            addCriterion("zw <", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwLessThanOrEqualTo(String value) {
            addCriterion("zw <=", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwLike(String value) {
            addCriterion("zw like", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwNotLike(String value) {
            addCriterion("zw not like", value, "zw");
            return (Criteria) this;
        }

        public Criteria andZwIn(List<String> values) {
            addCriterion("zw in", values, "zw");
            return (Criteria) this;
        }

        public Criteria andZwNotIn(List<String> values) {
            addCriterion("zw not in", values, "zw");
            return (Criteria) this;
        }

        public Criteria andZwBetween(String value1, String value2) {
            addCriterion("zw between", value1, value2, "zw");
            return (Criteria) this;
        }

        public Criteria andZwNotBetween(String value1, String value2) {
            addCriterion("zw not between", value1, value2, "zw");
            return (Criteria) this;
        }

        public Criteria andZwmcIsNull() {
            addCriterion("zwmc is null");
            return (Criteria) this;
        }

        public Criteria andZwmcIsNotNull() {
            addCriterion("zwmc is not null");
            return (Criteria) this;
        }

        public Criteria andZwmcEqualTo(String value) {
            addCriterion("zwmc =", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcNotEqualTo(String value) {
            addCriterion("zwmc <>", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcGreaterThan(String value) {
            addCriterion("zwmc >", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcGreaterThanOrEqualTo(String value) {
            addCriterion("zwmc >=", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcLessThan(String value) {
            addCriterion("zwmc <", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcLessThanOrEqualTo(String value) {
            addCriterion("zwmc <=", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcLike(String value) {
            addCriterion("zwmc like", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcNotLike(String value) {
            addCriterion("zwmc not like", value, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcIn(List<String> values) {
            addCriterion("zwmc in", values, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcNotIn(List<String> values) {
            addCriterion("zwmc not in", values, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcBetween(String value1, String value2) {
            addCriterion("zwmc between", value1, value2, "zwmc");
            return (Criteria) this;
        }

        public Criteria andZwmcNotBetween(String value1, String value2) {
            addCriterion("zwmc not between", value1, value2, "zwmc");
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

        public Criteria andXkmlmIsNull() {
            addCriterion("xkmlm is null");
            return (Criteria) this;
        }

        public Criteria andXkmlmIsNotNull() {
            addCriterion("xkmlm is not null");
            return (Criteria) this;
        }

        public Criteria andXkmlmEqualTo(String value) {
            addCriterion("xkmlm =", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmNotEqualTo(String value) {
            addCriterion("xkmlm <>", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmGreaterThan(String value) {
            addCriterion("xkmlm >", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmGreaterThanOrEqualTo(String value) {
            addCriterion("xkmlm >=", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmLessThan(String value) {
            addCriterion("xkmlm <", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmLessThanOrEqualTo(String value) {
            addCriterion("xkmlm <=", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmLike(String value) {
            addCriterion("xkmlm like", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmNotLike(String value) {
            addCriterion("xkmlm not like", value, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmIn(List<String> values) {
            addCriterion("xkmlm in", values, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmNotIn(List<String> values) {
            addCriterion("xkmlm not in", values, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmBetween(String value1, String value2) {
            addCriterion("xkmlm between", value1, value2, "xkmlm");
            return (Criteria) this;
        }

        public Criteria andXkmlmNotBetween(String value1, String value2) {
            addCriterion("xkmlm not between", value1, value2, "xkmlm");
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

        public Criteria andEjxkmIsNull() {
            addCriterion("ejxkm is null");
            return (Criteria) this;
        }

        public Criteria andEjxkmIsNotNull() {
            addCriterion("ejxkm is not null");
            return (Criteria) this;
        }

        public Criteria andEjxkmEqualTo(String value) {
            addCriterion("ejxkm =", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmNotEqualTo(String value) {
            addCriterion("ejxkm <>", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmGreaterThan(String value) {
            addCriterion("ejxkm >", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmGreaterThanOrEqualTo(String value) {
            addCriterion("ejxkm >=", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmLessThan(String value) {
            addCriterion("ejxkm <", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmLessThanOrEqualTo(String value) {
            addCriterion("ejxkm <=", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmLike(String value) {
            addCriterion("ejxkm like", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmNotLike(String value) {
            addCriterion("ejxkm not like", value, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmIn(List<String> values) {
            addCriterion("ejxkm in", values, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmNotIn(List<String> values) {
            addCriterion("ejxkm not in", values, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmBetween(String value1, String value2) {
            addCriterion("ejxkm between", value1, value2, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkmNotBetween(String value1, String value2) {
            addCriterion("ejxkm not between", value1, value2, "ejxkm");
            return (Criteria) this;
        }

        public Criteria andEjxkIsNull() {
            addCriterion("ejxk is null");
            return (Criteria) this;
        }

        public Criteria andEjxkIsNotNull() {
            addCriterion("ejxk is not null");
            return (Criteria) this;
        }

        public Criteria andEjxkEqualTo(String value) {
            addCriterion("ejxk =", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkNotEqualTo(String value) {
            addCriterion("ejxk <>", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkGreaterThan(String value) {
            addCriterion("ejxk >", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkGreaterThanOrEqualTo(String value) {
            addCriterion("ejxk >=", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkLessThan(String value) {
            addCriterion("ejxk <", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkLessThanOrEqualTo(String value) {
            addCriterion("ejxk <=", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkLike(String value) {
            addCriterion("ejxk like", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkNotLike(String value) {
            addCriterion("ejxk not like", value, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkIn(List<String> values) {
            addCriterion("ejxk in", values, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkNotIn(List<String> values) {
            addCriterion("ejxk not in", values, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkBetween(String value1, String value2) {
            addCriterion("ejxk between", value1, value2, "ejxk");
            return (Criteria) this;
        }

        public Criteria andEjxkNotBetween(String value1, String value2) {
            addCriterion("ejxk not between", value1, value2, "ejxk");
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

        public Criteria andPoxmIsNull() {
            addCriterion("poxm is null");
            return (Criteria) this;
        }

        public Criteria andPoxmIsNotNull() {
            addCriterion("poxm is not null");
            return (Criteria) this;
        }

        public Criteria andPoxmEqualTo(String value) {
            addCriterion("poxm =", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmNotEqualTo(String value) {
            addCriterion("poxm <>", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmGreaterThan(String value) {
            addCriterion("poxm >", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmGreaterThanOrEqualTo(String value) {
            addCriterion("poxm >=", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmLessThan(String value) {
            addCriterion("poxm <", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmLessThanOrEqualTo(String value) {
            addCriterion("poxm <=", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmLike(String value) {
            addCriterion("poxm like", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmNotLike(String value) {
            addCriterion("poxm not like", value, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmIn(List<String> values) {
            addCriterion("poxm in", values, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmNotIn(List<String> values) {
            addCriterion("poxm not in", values, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmBetween(String value1, String value2) {
            addCriterion("poxm between", value1, value2, "poxm");
            return (Criteria) this;
        }

        public Criteria andPoxmNotBetween(String value1, String value2) {
            addCriterion("poxm not between", value1, value2, "poxm");
            return (Criteria) this;
        }

        public Criteria andRyxxshztIsNull() {
            addCriterion("ryxxshzt is null");
            return (Criteria) this;
        }

        public Criteria andRyxxshztIsNotNull() {
            addCriterion("ryxxshzt is not null");
            return (Criteria) this;
        }

        public Criteria andRyxxshztEqualTo(String value) {
            addCriterion("ryxxshzt =", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztNotEqualTo(String value) {
            addCriterion("ryxxshzt <>", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztGreaterThan(String value) {
            addCriterion("ryxxshzt >", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztGreaterThanOrEqualTo(String value) {
            addCriterion("ryxxshzt >=", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztLessThan(String value) {
            addCriterion("ryxxshzt <", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztLessThanOrEqualTo(String value) {
            addCriterion("ryxxshzt <=", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztLike(String value) {
            addCriterion("ryxxshzt like", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztNotLike(String value) {
            addCriterion("ryxxshzt not like", value, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztIn(List<String> values) {
            addCriterion("ryxxshzt in", values, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztNotIn(List<String> values) {
            addCriterion("ryxxshzt not in", values, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztBetween(String value1, String value2) {
            addCriterion("ryxxshzt between", value1, value2, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andRyxxshztNotBetween(String value1, String value2) {
            addCriterion("ryxxshzt not between", value1, value2, "ryxxshzt");
            return (Criteria) this;
        }

        public Criteria andTbrqIsNull() {
            addCriterion("tbrq is null");
            return (Criteria) this;
        }

        public Criteria andTbrqIsNotNull() {
            addCriterion("tbrq is not null");
            return (Criteria) this;
        }

        public Criteria andTbrqEqualTo(String value) {
            addCriterion("tbrq =", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqNotEqualTo(String value) {
            addCriterion("tbrq <>", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqGreaterThan(String value) {
            addCriterion("tbrq >", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqGreaterThanOrEqualTo(String value) {
            addCriterion("tbrq >=", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqLessThan(String value) {
            addCriterion("tbrq <", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqLessThanOrEqualTo(String value) {
            addCriterion("tbrq <=", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqLike(String value) {
            addCriterion("tbrq like", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqNotLike(String value) {
            addCriterion("tbrq not like", value, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqIn(List<String> values) {
            addCriterion("tbrq in", values, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqNotIn(List<String> values) {
            addCriterion("tbrq not in", values, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqBetween(String value1, String value2) {
            addCriterion("tbrq between", value1, value2, "tbrq");
            return (Criteria) this;
        }

        public Criteria andTbrqNotBetween(String value1, String value2) {
            addCriterion("tbrq not between", value1, value2, "tbrq");
            return (Criteria) this;
        }

        public Criteria andJzdw1IsNull() {
            addCriterion("jzdw1 is null");
            return (Criteria) this;
        }

        public Criteria andJzdw1IsNotNull() {
            addCriterion("jzdw1 is not null");
            return (Criteria) this;
        }

        public Criteria andJzdw1EqualTo(String value) {
            addCriterion("jzdw1 =", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1NotEqualTo(String value) {
            addCriterion("jzdw1 <>", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1GreaterThan(String value) {
            addCriterion("jzdw1 >", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1GreaterThanOrEqualTo(String value) {
            addCriterion("jzdw1 >=", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1LessThan(String value) {
            addCriterion("jzdw1 <", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1LessThanOrEqualTo(String value) {
            addCriterion("jzdw1 <=", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1Like(String value) {
            addCriterion("jzdw1 like", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1NotLike(String value) {
            addCriterion("jzdw1 not like", value, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1In(List<String> values) {
            addCriterion("jzdw1 in", values, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1NotIn(List<String> values) {
            addCriterion("jzdw1 not in", values, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1Between(String value1, String value2) {
            addCriterion("jzdw1 between", value1, value2, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw1NotBetween(String value1, String value2) {
            addCriterion("jzdw1 not between", value1, value2, "jzdw1");
            return (Criteria) this;
        }

        public Criteria andJzdw2IsNull() {
            addCriterion("jzdw2 is null");
            return (Criteria) this;
        }

        public Criteria andJzdw2IsNotNull() {
            addCriterion("jzdw2 is not null");
            return (Criteria) this;
        }

        public Criteria andJzdw2EqualTo(String value) {
            addCriterion("jzdw2 =", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2NotEqualTo(String value) {
            addCriterion("jzdw2 <>", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2GreaterThan(String value) {
            addCriterion("jzdw2 >", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2GreaterThanOrEqualTo(String value) {
            addCriterion("jzdw2 >=", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2LessThan(String value) {
            addCriterion("jzdw2 <", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2LessThanOrEqualTo(String value) {
            addCriterion("jzdw2 <=", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2Like(String value) {
            addCriterion("jzdw2 like", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2NotLike(String value) {
            addCriterion("jzdw2 not like", value, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2In(List<String> values) {
            addCriterion("jzdw2 in", values, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2NotIn(List<String> values) {
            addCriterion("jzdw2 not in", values, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2Between(String value1, String value2) {
            addCriterion("jzdw2 between", value1, value2, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andJzdw2NotBetween(String value1, String value2) {
            addCriterion("jzdw2 not between", value1, value2, "jzdw2");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNull() {
            addCriterion("batch_num is null");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNotNull() {
            addCriterion("batch_num is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNumEqualTo(String value) {
            addCriterion("batch_num =", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotEqualTo(String value) {
            addCriterion("batch_num <>", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThan(String value) {
            addCriterion("batch_num >", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThanOrEqualTo(String value) {
            addCriterion("batch_num >=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThan(String value) {
            addCriterion("batch_num <", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThanOrEqualTo(String value) {
            addCriterion("batch_num <=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLike(String value) {
            addCriterion("batch_num like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotLike(String value) {
            addCriterion("batch_num not like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumIn(List<String> values) {
            addCriterion("batch_num in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotIn(List<String> values) {
            addCriterion("batch_num not in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumBetween(String value1, String value2) {
            addCriterion("batch_num between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotBetween(String value1, String value2) {
            addCriterion("batch_num not between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdIsNull() {
            addCriterion("basic_active_id is null");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdIsNotNull() {
            addCriterion("basic_active_id is not null");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdEqualTo(String value) {
            addCriterion("basic_active_id =", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdNotEqualTo(String value) {
            addCriterion("basic_active_id <>", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdGreaterThan(String value) {
            addCriterion("basic_active_id >", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdGreaterThanOrEqualTo(String value) {
            addCriterion("basic_active_id >=", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdLessThan(String value) {
            addCriterion("basic_active_id <", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdLessThanOrEqualTo(String value) {
            addCriterion("basic_active_id <=", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdLike(String value) {
            addCriterion("basic_active_id like", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdNotLike(String value) {
            addCriterion("basic_active_id not like", value, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdIn(List<String> values) {
            addCriterion("basic_active_id in", values, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdNotIn(List<String> values) {
            addCriterion("basic_active_id not in", values, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdBetween(String value1, String value2) {
            addCriterion("basic_active_id between", value1, value2, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andBasicActiveIdNotBetween(String value1, String value2) {
            addCriterion("basic_active_id not between", value1, value2, "basicActiveId");
            return (Criteria) this;
        }

        public Criteria andDomainCodeIsNull() {
            addCriterion("domain_code is null");
            return (Criteria) this;
        }

        public Criteria andDomainCodeIsNotNull() {
            addCriterion("domain_code is not null");
            return (Criteria) this;
        }

        public Criteria andDomainCodeEqualTo(String value) {
            addCriterion("domain_code =", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeNotEqualTo(String value) {
            addCriterion("domain_code <>", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeGreaterThan(String value) {
            addCriterion("domain_code >", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeGreaterThanOrEqualTo(String value) {
            addCriterion("domain_code >=", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeLessThan(String value) {
            addCriterion("domain_code <", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeLessThanOrEqualTo(String value) {
            addCriterion("domain_code <=", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeLike(String value) {
            addCriterion("domain_code like", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeNotLike(String value) {
            addCriterion("domain_code not like", value, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeIn(List<String> values) {
            addCriterion("domain_code in", values, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeNotIn(List<String> values) {
            addCriterion("domain_code not in", values, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeBetween(String value1, String value2) {
            addCriterion("domain_code between", value1, value2, "domainCode");
            return (Criteria) this;
        }

        public Criteria andDomainCodeNotBetween(String value1, String value2) {
            addCriterion("domain_code not between", value1, value2, "domainCode");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeIsNull() {
            addCriterion("basic_active_type is null");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeIsNotNull() {
            addCriterion("basic_active_type is not null");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeEqualTo(String value) {
            addCriterion("basic_active_type =", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeNotEqualTo(String value) {
            addCriterion("basic_active_type <>", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeGreaterThan(String value) {
            addCriterion("basic_active_type >", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeGreaterThanOrEqualTo(String value) {
            addCriterion("basic_active_type >=", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeLessThan(String value) {
            addCriterion("basic_active_type <", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeLessThanOrEqualTo(String value) {
            addCriterion("basic_active_type <=", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeLike(String value) {
            addCriterion("basic_active_type like", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeNotLike(String value) {
            addCriterion("basic_active_type not like", value, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeIn(List<String> values) {
            addCriterion("basic_active_type in", values, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeNotIn(List<String> values) {
            addCriterion("basic_active_type not in", values, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeBetween(String value1, String value2) {
            addCriterion("basic_active_type between", value1, value2, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andBasicActiveTypeNotBetween(String value1, String value2) {
            addCriterion("basic_active_type not between", value1, value2, "basicActiveType");
            return (Criteria) this;
        }

        public Criteria andSzzdw1IsNull() {
            addCriterion("szzdw1 is null");
            return (Criteria) this;
        }

        public Criteria andSzzdw1IsNotNull() {
            addCriterion("szzdw1 is not null");
            return (Criteria) this;
        }

        public Criteria andSzzdw1EqualTo(String value) {
            addCriterion("szzdw1 =", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1NotEqualTo(String value) {
            addCriterion("szzdw1 <>", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1GreaterThan(String value) {
            addCriterion("szzdw1 >", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1GreaterThanOrEqualTo(String value) {
            addCriterion("szzdw1 >=", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1LessThan(String value) {
            addCriterion("szzdw1 <", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1LessThanOrEqualTo(String value) {
            addCriterion("szzdw1 <=", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1Like(String value) {
            addCriterion("szzdw1 like", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1NotLike(String value) {
            addCriterion("szzdw1 not like", value, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1In(List<String> values) {
            addCriterion("szzdw1 in", values, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1NotIn(List<String> values) {
            addCriterion("szzdw1 not in", values, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1Between(String value1, String value2) {
            addCriterion("szzdw1 between", value1, value2, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw1NotBetween(String value1, String value2) {
            addCriterion("szzdw1 not between", value1, value2, "szzdw1");
            return (Criteria) this;
        }

        public Criteria andSzzdw2IsNull() {
            addCriterion("szzdw2 is null");
            return (Criteria) this;
        }

        public Criteria andSzzdw2IsNotNull() {
            addCriterion("szzdw2 is not null");
            return (Criteria) this;
        }

        public Criteria andSzzdw2EqualTo(String value) {
            addCriterion("szzdw2 =", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2NotEqualTo(String value) {
            addCriterion("szzdw2 <>", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2GreaterThan(String value) {
            addCriterion("szzdw2 >", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2GreaterThanOrEqualTo(String value) {
            addCriterion("szzdw2 >=", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2LessThan(String value) {
            addCriterion("szzdw2 <", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2LessThanOrEqualTo(String value) {
            addCriterion("szzdw2 <=", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2Like(String value) {
            addCriterion("szzdw2 like", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2NotLike(String value) {
            addCriterion("szzdw2 not like", value, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2In(List<String> values) {
            addCriterion("szzdw2 in", values, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2NotIn(List<String> values) {
            addCriterion("szzdw2 not in", values, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2Between(String value1, String value2) {
            addCriterion("szzdw2 between", value1, value2, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andSzzdw2NotBetween(String value1, String value2) {
            addCriterion("szzdw2 not between", value1, value2, "szzdw2");
            return (Criteria) this;
        }

        public Criteria andGlqsrqIsNull() {
            addCriterion("glqsrq is null");
            return (Criteria) this;
        }

        public Criteria andGlqsrqIsNotNull() {
            addCriterion("glqsrq is not null");
            return (Criteria) this;
        }

        public Criteria andGlqsrqEqualTo(String value) {
            addCriterion("glqsrq =", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqNotEqualTo(String value) {
            addCriterion("glqsrq <>", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqGreaterThan(String value) {
            addCriterion("glqsrq >", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqGreaterThanOrEqualTo(String value) {
            addCriterion("glqsrq >=", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqLessThan(String value) {
            addCriterion("glqsrq <", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqLessThanOrEqualTo(String value) {
            addCriterion("glqsrq <=", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqLike(String value) {
            addCriterion("glqsrq like", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqNotLike(String value) {
            addCriterion("glqsrq not like", value, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqIn(List<String> values) {
            addCriterion("glqsrq in", values, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqNotIn(List<String> values) {
            addCriterion("glqsrq not in", values, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqBetween(String value1, String value2) {
            addCriterion("glqsrq between", value1, value2, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andGlqsrqNotBetween(String value1, String value2) {
            addCriterion("glqsrq not between", value1, value2, "glqsrq");
            return (Criteria) this;
        }

        public Criteria andJdglIsNull() {
            addCriterion("jdgl is null");
            return (Criteria) this;
        }

        public Criteria andJdglIsNotNull() {
            addCriterion("jdgl is not null");
            return (Criteria) this;
        }

        public Criteria andJdglEqualTo(String value) {
            addCriterion("jdgl =", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglNotEqualTo(String value) {
            addCriterion("jdgl <>", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglGreaterThan(String value) {
            addCriterion("jdgl >", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglGreaterThanOrEqualTo(String value) {
            addCriterion("jdgl >=", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglLessThan(String value) {
            addCriterion("jdgl <", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglLessThanOrEqualTo(String value) {
            addCriterion("jdgl <=", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglLike(String value) {
            addCriterion("jdgl like", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglNotLike(String value) {
            addCriterion("jdgl not like", value, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglIn(List<String> values) {
            addCriterion("jdgl in", values, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglNotIn(List<String> values) {
            addCriterion("jdgl not in", values, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglBetween(String value1, String value2) {
            addCriterion("jdgl between", value1, value2, "jdgl");
            return (Criteria) this;
        }

        public Criteria andJdglNotBetween(String value1, String value2) {
            addCriterion("jdgl not between", value1, value2, "jdgl");
            return (Criteria) this;
        }

        public Criteria andGwzlbmIsNull() {
            addCriterion("gwzlbm is null");
            return (Criteria) this;
        }

        public Criteria andGwzlbmIsNotNull() {
            addCriterion("gwzlbm is not null");
            return (Criteria) this;
        }

        public Criteria andGwzlbmEqualTo(String value) {
            addCriterion("gwzlbm =", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmNotEqualTo(String value) {
            addCriterion("gwzlbm <>", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmGreaterThan(String value) {
            addCriterion("gwzlbm >", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmGreaterThanOrEqualTo(String value) {
            addCriterion("gwzlbm >=", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmLessThan(String value) {
            addCriterion("gwzlbm <", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmLessThanOrEqualTo(String value) {
            addCriterion("gwzlbm <=", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmLike(String value) {
            addCriterion("gwzlbm like", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmNotLike(String value) {
            addCriterion("gwzlbm not like", value, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmIn(List<String> values) {
            addCriterion("gwzlbm in", values, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmNotIn(List<String> values) {
            addCriterion("gwzlbm not in", values, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmBetween(String value1, String value2) {
            addCriterion("gwzlbm between", value1, value2, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmNotBetween(String value1, String value2) {
            addCriterion("gwzlbm not between", value1, value2, "gwzlbm");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcIsNull() {
            addCriterion("gwzlbmc is null");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcIsNotNull() {
            addCriterion("gwzlbmc is not null");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcEqualTo(String value) {
            addCriterion("gwzlbmc =", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcNotEqualTo(String value) {
            addCriterion("gwzlbmc <>", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcGreaterThan(String value) {
            addCriterion("gwzlbmc >", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcGreaterThanOrEqualTo(String value) {
            addCriterion("gwzlbmc >=", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcLessThan(String value) {
            addCriterion("gwzlbmc <", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcLessThanOrEqualTo(String value) {
            addCriterion("gwzlbmc <=", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcLike(String value) {
            addCriterion("gwzlbmc like", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcNotLike(String value) {
            addCriterion("gwzlbmc not like", value, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcIn(List<String> values) {
            addCriterion("gwzlbmc in", values, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcNotIn(List<String> values) {
            addCriterion("gwzlbmc not in", values, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcBetween(String value1, String value2) {
            addCriterion("gwzlbmc between", value1, value2, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andGwzlbmcNotBetween(String value1, String value2) {
            addCriterion("gwzlbmc not between", value1, value2, "gwzlbmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmIsNull() {
            addCriterion("zgdjm is null");
            return (Criteria) this;
        }

        public Criteria andZgdjmIsNotNull() {
            addCriterion("zgdjm is not null");
            return (Criteria) this;
        }

        public Criteria andZgdjmEqualTo(String value) {
            addCriterion("zgdjm =", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmNotEqualTo(String value) {
            addCriterion("zgdjm <>", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmGreaterThan(String value) {
            addCriterion("zgdjm >", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmGreaterThanOrEqualTo(String value) {
            addCriterion("zgdjm >=", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmLessThan(String value) {
            addCriterion("zgdjm <", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmLessThanOrEqualTo(String value) {
            addCriterion("zgdjm <=", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmLike(String value) {
            addCriterion("zgdjm like", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmNotLike(String value) {
            addCriterion("zgdjm not like", value, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmIn(List<String> values) {
            addCriterion("zgdjm in", values, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmNotIn(List<String> values) {
            addCriterion("zgdjm not in", values, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmBetween(String value1, String value2) {
            addCriterion("zgdjm between", value1, value2, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmNotBetween(String value1, String value2) {
            addCriterion("zgdjm not between", value1, value2, "zgdjm");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcIsNull() {
            addCriterion("zgdjmmc is null");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcIsNotNull() {
            addCriterion("zgdjmmc is not null");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcEqualTo(String value) {
            addCriterion("zgdjmmc =", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcNotEqualTo(String value) {
            addCriterion("zgdjmmc <>", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcGreaterThan(String value) {
            addCriterion("zgdjmmc >", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcGreaterThanOrEqualTo(String value) {
            addCriterion("zgdjmmc >=", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcLessThan(String value) {
            addCriterion("zgdjmmc <", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcLessThanOrEqualTo(String value) {
            addCriterion("zgdjmmc <=", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcLike(String value) {
            addCriterion("zgdjmmc like", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcNotLike(String value) {
            addCriterion("zgdjmmc not like", value, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcIn(List<String> values) {
            addCriterion("zgdjmmc in", values, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcNotIn(List<String> values) {
            addCriterion("zgdjmmc not in", values, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcBetween(String value1, String value2) {
            addCriterion("zgdjmmc between", value1, value2, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZgdjmmcNotBetween(String value1, String value2) {
            addCriterion("zgdjmmc not between", value1, value2, "zgdjmmc");
            return (Criteria) this;
        }

        public Criteria andZzdjsjIsNull() {
            addCriterion("zzdjsj is null");
            return (Criteria) this;
        }

        public Criteria andZzdjsjIsNotNull() {
            addCriterion("zzdjsj is not null");
            return (Criteria) this;
        }

        public Criteria andZzdjsjEqualTo(String value) {
            addCriterion("zzdjsj =", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjNotEqualTo(String value) {
            addCriterion("zzdjsj <>", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjGreaterThan(String value) {
            addCriterion("zzdjsj >", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjGreaterThanOrEqualTo(String value) {
            addCriterion("zzdjsj >=", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjLessThan(String value) {
            addCriterion("zzdjsj <", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjLessThanOrEqualTo(String value) {
            addCriterion("zzdjsj <=", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjLike(String value) {
            addCriterion("zzdjsj like", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjNotLike(String value) {
            addCriterion("zzdjsj not like", value, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjIn(List<String> values) {
            addCriterion("zzdjsj in", values, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjNotIn(List<String> values) {
            addCriterion("zzdjsj not in", values, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjBetween(String value1, String value2) {
            addCriterion("zzdjsj between", value1, value2, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZzdjsjNotBetween(String value1, String value2) {
            addCriterion("zzdjsj not between", value1, value2, "zzdjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmIsNull() {
            addCriterion("zjgwdjm is null");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmIsNotNull() {
            addCriterion("zjgwdjm is not null");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmEqualTo(String value) {
            addCriterion("zjgwdjm =", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmNotEqualTo(String value) {
            addCriterion("zjgwdjm <>", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmGreaterThan(String value) {
            addCriterion("zjgwdjm >", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmGreaterThanOrEqualTo(String value) {
            addCriterion("zjgwdjm >=", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmLessThan(String value) {
            addCriterion("zjgwdjm <", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmLessThanOrEqualTo(String value) {
            addCriterion("zjgwdjm <=", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmLike(String value) {
            addCriterion("zjgwdjm like", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmNotLike(String value) {
            addCriterion("zjgwdjm not like", value, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmIn(List<String> values) {
            addCriterion("zjgwdjm in", values, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmNotIn(List<String> values) {
            addCriterion("zjgwdjm not in", values, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmBetween(String value1, String value2) {
            addCriterion("zjgwdjm between", value1, value2, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmNotBetween(String value1, String value2) {
            addCriterion("zjgwdjm not between", value1, value2, "zjgwdjm");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcIsNull() {
            addCriterion("zjgwdjmc is null");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcIsNotNull() {
            addCriterion("zjgwdjmc is not null");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcEqualTo(String value) {
            addCriterion("zjgwdjmc =", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcNotEqualTo(String value) {
            addCriterion("zjgwdjmc <>", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcGreaterThan(String value) {
            addCriterion("zjgwdjmc >", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcGreaterThanOrEqualTo(String value) {
            addCriterion("zjgwdjmc >=", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcLessThan(String value) {
            addCriterion("zjgwdjmc <", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcLessThanOrEqualTo(String value) {
            addCriterion("zjgwdjmc <=", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcLike(String value) {
            addCriterion("zjgwdjmc like", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcNotLike(String value) {
            addCriterion("zjgwdjmc not like", value, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcIn(List<String> values) {
            addCriterion("zjgwdjmc in", values, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcNotIn(List<String> values) {
            addCriterion("zjgwdjmc not in", values, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcBetween(String value1, String value2) {
            addCriterion("zjgwdjmc between", value1, value2, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZjgwdjmcNotBetween(String value1, String value2) {
            addCriterion("zjgwdjmc not between", value1, value2, "zjgwdjmc");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjIsNull() {
            addCriterion("zyjszwpdsj is null");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjIsNotNull() {
            addCriterion("zyjszwpdsj is not null");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjEqualTo(String value) {
            addCriterion("zyjszwpdsj =", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjNotEqualTo(String value) {
            addCriterion("zyjszwpdsj <>", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjGreaterThan(String value) {
            addCriterion("zyjszwpdsj >", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjGreaterThanOrEqualTo(String value) {
            addCriterion("zyjszwpdsj >=", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjLessThan(String value) {
            addCriterion("zyjszwpdsj <", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjLessThanOrEqualTo(String value) {
            addCriterion("zyjszwpdsj <=", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjLike(String value) {
            addCriterion("zyjszwpdsj like", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjNotLike(String value) {
            addCriterion("zyjszwpdsj not like", value, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjIn(List<String> values) {
            addCriterion("zyjszwpdsj in", values, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjNotIn(List<String> values) {
            addCriterion("zyjszwpdsj not in", values, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjBetween(String value1, String value2) {
            addCriterion("zyjszwpdsj between", value1, value2, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZyjszwpdsjNotBetween(String value1, String value2) {
            addCriterion("zyjszwpdsj not between", value1, value2, "zyjszwpdsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjIsNull() {
            addCriterion("zjgwfjsj is null");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjIsNotNull() {
            addCriterion("zjgwfjsj is not null");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjEqualTo(String value) {
            addCriterion("zjgwfjsj =", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjNotEqualTo(String value) {
            addCriterion("zjgwfjsj <>", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjGreaterThan(String value) {
            addCriterion("zjgwfjsj >", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjGreaterThanOrEqualTo(String value) {
            addCriterion("zjgwfjsj >=", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjLessThan(String value) {
            addCriterion("zjgwfjsj <", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjLessThanOrEqualTo(String value) {
            addCriterion("zjgwfjsj <=", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjLike(String value) {
            addCriterion("zjgwfjsj like", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjNotLike(String value) {
            addCriterion("zjgwfjsj not like", value, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjIn(List<String> values) {
            addCriterion("zjgwfjsj in", values, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjNotIn(List<String> values) {
            addCriterion("zjgwfjsj not in", values, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjBetween(String value1, String value2) {
            addCriterion("zjgwfjsj between", value1, value2, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andZjgwfjsjNotBetween(String value1, String value2) {
            addCriterion("zjgwfjsj not between", value1, value2, "zjgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmIsNull() {
            addCriterion("glgwdjm is null");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmIsNotNull() {
            addCriterion("glgwdjm is not null");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmEqualTo(String value) {
            addCriterion("glgwdjm =", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmNotEqualTo(String value) {
            addCriterion("glgwdjm <>", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmGreaterThan(String value) {
            addCriterion("glgwdjm >", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmGreaterThanOrEqualTo(String value) {
            addCriterion("glgwdjm >=", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmLessThan(String value) {
            addCriterion("glgwdjm <", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmLessThanOrEqualTo(String value) {
            addCriterion("glgwdjm <=", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmLike(String value) {
            addCriterion("glgwdjm like", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmNotLike(String value) {
            addCriterion("glgwdjm not like", value, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmIn(List<String> values) {
            addCriterion("glgwdjm in", values, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmNotIn(List<String> values) {
            addCriterion("glgwdjm not in", values, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmBetween(String value1, String value2) {
            addCriterion("glgwdjm between", value1, value2, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwdjmNotBetween(String value1, String value2) {
            addCriterion("glgwdjm not between", value1, value2, "glgwdjm");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjIsNull() {
            addCriterion("glgwfjsj is null");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjIsNotNull() {
            addCriterion("glgwfjsj is not null");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjEqualTo(String value) {
            addCriterion("glgwfjsj =", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjNotEqualTo(String value) {
            addCriterion("glgwfjsj <>", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjGreaterThan(String value) {
            addCriterion("glgwfjsj >", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjGreaterThanOrEqualTo(String value) {
            addCriterion("glgwfjsj >=", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjLessThan(String value) {
            addCriterion("glgwfjsj <", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjLessThanOrEqualTo(String value) {
            addCriterion("glgwfjsj <=", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjLike(String value) {
            addCriterion("glgwfjsj like", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjNotLike(String value) {
            addCriterion("glgwfjsj not like", value, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjIn(List<String> values) {
            addCriterion("glgwfjsj in", values, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjNotIn(List<String> values) {
            addCriterion("glgwfjsj not in", values, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjBetween(String value1, String value2) {
            addCriterion("glgwfjsj between", value1, value2, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGlgwfjsjNotBetween(String value1, String value2) {
            addCriterion("glgwfjsj not between", value1, value2, "glgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmIsNull() {
            addCriterion("gqgwdjm is null");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmIsNotNull() {
            addCriterion("gqgwdjm is not null");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmEqualTo(String value) {
            addCriterion("gqgwdjm =", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmNotEqualTo(String value) {
            addCriterion("gqgwdjm <>", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmGreaterThan(String value) {
            addCriterion("gqgwdjm >", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmGreaterThanOrEqualTo(String value) {
            addCriterion("gqgwdjm >=", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmLessThan(String value) {
            addCriterion("gqgwdjm <", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmLessThanOrEqualTo(String value) {
            addCriterion("gqgwdjm <=", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmLike(String value) {
            addCriterion("gqgwdjm like", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmNotLike(String value) {
            addCriterion("gqgwdjm not like", value, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmIn(List<String> values) {
            addCriterion("gqgwdjm in", values, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmNotIn(List<String> values) {
            addCriterion("gqgwdjm not in", values, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmBetween(String value1, String value2) {
            addCriterion("gqgwdjm between", value1, value2, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmNotBetween(String value1, String value2) {
            addCriterion("gqgwdjm not between", value1, value2, "gqgwdjm");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjIsNull() {
            addCriterion("gqgwfjsj is null");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjIsNotNull() {
            addCriterion("gqgwfjsj is not null");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjEqualTo(String value) {
            addCriterion("gqgwfjsj =", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjNotEqualTo(String value) {
            addCriterion("gqgwfjsj <>", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjGreaterThan(String value) {
            addCriterion("gqgwfjsj >", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjGreaterThanOrEqualTo(String value) {
            addCriterion("gqgwfjsj >=", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjLessThan(String value) {
            addCriterion("gqgwfjsj <", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjLessThanOrEqualTo(String value) {
            addCriterion("gqgwfjsj <=", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjLike(String value) {
            addCriterion("gqgwfjsj like", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjNotLike(String value) {
            addCriterion("gqgwfjsj not like", value, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjIn(List<String> values) {
            addCriterion("gqgwfjsj in", values, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjNotIn(List<String> values) {
            addCriterion("gqgwfjsj not in", values, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjBetween(String value1, String value2) {
            addCriterion("gqgwfjsj between", value1, value2, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwfjsjNotBetween(String value1, String value2) {
            addCriterion("gqgwfjsj not between", value1, value2, "gqgwfjsj");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcIsNull() {
            addCriterion("gqgwdjmc is null");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcIsNotNull() {
            addCriterion("gqgwdjmc is not null");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcEqualTo(String value) {
            addCriterion("gqgwdjmc =", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcNotEqualTo(String value) {
            addCriterion("gqgwdjmc <>", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcGreaterThan(String value) {
            addCriterion("gqgwdjmc >", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcGreaterThanOrEqualTo(String value) {
            addCriterion("gqgwdjmc >=", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcLessThan(String value) {
            addCriterion("gqgwdjmc <", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcLessThanOrEqualTo(String value) {
            addCriterion("gqgwdjmc <=", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcLike(String value) {
            addCriterion("gqgwdjmc like", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcNotLike(String value) {
            addCriterion("gqgwdjmc not like", value, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcIn(List<String> values) {
            addCriterion("gqgwdjmc in", values, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcNotIn(List<String> values) {
            addCriterion("gqgwdjmc not in", values, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcBetween(String value1, String value2) {
            addCriterion("gqgwdjmc between", value1, value2, "gqgwdjmc");
            return (Criteria) this;
        }

        public Criteria andGqgwdjmcNotBetween(String value1, String value2) {
            addCriterion("gqgwdjmc not between", value1, value2, "gqgwdjmc");
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