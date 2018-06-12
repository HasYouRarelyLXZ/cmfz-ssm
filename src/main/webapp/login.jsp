<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<head>
    <title>持名法州后台管理中心</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css"></link>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/common.js"></script>
    <script type="text/javascript">

        $(function(){
            //点击更换验证码：
            $("#captchaImage").click(function(){//点击更换验证码
                var timestamp = new Date().getTime();
                $(this).attr('src',$(this).attr('src') + '?' +timestamp );
            });
            $(".loginButton").click(function () {
                formCheck();
            })
        });
        function formCheck() {//表单验证
            var u =$("#u").val();
            var p =$("#p").val();
            var img = $("#enCode").val();
            if (u == null || u.length <= 0) {
                alert("用户名不能为空");
                return false;
            } else if (u.length < 5 || u.length > 20) {
                alert("用户名必须为5-20位");
                return false;
            } else if (p == null || p.length <= 0) {
                alert("密码不能为空");
                return false;
            } else if (p.length < 5 || p.length > 20) {
                alert("密码必须为5-20位");
                return false;
            } else {
               var flag= $.ajax({
                    type:"post",
                    url:"${pageContext.request.contextPath}/admin/loginAdmin.do",
                    data:$("#loginForm").serialize(),
                    dataType:"json",
                    cache:false,
                    async:true,
                    success:function(data){
                        var dataMsg=data.msg;
                        if(dataMsg=="登录成功"){
                            window.location.href="${pageContext.request.contextPath}/main/main.jsp";
                            //alert(dataMsg);
                            return true;

                        }else {
                            alert(dataMsg);
                            return false;
                        }
                    }

                })
                if(flag){
                  alert(123);
                }else{
                   console.log(flag)
                }
            }

        }
    </script>
</head>
<body>

<div class="login">
    <form id="loginForm"  method="post">

        <table>
            <tbody>
            <tr>
                <td width="190" rowspan="2" align="center" valign="bottom">
                    <img src="${pageContext.request.contextPath}/img/header_logo.gif" />
                </td>
                <th>
                    用户名:
                </th>
                <td>
                    <input id="u" type="text"  name="name" class="text" value="" maxlength="20"/>
                </td>
            </tr>
            <tr>
                <th>
                    密&nbsp;&nbsp;&nbsp;码:
                </th>
                <td>
                    <input id="p" type="password" name="password" class="text" value="" maxlength="20" autocomplete="off"/>
                </td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <th>验证码:</th>
                <td>
                    <input type="text" id="enCode" name="enCode" class="text captcha" maxlength="4" autocomplete="off"/>
                    <img id="captchaImage" class="captchaImage" src="${pageContext.request.contextPath}/image/code.do" title="点击更换验证码"/>
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
                <th>
                    &nbsp;
                </th>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <th>&nbsp;</th>
                <td>
                    <input type="button" class="homeButton" value="" onclick="location.href='/'"><input type="submit"  class="loginButton"  value="登录">
                </td>
            </tr>
            </tbody></table>
        <div class="powered">COPYRIGHT © 2008-2017.</div>
        <div class="link">
            <a href="http://www.chimingfowang.com/">持名佛网首页</a> |
            <a href="http://www.chimingbbs.com/">交流论坛</a> |
            <a href="">关于我们</a> |
            <a href="">联系我们</a> |
            <a href="">授权查询</a>
        </div>
    </form>
</div>
</body>
</html>