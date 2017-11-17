<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta charset="utf-8"/>
  <title>党费缴纳成功-${_plantform_name}</title>
  <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="${ctx}/assets/css/ace-nobtn.css" class="ace-main-stylesheet" id="main-ace-style" />
  <link href="${ctx}/extend/css/faq.css" rel="stylesheet" type="text/css" />
  <link href="${ctx}/assets/css/font-awesome.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="top">
  <div class="w1000">
    <div class="logo"><t:img src="/img/logo.png"/></div>
    <div class="txt">${_plantform_name}</div>
  </div>
</div>
<div class="container">
  <div class="row">
    <div class="vspace-16" style="display: block;">&nbsp;</div>
    <div class="widget-box" >
      <div class="widget-header widget-header-flat">
        <h4 class="widget-title">党费缴纳成功</h4>
      </div>
      <div class="widget-body" >
        <div class="widget-main">
          <form class="form-horizontal">
            <div class="row">
              <table class="table table-bordered table-unhover" style="width: 500px; margin: 0 auto;">
                <tr>
                  <td colspan="2" class="title">党建缴纳信息</td>
                </tr>
                <tr>
                  <td class="bg-right">党费缴纳月份</td>
                  <td>${cm:formatDate(pmdMemberPayView.payMonth, "yyyy年MM月")}</td>
                </tr>
                <tr>
                  <td class="bg-right">工作证号</td>
                  <td>${pmdMemberPayView.user.code}</td>
                </tr>
                <tr>
                  <td class="bg-right">姓名</td>
                  <td>${pmdMemberPayView.user.realname}</td>
                </tr>
                <tr>
                  <td class="bg-right">所属党委</td>
                  <td>${partyMap.get(pmdMemberPayView.chargePartyId).name}</td>
                </tr>
                <c:if test="${not empty pmdMemberPayView.chargeBranchId}">
                  <tr>
                    <td class="bg-right">所属党支部</td>
                    <td>${branchMap.get(pmdMemberPayView.chargeBranchId).name}</td>
                  </tr>
                </c:if>
                <tr>
                  <td class="bg-right">党员类别</td>
                  <td>${PMD_MEMBER_TYPE_MAP.get(pmdMemberPayView.type)}</td>
                </tr>
                <tr>
                  <td class="bg-right">应交金额</td>
                  <td>${pmdMemberPayView.duePay}</td>
                </tr>
                <tr>
                  <td class="bg-right">实缴金额</td>
                  <td>${pmdMemberPayView.realPay}</td>
                </tr>
                <tr>
                  <td class="bg-right">党费缴纳订单号</td>
                  <td>${pmdMemberPayView.orderNo}</td>
                </tr>
                <tr>
                  <td class="bg-right">党费缴纳时间</td>
                  <td>${cm:formatDate(pmdMemberPayView.payTime, "yyyy-MM-dd mm:HH:ss")}</td>
                </tr>
              </table>
            </div>
            <div class="clearfix form-actions center">
              <%--<a href="javascript:window.opener=null;window.open('','_self');window.close();"
                 class="btn btn-info" type="button" id="submitBtn">
                <i class="ace-icon fa fa-close bigger-110"></i>
                关闭此页面
              </a>--%>
              <a href="${ctx}/#/user/pmd/pmdMember" class="btn btn-success">
                <i class="fa fa-reply"></i>
                返回组工系统</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<style>
  .bg-right {
    width: 130px;
    background-color: #f9f9f9 !important;
    text-align: right !important;
    vertical-align: middle !important;
    font-weight: bolder;
  }
  .title{
    font-size: 20px;
    font-weight: bolder;
    text-align: center;
    background-color: #f9f9f9 !important;
  }
</style>
<!--[if !IE]> -->
<script type="text/javascript">
  window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
  window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script>

</script>
</body>
</html>
