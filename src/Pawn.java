
public class Pawn extends ChessPiece{
	
	public Pawn(int values) {
		super(values);
	}

	public String Possible() {
		String list="", oldPiece;
		int i = super.getValues();
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//capture
                if (Character.isLowerCase(ChessBoard.chessBoard[r-1][c+j].charAt(0)) && i>=16) {
                    oldPiece=ChessBoard.chessBoard[r-1][c+j];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r-1][c+j]="P";
                    if (AlphaBetaChess.kingSafe()) {
                        list=list+r+c+(r-1)+(c+j)+oldPiece;
                    }
                    ChessBoard.chessBoard[r][c]="P";
                    ChessBoard.chessBoard[r-1][c+j]=oldPiece;
                }
            } catch (Exception e) {}
            try {//promotion && capture
                if (Character.isLowerCase(ChessBoard.chessBoard[r-1][c+j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=ChessBoard.chessBoard[r-1][c+j];
                        ChessBoard.chessBoard[r][c]=" ";
                        ChessBoard.chessBoard[r-1][c+j]=temp[k];
                        if (AlphaBetaChess.kingSafe()) {
                            //column1,column2,captured-piece,new-piece,P
                            list=list+c+(c+j)+oldPiece+temp[k]+"P";
                        }
                        ChessBoard.chessBoard[r][c]="P";
                        ChessBoard.chessBoard[r-1][c+j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {//move one up
            if (" ".equals(ChessBoard.chessBoard[r-1][c]) && i>=16) {
                oldPiece=ChessBoard.chessBoard[r-1][c];
                ChessBoard.chessBoard[r][c]=" ";
                ChessBoard.chessBoard[r-1][c]="P";
                if (AlphaBetaChess.kingSafe()) {
                    list=list+r+c+(r-1)+c+oldPiece;
                }
                ChessBoard.chessBoard[r][c]="P";
                ChessBoard.chessBoard[r-1][c]=oldPiece;
            }
        } catch (Exception e) {}
        try {//promotion && no capture
            if (" ".equals(ChessBoard.chessBoard[r-1][c]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=ChessBoard.chessBoard[r-1][c];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r-1][c]=temp[k];
                    if (AlphaBetaChess.kingSafe()) {
                        //column1,column2,captured-piece,new-piece,P
                        list=list+c+c+oldPiece+temp[k]+"P";
                    }
                    ChessBoard.chessBoard[r][c]="P";
                    ChessBoard.chessBoard[r-1][c]=oldPiece;
                }
            }
        } catch (Exception e) {}
        if(MainFrame.mode == 0) {
        	try {//move two up
                if (" ".equals(ChessBoard.chessBoard[r-1][c]) && " ".equals(ChessBoard.chessBoard[r-2][c]) && i>=48) {
                    oldPiece=ChessBoard.chessBoard[r-2][c];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r-2][c]="P";
                    if (AlphaBetaChess.kingSafe()) {
                        list=list+r+c+(r-2)+c+oldPiece;
                    }
                    ChessBoard.chessBoard[r][c]="P";
                    ChessBoard.chessBoard[r-2][c]=oldPiece;
                }
            } catch (Exception e) {}
        }
        return list;
	}
	
}
