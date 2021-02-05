<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_branch_member_other" style="width:600px;float: left;">
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
    $("#stat_branch_member_other ul li").click(function () {

        var url = "${ctx}/stat_branch_member_count";

        $.get(url, {type: $(this).data('type'),branchId: "${branchId}"}, function (html) {
            $("#stat_branch_member_other").replaceWith(html);
        });
    });
    <c:if test="${type == 3}">
    var countChart = echarts.init($('#memberOther-placeholder').get(0));
    var legendTitle = [];
    var countData = [];
    function addData(){
        if (${statPositiveMap.get(STUDENT_TYPE_BKS)!=0}){
            countData.push({name: '本科生党员(${statPositiveMap.get(STUDENT_TYPE_BKS)})', value: '${statPositiveMap.get(STUDENT_TYPE_BKS)}'});
            legendTitle.push('本科生党员(${statPositiveMap.get(STUDENT_TYPE_BKS)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_SS)!=0}){
            countData.push({name: '硕士生党员(${statPositiveMap.get(STUDENT_TYPE_SS)})', value: '${statPositiveMap.get(STUDENT_TYPE_SS)}'});
            legendTitle.push('硕士生党员(${statPositiveMap.get(STUDENT_TYPE_SS)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_BS)!=0}){
            countData.push({name: '博士生党员(${statPositiveMap.get(STUDENT_TYPE_BS)})', value: '${statPositiveMap.get(STUDENT_TYPE_BS)}'});
            legendTitle.push('博士生党员(${statPositiveMap.get(STUDENT_TYPE_BS)})');
        }
        if (${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)!=0}){
            countData.push({name: '教职工党员(${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)})', value: '${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)}'});
            legendTitle.push('教职工党员(${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)})');
        }
        if (${isRetirePositiveMap.get(TEACHER_TYPE_JZG)!=0}){
            countData.push({name: '离退休党员(${isRetirePositiveMap.get(TEACHER_TYPE_JZG)})', value: '${isRetirePositiveMap.get(TEACHER_TYPE_JZG)}'});
            legendTitle.push('离退休党员(${isRetirePositiveMap.get(TEACHER_TYPE_JZG)})');
        }
    }
    addData();
    var countOption = {
        title: {
            text: '（党员总数：${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE) + statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}）',
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
    countChart.setOption(countOption);
    </c:if>
    <c:if test="${empty type}">
    var countChart = echarts.init($('#memberCount-placeholder').get(0));
    var memberData = [];
    var legendTitle = [];
    var countData = [];

    function addData1(){
        //正式党员  预备党员
        if (${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE)!=0}){
            memberData.push({
                name: '${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_POSITIVE)}(${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE)})',
                value: '${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE)}',
            });
            legendTitle.push('${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_POSITIVE)}(${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE)})');
        }
        if (${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)!=0}){
            memberData.push({
                name: '${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_GROW)}(${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)})',
                value: '${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}',
            });
            legendTitle.push('${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_GROW)}(${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)})');
        }

        //其他
        if (${isRetirePositiveMap.get(TEACHER_TYPE_JZG)!=0}){
            countData.push({name: '离退休正式党员(${isRetirePositiveMap.get(TEACHER_TYPE_JZG)})', value: '${isRetirePositiveMap.get(TEACHER_TYPE_JZG)}'});
            legendTitle.push('离退休正式党员(${isRetirePositiveMap.get(TEACHER_TYPE_JZG)})');
        }
        if (${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG) != 0}){
            countData.push({name: '教职工正式党员(${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)})', value: '${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)}'});
            legendTitle.push('教职工正式党员(${statPositiveMap.get(TEACHER_TYPE_JZG)-isRetirePositiveMap.get(TEACHER_TYPE_JZG)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_BKS)!=0}){
            countData.push({name: '本科生正式党员(${statPositiveMap.get(STUDENT_TYPE_BKS)})', value: '${statPositiveMap.get(STUDENT_TYPE_BKS)}'});
            legendTitle.push('本科生正式党员(${statPositiveMap.get(STUDENT_TYPE_BKS)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_SS)!=0}){
            countData.push({name: '硕士生正式党员(${statPositiveMap.get(STUDENT_TYPE_SS)})', value: '${statPositiveMap.get(STUDENT_TYPE_SS)}'});
            legendTitle.push('硕士生正式党员(${statPositiveMap.get(STUDENT_TYPE_SS)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_BS)!=0}){
            countData.push({name: '博士生正式党员(${statPositiveMap.get(STUDENT_TYPE_BS)})', value: '${statPositiveMap.get(STUDENT_TYPE_BS)}'});
            legendTitle.push('博士生正式党员(${statPositiveMap.get(STUDENT_TYPE_BS)})');
        }
        if (${isRetireGrowMap.get(TEACHER_TYPE_JZG)!=0}){
            countData.push( {name: '离退休预备党员(${isRetireGrowMap.get(TEACHER_TYPE_JZG)})', value: '${isRetireGrowMap.get(TEACHER_TYPE_JZG)}'});
            legendTitle.push('离退休预备党员(${isRetireGrowMap.get(TEACHER_TYPE_JZG)})');
        }
        if (${statGrowMap.get(TEACHER_TYPE_JZG)-isRetireGrowMap.get(TEACHER_TYPE_JZG)!=0}){
            countData.push({name: '教职工预备党员(${statGrowMap.get(TEACHER_TYPE_JZG)-isRetireGrowMap.get(TEACHER_TYPE_JZG)})', value: '${statGrowMap.get(TEACHER_TYPE_JZG)-isRetireGrowMap.get(TEACHER_TYPE_JZG)}'});
            legendTitle.push('教职工预备党员(${statGrowMap.get(TEACHER_TYPE_JZG)-isRetireGrowMap.get(TEACHER_TYPE_JZG)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_BKS)!=0}){
            countData.push({name: '本科生正式党员(${statPositiveMap.get(STUDENT_TYPE_BKS)})', value: '${statPositiveMap.get(STUDENT_TYPE_BKS)}'});
            legendTitle.push('本科生正式党员(${statPositiveMap.get(STUDENT_TYPE_BKS)})');
        }
        if (${statGrowMap.get(STUDENT_TYPE_SS)!=0}){
            countData.push({name: '硕士生预备党员(${statGrowMap.get(STUDENT_TYPE_SS)})', value: '${statGrowMap.get(STUDENT_TYPE_SS)}'});
            legendTitle.push('硕士生预备党员(${statGrowMap.get(STUDENT_TYPE_SS)})');
        }
        if (${statPositiveMap.get(STUDENT_TYPE_BS)!=0}){
            countData.push({name: '博士生预备党员(${statPositiveMap.get(STUDENT_TYPE_BS)})', value: '${statPositiveMap.get(STUDENT_TYPE_BS)}'});
            legendTitle.push('博士生预备党员(${statPositiveMap.get(STUDENT_TYPE_BS)})');
        }
    }
    addData1();
    var countOption = {
        title: {
            text: '（党员总数：${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE) + statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}）',
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
                center: ['63%', '50%'],
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
                center: ['63%', '50%'],
                startAngle:240,
                /*label: {
                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                    backgroundColor: '#eee',
                    borderColor: '#aaa',
                    borderWidth: 1,
                    borderRadius: 4,
                    rich: {
                        a: {
                            color: '#999',
                            lineHeight: 22,
                            align: 'center'
                        },
                        hr: {
                            borderColor: '#aaa',
                            width: '50%',
                            borderWidth: 0.5,
                            height: 0
                        },
                        b: {
                            fontSize: 12,
                            lineHeight: 33
                        },
                        per: {
                            color: '#eee',
                            backgroundColor: '#334455',
                            padding: [2, 4],
                            borderRadius: 2
                        }
                    }
                },*/
                data: countData
            }
        ]
    };
    countChart.setOption(countOption);
    </c:if>
    <c:if test="${not empty type && type != 3}">
    var otherChart = echarts.init($('#memberOther-placeholder').get(0));

    var otherLabel= [
        <c:forEach items="${otherMap}" var="other">
        <c:if test="${not empty other.value}">
        '${other.key}(${other.value})',
        </c:if>
        </c:forEach>
    ];
    var otherData = [
        <c:forEach items="${otherMap}" var="other">
        <c:if test="${not empty other.value}">
        {name: "${other.key}(${other.value})", value: '${other.value}'},
        </c:if>
        </c:forEach>
    ];

    var otherOption = {
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
    otherChart.setOption(otherOption);
    </c:if>
</script>