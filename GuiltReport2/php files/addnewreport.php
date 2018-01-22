<?php
require "conn.php";
$user_name = $_POST["username"];
$header = $_POST["header"];
$text = $_POST["text"];
$location_x = $_POST["location_x"];
$location_y = $_POST["location_y"];

$mysql_qry = "insert into reports (username, header, text, location_x, location_y) values ('$user_name', '$header', '$text', '$location_x', '$location_y')";

if($conn->query($mysql_qry) === TRUE){
	$lastID = mysqli_insert_id($conn);
	echo "Insert completed! Report id is : $lastID";
}
else{
	echo "Insert failed!";
}

$conn->close();
?>