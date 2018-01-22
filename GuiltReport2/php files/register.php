<?php
require "conn.php";
$user_name = $_POST["username"];
$user_pass = $_POST["password"];
$name = $_POST["name"];
$surname = $_POST["surname"];

$mysql_qry = "insert into userinfo (username, password, name, surname) values ('$user_name', '$user_pass', '$name', '$surname')";

if($conn->query($mysql_qry) === TRUE){
	echo "Registiration completed!";
}
else{
	echo "Registiration failed!";
}
$conn->close();
?>