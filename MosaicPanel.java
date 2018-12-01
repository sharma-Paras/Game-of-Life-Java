
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;


public class MosaicPanel extends JPanel {


    private int rows;       
    private int columns;    
    private Color defaultColor;  
    private Color groutingColor;  
    private boolean alwaysDrawGrouting;  
    private boolean use3D = true;   
    private boolean autopaint = true; 
    private Color[][] grid; 
    private BufferedImage OSI;  
    private Graphics OSG;  
    private boolean needsRedraw;   
    public MosaicPanel() {
        this(42,42,16,16);
    }

   
    public MosaicPanel(int rows, int columns) {
        this(rows,columns,16,16);
    }

   
    public MosaicPanel(int rows, int columns, int preferredBlockWidth, int preferredBlockHeight) {
        this(rows, columns, preferredBlockWidth, preferredBlockHeight, null, 0);
    }


    public MosaicPanel(int rows, int columns, int preferredBlockWidth, int preferredBlockHeight, Color borderColor, int borderWidth) {
        this.rows = rows;
        this.columns = columns;
        grid = new Color[rows][columns];
        defaultColor = Color.BLACK;
        groutingColor = Color.GRAY;
        alwaysDrawGrouting = false;
        setBackground(defaultColor);
        setOpaque(true);
        setDoubleBuffered(false);
        if (borderColor != null) {
            if (borderWidth < 1)
                borderWidth = 1;
            setBorder(BorderFactory.createLineBorder(borderColor,borderWidth));
        }
        else
            borderWidth = 0;
        if (preferredBlockWidth > 0 && preferredBlockHeight > 0)
            setPreferredSize(new Dimension(preferredBlockWidth*columns + 2*borderWidth, preferredBlockHeight*rows + 2*borderWidth));
    }


    public void setDefaultColor(Color c) {
        if (c == null)
            c = Color.BLACK;
        if (! c.equals(defaultColor)) {
            defaultColor = c;
            setBackground(c);
            forceRedraw();
        }
    }


    public Color getDefaultColor() {
        return defaultColor;
    }


    public void setGroutingColor(Color c) {
        if (c == null || ! c.equals(groutingColor)) {
            groutingColor = c;
            forceRedraw();
        }
    }


   
    public Color getGroutingColor(Color c) {
        return groutingColor;
    }


    public void setAlwaysDrawGrouting(boolean always) {
        if (alwaysDrawGrouting != always) {
            alwaysDrawGrouting = always;
            forceRedraw();
        }
    }
    
    
    
    public boolean getUse3D() {
        return use3D;
    }
    
    
    
    public void setUse3D(boolean use3D) {
        this.use3D = use3D;
    }


   
    public boolean getAlwaysDrawGrouting() {
        return alwaysDrawGrouting; 
    }


    public void setGridSize(int rows, 
            int columns, boolean preserveData) {
        if (rows > 0 && columns > 0) {
            Color[][] newGrid = new Color[rows][columns];
            if (preserveData) {
                int rowMax = Math.min(rows,this.rows);
                int colMax = Math.min(columns,this.columns);
                for (int r = 0; r < rowMax; r++)
                    for (int c = 0; c < colMax; c++)
                        newGrid[r][c] = grid[r][c];
            }
            grid = newGrid;
            this.rows = rows;
            this.columns = columns;
            forceRedraw();
        }
    }


    public int getRowCount() {
        return rows;
    }


    public int getColumnCount() {
        return columns;
    }   



    public int getBlue(int row, int col) {
        if (row >=0 && row < rows && col >= 0 && col < columns && grid[row][col] != null)
            return grid[row][col].getBlue();
        else
            return defaultColor.getBlue();
    }


    public void setColor(int row, int col, Color c) {
        if (row >=0 && row < rows && col >= 0 && col < columns) {
            grid[row][col] = c;
            if (OSI == null)
                repaint();
            else {
                drawSquare(row,col,true);
            }
        }
    }


    
/*

    public void setHSBColor(int row, int col, 
            double hue, double saturation, double brightness) {
        if (row >=0 && row < rows && col >= 0 && col < columns) {
            grid[row][col] = makeHSBColor(hue,saturation,brightness);
            if (OSI == null)
                repaint();
            else {
                drawSquare(row,col,true);
            }
        }
    }
*/


    public void fill(Color c) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                grid[i][j] = c;
        forceRedraw();      
    }


    public void fill(int red, int green, int blue) {
        red = (red < 0)? 0 : ( (red > 255)? 255 : red);
        green = (green < 0)? 0 : ( (green > 255)? 255 : green);
        blue = (blue < 0)? 0 : ( (blue > 255)? 255 : blue);
        fill(new Color(red,green,blue));
    }


  


    public void clear() {
        fill(null);
    }


    public boolean getAutopaint() {
        return autopaint;
    }


    public void setAutopaint(boolean autopaint) {
        if (this.autopaint == autopaint)
            return;
        this.autopaint = autopaint;
        if (autopaint) 
            forceRedraw();
    }

  
    final public void forceRedraw() {
        needsRedraw = true;
        repaint();
    }

   
    


   

    public int xCoordToColumnNumber(int x) {
        Insets insets = getInsets();
        if (x < insets.left)
            return -1;
        double colWidth = (double)(getWidth()-insets.left-insets.right) / columns;
        int col = (int)( (x-insets.left) / colWidth);
        if (col >= columns)
            return columns;
        else
            return col;
    }

    
    public int yCoordToRowNumber(int y) {
        Insets insets = getInsets();
        if (y < insets.top)
            return -1;
        double rowHeight = (double)(getHeight()-insets.top-insets.bottom) / rows;
        int row = (int)( (y-insets.top) / rowHeight);
        if (row >= rows)
            return rows;
        else
            return row;
    }

   
    public BufferedImage getImage() {
        return OSI;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized(this) {
            if ( (OSI == null) || OSI.getWidth() != getWidth() || OSI.getHeight() != getHeight() ) {
                OSI = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                OSG = OSI.getGraphics();
                needsRedraw = true;
            }
        }
        if (needsRedraw) {
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++)
                    drawSquare(r,c,false);
            needsRedraw = false;
        }
        g.drawImage(OSI,0,0,null);
    }

    synchronized private void drawSquare(int row, int col, boolean callRepaint) {
          
        if (callRepaint && !autopaint)
            return;
        Insets insets = getInsets();
        double rowHeight = (double)(getHeight()-insets.left-insets.right) / rows;
        double colWidth = (double)(getWidth()-insets.top-insets.bottom) / columns;
        int xOffset = insets.left;
        int yOffset = insets.top; 
        int y = yOffset + (int)Math.round(rowHeight*row);
        int h = Math.max(1, (int)Math.round(rowHeight*(row+1))+yOffset - y);
        int x = xOffset + (int)Math.round(colWidth*col);
        int w = Math.max(1, (int)Math.round(colWidth*(col+1))+xOffset - x);
        Color c = grid[row][col];
        OSG.setColor( (c == null)? defaultColor : c );
        if (groutingColor == null || (c == null && !alwaysDrawGrouting)) {
            if (!use3D || c == null)
                OSG.fillRect(x,y,w,h);
            else
                OSG.fill3DRect(x,y,w,h,true);
        }
        else {
            if (!use3D || c == null)
                OSG.fillRect(x+1,y+1,w-2,h-2);
            else
                OSG.fill3DRect(x+1,y+1,w-2,h-2,true);
            OSG.setColor(groutingColor);
            OSG.drawRect(x,y,w-1,h-1);
        }
        if (callRepaint)
            repaint(x,y,w,h);
    }


} 
