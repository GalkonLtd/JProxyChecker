package com.galkonltd.proxychecker;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ProxyCheckerUI extends JFrame {

	private final JLabel totalProxies;
	private final JLabel liveProxies;
	private final JLabel deadProxies;
	private final JProgressBar progressBar;
	private final JButton checkButton;
	private final JTextArea consoleLog;
	private final JCheckBox checkGoogle;
	private final JSpinner threadCountSpinner;
	private final JTextField portFilterField;
	private JPanel contentPane;
	private final ProxyChecker checker;

	public void updateTotalProxies(int count) {
		totalProxies.setText("Total proxies: " + count);
	}

	public void updateLiveProxies(int count) {
		liveProxies.setText("Live proxies: " + count);
	}


	public void updateDeadProxies(int count) {
		deadProxies.setText("Dead proxies: " + count);
	}

	public void updateProgress(int current, int max) {
		progressBar.setValue(current);
		progressBar.setMaximum(max);
	}

	public void updateConsoleLog(String text) {
		consoleLog.append("\n" + text);
		consoleLog.setCaretPosition(consoleLog.getDocument().getLength());
	}


	/**
	 * Create the frame.
	 */
	public ProxyCheckerUI(ProxyChecker checker) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.checker = checker;
		setTitle("JProxyChecker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		totalProxies = new JLabel("Total proxies: 0");
		GridBagConstraints gbc_totalProxies = new GridBagConstraints();
		gbc_totalProxies.insets = new Insets(0, 0, 5, 5);
		gbc_totalProxies.gridx = 0;
		gbc_totalProxies.gridy = 0;
		contentPane.add(totalProxies, gbc_totalProxies);
		
		liveProxies = new JLabel("Live proxies: 0");
		GridBagConstraints gbc_liveProxies = new GridBagConstraints();
		gbc_liveProxies.insets = new Insets(0, 0, 5, 5);
		gbc_liveProxies.gridx = 1;
		gbc_liveProxies.gridy = 0;
		contentPane.add(liveProxies, gbc_liveProxies);
		
		deadProxies = new JLabel("Dead proxies: 0");
		GridBagConstraints gbc_deadProxies = new GridBagConstraints();
		gbc_deadProxies.insets = new Insets(0, 0, 5, 0);
		gbc_deadProxies.gridx = 2;
		gbc_deadProxies.gridy = 0;
		contentPane.add(deadProxies, gbc_deadProxies);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 1;
		contentPane.add(progressBar, gbc_progressBar);


		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{68, 29, 0};
		gbl_panel_1.rowHeights = new int[]{20, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

		JLabel lblThreadCount = new JLabel("Thread count:");
		GridBagConstraints gbc_lblThreadCount = new GridBagConstraints();
		gbc_lblThreadCount.anchor = GridBagConstraints.WEST;
		gbc_lblThreadCount.insets = new Insets(0, 0, 0, 5);
		gbc_lblThreadCount.gridx = 0;
		gbc_lblThreadCount.gridy = 0;
		panel_1.add(lblThreadCount, gbc_lblThreadCount);

		threadCountSpinner = new JSpinner();
		threadCountSpinner.setModel(new SpinnerNumberModel(64, 1, 512, 8));
		threadCountSpinner.addChangeListener(e -> Main.threadCount = (int) threadCountSpinner.getValue());
		GridBagConstraints gbc_threadCountSpinner = new GridBagConstraints();
		gbc_threadCountSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_threadCountSpinner.anchor = GridBagConstraints.NORTH;
		gbc_threadCountSpinner.gridx = 1;
		gbc_threadCountSpinner.gridy = 0;
		panel_1.add(threadCountSpinner, gbc_threadCountSpinner);

		checkGoogle = new JCheckBox("Check Google.com");
		checkGoogle.addActionListener(e -> Main.checkGoogle = checkGoogle.isSelected());
		GridBagConstraints gbc_checkGoogle = new GridBagConstraints();
		gbc_checkGoogle.insets = new Insets(0, 0, 5, 5);
		gbc_checkGoogle.gridx = 1;
		gbc_checkGoogle.gridy = 2;
		contentPane.add(checkGoogle, gbc_checkGoogle);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 2;
		gbc_panel_2.gridy = 2;
		contentPane.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);

		JLabel lblFilterPorts = new JLabel("Filter ports:");
		GridBagConstraints gbc_lblFilterPorts = new GridBagConstraints();
		gbc_lblFilterPorts.insets = new Insets(0, 0, 0, 5);
		gbc_lblFilterPorts.anchor = GridBagConstraints.EAST;
		gbc_lblFilterPorts.fill = GridBagConstraints.VERTICAL;
		gbc_lblFilterPorts.gridx = 0;
		gbc_lblFilterPorts.gridy = 0;
		panel_2.add(lblFilterPorts, gbc_lblFilterPorts);

		portFilterField = new JTextField();
		GridBagConstraints gbc_portFilterField = new GridBagConstraints();
		gbc_portFilterField.fill = GridBagConstraints.HORIZONTAL;
		gbc_portFilterField.gridx = 1;
		gbc_portFilterField.gridy = 0;
		panel_2.add(portFilterField, gbc_portFilterField);
		portFilterField.setColumns(10);
		
		checkButton = new JButton("Run Check");
		checkButton.addActionListener(e -> {
			try {
				if (!portFilterField.getText().isEmpty()) {
					String[] args = portFilterField.getText().split(",");
					if (args != null) {
						for (String p : args) {
							try {
								int port = Integer.parseInt(p);
								Main.filteredPorts.add(port);
							} catch (Exception ex) {

							}
						}
					}
				}
				updateConsoleLog("Running proxy list check...");
				checker.parseProxies("proxies.txt");
				updateConsoleLog("Verifying proxies using " + Main.threadCount + " threads...");
	            checker.verifyProxies();
				checkButton.setEnabled(false);
				checkButton.setText("Check in progress");
				checkGoogle.setEnabled(false);
				threadCountSpinner.setEnabled(false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		GridBagConstraints gbc_checkButton = new GridBagConstraints();
		gbc_checkButton.gridwidth = 3;
		gbc_checkButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkButton.insets = new Insets(0, 0, 5, 0);
		gbc_checkButton.gridx = 0;
		gbc_checkButton.gridy = 3;
		contentPane.add(checkButton, gbc_checkButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Console Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		consoleLog = new JTextArea();
		consoleLog.setLineWrap(true);
		consoleLog.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(consoleLog);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane);

		setLocationRelativeTo(null);
		setVisible(true);
	}

}
