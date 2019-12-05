<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${party!=null}">编辑</c:if><c:if test="${party==null}">添加</c:if>基层党组织</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/party_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <div class="row">
            <div class="col-xs-6">
                <input type="hidden" name="id" value="${party.id}">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>编号</label>
                    <div class="col-xs-8">
                        <input required class="form-control" type="text" name="code" value="${party.code}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control" name="name">${party.name}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">简称</label>
                    <div class="col-xs-8">
                        <input class="form-control" type="text" name="shortName" value="${party.shortName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">网址</label>
                    <div class="col-xs-8">
                        <input class="form-control url" type="text" name="url" value="${party.url}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-3 control-label">联系电话</label>
                    <div class="col-xs-8">
                        <input class="form-control" type="text" name="phone" value="${party.phone}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">传真</label>
                    <div class="col-xs-8">
                        <input class="form-control" type="text" name="fax" value="${party.fax}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">邮箱</label>
                    <div class="col-xs-8">
                        <input class="form-control email" type="text" name="email" value="${party.email}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">信箱</label>
                    <div class="col-xs-8">
                        <input class="form-control" type="text" name="mailbox" value="${party.mailbox}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>成立时间</label>
                    <div class="col-xs-8" style="width: 180px">
                        <div class="input-group date" data-date-format="yyyy.mm.dd">
                            <input required class="form-control date-picker" name="foundTime" type="text"
                                   placeholder="yyyy.mm.dd"
                                   value="${cm:formatDate(party.foundTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>所在单位</label>
                    <div class="col-xs-8">
                        <c:set var="unit" value="${cm:getUnitById(party.unitId)}"/>
                        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                data-width="273"
                                name="unitId" data-placeholder="请选择">
                            <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>党总支类别</label>
                    <div class="col-xs-8">
                        <select required class="form-control" name="classId" data-rel="select2"
                                data-placeholder="请选择分类">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_party_class"/>
                        </select>
                        <script>
                            $("#modalForm select[name=classId]").val('${party.classId}');
                        </script>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>组织类型</label>
                    <div class="col-xs-8">
                        <select required data-rel="select2" name="typeId" data-placeholder="请选择组织类型">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_party_type"/>
                        </select>
                        <script>
                            $("#modalForm select[name=typeId]").val('${party.typeId}');
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>所在单位属性</label>
                    <div class="col-xs-8">
                        <select required class="form-control" name="unitTypeId" data-rel="select2"
                                data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_party_unit_type"/>
                        </select>
                        <script>
                            $("#modalForm select[name=unitTypeId]").val('${party.unitTypeId}');
                        </script>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-6 control-label">所在单位是否独立法人</label>
                    <div class="col-xs-6">
                        <input name="isSeparate" ${party.isSeparate?"checked":""} type="checkbox"/>
                    </div>
                </div>
                <div class="form-group enterprise">
                    <label class="col-xs-6 control-label">是否大中型</label>
                    <div class="col-xs-6">
                        <input name="isEnterpriseBig" ${party.isEnterpriseBig?"checked":""} type="checkbox"/>
                    </div>
                </div>
                <div class="form-group enterprise">
                    <label class="col-xs-6 control-label">是否国有独资</label>
                    <div class="col-xs-6">
                        <input name="isEnterpriseNationalized" ${party.isEnterpriseNationalized?"checked":""}
                               type="checkbox"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-6 control-label">是否培育创建单位</label>
                    <div class="col-xs-6">
                        <input name="isPycj" ${party.isPycj?"checked":""}
                               type="checkbox"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-6 control-label">评选培育创建单位时间</label>
                    <div class="col-xs-6" style="width: 180px">
                        <div class="input-group date" data-date-format="yyyy.mm.dd">
                            <input class="form-control date-picker" name="pycjDate" type="text"
                                   placeholder="yyyy.mm.dd"
                                   value="${cm:formatDate(party.pycjDate,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-6 control-label">是否标杆院系</label>
                    <div class="col-xs-6">
                        <input name="isBg" ${party.isBg?"checked":""}
                               type="checkbox"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-6 control-label">评选标杆院系时间</label>
                    <div class="col-xs-6" style="width: 180px">
                        <div class="input-group date" data-date-format="yyyy.mm.dd">
                            <input class="form-control date-picker" name="bgDate" type="text"
                                   placeholder="yyyy.mm.dd"
                                   value="${cm:formatDate(party.bgDate,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${party!=null?'确定':'添加'}</button>
</div>
<style>
    .enterprise {
        display: none;
    }
</style>
<script>
    $("#modal :checkbox").bootstrapSwitch();
    $.register.date($('.input-group.date'));
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.reloadMetaData(function () {
                            $("#modal").modal("hide")
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();

    $('#modalForm select[name=unitTypeId]').change(function () {
        if ($(this).val() == '${cm:getMetaTypeByCode('mt_party_unit_type_enterprise').id}') {
            $(".enterprise").show();
        } else {
            $(".enterprise :checkbox").prop("checked", false);
            $(".enterprise").hide();
        }
    }).change();

    $.register.del_select($('#modalForm select[name=unitId]'));
    $('[data-rel="tooltip"]').tooltip();
</script>