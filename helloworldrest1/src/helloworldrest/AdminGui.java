package helloworldrest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import org.json.*;
import javax.swing.JSeparator;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Label;
import javax.swing.JEditorPane;
import java.awt.Panel;
import javax.swing.JRadioButton;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JCalendar;
import org.jdesktop.swingx.JXDatePicker;
import com.toedter.components.JSpinField;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.CalendarPanel;

public class AdminGui {

	private JFrame frame;
	private JTextField textFieldNomePartecipante;
	private JTextField textFieldCognomePartecipante;
	private JTable table;
	DefaultTableModel model; 
	private JTextField textFieldNome;
	private JTextField textFieldLogo;
	private JTextField textFieldLuogo;
	private JRadioButton rdbtnOrganizzatore;
	private JRadioButton rdbtnPartecipante;
	private JEditorPane editorPaneDescrizione;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private JEditorPane editorPaneProgramma;	
	private JComboBox comboBoxConvegni;
	private JComboBox comboBoxPartecipanti;
	private Vector modelComboBox;
	
	private JPanel panelConvegno;
	private JPanel panelProgramma;
	private JPanel panelPartecipanti;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGui window = new AdminGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 546, 371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelConvegno = new JPanel();
		tabbedPane.addTab("Convegno", null, panelConvegno, null);
		panelConvegno.setLayout(new MigLayout("", "[][grow]", "[][][grow][][]"));
		
		Label labelNome = new Label("Nome:");
		panelConvegno.add(labelNome, "cell 0 0,growx");
		
		textFieldNome = new JTextField();
		panelConvegno.add(textFieldNome, "cell 1 0,growx");
		textFieldNome.setColumns(10);
		
		JLabel lblLogo = new JLabel("Logo:");
		panelConvegno.add(lblLogo, "cell 0 1,growx");
		
		textFieldLogo = new JTextField();
		textFieldLogo.setEnabled(false);
		panelConvegno.add(textFieldLogo, "cell 1 1,growx");
		textFieldLogo.setColumns(10);
		
		JLabel lblDescrizione = new JLabel("Descrizione:");
		panelConvegno.add(lblDescrizione, "cell 0 2,growx,aligny top");
		
		editorPaneDescrizione = new JEditorPane();
		panelConvegno.add(editorPaneDescrizione, "cell 1 2,grow");
		
		JLabel lblLuogo = new JLabel("Luogo:");
		panelConvegno.add(lblLuogo, "cell 0 3,grow");
		
		textFieldLuogo = new JTextField();
		panelConvegno.add(textFieldLuogo, "cell 1 3,growx");
		textFieldLuogo.setColumns(10);
		
		JButton btnInviaConvegno = new JButton("Invia");
		btnInviaConvegno.addActionListener(new SendButtonListenerConvegno());
		panelConvegno.add(btnInviaConvegno, "cell 1 4,alignx right");
		
		panelProgramma = new JPanel();
		tabbedPane.addTab("Programma", null, panelProgramma, null);
		panelProgramma.setLayout(new MigLayout("", "[72px][433px,grow]", "[16px][][16px,grow][][grow][191px][grow][grow][16px][25px]"));
		
		JLabel lblConvegnoProgramma = new JLabel("Convegno:");
		panelProgramma.add(lblConvegnoProgramma, "cell 0 0,alignx left,aligny top");

		modelUpdate();
		comboBoxConvegni = new JComboBox(modelComboBox);			
		comboBoxConvegni.setRenderer( new ItemRenderer() );
		
		panelProgramma.add(comboBoxConvegni, "cell 1 0,growx");		
		
		JLabel lblData = new JLabel("Data:");
		panelProgramma.add(lblData, "cell 0 2,alignx left,aligny top");
		
		datePicker = new DatePicker();
		panelProgramma.add(datePicker, "cell 1 2,grow");
		
		JSpinField spinHours = new JSpinField();
		spinHours.setMaximum(24);
		spinHours.setMinimum(0);		
		
		JLabel lblOra = new JLabel("Ora:");
		panelProgramma.add(lblOra, "cell 0 4");
		
