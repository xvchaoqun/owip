<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <a onclick="drVoterType_au()" class="btn btn-default pull-right" style="margin-right: 20px">添加</a>
    <h3>${drVoterTypeTpl.name}-填表人类型</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/drVoterTypeTpl_type"
         data-url-del="${ctx}/drVoterType_del"
         data-url-co="${ctx}/drVoterType_changeOrder?tplId=${drVoterTypeTpl.id}"
         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-center table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th nowrap width="120">类别</th>
                    <c:if test="${!_query && commonList.recNum>1}">
                        <th style="width: 50px">排序</th>
                    </c:if>
                    <th nowrap>备注</th>
                    <th style="width: 120px"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${drVoterTypes}" var="drVoterType" varStatus="st">
                    <tr>
                        <td nowrap>${drVoterType.name}</td>
                        <shiro:hasPermission name="drVoterTypeTpl:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="javascript:;"
                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${drVoterType.id}" data-direction="1" title="上升"><i
                                            class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                           title="修改操作步长">
                                    <a href="javascript:;"
                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${drVoterType.id}" data-direction="-1"
                                       title="下降"><i class="fa fa-arrow-down"></i></a></td>
                                </td>
                            </c:if>
                        </shiro:hasPermission>
                        <td>${drVoterType.remark}</td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="drVoterTypeTpl:edit">
                                    <button onclick="drVoterType_au(${drVoterType.id})" class="btn btn-default btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="drVoterTypeTpl:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${drVoterType.id}">
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
                                        <shiro:hasPermission name="drVoterTypeTpl:del">
                                            <li>
                                                <a href="javascript:;" data-id="${drVoterType.id}" class="delBtn tooltip-error"
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
                        <wo:page commonList="${commonList}" uri="${ctx}/drVoterTypeTpl_type?id=${drVoterTypeTpl.id}" target="#modal .modal-content"
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
    function drVoterType_au(id){

        var url = "${ctx}/drVoterType_au?tplId=${drVoterTypeTpl.id}&pageNo=${commonList.pageNo}";
        if(id>0) url += "&id="+id;

        $.loadModal(url);
    }
</script>