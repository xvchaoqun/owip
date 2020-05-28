<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    // 初始化须放在页面最前面
    var selectedPostIds = [];
    <c:if test="${not empty unitPostGroup.postIds}">
    selectedPostIds = "${unitPostGroup.postIds}".split(",");
    </c:if>
    //var oldSelectedPostIds = selectedPostIds.slice();

    function initSelectedPostIds() {
        //console.log("selectedPostIds=" + selectedPostIds);
        $("input[name='unitPostId']", "#unitPostDiv").each(function () {
            var postId = $(this).val();
            if ($.inArray(postId, selectedPostIds)>=0) {
                this.checked = true;
            }
        })
    }
</script>
<div id="unitPostDiv">
    <c:import url="/unitPostGroup_table"/>
</div>
<script>
    function addPost(){
        $.post("${ctx}/unitPostGroup_addPost",{id:'${param.id}', postIds:selectedPostIds},function(ret){
            if(ret.success) {
                $("#jqGrid").trigger("reloadGrid");
                $("#unitPostDiv").load("${ctx}/unitPostGroup_addPost?id=${param.id}")
            }
        });
    }

    $(document).on("change", "#unitPostDiv input[name='unitPostId'], #unitPostDiv input[name='checkAll']", function(){

        $("input[name='unitPostId']", "#unitPostDiv").each(function () {
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
                if ($.inArray(postId, selectedPostIds)>=0) {
                    selectedPostIds.splice($.inArray(postId, selectedPostIds), postId.length);
                }

                $(this).closest("tr").removeClass("active");
            }
        });
    });

    function _tunePage_callback(html, options) {
        initSelectedPostIds();
    }
</script>