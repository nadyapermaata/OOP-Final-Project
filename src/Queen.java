
public class Queen extends ChessPiece{
	public Queen(int values) {
		super(values);
	}
	
	public String Possible() {
        String list="", oldPiece;
        int i = super.getValues();
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {
                        while(" ".equals(ChessBoard.chessBoard[r+temp*j][c+temp*k]))
                        {
                            oldPiece=ChessBoard.chessBoard[r+temp*j][c+temp*k];
                            ChessBoard.chessBoard[r][c]=" ";
                            ChessBoard.chessBoard[r+temp*j][c+temp*k]="Q";
                            if (AlphaBetaChess.kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            ChessBoard.chessBoard[r][c]="Q";
                            ChessBoard.chessBoard[r+temp*j][c+temp*k]=oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(ChessBoard.chessBoard[r+temp*j][c+temp*k].charAt(0))) {
                            oldPiece=ChessBoard.chessBoard[r+temp*j][c+temp*k];
                            ChessBoard.chessBoard[r][c]=" ";
                            ChessBoard.chessBoard[r+temp*j][c+temp*k]="Q";
                            if (AlphaBetaChess.kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            ChessBoard.chessBoard[r][c]="Q";
                            ChessBoard.chessBoard[r+temp*j][c+temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return list;
	}
	
}
