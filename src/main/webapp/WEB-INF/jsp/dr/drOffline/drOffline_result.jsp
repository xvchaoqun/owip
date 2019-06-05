<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回
            </a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    【编号】${drOffline.code}&nbsp;&nbsp;【推荐岗位】${drOffline.postName}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">编辑推荐结果</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content" style="padding-top: 0">

                <div class="row dispatch_cadres" style="width: 1250px;padding-top: 0">
                    <div class="dispatch" style="width: 480px;margin-right: 0">
                        <div class="widget-box" style="width: 470px;">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    第一步：填写民主推荐会基本信息
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main" style="margin-bottom: 10px; height: 480px;">
                                    <form class="form-horizontal" action="${ctx}/drOffline_result"
                                          id="resultForm" method="post">
                                        <input type="hidden" name="id" value="${param.id}">
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label"><span class="star">*</span>民主推荐统计表标题</label>
                                            <div class="col-xs-7">
                                                <textarea required class="form-control noEnter" name="title">${drOffline.title}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-4 control-label"><span class="star">*</span>参加推荐范围</label>
                                            <div class="col-xs-7">
                                                <textarea required class="form-control noEnter" name="scope">${drOffline.scope}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group" style="padding: 5px 10px;">
                                        <table class="table table-striped table-bordered
                                                table-condensed table-center table-unhover2">
                                            <thead>
                                                <tr>
                                                    <th>推荐<br/>人数</th>
                                                    <th>应参加<br/>推荐人数</th>
                                                    <th>实际参加<br/>推荐人数</th>
                                                    <th>收回<br/>推荐票数</th>
                                                    <th>弃权<br/>票数</th>
                                                    <th>作废<br/>票数</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="bg-center">
                                                    <input required class="form-control digits"
                                                           data-at="bottom center" data-my="top center"
                                                    style="width: 50px;margin: 0 auto;" type="text" name="headcount" maxlength="4"
                                                           value="${drOffline.headcount}">
                                                </td>
                                                <td class="bg-center">
                                                    <input required class="form-control digits"
                                                           data-at="bottom center" data-my="top center"
                                                           style="width: 50px;margin: 0 auto;" type="text"
                                                           name="expectVoterNum" maxlength="4"
                                                           value="${drOffline.expectVoterNum}">
                                                </td>
                                                <td class="bg-center">
                                                    <input required class="form-control digits"
                                                           data-at="bottom center" data-my="top center"
                                                           style="width: 50px;margin: 0 auto;" type="text"
                                                           name="actualVoterNum" maxlength="4"
                                                           value="${drOffline.actualVoterNum}">
                                                </td>
                                                <td class="bg-center">
                                                    <input required class="form-control digits"
                                                           data-at="bottom center" data-my="top center"
                                                           style="width: 50px;margin: 0 auto;" type="text" name="ballot" maxlength="4"
                                                           value="${drOffline.ballot}">
                                                </td>
                                                <td class="bg-center">
                                                    <input required class="form-control digits"
                                                           data-at="bottom center" data-my="top center"
                                                           style="width: 50px;margin: 0 auto;" type="text" name="abstain" maxlength="4"
                                                           value="${drOffline.abstain}">
                                                </td>
                                                <td class="bg-center">
                                                    <input required class="form-control digits"
                                                           data-at="bottom center" data-my="top center"
                                                           style="width: 50px;margin: 0 auto;" type="text" name="invalid" maxlength="4"
                                                           value="${drOffline.invalid}">
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-6 control-label">参加推荐的人员是否区分类别</label>
                                            <div class="col-xs-4">
                                                <input type="checkbox" class="big" name="needVoterType"
                                                ${drOffline.needVoterType?"checked":""}/>
                                            </div>
                                        </div>
                                        <div id="voterTypeSelectDiv">
                                        <div class="form-group">
                                            <label class="col-xs-6 control-label"><span class="star">*</span>请选择填表人类别模板</label>
                                            <div class="col-xs-4">
                                                <select required data-rel="select2"
                                                data-width="160"
                                                name="voterTypeTplId" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach items="${tplMap}" var="entity">
                                                        <option value="${entity.key}">${entity.value.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#resultForm select[name=voterTypeTplId]").val(${drOffline.voterTypeTplId});
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group" style="padding: 5px 10px;" id="voterTypes">
                                            <c:import url="/drOffline_result_voterTypes?offlineId=${drOffline.id}"/>
                                        </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer center">
                                    <button id="resultBtn"
                                            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                                            class="btn btn-primary"><i class="fa fa-save"></i> 保存
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cadres">
                        <div class="widget-box" style="width: ${drOffline.needVoterType?750:450}px;">
                            <div class="widget-header">
                                <h4 class="widget-title">
                                    第二步：录入选票
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main" style="margin-bottom: 10px; height: 480px;" id="candidatesDiv">
                                    <c:import url="/drOffline_addCandidate?offlineId=${drOffline.id}"/>
                                </div>
                                <div class="modal-footer center">
                                    <c:if test="${drOffline.needVoterType}">
                                    <button class="downloadBtn btn btn-warning"
                                        data-url="${ctx}/drOffline_result_export?offlineId=${drOffline.id}">
                                        <i class="fa fa-file-excel-o"></i> 导出(分类统计)
                                    </button>
                                    &nbsp;
                                    </c:if>
                                    <button class="downloadBtn btn btn-success" ${empty drOffline.needVoterType?"disabled":""}
                                        data-url="${ctx}/drOffline_result_export?offlineId=${drOffline.id}&needVoterType=0">
                                        <i class="fa fa-file-excel-o"></i> 导出${drOffline.needVoterType?"(推荐总结果)":""}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script>

    function needVoterTypeChange(){
        if($("#resultForm input[name=needVoterType]").bootstrapSwitch("state")) {
           $("#voterTypeSelectDiv").show();
           $("#voterTypeSelectDiv select[name=voterTypeTplId]").attr("required", "required");
        }else {
            $("#voterTypeSelectDiv").hide();
            $("#voterTypeSelectDiv select[name=voterTypeTplId]").removeAttr("required");
        }
    }
    $('#resultForm input[name=needVoterType]').on('switchChange.bootstrapSwitch', function(event, state) {
        needVoterTypeChange();
    });
    needVoterTypeChange();

    $("#resultForm :checkbox").bootstrapSwitch();
    var $voterTypeTplIdSelect = $('#resultForm select[name=voterTypeTplId]').select2();
    $voterTypeTplIdSelect.on("change", function () {
        var voterTypeTplId = $(this).val();
        $("#voterTypes").load("${ctx}/drOffline_result_voterTypes?offlineId=${drOffline.id}&tplId="+ voterTypeTplId)
    });

    $("#resultBtn").click(function () {
        $("#resultForm").submit();
        return false;
    });
    $("#resultForm").validate({
        submitHandler: function (form) {
            var $btn = $("#resultBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.openView('${ctx}/drOffline_result?id=${param.id}')
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>