<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                            class="fa fa-th-list"></i>   大会材料清单</span>
                        <span style="margin-left: 20px; visibility: hidden">
                    <a href="javascript:;"
                       class="popupBtn btn btn-info btn-sm"
                       data-width="900"
                       data-url="">
                        <i class="fa fa-info-circle"></i> &nbsp;</a>
                        </span>

                        <div class="panel-toolbar">
                            <a data-toggle="collapse"  href="#collapseOne">
                                <i class="ace-icon fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </h3>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <div style="width: 800px">
                            <table class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th width="30">序号</th>
                                    <th>材料名称</th>
                                    <c:if test="${empty param.partyId}">
                                        <th width="100">模板</th>
                                        <th width="100">报党委组织部</th>
                                    </c:if>
                                    <th width="100">预览</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${templates}" var="template" varStatus="vs">
                                    <c:set var="file" value="${fileMap.get(template.id)}"/>
                                    <tr>
                                        <td>${vs.count}</td>
                                        <td  style="text-align: left">${template.name}</td>
                                        <c:if test="${empty param.partyId}">
                                            <td>
                                                <a href="${ctx}/attach/download?path=${cm:encodeURI(template.filePath)}&filename=${cm:encodeURI(template.fileName)}">下载</a>
                                            </td>
                                            <td>
                                                <a class="popupBtn btn ${not empty file?"btn-success":"btn-primary"} btn-xs ${allowModify?"":"disabled"}"
                                                   data-url="${ctx}/pcsPrFile_au?templateId=${template.id}&id=${file.id}"><i
                                                        class="fa fa-upload"></i> ${not empty file?"重新上传":"上传材料"}</a>
                                            </td>
                                        </c:if>
                                        <td>
                                            <c:if test="${not empty file}">
                                                <a href="javascript:void(0)" class="popupBtn"
                                                   data-url="${ctx}/swf/preview?path=${cm:encodeURI(file.filePath)}&filename=${cm:encodeURI(file.fileName)}">预览</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                            class="fa fa-users"></i>   党代表候选人名单</span>
        <span style="margin-left: 20px">

        <a class="popupBtn btn btn-warning btn-sm"
         data-width="750"
         data-url="${ctx}/hf_content?code=hf_pcs_pr_realname">
          <i class="fa fa-question-circle"></i> 姓氏笔画排序规则</a>
        </span>
                        <span class="tip text-danger">请按姓氏笔画排序后保存，可拖拽行进行排序</span>

                        <div class="panel-toolbar">
                            <a data-toggle="collapse"  href="#collapseTwo">
                                <i class="ace-icon fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </h3>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <table id="jqGrid1" data-width-reduce="30"
                               class="jqGrid4 table-striped"></table>

                        <div class="modal-footer center" style="margin-top: 20px">
                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"  ${(allowModify && empty param.partyId)?"":"disabled"}
                                    class="btn btn-success btn-lg"><i class="fa fa-save"></i> 确定按姓氏笔画排序无误，保存名单
                            </button>
                        </div>
                    </div>
                </div>
            </div>


    </div>
</div>
<style>
    .table tr th, .table tr td {
        white-space: nowrap;
        text-align: center;
    }

    .panel .tip {
        margin-left: 30px;
        font-size: 22px;
        font-weight: bolder;
    }

</style>
<script>
    function _reload() {
        $.hashchange();
    }
    var candidates = ${cm:toJSONArray(candidates)};
    var colModel = [
        {
            label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
            if (cellvalue == undefined) return '-';
            return _cMap.PCS_PR_TYPE_MAP[cellvalue]
        }
        },
        {label: '工作证号', name: 'code', width: 120, frozen: true},
        {label: '被推荐人姓名', name: 'realname', width: 150, frozen: true},

        {
            label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
        },
        {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
        {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
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
         formatter: 'date',
         formatoptions: {newformat: 'Y-m-d'}
         },*/
        {
            label: '入党时间',
            name: 'growTime',
            width: 120,
            sortable: true,
            formatter: 'date',
            formatoptions: {newformat: 'Y-m-d'}
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
        }, {hidden: true, key: true, name: 'userId'}
    ];
    $("#jqGrid1").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 400,
        datatype: "local",
        rowNum: candidates.length,
        data: candidates,
        colModel: colModel
    });
    <c:if test="${allowModify  && empty param.partyId}">
    $("#jqGrid1").jqGrid('sortableRows')
    </c:if>

    $("#submitBtn").click(function () {

        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 保存',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回',
                    className: 'btn-default btn-show'
                }
            },
            message: "<div style='padding: 50px;font-size: 22px;font-weight: bolder;color: red;'><i class='fa fa-info-circle'></i> 保存前务必按照姓氏笔画排序。</div>",
            callback: function (result) {
                if (result) {
                    $("#submitBtn").button('loading');
                    var userIds = $("#jqGrid1").jqGrid("getDataIDs")
                    $.post("${ctx}/pcsPrList_sort", {userIds: userIds}, function (ret) {
                        if (ret.success) {
                            $.tip({
                                $target: $("#submitBtn"),
                                at: 'top center', my: 'bottom center', type: 'success',
                                msg: "保存成功。"
                            });
                            $("#submitBtn").button('reset');
                        }
                    });
                }
            },
            title: '确定按姓氏笔画排序无误，保存名单'
        });

    })
</script>