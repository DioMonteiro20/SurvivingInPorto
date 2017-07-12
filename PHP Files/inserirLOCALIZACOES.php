<?php
$host = "pdb6.awardspace.net"; 
$user = "2124939_survport"; 
$pass = "survporto16"; 
$db = "2124939_survport"; 

// Create connection
$conn = new mysqli($host, $user, $pass, $db);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


//Recebendo a informação
$latitude=$_POST["latitude"];
$longitude=$_POST["longitude"];
$userid = $_POST["userid"];
$useridINT = (int) $userid;


date_default_timezone_set("Europe/Lisbon"); //especifica o fuso horario pretendido para o servidor

$min = date("i"); //servidor da os minutos
$hora = date("H"); //servidor da as horas


$minINT=(int) $min;
$horaINT = (int) $hora;




$query = "INSERT INTO userLocal (id,latitude,longitude,userid,hora,min) VALUES (NULL, '$latitude', '$longitude',$useridINT,$horaINT,$minINT)";

mysqli_query($conn, $query);



$conn->close();
?>