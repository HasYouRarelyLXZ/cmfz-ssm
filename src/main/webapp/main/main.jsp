<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>持名法州主页</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/IconExtension.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/china.js"></script>

    <script type="text/javascript">
        <!--菜单处理-->
        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/Menu/showMenu.do",
                type: "get",
                dataType: "json",
                success: function (data) {
                    /*第一个参数为集合对象  第二个参数为下标   第三个参数是遍历出来的元素*/
                    $.each(data, function (index, first) {
                        var c = "";
                        console.log(data);
                        $.each(first.childMenu, function (index2, second) {
                            console.log(second.iconCls);
                            c += "<p style='text-align: center'><a href='#' onclick=\"addTabs('" + second.title + "','" + second.url + "','" + second.iconCls + "')\" class='easyui-linkbutton'  data-options=\"iconCls:'" + second.iconCls + "'\">" + second.title + "</a></p>";
                        });
                        /*添加数据到菜单栏*/
                        $('#aa').accordion('add', {
                            title: first.title,
                            content: c,
                            selected: true,
                            iconCls: first.iconCls
                        });
                    })

                }
            })
        });

        function addTabs(title, url, iconCls) {
            /*
            *存在选中  不存在添加
            * */
            var flag = $("#tt").tabs("exists", title);
            if (flag) {
                $("#tt").tabs("select", title);
            } else {
                $('#tt').tabs('add', {
                    title: title,
                    selected: true,
                    iconCls: iconCls,
                    href: "${pageContext.request.contextPath}/" + url,
                    closable: true
                });
            }


        }

    </script>

</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true" style="height:60px;background-color:  #5C160C">
    <div style="font-size: 24px;color: #FAF7F7;font-family: 楷体;font-weight: 900;width: 500px;float:left;padding-left: 20px;padding-top: 10px">
        持名法州后台管理系统
    </div>
    <div style="font-size: 16px;color: #FAF7F7;font-family: 楷体;width: 300px;float:right;padding-top:15px">
        欢迎您:${sessionScope.tuser} &nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改密码</a>&nbsp;&nbsp;<a
            href="${pageContext.request.contextPath}/admin/loginout.do" class="easyui-linkbutton"
            data-options="iconCls:'icon-01'">退出系统</a></div>
</div>
<div data-options="region:'south',split:true" style="height: 40px;background: #5C160C">
    <div style="text-align: center;font-size:15px; color: #FAF7F7;font-family: 楷体">&copy;百知教育 htf@zparkhr.com.cn</div>
</div>

<div data-options="region:'west',title:'导航菜单',split:true" style="width:220px;">
    <div id="aa" class="easyui-accordion" data-options="fit:true">

    </div>
</div>
<div data-options="region:'center'">
    <div id="tt" class="easyui-tabs" data-options="fit:true,narrow:true,pill:true">
        <div title="主页" data-options="iconCls:'icon-neighbourhood',"
             style="background-image:url(${pageContext.request.contextPath}/main/image/shouye.jpg);background-repeat: no-repeat;background-size:100% 100%;"></div>
    </div>
</div>
</body>
</html>