
package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CustomPanel extends JPanel
{
    private ArrayList<Pair> list;
    
    public CustomPanel()
    {
        super();
        
        this.list = new ArrayList();
        
        this.addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(MouseEvent e)
            {
               list.add(new Pair(e.getX(), e.getY()));
               repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e){}
            
        });
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.setColor(Color.LIGHT_GRAY);
        
        for (int i = 10; i < this.getWidth(); i += 10)
        {
            g.drawLine(i, 0, i, this.getHeight());
        }
        
        for (int i = 10; i < this.getHeight(); i += 10)
        {
            g.drawLine(0, i, this.getWidth(), i);
        }
        
        g.setColor(Color.BLACK);
        
        if (!list.isEmpty())
        {
            for (Pair pair : list)
            {
                g.fillOval(pair.x, pair.y, 5, 5);
            }
        }
    }
    
    public float[] getData()
    {
        float[] data = new float[list.size()];
        
        for (int i = 0; i < list.size(); i++)
        {
            data[i] = CantorPairing.CantorPair(list.get(i).x, list.get(i).y);
        }
        
        
//        float[] data = new float[list.size() * 2];
//        
//        for (int i = 0; i < list.size(); i++)
//        {
//            data[i * 2] = list.get(i).x;
//            data[i * 2 + 1] = list.get(i).y;
//        }
        
        //return data;
        
//        float[] trimmedData = new float[300];
//        
//        double inc = data.length / 300.0;
//        double ctr = 0.0;
//        
//        for (int i = 0; i < 300; i++)
//        {
//            ctr += inc;
//            trimmedData[i] = data[(int) Math.floor(ctr)];
//        }
//        
//        return trimmedData;
        float[] trimmedData = new float[100];
        
        if (data.length > 100)
        {
            for (int i = 0; i < 100; i++)
            {
                trimmedData[i] = data[i];
            }
        }
        else if (data.length > 50)
        {
            for (int i = 0; i < 50; i++)
            {
                trimmedData[i * 2] = data[i];
                trimmedData[i * 2 + 1] = data[i];
            }
        }
        else
        {
            for (int i = 0; i < 25; i++)
            {
                trimmedData[i * 4] = data[i];
                trimmedData[i * 4 + 1] = data[i];
                trimmedData[i * 4 + 2] = data[i];
                trimmedData[i * 4 + 3] = data[i];
            }
        }
        
        return trimmedData;
    }
    
    public void clear()
    {
        this.list.clear();
        repaint();
    }
    
    private class Pair
    {
        int x;
        int y;
        
        public Pair(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
