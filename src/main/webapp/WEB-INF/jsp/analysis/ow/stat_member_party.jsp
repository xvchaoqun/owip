<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="container" style="height:500px; margin: 0 auto;"></div>
<script>
var mychart= {
        chart: {
            type: 'column',
            height:'500'
        },
        exporting: {
        	enabled:false,
            url: '${ctx}/export',    // 导出图表的服务端处理地址
            filename: ''    // 返回下载的文件名
        },
        title: null,
		credits: { enabled: false }, // 版权信息
        xAxis: {
            categories: ${cm:toJSONArray(categories)},
			tickmarkPlacement:'on',
			gridLineWidth:0,
			gridLineDashStyle: 'ShortDot',
			 labels: {
				 rotation: -45,
				 align: 'right',
				 style: {
					 fontSize: '13px',
					 fontFamily: 'Verdana, sans-serif'
				 }
			}
        },
        yAxis: {
            title: {
                text: ''
            },
            lineWidth :1,
			gridLineWidth:0,
			endOnTick: true, //是否以刻度结束
			lineWidth:2,
			tickWidth:2,
			minorGridLineWidth:1,
			minorTickLength:4,
			stackLabels: {
				enabled: true,
				style: {
					fontWeight: 'bold',
					color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
				},
                x:0,
                y:-13
			}
        },
        tooltip: {
            formatter: function() {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    '总量: ' + this.point.stackTotal;
            }
        },
	plotOptions: {
		column: {
			stacking: 'normal',
			dataLabels: {
				enabled: true,
				color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
				style: {
					textShadow: '0 0 3px black'
                },
                x:0,
                y:-8
			}
		}
	},
        series: [{
			name:'教职工',
            data: ${cm:toJSONArray(teachers)},
            maxPointWidth: 70,
        },{
			name:'学生',
			data: ${cm:toJSONArray(students)},
			color:'#AF4E96',
            maxPointWidth: 70,
		}],
	legend: {
		align: 'right',
		x: -70,
		verticalAlign: 'top',
		y: 20,
		floating: true,
		backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
		borderColor: '#CCC',
		borderWidth: 1,
		shadow: false
	}
    };
//var mychartStr = JSON.stringify(mychart)/* jQuery.extend(true, {}, oldObject) */;

$(function () {
	
	$('#container').highcharts(mychart);
});
(function(H) {
    var each = H.each;
    H.wrap(H.seriesTypes.column.prototype, 'drawPoints', function(proceed) {
        var series = this;
        if (series.data.length > 0) {
            var width = series.barW > series.options.maxPointWidth ? series.options.maxPointWidth : series.barW;
            each(this.data, function(point) {
                point.shapeArgs.x += (point.shapeArgs.width - width) / 2;
                point.shapeArgs.width = width;
            });
        }
        proceed.call(this);
    })
})(Highcharts);
</script>