<?php  

 require "dbConnect.php";  

 
 
 $campo = $_POST["campo"]; 
 $procura1 = $_POST["procura1"];
 
 
 $sql_query = "select username, phone, email, pais, universidade, genero, localidade from androidotp where $campo ='$procura1' and verified=1 order by id;";  
 
$result = mysqli_query($con,$sql_query);  
$response = array(); 
        
         
	  while($row=mysqli_fetch_array($result))
	   { 
	   	//echo "<br>id : ".$row[0];
	   	//echo "<br>Nome : ".$row[1]; 
	   	array_push($response,array('username'=>$row[0],'phone'=>$row[1], 'email'=>$row[2], 'pais'=>$row[3], 'universidade'=>$row[4], 'genero'=>$row[5], 'localidade'=>$row[6]));
                
                //array_push($response,array($row[0], $row[1], $row[2]));
	   	 }

	   	 echo json_encode(array('result'=>$response));
                 //echo json_encode(array($response));
	   	   mysqli_close($con); //echo "Hello world...";

	   	    ?>﻿