<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:if test="${!hasSort && empty param.partyId}">
            <div class="alert alert-warning" style="font-weight: bolder;margin-bottom: 6px">
            请在“党员大会准备阶段”完成代表候选人姓氏笔画排序后，进行下面的操作。
            </div>
        </c:if>
        <div class="widget-box transparent">
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <div class="tab-content padding-4">

                        <form class="form-inline" action="${ctx}/pcsPrVote" id="recommendForm" method="post">
                            <table class="form-table">
                                <tr>
                                    <td width="50">时间：</td>
                                    <td colspan="2" width="60">
                                        <div class="input-group" data-my="bottom center" data-at="top center" style="width: 250px">
                                            <input class="form-control datetime-picker required"
                                                   type="text"  name="meetingTime"
                                                   value="${cm:formatDate(pcsPrRecommend.meetingTime, "yyyy-MM-dd HH:mm")}">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                        </div>
                                    </td>
                                    <td width="60">地点：</td>
                                    <td colspan="3">
                                        <input required type="text"
                                               data-my="bottom center" data-at="top center"
                                               name="meetingAddress" style="width: 100%;"
                                               value="${pcsPrRecommend.meetingAddress}">
                                    </td>
                                    <td colspan="2" width="50">选举结果报告单<c:if test="${not empty pcsPrRecommend.reportFilePath}">
                                        (<a href="javascript:void(0)" class="popupBtn"
                                           data-url="${ctx}/swf/preview?path=${cm:encodeURI(pcsPrRecommend.reportFilePath)}&filename=${cm:encodeURI("选举结果报告单.pdf")}">预览已上传</a>)
                                    </c:if>：
                                    </td>
                                    <td colspan="4">
                                        <input ${empty pcsPrRecommend.reportFilePath?"required":""} class="form-control" type="file" name="_file"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="">党员总数：</td>
                                    <td width="60">${pcsPartyView.memberCount}</td>
                                    <td colspan="2">有选举权的正式党员数：</td>
                                    <td style="width: 60px"><input required type="text" maxlength="3" class="num"

                                               name="voteMemberCount"
                                               value="${pcsPrRecommend.voteMemberCount}"></td>
                                    <td colspan="2">应参会正式党员数：</td>
                                    <td width="50"><input required type="text" maxlength="3" class="num"

                                               name="expectPositiveMemberCount"
                                               value="${pcsPrRecommend.expectPositiveMemberCount}"></td>
                                    <td colspan="2">实参会正式党员数：</td>
                                    <td width="50"><input required type="text" maxlength="3" class="num"

                                               name="actualPositiveMemberCount"
                                               value="${pcsPrRecommend.actualPositiveMemberCount}">
                                        <a href="javascript:;" onclick="_tipPopup()" class="text-success">应到会人数如何计算？</a>
                                    </td>

                                </tr>
                            </table>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span style="font-weight: bolder; color: #669fc7"><i
                                            class="fa fa-users"></i>   党员大会选举情况</span>
                                    </h3>
                                </div>
                                <div class="collapse in">
                                    <div class="panel-body">
                                        <table id="jqGrid1" data-width-reduce="30"
                                               class="jqGrid4 table-striped"></table>
                                    </div>
                                </div>
                            </div>
                        </form>
<c:if test="${empty param.partyId}">
                        <div class="modal-footer center" style="margin-top: 20px">
                            <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                                    autocomplete="off"  ${!allowModify?"disabled":""}
                                    class="btn btn-success btn-lg"><i class="fa fa-save"></i> 保存党员大会选举情况
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

    .panel input.vote {
        width: 60px !important;
        padding: 0px !important;
        text-align: center;
        font-weight: bolder;
        font-size: 18px;
        color: red;
    }

    .confirm-modal .modal-dialog {
        width: 800px;
    }
