package cup.example;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char
%{
	

    public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }
    
    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code, 
						new Location(yyline+1, yycolumn +1, yychar), 
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength()), lexem);
    }
    
    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}

/* Declare the lexical states */
%state COMMENT
/* Regular expressions for tokens */
Newline    = \r | \n | \r\n
Whitespace = [ \t\f] | {Newline}
NUM	       = [0-9]+
ID = [a-zA-Z_][a-zA-Z0-9_]*
StringChar = ([ \t\f]|[ \n\t]|\w)
Quotes = \"
StringLiteral = {Quotes}([^\"\\\n]|\\[nrtbfv\\\"']|\\\\)*{Quotes}
IntegerLiteral = [0-9]+

/* comments */

Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" {CommentContent} \*+ "/"
EndOfLineComment = ("//"|"\#")[^\r\n]*{Newline}
CommentContent = ( [^*] | \*+[^*/] )*

%eofval{
    return symbolFactory.newSymbol("EOF", sym.EOF);
%eofval}

%state CODESEG

%%  

/* Whitespace and comments */
/* Keywords */
<YYINITIAL> {
  {Whitespace} { }   
  {TraditionalComment}	{	}
  
    "else"       { return symbolFactory.newSymbol("ELSE", sym.ELSE); }
    "if"         { return symbolFactory.newSymbol("IF", sym.IF); }
  	"int"        { return symbolFactory.newSymbol("INT", sym.INT); }
    "return"     { return symbolFactory.newSymbol("RETURN", sym.RETURN); }
    "void"       { return symbolFactory.newSymbol("VOID", sym.VOID); }
    "while"      { return symbolFactory.newSymbol("WHILE", sym.WHILE); }
    
    "+"          { return symbolFactory.newSymbol("PLUS", PLUS); }
    "-"          { return symbolFactory.newSymbol("MINUS", MINUS); }
    "*"          { return symbolFactory.newSymbol("TIMES", TIMES); }
    "/"          { return symbolFactory.newSymbol("DIVIDE", DIVIDE); }
    "<"          { return symbolFactory.newSymbol("LT", LT); }
    "<="         { return symbolFactory.newSymbol("LE", LE); }
    ">"          { return symbolFactory.newSymbol("GT", GT); }
    ">="         { return symbolFactory.newSymbol("GE", GE); }
    "=="         { return symbolFactory.newSymbol("EQ", EQ); }
    "!="         { return symbolFactory.newSymbol("NEQ", NEQ); }
    "="          { return symbolFactory.newSymbol("ASSIGN", ASSIGN); }
    ";"          { return symbolFactory.newSymbol("SEMI", SEMI); }
    ","          { return symbolFactory.newSymbol("COMMA", COMMA); }
    "("          { return symbolFactory.newSymbol("LPAREN", LPAREN); }
    ")"          { return symbolFactory.newSymbol("RPAREN", RPAREN); }
    "["          { return symbolFactory.newSymbol("LBRACK", LBRACK); }
    "]"          { return symbolFactory.newSymbol("RBRACK", RBRACK); }
    "{"          { return symbolFactory.newSymbol("LBRACE", LBRACE); }
    "}"          { return symbolFactory.newSymbol("RBRACE", RBRACE); }
    
    "/*"          { return symbolFactory.newSymbol("CSTART", CSTART); }
    "*/"          { return symbolFactory.newSymbol("CEND", CEND); }
  
   {ID} 		{ return symbolFactory.newSymbol("ID", ID, yytext()); }
   {NUM}    	{ return symbolFactory.newSymbol("NUM", NUM, Integer.parseInt(yytext())); }
  
	/* Error handling for unrecognized characters */
    .            { emit_warning("Unrecognised character: " + yytext()); }
}
