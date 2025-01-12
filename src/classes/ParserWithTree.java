package classes;

import cup.example.*;

public class ParserWithTree extends Parser{

	public ParserWithTree() 
	{
		super();
	}
	
	private TreeNode createList(TreeNode list, TreeNode elem, String text) {
		TreeNode newNode;
		
		if (list != null)
			newNode = list;
		else
			newNode = new TreeNode(text);
		
		newNode.addChild(elem); 
		
		return newNode;
	}
	
	protected TreeNode createDeclList(TreeNode dl, TreeNode d) {
		TreeNode newNode = createList(dl, d, "declaration_list");
		
		return newNode;
	}
	
	protected TreeNode createParamList(TreeNode pl, TreeNode p) {
		TreeNode newNode = createList(pl, p, "param_list");
		
		return newNode;
	}
	
	protected TreeNode createParam(TreeNode ts, String i, Integer array) {
		TreeNode newNode;
		if (array == 1) {
			newNode = new TreeNode("param_array");
			newNode.addChild(new TreeNode("Array"));
		}
		else {
			newNode = new TreeNode("param");
		}
			
		newNode.addChild(ts);
		newNode.addChild(new TreeNode(i.toString()));
		
		return newNode;
	}
	
	protected TreeNode createTypeSpecifier(String typeName)
  	{ 
		TreeNode newNode = new TreeNode("TypeSpecifier", typeName);
  		return newNode;
  	}
	
	protected TreeNode createFunctionDeclarationNode(TreeNode ts, String i, TreeNode ps, TreeNode cs) 	
 	{ 
		TreeNode newNode = new TreeNode("fun_declaration", i);
		newNode.addChild(ts);
		//newNode.addChild(new TreeNode(i.toString()));
		newNode.addChild(ps);
		newNode.addChild(cs);
 		return newNode; 
 	}
	
	protected TreeNode createVarDeclaration(TreeNode ts, String i, Integer n)
	{
		TreeNode newNode;
		
		if (n > 0) {
			newNode = new TreeNode("var_declaration_array", i);
		}
		else {
			newNode = new TreeNode("var_declaration", i);
		}
		
		newNode.addChild(ts);
		newNode.addChild(new TreeNode(i.toString()));
		newNode.addChild(new TreeNode(n.toString())); 
		return newNode;
	}
	
	private TreeNode createDeclStmt(TreeNode list, TreeNode element, String text) {
		TreeNode newNode;
		
		if (list != null) {
			newNode = list;
			newNode.addChild(element);
		}
		else {
			newNode = new TreeNode(text);
		}
		
		return newNode;
	}
	
	protected TreeNode createStatementList(TreeNode sl, TreeNode s) {
		TreeNode newNode = createDeclStmt(sl, s, "statement_list");
		
		return newNode;
	}
	
	protected TreeNode createLocalDecl(TreeNode ld, TreeNode vd) {
		TreeNode newNode = createDeclStmt(ld, vd, "local_declarations");
		
		return newNode;
	}
	
	protected TreeNode selectionStmt(TreeNode exp, TreeNode stmt, TreeNode else_stmt) {
		TreeNode newNode = new TreeNode("selection_stmt");
		newNode.addChild(new TreeNode("IF"));
		newNode.addChild(exp); 
		newNode.addChild(stmt); 
		if (else_stmt != null) {
			newNode.addChild(new TreeNode("ELSE"));
			newNode.addChild(else_stmt); 
		}
	   	return newNode;
	}
	
	protected TreeNode iterationStmt(TreeNode exp, TreeNode stmt) {
		TreeNode newNode = new TreeNode("iteration_stmt");
		newNode.addChild(new TreeNode("WHILE"));
		newNode.addChild(exp); 
		newNode.addChild(stmt); 
		return newNode;
	}
	
	protected TreeNode compoundStmt(TreeNode ld, TreeNode sl) {
		TreeNode newNode = new TreeNode("compound_stmt");
		newNode.addChild(ld);
		newNode.addChild(sl);
	   	
		return newNode;
	}
	
	protected TreeNode expressionStmt(TreeNode exp) {
		TreeNode newNode;
		
		if (exp == null) {
			newNode = new TreeNode("empty_expression_stmt");
		}
		else {
			newNode = new TreeNode("expression_stmt");
			newNode.addChild(exp); 
		}
	   	
		return newNode;
	}
	
	protected TreeNode returnStatement(TreeNode exp) {
		TreeNode newNode = new TreeNode("return_stmt");
		newNode.addChild(new TreeNode("RETURN"));
		if (exp != null)
			newNode.addChild(exp); 
	   
		return newNode;
	}
	
	protected TreeNode addExpression(TreeNode exp, TreeNode v) {
		TreeNode newNode;
		if (v != null) {
			newNode = new TreeNode("expression");
			newNode.addChild(v);
			newNode.addChild(new TreeNode("ASSIGN"));
			newNode.addChild(exp); 
		}
		else {
			newNode = exp;
		}
		
		
		return newNode;
	}
	
	protected TreeNode addVar(String i, TreeNode exp) {
		   TreeNode newNode;
		   
		   if (exp != null) {
			   newNode = new TreeNode("var_array");
			   newNode.addChild(exp);
		   }
		   else {
			   newNode = new TreeNode("var");
		   }
		   newNode.addChild(new TreeNode(i.toString()));
		   
		   return newNode;
	}
	
	protected TreeNode simpleExpression(TreeNode ae, TreeNode rel, TreeNode exp) {
		TreeNode newNode;
		
		if (rel != null && exp != null) {
			   newNode = new TreeNode("simple_expression");
			   newNode.addChild(ae);
			   newNode.addChild(rel);
			   newNode.addChild(exp);
		}
		else {
			newNode = ae;
		}
		
		return newNode;
	}

	protected TreeNode additiveExpression(TreeNode ae, TreeNode op, TreeNode t) {
		TreeNode newNode;
		
		if (ae != null && op != null) {
			newNode = new TreeNode("additive_expression");
			newNode.addChild(ae); 
			newNode.addChild(op); 
			newNode.addChild(t); 
		}
		else {
			newNode = t;
		}
		
		return newNode;
	}

	protected TreeNode addTerm(TreeNode tm, TreeNode op, TreeNode f) {
		TreeNode newNode;
		
		if (tm != null && op != null) {
			newNode = new TreeNode("term");
			newNode.addChild(tm); 
			newNode.addChild(op); 
			newNode.addChild(f); 
		}
		else {
			newNode = f;
		}
		
		return newNode;
	}
	
	protected TreeNode argList(TreeNode al, TreeNode exp) {
		TreeNode newNode;

		newNode = new TreeNode("arg_list");
		if (al != null) {
			newNode.addChild(al); 
			newNode.addChild(new TreeNode("COMMA"));
		}
		newNode.addChild(exp);
		
		return newNode;
	}
	
}
