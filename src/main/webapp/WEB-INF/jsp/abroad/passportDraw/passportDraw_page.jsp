<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <c:set var="countCacheKeys" value="${CACHEKEY_PASSPORT_DRAW_TYPE_SELF}"/>
                    <c:set var="cacheCount" value="${cm:getMenuCacheCount(countCacheKeys)}"></c:set>
                    <li class="<c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/passportDraw?type=${PASSPORT_DRAW_TYPE_SELF}"><i class="fa fa-credit-card"></i> 因私出国（境）
                            <c:if test="${cacheCount>0}">
                                <span class="badge badge-warning">${cacheCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <c:set var="countCacheKeys" value="${CACHEKEY_PASSPORT_DRAW_TYPE_TW},${CACHEKEY_PASSPORT_DRAW_TYPE_LONG_SELF}"/>
                    <c:set var="cacheCount" value="${cm:getMenuCacheCount(countCacheKeys)}"></c:set>
                    <li class="<c:if test="${type==PASSPORT_DRAW_TYPE_TW}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/passportDraw?type=${PASSPORT_DRAW_TYPE_TW}"><i class="fa fa-credit-card"></i> 因公赴台、长期因公出国
                            <c:if test="${cacheCount>0}">
                                <span class="badge badge-warning">${cacheCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <c:set var="countCacheKeys" value="${CACHEKEY_PASSPORT_DRAW_TYPE_OTHER}"/>
                    <c:set var="cacheCount" value="${cm:getMenuCacheCount(countCacheKeys)}"></c:set>
                    <li class="<c:if test="${type==PASSPORT_DRAW_TYPE_OTHER}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/passportDraw?type=${PASSPORT_DRAW_TYPE_OTHER}"><i class="fa fa-credit-card"></i> 处理其他事务
                            <c:if test="${cacheCount>0}">
                                <span class="badge badge-warning">${cacheCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <li class="<c:if test="${type==-1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/passportDraw?type=-1"><i class="fa fa-trash"></i> 已删除</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${type!=-1}">
                            <button data-url="${ctx}/passportDraw_au?type=${type}"
                                    class="popupBtn btn btn-primary btn-sm">
                                <i class="fa fa-plus"></i> 申请
                            </button>
                            </c:if>
                            <button data-url="${ctx}/passportDraw_view" data-open-by="page"
                                    class="jqOpenViewBtn btn btn-success btn-sm">
                                <i class="fa fa-info-circle"></i> 详情
                            </button>
                            <c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
                                <button class="printProofBtn btn btn-warning btn-sm">
                                    <i class="fa fa-print"></i> 打印在职证明
                                </button>
                            </c:if>
                            <c:if test="${type>=0}">
                                <button id="resetReturnDateBtn" data-url="${ctx}/reset_passportDraw_returnDate"
                                        class="jqOpenViewBtn btn btn-info btn-sm">
                                    <i class="fa fa-edit"></i> 修改归还日期
                                </button>
                                <button id="resetDrawStatusBtn" data-url="${ctx}/reset_passportDraw_return"
                                        data-title="重置归还状态"
                                        data-msg="确认重置该证件为未归还状态？"
                                        class="jqItemBtn btn btn-info btn-sm">
                                    <i class="fa fa-reply"></i> 重置归还状态
                                </button>

                                <shiro:hasPermission name="passportDraw:del">
                                    <button id="delBtn" class="jqBatchBtn btn btn-danger btn-sm"
                                            data-rel="tooltip" data-placement="top" title="证件未领取才可以删除"
                                       data-url="${ctx}/passportDraw_batchDel" data-title="删除申请使用证件申请"
                                       data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 删除</button>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${type==-1}">
                                <shiro:hasPermission name="passportDraw:del">
                                    <a class="jqBatchBtn btn btn-success btn-sm"
                                       data-url="${ctx}/passportDraw_batchUnDel" data-title="找回已删除申请使用证件申请"
                                       data-msg="确定恢复这{0}条申请记录吗？"><i class="fa fa-reply"></i> 恢复申请</a>
                                </shiro:hasPermission>

                                <shiro:hasPermission name="passportDraw:del">
                                    <button id="realDelBtn" class="jqBatchBtn btn btn-danger btn-sm"
                                            data-rel="tooltip" data-placement="top" title="未审批的记录可以删除"
                                            data-url="${ctx}/passportDraw_batchDel?isReal=1" data-title="删除申请使用证件申请"
                                            data-msg="确定删除这{0}条申请记录吗？"><i class="fa fa-trash"></i> 完全删除</button>
                                </shiro:hasPermission>
                            </c:if>
                        <c:if test="${type!=-1}">
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-url="${ctx}/passportDraw_data?export=1&exportType=${type}"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                    class="fa fa-download"></i> 导出</a>
                        </c:if>
                        </div>
                        <div class="myTableDiv"
                             data-url-page="${ctx}/passportDraw"
                             data-url-del="${ctx}/passportDraw_del"
                             data-url-bd="${ctx}/passportDraw_batchDel"
                             data-url-co="${ctx}/passportDraw_changeOrder"
                             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                            <c:set var="_query"
                                   value="${not empty param.cadreId ||not empty param._applyDate || not empty param.code}"/>
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
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i
                                                        class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
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
        <div id="item-content">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>


    $(".printProofBtn").click(function () {
        var grid = $("#jqGrid");
        var ids = grid.getGridParam("selarrrow");

        if (ids.length == 0) {
            SysMsg.warning("请选择行", "提示");
            return;
        }
        $.print("${ctx}/report/abroad_draw_proof?ids[]=" + ids)
    });

    $("#jqGrid").jqGrid({
        url: '${ctx}/passportDraw_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '编号',
                align: 'center',
                name: 'id',
                width: 50,
                formatter: function (cellvalue, options, rowObject) {
                    return 'D{0}'.format(cellvalue);
                },
                frozen: true
            },
            {label: '申请日期', align: 'center', name: 'applyDate', width: 100, frozen: true, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${type==PASSPORT_DRAW_TYPE_TW}">
            {
                label: '申请类型', name: 'type', formatter: function (cellvalue, options, rowObject) {
                return _cMap.PASSPORT_DRAW_TYPE_MAP[cellvalue];
            }, width: 100, frozen: true
            },
            </c:if>
            {label: '工作证号', align: 'center', name: 'user.code', frozen: true},
            {
                label: '姓名',
                align: 'center',
                name: 'user.realname',
                width: 75,
                formatter: function (cellvalue, options, rowObject) {
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                            .format(rowObject.cadre.id, cellvalue);
                },
                frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', width: 250},
            {label: '申请领取证件名称', align: 'center', name: 'passportClass.name', width: 180},
            {label: '证号号码', align: 'center', name: 'passport.code', title:false, cellattr: function (rowId, val, rawObject, cm, rdata) {
                return 'data-tooltip="tooltip" data-container="body" data-html="true" data-original-title="所在保险柜：' + rawObject.passport.safeBox.code + '<br> (过期时间：' + rawObject.passport.expiryDate.substr(0,10) + ')"';
            }},
            <c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
            {
                label: '因私出国（境）行程',
                align: 'center',
                name: 'applyId',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {
                    return '<a class="openView" href="javascript:;" ' +
                            'data-url="${ctx}/applySelf_view?id={0}">S{1}</a>'.format(cellvalue, cellvalue);
                }, title:false, cellattr: function (rowId, val, rawObject, cm, rdata) {
                return 'data-tooltip="tooltip" data-container="body" data-html="true" data-original-title="出行时间：'
                        + rawObject.applySelf.startDate + '<br> 前往国家或地区：' + rawObject.applySelf.toCountry
                        + '<br> 出国境事由：'+ rawObject.applySelf.reason.replace(/\+\+\+/g, ',') +'"';
            }},
            </c:if>
            <c:if test="${type==PASSPORT_DRAW_TYPE_TW || type==PASSPORT_DRAW_TYPE_OTHER}">
            {label: '${type==PASSPORT_DRAW_TYPE_TW?"出行时间":"使用时间"}', name: 'startDate', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '${type==PASSPORT_DRAW_TYPE_TW?"回国时间":"归还时间"}', name: 'endDate', width: 100, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '${type==PASSPORT_DRAW_TYPE_TW?"出行天数":"使用天数"}',
                name: 'day',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    return DateDiff(rowObject.startDate, rowObject.endDate);
                }
            },
            {
                label: '${type==PASSPORT_DRAW_TYPE_TW?"因公事由":"事由"}',
                name: 'reason',
                width: 200,
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue.replace(/\+\+\+/g, ',');
                }
            },
            <c:if test="${type==PASSPORT_DRAW_TYPE_TW}">
            {label: '费用来源', name: 'costSource', width: 100},
            </c:if>
            {
                label: '${type==PASSPORT_DRAW_TYPE_TW?"批件":"说明材料"}',
                name: 'files',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {

                    var filesArray = [];
                    for (var i in rowObject.files) {
                        if (rowObject.files.hasOwnProperty(i)) {
                            var file = rowObject.files[i];
                            //filesArray.push('<a class="various" href="${ctx}/attach/passportDrawFile?id={0}">${type==PASSPORT_DRAW_TYPE_TW?"批件":"材料"}{1}</a>'.format(file.id, parseInt(i) + 1));
                            filesArray.push('<a class="various" rel="group{2}" title="{3}" data-title-id="{4}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">${type==PASSPORT_DRAW_TYPE_TW?"批件":"材料"}{1}</a>'.format(encodeURI(file.filePath), parseInt(i) + 1 ,rowObject.id, file.fileName, file.id));
                        }
                    }
                    return filesArray.join("，");
                }
            },
            </c:if>
            <c:if test="${type!=PASSPORT_DRAW_TYPE_OTHER}">
            {
                label: '是否签注',
                align: 'center',
                name: 'needSign',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.passportClass.code == 'mt_passport_normal') {
                        return '-';
                    }
                    return (cellvalue) ? "是" : "否";
                }
            },
            {
                label: '签注申请表', align: 'center', width: 100, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.passportClass.code == 'mt_passport_normal' || !rowObject.needSign) {
                    return '-';
                }
                return '<a href="${ctx}/report/passportSign?id={0}" target="_blank">签注申请表 </a>'.format(rowObject.id);
            }
            },
            {
                label: '打印签注申请表', align: 'center', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.passportClass.code == 'mt_passport_normal' || !rowObject.needSign) {
                    return '-';
                }
                return '<button onclick="$.print(\'${ctx}/report/passportSign?id={0}&format=pdf\')" class="btn btn-info btn-xs"><i class="fa fa-print"></i> 打印签注申请表</button>'
                        .format(rowObject.id);
            }
            },
            </c:if>
            {
                label: '组织部审批', align: 'center', width: 100, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status == '${PASSPORT_DRAW_STATUS_INIT}')
                    return '<button data-url="${ctx}/passportDraw_check?id={0}"  class="openView btn btn-success btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-check"></i> 组织部审批</button>';
                else
                    return rowObject.statusName;
            }
            },
            {
                label: '短信通知', align: 'center', width: 100, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status == '${PASSPORT_DRAW_STATUS_INIT}') {
                 return '-';
                 }
                if (rowObject.drawStatus == '${PASSPORT_DRAW_DRAW_STATUS_UNDRAW}') {
                    return '<button data-url="${ctx}/shortMsg_view?id={0}&type=passportDrawApply" class="popupBtn btn btn-warning btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-info"></i> 短信通知</button>';
                }
                return '-'
            }
            },
            {
                label: '领取证件', align: 'center', width: 100, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status != '${PASSPORT_DRAW_STATUS_PASS}') {
                    return '-';
                }
                if (rowObject.drawStatus == '${PASSPORT_DRAW_DRAW_STATUS_UNDRAW}') {
                    return '<button data-url="${ctx}/passportDraw_draw?id={0}" class="openView btn btn-info btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-hand-lizard-o"></i> 领取证件</button>'
                }
                return _cMap.PASSPORT_DRAW_DRAW_STATUS_MAP[rowObject.drawStatus];
            }
            },
            {label: '应交组织部日期', align: 'center', name: 'returnDate', width: 130,cellattr:function(rowId, val, rowObject, cm, rdata) {
                if (rowObject.drawStatus == '${PASSPORT_DRAW_DRAW_STATUS_DRAW}'
                 && (rowObject.passport.type=='${PASSPORT_TYPE_KEEP}' ||
                        (rowObject.passport.type=='${PASSPORT_TYPE_CANCEL}' && !rowObject.passport.cancelConfirm)))  {
                    var _date = rowObject.returnDate;
                    if (_date <= new Date().format('yyyy-MM-dd'))
                        return "class='danger'";
                }
            }, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '催交证件',
                align: 'center',
                width: 110,
                formatter: function (cellvalue, options, rowObject) {

                    if(rowObject.passport.type=='${PASSPORT_TYPE_CANCEL}' && rowObject.passport.cancelConfirm)
                        return '-';

                    if(rowObject.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_RETURN}' &&
                            rowObject.passport.type=='${PASSPORT_TYPE_LOST}')
                        return '证件丢失';

                    if (rowObject.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_DRAW}' || rowObject.returnDateNotNow) {
                        return '-';
                    }
                    return '<button data-url="${ctx}/shortMsg_view?id={0}&type=passportDrawReturn" class="popupBtn btn btn-danger btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-hand-paper-o"></i> 催交证件</button>';
                }
            },
            {
                label: '归还证件',
                align: 'center',
                width: 100,
                formatter: function (cellvalue, options, rowObject) {

                    if( rowObject.drawStatus == '${PASSPORT_DRAW_DRAW_STATUS_RETURN}'){

                        if(rowObject.useRecord==undefined) return '-';

                        return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">使用记录</a>'
                                .format(encodeURI(rowObject.useRecord), "使用记录.jpg");
                    }

                    if((rowObject.passport.type=='${PASSPORT_TYPE_CANCEL}' && rowObject.passport.cancelConfirm) ||
                            rowObject.passport.type=='${PASSPORT_TYPE_LOST}' ||
                            rowObject.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_DRAW}') {
                        return '-';
                    }
                    return '<button data-url="${ctx}/passportDraw_return?id={0}" class="openView btn btn-default btn-xs">'
                                    .format(rowObject.id)
                            + '<i class="fa fa-reply"></i> 归还证件</button>'
                }
            },
            {label: '实交组织部日期', align: 'center', name: 'realReturnDate', width: 130,formatter: function (cellvalue, options, rowObject) {

                if(rowObject.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_RETURN}' &&
                        rowObject.passport.type=='${PASSPORT_TYPE_LOST}'){

                    return '证件丢失'
                }

                if(rowObject.passport.type=='${PASSPORT_TYPE_CANCEL}'){

                    var ret = _cMap.PASSPORT_CANCEL_TYPE_MAP[rowObject.passport.cancelType];
                    if(rowObject.passport.cancelType=='${PASSPORT_CANCEL_TYPE_OTHER}'
                       && $.trim(rowObject.passport.cancelTypeOther)!=''){
                        ret = ret + ":" + $.trim(rowObject.passport.cancelTypeOther);
                    }
                    return ret;
                }

                if(cellvalue==undefined) return ''

                return new Date(cellvalue).format('yyyy-MM-dd');
            }},
            { label: '附件', formatter:function(cellvalue, options, rowObject){
                //console.log(rowObject.attachmentFilename)
                if(rowObject.attachment && rowObject.attachment!='')
                    return '<a href="javascript:void(0)" class="popupBtn" ' +
                            'data-url="${ctx}/swf/preview?path={0}&filename={1}">查看</a>'
                                    .format(encodeURI(rowObject.attachment), encodeURI(rowObject.attachmentFilename));
                else return '';
            }},{hidden:true, name:'drawStatus'}
        ],
        onSelectRow: function(id,status){
            saveJqgridSelected("#"+this.id, id, status);

            var rowData = $(this).getRowData(id);
            $("#resetReturnDateBtn").prop("disabled",(rowData.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_DRAW}'));
            $("#resetDrawStatusBtn").prop("disabled",(rowData.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_RETURN}'));
            $("#delBtn").prop("disabled",(rowData.drawStatus != '${PASSPORT_DRAW_DRAW_STATUS_UNDRAW}'));
        }
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $('[data-tooltip="tooltip"]').tooltip({html:true});
    });


    $(window).triggerHandler('resize.jqGrid');
    _initNavGrid("jqGrid", "jqGridPager");

    register_fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>