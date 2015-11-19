package domain;

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

        public Criteria andDwidIsNull() {
            addCriterion("dwid is null");
            return (Criteria) this;
        }

        public Criteria andDwidIsNotNull() {
            addCriterion("dwid is not null");
            return (Criteria) this;
        }

        public Criteria andDwidEqualTo(String value) {
            addCriterion("dwid =", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidNotEqualTo(String value) {
            addCriterion("dwid <>", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidGreaterThan(String value) {
            addCriterion("dwid >", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidGreaterThanOrEqualTo(String value) {
            addCriterion("dwid >=", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidLessThan(String value) {
            addCriterion("dwid <", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidLessThanOrEqualTo(String value) {
            addCriterion("dwid <=", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidLike(String value) {
            addCriterion("dwid like", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidNotLike(String value) {
            addCriterion("dwid not like", value, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidIn(List<String> values) {
            addCriterion("dwid in", values, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidNotIn(List<String> values) {
            addCriterion("dwid not in", values, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidBetween(String value1, String value2) {
            addCriterion("dwid between", value1, value2, "dwid");
            return (Criteria) this;
        }

        public Criteria andDwidNotBetween(String value1, String value2) {
            addCriterion("dwid not between", value1, value2, "dwid");
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