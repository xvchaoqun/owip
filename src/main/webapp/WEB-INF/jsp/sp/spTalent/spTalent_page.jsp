<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.talentTitle ||not empty param.isCadre}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="sp:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-width="900"
                            data-url="${ctx}/sp/spTalent_au"><i class="fa fa-plus"></i>
                        添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/sp/spTalent_au"
                            data-width="900"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/sp/spTalent_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入</button>
                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/sp/spTalent_data"
                            data-rel="tooltip" data-placement="top"
                            title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i>
                        导出</button>
                    <button data-url="${ctx}/sp/spTalent_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                        删除</button>
                </shiro:hasPermission>
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
                                <label>姓名</label>
                                <select data-rel="select2-ajax" name="userId"
                                        data-ajax-url="${ctx}/sysUser_selects"
                                        data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>人才/荣誉称号</label>
                                <input class="form-control search-query" name="talentTitle" type="text" value="${param.talentTitle}"
                                       placeholder="请输入人才/荣誉称号">
                            </div>
                            <div class="form-group">
                                <label>是否领导干部</label>
                                <select name="isCadre" data-width="100" data-rel="select2"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isCadre]").val('${param.isCadre}');
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                        data-url="${ctx}/sp/spTalent"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i>
                                    查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sp/spTalent"
                                            data-target="#page-content"><i class="fa fa-reply"></i>
                                        重置</button>
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
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sp/spTalent_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '工作证号',name: 'user.code',width:150,frozen: true},
                { label: '姓名',name: 'user.realname',formatter: function (cellvalue, options, rowObject) {
        if (rowObject.isCadre) {
            return $.cadre(rowObject.cadre.id, cellvalue);
        }else {
            return cellvalue;
        }
    },frozen:true},
                { label: '性别',name: 'user.gender',frozen: true,width: 80,formatter: $.jgrid.formatter.GENDER},
            <shiro:hasPermission name="sp:edit">
                { label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/sp/spDp_changeOrder"}, frozen: true},
            </shiro:hasPermission>
                { label: '所在单位',name: 'unitId',width: 200,align: 'left',formatter: $.jgrid.formatter.unit},
                { label: '出生日期',name: 'user.birth',formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '国家/地区',name: 'user.country'},
                { label: '民族',name: 'user.nation'},
                { label: '到校日期',name: 'arriveDate',formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m.d'}},
                { label: '编制类别',name: 'authorizedType'},
                { label: '人员类别',name: 'staffType'},
                { label: '政治面貌',name: 'politicsStatus',width: 150,formatter: $.jgrid.formatter.MetaType},
                { label: '专业技术职务',name: 'proPost',width: 150},
                { label: '专技岗位等级',name: 'proPostLevel',width: 200},
                { label: '一级学科',name: 'firstSubject',width: 150},
                { label: '人才/荣誉称号',name: 'talentTitle',width: 300,align: 'lift'},
                { label: '授予日期',name: 'awardDate',formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m.d'}},
                { label: '是否领导干部',name: 'isCadre',formatter: $.jgrid.formatter.TRUEFALSE},
                { label: '所担任行政职务',name: 'adminPost',width: 200},
                { label: '联系方式',name: 'phone',width: 150},
                { label: '备注',name: 'remark',width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
</script>