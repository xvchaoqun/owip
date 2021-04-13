<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-list"></i> 工作经历</a>
    </li>
   <%-- <li class="${type==3?"active":""}">
        <a href="javascript:;" onclick="_innerPage(3)"><i class="fa fa-flag"></i> 挂职锻炼经历</a>
    </li>--%>
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <li class="${type==2?"active":""}">
                <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
            </li>
            <li class="${type==3?"active":""}">
                <a href="javascript:;" onclick="_innerPage(3)"><i class="fa fa-list-ol"></i> 干部任免审批表简历预览</a>
            </li>
        </shiro:lacksPermission>
</ul>
</shiro:hasPermission>
<c:if test="${type==1}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <c:if test="${!cm:getHtmlFragment('hf_cadre_work').isDeleted}">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_cadre_work">
                    <i class="fa fa-info-circle"></i> 填写说明</a>
                </c:if>
                <shiro:hasPermission name="cadreWork:edit">
                    <a class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreWork_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加工作经历</a>

                    <a class="jqOpenViewBtn btn btn-info btn-sm"
                       data-grid-id="#jqGrid_cadreWork"
                       data-url="${ctx}/cadreWork_au"
                       data-id-name="fid"
                       data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加其间工作</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreWork:del">
                    <button data-url="${ctx}/cadreWork_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid_cadreWork"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                    <%--<span style="padding-left: 50px">点击列表第二列图标 <i class="fa fa-folder-o"></i> 显示/隐藏其间工作经历 </span>--%>
            </div>
        </shiro:lacksPermission>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreWork" class="jqGrid2"></table>
    <div id="jqGridPager_cadreWork"></div>
</c:if>
<c:if test="${type==2}">
    <div class="space-4"></div>
    <div class="row">
        <div class="col-xs-6 preview-text">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main resume" style="min-height: 647px" id="orginal">
                        <jsp:useBean id='map' class='java.util.HashMap' scope='request'>
                            <c:set target='${map}' property='cadreWorks' value='${cadreWorks}'/>
                        </jsp:useBean>
                        ${cm:freemarker(map, '/cadre/cadreWork.ftl')}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        最终数据（<span
                            style="font-weight: bolder; color: red;"
                            id="saveTime">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:;" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input id="saveBtn" type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>
<c:if test="${type==3}">
    <div class="space-4"></div>
    <div class="row">
        <div class="col-xs-6 preview-text">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main resume" style="min-height: 647px" id="orginal">
                        <jsp:useBean id='map3' class='java.util.HashMap' scope='request'>
                            <c:set target='${map3}' property='cadreResumes' value='${cadreResumes}'/>
                        </jsp:useBean>
                        ${cm:freemarker(map3, '/cadre/cadreResume.ftl')}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-6">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        最终数据（<span
                            style="font-weight: bolder; color: red;"
                            id="saveTime">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:;" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input id="saveBtn" type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>
<script type="text/template" id="switch_tpl">
    <button class="switchBtn btn btn-info btn-xs" onclick="_swtich({{=id}}, this)"
            data-id="{{=id}}"><i class="fa fa-folder-o"></i>
        <span>查看其间工作</span>({{=subWorkCount}})
    </button>
</script>
<script type="text/template" id="op_tpl">
<shiro:hasPermission name="cadreWork:edit">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/cadreWork_au?id={{=id}}&cadreId={{=cadreId}}&&fid={{=parentRowKey}}"><i
            class="fa fa-edit"></i> 编辑
    </button>
    <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
    {{if(parentRowKey>0){}}
    <button class="confirm btn btn-xs btn-info"
            data-msg="确定转移至主要工作经历？"
            data-callback="_callback"
            data-url="${ctx}/cadreWork_transfer?id={{=id}}&cadreId={{=cadreId}}"><i
            class="fa fa-reply"></i> 转移
    </button>
    {{}else if(subWorkCount==0){}}
        <button class="popupBtn btn btn-xs btn-info"
                data-url="${ctx}/cadreWork_transferToSubWork?id={{=id}}&cadreId={{=cadreId}}"><i
                class="fa fa-reply"></i> 转移
        </button>
    {{}}}
    </shiro:hasPermission>
</shiro:hasPermission>
<shiro:hasPermission name="cadreWork:del">
    <button class="confirm btn btn-xs btn-danger"
            data-parent="{{=parentRowKey}}"
            data-url="${ctx}/cadreWork_batchDel?ids={{=id}}&cadreId=${param.cadreId}"
            data-msg="确定删除该工作经历？"
            data-callback="_callback"><i class="fa fa-times"></i> 删除
    </button>
    </shiro:hasPermission>