		timePicker = new TimePicker();		
		panelProgramma.add(timePicker, "cell 1 4,grow");
		
		JLabel lblProgramma = new JLabel("Programma:");
		panelProgramma.add(lblProgramma, "cell 0 5,alignx left,aligny top");
		
		editorPaneProgramma = new JEditorPane();
		panelProgramma.add(editorPaneProgramma, "cell 1 5,grow");
		
		JButton btnInviaProgramma = new JButton("Invia");
		btnInviaProgramma.addActionListener(new SendButtonListenerProgramma());
		panelProgramma.add(btnInviaProgramma, "cell 1 9,alignx right,aligny top");
		
		JSpinField spinMinutes = new JSpinField();
		spinMinutes.setMaximum(60);		
		spinMinutes.setMinimum(0);
		
		panelPartecipanti = new JPanel();
		tabbedPane.addTab("Partecipanti", null, panelPartecipanti, null);
		panelPartecipanti.setLayout(new MigLayout("", "[][grow][][grow][][][][]", "[][][grow][][][][grow][][grow][]"));
		
		JLabel labelNomePartecipante = new JLabel("Nome:");
		panelPartecipanti.add(labelNomePartecipante, "cell 0 0");
		
		textFieldNomePartecipante = new JTextField();
		panelPartecipanti.add(textFieldNomePartecipante, "cell 1 0 7 1,growx");
		textFieldNomePartecipante.setColumns(10);
		
		JLabel labelCognomePartecipante = new JLabel("Cognome:");
		panelPartecipanti.add(labelCognomePartecipante, "cell 0 1");
		
		String[] columnNames = {"ID", "Nome", "Area", "H-index",""};
		model = new DefaultTableModel(null,columnNames);
		
		textFieldCognomePartecipante = new JTextField();
		panelPartecipanti.add(textFieldCognomePartecipante, "cell 1 1 7 1,growx");
		textFieldCognomePartecipante.setColumns(10);
		
		JLabel lblConvegno = new JLabel("Convegno:");
		panelPartecipanti.add(lblConvegno, "cell 0 2");
		
		Choice choiceConvegnoPartecipanti = new Choice();		
		choiceConvegnoPartecipanti.add("Java");
		choiceConvegnoPartecipanti.add("C++");
		choiceConvegnoPartecipanti.add("VB");
		choiceConvegnoPartecipanti.add("Perl");
				
		comboBoxPartecipanti = new JComboBox(modelComboBox);			
		comboBoxPartecipanti.setRenderer( new ItemRenderer() );
		panelPartecipanti.add(comboBoxPartecipanti, "cell 1 2 7 1,grow");
		
		JSeparator separator = new JSeparator();
		panelPartecipanti.add(separator, "cell 0 3 8 1");
		
		Label labelTipo = new Label("Tipo:");
		panelPartecipanti.add(labelTipo, "cell 0 4,alignx left,growy");
		
		rdbtnOrganizzatore = new JRadioButton("Organizzatore");
		rdbtnPartecipante = new JRadioButton("Partecipante");
		
		ButtonGroup bG = new ButtonGroup();
		bG.add(rdbtnPartecipante);
		bG.add(rdbtnOrganizzatore);
		rdbtnPartecipante.setSelected(true);
		panelPartecipanti.add(rdbtnPartecipante, "cell 1 4");
		panelPartecipanti.add(rdbtnOrganizzatore, "cell 1 4");
		
		table = new JTable(model);		
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		
		JScrollPane scrollpane = new JScrollPane(table);
		
		panelPartecipanti.add(scrollpane, "cell 0 7 8 1,grow");		
		
