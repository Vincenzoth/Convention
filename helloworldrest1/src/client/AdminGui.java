package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.HashMap;

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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import org.json.*;
import javax.swing.JSeparator;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Label;
import javax.swing.JEditorPane;

import javax.swing.JRadioButton;
import com.toedter.components.JSpinField;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.JFileChooser;

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
	private JButton btnFileChooser;

	private ServerConnection conn;

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
		conn = new ServerConnection();

		frame = new JFrame();
		frame.setBounds(100, 100, 546, 371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		panelConvegno = new JPanel();
		tabbedPane.addTab("Convegno", null, panelConvegno, null);
		panelConvegno.setLayout(new MigLayout("", "[][grow]", "[][][][grow][][]"));

		Label labelNome = new Label("Nome:");
		panelConvegno.add(labelNome, "cell 0 0,growx");

		textFieldNome = new JTextField();
		panelConvegno.add(textFieldNome, "cell 1 0,growx");
		textFieldNome.setColumns(10);

		JLabel lblLogo = new JLabel("Logo:");
		panelConvegno.add(lblLogo, "cell 0 1,growx");

		textFieldLogo = new JTextField();
		panelConvegno.add(textFieldLogo, "flowx,cell 1 1,growx");
		textFieldLogo.setColumns(10);

		JLabel lblDescrizione = new JLabel("Descrizione:");
		panelConvegno.add(lblDescrizione, "cell 0 3,growx,aligny top");

		editorPaneDescrizione = new JEditorPane();
		panelConvegno.add(editorPaneDescrizione, "cell 1 3,grow");

		JLabel lblLuogo = new JLabel("Luogo:");
		panelConvegno.add(lblLuogo, "cell 0 4,grow");

		textFieldLuogo = new JTextField();
		panelConvegno.add(textFieldLuogo, "cell 1 4,growx");
		textFieldLuogo.setColumns(10);

		JButton btnInviaConvegno = new JButton("Invia");
		btnInviaConvegno.addActionListener(new SendButtonListenerConvegno());
		panelConvegno.add(btnInviaConvegno, "cell 1 5,alignx right");

		btnFileChooser = new JButton("...");
		btnFileChooser.addActionListener(new FileChooserFrame());
		panelConvegno.add(btnFileChooser, "cell 1 1");

		panelProgramma = new JPanel();
		tabbedPane.addTab("Programma", null, panelProgramma, null);
		panelProgramma.setLayout(
				new MigLayout("", "[72px][433px,grow]", "[16px][][16px,grow][][grow][191px][grow][grow][16px][25px]"));

		JLabel lblConvegnoProgramma = new JLabel("Convegno:");
		panelProgramma.add(lblConvegnoProgramma, "cell 0 0,alignx left,aligny top");

		modelUpdate();
		comboBoxConvegni = new JComboBox(modelComboBox);
		comboBoxConvegni.setRenderer(new ItemRenderer());

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

		String[] columnNames = { "ID", "Nome", "Area", "H-index", "" };
		model = new DefaultTableModel(null, columnNames);

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
		comboBoxPartecipanti.setRenderer(new ItemRenderer());
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
			Item item = (Item) comboBoxPartecipanti.getSelectedItem();
			String idConvegno = item.getId();
			String name = textFieldNomePartecipante.getText();
			String surname = textFieldCognomePartecipante.getText();
			// Scopus scopus = new Scopus(name, surname);
			JSONObject ja;

			Action add = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					JTable table = (JTable) e.getSource();
					int modelRow = Integer.valueOf(e.getActionCommand());
					System.out.println();
					int organizzatore = 0;
					if (rdbtnOrganizzatore.isSelected())
						organizzatore = 1;
					String sql = "INSERT INTO partecipanti (id_convegno, id_partecipante,tipologia) VALUES" + "('"
							+ idConvegno + "', '" + table.getModel().getValueAt(modelRow, 0).toString() + "','"
							+ organizzatore + "')";

					conn.databaseInsert(sql);
				}
			};

			try {
				JSONObject jObject = new JSONObject(conn.scopusAuthor(name, surname));
				JSONArray jArr = jObject.getJSONArray("scopus");
				for (int i = 0; i < jArr.length(); i++) {
					JSONObject innerObj = jArr.getJSONObject(i);
					model.addRow(new Object[] { innerObj.getString("id"), innerObj.getString("nome"),
							innerObj.getString("area"), innerObj.getString("h-index"), "Aggiungi" });
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
			String logo = textFieldLogo.getText();
			String descrizione = editorPaneDescrizione.getText();
			String luogo = textFieldLuogo.getText();

			String url = "http://localhost:8080/helloworldrest1/users/upload";
			String charset = "UTF-8";
			File binaryFile = new File(logo);
			String boundary = Long.toHexString(System.currentTimeMillis()); // Just
																			// generate
																			// some
																			// unique
																			// random
																			// value.
			String CRLF = "\r\n"; // Line separator required by
									// multipart/form-data.

			try {
				URLConnection connection = new URL(url).openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
				OutputStream output = connection.getOutputStream();
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

				// Send binary file.
				writer.append("--" + boundary).append(CRLF);
				writer.append(
						"Content-Disposition: form-data; name=\"recFile\"; filename=\"" + binaryFile.getName() + "\"")
						.append(CRLF);
				writer.append("Content-Type: image/jpeg");
				writer.append("Content-Transfer-Encoding: binary").append(CRLF);
				writer.append(CRLF).flush();
				Files.copy(binaryFile.toPath(), output);
				output.flush(); // Important before continuing with writer!
				writer.append(CRLF).flush(); // CRLF is important! It indicates
												// end of boundary.

				// End of multipart/form-data.
				writer.append("--" + boundary + "--").append(CRLF).flush();

				// Request is lazily fired whenever you need to obtain
				// information about response.
				int responseCode = ((HttpURLConnection) connection).getResponseCode();
				System.out.println(responseCode); // Should be 200

				String sql = "INSERT INTO convegni (nome, logo, descrizione, luogo) VALUES" + "('" + name + "', '"
						+ binaryFile.getName() + "','" + descrizione + "','" + luogo + "')";

				conn.databaseInsert(sql);
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

	private class FileChooserFrame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileChooser = new JFileChooser();
			int n = fileChooser.showOpenDialog(null);
			if (n != 1)
				textFieldLogo.setText(fileChooser.getSelectedFile().toString());
		}

	}

	private class SendButtonListenerProgramma implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Item item = (Item) comboBoxConvegni.getSelectedItem();
			String idConvegno = item.getId();
			LocalDate localDate = datePicker.getDate();
			LocalTime localTime = timePicker.getTime();
			String programma = editorPaneProgramma.getText();

			String sql = "INSERT INTO programma (idConvegno, data, ora, programma) VALUES" + "('" + idConvegno + "', '"
					+ localDate + "','" + localTime + "','" + programma + "')";

			conn.databaseInsert(sql);

		}

	}

	private void modelUpdate() {
		String sql = "SELECT id, nome FROM convegni";
		try {
			String json = conn.databaseSelect(sql);

			HashMap<String, String> map = new HashMap<String, String>();
			JSONObject jObject = new JSONObject(json);
			JSONArray arr = jObject.getJSONArray("convegni");
			for (int i = 0; i < arr.length(); i++) {
				String nome = arr.getJSONObject(i).getString("nome");
				String id = arr.getJSONObject(i).getString("id");
				map.put(id, nome);
			}

			System.out.println(map);

			modelComboBox = new Vector();
			for (String key : map.keySet()) {
				modelComboBox.addElement(new Item(key, map.get(key)));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ItemRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value != null) {
				Item item = (Item) value;
				setText(item.getTitle().toUpperCase());
			}

			/*
			 * if (index == -1) { Item item = (Item)value; setText( "" +
			 * item.getId() ); }
			 */

			return this;
		}
	}

	class Item {
		private String id;
		private String title;

		public Item(String id, String title) {
			this.id = id;
			this.title = title;
		}

		public String getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String toString() {
			return title;
		}
	}

}
