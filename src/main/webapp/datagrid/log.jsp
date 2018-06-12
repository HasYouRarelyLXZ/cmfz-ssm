<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
    var toolbar = [{
        iconCls: 'icon-add',
        text: "自定义导出日志",
        handler: function () {
            $("#log_dialog").dialog("open")
        }
    }];

    $("#btn_log").click(function () {
        //提交form表单
        var titles = $("#log_customer_cc").combotree("getText");  //获取中文名字符串
        var values = $("#log_customer_cc").combotree("getValues");//获取列属性字段
        var c = "";
        //遍历拼接英文字符串
        $.each(values, function (index, title) {
            if (index != values.length - 1) {
                c += title + ",";
            } else {
                c += title;
            }
        });
        //提交form表单
        $("#log_customer_form").form('submit', {
            queryParams: {"titles": titles, "fileds": c},
            url: "${pageContext.request.contextPath}/log/export.do?",
        });

    });

    $(function () {
        $('#Log_dg').datagrid({
            url: "${pageContext.request.contextPath}/log/getLogJson.do?",
            pageList: [5, 10, 15, 20],
            pageSize: 5,
            toolbar: toolbar,
            rownumbers: true,
            fit: true,
            fitColumns: true,
            pagination: true,
            singleSelect: false,
            columns: [[
                {field: 'id', title: 'ID', checkbox: true},
                {field: 'name', title: '用户'},
                {field: 'date', title: '时间'},
                {field: 'transaction', title: '操作'},
                {field: 'result', title: '操作结果'},
            ]],
        });
    });
</script>

<%--datagrid--%>
<table id="Log_dg"></table>

<div id="log_dialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
    <form id="log_customer_form" method="post">
        <select id="log_customer_cc" class="easyui-combotree" style="width:200px;"
                data-options="url:'${pageContext.request.contextPath}/json/log_comboTree.json',required:true,checkbox:true,onlyLeafCheck:true,multiple:true"></select>
    </form>
    <a id="btn_log" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">确定导出</a>
</div>

