import java.util.*;

public class Proof {
	
	//list of theorems
	private TheoremSet myTheorems;

	//list of all the steps so far in order
	private LinkedList<Step> soFar;

	//the next linenumber that will be assigned to a step
	private LineNumber nextLine;

	//a string list of acceptable reasons
	private final ArrayList<String> acceptableReasons = new ArrayList<String>(Arrays.asList("print", "repeat", "show", "assume", "mp", "mt", "co", "ic"));

	//a stack of type Expression to keep track of what expression we need to prove
	private Stack<Expression> toProve;

	//an int variable to help determine whether or not the next line should stay, jump in, or jump out
	private int nextLineHelper;
	
	//a boolean variable to keep track if we started a proof
	private boolean started;

	public Proof (TheoremSet theorems) {
		// Initialization
		myTheorems = theorems;
		soFar = new LinkedList<Step>();
		nextLine = new LineNumber(1);
		toProve = new Stack<Expression>();
		nextLineHelper = 0;
	}
	
	public LineNumber nextLineNumber() {
		return nextLine;
	}
	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {

		//make step with the information from string x
		Step s = makeStep(x);

		//print step case
			//do not assign a line number if it is a print step and do not update this step into our soFar list
			//print the proof so far
			//set nextLineHelper to an arbitrary number (any number that is not -1, 0, 1) 
			//this is so that nextLine won't be updated the next time extendProof is called
		if (s.getReason().equals("print")) {
			printProof();			
		}

		//if it's not a print step case, it is part of the proof
		else {
			//check if any referenced line numbers are legal
			LinkedList<Step> refLines = s.getPrevLines();
			for (Step refStep : refLines) {
				if (!LineNumber.hasScope(nextLine, refStep.getLineNumber())) {
					throw new IllegalLineException("Cannot reference line number: " + refStep.getLineNumber());
				}
			}
			
			//assign line number to the step
			s.setLineNumber(nextLine);

			//check logic and update nextLineHelper
				//nextLineHelper will help determine what the nextLine will be
			nextLineHelper = checkLogic(s);

			//if we are jumping out of subproof, remove the recent toProve expression from the stack
			if (nextLineHelper == -1) {
				toProve.pop();
			}

			//if we are jumping in a subproof, add the expression to the toProve stack
			else if (nextLineHelper == 1) {
				toProve.push(s.getExpression());

				//if this is the first time to "jump in", then this is the first show step, so don't jump in
				if (soFar.size() == 0) {
					nextLineHelper = 0;
				}
	 		}

	 		//add the step to soFar
	 		soFar.addLast(s);
	 		
			//determine what the next line number will be
			//-1: if we need to "jump out" of the line i.e. exit subproof
			//0: if we need to continue proof/subproof
			//+1: if we need to "jump in" the line i.e. enter subproof
	 		
	 		if (nextLineHelper == -1) {
	 			if (!toProve.empty()) {
	 				nextLine = nextLine.jumpOut();
	 			}
	 		}
	 		
	 		else if (nextLineHelper == 0) {
	 			nextLine = nextLine.nextLine();
	 		}
	 		
	 		else if (nextLineHelper == 1) {
	 			nextLine = nextLine.jumpIn();
	 		}
	 	}
	}

