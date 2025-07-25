import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.event.*;

/**
 * Write a description of class tetris here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class tetris extends gui
{
    // instance variables - replace the example below with your own
    int tetrisWidth = 10;
    int tetrisHeight = 20;
    private ImageIcon ImageArray[][] = new ImageIcon [tetrisWidth][tetrisHeight+1];
    ImageIcon PlacedBlockArray[][] = new ImageIcon  [tetrisWidth][tetrisHeight];
    ImageIcon fallingBlockArray[][] = new ImageIcon  [tetrisWidth][tetrisHeight+1];
    private int boardWidth = windowLength/3;
    private int boardX = (windowLength-boardWidth)/2;
    String FallingBlock = "empty";
    double FallingBlockX;
    double FallingBlockY;
    boolean BlockFalling = false;
    double baseSpeed = 0.01;
    double speed = baseSpeed;
    boolean gameover = false;
    int rotation = 1; //1-4
    String blockArray[] = {"l","L","RL","Z","RZ","T","Square"};
    String blockList[] = new String [5];
    /**
     * Constructor for objects of class tetris
     */
    public tetris()
    {  
        addKeyListener(new MyClass());
        setup();
        while(!gameover){
            super.repaint();
        }
    }

    void clearBoard(){
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                ImageArray[x][y] = BlackSquare;
                fallingBlockArray[x][y] = BlackSquare;
            }
        }
    }

    private void setup(){
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                ImageArray[x][y] = BlackSquare;
                PlacedBlockArray[x][y] = BlackSquare;
                fallingBlockArray[x][y] = BlackSquare;
            }
        }
        for (int num = 0; num < blockList.length; num ++){
            blockList[num] = getBlock();
        }
    }

    public void paint (Graphics g){
        clearBoard();
        ShowPlacedBlocks();
        controlBlocks();
        ShowPlacedBlocks();
        offScreenImage = new BufferedImage(windowLength+10 ,windowHeight+100,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) offScreenImage.getGraphics();
        //grey background
        g2.setColor(new Color(200,200,200));
        g2.fillRect(0,0,windowLength+10,windowHeight+100);
        //tetris background
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                ImageArray[x][y].paintIcon(this,g2,((windowLength/2)-(20*tetrisWidth))+x*imageWidth,y*imageHeight+imageHeight);
            }
        }
        g2.setColor(new Color(0,0,0));
        for(int num = 1; num < blockList.length; num ++){
            g2.drawString(blockList[num],100,100+(60*(num-1)));
        }
        g2.drawString("controls",70,500);
        g2.drawString("left = A or left arrow",45,520);
        g2.drawString("right = D or right arrow",35,540);
        g2.drawString("down faster = S or down arrow",15,560);
        g2.drawString("rotate left = Q",60,580);
        g2.drawString("rotate right = E",55,600);
        g.drawImage(offScreenImage,0,0,null);
    }

    void checkLines(){
        for(int y=0; y<tetrisHeight; y++){
            boolean isLine = true;
            for(int x=0; x<tetrisWidth; x++){
                if(PlacedBlockArray[x][y] == BlackSquare){
                    isLine = false;
                }
            }
            if(isLine){
                removeLine(y);
            }
        }
    }

    void removeLine(int y){
        for (int numy=y; numy > 0;numy--){
            for(int x=0; x<tetrisWidth; x++){
                if(PlacedBlockArray[x][numy-1] == BlackSquare ){
                    PlacedBlockArray[x][numy] = BlackSquare;
                }
                if(PlacedBlockArray[x][numy-1] == BlueSquare){
                    PlacedBlockArray[x][numy] = BlueSquare;
                }
            }
        }
        clearBoard();
        ShowPlacedBlocks();
    }

    void ShowPlacedBlocks(){
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                if(PlacedBlockArray[x][y] != BlackSquare){
                    ImageArray[x][y] = PlacedBlockArray[x][y];
                }
                if(fallingBlockArray[x][y] != BlackSquare){
                    ImageArray[x][y] = fallingBlockArray[x][y];
                }
            }
        }
    }

    void controlBlocks(){
        if(BlockFalling){
            moveBlocks();
        }else{
            checkLines();
            addBlocks();
        }
    }

    void addBlocks(){
        for (int num  = 0; num < blockList.length-1; num++){
            blockList[num] = blockList[num+1];
        }
        blockList[blockList.length-1] = getBlock();
        FallingBlockY = 0;
        FallingBlockX = 4;
        FallingBlock = blockList[0];
        rotation = 1;
        BlockFalling = true;
    }

    String getBlock(){
        String output = blockArray[(int)Math.floor(Math.random()*(blockArray.length))];
        return output;
    }

    void moveBlocks(){
        if(FallingBlock == "Square"){
            drawSquare((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawSquare((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "L"){
            drawL((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawL((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "RL"){
            drawRL((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawRL((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "Z"){
            drawZ((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawZ((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "RZ"){
            drawRZ((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawRZ((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "l"){
            drawl((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawl((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "T"){
            drawT((int)FallingBlockX,(int)FallingBlockY,false);
            if(collisionTest()){
                FallingBlockY += speed;
            }else{
                BlockFalling = false;
                drawT((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
    }

    boolean collisionTest(){
        boolean temp = true;
        for(int x = 0; x < tetrisWidth; x++){
            for(int y = 0; y < tetrisHeight; y++){
                if(fallingBlockArray[x][y] == BlueSquare){
                    if(y != 19){
                        if(y != 0){
                            if(PlacedBlockArray[x][y+1] == BlueSquare){
                                temp = false;
                            }
                        }
                    }else{
                        temp = false;
                    }
                }
            }
        }
        return temp;
    }

    //draw l block
    void drawl(int x, int y, boolean place){
        if(rotation == 1 || rotation ==  3){
            if(x<0){
                x++;
            }
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x,y+2);
                putinfallingblockArray(x,y+3);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x][y+2] = BlueSquare;
                PlacedBlockArray[x][y+3] = BlueSquare;
            }
        }
        if(rotation == 2 || rotation ==  4){
            if(!place){
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x,y);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x+2,y);
            }else{
                PlacedBlockArray[x-1][y] = BlueSquare;
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x+2][y] = BlueSquare;
            }
        }
    }

    //draw T block
    void drawT(int x, int y, boolean place){
        if(rotation == 1){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x-1][y] = BlueSquare;
            }
        }
        if(rotation == 2){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
            }
        }
        if(rotation == 3){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x,y-1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x-1][y] = BlueSquare;
            }
        }
        if(rotation == 4){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x-1,y);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x-1][y] = BlueSquare;
            }
        }
    }

    //draw reverse Z block
    void drawRZ(int x, int y, boolean place){
        if(rotation == 1){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x-1][y+1] = BlueSquare;
            }
        }
        if(rotation == 2){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x+1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x+1][y+1] = BlueSquare;
            }
        }
        if(rotation == 3){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x+1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x-1][y] = BlueSquare;
                PlacedBlockArray[x+1][y+1] = BlueSquare;
            }
        }
        if(rotation == 4){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x-1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x-1][y] = BlueSquare;
                PlacedBlockArray[x-1][y+1] = BlueSquare;
            }
        }
    }

    //draw Z block
    void drawZ(int x, int y, boolean place){
        if(rotation == 1){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x+1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x+1][y+1] = BlueSquare;
                PlacedBlockArray[x-1][y] = BlueSquare;
            }
        }
        if(rotation == 2){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x-1,y+1);
            }else{
                putinfallingblockArray(x,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x-1,y+1);
            }
        }
        if(rotation == 3){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x-1][y+1] = BlueSquare;
            }
        }
        if(rotation == 4){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y-1);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x+1,y+1);
            }else{
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x+1][y+1] = BlueSquare;
            }
        }
    }

    //draw reverse L block 
    void drawRL(int x, int y, boolean place){
        if(rotation == 1){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x,y+2);
                fallingBlockArray[x-1][y+2] = BlueSquare;
            }else{
                try{
                    PlacedBlockArray[x][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x][y+1] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x][y+2] = BlueSquare;
                    PlacedBlockArray[x-1][y+2] = BlueSquare;
                } catch(Exception e){}
            }
        }
        if(rotation == 2){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x+1,y+1);
            }else{
                try{
                    PlacedBlockArray[x][y] = BlueSquare;
                    PlacedBlockArray[x+1][y] = BlueSquare;
                    PlacedBlockArray[x-1][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x+1][y+1] = BlueSquare;
                } catch(Exception e){}
            }
        }
        if(rotation == 3){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x,y+2);
                putinfallingblockArray(x+1,y);
            }else{
                try{
                    PlacedBlockArray[x+1][y] = BlueSquare;
                    PlacedBlockArray[x][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x][y+1] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x][y+2] = BlueSquare;
                }catch(Exception e){}
            }
        }
        if(rotation == 4){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y);
                fallingBlockArray[x-1][y-1] = BlueSquare;
            }else{
                try{
                    PlacedBlockArray[x-1][y] = BlueSquare;
                    PlacedBlockArray[x][y] = BlueSquare;
                    PlacedBlockArray[x+1][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x-1][y-1] = BlueSquare;
                } catch(Exception e){}
            }
        }
    }

    //draw L block
    void drawL(int x, int y, boolean place){
        if(rotation == 1){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x,y+1);
                putinfallingblockArray(x,y+2);
                putinfallingblockArray(x+1,y+2);
            }else{
                try{
                    PlacedBlockArray[x][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x][y+1] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x][y+2] = BlueSquare;
                    PlacedBlockArray[x+1][y+2] = BlueSquare;
                } catch(Exception e){}
            }
        }
        if(rotation == 2){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x+1,y);
                fallingBlockArray[x+1][y-1] = BlueSquare;
            }else{
                try{
                    PlacedBlockArray[x][y] = BlueSquare;
                    PlacedBlockArray[x-1][y] = BlueSquare;
                    PlacedBlockArray[x+1][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x+1][y-1] = BlueSquare;
                } catch(Exception e){}
            }
        }
        if(rotation == 3){
            if(!place){
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x+1,y+1);
                putinfallingblockArray(x+1,y+2);
                putinfallingblockArray(x,y);
            }else{
                try{
                    PlacedBlockArray[x+1][y] = BlueSquare;
                    PlacedBlockArray[x][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x+1][y+1] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x+1][y+2] = BlueSquare;
                } catch(Exception e){}
            }
        }
        if(rotation == 4){
            if(!place){
                putinfallingblockArray(x,y);
                putinfallingblockArray(x-1,y);
                putinfallingblockArray(x+1,y);
                putinfallingblockArray(x-1,y+1);
            }else{
                try{
                    PlacedBlockArray[x][y] = BlueSquare;
                    PlacedBlockArray[x+1][y] = BlueSquare;
                    PlacedBlockArray[x-1][y] = BlueSquare;
                } catch(Exception e){}
                try{
                    PlacedBlockArray[x-1][y+1] = BlueSquare;
                } catch(Exception e){}
            }
        }
    }

    //square block functions
    void drawSquare(int x, int y, boolean place){
        if(!place){
            putinfallingblockArray(x,y);
            putinfallingblockArray(x+1,y);
            putinfallingblockArray(x+1,y+1);
            putinfallingblockArray(x,y+1);
        }else{
            if(y>0){
                PlacedBlockArray[x][y+1] = BlueSquare;
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
                PlacedBlockArray[x+1][y+1] = BlueSquare;
            }
        }
    }

    void putinfallingblockArray(int placeX, int placeY){
        if(placeY>-1){
            if(placeX>-1){
                if(placeX<tetrisWidth){
                    fallingBlockArray[placeX][placeY] = BlueSquare;
                }
            }
        }
    }

    boolean blockCanRotate(String direction){
        boolean canrotate = true;
        if (direction == "L"){
            for(int y = 0; y < tetrisHeight; y++){
                for(int x = 0; x < tetrisWidth; x++){
                    if(fallingBlockArray[0][y] == BlueSquare){
                        if(y>0){
                            if(fallingBlockArray[x][y] == BlueSquare && fallingBlockArray[x][y+1] == BlueSquare){
                                canrotate = false;
                            }
                        }
                    }
                }
            }
        }else if (direction == "R"){
            for(int y = 0; y < tetrisHeight; y++){
                for(int x = 0; x < tetrisWidth; x++){
                    if(fallingBlockArray[tetrisWidth-1][y] == BlueSquare){
                        if(y<tetrisHeight-1){
                            if(fallingBlockArray[x][y] == BlueSquare && fallingBlockArray[x][y-1] == BlueSquare){
                                canrotate = false;
                            }
                        }
                    }
                }
            }
        }
        return canrotate;

    }

    // key listener for controls
    public class MyClass  implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            // Invoked when a key has been pressed.
            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(BlockFalling){
                    boolean canMoveLeft = true;
                    for(int y = 0; y < tetrisHeight; y++){
                        for(int x = 0; x < tetrisWidth; x++){
                            if(fallingBlockArray[x][y] == BlueSquare){
                                if(x != 0){
                                    if(PlacedBlockArray[x-1][y] == BlueSquare){
                                        canMoveLeft = false;
                                    }
                                }else{
                                    canMoveLeft = false;
                                }
                            }
                        }
                    }
                    if(canMoveLeft){
                        FallingBlockX -= 1;
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(BlockFalling){
                    boolean canMoveRight = true;
                    for(int y = 0; y < tetrisHeight; y++){
                        for(int x = 0; x < tetrisWidth; x++){
                            if(fallingBlockArray[x][y] == BlueSquare){
                                if(x != tetrisWidth-1){
                                    if(PlacedBlockArray[x+1][y] == BlueSquare){
                                        canMoveRight = false;
                                    }
                                }else{
                                    canMoveRight = false;
                                }
                            }
                        }
                    }
                    if(canMoveRight){
                        FallingBlockX += 1;
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                speed = 0.6;
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                if(BlockFalling){
                    if(blockCanRotate("L")){
                        if(rotation < 4){
                            rotation += 1;
                        }else{
                            rotation = 1;
                        }
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_E) {
                if(BlockFalling){
                    if(blockCanRotate("R")){
                        if(rotation > 1){
                            rotation -= 1;
                        }else{
                            rotation = 4;
                        }
                    }
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                speed = baseSpeed;
            }
        }
    }
}