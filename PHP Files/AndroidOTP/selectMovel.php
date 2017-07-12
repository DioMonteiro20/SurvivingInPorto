<?php  

 require "dbConnect.php";  

 
 
 $destino1 = $_POST["destino1"]; 

 
 
 $sql_query = "select phone from androidotp where username ='$destino1';";  
 
$result = mysqli_query($con,$sql_query);  
$response = array(); 
        
         
	  while($row=mysqli_fetch_array($result))
	   { 
	   	//echo "<br>id : ".$row[0];
	   	//echo "<br>Nome : ".$row[1]; 
	   	array_push($response,array('phone'=>$row[0]));
                
                //array_push($response,array($row[0], $row[1], $row[2]));
	   	 }

	   	 echo json_encode(array('result'=>$response));
                 //echo json_encode(array($response));
	   	   mysqli_close($con); //echo "Hello world...";

	   	    ?>﻿