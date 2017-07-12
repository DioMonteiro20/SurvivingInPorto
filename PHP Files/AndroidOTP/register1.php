<?php 
	
	
	//If a post request comes to this script 
	if($_SERVER['REQUEST_METHOD']=='POST'){	
		//getting username password and phone number 
		$username = $_POST['username'];
		$password = $_POST['password'];
		$phone = $_POST['phone'];
		$otp = $_POST['otp'];
                $email = $_POST['email'];
		$pais = $_POST['pais'];
		$universidade = $_POST['universidade'];
		$genero = $_POST['genero'];
                $localidade = $_POST['localidade'];
		//Generating a 6 Digits OTP or verification code 
		//$otp = rand(100000, 999999);
		
		//Importing the db connection script 
		require_once('dbConnect.php');
		
		//Creating an SQL Query 
		$sql = "INSERT INTO androidotp (username, password, phone, otp, email, pais, universidade, genero, localidade) values ('$username','$password','$phone','$otp', '$email','$pais','$universidade','$genero', '$localidade')";
		
		//If the query executed on the db successfully 
		if(mysqli_query($con,$sql)){
			//printing the response given by sendOtp function by passing the otp and phone number 
                        echo '{"ErrorMessage":"Success"}';
		}else{
			//printing the failure message in json 
			echo '{"ErrorMessage":"Failure"}';
		}
		
		//Closing the database connection 
		mysqli_close($con);
	}
        
        ?>