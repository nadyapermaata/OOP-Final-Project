import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	static int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize=64;
    JLabel statusLabel;
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0;i<64;i+=2) {
            g.setColor(new Color(120,162,204));
            g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            g.setColor(new Color(191,212,219));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        
        Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("ChessPieces.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (ChessBoard.chessBoard[i/8][i%8]) {
                case "P": j=5; k=0;
                    break;
                case "p": j=5; k=1;
                    break;
                case "R": j=2; k=0;
                    break;
                case "r": j=2; k=1;
                    break;
                case "K": j=4; k=0;
                    break;
                case "k": j=4; k=1;
                    break;
                case "B": j=3; k=0;
                    break;
                case "b": j=3; k=1;
                    break;
                case "Q": j=1; k=0;
                    break;
                case "q": j=1; k=1;
                    break;
                case "A": j=0; k=0;
                    break;
                case "a": j=0; k=1;
                    break;
            }
            if (j!=-1 && k!=-1) {
                g.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
        }
    }
    
    public void paintComponent(Graphics t, int line, int column) {
    	t.setColor(new Color(52, 216, 235));
    	t.fillRect(column*squareSize, line*squareSize, squareSize, squareSize);
    }
    
    
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            //inside the board
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
            //inside the board
            newMouseX=e.getX();
            newMouseY=e.getY();
            if (e.getButton()==MouseEvent.BUTTON1) {
                String dragMove;
                if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(ChessBoard.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
                    //pawn promotion
                	String temp[] = {"Queen","Rook","Bishop","Knight"};
                	int promotion = JOptionPane.showOptionDialog(null,
                			"Choose pawn promotion", 
                			"Pawn Promotion", 
                			JOptionPane.YES_NO_CANCEL_OPTION, 
                			JOptionPane.QUESTION_MESSAGE, 
                			null, 
                			temp, 
                			temp[0]);
                	switch(promotion) {
                	case 0 : 
                		dragMove=""+mouseX/squareSize+newMouseX/squareSize+ChessBoard.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
                		break;
                	case 1 : 
                		dragMove=""+mouseX/squareSize+newMouseX/squareSize+ChessBoard.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"RP";
                		break;
                	case 2 : 
                		dragMove=""+mouseX/squareSize+newMouseX/squareSize+ChessBoard.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"BP";
                		break;
                	case 3 :
                		dragMove=""+mouseX/squareSize+newMouseX/squareSize+ChessBoard.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"KP";
                		break;
                	default : 
                		dragMove=""+mouseX/squareSize+newMouseX/squareSize+ChessBoard.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
                	}
                } else {
                    //regular move
                    dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+ChessBoard.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
                }
                String userPosibilities=AlphaBetaChess.posibleMoves();
//                int finish = 0;
                if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
                    //valid move
                    AlphaBetaChess.makeMove(dragMove);
                    AlphaBetaChess.flipBoard();
                    System.out.println("Computer Possible Moves : \n" + AlphaBetaChess.posibleMoves());
                    int flagtemp = 1;
                    if(AlphaBetaChess.posibleMoves().length() == 0) {
    		        	Frame frame = new JFrame("Game Finish");       
    		            ((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    		            JLabel textLabel = new JLabel("You WINNN",SwingConstants.CENTER);       
    		            textLabel.setPreferredSize(new Dimension(300, 100));       
    		            ((JFrame) frame).getContentPane().add(textLabel, BorderLayout.CENTER);       
    		            frame.setSize(300,100);
    		            frame.setLocationRelativeTo(null);
    		            frame.setVisible(true);
    		            flagtemp = 0;
    	            }
                    if(flagtemp == 1) {
                    	AlphaBetaChess.makeMove(AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, 1000000, -1000000, "", 0));
                        MainFrame.turn++;
                        AlphaBetaChess.flipBoard();
                        System.out.println("Player Possible Moves : \n" + AlphaBetaChess.posibleMoves());
                        repaint();
                        MainFrame.turn++;
                        if(AlphaBetaChess.posibleMoves().length() == 0) {
        		        	Frame frame = new JFrame("Game Finish");       
        		            ((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        		            JLabel textLabel = new JLabel("You Lose...",SwingConstants.CENTER);       
        		            textLabel.setPreferredSize(new Dimension(300, 100));       
        		            ((JFrame) frame).getContentPane().add(textLabel, BorderLayout.CENTER);       
        		            frame.setSize(300,100);
        		            frame.setLocationRelativeTo(null);
        		            frame.setVisible(true);
        	            }
                    }
                }
            }
        }
    }
  
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
    
}