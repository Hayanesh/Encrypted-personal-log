package diary;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Notepad implements ActionListener{

JFrame f;
JMenuBar mb;
JMenu file,edit,process;
JMenuItem cut,copy,paste,selectAll,open,save,decrypt,encrypt;
JLabel date;
public JTextArea ta;

int[] key = new int[3];	
String[] res= new String[3];
String file_name = null;
String kfile_name = null;
String dir_diary = "D:/diary/";
String dir_key = "D:/Keys/";
String keys;
String fil;

Notepad(){  
//Creating the Notepad
file_name = dir_diary;
kfile_name = dir_key;
f=new JFrame("Notepad");
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
cut=new JMenuItem("cut");
copy=new JMenuItem("copy");
paste=new JMenuItem("paste");
selectAll=new JMenuItem("selectAll");
save= new JMenuItem("save");
decrypt = new JMenuItem("Decrypt");
encrypt = new JMenuItem("Encrypt");

cut.addActionListener(this);
copy.addActionListener(this);
paste.addActionListener(this);
selectAll.addActionListener(this);
save.addActionListener(this);

mb=new JMenuBar();
file=new JMenu("File");
edit=new JMenu("Edit");
process=new JMenu("Process");

//To display the date in the menubar 
//***FILE NAME OF THE DIARY CONTENT IS THE CURRENT DATE***
String la = "Date: ";
la=la.concat(datedisplay());
date = new JLabel(la);

open=new JMenuItem("Open File");  
open.addActionListener(this);

//Adding menuitems to menubar
file.add(open);file.add(save);
edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);
process.add(encrypt);process.add(decrypt);

decrypt.setVisible(false);
encrypt.addActionListener(this);
decrypt.addActionListener(this);

//Adding the menus to the menubar
mb.add(file);mb.add(edit);mb.add(process);

//Method to righ align the next menu
mb.add(Box.createHorizontalGlue());
mb.add(date);

//Setting the position of the menubar in the frame
mb.setBounds(0,0,808,20);
ta=new JTextArea();
ta.setBounds(2,20,810,500);

//Method to wrap the exeding text
ta.setLineWrap(true);

//Adding the components to the frame
f.add(mb);f.add(ta);
f.setLayout(null);
f.setSize(815,500);
f.setResizable(false);
}

//Funtion to get the return the currentdate in DD/MM/YY format
String datedisplay()
{   
    String date;
    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DATE);
    int month = c.get(Calendar.MONTH);
    //month count starts from 0
    month+=1;
    int year = c.get(Calendar.YEAR);
    res[0] = Integer.toString(day);
    res[1] = Integer.toString(month);
    res[2] = Integer.toString(year);
    for(int i =0;i<3;i++)
    {
       //Generating the file name to store the cont
       file_name = file_name.concat(res[i]);
    }
    for(int i =0;i<3;i++)
    {
        //Generating the file name to store the encryptionkey
       kfile_name = kfile_name.concat(res[i]);
    }
    for(int i =1;i<3;i++)
    { 
       res[0]= res[0].concat("/");
       res[0]= res[0].concat(res[i]);
    }
    //res is dd/mm/yyyy
    return res[0];
}
//Saving the textArea characters to the file
void saver() throws IOException
{
    String txt;
    Boolean b;
    int count;
    txt = ta.getText();
    count = txt.length();
    char[] c = new char[count];
    c=txt.toCharArray();
    file_name.concat(".txt");
    File fi;
    fi = new File(file_name);
    if(!fi.exists())
    try {
        b=fi.createNewFile();
    } catch (IOException ex) {  
    }
    //Note:FileWriter writes the text content as character to the file
    FileWriter writer = new FileWriter(fi);
    for(int i=0;i<count;i++)
        writer.write(c[i]);
    writer.close();
}

