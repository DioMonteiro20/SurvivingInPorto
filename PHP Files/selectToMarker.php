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
         
	 $sql = "SELECT username, phone, email, pais, universidade, genero, localidade, latitude, longitude from androidotp JOIN userLocal on androidotp.id=userLocal.userid";

	 $dados = mysqli_query($con,$sql); 
      
	 $response = array(); 
        
         
	  while($row=mysqli_fetch_array($dados))
	   { 

	   	array_push($response,array('username'=>$row[0],phone=>$row[1],'email'=>$row[2],'pais'=>$row[3],'universidade'=>$row[4],'genero'=>$row[5],'localidade'=>$row[6],'latitude'=>$row[7],'longitude'=>$row[8]));
                
   
	   	 }

	   	 echo json_encode(array('dados'=>$response));
    
	   	   mysqli_close($con); 

	   	    ?>