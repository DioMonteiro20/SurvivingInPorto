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
$data1=$_POST["data1"];
$hora1=$_POST["hora1"];
$nome1= $_POST["nome1"];
$descricao1=$_POST["descricao1"];
$local1=$_POST["local1"];
$custo1=$_POST["custo1"];
$latitude1= $_POST["latitude1"];
$longitude1=$_POST["longitude1"];
$responsavel1=$_POST["responsavel1"];
$contacto1= $_POST["contacto1"];

//$hora=$_POST["hora"];
//$horaINT=(int) $hora;
//$min=$_POST["min"];
//$minINT=(int) $min;




$query = "INSERT INTO eventos (id, data, hora, nome, descricao, local, custo, latitude, longitude, responsavel, contacto) VALUES (NULL, '$data1', '$hora1', '$nome1', '$descricao1', '$local1', '$custo1', '$latitude1', '$longitude1', '$responsavel1', '$contacto1')";

mysqli_query($conn, $query);



$conn->close();
?>