</script>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadreWork_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<style>
    .noSubWork [aria-describedby="jqGrid_cadreWork_subgrid"] a {
        display: none;
    }
    .table > tbody > tr.ui-subgrid.active > td,
    .table tbody tr.ui-subgrid:hover td, .table tbody tr.ui-subgrid:hover th {
        background-color: inherit !important;
    }

    .ui-subgrid tr.success td {
        background-color: inherit !important;
    }
    .resume p {
         text-indent: -9em;
         margin: 0 0 0 9em;
    }
</style>
<c:if test="${type==2}">

    <script>
        var ke = KindEditor.create('#content', {
            cssPath: "${ctx}/css/ke.css",
            items: ["source", "|", "fullscreen"],
            height: '550px',
            width: '100%',
            filterMode: true,
            htmlTags:{
                br : ['/'], p:['.text-indent']
            }
        });
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html().removeSpan(),
                type: "<%=CadreConstants.CADRE_INFO_TYPE_WORK%>"
            }, function (ret) {
                if (ret.success) {
                    _innerPage(2, function () {
                        $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 保存成功', position:{my:'bottom center'}});
                    });
                }
            });
        }
        function copyOrginal() {
            //console.log($("#orginal").html())
            ke.html($("#orginal").html());
            $("#saveTime").html("未保存");
            $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 复制成功，请点击"保存"按钮进行保存', position:{my:'bottom center'}});
        }
    </script>
</c:if>
<c:if test="${type==3}">

    <script>
        var ke = KindEditor.create('#content', {
            cssPath: "${ctx}/css/ke.css",
            items: ["source", "|", "fullscreen"],
            height: '550px',
            width: '100%',
            filterMode: true,
            htmlTags:{
                br : ['/'], p:['.text-indent']
            }
        });
        function updateCadreInfo() {

            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html().removeSpan(),
                type: "<%=CadreConstants.CADRE_INFO_TYPE_RESUME%>"
            }, function (ret) {
                if (ret.success) {
                    _innerPage(3, function () {
                        $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 保存成功', position:{my:'bottom center'}});
                    });
                }
            });
        }
        function copyOrginal() {
            //console.log($("#orginal").html())
            ke.html($("#orginal").html());
            $("#saveTime").html("未保存");
            $("#saveBtn").tip({content: '<i class="fa fa-check-circle green"></i> 复制成功，请点击"保存"按钮进行保存', position:{my:'bottom center'}});
        }
    </script>