public void encrypter() throws IOException
{   
    JFrame en = new JFrame("key");
    keys = JOptionPane.showInputDialog(en,"Enter the encryption key {any 4 digit number}");
    char[] k = new char[keys.length()];
    k = keys.toCharArray();
    int[] k1 = new int[4];
    for(int i=0;i<4;i++)
        k1[i]=(int)k[i];
    kfile_name.concat(".dat");
    File fil = new File(kfile_name);
    if(!fil.exists())
        fil.createNewFile();
   
    //Storing the encryption key to the key file
    FileOutputStream in = new FileOutputStream(kfile_name);
    DataOutputStream it= new DataOutputStream(in);
    for(int i=0;i<4;i++)
       it.writeInt(k1[i]);
    in.close();
    
    String txt,retxt;
    Boolean b;
    int count;
    txt = ta.getText();
    count = txt.length();
    char[] c = new char[count];
    c=txt.toCharArray();
    
    //Encryction Algorithm : Adding encryption key values to the content's character(ASCII)
    loop:    for(int i=0;i<count;)
    {   for(int j=0;j<k1.length;j++)
        {   
            c[i]= (char) (c[i]+k1[j]);
            if(i<(count-1))
               i++;
            else
                break loop;     
        }
    }
    retxt = String.copyValueOf(c);
    ta.setText(retxt);
    encrypt.setVisible(false);
    decrypt.setVisible(true);
}

public void decrypter() throws FileNotFoundException
{
     String txt,retxt;
            int count;
            JFrame en = new JFrame("key");
            String ke = JOptionPane.showInputDialog(en,"Enter the decryption key");
            char[] k = new char[ke.length()];
            k=ke.toCharArray();
            int[] k2 = new int[4];
            for(int i=0;i<4;i++)
                k2[i]=(int)k[i];
           
            //Getting the file contents as integer array
            InputStream fil = null;
            fil = new FileInputStream(kfile_name);
            DataInputStream itt = new DataInputStream(fil);
            int[] k3= new int[4];
            for(int i=0;i<4;i++)
                try {
                    k3[i]=itt.readInt();
                    } 
                    catch (IOException ex) { }
            
            //Checking with the encryption key
                    if(Arrays.equals(k3, k2))
                    {
                    txt = ta.getText();
                    count = txt.length();
                    char[] c = new char[count];
                    c=txt.toCharArray();
                    //Decryction Algorithm : Subracting encryption key values from the content's character(ASCII)
                    loop:for(int i=0;i<count;)
                    {
                    for(int j=0;j<k3.length;j++)
                    {
                    c[i]=(char)(c[i]-k3[j]);
                    if(i<(count-1))
                    i++;
                    else
                    break loop;
                    }
                    }
                    retxt = String.copyValueOf(c);
                    ta.setText(retxt);
                    } 
                    else
                    {
                        JOptionPane.showMessageDialog(f,
                            "Decryption Key invalid",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                        decrypter();
                    }
                    encrypt.setVisible(true);
                    decrypt.setVisible(false);
} 
void openFile(){  
char[] middle = new char[7];
JFileChooser fc=new JFileChooser();  
int i=fc.showOpenDialog(paste);
          
if(i==JFileChooser.APPROVE_OPTION){  
File g=fc.getSelectedFile();  
String filepath=g.getPath();  
              
displayContent(filepath);  
fil = g.getAbsolutePath(); 
System.out.println(fil);
System.out.println(fil.length());
}  
    //To get the filename of the opened file to match, with its corresponding key file
    int j=0;
    String s;
     for(i=9;i<fil.length();i++)
     {   
         middle[j]= fil.charAt(i);
         j++;
     }
     s= new String(middle);
     System.out.println(s);
     kfile_name=dir_key;
     kfile_name=kfile_name.concat(s);
     System.out.println(kfile_name);
     decrypt.setVisible(true);
     encrypt.setVisible(false);
}  
void displayContent(String fpath){  
try{  
BufferedReader br=new BufferedReader(new FileReader(fpath));  
String s1="",s2="";  
              
while((s1=br.readLine())!=null){  
s2+=s1+"\n";  
}  
ta.setText(s2);  
br.close();  
}catch (Exception e) {e.printStackTrace();  }  
}  
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
