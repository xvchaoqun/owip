<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/abroad/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="jqgrid-vertical-offset buttons">
                <a class="openView btn btn-success btn-sm" data-url="${ctx}/user/abroad/passportDraw_select"><i
                        class="fa fa-plus"></i> 申请使用因私出国（境）证件</a>
                <a class="popupBtn btn btn-info btn-sm"
                   data-url="${ctx}/hf_content?code=hf_passport_draw_note">
                    <i class="fa fa-info-circle"></i> 申请说明</a>
            </div>
            <div class="space-4"></div>
            <div class="tabbable">
                <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_SELF}">active</c:if>">
                        <a href="javascript:;" class="hashchange" data-querystr="type=${ABROAD_PASSPORT_DRAW_TYPE_SELF}"><i class="fa fa-plane"></i> 因私出国（境）</a>
                    </li>
                    <li class="<c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW}">active</c:if>">
                        <a href="javascript:;" class="hashchange" data-querystr="type=${ABROAD_PASSPORT_DRAW_TYPE_TW}"><i class="fa fa-ship"></i> 因公赴台、长期因公出国</a>
                    </li>
                    <li class="<c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_OTHER}">active</c:if>">
                        <a href="javascript:;" class="hashchange" data-querystr="type=${ABROAD_PASSPORT_DRAW_TYPE_OTHER}"><i class="fa fa-share"></i> 处理其他事务</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="myTableDiv"
                             data-url-page="${ctx}/user/abroad/passportDraw"
                             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

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
<script type="text/template" id="needSign_tpl">
    <button data-url="${ctx}/user/abroad/passportDraw_self_sign?type=view&id={{=id}}&passportId={{=passportId}}"
            class="openView btn btn-success btn-xs">
        <i class="fa fa-eye"></i> {{=printStr}}签注申请表
    </button>
</script>
<script type="text/template" id="notNeedSign_tpl">
    <button data-url="${ctx}/user/abroad/passportDraw_self_sign?type=add&id={{=id}}&passportId={{=passportId}}"
            class="openView btn btn-default btn-xs">
        <i class="fa fa-plus"></i> 办理签注
    </button>
</script>
<script type="text/template" id="abolish_tpl">
    <button class="confirm btn btn-danger btn-xs"
            data-url="${ctx}/user/abroad/passportDraw_del?id={{=id}}"
            data-callback="_delCallback"
            data-msg="确定撤销该申请吗？">
        <i class="fa fa-trash"></i> 撤销申请
    </button>
</script>
<script type="text/template" id="applyId_tpl">
    <a class="openView" href="javascript:"
       data-url="${ctx}/user/abroad/applySelf_view?id={{=id}}"> S{{=id}}</a>
