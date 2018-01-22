<?php
require "conn.php";
$row_ID = $_POST["row_ID"];
$header = $_POST["header"];
$text = $_POST["text"];

$mysql_qry = "UPDATE reports SET header = '$header', text = '$text' WHERE row_id = '$row_ID'";

if($conn->query($mysql_qry) === TRUE){
	echo "Update completed!";
}
else{
	echo "Update failed!";
}
$conn->close();
?>