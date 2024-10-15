<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이미지 목록</title>
    <style type="text/css">
        table {
            border-collapse: collapse;
            width: 100%; /* 테이블 너비 100% */
        }

        th, td {
            padding: 5px;
            text-align: center; /* 중앙 정렬 */
        }

        th {
            background-color: #f2f2f2; /* 헤더 배경 색상 */
        }
    </style>
   
</head>
<body>

<table border="1" frame="hsides" rules="rows">
    <tr>
        <th>
            <input type="checkbox" name="selectAll" onclick="checkAll(this)"> 전체체크
        </th>
        <th width="100">번호</th>
        <th width="200">이미지</th>
        <th width="200">상품명</th>
    </tr>
    
    <c:forEach var="userUploadDTO" items="${list}">
        <tr>
            <td>
                <input type="checkbox" name="imageCheckbox" value="${userUploadDTO.seq}" onchange="updateAll()"> <!-- 시퀀스 값 -->
            </td>
            <td>${userUploadDTO.seq}</td>
            <td>
                <a href="/spring/user/uploadView?seq=${userUploadDTO.seq}">
                    <img src="https://kr.object.ncloudstorage.com/bitcamp-9th-bucket-133/storage/${userUploadDTO.imageFileName}"
                         alt="${userUploadDTO.imageName}" width="50" height="50" />
                </a>
            </td>
            <td>${userUploadDTO.imageName}</td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4">
            <button onclick="deleteImg()">체크된 이미지 삭제하기</button>
        </td>
    </tr>
</table>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script src="../js/uploadDelete.js"></script>


</body>
</html>