</script>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/user/abroad/userPassportDraw_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '编号', name: 'id', width: 75, formatter: function (cellvalue, options, rowObject) {
                return "D{0}".format(cellvalue);
            }, frozen: true
            },
            {label: '申请日期', name: 'applyDate', frozen: true, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW}">
            {
                label: '申请类型', name: 'type', formatter: function (cellvalue, options, rowObject) {
                return _cMap.ABROAD_PASSPORT_DRAW_TYPE_MAP[cellvalue];
            }, width: 100, frozen: true
            },
            </c:if>
            {label: '申请领取证件名称', name: 'passportClass.name', width: 180, frozen: true},
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_SELF}">
            {
                label: '因私出国（境）行程', name: 'applyId', width: 160, formatter: function (cellvalue, options, rowObject) {

                return _.template($("#applyId_tpl").html().NoMultiSpace())({id: cellvalue});
            }
            },
            {
                label: '领取证件用途', name: 'useType', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.ABROAD_PASSPORT_DRAW_USE_TYPE_MAP[cellvalue];
            }
            },
            </c:if>
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW || type==ABROAD_PASSPORT_DRAW_TYPE_OTHER}">
            {label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"出行时间":"使用时间"}', name: 'startDate', width: 100},
            {label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"回国时间":"归还时间"}', name: 'endDate', width: 100},
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"出行天数":"使用天数"}',
                name: 'day',
                width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    return $.dayDiff(rowObject.startDate, rowObject.endDate);
                }
            },
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"因公事由":"事由"}',
                name: 'reason',
                width: 200,
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue.replace(/\+\+\+/g, ',');
                }
            },
            <c:if test="${type==ABROAD_PASSPORT_DRAW_TYPE_TW}">
            {label: '费用来源', name: 'costSource', align:'left', width: 150},
            </c:if>
            {
                label: '${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"批件":"说明材料"}',
                name: 'files',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {

                    var filesArray = [];
                    /*for (var i in rowObject.files) {
                        if (rowObject.files.hasOwnProperty(i)) {
                            var file = rowObject.files[i];
                            filesArray.push('<a href="${ctx}/attach/passportDrawFile?id={0}">${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"批件":"材料"}{1}</a>'.format(file.id, parseInt(i) + 1));
                        }
                    }*/
                    rowObject.files.forEach(function(file, i){
                        filesArray.push('<a class="various" rel="group{2}" title="{3}" data-title-id="{4}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">${type==ABROAD_PASSPORT_DRAW_TYPE_TW?"批件":"材料"}{1}</a>'.format(encodeURI(file.filePath), parseInt(i) + 1 ,rowObject.id, file.fileName, file.id));
                    })
                    return filesArray.join("，");
                }
            },
            </c:if>
            <c:if test="${type!=ABROAD_PASSPORT_DRAW_TYPE_OTHER}">
            {
                label: '是否签注', name: 'needSign', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }
            },
            {
                label: '签注申请表',
                name: 'op',
                width: 130,
                frozen: true,
                formatter: function (cellvalue, options, rowObject) {
                    var str = "";
                    <c:if test="${type!=ABROAD_PASSPORT_DRAW_TYPE_OTHER}">
                    if (rowObject.passportClass.code != 'mt_passport_normal') {
                        //console.log(rowObject.needSign)
                        if (rowObject.needSign)
                            str += _.template($("#needSign_tpl").html().NoMultiSpace())({
                                id: rowObject.id,
                                passportId: rowObject.passportId,
                                printStr:(rowObject.status == '${ABROAD_PASSPORT_DRAW_STATUS_PASS}')?"打印":""
                            });
                        else
                            str += _.template($("#notNeedSign_tpl").html().NoMultiSpace())({
                                id: rowObject.id,
                                passportId: rowObject.passportId
                            });
                    }
                    </c:if>
                    return str;
                }
            },
            </c:if>
            {
                label: '审批状态', name: 'status', width: 150, formatter: function (cellvalue, options, rowObject) {
                var str = _cMap.ABROAD_PASSPORT_DRAW_STATUS_MAP[cellvalue];
                if ($.trim(rowObject.approveRemark) != '') {
                    if (rowObject.status == '${ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS}') {
                        str += '（<a class="remark" data-remark="{0}" data-status="${ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS}"  href="javascript:;">原因</a>）'.format(rowObject.approveRemark)
                    }
                    if (rowObject.status == '${ABROAD_PASSPORT_DRAW_STATUS_PASS}') {
                        str += '（<a class="remark" data-remark="{0}" data-status="${ABROAD_PASSPORT_DRAW_STATUS_PASS}" href="javascript:;">备注</a>）'.format(rowObject.approveRemark);
                    }
                }
                return str;
            }
            },
            {label: '应交组织部日期', name: 'returnDate', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '实交组织部日期', name: 'realReturnDate', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '操作', name: 'op', width: 100, frozen: true, formatter: function (cellvalue, options, rowObject) {
                var str = "";
                if (rowObject.status == 0) {
                    str += _.template($("#abolish_tpl").html().NoMultiSpace())({id: rowObject.id});
                }
                return str;
            }
            }
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(".remark").click(function (e) {
            var remark = $(this).data("remark");
            var status = $(this).data("status");
            var title = "未通过原因";
            if (status == '${ABROAD_PASSPORT_DRAW_STATUS_PASS}')
                title = "备注";
            SysMsg.info(remark, title);
            e.stopPropagation();
        });

        $(window).triggerHandler('resize.jqGrid');
    });

    function _delCallback(target) {
        SysMsg.success('撤销成功。', '成功', function () {
            $("#jqGrid").trigger("reloadGrid");
        });
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });
</script>