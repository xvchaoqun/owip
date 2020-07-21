package domain.dr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrOnlineInspectorLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrOnlineInspectorLogExample() {
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

        public Criteria andOnlineIdIsNull() {
            addCriterion("online_id is null");
            return (Criteria) this;
        }

        public Criteria andOnlineIdIsNotNull() {
            addCriterion("online_id is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineIdEqualTo(Integer value) {
            addCriterion("online_id =", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotEqualTo(Integer value) {
            addCriterion("online_id <>", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdGreaterThan(Integer value) {
            addCriterion("online_id >", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_id >=", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdLessThan(Integer value) {
            addCriterion("online_id <", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdLessThanOrEqualTo(Integer value) {
            addCriterion("online_id <=", value, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdIn(List<Integer> values) {
            addCriterion("online_id in", values, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotIn(List<Integer> values) {
            addCriterion("online_id not in", values, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdBetween(Integer value1, Integer value2) {
            addCriterion("online_id between", value1, value2, "onlineId");
            return (Criteria) this;
        }

        public Criteria andOnlineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("online_id not between", value1, value2, "onlineId");
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

        public Criteria andPostIdsIsNull() {
            addCriterion("post_ids is null");
            return (Criteria) this;
        }

        public Criteria andPostIdsIsNotNull() {
            addCriterion("post_ids is not null");
            return (Criteria) this;
        }

        public Criteria andPostIdsEqualTo(String value) {
            addCriterion("post_ids =", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotEqualTo(String value) {
            addCriterion("post_ids <>", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsGreaterThan(String value) {
            addCriterion("post_ids >", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsGreaterThanOrEqualTo(String value) {
            addCriterion("post_ids >=", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsLessThan(String value) {
            addCriterion("post_ids <", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsLessThanOrEqualTo(String value) {
            addCriterion("post_ids <=", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsLike(String value) {
            addCriterion("post_ids like", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotLike(String value) {
            addCriterion("post_ids not like", value, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsIn(List<String> values) {
            addCriterion("post_ids in", values, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotIn(List<String> values) {
            addCriterion("post_ids not in", values, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsBetween(String value1, String value2) {
            addCriterion("post_ids between", value1, value2, "postIds");
            return (Criteria) this;
        }

        public Criteria andPostIdsNotBetween(String value1, String value2) {
            addCriterion("post_ids not between", value1, value2, "postIds");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Integer value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Integer value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Integer value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Integer value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Integer> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Integer> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
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

        public Criteria andPubCountIsNull() {
            addCriterion("pub_count is null");
            return (Criteria) this;
        }

        public Criteria andPubCountIsNotNull() {
            addCriterion("pub_count is not null");
            return (Criteria) this;
        }

        public Criteria andPubCountEqualTo(Integer value) {
            addCriterion("pub_count =", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountNotEqualTo(Integer value) {
            addCriterion("pub_count <>", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountGreaterThan(Integer value) {
            addCriterion("pub_count >", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pub_count >=", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountLessThan(Integer value) {
            addCriterion("pub_count <", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountLessThanOrEqualTo(Integer value) {
            addCriterion("pub_count <=", value, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountIn(List<Integer> values) {
            addCriterion("pub_count in", values, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountNotIn(List<Integer> values) {
            addCriterion("pub_count not in", values, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountBetween(Integer value1, Integer value2) {
            addCriterion("pub_count between", value1, value2, "pubCount");
            return (Criteria) this;
        }

        public Criteria andPubCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pub_count not between", value1, value2, "pubCount");
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

        public Criteria andExportCountIsNull() {
            addCriterion("export_count is null");
            return (Criteria) this;
        }

        public Criteria andExportCountIsNotNull() {
            addCriterion("export_count is not null");
            return (Criteria) this;
        }

        public Criteria andExportCountEqualTo(Integer value) {
            addCriterion("export_count =", value, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountNotEqualTo(Integer value) {
            addCriterion("export_count <>", value, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountGreaterThan(Integer value) {
            addCriterion("export_count >", value, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("export_count >=", value, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountLessThan(Integer value) {
            addCriterion("export_count <", value, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountLessThanOrEqualTo(Integer value) {
            addCriterion("export_count <=", value, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountIn(List<Integer> values) {
            addCriterion("export_count in", values, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountNotIn(List<Integer> values) {
            addCriterion("export_count not in", values, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountBetween(Integer value1, Integer value2) {
            addCriterion("export_count between", value1, value2, "exportCount");
            return (Criteria) this;
        }

        public Criteria andExportCountNotBetween(Integer value1, Integer value2) {
            addCriterion("export_count not between", value1, value2, "exportCount");
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