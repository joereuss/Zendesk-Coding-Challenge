# Zendesk-Coding-Challenge
Coding challenge for an internship at Zendesk

In this coding challenge, I was required to:
  - Connect to the Zendesk API
  - Request all the tickets for my account
  - Display them in a list
  - Display individual ticket details
  - Page through tickets when more than 25 are returned

I decided to code this challenge using java and displaying the data using CLI.

To run with Windows:
  - Download the files from GitHub making sure to note where the files are located.
  - Open the command prompt by typing cmd in the Windows search bar
  - Navigate through your file system to find the folder containing the files downloaded from GitHub
  - To compile, type:
    - javac -cp ".;lib/*" *.java
  - To run, type: 
    - java -cp ".;lib/*" CLI
  - To test, type:
    - java -cp ".;lib/*" org.junit.platform.console.ConsoleLauncher --scan-class-path

To Run with Linux:
  - Do the same as above
    - except when compiling, running, and testing use a colon (:) instead of a semicolon (;) whenever a semicolon is used.


