<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsPrListOw"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId|| not empty param.sort}"/>
            <div class="tabbable">
                <c:if test="${cls==1}">
                    <jsp:include page="menu.jsp"/>
                </c:if>
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                                <a style="margin-left: 20px;" href="${ctx}/pcsPrListOw_export?file=3">
                                    <i class="fa fa-download"></i> 全校党代表汇总表</a>

                                <button style="margin-left: 20px;" data-url="${ctx}/pcsPrListOw_sync"
                                        data-title="同步名单"
                                        data-msg="是否将全校党代表名单将同步至“党代表提案管理”——“党代表名单”内？
                                    （注：重复此操作将覆盖之前的数据）"
                                        data-grid-id="#jqGrid" ${notPassPCSPrRecommendsCount>0?"disabled":""}
                                        class="confirm btn btn-warning">
                                    <i class="fa fa-level-down"></i>
                                    确定正式出席学校党代会代表
                                </button>
                            </c:if>
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
                                        <input type="hidden" name="cls" value="${param.cls}">

                                        <div class="form-group">
                                            <label>被推荐人</label>
                                            <select name="userId" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${partyId}&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                                    data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${param.cls}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiselect: false,
        url: '${ctx}/pcsPrListOw_candidate_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.PCS_PR_TYPE_MAP[cellvalue]
            }
            },
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '被推荐人姓名', name: 'realname', width: 120, frozen: true},
            <c:if test="${cls==4}">
            {
                label: '排序', width: 80, index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/pcsProposal_candidateChangeOrder"}, frozen: true
            },
            </c:if>
            {label: '所在单位', name: 'unitName', width: 160, align: 'left', frozen: true},

            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m'}},
            {label: '民族', name: 'nation', width: 60},
            {
                label: '学历学位', name: '_learn', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return $.jgrid.formatter.MetaType(rowObject.eduId);
                } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                    return $.trim(rowObject.education);
                }
                return "-"
            }
            }, /*
             {
             label: '参加工作时间',
             name: 'workTime',
             width: 120,
             sortable: true,
             formatter: $.jgrid.formatter.date,
             formatoptions: {newformat: 'Y.m'}
             },*/
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '职别', name: 'proPost', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return '干部';
                } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                    return (rowObject.isRetire) ? "离退休" : $.trim(cellvalue);
                }
                return $.trim(rowObject.eduLevel);
            }
            },
            {
                label: '职务', width: 200,
                name: 'post', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return $.trim(cellvalue);
                }
                return "-"
            }
            },
            <c:if test="${cls==1}">
            {label: '票数', name: 'vote3', width: 80},
            </c:if>
            {
                label: '手机号', name: 'mobile', width: 120
            },
            {
                label: '邮箱', name: 'email', width: 200, align: "left"
            },
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
</script>