<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑候选人</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="drOnlinePost:*">
    <div>
        <form class="form-inline" action="${ctx}/dr/drOnlineCandidate_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
            <shiro:hasPermission name="drOnlinePost:*">
                <div class="form-group">
                    <input type="hidden" name="postId" value="${postId}">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/dr/dr_sysUser_selects"
                            name="userId" data-placeholder="请选择候选人">
                        <option></option>
                    </select>
                </div>
                <input type="button" id="submitBtn" class="btn btn-sm btn-primary" value="添加"/><br/>
            </shiro:hasPermission>
        </form>
    </div>
    <div class="space-6"></div>
    <span style="color: red">若候选人姓名相同，请编辑姓名，否则统计结果会有误。</span>
</shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/dr/drOnlineCandidate_page?postId=${postId}"
         data-url-del="${ctx}/dr/drOnlineCandidate_del"
         data-url-co="${ctx}/dr/candidate_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">工号</th>
                    <th class="col-xs-5">姓名</th>
                    <c:if test="${commonList.recNum>1}">
                        <th style="width: 50px">排序</th>
                    </c:if>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${candidates}" var="_candidate" varStatus="st">
                    <tr>
                        <td>${_candidate.user.code}</td>
                        <td nowrap>${_candidate.realname}</td>
                        <c:if test="${commonList.recNum>1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${_candidate.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${_candidate.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button onclick="candidateName_Edit(${_candidate.id})" class="btn btn-default btn-xs">
                                    <i class="fa fa-edit"></i> 编辑
                                </button>
                                <button class="delBtn btn btn-danger btn-xs" data-id="${_candidate.id}">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/dr/drOnlineCandidate_page" target="#modal .modal-content" pageNum="5"
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
    function candidateName_Edit(id){

        var url = "${ctx}/dr/drOnlineCandidate_au?pageNo=${commonList.pageNo}";
        if(id>0) url += "&id="+id;

        $.loadModal(url);
    }
    function openView(postId, pageNo){
        pageNo = pageNo||1;
        $.loadModal( "${ctx}/dr/drOnlineCandidate_page?postId="+postId + "&pageNo="+pageNo);
    }

    $("#submitBtn", "#modalForm").click(function(){$("#modalForm").submit();return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        pop_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $(".close").click(function () {
        $("#jqGrid2").trigger("reloadGrid");
    })
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#modalForm select[name=userId]'));
</script>