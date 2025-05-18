import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

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
    private ImageIcon ImageArray[][] = new ImageIcon [tetrisWidth][tetrisHeight];
    ImageIcon PlacedBlockArray[][] = new ImageIcon  [tetrisWidth][tetrisHeight];
    private int boardWidth = windowLength/3;
    private int boardX = (windowLength-boardWidth)/2;
    String FallingBlock = "empty";
    double FallingBlockX;
    double FallingBlockY;
    boolean BlockFalling = false;
    /**
     * Constructor for objects of class tetris
     */
    public tetris()
    {   
        setup();
        while(true){
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
        controlBlocks();
        offScreenImage = new BufferedImage(windowLength+10 ,windowHeight+100,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) offScreenImage.getGraphics();
        //white background
        g2.setColor(new Color(255,255,255));
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
        FallingBlock = "Square";
        BlockFalling = true;
    }

    void moveBlocks(){
        if(FallingBlock == "Square"){
            if(squareCollisionCheck((int)FallingBlockX,(int)FallingBlockY)){
                FallingBlockY += 0.01;
                drawSquare((int)FallingBlockX,(int)FallingBlockY);
            }else{
                BlockFalling = false;
            }
        }
    }

    boolean squareCollisionCheck(int x, int y){
        try{
            if(PlacedBlockArray[x][y+1] == BlackSquare){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    void drawSquare(int x, int y){
        ImageArray[x][y] = BlueSquare;
        ImageArray[x+1][y] = BlueSquare;
        ImageArray[x+1][y+1] = BlueSquare;
        ImageArray[x][y+1] = BlueSquare;
    }
}