<#list cadreEdus as cadreEdu>
<p>${cadreEdu.enrolTime?string("yyyy.MM")}${cadreEdu.finishTime???string("-","-至今")}${(cadreEdu.finishTime?string("yyyy.MM"))!}&nbsp;${cadreEdu.school!}${cadreEdu.dep!}${cadreEdu.major!}专业&nbsp;${cadreEdu.degree!}</p>
</#list>