package domain.cet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CetTrainCourseViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CetTrainCourseViewExample() {
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

        public Criteria andTrainIdIsNull() {
            addCriterion("train_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainIdIsNotNull() {
            addCriterion("train_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainIdEqualTo(Integer value) {
            addCriterion("train_id =", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotEqualTo(Integer value) {
            addCriterion("train_id <>", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdGreaterThan(Integer value) {
            addCriterion("train_id >", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_id >=", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdLessThan(Integer value) {
            addCriterion("train_id <", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdLessThanOrEqualTo(Integer value) {
            addCriterion("train_id <=", value, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdIn(List<Integer> values) {
            addCriterion("train_id in", values, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotIn(List<Integer> values) {
            addCriterion("train_id not in", values, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdBetween(Integer value1, Integer value2) {
            addCriterion("train_id between", value1, value2, "trainId");
            return (Criteria) this;
        }

        public Criteria andTrainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("train_id not between", value1, value2, "trainId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(Integer value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(Integer value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(Integer value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(Integer value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<Integer> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<Integer> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
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

        public Criteria andTeacherIsNull() {
            addCriterion("teacher is null");
            return (Criteria) this;
        }

        public Criteria andTeacherIsNotNull() {
            addCriterion("teacher is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherEqualTo(String value) {
            addCriterion("teacher =", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotEqualTo(String value) {
            addCriterion("teacher <>", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherGreaterThan(String value) {
            addCriterion("teacher >", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherGreaterThanOrEqualTo(String value) {
            addCriterion("teacher >=", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLessThan(String value) {
            addCriterion("teacher <", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLessThanOrEqualTo(String value) {
            addCriterion("teacher <=", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLike(String value) {
            addCriterion("teacher like", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotLike(String value) {
            addCriterion("teacher not like", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherIn(List<String> values) {
            addCriterion("teacher in", values, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotIn(List<String> values) {
            addCriterion("teacher not in", values, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherBetween(String value1, String value2) {
            addCriterion("teacher between", value1, value2, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotBetween(String value1, String value2) {
            addCriterion("teacher not between", value1, value2, "teacher");
            return (Criteria) this;
        }

        public Criteria andApplyLimitIsNull() {
            addCriterion("apply_limit is null");
            return (Criteria) this;
        }

        public Criteria andApplyLimitIsNotNull() {
            addCriterion("apply_limit is not null");
            return (Criteria) this;
        }

        public Criteria andApplyLimitEqualTo(Integer value) {
            addCriterion("apply_limit =", value, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitNotEqualTo(Integer value) {
            addCriterion("apply_limit <>", value, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitGreaterThan(Integer value) {
            addCriterion("apply_limit >", value, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_limit >=", value, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitLessThan(Integer value) {
            addCriterion("apply_limit <", value, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitLessThanOrEqualTo(Integer value) {
            addCriterion("apply_limit <=", value, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitIn(List<Integer> values) {
            addCriterion("apply_limit in", values, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitNotIn(List<Integer> values) {
            addCriterion("apply_limit not in", values, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitBetween(Integer value1, Integer value2) {
            addCriterion("apply_limit between", value1, value2, "applyLimit");
            return (Criteria) this;
        }

        public Criteria andApplyLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_limit not between", value1, value2, "applyLimit");
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

        public Criteria andIsGlobalIsNull() {
            addCriterion("is_global is null");
            return (Criteria) this;
        }

        public Criteria andIsGlobalIsNotNull() {
            addCriterion("is_global is not null");
            return (Criteria) this;
        }

        public Criteria andIsGlobalEqualTo(Boolean value) {
            addCriterion("is_global =", value, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalNotEqualTo(Boolean value) {
            addCriterion("is_global <>", value, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalGreaterThan(Boolean value) {
            addCriterion("is_global >", value, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_global >=", value, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalLessThan(Boolean value) {
            addCriterion("is_global <", value, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalLessThanOrEqualTo(Boolean value) {
            addCriterion("is_global <=", value, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalIn(List<Boolean> values) {
            addCriterion("is_global in", values, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalNotIn(List<Boolean> values) {
            addCriterion("is_global not in", values, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalBetween(Boolean value1, Boolean value2) {
            addCriterion("is_global between", value1, value2, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andIsGlobalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_global not between", value1, value2, "isGlobal");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdIsNull() {
            addCriterion("eva_table_id is null");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdIsNotNull() {
            addCriterion("eva_table_id is not null");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdEqualTo(Integer value) {
            addCriterion("eva_table_id =", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdNotEqualTo(Integer value) {
            addCriterion("eva_table_id <>", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdGreaterThan(Integer value) {
            addCriterion("eva_table_id >", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("eva_table_id >=", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdLessThan(Integer value) {
            addCriterion("eva_table_id <", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdLessThanOrEqualTo(Integer value) {
            addCriterion("eva_table_id <=", value, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdIn(List<Integer> values) {
            addCriterion("eva_table_id in", values, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdNotIn(List<Integer> values) {
            addCriterion("eva_table_id not in", values, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdBetween(Integer value1, Integer value2) {
            addCriterion("eva_table_id between", value1, value2, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andEvaTableIdNotBetween(Integer value1, Integer value2) {
            addCriterion("eva_table_id not between", value1, value2, "evaTableId");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
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

        public Criteria andSelectedCountIsNull() {
            addCriterion("selected_count is null");
            return (Criteria) this;
        }

        public Criteria andSelectedCountIsNotNull() {
            addCriterion("selected_count is not null");
            return (Criteria) this;
        }

        public Criteria andSelectedCountEqualTo(Integer value) {
            addCriterion("selected_count =", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountNotEqualTo(Integer value) {
            addCriterion("selected_count <>", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountGreaterThan(Integer value) {
            addCriterion("selected_count >", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("selected_count >=", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountLessThan(Integer value) {
            addCriterion("selected_count <", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountLessThanOrEqualTo(Integer value) {
            addCriterion("selected_count <=", value, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountIn(List<Integer> values) {
            addCriterion("selected_count in", values, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountNotIn(List<Integer> values) {
            addCriterion("selected_count not in", values, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountBetween(Integer value1, Integer value2) {
            addCriterion("selected_count between", value1, value2, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andSelectedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("selected_count not between", value1, value2, "selectedCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIsNull() {
            addCriterion("finish_count is null");
            return (Criteria) this;
        }

        public Criteria andFinishCountIsNotNull() {
            addCriterion("finish_count is not null");
            return (Criteria) this;
        }

        public Criteria andFinishCountEqualTo(Integer value) {
            addCriterion("finish_count =", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotEqualTo(Integer value) {
            addCriterion("finish_count <>", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThan(Integer value) {
            addCriterion("finish_count >", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_count >=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThan(Integer value) {
            addCriterion("finish_count <", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountLessThanOrEqualTo(Integer value) {
            addCriterion("finish_count <=", value, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountIn(List<Integer> values) {
            addCriterion("finish_count in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotIn(List<Integer> values) {
            addCriterion("finish_count not in", values, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountBetween(Integer value1, Integer value2) {
            addCriterion("finish_count between", value1, value2, "finishCount");
            return (Criteria) this;
        }

        public Criteria andFinishCountNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_count not between", value1, value2, "finishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountIsNull() {
            addCriterion("eva_finish_count is null");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountIsNotNull() {
            addCriterion("eva_finish_count is not null");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountEqualTo(Integer value) {
            addCriterion("eva_finish_count =", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountNotEqualTo(Integer value) {
            addCriterion("eva_finish_count <>", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountGreaterThan(Integer value) {
            addCriterion("eva_finish_count >", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("eva_finish_count >=", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountLessThan(Integer value) {
            addCriterion("eva_finish_count <", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountLessThanOrEqualTo(Integer value) {
            addCriterion("eva_finish_count <=", value, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountIn(List<Integer> values) {
            addCriterion("eva_finish_count in", values, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountNotIn(List<Integer> values) {
            addCriterion("eva_finish_count not in", values, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountBetween(Integer value1, Integer value2) {
            addCriterion("eva_finish_count between", value1, value2, "evaFinishCount");
            return (Criteria) this;
        }

        public Criteria andEvaFinishCountNotBetween(Integer value1, Integer value2) {
            addCriterion("eva_finish_count not between", value1, value2, "evaFinishCount");
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