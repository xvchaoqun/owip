<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${ctx}/assets/css/daterangepicker.css" />
<script src="${ctx}/assets/js/date-time/moment.js"></script>
<script src="${ctx}/assets/js/date-time/daterangepicker.js"></script>
<script src="${ctx}/extend/js/daterange-zh-CN.js"></script>
<script>
  $('[data-rel=date-range-picker]').daterangepicker({
    autoUpdateInput:false,
    showDropdowns:true,
    linkedCalendars:false,
    'applyClass' : 'btn-sm btn-success',
    'cancelClass' : 'btn-sm btn-default',
    locale: {
      applyLabel: '确定',
      cancelLabel: '清除',
      format: 'YYYY-MM-DD',
      separator: ' 至 '
    }
  }).on('apply.daterangepicker', function(ev, picker) {
    $(this).val(picker.startDate.format('YYYY-MM-DD') + ' 至 ' + picker.endDate.format('YYYY-MM-DD'));
  }).on('cancel.daterangepicker', function(ev, picker) {
    $(this).val('');
  });
</script>