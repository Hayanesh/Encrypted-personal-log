Here I tried to create an encrypted personal diary in java applet.

STEPS TO RUN THIS FILE

1.Dowload the files Diary.java and Notepad.java and save it in a directry.

2.Make sure your have installed a proper JDK and JRE.
  And configure it by setting the path in environmental varibles.
  Compile both the files together as 
  >javac Diary.java Notepad.java
   
3.Now create a jar file and execute it.
  Use the below link to if you don't know to create a jar file.
  http://www.wikihow.com/Create-JAR-File

4.If step3 not works for you use any one of the IDEs like Netbeans,Eclipse,etc.

5.Create a package Diary and copy the two java files to it.And click run. 
   
   IMPORTANT:This program runs only if you have local disk named D.
   If not replace D with any other local disk name except C in the java code.
  
6.Now the applet diary should appear.

HOW TO USE THIS APPLET

1.Press the enter button to create an account.

2.Enter your username,password and press ok.
  (Now your password is saved in the file name password.txt of your Disk D)

3.Retype the entered password in the next frame and press ok.
  (Now the entered password is checked with the password stored in password.txt file)

4.(optional)To reset the password type the old password and press reset button.

5.Now a notepad appears with limited features like open and save, cut,copy and paste.

6.Type the day's thoughts to it and press encrypt under the process menu.

7.Type a FOUR DIGIT number as the encryption key (say 1234)and press ok.
  (Should remember this key for decryption)

8.Now see that the enter contents get changed into sequence of symbols.

9.Now press save under the file menu.

10.Now the contents are saved to the file named today's day (eg.,2132015) in D:/Diary/
  (Try to open it using any text editor and see its contents.It must be encrypted)

11.To decrypt the code,run the applet again.Enter the password.

12.File->Open file go to D:/Diary/ and select the file named today's date ((say 21032015 )
   21 is the day,03 is the month 2015 is the year)

13.press decrypt under the proces menu.

14.Enter the encryption key(say 1234) and press ok.

15.Now the contents gets decrypted.
 
 
In the same way you can write the day's contents to a file per day.Each days contents can be encrypted with different values.
But you should remember the encryption key for the corresponding day.