	private Step makeStep (String x) throws IllegalLineException {

		//makes a step object from the information given in String x
		//it will return a step object with no line number assigned yet

		StringTokenizer st = new StringTokenizer(x);
		int numTokens = st.countTokens();

		Step myStep;
		Expression myExp = null;
		String myReason;
		LineNumber myLine1;
		LineNumber myLine2;

		//there can only be a max of 4 tokens in any line and a min of 1
		if (numTokens == 0 || numTokens > 4) {

			throw new IllegalLineException("invalid number of tokens: " + x);
		}

		myReason = st.nextToken();
		myStep = new Step(myReason);

		if (!(acceptableReasons.contains(myReason) || myTheorems.getNames().contains(myReason))) {
			throw new IllegalLineException("invalid reason: " + myReason);
		}

		//print
		if (myReason.equals("print")) {
			if (numTokens != 1) {
				throw new IllegalLineException("invalid format: " + x);
			}
		}

		//show or assume
		else if (myReason.equals("show") || myReason.equals("assume")) {
			if (numTokens != 2) {
				throw new IllegalLineException("invalid format: " + x);
			}

			myExp = new Expression(st.nextToken());
		}

		//mp, mt, or co
		else if (myReason.equals("mp") || myReason.equals("mt") || myReason.equals("co")) {

			if (numTokens != 4) {
				throw new IllegalLineException("invalid format: " + x);
			}

			myLine1 = new LineNumber(st.nextToken());
			myLine2 = new LineNumber(st.nextToken());
			myExp = new Expression(st.nextToken());

			//adds the two lines to be referenced in myStep
			//throws an error if either of the two lines cannot be found in proof
			myStep.setPrevLines(findStep(myLine1), findStep(myLine2));

		}

		//ic or repeat
		else if (myReason.equals("ic") || myReason.equals("repeat")) {

			if (numTokens != 3) {
				throw new IllegalLineException("invalid format: " + x);
			}

			myLine1 = new LineNumber(st.nextToken());
			myExp = new Expression(st.nextToken());

			myStep.setPrevLines(findStep(myLine1));

		}

		//theorem
		else {
			if (!myTheorems.getNames().contains(myReason)) {
				throw new IllegalLineException("invalid theorem name: " + myReason);
			}
			if (numTokens != 2) {
				throw new IllegalLineException("invalid format: " + x);
			}

			myExp = new Expression(st.nextToken());
		}

		myStep.setExpression(myExp);

		return myStep;
	}

	private Step findStep (LineNumber l) throws IllegalLineException {
		// Given a LineNumber reference this finds the Step object corresponding to that number.
		for (Step s : this.soFar) {
			if (s.getLineNumber().equals(l)) {
				return s;
			}
		}
		throw new IllegalLineException("Line reference not found.");
	}

	private int checkLogic (Step s) throws IllegalInferenceException {

		String reason = s.getReason();
		Expression myExp = s.getExpression();
		
		if (soFar.size() == 0) {
			if (!reason.equals("show")) {
				throw new IllegalInferenceException("invalid first line: requires show");
			}
			
			//if the size is 0 and reason is show, then we know that proof has started
			started = true;
		}

		if (reason.equals("mp") || reason.equals("mt") || reason.equals("co")) {
			Step ref1 = s.getPrevLines().get(0);
			Step ref2 = s.getPrevLines().get(1);

			if (reason.equals("mp")) {
				if (!checkMP(s, ref1, ref2)) {
					if (!checkMP(s, ref2, ref1)) {
						throw new IllegalInferenceException("invalid mp call");
					}
				}

				if (toProve.peek().equals(myExp)) {
					return -1;
				}
				return 0;
			}

			else if (reason.equals("mt")) {
				if (!checkMT(s, ref1, ref2)) {
					if (!checkMT(s, ref2, ref1)) {
						throw new IllegalInferenceException("invalid mt call");
					}
				}

				if (toProve.peek().equals(myExp)) {
					return -1;
				}
				return 0;

			}

			//reason.equals("mo")
			else {
				if (!checkMO(s, ref1, ref2)) {
					if (!checkMO(s, ref2, ref1)) {
						throw new IllegalInferenceException("invalid mo call");
					}
				}

				if (toProve.peek().equals(myExp)) {
					return -1;
				}
				return 0;
			}
		}

		if (reason.equals("ic")) {
			Step ref = s.getPrevLines().get(0);

			//From the expression E2, we can infer E1=>E2 for any expression E1.
			//e2 refers to E2
			//myExp refers to E1=>E2
			Expression e2 = ref.getExpression();

			if (!myExp.getMain().equals("=>")) {
				throw new IllegalInferenceException("invalid ic call");
			}

			if (!e2.equals(myExp.getRight())) {
				throw new IllegalInferenceException("invalid ic call");
			}

			if (toProve.peek().equals(myExp)) {
				return -1;
			}

			return 0;
		}

		if (reason.equals("show")) {
			
			return 1;
		}

		if (reason.equals("assume")) {
			if (soFar.size() == 0) {
				throw new IllegalInferenceException("invalid assume call: requires show in prev step");
			}
			Step prevStep = soFar.getLast();
			if (!prevStep.getReason().equals("show")) {
				throw new IllegalInferenceException("invalid assume call: requires show in prev step");
			}

			Expression prevExp = prevStep.getExpression();

			//After the step show E1=>E2, the step assume E1 may appear
			if (prevExp.getMain().equals("=>")) {
				if (!myExp.equals(prevExp.getLeft())) {
					throw new IllegalInferenceException("invalid assume call: expected E1 after E1=>E2 in prev step");
				}
			}

			//After the step show E, the step assume ~E may appear. 
			else if (myExp.getMain().equals("~")) {
				if (!prevExp.equals(myExp.getLeft())) {
					throw new IllegalInferenceException("invalid assume call: expected ~E after E in prev step");
				}
			}

			return 0;
		}

		if (reason.equals("repeat")) {
			//expression at line referenced must equal to myExp
			Step ref = s.getPrevLines().get(0);
			if (!myExp.equals(ref.getExpression())) {
				throw new IllegalInferenceException("invalid repeat call: line referenced does not match expression");
			}
			if (toProve.peek().equals(myExp)) {
				return -1;
			}
			return 0;
		}

		//code reaches here when reason is a theorem name
		else {
			Expression theoremExp = myTheorems.find(reason);
			if (!isTrue(theoremExp, myExp)) {
				throw new IllegalInferenceException("invalid theorem call: theorem and proof don't match");
			}

			if (toProve.peek().equals(myExp)) {
				return -1;
			}

			return 0;
		}
	}

