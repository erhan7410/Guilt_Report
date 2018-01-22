<?php
require "conn.php";
$row_ID = $_POST["row_ID"];


$mysql_qry = "DELETE FROM reports WHERE row_id = '$row_ID'";

if($conn->query($mysql_qry) === TRUE){
	echo "Delete completed!";
}
else{
	echo "Delete failed!";
}
$conn->close();
?>