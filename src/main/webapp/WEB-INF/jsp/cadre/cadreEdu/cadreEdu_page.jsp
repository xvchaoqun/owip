<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
   <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-list"></i> 学习经历</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-list-ol"></i> 预览</a>
    </li>
</ul>
   </shiro:lacksPermission>
</shiro:hasPermission>
<c:if test="${type==1}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="cadreEdu:edit">
            <button class="popupBtn btn btn-warning btn-sm"
               data-width="800"
               data-url="${ctx}/hf_content?code=hf_cadre_edu">
                <i class="fa fa-info-circle"></i> 填写说明</button>
            <%--<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-url="${ctx}/cadreEdu_rule?cadreId=${param.cadreId}"><i class="fa fa-search"></i>
                    学历学位的认定规则</a>
            </shiro:hasPermission>--%>
            <button class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreEdu_au?cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-plus"></i>
                添加学习经历</button>
            <button class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreEdu_au"
               data-grid-id="#jqGrid_cadreEdu"
               data-querystr="&cadreId=${param.cadreId}"
               data-width="900"><i class="fa fa-edit"></i>
                修改学习经历</button>
            <button class="jqOpenViewBtn btn btn-info btn-sm"
                       data-grid-id="#jqGrid_cadreEdu"
                       data-url="${ctx}/cadreWork_au?isEduWork=1"
                       data-id-name="fid"
                       data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加其间经历</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreEdu:del">
            <button data-url="${ctx}/cadreEdu_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreEdu"
                    data-querystr="cadreId=${param.cadreId}"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
        </shiro:lacksPermission>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadreEdu" class="jqGrid2"></table>
    <div id="jqGridPager_cadreEdu"></div>
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
                            <c:set target='${map}' property='cadreEdus' value='${cadreEdus}'/>
                        </jsp:useBean>
                        ${cm:freemarker(map, '/cadre/cadreEdu.ftl')}
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
<style>
    .noSubWork [aria-describedby="jqGrid_cadreEdu_subgrid"] a {
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
            filterMode:true,
            htmlTags:{
                p : ['style']
            },
            cssPath:"${ctx}/css/ke.css",
            items: ["source", "|", "fullscreen", "|", 'preview'],
            height: '550px',
            width: '100%'
        });
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html().removeSpan(),
                type: "<%=CadreConstants.CADRE_INFO_TYPE_EDU%>"
            }, function (ret) {
                if (ret.success) {
                    /*SysMsg.info("保存成功", "", function () {
                        _innerPage(2)
                    });*/
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
            //SysMsg.info("复制成功，请务必点击\"保存\"按钮进行保存")
        }
    </script>
</c:if>
<c:if test="${type==1}">
    <script type="text/template" id="switch_tpl">
        <button class="switchBtn btn btn-info btn-xs" onclick="_swtich({{=id}}, this)"
                data-id="{{=id}}"><i class="fa fa-folder-o"></i>
            <span>查看其间经历</span>({{=subWorkCount}})
        </button>
    </script>
    <script type="text/template" id="op_tpl">
    <shiro:hasPermission name="cadreWork:edit">
        <button class="popupBtn btn btn-xs btn-primary"
                data-url="${ctx}/cadreWork_au?id={{=id}}&cadreId={{=cadreId}}&&fid={{=parentRowKey}}"><i
                class="fa fa-edit"></i> 编辑
        </button>
    </shiro:hasPermission>
    <shiro:hasPermission name="cadreWork:del">
        <button class="confirm btn btn-xs btn-danger"
                data-parent="{{=parentRowKey}}"
                data-url="${ctx}/cadreWork_batchDel?ids[]={{=id}}&cadreId=${param.cadreId}"
                data-msg="确定删除该其间经历？"
                data-callback="_callback"><i class="fa fa-times"></i> 删除
        </button>
        </shiro:hasPermission>
    </script>
    <script>
        function _innerPage(type, fn) {
            $("#view-box .tab-content").loadPage({url:"${ctx}/cadreEdu_page?cadreId=${param.cadreId}&type=" + type, callback:fn})
        }

        var colModal = colModels.cadreEdu.concat();
        colModal.unshift({
                    label: '其间经历', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.subWorkCount == 0) return '--';
                    return _.template($("#switch_tpl").html().NoMultiSpace())({
                        id: rowObject.id,
                        subWorkCount: rowObject.subWorkCount
                    });
                }, width: 130
        });

        var needTutorEduTypes = ${cm:toJSONArray(needTutorEduTypes)};
        $("#jqGrid_cadreEdu").jqGrid({
            <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect:false,
            </c:if>
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreEdu",
            url: '${ctx}/cadreEdu_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: colModal,
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

            $('.noSubWork [aria-describedby="jqGrid_cadreEdu_subgrid"]').removeClass();

            //console.log(currentExpandRows)
            currentExpandRows.forEach(function(item, i){
                $("#jqGrid_cadreEdu").expandSubGridRow(item)
            })
        }).jqGrid("setFrozenColumns");
        $(window).triggerHandler('resize.jqGrid2');

        function _swtich(id, btn) {

            if (!$("i", btn).hasClass("fa-folder-open-o")) {
                $("#jqGrid_cadreEdu").expandSubGridRow(id)
            } else {
                $("#jqGrid_cadreEdu").collapseSubGridRow(id)
            }
            $.getEvent().stopPropagation();
        }
        var currentExpandRows = [];
        function subGridRowColapsed(parentRowID, parentRowKey) {
            $(".switchBtn i", '#jqGrid_cadreEdu #' + parentRowKey).removeClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_cadreEdu #' + parentRowKey).html("查看其间经历");
            currentExpandRows.remove(parentRowKey);
        }
        // the event handler on expanding parent row receives two parameters
        // the ID of the grid tow  and the primary key of the row
        function subGridRowExpanded(parentRowID, parentRowKey) {

            $(".switchBtn i", '#jqGrid_cadreEdu #' + parentRowKey).addClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_cadreEdu #' + parentRowKey).html("隐藏其间经历");
            currentExpandRows.remove(parentRowKey);
            currentExpandRows.push(parentRowKey);

            var childGridID = parentRowID + "_table";
            var childGridPagerID = parentRowID + "_pager";

            var childGridURL = '${ctx}/cadreWork_data?fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

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
                    {label: '工作类型', name: 'workType', formatter: $.jgrid.formatter.MetaType, width: 120},
                    {label: '备注', name: 'remark', width: 150},
                    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    {
                        label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                        //alert(rowObject.id)
                        return _.template($("#op_tpl").html().NoMultiSpace())
                        ({id: rowObject.id, parentRowKey: parentRowKey, cadreId: rowObject.cadreId})
                    }, width: ${cm:hasRole(ROLE_CADREADMIN)?200:150}
                    }
                    </shiro:lacksPermission>
                    </c:if>
                ],
                pager: null
            });
        }
        function _callback(target) {
            //_reloadSubGrid($(target).data("parent"))
            $("#jqGrid_cadreEdu").trigger("reloadGrid");
        }

        $.register.fancybox();
    </script>
</c:if>