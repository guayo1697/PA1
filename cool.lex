/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int white = 0;
    int comentario = 0;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
   
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
		case COMMENT:
	    	yybegin(ESTOFERROR);	
	   		return new Symbol(TokenConstants.ERROR, "Se alcanzo EOF en comentario!");
	   case ESTOFERROR:
	   break;
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%state COMMENT,ESTOFERROR,COMMENTLINEA

%%

<COMMENT,YYINITIAL>\(\* 		{yybegin(COMMENT);comentario++;}
<COMMENT>\*\)				{comentario--; if (comentario == 0) {yybegin(YYINITIAL);}}
<YYINITIAL>\*\)				{return new Symbol(TokenConstants.ERROR);}
<COMMENT>.|\r|\n				{}
<YYINITIAL>[" "]|[\t]|[\r]|[\f]       {white++;} 
<YYINITIAL>"--"[^\n]				{}   
<YYINITIAL>"=>"				{return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>\b 				{}                              
<YYINITIAL>\n          		{curr_lineno++;}
<YYINITIAL>\f 				{}
<YYINITIAL>["c" | "C"]["l" | "L"]["a" | "A"]["s | S"]["s | S"]	{return new Symbol(TokenConstants.CLASS);}
<YYINITIAL>["e" | "E"]["l" | "L"]["s" | "S"]["e" | "E"]			{return new Symbol(TokenConstants.ELSE);}
<YYINITIAL>["t"]["\r" | "\R"]["\u" | "\U"]["\e" | "\E"]       {return new Symbol(TokenConstants.BOOL_CONST, "true"); }
<YYINITIAL>["f"]["\a" | "\A"]["\l" | "\L"]["\s" | "\S"]["\e" | "\E"]      {return new Symbol(TokenConstants.BOOL_CONST, "false"); }
<YYINITIAL>["f" | "F"]["i" | "I"]	{return new Symbol(TokenConstants.FI);}
<YYINITIAL>["i" | "I"]["f" | "F"]	{return new Symbol(TokenConstants.IF);}
<YYINITIAL>["i" | "I"]["n" | "N"]	{return new Symbol(TokenConstants.IN);}
<YYINITIAL>["i" | "I"]["n" | "N"]["h" | "H"]["e" | "E"]["r" | "R"]["i" | "I"]["t" | "T"]["s" | "S"]	{return new Symbol(TokenConstants.INHERITS);}
<YYINITIAL>["l" | "L"]["e" | "E"]["t" | "T"]			{return new Symbol(TokenConstants.LET);}
<YYINITIAL>["l" | "L"]["o" | "O"]["o" | "O"]["p" | "P"]			{return new Symbol(TokenConstants.LOOP);}
<YYINITIAL>["p" | "P"]["o" | "O"]["o" | "O"]["l" | "L"]			{return new Symbol(TokenConstants.POOL);}
<YYINITIAL>["t" | "T"]["h" | "H"]["e" | "E"]["n" | "N"]			{return new Symbol(TokenConstants.THEN);}
<YYINITIAL>["w" | "W"]["h" | "H"]["i" | "I"]["l" | "L"]["e" | "E"]			{return new Symbol(TokenConstants.WHILE);}
<YYINITIAL>["c" | "C"]["a" | "A"]["s" | "S"]["e" | "E"]			{return new Symbol(TokenConstants.CASE);}
<YYINITIAL>["e" | "E"]["s" | "S"]["a" | "A"]["c" | "C"]			{return new Symbol(TokenConstants.ESAC);}
<YYINITIAL>["n" | "N"]["e" | "E"]["w" | "W"]			{return new Symbol(TokenConstants.NEW);}
<YYINITIAL>["o" | "O"]["f" | "F"]	{return new Symbol(TokenConstants.OF);}
<YYINITIAL>"."          {return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"@"          {return new Symbol(TokenConstants.AT); }
<YYINITIAL>"~"          {return new Symbol(TokenConstants.NEG); }
<YYINITIAL>["i"]["s"]["v"]["o"]["i"]["d"]	{return new Symbol(TokenConstants.ISVOID);}
<YYINITIAL>"/"          {return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"*"          {return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"+"          {return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"-"          {return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>"<="         {return new Symbol(TokenConstants.LE); }
<YYINITIAL>"<"          {return new Symbol(TokenConstants.LT); }
<YYINITIAL>"="          {return new Symbol(TokenConstants.EQ); }
<YYINITIAL>"not"        {return new Symbol(TokenConstants.NOT); }
<YYINITIAL>"<-"         {return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>","          {return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>":"          {return new Symbol(TokenConstants.COLON); }
<YYINITIAL>";"          {return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>[\{] 		{return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>[\}] 		{return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>[\(]  		{return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>[\)]  		{return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>[0-9]+       {	AbstractSymbol inte = AbstractTable.inttable.addString(yytext());
							return new Symbol(TokenConstants.INT_CONST,inte); }
<YYINITIAL>[a-z][a-zA-Z0-9\_]*         {	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
<YYINITIAL>[\"][^\"\0\n\\]+[\"]  			{
										String token = yytext().substring(1, yytext().length() - 1);
										int l = token.length();
										return new Symbol(TokenConstants.STR_CONST, new StringSymbol(token,l,0));
									 }
<YYINITIAL>[A-Z][a-zA-Z0-9\_]*       {	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
<YYINITIAL>error		{return new Symbol(TokenConstants.error);}


.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
