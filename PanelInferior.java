import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class PanelInferior extends JPanel implements Observador {
	
	private Controlador control;
	private JButton btnRango;
	private JButton btnClear;
	private JTextField txtCartasEnMano_orig; 
	private JTextField txtCartasEnMano; 
	private JTextField txtCartasBoard; 

	private ArrayList<String> cards_onBoard;
	private ArrayList<String> cards_onHand;

	public PanelInferior(Controlador control) {
		this.control = control;
		cards_onHand  = new ArrayList<String>();
		cards_onBoard = new ArrayList<String>();

		txtCartasBoard		 = new JTextField(30);
		txtCartasEnMano		 = new JTextField(30);
		txtCartasEnMano_orig = new JTextField(30);

		btnRango = new JButton("Procesa");
		btnClear = new JButton("Clear");

		txtCartasBoard.setEditable(false);
		txtCartasEnMano.setEditable(false);

		btnRango.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Parsea las cartas del jfieldtext con comas y las mete a un array
				String [] items = txtCartasEnMano_orig.getText().split(",");
				ArrayList<String> items_array = new ArrayList<String>();
				for (String s : items) 
					items_array.add(s.trim());
				control.procesar(items_array);		
		}});
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.clear();
		}});

		add(txtCartasEnMano_orig);
		add(txtCartasEnMano);
		add(txtCartasBoard);
		add(btnRango);
		add(btnClear);

		control.addObservador(this);
	}

	public String getCardsOnBoard() {
		String cards = "";

		for (int i = 0; i < cards_onBoard.size(); i++) 
			cards = cards_onBoard.get(i);

		return cards;
	}

	private void mostrarTextOnBoard(ArrayList<String> cards, JTextField jtf) {
		String cards_text = "";
		for (int i = 0; i < cards.size(); i++) {
			if (i == 0)
				cards_text = cards.get(i);	
			else
				cards_text += ", " + cards.get(i);
		}
		jtf.setText(cards_text);
	}

    private char valorCarta(int num) {
        char letra;

        switch (num) {
            case 10:
                letra = 'T';
                break;
            case 11:
                letra = 'J';
                break;
            case 12:
                letra = 'Q';
                break;
            case 13:
                letra = 'K';
                break;
            case 14:
                letra = 'A';
                break;
			default:
				letra = Integer.toString(num).charAt(0);
        }

        return letra;
    }

	@Override public void onShowText(final String card) {
		cards_onHand.add(card);
		mostrarTextOnBoard(cards_onHand, txtCartasEnMano);
	}

	@Override
	public void onSelectCard(final String text) { 
		txtCartasEnMano_orig.setText(text);

	}

	@Override
	public void onSelectCardBoard(final String card) { 
		if (cards_onBoard.size() < 5) {
			cards_onBoard.add(card);
			mostrarTextOnBoard(cards_onBoard, txtCartasBoard);
		}
	}

	@Override public void onDeselectCardBoard(final String card) {
		cards_onBoard.remove(card);	
		mostrarTextOnBoard(cards_onBoard, txtCartasBoard);
	}

	@Override public void onDeselectCard(final String card) {
		cards_onHand.remove(card);	
		mostrarTextOnBoard(cards_onHand, txtCartasEnMano);
	}

	@Override public void onClearCards() {
		cards_onHand.clear();
		mostrarTextOnBoard(cards_onHand, txtCartasEnMano);
		mostrarTextOnBoard(cards_onHand, txtCartasEnMano_orig);
	}

	@Override public void onSliderChange(final int value) {}
	@Override public void onRangeProccess(final ArrayList<String> cards) {}
}

