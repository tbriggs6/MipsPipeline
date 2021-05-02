/*
 * Created on Aug 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package mips.gui;

import instr.Instruction;
import instr.InstructionFormatException;
import instr.SimpleAssembler;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import mips.Memory;
import mips.RegisterFile;
import mips.pipe.*;
import javax.swing.JTable;

public class FiveStageDialog extends JFrame {

	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JLabel jLabel = null;
	private JTextField clockField = null;
	private JTextField jPC = null;
	private JLabel Clock = null;
	private JLabel jLabelPC = null;
	private JTextArea jRegsArea = null;
	private JLabel jCodeLabel = null;
	private JLabel jRegLabel = null;
	private FiveStagePanel jBaseMIPSPanel = null;

	private FiveStagePipeline pipeSim = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private String tableData[][] = null;
	
	private JButton jButtonReset = null;
	private JTextArea jConsole = null;
	
	private TextAreaObserver consOb = null;
	private JScrollPane jScrollPane1 = null;
	private JLabel jLabelAsmSource = null;
	private JTextField jInputSource = null;
	private JButton jOpenFile = null;
	/**
	 * This is the default constructor
	 */
	public FiveStageDialog(FiveStagePipeline pipeSim) {
		super();
		
		this.pipeSim = pipeSim;
		initialize();
		
		jRegsArea.setText(pipeSim.getRegs().toString());
		this.clockField.setText(pipeSim.getC().toString());
		this.jPC.setText(Integer.toHexString(pipeSim.getRegs().getPC()));
		this.jButton.setEnabled(true);
	}


	
	/**
	 * This is the default constructor
	 */
	public FiveStageDialog( ) {
		super();
		
		initializeSimulator();
		initialize();
		
		jRegsArea.setText(pipeSim.getRegs().toString());
		this.clockField.setText(pipeSim.getC().toString());
		this.jPC.setText(Integer.toHexString(pipeSim.getRegs().getPC()));
	 }

	/**
	 * 
	 */
	private void initializeSimulator() {
			Memory M = new Memory(0x10000);		// allocate 64K words
			
			RegisterFile R = new RegisterFile( );
			R.setPC(0x4000);
			R.setReg(31, 0x200);   // set return address for OS

			this.pipeSim = new FiveStagePipeline(M, R);
	}
	 
	
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(846, 662);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelAsmSource = new JLabel();
			jLabelAsmSource.setBounds(new java.awt.Rectangle(23,43,83,16));
			jLabelAsmSource.setText("Asm source:");
			jRegLabel = new JLabel();
			jRegLabel.setBounds(new java.awt.Rectangle(1,390,94,16));
			jRegLabel.setText("Registers:");
			jCodeLabel = new JLabel();
			jCodeLabel.setBounds(new java.awt.Rectangle(7,491,38,16));
			jCodeLabel.setText("Code:");
			jLabelPC = new JLabel();
			jLabelPC.setBounds(new java.awt.Rectangle(723,16,25,16));
			jLabelPC.setText("PC");
			Clock = new JLabel();
			Clock.setBounds(new java.awt.Rectangle(595,14,38,16));
			Clock.setText("Clock:");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(18,7,264,29));
			jLabel.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 18));
			jLabel.setText("MIPS 5-Stage Pipeline Simulator");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJBaseMIPSPanel(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getClockField(), null);
			jContentPane.add(getJPC(), null);
			jContentPane.add(Clock, null);
			jContentPane.add(jLabelPC, null);
			jContentPane.add(getJRegsArea(), null);
			jContentPane.add(jCodeLabel, null);
			jContentPane.add(jRegLabel, null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButtonReset(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(jLabelAsmSource, null);
			jContentPane.add(getJInputSource(), null);
			jContentPane.add(getJOpenFile(), null);
		}
		return jContentPane;
	}


	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(481,14,81,20));
			jButton.setEnabled(false);
			jButton.setText("Step");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					advanceSimulator( );
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes clockField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getClockField() {
		if (clockField == null) {
			clockField = new JTextField();
			clockField.setBounds(new java.awt.Rectangle(638,14,77,20));
			clockField.setName("jClock");
		}
		return clockField;
	}

	/**
	 * This method initializes jPC	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPC() {
		if (jPC == null) {
			jPC = new JTextField();
			jPC.setBounds(new java.awt.Rectangle(751,14,77,20));
		}
		return jPC;
	}

	/**
	 * This method initializes jRegsArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJRegsArea() {
		if (jRegsArea == null) {
			jRegsArea = new JTextArea();
			jRegsArea.setBounds(new java.awt.Rectangle(20,407,796,66));
			jRegsArea.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12));
		}
		return jRegsArea;
	}

	/**
	 * This method initializes jBaseMIPSPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJBaseMIPSPanel() {
		if (jBaseMIPSPanel == null) {
			jBaseMIPSPanel = new FiveStagePanel(pipeSim);
			jBaseMIPSPanel.setBounds(new java.awt.Rectangle(24,72,793,312));
		}
		return jBaseMIPSPanel;
	}

	private void advanceSimulator( )
	{
		if (!pipeSim.getC().isLow())
		{

				
			// at the end of a full cycle
			int c = pipeSim.getC().getClock();
			
			tableData[c][0] = "" + pipeSim.getC().getClock();
			
			if (pipeSim.getIF().getCurrentInstruction() != null)
				tableData[c][1] = pipeSim.getIF().getCurrentInstruction().getMnemonic();
			
			if (pipeSim.getID().getCurrentInstruction() != null)
				tableData[c][2] = pipeSim.getID().getCurrentInstruction().getMnemonic();
			
			if (pipeSim.getEX().getCurrentInstruction() != null)
				tableData[c][3] = pipeSim.getEX().getCurrentInstruction().getMnemonic();
			
			if (pipeSim.getMEM().getCurrentInstruction() != null)
				tableData[c][4] = pipeSim.getMEM().getCurrentInstruction().getMnemonic();
			
			if (pipeSim.getWB().getCurrentInstruction() != null)
				tableData[c][5] = pipeSim.getWB().getCurrentInstruction().getMnemonic();
			jTable.repaint();
		}
		
		pipeSim.tic();
		jRegsArea.setText(pipeSim.getRegs().toString());
		this.clockField.setText(pipeSim.getC().toString());
		this.jPC.setText(Integer.toHexString(pipeSim.getRegs().getPC()));
		
		this.jRegsArea.repaint();
		this.jBaseMIPSPanel.repaint();
		
		if (this.pipeSim.getRegs().getPC() <= 0x204)
		{
			this.jButton.setEnabled(false);
		}
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(350,509,450,99));
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setAutoscrolls(true);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			String columns[] = { "CLK", "IF", "ID", "EX", "MEM", "WB" };
			
			tableData = new String[500][6];
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 500; j++)
					tableData[j][i] = "";
			
			jTable = new JTable(tableData, columns);
			jTable.getColumnModel().getColumn(0).setWidth(20);
			for (int i = 1; i < 5; i++)
				jTable.getColumnModel().getColumn(i).setWidth(35);
		}
		return jTable;
	}

	/**
	 * This method initializes jButtonReset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonReset() {
		if (jButtonReset == null) {
			jButtonReset = new JButton();
			jButtonReset.setBounds(new java.awt.Rectangle(380,14,81,20));
			jButtonReset.setText("Reset");
			jButtonReset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pipeSim.getRegs().setPC(0x4000);
					jButton.setEnabled(true);
				}
			});
		}
		return jButtonReset;
	}

	/**
	 * This method initializes jConsole	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJConsole() {
		if (jConsole == null) {
			
			jConsole = new JTextArea();
			consOb = new TextAreaObserver(jConsole);
			jConsole.setRows(15);
			
			pipeSim.addObserver(consOb);
		}
		return jConsole;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(19,507,315,108));
			jScrollPane1.setViewportView(getJConsole());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jInputSource	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJInputSource() {
		if (jInputSource == null) {
			jInputSource = new JTextField();
			jInputSource.setBounds(new java.awt.Rectangle(119,44,298,20));
		}
		return jInputSource;
	}

	/**
	 * This method initializes jOpenFile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJOpenFile() {
		if (jOpenFile == null) {
			jOpenFile = new JButton();
			jOpenFile.setBounds(new java.awt.Rectangle(422,43,109,21));
			jOpenFile.setText("Open URL");
			jOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					loadASM( );
				}
			});
		}
		return jOpenFile;
	}

	private void loadASM( )
	{
		try {
			int address;
			Instruction instr[] = SimpleAssembler.assembleURL(this.jInputSource.getText());
			for (int i = 0; i < instr.length; i++)
			{
				address = 0x4000 + (i*4);
				pipeSim.getMemory().storeWord(address, instr[i].toInt( ));
			}
			jButton.setEnabled(true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null, e.getMessage(), "ASM Error", JOptionPane.ERROR_MESSAGE); 
			e.printStackTrace();
		}
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
