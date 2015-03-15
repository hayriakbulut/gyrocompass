package compass;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gunjan Nigam
 */
public class Main extends JPanel{
    DatagramSocket dsocket;
    float[] pack_rec = new float[25];
	byte[] buffer = new byte[256];
	byte[] read_float = new byte[4];
    HeadingCircular hc;
    public static int value=0;
    
    public Main()
    {
        super(null);

        JFrame frame = new JFrame();
        frame.setSize(300,320);
        
        //JMenuBar menuBar = new JMenuBar();

        //JMenu menu = new JMenu("check");
        //menuBar.add(menu);
        //frame.setJMenuBar(menuBar);
        
        frame.getContentPane().add(this);

        hc= new HeadingCircular("plane.png");
        this.add(hc);
        hc.setBounds(0,0,300,300);     
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
        try {
            dsocket = new DatagramSocket(49001);
            dsocket.setReceiveBufferSize(1);
        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timer time = new Timer();
        time.scheduleAtFixedRate(new dataReceive(), 0, 75);
    }
    public static void main(String[] args)
    {
        new Main();
    }
    public class dataReceive extends TimerTask
    {
        long time = System.currentTimeMillis();
        
        public void run() {
            long diff= System.currentTimeMillis()-time;
            time = System.currentTimeMillis();
           
            //UDP();
           
            hc.setHeadingValue(value);
            value++;
            System.out.println("heading:"+value);
           
            hc.repaint();
           
            //System.out.println(diff);
          
        }

    }
    public void UDP() 
	{
		try
		{
			DatagramPacket	packet = new DatagramPacket(buffer, buffer.length);
            dsocket.setSoTimeout(1000*60);
            dsocket.receive(packet);
           
            DataInputStream data_stream = new DataInputStream(new ByteArrayInputStream(packet.getData()));
            for(int i=0;i<25;i++)
            {
                read_float[3]=data_stream.readByte();
                read_float[2]=data_stream.readByte();
                read_float[1]=data_stream.readByte();
                read_float[0]=data_stream.readByte();
                DataInputStream data_stream1 = new DataInputStream(new ByteArrayInputStream(read_float));
                pack_rec[i]=data_stream1.readFloat();
            }
         
		hc.setHeadingValue(pack_rec[2]*57.32f);
       

		}catch(SocketTimeoutException e){JOptionPane.showMessageDialog(null, "Sorry Communication Failed and No Data Received");}
        catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

   

}