<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/abroad/abroadAdditionalPost_au"
             data-url-page="${ctx}/abroad/abroadAdditionalPost"
             data-url-export="${ctx}/abroad/abroadAdditionalPost_data"
             data-url-co="${ctx}/abroad/abroadAdditionalPost_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId ||not empty param.postId||not empty param.unitId
            || (not empty param.sort&&param.sort!='sort_order')}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="abroadAdditionalPost:edit">
                        <a class="editBtn btn btn-info btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </a>
                        <button class="jqEditBtn btn btn-primary btn-sm">
                            <i class="fa fa-edit"></i> 修改
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="abroadAdditionalPost:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/abroad/abroadAdditionalPost_batchDel" data-title="删除"
                           data-msg="确定删除这{0}个兼审单位吗？"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                <div class="form-group">
                                    <label>所属干部</label>
                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=1" data-width="250"
                                            name="cadreId" data-placeholder="请选择">
                                        <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>兼审单位</label>
                                    <select required class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                                        <option></option>
                                        <c:forEach items="${unitMap}" var="unit">
                                            <option value="${unit.key}">${unit.value.name}</option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=unitId]").val('${param.unitId}');
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>兼审单位职务属性</label>
                                    <select required data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                                        <option></option>
                                        <jsp:include page="/metaTypes?__code=mc_post"/>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=postId]").val('${param.postId}');
                                    </script>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
        </div><div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/abroad/abroadAdditionalPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 110, frozen: true},
            {label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue);}, frozen: true},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            { label: '兼审单位', name: 'unitId', width: 350, formatter: $.jgrid.formatter.unit},
            {label: '兼审单位职务属性', name: 'postId', width: 150, formatter:$.jgrid.formatter.MetaType},
            { label: '备注', name: 'remark', width: 300 },
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $('[data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
</script>