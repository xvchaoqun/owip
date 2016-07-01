<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-full"></i> 主职
            <div class="buttons">
                <button onclick="cadrePost_au(1, ${mainCadrePost.id})"
                        class="btn btn-mini btn-xs btn-warning">
                    <i class="fa fa-edit"></i> 编辑
                </button>
                <c:set var="relateDispatchCadreCount"
                       value="${fn:length(mainRelates)}"/>
                <button onclick="cadrePost_addDispatchs(${mainCadrePost.id})"
                        class="btn btn-success btn-mini btn-xs">
                    <i class="fa fa-link"></i>
                    <c:if test="${relateDispatchCadreCount>0}">任命文件(${relateDispatchCadreCount})</c:if>
                    <c:if test="${relateDispatchCadreCount==0}">关联任命文件
                </button>
                </c:if>
                </button>
                <button class="btn btn-mini btn-xs btn-primary"
                        onclick="cadrePost_selectDispatch(${mainCadrePost.id})">
                    <i class="fa fa-link"></i>
                    关联现任职务始任文件
                </button>
                <button class="btn btn-danger btn-mini btn-xs"
                        onclick="cadrePost_del(${mainCadrePost.id})">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </div>
        </h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <c:if test="${empty mainCadrePost}">
                <div class="buttons pull-right">
                    <a class="btn btn-info btn-sm" onclick="cadrePost_au(1)"><i class="fa fa-plus"></i> 添加主职</a>
                </div>
                <h4>&nbsp;</h4>

                <div class="space-4"></div>
            </c:if>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>职务</th>
                    <th>职务属性</th>
                    <th>行政级别</th>
                    <th>是否正职</th>
                    <th>职务类别</th>
                    <th>所属单位</th>
                    <th>任职日期</th>
                    <th>现任职务年限</th>
                    <th>现任职务始任日期</th>
                    <th>现任职务始任年限</th>
                    <th>现任职务始任文件</th>
                    <th>是否双肩挑</th>
                    <th>双肩挑单位</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty mainCadrePost}">
                    <c:set value="${postMap.get(mainCadrePost.postId)}" var="post"/>
                    <c:set value="${dispatchMap.get(dispatchCadreMap.get(mainCadrePost.dispatchCadreId).dispatchId)}"
                           var="dispatch"/>
                    <tr>
                        <td>${mainCadrePost.post}</td>
                        <td>${post.name}</td>
                        <td>${adminLevelMap.get(mainCadrePost.adminLevelId).name}</td>
                        <td>${post.boolAttr?"是":"否"}</td>
                        <td>${postClassMap.get(mainCadrePost.postClassId).name}</td>
                        <td>${unitMap.get(mainCadrePost.unitId).name}</td>
                        <td>${cm:formatDate(latestDispatch.workTime,'yyyy-MM-dd')}</td>
                        <td>${cm:intervalYearsUntilNow(latestDispatch.workTime)}</td>
                        <td>${cm:formatDate(dispatch.workTime,'yyyy-MM-dd')}</td>
                        <td>${cm:intervalYearsUntilNow(dispatch.workTime)}</td>
                        <td>
                                ${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}
                        </td>
                        <td>${mainCadrePost.isDouble?"是":"否"}</td>
                        <td>${unitMap.get(mainCadrePost.doubleUnitId).name}</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-half"></i> 兼职
            <div class="buttons">
                <a class="btn btn-info btn-xs" onclick="cadrePost_au(0)"><i class="fa fa-plus"></i> 添加兼职</a>
            </div>
        </h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="space-4"></div>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>兼任单位</th>
                    <th>兼任职务</th>
                    <th>兼任职务任职日期</th>
                    <th>兼任职务始任日期</th>
                    <th>兼任职务始任文件</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${subCadrePosts}" var="subCadrePost" varStatus="st">

                    <tr>
                        <td>${unitMap.get(subCadrePost.unitId).name}</td>
                        <td>${subCadrePost.post}</td>
                        <td><%--${cm:formatDate(subCadrePost.postTime,'yyyy-MM-dd')}--%></td>
                        <td><%--${cm:formatDate(subCadrePost.startTime,'yyyy-MM-dd')}--%></td>
                        <td>
                                ${dispatchMap.get(dispatchCadreMap.get(subCadrePost.dispatchCadreId).dispatchId).code}
                            <a class="btn btn-mini btn-xs btn-primary"
                               onclick="cadrePost_selectDispatch(${subCadrePost.id})">
                                    ${subCadrePost.dispatchCadreId==null?"选择":"修改"}</a>
                        </td>
                        <td>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button onclick="cadrePost_au(0, ${subCadrePost.id})"
                                        class="btn btn-mini btn-xs btn-warning">
                                    <i class="fa fa-edit"></i> 编辑
                                </button>
                                <button onclick="cadrePost_addDispatchs(${subCadrePost.id})"
                                        class="btn btn-default btn-mini btn-xs">
                                    <i class="fa fa-edit"></i> 关联任免文件
                                </button>
                                <button class="btn btn-danger btn-mini btn-xs"
                                        onclick="cadrePost_del(${subCadrePost.id})">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-history"></i> 任职级经历
            <div class="buttons">
                <a class="btn btn-info btn-xs" onclick="cadreAdminLevel_au()"><i class="fa fa-plus"></i> 添加任职级经历</a>
            </div>
        </h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="space-4"></div>
            <table class="table  table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>行政级别</th>
                    <th>是否现任职级</th>
                    <th>职级始任日期</th>
                    <th>职级始任职务</th>
                    <th>职级始任文件</th>
                    <th>职级结束日期</th>
                    <th>职级结束文件</th>
                    <th>备注</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${cadreAdminLevels}" var="cadreAdminLevel" varStatus="st">
                    <c:set value="${dispatchCadreMap.get(cadreAdminLevel.startDispatchCadreId)}" var="startDispatchCadre"/>
                    <c:set value="${dispatchMap.get(startDispatchCadre.dispatchId)}" var="startDispatch"/>
                    <c:set value="${dispatchMap.get(dispatchCadreMap.get(cadreAdminLevel.endDispatchCadreId).dispatchId)}"
                           var="endDispatch"/>
                    <tr>
                        <td>${adminLevelMap.get(cadreAdminLevel.adminLevelId).name}</td>
                        <td></td>
                        <td>${cm:formatDate(startDispatch.workTime,'yyyy-MM-dd')}</td>
                        <td>${startDispatchCadre.post}</td>
                        <td>
                                ${cm:getDispatchCode(startDispatch.code, startDispatch.dispatchTypeId, startDispatch.year)}
                        </td>
                        <td>${cm:formatDate(endDispatch.workTime,'yyyy-MM-dd')}</td>
                        <td>
                                ${cm:getDispatchCode(endDispatch.code, endDispatch.dispatchTypeId, endDispatch.year)}
                        </td>
                        <td>${cadreAdminLevel.remark}</td>
                        <td>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <a class="btn btn-xs btn-primary"
                                   onclick="cadreAdminLevel_selectStartDispatch(${cadreAdminLevel.id})">
                                    <i class="fa fa-link"></i>
                                        ${cadreAdminLevel.startDispatchCadreId==null?"关联始任文件":"修改始任文件"}</a>
                                <a class="btn btn-xs btn-primary"
                                   onclick="cadreAdminLevel_selectEndDispatch(${cadreAdminLevel.id})">
                                    <i class="fa fa-link"></i>
                                        ${cadreAdminLevel.endDispatchCadreId==null?"关联结束文件":"修改结束文件"}</a>
                                <a onclick="cadreAdminLevel_au(${cadreAdminLevel.id})" class="btn btn-mini btn-xs btn-warning">
                                    <i class="fa fa-edit"></i> 编辑
                                </a>
                                <a class="btn btn-danger btn-xs" onclick="cadreAdminLevel_del(${cadreAdminLevel.id})">
                                    <i class="fa fa-trash"></i> 删除
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<style>
    .widget-main .table {
        border-width: 1px !important;
        border-top: 0px !important;
    }

    .widget-main .table th, .widget-main .table td {
        text-align: center;
        height: 38px;
    }