</style>
<script type="text/template" id="alertTpl">
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <a name="OLE_LINK4"></a><a name="OLE_LINK3"></a><b><span style="font-size:14.0pt;font-family:宋体;">党员大会进行选举时，有选举权的到会人数超过应到会人数的<span>4/5</span>，会议有效。为了保证选举工作能够顺利进行，党员因下列情况不能参加选举的，经报上级党组织同意，并经支部党员大会通过，可以不计算在应到会人数之内：</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>1</span>）患有精神病或因其他疾病导致不能表达本人意志的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>2</span>）出国半年以上的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>3</span>）虽未受到留党察看以上党纪处分，但正在服刑的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>4</span>）年老体弱卧床不起和长期生病、生活不能自理的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span
                style="font-size:14.0pt;font-family:宋体;">（<span>5</span>）工作调动，下派锻炼，外出学习或工作半年以上等，按规定应转走正式组织关系而没有转走的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <p class="MsoListParagraph" align="left" style="margin-left:16.8pt;text-indent:28.1pt;">
        <b><span style="font-size:14.0pt;font-family:宋体;">（<span>6</span>）已经回原籍长期居住的离退休人员中的党员，因特殊情况，没有从原单位转出党员组织关系、确实不能参加选举的。</span></b><span
            style="font-size:14.0pt;font-family:宋体;"></span>
    </p>
    <b><span style="font-size:14.0pt;font-family:宋体;">凡上述情况之外的党员不能参加党员大会进行选举，仍应计算在应到会人数之列。</span></b><br/>
</script>
<script>

    function _tipPopup() {

        var msg = _.template($("#alertTpl").html());
        bootbox.alert({
            className: "confirm-modal",
            message: msg,
            title: '应到会人数如何计算？'
        });
    }

    register_datetime($('.datetime-picker'));

    $.fileInput($('#recommendForm input[type=file]'),{
        no_file: '请选择pdf文件 ...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

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
            label: '票数', name: 'vote3', formatter: function (cellvalue, options, rowObject) {
            return ('<input required type="text" name="vote{0}" data-container="{1}" value="{2}" class="vote num" maxlength="4">')
                    .format(rowObject.userId,"#jqGrid1", $.trim(cellvalue))
        }
        },
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
        },/*
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
        },{hidden: true, key: true, name: 'userId'}
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
        colModel: colModel,
        gridComplete: function () {
            <c:if test="${!allowModify || not empty param.partyId}">
            $("#recommendForm input, .panel input").prop("disabled", true);
            </c:if>
        }
    });
    $(window).triggerHandler('resize.jqGrid4');

    $("#submitBtn").click(function () {
        var $null = null;
        $(".vote", ".panel").each(function () {
            var $this = $(this);
            if ($.trim($this.val()) == '') {
                $null = $this;
                return false;
            }
        });
        //console.log($null)
        if ($null != null) {
            var $panel = $null.closest('.panel');
            var $title = $panel.find('span.title');
            $.tip({
                $target: $title,
                //$container:$panel,
                at: "top center",
                my: "bottom left",
                msg: '请填写完整每位候选人的票数'
            });
        }

        $("#recommendForm").submit();
        return false;
    })

    $("#recommendForm").validate({
        submitHandler: function (form) {
            _ajaxSubmit(form);
        }
    });

    function _ajaxSubmit(form) {

        var items = [];
        $.each($("#jqGrid1").jqGrid("getDataIDs"), function (i, userId) {
            var $row = $("[role='row'][id=" + userId + "]", "#jqGrid1");
            var item = {};
            item.userId = userId;
            item.vote = $.trim($("input.vote", $row).val());
            items.push(item);
        });

        //console.log(items)
        $(form).ajaxSubmit({
            data: {items: new Base64().encode(JSON.stringify(items))},
            success: function (ret) {
                if (ret.success) {
                    SysMsg.success("提交成功。", function () {
                        $.hashchange();
                     });
                    //$.hideView();
                }
            }
        });
    }

</script>