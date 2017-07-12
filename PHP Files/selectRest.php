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
         
	 $sql = "select * from restauracao WHERE verificado = 1 order by id ";

	 $restauracao = mysqli_query($con,$sql); 
      
	 $response = array(); 
        
         
	  while($row=mysqli_fetch_array($restauracao))
	   { 
	   	//echo "<br>id : ".$row[0];
	   	//echo "<br>Nome : ".$row[1]; 
	   	array_push($response,array('id'=>$row[0],'nome'=>$row[1], 'descricao'=>$row[2], 'latitude'=>$row[3], 'longitude'=>$row[4], 'responsavel'=>$row[5],  'contacto'=>$row[6]));
                
                //array_push($response,array($row[0], $row[1], $row[2]));
	   	 }

	   	 echo json_encode(array('restauracao'=>$response));
                 //echo json_encode(array($response));
	   	   mysqli_close($con); //echo "Hello world...";

	   	    ?>﻿