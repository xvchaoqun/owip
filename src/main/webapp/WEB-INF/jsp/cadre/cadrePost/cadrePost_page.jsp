<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-full"></i> 主职</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <c:if test="${fn:length(cadreMainWorks)==0}">
                <div class="buttons pull-right">
                    <a class="btn btn-info btn-sm" onclick="cadreMainWork_au()"><i class="fa fa-plus"></i> 添加主职</a>
                </div>
                <h4>&nbsp;</h4>
                <div class="space-4"></div>
            </c:if>
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>主职</th>
							<th>职务属性</th>
							<th>行政级别</th>
							<th>是否正职</th>
							<th>职务类别</th>
							<th>所属单位</th>
							<th>任职日期</th>
							<th>现任职务始任日期</th>
							<th>现任职务始任文号</th>
							<th>是否双肩挑</th>
							<th>双肩挑单位</th>
							<th>现任职务年限</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${cadreMainWorks}" var="cadreMainWork" varStatus="st">

                        <tr>
								<td>${cadreMainWork.work}</td>
								<td>${postMap.get(cadreMainWork.postId).name}</td>
								<td>${adminLevelMap.get(cadreMainWork.adminLevelId).name}</td>
								<td>${cadreMainWork.isPositive?"是":"否"}</td>
								<td>${postClassMap.get(cadreMainWork.postClassId).name}</td>
								<td>${unitMap.get(cadreMainWork.unitId).name}</td>
								<td>${cm:formatDate(cadreMainWork.postTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(cadreMainWork.startTime,'yyyy-MM-dd')}</td>
								<td>${dispatchMap.get(dispatchCadreMap.get(cadreMainWork.dispatchCadreId).dispatchId).code}
                                <a class="btn btn-mini btn-primary" onclick="cadreMainWork_selectDispatch(${cadreMainWork.id})">
                                    ${cadreMainWork.dispatchCadreId==null?"选择":"修改"}</a>
                                </td>
								<td>${cadreMainWork.isDouble?"是":"否"}</td>
								<td>${unitMap.get(cadreMainWork.doubleUnitId).name}</td>
								<td>${cm:intervalYearsUntilNow(cadreMainWork.startTime)}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                        <button onclick="cadreMainWork_au(${cadreMainWork.id})" class="btn btn-mini btn-warning">
                                            <i class="fa fa-edit"></i> 编辑
                                        </button>
                                        <button onclick="cadreMainWork_addDispatchs(${cadreMainWork.id})" class="btn btn-mini">
                                            <i class="fa fa-edit"></i> 关联任免文件
                                        </button>
                                    <button class="btn btn-danger btn-mini" onclick="cadreMainWork_del(${cadreMainWork.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
</div></div></div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-half"></i> 兼职</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
<div class="buttons pull-right">
    <a class="btn btn-info btn-sm" onclick="cadreSubWork_au()"><i class="fa fa-plus"></i> 添加兼职</a>
</div>
<h4 >&nbsp;</h4>
<div class="space-4"></div>
<table class="table table-striped table-bordered table-hover table-condensed">
    <thead>
    <tr>
        <th>兼任单位</th>
        <th>兼任职务</th>
        <th>兼任职务任职日期</th>
        <th>兼任职务始任日期</th>
        <th>兼任职务始任文号</th>
        <th nowrap></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${cadreSubWorks}" var="cadreSubWork" varStatus="st">

        <tr>
            <td>${unitMap.get(cadreSubWork.unitId).name}</td>
            <td>${cadreSubWork.post}</td>
            <td>${cm:formatDate(cadreSubWork.postTime,'yyyy-MM-dd')}</td>
            <td>${cm:formatDate(cadreSubWork.startTime,'yyyy-MM-dd')}</td>
            <td>
                    ${dispatchMap.get(dispatchCadreMap.get(cadreSubWork.dispatchCadreId).dispatchId).code}
                <a class="btn btn-mini btn-primary" onclick="cadreSubWork_selectDispatch(${cadreSubWork.id})">
                        ${cadreSubWork.dispatchCadreId==null?"选择":"修改"}</a>
            </td>
            <td>
                <div class="hidden-sm hidden-xs action-buttons">
                        <button onclick="cadreSubWork_au(${cadreSubWork.id})" class="btn btn-mini btn-warning">
                            <i class="fa fa-edit"></i> 编辑
                        </button>
                        <button onclick="cadreSubWork_addDispatchs(${cadreSubWork.id})" class="btn btn-mini">
                            <i class="fa fa-edit"></i> 关联任免文件
                        </button>
                        <button class="btn btn-danger btn-mini" onclick="cadreSubWork_del(${cadreSubWork.id})">
                            <i class="fa fa-times"></i> 删除
                        </button>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div></div></div>

<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-history"></i> 任职级经历</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
<div class="buttons pull-right">
    <a class="btn btn-info btn-sm" onclick="cadrePost_au()"><i class="fa fa-plus"></i> 添加任职级经历</a>
</div>
<h4>&nbsp;</h4>
<div class="space-4"></div>
<table class="table table-striped table-bordered table-hover table-condensed">
    <thead>
    <tr>
        <th>行政级别</th>
        <th>是否现任职级</th>
        <th>职级始任日期</th>
        <th>职级始任文号</th>
        <th>职级结束日期</th>
        <th>职级结束文号</th>
        <th>备注</th>
        <th nowrap></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${cadrePosts}" var="cadrePost" varStatus="st">

        <tr>
            <td>${adminLevelMap.get(cadrePost.adminLevelId).name}</td>
            <td>${cadrePost.isPresent?"是":"否"}</td>
            <td>${cm:formatDate(cadrePost.startTime,'yyyy-MM-dd')}</td>
            <td>
                    ${dispatchMap.get(dispatchCadreMap.get(cadrePost.startDispatchCadreId).dispatchId).code}
                <a class="btn btn-mini btn-primary" onclick="cadrePost_selectStartDispatch(${cadrePost.id})">
                        ${cadrePost.startDispatchCadreId==null?"选择":"修改"}</a>
            </td>
            <td>${cm:formatDate(cadrePost.endTime,'yyyy-MM-dd')}</td>
            <td>
                    ${dispatchMap.get(dispatchCadreMap.get(cadrePost.endDispatchCadreId).dispatchId).code}
                <a class="btn btn-mini btn-primary" onclick="cadrePost_selectEndDispatch(${cadrePost.id})">
                        ${cadrePost.endDispatchCadreId==null?"选择":"修改"}
            </td>
            <td>${cadrePost.remark}</td>
            <td>
                <div class="hidden-sm hidden-xs action-buttons">
                        <button onclick="cadrePost_au(${cadrePost.id})" class="btn btn-mini btn-warning">
                            <i class="fa fa-edit"></i> 编辑
                        </button>
                        <button class="btn btn-danger btn-mini" onclick="cadrePost_del(${cadrePost.id})">
                            <i class="fa fa-times"></i> 删除
                        </button>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
            </div></div></div>
<script>

    function cadreMainWork_au(id) {
        url = "${ctx}/cadreMainWork_au?cadreId=${cadre.id}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url, 800);
    }

    function cadreMainWork_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreMainWork_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function cadreSubWork_au(id) {
        url = "${ctx}/cadreSubWork_au?cadreId=${cadre.id}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadreSubWork_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreSubWork_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    var _id;
    var type;
    function cadreMainWork_addDispatchs(id){
        _id = id;
        type=1
        loadModal("${ctx}/cadreMainWork_addDispatchs?type=checkbox&cadreId=${cadre.id}&id="+id, 1000);
    }
    function cadreSubWork_addDispatchs(id){
        _id = id;
        type=2
        loadModal("${ctx}/cadreSubWork_addDispatchs?type=checkbox&cadreId=${cadre.id}&id="+id, 1000);
    }

    function cadreMainWork_selectDispatch(id){
        _id = id;
        type=3
        loadModal("${ctx}/cadreMainWork_addDispatchs?type=radio&cadreId=${cadre.id}&id="+id, 1000);
    }

    function cadreSubWork_selectDispatch(id){
        _id = id;
        type=4
        loadModal("${ctx}/cadreSubWork_addDispatchs?type=radio&cadreId=${cadre.id}&id="+id, 1000);
    }

    function cadrePost_selectStartDispatch(id){
        _id = id;
        type=5
        loadModal("${ctx}/cadrePost_addDispatchs?type=start&cadreId=${cadre.id}&id="+id, 1000);
    }

    function cadrePost_selectEndDispatch(id){
        _id = id;
        type=6
        loadModal("${ctx}/cadrePost_addDispatchs?type=end&cadreId=${cadre.id}&id="+id, 1000);
    }

    function closeSwfPreview(){
        switch (type) {
            case 1:
                cadreMainWork_addDispatchs(_id);
                break;
            case 2:
                cadreSubWork_addDispatchs(_id);
                break;
            case 3:
                cadreMainWork_selectDispatch(_id);
                break;
            case 4:
                cadreSubWork_selectDispatch(_id);
                break;
            case 5:
                cadrePost_selectStartDispatch(_id);
                break;
            case 6:
                cadrePost_selectEndDispatch(_id);
                break;
        }
    }

    function cadrePost_au(id) {
        url = "${ctx}/cadrePost_au?cadreId=${cadre.id}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function cadrePost_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadrePost_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadrePost_page?${pageContext.request.queryString}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#searchForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>