package domain.unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UnitViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UnitViewExample() {
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

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdIsNull() {
            addCriterion("dispatch_unit_id is null");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdIsNotNull() {
            addCriterion("dispatch_unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdEqualTo(Integer value) {
            addCriterion("dispatch_unit_id =", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdNotEqualTo(Integer value) {
            addCriterion("dispatch_unit_id <>", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdGreaterThan(Integer value) {
            addCriterion("dispatch_unit_id >", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dispatch_unit_id >=", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdLessThan(Integer value) {
            addCriterion("dispatch_unit_id <", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("dispatch_unit_id <=", value, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdIn(List<Integer> values) {
            addCriterion("dispatch_unit_id in", values, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdNotIn(List<Integer> values) {
            addCriterion("dispatch_unit_id not in", values, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_unit_id between", value1, value2, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andDispatchUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dispatch_unit_id not between", value1, value2, "dispatchUnitId");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNull() {
            addCriterion("work_time is null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIsNotNull() {
            addCriterion("work_time is not null");
            return (Criteria) this;
        }

        public Criteria andWorkTimeEqualTo(Date value) {
            addCriterion("work_time =", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotEqualTo(Date value) {
            addCriterion("work_time <>", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThan(Date value) {
            addCriterion("work_time >", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("work_time >=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThan(Date value) {
            addCriterion("work_time <", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeLessThanOrEqualTo(Date value) {
            addCriterion("work_time <=", value, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeIn(List<Date> values) {
            addCriterion("work_time in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotIn(List<Date> values) {
            addCriterion("work_time not in", values, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeBetween(Date value1, Date value2) {
            addCriterion("work_time between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andWorkTimeNotBetween(Date value1, Date value2) {
            addCriterion("work_time not between", value1, value2, "workTime");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andNotStatPostIsNull() {
            addCriterion("not_stat_post is null");
            return (Criteria) this;
        }

        public Criteria andNotStatPostIsNotNull() {
            addCriterion("not_stat_post is not null");
            return (Criteria) this;
        }

        public Criteria andNotStatPostEqualTo(Boolean value) {
            addCriterion("not_stat_post =", value, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostNotEqualTo(Boolean value) {
            addCriterion("not_stat_post <>", value, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostGreaterThan(Boolean value) {
            addCriterion("not_stat_post >", value, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostGreaterThanOrEqualTo(Boolean value) {
            addCriterion("not_stat_post >=", value, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostLessThan(Boolean value) {
            addCriterion("not_stat_post <", value, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostLessThanOrEqualTo(Boolean value) {
            addCriterion("not_stat_post <=", value, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostIn(List<Boolean> values) {
            addCriterion("not_stat_post in", values, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostNotIn(List<Boolean> values) {
            addCriterion("not_stat_post not in", values, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostBetween(Boolean value1, Boolean value2) {
            addCriterion("not_stat_post between", value1, value2, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andNotStatPostNotBetween(Boolean value1, Boolean value2) {
            addCriterion("not_stat_post not between", value1, value2, "notStatPost");
            return (Criteria) this;
        }

        public Criteria andMainPostCountIsNull() {
            addCriterion("main_post_count is null");
            return (Criteria) this;
        }

        public Criteria andMainPostCountIsNotNull() {
            addCriterion("main_post_count is not null");
            return (Criteria) this;
        }

        public Criteria andMainPostCountEqualTo(BigDecimal value) {
            addCriterion("main_post_count =", value, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountNotEqualTo(BigDecimal value) {
            addCriterion("main_post_count <>", value, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountGreaterThan(BigDecimal value) {
            addCriterion("main_post_count >", value, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("main_post_count >=", value, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountLessThan(BigDecimal value) {
            addCriterion("main_post_count <", value, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("main_post_count <=", value, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountIn(List<BigDecimal> values) {
            addCriterion("main_post_count in", values, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountNotIn(List<BigDecimal> values) {
            addCriterion("main_post_count not in", values, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_post_count between", value1, value2, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andMainPostCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_post_count not between", value1, value2, "mainPostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountIsNull() {
            addCriterion("vice_post_count is null");
            return (Criteria) this;
        }

        public Criteria andVicePostCountIsNotNull() {
            addCriterion("vice_post_count is not null");
            return (Criteria) this;
        }

        public Criteria andVicePostCountEqualTo(BigDecimal value) {
            addCriterion("vice_post_count =", value, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountNotEqualTo(BigDecimal value) {
            addCriterion("vice_post_count <>", value, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountGreaterThan(BigDecimal value) {
            addCriterion("vice_post_count >", value, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_post_count >=", value, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountLessThan(BigDecimal value) {
            addCriterion("vice_post_count <", value, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_post_count <=", value, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountIn(List<BigDecimal> values) {
            addCriterion("vice_post_count in", values, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountNotIn(List<BigDecimal> values) {
            addCriterion("vice_post_count not in", values, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_post_count between", value1, value2, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andVicePostCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_post_count not between", value1, value2, "vicePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountIsNull() {
            addCriterion("none_post_count is null");
            return (Criteria) this;
        }

        public Criteria andNonePostCountIsNotNull() {
            addCriterion("none_post_count is not null");
            return (Criteria) this;
        }

        public Criteria andNonePostCountEqualTo(BigDecimal value) {
            addCriterion("none_post_count =", value, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountNotEqualTo(BigDecimal value) {
            addCriterion("none_post_count <>", value, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountGreaterThan(BigDecimal value) {
            addCriterion("none_post_count >", value, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("none_post_count >=", value, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountLessThan(BigDecimal value) {
            addCriterion("none_post_count <", value, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("none_post_count <=", value, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountIn(List<BigDecimal> values) {
            addCriterion("none_post_count in", values, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountNotIn(List<BigDecimal> values) {
            addCriterion("none_post_count not in", values, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("none_post_count between", value1, value2, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andNonePostCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("none_post_count not between", value1, value2, "nonePostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountIsNull() {
            addCriterion("main_kj_post_count is null");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountIsNotNull() {
            addCriterion("main_kj_post_count is not null");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountEqualTo(BigDecimal value) {
            addCriterion("main_kj_post_count =", value, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountNotEqualTo(BigDecimal value) {
            addCriterion("main_kj_post_count <>", value, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountGreaterThan(BigDecimal value) {
            addCriterion("main_kj_post_count >", value, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("main_kj_post_count >=", value, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountLessThan(BigDecimal value) {
            addCriterion("main_kj_post_count <", value, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("main_kj_post_count <=", value, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountIn(List<BigDecimal> values) {
            addCriterion("main_kj_post_count in", values, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountNotIn(List<BigDecimal> values) {
            addCriterion("main_kj_post_count not in", values, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_kj_post_count between", value1, value2, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainKjPostCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_kj_post_count not between", value1, value2, "mainKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountIsNull() {
            addCriterion("vice_kj_post_count is null");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountIsNotNull() {
            addCriterion("vice_kj_post_count is not null");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountEqualTo(BigDecimal value) {
            addCriterion("vice_kj_post_count =", value, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountNotEqualTo(BigDecimal value) {
            addCriterion("vice_kj_post_count <>", value, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountGreaterThan(BigDecimal value) {
            addCriterion("vice_kj_post_count >", value, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_kj_post_count >=", value, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountLessThan(BigDecimal value) {
            addCriterion("vice_kj_post_count <", value, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_kj_post_count <=", value, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountIn(List<BigDecimal> values) {
            addCriterion("vice_kj_post_count in", values, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountNotIn(List<BigDecimal> values) {
            addCriterion("vice_kj_post_count not in", values, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_kj_post_count between", value1, value2, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andViceKjPostCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_kj_post_count not between", value1, value2, "viceKjPostCount");
            return (Criteria) this;
        }

        public Criteria andMainCountIsNull() {
            addCriterion("main_count is null");
            return (Criteria) this;
        }

        public Criteria andMainCountIsNotNull() {
            addCriterion("main_count is not null");
            return (Criteria) this;
        }

        public Criteria andMainCountEqualTo(BigDecimal value) {
            addCriterion("main_count =", value, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountNotEqualTo(BigDecimal value) {
            addCriterion("main_count <>", value, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountGreaterThan(BigDecimal value) {
            addCriterion("main_count >", value, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("main_count >=", value, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountLessThan(BigDecimal value) {
            addCriterion("main_count <", value, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("main_count <=", value, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountIn(List<BigDecimal> values) {
            addCriterion("main_count in", values, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountNotIn(List<BigDecimal> values) {
            addCriterion("main_count not in", values, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_count between", value1, value2, "mainCount");
            return (Criteria) this;
        }

        public Criteria andMainCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_count not between", value1, value2, "mainCount");
            return (Criteria) this;
        }

        public Criteria andViceCountIsNull() {
            addCriterion("vice_count is null");
            return (Criteria) this;
        }

        public Criteria andViceCountIsNotNull() {
            addCriterion("vice_count is not null");
            return (Criteria) this;
        }

        public Criteria andViceCountEqualTo(BigDecimal value) {
            addCriterion("vice_count =", value, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountNotEqualTo(BigDecimal value) {
            addCriterion("vice_count <>", value, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountGreaterThan(BigDecimal value) {
            addCriterion("vice_count >", value, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_count >=", value, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountLessThan(BigDecimal value) {
            addCriterion("vice_count <", value, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_count <=", value, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountIn(List<BigDecimal> values) {
            addCriterion("vice_count in", values, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountNotIn(List<BigDecimal> values) {
            addCriterion("vice_count not in", values, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_count between", value1, value2, "viceCount");
            return (Criteria) this;
        }

        public Criteria andViceCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_count not between", value1, value2, "viceCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountIsNull() {
            addCriterion("main_kj_count is null");
            return (Criteria) this;
        }

        public Criteria andMainKjCountIsNotNull() {
            addCriterion("main_kj_count is not null");
            return (Criteria) this;
        }

        public Criteria andMainKjCountEqualTo(BigDecimal value) {
            addCriterion("main_kj_count =", value, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountNotEqualTo(BigDecimal value) {
            addCriterion("main_kj_count <>", value, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountGreaterThan(BigDecimal value) {
            addCriterion("main_kj_count >", value, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("main_kj_count >=", value, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountLessThan(BigDecimal value) {
            addCriterion("main_kj_count <", value, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("main_kj_count <=", value, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountIn(List<BigDecimal> values) {
            addCriterion("main_kj_count in", values, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountNotIn(List<BigDecimal> values) {
            addCriterion("main_kj_count not in", values, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_kj_count between", value1, value2, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andMainKjCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("main_kj_count not between", value1, value2, "mainKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountIsNull() {
            addCriterion("vice_kj_count is null");
            return (Criteria) this;
        }

        public Criteria andViceKjCountIsNotNull() {
            addCriterion("vice_kj_count is not null");
            return (Criteria) this;
        }

        public Criteria andViceKjCountEqualTo(BigDecimal value) {
            addCriterion("vice_kj_count =", value, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountNotEqualTo(BigDecimal value) {
            addCriterion("vice_kj_count <>", value, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountGreaterThan(BigDecimal value) {
            addCriterion("vice_kj_count >", value, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_kj_count >=", value, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountLessThan(BigDecimal value) {
            addCriterion("vice_kj_count <", value, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vice_kj_count <=", value, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountIn(List<BigDecimal> values) {
            addCriterion("vice_kj_count in", values, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountNotIn(List<BigDecimal> values) {
            addCriterion("vice_kj_count not in", values, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_kj_count between", value1, value2, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andViceKjCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vice_kj_count not between", value1, value2, "viceKjCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountIsNull() {
            addCriterion("none_count is null");
            return (Criteria) this;
        }

        public Criteria andNoneCountIsNotNull() {
            addCriterion("none_count is not null");
            return (Criteria) this;
        }

        public Criteria andNoneCountEqualTo(BigDecimal value) {
            addCriterion("none_count =", value, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountNotEqualTo(BigDecimal value) {
            addCriterion("none_count <>", value, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountGreaterThan(BigDecimal value) {
            addCriterion("none_count >", value, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("none_count >=", value, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountLessThan(BigDecimal value) {
            addCriterion("none_count <", value, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("none_count <=", value, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountIn(List<BigDecimal> values) {
            addCriterion("none_count in", values, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountNotIn(List<BigDecimal> values) {
            addCriterion("none_count not in", values, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("none_count between", value1, value2, "noneCount");
            return (Criteria) this;
        }

        public Criteria andNoneCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("none_count not between", value1, value2, "noneCount");
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