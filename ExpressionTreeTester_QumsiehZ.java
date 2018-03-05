// Zayd Qumsieh
// Trees-Asg5 Tree Projekt
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

/*
	Reflection:
		This assignment was way easier than I thought. I had most of the code,
        so it was just a matter of figuring out how to transform an infix
        expression into an expression tree. Overall, it was a fun challenge.
*/

public class ExpressionTreeTester_QumsiehZ {
    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
     	System.out.println("Welcome to Zayd's Expression Solver... With Trees Now!");
        System.out.println("This calculator handles 5 operators (+, -, *, /, ^)");
        System.out.println("Not only that, but it supports decimals, multidigits, and spaces!");
        System.out.println("Give me an expression to simplify:");
        ExpressionSolver es = new ExpressionSolver(scan.nextLine());
        System.out.println("\nSimplified: \n" + es.simplify());
    }
}

//A container for useful static methods that operate on TreeNode objects.
class TreeUtilities {
	//the random object used by this class
	private static java.util.Random random = new java.util.Random();

	//used to prompt for command line input
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	//precondition:  t is non-empty
	//postcondition: returns the value in the leftmost node of t.
	public static Object leftmost(TreeNode t) {
		while (t.getLeft() != null) {
        	t = t.getLeft();
      	}
      
        return t.getValue();
	}

	//precondition:  t is non-empty
	//postcondition: returns the value in the rightmost node of t.
	public static Object rightmost(TreeNode t) {
		if (t.getRight() == null) return t.getValue();
      return rightmost(t.getRight());
	}

	//postcondition: returns the maximum depth of t, where an empty tree
	//               has depth 0, a tree with one node has depth 1, etc
	public static int maxDepth(TreeNode t) {
    	if (t == null) return 0;
     	int left = maxDepth(t.getLeft()), right = maxDepth(t.getRight());
     	return left > right ? left + 1 : right + 1;
	}

	//postcondition: each node in t has been lit up on display
	//               in a pre-order traversal
	public static void preOrder(TreeNode t, TreeDisplay display) {
     	display.visit(t);
      	if (t.getLeft() != null) preOrder(t.getLeft(), display);
      	if (t.getRight() != null) preOrder(t.getRight(), display);
	}

	//postcondition: each node in t has been lit up on display
	//               in an in-order traversal
	public static void inOrder(TreeNode t, TreeDisplay display) {
      	if (t.getLeft() != null) preOrder(t.getLeft(), display);
      	display.visit(t);
      	if (t.getRight() != null) preOrder(t.getRight(), display);
	}

	//postcondition: each node in t has been lit up on display
	//               in a post-order traversal
	public static void postOrder(TreeNode t, TreeDisplay display) {
      	if (t.getLeft() != null) postOrder(t.getLeft(), display);
      	if (t.getRight() != null) postOrder(t.getRight(), display);
      	display.visit(t);
	}

	//useful method for building a randomly shaped
	//tree of a given maximum depth
	public static TreeNode createRandom(int depth) {
		if (random.nextInt((int)Math.pow(2, depth)) == 0)
			return null;
		return new TreeNode(new Integer(random.nextInt(10)),
			createRandom(depth - 1),
			createRandom(depth - 1));
	}

	//returns the number of nodes in t
	public static int countNodes(TreeNode t) {
		int counter = 1;
      	if (t.getLeft() != null) counter += countNodes(t.getLeft());
      	if (t.getRight() != null) counter += countNodes(t.getRight());
      	return counter;
	}

	//returns the number of leaves in t
	public static int countLeaves(TreeNode t) {
		int counter = (t.getLeft() == null && t.getRight() == null) ? 1 : 0;
      	if (t.getLeft() != null) counter += countNodes(t.getLeft());
     	 if (t.getRight() != null)counter += countNodes(t.getRight());
      	return counter;
	}

