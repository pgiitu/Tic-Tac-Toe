
import java.net.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

public class MyGameServer {
	public static JFrame frame = new JFrame("Let,s Play - Have Fun:SERVER");
	public static JButton button[];
	
	public static void func(Socket socket ,String s)throws IOException
	{
	 PrintWriter out =
	        new PrintWriter(socket.getOutputStream());
		out.println(s);
		out.flush();
	}

	public static void finalresult(String check)
	{
		if(check.equals("tie"))
		{
			try{
			func(socket,"tie");}catch(IOException e){System.out.println("exception has been raised");}			
			JOptionPane.showMessageDialog(null,"It is a tie between the two");
			System.exit(0);
		}
		else
		{
			if(check.equals("winserver"))
			{	
				try{
					func(socket,"winserver");}catch(IOException e){System.out.println("exception has been raised");}
				JOptionPane.showMessageDialog(null,"The Server Has Won The Game");
				System.exit(0);
				//JOptionPane.showMessageDialog(network.this,"The server has won the game","result",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				if(check.equals("winclient"))
				{
					try{
						func(socket,"winclient");}catch(IOException e){System.out.println("exception has been raised");}
					
					JOptionPane.showMessageDialog(null,"the client has won the game");
					System.exit(0);
					//JOptionPane.showMessageDialog(network.this,"The server has won the game","result",JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		}
	}
	
	public static String win(){
		String check=new String();
		check="";
		for(int i=1;i<10;i++){
			if((i==1 || i==4 || i==7) && button[i].getText()=="X" && button[i+1].getText()=="X" && button[i+2].getText()=="X"){
				check="winserver";//System.exit(0);
			}
			if((i==1 || i==2 || i==3) && button[i].getText()=="X" && button[i+3].getText()=="X" && button[i+6].getText()=="X"){
				check="winserver";//System.exit(0);
			}
			if(i==1 && button[i].getText()=="X" && button[i+4].getText()=="X" && button[i+8].getText()=="X"){
				check="winserver";//System.exit(0);
			}
			if(i==3 && button[i].getText()=="X" && button[i+2].getText()=="X" && button[i+4].getText()=="X"){
				check="winserver";//System.exit(0);
			}
		}
		for(int i=1;i<10;i++){
			if((i==1 || i==4 || i==7) && button[i].getText()=="0" && button[i+1].getText()=="0" && button[i+2].getText()=="0"){
				check="winclient";//System.exit(0);
			}
			if((i==1 || i==2 || i==3) && button[i].getText()=="0" && button[i+3].getText()=="0" && button[i+6].getText()=="0"){
				check="winclient";//System.exit(0);
			}
			if(i==1 && button[i].getText()=="0" && button[i+4].getText()=="0" && button[i+8].getText()=="0"){
				check="winclient";//System.exit(0);
			}
			if(i==3 && button[i].getText()=="0" && button[i+2].getText()=="0" && button[i+4].getText()=="0"){
				check="winclient";//System.exit(0);
			}
		}
		int k=0;
		for(int i=1;i<10;i++)
		{
			if(button[i].getText()!="")
			{
				k=k+1;
			}
		}
		if(k==9)
		{
			check="tie";
		}
		return check;
	}
	public static void exit(ActionEvent evt)throws IOException{
		func(socket,"exit");
		System.exit(0);}
		
	public static void print(String b){
			int i=Integer.parseInt(b);
			if(button[i].getText()==""){
			button[i].setText("X");
			//button[i].setEnabled(false);
			}}
	
	public static void print2(String s){
		if(s!=null){
		int i=Integer.parseInt(s);
		if(button[i].getText()==""){
			button[i].setText("0");
			//button[i].setEnabled(false);
		}}
	}
	public static final int PORT = 8089;
	public static Socket socket;
	  public static void main(String[] args)
	      throws IOException {
	    ServerSocket s = new ServerSocket(PORT);
	    System.out.println("Started: " + s);
			frame.setPreferredSize(new Dimension(400,400));
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			JMenuBar one = new JMenuBar();
			JMenu second = new JMenu("OPTIONS");
		//	JMenuItem third = new JMenuItem("NEW GAME");
			JMenuItem fourth = new JMenuItem("EXIT");
			fourth.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	           		try {
						exit(evt);
					} catch (IOException e) {
					}
	            }
	        });
			//second.add(third);
			second.add(fourth);
			one.add(second);
			frame.setJMenuBar(one);
			frame.setLayout( new java.awt.GridLayout(3,3) );
			button = new JButton[10];
			for (int i=1;i < 10;i++){
		         button[ i ] = new JButton();
		         button[ i ].setBackground(Color.lightGray);
		         button[ i ].setActionCommand(Integer.toString( i ));
		         button[ i ].addActionListener(new ActionListener() {
		                public void actionPerformed( ActionEvent evt) {
		                   print(evt.getActionCommand());
		                   try{
		                   func(socket,evt.getActionCommand());
		                   String check=win();
		                   finalresult(check);
		                   if(check!=""){
		                	   func(socket,check);
		                	   }
		                   else
		                   {
		                	   func(socket,evt.getActionCommand());
		                   }
		                   ;}catch(IOException e){}
		                   }
		            } );
		        frame.add(button[ i ]);
		        }
			
			try {
			      socket = s.accept();
						frame.pack();
						frame.setVisible(true);
			      try {
			        System.out.println(
			          "Connection accepted: "+ socket);
			        BufferedReader in =
			          new BufferedReader(
			            new InputStreamReader(
			              socket.getInputStream()));
			      /*  while(true)
			        {
			        String str=in.readLine();
			        print2(str);
			        }*/
			        while(true)
			        {
			        String str=in.readLine();
			        if(str.equals("exit")){
			        	System.exit(0);
			        }
			        if(str.equals("tie")){
			    		JOptionPane.showMessageDialog(null,"It is a tie between the two");
			        	System.exit(0);
			        	}
			        else{
			        
			        	if(str.equals("winserver")){
			        		System.out.println("server has won");
			        		JOptionPane.showMessageDialog(null,"the server has won the game");
			        		System.exit(0);
			        	}
			        	else
			        	{
			        	    if(str.equals("winclient")){
			        			JOptionPane.showMessageDialog(null,"the client has won the game");
			        	    	System.exit(0);
			        	    }
			        	    else{
			        	    	System.out.println(str);
			        	    print2(str);
			        	    }

			        	}
			        }
			        
			      } 
			      }catch(Exception e){}//finally {socket.close();
			      //}
			    } catch(Exception e){}//finally {s.close();
			   // }
	  }

}
