import java.lang.Math.*;
import java.util.Stack;

class ExpressionTree {
    private String value;
    private ExpressionTree leftChild, rightChild, parent;
    
    ExpressionTree() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  ExpressionTree l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created ExpressionTree               
    */
    ExpressionTree(String s, ExpressionTree l, ExpressionTree r, ExpressionTree p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { return value; }

    ExpressionTree getLeftChild() { return leftChild; }

    ExpressionTree getRightChild() { return rightChild; }

    ExpressionTree getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(String o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(ExpressionTree n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(ExpressionTree n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    ExpressionTree(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new ExpressionTree(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new ExpressionTree( s.substring( mid + 1, right)));
        }
    }
    }


    // Returns a copy of the subtree rooted at this node... 2014
    ExpressionTree deepCopy() {
        ExpressionTree n = new ExpressionTree();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    }
    
    // Evaluate a single operation given an operator and two numbers
    double evaluate(String operator, double numberLeft, double numberRight){
    	double result = 0;
    	if (operator.equals("add")) result = numberLeft + numberRight;
    	else if (operator.equals("minus")) result = numberLeft - numberRight;
    	else if (operator.equals("mult")) result = numberLeft * numberRight;
    	else if (operator.equals("cos")) result = Math.cos(numberLeft);
    	else if (operator.equals("sin")) result = Math.sin(numberLeft);
    	else if (operator.equals("exp")) result = Math.exp(numberLeft);
    	
    	return result;
    }

 
    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) {
    	ExpressionTree newTree = this.deepCopy();
    	String value = newTree.getValue();
    	
    	if (newTree.getLeftChild()==null) { //if the expression is just a number, there is no more children
    		if(value.equals("x")) return x;
    	else return Double.parseDouble(value);
    	}else{
    		if(newTree.getRightChild()!=null) { //if there is more children on the left and on the right
    			return evaluate(value,newTree.getLeftChild().evaluate(x),newTree.getRightChild().evaluate(x));
    			}
    		else { //if there is more children on the left but none on the right 
    			return evaluate(value,newTree.getLeftChild().evaluate(x),0);
    			}
    		}
    }

    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    ExpressionTree differentiate() {
	// WRITE YOUR CODE HERE
    	ExpressionTree newTree = this.deepCopy();
    	String value = newTree.getValue();
    	if (this.getLeftChild()==null) { //if the expression is just a number, there is no more children
    		if(value.equals("x")) return new ExpressionTree("1");
    		else return new ExpressionTree("0");
    	}
    	
    	if (value.equals("add") || value.equals("minus")){
    		// the following lines concatenate the sign, 
    		// the differentiate of the left child and the differentiate of the right child in a string,
    		// the string is then converted to a tree.
    		String tempString = value +"("+ newTree.getLeftChild().deepCopy().differentiate().toString() + "," +
    				newTree.getRightChild().deepCopy().differentiate().toString() +")";
    		return new ExpressionTree(tempString);
    	}
    	
    	if (value.equals("mult")){
    		// the following lines add (the derivative of the left child times the right child) 
    		// and (the left child times the derivative right child) in a string,
    		// the string is then converted to a tree.
    		String tempString = "add" +"("+ value + "("+ newTree.getLeftChild().deepCopy().differentiate().toString() + 
    				"," + newTree.getRightChild().toString() + ")"+ "," + value +"("+ newTree.getLeftChild().toString() + 
    				"," + newTree.getRightChild().deepCopy().differentiate().toString() + ")"+")";
    		return new ExpressionTree(tempString);	
    	}
    	
    	if (value.equals("sin")){
    		// the following lines multiply (the cosine of the left child) 
    		// and (the derivative left child) in a string,
    		// the string is then converted to a tree.
    		String tempString = "mult" + "(" + "cos" +"("+ newTree.getLeftChild().toString() + ")" + "," +
    	"," + newTree.getLeftChild().deepCopy().differentiate().toString() + ")";
    		return new ExpressionTree(tempString);
    	}
    	
    	if (value.equals("cos")){
    		// the following lines subtract 0 from (the sine of the left child times 
    		// the differentiate of the left child) in a string,
    		// the string is then converted to a tree.
    		String tempString = "minus" + "("+ "0"+ "," + "mult" + "(" + "sin" + 
    				"(" + newTree.getLeftChild().toString() + ")" + "," + 
    				newTree.getLeftChild().deepCopy().differentiate().toString() + ")" + ")";
    		return new ExpressionTree(tempString);
    	}
    	
    	if (value.equals("exp")){
    		String tempString = "mult" + "("+ "exp" + "(" + newTree.getLeftChild().deepCopy().toString() + ")" + "," + 
    				newTree.getLeftChild().deepCopy().differentiate().toString() + ")";
    		return new ExpressionTree(tempString);
    	}
    	
    	
    	return null;	
    	
	// AND CHANGE THIS RETURN STATEMENT                        
        
    }
        
    
    public static void main(String args[]) {
        ExpressionTree e = new ExpressionTree("mult(x,add(add(2,x),cos(minus(x,4))))");
        System.out.println(e);
        System.out.println("result = " + e.evaluate(1));
        System.out.println("differentiate e =" + e.differentiate());
   
 }
}
