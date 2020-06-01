<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <div id="body-content">
      <div class="row">
        <form class="form-inline">
          <div class="form-group">
            <div class="col-xs-10">
              <input id="searchinput" class="form-control" type="text" name="realnameOrCode"
                     value="${realnameOrCode}" placeholder="请输入工作证号或姓名">
              <span id="searchclear" class="fa fa-times-circle-o"></span>
            </div>
            <div class="col-xs-2" style="padding-left: 0">
              <input type="button" id="search" class="btn btn-sm btn-primary" value="查询"/>
            </div>
          </div>
        </form>
        <br/>  <br/>
      </div>
      <div style="overflow:auto;width:100%">
        <table id="fixedTable" class="table table-bordered table-center" width="auto" style="white-space:nowrap">
          <thead>
          <tr>
            <th>工号</th>
            <th>姓名</th>
            <th>离任后所在单位及职务</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="cadre" items="${cadres}" varStatus="vs">
            <tr>
              <td>${cadre.code}</td>
              <td>
                <c:set var="backTo" value="${ctx}/m/cadre_compare_result?cadreIds[]=${param['cadreIds[]']}"/>
                <a href="javascript:;" class="openView" data-open-by="page" data-url="${ctx}/m/cadre_info?cadreId=${cadre.id}">
                    ${cadre.realname}
                </a>
              </td>
              <td style="text-align: left">${cadre.title}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="message-footer clearfix">
        <wo:page commonList="${commonList}" uri="${ctx}/m/cadreHistory_page?realnameOrCode=${param.realnameOrCode}"
                 target="#page-content" model="4"/>
      </div>
    </div>
    <div id="body-content-view"></div>

  </div>
</div>
<style>
  .table>thead>tr>th:last-child{
    border-right-color: inherit;
  }

  #searchclear {
    position: absolute;
    right: 20px;
    top: 0;
    bottom: 0;
    height: 14px;
    margin: auto;
    font-size: 14px;
    cursor: pointer;
    color: #ccc;
  }
</style>
<script>
  $("#search").click(function () {
    var realnameOrCode = $("#searchinput").val();
    search(realnameOrCode);
  })
    $("#searchclear").click(function () {
      var realnameOrCode = $("#searchinput").val();

      if ($.trim(realnameOrCode) != ''){
        realnameOrCode = '';
        search(realnameOrCode);
      }
    });

  function search(realnameOrCode) {
    $("#page-content").load("${ctx}/m/cadreHistory_page?realnameOrCode="+realnameOrCode);
  }
</script>
