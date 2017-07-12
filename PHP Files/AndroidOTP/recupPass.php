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
$destino1=$_POST["destino1"];
$password=$_POST["password"];


//$hora=$_POST["hora"];
//$horaINT=(int) $hora;
//$min=$_POST["min"];
//$minINT=(int) $min;

$sql_query = "select * from androidotp where phone like '$destino1' and verified=1;";  
 
$result = mysqli_query($conn,$sql_query);  
 
         if(mysqli_num_rows($result) >0 )  
         {  
         
                 $query ="UPDATE `androidotp` SET `password` = '$password' WHERE `androidotp`.`phone`='$destino1'";
                 if(mysqli_query($conn, $query))
                 {
                 echo "Enviada";
                 }
                 else{
                   echo "Falha no envio";  
                 }

         }  
         else  
         {   
                 echo "Login Failed.......Try Again..";  
         }  



//$conn->close();
?>