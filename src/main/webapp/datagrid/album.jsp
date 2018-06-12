<%@ page language="java" contentType="text/html; charset=utf-8" isELIgnored="false" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
    var toolbar = [{
        iconCls: 'icon-edit',
        text: "专辑详情",
        handler: function () {
            var row = $("#album").treegrid("getSelected")  //获取选中行
            if (row == null) {
                alert("请先选中行！！！")
            } else {
                if (row.size == null) {   //根据专辑中不包含章节的属性内容来确定选中行为专辑
                    $("#album_dialog").dialog("open")  //打开一个对话框
                    /*填充内容*/
                    $("#album_ff").form("load", row);
                    $("#img").prop("src", row.img);
                } else {
                    alert("请先选专辑！！！")
                }
            }
            //获取专辑


        }
    }, '-', {
        iconCls: 'icon-help',
        text: "添加专辑",
        handler: function () {
            $("#Album_dd").dialog("open");
        }
    }, '-', {
        iconCls: 'icon-help',
        text: "添加章节",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row == null) {
                alert("请先选中行！！！")
            } else {
                if (row.size == null) {   //判断有没有size这个属性的来判断是不是专辑
                    $("#chapter_dialog").dialog("open");
                    console.log(row.id + "+++++++++++++++++++++++")
                    $("#album_id").textbox("setValue", row.id);
                } else {
                    alert("请先选专辑！！！")
                }
            }

        }
    }, '-', {
        iconCls: 'icon-help',
        text: "下载音频",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row.size == null) {
                alert("请先选中要下载的章节");
            } else {
                location.href = "${pageContext.request.contextPath}/chapter/download.do?url=" + row.uploadPath + "&name=" + row.oldname;
            }
        }
    }]


    $(function () {
        $('#album').treegrid({
            url: '${pageContext.request.contextPath}/album/getAlbumJson.do',
            idField: 'id',
            treeField: 'title',
            //双击打开音乐播放器
            onDblClickRow: function (row) {
                $("#album_paly").dialog("open");
                $("#audio").prop("src", row.uploadPath);
            },
            pageList: [5, 10, 15, 20],
            pageSize: 10,
            toolbar: toolbar,
            fit: true,
            fitColumns: true,
            animate: true,
            collapsible: true,
            pagination: true,
            columns: [[
                {field: 'title', title: '名称', width: 60, align: 'center'},
                {field: 'size', title: '大小', width: 80},
                {field: 'duration', title: '时长', width: 80},
                {field: 'uploadPath', title: '路径', width: 80},
                {field: 'uploadDate', title: '上传时间', width: 80},
                {field: 'times', title: '下载次数', width: 80},
            ]]
        });

    })


</script>

<table id="album"></table>
<%--添加专辑dialog--%>
<div id="Album_dd" class="easyui-dialog" title="添加专辑" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:[{
				text:'保存',
				handler:function(){
                $('#addAlbum_ff').form('submit', {
                 url:'${pageContext.request.contextPath}/album/add.do',

                 })
				}
			},{
				text:'关闭',
				handler:function(){
				$('#Album_dd').dialog('close');
				}
			}]">
    <%--添加专辑--%>
    <form id="addAlbum_ff" method="post" enctype="multipart/form-data">
        <div>
            <label for="zjmc">专辑名称:</label>
            <input class="easyui-validatebox" id="zjmc" type="text" name="title"/>
        </div>
        <div>
            <label>封面:</label>
            <input class="easyui-filebox" name="imgPath" style="width:300px">
        </div>
        <div>
            <label for="jj">简介:</label>
            <input class="easyui-validatebox" type="text" id="jj" name="brief"/>
        </div>
        <div>
            <label for="pf">评分:</label>
            <input class="easyui-validatebox" type="text" id="pf" name="score"/>
        </div>
        <div>
            <label for="zz">作者:</label>
            <input class="easyui-validatebox" id="zz" type="text" name="author"/>
        </div>
        <div>
            <label for="by">播音:</label>
            <input class="easyui-validatebox" id="by" type="text" name="broadCast"/>
        </div>

    </form>
</div>

<%--展示专辑信息dialog--%>
<div id="album_dialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,resizable:true">
    <%--展示专辑信息--%>
    <form id="album_ff" method="post">
        <div>
            <label for="name">名称:</label>
            <input class="easyui-validatebox" id="name" type="text" name="title"/>
        </div>
        <div>
            <label for="count">集数:</label>
            <input class="easyui-validatebox" type="text" id="count" name="count"/>
        </div>
        <div>
            <label for="title">名称:</label>
            <input class="easyui-validatebox" id="title" type="text" name="title"/>
        </div>
        <div>
            <label for="coverImg">封面:</label>
            <img id="coverImg" width="160px" href="20px" src="../img/yingbao.jpg">
        </div>
        <div>
            <label for="score">评分:</label>
            <input class="easyui-validatebox" id="score" type="text" name="score"/>
        </div>
        <div>
            <label for="author">作者:</label>
            <input class="easyui-validatebox" id="author" type="text" name="author"/>
        </div>
        <div>
            <label for="broadCast">播音:</label>
            <input class="easyui-validatebox" id="broadCast" type="text" name="broadCast"/>
        </div>
        <div>
            <label for="brief">播放:</label>
            <input class="easyui-validatebox" id="brief" type="text" name="brief"/>
        </div>
        <div>
            <label for="publishDate">发布日期:</label>
            <input class="easyui-validatebox" id="publishDate" type="text" name="publishDate"/>
        </div>
    </form>

</div>
<%--添加章节dralog--%>
<div id="chapter_dialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,buttons:[{
				text:'保存',
				handler:function(){
                  $('#chapter_ff').form('submit',{
                  url:'${pageContext.request.contextPath}/chapter/add.do'
                  })
				}
			},{
				text:'关闭',
				handler:function(){
                    $('#chapter_dialog').dialog('close');
				}
			}]">
    <%--添加章节form--%>
    <form id="chapter_ff" method="post" enctype="multipart/form-data">
        <div hidden="hidden">
            <label for="album_id">id:</label>
            <input class="easyui-textbox" value="0" name="id" id="album_id"/>
        </div>
        <div>
            <label for="file">上传:</label>
            <input class="easyui-filebox" id="file" name="addFile"/>
        </div>

    </form>

</div>

<%--音乐播放器--%>
<div id="album_paly" class="easyui-dialog" title="My Audio" style="width:400px;height:200px;"
     data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">

    <audio id="audio" src="" controls="controls" autoplay="autoplay"></audio>
</div>
