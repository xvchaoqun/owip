<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/11/8
  Time: 0:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

       <div class="row">
      <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-12">
          <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
              <li class="active">
                <a data-toggle="tab" href="#home4">入党申请</a>
              </li>

              <li>
                <a data-toggle="tab" href="#profile4">积极分子</a>
              </li>

              <li>
                <a data-toggle="tab" href="#profile5">发展对象</a>
              </li>
              <li>
                <a data-toggle="tab" href="#profile6">预备党员</a>
              </li>
              <li>
                <a data-toggle="tab" href="#profile7">正式党员</a>
              </li>
            </ul>

            <div class="tab-content">
              <div id="home4" class="tab-pane in active">
                <div class="clearfix">
                  <div class="pull-right tableTools-container"></div>
                </div>
                <div class="table-header">
                  查询"新申请"结果
                </div>

                <!-- div.table-responsive -->

                <!-- div.dataTables_borderWrap -->
                <div>
                  <table id="dynamic-table" class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                      <th class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </th>
                      <th>工号</th>
                      <th>姓名</th>
                      <th>性别</th>

                      <th class="hidden-480">
                        <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                        申请时间
                      </th>
                      <th class="hidden-480">状态</th>

                      <th></th>
                    </tr>
                    </thead>

                    <tbody>


                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td class="center">
                        <label class="pos-rel">
                          <input type="checkbox" class="ace" />
                          <span class="lbl"></span>
                        </label>
                      </td>

                      <td>
                        <a href="#">1101142321</a>
                      </td>
                      <td>张三</td>
                      <td>男</td>
                      <td class="hidden-480">2015-12-28</td>

                      <td class="hidden-480">
                        <span class="label label-sm label-info arrowed arrowed-righ">新申请</span>
                      </td>

                      <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                          <a class="blue" href="#">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                          </a>

                          <a class="green" href="#">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                          </a>

                          <a class="red" href="#">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                          <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                              <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                              <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
                                </a>
                              </li>

                              <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
                                </a>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>

              <div id="profile4" class="tab-pane">
                <p>积极分子</p>
              </div>

              <div id="profile5" class="tab-pane">
                <p>发展对象</p>
              </div>
              <div id="profile6" class="tab-pane">
                <p>预备党员</p>
              </div>
              <div id="profile7" class="tab-pane">
                <p>正式党员</p>
              </div>
            </div>
          </div>
        </div>



        <!-- PAGE CONTENT ENDS -->
      </div>
      <!-- /.col -->
    </div>


