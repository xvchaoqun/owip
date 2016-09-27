<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row myTableDiv">
    <div class="buttons pull-right">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="popupBtn btn btn-warning btn-sm"
               data-url="${ctx}/cadreEdu_rule"><i class="fa fa-search"></i>
                学历学位的认定规则</a>
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreEdu_au?cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-plus"></i>
                添加学习经历</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreEdu_au"
               data-grid-id="#jqGrid_cadreEdu"
               data-querystr="&cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-edit"></i>
                修改学习经历</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button data-url="${ctx}/cadreEdu_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreEdu"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <h4><i class="fa fa-history"></i> 学习经历：</h4>
    <table id="jqGrid_cadreEdu" class="jqGrid4"></table>
    <div id="jqGridPager_cadreEdu"></div>
    <div class="space-4"></div>

    <div class="buttons pull-right">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreUnderEdu_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                添加在读学习经历</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreUnderEdu_au"
               data-grid-id="#jqGrid_cadreUnderEdu"
               data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                修改在读学习经历</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button data-url="${ctx}/cadreEdu_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreUnderEdu"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <h4><i class="fa fa-history"></i> 在读学习经历：</h4>
    <table id="jqGrid_cadreUnderEdu" class="jqGrid4"></table>
    <div id="jqGridPager_cadreUnderEdu"></div>
    <div class="space-4"></div>

    <div class="buttons pull-right">
        <shiro:hasPermission name="cadreEdu:edit">
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreTutor_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                添加导师信息</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreTutor_au"
               data-grid-id="#jqGrid_cadreTutor"
               data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                修改导师信息</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button data-url="${ctx}/cadreTutor_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreTutor"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <h4><i class="fa fa-user-secret"></i> 导师信息：</h4>
    <table id="jqGrid_cadreTutor" class="jqGrid4"></table>
    <div id="jqGridPager_cadreTutor"></div>
</div>
<script>
    $("#jqGrid_cadreEdu").jqGrid({
        pager: "#jqGridPager_cadreEdu",
        ondblClickRow:function(){},
        url: '${ctx}/cadreEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学历', name: 'eduId' ,frozen:true, formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }},
            {label: '入学时间', name: 'enrolTime',formatter:'date',formatoptions: {newformat:'Y.m'}, width:80},
            {label: '毕业时间', name: 'finishTime',formatter:'date',formatoptions: {newformat:'Y.m'}, width:80},
            {label: '毕业/在读', name: 'isGraduated', formatter:function(cellvalue, options, rowObject){
                return cellvalue?"毕业":"在读";
            }},
            {label: '是否最高学历', width:110, name: 'isHighEdu', formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            }},
            {label: '毕业/在读学校', name: 'school', width:280},
            {label: '院系', name: 'dep', width:380},
            {label: '所学专业', name: 'major', width:380},
            {label: '学校类型', name: 'schoolType', formatter:function(cellvalue, options, rowObject){
                return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
            }, width:80},

            {label: '学制', name: 'schoolLen', width:50},
            {label: '学习方式', name: 'learnStyle', formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }},
            {label: '学位', name: 'degree', formatter:function(cellvalue, options, rowObject){
                return rowObject.hasDegree?cellvalue:"-";
            }},
            {label: '是否最高学位', name: 'isHighDegree', formatter:function(cellvalue, options, rowObject){
                if(!rowObject.hasDegree) return "-";
                return cellvalue?"是":"否";
            }, width:110},
            {label: '学位授予国家', name: 'degreeCountry', width:110, formatter:function(cellvalue, options, rowObject){
                return rowObject.hasDegree?cellvalue:"-";
            }},
            {label: '学位授予单位', name: 'degreeUnit', width:150, formatter:function(cellvalue, options, rowObject){
                return rowObject.hasDegree?cellvalue:"-";
            }},
            {label: '学位授予日期', name: 'degreeTime', width:110,formatter:'date',formatoptions: {newformat:'Y.m'}},
            {label: '导师姓名', name: 'tutorName', formatter:function(cellvalue, options, rowObject){
                if(rowObject.eduId=="${cm:getMetaTypeByCode("mt_edu_master").id}" || rowObject.eduId=="${cm:getMetaTypeByCode("mt_edu_doctor").id}"){
                    return cellvalue==undefined?'':cellvalue;
                }else return '-'
            }},
            {label: '导师现所在单位及职务（或职称）', name: 'tutorTitle', formatter:function(cellvalue, options, rowObject){
                if(rowObject.eduId=="${cm:getMetaTypeByCode("mt_edu_master").id}" || rowObject.eduId=="${cm:getMetaTypeByCode("mt_edu_doctor").id}"){
                    return cellvalue==undefined?'':cellvalue;
                }else return '-'
            }, width:250},{label: '备注', name: 'major', width:180}]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid4');
    });

    $("#jqGrid_cadreUnderEdu").jqGrid({
        pager: "#jqGridPager_cadreUnderEdu",
        ondblClickRow:function(){},
        url: '${ctx}/cadreUnderEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '在读学历', name: 'eduId' ,frozen:true, formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }},
            {label: '在读学校', name: 'school'},
            {label: '院系', name: 'dep'},
            {label: '所学专业', name: 'major'},
            {label: '学校类型', name: 'schoolType', formatter:function(cellvalue, options, rowObject){
                return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
            }},
            {label: '入学时间', name: 'enrolTime',formatter:'date',formatoptions: {newformat:'Y-m'}},
            {label: '学习方式', name: 'learnStyle', formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid4');
    });

    $("#jqGrid_cadreTutor").jqGrid({
        pager: "#jqGridPager_cadreTutor",
        ondblClickRow:function(){},
        url: '${ctx}/cadreTutor_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '类型', name: 'type', width:'150' ,frozen:true, formatter:function(cellvalue, options, rowObject){
                return _cMap.CADRE_TUTOR_TYPE_MAP[cellvalue]
            }},
            {label: '导师姓名', name: 'name'},
            {label: '所在单位及职务(职称)', name: 'title', width:'500'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid4');
    });
</script>