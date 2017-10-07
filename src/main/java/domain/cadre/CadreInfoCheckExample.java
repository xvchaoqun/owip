package domain.cadre;

import java.util.ArrayList;
import java.util.List;

public class CadreInfoCheckExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CadreInfoCheckExample() {
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

        public Criteria andPostProIsNull() {
            addCriterion("post_pro is null");
            return (Criteria) this;
        }

        public Criteria andPostProIsNotNull() {
            addCriterion("post_pro is not null");
            return (Criteria) this;
        }

        public Criteria andPostProEqualTo(Boolean value) {
            addCriterion("post_pro =", value, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProNotEqualTo(Boolean value) {
            addCriterion("post_pro <>", value, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProGreaterThan(Boolean value) {
            addCriterion("post_pro >", value, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProGreaterThanOrEqualTo(Boolean value) {
            addCriterion("post_pro >=", value, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProLessThan(Boolean value) {
            addCriterion("post_pro <", value, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProLessThanOrEqualTo(Boolean value) {
            addCriterion("post_pro <=", value, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProIn(List<Boolean> values) {
            addCriterion("post_pro in", values, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProNotIn(List<Boolean> values) {
            addCriterion("post_pro not in", values, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProBetween(Boolean value1, Boolean value2) {
            addCriterion("post_pro between", value1, value2, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostProNotBetween(Boolean value1, Boolean value2) {
            addCriterion("post_pro not between", value1, value2, "postPro");
            return (Criteria) this;
        }

        public Criteria andPostAdminIsNull() {
            addCriterion("post_admin is null");
            return (Criteria) this;
        }

        public Criteria andPostAdminIsNotNull() {
            addCriterion("post_admin is not null");
            return (Criteria) this;
        }

        public Criteria andPostAdminEqualTo(Boolean value) {
            addCriterion("post_admin =", value, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminNotEqualTo(Boolean value) {
            addCriterion("post_admin <>", value, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminGreaterThan(Boolean value) {
            addCriterion("post_admin >", value, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminGreaterThanOrEqualTo(Boolean value) {
            addCriterion("post_admin >=", value, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminLessThan(Boolean value) {
            addCriterion("post_admin <", value, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminLessThanOrEqualTo(Boolean value) {
            addCriterion("post_admin <=", value, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminIn(List<Boolean> values) {
            addCriterion("post_admin in", values, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminNotIn(List<Boolean> values) {
            addCriterion("post_admin not in", values, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminBetween(Boolean value1, Boolean value2) {
            addCriterion("post_admin between", value1, value2, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostAdminNotBetween(Boolean value1, Boolean value2) {
            addCriterion("post_admin not between", value1, value2, "postAdmin");
            return (Criteria) this;
        }

        public Criteria andPostWorkIsNull() {
            addCriterion("post_work is null");
            return (Criteria) this;
        }

        public Criteria andPostWorkIsNotNull() {
            addCriterion("post_work is not null");
            return (Criteria) this;
        }

        public Criteria andPostWorkEqualTo(Boolean value) {
            addCriterion("post_work =", value, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkNotEqualTo(Boolean value) {
            addCriterion("post_work <>", value, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkGreaterThan(Boolean value) {
            addCriterion("post_work >", value, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkGreaterThanOrEqualTo(Boolean value) {
            addCriterion("post_work >=", value, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkLessThan(Boolean value) {
            addCriterion("post_work <", value, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkLessThanOrEqualTo(Boolean value) {
            addCriterion("post_work <=", value, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkIn(List<Boolean> values) {
            addCriterion("post_work in", values, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkNotIn(List<Boolean> values) {
            addCriterion("post_work not in", values, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkBetween(Boolean value1, Boolean value2) {
            addCriterion("post_work between", value1, value2, "postWork");
            return (Criteria) this;
        }

        public Criteria andPostWorkNotBetween(Boolean value1, Boolean value2) {
            addCriterion("post_work not between", value1, value2, "postWork");
            return (Criteria) this;
        }

        public Criteria andParttimeIsNull() {
            addCriterion("parttime is null");
            return (Criteria) this;
        }

        public Criteria andParttimeIsNotNull() {
            addCriterion("parttime is not null");
            return (Criteria) this;
        }

        public Criteria andParttimeEqualTo(Boolean value) {
            addCriterion("parttime =", value, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeNotEqualTo(Boolean value) {
            addCriterion("parttime <>", value, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeGreaterThan(Boolean value) {
            addCriterion("parttime >", value, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("parttime >=", value, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeLessThan(Boolean value) {
            addCriterion("parttime <", value, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeLessThanOrEqualTo(Boolean value) {
            addCriterion("parttime <=", value, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeIn(List<Boolean> values) {
            addCriterion("parttime in", values, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeNotIn(List<Boolean> values) {
            addCriterion("parttime not in", values, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeBetween(Boolean value1, Boolean value2) {
            addCriterion("parttime between", value1, value2, "parttime");
            return (Criteria) this;
        }

        public Criteria andParttimeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("parttime not between", value1, value2, "parttime");
            return (Criteria) this;
        }

        public Criteria andTrainIsNull() {
            addCriterion("train is null");
            return (Criteria) this;
        }

        public Criteria andTrainIsNotNull() {
            addCriterion("train is not null");
            return (Criteria) this;
        }

        public Criteria andTrainEqualTo(Boolean value) {
            addCriterion("train =", value, "train");
            return (Criteria) this;
        }

        public Criteria andTrainNotEqualTo(Boolean value) {
            addCriterion("train <>", value, "train");
            return (Criteria) this;
        }

        public Criteria andTrainGreaterThan(Boolean value) {
            addCriterion("train >", value, "train");
            return (Criteria) this;
        }

        public Criteria andTrainGreaterThanOrEqualTo(Boolean value) {
            addCriterion("train >=", value, "train");
            return (Criteria) this;
        }

        public Criteria andTrainLessThan(Boolean value) {
            addCriterion("train <", value, "train");
            return (Criteria) this;
        }

        public Criteria andTrainLessThanOrEqualTo(Boolean value) {
            addCriterion("train <=", value, "train");
            return (Criteria) this;
        }

        public Criteria andTrainIn(List<Boolean> values) {
            addCriterion("train in", values, "train");
            return (Criteria) this;
        }

        public Criteria andTrainNotIn(List<Boolean> values) {
            addCriterion("train not in", values, "train");
            return (Criteria) this;
        }

        public Criteria andTrainBetween(Boolean value1, Boolean value2) {
            addCriterion("train between", value1, value2, "train");
            return (Criteria) this;
        }

        public Criteria andTrainNotBetween(Boolean value1, Boolean value2) {
            addCriterion("train not between", value1, value2, "train");
            return (Criteria) this;
        }

        public Criteria andCourseIsNull() {
            addCriterion("course is null");
            return (Criteria) this;
        }

        public Criteria andCourseIsNotNull() {
            addCriterion("course is not null");
            return (Criteria) this;
        }

        public Criteria andCourseEqualTo(Boolean value) {
            addCriterion("course =", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotEqualTo(Boolean value) {
            addCriterion("course <>", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseGreaterThan(Boolean value) {
            addCriterion("course >", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseGreaterThanOrEqualTo(Boolean value) {
            addCriterion("course >=", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseLessThan(Boolean value) {
            addCriterion("course <", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseLessThanOrEqualTo(Boolean value) {
            addCriterion("course <=", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseIn(List<Boolean> values) {
            addCriterion("course in", values, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotIn(List<Boolean> values) {
            addCriterion("course not in", values, "course");
            return (Criteria) this;
        }

        public Criteria andCourseBetween(Boolean value1, Boolean value2) {
            addCriterion("course between", value1, value2, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotBetween(Boolean value1, Boolean value2) {
            addCriterion("course not between", value1, value2, "course");
            return (Criteria) this;
        }

        public Criteria andCourseRewardIsNull() {
            addCriterion("course_reward is null");
            return (Criteria) this;
        }

        public Criteria andCourseRewardIsNotNull() {
            addCriterion("course_reward is not null");
            return (Criteria) this;
        }

        public Criteria andCourseRewardEqualTo(Boolean value) {
            addCriterion("course_reward =", value, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardNotEqualTo(Boolean value) {
            addCriterion("course_reward <>", value, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardGreaterThan(Boolean value) {
            addCriterion("course_reward >", value, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardGreaterThanOrEqualTo(Boolean value) {
            addCriterion("course_reward >=", value, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardLessThan(Boolean value) {
            addCriterion("course_reward <", value, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardLessThanOrEqualTo(Boolean value) {
            addCriterion("course_reward <=", value, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardIn(List<Boolean> values) {
            addCriterion("course_reward in", values, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardNotIn(List<Boolean> values) {
            addCriterion("course_reward not in", values, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardBetween(Boolean value1, Boolean value2) {
            addCriterion("course_reward between", value1, value2, "courseReward");
            return (Criteria) this;
        }

        public Criteria andCourseRewardNotBetween(Boolean value1, Boolean value2) {
            addCriterion("course_reward not between", value1, value2, "courseReward");
            return (Criteria) this;
        }

        public Criteria andResearchDirectIsNull() {
            addCriterion("research_direct is null");
            return (Criteria) this;
        }

        public Criteria andResearchDirectIsNotNull() {
            addCriterion("research_direct is not null");
            return (Criteria) this;
        }

        public Criteria andResearchDirectEqualTo(Boolean value) {
            addCriterion("research_direct =", value, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectNotEqualTo(Boolean value) {
            addCriterion("research_direct <>", value, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectGreaterThan(Boolean value) {
            addCriterion("research_direct >", value, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectGreaterThanOrEqualTo(Boolean value) {
            addCriterion("research_direct >=", value, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectLessThan(Boolean value) {
            addCriterion("research_direct <", value, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectLessThanOrEqualTo(Boolean value) {
            addCriterion("research_direct <=", value, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectIn(List<Boolean> values) {
            addCriterion("research_direct in", values, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectNotIn(List<Boolean> values) {
            addCriterion("research_direct not in", values, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectBetween(Boolean value1, Boolean value2) {
            addCriterion("research_direct between", value1, value2, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchDirectNotBetween(Boolean value1, Boolean value2) {
            addCriterion("research_direct not between", value1, value2, "researchDirect");
            return (Criteria) this;
        }

        public Criteria andResearchInIsNull() {
            addCriterion("research_in is null");
            return (Criteria) this;
        }

        public Criteria andResearchInIsNotNull() {
            addCriterion("research_in is not null");
            return (Criteria) this;
        }

        public Criteria andResearchInEqualTo(Boolean value) {
            addCriterion("research_in =", value, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInNotEqualTo(Boolean value) {
            addCriterion("research_in <>", value, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInGreaterThan(Boolean value) {
            addCriterion("research_in >", value, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInGreaterThanOrEqualTo(Boolean value) {
            addCriterion("research_in >=", value, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInLessThan(Boolean value) {
            addCriterion("research_in <", value, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInLessThanOrEqualTo(Boolean value) {
            addCriterion("research_in <=", value, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInIn(List<Boolean> values) {
            addCriterion("research_in in", values, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInNotIn(List<Boolean> values) {
            addCriterion("research_in not in", values, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInBetween(Boolean value1, Boolean value2) {
            addCriterion("research_in between", value1, value2, "researchIn");
            return (Criteria) this;
        }

        public Criteria andResearchInNotBetween(Boolean value1, Boolean value2) {
            addCriterion("research_in not between", value1, value2, "researchIn");
            return (Criteria) this;
        }

        public Criteria andBookIsNull() {
            addCriterion("book is null");
            return (Criteria) this;
        }

        public Criteria andBookIsNotNull() {
            addCriterion("book is not null");
            return (Criteria) this;
        }

        public Criteria andBookEqualTo(Boolean value) {
            addCriterion("book =", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotEqualTo(Boolean value) {
            addCriterion("book <>", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookGreaterThan(Boolean value) {
            addCriterion("book >", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookGreaterThanOrEqualTo(Boolean value) {
            addCriterion("book >=", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookLessThan(Boolean value) {
            addCriterion("book <", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookLessThanOrEqualTo(Boolean value) {
            addCriterion("book <=", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookIn(List<Boolean> values) {
            addCriterion("book in", values, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotIn(List<Boolean> values) {
            addCriterion("book not in", values, "book");
            return (Criteria) this;
        }

        public Criteria andBookBetween(Boolean value1, Boolean value2) {
            addCriterion("book between", value1, value2, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotBetween(Boolean value1, Boolean value2) {
            addCriterion("book not between", value1, value2, "book");
            return (Criteria) this;
        }

        public Criteria andPaperIsNull() {
            addCriterion("paper is null");
            return (Criteria) this;
        }

        public Criteria andPaperIsNotNull() {
            addCriterion("paper is not null");
            return (Criteria) this;
        }

        public Criteria andPaperEqualTo(Boolean value) {
            addCriterion("paper =", value, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperNotEqualTo(Boolean value) {
            addCriterion("paper <>", value, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperGreaterThan(Boolean value) {
            addCriterion("paper >", value, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperGreaterThanOrEqualTo(Boolean value) {
            addCriterion("paper >=", value, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperLessThan(Boolean value) {
            addCriterion("paper <", value, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperLessThanOrEqualTo(Boolean value) {
            addCriterion("paper <=", value, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperIn(List<Boolean> values) {
            addCriterion("paper in", values, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperNotIn(List<Boolean> values) {
            addCriterion("paper not in", values, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperBetween(Boolean value1, Boolean value2) {
            addCriterion("paper between", value1, value2, "paper");
            return (Criteria) this;
        }

        public Criteria andPaperNotBetween(Boolean value1, Boolean value2) {
            addCriterion("paper not between", value1, value2, "paper");
            return (Criteria) this;
        }

        public Criteria andResearchRewardIsNull() {
            addCriterion("research_reward is null");
            return (Criteria) this;
        }

        public Criteria andResearchRewardIsNotNull() {
            addCriterion("research_reward is not null");
            return (Criteria) this;
        }

        public Criteria andResearchRewardEqualTo(Boolean value) {
            addCriterion("research_reward =", value, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardNotEqualTo(Boolean value) {
            addCriterion("research_reward <>", value, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardGreaterThan(Boolean value) {
            addCriterion("research_reward >", value, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardGreaterThanOrEqualTo(Boolean value) {
            addCriterion("research_reward >=", value, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardLessThan(Boolean value) {
            addCriterion("research_reward <", value, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardLessThanOrEqualTo(Boolean value) {
            addCriterion("research_reward <=", value, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardIn(List<Boolean> values) {
            addCriterion("research_reward in", values, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardNotIn(List<Boolean> values) {
            addCriterion("research_reward not in", values, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardBetween(Boolean value1, Boolean value2) {
            addCriterion("research_reward between", value1, value2, "researchReward");
            return (Criteria) this;
        }

        public Criteria andResearchRewardNotBetween(Boolean value1, Boolean value2) {
            addCriterion("research_reward not between", value1, value2, "researchReward");
            return (Criteria) this;
        }

        public Criteria andRewardIsNull() {
            addCriterion("reward is null");
            return (Criteria) this;
        }

        public Criteria andRewardIsNotNull() {
            addCriterion("reward is not null");
            return (Criteria) this;
        }

        public Criteria andRewardEqualTo(Boolean value) {
            addCriterion("reward =", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotEqualTo(Boolean value) {
            addCriterion("reward <>", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThan(Boolean value) {
            addCriterion("reward >", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThanOrEqualTo(Boolean value) {
            addCriterion("reward >=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThan(Boolean value) {
            addCriterion("reward <", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThanOrEqualTo(Boolean value) {
            addCriterion("reward <=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardIn(List<Boolean> values) {
            addCriterion("reward in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotIn(List<Boolean> values) {
            addCriterion("reward not in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardBetween(Boolean value1, Boolean value2) {
            addCriterion("reward between", value1, value2, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotBetween(Boolean value1, Boolean value2) {
            addCriterion("reward not between", value1, value2, "reward");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadIsNull() {
            addCriterion("famliy_abroad is null");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadIsNotNull() {
            addCriterion("famliy_abroad is not null");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadEqualTo(Boolean value) {
            addCriterion("famliy_abroad =", value, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadNotEqualTo(Boolean value) {
            addCriterion("famliy_abroad <>", value, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadGreaterThan(Boolean value) {
            addCriterion("famliy_abroad >", value, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadGreaterThanOrEqualTo(Boolean value) {
            addCriterion("famliy_abroad >=", value, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadLessThan(Boolean value) {
            addCriterion("famliy_abroad <", value, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadLessThanOrEqualTo(Boolean value) {
            addCriterion("famliy_abroad <=", value, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadIn(List<Boolean> values) {
            addCriterion("famliy_abroad in", values, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadNotIn(List<Boolean> values) {
            addCriterion("famliy_abroad not in", values, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadBetween(Boolean value1, Boolean value2) {
            addCriterion("famliy_abroad between", value1, value2, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andFamliyAbroadNotBetween(Boolean value1, Boolean value2) {
            addCriterion("famliy_abroad not between", value1, value2, "famliyAbroad");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(Boolean value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(Boolean value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(Boolean value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(Boolean value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(Boolean value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<Boolean> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<Boolean> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(Boolean value1, Boolean value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("company not between", value1, value2, "company");
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