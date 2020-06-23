package domain.cet;

import org.apache.commons.lang3.BooleanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CetAnnualObjExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetAnnualObjExample() {
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

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andAnnualIdIsNull() {
            addCriterion("annual_id is null");
            return (Criteria) this;
        }

        public Criteria andAnnualIdIsNotNull() {
            addCriterion("annual_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnnualIdEqualTo(Integer value) {
            addCriterion("annual_id =", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdNotEqualTo(Integer value) {
            addCriterion("annual_id <>", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdGreaterThan(Integer value) {
            addCriterion("annual_id >", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("annual_id >=", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdLessThan(Integer value) {
            addCriterion("annual_id <", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdLessThanOrEqualTo(Integer value) {
            addCriterion("annual_id <=", value, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdIn(List<Integer> values) {
            addCriterion("annual_id in", values, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdNotIn(List<Integer> values) {
            addCriterion("annual_id not in", values, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdBetween(Integer value1, Integer value2) {
            addCriterion("annual_id between", value1, value2, "annualId");
            return (Criteria) this;
        }

        public Criteria andAnnualIdNotBetween(Integer value1, Integer value2) {
            addCriterion("annual_id not between", value1, value2, "annualId");
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

        public Criteria andAdminLevelIsNull() {
            addCriterion("admin_level is null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIsNotNull() {
            addCriterion("admin_level is not null");
            return (Criteria) this;
        }

        public Criteria andAdminLevelEqualTo(Integer value) {
            addCriterion("admin_level =", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotEqualTo(Integer value) {
            addCriterion("admin_level <>", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThan(Integer value) {
            addCriterion("admin_level >", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_level >=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThan(Integer value) {
            addCriterion("admin_level <", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelLessThanOrEqualTo(Integer value) {
            addCriterion("admin_level <=", value, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelIn(List<Integer> values) {
            addCriterion("admin_level in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotIn(List<Integer> values) {
            addCriterion("admin_level not in", values, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelBetween(Integer value1, Integer value2) {
            addCriterion("admin_level between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andAdminLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_level not between", value1, value2, "adminLevel");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireIsNull() {
            addCriterion("need_update_require is null");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireIsNotNull() {
            addCriterion("need_update_require is not null");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireEqualTo(Boolean value) {
            addCriterion("need_update_require =", value, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireNotEqualTo(Boolean value) {
            addCriterion("need_update_require <>", value, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireGreaterThan(Boolean value) {
            addCriterion("need_update_require >", value, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireGreaterThanOrEqualTo(Boolean value) {
            addCriterion("need_update_require >=", value, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireLessThan(Boolean value) {
            addCriterion("need_update_require <", value, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireLessThanOrEqualTo(Boolean value) {
            addCriterion("need_update_require <=", value, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireIn(List<Boolean> values) {
            addCriterion("need_update_require in", values, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireNotIn(List<Boolean> values) {
            addCriterion("need_update_require not in", values, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireBetween(Boolean value1, Boolean value2) {
            addCriterion("need_update_require between", value1, value2, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andNeedUpdateRequireNotBetween(Boolean value1, Boolean value2) {
            addCriterion("need_update_require not between", value1, value2, "needUpdateRequire");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNull() {
            addCriterion("post_type is null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIsNotNull() {
            addCriterion("post_type is not null");
            return (Criteria) this;
        }

        public Criteria andPostTypeEqualTo(Integer value) {
            addCriterion("post_type =", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotEqualTo(Integer value) {
            addCriterion("post_type <>", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThan(Integer value) {
            addCriterion("post_type >", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_type >=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThan(Integer value) {
            addCriterion("post_type <", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("post_type <=", value, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeIn(List<Integer> values) {
            addCriterion("post_type in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotIn(List<Integer> values) {
            addCriterion("post_type not in", values, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeBetween(Integer value1, Integer value2) {
            addCriterion("post_type between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andPostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("post_type not between", value1, value2, "postType");
            return (Criteria) this;
        }

        public Criteria andIdentityIsNull() {
            addCriterion("identity is null");
            return (Criteria) this;
        }

        public Criteria andIdentityIsNotNull() {
            addCriterion("identity is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityEqualTo(String value) {
            addCriterion("identity =", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotEqualTo(String value) {
            addCriterion("identity <>", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityGreaterThan(String value) {
            addCriterion("identity >", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityGreaterThanOrEqualTo(String value) {
            addCriterion("identity >=", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLessThan(String value) {
            addCriterion("identity <", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLessThanOrEqualTo(String value) {
            addCriterion("identity <=", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityLike(String value) {
            addCriterion("identity like", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotLike(String value) {
            addCriterion("identity not like", value, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityIn(List<String> values) {
            addCriterion("identity in", values, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotIn(List<String> values) {
            addCriterion("identity not in", values, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityBetween(String value1, String value2) {
            addCriterion("identity between", value1, value2, "identity");
            return (Criteria) this;
        }

        public Criteria andIdentityNotBetween(String value1, String value2) {
            addCriterion("identity not between", value1, value2, "identity");
            return (Criteria) this;
        }

        public Criteria isFinishedOffline(Boolean isFinished) {
            if(BooleanUtils.isTrue(isFinished)) {
                addCriterion("finish_period_offline >= period_offline");
            }else{
                addCriterion("finish_period_offline < period_offline");
            }
            return (Criteria) this;
        }

        public Criteria isFinishedOnline(Boolean isFinished) {
            if(BooleanUtils.isTrue(isFinished)) {
                addCriterion("finish_period_online >= period_online");
            }else{
                addCriterion("finish_period_online < period_online");
            }
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIsNull() {
            addCriterion("lp_work_time is null");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIsNotNull() {
            addCriterion("lp_work_time is not null");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time =", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time <>", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("lp_work_time >", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time >=", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeLessThan(Date value) {
            addCriterionForJDBCDate("lp_work_time <", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lp_work_time <=", value, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeIn(List<Date> values) {
            addCriterionForJDBCDate("lp_work_time in", values, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("lp_work_time not in", values, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lp_work_time between", value1, value2, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andLpWorkTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lp_work_time not between", value1, value2, "lpWorkTime");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineIsNull() {
            addCriterion("period_offline is null");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineIsNotNull() {
            addCriterion("period_offline is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineEqualTo(BigDecimal value) {
            addCriterion("period_offline =", value, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineNotEqualTo(BigDecimal value) {
            addCriterion("period_offline <>", value, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineGreaterThan(BigDecimal value) {
            addCriterion("period_offline >", value, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("period_offline >=", value, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineLessThan(BigDecimal value) {
            addCriterion("period_offline <", value, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("period_offline <=", value, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineIn(List<BigDecimal> values) {
            addCriterion("period_offline in", values, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineNotIn(List<BigDecimal> values) {
            addCriterion("period_offline not in", values, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period_offline between", value1, value2, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOfflineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period_offline not between", value1, value2, "periodOffline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineIsNull() {
            addCriterion("period_online is null");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineIsNotNull() {
            addCriterion("period_online is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineEqualTo(BigDecimal value) {
            addCriterion("period_online =", value, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineNotEqualTo(BigDecimal value) {
            addCriterion("period_online <>", value, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineGreaterThan(BigDecimal value) {
            addCriterion("period_online >", value, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("period_online >=", value, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineLessThan(BigDecimal value) {
            addCriterion("period_online <", value, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("period_online <=", value, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineIn(List<BigDecimal> values) {
            addCriterion("period_online in", values, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineNotIn(List<BigDecimal> values) {
            addCriterion("period_online not in", values, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period_online between", value1, value2, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andPeriodOnlineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("period_online not between", value1, value2, "periodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineIsNull() {
            addCriterion("finish_period_offline is null");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineIsNotNull() {
            addCriterion("finish_period_offline is not null");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineEqualTo(BigDecimal value) {
            addCriterion("finish_period_offline =", value, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineNotEqualTo(BigDecimal value) {
            addCriterion("finish_period_offline <>", value, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineGreaterThan(BigDecimal value) {
            addCriterion("finish_period_offline >", value, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_period_offline >=", value, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineLessThan(BigDecimal value) {
            addCriterion("finish_period_offline <", value, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_period_offline <=", value, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineIn(List<BigDecimal> values) {
            addCriterion("finish_period_offline in", values, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineNotIn(List<BigDecimal> values) {
            addCriterion("finish_period_offline not in", values, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_period_offline between", value1, value2, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOfflineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_period_offline not between", value1, value2, "finishPeriodOffline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineIsNull() {
            addCriterion("finish_period_online is null");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineIsNotNull() {
            addCriterion("finish_period_online is not null");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineEqualTo(BigDecimal value) {
            addCriterion("finish_period_online =", value, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineNotEqualTo(BigDecimal value) {
            addCriterion("finish_period_online <>", value, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineGreaterThan(BigDecimal value) {
            addCriterion("finish_period_online >", value, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_period_online >=", value, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineLessThan(BigDecimal value) {
            addCriterion("finish_period_online <", value, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineLessThanOrEqualTo(BigDecimal value) {
            addCriterion("finish_period_online <=", value, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineIn(List<BigDecimal> values) {
            addCriterion("finish_period_online in", values, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineNotIn(List<BigDecimal> values) {
            addCriterion("finish_period_online not in", values, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_period_online between", value1, value2, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andFinishPeriodOnlineNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finish_period_online not between", value1, value2, "finishPeriodOnline");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodIsNull() {
            addCriterion("special_period is null");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodIsNotNull() {
            addCriterion("special_period is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodEqualTo(BigDecimal value) {
            addCriterion("special_period =", value, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodNotEqualTo(BigDecimal value) {
            addCriterion("special_period <>", value, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodGreaterThan(BigDecimal value) {
            addCriterion("special_period >", value, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("special_period >=", value, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodLessThan(BigDecimal value) {
            addCriterion("special_period <", value, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("special_period <=", value, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodIn(List<BigDecimal> values) {
            addCriterion("special_period in", values, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodNotIn(List<BigDecimal> values) {
            addCriterion("special_period not in", values, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("special_period between", value1, value2, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andSpecialPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("special_period not between", value1, value2, "specialPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodIsNull() {
            addCriterion("daily_period is null");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodIsNotNull() {
            addCriterion("daily_period is not null");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodEqualTo(BigDecimal value) {
            addCriterion("daily_period =", value, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodNotEqualTo(BigDecimal value) {
            addCriterion("daily_period <>", value, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodGreaterThan(BigDecimal value) {
            addCriterion("daily_period >", value, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("daily_period >=", value, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodLessThan(BigDecimal value) {
            addCriterion("daily_period <", value, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("daily_period <=", value, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodIn(List<BigDecimal> values) {
            addCriterion("daily_period in", values, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodNotIn(List<BigDecimal> values) {
            addCriterion("daily_period not in", values, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("daily_period between", value1, value2, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andDailyPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("daily_period not between", value1, value2, "dailyPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodIsNull() {
            addCriterion("unit_period is null");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodIsNotNull() {
            addCriterion("unit_period is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodEqualTo(BigDecimal value) {
            addCriterion("unit_period =", value, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodNotEqualTo(BigDecimal value) {
            addCriterion("unit_period <>", value, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodGreaterThan(BigDecimal value) {
            addCriterion("unit_period >", value, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_period >=", value, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodLessThan(BigDecimal value) {
            addCriterion("unit_period <", value, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_period <=", value, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodIn(List<BigDecimal> values) {
            addCriterion("unit_period in", values, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodNotIn(List<BigDecimal> values) {
            addCriterion("unit_period not in", values, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_period between", value1, value2, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUnitPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_period not between", value1, value2, "unitPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodIsNull() {
            addCriterion("upper_period is null");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodIsNotNull() {
            addCriterion("upper_period is not null");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodEqualTo(BigDecimal value) {
            addCriterion("upper_period =", value, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodNotEqualTo(BigDecimal value) {
            addCriterion("upper_period <>", value, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodGreaterThan(BigDecimal value) {
            addCriterion("upper_period >", value, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("upper_period >=", value, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodLessThan(BigDecimal value) {
            addCriterion("upper_period <", value, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("upper_period <=", value, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodIn(List<BigDecimal> values) {
            addCriterion("upper_period in", values, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodNotIn(List<BigDecimal> values) {
            addCriterion("upper_period not in", values, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("upper_period between", value1, value2, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andUpperPeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("upper_period not between", value1, value2, "upperPeriod");
            return (Criteria) this;
        }

        public Criteria andHasArchivedIsNull() {
            addCriterion("has_archived is null");
            return (Criteria) this;
        }

        public Criteria andHasArchivedIsNotNull() {
            addCriterion("has_archived is not null");
            return (Criteria) this;
        }

        public Criteria andHasArchivedEqualTo(Boolean value) {
            addCriterion("has_archived =", value, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedNotEqualTo(Boolean value) {
            addCriterion("has_archived <>", value, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedGreaterThan(Boolean value) {
            addCriterion("has_archived >", value, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_archived >=", value, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedLessThan(Boolean value) {
            addCriterion("has_archived <", value, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedLessThanOrEqualTo(Boolean value) {
            addCriterion("has_archived <=", value, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedIn(List<Boolean> values) {
            addCriterion("has_archived in", values, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedNotIn(List<Boolean> values) {
            addCriterion("has_archived not in", values, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedBetween(Boolean value1, Boolean value2) {
            addCriterion("has_archived between", value1, value2, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andHasArchivedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_archived not between", value1, value2, "hasArchived");
            return (Criteria) this;
        }

        public Criteria andIsQuitIsNull() {
            addCriterion("is_quit is null");
            return (Criteria) this;
        }

        public Criteria andIsQuitIsNotNull() {
            addCriterion("is_quit is not null");
            return (Criteria) this;
        }

        public Criteria andIsQuitEqualTo(Boolean value) {
            addCriterion("is_quit =", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotEqualTo(Boolean value) {
            addCriterion("is_quit <>", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitGreaterThan(Boolean value) {
            addCriterion("is_quit >", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_quit >=", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitLessThan(Boolean value) {
            addCriterion("is_quit <", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitLessThanOrEqualTo(Boolean value) {
            addCriterion("is_quit <=", value, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitIn(List<Boolean> values) {
            addCriterion("is_quit in", values, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotIn(List<Boolean> values) {
            addCriterion("is_quit not in", values, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitBetween(Boolean value1, Boolean value2) {
            addCriterion("is_quit between", value1, value2, "isQuit");
            return (Criteria) this;
        }

        public Criteria andIsQuitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_quit not between", value1, value2, "isQuit");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
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