<?php 

$host = "pdb6.awardspace.net"; 
$user = "2124939_survport"; 
$pass = "survporto16"; 
$db = "2124939_survport"; 


$con = mysqli_connect($host,$user,$pass,$db); 

if(!$con) 
	{ 
		//die("Error ".mysqli_connect_error());
	 } 
	 else 
	 { 

	 	//echo "<h3>connection success</h3>"; 

	 } 
         
	 $sql = "SELECT otp from androidotp ORDER BY id DESC LIMIT 1";
      

	 $users = mysqli_query($con,$sql); 
      
	 $response = array(); 
        
         
	  while($row=mysqli_fetch_array($users))
	   { 
	   	//echo "<br>id : ".$row[0];
	   	//echo "<br>Nome : ".$row[1]; 
	   	array_push($response,array('otp'=>$row[0]));
                
                //array_push($response,array($row[0], $row[1], $row[2]));
	   	 }

	   	 echo json_encode(array('users'=>$response));
                 //echo json_encode(array($response));
	   	   mysqli_close($con); //echo "Hello world...";

	   	    ?>﻿