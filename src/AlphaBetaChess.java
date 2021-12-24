
public class AlphaBetaChess {
    static int kingPositionC = 0, kingPositionL = 0;
    static int globalDepth = 4;
    
    public static void init(int a) {
    	if(a == 0) {
    		for(int i = 0; i < ChessBoard.normalChessBoard.length ; i++) {
				ChessBoard.chessBoard[i] = ChessBoard.normalChessBoard[i].clone();
			}
    	}
    	else if(a == 1) {
    		for(int i = 0; i < ChessBoard.desChessBoard.length ; i++) {
    			ChessBoard.chessBoard[i] = ChessBoard.desChessBoard[i].clone();
			}

    	}
    	while (!"A".equals(ChessBoard.chessBoard[kingPositionC/8][kingPositionC%8])) {kingPositionC++;}//get King's location
        while (!"a".equals(ChessBoard.chessBoard[kingPositionL/8][kingPositionL%8])) {kingPositionL++;}//get king's location

    }
    
    public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        String list=posibleMoves();
        if (depth==0 || list.length()==0) {return move+(Rating.rating(list.length(), depth)*(player*2-1));}
        list=sortMoves(list);
        player=1-player;//1 atau 0
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i,i+5));
            flipBoard();
            String returnString=alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            flipBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (depth==globalDepth) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) {return move+beta;} else {return move+alpha;}
            }
        }
        if (player==0) {return move+beta;} else {return move+alpha;}
    }
    public static void flipBoard() {
        String temp;
        for (int i=0;i<32;i++) {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(ChessBoard.chessBoard[r][c].charAt(0))) {
                temp=ChessBoard.chessBoard[r][c].toLowerCase();
            } else {
                temp=ChessBoard.chessBoard[r][c].toUpperCase();
            }
            if (Character.isUpperCase(ChessBoard.chessBoard[7-r][7-c].charAt(0))) {
            	ChessBoard.chessBoard[r][c]=ChessBoard.chessBoard[7-r][7-c].toLowerCase();
            } else {
            	ChessBoard.chessBoard[r][c]=ChessBoard.chessBoard[7-r][7-c].toUpperCase();
            }
            ChessBoard.chessBoard[7-r][7-c]=temp;
        }
        int kingTemp=kingPositionC;
        kingPositionC=63-kingPositionL;
        kingPositionL=63-kingTemp;
    }
    public static void makeMove(String move) {
        if (move.charAt(4)!='P') {
        	ChessBoard.chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=ChessBoard.chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
        	ChessBoard.chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
            if ("A".equals(ChessBoard.chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                kingPositionC=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
            }
        } else {
            //pawn promotion
        	ChessBoard.chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
        	ChessBoard.chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
        }
    }
    public static void undoMove(String move) {
        if (move.charAt(4)!='P') {
        	ChessBoard.chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=ChessBoard.chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
        	ChessBoard.chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
            if ("A".equals(ChessBoard.chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
                kingPositionC=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
            }
        } else {
            //pawn promotion
        	ChessBoard.chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
        	ChessBoard.chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
        }
    }
    public static String posibleMoves() {
        String list="";
        ChessPiece[] piece = new ChessPiece[6]; 
        for (int i=0; i<64; i++) {
            switch (ChessBoard.chessBoard[i/8][i%8]) {
                case "P": 
                	piece[0] = new Pawn(i);
                	list += piece[0].Possible();
                    break;
                case "R": 
                	piece[1] = new Rook(i);
                	list += piece[1].Possible();
                    break;
                case "K": 
                	piece[2] = new Knight(i);
                	list += piece[2].Possible();
                    break;
                case "B": 
                	piece[3] = new Bishop(i);
                	list += piece[3].Possible();
                    break;
                case "Q": 
                	piece[4] = new Queen(i);
                	list += piece[4].Possible();
                    break;
                case "A": 
                	piece[5] = new King(i);
                	list += piece[5].Possible(); 
                	break;
            }
        }
        return list;//x1,y1,x2,y2,captured piece
    }

    public static String sortMoves(String list) {
        int[] score=new int [list.length()/5];
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i, i+5));
            score[i/5]=-Rating.rating(-1, 0);
            undoMove(list.substring(i, i+5));
        }
        String newListA="", newListB=list;
        for (int i=0;i<Math.min(6, list.length()/5);i++) {//first few moves only
            int max=-1000000, maxLocation=0;
            for (int j=0;j<list.length()/5;j++) {
                if (score[j]>max) {max=score[j]; maxLocation=j;}
            }
            score[maxLocation]=-1000000;
            newListA+=list.substring(maxLocation*5,maxLocation*5+5);
            newListB=newListB.replace(list.substring(maxLocation*5,maxLocation*5+5), "");
        }
        return newListA+newListB;
    }
    public static boolean kingSafe() {
        //bishop/queen
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(ChessBoard.chessBoard[kingPositionC/8+temp*i][kingPositionC%8+temp*j])) {temp++;}
                    if ("b".equals(ChessBoard.chessBoard[kingPositionC/8+temp*i][kingPositionC%8+temp*j]) ||
                            "q".equals(ChessBoard.chessBoard[kingPositionC/8+temp*i][kingPositionC%8+temp*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        //rook/queen
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(ChessBoard.chessBoard[kingPositionC/8][kingPositionC%8+temp*i])) {temp++;}
                if ("r".equals(ChessBoard.chessBoard[kingPositionC/8][kingPositionC%8+temp*i]) ||
                        "q".equals(ChessBoard.chessBoard[kingPositionC/8][kingPositionC%8+temp*i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(ChessBoard.chessBoard[kingPositionC/8+temp*i][kingPositionC%8])) {temp++;}
                if ("r".equals(ChessBoard.chessBoard[kingPositionC/8+temp*i][kingPositionC%8]) ||
                        "q".equals(ChessBoard.chessBoard[kingPositionC/8+temp*i][kingPositionC%8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
        }
        //knight
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("k".equals(ChessBoard.chessBoard[kingPositionC/8+i][kingPositionC%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("k".equals(ChessBoard.chessBoard[kingPositionC/8+i*2][kingPositionC%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        //pawn
        if (kingPositionC>=16) {
            try {
                if ("p".equals(ChessBoard.chessBoard[kingPositionC/8-1][kingPositionC%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(ChessBoard.chessBoard[kingPositionC/8-1][kingPositionC%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
            //king
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("a".equals(ChessBoard.chessBoard[kingPositionC/8+i][kingPositionC%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
}