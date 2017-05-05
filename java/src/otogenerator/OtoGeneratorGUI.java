package otogenerator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class OtoGeneratorGUI implements ActionListener{
	static File indexFile, replaceFile;
	static String suffix, consonant, cutoff, preutt, overlap;
	static int offset, offinc, maxDups;
	static boolean startend, replace;
	private static JTextField txtSuffix;
	private static JTextField txtInitOff;
	private static JTextField txtOffInc;
	private static JTextField txtConsonant;
	private static JTextField txtCutoff;
	private static JTextField txtPreutt;
	private static JTextField txtOverlap;
	private static JTextField txtMaxDups;
	private static JRadioButton rdbtnStartendyes, rdbtnStartendno, rdbtnReplaceyes, rdbtnReplaceno;
	private static JButton btnGenerate;
	private static JFrame frame;
	
	public static void main(String[] args){
		OtoGeneratorGUI otoGui = new OtoGeneratorGUI();
		otoGui.guiiiii();
	}
	
	public void guiiiii() {
		indexFile = new File("./csv/index.csv");
		replaceFile = new File("./csv/replace.csv");
		
		frame = new JFrame("OTO Generator");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{77, 76, 0};
		gbl_panel.rowHeights = new int[]{33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblSuffix = new JLabel("Suffix");
		GridBagConstraints gbc_lblInitOff_1 = new GridBagConstraints();
		gbc_lblInitOff_1.anchor = GridBagConstraints.WEST;
		gbc_lblInitOff_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblInitOff_1.gridx = 0;
		gbc_lblInitOff_1.gridy = 0;
		panel.add(lblSuffix, gbc_lblInitOff_1);
		
		txtSuffix = new JTextField();
		GridBagConstraints gbc_txtSuffix = new GridBagConstraints();
		gbc_txtSuffix.fill = GridBagConstraints.BOTH;
		gbc_txtSuffix.insets = new Insets(0, 0, 5, 0);
		gbc_txtSuffix.gridx = 1;
		gbc_txtSuffix.gridy = 0;
		panel.add(txtSuffix, gbc_txtSuffix);
		txtSuffix.setColumns(10);
		
		JLabel lblInitOff = new JLabel("Initial Offset");
		GridBagConstraints gbc_lblInitOff = new GridBagConstraints();
		gbc_lblInitOff.anchor = GridBagConstraints.WEST;
		gbc_lblInitOff.insets = new Insets(0, 0, 5, 5);
		gbc_lblInitOff.gridx = 0;
		gbc_lblInitOff.gridy = 1;
		panel.add(lblInitOff, gbc_lblInitOff);
		
		txtInitOff = new JTextField();
		GridBagConstraints gbc_txtInitOff = new GridBagConstraints();
		gbc_txtInitOff.insets = new Insets(0, 0, 5, 0);
		gbc_txtInitOff.fill = GridBagConstraints.BOTH;
		gbc_txtInitOff.gridx = 1;
		gbc_txtInitOff.gridy = 1;
		panel.add(txtInitOff, gbc_txtInitOff);
		txtInitOff.setColumns(10);
		
		JLabel lblOffInc = new JLabel("Offset Increment");
		GridBagConstraints gbc_lblOffInc = new GridBagConstraints();
		gbc_lblOffInc.anchor = GridBagConstraints.WEST;
		gbc_lblOffInc.insets = new Insets(0, 0, 5, 5);
		gbc_lblOffInc.gridx = 0;
		gbc_lblOffInc.gridy = 2;
		panel.add(lblOffInc, gbc_lblOffInc);
		
		txtOffInc = new JTextField();
		GridBagConstraints gbc_txtOffInc = new GridBagConstraints();
		gbc_txtOffInc.insets = new Insets(0, 0, 5, 0);
		gbc_txtOffInc.fill = GridBagConstraints.BOTH;
		gbc_txtOffInc.gridx = 1;
		gbc_txtOffInc.gridy = 2;
		panel.add(txtOffInc, gbc_txtOffInc);
		txtOffInc.setColumns(10);
		
		JLabel lblConsonant = new JLabel("Consonant");
		GridBagConstraints gbc_lblConsonant = new GridBagConstraints();
		gbc_lblConsonant.anchor = GridBagConstraints.WEST;
		gbc_lblConsonant.insets = new Insets(0, 0, 5, 5);
		gbc_lblConsonant.gridx = 0;
		gbc_lblConsonant.gridy = 3;
		panel.add(lblConsonant, gbc_lblConsonant);
		
		txtConsonant = new JTextField();
		GridBagConstraints gbc_txtConsonant = new GridBagConstraints();
		gbc_txtConsonant.insets = new Insets(0, 0, 5, 0);
		gbc_txtConsonant.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConsonant.gridx = 1;
		gbc_txtConsonant.gridy = 3;
		panel.add(txtConsonant, gbc_txtConsonant);
		txtConsonant.setColumns(10);
		
		JLabel lblCutoff = new JLabel("Cutoff");
		GridBagConstraints gbc_lblCutoff = new GridBagConstraints();
		gbc_lblCutoff.anchor = GridBagConstraints.WEST;
		gbc_lblCutoff.insets = new Insets(0, 0, 5, 5);
		gbc_lblCutoff.gridx = 0;
		gbc_lblCutoff.gridy = 4;
		panel.add(lblCutoff, gbc_lblCutoff);
		
		txtCutoff = new JTextField();
		GridBagConstraints gbc_txtCutoff = new GridBagConstraints();
		gbc_txtCutoff.insets = new Insets(0, 0, 5, 0);
		gbc_txtCutoff.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCutoff.gridx = 1;
		gbc_txtCutoff.gridy = 4;
		panel.add(txtCutoff, gbc_txtCutoff);
		txtCutoff.setColumns(10);
		
		JLabel lblPreutt = new JLabel("Preutterance");
		GridBagConstraints gbc_lblPreutt = new GridBagConstraints();
		gbc_lblPreutt.anchor = GridBagConstraints.WEST;
		gbc_lblPreutt.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreutt.gridx = 0;
		gbc_lblPreutt.gridy = 5;
		panel.add(lblPreutt, gbc_lblPreutt);
		
		txtPreutt = new JTextField();
		GridBagConstraints gbc_txtPreutt = new GridBagConstraints();
		gbc_txtPreutt.anchor = GridBagConstraints.NORTH;
		gbc_txtPreutt.insets = new Insets(0, 0, 5, 0);
		gbc_txtPreutt.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPreutt.gridx = 1;
		gbc_txtPreutt.gridy = 5;
		panel.add(txtPreutt, gbc_txtPreutt);
		txtPreutt.setColumns(10);
		
		JLabel lblOverlap = new JLabel("Overlap");
		GridBagConstraints gbc_lblOverlap = new GridBagConstraints();
		gbc_lblOverlap.anchor = GridBagConstraints.WEST;
		gbc_lblOverlap.insets = new Insets(0, 0, 5, 5);
		gbc_lblOverlap.gridx = 0;
		gbc_lblOverlap.gridy = 6;
		panel.add(lblOverlap, gbc_lblOverlap);
		
		txtOverlap = new JTextField();
		GridBagConstraints gbc_txtOverlap = new GridBagConstraints();
		gbc_txtOverlap.insets = new Insets(0, 0, 5, 0);
		gbc_txtOverlap.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOverlap.gridx = 1;
		gbc_txtOverlap.gridy = 6;
		panel.add(txtOverlap, gbc_txtOverlap);
		txtOverlap.setColumns(10);
		
		JLabel lblStartend = new JLabel("Include start/end aliases? (such as [- k])");
		GridBagConstraints gbc_lblStartend = new GridBagConstraints();
		gbc_lblStartend.anchor = GridBagConstraints.WEST;
		gbc_lblStartend.gridwidth = 2;
		gbc_lblStartend.insets = new Insets(0, 0, 5, 0);
		gbc_lblStartend.gridx = 0;
		gbc_lblStartend.gridy = 7;
		panel.add(lblStartend, gbc_lblStartend);
		
		ButtonGroup startendGroup = new ButtonGroup();
		
		rdbtnStartendyes = new JRadioButton("Include");
		startendGroup.add(rdbtnStartendyes);
		GridBagConstraints gbc_rdbtnStartendyes = new GridBagConstraints();
		gbc_rdbtnStartendyes.gridwidth = 2;
		gbc_rdbtnStartendyes.anchor = GridBagConstraints.WEST;
		gbc_rdbtnStartendyes.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnStartendyes.gridx = 0;
		gbc_rdbtnStartendyes.gridy = 8;
		panel.add(rdbtnStartendyes, gbc_rdbtnStartendyes);
		
		rdbtnStartendno = new JRadioButton("Don't include");
		startendGroup.add(rdbtnStartendno);
		GridBagConstraints gbc_rdbtnStartendno = new GridBagConstraints();
		gbc_rdbtnStartendno.gridwidth = 2;
		gbc_rdbtnStartendno.anchor = GridBagConstraints.WEST;
		gbc_rdbtnStartendno.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnStartendno.gridx = 0;
		gbc_rdbtnStartendno.gridy = 9;
		panel.add(rdbtnStartendno, gbc_rdbtnStartendno);
		
		JLabel lblReplace = new JLabel("Replace aliases according to replace.csv?");
		GridBagConstraints gbc_lblReplace = new GridBagConstraints();
		gbc_lblReplace.anchor = GridBagConstraints.WEST;
		gbc_lblReplace.gridwidth = 2;
		gbc_lblReplace.insets = new Insets(0, 0, 5, 0);
		gbc_lblReplace.gridx = 0;
		gbc_lblReplace.gridy = 10;
		panel.add(lblReplace, gbc_lblReplace);
		
		ButtonGroup replaceGroup = new ButtonGroup();
		
		rdbtnReplaceyes = new JRadioButton("Replace");
		replaceGroup.add(rdbtnReplaceyes);
		GridBagConstraints gbc_rdbtnReplaceyes = new GridBagConstraints();
		gbc_rdbtnReplaceyes.gridwidth = 2;
		gbc_rdbtnReplaceyes.anchor = GridBagConstraints.WEST;
		gbc_rdbtnReplaceyes.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnReplaceyes.gridx = 0;
		gbc_rdbtnReplaceyes.gridy = 11;
		panel.add(rdbtnReplaceyes, gbc_rdbtnReplaceyes);
		
		rdbtnReplaceno = new JRadioButton("Don't replace");
		replaceGroup.add(rdbtnReplaceno);
		GridBagConstraints gbc_rdbtnReplaceno = new GridBagConstraints();
		gbc_rdbtnReplaceno.gridwidth = 2;
		gbc_rdbtnReplaceno.anchor = GridBagConstraints.WEST;
		gbc_rdbtnReplaceno.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnReplaceno.gridx = 0;
		gbc_rdbtnReplaceno.gridy = 12;
		panel.add(rdbtnReplaceno, gbc_rdbtnReplaceno);
		
		JLabel lblMaxDups = new JLabel("Maximum duplicates (0 to delete all)");
		GridBagConstraints gbc_lblMaxDups = new GridBagConstraints();
		gbc_lblMaxDups.anchor = GridBagConstraints.WEST;
		gbc_lblMaxDups.gridwidth = 2;
		gbc_lblMaxDups.insets = new Insets(0, 0, 5, 0);
		gbc_lblMaxDups.gridx = 0;
		gbc_lblMaxDups.gridy = 13;
		panel.add(lblMaxDups, gbc_lblMaxDups);
		
		txtMaxDups = new JTextField();
		GridBagConstraints gbc_txtMaxDups = new GridBagConstraints();
		gbc_txtMaxDups.insets = new Insets(0, 0, 5, 5);
		gbc_txtMaxDups.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxDups.gridx = 0;
		gbc_txtMaxDups.gridy = 14;
		panel.add(txtMaxDups, gbc_txtMaxDups);
		txtMaxDups.setColumns(10);
		
		btnGenerate = new JButton("Generate OTO");
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.gridwidth = 2;
		gbc_btnGenerate.gridx = 0;
		gbc_btnGenerate.gridy = 15;
		panel.add(btnGenerate, gbc_btnGenerate);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    
	    rdbtnStartendyes.addActionListener(this);
	    rdbtnStartendno.addActionListener(this);
	    rdbtnReplaceyes.addActionListener(this);
	    rdbtnReplaceno.addActionListener(this);
	    btnGenerate.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {  
		if (e.getSource() == rdbtnStartendyes){
			startend = true;
		}
		if (e.getSource() == rdbtnStartendno){
			startend = false;
		}
		if (e.getSource() == rdbtnReplaceyes){
			replace = true;
		}
		if (e.getSource() == rdbtnReplaceno){
			replace = false;
		}
		if (e.getSource() == btnGenerate){
			suffix = txtSuffix.getText();
			offset = Integer.parseInt(txtInitOff.getText());
			offinc = Integer.parseInt(txtOffInc.getText());
			consonant = txtConsonant.getText();
			cutoff = txtCutoff.getText();
			preutt = txtPreutt.getText();
			overlap = txtOverlap.getText();
		 	maxDups = Integer.parseInt(txtMaxDups.getText());
		 	
		 	OtoGenerator.generateOto(indexFile, replaceFile, 
		 							 suffix, offset, offinc, consonant, cutoff, preutt, overlap, 
		 							 startend, replace, maxDups);
		 	JOptionPane.showMessageDialog(null, "Done.", "OTO Generator", JOptionPane.INFORMATION_MESSAGE);
		 	frame.dispose();
		}
    }
}
