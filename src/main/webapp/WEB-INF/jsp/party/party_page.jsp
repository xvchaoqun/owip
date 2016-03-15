<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/party_au"
             data-url-page="${ctx}/party_page"
             data-url-del="${ctx}/party_del"
             data-url-bd="${ctx}/party_batchDel"
             data-url-co="${ctx}/party_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.name ||not empty param.unitId
            ||not empty param.classId ||not empty param.typeId ||not empty param.unitTypeId
            || not empty param.code}"/>
            <div class="widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <mytag:sort-form css="form-horizontal " id="searchForm">
                            <div class="row">
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">编号</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="code" type="text" value="${param.code}"   placeholder="请输入编号">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">名称</label>
                                        <div class="col-xs-6">
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"            placeholder="请输入名称">
                                        </div>
                                    </div>

                                </div>
                                <div class="col-xs-4">
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">所属单位</label>
                                        <div class="col-xs-6">
                                            <select name="unitId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${unitMap}" var="unit"> 
                                                    <option value="${unit.key}">${unit.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=unitId]").val('${param.unitId}');     </script>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">组织类别</label>
                                        <div class="col-xs-6">
                                            <select name="classId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${classMap}" var="cls"> 
                                                    <option value="${cls.key}">${cls.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=classId]").val('${param.classId}');     </script>
                                             
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">

                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">组织类型</label>
                                        <div class="col-xs-6">
                                            <select name="typeId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${typeMap}" var="type"> 
                                                    <option value="${type.key}">${type.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=typeId]").val('${param.typeId}');     </script>
                                             
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-xs-3 control-label">单位属性</label>
                                        <div class="col-xs-6">
                                            <select name="unitTypeId" data-rel="select2" data-placeholder="请选择"> 
                                                <option></option>
                                                  <c:forEach items="${unitTypeMap}" var="unitType"> 
                                                    <option value="${unitType.key}">${unitType.value.name}</option>
                                                      </c:forEach>  </select> 
                                            <script>         $("#searchForm select[name=unitTypeId]").val('${param.unitTypeId}');     </script>
                                        </div>
                                    </div>

                                </div>
                            </div>




                            <div class="clearfix form-actions center">
                                <a class="searchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </mytag:sort-form>
                    </div>
                </div>
            </div>

            <div class="buttons pull-right">
                <shiro:hasPermission name="party:edit">
                    <a class="editBtn btn btn-info btn-sm" data-width="900"><i class="fa fa-plus"></i> 添加</a>
                </shiro:hasPermission>
                <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    <shiro:hasPermission name="party:del">
                        <a class="batchDelBtn btn btn-danger btn-sm"><i class="fa fa-times"></i> 删除</a>
                    </shiro:hasPermission>
                </c:if>
            </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <div class="table-container">
                <table style="min-width: 1300px" class="overflow-y table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
                        <mytag:sort-th field="code">编号</mytag:sort-th>
							<th>名称</th>
							<th>所属单位</th>
							<th>党总支类别</th>
							<%--<th>组织类别</th>--%>
							<%--<th>所在单位属性</th>--%>
							<th>联系电话</th>
                        <shiro:hasPermission name="party:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${partys}" var="party" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${party.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td >${party.code}</td>
								<td >
                                <a href="javascript:;" class="openView" data-url="${ctx}/party_view?id=${party.id}">
                                        ${party.name}
                                </a>
								</td>

								<td>${unitMap.get(party.unitId).name}</td>
								<td>${classMap.get(party.classId).name}</td>
								<%--<td>${typeMap.get(party.typeId).name}</td>--%>
								<%--<td>${unitTypeMap.get(party.unitTypeId).name}</td>--%>
								<td>${party.phone}</td>
                            <shiro:hasPermission name="party:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${party.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${party.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td >
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="party:edit">
                                    <button data-id="${party.id}" data-width="900" class="editBtn btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>

                                    <c:if test="${cm:typeEqualsCode(party.classId,'mt_direct_branch')}">
                                        <shiro:hasPermission name="member:edit">
                                        <button data-url="${ctx}/member_au?partyId=${party.id}" class="openView btn btn-success btn-mini btn-xs">
                                            <i class="fa fa-user"></i> 添加党员
                                        </button>
                                        </shiro:hasPermission>
                                    </c:if>

                                    <shiro:hasPermission name="partyMemberGroup:edit">
                                        <button data-id="${party.id}" class="addPartyMemberGroupBtn btn btn-primary btn-mini btn-xs">
                                            <i class="fa fa-users"></i> 添加分党委班子
                                        </button>
                                    </shiro:hasPermission>
                                     <%--<shiro:hasPermission name="party:del">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${party.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>--%>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="party:edit">
                                            <li>
                                                <a href="#" data-id="${party.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="party:del">
                                            <li>
                                                <a href="#" data-id="${party.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/party_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>

<script>
    stickheader();
    $(".myTableDiv .addPartyMemberGroupBtn").click(function(){

        loadModal("${ctx}/partyMemberGroup_au?partyId="+$(this).data("id"));
    })

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>