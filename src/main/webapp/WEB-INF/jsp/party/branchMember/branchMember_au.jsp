<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${branchMember!=null}">编辑</c:if><c:if test="${branchMember==null}">添加</c:if></h3>
</div>
<div class="modal-body overflow-visible">
    <form class="form-horizontal" action="${ctx}/branchMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="groupId" value="${groupId}">
        <input type="hidden" name="id" value="${branchMember.id}">
        <c:set var="sysUser" value="${cm:getUserById(branchMember.userId)}"/>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
                <c:if test="${branchMember==null}">
                    <div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/sysUser_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.username}</option>
                    </select>
                    </div>
                </c:if>
                <c:if test="${branchMember!=null}">
                    <div class="col-xs-6 label-text">
                        <input type="hidden" name="userId" value="${sysUser.id}">
                    ${sysUser.realname}(${sysUser.username})
                    </div>
                </c:if>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>选择职务</label>
            <div class="col-xs-6">
                <select required class="multiselect" multiple="" data-width="272" name="types">
                    <jsp:include page="/metaTypes?__code=mc_branch_member_type"/>
                </select>
                <script>

                    $.register.multiselect($('#modalForm select[name=types]'), '${branchMember.types}'.split(","));

                </script>
            </div>
        </div>
        <div class="form-group" id="isDoubleLeader" style="display: none">
            <label class="col-xs-3 control-label">是否双带头人</label>
            <div class="col-xs-6">
                <label>
                    <input name="isDoubleLeader" ${branchMember.isDoubleLeader?"checked":""}  type="checkbox" />
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任职时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker"  name="assignDate" type="text"
                           placeholder="yyyy.mm" data-date-min-view-mode="1" data-date-format="yyyy.mm"
                           value="${cm:formatDate(branchMember.assignDate,'yyyy.MM')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <c:if test="${branchMember.isHistory}">
          <div class="form-group">
            <label class="col-xs-3 control-label">离任时间</label>
            <div class="col-xs-6">
              <div class="input-group">
                <input class="form-control date-picker" name="dismissDate" type="text"
                       data-date-min-view-mode="1" placeholder="yyyy.mm"
                       data-date-format="yyyy.mm" value="${cm:formatDate(branchMember.dismissDate,'yyyy.MM')}" />
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
              </div>
            </div>
          </div>
          </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">办公电话</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="officePhone" value="${branchMember.officePhone}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号</label>

            <div class="col-xs-6">
                <input class="form-control mobile" type="text" name="mobile" value="${branchMember.mobile}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${branchMember!=null}">确定</c:if><c:if test="${branchMember==null}">添加</c:if>"/>
</div>

<script>

    $("#modalForm input[name=isDoubleLeader]").bootstrapSwitch();
    $.register.date($('.date-picker'));

   $("#modalForm select[name=types]").change(function(){
       var isShow=false;
       $("#modalForm select[name=types] option:selected").each(function (){
           var typeId=$(this).val();
           if(typeId=='${cm:getMetaTypeByCode("mt_branch_secretary").id}'){
               isShow=true;
           }
       });
       if(isShow){
           $("#isDoubleLeader").show();
           $("#modalForm input[name=isDoubleLeader]").removeAttr("disabled");
       }else{
           $("#isDoubleLeader").hide();
           $("#modalForm input[name=isDoubleLeader]").attr("disabled","disabled");
       }
    }).change();

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide")
                        var jqGridId = '${param.gridId}'||'jqGrid';
                        $("#"+jqGridId).trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.user_select($('#modalForm select[name=userId]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>