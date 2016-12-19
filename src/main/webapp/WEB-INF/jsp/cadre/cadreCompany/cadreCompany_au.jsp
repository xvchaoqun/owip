<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreCompany!=null}">编辑</c:if><c:if test="${cadreCompany==null}">添加</c:if>干部企业兼职情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCompany_au?cadreId=${cadre.id}" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${cadreCompany.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">兼职类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="type"
                        data-placeholder="请选择" data-width="162">
                    <option></option>
                    <c:forEach items="${CADRE_COMPANY_TYPE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=type]").val(${cadreCompany.type});
                </script>
            </div>
        </div>
        <div class="form-group" id="typeOtherDiv">
            <label class="col-xs-3 control-label">其他兼职类型</label>
            <div class="col-xs-6">
                <input  type="text" name="typeOther" placeholder="请输入其他兼职类型">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">是否取酬</label>
            <div class="col-xs-6">
                <label>
                    <input name="hasPay" ${cadreCompany.hasPay?"checked":""}  type="checkbox" />
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼职起始时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreCompany.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">兼职单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreCompany.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">报批单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reportUnit" value="${cadreCompany.reportUnit}">
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">批复文件</label>
                <div class="col-xs-6">
                    <input class="form-control" type="file" name="_paper" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-6">
                    <textarea class="form-control" name="remark">${cadreCompany.remark}</textarea>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreCompany!=null}">确定</c:if><c:if test="${cadreCompany==null}">添加</c:if>"/>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreCompany").trigger("reloadGrid");
                    }
                }
            });
        }
    });

    function typeChange(){

        if($("#modalForm select[name=type]").val() == '${CADRE_COMPANY_TYPE_OTHER}'){
            $("#typeOtherDiv").show();
            $("#modalForm input[name=typeOther]").attr("required", "required");
        }else{
            $("#typeOtherDiv").hide();
            $("#modalForm input[name=typeOther]").removeAttr("required");
        }
    }
    $("#modalForm select[name=type]").change(function(){
        typeChange();
    });
    typeChange();

    $('#modalForm input[type=file]').ace_file_input({
        no_file:'请选择文件 ...',
        btn_choose:'选择',
        btn_change:'更改',
        droppable:false,
        onchange:null,
        thumbnail:false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>