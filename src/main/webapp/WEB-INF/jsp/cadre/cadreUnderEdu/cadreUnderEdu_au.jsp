<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreUnderEdu!=null}">编辑</c:if><c:if test="${cadreUnderEdu==null}">添加</c:if>在读学习经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreUnderEdu_au?cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreUnderEdu.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>在读学历</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="eduId" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_edu"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=eduId]").val(${cadreUnderEdu.eduId});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>在读学校</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="school" value="${cadreUnderEdu.school}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>院系</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="dep" value="${cadreUnderEdu.dep}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>所学专业</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="major" value="${cadreUnderEdu.major}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>学校类型</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="schoolType" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="<%=CadreConstants.CADRE_SCHOOL_TYPE_MAP%>" var="schoolType">
                        <option value="${schoolType.key}">${schoolType.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=schoolType]").val(${cadreUnderEdu.schoolType});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>入学时间</label>

            <div class="col-xs-6">
                <div class="input-group" style="width:200px">
                    <input required class="form-control date-picker" name="_enrolTime" type="text"
                           data-date-min-view-mode="1"
                           data-date-format="yyyy.mm" value="${cm:formatDate(cadreUnderEdu.enrolTime,'yyyy.MM')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>学习方式</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="learnStyle" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_learn_style"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=learnStyle]").val(${cadreUnderEdu.learnStyle});
                </script>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control" name="remark" rows="5">${cadreUnderEdu.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cadreUnderEdu!=null}">确定</c:if><c:if test="${cadreUnderEdu==null}">添加</c:if>"/>
</div>

<script>

    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreUnderEdu").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>