<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <a onclick="metaType_au()" class="btn btn-default pull-right" style="margin-right: 20px">添加</a>
    <h3>${metaClass.name}-元数据属性</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/metaClass_type"
         data-url-del="${ctx}/metaType_del"
         data-url-co="${ctx}/metaType_changeOrder?classId=${metaClass.id}"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <shiro:hasRole name="admin">
                    <th>代码</th>
                    <th>布尔属性</th>
                    <th>附加属性</th>
                    <th>备注</th>
                    </shiro:hasRole>
                    <shiro:hasPermission name="metaType:changeOrder">
                        <c:if test="${!_query && commonList.recNum>1}">
                            <th style="width: 50px">排序</th>
                        </c:if>
                    </shiro:hasPermission>
                    <th style="width: 120px"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${metaTypes}" var="metaType" varStatus="st">
                    <tr>
                        <td nowrap>${metaType.name}</td>
                        <shiro:hasRole name="admin">
                        <td nowrap>${metaType.code}</td>
                        <td nowrap>
                            <c:if test="${not empty metaType.boolAttr}">
                                ${metaType.boolAttr?"是":"否"}
                            </c:if>
                        </td>
                        <td nowrap>${metaType.extraAttr}</td>
                        <td>${metaType.remark}</td>
                        </shiro:hasRole>
                        <shiro:hasPermission name="metaType:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#"
                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${metaType.id}" data-direction="1" title="上升"><i
                                            class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                           title="修改操作步长">
                                    <a href="#"
                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${metaType.id}" data-direction="-1"
                                       title="下降"><i class="fa fa-arrow-down"></i></a></td>
                                </td>
                            </c:if>
                        </shiro:hasPermission>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="metaType:edit">
                                    <button onclick="metaType_au(${metaType.id})" class="btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="metaType:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${metaType.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown"
                                            data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <shiro:hasPermission name="metaType:del">
                                            <li>
                                                <a href="#" data-id="${metaType.id}" class="delBtn tooltip-error"
                                                   data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                        </shiro:hasPermission>
                                    </ul>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/metaClass_type?id=${metaClass.id}" target="#modal .modal-content"
                                 pageNum="5"
                                 model="3"/>
                    </c:if>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>

    function metaType_au(id){

        var url = "${ctx}/metaType_au?classId=${metaClass.id}&pageNo=${commonList.pageNo}";
        if(id>0) url += "&id="+id;

        loadModal(url);
    }

    $('#modalForm [data-rel="select2"]').select2();
   /* $("#modal .adminBtn").click(function () {

        $.post("${ctx}/metaType_admin", {id: $(this).data("id")}, function (data) {
            if (data.success) {
                pop_reload();
                SysMsg.success('操作成功。', '成功');
            }
        });
    })*/

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }, errorPlacement: function (error, element) {

        }, invalidHandler: function (form, validator) {
            //var errors = validator.numberOfInvalids();
            SysMsg.error("请选择用户和类别", '错误');
        }
    });
</script>