		JButton btnInviaPartecipante = new JButton("Invia");
		btnInviaPartecipante.addActionListener(new SendButtonListenerPartecipanti());				
		panelPartecipanti.add(btnInviaPartecipante, "cell 7 9,alignx right");
	}
	
	private class SendButtonListenerPartecipanti implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = textFieldNomePartecipante.getText();
			String surname = textFieldCognomePartecipante.getText();			
			Scopus scopus = new Scopus(name, surname);
			JSONObject ja;
			
			Action add = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        System.out.println();
			        try {	
			        	int organizzatore=0;
			            if(rdbtnOrganizzatore.isSelected())
			         	   organizzatore=1;
						String sql = "INSERT INTO partecipanti (id_convegno, id_partecipante,tipologia) VALUES" + 
		        	   			"('3666', '"+table.getModel().getValueAt(modelRow, 0).toString()+"','"+organizzatore+"')";
						databaseInsert(sql);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        //((DefaultTableModel)table.getModel()).removeRow(modelRow);
			    }
			};
			
			
			
			try {
				ja = new JSONObject(scopus.webScraping());				
		        JSONArray jArr = (JSONArray) ja.getJSONArray("scopus");
		        for(int i = 0; i < jArr.length();i++) {
		            JSONObject innerObj = jArr.getJSONObject(i);
		            model.addRow(new Object[]{innerObj.getString("id"),innerObj.getString("nome"),innerObj.getString("area"),innerObj.getString("h-index"),"Aggiungi"});
		            ButtonColumn buttonColumn = new ButtonColumn(table, add, 4);		            
		        }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		}
		
	}
	
	private class SendButtonListenerConvegno implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = textFieldNome.getText();
			String descrizione = editorPaneDescrizione.getText();
			String luogo = textFieldLuogo.getText();
			
			String sql = "INSERT INTO convegni (nome, logo, descrizione, luogo) VALUES" + 
    	   				 "('"+name+"', '0','"+descrizione+"','"+luogo+"')";
			
			try {
				databaseInsert(sql);							
				modelUpdate();
				
				panelProgramma.remove(comboBoxConvegni);
				panelProgramma.revalidate(); 				
				panelPartecipanti.remove(comboBoxPartecipanti);
				panelPartecipanti.revalidate();
				frame.repaint();
				comboBoxConvegni = new JComboBox(modelComboBox);
				comboBoxPartecipanti = new JComboBox(modelComboBox);
				
				panelPartecipanti.add(comboBoxPartecipanti, "cell 1 2 7 1,grow");
				panelProgramma.add(comboBoxConvegni, "cell 1 0,growx");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class SendButtonListenerProgramma implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Item item = (Item)comboBoxConvegni.getSelectedItem();	        
			String idConvegno = item.getId();					
			LocalDate localDate = datePicker.getDate();
			LocalTime localTime = timePicker.getTime();					
			String programma = editorPaneProgramma.getText();
			
			String sql = "INSERT INTO programma (idConvegno, data, ora, programma) VALUES" + 
	   				 	 "('"+idConvegno+"', '"+localDate+"','"+localTime+"','"+programma+"')";
			//System.out.println(sql);
			try {
				databaseInsert(sql);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		}
		
	}
	
	private void modelUpdate(){
		String sql = "SELECT id, nome FROM convegni";
		try {
			Map<String,String> result=databaseSelect(sql);			
			modelComboBox = new Vector();	        
			for(String  key : result.keySet()){
				modelComboBox.addElement( new Item(key, result.get(key) ) );				
			}						         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void databaseInsert(String sql) throws IOException {
		  /**
        * 3306 is the default port for MySQL in XAMPP. Note both the 
        * MySQL server and Apache must be running. 
        */
				
       String url = "jdbc:mysql://localhost:3306/";
       
       /**
        * The MySQL user.
        */
       String user = "root";
       
       /**
        * Password for the above MySQL user. If no password has been 
        * set (as is the default for the root user in XAMPP's MySQL),
        * an empty string can be used.
        */
       String password = "";              
       
       try
       {
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           Connection con = DriverManager.getConnection(url, user, password);
           
           Statement stt = con.createStatement();
           
           /**
            * Create and select a database for use. 
            */
          // stt.execute("CREATE DATABASE IF NOT EXISTS hellosmile");
           stt.execute("USE test");
           
           /**
            * Create an example table
            */
           //stt.execute("DROP TABLE IF EXISTS music");
          // stt.execute("CREATE TABLE music (" +
          //         "id BIGINT NOT NULL AUTO_INCREMENT,"
          //         + "emotion VARCHAR(25),"
           //        + "url VARCHAR(25),"
           //        + "PRIMARY KEY(id)"
           //        + ")");
           
           /**
            * Add entries to the example table
            */
           //stt.execute("INSERT INTO music (emotion, url) VALUES" + 
                //   "('sadness', 'Bloggs'), ('happy', 'Bloggs'), ('anger', 'Hill')");
           
           /**
            * Query people entries with the lname 'Bloggs'
            */
           //Random r = new Random();           
                      
           //String sql = "SELECT * FROM partecipanti";            
           System.out.println(sql);
           //ResultSet res = stt.executeQuery(sql);
           stt.execute(sql);
           
           /**
            * Iterate over the result set from the above query
            */
          /* while (res.next())
           {
               System.out.println(res.getString("id_convegno"));
               //music = res.getString("music");
           }
           System.out.println("");
           
           /**
            * Free all opened resources
            */
          // res.close();
           stt.close();            
           con.close();            
           
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
              
	}

	private Map<String,String> databaseSelect(String sql) throws IOException {
		  /**
	  * 3306 is the default port for MySQL in XAMPP. Note both the 
	  * MySQL server and Apache must be running. 
	  */
				
	 String url = "jdbc:mysql://localhost:3306/";
	
	 /**
	  * The MySQL user.
	  */
	 String user = "root";
	
	 /**
	  * Password for the above MySQL user. If no password has been 
	  * set (as is the default for the root user in XAMPP's MySQL),
	  * an empty string can be used.
	  */
	 String password = "";
	
	 Map<String,String> result = new HashMap<String,String>();
	
	 try
	 {
	     Class.forName("com.mysql.jdbc.Driver").newInstance();
	     Connection con = DriverManager.getConnection(url, user, password);
	     
	     Statement stt = con.createStatement();
	     
	     /**
	      * Create and select a database for use. 
	      */
	    // stt.execute("CREATE DATABASE IF NOT EXISTS hellosmile");
	     stt.execute("USE test");
	     
	     /**
	      * Create an example table
	      */
	     //stt.execute("DROP TABLE IF EXISTS music");
	    // stt.execute("CREATE TABLE music (" +
	    //         "id BIGINT NOT NULL AUTO_INCREMENT,"
	    //         + "emotion VARCHAR(25),"
	     //        + "url VARCHAR(25),"
	     //        + "PRIMARY KEY(id)"
	     //        + ")");
	     
	     /**
	      * Add entries to the example table
	      */
	     //stt.execute("INSERT INTO music (emotion, url) VALUES" + 
	          //   "('sadness', 'Bloggs'), ('happy', 'Bloggs'), ('anger', 'Hill')");
	     
	     /**
	      * Query people entries with the lname 'Bloggs'
	      */
	     //Random r = new Random();        
	     //String sql = "SELECT * FROM partecipanti";            
	     System.out.println(sql);
	     ResultSet res = stt.executeQuery(sql);	     
	     	     
	     while (res.next())
	     {	             	    	 
	    	 result.put(res.getString("id"),res.getString("nome"));
	     }	
	     
	     /**
	      * Free all opened resources
	      */
	     res.close();
	     stt.close();            
	     con.close();            
	     
	 }
	 catch (Exception e)
	 {
	     e.printStackTrace();
	 }
	    
	 	return result;
	
	}
	
	class ItemRenderer extends BasicComboBoxRenderer
    {
        public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

            if (value != null)
            {
                Item item = (Item)value;
                setText( item.getTitle().toUpperCase() );
            }

            /*
            if (index == -1)
            {
                Item item = (Item)value;
                setText( "" + item.getId() );
            }
             */

            return this;
        }
    }
	
	class Item
    {
        private String id;
        private String title;

        public Item(String id, String title)
        {
            this.id = id;
            this.title = title;
        }

        public String getId()
        {
            return id;
        }

        public String getTitle()
        {
            return title;
        }

        public String toString()
        {
            return title;
        }
    }
	
	

}
