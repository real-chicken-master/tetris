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
            }
        }
    }

    private void setup(){
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                ImageArray[x][y] = BlackSquare;
                PlacedBlockArray[x][y] = BlackSquare;
            }
        }
    }

    public void paint (Graphics g){
        clearBoard();
        checkLines();
        ShowPlacedBlocks();
        controlBlocks();
        offScreenImage = new BufferedImage(windowLength+10 ,windowHeight+100,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) offScreenImage.getGraphics();
        //grey background
        g2.setColor(new Color(200,200,200));
        g2.fillRect(0,0,windowLength+10,windowHeight+100);
        //tetris background
        g2.setColor(new Color(0,0,0));
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                ImageArray[x][y].paintIcon(this,g2,100+x*imageWidth,y*imageHeight+imageHeight);
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
    }

    void removeLine(int y){
        for(int x=0; x<tetrisWidth; x++){
            PlacedBlockArray[x][y] = BlackSquare;
        }
        for(int x=0; x<tetrisWidth; x++){
            PlacedBlockArray[x][y] = PlacedBlockArray[x][y-1];
        }
        for(int x=0; x<tetrisWidth; x++){
            PlacedBlockArray[x][y-1] = BlackSquare;
        }
    }

    void ShowPlacedBlocks(){
        for(int x=0; x<tetrisWidth; x++){
            for(int y=0; y<tetrisHeight; y++){
                if(PlacedBlockArray[x][y] != BlackSquare){
                    ImageArray[x][y] = PlacedBlockArray[x][y];
                }
            }
        }
    }

    void controlBlocks(){
        if(BlockFalling){
            moveBlocks();
        }else{
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
            if(squareCollisionCheck((int)FallingBlockX,(int)FallingBlockY)){
                FallingBlockY += speed;
                drawSquare((int)FallingBlockX,(int)FallingBlockY,false);
            }else{
                drawSquare((int)FallingBlockX,(int)FallingBlockY,true);
                BlockFalling = false;
            }
        }
        if(FallingBlock == "L"){
            if(LCollisionCheck((int)FallingBlockX,(int)FallingBlockY)){
                FallingBlockY += speed;
                drawL((int)FallingBlockX,(int)FallingBlockY,false);
            }else{
                drawL((int)FallingBlockX,(int)FallingBlockY,true);
                BlockFalling = false;
            }
        }
    }

    //L block functions
    void drawL(int x, int y, boolean place){
        if(!place){
            ImageArray[x][y] = BlueSquare;
            ImageArray[x][y+1] = BlueSquare;
            ImageArray[x][y+2] = BlueSquare;
            ImageArray[x+1][y+2] = BlueSquare;
        }else{
            if(y>0){
                PlacedBlockArray[x][y-2] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
            }else{
                gameover = true;
            }
        }
    }

    boolean LCollisionCheck(int x, int y){
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

    //square block functions
    void drawSquare(int x, int y, boolean place){
        if(!place){
            ImageArray[x][y] = BlueSquare;
            ImageArray[x+1][y] = BlueSquare;
            ImageArray[x+1][y+1] = BlueSquare;
            ImageArray[x][y+1] = BlueSquare;
        }else{
            if(y>0){
                PlacedBlockArray[x][y] = BlueSquare;
                PlacedBlockArray[x][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y-1] = BlueSquare;
                PlacedBlockArray[x+1][y] = BlueSquare;
            }else{
                gameover = true;
            }
        }
    }

    boolean squareCollisionCheck(int x, int y){
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
            // Invoked when a key has been typed.
        }

        public void keyPressed(KeyEvent e) {
            // Invoked when a key has been pressed.
            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(FallingBlockX > 0){
                    if(PlacedBlockArray[(int)FallingBlockX-1][(int)FallingBlockY] == BlackSquare){
                        FallingBlockX -= 1;
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(FallingBlockX < tetrisWidth-2){
                    if(PlacedBlockArray[(int)FallingBlockX+1][(int)FallingBlockY] == BlackSquare){
                        FallingBlockX += 1;
                    }
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                speed = 0.5;
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                if(rotation < 4){
                    rotation += 1;
                }else{
                    rotation = 1;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_E) {
                if(rotation > 1){
                    rotation -= 1;
                }else{
                    rotation = 4;
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