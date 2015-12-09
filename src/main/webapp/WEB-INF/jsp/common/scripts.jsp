<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>

<!--[if lt IE 8]>
<script type="text/javascript">
location.href="${ctx}/extend/unsupport.html"
</script>
<![endif]-->
<!-- basic scripts -->
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
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
</script>
<script src="${ctx}/assets/js/bootstrap.js"></script>

<!--[if lte IE 8]>
<script src="${ctx}/assets/js/excanvas.js"></script>
<![endif]-->
<script src="${ctx}/assets/js/jquery-ui.custom.js"></script>
<%--<script src="assets/js/jquery.ui.touch-punch.js"></script>
<script src="assets/js/jquery.easypiechart.js"></script>
<script src="assets/js/jquery.sparkline.js"></script>
-%>

<!-- ace scripts -->

<%--<script src="assets/js/ace/elements.colorpicker.js"></script>--%>
<script src="${ctx}/assets/js/ace/elements.fileinput.js"></script>
<%--<script src="assets/js/ace/elements.typeahead.js"></script>--%>
<script src="${ctx}/assets/js/ace/elements.wysiwyg.js"></script>
<script src="${ctx}/assets/js/ace/elements.spinner.js"></script>
<script src="${ctx}/assets/js/ace/elements.treeview.js"></script>
<script src="${ctx}/assets/js/ace/elements.wizard.js"></script>
<script src="${ctx}/assets/js/ace/elements.aside.js"></script>
<script src="${ctx}/assets/js/ace/ace.js"></script>
<%--<script src="assets/js/ace/ace.ajax-content.js"></script>--%>
<%--<script src="assets/js/ace/ace.touch-drag.js"></script>--%>
<script src="${ctx}/assets/js/ace/ace.sidebar.js"></script>
<script src="${ctx}/assets/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="${ctx}/assets/js/ace/ace.submenu-hover.js"></script>
<script src="${ctx}/assets/js/ace/ace.widget-box.js"></script>
<%--<script src="assets/js/ace/ace.settings.js"></script>
<script src="assets/js/ace/ace.settings-rtl.js"></script>
<script src="assets/js/ace/ace.settings-skin.js"></script>
<script src="assets/js/ace/ace.widget-on-reload.js"></script>
<script src="assets/js/ace/ace.searchbox-autocomplete.js"></script>--%>

<!--extend-->
<script src="${ctx}/extend/js/toastr.js"></script>
<script src="${ctx}/extend/js/jquery.form.js"></script>
<script src="${ctx}/extend/js/jquery.dynatree.min.js"></script>

<script src="${ctx}/extend/js/jquery.validate.min.js"></script>
<script src="${ctx}/extend/js/additional-methods.min.js"></script>
<script src="${ctx}/extend/js/jquery.validate.extend.js"></script>

<script src="${ctx}/extend/js/jquery.showLoading.min.js"></script>
<script src="${ctx}/extend/js/underscore.js"></script>
<script src="${ctx}/extend/js/custom.js"></script>
<script src="${ctx}/extend/js/prototype.js"></script>

<script src="${ctx}/extend/js/bootbox.min.js"></script>
<script src="${ctx}/extend/js/select2.full.js"></script>
<script src="${ctx}/extend/js/select2-zh-CN.js"></script>

<script src="${ctx}/assets/js/date-time/bootstrap-datepicker.js"></script>
<script src="${ctx}/extend/js/bootstrap-datepicker.zh-CN.min.js"></script>

<script src="${ctx}/assets/js/jquery.inputlimiter.1.3.1.js"></script>

<script src="${ctx}/extend/js/placeholders.jquery.js"></script>
<script src="${ctx}/extend/js/setup.js"></script>
