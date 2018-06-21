package gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.lang.NumberFormatException;
import TDALista.PositionList;
import TDALista.SimplyLinkedList;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDAArbolBinario.InvalidOperationException;
import java.util.Vector;

/**
 * Class GUI
 * Interfaz grafica para trabajar con expresiones aritmeticas. 
 *
 */
public class GUI {
	private JFrame frame;
	private Logica logica;
	private JTable declaradas;
    private JTextArea expresion;
    private JTextField infija;
	private JTextField prefija;
	private JTextField postfija;
	private JTextField textExpresion;
	private JTextField textNuevaVariable;
	private JTextField textAltura;
	private JTextField textHojas;
	private JTextField textNodos;
	private JTextField textInternos;
	private JTextField textEvaluar;
	private JLabel labelExpresion;
	private JLabel labelNuevaVariable;
	private JButton buttonReemplazar;
	private JButton buttonAgregar;
	private JButton buttonCancelar;
	private JButton buttonActualizar;
	private PositionList<String> exp, names;
	private Vector<Vector<String>> tableData;
	private final String stateA = "Reemplazar términos";
	private final String stateB = "Siguiente expresion";
	private final String stateC = "Reemplazar expresiones";

	/**
	 * Crea un objeto GUI y visualiza la ventana del mismo.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Instancia e inicializa un objeto de tipo GUI
	 */
	public GUI() {
		initialize();
	}

