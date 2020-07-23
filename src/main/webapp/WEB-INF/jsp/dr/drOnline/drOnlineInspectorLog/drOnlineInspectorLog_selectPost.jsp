<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="doilDiv">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>限定推荐职务</h3>
    </div>
    <div class="modal-body">
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <c:if test="${edit==1}">
                    <th class="center"></th>
                </c:if>
                <th nowrap>职务名称</th>
                <th nowrap>关联岗位</th>
                <th nowrap>岗位编码</th>
                <th nowrap>岗位级别</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${edit==0?inspectorLog.drOnlinePost:drOnlinePosts}" var="post" varStatus="st">
                <tr>
                    <c:if test="${edit==1}">
                        <td class="center">
                            <label class="pos-rel">
                                <input type="checkbox" name="postId"
                                       value="${post.id}"
                                       class="ace"/>
                                <span class="lbl"></span>
                            </label>
                        </td>
                    </c:if>
                    <td nowrap>${post.name}</td>
                    <td nowrap>${post.unitPost.name}</td>
                    <td nowrap>${post.unitPost.code}</td>
                    <td nowrap>${cm:getMetaType(post.unitPost.adminLevel).name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${edit==0&&fn:length(inspectorLog.drOnlinePost)==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">无限制</h4>
            </div>
        </c:if>
    </div>
    <div class="modal-footer">

        <c:if test="${edit==1}">
                <button class="popupBtn btn btn-default"
                        data-url="/dr/drOnlineInspectorLog_selectPost?onlineId=${onlineId}&id=${param.id}"><i class="fa fa-reply"></i> 返回</button>
            <button onclick="selectPosts()" class="btn btn-success"><i class="fa fa-save"></i> 保存</button>
        </c:if>
        <c:if test="${edit==0}">
            <button onclick="editPosts()" class="btn btn-primary"><i class="fa fa-edit"></i> 编辑</button>
        </c:if>
    </div>
</div>
<script>
    var selectedPostIds = [];
    <c:if test="${not empty inspectorLog.drOnlinePost}">
        selectedPostIds = "${inspectorLog.postIds}".split(",");
    </c:if>

    initSelectedPosts();
    function initSelectedPosts() {
        $("input[name='postId']", "#doilDiv").each(function () {
            var postId = $(this).val();
            if ($.inArray(postId, selectedPostIds)>=0) {
                this.checked = true;
            }
        })
    }

    $(document).on("change", "#doilDiv input[name='postId']", function(){

        $("input[name='postId']", "#doilDiv").each(function () {
            var postId = $(this).val();
            if (this.checked) { //被选中的复选框
                if (selectedPostIds.toString() == "") {
                    selectedPostIds.push(postId);
                } else {  //判断id数组中是否含有以前存入的元素，没有则添加
                    if ($.inArray(postId, selectedPostIds)<0) {
                        selectedPostIds.push(postId);
                    }
                }
            } else {  //未被选中的复选框
                //console.log(postId)
                if ($.inArray(postId, selectedPostIds)>=0) {
                    selectedPostIds.splice($.inArray(postId, selectedPostIds), postId.length);
                }
                $(this).closest("tr").removeClass("active");
            }
        });
    });

    function editPosts(){
        $.loadModal("${ctx}/dr/drOnlineInspectorLog_selectPost?edit=1&onlineId=${onlineId}&id=${param.id}");
    }
    function selectPosts(){
        //console.log(selectedPostIds)
        $.post("${ctx}/dr/drOnlineInspectorLog_selectPost",{id:'${param.id}', postIds:selectedPostIds},function(ret){
            if(ret.success) {
                $("#jqGrid2").trigger("reloadGrid");
                $("#doilDiv").load("${ctx}/dr/drOnlineInspectorLog_selectPost?edit=0&id=${param.id}&onlineId=${onlineId}")
            }
        });
    }

</script>