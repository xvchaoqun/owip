package domain.sc.scMatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ScMatterItemViewExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScMatterItemViewExample() {
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

        public Criteria andMatterIdIsNull() {
            addCriterion("matter_id is null");
            return (Criteria) this;
        }

        public Criteria andMatterIdIsNotNull() {
            addCriterion("matter_id is not null");
            return (Criteria) this;
        }

        public Criteria andMatterIdEqualTo(Integer value) {
            addCriterion("matter_id =", value, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdNotEqualTo(Integer value) {
            addCriterion("matter_id <>", value, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdGreaterThan(Integer value) {
            addCriterion("matter_id >", value, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("matter_id >=", value, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdLessThan(Integer value) {
            addCriterion("matter_id <", value, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdLessThanOrEqualTo(Integer value) {
            addCriterion("matter_id <=", value, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdIn(List<Integer> values) {
            addCriterion("matter_id in", values, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdNotIn(List<Integer> values) {
            addCriterion("matter_id not in", values, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdBetween(Integer value1, Integer value2) {
            addCriterion("matter_id between", value1, value2, "matterId");
            return (Criteria) this;
        }

        public Criteria andMatterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("matter_id not between", value1, value2, "matterId");
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

        public Criteria andRealHandTimeIsNull() {
            addCriterion("real_hand_time is null");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeIsNotNull() {
            addCriterion("real_hand_time is not null");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeEqualTo(Date value) {
            addCriterionForJDBCDate("real_hand_time =", value, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("real_hand_time <>", value, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("real_hand_time >", value, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_hand_time >=", value, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeLessThan(Date value) {
            addCriterionForJDBCDate("real_hand_time <", value, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("real_hand_time <=", value, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeIn(List<Date> values) {
            addCriterionForJDBCDate("real_hand_time in", values, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("real_hand_time not in", values, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_hand_time between", value1, value2, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andRealHandTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("real_hand_time not between", value1, value2, "realHandTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeIsNull() {
            addCriterion("fill_time is null");
            return (Criteria) this;
        }

        public Criteria andFillTimeIsNotNull() {
            addCriterion("fill_time is not null");
            return (Criteria) this;
        }

        public Criteria andFillTimeEqualTo(Date value) {
            addCriterionForJDBCDate("fill_time =", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("fill_time <>", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("fill_time >", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fill_time >=", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeLessThan(Date value) {
            addCriterionForJDBCDate("fill_time <", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("fill_time <=", value, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeIn(List<Date> values) {
            addCriterionForJDBCDate("fill_time in", values, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("fill_time not in", values, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fill_time between", value1, value2, "fillTime");
            return (Criteria) this;
        }

        public Criteria andFillTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("fill_time not between", value1, value2, "fillTime");
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

        public Criteria andTransferIdIsNull() {
            addCriterion("transfer_id is null");
            return (Criteria) this;
        }

        public Criteria andTransferIdIsNotNull() {
            addCriterion("transfer_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransferIdEqualTo(Integer value) {
            addCriterion("transfer_id =", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotEqualTo(Integer value) {
            addCriterion("transfer_id <>", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdGreaterThan(Integer value) {
            addCriterion("transfer_id >", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_id >=", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLessThan(Integer value) {
            addCriterion("transfer_id <", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_id <=", value, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdIn(List<Integer> values) {
            addCriterion("transfer_id in", values, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotIn(List<Integer> values) {
            addCriterion("transfer_id not in", values, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdBetween(Integer value1, Integer value2) {
            addCriterion("transfer_id between", value1, value2, "transferId");
            return (Criteria) this;
        }

        public Criteria andTransferIdNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_id not between", value1, value2, "transferId");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Boolean value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Boolean value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Boolean value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Boolean value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Boolean> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Boolean> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIsNull() {
            addCriterion("draw_time is null");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIsNotNull() {
            addCriterion("draw_time is not null");
            return (Criteria) this;
        }

        public Criteria andDrawTimeEqualTo(Date value) {
            addCriterion("draw_time =", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotEqualTo(Date value) {
            addCriterion("draw_time <>", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeGreaterThan(Date value) {
            addCriterion("draw_time >", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("draw_time >=", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLessThan(Date value) {
            addCriterion("draw_time <", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeLessThanOrEqualTo(Date value) {
            addCriterion("draw_time <=", value, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeIn(List<Date> values) {
            addCriterion("draw_time in", values, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotIn(List<Date> values) {
            addCriterion("draw_time not in", values, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeBetween(Date value1, Date value2) {
            addCriterion("draw_time between", value1, value2, "drawTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimeNotBetween(Date value1, Date value2) {
            addCriterion("draw_time not between", value1, value2, "drawTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeIsNull() {
            addCriterion("hand_time is null");
            return (Criteria) this;
        }

        public Criteria andHandTimeIsNotNull() {
            addCriterion("hand_time is not null");
            return (Criteria) this;
        }

        public Criteria andHandTimeEqualTo(Date value) {
            addCriterion("hand_time =", value, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeNotEqualTo(Date value) {
            addCriterion("hand_time <>", value, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeGreaterThan(Date value) {
            addCriterion("hand_time >", value, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("hand_time >=", value, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeLessThan(Date value) {
            addCriterion("hand_time <", value, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeLessThanOrEqualTo(Date value) {
            addCriterion("hand_time <=", value, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeIn(List<Date> values) {
            addCriterion("hand_time in", values, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeNotIn(List<Date> values) {
            addCriterion("hand_time not in", values, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeBetween(Date value1, Date value2) {
            addCriterion("hand_time between", value1, value2, "handTime");
            return (Criteria) this;
        }

        public Criteria andHandTimeNotBetween(Date value1, Date value2) {
            addCriterion("hand_time not between", value1, value2, "handTime");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedIsNull() {
            addCriterion("matter_is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedIsNotNull() {
            addCriterion("matter_is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedEqualTo(Boolean value) {
            addCriterion("matter_is_deleted =", value, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedNotEqualTo(Boolean value) {
            addCriterion("matter_is_deleted <>", value, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedGreaterThan(Boolean value) {
            addCriterion("matter_is_deleted >", value, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("matter_is_deleted >=", value, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedLessThan(Boolean value) {
            addCriterion("matter_is_deleted <", value, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("matter_is_deleted <=", value, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedIn(List<Boolean> values) {
            addCriterion("matter_is_deleted in", values, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedNotIn(List<Boolean> values) {
            addCriterion("matter_is_deleted not in", values, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("matter_is_deleted between", value1, value2, "matterIsDeleted");
            return (Criteria) this;
        }

        public Criteria andMatterIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("matter_is_deleted not between", value1, value2, "matterIsDeleted");
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

        public Criteria andTransferDateIsNull() {
            addCriterion("transfer_date is null");
            return (Criteria) this;
        }

        public Criteria andTransferDateIsNotNull() {
            addCriterion("transfer_date is not null");
            return (Criteria) this;
        }

        public Criteria andTransferDateEqualTo(Date value) {
            addCriterionForJDBCDate("transfer_date =", value, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("transfer_date <>", value, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateGreaterThan(Date value) {
            addCriterionForJDBCDate("transfer_date >", value, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("transfer_date >=", value, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateLessThan(Date value) {
            addCriterionForJDBCDate("transfer_date <", value, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("transfer_date <=", value, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateIn(List<Date> values) {
            addCriterionForJDBCDate("transfer_date in", values, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("transfer_date not in", values, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("transfer_date between", value1, value2, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("transfer_date not between", value1, value2, "transferDate");
            return (Criteria) this;
        }

        public Criteria andTransferLocationIsNull() {
            addCriterion("transfer_location is null");
            return (Criteria) this;
        }

        public Criteria andTransferLocationIsNotNull() {
            addCriterion("transfer_location is not null");
            return (Criteria) this;
        }

        public Criteria andTransferLocationEqualTo(String value) {
            addCriterion("transfer_location =", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationNotEqualTo(String value) {
            addCriterion("transfer_location <>", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationGreaterThan(String value) {
            addCriterion("transfer_location >", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationGreaterThanOrEqualTo(String value) {
            addCriterion("transfer_location >=", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationLessThan(String value) {
            addCriterion("transfer_location <", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationLessThanOrEqualTo(String value) {
            addCriterion("transfer_location <=", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationLike(String value) {
            addCriterion("transfer_location like", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationNotLike(String value) {
            addCriterion("transfer_location not like", value, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationIn(List<String> values) {
            addCriterion("transfer_location in", values, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationNotIn(List<String> values) {
            addCriterion("transfer_location not in", values, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationBetween(String value1, String value2) {
            addCriterion("transfer_location between", value1, value2, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferLocationNotBetween(String value1, String value2) {
            addCriterion("transfer_location not between", value1, value2, "transferLocation");
            return (Criteria) this;
        }

        public Criteria andTransferReasonIsNull() {
            addCriterion("transfer_reason is null");
            return (Criteria) this;
        }

        public Criteria andTransferReasonIsNotNull() {
            addCriterion("transfer_reason is not null");
            return (Criteria) this;
        }

        public Criteria andTransferReasonEqualTo(String value) {
            addCriterion("transfer_reason =", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonNotEqualTo(String value) {
            addCriterion("transfer_reason <>", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonGreaterThan(String value) {
            addCriterion("transfer_reason >", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonGreaterThanOrEqualTo(String value) {
            addCriterion("transfer_reason >=", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonLessThan(String value) {
            addCriterion("transfer_reason <", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonLessThanOrEqualTo(String value) {
            addCriterion("transfer_reason <=", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonLike(String value) {
            addCriterion("transfer_reason like", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonNotLike(String value) {
            addCriterion("transfer_reason not like", value, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonIn(List<String> values) {
            addCriterion("transfer_reason in", values, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonNotIn(List<String> values) {
            addCriterion("transfer_reason not in", values, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonBetween(String value1, String value2) {
            addCriterion("transfer_reason between", value1, value2, "transferReason");
            return (Criteria) this;
        }

        public Criteria andTransferReasonNotBetween(String value1, String value2) {
            addCriterion("transfer_reason not between", value1, value2, "transferReason");
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