	//precondition:  all values in t are Integer objects
	//postcondition: returns the sum of all values in t
	public static int sum(TreeNode t) {
      	int cumSum = (int)(t.getValue());
		if (t.getLeft() != null) cumSum += sum(t.getLeft());
      	if (t.getRight() != null) cumSum += sum(t.getRight()); 
      	return cumSum;
	}

	//postcondition:  returns a new tree, which is a complete copy
	//                of t with all new TreeNode objects pointing
	//                to the same values as t (in the same order, shape, etc)
	public static TreeNode copy(TreeNode t) {
		return new TreeNode(t.getValue(), t.getLeft(), t.getRight());
	}

	//postcondition:  returns true if t1 and t2 have the same
	//                shape (but not necessarily the same values);
	//                otherwise, returns false
	public static boolean sameShape(TreeNode t1, TreeNode t2) {
		if ((t1 == null ^ t2 == null) || 
           sameShape(t1.getLeft(), t2.getLeft()) || 
           sameShape(t1.getRight(), t2.getRight())) return false;
      
      return t1 == t2;
	}
	
	public static double solveExpression(String str) {
     	ExpressionSolver es = new ExpressionSolver(str);
     	TreeNode exp = postFixToTree(es.getExpression());
    	return simplify(exp);
	}
	
	public static TreeNode postFixToTree(ArrayList<Symbol> expression) {
        Stack<TreeNode> treeStack = new Stack<TreeNode>();
        
        for (int i = 0; i < expression.size(); i++) {
            TreeNode temp = new TreeNode(expression.get(i));
            
            if (expression.get(i).getType() == "number") {
                temp.setLeft(treeStack.pop());
                temp.setRight(treeStack.pop());
            }
            
            treeStack.push(temp);
        }
        
        return treeStack.pop();
    }
    
    public static double simplify(TreeNode expression) {
    	if (expression.getLeft() == null && expression.getRight() == null) return (double)(expression.getValue());
        Symbol operator = new Number((double)(expression.getValue()));
 		return operator.action((double)(expression.getLeft().getValue()), (double)(expression.getRight().getValue()));
    }
}

class TreeNode {
  private Object value;
  private TreeNode left;
  private TreeNode right;
  
  public TreeNode(Object initValue)
    { value = initValue; left = null; right = null; }

  public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
    { value = initValue; left = initLeft; right = initRight; }

  public Object getValue() { return value; }
  public TreeNode getLeft() { return left; }
  public TreeNode getRight() { return right; }

  public void setValue(Object theNewValue) { value = theNewValue; }
  public void setLeft(TreeNode theNewLeft) { left = theNewLeft; }
  public void setRight(TreeNode theNewRight) { right = theNewRight; }
}

//a graphical component for displaying the contents of a binary tree.
//
//sample usage:
//
// TreeDisplay display = new TreeDisplay();
// display.displayTree(someTree);
// display.visit(someNode);
class TreeDisplay extends JComponent {
	//number of pixels between text and edge
	private static final int ARC_PAD = 2;

	//the tree being displayed
	private TreeNode root = null;

	//the node last visited
	private TreeNode visiting = null;

	//the set of all nodes visited so far
	private Set visited = new HashSet();

	//number of milliseconds to pause when visiting a node
	private int delay = 500;

