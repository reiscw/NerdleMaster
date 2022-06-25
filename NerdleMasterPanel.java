import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class NerdleMasterPanel extends JPanel {
   
    private JComboBox[] solutions;
    private JToggleButton[] candidateButtons;
    private JToggleButton[] nonCandidateButtons;
	private JLabel solutionsLabel;
	private JLabel includedLettersLabel;
	private JLabel excludedLettersLabel;
	private JLabel equationCount;
	private JLabel equationCountLabel;
	private JLabel note;
	private JLabel candidateLabel;
    private JButton updateButton;
    private JButton resetButton;
    private JButton quitButton;
    private JTextArea equationList;
    private JLabel[] excludedByPositionLabels;
	private JTextField[] excludedByPositionFields;

    public NerdleMasterPanel() {
	
		// construct dropdown boxes for solution

		solutions = new JComboBox[8];
		String[] choices = {"?", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "="};
		for (int i = 0; i < solutions.length; i++) {
			solutions[i] = new JComboBox(choices);
			add(solutions[i]);
		}

		// construct labels

        solutionsLabel = new JLabel ("Solution");
        includedLettersLabel = new JLabel ("Included Symbols");
        excludedLettersLabel = new JLabel ("Excluded Symbols");
		equationCountLabel = new JLabel ("Current Equation Count: ");
        equationCount = new JLabel ("67346");
        note = new JLabel("Note only the first 25 candidates are listed.");
        candidateLabel = new JLabel("Candidates:");
        
        add(solutionsLabel);
        add(includedLettersLabel);
        add(excludedLettersLabel);
        add(equationCountLabel);
        add(equationCount);
        add(note);
        add(candidateLabel);

        // construct candidateButtons
        
        String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/"};
        
        candidateButtons = new JToggleButton[14];
        for (int i = 0; i < 14; i++) {
			candidateButtons[i] = new JToggleButton(digits[i], false);
			add(candidateButtons[i]);
		}
        
        // construct nonCandidateButtons
        
        nonCandidateButtons = new JToggleButton[14];
        for (int i = 0; i < 14; i++) {
			nonCandidateButtons[i] = new JToggleButton(digits[i], false);
			add(nonCandidateButtons[i]);
		}
		
		// construct word list and update button
		updateButton = new JButton ("Update");
		resetButton = new JButton ("Reset");
		quitButton = new JButton ("Quit");
		equationList = new JTextArea (5, 5);
		add(updateButton);
		add(resetButton);
		add(quitButton);
        add(equationList);

		// construct excluded by position fields and labels
		excludedByPositionLabels = new JLabel[8];
		excludedByPositionLabels[0] = new JLabel("Exclude from 1st position: ");
		excludedByPositionLabels[1] = new JLabel("Exclude from 2nd position: ");
		excludedByPositionLabels[2] = new JLabel("Exclude from 3rd position: ");
		excludedByPositionLabels[3] = new JLabel("Exclude from 4th position: ");
		excludedByPositionLabels[4] = new JLabel("Exclude from 5th position: ");
		excludedByPositionLabels[5] = new JLabel("Exclude from 6th position: ");
		excludedByPositionLabels[6] = new JLabel("Exclude from 7th position: ");
		excludedByPositionLabels[7] = new JLabel("Exclude from 8th position: ");
		excludedByPositionFields = new JTextField[8];
		for (int i = 0; i < 8; i++) {
			excludedByPositionFields[i] = new JTextField();
			add(excludedByPositionFields[i]);
			add(excludedByPositionLabels[i]);
		}
		
        // adjust size and set layout
        setPreferredSize(new Dimension (950, 600));
        setLayout(null);

        // set component bounds 
	
		// solutions
        for (int i = 0, col = 35; i < 8; i++, col = col + 65) {
			solutions[i].setBounds(col, 40, 60, 25);
		}

		// row 1 of candidate/noncandidate buttons
		for (int i = 0, col = 35; i < 10; col = col + 55, i++) {
			candidateButtons[i].setBounds(col, 140, 50, 25);
			nonCandidateButtons[i].setBounds(col, 230, 50, 25);
		}
		
		// row 2 of candidate/noncandidate buttons
		for (int i = 10, col = 35; i < 14; col = col + 55, i++) {
			candidateButtons[i].setBounds(col, 170, 50, 25);
			nonCandidateButtons[i].setBounds(col, 260, 50, 25);
		}
		
		// labels and text fields
		solutionsLabel.setBounds(35, 10, 100, 25);
        includedLettersLabel.setBounds(35, 110, 150, 25);
        excludedLettersLabel.setBounds(35, 200, 150, 25);
        equationCountLabel.setBounds(635, 40, 200, 25);
        equationCount.setBounds(835, 40, 130, 25);
        note.setBounds(35, 550, 500, 25);
        updateButton.setBounds(35, 80, 100, 25);
        resetButton.setBounds(140, 80, 100, 25);
        quitButton.setBounds(245, 80, 100, 25);
        candidateLabel.setBounds(635, 80, 265, 25);
        equationList.setBounds(635, 110, 265, 460);
        
        for (int i = 0, row = 300; i < 8; i++, row = row + 30) {
			excludedByPositionLabels[i].setBounds(35, row, 220, 25);
			excludedByPositionFields[i].setBounds(240, row, 250, 25);
		}

		// action listeners for update, quit, and reset buttons
        
        updateButton.addActionListener(e -> {
			try {
				update();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
		quitButton.addActionListener(e -> {
			try {
				System.exit(0);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
		resetButton.addActionListener(e -> {
			try {
				reset(); update();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		});
		
    }
    
    public void update() throws FileNotFoundException {
		Nerdle puzzle = new Nerdle();
		// add solutions
		for (int i = 0; i < 8; i++) {
			char letter = ((String)(solutions[i].getSelectedItem())).toLowerCase().charAt(0);
			if (letter != '?') {
				puzzle.setSolution(i, letter);
			}
		}
		// add candidates and noncandidates
		for (int i = 0; i < 14; i++) {
			if (candidateButtons[i].isSelected()) {
				char letter = candidateButtons[i].getText().toLowerCase().charAt(0);
				puzzle.addCandidate(letter);
			}
			if (nonCandidateButtons[i].isSelected()) {
				char letter = nonCandidateButtons[i].getText().toLowerCase().charAt(0);
				puzzle.addNonCandidate(letter);
			}
		}
		
		// add specific exclusions
		for (int i = 0; i < 8; i++) {
			String temp = excludedByPositionFields[i].getText();
			if (!temp.equals("")) {
				puzzle.addSpecificExclusions(i, temp);
			}
		}
		
		// get possible words
		ArrayList<String> possibles = puzzle.getPossibleWords();
		
		// set text display
		String text = "";
		for (int i = 0; i < possibles.size() && i < 25; i++) {
			text = text + possibles.get(i) + "\n";
		}
		equationList.setText(text);
		
		// update word count
		equationCount.setText("" + possibles.size());
		
	}
	
	public void reset() {
		for (int i = 0; i < 8; i++) {
			solutions[i].setSelectedIndex(0);
			excludedByPositionFields[i].setText("");
		}
		for (int i = 0; i < 14; i++) {
			candidateButtons[i].setSelected(false);
			nonCandidateButtons[i].setSelected(false);
		}
	}
}
