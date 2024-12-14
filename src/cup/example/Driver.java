package cup.example;

import java_cup.runtime.*;
import classes.*;
import symbols.*;

class Driver {

	public static void main(String[] args) throws Exception {
		Parser parser = new ParserWithTree();
		parser.parse();
		//parser.debug_parse();
		
		Tree tree = parser.getSyntaxTree();
		tree.printTree();
		SymbolsTable table = new SymbolsTable(tree);
		table.createTable();
		table.printTable();
		
		// Test comment for git
		/*
		ComplexSymbolFactory f = new ComplexSymbolFactory();
		  
		  File file = new File("input.txt");
		  FileInputStream fis = null;
		  try {
		    fis = new FileInputStream(file);
		  } catch (IOException e) {
		    e.printStackTrace();
		  } 
		  Lexer lexer = new Lexer(f,fis);
		  Symbol currentSymbol ;
		  while ((currentSymbol = lexer.next_token()).sym != sym.EOF) {
			  System.out.println("currentSymbol == " + currentSymbol);
		  }
		*/ 
	}
	
}