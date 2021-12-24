
public class King extends ChessPiece{

	public King(int values) {
		super(values);
	}
	
	public String Possible() {
        String list="", oldPiece;
        int i = super.getValues();
        int r=i/8, c=i%8;
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {
                    if (Character.isLowerCase(ChessBoard.chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(ChessBoard.chessBoard[r-1+j/3][c-1+j%3])) {
                        oldPiece=ChessBoard.chessBoard[r-1+j/3][c-1+j%3];
                        ChessBoard.chessBoard[r][c]=" ";
                        ChessBoard.chessBoard[r-1+j/3][c-1+j%3]="A";
                        int kingTemp=AlphaBetaChess.kingPositionC;
                        AlphaBetaChess.kingPositionC=i+(j/3)*8+j%3-9;
                        if (AlphaBetaChess.kingSafe()) {
                            list=list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
                        }
                        ChessBoard.chessBoard[r][c]="A";
                        ChessBoard.chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
                        AlphaBetaChess.kingPositionC=kingTemp;
                    }
                } catch (Exception e) {}
            }
        }
        return list;		
	}
	
}
