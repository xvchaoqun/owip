<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=ScConstants.SC_PASSPORTHAND_STATUS_HAND%>" var="SC_PASSPORTHAND_STATUS_HAND"/>
<c:set value="<%=ScConstants.SC_PASSPORTHAND_STATUS_UNHAND%>" var="SC_PASSPORTHAND_STATUS_UNHAND"/>
<c:set value="<%=ScConstants.SC_PASSPORTHAND_STATUS_ABOLISH%>" var="SC_PASSPORTHAND_STATUS_ABOLISH"/>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId || not empty param.code || not empty param.sort}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scPassportHand?cls=1"><i
                            class="fa fa-plus-square"></i> 未交证件</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scPassportHand?cls=2"><i
                            class="fa fa-check-square-o"></i> 已交证件</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scPassportHand?cls=3"><i
                            class="fa fa-trash-o"></i> 撤销</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <c:if test="${cls==1}">
                <shiro:hasPermission name="scPassportHand:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/sc/scPassportHand_selectCadres">
                        <i class="fa fa-plus"></i> 添加新提任干部
                    </button>
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/sc/scPassportHand_dispatch">
                        <i class="fa fa-files-o"></i> 任免文件提取新提任干部
                    </button>
                   <%-- <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/sc/scPassportHand_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>--%>
                </shiro:hasPermission>
                <button class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/sc/scPassportHand_abolish"
                        data-grid-id="#jqGrid"><i class="fa fa-trash"></i>
                    撤销
                </button>
                </c:if>

            <c:if test="${cls==2}">
                <button class="jqItemBtn btn btn-warning btn-sm"
                        data-url="${ctx}/sc/scPassportHand_unhand"
                        data-title="转移至“未交证件”"
                        data-msg="确认转移至“未交证件”？"
                        data-callback="_reload"
                        data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                    转移至“未交证件”
                </button>
            </c:if>
            <c:if test="${cls==3}">
                <button class="jqItemBtn btn btn-warning btn-sm"
                        data-url="${ctx}/sc/scPassportHand_unabolish"
                        data-title="取消撤销"
                        data-msg="确认取消撤销？"
                        data-callback="_reload"
                        data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                    取消撤销
                </button>
            </c:if>
                <c:if test="${cls!=2}">
                    <shiro:hasPermission name="scPassportHand:del">
                        <button data-url="${ctx}/sc/scPassportHand_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-times"></i> 删除
                        </button>
                    </shiro:hasPermission>
                </c:if>
               <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/sc/scPassportHand_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>--%>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <div class="form-group">
                                <label>所属干部</label>
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scPassportHand?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scPassportHand?cls=${cls}"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }

    var normalPassortId = ${cm:getMetaTypeByCode("mt_passport_normal").id}
    var hkPassortId = ${cm:getMetaTypeByCode("mt_passport_hk").id}
    var twPassortId = ${cm:getMetaTypeByCode("mt_passport_tw").id}

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/sc/scPassportHand_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                <c:if test="${cls!=3}">
            {label: '交证件', name: '_hand', width: 200,formatter: function (cellvalue, options, rowObject) {

                if(rowObject.status=='${SC_PASSPORTHAND_STATUS_ABOLISH}') return '--'

                var passports = {};
                passports[normalPassortId] = {ret:0, msg:'未交证件'};
                passports[hkPassortId] = {ret:0, msg:'未交证件'};
                passports[twPassortId] = {ret:0, msg:'未交证件'};
                var handPassportCount = 0;
                $.each(rowObject.scPassports, function(i, p){
                    passports[p.classId] = {ret:p.isExist?1:2, msg:p.isExist? p.code:'不持有此证件'};
                    if(p.isExist) handPassportCount++;
                })
                var notHandAll = (passports[normalPassortId].ret==0 ||passports[hkPassortId].ret==0 || passports[twPassortId].ret==0)

                var str = ('<button data-url="${ctx}/sc/scPassport?handId={0}"'+
                        ' class="openView btn {2} btn-xs"><i class="fa fa-hand-paper-o"></i> {1}</button>')
                        .format(rowObject.id, notHandAll?'交证件':'已交', notHandAll?'btn-primary':'btn-success');

                <c:if test="${cls==1}">
                if(!notHandAll){
                    str += ('&nbsp;<button class="confirm btn btn-warning btn-xs"' +
                    ' data-title="证件入库" data-msg="确定入库？（所有的证件将添加或覆盖至集中管理库中，且本记录将转移至“已交证件”）" ' +
                    'data-url="${ctx}/sc/scPassportHand_import?handId={0}" data-callback="_reload">' +
                    '<i class="fa fa-plus-circle"></i> 证件入库({1}/{2})</button>').format(rowObject.id, rowObject.passportCount,
                            handPassportCount)
                }
                </c:if>
                <c:if test="${cls==2}">
                str += "&nbsp; 证件已入库"
                </c:if>
                return str;
            }, frozen:true},
            {label: '证件类型', name: '_passportType', width: 200, formatter: function (cellvalue, options, rowObject) {
                var passports = {};
                passports[normalPassortId] = {ret:0, msg:'未交证件'};
                passports[hkPassortId] = {ret:0, msg:'未交证件'};
                passports[twPassortId] = {ret:0, msg:'未交证件'};
                $.each(rowObject.scPassports, function(i, p){
                    passports[p.classId] = {ret:p.isExist?1:2, msg:p.isExist? p.code:'不持有此证件'};
                })
                //console.log(passports)
                //console.log(passports[hkPassortId])

                return ('<span class="text {0}" title="{6}">护照({1})</span> ' +
                '&nbsp;<span class="text {2}" title="{7}">港澳({3})</span> ' +
                '&nbsp;<span class="text {4}" title="{8}">台湾({5})</span>')
                        .format( passports[normalPassortId].ret>0?'text-success':'text-danger', passports[normalPassortId].ret==1?1:0,
                        passports[hkPassortId].ret>0?'text-success':'text-danger', passports[hkPassortId].ret==1?1:0,
                        passports[twPassortId].ret>0?'text-success':'text-danger', passports[twPassortId].ret==1?1:0,
                        passports[normalPassortId].msg, passports[hkPassortId].msg, passports[twPassortId].msg);
            }, frozen:true},
            </c:if>
            <c:if test="${cls==3}">
            {label: '撤销日期', name: 'abolishTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
                </c:if>
            {label: '新提任日期', name: 'appointDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
            {label: '工作证号', name: 'cadre.code', width: 110, frozen: true, frozen:true},
            { label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {

                return (rowObject.cadre!=undefined)? $.cadre(rowObject.cadre.id, cellvalue) : cellvalue;
            }, frozen:true},
            {label: '职务', name: 'post', width: 350, align:'left'},
            {label: '职务属性', name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {label: '所在单位', name: 'unit.name', width: 200, align:'left'},
            {label: '单位类型', name: 'unit.unitType.name', width: 150},
            {
                label: '发文号', name: 'dispatch.dispatchCode',
                width: 180, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.dispatch==undefined) return '--'
                return $.swfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName, cellvalue, cellvalue);
            }},
            {label: '短信通知', name: '_msg', width: 150, formatter: function (cellvalue, options, rowObject) {

                return ('<button data-url="${ctx}/sc/scPassportMsg?handId={0}"'+
                ' class="popupBtn btn btn-warning btn-xs"><i class="fa fa-send"></i> 通知</button>' +
                '&nbsp;<button data-url="${ctx}/sc/scPassportMsgList?handId={0}"'+
                ' data-width="650" class="popupBtn btn btn-info btn-xs"><i class="fa fa-search"></i> 记录({1})</button>')
                        .format(rowObject.id, rowObject.msgCount);
            }},
            {label: '备注', name: 'remark', width: 180, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>