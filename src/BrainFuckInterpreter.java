import java.io.File;


public class BrainFuckInterpreter {
	Interpreter i;
	GUI gui;
	
	public static void main(String[] args){
		BrainFuckInterpreter bfi = new BrainFuckInterpreter();
	}
	
	public BrainFuckInterpreter(){
		gui = new GUI(this);
		gui.setVisible(true);
	}
	
	public void runCodeFromFile(String filename){
		try{
			i = new Interpreter(new File(filename));
		}catch(Exception e){
			gui.setOutput("Error: Failed to locate file!");
		}
		i.process();
		gui.setInput(i.getInputText());
		gui.setOutput(i.getOutputText());
		gui.setDebug(i.getDebugText());
	}

	public void runCodeFromSource(String s) {
		i = new Interpreter(s);
		i.process();
		gui.setOutput(i.getOutputText());
		gui.setDebug(i.getDebugText());
	}
	
}