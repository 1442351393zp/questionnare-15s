<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<%=path %>/template/css/module/jquery.treetable.css">
    <link rel="stylesheet" href="<%=path %>/template/css/module/jquery.treetable.theme.default.css">
    <link rel="stylesheet" href="<%=path %>/template/css/institution.css">
    <title>机构管理</title>
</head
<body>
    <div class="institution-box">
        <div class="nav-btn">
            <button class="btn btn-primary expand" type="button">全部展开</button>
            <button class="btn btn-primary collapseAll" type="button">全部折叠</button>
        </div>
        <table id="institution-list">
            <thead>
                <th>机构名称</th>
                <th>机构代码</th>
            </thead>
            <tbody>
                <tr data-tt-id="1" data-tt-parent-id="root">
                    <td>机构名称</td>
                    <td>111</td>
                </tr>
                <tr data-tt-id="2" data-tt-parent-id="1">
                    <td>一部</td>
                    <td>111</td>
                </tr>
                <tr data-tt-id="3" data-tt-parent-id="2">
                    <td>一部</td>
                    <td>111</td>
                </tr>
                <tr data-tt-id="4" data-tt-parent-id="root">
                    <td>机构名称</td>
                    <td>111</td>
                </tr>
                <tr data-tt-id="5" data-tt-parent-id="4">
                    <td>机构名称</td>
                    <td>111</td>
                </tr>
            </tbody>
        </table>
    </div>
    <script src="<%=path %>/template/js/module/jquery.treetable.js"></script>
    <script src="<%=path %>/template/js/institution.js"></script>
</body>
</html>