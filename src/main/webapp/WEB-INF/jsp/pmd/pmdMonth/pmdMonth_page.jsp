<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pmdMonth"
             data-url-export="${ctx}/pmdMonth_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.payMonth || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="multi-row-head-table tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="pmdMonth:edit">
                                <button id="addBtn" class="popupBtn btn btn-success btn-sm"
                                    ${(not empty _pmdMonth)?'disabled':''}
                                        data-width="400"
                                        data-url="${ctx}/pmd/pmdMonth_au"><i
                                        class="fa fa-plus"></i> 新建缴费月份
                                </button>
                                <button id="updateBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/pmd/pmdMonth_au"
                                   data-grid-id="#jqGrid"
                                        data-width="400"><i class="fa fa-edit"></i>
                                    修改缴费月份</button>
                                <button id="selectPartiesBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/pmd/pmdMonth_selectParties"
                                        data-id-name="monthId"
                                        data-grid-id="#jqGrid"
                                        ><i class="fa fa-edit"></i> 编辑缴费党委
                                </button>

                                <button class="popupBtn btn btn-warning btn-sm"
                                    ${(empty _pmdMonth)?'disabled':''}
                                        data-url="${ctx}/pmd/pmdSendMsg_notifyPartyAdmins"
                                        ><i class="fa fa-send"></i> 通知党委管理员
                                </button>
                                <c:if test="${not empty _pmdMonth}">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/pmd/pmdMonth_addParty">
                                        <i class="fa fa-plus"></i> 新增缴费党委
                                    </button>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasRole name="${ROLE_ADMIN}">
                                <button class="jqItemBtn btn btn-danger btn-sm"
                                        data-title="更新结算"
                                        data-msg="确定更新结算？（仅更新汇总数据）"
                                   data-url="${ctx}/pmd/pmdMonth_end?update=1"
                                   data-id-name="monthId"
                                   data-grid-id="#jqGrid"><i class="fa fa-refresh"></i>
                                    更新结算</button>
                            </shiro:hasRole>
                        </div>
                        <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                            <label>缴纳月份</label>
                                            <input class="form-control search-query" name="payMonth" type="text"
                                                   value="${param.payMonth}"
                                                   placeholder="请输入缴纳月份">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>--%>
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

    function _isEnd(rowObject) {
        return rowObject.status == '${PMD_MONTH_STATUS_END}'
    }
    function _isInit(rowObject) {
        return rowObject.status == '${PMD_MONTH_STATUS_INIT}'
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdMonth_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '月份', name: 'payMonth', formatter: 'date', formatoptions: {newformat: 'Y年m月'}, frozen: true},
            {
                label: '启动', name: '_start', width:150, formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject) == false) {
                    return rowObject.startTime.substr(0, 16);
                }
                return ('<button class="popupBtn btn btn-success btn-xs"' +
                'data-url="${ctx}/pmd/pmdMonth_start?monthId={0}"><i class="fa fa-cogs"></i> 启动</button>')
                        .format(rowObject.id);
            }, frozen: true
            },
            {
                label: '结算', name: '_end', width:80, formatter: function (cellvalue, options, rowObject) {

                if (_isEnd(rowObject))  return '<span class="text-success">已结算</span>';
                if (!rowObject.canEnd) return "-";
                return ('<button class="popupBtn btn btn-success btn-xs" ' +
                'data-url="${ctx}/pmd/pmdMonth_end?monthId={0}"><i class="fa fa-rmb"></i> 结算</button>')
                        .format(rowObject.id);
            }, frozen: true
            },
            {label: '结算时间', name: 'endTime', width:150, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}, frozen: true},
            {
                label: '对账和报表', name: '_report', formatter: function (cellvalue, options, rowObject) {

                if (_isEnd(rowObject) == false)  return "-";
                return ('<button class="popupBtn btn btn-success btn-xs" data-width="700" ' +
                'data-url="${ctx}/pmd/pmdOw_export_page?monthId={0}"><i class="fa fa-download"></i> 报表</button>')
                        .format(rowObject.id);
            }, frozen: true
            },
            {
                label: '各党委<br/>详情', name: '_partyDetail', width:80, formatter: function (cellvalue, options, rowObject) {

                if (_isInit(rowObject))  return "-";
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/pmd/pmdParty?cls=2&monthId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }
            },
            {label: '党委数', name: 'partyCount', width:70},
            {
                label: '已报送<br/>党委数', name: 'hasReportCount', width:80, formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.hasReportCount;
            }
            },
            /*{
                label: '未报送<br/>党委数', name: '_notReportCount', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return rowObject.partyCount - (_isEnd(rowObject) ? rowObject.hasReportCount : rowObject.r.hasReportCount);
            }
            },*/
            {
                label: '已报送党委<br/>党员总数', name: 'memberCount',formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.memberCount;
            }
            },
            { label: '线上缴纳<br/>党费总数',name: '_onlinePay', width:90, formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject)?(rowObject.onlineRealPay + rowObject.onlineRealDelayPay).toFixed(2)
                        :(rowObject.r.onlineRealPay + rowObject.r.onlineRealDelayPay).toFixed(2);
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                return "class='success bolder'";
            }},
            { label: '现金缴纳<br/>党费总数',name: '_cashPay', width:80, formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject)?(rowObject.cashRealPay + rowObject.cashRealDelayPay).toFixed(2)
                        :(rowObject.r.cashRealPay + rowObject.r.cashRealDelayPay).toFixed(2);
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                return "class='success bolder'";
            }},
            {
                label: '本月应缴费<br/>党费数', name: 'duePay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.duePay;
            }
            },
            {
                label: '本月按时缴纳<br/>党费党员数',
                name: 'finishMemberCount',
                formatter: function (cellvalue, options, rowObject) {
                    if (_isInit(rowObject))  return "-";
                    return _isEnd(rowObject) ? cellvalue : rowObject.r.finishMemberCount;
                }
            },
            /*{
                label: '本月实缴<br/>党费数', name: 'realPay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.realPay;
            }
            },*/
            {
                label: '本月线上缴纳<br/>党费数', name: 'onlineRealPay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.onlineRealPay;
            }
            },
            {
                label: '本月现金缴纳<br/>党费数', name: 'cashRealPay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.cashRealPay;
            }
            },
            {
                label: '本月延迟缴纳<br/>党费党员数', name: 'delayMemberCount', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.delayMemberCount;
            }
            },
            {
                label: '本月延迟缴纳<br/>党费数', name: 'delayPay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.delayPay;
            }
            },
            /*{label: '往月延迟<br/>缴费党员数', name: 'historyDelayMemberCount'},*/
            {label: '往月应补缴<br/>党费数', name: 'historyDelayPay'},
            /*{ label: '补缴往月<br/>党费党员数',name: 'realDelayMemberCount', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.realDelayMemberCount;
            }},
            {
                label: '实补缴<br/>往月党费数', name: 'realDelayPay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.realDelayPay;
            }
            },*/
            {
                label: '往月线上<br/>补缴党费数',
                name: 'onlineRealDelayPay',
                formatter: function (cellvalue, options, rowObject) {
                    if (_isInit(rowObject))  return "-";
                    return _isEnd(rowObject) ? cellvalue : rowObject.r.onlineRealDelayPay;
                }
            },
            {
                label: '往月现金<br/>补缴党费数', name: 'cashRealDelayPay', formatter: function (cellvalue, options, rowObject) {
                if (_isInit(rowObject))  return "-";
                return _isEnd(rowObject) ? cellvalue : rowObject.r.cashRealDelayPay;
            }
            }, {hidden: true, name: 'status'}, {name: 'id', hidden: true, key: true}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length > 1) {
            $("#selectPartiesBtn,#updateBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            <c:if test="${not empty _pmdMonth}">
            $("#selectPartiesBtn").prop("disabled", (rowData.id != '${_pmdMonth.id}'
            || rowData.status != '${PMD_MONTH_STATUS_INIT}'));
            </c:if>
            <c:if test="${empty _pmdMonth}">
            $("#selectPartiesBtn").prop("disabled", ( rowData.status != '${PMD_MONTH_STATUS_INIT}'));
            </c:if>

            $("#updateBtn").prop("disabled", (rowData.status != '${PMD_MONTH_STATUS_INIT}'));
        }
    }
</script>