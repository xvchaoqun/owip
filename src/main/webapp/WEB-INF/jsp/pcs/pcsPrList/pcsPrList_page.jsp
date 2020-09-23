<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:if test="${empty param.partyId}">
            <jsp:include page="menu.jsp"/>
        </c:if>
        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4">
                        <c:if test="${!hasSort && empty param.partyId}">
                            <div class="alert alert-warning" style="font-weight: bolder;margin-bottom: 6px">
                                请在“党员大会准备阶段”完成代表候选人姓氏笔画排序后，进行下面的操作。
                            </div>
                        </c:if>
                        <form class="form-inline" action="${ctx}/pcs/pcsPrList_au" id="recommendForm" method="post">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                                            class="fa fa-users"></i>  党代表名单</span>
                                        <c:if test="${not empty param.partyId}">
                                            <a style="padding-left: 20px;color: #23527c" href="${ctx}/pcs/pcsPrOw_export?file=pl&partyId=${param.partyId}&stage=${PCS_STAGE_THIRD}">
                                                <i class="fa fa-download"></i> 下载：党代表名单</a>
                                        </c:if>
<c:if test="${empty param.partyId}">
                                    <span style="margin-left: 20px">
                                                  <a href="javascript:;"
                                                     class="popupBtn btn btn-info btn-sm ${!allowModify?"disabled":""}"
                                                     data-width="900"
                                                     data-url="${ctx}/pcs/pcsPrList_candidates">
                                                      <i class="fa fa-plus-circle"></i> 添加</a>
                                                </span>
                                        <span class="tip">已选<span
                                                class="count">${fn:length(candidates)}</span>人，请认真核对代表的手机号和邮箱，确认无误后保存。</span>

    </c:if>
                                    </h3>
                                </div>
                                <div class="collapse in">
                                    <div class="panel-body">
                                        <table id="jqGrid2" data-width-reduce="30"
                                               class="jqGrid4 table-striped"></table>
                                    </div>
                                </div>
                            </div>
                        </form>
<c:if test="${empty param.partyId}">
                        <div class="modal-footer center" style="margin-top: 20px">
                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"  ${!allowModify?"disabled":""}
                                    class="btn btn-success btn-lg"><i class="fa fa-save"></i> 保&nbsp;&nbsp;存
                            </button>

                        </div>
    </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .form-table {
        margin: 0 10px 10px 0px;
        border: 1px solid #e5e5e5;
    }

    .form-table tr td {
        padding: 5px;
        border: 1px solid #e5e5e5;
        white-space: nowrap;
    }

    .form-table input, .form-table input:focus {
        width: 80px;
        background-color: #f2dede;
        border: solid 1px darkred;
        font-size: 20px;
        font-weight: bolder;
        color: #000 !important;
        /*text-align: center !important;*/
    }

    .form-table tr td:nth-child(odd) {
        font-weight: bolder;
        background-color: #f9f9f9 !important;
        text-align: right !important;
        vertical-align: middle !important;
    }

    .panel input.mobile, .panel input.email {
        width: 140px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
        color: red;
    }
    .panel input.email{
        width: 250px!important;
    }
    .panel .tip {
        margin-left: 30px
    }
    .panel .tip .count{
        color: darkred;
        font-size: 24px;
        font-weight: bolder;
    }
</style>
<script>
    var candidates = ${cm:toJSONArray(candidates)};
    var colModel = [
        <c:if test="${empty param.partyId}">
        {
            label: '移除', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            //console.log(options)
            return '<button ${!allowModify?"disabled":""} class="delRowBtn btn btn-danger btn-xs" data-id="{0}" data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.userId, options.gid)
        }
        },
            </c:if>
        {
            label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '--';
            return _cMap.PCS_PR_TYPE_MAP[cellvalue]
        }
        },
        {label: '工作证号', name: 'code', width: 120, frozen: true},
        {label: '姓名', name: 'realname', width: 110, frozen: true},

        {
            label: '手机号', name: 'mobile', width:160, formatter: function (cellvalue, options, rowObject) {
            return ('<input required type="text" name="mobile{0}" data-container="{1}" value="{2}" class="mobile" maxlength="11">')
                    .format(rowObject.userId, "#jqGrid", $.trim(cellvalue))
        }
        },
        {
            label: '邮箱', name: 'email', width:300, formatter: function (cellvalue, options, rowObject) {
            return ('<input required type="text" name="email{0}" data-container="{1}" value="{2}" class="email" maxlength="50">')
                    .format(rowObject.userId, "#jqGrid", $.trim(cellvalue))
        }
        },
        {
            label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
        },
        {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m',baseDate: '${_ageBaseDate}'}},
        {label: '民族', name: 'nation', width: 60},
        {label: '学历学位', name: 'education'},
        /*
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
        }, {label: '票数', name: 'vote3', width: 80}, {hidden: true, key: true, name: 'userId'}
    ];

    $("#jqGrid2").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 400,
        datatype: "local",
        rowNum: candidates.length,
        data: candidates,
        colModel: colModel,
        gridComplete: function () {
            <c:if test="${!allowModify || not empty param.partyId}">
             $("#recommendForm input, .panel input").prop("disabled", true);
            </c:if>
        }
    });
    $(window).triggerHandler('resize.jqGrid4');

    $(document).on("click", ".delRowBtn", function () {

        var $jqGrid = $("#" + $(this).data("gid"));
        var $count = $(this).closest(".panel").find(".tip .count");
        // console.log($(this).data("gid"))
        $jqGrid.delRowData($(this).data("id"));
        $count.html($jqGrid.jqGrid("getDataIDs").length);
    })

    $("#submitBtn").click(function () {
        $("#recommendForm").submit();
        return false;
    })

    $("#recommendForm").validate({
        submitHandler: function (form) {
            var items = [];
            $.each($("#jqGrid2").jqGrid("getDataIDs"), function (i, userId) {
                var $row = $("[role='row'][id=" + userId + "]", "#jqGrid2");
                var item = {};
                item.userId = userId;
                item.mobile = $.trim($("input.mobile", $row).val());
                item.email = $.trim($("input.email", $row).val());
                items.push(item);
            });

            //console.log(items)
            $(form).ajaxSubmit({
                data: {items: $.base64.encode(JSON.stringify(items))},
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("保存成功。", function () {
                            $.hashchange();
                        });
                        //$.hideView();
                    }
                }
            });
        }
    });

</script>