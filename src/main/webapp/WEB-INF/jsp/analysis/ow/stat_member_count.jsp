<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_member_other" style="width:750px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                党员数量统计
            </h5>
            <div class="widget-toolbar no-border">
                <div class="inline dropdown-hover">
                    <button class="btn btn-xs btn-info">
                        ${empty type?"详细情况":(type==1)?"性别":(type==2)?"民族":"基本情况"}
                        <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                        <li class="${empty type?'active':''}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${empty type?'':'invisible'}">
                                    &nbsp;</i>
                                详细情况
                            </a>
                        </li>
                        <li data-type="3"  class="${type!=3?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=3?'invisible':''}">
                                    &nbsp;</i>
                                基本情况
                            </a>
                        </li>
                        <li data-type="1"  class="${type!=1?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=1?'invisible':''}">
                                    &nbsp;</i>
                                性别
                            </a>
                        </li>
                        <li data-type="2"  class="${type!=2?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=2?'invisible':''}">
                                    &nbsp;</i>
                                民族
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="widget-body">
            <c:if test="${not empty type}">
                <div class="widget-main">
                    <div id="memberOther-placeholder" style="height: 252px"></div>
                </div>
            </c:if>
            <c:if test="${empty type}">
                <div class="widget-main">
                    <div id="memberCount-placeholder" style="min-height: 252px;">
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script>
    $("#stat_member_other ul li").click(function () {

        var url = "${ctx}/${isPartyAdmin?'stat_party_member_count':'stat_member_count'}";

        $.get(url, {type: $(this).data('type'),partyId: "${partyId}"}, function (html) {
            $("#stat_member_other").replaceWith(html);
        });
    });

    <c:if test="${type == 3}">
    var $div = $('#memberOther-placeholder');
    </c:if>
    <c:if test="${empty type}">
    var $div = $('#memberCount-placeholder');
    </c:if>
    <c:if test="${not empty type && type != 3}">
    var $div = $('#memberOther-placeholder');
    </c:if>

    var url = "${ctx}/${isPartyAdmin?'stat_party_member_count_data':'stat_member_count_data'}";

    (function(displayDiv, type,partyId,branchId){
        var countChart = echarts.init(displayDiv);
        countChart.showLoading({text: '正在加载数据'});

        $.get(url, {type:type,partyId:partyId,branchId:branchId}, function (ret) {
            var otherMap = ret.otherMap;
            var statPoliticalStatusMap= ret.statPoliticalStatusMap;
            var isRetireGrowMap= ret.isRetireGrowMap;
            var isRetirePositiveMap= ret.isRetirePositiveMap;
            var statGrowMap= ret.statGrowMap;
            var statPositiveMap= ret.statPositiveMap;

            if(type == 3){
                var legendTitle = [];
                var countData = [];
                var JZG_count=statPositiveMap[${TEACHER_TYPE_JZG}]-isRetirePositiveMap[${TEACHER_TYPE_JZG}];
                var count=statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}];
                function addData(){
                    if (statPositiveMap[${STUDENT_TYPE_BKS}]!=0){
                        countData.push({name: '本科生党员('+statPositiveMap[${STUDENT_TYPE_BKS}]+')', value: statPositiveMap[${STUDENT_TYPE_BKS}]});
                        legendTitle.push('本科生党员('+statPositiveMap[${STUDENT_TYPE_BKS}]+')');
                    }
                    if (statPositiveMap[${STUDENT_TYPE_SS}]!=0){
                        countData.push({name: '硕士生党员('+statPositiveMap[${STUDENT_TYPE_SS}]+')', value: statPositiveMap[${STUDENT_TYPE_SS}]});
                        legendTitle.push('硕士生党员('+statPositiveMap[${STUDENT_TYPE_SS}]+')');
                    }
                    if (statPositiveMap[${STUDENT_TYPE_BS}]!=0){
                        countData.push({name: '博士生党员('+statPositiveMap[${STUDENT_TYPE_BS}]+')', value: statPositiveMap[${STUDENT_TYPE_BS}]});
                        legendTitle.push('博士生党员('+statPositiveMap[${STUDENT_TYPE_BS}]+')');
                    }

                    if (JZG_count!=0){
                        countData.push({name: '教职工党员('+JZG_count+')', value: JZG_count });
                        legendTitle.push('教职工党员('+JZG_count+')');
                    }
                    if (isRetirePositiveMap[${TEACHER_TYPE_JZG}]!=0){
                        countData.push({name: '离退休党员('+isRetirePositiveMap[${TEACHER_TYPE_JZG}]+')', value: isRetirePositiveMap[${TEACHER_TYPE_JZG}]});
                        legendTitle.push('离退休党员('+isRetirePositiveMap[${TEACHER_TYPE_JZG}]+')');
                    }
                }
                addData();
                var option = {
                    title: {
                        text: '（党员总数：'+count+'）',
                        left: 'right'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left:-5,
                        top: -5,
                        bottom: 5,
                        data: legendTitle
                    },
                    series: [
                        {
                            name: '党员数量',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data:countData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur:10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        {
                            name: '党员数量',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data: countData,
                            itemStyle:{            //饼图图形上的文本标签
                                normal: {
                                    label: {
                                        show: true,
                                        position: 'inner',
                                        fontSize: 12,
                                        align: "left",
                                        textStyle: {
                                            fontWeight: 300,
                                            fontSize: 12,   //文字的字体大小
                                            color:'white'
                                        },
                                        formatter: '{d}%'
                                    },
                                    labelLine:{
                                        show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                                    }
                                }
                            }
                        }
                    ]
                };
            }
            if($.trim(type)==''){
                var memberData = [];
                var legendTitle = [];
                var countData = [];
                var positive_JZG_count = statPositiveMap[${TEACHER_TYPE_JZG}]-isRetirePositiveMap[${TEACHER_TYPE_JZG}];
                var grow_JZG_count = statGrowMap[${TEACHER_TYPE_JZG}]-isRetireGrowMap[${TEACHER_TYPE_JZG}];
                var count =statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}];
                function addData1(){
                    //正式党员  预备党员
                    if (statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}]!=0){
                        memberData.push({
                            name: '正式党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}]+')',
                            value: statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}],
                        });
                        legendTitle.push('正式党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}]+')');
                    }
                    if (statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}]!=0){
                        memberData.push({
                            name: '预备党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}]+')',
                            value: statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}],
                        });
                        legendTitle.push('预备党员('+statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}]+')');
                    }

                    //其他
                    if (isRetirePositiveMap[${TEACHER_TYPE_JZG}]!=0){
                        countData.push({name: '离退休正式党员('+isRetirePositiveMap[${TEACHER_TYPE_JZG}]+')', value: isRetirePositiveMap[${TEACHER_TYPE_JZG}]});
                        legendTitle.push('离退休正式党员('+isRetirePositiveMap[${TEACHER_TYPE_JZG}]+')');
                    }
                    if (positive_JZG_count != 0){
                        countData.push({name: '教职工正式党员('+positive_JZG_count+')', value: positive_JZG_count});
                        legendTitle.push('教职工正式党员('+positive_JZG_count+')');
                    }
                    if (statPositiveMap[${STUDENT_TYPE_BKS}]!=0){
                        countData.push({name: '本科生正式党员('+statPositiveMap[${STUDENT_TYPE_BKS}]+')', value: statPositiveMap[${STUDENT_TYPE_BKS}]});
                        legendTitle.push('本科生正式党员('+statPositiveMap[${STUDENT_TYPE_BKS}]+')');
                    }
                    if (statPositiveMap[${STUDENT_TYPE_SS}]!=0){
                        countData.push({name: '硕士生正式党员('+statPositiveMap[${STUDENT_TYPE_SS}]+')', value: statPositiveMap[${STUDENT_TYPE_SS}]});
                        legendTitle.push('硕士生正式党员('+statPositiveMap[${STUDENT_TYPE_SS}]+')');
                    }
                    if (statPositiveMap[${STUDENT_TYPE_BS}]!=0){
                        countData.push({name: '博士生正式党员('+statPositiveMap[${STUDENT_TYPE_BS}]+')', value: statPositiveMap[${STUDENT_TYPE_BS}]});
                        legendTitle.push('博士生正式党员('+statPositiveMap[${STUDENT_TYPE_BS}]+')');
                    }
                    if (isRetireGrowMap[${TEACHER_TYPE_JZG}]!=0){
                        countData.push( {name: '离退休预备党员('+isRetireGrowMap[${TEACHER_TYPE_JZG}]+')', value: isRetireGrowMap[${TEACHER_TYPE_JZG}]});
                        legendTitle.push('离退休预备党员('+isRetireGrowMap[${TEACHER_TYPE_JZG}]+')');
                    }
                    if (grow_JZG_count!=0){
                        countData.push({name: '教职工预备党员('+grow_JZG_count+')', value: grow_JZG_count});
                        legendTitle.push('教职工预备党员('+grow_JZG_count+')');
                    }
                    if (statGrowMap[${STUDENT_TYPE_BKS}]!=0){
                        countData.push({name: '本科生预备党员('+statGrowMap[${STUDENT_TYPE_BKS}]+')', value: statGrowMap[${STUDENT_TYPE_BKS}]});
                        legendTitle.push('本科生预备党员('+statGrowMap[${STUDENT_TYPE_BKS}]+')');
                    }
                    if (statGrowMap[${STUDENT_TYPE_SS}]!=0){
                        countData.push({name: '硕士生预备党员('+statGrowMap[${STUDENT_TYPE_SS}] +')', value: statGrowMap[${STUDENT_TYPE_SS}]});
                        legendTitle.push('硕士生预备党员('+statGrowMap[${STUDENT_TYPE_SS}] +')');
                    }
                    if (statGrowMap[${STUDENT_TYPE_BS}]!=0){
                        countData.push({name: '博士生预备党员('+statGrowMap[${STUDENT_TYPE_BS}]+')', value: statGrowMap[${STUDENT_TYPE_BS}]});
                        legendTitle.push('博士生预备党员('+statGrowMap[${STUDENT_TYPE_BS}]+')');
                    }
                }
                addData1();
                var option = {
                    title: {
                        text: '（党员总数：'+count+'）',
                        left: 'right'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left:-5,
                        top: -5,
                        bottom: 5,
                        data: legendTitle
                    },
                    series: [
                        {
                            name: '党员数量',
                            type: 'pie',
                            selectedMode: 'single',
                            radius: [0, '45%'],
                            center: ['63%', '53%'],
                            startAngle:240,
                            label: {
                                color: 'white',
                                position: 'inner'
                            },
                            labelLine: {
                                show: false
                            },
                            data: memberData
                        },
                        {
                            name: '党员数量',
                            type: 'pie',
                            radius: ['55%', '70%'],
                            center: ['63%', '53%'],
                            startAngle:240,
                            data: countData
                        }
                    ]
                };
            }
            if($.trim(type)!='' && type != 3){

                var otherLabel= [];
                var otherData = [];
                $.each(otherMap, function (key, value) {

                    var item = key + '(' + value + ')';
                    otherLabel.push(item);
                    otherData.push({
                        name: item,
                        value: value
                    });
                });

                var option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: -5,
                        top: 5,
                        bottom: 5,
                        data: otherLabel
                    },
                    series: [
                        {
                            name: '党员基本情况',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data:otherData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur:10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        {
                            name: '党员基本情况',
                            type: 'pie',
                            radius: '60%',
                            center: ['60%', '60%'],
                            data: otherData,
                            itemStyle:{            //饼图图形上的文本标签
                                normal: {
                                    label: {
                                        show: true,
                                        position: 'inner',
                                        fontSize: 12,
                                        align: "left",
                                        textStyle: {
                                            fontWeight: 300,
                                            fontSize: 12,   //文字的字体大小
                                            color:'white'
                                        },
                                        formatter: '{d}%'
                                    },
                                    labelLine:{
                                        show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                                    }
                                }
                            }
                        }
                    ]
                };
            }

            countChart.setOption(option);
            countChart.hideLoading();
        });
    })($div[0], '${type}','${partyId}','${branchId}');
</script>