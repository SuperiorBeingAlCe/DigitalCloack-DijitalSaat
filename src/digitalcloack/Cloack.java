package digitalcloack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Cloack {

	 public Cloack() {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                    ex.printStackTrace();
	                }

	                JFrame frame = new JFrame("Digital Clock");
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setLayout(new BorderLayout());
	                frame.add(new TestPane());
	                frame.setSize(600, 300);
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	            }
	        });
	    }

	    public class TestPane extends JPanel {

	        private DigitPane hour;
	        private DigitPane min;
	        private DigitPane second;
	        private JLabel[] seperator;
	        private JLabel dateLabel;

	        private int tick = 0;

	        public TestPane() {
	            setLayout(new GridBagLayout());
	            setBackground(Color.BLACK); 

	            hour = new DigitPane();
	            min = new DigitPane();
	            second = new DigitPane();
	            seperator = new JLabel[]{new JLabel(":"), new JLabel(":")};
	            dateLabel = new JLabel();

	            for (JLabel label : seperator) {
	                label.setForeground(Color.GREEN); 
	                label.setFont(new Font("Arial", Font.BOLD, 60)); 
	            }

	            dateLabel.setForeground(Color.GREEN);
	            dateLabel.setFont(new Font("Arial", Font.BOLD, 30)); 

	            GridBagConstraints gbc = new GridBagConstraints();
	            gbc.gridx = 0;
	            gbc.gridy = 0;
	            gbc.gridwidth = 5;
	            add(dateLabel, gbc);

	            gbc.gridy = 1;
	            gbc.gridwidth = 1;
	            add(hour, gbc);
	            gbc.gridx = 1;
	            add(seperator[0], gbc);
	            gbc.gridx = 2;
	            add(min, gbc);
	            gbc.gridx = 3;
	            add(seperator[1], gbc);
	            gbc.gridx = 4;
	            add(second, gbc);

	            Timer timer = new Timer(500, new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    Calendar cal = Calendar.getInstance();
	                    hour.setValue(cal.get(Calendar.HOUR_OF_DAY));
	                    min.setValue(cal.get(Calendar.MINUTE));
	                    second.setValue(cal.get(Calendar.SECOND));

	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    dateLabel.setText(sdf.format(cal.getTime()));

	                    if (tick % 2 == 1) {
	                        seperator[0].setText(" ");
	                        seperator[1].setText(" ");
	                    } else {
	                        seperator[0].setText(":");
	                        seperator[1].setText(":");
	                    }
	                    tick++;
	                }
	            });
	            timer.setRepeats(true);
	            timer.setCoalesce(true);
	            timer.start();
	        }
	    }

	    public class DigitPane extends JPanel {

	        private int value;

	        public DigitPane() {
	            setForeground(Color.GREEN); 
	            setBackground(Color.BLACK); 
	            setFont(new Font("Arial", Font.BOLD, 60)); 
	        }

	        @Override
	        public Dimension getPreferredSize() {
	            FontMetrics fm = getFontMetrics(getFont());
	            return new Dimension(fm.stringWidth("00"), fm.getHeight());
	        }

	        public void setValue(int aValue) {
	            if (value != aValue) {
	                int old = value;
	                value = aValue;
	                firePropertyChange("value", old, value);
	                repaint();
	            }
	        }

	        public int getValue() {
	            return value;
	        }

	        protected String pad(int value) {
	            StringBuilder sb = new StringBuilder(String.valueOf(value));
	            while (sb.length() < 2) {
	                sb.insert(0, "0");
	            }
	            return sb.toString();
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.setColor(getForeground());
	            String text = pad(getValue());
	            FontMetrics fm = getFontMetrics(g.getFont());
	            int x = (getWidth() - fm.stringWidth(text)) / 2;
	            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
	            g.drawString(text, x, y);
	        }
	    }}
