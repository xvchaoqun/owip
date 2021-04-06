<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/partyMemberGroup_au?type=${type}"
                 data-url-page="${ctx}/partyMemberGroup?type=${type}"
                 data-url-export="${ctx}/partyMemberGroup_data"
                 data-url-co="${ctx}/partyMemberGroup_changeOrder"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.classId||not empty param.partyId
                ||not empty param.name || not empty param._appointTime || not empty param._tranTime}"/>

                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="partyMemberGroup:edit">
                                    <c:if test="${status>=0}">
                                    <button data-url="${ctx}/partyMemberGroup_au?type=${type}"
                                            class="popupBtn btn btn-info btn-sm">
                                        <i class="fa fa-plus"></i> 添加
                                    </button>
                                    </c:if>
                                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                                        <i class="fa fa-edit"></i> 修改信息</a>
                                    <c:if test="${type==0}">
                                        <c:if test="${status>=0}">
                                        <shiro:hasPermission name="partyMemberGroup:realDel">
                                            <div class="btn-group">
                                                <button data-toggle="dropdown"
                                                        data-rel="tooltip" data-placement="top" data-html="true"
                                                        title="<div style='width:180px'>批量导入操作</div>"
                                                        class="btn btn-info btn-sm dropdown-toggle tooltip-success">
                                                    <i class="fa fa-hand-o-right"></i> 批量导入 <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                                        <li>
                                                            <a href="javascript:;" class="popupBtn"
                                                               data-url="${ctx}/partyMemberGroup_import">
                                                                <i class="fa fa-upload"></i> 批量导入班子</a>
                                                        </li>
                                                        <li role="separator" class="divider"></li>
                                                        <li>
                                                            <a href="javascript:;" class="popupBtn"
                                                               data-url="${ctx}/partyMember_import">
                                                                <i class="fa fa-upload"></i> 批量导入班子成员</a>
                                                        </li>
                                                </ul>
                                            </div>
                                        </shiro:hasPermission>
                                    </c:if>
                                    </c:if>
                                </shiro:hasPermission>
                                <c:if test="${type==0}">
                                    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i
                                            class="fa fa-download"></i> 导出</a>
                                    <c:if test="${status==1}">
                                        <shiro:hasPermission name="sysMsg:list">
                                            <a class="jqBatchBtn btn btn-warning btn-sm tooltip-success"
                                               data-callback="_isSendSuccess"
                                               data-url="/sys/sysMsg_partyRemind" date-title="提醒班子换届" data-msg="确定发送提醒这{0}个领导班子换届吗？"><i
                                                    class="fa fa fa-info-circle"></i> 换届提醒</a>
                                        </shiro:hasPermission>
                                    </c:if>
                                    <c:if test="${status>=0}">
                                        <shiro:hasPermission name="partyMemberGroup:del">
                                            <a class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                                               data-url="${ctx}/partyMemberGroup_batchDel" data-title="撤销领导班子"
                                               data-msg="确定撤销这{0}个领导班子吗？"><i class="fa fa-history"></i> 撤销</a>
                                            【注：撤销操作将同时删除相关管理员，请谨慎操作！】
                                        </shiro:hasPermission>
                                    </c:if>
                                    <c:if test="${status==-1}">
                                        <shiro:hasPermission name="partyMemberGroup:realDel">
                                            <a class="jqBatchBtn btn btn-danger btn-sm"
                                               data-url="${ctx}/partyMemberGroup_realDel"
                                               data-title="删除领导班子"
                                               data-msg="确定完全删除这{0}个领导班子吗？（不可恢复，请谨慎操作！）"><i class="fa fa-times"></i> 完全删除</a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="partyMemberGroup:del">
                                            <a class="jqBatchBtn btn btn-success btn-sm"
                                               data-url="${ctx}/partyMemberGroup_batchDel"
                                               data-querystr="isDeleted=0"
                                               data-title="恢复已删除领导班子"
                                               data-msg="确定恢复这{0}个领导班子吗？"><i class="fa fa-reply"></i> 恢复</a>
                                            【注：恢复操作之后需要重新设置相关管理员！】
                                        </shiro:hasPermission>
                                    </c:if>
                                </c:if>
                                <c:if test="${type==1}">
                                    <shiro:hasPermission name="partyMemberGroup:del">
                                        <button data-url="${ctx}/pgbMemberGroup_batchDel"
                                                data-title="删除"
                                                data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                                class="jqBatchBtn btn btn-danger btn-sm">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <div class="pull-right hidden-sm hidden-xs">
                                    <select id="sortBy" data-placeholder="请选择排序方式" data-width="250">
                                        <option></option>
                                        <option value="appointTime_asc">按任命时间排序(升序)</option>
                                        <option value="appointTime_desc">按任命时间排序(降序)</option>
                                        <option value="tranTime_asc">按应换届时间排序(升序)</option>
                                        <option value="tranTime_desc">按应换届时间排序(降序)</option>
                                    </select>
                                    <script>
                                        $("#sortBy").val('${param.sortBy}');
                                        $("#searchForm input[name=sortBy]").val('${param.sortBy}');
                                        $("#sortBy").select2({
                                            theme: "default"
                                        }).change(function () {
                                            $("#searchForm input[name=sortBy]").val($(this).val());
                                            $("#searchForm .jqSearchBtn").click();
                                            if($(this).val()==''){
                                                throw new Error();
                                            }
                                        })
                                    </script>
                                </div>
                            </div>
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">
                                            <input type="hidden" name="sortBy">
                                            <input type="hidden" name="status" value="${status}">
                                            <div class="form-group">
                                                <label>名称</label>
                                                <input class="form-control search-query" name="name" type="text"
                                                       value="${param.name}"
                                                       placeholder="请输入名称">
                                            </div>
                                            <div class="form-group">
                                                <label>${_p_partyName}类别</label>
                                                <select name="classId" data-rel="select2" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_party_class"/>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=classId]").val('${param.classId}');
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>所属${type==1?"内设党总支":_p_partyName}</label>
                                                <select name="partyId" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects"
                                                        data-placeholder="请选择所属${type==1?"内设党总支":_p_partyName}">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                                <script>
                                                    $.register.del_select($("#searchForm select[name=partyId]"), 350)
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>任命时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="任命时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                                    <input placeholder="请选择任命时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_appointTime" value="${param._appointTime}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>应换届时间</label>
                                                <div class="input-group tooltip-success" data-rel="tooltip" title="应换届时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                                    <input placeholder="请选择应换届时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                           type="text" name="_tranTime" value="${param._tranTime}"/>
                                                </div>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-querystr="status=${status}">
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
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
        <div id="body-content-view2"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    function _isSendSuccess($btn, ret){
        if (ret.success){
            SysMsg.success('换届提醒发送成功。', '成功');
        }else {
            SysMsg.info('换届提醒发送失败，请重新发送。', '提醒');
        }
    }

    $("#jqGrid").jqGrid({
        url: '${ctx}/partyMemberGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '名称',
                name: 'name',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    //var str = '<span class="label label-sm label-primary" style="display: inline!important;"> 现任班子</span>&nbsp;';
                    var str = '<i class="fa fa-flag red" title="现任领导班子"></i> ';
                    return (!rowObject.isDeleted) ? str + cellvalue : cellvalue;
                },
                frozen: true
            },
            <c:if test="${type==0}">
                {
                    label: '班子成员管理', name: 'memberCount', width: 110, formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="openView btn btn-warning btn-xs" ' +
                            'data-url="${ctx}/partyMember?groupId={0}">'
                            + '<i class="fa fa-search"></i> 详情({1})</button>')
                            .format(rowObject.id, rowObject.memberCount);
                    }
                },
                {
                    label: '导出班子成员', name: 'courseNum', formatter: function (cellvalue, options, rowObject) {
                        if (!rowObject.isDeleted)
                            return ('<button class="downloadBtn btn btn-primary btn-xs" ' +
                                'data-url="${ctx}/partyMember?export=1&groupId={0}"><i class="fa fa-file-excel-o"></i> 导出</a>')
                                .format(rowObject.id);
                        return '--'
                    }
                },
            </c:if>
            {
                label: '所属${type==1?"内设党总支":_p_partyName}',
                name: 'partyId',
                align: 'left',
                width: 380,
                formatter: function (cellvalue, options, rowObject) {

                    return $.party(rowObject.partyId);
                }
            },
            {label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '应换届时间', name: 'tranTime', width: 130,
                formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'},
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (!rowObject.isDeleted){
                        if($.yearOffNow(rowObject.tranTime) > 0) {
                            return "class='dark-danger'"; // 超过1年，深红
                        }else if($.dayOffNow(rowObject.tranTime) > 0){
                            return "class='danger'";
                        }
                    }
                }
            },
            <c:if test="${status==-1}">
            {
                label: '实际换届时间',
                name: 'actualTranTime',
                width: 130,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            }
            </c:if>
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>