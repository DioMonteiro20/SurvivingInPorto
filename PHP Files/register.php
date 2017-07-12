<?php  
$servername = "fdb3.awardspace.net";
$username = "2124939_survport";
$password = "survporto16";
$dbname = "2124939_survport";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


//Recebendo a informaÃ§Ã£o
$username = $_POST["username"]; //tinha ' '
$password = $_POST["password"];

$sql = "SELECT username FROM user WHERE username='$username'";
$result = mysqli_query($conn,$sql);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);
 
if(mysqli_num_rows($result) >= 1)
{
	echo "Sorry...This email already exist..";
        
}
else
{
	$query = "INSERT INTO user (username,password) VALUES ('$username', '$password')";
 
mysqli_query($conn, $query);

}

$conn->close();


 ?>  