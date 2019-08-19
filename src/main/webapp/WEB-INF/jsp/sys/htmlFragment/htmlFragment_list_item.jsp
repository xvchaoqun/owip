<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box myTableDiv" data-cls="${htmlFragment.code}" style="width:828px">
  <div class="widget-header">
    <h4 class="widget-title">
      ${htmlFragment.title}
    </h4>
  </div>
  <div class="widget-body">
    <div class="widget-main">
        <form class="form-horizontal" action="${ctx}/htmlFragment_list_item" id="modalForm-${htmlFragment.id}" method="post">
            <input type="hidden" name="id" value="${htmlFragment.id}">
            <input type="hidden" name="cls" value="${htmlFragment.code}">
            <div class="form-group">
                <div class="col-xs-6">
                <textarea id="content-${htmlFragment.id}">
                    ${htmlFragment.content}
                </textarea>
                </div>
            </div>
        </form>
        <div class="clearfix form-actions center" style="margin: 0">
                <button class="btn btn-info" type="button" id="submitBtn-${htmlFragment.id}">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    修改
                </button>
        </div>
    </div>
  </div>
</div>
<script>
    var ke_${htmlFragment.id} = KindEditor.create('#content-${htmlFragment.id}', {
        allowFileManager : true,
        uploadJson : '${ctx}/ke/upload_json',
        fileManagerJson : '${ctx}/ke/file_manager_json',
        height: '500px',
        width: '800px'
    });
    $("#submitBtn-${htmlFragment.id}").click(function(){$("#modalForm-${htmlFragment.id}").submit(); return false;});
    $("#modalForm-${htmlFragment.id}").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                data:{content: ke_${htmlFragment.id}.html()},
                success:function(ret){
                    if(ret.success){
                        SysMsg.info("修改成功。");
                    }
                }
            });
        }
    });
</script>