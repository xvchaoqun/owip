<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <div style="margin-bottom: 8px">
            <div class="buttons">
                <a href="javascript:;" class="hideView btn btn-sm btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回
                </a>
            </div>
        </div>
    </ul>

    <div class="tab-content">
        <div class="tab-pane in active">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller" style="font-size: larger;font-weight: bolder">
                        <i class="fa fa-check-square"></i> 已报名岗位
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <table id="jqGrid1" class="jqGrid4 table-striped"></table>
                    </div>
                </div>
            </div>

            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller" style="font-size: larger;font-weight: bolder">
                        <i class="fa fa-check-square-o"></i> 可选择的岗位
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <table id="jqGrid2" class="jqGrid4 table-striped"></table>
                    </div>
                </div>
            </div>

            <div class="modal-footer center" style="margin-top: 20px">
                <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                        autocomplete="off"
                        class="btn btn-success btn-lg"><i class="fa fa-random"></i> 确定调整岗位
                </button>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="msgTpl">
    <div class="submit-note">
        <div>
            您要将
        </div>
        <div class="applys">
            {{_.each(applyPosts, function(post, idx){ }}
            <div class="apply text-primary">{{=post}}</div>
            {{});}}
        </div>
        <div>调整为</div>
        <div class="selectables">
            {{_.each(selectablePosts, function(post, idx){ }}
            <div class="selectable text-success">{{=post}}</div>
            {{});}}
        </div>
    </div>
</script>
<style>
    .confirm-modal .modal-dialog{
        width: 400px;
    }
    .confirm-modal .submit-note {
        padding: 20px;
        font-weight: bolder;
        font-size: 16px;
    }
    .confirm-modal .submit-note .applys, .confirm-modal .submit-note .selectables{
        padding-left: 20px;
    }
    .confirm-modal .submit-note .apply, .confirm-modal .submit-note .selectable {
        font-weight: bolder;
        font-size: 20px;
    }
</style>
<script>
    var applyPosts = ${cm:toJSONArray(applyPosts)};
    var selectablePosts = ${cm:toJSONArray(selectablePosts)};

    var colModel = [
        {
            label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
            var type = _cMap.CRS_POST_TYPE_MAP[rowObject.type];
            return type + "〔" + rowObject.year + "〕" + rowObject.seq + "号";

        }, width: 190, frozen: true
        },
        {label: '招聘岗位', name: 'name', width: '300', frozen: true},
        {label: '分管工作', name: 'job', width: '300', formatter: $.jgrid.formatter.NoMultiSpace},
        {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
        {label: '所属单位', name: 'unit.name', width: 200},
        {
            label: '招聘公告', name: 'notice', width: 90, formatter: function (cellvalue, options, rowObject) {

            if ($.trim(rowObject.notice) == '') return '--'

            return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/pdf_preview?path={0}&filename={1}">查看</a>'
                    .format(rowObject.notice, encodeURI(rowObject.name + "招聘公告.pdf"))
        }
        },
        {
            label: '基本条件', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_requirement?postId={0}">查看</a>'
                    .format(rowObject.id)
        }
        },
        {
            label: '任职资格', name: 'qualification', width: 90, formatter: function (cellvalue, options, rowObject) {
            return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/user/crsPost_qualification?postId={0}">查看</a>'
                    .format(rowObject.id)
        }
        },
        {label: '招聘人数', name: 'num', width: 90},
        {
            label: '应聘截止时间', name: 'endTime', width: 150, formatter: $.jgrid.formatter.date,
            formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}
        }
    ];

    var colModel1 = colModel.concat(
            {
                label: '报名情况', name: '_apply', width: 150, formatter: function (cellvalue, options, rowObject) {
                return (!rowObject.applicantIsQuit) ? "已报名" : "已退出";
            }
            });

    $("#jqGrid1").jqGrid({
        pager: null,
        multiboxonly: false,
        datatype: "local",
        rowNum: applyPosts.length,
        data: applyPosts,
        colModel: colModel1
    }).jqGrid("setFrozenColumns");

    $("#jqGrid2").jqGrid({
        pager: null,
        multiboxonly: false,
        datatype: "local",
        rowNum: selectablePosts.length,
        data: selectablePosts,
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid4');

    $("#submitBtn").click(function () {

        var applyPostIds = $("#jqGrid1").getGridParam("selarrrow");
        var selectablePostIds = $("#jqGrid2").getGridParam("selarrrow");
        if (applyPostIds.length == 0) {
            SysMsg.info("请选择已报名岗位");
            return;
        }
        if (selectablePostIds.length == 0) {
            SysMsg.info("请选择需要调整的岗位");
            return;
        }
        var applyPosts = [];
        $.each(applyPostIds, function (i, postId) {
            var rowData = $("#jqGrid1").getRowData(postId);
            applyPosts.push(rowData.name);
        });
        var selectablePosts = [];
        $.each(selectablePostIds, function (i, postId) {
            var rowData = $("#jqGrid2").getRowData(postId);
            selectablePosts.push(rowData.name);
        });
        var msg = _.template($("#msgTpl").html())({applyPosts: applyPosts, selectablePosts: selectablePosts})

        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-random"></i> 确定调整',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-times"></i> 取消',
                    className: 'btn-default btn-show'
                }
            },
            message: msg,
            callback: function (result) {
                if (result) {
                    var $btn = $("#submitBtn").button('loading');
                    $.post("${ctx}/user/crsApplicantAdjust", {
                        applyPostIds: applyPostIds,
                        selectablePostIds: selectablePostIds
                    }, function (ret) {
                        if (ret.success) {

                            $btn.button("success").addClass("btn-success");
                            $.hashchange();
                        } else {
                            $btn.button('reset');
                        }
                    });
                }
            },
            title: '岗位调整'
        });


    });
</script>