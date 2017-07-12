    <?php 

    $response = array();

date_default_timezone_set("Europe/Lisbon"); //especifica o fuso horario pretendido para o servidor

$min = date("i"); //servidor da os minutos
$hora = date("H"); //servidor da as horas


$minINT=(int) $min;
$horaINT = (int) $hora;
//echo "$horaINT h e $minINT min";

      //  $response["horaCorrente"] = $horaINT;
      //  $response["minCorrente"] = $minINT;

array_push($response,array(horaCorrente=>$horaINT,minCorrente=> $minINT));

        // echoing JSON response
       echo json_encode(array('hora'=>$response));
         

	   	    ?>






















