package helloworldrest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
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
		panel_1.setLayout(new MigLayout("", "[][grow][][grow]", "[][][grow][grow][]"));
		
		JLabel labelName = new JLabel("Nome:");
		panel_1.add(labelName, "cell 0 0");
		
		textFieldName = new JTextField();
		panel_1.add(textFieldName, "cell 1 0 3 1,growx");
		textFieldName.setColumns(10);
		
		JLabel labelSurname = new JLabel("Cognome:");
		panel_1.add(labelSurname, "cell 0 1");
		
		textFieldSurname = new JTextField();
		panel_1.add(textFieldSurname, "cell 1 1 3 1,growx");
		textFieldSurname.setColumns(10);
		
		JButton btnInvia = new JButton("Invia");
		btnInvia.addActionListener(new SendButtonListener());				
		
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Nome"); 
		model.addColumn("Cognome"); 
		model.addColumn("Area"); 
		model.addColumn("H-index"); 
		
		panel_1.add(table, "cell 0 2 4 2,grow");
		panel_1.add(btnInvia, "cell 3 4,alignx right");
	}
	
	private class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = textFieldName.getText();
			String surname = textFieldSurname.getText();			
			Scopus scopus = new Scopus(name, surname);
			JSONObject ja;			
			try {
				ja = new JSONObject(scopus.webScraping());				
		        JSONArray jArr = (JSONArray) ja.getJSONArray("scopus");
		        for(int i = 0; i < jArr.length();i++) {
		            JSONObject innerObj = jArr.getJSONObject(i);
		            model.addRow(new Object[]{innerObj.getString("nome"),innerObj.getString("nome"),innerObj.getString("area"),innerObj.getString("h-index")});
		            
		        }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		}
		
	}

}
