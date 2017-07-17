<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        <c:if test="${param.op=='back'}">证件找回</c:if>
        <c:if test="${param.op!='back'}">
            <c:if test="${not empty param.applyId}">新办理的证件集中保管</c:if>
            <c:if test="${empty param.applyId}">
            <c:if test="${passport!=null}">编辑</c:if><c:if test="${passport==null}">添加</c:if>
            ${type==PASSPORT_TYPE_LOST?"丢失":""}证件信息
            </c:if>
        </c:if>
    </h3>
</div>
<c:set var="today" value='<%=DateUtils.getCurrentDateTime("yyyy-MM-dd")%>'/>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/passport_au" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${passport.id}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="op" value="${param.op}">
        <c:if test="${not empty param.applyId}">
            <input type="hidden" name="cadreId" value="${cadre.id}">
        </c:if>
    <c:if test="${empty param.applyId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">干部</label>
				<div class="col-xs-6">
                    <select required ${not empty param.applyId?"disabled":""} data-rel="select2-ajax"
                            data-ajax-url="${ctx}/cadre_selects?type=0"
                            name="cadreId" data-placeholder="请选择干部">
                        <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
    </c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">证件名称</label>
				<div class="col-xs-6">
                    <c:if test="${not empty param.applyId}">
                        <input type="hidden" name="classId" value="${passport.classId}">
                    </c:if>
                    <select required ${not empty param.applyId?"disabled":""}  data-rel="select2" name="classId" data-placeholder="请选择证件名称">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_passport_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=classId]").val(${passport.classId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">证件号码</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="code" value="${passport.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发证机关</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="authority"
                               value="${empty passport?"公安部出入境管理局":passport.authority}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发证日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_issueDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">有效期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_expiryDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>

            <c:if test="${param.op=='back' || type==PASSPORT_TYPE_KEEP ||
            (type==PASSPORT_TYPE_LOST && passport.lostType==PASSPORT_LOST_TYPE_TRANSFER)}">
			<div class="form-group">
				<label class="col-xs-3 control-label">集中保管日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_keepDate" type="text"
                               data-date-format="yyyy-mm-dd" value="${param.op=='back'?today:cm:formatDate(passport.keepDate,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
             </c:if>
            <c:if test="${param.op=='back' || type!=PASSPORT_TYPE_LOST}">
			<div class="form-group">
				<label class="col-xs-3 control-label">存放保险柜</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="safeBoxId" data-placeholder="保险柜">
                        <option></option>
                        <c:forEach items="${safeBoxMap}" var="safeBox">
                            <option value="${safeBox.key}">${safeBox.value.code}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=safeBoxId]").val(${passport.safeBoxId});
                    </script>
				</div>
			</div>
        </c:if>
        <c:if test="${param.op!='back' && type==PASSPORT_TYPE_LOST}">
            <div class="form-group">
                <label class="col-xs-3 control-label">登记丢失日期</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_lostTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${passport==null?today:cm:formatDate(passport.lostTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">丢失证明</label>
                <div class="col-xs-6">
                    <input  class="form-control" type="file" name="_lostProof" />
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="${param.op=='back'?'找回':(passport!=null?'确定':'添加')}"/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                            //console.log("成功：left:{0}, top:{1}".format(_left, _top))
                            $("#jqGrid").trigger("reloadGrid");
                            <c:if test="${not empty param.applyId}">
                            page_reload();
                            </c:if>
                        //});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_date($('.date-picker'))
    register_user_select($('[data-rel="select2-ajax"]'));

    $.fileInput($('#modalForm input[type=file]'))
</script>