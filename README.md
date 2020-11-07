# bog-ecommerce
Demo project for BOG 


Database Access : PostgreSQL

In order to run the application you should have Database named : "bog-db" and schema named "ecommerce-platform". DB port is running on 5432
The DB server must have an Admin user with Save and Retrieve privilages and username = "postgres" , password = "123456"

This configuration can be changed in Application.properties under "spring.datasource.url" , "spring.datasource.username", "spring.datasource.password"

API technologies : Application in running on Spring Boot with Java 8 and Tomcat for Application Server
* Built in Swagger

Every Method is Documented

PROBLEMS : 

The only problem encoutered so far was generating short-link for the Application. In order to generate the short-link the application
must have a domain. Because I didn't own any domain and the application runs on localhost ( which cannot generate any short-link ) 
I came up with the temporary solution. Right now I'm generating the short-links for my github account (this is to show that the actual generating short-link
was not the problem ) , and when it comes to sending the shortlink url to the client, I'm actually sending the full localhost url (because my private github repos url won't come handy
to open confirmation page :D )


Used external APIs :
 * short-link generator -> https://api-ssl.bitly.com/v4/shorten
 * image upload -> https://api.imgur.com/3/image

EMAIL SENDER : 

Currently any email that is sent from the application , the SENDER of the email is set to my personal Email account (tornikeshelia1996@gmail.com)

in order to override this and set your or your jobs official domain for the email , you can go to "BogEcommerceApplication" class
and set the properties as you wish.
"mailSender.setUsername" - the username from which the emails should be sent 
"mailSender.setPassword" - the GENERATED APP PASSWORD (not the actual password of your private email account) which gmail provides.

in order to set your own app password you need to follow this few steps to generate the CODE :

https://support.google.com/accounts/answer/185833?hl=en
Here's the steps : 
Go to your Google Account.
Select Security.
Under "Signing in to Google," select App Passwords. You may need to sign in. If you don’t have this option, it might be because:
  2-Step Verification is not set up for your account.
  2-Step Verification is only set up for security keys.
  Your account is through work, school, or other organization.
  You turned on Advanced Protection.
At the bottom, choose Select app and choose the app you using and then Select device and choose the device you’re using and then Generate.
Follow the instructions to enter the App Password. The App Password is the 16-character code in the yellow bar on your device.
Tap Done.

Now copy that password and add it to "BogEcommerceApplication" -> "mailSender.setPassword()".

OR - you could just use my email and my app password which are already typed in.