	private boolean checkMP(Step s, Step ref1, Step ref2) {
		//From the expressions E1 and (E1=>E2) we can infer E2
		//e1 refers to E1
		//exp refers to (E1=>E2)
		//e2 refers to E2

		Expression e1 = ref1.getExpression();
		Expression exp = ref2.getExpression();
		Expression e2 = s.getExpression();

		if (!exp.getMain().equals("=>")) {
			return false;
		}

		if (!e1.equals(exp.getLeft())) {
			return false;
		}

		if (!e2.equals(exp.getRight())) {
			return false;
		}

		return true;
	}

	private boolean checkMT(Step s, Step ref1, Step ref2) {
		//From the expressions ~E2 and (E1=>E2) we can infer ~E1
		//e2 refers to ~E2
		//exp refers to (E1=>E2)
		//e1 refers to ~E1

		Expression e2 = ref1.getExpression();
		Expression exp = ref2.getExpression();
		Expression e1 = s.getExpression();

		if (!e2.getMain().equals("~")) {
			return false;
		}

		if (!exp.getMain().equals("=>")) {
			return false;
		}

		if (!e1.getMain().equals("~")) {
			return false;
		}

		//check if E2 is in exp
		if (!e2.getLeft().equals(exp.getRight())) {
			return false;
		}

		//check if E1 is in exp
		if (!e1.getLeft().equals(exp.getLeft())) {
			return false;
		}

		return true;
	}

	private boolean checkMO(Step s, Step ref1, Step ref2) {
		//From the expressions E and ~E we can infer any expression. 
		//e refers to E
		//notE refers to ~E
		Expression e = ref1.getExpression();
		Expression notE = ref2.getExpression();

		if (!notE.getMain().equals("~")) {
			return false;
		}

		if (!notE.getLeft().equals(e)) {
			return false;
		}

		return true;
	}
	
	public void printProof () {
		// Prints the entire Proof so far
		for (Step s : this.soFar) {
			System.out.println(s.toString());
		}
	}

	public String toString ( ) {
		String a = "";
		for (Step s : this.soFar) {
			a += s.toString();
			a += "\n";
		}
		return a;
	}

	public boolean isComplete ( ) {
		return started && toProve.empty();
	}
	
	public boolean isTrue(Expression theorem, Expression proof) {
		
		return theorem.matches(proof);
	}
}