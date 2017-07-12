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
$userid = $_POST["userid"];

echo $userid;
$useridINT = (int) $userid;
echo $userint;
//$useridINT = intval($userid);




$query = "DELETE FROM userLocal WHERE userid=$useridINT";

mysqli_query($conn, $query);



$conn->close();
?>