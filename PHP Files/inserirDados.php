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
$username1=$_POST["username1"];
$password1=$_POST["password1"];
$phone1= $_POST["phone1"];
$email1=$_POST["email1"];
$pais1=$_POST["pais1"];
$universidade1= $_POST["universidade1"];
$genero1=$_POST["genero1"];
$localidade1= $_POST["localidade1"];

//$hora=$_POST["hora"];
//$horaINT=(int) $hora;
//$min=$_POST["min"];
//$minINT=(int) $min;




$query ="UPDATE `androidotp` SET `username` = '$username1', `password`='$password1',`phone`='$phone1', `email`='$email1',`pais`='$pais1',`universidade`='$universidade1',`genero`='$genero1',`localidade`='$localidade1' WHERE `androidotp`.`username`='$username1'";

mysqli_query($conn, $query);



$conn->close();
?>