/*IMPORTANT:This program runs only if you have local disk named D.
  If not replace D with any other local disk name except C
*/
package diary;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *Project name: Personal Diary 
 *Reason : OOPS assignment
 *Features: User-defined Encryption and Decryption
 *Security:Password Protection
 * Author: Hayanesh
 */

public class Diary extends Notepad implements ActionListener {
     
     JFrame create,enter,reset,text_editor,frame;
     JFrame controllingFrame;
     JLabel name_create,pass_create,confir_create,diary_wel;
     JLabel name_enter,pass_enter,label,wel_label;
     
     JButton ok_create,cancel_create,button,okButton,resetButton;
     JButton ok_enter,reset_enter,wel_enter;
     
     JTextArea t1_create;
     
     JPasswordField t2_create,t3_create;
     JPanel messagePane,buttonPane,p,dia1,dia2;
          
     JPasswordField passwordField;
        
    public Diary(){
    
   //Creating Diary and Keys folder in D partion
    File dir = new File(dir_diary);
    dir.mkdir();
    File dir2 = new File(dir_key);
    dir2.mkdir();
    
    //Frame1 contains two panels 
    create = new JFrame("Diary");
    create.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Panel1
    dia1= new JPanel();
    diary_wel = new JLabel("Welcome to your Diary");
    diary_wel.setForeground(Color.BLUE);
    diary_wel.setFont(diary_wel.getFont().deriveFont(40.0f));
    diary_wel.setSize(500,100);
    wel_enter = new JButton("Enter");
    wel_enter.addActionListener(this);
    wel_label = new JLabel();
    
    //Panel2
    dia2= new JPanel();
    name_create= new JLabel("User Name");
    pass_create= new JLabel("New Password");
    confir_create= new JLabel("Confirm Password");
    t1_create = new JTextArea();
    t1_create.setFont(t1_create.getFont().deriveFont(15.0f));
    t2_create = new JPasswordField(10);
    t3_create = new JPasswordField(10);
    ok_create = new JButton("OK");
    cancel_create = new JButton("Cancel");
    cancel_create.addActionListener(this);
    ok_create.addActionListener(this);
    }
    
    //Creating Frame1 to create account
    public void launchframe1()
    {   
        /*Re-Intialising the PasswordField
          To clear the PasswordField,
          when Frame1 is called through resetbutton of frame2
        */
        t1_create.setText("");
        t2_create.setText("");
        t3_create.setText("");
        
        //To hide the Create an Account label before Enterbutton is clicked
        wel_label.setVisible(false);
        wel_enter.setVisible(true);
        
        //Adding components to Panel1
        dia1.add(diary_wel);
        dia1.add(wel_enter);
        dia1.add(wel_label);
        dia1.setVisible(true);
        
        //Adding components to Panel2
        dia2.setLayout(new GridLayout(4,2));
        dia2.add(name_create);
        dia2.add(t1_create);
        dia2.add(pass_create);
        dia2.add(t2_create);
        dia2.add(confir_create);
        dia2.add(t3_create);
        dia2.add(ok_create);
        dia2.add(cancel_create);
        dia2.setVisible(false);
        
        //Adding the Pannels to the Frame1
        create.add(dia1);
        create.add(dia2);
        create.setVisible(true);
        create.setLayout(new GridLayout(0,1));
        create.setSize(500,250);
    }
    
