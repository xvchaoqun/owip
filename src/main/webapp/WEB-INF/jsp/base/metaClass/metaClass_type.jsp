<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <div class="buttons pull-right" style="margin-right: 20px">
        <button onclick="metaType_au()" class="btn btn-info">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button data-url="${ctx}/metaClass_type?id=${metaClass.id}&export=1"
                class="downloadBtn btn btn-success">
            <i class="fa fa-download"></i> 导出
        </button>
    </div>
    <h3>${metaClass.name}-元数据属性</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/metaClass_type"
         data-url-del="${ctx}/metaType_del"
         data-url-co="${ctx}/metaType_changeOrder?classId=${metaClass.id}"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-center table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th width="50">ID</th>
                    <th nowrap>名称</th>
                    <c:if test="${not empty metaClass.boolAttr}">
                        <th nowrap width="80">${metaClass.boolAttr}</th>
                    </c:if>
                    <shiro:hasPermission name="metaClass:viewAll">
                        <th nowrap>代码</th>
                        <c:if test="${not empty metaClass.extraAttr}">
                            <th>${metaClass.extraAttr}</th>
                        </c:if>
                        <th nowrap>备注</th>
                    </shiro:hasPermission>
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
                        <td>${metaType.id}</td>
                        <td style="text-align: left">${metaType.name}</td>
                        <c:if test="${not empty metaClass.boolAttr}">
                            <td nowrap>
                                    ${metaType.boolAttr?"是":"否"}
                            </td>
                        </c:if>
                        <shiro:hasPermission name="metaClass:viewAll">
                            <td nowrap>${metaType.code}</td>
                            <c:if test="${not empty metaClass.extraAttr}">
                                <td>
                                    <c:if test="${fn:length(metaClass.options)>0}">
                                        <c:set var="op" value="${metaClass.options.get(metaType.extraAttr)}"/>
                                        ${op.key}-${op.name}<c:if test="${not empty op.detail && !(op.detail eq op.name)}">|${op.detail}</c:if>
                                    </c:if>
                                    <c:if test="${fn:length(metaClass.options)==0}">
                                        ${metaType.extraAttr}
                                    </c:if>
                                </td>
                            </c:if>
                            <td>${metaType.remark}</td>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="metaType:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="javascript:;"
                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${metaType.id}" data-direction="1" title="上升"><i
                                            class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                           title="修改操作步长">
                                    <a href="javascript:;"
                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${metaType.id}" data-direction="-1"
                                       title="下降"><i class="fa fa-arrow-down"></i></a></td>
                                </td>
                            </c:if>
                        </shiro:hasPermission>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="metaType:edit">
                                    <button onclick="metaType_au(${metaType.id})" class="btn btn-primary btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="metaType:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${metaType.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown"
                                            data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <shiro:hasPermission name="metaType:del">
                                            <li>
                                                <a href="javascript:;" data-id="${metaType.id}"
                                                   class="delBtn tooltip-error"
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
                <wo:page commonList="${commonList}" uri="${ctx}/metaClass_type?id=${metaClass.id}"
                         target="#modal .modal-content"
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
    function metaType_au(id) {
        var url = "${ctx}/metaType_au?classId=${metaClass.id}&pageNo=${commonList.pageNo}";
        if (id > 0) url += "&id=" + id;
        $.loadModal(url);
    }
</script>