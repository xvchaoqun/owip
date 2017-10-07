<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="space-4"></div>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-full"></i> 家庭成员信息
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
            <div class="buttons">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=${HF_CADRE_FAMLIY}">
                    <i class="fa fa-info-circle"></i> 填写说明</a>
                <shiro:hasPermission name="cadreFamliy:edit">
                    <a class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreFamliy_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreFamliy_au"
                       data-grid-id="#jqGrid_cadreFamliy"
                       data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreFamliy:del">
                    <a data-url="${ctx}/cadreFamliy_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<div class='bolder text-danger'>（与此关联的家庭成员移居国（境）外的情况也将删除）</div>"
                            data-grid-id="#jqGrid_cadreFamliy"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </a>
                </shiro:hasPermission>
            </div>
    </shiro:lacksRole>
    </c:if>
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_cadreFamliy" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_cadreFamliy"></div>
        </div>
    </div>
</div>

<div class="space-4"></div>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-battery-full"></i> 家庭成员移居国（境）外的情况

        <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
            <span style="color: #000;font-size: 14px; font-weight: normal">
            <input type="checkbox" data-name="famliy_abroad" name="check" class="cadre-info-check"> 无此类情况
                </span>
        </shiro:lacksRole>
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
            <div class="buttons">
                <shiro:hasPermission name="cadreFamliy:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreFamliyAbroad_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                        添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreFamliyAbroad_au"
                       data-grid-id="#jqGrid_cadreFamliyAbroad"
                       data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreFamliy:del">
                    <button data-url="${ctx}/cadreFamliyAbroad_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadreFamliyAbroad"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
    </shiro:lacksRole>
    </c:if>
        </h4>

    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_cadreFamliyAbroad" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_cadreFamliyAbroad"></div>
        </div>
    </div>
</div>


<script>
    <c:if test="${!canUpdate}">
    $("button.btn").prop("disabled", true);
    </c:if>
    $(".cadre-info-check").prop("checked", ${!canUpdate});
    <c:if test="${!canUpdateInfoCheck}">
    $(".cadre-info-check").prop("disabled", true);
    </c:if>

    $("#jqGrid_cadreFamliy").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        pager: "#jqGridPager_cadreFamliy",
        ondblClickRow: function () {
        },
        url: '${ctx}/cadreFamliy_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '称谓', name: 'title', frozen: true, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CADRE_FAMLIY_TITLE_MAP[cellvalue]
            }
            },
            {label: '姓名', width: 120, name: 'realname'},
            {label: '出生年月', name: 'birthday', formatter: 'date', formatoptions: {newformat: 'Y-m'}},
            /*{
                label: '年龄',
                name: 'birthday',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    return calAge(cellvalue);
                }
            },*/
            {
                label: '政治面貌', name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.politicalStatusMap[cellvalue].name
            }
            },
            {label: '工作单位及职务', name: 'unit', width: 450, align:"left"}
        ]
    }).jqGrid("setFrozenColumns");

    $("#jqGrid_cadreFamliyAbroad").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        pager: "#jqGridPager_cadreFamliyAbroad",
        ondblClickRow: function () {
        },
        url: '${ctx}/cadreFamliyAbroad_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '称谓',
                name: 'cadreFamliy.title',
                frozen: true,
                formatter: function (cellvalue, options, rowObject) {
                    return _cMap.CADRE_FAMLIY_TITLE_MAP[cellvalue]
                }
            },
            {label: '姓名', name: 'cadreFamliy.realname'},
            {label: '移居国家', name: 'country', width: 200},
            {
                label: '移居类别', name: 'type', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.abroadTypeMap[cellvalue].name
            }
            },
            {label: '移居时间', name: 'abroadTime', formatter: 'date', formatoptions: {newformat: 'Y-m'}},
            {label: '现居住城市', name: 'city', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid4');
</script>