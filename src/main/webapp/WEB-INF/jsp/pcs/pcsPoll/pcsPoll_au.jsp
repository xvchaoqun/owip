<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pcsPoll!=null?'编辑':'创建'}党支部投票</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPoll_au" autocomplete="off" disableautocomplete id="pcsPollForm"
          method="post">
        <input type="hidden" name="id" value="${pcsPoll.id}">

        <c:if test="${empty pcsPoll}">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
                <div class="col-xs-6">
                    <select required class="form-control" data-rel="select2-ajax" data-callback="_selectPartyCallback"
                            data-ajax-url="${ctx}/party_selects?auth=1&pcsConfigId=${_pcsConfig.id}"
                            name="partyId" data-placeholder="请选择" data-width="270">
                        <option value="${pcsParty.partyId}">${pcsParty.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                <label class="col-xs-4 control-label"><span class="star">*</span> 所属党支部</label>
                <div class="col-xs-6">
                    <select required class="form-control" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/branch_selects?auth=1&pcsConfigId=${_pcsConfig.id}"
                            name="branchId" data-placeholder="请选择" data-width="270">
                        <option value="${pcsBranch.branchId}">${pcsBranch.name}</option>
                    </select>
                </div>
            </div>
            <c:set var="directBranchClassId" value='${cm:getMetaTypeByCode("mt_direct_branch").id}'/>
            <script>

                $.register.party_branch_select($("#pcsPollForm"), "branchDiv",
                    '${directBranchClassId}', "${pcsParty.partyId}",
                    "${pcsParty.isDirectBranch?directBranchClassId:''}", "partyId", "branchId", true);

                function _selectPartyCallback($select) {
                    var partyId = $select.val();
                    if (partyId == '') return;
                    $.get("${ctx}/pcs/pcsPoll_stage?partyId=" + partyId, function (ret) {
                        if (ret <= 0) {
                            $("#stage").html("<span class='red bolder'>请等待${_p_partyName}开启投票</span>");
                            $("#submitBtn").prop("disabled", true);
                            return;
                        }
                        $("#stage").html(_cMap.PCS_POLL_STAGE_MAP[ret])
                        $("#submitBtn").prop("disabled", false);
                    })
                }
            </script>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span> 投票阶段</label>
                <div class="col-xs-6 label-text">
                    <span id="stage">${PCS_POLL_STAGE_MAP.get(pcsPoll.stage)}</span>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty pcsPoll}">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
                <div class="col-xs-6 label-text">
                        ${pcsPoll.partyName}
                        <input type="hidden" name="partyId" value="${pcsPoll.partyId}">
                </div>
            </div>
            <c:if test="${not empty pcsPoll.branchName}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span> 所属党支部</label>
                    <div class="col-xs-6 label-text">
                            ${pcsPoll.branchName}
                        <input type="hidden" name="branchId" value="${pcsPoll.branchId}">
                    </div>
                </div>
            </c:if>
        </c:if>

        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 投票名称</label>
            <div class="col-xs-6">
				 <textarea required class="form-control noEnter" rows="2" maxlength="50"
                           name="name">${pcsPoll.name}</textarea>
            </div>
        </div>

        <%--<div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 代表推荐人数</label>
            <div class="col-xs-6">
                <input required style="width: 78px;" ${pcsPoll.hasReport||pcsPoll.inspectorFinishNum>0?"disabled":""} class="form-control digits" type="text"
                       name="prNum" value="${pcsPoll.prNum}" data-rule-min="0">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 党委委员推荐人数</label>
            <div class="col-xs-6">
                <input required style="width: 78px;" ${pcsPoll.hasReport||pcsPoll.inspectorFinishNum>0?"disabled":""} class="form-control digits" type="text"
                       name="dwNum" value="${pcsPoll.dwNum}" data-rule-min="0">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 纪委委员推荐人数</label>
            <div class="col-xs-6">
                <input required style="width: 78px;" ${pcsPoll.hasReport||pcsPoll.inspectorFinishNum>0?"disabled":""} class="form-control digits" type="text"
                       name="jwNum" value="${pcsPoll.jwNum}" data-rule-min="0">
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 投票起始时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 150px;">
                    <input required class="form-control datetime-picker"
                           name="startTime"
                           type="text"
                           value="${(cm:formatDate(pcsPoll.startTime,'yyyy-MM-dd HH:mm'))}"/>
                </div>
                <span id="tipSt" style="color: red;"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 投票截止时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 150px;">
                    <input required class="form-control datetime-picker"
                           name="endTime"
                           type="text"
                           value="${(cm:formatDate(pcsPoll.endTime,'yyyy-MM-dd HH:mm'))}"/>
                </div>
                <span id="tipEt" style="color: red;"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"> 备注</label>
            <div class="col-xs-6">
                         <textarea class="form-control noEnter" rows="2" maxlength="100"
                                   name="remark">${pcsPoll.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsPoll?'确定':'添加'}</button>
</div>
<script>

    //控制时间
    $('input[name=startTime]').focus(function () {
        $('#tipSt').text('')
    })
    $('input[name=endTime]').on('change', function () {
        var st = $('input[name=startTime]')
        var et = $('input[name=endTime]')
        //console.log(st.val())
        if (st.val() == undefined || st.val().length == 0) {
            et.val('')
            //console.log(et.val())
            $('#tipSt').text('请先填写投票起始时间')
        } else if (st.val() >= et.val()) {
            et.val('')
            $('#tipEt').text('截止时间应晚于起始时间')
        } else if (st.val() < et.val()) {
            $('#tipEt').text('')
        }
    })

    $("#submitBtn").click(function () {
        $("#pcsPollForm").submit();
        return false;
    });
    $("#pcsPollForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#pcsPollForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.datetime($('.datetime-picker'));
    $.register.date($('.date-picker'));
</script>