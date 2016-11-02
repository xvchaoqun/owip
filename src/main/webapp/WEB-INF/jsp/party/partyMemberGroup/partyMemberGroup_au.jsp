<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${partyMemberGroup!=null}">编辑</c:if><c:if test="${partyMemberGroup==null}">添加</c:if>基层党组织领导班子</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/partyMemberGroup_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${partyMemberGroup.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党总支</label>
				<div class="col-xs-6">
                    <div class="label-text">
                        <input type="hidden" name="partyId" value="${party.id}">
                    ${party.name}
                    </div>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">上一届班子</label>
                <div class="col-xs-6">
                    <div class="help-block">
                        <select class="form-control" name="fid"
                                data-rel="select2-ajax" data-ajax-url="${ctx}/partyMemberGroup_selects?partyId=${party.id}"
                                data-placeholder="请选择班子">
                            <option value="${fPartyMemberGroup.id}">${fPartyMemberGroup.name}</option>
                        </select>
                    </div>
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${partyMemberGroup.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否现任班子</label>
				<div class="col-xs-8">
                    <div class="col-xs-3">
                        <label>
                            <input name="isPresent" ${partyMemberGroup.isPresent?"checked":""}  type="checkbox" />
                            <span class="lbl"></span>
                        </label>
                    </div>
                    <div class="col-xs-offset-3">（提示：每个分党委的“现任班子”只有一个）</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">应换届时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_tranTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(partyMemberGroup.tranTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实际换届时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_actualTranTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(partyMemberGroup.actualTranTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任命时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_appointTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(partyMemberGroup.appointTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchUnit_selects?unitId=${party.unitId}"
                            name="dispatchUnitId" data-placeholder="请选择单位发文">
                        <option value="${partyMemberGroup.dispatchUnitId}">${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year )}</option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${partyMemberGroup!=null}">确定</c:if><c:if test="${partyMemberGroup==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm :checkbox").bootstrapSwitch();
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        if("${param.type}"=="view"){
                            _reload();
                            //SysMsg.success('操作成功。', '成功');
                        }else {
                            $("#modal").modal("hide")
                           // SysMsg.success('提交成功。', '成功',function(){
                                $("#jqGrid").trigger("reloadGrid");
                                $(".closeView").click();
                          //  });
                        }
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('[data-rel="select2-ajax"]').select2({
        width:300,
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>