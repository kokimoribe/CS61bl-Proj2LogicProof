Daniel Kennedy cs61bl-hq

We spent a lot of time planning and designing for this project, specifically, rationalizing what the various classes should do and how they should interact with each other. I was responsible for the LineNumber class, a fair portion of the Proof class, and also wrote two new classes, Step and Tree, that abstracted our implementation of the ProofChecker and provided simplicity and organization. Part of my influence on the design of the project was to break larger classes and methods into smaller classes and helper methods (for example - I delegated tasks in the extendsProof method to other helper methods). 
In terms of testing, with such a complicated project it was a challenge to write tests that only test one thing at a time. The Step and Tree classes were simple enough to not need testing beyond the tests in other parts of the project that use those classes. For testing LineNumber, I implemented tests for each method. Some of the methods require other methods to have been called previously, so I had to make sure to test the basic methods first before moving on to more complicated ones. Fortunately there are not too many niche cases for possible LineNumbers so only a few tests were necessary. Testing the Proof class was by far the most challenging due to the number of possible cases. I designed tests for extendProof with simplicity in mind at first, so that each test would test only one case. I wrote tests with catching niche situations in mind, such as tests for catching illegal inputs for the very first line of the proof, and the case that a "show" line is entered multiple times in a row, and the case that a theorem is invoked incorrectly. 




Koki Moribe cs61bl-fl

Expression.java - test catches syntax errors in a given string input for an expression. syntax errors include spaces, multiple variables in a row, multiple operators a row, missing parentheses, etc. etc. It successfully creates an Expression object when given the correct syntax and throws an IllegalLineException when given incorrect syntax.

TheoremSet.java - test catches invalid theorem names in a given string input for a theorem title. invalid theorem names include any of the predefined reasons/operators. The most recently added theorem replaces any theorem with the same name. All other additional methods return the expected results.

Step.java - test catches if any valid input for a Step object fails to create. Public methods return the expected results.

LineNumber.java - test catches for any LineNumbers that do not correctly update via nextLine, jumpIn, and jumpOut. Constructs a LineNumber successfully when given a String input. Also is able to display toString correctly and has a method to catch illegal line references.

Proof.java - test catches any syntax errors outside of Expression and TheoremSet creation, and catches all semantic errors. Errors throw the appropriate exception. All public methods return the expected results and extendProof successfully checks for both syntax and semantic errors and successfully updates each step into a list of steps. 

In general, we were able to approach the project as according to plan when we first discussed about it as a group. There were some things that had to be reworked, like the tree class, but the overall approach remained the same. We understood that the Proof.java would be the main challenge, and so we took care of implementing all the other necessary classes first. Once that was completed, we wrote pseudocode to get an idea  of how the procedure would work to get the extendProof successfully working. There were a number of times when discussion of each other's code helped us catch any missed error handling.
We individually worked on the classes outside of Proof.java. Once that was done, we collectively worked on the Proof.java.




Jeff Lords cs61bl-fn

Due to a heavy work schedule, Jeff wasn't able to contribute in the initial design and writing of the classes.  After being brought up to speed by Koki and Daniel, Jeff worked on a checkSemantics method for the expression class to be called to check the validity of steps in the proof.  However Koki's code was much better so this method didn't end up being used in the project.  Jeff also read through all of the code looking for small errors/bugs.


