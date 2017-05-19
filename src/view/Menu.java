package view;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class Menu extends JPanel {
	private JButton btnStartGame = new JButton("Start Game");
	
	public Menu(GuiClient main) {
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		

		splitPane.setLeftComponent(btnStartGame);
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.getController().preGame(main.getRound());
				main.page("place bet");
			}
		});
		btnStartGame.setEnabled(false);
		
		JButton btnAddPlayer = new JButton("Add Player");
		splitPane.setRightComponent(btnAddPlayer);
		btnAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.page("add player");
			}
		});
		

	}

	public void canStart(boolean b) {
		btnStartGame.setEnabled(true);
		
	}
}
