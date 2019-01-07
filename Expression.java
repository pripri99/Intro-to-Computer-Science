import java.io.*;
import java.util.*;

public class Expression {
	static String delimiters="+-*%()";
	
	
	// Returns the value of the arithmetic Expression described by expr
	// Throws an exception if the Expression is malformed
	static Integer evaluate(String expr) throws Exception {
		String expression = "("+expr+")"; // put the expression in between parenthesis
		//The following code rule out the case were there is an uneven number of parenthesis
		int openParenthesis = 0;
		int closedParenthesis = 0;
		int nbrOperators = 0;
		char[] charArrayExpression = expression.toCharArray();
		for(int i=0; i<charArrayExpression.length; i++){
			if (Character.isDigit(expression.charAt(i))){
				if ((Character.toString(charArrayExpression[i+1]).equals("("))){
				throw new Exception();
			}
			}
			if (delimiters.contains(Character.toString(charArrayExpression[i]))){
				if (Character.toString(charArrayExpression[i]).equals("(")){
					openParenthesis = openParenthesis+1;
				}
				else if (Character.toString(charArrayExpression[i]).equals(")")){
					closedParenthesis  = closedParenthesis+1;
				}
				else{
					nbrOperators = nbrOperators+1;
				}
			}
		}
		if (openParenthesis != closedParenthesis){
			throw new Exception();
		}
		
		String addOpenPar = "(";
		String addClosedPar = ")";
		if(openParenthesis < nbrOperators){
			for (int j=0; j<openParenthesis; j++){
				addOpenPar = addOpenPar + "(";
				addClosedPar = addClosedPar+")";
			}
		}
		// the following code make it so there is the same number of operator and bracket couple
		expression = addOpenPar+ expression +addClosedPar; 
		StringTokenizer st = new StringTokenizer( expression , delimiters , true );
		Stack<Integer> numberStack = new Stack<Integer> ();
		Stack<String> operatorStack = new Stack<String> ();

	    while ( st.hasMoreTokens() ) {
		String element = st.nextToken();
		if (element.equals("(")){
			continue;
		}else if(element.equals("+")){
			operatorStack.push(element);
		}else if(element.equals("-")){
			operatorStack.push(element);
		}else if(element.equals("*")){
			operatorStack.push(element);
		}else if(element.equals("%")){
			operatorStack.push(element);
		}else if(element.equals(")") ){
			if(operatorStack.empty()) continue;
			String operator = operatorStack.pop();
			Integer result = numberStack.pop();
			if(operator.equals("+")){
				result = new Integer(Integer.valueOf(numberStack.pop())+ result);
			}else if(operator.equals("-")){
				result = new Integer(Integer.valueOf(numberStack.pop())- result);
			}else if(operator.equals("*")){
				result = new Integer(Integer.valueOf(numberStack.pop()) * result);
			}else if(operator.equals("%")){
				result = new Integer(Integer.valueOf(numberStack.pop())/result);
			}
			numberStack.push(result);
		}
		else{
			numberStack.push(Integer.valueOf(element));
		}
	    }
        Integer number = numberStack.pop();
	    // change this
	    return number;
	}	
		
	public static void main(String args[]) throws Exception {
		String line;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                                      	                        
		do {
			line=stdin.readLine();
			if (line.length()>0) {
				try {
					Integer x=evaluate(line);
					System.out.println(" = " + x);
				}
				catch (Exception e) {
					System.out.println("Malformed Expression: "+e);
				}
			}
		} while (line.length()>0);
	}
}