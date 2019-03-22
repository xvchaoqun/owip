<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchCadre!=null}">编辑</c:if><c:if test="${dispatchCadre==null}">添加</c:if>干部任免</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchCadre_au" autocomplete="off" disableautocomplete id="modalForm" method="post">

        <div class="row">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label">所属发文</label>
                    <div class="col-xs-6">
                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
                                name="dispatchId" data-placeholder="请选择发文">
                            <option value="${dispatchCadre.dispatch.id}"> ${dispatchCadre.dispatch.dispatchCode}</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label">类别</label>
                    <div class="col-xs-6">
                        <c:forEach var="DISPATCH_CADRE_TYPE" items="${DISPATCH_CADRE_TYPE_MAP}">
                            <label class="label-text">
                                <input name="type" type="radio" class="ace" value="${DISPATCH_CADRE_TYPE.key}"
                                       <c:if test="${dispatchCadre.type==DISPATCH_CADRE_TYPE.key}">checked</c:if>/>
                                <span class="lbl"> ${DISPATCH_CADRE_TYPE.value}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label">关联岗位</label>
                    <div class="col-xs-6">
                        <select data-ajax-url="${ctx}/unitPost_selects" data-width="545"
                                name="unitPostId" data-placeholder="请选择">
                            <option value="${unitPost.id}">${unitPost.name}-${unitPost.job}-${unitPost.unitName}</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-6">
        <input type="hidden" name="id" value="${dispatchCadre.id}">
                <div class="form-group">
                    <label class="col-xs-3 control-label" id="typeNameTd"><span class="star">*</span>任命职务</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control noEnter" name="post" rows="2">${dispatchCadre.post}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>干部类型</label>
                    <div class="col-xs-6">
                        <select required data-rel="select2" name="cadreTypeId" data-placeholder="请选择干部类型">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_dispatch_cadre_type"/>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=cadreTypeId]").val('${dispatchCadre.cadreTypeId}');
                        </script>
                    </div>
                </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免方式</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="wayId" data-placeholder="请选择任免方式">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_dispatch_cadre_way"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=wayId]").val('${dispatchCadre.wayId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任免程序</label>
				<div class="col-xs-6">
                    <select class="form-control" data-rel="select2" name="procedureId" data-placeholder="请选择任免程序">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_dispatch_cadre_procedure"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=procedureId]").val('${dispatchCadre.procedureId}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">工作证号</label>
				<div class="col-xs-8">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
                            name="cadreId" data-placeholder="请选择干部">
                        <option value="${dispatchCadre.cadre.id}">${dispatchCadre.cadre.code}</option>
                    </select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-8">
                        <input disabled class="form-control" type="text" name="_name" value="${dispatchCadre.cadre.realname}">
				</div>
			</div>
            </div>
            <div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                    <select name="postType" data-rel="select2" data-placeholder="请选择职务属性">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post"/>
                    </select>
                    <script>
                        $("#modalForm select[name=postType]").val('${dispatchCadre.postType}');
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
				<div class="col-xs-6">
                    <select required class="form-control" data-rel="select2" name="adminLevel" data-placeholder="请选择行政级别">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=adminLevel]").val('${dispatchCadre.adminLevel}');
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>单位类别</label>
                <div class="col-xs-6">
                    <select required class="form-control" name="_unitStatus" data-rel="select2" data-placeholder="请选择单位类别">
                        <option></option>
                        <option value="1" ${dispatchCadre.unit.status==1?"selected":""}>正在运转单位</option>
                        <option value="2" ${dispatchCadre.unit.status==2?"selected":""}>历史单位</option>
                    </select>
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属单位</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                            name="unitId" data-placeholder="请选择单位">
                        <option value="${dispatchCadre.unit.id}">${dispatchCadre.unit.name}</option>
                    </select>
				</div>
			</div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">单位类型</label>
                    <div class="col-xs-8">
                        <input class="form-control" name="_unitType" type="text" disabled value="${dispatchCadre.unit.unitType.name}">
                    </div>
                </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-8">
                        <textarea class="form-control limited" name="remark" rows="2">${dispatchCadre.remark}</textarea>
				</div>
			</div>
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchCadre!=null}">确定</c:if><c:if test="${dispatchCadre==null}">添加</c:if>"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.ajax_select($('#modalForm select[name=dispatchId][data-rel="select2-ajax"]'));

    $("#modalForm input[name=type]").change(function () {

        //console.log("$(this).val()=" + $(this).val())
        if ($(this).val() == '<%=DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT%>') {
            $("#typeNameTd").html("任命职务");
        } else {
            $("#typeNameTd").html("免去职务");
        }
    })

    $.register.ajax_select($('#modalForm select[name=unitPostId]'), function (state) {
        var $state = state.text;
        if(state.up!=undefined){
            if ($.trim(state.up.job)!='')
                $state += "-" + state.up.job;
            if ($.trim(state.up.unitName)!='')
                $state += "-" + state.up.unitName;
        }
        return $state;
    }).on("change", function () {
        //console.log($(this).select2("data")[0])
        var up = $(this).select2("data")[0]['up'] ;
        //console.log(up)
        if(up!=undefined){
            if(up.postClass=='${cm:getMetaTypeByCode("mt_post_dw").id}'){
                $("#modalForm select[name=cadreTypeId]").val('${cm:getMetaTypeByCode("mt_dispatch_cadre_dw").id}').trigger("change")
            }else if(up.postClass=='${cm:getMetaTypeByCode("mt_post_xz").id}'){
                $("#modalForm select[name=cadreTypeId]").val('${cm:getMetaTypeByCode("mt_dispatch_cadre_xz").id}').trigger("change")
            }

            $('#modalForm textarea[name=post]').val(up.name)
            $("#modalForm select[name=postType]").val(up.postType).trigger("change");
            $("#modalForm select[name=adminLevel]").val(up.adminLevel).trigger("change");
            $("#modalForm select[name=_unitStatus]").val(up.unitStatus).trigger("change");

            var option = new Option(up.unitName, up.unitId, true, true);
            $("#modalForm select[name=unitId]").append(option).trigger('change');


            $('#modalForm input[name=_unitType]').val(_cMap.metaTypeMap[up.unitTypeId].name)
        }
    });

    var $selectCadre = $.register.user_select($('#modalForm select[name=cadreId]'),function(state){ var $state = state.text;
        if(state.code!=undefined && state.code.length>0)
            $state = state.code;
        return $state;
    });
    $selectCadre.on("change",function(){
        var name = $(this).select2("data")[0]['text']||'';
        $('#modalForm input[name=_name]').val(name);
    });

    $.register.unit_select($('#modalForm select[name=_unitStatus]'), $('#modalForm select[name=unitId]'), $('#modalForm input[name=_unitType]'));
</script>