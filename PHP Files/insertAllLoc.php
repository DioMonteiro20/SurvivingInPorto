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
$useridINT = (int)$userid;






$query = "INSERT INTO AllLocal (id,iduser,latitude,longitude) VALUES (NULL,$useridINT, '$latitude', '$longitude')";

mysqli_query($conn, $query);



$conn->close();
?>