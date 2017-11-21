<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <h4 class="widget-title lighter smaller"
            style="position:absolute; top: -50px; left: 550px; ">
            <a href="javascript:" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-reply"></i>
                返回</a>
        </h4>

        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4">
                        <form class="form-inline" action="${ctx}/pcsVoteGroup_record" id="recommendForm"
                              method="post">
                            <input type="hidden" name="id" value="${param.groupId}">
                            <table class="form-table">
                                <tr>
                                    <td class="">小组名称：</td>
                                    <td width="60">${pcsVoteGroup.name}</td>
                                    <td>小组负责人：</td>
                                    <td width="60">${pcsVoteGroup.leader}</td>
                                    <td>小组成员：</td>
                                    <td width="60">${pcsVoteGroup.member}</td>
                                    <td>领回选票张数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="vote"
                                               value="${pcsVoteGroup.vote}"></td>
                                    <td>有效票数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="valid"
                                               value="${pcsVoteGroup.valid}"></td>
                                    <td>无效票数：</td>
                                    <td><input required type="text" maxlength="3" class="num"
                                               data-my="bottom center" data-at="top center"
                                               name="invalid"
                                               value="${pcsVoteGroup.invalid}">
                                    </td>
                                </tr>
                            </table>
                            <div id="accordion">

                                <div id="panel" class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><span class="title">
                                                <i class="fa fa-users"></i> ${PCS_USER_TYPE_MAP.get(type)}计票录入</span>
                                            <span style="margin-left: 20px">
                            <select id="userId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}"
                                    data-placeholder="请输入账号或姓名或职工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                                &nbsp;
                            <a href="javascript:;" onclick="_addUser()"
                               class="btn btn-info btn-sm ${!allowModify?"disabled":""}">
                                <i class="fa fa-plus-circle"></i> 添加</a>
                                <a href="javascript:;"
                                   class="popupBtn btn btn-primary btn-sm ${!allowModify?"disabled":""}"
                                   data-width="900"
                                   data-url="${ctx}/pcsVoteGroup_candidates?type=${type}">
                                    <i class="fa fa-plus-circle"></i>
                                    ${committeeCanSelect?'选择':'同步'}预备人选</a>
                                </span>
                                                <span class="tip">已选<span
                                                        class="count">${fn:length(pcsVoteCandidates)}</span>人</span>

                                        </h3>

                                    </div>
                                    <div id="collapse"
                                         class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <table id="jqGrid_record" data-width-reduce="30"
                                                   class="jqGrid4 table-striped"></table>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </form>
                        <div class="modal-footer center" style="margin-top: 20px">
                            <button id="submitBtn" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 正在保存，请稍后..." data-success-text="保存成功"
                                    autocomplete="off"  ${!allowModify?"disabled":""}
                                    class="btn btn-success btn-lg"><i class="fa fa-random"></i> 保存名单
                            </button>
                        </div>
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
        width: 60px;
        background-color: #f2dede;
        border: solid 1px darkred;
        font-size: 20px;
        font-weight: bolder;
        color: #000 !important;
        text-align: center !important;
    }

    .form-table tr td:nth-child(odd) {
        font-weight: bolder;
        background-color: #f9f9f9 !important;
        text-align: right !important;
        vertical-align: middle !important;
    }

    .panel .tip {
        margin-left: 30px
    }

    .panel .tip .count, .modal .tip .count {
        color: darkred;
        font-size: 24px;
        font-weight: bolder;
    }

    .panel {
        margin-bottom: 10px;
    }

    .panel input.num {
        width: 60px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
        color: red;
    }

    .panel span.title {
        font-weight: bolder;
        color: #669fc7
    }

    .panel span.agree{
        font-weight: bolder;
        font-size: 18px;
        color: red;
    }

    .panel select[disabled] {
        color: #848484 !important;
        background-color: #eee !important;
    }

    .confirm-modal .modal-dialog {
        width: 800px;
    }
</style>

