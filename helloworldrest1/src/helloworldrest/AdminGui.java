package helloworldrest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTable;
import org.json.*;

public class AdminGui {

	private JFrame frame;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTable table;
	DefaultTableModel model; 

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Convegno", null, panel, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Partecipanti", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[][grow][][grow][][][][]", "[][][][grow][grow][]"));
		
		JLabel labelName = new JLabel("Nome:");
		panel_1.add(labelName, "cell 0 0");
		
		textFieldName = new JTextField();
		panel_1.add(textFieldName, "cell 1 0 7 1,growx");
		textFieldName.setColumns(10);
		
		JLabel labelSurname = new JLabel("Cognome:");
		panel_1.add(labelSurname, "cell 0 1");
		
		JButton btnInvia = new JButton("Invia");
		btnInvia.addActionListener(new SendButtonListener());				
		
		String[] columnNames = {"ID", "Nome", "Area", "H-index",""};
		model = new DefaultTableModel(null,columnNames);
		
		textFieldSurname = new JTextField();
		panel_1.add(textFieldSurname, "cell 1 1 7 1,growx");
		textFieldSurname.setColumns(10);
		table = new JTable(model);		
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		
		JScrollPane scrollpane = new JScrollPane(table);
		
		panel_1.add(scrollpane, "cell 0 2 8 1");		
		panel_1.add(btnInvia, "cell 3 5,alignx right");
	}
	
	private class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = textFieldName.getText();
			String surname = textFieldSurname.getText();			
			Scopus scopus = new Scopus(name, surname);
			JSONObject ja;
			
			Action add = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        System.out.println(table.getModel().getValueAt(modelRow, 0));
			        
			        //((DefaultTableModel)table.getModel()).removeRow(modelRow);
			    }
			};
			
			try {
				database();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
	
	private static String database() throws IOException {
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
       
       String music = "";
       
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
           Random r = new Random();            
           String sql = "SELECT * FROM partecipanti";            
           System.out.println(sql);
           ResultSet res = stt.executeQuery(sql);
           
           /**
            * Iterate over the result set from the above query
            */
           while (res.next())
           {
               System.out.println(res.getString("id_convegno"));
               //music = res.getString("music");
           }
           System.out.println("");
           
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
       
       return music;
	}
	
	

}
