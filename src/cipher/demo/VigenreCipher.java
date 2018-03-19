package cipher.demo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VigenreCipher extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField tfPT;
	private JTextField tfKey;
	private JTextField tfCT;
	private JButton btnEC = new JButton("Encrypt");
	private JButton btnDC = new JButton("Decrypt");
	private JButton btnReset = new JButton("Reset All");
	char[][] VigenreArray = new char[26][26];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VigenreCipher frame = new VigenreCipher();
					frame.setVisible(true);
					frame.createVigenreArray();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*Vigenre Square Array(Default: Uppercased Letters)*/
	public void createVigenreArray() {
		int n = 65;  //initialized as A
		for(int i=0; i<26; i++) {        //fill the first row
			VigenreArray[i][0] = (char)n;
			n++;
		}
		for(int i=0; i<26; i++) {        //fill the other rows according to 1st row
			for(int j=1; j<26; j++) {
				VigenreArray[i][j] = (char)((int)VigenreArray[i][j-1] + 1);
				if(VigenreArray[i][j] > 90)   //91 -> 65
					VigenreArray[i][j] = (char)65;  
			}
		}
		
		/*Test the VigenreArray
		for(int i=0; i<26; i++) {
			System.out.println("ROW" + (i+1));
			for(int j=0; j<26; j++) {
				System.out.println(VigenreArray[i][j] + " ");
			}
		}	
		*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnEC) {   
			/*Encrypt Vigenre*/
			String strPlainText = getTfPT().getText();    
			String strKey = getTfKey().getText();
			StringBuffer strCipherText = new StringBuffer();
			
			if (strPlainText == null || "".equals(strPlainText) || strKey == null || "".equals(strKey)) {
				JOptionPane.showMessageDialog(null, "Please enter the plaintext and the key!");
				return;
			}

			int lPT = strPlainText.length();
			int lKey = strKey.length(); 
			char[] strPTArray = strPlainText.toCharArray();  //transform the String into the Array
			char[] strKeyArray = strKey.toCharArray();
			
			for(int i=0; i<lPT; i++){
				char p = strPTArray[i];
				char k = strKeyArray[i%lKey];   // %lKey:in case that length(Key) < length(PlainText)
				
				strCipherText.append(VigenreArray[(int)p - 65][(int)k - 65]);   //Look up Vigenre Square Array
			}
			getTfCT().setText(strCipherText.toString());
			
		}else if(e.getSource() == btnDC) {                      
			/*Decrypt Vigenre*/
			String strCipherText = getTfCT().getText();
			String strKey = getTfKey().getText();
			StringBuffer strPlainText = new StringBuffer();

			if (strCipherText == null || "".equals(strCipherText) || strKey == null || "".equals(strKey)) {
				JOptionPane.showMessageDialog(null, "Please enter the ciphertext and the key!");
				return;
			}
			
			int lCT = strCipherText.length();
			int lKey = strKey.length();
			char[] strCTArray = strCipherText.toCharArray();  //transform the String into the Array
			char[] strKeyArray = strKey.toCharArray();
			
			for(int i=0; i<lCT; i++){
				char c = strCTArray[i];
				char k = strKeyArray[i%lKey];   // %lKey:in case that length(Key) < length(CipherText)
	
				for(int j=0; j<26; j++) {
					if(VigenreArray[(int)k - 65][j] == c) {       //Look up Vigenre Square Array in reverse
						strPlainText.append((char)(j + 65));
						break;
					}
				}
			}
			getTfPT().setText(strPlainText.toString());
			
		}else if(e.getSource() == btnReset) {
			/*Clear All the TextField*/
			getTfPT().setText("");
			getTfKey().setText("");
			getTfCT().setText("");
		}
	}
	
	/**
	 * Create the frame.
	 */
	public VigenreCipher() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPT = new JLabel("PlainText");
		lblPT.setBounds(37, 89, 72, 18);
		contentPane.add(lblPT);
		
		tfPT = new JTextField();
		tfPT.setBounds(139, 86, 264, 24);
		contentPane.add(tfPT);
		tfPT.setColumns(10);
		
		JLabel lblKey = new JLabel("Key");
		lblKey.setBounds(37, 140, 72, 18);
		contentPane.add(lblKey);
		
		tfKey = new JTextField();
		tfKey.setBounds(139, 137, 264, 24);
		contentPane.add(tfKey);
		tfKey.setColumns(10);
		
		JLabel lblCT = new JLabel("CipherText");
		lblCT.setBounds(37, 200, 72, 18);
		contentPane.add(lblCT);
		
		tfCT = new JTextField();
		tfCT.setBounds(139, 197, 264, 24);
		contentPane.add(tfCT);
		tfCT.setColumns(10);
		
		btnEC.setBounds(83, 286, 113, 27);
		contentPane.add(btnEC);
		btnEC.addActionListener(this);
		
		btnDC.setBounds(236, 286, 113, 27);
		contentPane.add(btnDC);
		btnDC.addActionListener(this);
		
		btnReset.setBounds(154, 351, 113, 27);
		contentPane.add(btnReset);
		btnReset.addActionListener(this);
	}

	public JButton getBtnReset() {
		return btnReset;
	}

	public void setBtnReset(JButton btnReset) {
		this.btnReset = btnReset;
	}

	public JTextField getTfPT() {
		return tfPT;
	}

	public void setTfPT(JTextField tfPT) {
		this.tfPT = tfPT;
	}

	public JTextField getTfKey() {
		return tfKey;
	}

	public void setTfKey(JTextField tfKey) {
		this.tfKey = tfKey;
	}

	public JTextField getTfCT() {
		return tfCT;
	}

	public void setTfCT(JTextField tfCT) {
		this.tfCT = tfCT;
	}
}
