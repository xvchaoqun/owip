<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW" value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW%>"/>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF"
       value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF%>"/>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF"
       value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF%>"/>
<c:set var="CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER"
       value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER%>"/>

<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <c:set var="countCacheKeys" value="<%=CacheConstants.CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_SELF%>"/>
                    <c:set var="cacheCount" value="${cm:getMenuCacheCount(countCacheKeys)}"></c:set>
                    <li class="<c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_SELF}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/abroad/passportDraw?type=${ABROAD_PASSPORT_DRAW_TYPE_SELF}"><i
                                class="fa fa-credit-card"></i> 因私出国（境）
                            <c:if test="${cacheCount>0}">
                                <span class="badge badge-warning">${cacheCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <c:set var="countCacheKeys"
                           value="${CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW},${CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF},${CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_PUB_SELF}"/>
                    <c:set var="cacheCount" value="${cm:getMenuCacheCount(countCacheKeys)}"></c:set>
                    <li class="<c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/abroad/passportDraw?type=${ABROAD_PASSPORT_DRAW_TYPE_TW}"><i
                                class="fa fa-credit-card"></i> 因公赴台、长期因公出国、因公出访持因私证件
                            <c:if test="${cacheCount>0}">
                                <span class="badge badge-warning">${cacheCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <c:set var="countCacheKeys" value="${CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER}"/>
                    <c:set var="cacheCount" value="${cm:getMenuCacheCount(countCacheKeys)}"></c:set>
                    <li class="<c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_OTHER}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/abroad/passportDraw?type=${ABROAD_PASSPORT_DRAW_TYPE_OTHER}"><i
                                class="fa fa-credit-card"></i> 处理其他事务
                            <c:if test="${cacheCount>0}">
                                <span class="badge badge-warning">${cacheCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <li class="<c:if test="${type==-1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passportDraw?type=-1"><i
                                class="fa fa-trash"></i> 已删除</a>
                    </li>
                    <shiro:hasPermission name="passportDraw:import">
                    <div class="buttons pull-left" style="left: 50px;">

                        <shiro:hasPermission name="passportDraw:edit">
                            <button class="openView btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/abroad/passportDraw_au"><i class="fa fa-plus"></i> 添加</button>
                        </shiro:hasPermission>

                        <button class="popupBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/abroad/passportDraw_import"
                        data-rel="tooltip" data-placement="top"
                        title="从Excel中批量导入"><i class="fa fa-upload"></i> 批量导入</button>
                    </div>
                    </shiro:hasPermission>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${type!=-1}">
                                <button data-url="${ctx}/abroad/passportDraw_add?type=${type}"
                                        class="popupBtn btn btn-success btn-sm">
                                    <i class="fa fa-plus"></i> 申请
                                </button>
                                <button data-url="${ctx}/abroad/passportDraw_au"
                                        data-open-by="page"
                                        class="jqOpenViewBtn btn btn-primary btn-sm">
                                    <i class="fa fa-edit"></i> 修改
                                </button>
                            </c:if>
                            <button data-url="${ctx}/abroad/passportDraw_view" data-open-by="page"
                                    class="jqOpenViewBtn btn btn-success btn-sm">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>
                            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_SELF}">
                                <button class="jqLinkBtn btn btn-warning btn-sm"
                                        data-target="_blank"
                                        data-url="/report/preview?url=${ctx}/report/abroad_draw_proof">
                                    <i class="fa fa-print"></i> 打印在职证明
                                </button>
                            </c:if>
                            <c:if test="${type>=0}">
                                <button id="resetReturnDateBtn" data-url="${ctx}/abroad/reset_passportDraw_returnDate"
                                        class="jqOpenViewBtn btn btn-info btn-sm">
                                    <i class="fa fa-edit"></i> 修改归还日期
                                </button>
                                <button id="resetDrawStatusBtn" data-url="${ctx}/abroad/reset_passportDraw_return"
                                        data-title="重置归还状态"
                                        data-msg="确认重置该证件为未归还状态？"
                                        class="jqItemBtn btn btn-primary btn-sm">
                                    <i class="fa fa-reply"></i> 重置归还状态
                                </button>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/sysApprovalLog"
                                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PASSPORTDRAW%>"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 操作记录
                                </button>
                                <shiro:hasPermission name="passportDraw:del">
                                    <button id="delBtn" class="jqBatchBtn btn btn-danger btn-sm"
                                            data-rel="tooltip" data-placement="top" title="证件未领取才可以删除"
                                            data-url="${ctx}/abroad/passportDraw_batchDel?isDeleted=1" data-title="删除申请使用证件申请"
                                            data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${type==-1}">
                                <shiro:hasPermission name="passportDraw:del">
                                    <a class="jqBatchBtn btn btn-success btn-sm"
                                       data-url="${ctx}/abroad/passportDraw_batchDel?isDeleted=0" data-title="找回已删除申请使用证件申请"
                                       data-msg="确定恢复这{0}条申请记录吗？"><i class="fa fa-reply"></i> 恢复申请</a>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="passportDraw:del">
                                    <button id="realDelBtn" class="jqBatchBtn btn btn-danger btn-sm"
                                            data-rel="tooltip" data-placement="top" title="未审批的记录可以删除"
                                            data-url="${ctx}/abroad/passportDraw_batchDel?isReal=1"
                                            data-title="删除申请使用证件申请"
                                            data-msg="确定删除这{0}条申请记录吗？<br/>（不可删除审批通过的记录）"><i class="fa fa-trash"></i> 完全删除
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${type!=-1}">
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/abroad/passportDraw_data?export=1&exportType=${type}"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                        class="fa fa-download"></i> 导出</a>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/abroad/passportDraw_data?export=1&exportType=-1"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                        class="fa fa-download"></i> 导出签字页</a>
                            </c:if>
                        </div>
                        <div class="myTableDiv"
                             data-url-page="${ctx}/abroad/passportDraw"
                             data-url-del="${ctx}/abroad/passportDraw_del"
                             data-url-bd="${ctx}/abroad/passportDraw_batchDel"
                             data-url-co="${ctx}/abroad/passportDraw_changeOrder"
                             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                            <c:set var="_query"
                                   value="${not empty param.cadreId ||not empty param._applyDate
                                   ||not empty param.status ||not empty param.drawStatus || not empty param.code}"/>
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
                                                <label>姓名</label>

                                                <div class="input-group">
                                                    <input type="hidden" name="type" value="${type}">
                                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                            name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>申请日期</label>

                                                <div class="input-group tooltip-success" data-rel="tooltip"
                                                     title="申请日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                                    <input placeholder="请选择申请日期范围" data-rel="date-range-picker"
                                                           class="form-control date-range-picker" type="text"
                                                           name="_applyDate" value="${param._applyDate}"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>审批状态</label>
                                                <select name="status" data-rel="select2" data-width="140" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${ABROAD_PASSPORT_DRAW_STATUS_MAP}" var="entity">
                                                        <option value="${entity.key}">${entity.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=status]").val('${param.status}');
                                                </script>
                                            </div>
                                            <div class="form-group">
                                                <label>领取状态</label>
                                                <select name="drawStatus" data-rel="select2" data-width="100" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP}"
                                                               var="entity">
                                                        <option value="${entity.key}">${entity.value}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#searchForm select[name=drawStatus]").val('${param.drawStatus}');
                                                </script>
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
        <div id="body-content-view">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/abroad/passportDraw_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '编号',
                align: 'center',
                name: 'id',
                formatter: function (cellvalue, options, rowObject) {
                    return 'D{0}'.format(cellvalue);
                },
                frozen: true
            },
            {
                label: '申请日期',
                align: 'center',
                name: 'applyDate',
                frozen: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW}">
            {
                label: '申请类型', name: 'type',width: 130, formatter: function (cellvalue, options, rowObject) {
                    return _cMap.ABROAD_PASSPORT_DRAW_TYPE_MAP[cellvalue];
                }, frozen: true
            },
            </c:if>
            {label: '工作证号', name: 'user.code',width: 120, frozen: true},
            {
                label: '姓名',
                align: 'center',
                name: 'user.realname',
                width: 75,
                formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadreId, cellvalue);
                },
                frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', width: 250, align: 'left'},
            {label: '申请领取证件名称', name: 'passportClass.name', width: 180},
            {
                label: '证号号码',
                align: 'center',
                name: 'passport.code',
                title: false,
                cellattr: function (rowId, val, rawObject, cm, rdata) {

                    return 'data-tooltip="tooltip" data-container="#body-content" data-html="true" data-original-title="所在保险柜：' + rawObject.passport.safeBox.code + '<br> (过期时间：' + $.substr(rawObject.passport.expiryDate, 0, 10) + ')"';
                }
            },
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_SELF}">
            {
                label: '因私出国（境）行程',
                align: 'center',
                name: 'applyId',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--'
                    return '<a class="openView" href="javascript:;" ' +
                        'data-url="${ctx}/abroad/applySelf_view?id={0}">S{1}</a>'.format(cellvalue, cellvalue);
                }, title: false, cellattr: function (rowId, val, rawObject, cm, rdata) {

                    if(rawObject.applySelf==undefined) return;
                    return 'data-tooltip="tooltip" data-container="#body-content" data-html="true" data-original-title="出行时间：'
                        + $.date(rawObject.applySelf.startDate, 'yyyy.MM.dd') + '<br> 前往国家或地区：' + rawObject.applySelf.toCountry
                        + '<br> 出国境事由：' + $.replace(rawObject.applySelf.reason, /\+\+\+/g, ',') + '"';
                }
            },
            </c:if>
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW || type==ABROAD_PASSPORT_DRAW_TYPE_OTHER}">
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"出行时间":"使用开始日期"}',
                name: 'startDate', width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"回国时间":"使用结束日期"}',
                name: 'endDate', width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"出行天数":"使用天数"}',
                name: 'day',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.endDate==undefined) return '--'
                    return $.dayDiff(rowObject.startDate, rowObject.endDate);
                }
            },
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"因公事由":"事由"}',
                name: 'reason',
                width: 200,
                formatter: function (cellvalue, options, rowObject) {
                    return $.replace(cellvalue, /\+\+\+/g, ',');
                }, align: 'left'
            },
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW}">
            {label: '费用来源', name: 'costSource', align: 'left', width: 150},
            </c:if>
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"批件":"说明材料"}',
                name: 'files',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {

                    var filesArray = [];

                    rowObject.files.forEach(function (file, i) {
                        filesArray.push('<a class="various" rel="group{2}" title="{3}" data-title-id="{4}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"批件":"材料"}{1}</a>'.format(file.filePath, parseInt(i) + 1, rowObject.id, file.fileName, file.id));
                    })
                    return filesArray.join("，");
                }
            },
            </c:if>
            <c:if test="${type!=ABROAD_PASSPORT_DRAW_TYPE_OTHER}">
            {
                label: '是否签注',
                align: 'center',
                name: 'needSign',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.passportClass.code == 'mt_passport_normal') {
                        return '--';
                    }
                    return (cellvalue) ? "是" : "否";
                }
            },
            {
                label: '签注申请表', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.passportClass.code == 'mt_passport_normal' || !rowObject.needSign) {
                        return '--';
                    }
                    return '<a href="${ctx}/report/passportSign?id={0}" target="_blank">签注申请表 </a>'.format(rowObject.signId);
                }
            },
            {
                label: '打印签注申请表', width: 150, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.passportClass.code == 'mt_passport_normal' || !rowObject.needSign) {
                        return '--';
                    }
                    return '<button onclick="$.print(\'${ctx}/report/passportSign?id={0}&format=pdf\')" class="btn btn-info btn-xs"><i class="fa fa-print"></i> 打印签注申请表</button>'
                        .format(rowObject.signId);
                }
            },
            </c:if>
            {
                label: '组织部审批', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.status == '${ABROAD_PASSPORT_DRAW_STATUS_INIT}')
                        return '<button data-url="${ctx}/abroad/passportDraw_check?id={0}"  class="openView btn btn-success btn-xs">'
                                .format(rowObject.id)
                            + '<i class="fa fa-check"></i> 组织部审批</button>';
                    else
                        return rowObject.statusName;
                }
            },
            {
                label: '发送通知', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.status == '${ABROAD_PASSPORT_DRAW_STATUS_INIT}') {
                        return '--';
                    }
                    if (rowObject.drawStatus == '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW}') {
                        return '<button data-url="${ctx}/abroad/shortMsg_view?id={0}&type=passportDrawApply" class="popupBtn btn btn-warning btn-xs">'
                                .format(rowObject.id)
                            + '<i class="fa fa-info"></i> 发送通知</button>';
                    }
                    return '--'
                }
            },
            {
                label: '领取证件', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.status != '${ABROAD_PASSPORT_DRAW_STATUS_PASS}') {
                        return '--';
                    }
                    if (rowObject.drawStatus == '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW}') {
                        return '<button data-url="${ctx}/abroad/passportDraw_draw?id={0}" class="openView btn btn-info btn-xs">'
                                .format(rowObject.id)
                            + '<i class="fa fa-hand-lizard-o"></i> 领取证件</button>'
                    }
                    return _cMap.ABROAD_PASSPORT_DRAW_DRAW_STATUS_MAP[rowObject.drawStatus];
                }
            },
            {
                label: '应交组织部日期',
                align: 'center',
                name: 'returnDate',
                width: 130,
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (rowObject.drawStatus == '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW}'
                        && (rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_KEEP}' ||
                            (rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_CANCEL}' && !rowObject.passport.cancelConfirm))) {
                        var _date = rowObject.returnDate;
                        if (_date <= $.date(new Date(), 'yyyy-MM-dd'))
                            return "class='danger'";
                    }
                },
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '催交证件',
                align: 'center',
                width: 110,
                formatter: function (cellvalue, options, rowObject) {

                    if (rowObject.usePassport == '${ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN}') {
                        return '--'
                    }

                    if (rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_CANCEL}' && rowObject.passport.cancelConfirm)
                        return '--';

                    if (rowObject.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN}' &&
                        rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_LOST}')
                        return '证件丢失';

                    if (rowObject.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW}' || rowObject.returnDateNotNow) {
                        return '--';
                    }
                    return '<button data-url="${ctx}/abroad/shortMsg_view?id={0}&type=passportDrawReturn" class="popupBtn btn btn-danger btn-xs">'
                            .format(rowObject.id)
                        + '<i class="fa fa-hand-paper-o"></i> 催交证件</button>';
                }
            },
            {
                label: '归还证件',
                align: 'center',

                formatter: function (cellvalue, options, rowObject) {

                    if (rowObject.drawStatus == '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN}') {

                        if (rowObject.useRecord == undefined) return '--';

                        return $.imgPreview(rowObject.useRecord, "使用记录.jpg", "使用记录");
                    }

                    if ((rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_CANCEL}' && rowObject.passport.cancelConfirm) ||
                        rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_LOST}' ||
                        rowObject.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW}') {
                        return '--';
                    }

                    if (rowObject.usePassport == '${ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN}') {
                        return '拒不交回'
                    }

                    return '<button data-url="${ctx}/abroad/passportDraw_return?id={0}" class="openView btn btn-default btn-xs">'
                            .format(rowObject.id)
                        + '<i class="fa fa-reply"></i> 归还证件</button>'
                }, title: false, cellattr: function (rowId, val, rawObject, cm, rdata) {
                    if (rawObject.usePassport == '${ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN}') {
                        return 'data-tooltip="tooltip" data-container="#body-content" data-html="true" data-original-title="' + rawObject.returnRemark + '"';
                    }
                }
            },
            {
                label: '实交组织部日期',
                align: 'center',
                name: 'realReturnDate',
                width: 130,
                formatter: function (cellvalue, options, rowObject) {

                    if (rowObject.usePassport == '${ABROAD_PASSPORT_DRAW_USEPASSPORT_REFUSE_RETURN}') {

                        return '<button data-url="${ctx}/abroad/passportDraw_return?id={0}" class="openView btn btn-default btn-xs">'
                                .format(rowObject.id)
                            + '<i class="fa fa-reply"></i> 重新交回</button>'
                    }

                    if (rowObject.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN}' &&
                        rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_LOST}') {

                        return '证件丢失'
                    }

                    if (rowObject.passport.type == '${ABROAD_PASSPORT_TYPE_CANCEL}') {

                        var ret = _cMap.ABROAD_PASSPORT_CANCEL_TYPE_MAP[rowObject.passport.cancelType];
                        if (rowObject.passport.cancelType == '${ABROAD_PASSPORT_CANCEL_TYPE_OTHER}'
                            && $.trim(rowObject.passport.cancelTypeOther) != '') {
                            ret = ret + ":" + $.trim(rowObject.passport.cancelTypeOther);
                        }
                        return ret;
                    }

                    if (cellvalue == undefined) return '--'

                    return $.date(cellvalue, "yyyy-MM-dd");
                }
            },
            {
                label: '附件', formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.attachmentFilename)
                    return $.pdfPreview(rowObject.attachment, rowObject.attachmentFilename, "查看");
                }
            },
            {label: '备注', name:'remark', width:350, align:'left'},
            {hidden: true, name: 'drawStatus'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id);

            var rowData = $(this).getRowData(id);
            $("#resetReturnDateBtn").prop("disabled", (rowData.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW}'));
            $("#resetDrawStatusBtn").prop("disabled", (rowData.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN}'));
            //$("#delBtn").prop("disabled", (rowData.drawStatus != '${ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW}'));
        }
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-tooltip="tooltip"]').tooltip({html: true});
    });


    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.fancybox();

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>