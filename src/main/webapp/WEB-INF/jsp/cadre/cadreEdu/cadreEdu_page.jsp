<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="buttons pull-right">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="btn btn-info btn-sm" onclick="_au()" data-width="900"><i class="fa fa-plus"></i>
                添加学习经历</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button onclick="_batchDel()" class="btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <h4><i class="fa fa-history"></i> 学习经历：</h4>
    <table id="jqGrid_cadreEdu" class="jqGrid4 table-striped"></table>
    <div id="jqGridPager_cadreEdu"></div>

    <div class="buttons pull-right">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="btn btn-info btn-sm" onclick="_au()" data-width="900"><i class="fa fa-plus"></i>
                添加导师信息</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button onclick="_batchDel()" class="btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <h4><i class="fa fa-user-secret"></i> 导师信息：</h4>
    <table id="jqGrid_cadreTutor" class="jqGrid4 table-striped"></table>
    <div id="jqGridPager_cadreTutor"></div>
</div>



<script>
    $("#jqGrid_cadreEdu").jqGrid({
        pager: "#jqGridPager_cadreEdu",
        url: '${ctx}/cadreEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学历', name: 'eduId'},
            {label: '最高学历', name: 'isHighEdu'},
            {label: '毕业学校', name: 'school'},
            {label: '院系', name: 'dep'},
            {label: '所学专业', name: 'major'},
            {label: '学校类型', name: 'schoolType'},
            {label: '入学时间', name: 'enrolTime'},
            {label: '毕业时间', name: 'finishTime'},
            {label: '学制', name: 'schoolLen'},
            {label: '学习方式', name: 'learnStyle'},
            {label: '学位', name: 'degree'},
            {label: '最高学位', name: 'isHighDegree'},
            {label: '学位授予国家', name: 'degreeCountry', width:150},
            {label: '学位授予单位', name: 'degreeUnit', width:150},
            {label: '学位授予日期', name: 'degreeTime', width:150}
        ]
    }).jqGrid("setFrozenColumns");

    $("#jqGrid_cadreTutor").jqGrid({
        pager: "#jqGridPager_cadreTutor",
        url: '${ctx}/cadreTutor_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '类别', name: 'type', width:150},
            {label: '姓名', name: 'name'},
            {label: '所在单位及职务(职称)', name: 'title', width:250}
        ]
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid4');
</script>