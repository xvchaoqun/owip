<#list dataList as row>
    <w:p w:rsidR="00466809" w:rsidRPr="003B1A40" w:rsidRDefault="00466809" w:rsidP="003B1A40">
        <w:pPr>
            <w:spacing w:before="60" w:line="310" w:lineRule="exact"/>
            <w:ind w:left="2260" w:right="100" w:hanging="${row[1]?starts_with("（")?string("136","2260")}"/>
            <w:rPr>
                <w:rFonts w:ascii="宋体" w:hint="eastAsia"/>
                <w:sz w:val="24"/>
            </w:rPr>
        </w:pPr>
        <w:bookmarkStart w:id="14" w:name="A1701_20"/>
        <w:bookmarkEnd w:id="14"/>
        <#list row as col>
            <#if col_index!=0>
                <w:r>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:hint="eastAsia"/>
                        <w:sz w:val="24"/>
                    </w:rPr>
                    <w:t xml:space="preserve"><#if needWhiteSpace?? && needWhiteSpace>    </#if>${col}<#if col_has_next && !col?ends_with(" ")>  </#if></w:t>
                </w:r>
            </#if>
        </#list>
    </w:p>
</#list>
