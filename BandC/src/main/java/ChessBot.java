import java.util.List;

public class ChessBot {

    private boolean colorIsWhite = false;
    ChessGrid startingGrid;
    public int[] chooseBestMove(char[][] startLayout, boolean botColorIsWhite){
        startingGrid = new ChessGrid(startLayout);
        colorIsWhite = botColorIsWhite;
        int bestscore = -1000; //this is a easy solution
        int[] outMove = new int[] {-1,-1,-1,-1};

      //  lookAtFutureBoards(startLayout, botColorIsWhite, 2);

        return lookAtFutureBoards(startLayout, botColorIsWhite, 4);
    }

    private int[] lookAtFutureBoards(char[][] startLayout, boolean turnColor, int turnCounts) {
        turnCounts--;

        int[] bestMoveAndScore = new int[] {-1,-1,-1,-1,-400};
        if(turnCounts % 2 == 1){
            bestMoveAndScore[4] = 400;
        }
        for(int y = 0; y< startLayout[0].length; y++ ){
            for(int x = 0; x< startLayout.length; x++ ){
                ChessPiece unit = startingGrid.getUnit(x,y);
                if(unit != null && unit.colorIsWhite == turnColor){
                    List<ChessSquare> unitPosMoves =  unit.getPossibleSquares();
                    for(ChessSquare nextMoveSquare: unitPosMoves){
                        ChessGrid tempGrid = new ChessGrid(startLayout);
                        tempGrid.moveUnitToNewSpace(unit.getxCord(), unit.getyCord(), nextMoveSquare.getCords()[0], nextMoveSquare.getCords()[1]);

                        int[] moveStats;
                        if(turnCounts != 0) {
                            if(turnCounts%2 == 1){
                                moveStats = lookAtFutureBoards(tempGrid.drawGrid(false, true), !turnColor, turnCounts);
                                if (moveStats[4] < bestMoveAndScore[4]) {
                                    bestMoveAndScore = new int[]{x, y, nextMoveSquare.getCords()[0], nextMoveSquare.getCords()[1], moveStats[4]};
                                }
                            }else {
                                moveStats = lookAtFutureBoards(tempGrid.drawGrid(false, true), !turnColor, turnCounts);
                                if (moveStats[4] < bestMoveAndScore[4]) {//change this?
                                    bestMoveAndScore = new int[]{x, y, nextMoveSquare.getCords()[0], nextMoveSquare.getCords()[1], moveStats[4]};
                                }
                            }
                        }else {
                            int score  =  countScore(tempGrid.drawGrid(false,true));
                            if(score > bestMoveAndScore[4]){
                                bestMoveAndScore = new int[] {x,y,nextMoveSquare.getCords()[0],nextMoveSquare.getCords()[1],score};
                            }
                        }



                    }
                }
            }
        }
        return bestMoveAndScore;
    }

    private int countScore(char[][] checkedBoard){//change this
        int score = 0;
        for(int x = 0; x < 8; x++ ){
            for (int y = 0; y < 8; y++){
                score += scoreKey(checkedBoard[x][y]);
            }
        }
        return score;
    }


    private int scoreKey(char key){
        if(key == 'P'){
            if(colorIsWhite) return 1;
            else return -1;
        }
        if(key == 'p'){
            if(colorIsWhite) return -1;
            else return 1;
        }

        if(key == 'R'){
            if(colorIsWhite) return 5;
            else return -5;
        }
        if(key == 'r'){
            if(colorIsWhite) return -5;
            else return 5;
        }

        if(key == 'N'){
            if(colorIsWhite) return 3;
            else return -3;
        }
        if(key == 'n'){
            if(colorIsWhite) return -3;
            else return 3;
        }
        if(key == 'B'){
            if(colorIsWhite) return 3;
            else return -3;
        }
        if(key == 'b'){
            if(colorIsWhite) return -3;
            else return 3;
        }
        if(key == 'K'){
            if(colorIsWhite) return 300;
            else return -300;
        }
        if(key == 'k'){
            if(colorIsWhite) return -300;
            else return 300;
        }
        if(key == 'Q'){
            if(colorIsWhite) return 10;
            else return -10;
        }
        if(key == 'q'){
            if(colorIsWhite) return -10;
            else return 10;
        }

        return 0;

    }


}
