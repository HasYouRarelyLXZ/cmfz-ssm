<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
    var toolbar = [{
        iconCls: 'icon-add',
        text: "导入excel",
        handler: function () {
            $("#dd").dialog("open");
        }
    }, '-', {
        iconCls: 'icon-add',
        text: "自定义导出用户",
        handler: function () {
            $("#custom_dialog").dialog("open")
        }
    }, '-', {
        iconCls: 'icon-edit',
        text: "冻结用户",
        handler: function () {
            var row = $("#user_dg").datagrid("getSelected");
            if (row == null) {
                alert("请选中行")
            } else {
                if (row.status == '冻结') {
                    alert("该用户已经被冻结了,请执行其他操作");
                } else {  //执行冻结操作
                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/user/islockUser.do",
                        data: {id: row.id, flag: true},
                        success: function () {
                            $('#user_dg').datagrid('reload');
                            alert("操作成功");
                        }
                    });
                }
            }
        }
    }, '-', {
        iconCls: 'icon-save',
        text: "解冻用户",
        handler: function () {
            var row = $("#user_dg").datagrid("getSelected");
            if (row == null) {
                alert("请选中行")
            } else {
                if (row.status == '正常') {
                    alert("该用户是正常状态,请执行其他操作");
                } else {  //执行解冻结操作
                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/user/islockUser.do",
                        data: {id: row.id, flag: false},
                        success: function () {
                            $('#user_dg').datagrid('reload');
                            alert("操作成功");
                        }
                    });
                }
            }
        }
    }];

    $("#btn").click(function () {
        //提交form表单
        var titles = $("#customer_cc").combotree("getText");  //获取中文名字符串
        var values = $("#customer_cc").combotree("getValues");//获取列属性字段
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
        $("#customer_form").form('submit', {
            queryParams: {"titles": titles, "fileds": c},
            url: "${pageContext.request.contextPath}/user/export.do?",
        });
        $("custom_dialog").dialog("closed");
    });

    $(function () {
        $('#user_dg').datagrid({
            url: "${pageContext.request.contextPath}/user/showAllUser.do?",
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
                {field: 'username', title: '用户名'},
                {field: 'sex', title: '性别'},
                {field: 'phoneNum', title: '手机号'},
                {field: 'dharmaName', title: '法名'},
                {field: 'code', title: '盐值'},
                {field: 'province', title: '归属地'},
                {field: 'city', title: '城市'},
                {field: 'headPic', title: '头像'},
                {field: 'sign', title: '签名'},
                {field: 'date', title: '注册日期'},
                {
                    field: 'status', title: '用户状态',
                    formatter: function (value, row, index) {
                        if (value == '冻结') {
                            return '0'
                        }
                        else if (value == '正常') {
                            return '1'
                        }
                    }
                },
                {field: 'duru_id', title: '上师ID'}
            ]],
        });
    });
</script>

<%--datagrid--%>
<table id="user_dg"></table>

<div id="custom_dialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">
    <form id="customer_form" method="post">
        <select id="customer_cc" class="easyui-combotree" style="width:200px;"
                data-options="url:'${pageContext.request.contextPath}/json /comboTree.json',required:true,checkbox:true,onlyLeafCheck:true,multiple:true"></select>
    </form>
    <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">确定导出</a>
</div>

