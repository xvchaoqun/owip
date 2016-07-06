package domain.cadre;

import java.util.ArrayList;
import java.util.List;

public class CadreResearchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreResearchExample() {
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

        public Criteria andCadreIdIsNull() {
            addCriterion("cadre_id is null");
            return (Criteria) this;
        }

        public Criteria andCadreIdIsNotNull() {
            addCriterion("cadre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCadreIdEqualTo(Integer value) {
            addCriterion("cadre_id =", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotEqualTo(Integer value) {
            addCriterion("cadre_id <>", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThan(Integer value) {
            addCriterion("cadre_id >", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cadre_id >=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThan(Integer value) {
            addCriterion("cadre_id <", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cadre_id <=", value, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdIn(List<Integer> values) {
            addCriterion("cadre_id in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotIn(List<Integer> values) {
            addCriterion("cadre_id not in", values, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andCadreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cadre_id not between", value1, value2, "cadreId");
            return (Criteria) this;
        }

        public Criteria andChairFileIsNull() {
            addCriterion("chair_file is null");
            return (Criteria) this;
        }

        public Criteria andChairFileIsNotNull() {
            addCriterion("chair_file is not null");
            return (Criteria) this;
        }

        public Criteria andChairFileEqualTo(String value) {
            addCriterion("chair_file =", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileNotEqualTo(String value) {
            addCriterion("chair_file <>", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileGreaterThan(String value) {
            addCriterion("chair_file >", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileGreaterThanOrEqualTo(String value) {
            addCriterion("chair_file >=", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileLessThan(String value) {
            addCriterion("chair_file <", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileLessThanOrEqualTo(String value) {
            addCriterion("chair_file <=", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileLike(String value) {
            addCriterion("chair_file like", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileNotLike(String value) {
            addCriterion("chair_file not like", value, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileIn(List<String> values) {
            addCriterion("chair_file in", values, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileNotIn(List<String> values) {
            addCriterion("chair_file not in", values, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileBetween(String value1, String value2) {
            addCriterion("chair_file between", value1, value2, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileNotBetween(String value1, String value2) {
            addCriterion("chair_file not between", value1, value2, "chairFile");
            return (Criteria) this;
        }

        public Criteria andChairFileNameIsNull() {
            addCriterion("chair_file_name is null");
            return (Criteria) this;
        }

        public Criteria andChairFileNameIsNotNull() {
            addCriterion("chair_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andChairFileNameEqualTo(String value) {
            addCriterion("chair_file_name =", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameNotEqualTo(String value) {
            addCriterion("chair_file_name <>", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameGreaterThan(String value) {
            addCriterion("chair_file_name >", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("chair_file_name >=", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameLessThan(String value) {
            addCriterion("chair_file_name <", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameLessThanOrEqualTo(String value) {
            addCriterion("chair_file_name <=", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameLike(String value) {
            addCriterion("chair_file_name like", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameNotLike(String value) {
            addCriterion("chair_file_name not like", value, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameIn(List<String> values) {
            addCriterion("chair_file_name in", values, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameNotIn(List<String> values) {
            addCriterion("chair_file_name not in", values, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameBetween(String value1, String value2) {
            addCriterion("chair_file_name between", value1, value2, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andChairFileNameNotBetween(String value1, String value2) {
            addCriterion("chair_file_name not between", value1, value2, "chairFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileIsNull() {
            addCriterion("join_file is null");
            return (Criteria) this;
        }

        public Criteria andJoinFileIsNotNull() {
            addCriterion("join_file is not null");
            return (Criteria) this;
        }

        public Criteria andJoinFileEqualTo(String value) {
            addCriterion("join_file =", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileNotEqualTo(String value) {
            addCriterion("join_file <>", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileGreaterThan(String value) {
            addCriterion("join_file >", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileGreaterThanOrEqualTo(String value) {
            addCriterion("join_file >=", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileLessThan(String value) {
            addCriterion("join_file <", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileLessThanOrEqualTo(String value) {
            addCriterion("join_file <=", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileLike(String value) {
            addCriterion("join_file like", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileNotLike(String value) {
            addCriterion("join_file not like", value, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileIn(List<String> values) {
            addCriterion("join_file in", values, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileNotIn(List<String> values) {
            addCriterion("join_file not in", values, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileBetween(String value1, String value2) {
            addCriterion("join_file between", value1, value2, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileNotBetween(String value1, String value2) {
            addCriterion("join_file not between", value1, value2, "joinFile");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameIsNull() {
            addCriterion("join_file_name is null");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameIsNotNull() {
            addCriterion("join_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameEqualTo(String value) {
            addCriterion("join_file_name =", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameNotEqualTo(String value) {
            addCriterion("join_file_name <>", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameGreaterThan(String value) {
            addCriterion("join_file_name >", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("join_file_name >=", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameLessThan(String value) {
            addCriterion("join_file_name <", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameLessThanOrEqualTo(String value) {
            addCriterion("join_file_name <=", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameLike(String value) {
            addCriterion("join_file_name like", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameNotLike(String value) {
            addCriterion("join_file_name not like", value, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameIn(List<String> values) {
            addCriterion("join_file_name in", values, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameNotIn(List<String> values) {
            addCriterion("join_file_name not in", values, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameBetween(String value1, String value2) {
            addCriterion("join_file_name between", value1, value2, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andJoinFileNameNotBetween(String value1, String value2) {
            addCriterion("join_file_name not between", value1, value2, "joinFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileIsNull() {
            addCriterion("publish_file is null");
            return (Criteria) this;
        }

        public Criteria andPublishFileIsNotNull() {
            addCriterion("publish_file is not null");
            return (Criteria) this;
        }

        public Criteria andPublishFileEqualTo(String value) {
            addCriterion("publish_file =", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileNotEqualTo(String value) {
            addCriterion("publish_file <>", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileGreaterThan(String value) {
            addCriterion("publish_file >", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileGreaterThanOrEqualTo(String value) {
            addCriterion("publish_file >=", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileLessThan(String value) {
            addCriterion("publish_file <", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileLessThanOrEqualTo(String value) {
            addCriterion("publish_file <=", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileLike(String value) {
            addCriterion("publish_file like", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileNotLike(String value) {
            addCriterion("publish_file not like", value, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileIn(List<String> values) {
            addCriterion("publish_file in", values, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileNotIn(List<String> values) {
            addCriterion("publish_file not in", values, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileBetween(String value1, String value2) {
            addCriterion("publish_file between", value1, value2, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileNotBetween(String value1, String value2) {
            addCriterion("publish_file not between", value1, value2, "publishFile");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameIsNull() {
            addCriterion("publish_file_name is null");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameIsNotNull() {
            addCriterion("publish_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameEqualTo(String value) {
            addCriterion("publish_file_name =", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameNotEqualTo(String value) {
            addCriterion("publish_file_name <>", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameGreaterThan(String value) {
            addCriterion("publish_file_name >", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("publish_file_name >=", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameLessThan(String value) {
            addCriterion("publish_file_name <", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameLessThanOrEqualTo(String value) {
            addCriterion("publish_file_name <=", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameLike(String value) {
            addCriterion("publish_file_name like", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameNotLike(String value) {
            addCriterion("publish_file_name not like", value, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameIn(List<String> values) {
            addCriterion("publish_file_name in", values, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameNotIn(List<String> values) {
            addCriterion("publish_file_name not in", values, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameBetween(String value1, String value2) {
            addCriterion("publish_file_name between", value1, value2, "publishFileName");
            return (Criteria) this;
        }

        public Criteria andPublishFileNameNotBetween(String value1, String value2) {
            addCriterion("publish_file_name not between", value1, value2, "publishFileName");
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