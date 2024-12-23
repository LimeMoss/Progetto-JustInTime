<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JustInTime</title>
    <link href="../css/header.css" rel="stylesheet">
    <link href="../css/homepagestyle.css" rel="stylesheet">
</head>
<body>

<!-- Include dell'header -->
<jsp:include page="../WEB-INF/header.jsp" />

<!-- Contenuto principale -->
<main>
    <div class="welcome-container">
        <h1 id="welcome">Benvenuto in</h1>
        <h1 class="title">
            <span style="color: #FFEDDC;">Just</span><span style="color: #DDA15E;">In</span><span style="color: #E48826;">Time</span>
        </h1>
        <button class="new-game-button">Nuova Partita</button>
    </div>
</main>

</body>
</html>
