/*
 * Created on Oct 3, 2006
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package multiplier;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MultiplierFrame extends JFrame implements MultiplierListener {

    private JPanel jContentPane = null;
    private JLabel jLblMultiplier = null;
    private JTextField jTxtMultiplier = null;
    private JButton jBtnStep = null;
    private JPanel jContent = null;
    private JLabel jLblMultiplicand = null;
    private JTextField jTxtMuliplicand = null;
    private JLabel jLblProduct = null;
    private JTextField jTxtProduct = null;
    private JLabel jLblSteps = null;
    private JTextField jTxtSteps = null;
    private JButton jBtnRun = null;
    private JLabel jLblStatus = null;
    private JLabel jLblTitle = null;
    public MultiplierFrame() throws HeadlessException {
        super();
        // TODO Auto-generated constructor stub

        initialize();
    }

    public MultiplierFrame(GraphicsConfiguration arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub

        initialize();
    }

    public MultiplierFrame(String arg0) throws HeadlessException {
        super(arg0);
        // TODO Auto-generated constructor stub

        initialize();
    }

    public MultiplierFrame(String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub

        initialize();
    }

    /**
     * This method initializes jTxtMultiplier	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTxtMultiplier() {
        if (jTxtMultiplier == null) {
            jTxtMultiplier = new JTextField();
        }
        return jTxtMultiplier;
    }

    /**
     * This method initializes jBtnStep	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJBtnStep() {
        if (jBtnStep == null) {
            jBtnStep = new JButton();
            jBtnStep.setText("Step");
        }
        return jBtnStep;
    }

    /**
     * This method initializes jContent	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJContent() {
        if (jContent == null) {
            jContent = new MultiplierPane();
            jContent.setForeground(java.awt.Color.black);
            jContent.setBackground(java.awt.Color.white);
            jContent.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED), "Multiplier ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
        }
        return jContent;
    }

    /**
     * This method initializes jTxtMuliplicand	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTxtMuliplicand() {
        if (jTxtMuliplicand == null) {
            jTxtMuliplicand = new JTextField();
        }
        return jTxtMuliplicand;
    }

    /**
     * This method initializes jTxtProduct	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTxtProduct() {
        if (jTxtProduct == null) {
            jTxtProduct = new JTextField();
        }
        return jTxtProduct;
    }

    /**
     * This method initializes jTxtSteps	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTxtSteps() {
        if (jTxtSteps == null) {
            jTxtSteps = new JTextField();
        }
        return jTxtSteps;
    }

    /**
     * This method initializes jBtnRun	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJBtnRun() {
        if (jBtnRun == null) {
            jBtnRun = new JButton();
            jBtnRun.setText("Run");
        }
        return jBtnRun;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(620, 446);
        this.setContentPane(getJContentPane());
        this.setTitle("Multiplier");
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.insets = new java.awt.Insets(10,10,10,10);
            gridBagConstraints8.gridwidth = 4;
            gridBagConstraints8.gridy = 0;
            jLblTitle = new JLabel();
            jLblTitle.setText("CSC220 - Multiplier Demonstration");
            jLblTitle.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 24));
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.insets = new java.awt.Insets(10,10,10,10);
            gridBagConstraints7.gridwidth = 4;
            gridBagConstraints7.gridy = 3;
            jLblStatus = new JLabel();
            jLblStatus.setText("Status of Multiplier");
            jLblStatus.setFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 18));
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 3;
            gridBagConstraints6.gridy = 5;
            GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
            gridBagConstraints51.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints51.gridy = 4;
            gridBagConstraints51.weightx = 1.0;
            gridBagConstraints51.gridx = 3;
            GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
            gridBagConstraints41.gridx = 2;
            gridBagConstraints41.gridy = 4;
            jLblSteps = new JLabel();
            jLblSteps.setText("Steps:");
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 4;
            gridBagConstraints31.weightx = 1.0;
            gridBagConstraints31.gridx = 1;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.gridy = 4;
            jLblProduct = new JLabel();
            jLblProduct.setText("Product:");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 1;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.gridwidth = 3;
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            jLblMultiplicand = new JLabel();
            jLblMultiplicand.setText("Multiplicand:");
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints21.weightx = 1.0D;
            gridBagConstraints21.weighty = 1.0D;
            gridBagConstraints21.gridwidth = 3;
            gridBagConstraints21.insets = new java.awt.Insets(5,5,5,5);
            gridBagConstraints21.gridy = 6;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 1;
            gridBagConstraints4.gridy = 5;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 2;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.gridwidth = 3;
            gridBagConstraints3.gridx = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 2;
            jLblMultiplier = new JLabel();
            jLblMultiplier.setText("Multiplier:");
            jContentPane = new JPanel();
            jContentPane.setLayout(new GridBagLayout());
            jContentPane.add(jLblMultiplier, gridBagConstraints2);
            jContentPane.add(getJTxtMultiplier(), gridBagConstraints3);
            jContentPane.add(getJBtnStep(), gridBagConstraints4);
            jContentPane.add(getJContent(), gridBagConstraints21);
            jContentPane.add(jLblMultiplicand, gridBagConstraints);
            jContentPane.add(getJTxtMuliplicand(), gridBagConstraints1);
            jContentPane.add(jLblProduct, gridBagConstraints5);
            jContentPane.add(getJTxtProduct(), gridBagConstraints31);
            jContentPane.add(jLblSteps, gridBagConstraints41);
            jContentPane.add(getJTxtSteps(), gridBagConstraints51);
            jContentPane.add(getJBtnRun(), gridBagConstraints6);
            jContentPane.add(jLblStatus, gridBagConstraints7);
            jContentPane.add(jLblTitle, gridBagConstraints8);
        }
        return jContentPane;
    }
    
    public void draw( )
    {
        Graphics G = jContent.getGraphics();
        if (G == null)
            throw new RuntimeException("dkdkd");
        
        
    }
    
    
    public void handleMultiplierStep(MultiplierState oldState, MultiplierState newState) {
        
        
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
