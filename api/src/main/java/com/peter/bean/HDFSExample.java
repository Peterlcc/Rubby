package com.peter.bean;

import java.util.ArrayList;
import java.util.List;

public class HDFSExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HDFSExample() {
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

        public Criteria andUserIsNull() {
            addCriterion("user is null");
            return (Criteria) this;
        }

        public Criteria andUserIsNotNull() {
            addCriterion("user is not null");
            return (Criteria) this;
        }

        public Criteria andUserEqualTo(String value) {
            addCriterion("user =", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotEqualTo(String value) {
            addCriterion("user <>", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThan(String value) {
            addCriterion("user >", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThanOrEqualTo(String value) {
            addCriterion("user >=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThan(String value) {
            addCriterion("user <", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThanOrEqualTo(String value) {
            addCriterion("user <=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLike(String value) {
            addCriterion("user like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotLike(String value) {
            addCriterion("user not like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserIn(List<String> values) {
            addCriterion("user in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotIn(List<String> values) {
            addCriterion("user not in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserBetween(String value1, String value2) {
            addCriterion("user between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotBetween(String value1, String value2) {
            addCriterion("user not between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andKerberosIsNull() {
            addCriterion("kerberos is null");
            return (Criteria) this;
        }

        public Criteria andKerberosIsNotNull() {
            addCriterion("kerberos is not null");
            return (Criteria) this;
        }

        public Criteria andKerberosEqualTo(String value) {
            addCriterion("kerberos =", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosNotEqualTo(String value) {
            addCriterion("kerberos <>", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosGreaterThan(String value) {
            addCriterion("kerberos >", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosGreaterThanOrEqualTo(String value) {
            addCriterion("kerberos >=", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosLessThan(String value) {
            addCriterion("kerberos <", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosLessThanOrEqualTo(String value) {
            addCriterion("kerberos <=", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosLike(String value) {
            addCriterion("kerberos like", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosNotLike(String value) {
            addCriterion("kerberos not like", value, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosIn(List<String> values) {
            addCriterion("kerberos in", values, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosNotIn(List<String> values) {
            addCriterion("kerberos not in", values, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosBetween(String value1, String value2) {
            addCriterion("kerberos between", value1, value2, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosNotBetween(String value1, String value2) {
            addCriterion("kerberos not between", value1, value2, "kerberos");
            return (Criteria) this;
        }

        public Criteria andKerberosUserIsNull() {
            addCriterion("kerberos_user is null");
            return (Criteria) this;
        }

        public Criteria andKerberosUserIsNotNull() {
            addCriterion("kerberos_user is not null");
            return (Criteria) this;
        }

        public Criteria andKerberosUserEqualTo(String value) {
            addCriterion("kerberos_user =", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserNotEqualTo(String value) {
            addCriterion("kerberos_user <>", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserGreaterThan(String value) {
            addCriterion("kerberos_user >", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserGreaterThanOrEqualTo(String value) {
            addCriterion("kerberos_user >=", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserLessThan(String value) {
            addCriterion("kerberos_user <", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserLessThanOrEqualTo(String value) {
            addCriterion("kerberos_user <=", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserLike(String value) {
            addCriterion("kerberos_user like", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserNotLike(String value) {
            addCriterion("kerberos_user not like", value, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserIn(List<String> values) {
            addCriterion("kerberos_user in", values, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserNotIn(List<String> values) {
            addCriterion("kerberos_user not in", values, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserBetween(String value1, String value2) {
            addCriterion("kerberos_user between", value1, value2, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosUserNotBetween(String value1, String value2) {
            addCriterion("kerberos_user not between", value1, value2, "kerberosUser");
            return (Criteria) this;
        }

        public Criteria andKerberosPathIsNull() {
            addCriterion("kerberos_path is null");
            return (Criteria) this;
        }

        public Criteria andKerberosPathIsNotNull() {
            addCriterion("kerberos_path is not null");
            return (Criteria) this;
        }

        public Criteria andKerberosPathEqualTo(String value) {
            addCriterion("kerberos_path =", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathNotEqualTo(String value) {
            addCriterion("kerberos_path <>", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathGreaterThan(String value) {
            addCriterion("kerberos_path >", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathGreaterThanOrEqualTo(String value) {
            addCriterion("kerberos_path >=", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathLessThan(String value) {
            addCriterion("kerberos_path <", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathLessThanOrEqualTo(String value) {
            addCriterion("kerberos_path <=", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathLike(String value) {
            addCriterion("kerberos_path like", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathNotLike(String value) {
            addCriterion("kerberos_path not like", value, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathIn(List<String> values) {
            addCriterion("kerberos_path in", values, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathNotIn(List<String> values) {
            addCriterion("kerberos_path not in", values, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathBetween(String value1, String value2) {
            addCriterion("kerberos_path between", value1, value2, "kerberosPath");
            return (Criteria) this;
        }

        public Criteria andKerberosPathNotBetween(String value1, String value2) {
            addCriterion("kerberos_path not between", value1, value2, "kerberosPath");
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