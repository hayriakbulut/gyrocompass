package compass;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

class UDPClient
{
   public UDPClient()
   {
       java.util.Timer time= new java.util.Timer();
       time.scheduleAtFixedRate(new DataGenerator(), 0, 25);
   }
   public static void main(String args[]) throws Exception
   {
       new  UDPClient();
   }
   class DataGenerator extends TimerTask
    {
        long time;
        DatagramSocket clientSocket;
        InetAddress IPAddress;
        int port;
        float[] data = new float[25];
        byte[] sendData;
        public DataGenerator()
        {
            try {
                time = System.currentTimeMillis();
                clientSocket = new DatagramSocket();
                byte[] serverAddress = {(byte) 192, (byte) 168, (byte) 1, (byte) 48};
                IPAddress = InetAddress.getByAddress(serverAddress);
                port = 49001;
                data[0]=-180;
                data[1]=-30/57.32f;
                
                data[3]=-300;
                data[4]=-300;
                data[5]=-300;
                sendData= new byte[100];
            } catch (UnknownHostException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void run() {
            try {
                //System.out.println(data[1]+"\t"+data[2]);
                long diff = System.currentTimeMillis() - time;
                time = System.currentTimeMillis();
                data[0] = data[0]+0.05f;
                data[1] = data[1]+(0.025f/57.32f);
                data[2] = (data[2] + (0.1f/57.32f)) % (360/57.32f);
                data[3] += 0.2f;
                data[4] += 0.1f;
                data[5] += 0.1f;
                data[6] += 0.02f;
                data[7] += 0.06f;
                data[8] += 0.04f;
                data[9] = (data[9] + 1.2f) % 1000;
                data[10] = (data[10] + 0.7f) % 150;
                for (int i = 11; i < 25; i++) {
                    data[i] = (float) Math.random();
                }
                if (data[0] > 180) {
                    data[0] = -180;
                }
                if(data[1]>(30/57.32f))
                    data[1] = -(30/57.32f);
                if (data[3] > 300) {
                    data[3] = -300;
                }
                if (data[4] > 300) {
                    data[4] = -300;
                }
                if (data[5] > 300) {
                    data[5] = -300;
                }
                if (data[6] > 1.7) {
                    data[6] = -1.7f;
                }
                if (data[7] > 1.7) {
                    data[7] = -1.7f;
                }
                if (data[8] > 1.7f) {
                    data[8] = -1.7f;
                }
                
                for (int i = 0; i < 25; i++) {
                    byte[] byteData = toByta(data[i]);
                    sendData[4 * i + 0] = byteData[3];
                    sendData[4 * i + 1] = byteData[2];
                    sendData[4 * i + 2] = byteData[1];
                    sendData[4 * i + 3] = byteData[0];
                }
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                clientSocket.send(sendPacket);
                //System.out.println(diff);
            } catch (IOException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        public byte[] toByta(int data) {
    return new byte[] {
        (byte)((data >> 24) & 0xff),
        (byte)((data >> 16) & 0xff),
        (byte)((data >> 8) & 0xff),
        (byte)((data >> 0) & 0xff),
    };
}
        public byte[] toByta(float data) {
    return toByta(Float.floatToRawIntBits(data));
}

    }
}