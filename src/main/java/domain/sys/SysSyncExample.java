package domain.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysSyncExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysSyncExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
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

        public Criteria andAutoStartIsNull() {
            addCriterion("auto_start is null");
            return (Criteria) this;
        }

        public Criteria andAutoStartIsNotNull() {
            addCriterion("auto_start is not null");
            return (Criteria) this;
        }

        public Criteria andAutoStartEqualTo(Boolean value) {
            addCriterion("auto_start =", value, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartNotEqualTo(Boolean value) {
            addCriterion("auto_start <>", value, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartGreaterThan(Boolean value) {
            addCriterion("auto_start >", value, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("auto_start >=", value, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartLessThan(Boolean value) {
            addCriterion("auto_start <", value, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartLessThanOrEqualTo(Boolean value) {
            addCriterion("auto_start <=", value, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartIn(List<Boolean> values) {
            addCriterion("auto_start in", values, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartNotIn(List<Boolean> values) {
            addCriterion("auto_start not in", values, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_start between", value1, value2, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_start not between", value1, value2, "autoStart");
            return (Criteria) this;
        }

        public Criteria andAutoStopIsNull() {
            addCriterion("auto_stop is null");
            return (Criteria) this;
        }

        public Criteria andAutoStopIsNotNull() {
            addCriterion("auto_stop is not null");
            return (Criteria) this;
        }

        public Criteria andAutoStopEqualTo(Boolean value) {
            addCriterion("auto_stop =", value, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopNotEqualTo(Boolean value) {
            addCriterion("auto_stop <>", value, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopGreaterThan(Boolean value) {
            addCriterion("auto_stop >", value, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopGreaterThanOrEqualTo(Boolean value) {
            addCriterion("auto_stop >=", value, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopLessThan(Boolean value) {
            addCriterion("auto_stop <", value, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopLessThanOrEqualTo(Boolean value) {
            addCriterion("auto_stop <=", value, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopIn(List<Boolean> values) {
            addCriterion("auto_stop in", values, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopNotIn(List<Boolean> values) {
            addCriterion("auto_stop not in", values, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_stop between", value1, value2, "autoStop");
            return (Criteria) this;
        }

        public Criteria andAutoStopNotBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_stop not between", value1, value2, "autoStop");
            return (Criteria) this;
        }

        public Criteria andIsStopIsNull() {
            addCriterion("is_stop is null");
            return (Criteria) this;
        }

        public Criteria andIsStopIsNotNull() {
            addCriterion("is_stop is not null");
            return (Criteria) this;
        }

        public Criteria andIsStopEqualTo(Boolean value) {
            addCriterion("is_stop =", value, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopNotEqualTo(Boolean value) {
            addCriterion("is_stop <>", value, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopGreaterThan(Boolean value) {
            addCriterion("is_stop >", value, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_stop >=", value, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopLessThan(Boolean value) {
            addCriterion("is_stop <", value, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopLessThanOrEqualTo(Boolean value) {
            addCriterion("is_stop <=", value, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopIn(List<Boolean> values) {
            addCriterion("is_stop in", values, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopNotIn(List<Boolean> values) {
            addCriterion("is_stop not in", values, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopBetween(Boolean value1, Boolean value2) {
            addCriterion("is_stop between", value1, value2, "isStop");
            return (Criteria) this;
        }

        public Criteria andIsStopNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_stop not between", value1, value2, "isStop");
            return (Criteria) this;
        }

        public Criteria andTotalPageIsNull() {
            addCriterion("total_page is null");
            return (Criteria) this;
        }

        public Criteria andTotalPageIsNotNull() {
            addCriterion("total_page is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPageEqualTo(Integer value) {
            addCriterion("total_page =", value, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageNotEqualTo(Integer value) {
            addCriterion("total_page <>", value, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageGreaterThan(Integer value) {
            addCriterion("total_page >", value, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_page >=", value, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageLessThan(Integer value) {
            addCriterion("total_page <", value, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageLessThanOrEqualTo(Integer value) {
            addCriterion("total_page <=", value, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageIn(List<Integer> values) {
            addCriterion("total_page in", values, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageNotIn(List<Integer> values) {
            addCriterion("total_page not in", values, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageBetween(Integer value1, Integer value2) {
            addCriterion("total_page between", value1, value2, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalPageNotBetween(Integer value1, Integer value2) {
            addCriterion("total_page not between", value1, value2, "totalPage");
            return (Criteria) this;
        }

        public Criteria andTotalCountIsNull() {
            addCriterion("total_count is null");
            return (Criteria) this;
        }

        public Criteria andTotalCountIsNotNull() {
            addCriterion("total_count is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCountEqualTo(Integer value) {
            addCriterion("total_count =", value, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountNotEqualTo(Integer value) {
            addCriterion("total_count <>", value, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountGreaterThan(Integer value) {
            addCriterion("total_count >", value, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_count >=", value, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountLessThan(Integer value) {
            addCriterion("total_count <", value, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("total_count <=", value, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountIn(List<Integer> values) {
            addCriterion("total_count in", values, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountNotIn(List<Integer> values) {
            addCriterion("total_count not in", values, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("total_count between", value1, value2, "totalCount");
            return (Criteria) this;
        }

        public Criteria andTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("total_count not between", value1, value2, "totalCount");
            return (Criteria) this;
        }

        public Criteria andCurrentPageIsNull() {
            addCriterion("current_page is null");
            return (Criteria) this;
        }

        public Criteria andCurrentPageIsNotNull() {
            addCriterion("current_page is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentPageEqualTo(Integer value) {
            addCriterion("current_page =", value, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageNotEqualTo(Integer value) {
            addCriterion("current_page <>", value, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageGreaterThan(Integer value) {
            addCriterion("current_page >", value, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_page >=", value, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageLessThan(Integer value) {
            addCriterion("current_page <", value, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageLessThanOrEqualTo(Integer value) {
            addCriterion("current_page <=", value, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageIn(List<Integer> values) {
            addCriterion("current_page in", values, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageNotIn(List<Integer> values) {
            addCriterion("current_page not in", values, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageBetween(Integer value1, Integer value2) {
            addCriterion("current_page between", value1, value2, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentPageNotBetween(Integer value1, Integer value2) {
            addCriterion("current_page not between", value1, value2, "currentPage");
            return (Criteria) this;
        }

        public Criteria andCurrentCountIsNull() {
            addCriterion("current_count is null");
            return (Criteria) this;
        }

        public Criteria andCurrentCountIsNotNull() {
            addCriterion("current_count is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentCountEqualTo(Integer value) {
            addCriterion("current_count =", value, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountNotEqualTo(Integer value) {
            addCriterion("current_count <>", value, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountGreaterThan(Integer value) {
            addCriterion("current_count >", value, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_count >=", value, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountLessThan(Integer value) {
            addCriterion("current_count <", value, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountLessThanOrEqualTo(Integer value) {
            addCriterion("current_count <=", value, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountIn(List<Integer> values) {
            addCriterion("current_count in", values, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountNotIn(List<Integer> values) {
            addCriterion("current_count not in", values, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountBetween(Integer value1, Integer value2) {
            addCriterion("current_count between", value1, value2, "currentCount");
            return (Criteria) this;
        }

        public Criteria andCurrentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("current_count not between", value1, value2, "currentCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountIsNull() {
            addCriterion("insert_count is null");
            return (Criteria) this;
        }

        public Criteria andInsertCountIsNotNull() {
            addCriterion("insert_count is not null");
            return (Criteria) this;
        }

        public Criteria andInsertCountEqualTo(Integer value) {
            addCriterion("insert_count =", value, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountNotEqualTo(Integer value) {
            addCriterion("insert_count <>", value, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountGreaterThan(Integer value) {
            addCriterion("insert_count >", value, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("insert_count >=", value, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountLessThan(Integer value) {
            addCriterion("insert_count <", value, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountLessThanOrEqualTo(Integer value) {
            addCriterion("insert_count <=", value, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountIn(List<Integer> values) {
            addCriterion("insert_count in", values, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountNotIn(List<Integer> values) {
            addCriterion("insert_count not in", values, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountBetween(Integer value1, Integer value2) {
            addCriterion("insert_count between", value1, value2, "insertCount");
            return (Criteria) this;
        }

        public Criteria andInsertCountNotBetween(Integer value1, Integer value2) {
            addCriterion("insert_count not between", value1, value2, "insertCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountIsNull() {
            addCriterion("update_count is null");
            return (Criteria) this;
        }

        public Criteria andUpdateCountIsNotNull() {
            addCriterion("update_count is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateCountEqualTo(Integer value) {
            addCriterion("update_count =", value, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountNotEqualTo(Integer value) {
            addCriterion("update_count <>", value, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountGreaterThan(Integer value) {
            addCriterion("update_count >", value, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_count >=", value, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountLessThan(Integer value) {
            addCriterion("update_count <", value, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountLessThanOrEqualTo(Integer value) {
            addCriterion("update_count <=", value, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountIn(List<Integer> values) {
            addCriterion("update_count in", values, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountNotIn(List<Integer> values) {
            addCriterion("update_count not in", values, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountBetween(Integer value1, Integer value2) {
            addCriterion("update_count between", value1, value2, "updateCount");
            return (Criteria) this;
        }

        public Criteria andUpdateCountNotBetween(Integer value1, Integer value2) {
            addCriterion("update_count not between", value1, value2, "updateCount");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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