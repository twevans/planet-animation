package animation;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GUI {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Planet Positions");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // add to chart animation class as well?
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
    } 
}

class MyPanel extends JPanel implements ActionListener {
	protected JButton b;
	protected JSpinner s;
	protected JRadioButton rS;
	protected JRadioButton rM;
	protected JRadioButton rMe;
	protected JRadioButton rVe;
	protected JRadioButton rMa;
	protected JRadioButton rJu;
	protected JRadioButton rSa;
	protected JRadioButton rUr;
	protected JRadioButton rNe;
	
	
    public MyPanel() {
    	setBorder(BorderFactory.createLineBorder(Color.black));
        //setLayout(null);
        
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        Date startDate = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        Date endDate = new GregorianCalendar(2100, Calendar.JANUARY, 1).getTime();
        SpinnerModel v2 = new SpinnerDateModel(now, startDate, endDate, Calendar.YEAR);
        s = new JSpinner(v2);
        s.setEditor(new JSpinner.DateEditor(s,"dd.MM.yyyy HH:mm"));
        //s.setBounds(250,525,150,50);
        s.setToolTipText("Enter a start date (between 1900 and 2100) and a time");
        add(s);
        
        b = new JButton("Run");
        //b.setBounds(500,525,50,50);
        b.setVerticalTextPosition(AbstractButton.CENTER);
        b.setHorizontalTextPosition(AbstractButton.LEADING); 
        b.addActionListener(this);
        b.setToolTipText("Click this button to start.");
        add(b);
        
        rS = new JRadioButton("Sun");
        add(rS);
        rM = new JRadioButton("Moon");
        add(rM);
        rMe = new JRadioButton("Mercury");
        add(rMe);
        rVe = new JRadioButton("Venus");
        add(rVe);
        rMa = new JRadioButton("Mars");
        add(rMa);
        rJu = new JRadioButton("Jupiter");
        add(rJu);
        rSa = new JRadioButton("Saturn");
        add(rSa);
        rUr = new JRadioButton("Uranus");
        add(rUr);
        rNe = new JRadioButton("Neptune");
        add(rNe);
    }
    
    public void actionPerformed(ActionEvent e) {
    	
        
    	
    	ChartAnimation ca = new ChartAnimation();
		ca.run();
    	
    }
    
    //public Dimension getPreferredSize() {
     //   return new Dimension(1080,600);
    //}
}


