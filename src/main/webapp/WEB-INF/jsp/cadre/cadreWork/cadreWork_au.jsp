<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <c:if test="${empty param.fid}">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    </c:if>
    <c:if test="${not empty param.fid}">
    <button type="button" onclick="showSubWork(${param.fid})" aria-hidden="true" class="close">&times;</button>
    </c:if>
    <h3><c:if test="${cadreWork!=null}">编辑</c:if><c:if test="${cadreWork==null}">添加</c:if><c:if test="${not empty param.fid}">期间</c:if>工作经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreWork_au" id="modalForm" method="post">
            <input type="hidden" name="id" value="${cadreWork.id}">
            <c:if test="${not empty cadreWork.fid}">
            <input  type="hidden" name="fid" value="${cadreWork.fid}">
            </c:if>
            <c:if test="${empty cadreWork.fid}">
            <input  type="hidden" name="fid" value="${param.fid}">
             </c:if>
                <c:if test="${not empty param.fid}">
                    <input  type="hidden" name="cadreId" value="${param.cadreId}">
                </c:if>
            <c:if test="${empty param.fid}">
			<div class="form-group">
				<label class="col-xs-4 control-label">所属干部</label>
				<div class="col-xs-6">
                    <input  type="hidden" name="cadreId" value="${cadre.id}">
                    <input type="text" value="${sysUser.realname}" disabled>
				</div>
			</div>
                </c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label">开始日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreWork.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">结束日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_endTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreWork.endTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">工作单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreWork.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">担任职务或者专技职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${cadreWork.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="typeId" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=typeId]").val(${cadreWork.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">院系/机关工作经历</label>
				<div class="col-xs-6">
                    <label>
                        <input required name="workType" type="radio" class="ace" value="1"
                               <c:if test="${cadreWork.workType==1}">checked</c:if>/>
                        <span class="lbl"> 院系工作经历</span>
                    </label>
                    <label>
                        <input name="workType" type="radio" class="ace" value="2"
                               <c:if test="${cadreWork.workType==2}">checked</c:if>/>
                        <span class="lbl"> 机关工作经历</span>
                    </label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control" type="text" name="remark" >${cadreWork.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${empty param.fid}">
        <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    </c:if>
    <c:if test="${not empty param.fid}">
        <a href="#" onclick="showSubWork(${param.fid})" class="btn btn-default">取消</a>
    </c:if>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreWork!=null}">确定</c:if><c:if test="${cadreWork==null}">添加</c:if>"/>
</div>

<script>

    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
    
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        <c:if test="${empty param.fid}">
                        _reload();
                        </c:if>
                        <c:if test="${not empty param.fid}">
                        showSubWork("${param.fid}");
                        </c:if>

                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
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