<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${passportApply!=null}">修改</c:if><c:if test="${passportApply==null}">添加</c:if>证件使用记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/abroad/passportApply_au"
          autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${passportApply.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>证件号码</label>
            <c:if test="${passportApply!=null}">
                <div class="col-xs-6 label-text">
                    ${passportApply.code}
                </div>
            </c:if>
             <c:if test="${passportApply==null}">
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="passportCode" value="${passportApply.code}">
                <span class="help-block">注：请填写证件库中的证件号码</span>
            </div>
            </c:if>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>申请日期</label>
            <div class="col-xs-6">
                <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                    <input required class="form-control" name="applyDate" type="text"
                            value="${cm:formatDate(passportApply.applyDate,'yyyy.MM.dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">审批日期</label>
            <div class="col-xs-6">
                <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                    <input class="form-control" name="approveTime" type="text"
                            value="${cm:formatDate(passportApply.approveTime,'yyyy.MM.dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">审批人</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax"
                        data-width="273"
                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${approvalUser.id}">${approvalUser.realname}-${approvalUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">应交组织部日期</label>
            <div class="col-xs-6">
                <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                    <input class="form-control" name="expectDate" type="text"
                            value="${cm:formatDate(passportApply.expectDate,'yyyy.MM.dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>实交组织部日期</label>
            <div class="col-xs-6">
                <div class="input-group date" data-date-format="yyyy.mm.dd" style="width: 130px">
                    <input required class="form-control" name="handleDate" type="text"
                            value="${cm:formatDate(passportApply.handleDate,'yyyy.MM.dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件接收人</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax"
                        data-width="273"
                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                                    name="handleUserId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${handleUser.id}">${handleUser.realname}-${handleUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark" rows="2"
                          value="${passportApply.remark}"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${passportApply==null}">
    <span class="note">
          <ul>
              <li>添加后的记录，保存至【批准办理新证件（已交证件）】中</li>
          </ul>
      </span>
    </c:if>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${passportApply!=null}">确定</c:if><c:if test="${passportApply==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.register.date($('.input-group.date'));
    $('textarea.limited').inputlimiter();
</script>