</c:if>
<c:if test="${type==1}">
    <script>
        function _innerPage(type, fn) {
            $("#tab-content").loadPage({url:"${ctx}/cadreWork_page?cadreId=${param.cadreId}&type=" + type, callback:fn})
        }
        $("#jqGrid_cadreWork").jqGrid({
            <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect: false,
            </c:if>
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreWork",
            url: '${ctx}/cadreWork_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {
                    label: '其间工作', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.subWorkCount == 0) return '--';
                    return _.template($("#switch_tpl").html().NoMultiSpace())({
                        id: rowObject.id,
                        subWorkCount: rowObject.subWorkCount
                    });
                }, width: 130,frozen:true
                },
                {label: '开始日期', name: 'startTime', width: 80, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'},frozen:true},
                {label: '结束日期', name: 'endTime', width: 80, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'},frozen:true},
                {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align: 'left',frozen:true},
                {label: '工作类型', name: 'workTypes', align:'left', width: 130, formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return '--'
                    return ($.map(cellvalue.split(","), function(workType){
                        return $.jgrid.formatter.MetaType(workType);
                    })).join("，")
                }},
                {label: '是否担任领导职务', name: 'isCadre', width: 130, formatter: $.jgrid.formatter.TRUEFALSE},
                {label: '补充说明', name: 'note', width: 200},
                {label: '备注', name: 'remark', width: 150},
                {
                    label: '干部任免文件', name: 'dispatchCadreRelates', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--'
                    var count = cellvalue.length;
                    <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                    if (count == 0) return '--'
                    </shiro:lacksPermission>
                    return _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
                }, width: 120
                },
                <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
                <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                        {
                            label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                            //alert(rowObject.id)
                            return _.template($("#op_tpl").html().NoMultiSpace())
                            ({id: rowObject.id, parentRowKey: null, subWorkCount: rowObject.subWorkCount, cadreId: rowObject.cadreId})
                        }, width: ${cm:isPermitted(PERMISSION_CADREADMIN)?200:150}
                        }
                </shiro:lacksPermission>
                </c:if>
            ],
            rowattr: function (rowData, currentObj, rowId) {
                //console.log(currentObj)
                if (currentObj.subWorkCount == 0) {
                    //console.log(rowId)
                    //console.log($("#"+rowId).text())
                    return {'class': 'noSubWork'}
                }
            },
            subGrid: true,
            subGridRowExpanded: subGridRowExpanded,
            subGridRowColapsed: subGridRowColapsed,
            subGridOptions: {
                plusicon: "fa fa-folder-o",
                minusicon: "fa fa-folder-open-o"
                // selectOnExpand:true
            }
        }).on("initGrid", function () {

            $('.noSubWork [aria-describedby="jqGrid_cadreWork_subgrid"]').removeClass();

            //console.log(currentExpandRows)
            currentExpandRows.forEach(function(item, i){
                $("#jqGrid_cadreWork").expandSubGridRow(item)
            })
        });

        $(window).triggerHandler('resize.jqGrid2');

        function _swtich(id, btn) {

            if (!$("i", btn).hasClass("fa-folder-open-o")) {
                $("#jqGrid_cadreWork").expandSubGridRow(id)
            } else {
                $("#jqGrid_cadreWork").collapseSubGridRow(id)
            }
            $.getEvent().stopPropagation();
        }

        var currentExpandRows = [];
        function subGridRowColapsed(parentRowID, parentRowKey) {
            $(".switchBtn i", '#jqGrid_cadreWork #' + parentRowKey).removeClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_cadreWork #' + parentRowKey).html("查看其间工作");
            currentExpandRows.remove(parentRowKey);
        }
        // the event handler on expanding parent row receives two parameters
        // the ID of the grid tow  and the primary key of the row
        function subGridRowExpanded(parentRowID, parentRowKey) {

            $(".switchBtn i", '#jqGrid_cadreWork #' + parentRowKey).addClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_cadreWork #' + parentRowKey).html("隐藏其间工作");
            currentExpandRows.remove(parentRowKey);
            currentExpandRows.push(parentRowKey);

            var childGridID = parentRowID + "_table";
            var childGridPagerID = parentRowID + "_pager";

            var childGridURL = '${ctx}/cadreWork_data?isEduWork=0&fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

            $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
            $("#" + childGridID).jqGrid({
                ondblClickRow: function () {
                },
                multiselect: false,
                url: childGridURL,
                colModel: [
                    {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                    {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                    {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align: 'left'},
                    {label: '工作类型', name: 'workTypes', formatter: function (cellvalue, options, rowObject) {
                        if($.trim(cellvalue)=='') return '--'
                        return ($.map(cellvalue.split(","), function(workType){
                            return $.jgrid.formatter.MetaType(workType);
                        })).join("，")
                    }, width: 140},
                    {label: '是否担任领导职务', name: 'isCadre', width: 140, formatter: $.jgrid.formatter.TRUEFALSE},
                    {label: '备注', name: 'remark', width: 150},
                    {
                        label: '干部任免文件',
                        name: 'dispatchCadreRelates',
                        formatter: function (cellvalue, options, rowObject) {
                            if(cellvalue==undefined) return '--'
                            var count = cellvalue.length;
                            <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                            if (count == 0) return '--'
                            </shiro:lacksPermission>
                            return _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                            ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
                        },
                        width: 120
                    },
                    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    {
                        label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                        //alert(rowObject.id)
                        return _.template($("#op_tpl").html().NoMultiSpace())
                        ({id: rowObject.id, parentRowKey: parentRowKey, cadreId: rowObject.cadreId})
                    }, width: ${cm:isPermitted(PERMISSION_CADREADMIN)?200:150}
                    }
                    </shiro:lacksPermission>
                    </c:if>
                ],
                pager: null
            });
        }

        function _callback(target) {
            //_reloadSubGrid($(target).data("parent"))
            $("#jqGrid_cadreWork").trigger("reloadGrid");
        }

        function _reloadSubGrid(fid) {
            if (fid > 0) $("#jqGrid_cadreWork").collapseSubGridRow(fid).expandSubGridRow(fid)
        }

        // 删除其间工作时调用
        function showSubWork(id) {
            $.loadModal("${ctx}/cadreWork_page?cadreId=${param.cadreId}&fid=" + id, 1000);
        }

        $('#searchForm [data-rel="select2"]').select2();
        $('[data-rel="tooltip"]').tooltip();
    </script>
</c:if>