	//creates a frame with a new TreeDisplay component.
	//(constructor returns the TreeDisplay component--not the frame).
    public TreeDisplay()
    {
		//create surrounding frame
		JFrame frame = new JFrame("Tree Display");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add the TreeDisplay component to the frame
		frame.getContentPane().add(this);

		//show frame
		frame.pack();
		frame.setVisible(true);

		java.util.Timer timer = new java.util.Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				TreeDisplay.this.repaint();
			}
		};
		timer.schedule(task, 0, 1000);
	}

	//tells the frame the default size of the tree
	public Dimension getPreferredSize()
	{
		return new Dimension(400, 300);
	}

	//called whenever the TreeDisplay must be drawn on the screen
    public void paint(Graphics g)
    {
		Graphics2D g2 = (Graphics2D)g;
		Dimension d = getSize();

		//draw white background
		g2.setPaint(Color.white);
		g2.fill(new Rectangle2D.Double(0, 0, d.width, d.height));

        int depth = TreeUtilities.maxDepth(root);

        if (depth == 0)
        	//no tree to draw
        	return;

		//hack to avoid division by zero, if only one level in tree
        if (depth == 1)
        	depth = 2;

		//compute the size of the text
       	FontMetrics font = g2.getFontMetrics();
        int leftPad = font.stringWidth(
			TreeUtilities.leftmost(root).toString()) / 2;
        int rightPad = font.stringWidth(
			TreeUtilities.rightmost(root).toString()) / 2;
        int textHeight = font.getHeight();

		//draw the actual tree
        drawTree(g2, root, leftPad + ARC_PAD,
        			d.width - rightPad - ARC_PAD,
        			textHeight / 2 + ARC_PAD,
        			(d.height - textHeight - 2 * ARC_PAD) / (depth - 1));
    }

	//draws the tree, starting from the given node, in the region with x values ranging
	//from minX to maxX, with y value beginning at y, and next level at y + yIncr.
    private void drawTree(Graphics2D g2, TreeNode t, int minX, int maxX, int y, int yIncr)
    {
		//skip if empty
		if (t == null)
			return;

		//compute useful coordinates
		int x = (minX + maxX) / 2;
		int nextY = y + yIncr;

		//draw black lines
		g2.setPaint(Color.black);
		if (t.getLeft() != null)
		{
			int nextX = (minX + x) / 2;
			g2.draw(new Line2D.Double(x, y, nextX, nextY));
		}
		if (t.getRight() != null)
		{
			int nextX = (x + maxX) / 2;
			g2.draw(new Line2D.Double(x, y, nextX, nextY));
		}

		//measure text
		FontMetrics font = g2.getFontMetrics();
		String text = t.getValue().toString();
		int textHeight = font.getHeight();
		int textWidth = font.stringWidth(text);

		//draw the box around the node
		Rectangle2D.Double box = new Rectangle2D.Double(
			x - textWidth / 2 - ARC_PAD, y - textHeight / 2 - ARC_PAD,
			textWidth + 2 * ARC_PAD, textHeight + 2 * ARC_PAD);//, ARC_PAD, ARC_PAD);
		Color c;
		//color depends on whether we haven't visited, are visiting, or have visited.
		if (t == visiting)
			c = Color.YELLOW;
		else if (visited.contains(t))
			c = Color.ORANGE;
		else
			c = new Color(187, 224, 227);
		g2.setPaint(c);
		g2.fill(box);
		//draw black border
		g2.setPaint(Color.black);
		g2.draw(box);

		//draw text
		g2.drawString(text, x - textWidth / 2, y + textHeight / 2);

		//draw children
		drawTree(g2, t.getLeft(), minX, x, nextY, yIncr);
		drawTree(g2, t.getRight(), x, maxX, nextY, yIncr);
	}

	//tells the component to switch to displaying the given tree
    public void displayTree(TreeNode root)
    {
		this.root = root;

		//signal that the display needs to be redrawn
		repaint();
	}

	//light up this particular node, indicating we're visiting it.
	public void visit(TreeNode t)
	{
		//if we've already visited it, we assume this is a new traversal,
		//and reset the set of visited nodes.
		if (visited.contains(t))
			visited = new HashSet();

		//update visiting and visited
		visiting = t;
		visited.add(t);

		//signal that the display needs to be redrawn
		repaint();

		//pause, so you can see the traversal
		try
		{
			Thread.sleep(delay);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	//change the length of time to pause when visiting a node
	public void setDelay(int delay)
	{
		this.delay = delay;
	}
}