</style>
<script>
    $("#jqGrid_mainCadrePost").jqGrid({
        pager: null,
        ondblClickRow:function(){},
        url: '${ctx}/mainCadrePost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学历', name: 'eduId' ,frozen:true, formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }},
            {label: '是否最高学历', width:120, name: 'isHighEdu', formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            }},
            {label: '毕业学校', name: 'school'},
            {label: '院系', name: 'dep'},
            {label: '所学专业', name: 'major'},
            {label: '学校类型', name: 'schoolType', formatter:function(cellvalue, options, rowObject){
                return _cMap.CADRE_SCHOOL_TYPE_MAP[cellvalue]
            }},
            {label: '入学时间', name: 'enrolTime',formatter:'date',formatoptions: {newformat:'Y-m'}},
            {label: '毕业时间', name: 'finishTime',formatter:'date',formatoptions: {newformat:'Y-m'}},
            {label: '学制', name: 'schoolLen'},
            {label: '学习方式', name: 'learnStyle', formatter:function(cellvalue, options, rowObject){
                return _metaTypeMap[cellvalue]
            }},
            {label: '学位', name: 'degree'},
            {label: '最高学位', name: 'isHighDegree', formatter:function(cellvalue, options, rowObject){
                return cellvalue?"是":"否";
            }},
            {label: '学位授予国家', name: 'degreeCountry', width:150},
            {label: '学位授予单位', name: 'degreeUnit', width:150},
            {label: '学位授予日期', name: 'degreeTime', width:150,formatter:'date',formatoptions: {newformat:'Y-m-d'}}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid4');
    });

    function cadrePost_au(isMainPost, id) {
        url = "${ctx}/cadrePost_au?isMainPost="+ isMainPost +"&cadreId=${cadre.id}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadrePost_del(id) {
        bootbox.confirm("确定删除主职吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadrePost_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    var _id;
    var type;
    function cadrePost_addDispatchs(id) {
        _id = id;
        type = 1
        loadModal("${ctx}/cadrePost_addDispatchs?single=0&cadreId=${cadre.id}&id=" + id, 1000);
    }

    function cadrePost_selectDispatch(id) {
        _id = id;
        type = 3
        loadModal("${ctx}/cadrePost_addDispatchs?single=1&cadreId=${cadre.id}&id=" + id, 1000);
    }

    function cadreAdminLevel_selectStartDispatch(id) {
        _id = id;
        type = 5
        loadModal("${ctx}/cadreAdminLevel_addDispatchs?type=start&cadreId=${cadre.id}&id=" + id, 1000);
    }

    function cadreAdminLevel_selectEndDispatch(id) {
        _id = id;
        type = 6
        loadModal("${ctx}/cadreAdminLevel_addDispatchs?type=end&cadreId=${cadre.id}&id=" + id, 1000);
    }

    function closeSwfPreview() {
        switch (type) {
            case 1:
                cadrePost_addDispatchs(_id);
                break;
            case 3:
                cadrePost_selectDispatch(_id);
                break;
            case 5:
                cadreAdminLevel_selectStartDispatch(_id);
                break;
            case 6:
                cadreAdminLevel_selectEndDispatch(_id);
                break;
        }
    }

    function cadreAdminLevel_au(id) {
        url = "${ctx}/cadreAdminLevel_au?cadreId=${cadre.id}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadreAdminLevel_del(id) {
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreAdminLevel_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadrePost_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>