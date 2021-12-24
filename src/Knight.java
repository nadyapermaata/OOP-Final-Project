
public class Knight extends ChessPiece {

	public Knight(int values) {
		super(values);
	}
	
	public String Possible() {
		String list="", oldPiece;
		int i = super.getValues();
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(ChessBoard.chessBoard[r+j][c+k*2].charAt(0)) || " ".equals(ChessBoard.chessBoard[r+j][c+k*2])) {
                        oldPiece=ChessBoard.chessBoard[r+j][c+k*2];
                        ChessBoard.chessBoard[r][c]=" ";
                        if (AlphaBetaChess.kingSafe()) {
                            list=list+r+c+(r+j)+(c+k*2)+oldPiece;
                        }
                        ChessBoard.chessBoard[r][c]="K";
                        ChessBoard.chessBoard[r+j][c+k*2]=oldPiece;
                    }
                } catch (Exception e) {}
                try {
                    if (Character.isLowerCase(ChessBoard.chessBoard[r+j*2][c+k].charAt(0)) || " ".equals(ChessBoard.chessBoard[r+j*2][c+k])) {
                        oldPiece=ChessBoard.chessBoard[r+j*2][c+k];
                        ChessBoard.chessBoard[r][c]=" ";
                        if (AlphaBetaChess.kingSafe()) {
                            list=list+r+c+(r+j*2)+(c+k)+oldPiece;
                        }
                        ChessBoard.chessBoard[r][c]="K";
                        ChessBoard.chessBoard[r+j*2][c+k]=oldPiece;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
	}
	
}