    //Creating Frame2 to check the password
     public void launchframe2() {
        create.dispose();
        frame = new JFrame("Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showframe2(frame);
        Focus();
        frame.pack();
        frame.setVisible(true);
    }
    
    //Adding components to Frame2
    public void showframe2(JFrame f)
    {
        //Fram2 contains two Panels
        controllingFrame = f;
   
        //Panel1
        p = new JPanel();
        p.setLayout(new GridLayout(0,1));
        okButton = new JButton("OK");
        resetButton = new JButton("Reset");
        okButton.addActionListener(this);
        resetButton.addActionListener(this);
        p.add(okButton);
        p.add(resetButton);
        
        //Panel2
        label = new JLabel("Enter the password: ");
        label.setBounds(0,0,60,20);
        passwordField = new JPasswordField(10);
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPane.add(label);
        textPane.add(passwordField);
        
        //Adding panels to frame2
        controllingFrame.setLayout(new FlowLayout(FlowLayout.TRAILING));
        controllingFrame.add(textPane);
        controllingFrame.add(p);
        controllingFrame.setVisible(true);   
    }
    
    //To make the passwordField active to enter password
    public void Focus()             
    {                 
        passwordField.requestFocusInWindow();             
    } 
    
    //Creating Frame3 *Notepad* 
    public void launchframe3()
    {
        new Notepad();
        f.setVisible(true);
    }
    
    //Setting the action events for the corresponding buttons
    public void actionPerformed(ActionEvent e)
    {   
        if(e.getSource()==wel_enter)
        {  
           dia2.setVisible(true);
           wel_enter.setVisible(false);
           wel_label.setText("Create an account");
           wel_label.setFont(wel_label.getFont().deriveFont(20.0f));
           wel_label.setVisible(true);
        }
        else if(e.getSource()==ok_create)
        {
        //>>String name = t1_create.getText();
        char[] password = t2_create.getPassword();
        char[] temp = t3_create.getPassword();
        
        //Checking for password correctness
        if(Arrays.equals(password, temp))
        {   
            File pass;
            pass = new File("D:/password.txt");
            //Storing the password to the created file
            try {
                FileWriter wr = new FileWriter(pass);
                wr.write(password);
                wr.close();
            } catch (IOException ex) {
            }
           
            launchframe2();
        }
        else
        { 
            //Warning message for password mismatch
           AboutDialog();
        }
        }
        else if(e.getSource()==cancel_create)
        {     
            create.dispose();
        }
       //Frame2 actions
        else if (e.getSource()==okButton) 
        { 
            char[] input = passwordField.getPassword();
            try {
                if (isPasswordCorrect(input)) {
                    JOptionPane.showMessageDialog(controllingFrame,
                            "Welcome!! Click ok to open the Notepad");
                    frame.dispose();
                    launchframe3();
                } else {
                    JOptionPane.showMessageDialog(controllingFrame,
                            "Invalid password. Try again.",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (FileNotFoundException ex) {}
        }
        else if(e.getSource()==resetButton)
        {
            char[] check = passwordField.getPassword();
            try {
                if (isPasswordCorrect(check)) {
                    JOptionPane.showMessageDialog(controllingFrame,
                            "Success! You typed the right password.");
                    frame.dispose();
                    File pass = new File("E:/password.txt");
                    PrintWriter writer = new PrintWriter(pass);
                    
                    //To clear the old password
                    writer.print("");
                    
                    writer.close();
                    launchframe1();
                } else {
                    JOptionPane.showMessageDialog(controllingFrame,
                            "Enter the Old password in the password field and press reset");           
                }
            } catch (FileNotFoundException ex) {} 
        }
        //Frame3 Edit
        else if(e.getSource()==cut)
	   ta.cut();
        else if(e.getSource()==paste)
           ta.paste();
        else if(e.getSource()==copy)
            ta.copy();
        else if(e.getSource()==selectAll)
            ta.selectAll();
        //Frame3 File
        else if(e.getSource()==open)
            {openFile();}  
        else if(e.getSource()==save)
                try {
                    saver();
        } catch (IOException ex) {
           
        } 
        //Frame3 process
        else if(e.getSource()==encrypt)
        {
            try {
                encrypter();
            } catch (IOException ex) {    
            }
        }
        else if(e.getSource()==decrypt)
        {  
            try {
                decrypter();
            } catch (FileNotFoundException ex) {   
            }
        }
    }
    
     public void AboutDialog() 
    {
        JOptionPane.showMessageDialog(create,
                            "Entered password do not match");
    }
    
    //Function to check the entered password with the stored password in password.txt
     private static boolean isPasswordCorrect(char[] input) throws FileNotFoundException {
        boolean isCorrect = true;
        int count =0;
        File pass = new File("D:/password.txt");
        Scanner sc2 = new Scanner(pass);
        while(sc2.hasNext())
        {
            count++;
            sc2.next();
        }
        Scanner sc3 = new Scanner(pass);
        char[] correctPassword= new char[count];
        for(int i=0;i<count;i++)
            correctPassword = (sc3.next()).toCharArray();
        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }
        return isCorrect;
    }
   
    //MAIN METHOD 
    public static void main(String[] args) throws FileNotFoundException, IOException,NullPointerException{
        Diary x = new Diary();
        //Creating a file to save the user password 
        File myfile = new File("D:/Password.txt");
        if(!myfile.exists())
            myfile.createNewFile();
        Scanner mp = new Scanner(myfile);
        if(mp.hasNext())
        {
        x.launchframe2();
        }
        else 
        {
        x.launchframe1();
        }   
    }    
}