	private void initialize() {
		logica = new Logica();
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(UIManager.getColor("Button.background"));
		ImageIcon imgIcon = new ImageIcon(GUI.class.getResource("img/icon.png"));
		Image img = imgIcon.getImage();
		frame.setIconImage(img);
		frame.setTitle("Expresiones aritméticas");
		frame.setBounds(100, 100, 885, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocation(0, 0);
		initAgregarVariables();
		initVariablesDeclaradas();
		initTiposDeNotacion();
		initActualizarExpresion();
		initFotoArbolExpresion();
		initEvaluarExpresion();
		initReemplazarSubexpresiones();
		initInfoDelArbolDeExpresion();	
	}
	
	private void initAgregarVariables() {
		JPanel AgregarVariables = new JPanel();
		AgregarVariables.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		AgregarVariables.setBounds(14, 8, 277, 143);
		AgregarVariables.setLayout(null);
		frame.getContentPane().add(AgregarVariables);
		
		JLabel labelVariable = new JLabel("Variable:");
		labelVariable.setFont(new Font("Dialog", Font.BOLD, 13));
		labelVariable.setBounds(45, 32, 70, 15);
		AgregarVariables.add(labelVariable);
		
		JLabel labelValor = new JLabel("Valor:");
		labelValor.setFont(new Font("Dialog", Font.BOLD, 13));
		labelValor.setBounds(45, 59, 70, 15);
		AgregarVariables.add(labelValor);
		
		JTextField textVariable = new JTextField();
		textVariable.setBounds(133, 32, 114, 19);
		AgregarVariables.add(textVariable);

		JTextField textValor = new JTextField();
		textValor.setBounds(133, 59, 114, 19);
		AgregarVariables.add(textValor);
		
		buttonAgregar = new JButton("Agregar");
		buttonAgregar.setBounds(90, 106, 106, 25);
		buttonAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textVariable.getText(), rawVal = textValor.getText();
				try {
					double val = Double.parseDouble(rawVal);
					logica.assign(name,val);
					int index = -1;
					for(int i = 0; index == -1 && i < tableData.size(); i++)
						if(tableData.get(i).get(0).equals(name)) index = i;
					// This way integer numbers get a zero appended:
					String strVal = String.valueOf(val);  
					if(index == -1) {
						Vector<String> row = new Vector<String>();
						row.add(name);
						row.add(strVal);
						tableData.add(row);
					} else tableData.get(index).set(1,strVal);
					declaradas.updateUI();
					expresion.setEditable(true);
					buttonActualizar.setEnabled(true);
				} catch(InvalidInputException f) {
					 JOptionPane.showMessageDialog(null,f.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				} catch(NumberFormatException f) {
					JOptionPane.showMessageDialog(null,"No ha ingresado un número válido.","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		AgregarVariables.add(buttonAgregar);
	}
	
	private void initVariablesDeclaradas() {
		JPanel VariablesDeclaradas = new JPanel();
		VariablesDeclaradas.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		VariablesDeclaradas.setBounds(14, 163, 277, 357);
		VariablesDeclaradas.setLayout(null);
		frame.getContentPane().add(VariablesDeclaradas);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Variable");
		columnNames.add("Valor");
	    tableData = new Vector<Vector<String>>();
		declaradas = new JTable(tableData, columnNames);
		declaradas.setEnabled(false);
		JScrollPane scrollDeclaradas =  new JScrollPane(declaradas);
		scrollDeclaradas.setBounds(25, 20, 230, 320);
		VariablesDeclaradas.add(scrollDeclaradas);
	}
	
	private void initTiposDeNotacion() {
		JPanel TiposDeNotacion = new JPanel();
		TiposDeNotacion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		TiposDeNotacion.setBounds(303, 252, 277, 268);
		TiposDeNotacion.setLayout(null);
		frame.getContentPane().add(TiposDeNotacion);
	
		JLabel labelNotacionPrefija = new JLabel("Notación prefija:");
		labelNotacionPrefija.setBounds(22, 12, 165, 17);
		labelNotacionPrefija.setFont(new Font("Dialog", Font.BOLD, 13));
		TiposDeNotacion.add(labelNotacionPrefija);
		
		prefija = new JTextField();
		prefija.setEditable(false);
		JScrollPane scrollPrefija = new JScrollPane(prefija);
		scrollPrefija.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPrefija.setBounds(22, 39, 230, 50);
		scrollPrefija.setVisible(true);
		TiposDeNotacion.add(scrollPrefija);	
		
		JLabel labelNotacionInfija = new JLabel("Notación infija:");
		labelNotacionInfija.setBounds(22, 100, 165, 17);
		labelNotacionInfija.setFont(new Font("Dialog", Font.BOLD, 13));
		TiposDeNotacion.add(labelNotacionInfija);
		
		infija = new JTextField();
		infija.setEditable(false);
		JScrollPane scrollInfija = new JScrollPane(infija);
		scrollInfija.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollInfija.setBounds(22, 123, 230, 50);	
		scrollInfija.setVisible(true);
		TiposDeNotacion.add(scrollInfija);	
		
		JLabel labelNotacionPostfija = new JLabel("Notación postfija:");
		labelNotacionPostfija.setBounds(22, 179, 165, 17);
		labelNotacionPostfija.setFont(new Font("Dialog", Font.BOLD, 13));
		TiposDeNotacion.add(labelNotacionPostfija);
		
		postfija = new JTextField();
		postfija.setEditable(false);
		JScrollPane scrollPostfija = new JScrollPane(postfija);
		scrollPostfija.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPostfija.setBounds(22, 202, 230, 50);	
		scrollPostfija.setVisible(true);
		TiposDeNotacion.add(scrollPostfija);	
	}
	
	private void initActualizarExpresion() {
		JPanel ActualizarExpresion = new JPanel();
		ActualizarExpresion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		ActualizarExpresion.setBounds(303, 8, 277, 143);
		ActualizarExpresion.setLayout(null);
		frame.getContentPane().add(ActualizarExpresion);
		
		JLabel labelExpresion = new JLabel("Expresion:");
		labelExpresion.setBounds(98, 15, 100, 15);
		labelExpresion.setFont(new Font("Dialog", Font.BOLD, 13));
		ActualizarExpresion.add(labelExpresion);
		
		expresion = new JTextArea();
		expresion.setLineWrap(true);
		expresion.setEditable(false);
		JScrollPane scrollExpresion = new JScrollPane(expresion);
		scrollExpresion.setBounds(29, 45, 225, 50);
		scrollExpresion.setVisible(true);
		scrollExpresion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollExpresion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ActualizarExpresion.add(scrollExpresion);
		
		buttonActualizar = new JButton("Actualizar");
		buttonActualizar.setEnabled(false);
		buttonActualizar.setBounds(81, 108, 117, 19);
		buttonActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logica.setExpr(expresion.getText());
					buttonReemplazar.setEnabled(true);
					updateInfo();
					evaluateExp();
					updateNotaciones();
				} catch(InvalidInputException f) {
					JOptionPane.showMessageDialog(null,f.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		ActualizarExpresion.add(buttonActualizar);
	}
	
	private void updateNotaciones() {
		prefija.setText(logica.getPrefix());
		infija.setText(logica.getInfix());
		postfija.setText(logica.getPostfix());
	}
	
	private void initFotoArbolExpresion() {
		JPanel FotoArbolExpresion = new JPanel();
		FotoArbolExpresion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FotoArbolExpresion.setBackground(Color.WHITE);
		FotoArbolExpresion.setBounds(592, 8, 277, 175);
		frame.getContentPane().add(FotoArbolExpresion);
		
		JLabel treeImg = new JLabel("");
		ImageIcon imgTree = new ImageIcon(GUI.class.getResource("img/tree.png"));
		treeImg.setIcon(imgTree);
		FotoArbolExpresion.add(treeImg);
	}
	
	private void initEvaluarExpresion() {
		JPanel EvaluarExpresion = new JPanel();
		EvaluarExpresion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		EvaluarExpresion.setBounds(303, 163, 277, 77);
		EvaluarExpresion.setLayout(null);
		frame.getContentPane().add(EvaluarExpresion);
		
		textEvaluar = new JTextField();
		textEvaluar.setEditable(false);
		JScrollPane scrollEvaluar = new JScrollPane(textEvaluar);
		scrollEvaluar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollEvaluar.setBounds(175, 20, 88, 40);
		scrollEvaluar.setVisible(true);
		EvaluarExpresion.add(scrollEvaluar);
		
		JLabel lblValorDeLa = new JLabel("Valor de la expresión:");
		lblValorDeLa.setBounds(13, 22, 161, 35);
		EvaluarExpresion.add(lblValorDeLa);
		
	}
	
	private void evaluateExp() {
		try {
			Double resRecu = logica.solveRecursive();
			Double resPila = logica.solveWithStack();
			Double eps = 1e-9;
			// The equality check handles Infinity,-Infinity & NaN values:
			if(Math.abs(resRecu-resPila) < eps || resRecu.equals(resPila)) textEvaluar.setText(resRecu.toString());
			else JOptionPane.showMessageDialog(null,"Los resultados no coinciden","Error", JOptionPane.ERROR_MESSAGE);
		} catch(InvalidOperationException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void finishReplacement() {
		try {
			while(!exp.isEmpty()) exp.remove(exp.first());
		} catch(EmptyListException | InvalidPositionException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
		textExpresion.setText("");
		textNuevaVariable.setText("");
		labelExpresion.setEnabled(false);
		labelNuevaVariable.setEnabled(false);
		textNuevaVariable.setEditable(false);
		buttonCancelar.setVisible(false);
		buttonAgregar.setEnabled(true);
		buttonActualizar.setEnabled(true);
		buttonReemplazar.setText(stateA);
		buttonReemplazar.setToolTipText(null);
	}
	
	private boolean isDuplicated(String name) {
		for(String s : names) if(s.equals(name)) return true;
		return false;
	}
	
	private void addVariable() throws InvalidInputException{
		String name = textNuevaVariable.getText();
		textNuevaVariable.setText("");
		if(!logica.isNameValid(name) || logica.getValue(name) != null || isDuplicated(name))
			throw new InvalidInputException("La variable \"" + name + "\" ya existe");
		else names.addLast(name);
	}
	
	private void updateReemplazarVariables() {
		try {
			textExpresion.setText(exp.first().element());
			exp.remove(exp.first());
		} catch(EmptyListException | InvalidPositionException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
		int size = exp.size();
		String msg, state = size > 0 ? stateB : stateC;
		if(size > 0) msg = exp.size() + (size == 1 ? " variable restante" : " variables restantes");
		else msg = null;
		buttonReemplazar.setText(state);
		buttonReemplazar.setToolTipText(msg);
	}
	
	private void addRow(String name){
		Vector<String> row = new Vector<String>();
		row.add(name);
		row.add(logica.getValue(name));
		tableData.add(row);
	}
	
	private void updateDeclaradas() {
		for(String s : names) addRow(s);
		declaradas.updateUI();
	}
	
	private void initReemplazarSubexpresiones() {
		JPanel ReemplazarExpresiones = new JPanel();
		ReemplazarExpresiones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		ReemplazarExpresiones.setBounds(592, 195, 277, 137);
		ReemplazarExpresiones.setLayout(null);
		frame.getContentPane().add(ReemplazarExpresiones);
		
		labelExpresion = new JLabel("Expresión:");
		labelExpresion.setEnabled(false);
		labelExpresion.setBounds(12, 58, 98, 15);
		ReemplazarExpresiones.add(labelExpresion);
		
		labelNuevaVariable = new JLabel("Nueva variable:");
		labelNuevaVariable.setEnabled(false);
		labelNuevaVariable.setBounds(12, 100, 124, 15);
		ReemplazarExpresiones.add(labelNuevaVariable);
		
		textExpresion = new JTextField("");
		textExpresion.setEditable(false);
		JScrollPane scrollExpresion = new JScrollPane(textExpresion);
		scrollExpresion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollExpresion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollExpresion.setBounds(128, 45, 140, 40);
		scrollExpresion.setVisible(true);
		ReemplazarExpresiones.add(scrollExpresion);
		
		textNuevaVariable = new JTextField("");
		textNuevaVariable.setEditable(false);
		JScrollPane scrollNuevaVariable = new JScrollPane(textNuevaVariable);
		scrollNuevaVariable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollNuevaVariable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollNuevaVariable.setBounds(128, 90, 140, 40);
		scrollNuevaVariable.setVisible(true);
		ReemplazarExpresiones.add(scrollNuevaVariable);
		
		buttonReemplazar = new JButton(stateA);
		buttonReemplazar.setToolTipText(null);
		buttonReemplazar.setEnabled(false);
		buttonReemplazar.setBounds(28, 10, 218, 25);
		buttonReemplazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String state = buttonReemplazar.getText(); 
				if(state == stateA) {
					exp = logica.nodesHeight1();
					String msg = "La raíz tiene altura uno, ningún cambio efectuado.";
					if(exp.isEmpty()) JOptionPane.showMessageDialog(null,msg,"Info", JOptionPane.INFORMATION_MESSAGE);
					else {
						names = new SimplyLinkedList<String>();
						labelExpresion.setEnabled(true);
						labelNuevaVariable.setEnabled(true);
						textNuevaVariable.setEditable(true);
						buttonCancelar.setVisible(true);
						buttonAgregar.setEnabled(false);
						buttonActualizar.setEnabled(false);
						updateReemplazarVariables();
					}
				} else if(state == stateB) {
					try {
						addVariable();
						updateReemplazarVariables();
					} catch(InvalidInputException f) {
						JOptionPane.showMessageDialog(null,f.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
					}	
				} else if(state == stateC){
					try {
						addVariable();
						PositionList<String> namesCopy = new SimplyLinkedList<String>(); 
						for(String s : names) namesCopy.addLast(s);
						logica.reemplazarTermino(namesCopy);
						updateInfo();
						evaluateExp();
						updateNotaciones();
						updateDeclaradas();
						String msg = "Las expresiones han sido reemplazadas.";
						JOptionPane.showMessageDialog(null,msg,"Info", JOptionPane.INFORMATION_MESSAGE);
						finishReplacement();
					} catch(InvalidInputException f) {
						JOptionPane.showMessageDialog(null,f.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
					} 
				}
			}
		});
		ReemplazarExpresiones.add(buttonReemplazar);
		
		buttonCancelar = new JButton("");
		buttonCancelar.setToolTipText("Cancelar: ninguna expresión será modificada.");
		buttonCancelar.setVisible(false);
		buttonCancelar.setBounds(249, 13, 18, 18);
		ImageIcon imgCancelar = new ImageIcon(GUI.class.getResource("img/cancel.png"));
		buttonCancelar.setIcon(imgCancelar);
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishReplacement();
			}
		});
		ReemplazarExpresiones.add(buttonCancelar);
	}
	
	private void updateInfo(){
		String[] info = logica.infoBinaryTree().split(" ");
		textAltura.setText(info[0]);
		textHojas.setText(info[1]);
		textNodos.setText(info[2]);
		textInternos.setText(info[3]);
	}
	
	private void initInfoDelArbolDeExpresion() {
		JPanel InfoDelArbolDeExpresion = new JPanel();
		InfoDelArbolDeExpresion.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		InfoDelArbolDeExpresion.setBounds(592, 344, 277, 175);
		InfoDelArbolDeExpresion.setLayout(null);
		frame.getContentPane().add(InfoDelArbolDeExpresion);
		
		JLabel labelInfo = new JLabel("Info del árbol de expresión:");
		labelInfo.setFont(new Font("Dialog", Font.BOLD, 13));
		labelInfo.setBounds(12, 25, 253, 13);
		InfoDelArbolDeExpresion.add(labelInfo);
		
		JLabel labelAltura = new JLabel("Altura:");
		labelAltura.setFont(new Font("Dialog", Font.BOLD, 13));
		labelAltura.setBounds(12, 66, 178, 13);
		InfoDelArbolDeExpresion.add(labelAltura);
		
		JLabel labelHojas = new JLabel("Nro de hojas:");
		labelHojas.setFont(new Font("Dialog", Font.BOLD, 13));
		labelHojas.setBounds(12, 90, 178, 13);
		InfoDelArbolDeExpresion.add(labelHojas);
		
		JLabel labelNodos = new JLabel("Nro de nodos:");
		labelNodos.setFont(new Font("Dialog", Font.BOLD, 13));
		labelNodos.setBounds(12, 113, 178, 13);
		InfoDelArbolDeExpresion.add(labelNodos);
		
		JLabel labelInternos = new JLabel("Nro de nodos internos:");
		labelInternos.setFont(new Font("Dialog", Font.BOLD, 13));
		labelInternos.setBounds(12, 136, 190, 13);
		InfoDelArbolDeExpresion.add(labelInternos);
		
		textAltura = new JTextField();
		textAltura.setHorizontalAlignment(SwingConstants.RIGHT);
		textAltura.setEditable(false);
		textAltura.setColumns(10);
		textAltura.setBounds(205, 66, 60, 15);
		InfoDelArbolDeExpresion.add(textAltura);
		
		textHojas = new JTextField();
		textHojas.setHorizontalAlignment(SwingConstants.RIGHT);
		textHojas.setEditable(false);
		textHojas.setColumns(10);
		textHojas.setBounds(205, 91, 60, 15);
		InfoDelArbolDeExpresion.add(textHojas);
		
		textNodos = new JTextField();
		textNodos.setHorizontalAlignment(SwingConstants.RIGHT);
		textNodos.setEditable(false);
		textNodos.setColumns(10);
		textNodos.setBounds(205, 113, 60, 15);
		InfoDelArbolDeExpresion.add(textNodos);
		
		textInternos = new JTextField();
		textInternos.setHorizontalAlignment(SwingConstants.RIGHT);
		textInternos.setEditable(false);
		textInternos.setBounds(205, 136, 60, 15);
		InfoDelArbolDeExpresion.add(textInternos);
		textInternos.setColumns(10);
	}
}
