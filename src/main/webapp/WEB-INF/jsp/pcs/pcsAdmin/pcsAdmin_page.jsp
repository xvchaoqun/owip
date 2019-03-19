<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsAdmin"
             data-url-export="${ctx}/pcsAdmin_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.partyId ||not empty param.userId || not empty param.type || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pcsAdmin:edit">
                    <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/pcsAdmin_au"><i class="fa fa-plus"></i> 添加管理员</a>
                    <a class="confirm btn btn-success btn-sm"
                       data-url="${ctx}/pcsAdmin_sync"
                       data-title="同步党代会管理员"
                       data-msg="确定同步每个${_p_partyName}现任的书记、副书记为党代会的管理员？（将删除现有的书记和副书记管理员）"
                       data-callback="_reload"><i class="fa fa-random"></i>
                        同步党代会管理员</a>

                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pcsAdmin_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改管理员信息</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="pcsAdmin:del">
                    <button data-url="${ctx}/pcsAdmin_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}位管理员？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
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
                                <label>所属${_p_partyName}</label>
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>用户</label>
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?noAuth=1&status=${MEMBER_STATUS_NORMAL}"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>类型</label>
                                <select name="type" data-rel="select2" data-placeholder="请选择"> 
                                    <option></option>
                                      <c:forEach items="${PCS_ADMIN_TYPE_MAP}" var="type"> 
                                        <option value="${type.key}">${type.value}</option>
                                          </c:forEach>  </select> 
                                <script>         $("#searchForm select[name=type]").val('${param.type}');     </script>
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
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/pcsAdmin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '所属${_p_partyName}',
                name: 'partyId',
                align: 'left',
                width: 400,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(cellvalue);
                }
            },
            {
                label: '姓名', name: 'user.realname', width: 90, formatter: function (cellvalue, options, rowObject) {
                return $.user(rowObject.userId, cellvalue);
            }, frozen: true
            },
            {
                label: '手机号码', name: 'user.mobile',width:120
            },
            {label: '工作证号', name: 'user.code', width: 120, frozen: true},
            {label: '所在单位', name: 'unit', width: 300, align:'left', frozen: true},
            {
                label: '类型', name: 'type', formatter: function (cellvalue, options, rowObject) {
                if ($.trim(cellvalue) == '') return '-'
                return _cMap.PCS_ADMIN_TYPE_MAP[cellvalue];
            }
            },
            { label: '备注',name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();

    $.register.del_select($('#searchForm select[name=partyId]'));
    $.register.user_select($('#searchForm select[name=userId]'));
    $('[data-rel="tooltip"]').tooltip();
</script>