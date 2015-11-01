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

public class ProxyCheckerUI extends JFrame {

	private final JLabel totalProxies;
	private final JLabel liveProxies;
	private final JLabel deadProxies;
	private final JProgressBar progressBar;
	private final JButton checkButton;
	private final JTextArea consoleLog;
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
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		
		checkButton = new JButton("Run Check");
		checkButton.addActionListener(e -> {
			try {
				updateConsoleLog("Running proxy list check...");
				checker.parseProxies("proxies.txt");
				updateConsoleLog("Verifying proxies using " + Main.threadCount + " threads...");
	            checker.verifyProxies();
				checkButton.setEnabled(false);
				checkButton.setText("Check in progress");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		GridBagConstraints gbc_checkButton = new GridBagConstraints();
		gbc_checkButton.gridwidth = 3;
		gbc_checkButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkButton.insets = new Insets(0, 0, 5, 0);
		gbc_checkButton.gridx = 0;
		gbc_checkButton.gridy = 2;
		contentPane.add(checkButton, gbc_checkButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Console Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
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
