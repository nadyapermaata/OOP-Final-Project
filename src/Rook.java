
public class Rook extends ChessPiece {

	public Rook(int values) {
		super(values);
	}
	
	public String Possible() {
        String list="", oldPiece;
        int i = super.getValues();
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            try {
                while(" ".equals(ChessBoard.chessBoard[r][c+temp*j]))
                {
                    oldPiece=ChessBoard.chessBoard[r][c+temp*j];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r][c+temp*j]="R";
                    if (AlphaBetaChess.kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    ChessBoard.chessBoard[r][c]="R";
                    ChessBoard.chessBoard[r][c+temp*j]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(ChessBoard.chessBoard[r][c+temp*j].charAt(0))) {
                    oldPiece=ChessBoard.chessBoard[r][c+temp*j];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r][c+temp*j]="R";
                    if (AlphaBetaChess.kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    ChessBoard.chessBoard[r][c]="R";
                    ChessBoard.chessBoard[r][c+temp*j]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(ChessBoard.chessBoard[r+temp*j][c]))
                {
                    oldPiece=ChessBoard.chessBoard[r+temp*j][c];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r+temp*j][c]="R";
                    if (AlphaBetaChess.kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    ChessBoard.chessBoard[r][c]="R";
                    ChessBoard.chessBoard[r+temp*j][c]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(ChessBoard.chessBoard[r+temp*j][c].charAt(0))) {
                    oldPiece=ChessBoard.chessBoard[r+temp*j][c];
                    ChessBoard.chessBoard[r][c]=" ";
                    ChessBoard.chessBoard[r+temp*j][c]="R";
                    if (AlphaBetaChess.kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    ChessBoard.chessBoard[r][c]="R";
                    ChessBoard.chessBoard[r+temp*j][c]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
        }
        return list;
	}
	
}
