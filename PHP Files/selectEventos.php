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
         
	 $sql = "select * from eventos WHERE verificado = 1 order by data";

	 $eventos = mysqli_query($con,$sql); 
      
	 $response = array(); 
        
         
	  while($row=mysqli_fetch_array($eventos))
	   { 
	   	//echo "<br>id : ".$row[0];
	   	//echo "<br>Nome : ".$row[1]; 
	   	array_push($response,array('id'=>$row[0],'data'=>$row[1],'hora'=>$row[2], 'nome'=>$row[3], 'descricao'=>$row[4], 'local'=>$row[5], 'custo'=>$row[6], 'latitude'=>$row[7], 'longitude'=>$row[8]));
                
                //array_push($response,array($row[0], $row[1], $row[2]));
	   	 }

	   	 echo json_encode(array('eventos'=>$response));
                 //echo json_encode(array($response));
	   	   mysqli_close($con); //echo "Hello world...";

	   	    ?>﻿