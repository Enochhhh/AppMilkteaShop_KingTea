# AppMilkteaShop_KingTea
Front-end side with Android and Back-end side with Spring Boot

## Member
  - Phan Hồng Sơn: This project I'm in charge of both Front-end and Back-end

## Technology
  - Front-end: Android
  - Back-end: Java, Spring Boot
  - Database: MySQL

## Tool 
  - Eclipse IDE
  - Android Studio
  - MySQL Workbench 8.0

## Instructions for running the app
  First, you can install Eclipse IDE(JDK 1.8), Android Studio, and MySQL Workbench on your computer
  Then, download the source code on GitHub with this URL https://github.com/Enochhhh/AppMilkteaShop_KingTea.git, and unzip the file. After the file is unzipped, open MySQL to create a Database for the application with the file CreateDB.sql in the folder Database, and then import data for it by running the file DataDB.xml
  Next step, you'll need to download the Lombok library for Eclipse. You can refer it to https://www.youtube.com/watch?v=gsFPXkYDb-s&pp=ugMICgJ2aRABGAHKBRpkb3dubG9hZCBsb21ib2sgaW4gZWNsaXBzZQ%3D%3D
  After downloading the Lombok, import the folder named "kingtea" into Eclipse IDE to run the Back-end side, and import the folder named "AppMilkteaShop" into Android Studio to run the Front-end side
  So that you know, the application is running on a local host; you should configure the application to match the path on your machine. More specifically, on the Back-end side, you will see all configuration information; you must change it with the path on your computer
  Finally, you have to open an interface named ApiHelper on the Front-end side in the folder called helper; and change the domainApi variable by your IPv4 Address; you can open the Command Prompt and type ipconfig to check it
  After completing the steps, you can run the application by running the Back-end side first and then running the Front-end side later

## Paypal
  - You can test the function PayPal Payment with two accounts following
      + Main account to receive money from customers: Email: sb-647o47c26486897@business.example.com, Password: 9D)XAt6?
      + Account to send money for payment: Email: sb-kqxfi26486900@personal.example.com, Password: tJ#rv!n8
        
## The function and mission of the application that I have implemented
  - Configuration Spring Security
  - Login/Register: After the user login successfully, the Back-end side will generate a token and send it to the Front-end side, and the Front-end side will store this token in the file SharedPreferences to get the token value when calling API
  - Logout: The token stored before will be deleted when logout
  - Authorization Admin and User
  - Load information about the user and information of the category to the page Home
  - Show all milk tea in the shop, show milktea with the category
  - Search milk tea with the keyword
  - Add milk tea to the user's cart (Original milk tea, this milk tea has not been customized topping, size, ice, or sugar)
  - Show milk tea details, customize milk tea, and add customized milk tea to the user's cart
  - Cart Management: The user can manage your cart, specifically as increase/decrease quantity, and delete
  - Order and pay
  - Payment with Paypal Sandbox
  - Send mail to the email's user
  - User manages your orders
  - User watches order details
  - User cancels unconfirmed orders
  - User pays back unpaid orders
  - Change/Forgot Password: OTP Code will be sent to the email that the user typed; the user must enter the correct OTP to change a new password
  - User manages your profile
  - User sends reports
  - Admin receives orders that are waiting to accept