<script>

    var colModel = [
        {
            label: '移除', name: 'requirement', width: 90, formatter: function (cellvalue, options, rowObject) {
            if (rowObject.isFromStage) return '-'
            //console.log(options)
            return '<button ${!allowModify?"disabled":""} class="delRowBtn btn btn-danger btn-xs" data-id="{0}" data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.userId, options.gid)
        }
        },
        {label: '候选人姓名', name: 'realname', width: 150, frozen: true},
        {
            label: '赞成票数', name: 'agree', formatter: function (cellvalue, options, rowObject) {
            if (rowObject.isFromStage){
               /* var valid = parseInt($("input[name=valid]", "#recommendForm").val());
                var degree = rowObject.degree;
                var abstain = rowObject.abstain;
                var agree = "";
                if(valid>0 && degree>=0 && abstain>=0){
                    agree = valid -(degree + abstain);
                }*/
                return '<span class="agree">{0}</span>'.format(rowObject.agree==undefined?'-':rowObject.agree)
            }
            return ('<input required type="text" name="agree{0}" value="{1}" class="agree num" maxlength="4">')
                    .format(rowObject.userId, $.trim(cellvalue))
        }
        },
        {
            label: '不赞成票数', name: 'degree', formatter: function (cellvalue, options, rowObject) {
            if (!rowObject.isFromStage) return '-'
            return ('<input required type="text" name="degree{0}" data-user-id="{0}" value="{1}" class="degree num" maxlength="4">')
                    .format(rowObject.userId, $.trim(cellvalue))
        }
        },
        {
            label: '弃权票数', name: 'abstain', formatter: function (cellvalue, options, rowObject) {
            if (!rowObject.isFromStage) return '-'
            return ('<input required type="text" name="abstain{0}" data-user-id="{0}" value="{1}" class="abstain num" maxlength="4">')
                    .format(rowObject.userId, $.trim(cellvalue))
        }
        },
        {
            label: '无效票数', name: 'invalid', formatter: function (cellvalue, options, rowObject) {
            if (!rowObject.isFromStage) return '-'
            return ('<input required type="text" name="invalid{0}" data-user-id="{0}" value="{1}" class="invalid num" maxlength="4">')
                    .format(rowObject.userId, $.trim(cellvalue))
        }
        },
        {
            label: '备注', name: '_remark', formatter: function (cellvalue, options, rowObject) {

            return rowObject.isFromStage ? "预备人选" : "另选他人";
        }
        }, {hidden: true, key: true, name: 'userId'}
    ];

    var candidates = ${cm:toJSONArray(pcsVoteCandidates)};
    var jqGrid = $("#jqGrid_record").jqGrid({
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
            <c:if test="${!allowModify}">
            $("#recommendForm input, .panel input, .panel select").prop("disabled", true);
            </c:if>
        }
    });
    $(window).triggerHandler('resize.jqGrid4');

    $(document).on("change blur keyup", "input.degree, input.abstain, input.invalid", function(){

        var valid = parseInt($("input[name=valid]", "#recommendForm").val());

        var userId = $(this).data("user-id");
        var $row = $("[role='row'][id=" + userId + "]", "#jqGrid_record");
        var degree = parseInt($.trim($("input.degree", $row).val()));
        var abstain = parseInt($.trim($("input.abstain", $row).val()));
        var invalid = parseInt($.trim($("input.invalid", $row).val()));

        //console.log(valid + " " + degree + " " + abstain)
        if(valid>0 && degree>=0 && abstain>=0 && invalid>=0){
            $("span.agree", $row).html(valid-(degree + abstain + invalid));
        }else{
            $("span.agree", $row).html("-");
        }
    });

    $(document).on("change blur keyup", "input[name=valid]", function(){

        var valid = parseInt($("input[name=valid]", "#recommendForm").val());

        $.each($("#jqGrid_record").jqGrid("getDataIDs"), function (i, userId) {
            var $row = $("[role='row'][id=" + userId + "]", "#jqGrid_record");
            var degree = parseInt($.trim($("input.degree", $row).val()));
            var abstain = parseInt($.trim($("input.abstain", $row).val()));
            var invalid = parseInt($.trim($("input.invalid", $row).val()));

            //console.log(valid + " " + degree + " " + abstain)
            if (valid > 0 && degree >= 0 && abstain >= 0 && invalid >= 0) {
                $("span.agree", $row).html(valid - (degree + abstain+invalid));
            } else {
                $("span.agree", $row).html("-");
            }
        });
    });

    $("#submitBtn").click(function () {
        $("#recommendForm").submit();
        return false;
    })

    $("#recommendForm").validate({
        submitHandler: function (form) {

            var vote = parseInt($("input[name=vote]", "#recommendForm").val());
            var valid = parseInt($("input[name=valid]", "#recommendForm").val());
            var invalid = parseInt($("input[name=invalid]", "#recommendForm").val());
            if(vote != (valid + invalid)){
                $.tip({$target:$("input[name=vote]", "#recommendForm"), msg:'领回选票张数填写有误'})
                return;
            }
            var items = [];
            $.each($("#jqGrid_record").jqGrid("getDataIDs"), function (i, userId) {

                var rowData = $("#jqGrid_record").getRowData(userId);
                var $row = $("[role='row'][id=" + userId + "]", "#jqGrid_record");
                var item = {};
                item.userId = userId;
                item.realname = rowData.realname;
                item.agree = $.trim($("input.agree", $row).val());
                item.degree = $.trim($("input.degree", $row).val());
                item.abstain = $.trim($("input.abstain", $row).val());
                item.invalid = $.trim($("input.invalid", $row).val());
                items.push(item);
            });

            var $btn = $("#submitBtn").button('loading');
            //console.log(items)
            //return;
            $(form).ajaxSubmit({
                data: {items: new Base64().encode(JSON.stringify(items))},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });

    function _addUser(selectId, jqGridId) {

        var $select = $("#userId");
        var $jqGrid = $("#jqGrid_record");
        var userId = $select.val();
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "请选择教职工党员"
            })
            return;
        }
        var rowData = $jqGrid.getRowData(userId);
        if (rowData.userId != undefined) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                my: 'bottom center', at: 'top center', msg: "您已经添加了该教职工"
            })
            return;
        }

        $.post("${ctx}/pcsVoteGroup_selectUser", {"userIds[]": userId, type:'${param.type}'}, function (ret) {
            if (ret.success) {
                // console.log(ret.candidate)
                $jqGrid.jqGrid("addRowData", ret.candidates[0].userId, ret.candidates[0], "last");
                $select.val(null).trigger("change");
                $select.closest(".panel").find(".tip .count").html($jqGrid.jqGrid("getDataIDs").length);
            }
        })
    }

    $(document).on("click", ".delRowBtn", function () {

        var $jqGrid = $("#" + $(this).data("gid"));
        var $count = $(this).closest(".panel").find(".tip .count");
        // console.log($(this).data("gid"))
        $jqGrid.delRowData($(this).data("id"));
        $count.html($jqGrid.jqGrid("getDataIDs").length);
    })

    register_user_select($('[data-rel="select2-ajax"]'));
</script>