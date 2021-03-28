<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-home"></i> 家庭成员信息
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <div class="buttons">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_cadre_family">
                    <i class="fa fa-info-circle"></i> 填写说明</a>
                <shiro:hasPermission name="cadreFamily:edit">
                    <a class="popupBtn btn btn-success btn-sm" data-width="800"
                       data-url="${ctx}/cadreFamily_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreFamily_au"  data-width="800"
                       data-grid-id="#jqGrid_cadreFamily"
                       data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreFamily:del">
                    <a data-url="${ctx}/cadreFamily_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<div class='bolder text-danger'>（与此关联的家庭成员移居国（境）外的情况也将删除）</div>"
                            data-grid-id="#jqGrid_cadreFamily"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </a>
                </shiro:hasPermission>
            </div>
    </shiro:lacksPermission>
    </c:if>
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table id="jqGrid_cadreFamily" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_cadreFamily"></div>
        </div>
    </div>
</div>

<div class="space-4"></div>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-plane"></i> 配偶、子女及其配偶移居国（境）外情况

        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <span style="color: #000;font-size: 14px; font-weight: normal">
            <input type="checkbox" data-name="family_abroad" name="check" class="cadre-info-check"> 无此类情况
                </span>
        </shiro:lacksPermission>
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            <div class="buttons">
                <shiro:hasPermission name="cadreFamily:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreFamilyAbroad_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreFamilyAbroad_au"
                       data-grid-id="#jqGrid_cadreFamilyAbroad"
                       data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreFamily:del">
                    <button data-url="${ctx}/cadreFamilyAbroad_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid_cadreFamilyAbroad"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
    </shiro:lacksPermission>
    </c:if>
        </h4>

    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table id="jqGrid_cadreFamilyAbroad" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_cadreFamilyAbroad"></div>
        </div>
    </div>
</div>


<script>
    <c:if test="${!canUpdate}">
    $(".cadreView button.btn").prop("disabled", true);
    </c:if>
    $(".cadre-info-check").prop("checked", ${!canUpdate});
    <c:if test="${!canUpdateInfoCheck}">
    $(".cadre-info-check").prop("disabled", true);
    </c:if>

    $("#jqGrid_cadreFamily").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        pager: "#jqGridPager_cadreFamily",
        ondblClickRow: function () {
        },
        url: '${ctx}/cadreFamily_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreFamily
    }).jqGrid("setFrozenColumns");
    //$.initNavGrid("jqGrid_cadreFamily", "jqGridPager_cadreFamily");

    $("#jqGrid_cadreFamilyAbroad").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        pager: "#jqGridPager_cadreFamilyAbroad",
        ondblClickRow: function () {
        },
        url: '${ctx}/cadreFamilyAbroad_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreFamilyAbroad
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid4');
</script>