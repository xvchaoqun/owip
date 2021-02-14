<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 900px">
<h3>${dpMember!=null?'修改党派成员信息':'添加党派成员信息'}</h3>
<hr/>
<form class="form-horizontal" action="${ctx}dp/dpMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
    <div class="row">
        <div class="col-xs-7">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>账号</label>
                <div class="col-xs-6">
                    <c:if test="${not empty dpMember}">
                        <input type="hidden" value="${dpMember.userId}" name="userId">
                    </c:if>
                    <select ${not empty dpMember?"disabled data-theme='default'":""} required data-rel="select2-ajax"
                                                                                   data-ajax-url="${ctx}/dp/notDpMember_selects"
                                                                                   name="userId" data-width="270"
                                                                                   data-placeholder="请输入账号或姓名或工作证号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属党派</label>
                <c:if test="${empty dpParty}">
                    <div class="col-xs-6">
                        <select required data-rel="select2-ajax" data-width="270"
                                data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                name="partyId" data-placeholder="请选择">
                            <option></option>
                        </select>
                    </div>
                </c:if>
                <c:if test="${not empty dpParty}">
                    <div class="col-xs-6">
                        <select required data-rel="select2-ajax" data-width="270"
                                data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                name="partyId">
                            <option value="${dpParty.id}">${dpParty.name}</option>
                        </select>
                    </div>
                </c:if>
            </div>


            <div class="form-group">
                <label class="col-xs-4 control-label">加入党派时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 272px">
                        <input class="form-control date-picker" name="_dpGrowTime" type="text"
                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(dpMember.dpGrowTime,'yyyy.MM.dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">党派内职务</label>
                <div class="col-xs-6" style="width: 296px">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="dpPost" rows="2">${dpMember.dpPost}</textarea>
                </div>
            </div>
            <%--<div class="form-group">
                <label class="col-xs-4 control-label">兼职(其他校外职务）</label>
                <div class="col-xs-6" style="width: 296px">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="partTimeJob" rows="2">${dpMember.partTimeJob}</textarea>
                </div>
            </div>--%>
            <div class="form-group">
                <label class="col-xs-4 control-label">通讯地址</label>
                <div class="col-xs-6" style="width: 296px">
                    <input class="form-control" type="text" name="address" value="${dpMember.address}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">手机号</label>
                <div class="col-xs-6" style="width: 296px">
                    <input class="form-control" type="text" name="mobile" value="${dpMember.mobile}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">邮箱</label>
                <div class="col-xs-6" style="width: 296px">
                    <input class="form-control" type="text" name="email" value="${dpMember.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">备注</label>
                <div class="col-xs-6">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="remark" rows="3">${dpMember.remark}</textarea>
                </div>
            </div>
        </div>
        <div class="col-xs-5">
            <%--<div class="form-group">
                <label class="col-xs-3 control-label">培训情况</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"  maxlength="50"
                                  name="trainState" rows="3">${dpMember.trainState}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">政治表现</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"  maxlength="50"
                                  name="politicalAct" rows="3">${dpMember.politicalAct}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="partyReward" rows="3">${dpMember.partyReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">其他奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="otherReward" rows="3">${dpMember.otherReward}</textarea>
                </div>
            </div>--%>
        </div>
    </div>
</form>
<div class="clearfix form-actions center" style="width: 70%">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
</div>
</div>
<script>
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'), {endDate: '${_today}'});

    $("#body-content-view button[type=submit]").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                        //});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));

    $.register.user_select($('#modalForm select[name=userId]'));
</script>