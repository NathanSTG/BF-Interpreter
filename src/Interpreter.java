import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * This class will provide methods for processing and executing a brainfuck source file.
 * The processor will take in a brainfuck source file into the constructor, process the source
 * file, and return an output string. <b>The input methods of this class will only work when run
 * in the console.</b> Further modification to the <i>input()</i> method will be needed to make
 * input work through other methods.
 */
public class Interpreter{
	/* The instance variables */
	private String sourceText;
	private String outputText;
	private String debugText;
	private int debugIndex;
	private int pointerIndex;
	private int[] cells;
	private int loopStartIndex;
	private boolean inLoop;
	private boolean loop;
	private Scanner s;
	private Scanner scanner2;
	
	/**
	 * Constructor will take in a File object of the source file.
	 * @param inputFile - File object of input file
	 */
	public Interpreter(File inputFile){
		s = new Scanner(System.in);
		
		try{
			scanner2 = new Scanner(new BufferedReader(new FileReader(inputFile.getPath())));
		}catch(Exception e){
			System.out.println("There was an error finding the file");
			return;
		}
		
		sourceText = "";
		
		while(scanner2.hasNext()){
			sourceText += scanner2.next();
		}
		
		outputText = "";
		debugText = "";
		debugIndex = 0;
		pointerIndex = 50000;
		cells = new int[100000];
		inLoop = false;
		loop = false;
		this.fillArray();
		debug("Source - File: " + inputFile.getPath());
	}
	
	public Interpreter(String source){
		s = new Scanner(System.in);
		sourceText = source;
		outputText = "";
		debugText = "";
		debugIndex = 0;
		pointerIndex = 50000;
		cells = new int[100000];
		inLoop = false;
		loop = false;
		this.fillArray();
		debug("Source - Editor tab");
	}
	
	/**
	 * Processes every character in the string that is the sourceText.
	 * @return - outputText
	 */
	public String process(){
		char[] chars;
		if(sourceText.length() <= 0){
			outputText = "Error: Invalid source";
			return outputText;
		}else{
			chars = sourceText.toCharArray();
		}
		debug("Number of characters: " + chars.length);
		for(int i = 0; i < chars.length; i++){
			if(inLoop){
				if(loop){
					i = loopStartIndex;
					loop = false;
				}
			}
			char c = chars[i];
			if(c == '+'){
				addToPointer();
			}else if(c == '-'){
				subtractFromPointer();
			}else if(c == '<'){
				movePointerLeft();
			}else if(c == '>'){
				movePointerRight();
			}else if(c == '.'){
				output();
			}else if(c == ','){
				getInput();
			}else if(c == '['){
				if(!inLoop){
					startLoop(i);
				}
			}else if(c == ']'){
				endLoop();
			}
		}
		return outputText;
	}
	
	private void startLoop(int i){
		inLoop = true;
		loop = true;
		loopStartIndex = i;
		debug("Entered a loop.");
	}
	
	private void endLoop(){
		if(inLoop){
			if(cells[pointerIndex] == 0){
				debug("Finished loop");
				loop = false;
				inLoop = false;
				return;
			}else{
				debug("Reached end of loop. Returning to beginning. Current pointer is at cell " + (pointerIndex - 50000) + " with a value of " + cells[pointerIndex]);
				loop = true;
				return;
			}
		}else{
			debug("Syntax error. ']' not expected");
		}
	}
	
	private void fillArray(){
		for(int i = 0; i < 100000; i++){
			cells[i] = 0;
		}
	}
	
	private void addToPointer(){
		cells[pointerIndex]++;
		debug("Added 1 to cell: " + (pointerIndex - 50000) + ". Cell " + (pointerIndex - 50000) + " = " + cells[pointerIndex]);
	}
	
	private void subtractFromPointer(){
		cells[pointerIndex]--;
		debug("Subtracted 1 from cell: " + (pointerIndex - 50000) + ". Cell " + (pointerIndex - 50000) + " = " + cells[pointerIndex]);
	}
	
	private void movePointerLeft(){
		pointerIndex--;
		debug("Moved pointer one space left. Pointer is now at cell " + (pointerIndex - 50000));
	}
	
	private void movePointerRight(){
		pointerIndex++;
		debug("Moved pointer one space right. Pointer is now at cell " + (pointerIndex - 50000));
	}
	
	private void output(){
		char c = toChar(cells[pointerIndex]);
		outputText += c;
		debug("Output text: '" + c + "'");
	}
	
	private void getInput(){
		char in = s.next().charAt(0);
		int num = toInt(in);
		cells[pointerIndex] = num;
	}
	
	private void debug(String s){
		debugText += debugIndex + ". " + s + "\n";
		debugIndex++;
	}
	
	public String getInputText() {
		return sourceText;
	}
	
	public String getOutputText() {
		return outputText;
	}
	
	public String getDebugText() {
		return debugText;
	}
	
	private int toInt(char c){
		return (int)(c);
	}
	
	private char toChar(int i){
		return (char)(i);
	}
}