<?php

$id = $_GET['id'];

$img = @imagecreatefrompng ("logos/$id.png");
 if(!$img){
	$img = imagecreatefrompng ("logos/-1.png");	 
 }
header("Content-Type: image/png");
imagepng ($img);
imagedestroy($img);

?>
