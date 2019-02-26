<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cet/cetExpert"
                 data-url-export="${ctx}/cet/cetExpert_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type ||not empty param.userId || not empty param.realname
            || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetExpert:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/cet/cetExpert_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetExpert_au"
                       data-grid-id="#jqGrid" ><i class="fa fa-edit"></i> 修改</a>

                    <a class="popupBtn btn btn-info btn-sm tooltip-info"
                               data-url="${ctx}/cet/cetExpert_import"
                               data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetExpert:del">
                    <button data-url="${ctx}/cet/cetExpert_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>

                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
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
                            <label>专家类型</label>
                            <select data-rel="select2" name="type" data-width="120" data-placeholder="请选择">
                                <option></option>
                                <c:forEach items="<%=CetConstants.CET_EXPERT_TYPE_MAP%>" var="entity">
                                    <option value="${entity.key}">${entity.value}</option>
                                </c:forEach>
                            </select>
                            <script>
                                $("#searchForm select[name=type]").val("${param.type}")
                            </script>
                        </div>
                        <div class="form-group inDiv">
                            <label>选择专家</label>
                            <c:set var="sysUser" value="${cm:getUserById(cm:toInt(param.userId))}"/>
                            <select name="userId" data-rel="select2-ajax" data-width="272"
                                    data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                    data-placeholder="请输入账号或姓名或教工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="form-group outDiv">
                            <label>专家姓名</label>
                            <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
                                   placeholder="请输入专家姓名">
                        </div>
                        <div class="form-group outDiv">
                            <label>专家编号</label>
                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入专家编号">
                        </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
    $("#searchForm select[name=type]").on("change", function () {
        if ($(this).val() == '<%=CetConstants.CET_EXPERT_TYPE_IN%>') {
            $(".inDiv").show();
            $(".outDiv").hide();
        } else if ($(this).val() == '<%=CetConstants.CET_EXPERT_TYPE_OUT%>')  {
            $(".inDiv").hide();
            $(".outDiv").show();
        } else{
            $(".inDiv").hide();
            $(".outDiv").hide();
        }
    }).change();

    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetExpert_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '专家类别',name: 'type', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '--';
                return '<div class="bolder text {0}">{1}</div>'.format(
                    rowObject.type=='<%=CetConstants.CET_EXPERT_TYPE_IN%>'?'text-success':'text-primary',
                    _cMap.CET_EXPERT_TYPE_MAP[cellvalue]);
            }, frozen:true},
            { label: '工号/编号',name: 'code', width:110, formatter:function(cellvalue, options, rowObject){

                //console.log("rowObject.type="+rowObject.type)
                if(rowObject.type=='<%=CetConstants.CET_EXPERT_TYPE_IN%>'
                    && rowObject.user!=undefined){
                    return rowObject.user.code;
                }
                if(cellvalue==undefined) return '--';
                return cellvalue;
            }, frozen:true},
            { label: '姓名',name: 'realname', frozen:true},
            {
                label: '排序', align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/cet/cetExpert_changeOrder"}, frozen:true
            },
            { label: '所在单位',name: 'unit', width:300, align:'left'},
            { label: '职务和职称',name: 'post', width:150, align:'left'},
            { label: '联系方式',name: 'contact', width:150},
            { label: '主讲课程',name: 'courseNum'},
            { label: '选课人次',name: 'traineeCount'},
            { label: '学员评价',name: '_eva'},
            { label: '备注',name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($("#searchForm select[name=userId]"))
    $('[data-rel="tooltip"]').tooltip();
</script>