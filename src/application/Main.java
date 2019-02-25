
package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import neuralnetwork.DataUtils;
import neuralnetwork.Error;
import neuralnetwork.INeuralNetworkCallback;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Result;

public class Main
{
    private JFrame frame;
    private JPanel panel;
    private CustomPanel canvas;
    private JPanel display;
    private JLabel digit;
    private JLabel percentage;
    private JTextField field;
    private JButton btnTrain;
    private JButton btnClear;
    
    public Main()
    {
        canvas = new CustomPanel();
        canvas.setBounds(0, 0, 300, 500);
        canvas.setBackground(Color.WHITE);
        
        canvas.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e)
            {                
                float[][] input = DataUtils.readInputsFromFile("trainingdata/input.txt");
                int[] output = DataUtils.readOutputsFromFile("trainingdata/output.txt");
                
                NeuralNetwork neuralNetwork = new NeuralNetwork(input, output, new INeuralNetworkCallback(){

                    @Override
                    public void success(Result result)
                    {
                        digit.setText(Integer.toString(result.predictValue(canvas.getData())));
                        percentage.setText("Success Percentage: " + String.format("%.2f", result.getSuccessPercentage()) + "%");
                    }

                    @Override
                    public void failure(Error error)
                    {
                        System.out.println("Error: " + error.name());
                        System.out.println(error.getDescription());
                    }

                });

                neuralNetwork.startLearning();
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        
        });
        
        digit = new JLabel();
        digit.setBounds(80, 20, 30, 30);
        digit.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        
        percentage = new JLabel();
        percentage.setBounds(5, 70, 180, 20);
        
        display = new JPanel(null);
        display.setBounds(310, 10, 180, 100);
        display.setBackground(Color.WHITE);
        display.add(digit);
        display.add(percentage);
        
        field = new JTextField();
        field.setBounds(310, 120, 180, 30);
        
        btnTrain = new JButton("Train");
        btnTrain.setBounds(310, 160, 180, 25);
        btnTrain.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnTrain.setFocusPainted(false);
        
        btnTrain.addActionListener((e)->{
            float[] data = canvas.getData();
            
            if (data.length != 0 && !field.getText().isEmpty())
            {
                try
                {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("trainingdata/input.txt", true));
                    
                    for (int i = 0; i < data.length; i++)
                    {
                        if (i == 0)
                        {
                            writer.append(Float.toString(data[i]));
                        }
                        else
                        {
                            writer.append("," + Float.toString(data[i]));
                        }
                    }
                    
                    writer.newLine();
                    writer.close();
                    
                    writer = new BufferedWriter(new FileWriter("trainingdata/output.txt", true));
                    writer.append(field.getText());
                    writer.newLine();
                    writer.close();
                }
                catch (Exception ex)
                {
                    System.out.println("Exception Thrown: " + ex.getMessage());
                }
                
                canvas.clear();
                digit.setText("");
                percentage.setText("");
                field.setText("");
            }
        });
        
        btnClear = new JButton("Clear");
        btnClear.setBounds(310, 195, 180, 25);
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnClear.setFocusPainted(false);
        
        btnClear.addActionListener((e)->{
            canvas.clear();
            digit.setText("");
            percentage.setText("");
        });
        
        panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(500, 300));
        panel.add(canvas);
        panel.add(display);
        panel.add(field);
        panel.add(btnTrain);
        panel.add(btnClear);
        
        frame = new JFrame("Digit Recognizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setLocation(800, 400);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args)
    {
        Main main = new Main();
    }
}