<!-- page specific plugin scripts -->
<script src="assets/js/dataTables/jquery.dataTables.js"></script>
<script src="assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
<script src="assets/js/dataTables/extensions/buttons/dataTables.buttons.js"></script>
<script src="assets/js/dataTables/extensions/buttons/buttons.flash.js"></script>
<script src="assets/js/dataTables/extensions/buttons/buttons.html5.js"></script>
<script src="assets/js/dataTables/extensions/buttons/buttons.print.js"></script>
<script src="assets/js/dataTables/extensions/buttons/buttons.colVis.js"></script>
<script src="assets/js/dataTables/extensions/select/dataTables.select.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
  jQuery(function($) {
    //initiate dataTables plugin
    var myTable =
            $('#dynamic-table')
              //.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
                    .DataTable( {
                      "language" : {
                        "url" : "extend/js/jquery.dataTables.zh_CN.json"
                      },
                      bAutoWidth: false,
                      "aoColumns": [
                        { "bSortable": false },
                        null, null,null, null, null,
                        { "bSortable": false }
                      ],
                      "aaSorting": [],


                      //"bProcessing": true,
                      //"bServerSide": true,
                      //"sAjaxSource": "http://127.0.0.1/table.php"	,

                      //,
                      //"sScrollY": "200px",
                      //"bPaginate": false,

                      //"sScrollX": "100%",
                      //"sScrollXInner": "120%",
                      //"bScrollCollapse": true,
                      //Note: if you are applying horizontal scrolling (sScrollX) on a ".table-bordered"
                      //you may want to wrap the table inside a "div.dataTables_borderWrap" element

                      //"iDisplayLength": 50


                      select: {
                        style: 'multi'
                      }
                    } );



    $.fn.dataTable.Buttons.swfPath = "assets/js/dataTables/extensions/buttons/swf/flashExport.swf"; //in Ace demo ../assets will be replaced by correct assets path
    $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

    new $.fn.dataTable.Buttons( myTable, {
      buttons: [
        {
          "extend": "colvis",
          "text": "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>Show/hide columns</span>",
          "className": "btn btn-white btn-primary btn-bold",
          columns: ':not(:first):not(:last)'
        },
        {
          "extend": "copy",
          "text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
          "className": "btn btn-white btn-primary btn-bold"
        },
        {
          "extend": "csv",
          "text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
          "className": "btn btn-white btn-primary btn-bold"
        },
        {
          "extend": "excel",
          "text": "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>Export to Excel</span>",
          "className": "btn btn-white btn-primary btn-bold"
        },
        {
          "extend": "pdf",
          "text": "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>Export to PDF</span>",
          "className": "btn btn-white btn-primary btn-bold"
        }
      ]
    } );
    myTable.buttons().container().appendTo( $('.tableTools-container') );

    //style the message box
    var defaultCopyAction = myTable.button(1).action();
    myTable.button(1).action(function (e, dt, button, config) {
      defaultCopyAction(e, dt, button, config);
      $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
    });


    var defaultColvisAction = myTable.button(0).action();
    myTable.button(0).action(function (e, dt, button, config) {

      defaultColvisAction(e, dt, button, config);


      if($('.dt-button-collection > .dropdown-menu').length == 0) {
        $('.dt-button-collection')
                .wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
                .find('a').attr('href', '#').wrap("<li />")
      }
      $('.dt-button-collection').appendTo('.tableTools-container .dt-buttons')
    });

    ////

    setTimeout(function() {
      $($('.tableTools-container')).find('a.dt-button').each(function() {
        var div = $(this).find(' > div').first();
        if(div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
        else $(this).tooltip({container: 'body', title: $(this).text()});
      });
    }, 500);


    myTable.on( 'select', function ( e, dt, type, index ) {
      if ( type === 'row' ) {
        $( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
      }
    } );
    myTable.on( 'deselect', function ( e, dt, type, index ) {
      if ( type === 'row' ) {
        $( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
      }
    } );




    /////////////////////////////////
    //table checkboxes
    $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

    //select/deselect all rows according to table header checkbox
    $('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
      var th_checked = this.checked;//checkbox inside "TH" table header

      $('#dynamic-table').find('tbody > tr').each(function(){
        var row = this;
        if(th_checked) myTable.row(row).select();
        else  myTable.row(row).deselect();
      });
    });

    //select/deselect a row when the checkbox is checked/unchecked
    $('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
      var row = $(this).closest('tr').get(0);
      if(!this.checked) myTable.row(row).deselect();
      else myTable.row(row).select();
    });



    $(document).on('click', '#dynamic-table .dropdown-toggle', function(e) {
      e.stopImmediatePropagation();
      e.stopPropagation();
      e.preventDefault();
    });



    //And for the first simple table, which doesn't have TableTools or dataTables
    //select/deselect all rows according to table header checkbox
    var active_class = 'active';
    $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
      var th_checked = this.checked;//checkbox inside "TH" table header

      $(this).closest('table').find('tbody > tr').each(function(){
        var row = this;
        if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
        else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
      });
    });

    //select/deselect a row when the checkbox is checked/unchecked
    $('#simple-table').on('click', 'td input[type=checkbox]' , function(){
      var $row = $(this).closest('tr');
      if(this.checked) $row.addClass(active_class);
      else $row.removeClass(active_class);
    });



    /********************************/
    //add tooltip for small view action buttons in dropdown menu
    $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});

    //tooltip placement on right or left
    function tooltip_placement(context, source) {
      var $source = $(source);
      var $parent = $source.closest('table')
      var off1 = $parent.offset();
      var w1 = $parent.width();

      var off2 = $source.offset();
      //var w2 = $source.width();

      if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
      return 'left';
    }


  })
</script>
