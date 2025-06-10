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
    double baseSpeed = 0.03;
    double speed = baseSpeed;
    boolean gameover = false;
    int rotation = 1; //1-4
    /**
     * Constructor for objects of class tetris
     */
    public tetris()
    {  
        addKeyListener(new MyClass());
        setup();
        while(!gameover){
            repaint();
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
        g2.setColor(new Color(0,0,0));
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                ImageArray[x][y].paintIcon(this,g2,((windowLength/2)-(20*tetrisWidth))+x*imageWidth,y*imageHeight+imageHeight);
            }
        }
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
        for(int x=0; x<tetrisWidth; x++){
            if(PlacedBlockArray[x][0] == BlueSquare){

            }
        }
    }

    void removeLine(int y){
        for (int numy=y; numy > 0;numy--){
            for(int x=0; x<tetrisWidth; x++){
                if(PlacedBlockArray[x][numy-1].equals(BlackSquare)){
                    PlacedBlockArray[x][numy] = BlackSquare;
                }
                if(PlacedBlockArray[x][numy-1].equals(BlueSquare)){
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
        FallingBlockY = 0;
        FallingBlockX = 4;
        FallingBlock = "L";
        rotation = 1;
        BlockFalling = true;
    }

    void moveBlocks(){
        if(FallingBlock == "Square"){
            if(squareDownCollisionCheck((int)FallingBlockX,(int)FallingBlockY)){
                FallingBlockY += speed;
                drawSquare((int)FallingBlockX,(int)FallingBlockY,false);
            }else{
                BlockFalling = false;
                drawSquare((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
        if(FallingBlock == "L"){
            if(LDownCollisionCheck((int)FallingBlockX,(int)FallingBlockY)){
                FallingBlockY += speed;
                drawL((int)FallingBlockX,(int)FallingBlockY,false);
            }else{
                BlockFalling = false;
                drawL((int)FallingBlockX,(int)FallingBlockY,true);
                if(FallingBlockY <= 0){
                    gameover = true;
                }
            }
        }
    }

    //L block functions
    //draw L block
    void drawL(int x, int y, boolean place){
        if(rotation == 1){
            if(!place){
                fallingBlockArray[x][y] = BlueSquare;
                fallingBlockArray[x][y+1] = BlueSquare;
                fallingBlockArray[x][y+2] = BlueSquare;
                fallingBlockArray[x+1][y+2] = BlueSquare;
            }else{
                for(int temp = y-3; temp < y; temp ++){
                    try{
                        if(temp == y-3){
                            PlacedBlockArray[x][y-1] = BlueSquare;
                        }
                    } catch(Exception e){}
                    try{
                        if(temp == y-2){
                            PlacedBlockArray[x][y] = BlueSquare;
                        }
                    } catch(Exception e){}
                    if(temp < y-2){
                        PlacedBlockArray[x][y+1] = BlueSquare;
                        PlacedBlockArray[x+1][y+1] = BlueSquare;
                    }
                }
            }
        }
        if(rotation == 2){
            if(!place){
                fallingBlockArray[x][y] = BlueSquare;
                fallingBlockArray[x-1][y] = BlueSquare;
                fallingBlockArray[x+1][y] = BlueSquare;
                fallingBlockArray[x+1][y-1] = BlueSquare;
            }else{
                for(int temp = y-3; temp < y; temp ++){
                    try{
                        if(temp == y-3){
                            PlacedBlockArray[x][y] = BlueSquare;
                        }
                    } catch(Exception e){}
                    try{
                        if(temp == y-2){
                            PlacedBlockArray[x-1][y] = BlueSquare;
                        }
                    } catch(Exception e){}
                    if(temp < y-2){
                        PlacedBlockArray[x+1][y] = BlueSquare;
                        PlacedBlockArray[x+1][y-1] = BlueSquare;
                    }
                }
            }
        }
        if(rotation == 3){
            if(!place){
                fallingBlockArray[x+1][y] = BlueSquare;
                fallingBlockArray[x+1][y+1] = BlueSquare;
                fallingBlockArray[x+1][y+2] = BlueSquare;
                fallingBlockArray[x][y] = BlueSquare;
            }else{
                for(int temp = y-3; temp < y; temp ++){
                    try{
                        if(temp == y-3){
                            PlacedBlockArray[x+1][y-1] = BlueSquare;
                        }
                    } catch(Exception e){}
                    try{
                        if(temp == y-2){
                            PlacedBlockArray[x+1][y] = BlueSquare;
                        }
                    } catch(Exception e){}
                    if(temp < y-2){
                        PlacedBlockArray[x+1][y+1] = BlueSquare;
                        PlacedBlockArray[x][y-1] = BlueSquare;
                    }
                }
            }
        }
        if(rotation == 4){
            if(!place){
                fallingBlockArray[x][y] = BlueSquare;
                fallingBlockArray[x-1][y] = BlueSquare;
                fallingBlockArray[x+1][y] = BlueSquare;
                fallingBlockArray[x-1][y+1] = BlueSquare;
            }else{
                for(int temp = y-3; temp < y; temp ++){
                    try{
                        if(temp == y-3){
                            PlacedBlockArray[x][y-1] = BlueSquare;
                        }
                    } catch(Exception e){}
                    try{
                        if(temp == y-2){
                            PlacedBlockArray[x-1][y-1] = BlueSquare;
                        }
                    } catch(Exception e){}
                    if(temp < y-2){
                        PlacedBlockArray[x+1][y-1] = BlueSquare;
                        PlacedBlockArray[x-1][y] = BlueSquare;
                    }
                }
            }
        }
    }

    //L block collision
    boolean LDownCollisionCheck(int x, int y){
        try{
            if(rotation == 1){
                if(ImageArray[x][y+2] == BlackSquare && ImageArray[x+1][y+2] == BlackSquare && ImageArray[x][y+2] == BlackSquare){
                    return true;
                }else{
                    return false;
                }
            }else if(rotation == 2){
                if(ImageArray[x][y+1] == BlackSquare && ImageArray[x+1][y+1] == BlackSquare && ImageArray[x-1][y+1] == BlackSquare){
                    return true;
                }else{
                    return false;
                }
            }else if(rotation == 3){
                if(ImageArray[x][y] == BlackSquare && ImageArray[x+1][y+2] == BlackSquare){
                    return true;
                }else{
                    return false;
                }
            }else{
                if(ImageArray[x][y] == BlackSquare && ImageArray[x+1][y] == BlackSquare && ImageArray[x-1][y+1] == BlackSquare){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(Exception e){
            System.out.println("test 1");
            return false;
        }
    }

    //square block functions
    void drawSquare(int x, int y, boolean place){
        if(!place){
            fallingBlockArray[x][y] = BlueSquare;
            fallingBlockArray[x+1][y] = BlueSquare;
            fallingBlockArray[x+1][y+1] = BlueSquare;
            fallingBlockArray[x][y+1] = BlueSquare;
        }else{
            if(y>0){
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
            }
        }
    }

    boolean squareDownCollisionCheck(int x, int y){
        try{
            if(ImageArray[x][y+1] == BlackSquare && ImageArray[x+1][y+1] == BlackSquare){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
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
                speed = 0.5;
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                if(BlockFalling){
                    if(rotation < 4){
                        rotation += 1;
                    }else{
                        rotation = 1;
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_E) {
                if(BlockFalling){
                    if(rotation > 1){
                        rotation -= 1;
                    }else{
                        rotation = 4;
                    }
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                speed = 0.03;
            }
        }
    }
}