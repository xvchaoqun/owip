<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="container" style="height:500px; margin: 0 auto;"></div>
<script>
	var data = [];
	var beans = ${cm:toJSONArray(beans)};
	beans.forEach(function(bean, i){
		data.push([Date.UTC(bean.year, bean.month-1, bean.day), bean.onlineCount]);
	})

	if(data.length>0){
		Highcharts.setOptions({ lang: { resetZoom: "返回", resetZoomTitle: "回到初始状态" }, global: { useUTC: false } });
		var title = '每日最高在线人数统计（' + Highcharts.dateFormat('%Y年%m月', data[0][0]) + '至' + Highcharts.dateFormat('%Y年%m月', data[data.length-1][0]) + "）";
		var fromYear = new Date(data[0][0]).getFullYear();
		var toYear = new Date(data[data.length-1][0]).getFullYear();
		$('#container').highcharts({
			credits: { enabled: false },
			exporting: {
				type: 'image/png',
				filename: title,
				url:'${ctx}/export',
				buttons: {
					contextButton: {
						menuItems: [{
							text: '打印图片',
							onclick: function () {
								this.print();
							},height:50
						}, {separator:true
						},{
							text: '下载图片',
							onclick: function () {
								this.exportChart();
							}
						}]
					}
				}
			},
			chart: {
				zoomType: 'x'
			},
			title: {
				text: title
			},
			xAxis: {
				type: 'datetime',
				labels: {
					formatter: function () {
						return Highcharts.dateFormat('%Y-%m-%d', this.value);
					}
				}
			},
			yAxis: {
				title: {
					text: '最高在线人数'
				}
			},
			tooltip: {
				formatter: function () {
					return Highcharts.dateFormat('%Y-%m-%d', this.x) +'<br/><b>' + this.series.name + '：' +
							+ this.y.toFixed(2) +"</b>";
				}
			},
			legend: {
				enabled: false
			},
			plotOptions: {
				area: {
					fillColor: {
						linearGradient: {
							x1: 0,
							y1: 0,
							x2: 0,
							y2: 1
						},
						stops: [
							[0, Highcharts.getOptions().colors[0]],
							[1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
						]
					},
					marker: {
						radius: 2
					},
					lineWidth: 1,
					states: {
						hover: {
							lineWidth: 1
						}
					},
					threshold: null
				}
			},

			series: [{
				type: 'area',
				name: '最高在线人数',
				data: data
			}]
		});
	}
</script>