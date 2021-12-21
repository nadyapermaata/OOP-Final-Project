import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	static int humanAsWhite = -1;
	static int turn = 0;
	static int mode;
	UserInterface ui = new UserInterface();
	String[] gameMode = {"Normal Chess", "DesCess"};
	Object[] option={"Computer","Me"};
	private JButton reset;
	private JPanel toolboxPanel, toolboxPadding;
	
	public MainFrame() {
		super("DesCess");
		
		Image image = new ImageIcon("Logo.png").getImage();
		Icon icon = new ImageIcon(image);
		
		JOptionPane.showOptionDialog(null, 
				null,
		        "DesCess", 
		        JOptionPane.OK_CANCEL_OPTION, 
		        JOptionPane.INFORMATION_MESSAGE, 
		        icon, 
		        new String[]{"Start"},
		        "Start");
		
		
		
		mode = JOptionPane.showOptionDialog(null, 
				"Choose Game Mode", 
				"Game Mode", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				null,
				gameMode, 
				gameMode[0]);
		
		
		AlphaBetaChess.init(mode);
	
		reset = new JButton("Reset");
		toolboxPanel = new JPanel();
		toolboxPanel.setLayout(new GridLayout(1, 1, 1, 1));
		toolboxPadding = new JPanel();
		toolboxPadding.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));

		toolboxPanel.add(reset);
		toolboxPadding.add(toolboxPanel);
		add(toolboxPadding, BorderLayout.NORTH);
		add(ui, BorderLayout.CENTER);

		reset.addActionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		humanAsWhite=JOptionPane.showOptionDialog(null, "Who plays first?", "Options", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
		if (humanAsWhite==0) {
            AlphaBetaChess.makeMove(AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, 1000000, -1000000, "", 0));
            AlphaBetaChess.flipBoard();
            turn++;
            repaint();
        }
		
		setSize(526, 576);
		setVisible(true);		
	}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == reset) {
				if(mode == 0) {
					for(int i = 0; i < AlphaBetaChess.normalChessBoard.length ; i++) {
						AlphaBetaChess.chessBoard[i] = AlphaBetaChess.normalChessBoard[i].clone();
					}
				}
				else if (mode == 1) {
					for(int i = 0; i < AlphaBetaChess.desChessBoard.length ; i++) {
						AlphaBetaChess.chessBoard[i] = AlphaBetaChess.desChessBoard[i].clone();
					}
				}
				repaint();
				
				if (humanAsWhite == 0) {
					AlphaBetaChess.makeMove(AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, 1000000, -1000000, "", 0));
		            AlphaBetaChess.flipBoard();
		            turn++;
				}
				repaint();
			}
		}
	}

