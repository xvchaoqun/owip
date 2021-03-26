<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<c:set value="${cm:forcePdfPath(path)}" var="pdfPath"/>
<c:set value="${_uploadPath}${pdfPath}" var="_fullPath"/>
<c:if test="${empty path || !cm:exists(_fullPath)}">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        文件不存在：${pdfPath}
    </div>
    <div class="modal-footer">
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</c:if>
<c:if test="${not empty path && cm:exists(_fullPath)}">
    <c:set var="totalPage" value="${cm:getPages(_fullPath)}"/>
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>
                ${filename}
        </h3>
    </div>
    <div class="modal-body" style="background-color: #eaeaea;padding: 0">
        <c:forEach begin="1" end="${totalPage}" var="pageNo" varStatus="vs">
            <div id="page${pageNo}" class="page block-loading" oncontextmenu="return false"
                 onselectstart="return false" ondragstart="return false" οncοpy="return false">
                <img data-src="${ctx}/pdf_image?path=${cm:sign(path)}&pageNo=${pageNo}"
                     src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                     onload="lzld(this)"
                     style="width: 100%;padding-bottom: ${vs.last?0:5}px;">
            </div>
        </c:forEach>
    </div>
    <div class="modal-footer">
        <span class="pdf-pager">
            <span class="fa fa-search-plus"></span>
            <span class="fa fa-step-backward disabled"></span>
            <span class="fa fa-backward disabled"></span>
            <span class="separator left"></span>
                <select>
                    <c:forEach begin="1" end="${totalPage}" var="page">
                        <option>${page}</option>
                    </c:forEach>
                </select>
                共 ${totalPage} 页
            <span class="separator right"></span>
            <span class="fa fa-forward"></span>
            <span class="fa fa-step-forward"></span>
        </span>
        <c:if test="${!np}">
            <a href="javascript:;" class="printBtn btn btn-info"
               data-url="${ctx}/pdf?path=${cm:sign(pdfPath)}"><i class="fa fa-print"></i> 打印</a>
        </c:if>
        <c:if test="${!nd}">
            <a href="javascript:;" data-url="${ctx}/attach_download?path=${cm:sign(path)}&filename=${filename}"
               class="downloadBtn btn btn-success"><i class="fa fa-download"></i> 下载</a>
        </c:if>
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default"><i class="fa fa-times"></i> 关闭</a>
    </div>
    <style>
        .page {
            cursor: grab;
        }
        .page:active {
            cursor: grabbing;
        }
        .pdf-pager {
            position: absolute;
            right: 20px;
            cursor: default;
        }
        .pdf-pager .fa-search-plus {
            padding-right: 10px;
            cursor: pointer;
        }
        .pdf-pager .fa-backward{
            padding-right: 6px;
            cursor: pointer;
        }
        .pdf-pager .fa-step-backward {
            padding-right: 10px;
            cursor: pointer;
        }
        .pdf-pager .fa-forward, .pdf-pager .fa-step-forward {
            padding-left: 10px;
            cursor: pointer;
        }
        .pdf-pager .disabled {
            opacity: .35;
            filter: Alpha(Opacity=35);
            background-image: none;
            cursor: default;
        }
        .pdf-pager .separator {
            height: 18px;
            border: 0;
            border-left: 1px solid #ccc;
            padding-right: 5px;
        }
        .pdf-pager .separator.right {
            height: 18px;
            border: 0;
            border-right: 1px solid #ccc;
            padding-right: 2px;
        }
    </style>
    <link rel="stylesheet" href="${ctx}/extend/js/viewer/viewer.min.css"/>
    <script src="${ctx}/extend/js/viewer/viewer.min.js"></script>
    <script>

        function updatePdfPagerButtonStatus() {

            var pageNo = $(".pdf-pager select").val();
            var $backward = $(".fa-backward, .fa-step-backward", ".pdf-pager");
            var $forward = $(".fa-forward, .fa-step-forward", ".pdf-pager");
            if (pageNo == 1) {
                $backward.addClass("disabled");
            } else {
                $backward.removeClass("disabled");
            }
            if (pageNo == ${totalPage} || ${totalPage==1}) {
                $forward.addClass("disabled");
            } else {
                $forward.removeClass("disabled");
            }
        }

        $(".pdf-pager select").change(function () {
            var pageNo = $(this).val();
            $("#page" + pageNo)[0].scrollIntoView(true);
            updatePdfPagerButtonStatus();
        }).change();

        $(".pdf-pager .fa-backward").click(function () {
            var pageNo = $(".pdf-pager select").val();
            if (pageNo > 1) {
                $(".pdf-pager select").val(pageNo - 1).trigger("change");
            }
        })
        $(".pdf-pager .fa-step-backward").click(function () {
            $(".pdf-pager select").val(1).trigger("change");
        })
        $(".pdf-pager .fa-forward").click(function () {
            var pageNo = $(".pdf-pager select").val();
            if (pageNo < ${totalPage}) {
                //console.log("pageNo="+pageNo)
                $(".pdf-pager select").val(parseInt(pageNo) + 1).trigger("change");
            }
        })
        $(".pdf-pager .fa-step-forward").click(function () {
            $(".pdf-pager select").val(${totalPage}).trigger("change");
        })

        $(".pdf-pager .fa-search-plus").click(function () {
            var pageNo = $(".pdf-pager select").val();
            //console.log($("img", "#page"+pageNo).attr("src"))
            const viewer = new Viewer($("img", "#page" + pageNo)[0], {
                inline: false,
                toolbar: {
                    zoomIn: 4,
                    zoomOut: 4,
                    oneToOne: 4,
                    reset: 4,
                    prev: 0,
                    play: 0,
                    next: 0,
                    rotateLeft: 4,
                    rotateRight: 4,
                    flipHorizontal: 4,
                    flipVertical: 4,
                },
                navbar: false,
                title: false,
                viewed() {
                    viewer.zoomTo(1);
                },
                shown: function () {
                    $(".viewer-canvas")[0].oncontextmenu = function () {
                        return false;
                    };
                },
                hidden: function () {
                    viewer.destroy();
                }
            }).show();
            viewer.show();
        })

        $(".modal-body").scroll(function () {

            var height = $(this).height();
            for (var i = 1; i <= ${totalPage}; i++) {
                var pos = $("#page" + i).offset().top;
                if (i == ${totalPage} && pos <= height / 2) {
                    //console.log(i + '===' + i);
                    $(".pdf-pager select").val(i);
                }
                if (i <${totalPage}) {
                    var nextPagePos = $("#page" + (i + 1)).offset().top;
                    if (pos <= height / 2 && nextPagePos > height / 2) {
                        //console.log(i + '===' + i);
                        $(".pdf-pager select").val(i);
                    }
                }
            }
            updatePdfPagerButtonStatus();
        });

        var container = $(".modal-body")[0];
        var isMouseDown = false;
        var startY = 0;
        container.addEventListener('mousedown', e => {
            isMouseDown = true;
            startY = e.offsetY;
        });
        container.addEventListener('mousemove', e => {
            //console.log("isMouseDown=" + isMouseDown)
            if (isMouseDown) {
                var scrollTop = container.scrollTop;
                //console.log("container.scrollHeight=" + container.scrollHeight +"container.offsetHeight=" + container.offsetHeight +"container.scrollTop=" + container.scrollTop + " e.offsetY - startY=" + (e.offsetY - startY))
                if (e.offsetY > startY && scrollTop <= 0) return;
                if (e.offsetY < startY && scrollTop + container.offsetHeight == container.scrollHeight) return;

                // 获取鼠标按下后移动的距离
                var offsetY = e.offsetY - startY;
                if (offsetY > scrollTop) {
                    offsetY = scrollTop;
                }
                container.scrollTop = scrollTop - offsetY;
            }
        });

        container.addEventListener('mouseup', () => {
            isMouseDown = false;
            //console.log("mouseup")
        })
        container.addEventListener('click', () => {
            isMouseDown = false;
            //console.log("click")
        })
        container.addEventListener('dblclick', () => {
            isMouseDown = false;
            //console.log("dblclick")
        })
        container.addEventListener('mouseout', () => {
            isMouseDown = false;
            //console.log("mouseout")
        })
        container.addEventListener('mouseover', () => {
            isMouseDown = false;
            //console.log("mouseover")
        })
    </script>
</c:if>