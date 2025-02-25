<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JustInTime</title>
    <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/homepagestyle.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/footer.css">
</head>

<body>
<div id="header-container">
    <header class="header">
        <div class="logo">
            <a title=""  href="" class="logo-image" id="logo-image">
                <img src="../images/logo.png" alt="">
            </a>
            <div class="logo-text"><span style="color: #FFEDDC;">Just</span><span style="color: #DDA15E;">In</span><span style="color: #E48826;">Time</span>
            </div>
        </div>

        <nav class="nav-links">
            <a href="#rules">Regolamento</a>
            <a href="#login">Login</a>
        </nav>
    </header>
</div>

<main>
    <div class="welcome-container">
        <h1 id="welcome">Benvenuto in</h1>
        <h1 class="title">
            <span style="color: #FFEDDC;">Just</span><span style="color: #DDA15E;">In</span><span style="color: #E48826;">Time</span>
        </h1>
        <button class="new-game-button">Nuova Partita</button>
    </div>
</main>

<div id="footer-container">
    <footer class="footer">
        <div class="nav-links">
            <a href="#contatti">Contatti</a>
        </div>
        <div class="logo">
            <img class="logo-image" src="../images/logo.png" alt="">
        </div>
    </footer>
</div>
</body>
</html>
