package symbols;

import java.util.HashMap;
import java.util.Map;

import classes.Tree;
import classes.TreeNode;


public class SymbolsTable {
	
	private Tree syntaxTree;
	private HashMap<String, SymbolDetails> table = new HashMap<String, SymbolDetails>();
	
	private void extractSymbolsFromNode(TreeNode node, String currentContext, IdentifierScope scope)
	{
		String context = currentContext;
		IdentifierScope localScope = scope;
		if (node.getData().equals("var_declaration") || node.getData().equals("var_declaration_array"))
		{
			var currentChildren = node.getChildren();
			
			if (currentChildren.length == 3) {
				var identifiersNode = currentChildren[1];
				var variableNames = identifiersNode.getData();
			
				    String symbolName = variableNames;
				    
				    if (table.containsKey(symbolName)){
				        System.err.println("Error: Variable '" + symbolName + "' is declared multiple times in the same scope.");
				        System.exit(1);
				    }
				    else {
						SymbolDetails details = new SymbolDetails();
						details.contextName = currentContext;			
						details.symbolName = node.getExtraData();
						details.dataType = node.getChildren()[0].getExtraData();
						details.symbolScope = scope;
						details.symbolType = SymbolType.Variable;
						table.put(details.symbolName, details);
				    }
			}
			
		}
		if (node.getData().equals("fun_declaration"))
		{
			String funcName = node.getExtraData();
		    
		    if (table.containsKey(funcName)){
		        System.err.println("Error: Function '" + funcName + "' is declared multiple times in the same scope.");
		        System.exit(1);
		    }

			var currentChildren = node.getChildren();
			SymbolDetails details = new SymbolDetails();
			details.contextName = currentContext;			
			details.symbolName = node.getExtraData();
			details.dataType = node.getChildren()[0].getExtraData();
			details.symbolScope = scope;
			details.symbolType = SymbolType.Function;
			context = details.symbolName;
			localScope = IdentifierScope.Local;
			table.put(details.symbolName, details);
		    
			if (currentChildren.length == 3) {
				var paramsNode = currentChildren[1];
				
				if (paramsNode.getData().equals("param_list")) {
					var paramsNames = paramsNode.getChildren();
					
					for (int i = 0; i < paramsNames.length; i++) {
						String symbolName = paramsNames[i].getChildren()[1].getData();
						
						if (paramsNames[i].getData() == "param_array") {
							symbolName = paramsNames[i].getChildren()[2].getData();
						}
						
						if (table.containsKey("Parameter " + symbolName + " from Function " + funcName)) {
							System.err.println("Error: Parameter '" + symbolName + "' is declared multiple times in the same scope, in function: " + funcName);
							System.exit(1);
						} else {
							SymbolDetails details1 = new SymbolDetails();
							details1.contextName = funcName;
							details1.symbolName = "Parameter " + symbolName + " from Function " + funcName;
							details1.dataType = currentChildren[0].getData();
							details1.symbolScope = IdentifierScope.Local;
							details1.symbolType = SymbolType.Variable;
							table.put(details1.symbolName, details1);	
						}
					}
				}
			}
		   	context = funcName;
		   	localScope = IdentifierScope.Local;
		}
		for (int i = 0; i < node.getChildren().length; i++)
		{
			extractSymbolsFromNode(node.getChildren()[i], context, localScope);
		}		
	}
	
	public SymbolsTable(Tree syntaxTree) 
	{
		this.syntaxTree = syntaxTree;
	}
	
	public void createTable()
	{
		extractSymbolsFromNode(syntaxTree.getRoot(), "Global", IdentifierScope.Global);
	}	
	
	public SymbolDetails getSymbol(String symbol)
	{
		if (table.containsKey(symbol))
		{
			return table.get(symbol);
		}
		return null;
	}
	
	public void printTable()
	{
		for (Map.Entry<String, SymbolDetails> mapEntry : table.entrySet()) {
			String symbol = mapEntry.getKey();
			SymbolDetails details = mapEntry.getValue();
			System.out.println("------------ SYMBOL: " + symbol + " -----------------");
			System.out.println("Data Type: " + details.dataType);
			System.out.println("Context: " + details.contextName);
			System.out.println("Symbol Type: " + details.symbolType);
			System.out.println("Symbol Scope: " + details.symbolScope);
		}		
	}
	
}
