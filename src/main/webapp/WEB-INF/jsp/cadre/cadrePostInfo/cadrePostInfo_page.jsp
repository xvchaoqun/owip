<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:;" onclick="_innerPage2(1)"><i class="fa fa-flag"></i> 专技岗位过程信息</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:;" onclick="_innerPage2(2)"><i class="fa fa-flag"></i> 管理岗位过程信息</a>
    </li>
    <li class="${type==3?"active":""}">
        <a href="javascript:;" onclick="_innerPage2(3)"><i class="fa fa-flag"></i> 工勤岗位过程信息</a>
    </li>
<shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="buttons" style="position:absolute;left: 520px;">
    <a class="popupBtn btn btn-warning btn-sm"
       data-width="800"
       data-url="${ctx}/hf_content?code=hf_cadre_post_info">
        <i class="fa fa-info-circle"></i> 填写说明</a>
    </div>
</shiro:lacksPermission>
</ul>

<c:if test="${type==1}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <shiro:hasPermission name="cadrePostInfo:edit">
                <button class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/cadrePostPro_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/cadrePostPro_au"
                   data-grid-id="#jqGrid_cadrePostPro"
                   data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                    修改</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadrePostInfo:del">
                <button data-url="${ctx}/cadrePostPro_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_cadrePostPro"
                        data-querystr="cadreId=${param.cadreId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-times"></i> 删除
                </button>
            </shiro:hasPermission>
        </shiro:lacksPermission>
    </c:if>
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="post_pro" name="check" class="cadre-info-check"> 无此类情况
        </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePostPro" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePostPro"></div>
</c:if>
<c:if test="${type==2}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">

            <shiro:hasPermission name="cadrePostInfo:edit">
                <button class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/cadrePostAdmin_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/cadrePostAdmin_au"
                   data-grid-id="#jqGrid_cadrePostAdmin"
                   data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                    修改</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadrePostInfo:del">
                <button data-url="${ctx}/cadrePostAdmin_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_cadrePostAdmin"
                        data-querystr="cadreId=${param.cadreId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-times"></i> 删除
                </button>
            </shiro:hasPermission>
        </shiro:lacksPermission>
    </c:if>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <input type="checkbox" data-name="post_admin" name="check" class="cadre-info-check"> 无此类情况
    </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePostAdmin" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePostAdmin"></div>
</c:if>
<c:if test="${type==3}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">

            <shiro:hasPermission name="cadrePostInfo:edit">
                <button class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/cadrePostWork_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/cadrePostWork_au"
                   data-grid-id="#jqGrid_cadrePostWork"
                   data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                    修改</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadrePostInfo:del">
                <button data-url="${ctx}/cadrePostWork_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_cadrePostWork"
                        data-querystr="cadreId=${param.cadreId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-times"></i> 删除
                </button>
            </shiro:hasPermission>

        </shiro:lacksPermission>
    </c:if>
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <input type="checkbox" data-name="post_work" name="check" class="cadre-info-check"> 无此类情况
        </shiro:lacksPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePostWork" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePostWork"></div>
</c:if>
<script>
    function _innerPage2(type) {
        $("#view-box .tab-content").loadPage("${ctx}/cadrePostInfo_page?cadreId=${param.cadreId}&type=" + type)
    }

    <c:if test="${!canUpdate}">
    $("button.btn").prop("disabled", true);
    </c:if>
    $(".cadre-info-check").prop("checked", ${!canUpdate});
    <c:if test="${!canUpdateInfoCheck}">
    $(".cadre-info-check").prop("disabled", true);
    </c:if>

    <c:if test="${type==1}">
    $("#jqGrid_cadrePostPro").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostPro",
        url: '${ctx}/cadrePostPro_data?cadreId=${param.cadreId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadrePostPro
    });
    </c:if>
    <c:if test="${type==2}">
    $("#jqGrid_cadrePostAdmin").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostAdmin",
        url: '${ctx}/cadrePostAdmin_data?cadreId=${param.cadreId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadrePostAdmin
    });
    </c:if>
    <c:if test="${type==3}">
    $("#jqGrid_cadrePostWork").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostWork",
        url: '${ctx}/cadrePostWork_data?cadreId=${param.cadreId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadrePostWork
    });
    </c:if>
    $(window).triggerHandler('resize.jqGrid2');
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>