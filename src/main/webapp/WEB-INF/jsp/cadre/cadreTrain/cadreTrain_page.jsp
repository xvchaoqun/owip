<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 培训情况</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
    </li>
</ul>
<c:if test="${type==1}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="cadreTrain:edit">
            <a class="popupBtn btn btn-success btn-sm"
               data-url="${ctx}/cadreTrain_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                添加</a>
            <a class="jqOpenViewBtn btn btn-primary btn-sm"
               data-url="${ctx}/cadreTrain_au"
               data-grid-id="#jqGrid_cadreTrain"
               data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                修改</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="cadreTrain:del">
            <button data-url="${ctx}/cadreTrain_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreTrain"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadreTrain" class="jqGrid2"></table>
    <div id="jqGridPager_cadreTrain"></div>
</c:if>
<c:if test="${type==2}">
    <div class="row two-frames">
        <div class="left">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        初始数据
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="min-height: 647px" id="orginal">
                        <c:forEach items="${cadreTrains}" var="cadreTrain">
                            <p>${cm:formatDate(cadreTrain.startTime, "yyyy.MM")}${(cadreTrain.endTime!=null)?"-":"-至今"}${cm:formatDate(cadreTrain.endTime, "yyyy.MM")}，${cadreTrain.content}，${cadreTrain.unit}主办</p>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="right">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        最终数据（<span
                            style="font-weight: bolder; color: red;">最近保存时间：${empty cadreInfo.lastSaveDate?"未保存":cm:formatDate(cadreInfo.lastSaveDate, "yyyy-MM-dd HH:mm")}</span>）
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <textarea id="content">${cadreInfo.content}</textarea>
                        <input type="hidden" name="content">
                    </div>
                    <div class="modal-footer center">
                        <a href="javascript:" onclick="copyOrginal()" class="btn btn-sm btn-success">
                            <i class="ace-icon fa fa-copy"></i>
                            同步自动生成的数据
                        </a>
                        <input type="button" onclick="updateCadreInfo()" class="btn btn-primary" value="保存"/>

                    </div>
                </div>
            </div>

        </div>
    </div>
</c:if>
<div class="row footer-margin lower">&nbsp;</div>
<c:if test="${type==2}">
    <script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
    <script>
        var ke = KindEditor.create('#content', {
            items : ["source", "|", "fullscreen"],
            height: '550px',
            width: '700px'
        });
        function updateCadreInfo() {
            $.post("${ctx}/cadreInfo_updateContent", {
                cadreId: '${param.cadreId}',
                content: ke.html(),
                type:"${CADRE_INFO_TYPE_TRAIN}"
            }, function (ret) {
                if (ret.success) {
                    SysMsg.info("保存成功", "", function () {
                        _innerPage(2)
                    });
                }
            });
        }
        function copyOrginal() {
            //console.log($("#orginal").html())
            ke.html($("#orginal").html());
            SysMsg.info("复制成功，请务必点击\"保存\"按钮进行保存")
        }
    </script>
</c:if>
<c:if test="${type==1}">
    <script>
        function _innerPage(type) {
            $("#view-box .tab-content").load("${ctx}/cadreTrain_page?cadreId=${param.cadreId}&type=" + type)
        }
        $("#jqGrid_cadreTrain").jqGrid({
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_cadreTrain",
            url: '${ctx}/cadreTrain_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {label: '起始时间', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'},frozen:true },
                {label: '结束时间', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m.d'},frozen:true },
                {label: '培训内容', name: 'content', width: 350},
                {label: '主办单位', name: 'unit', width: 280},
                {label: '备注', name: 'remark', width: 350}
            ]
        }).on("initGrid", function () {
            $(window).triggerHandler('resize.jqGrid2');
        });

        function _delCallback(target) {
            $("#jqGrid_cadreTrain").trigger("reloadGrid");
        }

        $('#searchForm [data-rel="select2"]').select2();
        $('[data-rel="tooltip"]').tooltip();
    </script>
</c:if>