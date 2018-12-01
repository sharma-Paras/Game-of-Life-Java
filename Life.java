
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



public class Life extends JPanel implements ActionListener, MouseListener, MouseMotionListener , ItemListener {
	
	MosaicPanel display;

    
    public static void main(String[] args) {
        JFrame f = new JFrame("Life");
        JPanel p = new Life();
        f.setContentPane(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocation(100,50);
        f.setVisible(true);
    }

    private final int GRID_SIZE =100;  
    private boolean[][] alive;   
	  
    private Timer timer;  
    private JButton  stopGoButton;  
    private JButton  nextButton;   
    private JButton  randomButton;  
    private JButton  clearButton;   
    private JButton  quitButton; 
    private JButton  pattern; 
    private JButton  pattern1;
    private JButton  pattern2;
	Choice c1;
                            	
    public Life() {
        alive = new boolean[GRID_SIZE][GRID_SIZE];
        setLayout(new BorderLayout(3,3));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
        int cellSize = 600/GRID_SIZE; 
      display = new MosaicPanel(GRID_SIZE,GRID_SIZE,cellSize,cellSize);
        if (cellSize < 5)
            display.setGroutingColor(null);
        display.setUse3D(false);
        add(display,BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        add(bottom,BorderLayout.SOUTH);
        clearButton = new JButton("Clear");
        stopGoButton = new JButton("Start");
        quitButton = new JButton("Quit");
        nextButton = new JButton("One Step");
        randomButton = new JButton("Random Fill");
		pattern= new JButton("Load puffer");
		pattern1 =new JButton("Load Acorn");
		pattern2 = new JButton("Load Glider Gun");
		c1 = new Choice();
		c1.add("DIY");  
		c1.add("Puffer");
		c1.add("Glider Gun");
		c1.add("Acorn");
		
        bottom.add(stopGoButton);
        bottom.add(nextButton);
        bottom.add(randomButton);
        bottom.add(clearButton);
        bottom.add(quitButton);
		bottom.add(c1);
		
		
        stopGoButton.addActionListener(this);
        clearButton.addActionListener(this);
        quitButton.addActionListener(this);
        randomButton.addActionListener(this);
        nextButton.addActionListener(this);
		pattern.addActionListener(this);
		pattern1.addActionListener(this);
		pattern2.addActionListener(this);
		c1.addItemListener(this);
        display.addMouseListener(this);
        display.addMouseMotionListener(this);
		
        timer = new Timer(50,this);
    }

    

    private void doFrame() {
        boolean[][] newboard = new boolean[GRID_SIZE][GRID_SIZE];
        for ( int r = 0; r < GRID_SIZE; r++ ) {
            int above, below; 
            int left, right;  
            above = r > 0 ? r-1 : GRID_SIZE-1;
            below = r < GRID_SIZE-1 ? r+1 : 0;
            for ( int c = 0; c < GRID_SIZE; c++ ) {
                left =  c > 0 ? c-1 : GRID_SIZE-1;
                right = c < GRID_SIZE-1 ? c+1 : 0;
                int n = 0; 
                if (alive[above][left])
                    n++;
                if (alive[above][c])
                    n++;
                if (alive[above][right])
                    n++;
                if (alive[r][left])
                    n++;
                if (alive[r][right])
                    n++;
                if (alive[below][left])
                    n++;
                if (alive[below][c])
                    n++;
                if (alive[below][right])
                    n++;
                if (n == 3 || (alive[r][c] && n == 2))
                    newboard[r][c] = true;
                else
                    newboard[r][c] = false;
            }
        }
        alive = newboard;
    }


    
    private void showBoard() {
        display.setAutopaint(false); 
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                if (alive[r][c])
                    display.setColor(r,c,Color.WHITE);
                else
                    display.setColor(r,c,null); 
            }
        }
        display.setAutopaint(true);  
    }


    
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); 
        if (src == quitButton) { 
            System.exit(0);
        }
        else if (src == clearButton) {  
            alive = new boolean[GRID_SIZE][GRID_SIZE];
            display.clear();
        }
        else if (src == nextButton) {  
            doFrame();
            showBoard();
        }
        
		
		else if (src == stopGoButton) {  
            if (timer.isRunning()) { 
                timer.stop(); 
                clearButton.setEnabled(true);  
                randomButton.setEnabled(true);
                nextButton.setEnabled(true);
                stopGoButton.setText("Start"); 
            }
            else {  
                timer.start();  
                clearButton.setEnabled(false);  
                randomButton.setEnabled(false);
                nextButton.setEnabled(false);
                stopGoButton.setText("Stop"); 
            }
        }
        else if (src == randomButton) { 
            for (int r = 0; r < GRID_SIZE; r++) {
                for (int c = 0; c < GRID_SIZE; c++)
                    alive[r][c] = (Math.random() < 0.25);  
            }
            showBoard();
        }
        else if (src == timer) { 
            doFrame();
            showBoard();
        }
    }

    public void itemStateChanged(ItemEvent ie)
	{
		
		if(c1.getSelectedItem() == "Puffer")
		{
			
			for (int r = 0; r < GRID_SIZE; r++) {
                for (int c = 0; c < GRID_SIZE; c++){
                    alive[r][c] = false;
				
             			
				

            }}
			
				alive[24][20]=true;		
		alive[26][20]=true;
		alive[32][20]=true;
		alive[34][20]=true;
		alive[25][21]=true;
		alive[28][21]=true;
		alive[30][21]=true;
		alive[33][21]=true;
		alive[23][22]=true;
		alive[25][22]=true;
		alive[27][22]=true;
		alive[29][22]=true;
		alive[31][22]=true;
		alive[33][22]=true;
		alive[35][22]=true;
		alive[22][23]=true;
		alive[24][23]=true;
		alive[25][23]=true;
		alive[27][23]=true;
		alive[31][23]=true;
		alive[31][23]=true;
		alive[33][23]=true;
		alive[34][23]=true;
		alive[36][23]=true;
		alive[18][24]=true;
		alive[19][24]=true;
		alive[24][24]=true;
		alive[28][24]=true;
		alive[30][24]=true;
		alive[34][24]=true;
		alive[39][24]=true;
		alive[40][24]=true;
		alive[22][25]=true;
		alive[23][25]=true;
		alive[28][25]=true;
		alive[30][25]=true;
		alive[35][25]=true;
		alive[36][25]=true;
		alive[19][26]=true;
		alive[22][26]=true;
		alive[26][26]=true;
		alive[28][26]=true;
		alive[30][26]=true;
		alive[32][26]=true;
		alive[36][26]=true;
		alive[39][26]=true;
		alive[19][27]=true;
		alive[20][27]=true;
		alive[24][27]=true;
		alive[28][27]=true;
		alive[30][27]=true;
		alive[34][27]=true;
		alive[38][27]=true;
		alive[39][27]=true;
		alive[20][28]=true;
		alive[22][28]=true;
		alive[24][28]=true;
		alive[26][28]=true;
		alive[27][28]=true;
		alive[28][28]=true;
		alive[30][28]=true;
		alive[31][28]=true;
		alive[32][28]=true;
		alive[34][28]=true;
		alive[36][28]=true;
		alive[38][28]=true;
		alive[20][29]=true;
		alive[21][29]=true;
		alive[37][29]=true;
		alive[38][29]=true;
		alive[20][30]=true;
		alive[21][30]=true;
		alive[23][30]=true;
		alive[35][30]=true;
		alive[37][30]=true;
		alive[38][30]=true;
		alive[23][31]=true;
		alive[35][31]=true;
		alive[22][32]=true;
		alive[23][32]=true;
		alive[25][32]=true;
		alive[33][32]=true;
		alive[35][32]=true;
		alive[36][32]=true;
		alive[25][33]=true;
		alive[33][33]=true;
		alive[13][34]=true;
		alive[14][34]=true;
	
		alive[24][34]=true;
		alive[25][34]=true;
		alive[27][34]=true;
		alive[29][34]=true;
		alive[31][34]=true;
		alive[33][34]=true;
		alive[34][34]=true;
		alive[44][34]=true;
		alive[45][34]=true;
		alive[14][35]=true;
		alive[15][35]=true;
		alive[16][35]=true;
		alive[18][35]=true;
		alive[22][35]=true;
		alive[27][35]=true;
		alive[29][35]=true;
		alive[31][35]=true;
		alive[36][35]=true;
		alive[40][35]=true;
		alive[42][35]=true;
		alive[43][35]=true;
		alive[44][35]=true;
		alive[14][36]=true;
		alive[15][36]=true;
		alive[19][36]=true;
		alive[21][36]=true;
		alive[26][36]=true;
		alive[32][36]=true;
		alive[37][36]=true;
		alive[39][36]=true;
		alive[43][36]=true;
		alive[44][36]=true;
		alive[14][37]=true;
		alive[15][37]=true;
		alive[17][37]=true;
		alive[19][37]=true;
		alive[21][37]=true;
		alive[23][37]=true;
		alive[28][37]=true;
		alive[30][37]=true;
		alive[35][37]=true;
		alive[37][37]=true;
		alive[39][37]=true;
		alive[41][37]=true;
		alive[43][37]=true;
		alive[44][37]=true;
		alive[17][38]=true;
		alive[19][38]=true;
		alive[22][38]=true;
		alive[24][38]=true;
		alive[28][38]=true;
		alive[30][38]=true;
		alive[34][38]=true;
		alive[36][38]=true;
		alive[39][38]=true;
		alive[41][38]=true;
		alive[16][39]=true;
		alive[17][39]=true;
		alive[19][39]=true;
		alive[22][39]=true;
		alive[23][39]=true;
		alive[24][39]=true;
		alive[27][39]=true;
		alive[31][39]=true;
		alive[34][39]=true;
		alive[35][39]=true;
		alive[36][39]=true;
		alive[39][39]=true;
		alive[41][39]=true;
		alive[42][39]=true;
		alive[18][40]=true;
		alive[19][40]=true;
		alive[24][40]=true;
		alive[25][40]=true;
		alive[26][40]=true;
		alive[32][40]=true;
		alive[33][40]=true;
		alive[34][40]=true;
		alive[39][40]=true;
		alive[40][40]=true;
		alive[19][41]=true;
		alive[20][41]=true;
		alive[21][41]=true;
		alive[37][41]=true;
		alive[38][41]=true;
		alive[39][41]=true;
		alive[20][42]=true;
		alive[38][42]=true;
				showBoard();
			
			
		}
		
		
		if(c1.getSelectedItem() == "Glider Gun")
		{
			
			for (int r = 0; r < GRID_SIZE; r++) {
                for (int c = 0; c < GRID_SIZE; c++){
                    alive[r][c] = false;
				
             			
				

            }}
		
		 alive[12][5]=true;
		alive[12][6]=true;
		alive[13][5]=true;
		alive[13][6]=true;
		alive[12][15]=true;
		alive[13][15]=true;
		alive[14][15]=true;
		alive[11][16]=true;
		alive[15][16]=true;
		alive[10][17]=true;
		alive[10][18]=true;
		alive[16][17]=true;
		alive[16][18]=true;
		alive[13][19]=true;
		alive[11][20]=true;
		alive[15][20]=true;
		alive[12][21]=true;
		alive[13][21]=true;
		alive[14][21]=true;
		alive[13][22]=true;
		alive[10][25]=true;
		alive[11][25]=true;
		alive[12][25]=true;
		alive[10][26]=true;
		alive[11][26]=true;
		alive[12][26]=true;
		alive[9][27]=true;
		alive[13][27]=true;
		alive[8][29]=true;
		alive[9][29]=true;
		alive[13][29]=true;
		alive[14][29]=true;
		alive[10][39]=true;
		alive[11][39]=true;
		alive[10][40]=true;
		alive[11][40]=true;
               alive[12][50]=true;
		alive[12][51]=true;
		alive[13][50]=true;
		alive[13][51]=true;
		alive[12][60]=true;
		alive[13][60]=true;
		alive[14][60]=true;
		alive[11][61]=true;
		alive[15][61]=true;
		alive[10][62]=true;
		alive[10][62]=true;
		alive[16][62]=true;
		alive[16][63]=true;
		alive[13][64]=true;
		alive[11][65]=true;
		alive[15][65]=true;
		alive[12][66]=true;
		alive[13][66]=true;
		alive[14][66]=true;
		alive[13][67]=true;
		alive[10][70]=true;
		alive[11][70]=true;
		alive[12][70]=true;
		alive[10][71]=true;
		alive[11][71]=true;
		alive[12][71]=true;
		alive[9][72]=true;
		alive[13][72]=true;
		alive[8][74]=true;
		alive[9][74]=true;
		alive[13][74]=true;
		alive[14][74]=true;
		alive[10][84]=true;
		alive[11][84]=true;
		alive[10][85]=true;
		alive[11][85]=true;
		alive[10][63]=true;
	
				showBoard();
			
			
		}
		
		
			
		if(c1.getSelectedItem() == "Acorn")
		{
			
			for (int r = 0; r < GRID_SIZE; r++) {
                for (int c = 0; c < GRID_SIZE; c++){
                    alive[r][c] = false;
				
             			
				

            }}
			
		
			alive[70][70]=true;
			alive[70][71]=true;
			alive[68][71]=true;
			alive[69][73]=true;
			alive[70][74]=true;
			alive[70][75]=true;
			alive[70][76]=true;
			showBoard();
			
	}}
		
		
		
		
		
	
    
    public void mousePressed(MouseEvent e) {
        if (timer.isRunning())
            return;
        int row = display.yCoordToRowNumber(e.getY());
        int col = display.yCoordToRowNumber(e.getX());
        if (row >= 0 && row < display.getRowCount() && col >= 0 && col < display.getColumnCount()) {
            if (e.isMetaDown() || e.isControlDown()) {
                display.setColor(row,col,null);
                alive[row][col] = false;
            }
            else {
                display.setColor(row,col,Color.WHITE);
                alive[row][col] = true;
            }
        }
    }


   
    public void mouseDragged(MouseEvent e) {
        mousePressed(e); 
    }

	
	
	
    public void mouseClicked(MouseEvent e) { }  
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }


}
