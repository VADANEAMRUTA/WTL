<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST['t1'];
    $mobile = $_POST['t2'];
    $roll_no = $_POST['t3'];
    $email = $_POST['t4'];
    $address = $_POST['t5'];

    // You can process or store the data in a database here

    echo "<script>alert('Form submitted successfully!'); window.location.href='index.html';</script>";
}
?>
