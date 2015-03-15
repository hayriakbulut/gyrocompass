package compass;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gunjan Nigam
 */
public class HeadingCircular extends JPanel{

    
    private float headingValue;
    int centerX,centerY;
    Image img;
    JLabel label;
    Font font8,font16,font20;
    BasicStroke stroke1,stroke2;
    public HeadingCircular(int size,String imagename)
    {
        super(null);
        try {
            this.setSize(size, size);
            System.out.println(this.getClass().getResource(imagename)); 
            img = ImageIO.read(getClass().getResource(imagename));
            centerX = size / 2;
            centerY = size / 2;
            setOpaque(false);
            font8 = new Font("Sanserif",Font.PLAIN,8);
            font16 = new Font("Sanserif",Font.PLAIN,16);
            font20 = new Font("Sanserif",Font.PLAIN,20);
            //stroke1 = new BasicStroke(0.5);
            stroke1 = new BasicStroke(1);
            stroke2 = new BasicStroke(2);
           
        } catch (IOException ex) {
            Logger.getLogger(HeadingCircular.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    public HeadingCircular(String imagename)
    {
       this(300,imagename);
    }
    @Override
    public void paintComponent(Graphics g)
    {
        
        try {
            centerX = this.getWidth() / 2;
            centerY = this.getHeight() / 2;
            Graphics2D g2d = (Graphics2D) g;
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setPaint(new Color(74,74,86));
            g2d.fillRect(0, 0, centerX * 2, centerY * 2);
           
            g2d.setStroke(stroke2);
            g2d.setPaint(Color.WHITE);
           
            AffineTransform affTransform = g2d.getTransform();
            g2d.transform(affTransform.getRotateInstance(-this.getHeadingValue() / 57.29, centerX, centerY));
            //g2d.drawImage(img, centerX - 25, centerY - 25, null);
/*            
            for(int i=0;i<36;i++)
            {
                AffineTransform affineTransform = g2d.getTransform();

                g2d.transform(affineTransform.getRotateInstance((10*i)/57.29,centerX,centerY));
                g2d.drawLine(centerX,3,centerX,18);
                if(i%9==0)
                {
                    g2d.setStroke(stroke1);
                    g2d.drawLine(centerX,60,centerX,centerY);
                    g2d.setStroke(stroke2);
                    g2d.setFont(font20);
                    if(i==0) g2d.drawString("N",centerX-5,40);
                    if(i==9) g2d.drawString("E",centerX-5,40);
                    if(i==18) g2d.drawString("S",centerX-7,40);
                    if(i==27) g2d.drawString("W",centerX-8,40);
                }
                else if(i%1==0)
                {
                //g2d.setFont(font16);
                g2d.setFont(font8);
                if(i>=10)
                    g2d.drawString(Integer.toString(i*10),centerX-8,40);
                else
                    g2d.drawString(Integer.toString(i*10),centerX-5,40);
                }
                g2d.setTransform(affineTransform);
            }

 */           
            for(int i=0;i<360;i++)
            {
                AffineTransform affineTransform = g2d.getTransform();

                g2d.transform(affineTransform.getRotateInstance((i)/57.29,centerX,centerY));
                
                g2d.setStroke(new BasicStroke(0.5f));
                g2d.drawLine(centerX,7,centerX,10);
                g2d.setStroke(stroke2);
                
                if(i%90==0)
                {
                    g2d.setStroke(stroke1);
                    g2d.drawLine(centerX,60,centerX,centerY);
                    g2d.setStroke(stroke2);
                    g2d.setFont(font20);
                    if(i==0) g2d.drawString("N",centerX-5,40);
                    if(i==90) g2d.drawString("E",centerX-5,40);
                    if(i==180) g2d.drawString("S",centerX-7,40);
                    if(i==270) g2d.drawString("W",centerX-8,40);
                }
                else if(i%10==0)
                {
                //g2d.setFont(font16);
                g2d.drawLine(centerX,3,centerX,18);	
                g2d.setFont(font8);
                if(i>=100)
                    g2d.drawString(Integer.toString(i),centerX-8,40);
                else
                    g2d.drawString(Integer.toString(i),centerX-5,40);
                }
                g2d.setTransform(affineTransform);
            }            
 
            
            
            g2d.setTransform(affTransform);
            //g2d.drawImage(img, centerX - 25, centerY - 25, null);
            
            //draw red line
            g2d.setPaint(Color.RED);
            g2d.setStroke(new BasicStroke(4)); 
            g2d.drawLine(centerX,3,centerX,30);
            
            //draw rectangle
            g2d.setFont(font20);            
            g2d.setStroke(new BasicStroke(2));
            g2d.setPaint(new Color(74,74,86));
            g2d.fillRect(centerX-30, centerY-20, 60, 40);
            g2d.setPaint(Color.RED);
            g2d.drawRect(centerX-30, centerY-20, 60, 40);
            
            //draw heading inside rectangle
            String heading = String.format( "%03d" , (int)this.getHeadingValue() % 360);
            g2d.drawString(heading,centerX-18,centerY+8);
            
        } catch (Exception ex) {
            Logger.getLogger(HeadingCircular.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    /**
     * @return the headingValue
     */
    public float getHeadingValue() {
        return headingValue;
    }

    /**
     * @param headingValue the headingValue to set
     */
    public void setHeadingValue(float headingValue) {
        this.headingValue = headingValue;
    }


            

}
